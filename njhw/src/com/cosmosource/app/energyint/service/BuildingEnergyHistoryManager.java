package com.cosmosource.app.energyint.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.cosmosource.app.energyint.model.EiHistoryConsume;
import com.cosmosource.base.service.BaseManager;

public class BuildingEnergyHistoryManager extends BaseManager {

	private int year;

	private List<String> cosumes;

	// 获取数据的类
	private EiReportManager eiReportManager;

	public EiReportManager getEiReportManager() {
		return eiReportManager;
	}

	public void setEiReportManager(EiReportManager eiReportManager) {
		this.eiReportManager = eiReportManager;
	}

	public BuildingEnergyHistoryManager() {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);

		cosumes = new ArrayList<String>();
		cosumes.add(EIConstrants.TOTAL_MEASURE);
		cosumes.add(EIConstrants.KWH_MEASURE);
		cosumes.add(EIConstrants.WATER_MEASURE);
		cosumes.add(EIConstrants.FLOW_MEASURE_FLOW);
		cosumes.add(EIConstrants.FLOW_MEASURE_HEAT);

	}

	/**
	 * 获取大厦能耗历史数据（5年）
	 * 
	 * @param energyType
	 * @return
	 */
	public HashMap<String, LinkedHashMap<Integer, Double>> getBuildingEnergyHistory() {

		HashMap<String, LinkedHashMap<Integer, Double>> buildingEnergyHistory = new HashMap<String, LinkedHashMap<Integer, Double>>();
		List<Integer> yearList = new ArrayList<Integer>();

		for (int years = year - 5; years <= year - 1; years++) {
			yearList.add(years);
		}

		List<EiHistoryConsume> result = eiReportManager
				.queryHistoryBuildingInfo(yearList);

		if (result == null || result.size() == 0) {
			for (String consumeType : cosumes) {
				LinkedHashMap<Integer, Double> oneTypeConsume = new LinkedHashMap<Integer, Double>();
				for (Integer year : yearList) {
					oneTypeConsume.put(year, 0.0);
				}
				buildingEnergyHistory.put(consumeType, oneTypeConsume);
			}
		}

		// 水耗
		LinkedHashMap<Integer, Double> water = new LinkedHashMap<Integer, Double>();
		// 气耗
		LinkedHashMap<Integer, Double> flow = new LinkedHashMap<Integer, Double>();
		// 气耗（heat）
		LinkedHashMap<Integer, Double> flowHeat = new LinkedHashMap<Integer, Double>();
		// 电耗
		LinkedHashMap<Integer, Double> kwh = new LinkedHashMap<Integer, Double>();
		// 油耗
		LinkedHashMap<Integer, Double> oil = new LinkedHashMap<Integer, Double>();
		// 油耗(汽油)
		LinkedHashMap<Integer, Double> oilG = new LinkedHashMap<Integer, Double>();
		// 油耗(柴油)
		LinkedHashMap<Integer, Double> oilD = new LinkedHashMap<Integer, Double>();
		// 总能耗
		LinkedHashMap<Integer, Double> total = new LinkedHashMap<Integer, Double>();

		for (EiHistoryConsume eiHistoryConsume : result) {
			if (eiHistoryConsume.getConsume_type() == 1) {
				water.put(eiHistoryConsume.getHistory_year(),
						eiHistoryConsume.getMeasure());
			} else if (eiHistoryConsume.getConsume_type() == 2) {
				flow.put(eiHistoryConsume.getHistory_year(),
						eiHistoryConsume.getMeasure());
				flowHeat.put(eiHistoryConsume.getHistory_year(),
						eiHistoryConsume.getMeasure_heat());
			} else if (eiHistoryConsume.getConsume_type() == 3) {
				kwh.put(eiHistoryConsume.getHistory_year(),
						eiHistoryConsume.getMeasure());
			} else if (eiHistoryConsume.getConsume_type() == 4) {
				oil.put(eiHistoryConsume.getHistory_year(),
						EIUtils.add(eiHistoryConsume.getMeasure(), eiHistoryConsume.getMeasure_heat()));
				oilG.put(eiHistoryConsume.getHistory_year(),
						eiHistoryConsume.getMeasure());
				oilD.put(eiHistoryConsume.getHistory_year(),
						eiHistoryConsume.getMeasure());
			}
		}

		Double waterValue = null;
		Double flowValue = null;
		Double flowHeatValue = null;
		Double kwhValue = null;
		Double oilValue = null;
		Double oilGValue = null;
		Double oilDValue = null;
		Double totalValue = null;
		for (Integer year : yearList) {
			if (water.containsKey(year)) {
				waterValue = new BigDecimal(String.valueOf(water.get(year)))
						.doubleValue();
				if (waterValue == null) {
					waterValue = 0.0;
					water.put(year, waterValue);
				}
			} else {
				waterValue = 0.0;
				water.put(year, waterValue);
			}

			if (flow.containsKey(year)) {
				flowValue = new BigDecimal(String.valueOf(flow.get(year)))
						.doubleValue();
				if (flowValue == null) {
					flowValue = 0.0;
					flow.put(year, flowValue);
				}
			} else {
				flowValue = 0.0;
				flow.put(year, flowValue);
			}

			if (flowHeat.containsKey(year)) {
				flowHeatValue = new BigDecimal(String.valueOf(flowHeat
						.get(year))).doubleValue();
				if (flowHeatValue == null) {
					flowHeatValue = 0.0;
					flowHeat.put(year, flowHeatValue);
				}
			} else {
				flowHeatValue = 0.0;
				flowHeat.put(year, flowHeatValue);
			}

			if (kwh.containsKey(year)) {
				kwhValue = new BigDecimal(String.valueOf(kwh.get(year)))
						.doubleValue();
				if (kwhValue == null) {
					kwhValue = 0.0;
					kwh.put(year, kwhValue);
				}
			} else {
				kwhValue = 0.0;
				kwh.put(year, kwhValue);
			}
			
			if (oil.containsKey(year)) {
				oilValue = new BigDecimal(String.valueOf(oil.get(year)))
						.doubleValue();
				if (oilValue == null) {
					oil.put(year, 0.0);
				}
			} else {
				oil.put(year, 0.0);
			}
			
			if (oilG.containsKey(year)) {
				oilGValue = new BigDecimal(String.valueOf(oilG.get(year)))
						.doubleValue();
				if (oilGValue == null) {
					oilGValue = 0.0;
				}
			} else {
				oilGValue = 0.0;
			}
			
			if (oilD.containsKey(year)) {
				oilDValue = new BigDecimal(String.valueOf(oilD.get(year)))
						.doubleValue();
				if (oilDValue == null) {
					oilDValue = 0.0;
				}
			} else {
				oilDValue = 0.0;
			}

			// 计算总能耗（Kwh）
			Double kwhCoal = EIUtils.mul(kwhValue,
					EIConstrants.POWER_TO_STANDARD_COAL);
			Double flowCoal = EIUtils.mul(flowValue,
					EIConstrants.FLOW_POWER_TO_STANDARD_COAL);
			Double waterCoal = EIUtils.mul(waterValue,
					EIConstrants.WATER_TO_STANDARD_COAL);
			Double oilGCoal = EIUtils.mul(oilGValue,
					EIConstrants.GASOLINE_POWER_TO_STANDARD_COAL);
			Double oilDCoal = EIUtils.mul(oilDValue,
					EIConstrants.DIESOL_OIL_POWER_TO_STANDARD_COAL);
			totalValue = EIUtils.calcTotalConsumeWithCoalNew(waterCoal, kwhCoal,
					flowCoal, oilGCoal, oilDCoal);
			total.put(year, totalValue);
		}

		buildingEnergyHistory.put(EIConstrants.WATER_MEASURE, water);
		buildingEnergyHistory.put(EIConstrants.FLOW_MEASURE_FLOW, flow);
		buildingEnergyHistory.put(EIConstrants.KWH_MEASURE, kwh);
		buildingEnergyHistory.put(EIConstrants.OIL_MEASURE, oil);
		buildingEnergyHistory.put(EIConstrants.TOTAL_MEASURE, total);

		return buildingEnergyHistory;
	}
}
