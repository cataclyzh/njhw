package com.cosmosource.app.threedimensional.action;

import com.cosmosource.app.threedimensional.service.RoomManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings("unchecked")
public class RoomAction extends BaseAction {

	private static final long serialVersionUID = 1533830008983298926L;
	private RoomManager roomManager;

	public String getRoomIds() {
		String roomNum = getParameter("roomNum");
		try {
			JSONObject json = new JSONObject();
			String roomId = roomManager.getRoomIdByRoomNum(roomNum);
			json.put("roomId", roomId);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public RoomManager getRoomManager() {
		return roomManager;
	}

	public void setRoomManager(RoomManager roomManager) {
		this.roomManager = roomManager;
	}

}
