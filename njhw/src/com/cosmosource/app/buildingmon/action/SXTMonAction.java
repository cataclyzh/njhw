package com.cosmosource.app.buildingmon.action;

import com.cosmosource.app.buildingmon.service.BuildingMonManager;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.xwork2.Action;

/**
* @description: 摄像头监控
* @author herb
* @date Mar 26, 2013 6:35:35 PM
 */
@SuppressWarnings("unchecked")
public class SXTMonAction extends BaseAction {
	private BuildingMonManager buildingMonManager;
	private Page<Objtank> page = new Page<Objtank>(Constants.PAGESIZE);//默认每页20条记录
	/**
	 * 
	 */
	private static final long serialVersionUID = -618634380016811013L;

	/**
	 * 
	* @title: sxtMonMain 
	* @description: 摄像头主页
	* @author herb
	* @return
	* @date Mar 26, 2013 7:00:18 PM     
	* @throws
	 */
	public String sxtMonMain() {
		return Action.SUCCESS;
	}
	/**
	 * 
	* @title: zjList 
	* @description: 查询摄像头列表
	* @author herb
	* @return
	* @throws Exception
	* @date Mar 29, 2013 2:22:32 PM     
	* @throws
	 */
	public String sxtMonList() throws Exception {
		String nodeId = Struts2Util.getParameter("nodeId");
		long pid = Long.parseLong(nodeId);
		//page = buildingMonManager.getLevelSXTPageList(page, nodeId);
		String userId = this.getRequest().getSession().getAttribute(Constants.USER_ID).toString();
		page = buildingMonManager.getPermissionObjTankPageList(page, Objtank.EXT_RES_TYPE_4, pid, userId);
		return SUCCESS;
	}
	@Override
	protected void prepareModel() throws Exception {
		
	}
	@Override
	public Object getModel() {
		return null;
	}
	public BuildingMonManager getBuildingMonManager() {
		return buildingMonManager;
	}
	public void setBuildingMonManager(BuildingMonManager buildingMonManager) {
		this.buildingMonManager = buildingMonManager;
	}
	public Page<Objtank> getPage() {
		return page;
	}
	public void setPage(Page<Objtank> page) {
		this.page = page;
	}
	
	
}
