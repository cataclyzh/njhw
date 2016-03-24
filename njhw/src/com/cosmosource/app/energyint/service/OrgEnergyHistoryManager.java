package com.cosmosource.app.energyint.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.energyint.model.EiHistoryConsume;
import com.cosmosource.app.energyint.model.EiReport;
import com.cosmosource.base.service.BaseManager;

/**
 * 获取所有单位某年某个月的能耗
 * 
 */
public class OrgEnergyHistoryManager extends BaseManager {

	// 获取数据的类
	private EiReportManager eiReportManager;

	public EiReportManager getEiReportManager() {
		return eiReportManager;
	}

	public void setEiReportManager(EiReportManager eiReportManager) {
		this.eiReportManager = eiReportManager;
	}

	/**
	 * 获取某年某月各个单位的各种能耗
	 * 
	 * @return
	 */
	public HashMap<String, LinkedHashMap<String, Double>> getPreMonthEnergy(
			int year, int month) {
        Map<String,Integer> parametar = new HashMap<String,Integer>();
        parametar.put("year", year);
        parametar.put("month", month);
        parametar.put("consume_type", EiHistoryConsume.consume_type_1);

        //水
		Map<String, Double> water = eiReportManager.queryHistoryMonthInfo(parametar);
		if (water == null) {
			return null;
		}
		
		//气
        parametar.put("consume_type", EiHistoryConsume.consume_type_2);
        Map<String, Double> flowFlow = eiReportManager.queryHistoryMonthInfo(parametar);
        if (flowFlow == null)
        {
            return null;
        }
        parametar.put("consume_type", EiHistoryConsume.consume_type_22);
        Map<String, Double> flowHeat = eiReportManager.queryHistoryMonthInfo(parametar);
        if (flowHeat == null)
        {
            return null;
        }
		
        //电
		parametar.put("consume_type", EiHistoryConsume.consume_type_3);
		Map<String, Double> kwh = eiReportManager.queryHistoryMonthInfo(parametar);
		if (kwh == null) {
			return null;
		}
		
        //油
		parametar.put("consume_type", EiHistoryConsume.consume_type_4);
		Map<String, Double> oil = eiReportManager.queryHistoryMonthInfo(parametar);
		if (oil == null) {
			return null;
		}

		HashMap<String, LinkedHashMap<String, Double>> result = new HashMap<String, LinkedHashMap<String, Double>>();
		result.put(EIConstrants.WATER_MEASURE, (LinkedHashMap<String, Double>)water);
		result.put(EIConstrants.FLOW_MEASURE_FLOW, (LinkedHashMap<String, Double>)flowFlow);
		result.put(EIConstrants.KWH_MEASURE, (LinkedHashMap<String, Double>)kwh);
		result.put(EIConstrants.OIL_MEASURE, (LinkedHashMap<String, Double>)oil);
		
		//油
		parametar.put("consume_type", EiHistoryConsume.consume_type_4);
		Map<String, Double> oilM = eiReportManager.queryOilHistoryMonthInfo(parametar);

		// 计算总能耗（Kwh）
		Double kwhValue = null;
		Double flowHeatValue = null;
		Double waterValue = null;
		Double gasolineValue = null;
		Double dOilValue = null;
		Double totalValue = null;
		LinkedHashMap<String, Double> total = new LinkedHashMap<String, Double>();
		for (String org : kwh.keySet()) {
			kwhValue = new BigDecimal(String.valueOf(kwh.get(org)))
					.doubleValue();
			if (kwhValue == null) {
				kwhValue = 0.0;
			}
			if (flowFlow.containsKey(org)) {
				flowHeatValue = new BigDecimal(
						String.valueOf(flowFlow.get(org))).doubleValue();
				if (flowHeatValue == null) {
					flowHeatValue = 0.0;
				}
			} else {
				flowHeatValue = 0.0;
			}

			if (water.containsKey(org)) {
				waterValue = new BigDecimal(String.valueOf(water.get(org)))
						.doubleValue();
				if (waterValue == null) {
					waterValue = 0.0;
				}
			} else {
				waterValue = 0.0;
			}
			
			if (oilM.containsKey(org+"G")) {
				gasolineValue = new BigDecimal(String.valueOf(oilM.get(org+"G")))
						.doubleValue();
				if (gasolineValue == null) {
					gasolineValue = 0.0;
				}
			} else {
				gasolineValue = 0.0;
			}
			
			if (oilM.containsKey(org+"D")) {
				dOilValue = new BigDecimal(String.valueOf(oilM.get(org+"D")))
						.doubleValue();
				if (dOilValue == null) {
					dOilValue = 0.0;
				}
			} else {
				dOilValue = 0.0;
			}

			Double kwhCoal = EIUtils.mul(kwhValue,
					EIConstrants.POWER_TO_STANDARD_COAL);
			Double flowCoal = EIUtils.mul(flowHeatValue,
					EIConstrants.FLOW_POWER_TO_STANDARD_COAL);
			Double waterCoal = EIUtils.mul(waterValue,
					EIConstrants.WATER_TO_STANDARD_COAL);
			Double gasolineCoal = EIUtils.mul(gasolineValue,
					EIConstrants.GASOLINE_POWER_TO_STANDARD_COAL);
			Double dOilCoal = EIUtils.mul(dOilValue,
					EIConstrants.DIESOL_OIL_POWER_TO_STANDARD_COAL);
			totalValue = EIUtils.calcTotalConsumeWithCoalNew(waterCoal, kwhCoal,
					flowCoal, gasolineCoal, dOilCoal);
			total.put(org, totalValue);
		}
		result.put(EIConstrants.TOTAL_MEASURE, total);

		return result;
	}

}
