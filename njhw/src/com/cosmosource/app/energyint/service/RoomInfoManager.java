package com.cosmosource.app.energyint.service;

import com.cosmosource.base.service.BaseManager;

public class RoomInfoManager extends BaseManager {

	/**
	 * 根据房间ID获取房间
	 */
	public String getRoom(String roomId) {
		String room = (String) sqlDao.getSqlMapClientTemplate().queryForObject(
				"EnergyintSQL.SELECT_ROOM_NAME", roomId);

		return room;
	}

	/**
	 * 获取房间所在的哪栋楼
	 * 
	 * @param room
	 * @return
	 */
	public String getSeat(String room) {
		String seat = (String) sqlDao.getSqlMapClientTemplate().queryForObject(
				"EnergyintSQL.SELECT_ROOM_SEAT", room);

		return seat;
	}

	/**
	 * 获取房间所在的楼层
	 * 
	 * @param room
	 * @return
	 */
	public String getFloor(String room) {
		String floor = (String) sqlDao.getSqlMapClientTemplate()
				.queryForObject("EnergyintSQL.SELECT_ROOM_FLOOR", room);

		return floor;
	}

}
