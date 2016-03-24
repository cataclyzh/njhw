package com.cosmosource.app.personnel.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.personnel.service.DepRoomAssignmentsManager;
import com.cosmosource.base.action.BaseAction;

/**
 * 
* @description: 获得机构
* @author SQS
* @date 2013-3-17 下午06:25:49
 */
@SuppressWarnings({ "unchecked", "serial" })
public class DepRoomAssignmentsAction extends BaseAction {
	
	private Org org = new Org();
	private DepRoomAssignmentsManager depRoomAssignmentsManager;
	
    @Override
	protected void prepareModel() throws Exception {
	}
	public Org getModel() {
		return org;
	}
	
	/**
	 * 
	* @title: 
	* @description: 初始化物业房间分配页面
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	public String initRoomAssignments(){
		List<Org> list = depRoomAssignmentsManager.findOrgName();
		getRequest().setAttribute("orgList", list);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: propertyRoomAssign
	 * @description: 初始化物业房间分配页面--右侧
	 * @author SQS
	 * @return
	 * @throws Exception
	 * @date 2013-3-17 下午06:27:26     
	 * @throws
	 */
	public String propertyRoomAssign(){
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("orgId", getParameter("orgId"));
			List<EmOrgRes> emList = depRoomAssignmentsManager.findAssignedOrgRoom(map);
			getRequest().setAttribute("emList", emList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public DepRoomAssignmentsManager getDepRoomAssignmentsManager() {
		return depRoomAssignmentsManager;
	}
	public void setDepRoomAssignmentsManager(
			DepRoomAssignmentsManager depRoomAssignmentsManager) {
		this.depRoomAssignmentsManager = depRoomAssignmentsManager;
	}
	
}
