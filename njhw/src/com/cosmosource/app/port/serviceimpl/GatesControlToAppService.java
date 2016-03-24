package com.cosmosource.app.port.serviceimpl;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.port.model.SiteStatus;
import com.cosmosource.base.service.BaseManager;

/**
 * @ClassName:GatesControlToAppService
 * @Description：闸机操作
 * @Author：hp 
 * @Date:2013-3-24
 */
public class GatesControlToAppService extends BaseManager{
	
	private static final Log log = LogFactory.getLog(GatesControlToAppService.class);

	
	/**
	* @Description：控制闸机开关
	* @Author：hp
	* @Date：2013-3-24
	* @param gateNo
	* @param order
	* @return
	**/
	public String controlGates(String gateNo,String order,String loginId){
		try {
			if(StringUtils.isBlank(gateNo) || StringUtils.isBlank(order) || StringUtils.isBlank(loginId)){
				log.info("闸机号和命令不能为空!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
	
	
	/**
	* @Description：查询闸机的状态
	* @Author：hp
	* @Date：2013-3-24
	* @param gateNo
	* @return
	**/
	public List<SiteStatus> queryGateStatus(String gateNo){
		try {
			if(StringUtils.isBlank(gateNo)){
				log.info("闸机号不能为空!");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}
	
}
