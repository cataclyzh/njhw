package com.cosmosource.app.buildingmon.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.buildingmon.service.BuildingMonManager;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.xwork2.Action;

@SuppressWarnings({ "serial", "unchecked" })
public class BuildingAction extends BaseAction{
	
	private Objtank entity = new Objtank();
	private Page<HashMap<String, Object>> page = new Page<HashMap<String,Object>>(Constants.PAGESIZE);//默认每页20条记录
	private List<Objtank> list = new ArrayList<Objtank>();
	private BuildingMonManager buildingMonManager;

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public Objtank getModel() {
		return entity;
	}
	
	public String orgTree() throws Exception {
		getRequest().setAttribute("floor", getRequest().getParameter("floor"));
		getRequest().setAttribute("actionName", getRequest().getParameter("actionName"));
		return SUCCESS;
	}
	
	/**
	 * 查询资源列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setPId(Long.parseLong(nodeId));
		}
		page = buildingMonManager.queryObjtank(page, entity);
		return SUCCESS;
	}
	
	/**
	 * 取得资源树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String orgTreeData() throws Exception {
		String floor = getParameter("floor");
		String actionName = getParameter("actionName");
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			Struts2Util.renderXml(
					buildingMonManager.getOrgTreeData(id, getContextPath(),type,floor,actionName),
					"encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 
	* @title: buildMain 
	* @description: 楼座主页
	* @author cjw
	* @return
	* @date 2013-3-27 下午01:53:38     
	* @throws
	 */
	public String buildMain() {
		return Action.SUCCESS;
	}

	public Objtank getEntity() {
		return entity;
	}

	public void setEntity(Objtank entity) {
		this.entity = entity;
	}

	public Page<HashMap<String, Object>> getPage() {
		return page;
	}

	public void setPage(Page<HashMap<String, Object>> page) {
		this.page = page;
	}

	public BuildingMonManager getBuildingMonManager() {
		return buildingMonManager;
	}

	public void setBuildingMonManager(BuildingMonManager buildingMonManager) {
		this.buildingMonManager = buildingMonManager;
	}

	public List<Objtank> getList() {
		return list;
	}

	public void setList(List<Objtank> list) {
		this.list = list;
	}
	
	
}
