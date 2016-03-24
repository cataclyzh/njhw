package com.cosmosource.app.buildingmon.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.buildingmon.service.MJMonManager;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.model.SiteStatus;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class MJMonAction extends BaseAction {
	
	private static final long serialVersionUID = -618634380016811013L;
	
	private Objtank entity = new Objtank();
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);//默认每页20条记录
	private MJMonManager mjMonManager;
	private DoorControlToAppService doorControlToAppService;
	
	
	public String mjMain() {
		return SUCCESS;
	}
	
	/**
	 * 查询资源列表
	 * @return 
	 * @throws Exception
	 */
	public String mjList() throws Exception {
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null) entity.setPId(Long.parseLong(nodeId));
		page = mjMonManager.queryObjtank(page, entity);
		String mjids = "";
		for (HashMap map :  page.getResult()) mjids += map.get("NODE_ID").toString() + ",";
		getRequest().setAttribute("mjids", mjids);
		return SUCCESS;
	}
	
	/**
	 * 定时调用门禁接口刷新状态
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String refreshMJStatus() throws Exception {
		JSONObject json = new JSONObject();
		List<SiteStatus> list = new ArrayList<SiteStatus>();
		//:TODO查询门禁状态接口应该返回一个List<SiteStatus>
		//list = doorControlToAppService.queryDoorStatus(getParameter("mjids"), "3C");
		ArrayList statusList = new ArrayList();
		statusList.add("门禁状态1");
		statusList.add("门禁状态11");
		statusList.add("门禁状态2");
		statusList.add("门禁状态22");
		statusList.add("门禁状态3");
		statusList.add("门禁状态33");
		
		try {
			if (list != null) {
				json.put("statusList", statusList);
				json.put("status", 0);
			} else {
				json.put("status", 1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}

	@Override
	public Objtank getModel() {
		// TODO Auto-generated method stub
		return entity;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public Objtank getEntity() {
		return entity;
	}

	public void setEntity(Objtank entity) {
		this.entity = entity;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}
	
	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}

	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}

	public MJMonManager getMjMonManager() {
		return mjMonManager;
	}

	public void setMjMonManager(MJMonManager mjMonManager) {
		this.mjMonManager = mjMonManager;
	}

}
