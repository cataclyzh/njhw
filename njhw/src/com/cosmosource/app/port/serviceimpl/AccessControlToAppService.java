package com.cosmosource.app.port.serviceimpl;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.base.service.BaseManager;

/**
 * @ClassName:AccessControlToAppService
 * @Description：门禁的操作 
 * @Author：hp 
 * @Date:2013-3-25
 */
public class AccessControlToAppService extends BaseManager{
	
	private static final Log log = LogFactory.getLog(AccessControlToAppService.class);

	/**
	* @Description：控制门禁
	* @Author：hp
	* @Date：2013-3-25
	* @param accessId  门禁id
	* @param order
	* @return
	**/
	public String controlAccess(String accessId,String order,String loginId){
		try {
			if(StringUtils.isBlank(accessId) || StringUtils.isBlank(order) || StringUtils.isBlank(loginId)){
				log.info("门禁id和命令不能为空!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
}
