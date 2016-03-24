package com.cosmosource.app.buildingmon.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.buildingmon.service.ZJMonManager;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.model.SiteStatus;
import com.cosmosource.app.port.serviceimpl.GatesControlToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class ZJMonAction  extends BaseAction {
	
	private static final long serialVersionUID = -618634380016811013L;
	
	private Objtank entity = new Objtank();
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);//默认每页20条记录
	private ZJMonManager zjMonManager;
	private GatesControlToAppService gatesControlToAppService;
	
	
	public String zjMain() {
		return SUCCESS;
	}
	
	/**
	 * 查询资源列表
	 * @return 
	 * @throws Exception
	 */
	public String zjList() throws Exception {
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null) entity.setPId(Long.parseLong(nodeId));
		page = zjMonManager.queryObjtank(page, entity);
		String zjids = "";
		for (HashMap map :  page.getResult()) zjids += map.get("NODE_ID").toString() + ",";
		getRequest().setAttribute("zjids", zjids);
		return SUCCESS;
	}
	
	/**
	 * 定时调用闸机接口刷新状态
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String refreshZJStatus() throws Exception {
		JSONObject json = new JSONObject();
		List<SiteStatus> list = new ArrayList<SiteStatus>();
		//list = gatesControlToAppService.queryGateStatus(getParameter("zjids"));
		ArrayList statusList = new ArrayList();
		statusList.add("闸机状态1");
		statusList.add("闸机状态11");
		statusList.add("闸机状态2");
		statusList.add("闸机状态22");
		statusList.add("闸机状态3");
		statusList.add("闸机状态33");
		statusList.add("闸机状态8");
		statusList.add("闸机状态88");
		
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

	public ZJMonManager getZjMonManager() {
		return zjMonManager;
	}

	public void setZjMonManager(ZJMonManager zjMonManager) {
		this.zjMonManager = zjMonManager;
	}

	public GatesControlToAppService getGatesControlToAppService() {
		return gatesControlToAppService;
	}

	public void setGatesControlToAppService(
			GatesControlToAppService gatesControlToAppService) {
		this.gatesControlToAppService = gatesControlToAppService;
	}
}
