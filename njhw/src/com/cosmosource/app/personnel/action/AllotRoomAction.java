package com.cosmosource.app.personnel.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.personnel.service.AllotRoomManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/** 
* @description: 分配房间给部门
* @author zh
* @date 2013-03-23
*/ 
public class AllotRoomAction extends BaseAction<Object> {
	
	// 定义全局变量
	private static final long serialVersionUID = 4227875753301925460L;
	// 定义实体变量
	private EmOrgRes emOrgRes = new EmOrgRes();
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	// 定义分页变量
	private Page<HashMap> orgPage = new Page<HashMap>(50);// 默认每页50条记录
	// 定义注入对象
	private AllotRoomManager allotRoomManager;
	
	/** 
	* @title: init
	* @description: 初始化
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String init() {
		orgPage = allotRoomManager.queryOrgList(orgPage, null);
		getRequest().setAttribute("page", orgPage);
		return SUCCESS;
	}

	/** 
	* @title: showTree
	* @description: 显示楼宇结构树
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String showTree() {
		String ids = getParameter("ids") != null ? getParameter("ids").toString() : "";
		getRequest().setAttribute("ids", ids);
		String type = getParameter("type") != null ? getParameter("type").toString() : "";
		getRequest().setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 取得资源树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String objTreeSelectData() throws Exception {
		String ids = getParameter("ids");
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			Struts2Util.renderXml( allotRoomManager.getObjTreeSelectData(id,ids, type), "encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 
	* @title: queryAllotRooms
	* @description: 查询房间分配情况
	* @author zh
	* @date 2013-04-07
	*/ 
	@SuppressWarnings("unchecked")
	public String queryAllotRooms() throws Exception {
		page.setPageSize(12);
		String ids = getParameter("ids");
		ArrayList idsList = new ArrayList();
		if (!"".equals(ids) && ids != null) {
			for (String id : ids.split(",")) {
				if (!"".equals(id)) idsList.add(Long.parseLong(id));
			}
		}
		
		HashMap map = new HashMap();
		map.put("ids", idsList);
		map.put("names", getParameter("names"));
		map.put("len", idsList.size());
		map.put("orgId", getParameter("orgId"));
		map.put("isAllot", "1");
		
		page = allotRoomManager.queryAllotRooms(page, map);
		Long count = (Long)allotRoomManager.findUnique("select count(*) from EmOrgRes t where t.eorType = '1' and t.orgId = ?",
				Long.parseLong(getParameter("orgId")));
		map.put("count", count);
		map.put("ids", ids);
		getRequest().setAttribute("map", map);
		getRequest().setAttribute("orgName", allotRoomManager.getOrgName(getParameter("orgId").toString()));
		//getRequest().setAttribute("balconyList", allotRoomManager.loadBalcony());
		return SUCCESS;
	}
	
	/** 
	* @title: queryAllotRooms
	* @description: 查询房间分配情况
	* @author zh
	* @date 2013-04-07
	*/ 
	@SuppressWarnings("unchecked")
	public String queryAllRooms() throws Exception {
		page.setPageSize(12);
		String ids = getParameter("ids");
		ArrayList idsList = new ArrayList();
		if (!"".equals(ids) && ids != null) {
			for (String id : ids.split(",")) {
				if (!"".equals(id)) idsList.add(Long.parseLong(id));
			}
		}
		
		HashMap map = new HashMap();
		map.put("ids", idsList);
		map.put("names", getParameter("names"));
		map.put("len", idsList.size());
		map.put("isAllot", getParameter("isAllot"));
		
		page = allotRoomManager.queryAllotRooms(page, map);
		Long count = (Long)allotRoomManager.countAllotRooms(null);
		map.put("count", count);
		Long countAllot = (Long)allotRoomManager.countAllotRooms("1");
		map.put("countAllot", countAllot);
		Long countNotAllot = (Long)allotRoomManager.countAllotRooms("2");
		map.put("countNotAllot", countNotAllot);
		map.put("ids", ids);
		getRequest().setAttribute("map", map);
		Map<String, String> roomAllot = new LinkedHashMap<String, String>();
		roomAllot.put("1", "已分配");
		roomAllot.put("2", "未分配");
		getRequest().setAttribute("isAllot", roomAllot);
		//getRequest().setAttribute("balconyList", allotRoomManager.loadBalcony());
		return SUCCESS;
	}
	
	/** 
	* @title: queryAllotRoomsForAdd
	* @description: 查询未分配房间情况
	* @author hj
	* @date 2013-09-04
	*/ 
	@SuppressWarnings("unchecked")
	public String queryAllotRoomsForAdd() throws Exception {
		page.setPageSize(10);
		String ids = getParameter("ids");
		ArrayList idsList = new ArrayList();
		if (!"".equals(ids) && ids != null) {
			for (String id : ids.split(",")) {
				if (!"".equals(id)) idsList.add(Long.parseLong(id));
			}
		}
		
		HashMap map = new HashMap();
		map.put("ids", idsList);
		map.put("names", getParameter("names"));
		map.put("len", idsList.size());
		map.put("isAllot", "2");
		
		page = allotRoomManager.queryAllotRooms(page, map);
		Long count = allotRoomManager.countRoomsCanAllot();
		map.put("count", count);
		map.put("ids", ids);
		getRequest().setAttribute("map", map);
		//getRequest().setAttribute("balconyList", allotRoomManager.loadBalcony());
		return SUCCESS;
	}
	
