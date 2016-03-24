package com.cosmosource.common.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.common.service.AuthorityManager;

/**
 * 用户解锁任务管理器
 * @author WXJ
 *
 */
public class UnlockUserManager {
	
	private static Logger logger = LoggerFactory.getLogger(UnlockUserManager.class);
	
	private AuthorityManager authorityManager;
	
	/**
	 * 用户锁定时长(单位:天)
	 */
	private int unlockDays = 1;
	
	/**
	 * 公司编码
	 */
	private String company;
	
	public void updatePasswordExpired(){
		logger.info("执行用户解锁定时任务");
		List<String> unlockLoginnameList = authorityManager.findPasswordExpiredLockedUser(unlockDays, company);
		logger.info("达到解锁时间的用户数量为" + unlockLoginnameList.size());
		int result = authorityManager.updateUserUnlock(unlockLoginnameList);
		logger.info("成功解锁用户数量为" + result);
	}

	//--properties
	public void setCompany(String company) {
		this.company = company;
	}

	public void setUnlockDays(int unlockDays) {
		this.unlockDays = unlockDays;
	}

	//--service
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}
}
