package com.cosmosource.app.personnel.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.personnel.service.SWAllotRoomManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/** 
* @description: 分配房间给部门 —— 三维
* @author zh
* @date 2013-03-23
*/ 
public class SWAllotRoomAction extends BaseAction<Object> {
	
	// 定义全局变量
	private static final long serialVersionUID = 4227875753301925460L;
	// 定义实体变量
	private EmOrgRes emOrgRes = new EmOrgRes();
	// 定义注入对象
	private SWAllotRoomManager swAllotRoomManager;

	/** 
	* @title: init
	* @description: 初始化
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String init() {
		getRequest().setAttribute("orgList", swAllotRoomManager.loadOrg());
		return SUCCESS;
	}
	
	/** 
	* @title: validAllotInfo
	* @description: 提交验证
	* @author zh
	* @date 2013-03-23
	*/ 
	public String validAllotInfo() {
		JSONObject json = new JSONObject();
		try {
			String ids = getParameter("ids");
			long orgId = Long.parseLong(getParameter("orgId"));
			// 效验选中的房间是否已分配给别的委办局
			List<HashMap> list = swAllotRoomManager.checkRoomIsAllot(ids, orgId);
			if (list.size() == 0) {
				json.put("status", 0);
			} else {
				String names = "", roomids = "";
				for (HashMap map : list) {
					names += map.get("RES_NAME").toString() + ",";
					roomids += map.get("RES_ID").toString() + ",";
				}
				json.put("status", 1);
				json.put("names", names);
				json.put("roomids", roomids);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}

	/** 
	* @title: save
	* @description: 提交批量分配房间
	* @author zh
	* @date 2013-03-23
	*/ 
	public String allotSave() {
		try {
			String cancelIds = getParameter("cancelIds");
			String allotIds = getParameter("allotIds");
			long orgId = Long.parseLong(getParameter("orgId"));
			String orgName =  getParameter("orgName");
			String skipRoomIds = getParameter("skipRoomIds");
			swAllotRoomManager.saveSWAllotRoom(orgId, orgName, cancelIds, allotIds, skipRoomIds);
			Struts2Util.renderText("true");
		} catch (Exception e) {
			Struts2Util.renderText("false");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public EmOrgRes getModel() {
		// TODO Auto-generated method stub
		return emOrgRes;
	}
	
	@Override
	protected void prepareModel() throws Exception {
	}

	public EmOrgRes getEmOrgRes() {
		return emOrgRes;
	}

	public void setEmOrgRes(EmOrgRes emOrgRes) {
		this.emOrgRes = emOrgRes;
	}
	
	public SWAllotRoomManager getSwAllotRoomManager() {
		return swAllotRoomManager;
	}

	public void setSwAllotRoomManager(SWAllotRoomManager swAllotRoomManager) {
		this.swAllotRoomManager = swAllotRoomManager;
	}
}
