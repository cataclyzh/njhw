package com.cosmosource.app.buildingmon.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.cosmosource.app.buildingmon.service.BuildMonManager;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.port.model.SiteStatus;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("unchecked")
public class BuildMonAction extends BaseAction {

	private static final long serialVersionUID = 9109148287490943928L;
	private BuildMonManager buildMonManager;
	private DevicePermissionToAppService devicePermissionToApp;
	
	public String indexInit() {
		getRequest().setAttribute("orgList", this.buildMonManager.findByHQL("select t from Org t where t.levelNum = ? and t.PId = ? order by t.orderNum", Org.LEVELNUM_2,Org.ORG_PID_2));
		return SUCCESS;
	}
	
	/** 
	* @title: visitorPosition
	* @description: 访客定位
	* @author zh
	* @date 2013-05-4
	*/ 
	public String visitorPosition() throws Exception {
		try {
			net.sf.json.JSONObject json = new net.sf.json.JSONObject();
			//获取需要定位的人员的姓名
			String visitorName = getParameter("visitorName");
			String visitorID = getParameter("visitorID");
			if("NONE".equals(visitorID)){
				visitorID = "";
			}
			if("NONE".equals(visitorName)){
				visitorName = "";
			}
			//先从访客表中查询
			List<Map<String ,String>> visitorInfoList = (List<Map<String, String>>) buildMonManager.loadVisitorInfo(visitorName,visitorID);
			//再从随行人员表中查询
			JSONArray jsonArrayVisitor =	JSONArray.fromObject(visitorInfoList);
			json.put("jsonArrayVisitor", jsonArrayVisitor);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 定时调用设备报警接口刷新数据
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String refreshWarningDevice() throws Exception {
		//:TODO调用设备报警接口刷新数据
		//List<SiteStatus> deviceList = new ArrayList<SiteStatus>();
		//list = devicePermissionToApp.queryWarningStatus();
		
		// 测试数据
		ArrayList deviceList = new ArrayList();
		
		SiteStatus siteStatus = new SiteStatus();
		siteStatus.setNodeId("19");
		siteStatus.setExtResName("A座闸机001");
		siteStatus.setExtResType("闸机");
		siteStatus.setStatusCode("00");
		siteStatus.setStatusName("异常");
		deviceList.add(siteStatus);
		
		siteStatus = new SiteStatus();
		siteStatus.setNodeId("126");
		siteStatus.setExtResName("A座电梯061");
		siteStatus.setExtResType("电梯");
		siteStatus.setStatusCode("01");
		siteStatus.setStatusName("异常");
		deviceList.add(siteStatus);
		
		siteStatus = new SiteStatus();
		siteStatus.setNodeId("112");
		siteStatus.setExtResName("B座闸机005");
		siteStatus.setExtResType("闸机");
		siteStatus.setStatusCode("00");
		siteStatus.setStatusName("异常");
		deviceList.add(siteStatus);
		
		siteStatus = new SiteStatus();
		siteStatus.setNodeId("133");
		siteStatus.setExtResName("A座门禁003");
		siteStatus.setExtResType("门禁");
		siteStatus.setStatusCode("03");
		siteStatus.setStatusName("异常");
		deviceList.add(siteStatus);
		
		siteStatus = new SiteStatus();
		siteStatus.setNodeId("139");
		siteStatus.setExtResName("A座门禁009");
		siteStatus.setExtResType("门禁");
		siteStatus.setStatusCode("03");
		siteStatus.setStatusName("异常");
		deviceList.add(siteStatus);
		
		siteStatus = new SiteStatus();
		siteStatus.setNodeId("136");
		siteStatus.setExtResName("A座门禁008");
		siteStatus.setExtResType("门禁");
		siteStatus.setStatusCode("03");
		siteStatus.setStatusName("异常");
		deviceList.add(siteStatus);
		
		Struts2Util.renderJson(deviceList,"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public Object getModel() {
		return null;
	}

	public BuildMonManager getBuildMonManager() {
		return buildMonManager;
	}

	public void setBuildMonManager(BuildMonManager buildMonManager) {
		this.buildMonManager = buildMonManager;
	}

	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}
}
