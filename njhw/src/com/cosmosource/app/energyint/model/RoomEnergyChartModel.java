package com.cosmosource.app.energyint.model;

import java.util.HashMap;

public class RoomEnergyChartModel {
	private HashMap<Integer, Double> roomEverydayKwh;

	public HashMap<Integer, Double> getRoomEverydayKwh() {
		return roomEverydayKwh;
	}

	public void setRoomEverydayKwh(HashMap<Integer, Double> roomEverydayKwh) {
		this.roomEverydayKwh = roomEverydayKwh;
	}

}
