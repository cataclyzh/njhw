package com.cosmosource.app.energyint.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.energyint.model.EiHistoryConsume;
import com.cosmosource.app.energyint.model.EiReport;
import com.cosmosource.base.service.BaseManager;

/**
 * 获取某一时间段某一能耗类型所有单位的能耗数据
 * 
 */
public class OrgEnergyDetailManager extends BaseManager {

	// 获取数据的类
	private EiReportManager eiReportManager;

	public EiReportManager getEiReportManager() {
		return eiReportManager;
	}

	public void setEiReportManager(EiReportManager eiReportManager) {
		this.eiReportManager = eiReportManager;
	}

	/**
	 * 获取各个单位能耗的接口
	 * 
	 * @param queryTimeType
	 * @return
	 */
	public HashMap<String, LinkedHashMap<String, Double>> getEnergyDetail(
			String queryTimeType) {

		HashMap<String, LinkedHashMap<String, Double>> energyDetail = null;

		if (EIConstrants.TIME_TYPE_PRE_HOUR.equals(queryTimeType)) {
			energyDetail = getPreHourEnergy();
		} else if (EIConstrants.TIME_TYPE_PRE_DAY.equals(queryTimeType)) {
			energyDetail = getPreDayEnergy();
		} else if (EIConstrants.TIME_TYPE_PRE_MONTH.equals(queryTimeType)) {
			energyDetail = getPreMonthEnergy();
		} else if (EIConstrants.TIME_TYPE_CURRENT_YEAR.equals(queryTimeType)) {
			energyDetail = getCurrentYearEnergy();
		} else if (EIConstrants.TIME_TYPE_PRE_YEAR.equals(queryTimeType)) {
			energyDetail = getPreYearEnergy();
		} else {
			energyDetail = new HashMap<String, LinkedHashMap<String, Double>>();
		}

		return energyDetail;
	}
	
	/**
	 * 获取各个单位能耗的接口
	 * 
	 * @param queryTimeType
	 * @return
	 */
	public HashMap<String, LinkedHashMap<String, Double>> getEnergyMonitorDetail(
			String queryTimeType) {

		HashMap<String, LinkedHashMap<String, Double>> energyDetail = null;

		if (EIConstrants.TIME_TYPE_CURRENT_YEAR.equals(queryTimeType)) {
			energyDetail = getCurrentYearEnergy();
		} else {
			energyDetail = getMonthEnergy(queryTimeType);
		}

		return energyDetail;
	}

