package com.cosmosource.app.port.serviceimpl;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName:ParkingAreaToAppService
 * @Description： 停车场操作
 * @Author：hp 
 * @Date:2013-3-25
 */
public class ParkingAreaToAppService {
	
	private static final Log log = LogFactory.getLog(ParkingAreaToAppService.class);

	
	/**
	* @Description：得到车位信息
	* @Author：hp
	* @Date：2013-3-25
	* @param plateNo  车牌号码
	* @return
	**/
	public String getParkingInfo(String plateNo){
		try {
			if(StringUtils.isBlank(plateNo)){
				log.info("车牌号参数不能为空!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
}
