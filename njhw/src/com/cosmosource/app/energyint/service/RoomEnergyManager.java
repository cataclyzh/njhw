package com.cosmosource.app.energyint.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cosmosource.app.energyint.model.RoomEnergyChartModel;

public class RoomEnergyManager {

	// 当前年份
	private int year;

	// 当前月份
	private int month;

	// 当前天
	private int day;

	// 房间所在的哪栋楼
	private String seat;

	// 房间所在的楼层
	private String floor;

	// 房间名
	private String room;

	// 房间id
	private String roomId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	// 获取房间所在哪栋楼、楼层的类
	private RoomInfoManager roomInfoManager;

	public RoomInfoManager getRoomInfoManager() {
		return roomInfoManager;
	}

	public void setRoomInfoManager(RoomInfoManager roomInfoManager) {
		this.roomInfoManager = roomInfoManager;
	}

	public RoomEnergyManager() {
		Calendar today = Calendar.getInstance();
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1;
		day = today.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取房间当月每天的能耗(只计算电耗)
	 * 
	 * @return
	 */
	public RoomEnergyChartModel getRoomEverydayEnergy() {
		RoomEnergyChartModel roomEnergyChartModel = new RoomEnergyChartModel();

		if (seat == null || floor == null) {
			room = roomInfoManager.getRoom(roomId);
			seat = roomInfoManager.getSeat(room);
			floor = roomInfoManager.getFloor(room);
		}

		if (seat == null || "".equals(seat) || floor == null
				|| "".equals(floor) || room == null || "".equals(room)) {
			HashMap<Integer, Double> energy = new LinkedHashMap<Integer, Double>();
			for (int i = 1; i <= day; i++) {
				energy.put(i, 0.0);
			}
			roomEnergyChartModel.setRoomEverydayKwh(energy);
		}

		// 获取房间当月每天的电耗
		HashMap<Integer, Double> roomEnergy = new LinkedHashMap<Integer, Double>();
		ExecutorService pool = null;
		pool = Executors.newFixedThreadPool(1);
		RoomEverydayKwhThread roomEverydayKwhThread = new RoomEverydayKwhThread(
				seat, floor, room, year, month);
		pool.execute(roomEverydayKwhThread);

		pool.shutdown();
		while (!pool.isTerminated()) {
			// all tasks have completed following shut down
		}

		TreeMap<Integer, Double> roomEverydayKwh = roomEverydayKwhThread
				.getKwh();

		Double kwhConsume = 0.0;

		for (int i = 1; i <= day; i++) {
			kwhConsume = roomEverydayKwh.get(i);
			if (kwhConsume == null) {
				kwhConsume = 0.0;
			}
			roomEnergy.put(i, kwhConsume);
		}

		roomEnergyChartModel.setRoomEverydayKwh(roomEnergy);

		return roomEnergyChartModel;
	}
}

/**
 * 获取大厦当月每天的电耗的线程类
 * 
 */
class RoomEverydayKwhThread extends Thread {

	private TreeMap<Integer, Double> kwh;

	private String seat;
	private String floor;
	private String room;
	private int year;
	private int month;

	public RoomEverydayKwhThread(String seat, String floor, String room,
			int year, int month) {
		this.seat = seat;
		this.floor = floor;
		this.room = room;
		this.year = year;
		this.month = month;
	}

	@Override
	public void run() {
		getRoomEverydayKwh();
	}

	private void getRoomEverydayKwh() {
		String mdxStr = String.format(MdxStrings.ROOM_CONSUME, seat, floor,
				room, year, month);

		MdxExecutor mdxExecutor = new MdxExecutor();
		kwh = mdxExecutor.getResultHashMap(mdxStr);
	}

	public TreeMap<Integer, Double> getKwh() {
		return kwh;
	}

	public void setKwh(TreeMap<Integer, Double> kwh) {
		this.kwh = kwh;
	}

}