	/**
	 * 获取前一小时各个单位的各种能耗
	 * 
	 * @return
	 */
	private HashMap<String, LinkedHashMap<String, Double>> getPreHourEnergy() {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间
		calendar.add(Calendar.HOUR, -1); // 得到前一小时

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // 月份需要加1
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		EiReport parameterEiReport = new EiReport();
		parameterEiReport.setYear(year);
		parameterEiReport.setMonth(month);
		parameterEiReport.setDay(day);
		parameterEiReport.setHour(hour);
		parameterEiReport.setQueryTimeType(3);

		LinkedHashMap<String, Double> water = eiReportManager
				.waterProcess(parameterEiReport);
		if (water == null) {
			return null;
		}
		List<LinkedHashMap<String, ?>> flow = eiReportManager
				.flowProcess(parameterEiReport);
		if (flow == null || flow.size() < 2) {
			return null;
		}
		LinkedHashMap<String, Double> flowHeat = (LinkedHashMap<String, Double>) flow
				.get(0);
		if (flowHeat == null) {
			return null;
		}
		LinkedHashMap<String, Double> flowFlow = (LinkedHashMap<String, Double>) flow
				.get(1);
		if (flowFlow == null) {
			return null;
		}
		LinkedHashMap<String, Double> kwh = eiReportManager
				.queryEnerygyInfoByOrgans(parameterEiReport);
		if (kwh == null) {
			return null;
		}

		HashMap<String, LinkedHashMap<String, Double>> result = new HashMap<String, LinkedHashMap<String, Double>>();
		result.put(EIConstrants.WATER_MEASURE, water);
		result.put(EIConstrants.FLOW_MEASURE_FLOW, flowFlow);
		result.put(EIConstrants.KWH_MEASURE, kwh);

		// 计算总能耗（Kwh）
		Double kwhValue = null;
		Double flowHeatValue = null;
		Double waterValue = null;
		Double totalValue = null;
		LinkedHashMap<String, Double> total = new LinkedHashMap<String, Double>();
		for (String org : kwh.keySet()) {
			kwhValue = new BigDecimal(String.valueOf(kwh.get(org)))
					.doubleValue();
			if (kwhValue == null) {
				kwhValue = 0.0;
			}
			if (flowHeat.containsKey(org)) {
				flowHeatValue = new BigDecimal(
						String.valueOf(flowHeat.get(org))).doubleValue();
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

			Double kwhCoal = EIUtils.mul(kwhValue,
					EIConstrants.POWER_TO_STANDARD_COAL);
			Double flowCoal = EIUtils.mul(flowHeatValue,
					EIConstrants.HEATING_POWER_TO_STANDARD_COAL);
			Double waterCoal = EIUtils.mul(waterValue,
					EIConstrants.WATER_TO_STANDARD_COAL);
			totalValue = EIUtils.calcTotalConsumeWithCoal(waterCoal, kwhCoal,
					flowCoal);
			total.put(org, totalValue);
		}
		result.put(EIConstrants.TOTAL_MEASURE, total);

		return result;
	}

	/**
	 * 获取前一天各个单位的各种能耗
	 * 
	 * @return
	 */
	private HashMap<String, LinkedHashMap<String, Double>> getPreDayEnergy() {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间
		calendar.add(Calendar.DATE, -1); // 得到前一天

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // 月份需要加1
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		EiReport parameterEiReport = new EiReport();
		parameterEiReport.setYear(year);
		parameterEiReport.setMonth(month);
		parameterEiReport.setDay(day);
		parameterEiReport.setQueryTimeType(2);

		LinkedHashMap<String, Double> water = eiReportManager
				.waterProcess(parameterEiReport);
		if (water == null) {
			return null;
		}
		List<LinkedHashMap<String, ?>> flow = eiReportManager
				.flowProcess(parameterEiReport);
		if (flow == null || flow.size() < 2) {
			return null;
		}
		LinkedHashMap<String, Double> flowHeat = (LinkedHashMap<String, Double>) flow
				.get(0);
		if (flowHeat == null) {
			return null;
		}
		LinkedHashMap<String, Double> flowFlow = (LinkedHashMap<String, Double>) flow
				.get(1);
		if (flowFlow == null) {
			return null;
		}
		LinkedHashMap<String, Double> kwh = eiReportManager
				.queryEnerygyInfoByOrgans(parameterEiReport);
		if (kwh == null) {
			return null;
		}

		HashMap<String, LinkedHashMap<String, Double>> result = new HashMap<String, LinkedHashMap<String, Double>>();
		result.put(EIConstrants.WATER_MEASURE, water);
		result.put(EIConstrants.FLOW_MEASURE_FLOW, flowFlow);
		result.put(EIConstrants.KWH_MEASURE, kwh);

		// 计算总能耗（Kwh）
		Double kwhValue = null;
		Double flowHeatValue = null;
		Double waterValue = null;
		Double totalValue = null;
		LinkedHashMap<String, Double> total = new LinkedHashMap<String, Double>();
		for (String org : kwh.keySet()) {
			kwhValue = new BigDecimal(String.valueOf(kwh.get(org)))
					.doubleValue();
			if (kwhValue == null) {
				kwhValue = 0.0;
			}
			if (flowHeat.containsKey(org)) {
				flowHeatValue = new BigDecimal(
						String.valueOf(flowHeat.get(org))).doubleValue();
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

			Double kwhCoal = EIUtils.mul(kwhValue,
					EIConstrants.POWER_TO_STANDARD_COAL);
			Double flowCoal = EIUtils.mul(flowHeatValue,
					EIConstrants.HEATING_POWER_TO_STANDARD_COAL);
			Double waterCoal = EIUtils.mul(waterValue,
					EIConstrants.WATER_TO_STANDARD_COAL);
			totalValue = EIUtils.calcTotalConsumeWithCoal(waterCoal, kwhCoal,
					flowCoal);
			total.put(org, totalValue);
		}
		result.put(EIConstrants.TOTAL_MEASURE, total);

		return result;
	}

	/**
	 * 获取前一个月各个单位的各种能耗
	 * 
	 * @return
	 */
	private HashMap<String, LinkedHashMap<String, Double>> getPreMonthEnergy() {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间
		calendar.add(Calendar.MONTH, -1); // 得到前一月

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // 月份需要加1

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

		HashMap<String, LinkedHashMap<String, Double>> result = new HashMap<String, LinkedHashMap<String, Double>>();
		result.put(EIConstrants.WATER_MEASURE, (LinkedHashMap<String, Double>)water);
		result.put(EIConstrants.FLOW_MEASURE_FLOW, (LinkedHashMap<String, Double>)flowFlow);
		result.put(EIConstrants.KWH_MEASURE, (LinkedHashMap<String, Double>)kwh);

		// 计算总能耗（Kwh）
		Double kwhValue = null;
		Double flowHeatValue = null;
		Double waterValue = null;
		Double totalValue = null;
		LinkedHashMap<String, Double> total = new LinkedHashMap<String, Double>();
		for (String org : kwh.keySet()) {
			kwhValue = new BigDecimal(String.valueOf(kwh.get(org)))
					.doubleValue();
			if (kwhValue == null) {
				kwhValue = 0.0;
			}
			if (flowHeat.containsKey(org)) {
				flowHeatValue = new BigDecimal(
						String.valueOf(flowHeat.get(org))).doubleValue();
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

			Double kwhCoal = EIUtils.mul(kwhValue,
					EIConstrants.POWER_TO_STANDARD_COAL);
			Double flowCoal = EIUtils.mul(flowHeatValue,
					EIConstrants.HEATING_POWER_TO_STANDARD_COAL);
			Double waterCoal = EIUtils.mul(waterValue,
					EIConstrants.WATER_TO_STANDARD_COAL);
			totalValue = EIUtils.calcTotalConsumeWithCoal(waterCoal, kwhCoal,
					flowCoal);
			total.put(org, totalValue);
		}
		result.put(EIConstrants.TOTAL_MEASURE, total);

		return result;
	}

	/**
	 * 获取前12个月各个单位的各种能耗
	 * 
	 * @return
	 */
	private HashMap<String, LinkedHashMap<String, Double>> getMonthEnergy(String preNum) {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间
		calendar.add(Calendar.MONTH, Integer.parseInt(preNum)); // 得到前一月

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // 月份需要加1

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
	
	/**
	 * 获取当年各个单位的能耗
	 * 
	 * @return
	 */
	private HashMap<String, LinkedHashMap<String, Double>> getCurrentYearEnergy() {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        
        Map<String,Integer> parametar = new HashMap<String,Integer>();
        parametar.put("year", year);
        parametar.put("month", month);
        //水
        parametar.put("consume_type", EiHistoryConsume.consume_type_1);
        Map<String, Double> water = eiReportManager.queryHistoryYearInfo(parametar);
        if (water == null)
        {
            return null;
        }
        
        //气
        parametar.put("consume_type", EiHistoryConsume.consume_type_22);
        Map<String, Double> flowHeat = eiReportManager.queryHistoryYearInfo(parametar);
        if (flowHeat == null)
        {
            return null;
        }
        parametar.put("consume_type", EiHistoryConsume.consume_type_2);
        Map<String, Double> flowFlow = eiReportManager.queryHistoryYearInfo(parametar);
        if (flowFlow == null)
        {
            return null;
        }
        
        //电
        parametar.put("consume_type", EiHistoryConsume.consume_type_3);
        Map<String, Double> kwh = eiReportManager.queryHistoryYearInfo(parametar);
        if (kwh == null)
        {
            return null;
        }

		HashMap<String, LinkedHashMap<String, Double>> result = new HashMap<String, LinkedHashMap<String, Double>>();
		result.put(EIConstrants.WATER_MEASURE, (LinkedHashMap<String, Double>)water);
		result.put(EIConstrants.FLOW_MEASURE_FLOW, (LinkedHashMap<String, Double>)flowFlow);
		result.put(EIConstrants.KWH_MEASURE, (LinkedHashMap<String, Double>)kwh);

		// 算总能耗
		Double kwhValue = null;
		Double flowHeatValue = null;
		Double waterValue = null;
		Double totalValue = null;
		LinkedHashMap<String, Double> total = new LinkedHashMap<String, Double>();
		for (String org : kwh.keySet()) {
			kwhValue = new BigDecimal(String.valueOf(kwh.get(org)))
					.doubleValue();
			if (kwhValue == null) {
				kwhValue = 0.0;
			}
			if (flowHeat.containsKey(org)) {
				flowHeatValue = new BigDecimal(
						String.valueOf(flowHeat.get(org))).doubleValue();
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

			Double kwhCoal = EIUtils.mul(kwhValue,
					EIConstrants.POWER_TO_STANDARD_COAL);
			Double flowCoal = EIUtils.mul(flowHeatValue,
					EIConstrants.HEATING_POWER_TO_STANDARD_COAL);
			Double waterCoal = EIUtils.mul(waterValue,
					EIConstrants.WATER_TO_STANDARD_COAL);
			totalValue = EIUtils.calcTotalConsumeWithCoal(waterCoal, kwhCoal,
					flowCoal);
			total.put(org, totalValue);
		}
		result.put(EIConstrants.TOTAL_MEASURE, total);

		return result;
	}

	/**
	 * 获取上一年各个单位的各种能耗
	 * 
	 * @return
	 */
	private HashMap<String, LinkedHashMap<String, Double>> getPreYearEnergy() {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间
		int year = calendar.get(Calendar.YEAR) - 1;// 获取前一年

		List<Integer> years = new ArrayList<Integer>();
		years.add(year);

		List<EiHistoryConsume> eiHistoryConsumeList = eiReportManager
				.queryHistoryOrganInfo(years);
		if (eiHistoryConsumeList == null) {
			return null;
		}

		LinkedHashMap<String, Double> water = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> flowHeat = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> flowFlow = new LinkedHashMap<String, Double>();
		LinkedHashMap<String, Double> kwh = new LinkedHashMap<String, Double>();

		Double waterValue = null;
		Double flowHeatValue = null;
		Double flowFlowValue = null;
		Double kwhValue = null;

		for (EiHistoryConsume eiHistoryConsume : eiHistoryConsumeList) {
			if (eiHistoryConsume.getConsume_type() == 1) {
				waterValue = eiHistoryConsume.getMeasure();
				if (waterValue != null) {
					water.put(eiHistoryConsume.getOrg_name().trim(), waterValue);
				}
			} else if (eiHistoryConsume.getConsume_type() == 2) {
				flowFlowValue = eiHistoryConsume.getMeasure();
				flowHeatValue = eiHistoryConsume.getMeasure_heat();
				if (flowFlowValue != null) {
					flowFlow.put(eiHistoryConsume.getOrg_name().trim(),
							flowFlowValue);
				}
				if (flowHeatValue != null) {
					flowHeat.put(eiHistoryConsume.getOrg_name().trim(),
							flowHeatValue);
				}
			} else if (eiHistoryConsume.getConsume_type() == 3) {
				kwhValue = eiHistoryConsume.getMeasure();
				if (kwhValue != null) {
					kwh.put(eiHistoryConsume.getOrg_name().trim(), kwhValue);
				}
			}
		}
		HashMap<String, LinkedHashMap<String, Double>> result = new HashMap<String, LinkedHashMap<String, Double>>();
		result.put(EIConstrants.WATER_MEASURE, water);
		result.put(EIConstrants.FLOW_MEASURE_FLOW, flowFlow);
		result.put(EIConstrants.KWH_MEASURE, kwh);

		// 计算总能耗（Kwh）
		LinkedHashMap<String, Double> total = new LinkedHashMap<String, Double>();
		Double totalValue = null;
		for (String org : kwh.keySet()) {
			kwhValue = new BigDecimal(String.valueOf(kwh.get(org)))
					.doubleValue();
			if (kwhValue == null) {
				kwhValue = 0.0;
			}
			if (flowHeat.containsKey(org)) {
				flowHeatValue = new BigDecimal(
						String.valueOf(flowHeat.get(org))).doubleValue();
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

			Double kwhCoal = EIUtils.mul(kwhValue,
					EIConstrants.POWER_TO_STANDARD_COAL);
			Double flowCoal = EIUtils.mul(flowHeatValue,
					EIConstrants.HEATING_POWER_TO_STANDARD_COAL);
			Double waterCoal = EIUtils.mul(waterValue,
					EIConstrants.WATER_TO_STANDARD_COAL);
			totalValue = EIUtils.calcTotalConsumeWithCoal(waterCoal, kwhCoal,
					flowCoal);
			total.put(org, totalValue);
		}
		result.put(EIConstrants.TOTAL_MEASURE, total);

		return result;

	}
}
