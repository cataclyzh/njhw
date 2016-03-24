package com.cosmosource.app.port.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.integrateservice.model.LightObixModel;
import com.cosmosource.app.utils.ConditionerControlUtils;
import com.cosmosource.app.utils.LightControlUtils;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.PropertiesUtil;

/**
 * @ClassName:BuildAutoToAppService
 * @Description：楼宇自控系统操作
 * @Author：hp 
 * @Date:2013-3-25
 */
public class BuildAutoToAppService extends BaseManager{
	
	private static final Log log = LogFactory.getLog(BuildAutoToAppService.class);
	
	/**
	* @Description：控制楼宇的照明
	* @Author：hp
	* @Date：2013-3-25
	* @param lightNo  照明id
	* @param order    命令
	* @return
	**/
	public String controlBuildLight(String lightNo,String operType,String loginId){
		try {
			if(StringUtils.isBlank(lightNo) || StringUtils.isBlank(operType) || StringUtils.isBlank(loginId)){
				log.info("照明id和命令不能为空"); 
				return null;
			}
			boolean flag = false;
			if("on".equals(operType)){
				flag = true;
			}
			String uri = PropertiesUtil.getAnyConfigProperty("light.obix.uri", PropertiesUtil.NJHW_CONFIG);
			String username = PropertiesUtil.getAnyConfigProperty("light.obix.username", PropertiesUtil.NJHW_CONFIG);
			String password = PropertiesUtil.getAnyConfigProperty("light.obix.password", PropertiesUtil.NJHW_CONFIG);
			String operUri = PropertiesUtil.getAnyConfigProperty("light.obix.operUri", PropertiesUtil.NJHW_CONFIG);
			LightObixModel lm = new LightObixModel();
			lm.setUri(uri);
			lm.setUsername(username);
			lm.setPassword(password);
			lm.setOperUri(operUri+"BooleanWritable"+lightNo+"/set/");
			LightControlUtils.controlLight(lm, flag);
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		
		return null;
	}
	
	/**
	* @Description：取得楼宇灯的状态
	* @Author：hp
	* @Date：2013-3-25
	* @param lightNo  照明id
	* @param order    命令
	* @return
	**/
	public String getBuildLightStatus(String lightNo){
		String status = "off";
		try {
			if(StringUtils.isBlank(lightNo)){
				log.info("照明id不能为空"); 
				return "off";
			}
			String uri = PropertiesUtil.getAnyConfigProperty("light.obix.uri", PropertiesUtil.NJHW_CONFIG);
			String username = PropertiesUtil.getAnyConfigProperty("light.obix.username", PropertiesUtil.NJHW_CONFIG);
			String password = PropertiesUtil.getAnyConfigProperty("light.obix.password", PropertiesUtil.NJHW_CONFIG);
			String operUri = PropertiesUtil.getAnyConfigProperty("light.obix.operUri", PropertiesUtil.NJHW_CONFIG);
			LightObixModel lm = new LightObixModel();
			lm.setUri(uri);
			lm.setUsername(username);
			lm.setPassword(password);
			lm.setOperUri(operUri+"BooleanWritable"+lightNo+"/out/");
			boolean flag = LightControlUtils.getLightStatus(lm);
			if(flag){
				status = "on";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return status;
	}
	
	
	/**
	* @Description：控制楼宇空调
	* @Author：hp
	* @Date：2013-3-25
	* @param coolNos  空调id
	* @param order           命令
	* @return
	**/
	public String controlBuildAirCondition(String[] coolNos,String order,String loginId,Double temperature){
		try {
			if(coolNos == null || coolNos.length == 0 || StringUtils.isBlank(order) || StringUtils.isBlank(loginId)){
				log.info("空调id和命令不能为空"); 
				return null;
			}
			String uri = PropertiesUtil.getAnyConfigProperty("bulidD.conditioner.obix.uri", PropertiesUtil.NJHW_CONFIG);
			String username = PropertiesUtil.getAnyConfigProperty("conditioner.obix.username", PropertiesUtil.NJHW_CONFIG);
			String password = PropertiesUtil.getAnyConfigProperty("conditioner.obix.password", PropertiesUtil.NJHW_CONFIG);
			String operUri = PropertiesUtil.getAnyConfigProperty("conditioner.obix.operUri", PropertiesUtil.NJHW_CONFIG);
			String tempUrl = "";
			for(int i=0;i<coolNos.length;i++){
				tempUrl = uri+operUri+coolNos[i]+"/points/";
				LightObixModel lm = new LightObixModel();
				lm.setUri(uri);
				lm.setUsername(username);
				lm.setPassword(password);
				lm.setOperUri(tempUrl+"Setpoint/set/");
				ConditionerControlUtils.controlConditionerPoint(lm,temperature);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		
		return null;
	}
	
	
	/**
	 * 
	* @title: loadWeatherMessage 
	* @description: 根据roomId,objType 取得objtank对象
	* @author huang.yf
	* @return
	* @date 2013-6-18  下午03:05:06     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Objtank> loadObjTankByRoomId(String roomId,String resType){
		//根据类型和roomID获得 设备
		String hql=" select o from Objtank o where o.PId = ? and o.extResType=?";
		return dao.findByHQL(hql, new BigDecimal(roomId).longValue(),resType);
	}

	/**
	* @Description：取得楼宇灯的状态
	* @Author：hp
	* @Date：2013-3-25
	* @param lightNo  照明id
	* @param order    命令
	* @return
	**/
	public Map<String,String> getBuildConditionerTemperature(String[] coolNos,String type,String floorType){
		Map<String,String> m = new HashMap<String,String>();
		m.put("openStatus", "close");
		String status = "0";
		try {
			if(coolNos == null || coolNos.length == 0){
				log.info("空调id不能为空");
				m.put("value", "0");
				return m;
			}
			
			String uri = "";
			if(floorType.equals("A")){
				uri = PropertiesUtil.getAnyConfigProperty("bulidA.conditioner.obix.uri", PropertiesUtil.NJHW_CONFIG);
			}else if(floorType.endsWith("B")){
				uri = PropertiesUtil.getAnyConfigProperty("bulidB.conditioner.obix.uri", PropertiesUtil.NJHW_CONFIG);
			}else if(floorType.endsWith("C")){
				uri = PropertiesUtil.getAnyConfigProperty("bulidC.conditioner.obix.uri", PropertiesUtil.NJHW_CONFIG);
			}else if(floorType.endsWith("D")){
				uri = PropertiesUtil.getAnyConfigProperty("bulidD.conditioner.obix.uri", PropertiesUtil.NJHW_CONFIG);
			}
			String username = PropertiesUtil.getAnyConfigProperty("conditioner.obix.username", PropertiesUtil.NJHW_CONFIG);
			String password = PropertiesUtil.getAnyConfigProperty("conditioner.obix.password", PropertiesUtil.NJHW_CONFIG);
			String operUri = PropertiesUtil.getAnyConfigProperty("conditioner.obix.operUri", PropertiesUtil.NJHW_CONFIG);
			//only one cool
			if(coolNos.length == 1){
				operUri = uri+operUri+coolNos[0]+"/points/";
				LightObixModel lm = new LightObixModel();
				lm.setUri(uri);
				lm.setUsername(username);
				lm.setPassword(password);
				lm.setOperUri(operUri+"State/out/");
				float s = ConditionerControlUtils.getConditionerStatus(lm);
				if(s > 0){
					m.put("openStatus", "open");
				}else{
					m.put("openStatus", "close");
				}
				//m.put("openStatus", "open");
				if("room".equals(type)){
					lm.setOperUri(operUri+"Display$20Temperature/out/value/");
				}else{
					lm.setOperUri(operUri+"Setpoint/out/value/");
				}
				status = ConditionerControlUtils.getConditionerTemperature(lm);
				status = NumberUtil.format(status, "0.0");
				m.put("coolId", coolNos[0]);
			}else{
				Double value = 0.0;
				for(int i=0;i<coolNos.length;i++){
					String operUris = uri+operUri+coolNos[i]+"/points/";
					LightObixModel lm = new LightObixModel();
					lm.setUri(uri);
					lm.setUsername(username);
					lm.setPassword(password);
					lm.setOperUri(operUris+"State/out/");
					float s = ConditionerControlUtils.getConditionerStatus(lm);
					if(s > 0){
						if(m.get("coolId") == null){
							m.put("coolId", coolNos[i]);
						}else{
							m.put("coolId", m.get("coolId")+","+coolNos[i]);
						}
						m.put("openStatus", "open");
					}else{
						m.put("coolId", coolNos[0]);
						m.put("openStatus", "close");
					}
					if("room".equals(type)){
						lm.setOperUri(operUris+"Display$20Temperature/out/value/");
					}else{
						lm.setOperUri(operUris+"Setpoint/out/value/");
					}
					status = ConditionerControlUtils.getConditionerTemperature(lm);
					value = value + Double.parseDouble(status);
				}
				if(m.get("coolId") == null){
					status = "0";
				}else{
					String[] maps = m.get("coolId").split(",");
					status = NumberUtil.format(value/maps.length+"", "0.0");
				}
			}
			m.put("value", status);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return m;
	}
}
