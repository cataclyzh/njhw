package com.cosmosource.app.buildingmon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.app.wirelessLocation.service.WirelessManager;
import com.cosmosource.base.service.BaseManager;
/**
 * 
* @description: 新楼宇监控处理manager
* @author herb
* @date Apr 2, 2013 11:53:07 AM
 */
@SuppressWarnings("unchecked")
public class BuildMonManager extends BaseManager {
	
	private WirelessManager wirelessManager;

	public void setWirelessManager(WirelessManager wirelessManager) {
		this.wirelessManager = wirelessManager;
	}

	/**
	 * @description: 加载访客位置点ID
	 * @author zh
	 * @param visitorName
	 * @return List<HashMap> 
	 */
	public List<HashMap> loadVisitorPoints(String visitorName) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("visitorName", visitorName);
		return sqlDao.findList("BuildingmonSQL.loadVisitorPoints", map);
	}
	
	/**
	 * @description: 根据访客名称取得访客及访问事务信息
	 * @author zh
	 * @param visitorName
	 * @return HashMap
	 */
	public List<Map<String ,String>> loadVisitorInfo(String visitorName, String visitorID) {
		List<Map<String ,String>> result = new ArrayList<Map<String ,String>>();
		Map<String,String> map = new HashMap<String,String>();
		if(visitorID != null && visitorID.trim().length() > 0){
			map.put("visitorID", visitorID);
		}
		if(visitorName != null && visitorName.trim().length() > 0){
			map.put("name", visitorName.trim());
		}
		
		map.put("vDate", DateUtils.getCurrentDateStr());
		
		List<Map<String ,String>> list = sqlDao.findList("ThreeDimensionalSQL.SELECT_VISITOR", map);
		if(list != null && list.size() > 0){
			Map<String,String> visitorInfo = list.get(0);
			logger.info("visitorInfo: " + visitorInfo);
			String cardNum = visitorInfo.get("WIRELESS_CARD");
			visitorInfo.put("DW_CARD", cardNum);

			String tagMac = wirelessManager.getqueryTagMacByIDCradNum(cardNum);
			if(tagMac != null && tagMac.trim().length() > 0) {
				//通过无线定位获取当前位置
				JSONObject json = wirelessManager.wirelessLocation(tagMac);
				String pointId = json.get("pointId").toString();
				String pointName = json.get("pointName").toString();
				visitorInfo.put("DEVICEID", pointId);
				visitorInfo.put("LOCATION", pointName);
			} else {
				visitorInfo.put("DEVICEID", "NONE");
			}
			result.add(visitorInfo);
		}
		return result;
	}
	
	public void getLocationNoByCardNum(){
		String tagMac = wirelessManager.getqueryTagMacByIDCradNum("0000997165721861");
		logger.info("tagMac: " + tagMac);
	}
	/**
	 * @description: 根据随访人员名称取得随行人员及事务信息
	 * @author zh
	 * @param visitorName
	 * @return HashMap
	 */
	public List<Map<String ,String>>  loadAuxiInfo(String visitorName,String visitorID) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("visitorName", visitorName);
		map.put("in_out", VmVisit.VISITOR_IN);
		map.put("visitorID", visitorID);
		List<Map<String ,String>>  list = sqlDao.findList("BuildingmonSQL.loadVisitorInfo_auxi", map);
		if (list.size() > 0 && list != null)
		{
			for (int i = 0; i < list.size(); i++) {
				String cardNum = list.get(i).get("VA_BAK2");
				//随行人员无照片
				list.get(i).put("RES_BAK2", "NONE");
				/*/通过无线定位获取当前位置
				String tagMac = wirelessManager.getqueryTagMacByIDCradNum(cardNum);
				if("".equals(tagMac))
				{
					list.get(i).put("DEVICEID", "NONE");
				} 
				else
				{
				JSONObject json = wirelessManager.wirelessLocation(tagMac);
				String floor = json.get("floor").toString();
				String deviceId = json.get("CoordinatesId").toString();
				list.get(i).put("LOCATION", floor);
				list.get(i).put("DEVICEID", deviceId);
			    }*/
			}
			return  list;
		}	
		return null;
	}
	
}
