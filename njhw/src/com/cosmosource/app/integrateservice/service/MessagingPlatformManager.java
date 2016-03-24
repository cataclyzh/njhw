package com.cosmosource.app.integrateservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.TCommonSmsBox;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonMsgBox;

public class MessagingPlatformManager extends BaseManager{
	/**
	 * 
	* @title: sendPhoneMessage 
	* @description: 短信发送平台
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public void savePhoneMessage(TCommonSmsBox tCommonSmsBox){
		 dao.save(tCommonSmsBox);
	}
	
	/**
	 * 
	* @title: findMessageButton 
	* @description: 短信发送平台--查询按钮使用
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<TCommonSmsBox> findMessageButton(final Page<TCommonSmsBox> page,final Map<String,Object> map){
		return sqlDao.findPage(page, "IntegrateSQL.findMessageButton", map);
	}
	
	/**
	 * 
	 * @title: findMessageButton 
	 * @description: 删除发件箱的的消息
	 * @author SQS
	 * @return
	 * @date 2013-3-19 上午11:11:33
	 * @throws
	 */
	public void deleteOutBoxMessages(Long ids){
		super.dao.deleteById(TCommonSmsBox.class, ids);
	}
	
	/**
	 * @description:
	 * @author sqs
	 * @param HashMap map
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> findRecipientMessage(HashMap pMap) {
		return   findListBySql("IntegrateSQL.findRecipientMessage", pMap);
	}
}
