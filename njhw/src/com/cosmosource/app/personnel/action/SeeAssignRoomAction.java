package com.cosmosource.app.personnel.action;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.personnel.service.SeeAssignRoomManager;
import com.cosmosource.base.action.BaseAction;

/** 
* @description: 委办局房间分配查询    -- 三维
* @author sqs
* @date 2013-03-31
*/ 
@SuppressWarnings("serial")
public class SeeAssignRoomAction extends BaseAction<Object> {
	private EmOrgRes emOrgRes = new EmOrgRes();
	private SeeAssignRoomManager seeAssignRoomManager;

	/** 
	* @title: initSeeRoom
	* @description: 初始化
	* @author sqs
	* @date 2013-03-31 
	*/ 
	public String initSeeRoom() {
		getRequest().setAttribute("orgList", seeAssignRoomManager.loadOrg());
		return SUCCESS;
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
	public SeeAssignRoomManager getSeeAssignRoomManager() {
		return seeAssignRoomManager;
	}
	public void setSeeAssignRoomManager(SeeAssignRoomManager seeAssignRoomManager) {
		this.seeAssignRoomManager = seeAssignRoomManager;
	}
}