	/**
	 * 根据楼座ID加载楼层
	 * @return 
	 * @throws Exception
	 */
	public String loadFloorByBid() {
		JSONObject json = new JSONObject();
		List list = allotRoomManager.loadFloorByBid(Long.parseLong(getParameter("grandId")));
		try {
			if (list != null &&  list.size() > 0) {
				json.put("list", list);
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
	
	/** 
	* @title: save
	* @description: 提交批量分配房间
	* @author zh
	* @date 2013-03-23
	*/ 
	public String allotSave() {
		try {
			String allNodeId = getParameter("allNodeId");
			String chkNodeId = getParameter("chkNodeId");
			long orgId = Long.parseLong(getParameter("orgId"));
			allotRoomManager.saveAllotRoom(allNodeId, chkNodeId, orgId);
			Struts2Util.renderText("success");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* @title: allotDeleteSave
	* @description: 提交删除分配房间
	* @author hj
	* @date 2013-09-04
	*/ 
	public String allotDeleteSave() {
		try {
			String chkNodeId = getParameter("chkNodeId");
			long orgId = Long.parseLong(getParameter("orgId"));
			String rooms = allotRoomManager.allotDeleteSave(chkNodeId, orgId);
			if (StringUtil.isNotBlank(rooms)) {
				Struts2Util.renderText(rooms);
			} else {
				Struts2Util.renderText("success");
			}
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	
	/** 
	* @title: allotRoomSave
	* @description: 提交添加分配房间
	* @author hj
	* @date 2013-09-04
	*/ 
	public String allotRoomSave() {
		try {
			String chkNodeId = getParameter("chkNodeId");
			long orgId = Long.parseLong(getParameter("orgId"));
			allotRoomManager.allotRoomSave(chkNodeId, orgId);

			Struts2Util.renderText("success");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
//	/** 
//	* @title: list
//	* @description: 查询房间分配信息
//	* @author zh
//	* @date 2013-03-21
//	*/ 
//	public String list() throws Exception {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("orgId", getParameter("orgId"));
//		page = allotRoomManager.queryRoomList(page, map);
//		
//		getRequest().setAttribute("map", map);
//		getRequest().setAttribute("orgList", allotRoomManager.loadOrg());
//		return SUCCESS;
//	}
//	
//	/**
//	 * 批量删除选中的房间分配信息
//	 * @return 
//	 * @throws Exception
//	 */
//	public String delete() throws Exception {
//		try {
//			allotRoomManager.deleteRoomAllot(_chk);
//			addActionMessage("删除房间分配信息成功");
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			addActionMessage("删除房间分配信息失败");
//		}
//		return RELOAD;
//	}
//	
//	/**
//	 * 显示用于分配
//	 * @return 
//	 * @throws Exception
//	 */
//	public String input() throws Exception {
//		getRequest().setAttribute("orgId", getParameter("orgId"));
//		getRequest().setAttribute("orgList", allotRoomManager.loadOrg());
//		getRequest().setAttribute("balconyList", allotRoomManager.loadBalcony());
//		return SUCCESS;
//	}
//	
//	/**
//	 * 根据楼层ID加载所有未分配的房间
//	 * @return 
//	 * @throws Exception
//	 */
//	public String loadRoomByFid() {
//		JSONObject json = new JSONObject();
//		List list = allotRoomManager.loadRoomByFid(Long.parseLong(getParameter("fid")));
//		try {
//			if (list != null &&  list.size() > 0) {
//				json.put("list", list);
//				json.put("status", 0);
//			} else {
//				json.put("status", 1);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
//		return null;
//	}
	
//	/** 
//	* @title: validAllotInfo
//	* @description: 提交验证
//	* @author zh
//	* @date 2013-03-23
//	*/ 
//	public String validAllotInfo() {
//		JSONObject json = new JSONObject();
//		try {
//			String ids = getParameter("ids");
//			long orgId = Long.parseLong(getParameter("orgId"));
//			// 效验选中的房间是否已分配给别的委办局
//			List<HashMap> list = allotRoomManager.checkRoomIsAllot(ids, orgId);
//			if (list.size() == 0) {
//				json.put("status", 0);
//			} else {
//				String names = "", roomids = "";
//				for (HashMap map : list) {
//					names += map.get("RES_NAME").toString() + ",";
//					roomids += map.get("RES_ID").toString() + ",";
//				}
//				json.put("status", 1);
//				json.put("names", names);
//				json.put("roomids", roomids);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
//		return null;
//	}
	
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

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}
	
	public AllotRoomManager getAllotRoomManager() {
		return allotRoomManager;
	}

	public void setAllotRoomManager(AllotRoomManager allotRoomManager) {
		this.allotRoomManager = allotRoomManager;
	}

	public Page<HashMap> getOrgPage() {
		return orgPage;
	}

	public void setOrgPage(Page<HashMap> orgPage) {
		this.orgPage = orgPage;
	}
}
