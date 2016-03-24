package com.cosmosource.app.personnel.action;

import java.util.List;

import net.sf.json.JSONObject;

import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.personnel.service.SWAllotRoomManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("serial")
public class SWPersonAllotRoomAction extends BaseAction<Object> {
	
	
	private SWAllotRoomManager swAllotRoomManager;

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public Object getModel() {
		return null;
	}

	
	public String init(){
		Struts2Util.getRequest().setAttribute("orgId", Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
		return INIT;
	}
	
	/** 
	* @title: input
	* @description: 初始化人员分配房间
	* @author cjw
	* @date 2013-04-02
	*/ 
	public String input(){
		String roomId = Struts2Util.getParameter("roomId");
		String orgId =  Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		Struts2Util.getRequest().setAttribute("roomId",roomId);
		try {
			List<Org> orgs = swAllotRoomManager.loodOrgsByUserOrgId(Long.parseLong(orgId));
			Struts2Util.getRequest().setAttribute("orgList",orgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	
	/**
	 * 
	* @title: loadUsersByOrgId 
	* @description:通过单位得到单位下的人员
	* @author cjw
	* @return
	* @date 2013-4-6 上午10:26:58     
	* @throws
	 */
	public String  loadUsersByOrgId(){
		String orgId = Struts2Util.getParameter("orgId");
		String roomId = Struts2Util.getParameter("roomId");
		JSONObject json = new JSONObject();
		try {
			
			List<Users> unAllotUsers = swAllotRoomManager.loadUsersByOrgIdUnAllot(Long.parseLong(orgId));
			List<Users> allotThisUsers = swAllotRoomManager.loadUsersByOrgIdAllotThisRoom(Long.parseLong(orgId),Long.parseLong(roomId));
			List<Users> allotOtherUsers = swAllotRoomManager.loadUsersByOrgIdAllotOtherRoom(Long.parseLong(orgId),Long.parseLong(roomId));
			if(unAllotUsers==null && allotThisUsers==null && allotOtherUsers==null ){
				json.put("state", 0);
			}else{
				json.put("state", 1);
				json.put("unAllotUsers", JsonUtil.listToJsonb(unAllotUsers));
				json.put("allotThisUsers", JsonUtil.listToJsonb(allotThisUsers));
				json.put("allotOtherUsers", JsonUtil.listToJsonb(allotOtherUsers));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: save 
	* @description: 人员分配
	* @author cjw
	* @return
	* @date 2013-4-2 下午05:34:50     
	* @throws
	 */
	public String save() {
		try {
			String orgId =  Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
			String roomId = getParameter("roomId");
			String userIds = getParameter("userIds");
			swAllotRoomManager.updateUsersByRoomId(roomId, userIds,orgId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		String orgId =  Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		Struts2Util.getRequest().setAttribute("roomId","3206");
		List<Org> orgs = swAllotRoomManager.loodOrgsByUserOrgId(Long.parseLong(orgId));
		Struts2Util.getRequest().setAttribute("orgList",orgs);
		return SUCCESS;
	}

	public SWAllotRoomManager getSwAllotRoomManager() {
		return swAllotRoomManager;
	}

	public void setSwAllotRoomManager(SWAllotRoomManager swAllotRoomManager) {
		this.swAllotRoomManager = swAllotRoomManager;
	}
	
	
}
