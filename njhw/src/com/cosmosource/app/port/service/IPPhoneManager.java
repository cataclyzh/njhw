package com.cosmosource.app.port.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.service.BaseManager;

/**
 * @ClassName:IPPhoneManager
 * @Description：处理ip业务逻辑的manager类
 * @Author：hp
 * @Date:2013-4-21
 */
public class IPPhoneManager extends BaseManager {

	/**
	 * @title: controlDoor
	 * @description: 开门操作
	 * @author mac
	 * @param vsId
	 * @return
	 * @date 2013-5-1 下午05:09:07
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String controlDoor(String mac) {
		String dev = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mac", mac);
		try {
			List<Map> lists = this.findListBySql("PortSQL.controlDoor", map);
			if (lists != null && lists.size() > 0) {
				dev = lists.get(0).get("NODEID").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dev;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String controlDoorISE(String roomName) {
		String dev = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("roomName", roomName);
		try {
			List<Map> lists = this.findListBySql("PortSQL.controlDoorISE", map);
			if (lists != null && lists.size() > 0) {
				dev = lists.get(0).get("NODEID").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dev;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, String> getPhoneMacInfo(String macAddress) {

		Map<String, String> map = new HashMap<String, String>();
		HashMap<String, String> returnMap = new HashMap<String, String>();
		map.put("macAddress", macAddress);
		try {
			List<Map> lists = this
					.findListBySql("PortSQL.getPhoneMacInfo", map);
			if (lists != null && lists.size() > 0) {
				returnMap.put("macAddress", lists.get(0).get("MACADDRESS")
						.toString());
				returnMap.put("roomId", lists.get(0).get("ROOMID").toString());
				returnMap.put("roomNo", lists.get(0).get("ROOMNO").toString());
				return returnMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String getDoorInfoByRoomName(String roomName){
		Map<String, String> param = new HashMap<String, String>();
		String result = null;
		param.put("roomName", roomName);
		try {
			List<Map> lists = this
					.findListBySql("PortSQL.getDoorInfoByRoomName", param);
			if (lists != null && lists.size() > 0) {
				Map info = lists.get(0);
				result = String.valueOf(info.get("DOORID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updatePhoneMacaddress(Map<String, String> info) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("macAddress", info.get("macAddress"));
		map.put("rooId", info.get("rooId"));
		map.put("roomNo", info.get("roomNo"));
		try {
			this.findListBySql("PortSQL.updateMacInfoForISE", map);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getRoomNo(HashMap<String, String> map) {
		String dev = null;
		try {
			List<Map> lists = this
					.findListBySql("PortSQL.getRoomIDForISE", map);
			if (lists != null && lists.size() > 0) {
				dev = lists.get(0).get("ROOM_NO").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dev;
	}
}
