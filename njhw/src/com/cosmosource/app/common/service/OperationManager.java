package com.cosmosource.app.common.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cosmosource.app.entity.EiEnergy;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.model.SiteStatus;
import com.cosmosource.app.port.model.WisdomNj;
import com.cosmosource.app.port.serviceimpl.EnergySysToAppService;
import com.cosmosource.app.port.serviceimpl.GatesControlToAppService;
import com.cosmosource.app.port.serviceimpl.ParkingAreaToAppService;
import com.cosmosource.app.port.serviceimpl.WisdomNjToAppService;
import com.cosmosource.base.service.BaseManager;

public class OperationManager extends  BaseManager{
	private WisdomNjToAppService wisdomNjToAppService; //南京周围的天气 交通 情况
	
	private GatesControlToAppService gatesControlToAppService;//闸机service
	
	private EnergySysToAppService energySysToAppService; //能源service
	
	private ParkingAreaToAppService  parkingAreaToAppService; //停车场

	
	/**
	 * 
	* @title: loadWeatherMessage 
	* @description: 加载停车场信息
	* @author cjw
	* @return
	* @date 2013-4-5 下午03:05:06     
	* @throws
	 */
	public String loadParkingMessage(){
		
		return null;
	}
	/**
	 * 
	* @title: loadWeatherMessage 
	* @description: 加载环境的信息
	* @author cjw
	* @return
	* @date 2013-4-5 下午03:05:06     
	* @throws
	 */
	public Collection<WisdomNj> loadEnviromentMessage(){
		Collection<WisdomNj> collection =wisdomNjToAppService.queryAroundInformation("");//参数是地址
		return collection;
	}
	/**
	 * 
	* @title: loadWeatherMessage 
	* @description: 加载天气的信息
	* @author cjw
	* @return
	* @date 2013-4-5 下午03:05:06     
	* @throws
	 */
	public String loadWeatherMessage(){
		Collection<WisdomNj> collection =wisdomNjToAppService.queryWeatherInformation("");//参数是地址
		return null;
	}
	
	/**
	 * 
	* @title: loadWeatherMessage 
	* @description: 加载能耗的信息
	* @author cjw
	* @return
	* @date 2013-4-5 下午03:05:06     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<EiEnergy> loadEnergyMessage(){
		List<EiEnergy> list= new ArrayList<EiEnergy>();
		try {
			list = dao.getAll(EiEnergy.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	/**
	 * 
	* @title: loadWeatherMessage 
	* @description: 加载摄像头信息
	* @author cjw
	* @return
	* @date 2013-4-5 下午03:05:06     
	* @throws
	 */
	public String loadVideoMessage(){
		return null;
	}
	
	/**
	 * 
	* @title: loadWeatherMessage 
	* @description: 加载闸机信息
	* @author cjw
	* @return
	* @date 2013-4-5 下午03:05:06     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Objtank> loadGatesMessage(){
		//得到所有的闸机
		String hql=" select o.nodeId,o.name from Objtank o where o.extResType=?";
		return dao.findByHQL(hql, Objtank.EXT_RES_TYPE_1);
		
	}
	/** 
	* @title: loadGatesMessageByGateId 
	* @description: 通过闸机id得到闸机的信息
	* @author cjw
	* @param gatesId
	* @return
	* @date 2013-4-22 下午06:44:27     
	* @throws 
	*/ 
	public SiteStatus loadGatesMessageByGateId(String gatesId){
		//SiteStatus siteStatue = gatesControlToAppService.queryGateStatus(gateId);
		return null;
	}
	public WisdomNjToAppService getWisdomNjToAppService() {
		return wisdomNjToAppService;
	}

	public void setWisdomNjToAppService(WisdomNjToAppService wisdomNjToAppService) {
		this.wisdomNjToAppService = wisdomNjToAppService;
	}

	public GatesControlToAppService getGatesControlToAppService() {
		return gatesControlToAppService;
	}

	public void setGatesControlToAppService(
			GatesControlToAppService gatesControlToAppService) {
		this.gatesControlToAppService = gatesControlToAppService;
	}
	public EnergySysToAppService getEnergySysToAppService() {
		return energySysToAppService;
	}
	public void setEnergySysToAppService(EnergySysToAppService energySysToAppService) {
		this.energySysToAppService = energySysToAppService;
	}
	public ParkingAreaToAppService getParkingAreaToAppService() {
		return parkingAreaToAppService;
	}
	public void setParkingAreaToAppService(
			ParkingAreaToAppService parkingAreaToAppService) {
		this.parkingAreaToAppService = parkingAreaToAppService;
	}
	

	
}
