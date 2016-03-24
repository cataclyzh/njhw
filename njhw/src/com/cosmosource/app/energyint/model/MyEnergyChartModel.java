package com.cosmosource.app.energyint.model;

import java.util.HashMap;

public class MyEnergyChartModel {
	private HashMap<Integer, Double> buildingEverydayEnergy;

	private HashMap<Integer, Double> myEverydayEnergy;

	private HashMap<Integer, Double> buildingEverydayKwh;

	private HashMap<Integer, Double> myEverydayKwh;

	public HashMap<Integer, Double> getBuildingEverydayEnergy() {
		return buildingEverydayEnergy;
	}

	public void setBuildingEverydayEnergy(
			HashMap<Integer, Double> buildingEverydayEnergy) {
		this.buildingEverydayEnergy = buildingEverydayEnergy;
	}

	public HashMap<Integer, Double> getMyEverydayEnergy() {
		return myEverydayEnergy;
	}

	public void setMyEverydayEnergy(HashMap<Integer, Double> myEverydayEnergy) {
		this.myEverydayEnergy = myEverydayEnergy;
	}

	public HashMap<Integer, Double> getBuildingEverydayKwh() {
		return buildingEverydayKwh;
	}

	public void setBuildingEverydayKwh(
			HashMap<Integer, Double> buildingEverydayKwh) {
		this.buildingEverydayKwh = buildingEverydayKwh;
	}

	public HashMap<Integer, Double> getMyEverydayKwh() {
		return myEverydayKwh;
	}

	public void setMyEverydayKwh(HashMap<Integer, Double> myEverydayKwh) {
		this.myEverydayKwh = myEverydayKwh;
	}

}
