package com.cosmosource.app.threedimensional.service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;

import net.sf.json.JSONObject;

import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.app.wirelessLocation.service.WirelessManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;

public class VisitorManager extends BaseManager {
	
	private WirelessManager wirelessManager;
	
	public void setWirelessManager(WirelessManager wirelessManager) {
		this.wirelessManager = wirelessManager;
	}

	// 取进入闸机的访客信息
	@SuppressWarnings("unchecked")
	public Page<HashMap> getVisitorList(final Page<HashMap> page) {
		Map param = new HashMap();
		param.put("vDate", DateUtils.getCurrentDateStr());
		return sqlDao.findPage(page, "ThreeDimensionalSQL.SELECT_VISITOR", param);
	}
	
	// 取异常访客信息
	@SuppressWarnings("unchecked")
	public Page<HashMap> getVisitorErrorList(final Page<HashMap> page) {
		return sqlDao.findPage(page, "ThreeDimensionalSQL.SELECT_VISITOR_ERROR", null);
	}
	
	// 更新访客是否异常
	@SuppressWarnings("unchecked")
	public void updateErrorVisitor() {
		List<HashMap> visitorList = new ArrayList<HashMap>();
		// 查询访客及附属访客信息
		Map param = new HashMap();
		param.put("vDate", DateUtils.getCurrentDateStr());
		visitorList = sqlDao.findList(
				"ThreeDimensionalSQL.SELECT_VISITOR", param);
		// 更新访客状态表
		if(visitorList != null){
			updateVisitorStatus(visitorList);
		}
		return;
	}

