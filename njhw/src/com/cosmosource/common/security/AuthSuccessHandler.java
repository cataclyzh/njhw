package com.cosmosource.common.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.DBManager;
import com.cosmosource.base.service.SpringContextHolder;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcSubsys;
import com.cosmosource.common.entity.TCommonLogOperation;
import com.cosmosource.common.service.AuthorityManager;
//import com.cosmosource.common.entity.TAcUser;
/**
 * @类描述:  权限验证成功的后处理
 * 
 * @作者： WXJ
 */
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
   
	private AuthorityManager authorityManager;
	private static Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication auth) throws IOException, ServletException {
		      

		logger.info("登录成功： 登录名：[{}] 登录时间：[{}] 登录IP：[{}] 登录SessionID：[{}] ", new Object[] {  auth.getName(), 
				DateUtil.getDateTime(),SpringSecurityUtils.getCurrentUserIp(),request.getSession().getId()});
		
    	
		Users loginUser = authorityManager.findUserByLoginName(auth.getName());
		
		request.getSession().setAttribute(Constants.LOGIN_NAME, auth.getName());
    	request.getSession().setAttribute(Constants.AUTH, auth);
    	    
    	//用户机构信息
    	request.getSession().setAttribute(Constants.ORG_ID, authorityManager.getOrgId(loginUser.getOrgId()));
    	Org org = authorityManager.getUserOrg(loginUser.getOrgId());
    	request.getSession().setAttribute(Constants.ORG_NAME, org.getName());
    	
    	request.getSession().setAttribute(Constants.USER_NAME, loginUser.getDisplayName());
    	request.getSession().setAttribute(Constants.USER_CODE, loginUser.getUCode());
    	request.getSession().setAttribute(Constants.USER_ID, loginUser.getUserid());
    	
    	request.getSession().setAttribute(Constants.COMPANY, "appadmin");
    	request.getSession().setAttribute(Constants.COMPANY_Min, "app");
    	
    	List<TAcSubsys> sys = authorityManager.getSysList(loginUser.getOrgId());    	
    	request.getSession().setAttribute(Constants.ORG_SUBSYS, sys);
    	
    	//DefaultPage
    	String dpStr = authorityManager.findDefaultPageByUserid(loginUser.getUserid().toString());
    	request.getSession().setAttribute(Constants.DEFAULTPAGE, dpStr);
    	
    	//获取用户扩展信息
    	NjhwUsersExp uep = authorityManager.getUserExp(loginUser.getUserid());
    	if (uep!=null) {
    		//USER_TEL_MAC 话机MAC
        	request.getSession().setAttribute(Constants.USER_TEL_MAC, uep.getTelMac());
        	if ("1".equals(uep.getCardType())){
        		NjhwTscard scard = authorityManager.getScard(loginUser.getUserid());
        		if (scard!=null) {
	        		//USER_CARD_NO 市民卡号
	            	request.getSession().setAttribute(Constants.USER_CARD_NO, scard.getCardId());
        		}
        	}else{
        		//USER_CARD_NO 临时卡号
            	request.getSession().setAttribute(Constants.USER_CARD_NO, uep.getTmpCard());
        	}        	
    	}
    	   
    	//获取当前登录人全部授权的功能菜单
    	List<Objtank> objtanks = authorityManager.getAuthAllMenuCollection(loginUser.getUserid());
    	request.getSession().setAttribute(Constants.ROLES_MENUS, objtanks);
    	
        //是否记录操作日志
        if("Y".equals(Constants.DBMAP.get("COMMON_WRITEOPLOG_YN"))){
    		DBManager mgr = SpringContextHolder.getBean("dbMgr");
    		
    		TCommonLogOperation logOp = new TCommonLogOperation();
    		
    		logOp.setOpTime(DateUtil.getSysDate());
    		logOp.setActionId("login.act");
    		logOp.setActionName("登录系统");
    		logOp.setLoginname(auth.getName());
    		logOp.setSessionId(request.getSession().getId());
    		logOp.setOpType("00");
    		logOp.setOpIp(SpringSecurityUtils.getCurrentUserIp());
    		mgr.save(logOp);
        	
        }
    	

    	
        super.onAuthenticationSuccess(request, response, auth);
    }
	
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

}