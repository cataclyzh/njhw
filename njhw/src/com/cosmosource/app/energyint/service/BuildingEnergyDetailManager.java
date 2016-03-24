package com.cosmosource.app.energyint.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cosmosource.app.energyint.model.EiHistoryConsume;
import com.cosmosource.app.energyint.model.EiReport;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.StringUtil;

/**
 * 查看大厦能耗数据明细
 * 
 */
public class BuildingEnergyDetailManager extends BaseManager {

	// 获取数据的对象
	private EiReportManager eiReportManager;

	public EiReportManager getEiReportManager() {
		return eiReportManager;
	}

	public void setEiReportManager(EiReportManager eiReportManager) {
		this.eiReportManager = eiReportManager;
	}

	/**
	 * 获取能耗明细数据
	 * 
	 * @param queryTimeType
	 * @return
	 */
	public HashMap<String, Double> getEnergyDetail(String queryTimeType) {

		HashMap<String, Double> energyDetail = null;

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
			energyDetail = new HashMap<String, Double>();
		}

		return energyDetail;
	}
	
	public List queryOilEnergyInput() {
		Map map = new HashMap();

		List<Map> lm = sqlDao.getSqlMapClientTemplate().queryForList(
				"EnergyintSQL.queryOilEnergyInput", map);

		
		return lm;
	}
	
	/**
	 * 获取能耗明细数据
	 * 
	 * @param queryTimeType
	 * @return
	 */
	public Map getEnergyDetailList(String queryEnergyType) {
		List<Double> l = new ArrayList<Double>();
		
		Map map = new HashMap();
		
		map.put("queryEnergyType", queryEnergyType);
		
		List<Map> lm = sqlDao.getSqlMapClientTemplate().queryForList(
				"EnergyintSQL.getEnergyMonthDetail", map);
		
		Map resultMonthMap = new HashMap();
		
		if (lm != null && lm.size() > 0) {
			for (Map rm : lm) {
				resultMonthMap.put(rm.get("HISTORY_DATE"), rm);
			}
		}
		
		Map<String, Map> monthMap = new HashMap<String, Map>();
		for (int i = -12 ; i < 0 ; i++) {
			Calendar calendar = Calendar.getInstance();// 获取系统当前时间
			calendar.add(Calendar.MONTH, i); // 得到前一月

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			
			String key = year + "-" + month;
			
			if (resultMonthMap.containsKey(key)) {
				monthMap.put(i + "", (Map) resultMonthMap.get(key));
			} else {
				Map mm = new HashMap();
				
				mm.put("MEASURE", 0);
				mm.put("MEASURE_HEAT", 0);
				
				monthMap.put(i + "", mm);
			}
		}
		
		return monthMap;
	}
	
	/**
	 * 保存油耗能源信息
	 * 
	 * @param insertOilMonthInfo
	 * @return
	 */
	public void insertOilMonthInfo(Long orgId, String oilG_2, String oilD_2,
			int preMonth) {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间
		calendar.add(Calendar.MONTH, -preMonth); // 得到前一月

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		EiHistoryConsume eiHistoryConsume = new EiHistoryConsume();
		eiHistoryConsume.setOrg_name(orgId.toString());
		eiHistoryConsume.setHistory_month(month);
		eiHistoryConsume.setHistory_year(year);
		eiHistoryConsume.setConsume_type(4);
		eiHistoryConsume.setMeasure(Double.parseDouble(oilG_2));
		eiHistoryConsume.setMeasure_heat(Double.parseDouble(oilD_2));
		
		List<Map> lm = sqlDao.getSqlMapClientTemplate().queryForList(
				"EnergyintSQL.queryOilEnergyMonth", eiHistoryConsume);
		
		if (lm != null && lm.size() > 0 && lm.get(0).get("COUNT") != null && Long.parseLong(lm.get(0).get("COUNT").toString()) > 0) {
			sqlDao.getSqlMapClientTemplate().update("EnergyintSQL.UPDATE_EI_HISTORY_CONSUME_MONTH",eiHistoryConsume);
		} else {
			sqlDao.getSqlMapClientTemplate().insert("EnergyintSQL.INSERT_EI_HISTORY_CONSUME_MONTH",eiHistoryConsume);
		}
	}
	
	/**
	 * 获取能耗明细数据
	 * 
	 * @param queryTimeType
	 * @return
	 */
	public Map getEnergyDetailList() {
		Map elec = getEnergyDetailList("3");
		Map water = getEnergyDetailList("1");
		Map flow = getEnergyDetailList("2");
		Map oil = getEnergyDetailList("4");
		
		Map total = new HashMap();
		
		for (int i = -12; i < 0; i++) {
			String key = i + "";
				
			Double kwhStandardCoal = eiReportManager.countStandardCoal(Double.parseDouble(((Map) elec.get(key)).get("MEASURE").toString()), EIConstrants.POWER_TO_STANDARD_COAL);
			
			Double waterStandardCoal = eiReportManager.countStandardCoal(Double.parseDouble(((Map) water.get(key)).get("MEASURE").toString()), EIConstrants.WATER_TO_STANDARD_COAL);
			
			Double flowStandardCoal = eiReportManager.countStandardCoal(Double.parseDouble(((Map) flow.get(key)).get("MEASURE").toString()), EIConstrants.FLOW_POWER_TO_STANDARD_COAL);
			
			Double gasolineStandardCoal = eiReportManager.countStandardCoal(Double.parseDouble(((Map) oil.get(key)).get("MEASURE").toString()), EIConstrants.GASOLINE_POWER_TO_STANDARD_COAL);
			
			Double diesolOilStandardCoal = eiReportManager.countStandardCoal(Double.parseDouble(((Map) oil.get(key)).get("MEASURE_HEAT").toString()), EIConstrants.DIESOL_OIL_POWER_TO_STANDARD_COAL);
			
			//总标准煤(千克标准煤)
	        Double count = eiReportManager.sum(kwhStandardCoal, waterStandardCoal,
	        		flowStandardCoal, gasolineStandardCoal, diesolOilStandardCoal);

	        // 总能耗（kWh）
	        total.put(key, eiReportManager.div(count,EIConstrants.POWER_TO_STANDARD_COAL));
	        
	        String percentKey = key + "per";
	        
	        if(count == 0d)
	        {
	            count = 1d;
	        }
	        
	        // 标准煤百分比
	        elec.put(percentKey, eiReportManager.div(kwhStandardCoal, count));
	        water.put(percentKey, eiReportManager.div(waterStandardCoal, count));
	        flow.put(percentKey, eiReportManager.div(flowStandardCoal, count));
	        oil.put(percentKey, eiReportManager.div(eiReportManager.sum(gasolineStandardCoal, diesolOilStandardCoal), count));
	        
	        elec.put(key, ((Map) elec.get(key)).get("MEASURE"));
	        water.put(key, ((Map) water.get(key)).get("MEASURE"));
	        flow.put(key, ((Map) flow.get(key)).get("MEASURE"));
	        
	        oil.put(key, eiReportManager.sum(Double.parseDouble(((Map) oil.get(key)).get("MEASURE").toString()), 
	        		Double.parseDouble(((Map) oil.get(key)).get("MEASURE_HEAT").toString())));
		}
		
		Map result = new HashMap();
		
		result.put("elec", elec);
		result.put("water", water);
		result.put("flow", flow);
		result.put("oil", oil);
		result.put("total", total);
		
		return result;
	}

	/**
	 * 获取前一个小时的能耗数据
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private HashMap<String, Double> getPreHourEnergy() {
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

		HashMap<String, Double> result = (HashMap<String, Double>) eiReportManager
				.getGeneralReport(parameterEiReport,false);

		return result;
	}

	/**
	 * 获取前一天的能耗数据
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private HashMap<String, Double> getPreDayEnergy() {
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

		HashMap<String, Double> result = (HashMap<String, Double>) eiReportManager
				.getGeneralReport(parameterEiReport,false);

		return result;
	}

	/**
	 * 获取前一个月的能耗数据
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private HashMap<String, Double> getPreMonthEnergy() {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间
		calendar.add(Calendar.MONTH, -1); // 得到前一月

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // 月份需要加1

		EiReport parameterEiReport = new EiReport();
		parameterEiReport.setYear(year);
		parameterEiReport.setMonth(month);
		parameterEiReport.setQueryTimeType(1);

		HashMap<String, Double> result = (HashMap<String, Double>) eiReportManager
				.getGeneralReport(parameterEiReport,true);

		return result;
	}

	/**
	 * 获取当年的能耗数据
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private HashMap<String, Double> getCurrentYearEnergy() {
		Calendar calendar = Calendar.getInstance();// 获取系统当前时间

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		EiReport parameterEiReport = new EiReport();
		parameterEiReport.setYear(year);
		parameterEiReport.setQueryTimeType(0);

		HashMap<String, Double> result = (HashMap<String, Double>) eiReportManager
				.getGeneralReport(parameterEiReport,true);
		
		//需要叠加没有进入月历史表的数据
		parameterEiReport.setMonth(month);
		parameterEiReport.setQueryTimeType(1);
		HashMap<String, Double> result2 = (HashMap<String, Double>) eiReportManager
        .getGeneralReport(parameterEiReport,false);
		
		Iterator<Entry<String, Double>> iterator = result.entrySet().iterator();
		while(iterator.hasNext())
		{
		    Entry<String, Double> entry = iterator.next();
		    if(result2.containsKey(entry.getKey()))
		    {
		        entry.setValue(EIUtils.add(entry.getValue(), result2.get(entry.getKey())));
		    }
		}
		return result;
	}

	/**
	 * 获取前一年能耗数据
	 * 
	 * @return
	 */
	private HashMap<String, Double> getPreYearEnergy() {

		Calendar calendar = Calendar.getInstance();// 获取系统当前时间

		int year = calendar.get(Calendar.YEAR) - 1;

		List<Integer> yearsList = new ArrayList<Integer>();
		yearsList.add(year);

		List<EiHistoryConsume> result = eiReportManager
				.queryHistoryBuildingInfo(yearsList);

		HashMap<String, Double> resultMap = new HashMap<String, Double>();

		if (result == null || result.size() == 0) {
			// 没有查到记录
			resultMap.put(EIConstrants.WATER_MEASURE, 0.0);
			resultMap.put(EIConstrants.KEY_WATER_TO_STANDARD_COAL, 0.0);

			// 气
			// 消耗
			resultMap.put(EIConstrants.FLOW_MEASURE_HEAT, 0.0);
			resultMap.put(EIConstrants.FLOW_MEASURE_FLOW, 0.0);
			// 标准煤
			resultMap.put(EIConstrants.KEY_HEATING_POWER_TO_STANDARD_COAL, 0.0);

			// 电
			// 消耗
			resultMap.put(EIConstrants.KWH_MEASURE, 0.0);
			// 标准煤
			resultMap.put(EIConstrants.KEY_POWER_TO_STANDARD_COAL, 0.0);

		} else {
			for (EiHistoryConsume eiHistoryConsume : result) {
				if (eiHistoryConsume.getConsume_type() == 1) {
					// 水
					resultMap.put(EIConstrants.WATER_MEASURE,
							eiHistoryConsume.getMeasure());
					// 水的标准煤（千克标准煤）
					resultMap.put(EIConstrants.KEY_WATER_TO_STANDARD_COAL,
							EIUtils.mul(eiHistoryConsume.getMeasure(),
									EIConstrants.WATER_TO_STANDARD_COAL));
				} else if (eiHistoryConsume.getConsume_type() == 2) {
					// 气
					// 消耗
					resultMap.put(EIConstrants.FLOW_MEASURE_HEAT,
							eiHistoryConsume.getMeasure_heat());
					resultMap.put(EIConstrants.FLOW_MEASURE_FLOW,
							eiHistoryConsume.getMeasure());
					// 气的标准煤（千克标准煤）
					resultMap
							.put(EIConstrants.KEY_HEATING_POWER_TO_STANDARD_COAL,
									EIUtils.mul(
											eiHistoryConsume.getMeasure_heat(),
											EIConstrants.HEATING_POWER_TO_STANDARD_COAL));
				} else if (eiHistoryConsume.getConsume_type() == 3) {
					// 电
					// 消耗
					resultMap.put(EIConstrants.KWH_MEASURE,
							eiHistoryConsume.getMeasure());
					// 电的标准煤（千克标准煤）
					resultMap.put(EIConstrants.KEY_POWER_TO_STANDARD_COAL,
							EIUtils.mul(eiHistoryConsume.getMeasure(),
									EIConstrants.POWER_TO_STANDARD_COAL));
				}
			}
		}

		// 计算能耗百分比
		Double kwhCoal = (Double) resultMap
				.get(EIConstrants.KEY_POWER_TO_STANDARD_COAL);
		Double heatCoal = (Double) resultMap
				.get(EIConstrants.KEY_HEATING_POWER_TO_STANDARD_COAL);
		Double waterCoal = (Double) resultMap
				.get(EIConstrants.KEY_WATER_TO_STANDARD_COAL);
		if (kwhCoal != null && heatCoal != null && waterCoal != null) {
			// 总标准煤(千克标准煤)
			Double count = EIUtils
					.add((Double) resultMap
							.get(EIConstrants.KEY_POWER_TO_STANDARD_COAL),
							(Double) resultMap
									.get(EIConstrants.KEY_HEATING_POWER_TO_STANDARD_COAL),
							(Double) resultMap
									.get(EIConstrants.KEY_WATER_TO_STANDARD_COAL));

			// 计算总能耗（Kwh）
			// 总能耗
			resultMap.put(EIConstrants.TOTAL_MEASURE,
					EIUtils.div(count, EIConstrants.POWER_TO_STANDARD_COAL));

			// 防止分母为0的情况
			if (count == 0) {
				count = 1d;
			}

			// 标准煤百分比
			resultMap.put(EIConstrants.FLOW_PERCENTAGE, EIUtils.div(
					(Double) resultMap.get("HEATING_POWER_TO_STANDARD_COAL"),
					count));
			resultMap.put(EIConstrants.WATER_PERCENTAGE, EIUtils.div(
					(Double) resultMap.get("WATER_TO_STANDARD_COAL"), count));
			resultMap.put(EIConstrants.KWH_PERCENTAGE, EIUtils.div(
					(Double) resultMap.get("POWER_TO_STANDARD_COAL"), count));
		} else {
			// 总能耗
			resultMap.put(EIConstrants.TOTAL_MEASURE, 0.0);

			// 标准煤百分比
			resultMap.put(EIConstrants.FLOW_PERCENTAGE, 0.0);
			resultMap.put(EIConstrants.WATER_PERCENTAGE, 0.0);
			resultMap.put(EIConstrants.KWH_PERCENTAGE, 0.0);
		}

		return resultMap;
	}



}
