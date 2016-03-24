package com.cosmosource.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.service.AuthorityManager;
/**
 * @类描述:  权限验证失败的后处理
 * 
 * @作者： WXJ
 */
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	private static Logger logger = LoggerFactory.getLogger(AuthFailureHandler.class);
	
	/**
	 * 默认连续输入密码错误最大允许次数
	 */
	public static final int DEFAULT_MAX_PASSWORD_WRONG_COUNT = 5;
	
	/**
	 * 默认输入密码连续错误不锁定用户
	 */
	public static final boolean DEFAULT_PASSWORD_WRONG_LOCK = false;
	
	private AuthorityManager authorityManager;
	
	/**
	 * 用户名不存在转向页面
	 */
	private String usernameNotFoundFailureUrl;
	
	/**
	 * 密码错误转向页面
	 */
	private String badCredentialsFailureUrl;
	
	/**
	 * 用户锁定转向页面
	 */
	private String lockedFailureUrl;
	
	/**
	 * 密码过期转向页面
	 */
	private String credentialsExpiredFailureUrl;
	
	/**
	 * 用户停用转向页面
	 */
	private String disabledFailureUrl;
	
	/**
	 * 输入密码连续错误是否锁定用户
	 */
	private boolean passwordWrongLock = DEFAULT_PASSWORD_WRONG_LOCK;
	
	/**
	 * 连续输入密码错误最大允许次数
	 */
	private int maxPasswordWrongCount = DEFAULT_MAX_PASSWORD_WRONG_COUNT;
	
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    	logger.info("登录失败,登录时间:" + DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));
    	
    	if(exception != null && exception.getAuthentication() != null){
    		String loginname = exception.getAuthentication().getName();
        	if(exception instanceof UsernameNotFoundException){
    		    logger.info("登录失败原因:用户名:" + loginname + "不存在");
    		    setDefaultFailureUrl(usernameNotFoundFailureUrl);
            }
        	else if(exception instanceof BadCredentialsException){
            	logger.info("登录失败原因:密码错误");
        		if(passwordWrongLock){
            		Long wrongCount = authorityManager.findPasswordWrongCount(loginname);
            		//处理密码错误次数为null的情况,默认赋值为0
            		if(wrongCount == null){
            			wrongCount = 0L;
            		}
            		
            		if(wrongCount >= maxPasswordWrongCount){
            			//锁定用户
            			authorityManager.updateUserState(loginname, SecurityConstant.ACCOUNT_LOCKED);
            			logger.info("输入密码连续错误次数达到" + maxPasswordWrongCount + "次,锁定用户");
            		}
            		else {
            			//密码连续错误次数+1
            			int increaseWrongCount = wrongCount.intValue() + 1;
                		authorityManager.updatePasswordWrongCount(loginname, increaseWrongCount);
                		if(increaseWrongCount >= maxPasswordWrongCount){
                			authorityManager.updateUserLocked(loginname);
                			logger.info("输入密码连续错误次数达到" + maxPasswordWrongCount + "次,锁定用户");
                		}
                		else {
                			logger.info("输入密码连续错误次数:" + increaseWrongCount);
                		}         		
            		}
        		}
    		    setDefaultFailureUrl(badCredentialsFailureUrl);
            } 
        	else if(exception instanceof LockedException){
        		logger.info("登录失败原因:用户:" + loginname + "已锁定");	
        		setDefaultFailureUrl(lockedFailureUrl);
        	}
        	else if(exception instanceof CredentialsExpiredException){
        		logger.info("登录失败原因:密码已过期");
        		setDefaultFailureUrl(credentialsExpiredFailureUrl);
        	}
        	else if(exception instanceof DisabledException){
            	logger.info("登录失败原因:用户被停用");
            	setDefaultFailureUrl(disabledFailureUrl);
        	}
        	else {
        		logger.info(exception.getMessage());
        	}
    	}
    	    	
        super.onAuthenticationFailure(request, response, exception);
    }

    //-- service
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	//-- properties
	public void setPasswordWrongLock(boolean passwordWrongLock) {
		this.passwordWrongLock = passwordWrongLock;
	}

	public void setMaxPasswordWrongCount(int maxPasswordWrongCount) {
		this.maxPasswordWrongCount = maxPasswordWrongCount;
	}

	public void setLockedFailureUrl(String lockedFailureUrl) {
		this.lockedFailureUrl = lockedFailureUrl;
	}

	public void setUsernameNotFoundFailureUrl(String usernameNotFoundFailureUrl) {
		this.usernameNotFoundFailureUrl = usernameNotFoundFailureUrl;
	}

	public void setBadCredentialsFailureUrl(String badCredentialsFailureUrl) {
		this.badCredentialsFailureUrl = badCredentialsFailureUrl;
	}

	public void setCredentialsExpiredFailureUrl(String credentialsExpiredFailureUrl) {
		this.credentialsExpiredFailureUrl = credentialsExpiredFailureUrl;
	}

	public void setDisabledFailureUrl(String disabledFailureUrl) {
		this.disabledFailureUrl = disabledFailureUrl;
	}
    
}