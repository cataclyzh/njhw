/**
 * <p>文件名: HomeAction.java</p>
 * <p>描述：首页Action</p>
 * <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
 * <p>公司: Holytax Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间： 2011-10-27 上午10:50:24 
 * @作者：sjy
 * @版本： V1.0
 * <p>类修改者	jtm	 修改日期	2012-02-07		修改说明 
 * 更新首页呈现方式
 * </p>   
 * 
 */
package com.cosmosource.app.buildingmon.action;

import java.util.HashMap;
import java.util.Map;

import com.cosmosource.app.buildingmon.service.RoomAssignmentsManager;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;

/** 
* @description: 楼宇监控
* @author SQS
* @date 2013-3-17     
*/ 
@SuppressWarnings({ "unchecked", "serial" })
public class RoomAssignmentsAction extends BaseAction {

	private Page<Objtank> page = new Page<Objtank>(Constants.PAGESIZE);//默认每页20条记录  
	private RoomAssignmentsManager roomAssignmentsManager;
	private Objtank objtank;
	
	@Override
	protected void prepareModel() throws Exception {
		
	}
	
	@Override
//	public Object getModel() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	public Objtank getModel() {
		return objtank;
	}
	
	/** 
	* @description: 初始化楼宇页面
	* @author SQS
	* @date 2013-3-17     
	*/ 
	public String init(){
		return SUCCESS;
	}
	
	/**
	 * @描述:获取楼宇清单列表
	 * @作者：SQS
	 * @日期：2013-3-17
	 * @return
	 * @throws Exception
	 */
	public String roomAssignments() throws Exception {
		String name = getParameter("name");
		String title = getParameter("title");
		String keyword = getParameter("keyword");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("title", title);
		map.put("keyword", keyword);
		page = roomAssignmentsManager.getRoom(page, map);
		getRequest().setAttribute("map", map);
		return SUCCESS;
	}

	public RoomAssignmentsManager getRoomAssignmentsManager() {
		return roomAssignmentsManager;
	}

	public void setRoomAssignmentsManager(
			RoomAssignmentsManager roomAssignmentsManager) {
		this.roomAssignmentsManager = roomAssignmentsManager;
	}

	public Page<Objtank> getPage() {
		return page;
	}
	public void setPage(Page<Objtank> page) {
		this.page = page;
	}
	public Objtank getObjtank() {
		return objtank;
	}
	public void setObjtank(Objtank objtank) {
		this.objtank = objtank;
	}
}
