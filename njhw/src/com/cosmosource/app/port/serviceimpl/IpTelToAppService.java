package com.cosmosource.app.port.serviceimpl;

import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** 
* @description: ip电话接口
* @author cjw
* @date 2013-4-16 下午03:47:23     
*/ 
public class IpTelToAppService {
	
	private static final Log log = LogFactory.getLog(IpTelToAppService.class);
	
	
	/** 
	* @title: messageWarn 
	* @description: 消息提醒
	* @author cjw
	* @param content
	* @param type
	* @param ipTelId
	* @param loginId
	* @return
	* @date 2013-4-16 下午03:51:43     
	* @throws 
	*/ 
	public String messageWarn(String content,String type,String ipTelId,String loginId){
		try {
			if(StringUtils.isBlank(ipTelId) || StringUtils.isBlank(loginId)){
				log.info("电话id不能为空!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	} 
	
	/** 
	* @title: cleanCarSure 
	* @description: 洗车的确认
	* @author cjw
	* @param userId
	* @param time
	* @param loginId
	* @return
	* @date 2013-4-16 下午03:58:58     
	* @throws 
	*/ 
	public String cleanCarSure(String userId, Date time,String loginId){
		try {
			if(StringUtils.isBlank(userId) || StringUtils.isBlank(loginId)){
				log.info("洗车人的id不能为空!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
	
	/** 
	* @title: orderPutOff 
	* @description: 预约下推
	* @author cjw
	* @param mac
	* @param loginId
	* @return
	* @date 2013-4-16 下午04:01:55     
	* @throws 
	*/ 
	public String orderPutOff(String mac,String loginId){
		try {
			if(StringUtils.isBlank(mac) || StringUtils.isBlank(loginId)){
				log.info("洗车人的id不能为空!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}

}