	@SuppressWarnings("unchecked")
	private void updateVisitorStatus(List<HashMap> visitorList) {
//			List<HashMap> list = new ArrayList<HashMap>();
		for (int i=0; i < visitorList.size(); i++) {
			Map visitorInfo = visitorList.get(i);
			// 查询被访问人的部门ID
			String userId = String.valueOf(visitorInfo.get("USER_ID"));
			Map getOrgParam = new HashMap();
			getOrgParam.put("userId", userId);
			List<Map> getOrgList = sqlDao.findList("ThreeDimensionalSQL.getOrgIdByUserId", getOrgParam);
			if(getOrgList == null || getOrgList.size() == 0){
				continue;
			}
			String orgIdStr = String.valueOf(getOrgList.get(0).get("ORG_ID"));
			//getOrgIdByUserId
			
			// 查询访客的合法区域
			Map selectNormalAreaParam = new HashMap();
			selectNormalAreaParam.put("orgId", orgIdStr);
			List<Map> normalPointList = sqlDao.findList(
					"ThreeDimensionalSQL.SELECT_OBJTANK_BY_OGRID", selectNormalAreaParam);
			
			//
			String cardNum = String.valueOf(visitorInfo.get("WIRELESS_CARD"));
			String tagMac = wirelessManager.getqueryTagMacByIDCradNum(cardNum);
			if(tagMac == null || tagMac.trim().length() == 0){
				continue;
			}
			
			//通过无线定位获取当前位置
			JSONObject json = wirelessManager.wirelessLocation(tagMac);
			//String pointId = json.get("pointId").toString();
			String pointName = json.get("pointName").toString();
			//for test
			//pointName = "A座17F";
			
			String compareName = null;
			String pN1 = pointName.substring(pointName.length() - 3);
			if(pN1 == "15F" || pN1 == "16F" || pN1 == "17F" || pN1 == "18F"){
				compareName = pN1;
			}else{
				compareName = pointName.replace("F", "层");
			}
			int flag = 0;
			for(int j=0; j<normalPointList.size(); j++){
				String normalName = String.valueOf(normalPointList.get(j).get("KEYWORD"));
				if(normalName.equals(compareName)){
					flag = 1;
				}
			}
			logger.info("flag: " + flag);

//			if(flag == 1){
//				return;
//			}
			
			Map selectVisitorStatusParam = new HashMap();
			selectVisitorStatusParam.put("vId", visitorInfo.get("ID"));
			selectVisitorStatusParam.put("RECORDTIME", visitorInfo.get("TIME"));
			// 查询访客状态表
			List<Map> visitorStatusList = sqlDao.findList(
					"ThreeDimensionalSQL.SELECT_VISITOR_STATUS", 
					selectVisitorStatusParam);

			HashMap updateMap = new HashMap();
			List<Map> updateList = new ArrayList<Map>();
			// 插入或更新访客状态表信息
			if (visitorStatusList != null && visitorStatusList.size() > 0) {
				Map visitorStatusMap = visitorStatusList.get(0);
				int frequency = ((java.math.BigDecimal)visitorStatusMap.get("FREQUENCY")).intValue();
				if (flag == 0) {
					updateMap.put("frequency", frequency + 1);
				} else {
					if(frequency < 3){
						updateMap.put("frequency", 0);
					}
				}
				if(frequency < 3){
					updateMap.put("vi_id", visitorInfo.get("ID"));
					updateMap.put("time", visitorInfo.get("TIME"));
					updateMap.put("isnormal", flag);
					updateList.add(updateMap);
					sqlDao.batchUpdate(
						"ThreeDimensionalSQL.UPDATE_VISITOR_STATUS",
						updateList);
				}
			} else {
				updateMap.put("vid", visitorInfo.get("ID"));
				updateMap.put("vname", visitorInfo.get("NAME"));
				String time = String.valueOf(visitorInfo.get("TIME"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
//				updateMap.put("time", visitorInfo.get("TIME"));
				try {
					updateMap.put("time", sdf.parse(time));
				} catch (ParseException e) {
					updateMap.put("time", new Date());
				}
				updateMap.put("oid", orgIdStr);
				updateMap.put("oname", visitorInfo.get("ORG_NAME"));
				updateMap.put("isnormal", flag);
				if (flag == 0) {
					updateMap.put("frequency", 1);
					updateList.add(updateMap);
					sqlDao.batchInsert(
							"ThreeDimensionalSQL.INSERT_VISITOR_STATUS",
							updateList);
				}
			}
		}
	}
	
	//根据访客ID查询访客在实际开始访问和实际结束访问的时间内经过的一组设备的ID（门禁、闸机、无线定位器）
	public List<HashMap<String,String>> getVistorIDs(String visitorID)
	{
		List <HashMap<String,String>> list = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("visitorID", visitorID);
		return list = sqlDao.findList("ThreeDimensionalSQL.SELECT_VISITOR_DEVICEIDS_BY_REAL_TIME", map);
	}
	
	//根据访客随行人员ID查询访客随行人员在实际开始访问和实际结束访问的时间内经过的一组设备的ID（门禁、闸机、无线定位器）
	public List<HashMap<String,String>> getAuxiIDs(String visitorID)
	{
		List <HashMap<String,String>> list = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("visitorID", visitorID);
		return list = sqlDao.findList("ThreeDimensionalSQL.SELECT_AUXI_DEVICEIDS_BY_REAL_TIME", map);
	}
	//根据访客编号查询访客实际开始和结束访问时间及定位卡号
	public  List<Map<String,String>> getVistorCardNumAndTime(String visitorID)
	{
		List <Map<String,String>> list = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("visitorID", visitorID);
		return list = sqlDao.findList("ThreeDimensionalSQL.SELECT_VISITOR_CARDNUM_BY_VISITORID", map);
	}
	//根据访客证件号和证件类型查询访客实际开始和结束访问时间及定位卡号
	public  List<Map<String,String>> getVistorCardNumAndTimes(String cardId,String cardType)
	{
		List <Map<String,String>> list = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("cardType", cardType);
		map.put("cardId", cardId);
		return list = sqlDao.findList("ThreeDimensionalSQL.SELECT_VISITOR_CARDNUM_BY_VISITOR_CARDID", map);
	}
	 /**
	* @Description 实时的位置
	* @Author：zhujiabiao
	* @Date 2013-8-13 下午02:30:40 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public JSONObject wirelessLocation(String tagMac) {
		String url;
		String wirelessLocation;
		String soapActionURI;

		url = Constants.DBMAP.get("WIRELESS_URL");
		wirelessLocation = Constants.DBMAP.get("WIRELESS_LOCATION");
		soapActionURI = Constants.DBMAP.get("WIRELESS_SOAPACTIONURI");
		String str = "";
		String serviceUrl = url;
		JSONObject jo = null;
		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(serviceUrl));
			call.setOperation(wirelessLocation);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(soapActionURI + wirelessLocation);
			call.setOperationName(new QName(soapActionURI, wirelessLocation));
			// 设置要传递的参数
			call.addParameter(new QName(soapActionURI, "tagMac"),
					XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_SCHEMA);
			call.setUseSOAPAction(true);
			Object res = (Object) call.invoke(new Object[] { tagMac });
			Schema schema = (Schema) res;
			MessageElement[] msgele = schema.get_any();
			String coordinatesId = "";
			for (int i = 0; i < msgele.length; i++) {
				if (msgele[i].toString().indexOf("CoordinatesId") > -1) {
					coordinatesId = msgele[i].getValue();
					break;
				}
			}
			jo = new JSONObject();
			String floor = "";
			String nodeId = "";
			if (coordinatesId != null && !coordinatesId.equals("")) {
				Map map = new HashMap();
				map.put("coordinates", coordinatesId);
				List<Map> list = null;
				Map<String, Object> resMap = new HashMap<String, Object>();
				if (coordinatesId != null && !"".equals(coordinatesId)) {
					list = sqlDao.findList(
							"WirelessSql.selectFloorByCoordinates", map);
					if (list != null && list.size() > 0) {
						resMap = (HashMap<String, Object>) list.get(0);
						floor = (String) resMap.get("NAME");
						nodeId = ((BigDecimal) resMap.get("NODEID")).toString();
					}
				}
			}
			jo.put("CoordinatesId", coordinatesId);
			jo.put("floor", floor);
			jo.put("nodeId", nodeId);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	// 根据访客信息查询定位卡号
	@SuppressWarnings("unchecked")
	public List<HashMap> getHistoryByVisitor(String cardId, String startTime,
			String endTime) {
		List<HashMap> visitorList = new ArrayList<HashMap>();
		try {
			Map map = new HashMap();
			map.put("cardId", cardId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			visitorList = sqlDao.findList(
					"ThreeDimensionalSQL.SELECT_HISTORY_BY_VISITOR", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visitorList;
	}
	
	public List<Map<String,String>> getVisitByVisitorIdAndTimeIn(long vi_id, String time){
		Map param = new HashMap();
		param.put("vi_id", vi_id);
		param.put("recordTime", time);
		return sqlDao.findList(
				"ThreeDimensionalSQL.getVisitByVisitorIdAndTimeIn", param);
	}
}
