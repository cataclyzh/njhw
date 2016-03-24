package com.cosmosource.common.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.common.service.AuthorityManager;

/**
 * 密码失效管理器,用于定时任务调用
 * @author WXJ
 *
 */
public class PasswordExpiredManager{
		
	private static Logger logger = LoggerFactory.getLogger(PasswordExpiredManager.class);
	
	private AuthorityManager authorityManager;
	
	/**
	 * 密码失效时长(单位:天)
	 */
	private int passwordExpiredDays = 90;
	
	/**
	 * 公司编码
	 */
	private String company;
	
	public void updatePasswordExpired(){
		logger.info("执行密码失效定时任务,设置密码已失效用户状态为初始化:" + SecurityConstant.ACCOUNT_INIT);
		List<String> expiredLoginnameList = authorityManager.findPasswordExpired(passwordExpiredDays, company);
		logger.info("密码已失效用户数量为" + expiredLoginnameList.size());
		int result = authorityManager.updatePasswordExpired(expiredLoginnameList);
		logger.info("成功设置用户密码失效数量为" + result);
	}

	//--properties
	public void setPasswordExpiredDays(int passwordExpiredDays) {
		this.passwordExpiredDays = passwordExpiredDays;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	//--service
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}
	
}
