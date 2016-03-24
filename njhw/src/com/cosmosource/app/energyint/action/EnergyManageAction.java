package com.cosmosource.app.energyint.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cosmosource.app.energyint.model.EiHistoryConsume;
import com.cosmosource.app.energyint.model.EiReport;
import com.cosmosource.app.energyint.model.MyEnergyChartModel;
import com.cosmosource.app.energyint.model.MyEnergyContributionModel;
import com.cosmosource.app.energyint.model.RoomEnergyChartModel;
import com.cosmosource.app.energyint.service.BuildingEnergyDetailManager;
import com.cosmosource.app.energyint.service.BuildingEnergyHistoryManager;
import com.cosmosource.app.energyint.service.EIConstrants;
import com.cosmosource.app.energyint.service.EiReportManager;
import com.cosmosource.app.energyint.service.EnergyManageManager;
import com.cosmosource.app.energyint.service.MyEnergyContributionManager;
import com.cosmosource.app.energyint.service.OrgEnergyDetailManager;
import com.cosmosource.app.energyint.service.OrgEnergyHistoryManager;
import com.cosmosource.app.energyint.service.RoomEnergyManager;
import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Org;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/**
 * @description: 能耗管理首页
 * @author sqs
 * @date 2013-03-31
 */
@SuppressWarnings("unchecked")
public class EnergyManageAction extends BaseAction {

	private static final long serialVersionUID = 9109148287490943928L;

	private EmOrgRes emOrgRes;
	private EnergyManageManager energyManageManager;
	private MyEnergyContributionManager myEnergyContributionManager;
	private RoomEnergyManager roomEnergyManager;
	private BuildingEnergyDetailManager buildingEnergyDetailManager;
	private BuildingEnergyHistoryManager buildingEnergyHistoryManager;
	private OrgEnergyDetailManager orgEnergyDetailManager;
	private OrgEnergyHistoryManager orgEnergyHistoryManager;
	private EiReportManager eiReportManager;

	/**
	 * @title: initEnergy
	 * @description: 初始化
	 * @author sqs
	 * @date 2013-03-31
	 */

	public String initEnergy() {
		Map energyOpt = new LinkedHashMap();
		
		for (int i = -1 ; i > -13 ; i--) {
			Calendar calendar = Calendar.getInstance();// 获取系统当前时间
			calendar.add(Calendar.MONTH, i); // 得到前一月

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			
			String key = year + "-" + month;
			
			energyOpt.put(i + "", key);
		}
		
		energyOpt.put(EIConstrants.TIME_TYPE_CURRENT_YEAR, "当年累积");
		getRequest().setAttribute("energyOpt", energyOpt);
		
		HashMap<String, Double> energyDetailCurrentYear = buildingEnergyDetailManager
		.getEnergyDetail(EIConstrants.TIME_TYPE_CURRENT_YEAR);
		
		Map m = buildingEnergyDetailManager.getEnergyDetailList();
		
		Map m1 = (Map) m.get("elec");
		Map m2 = (Map) m.get("flow");
		Map m3 = (Map) m.get("water");
		Map m4 = (Map) m.get("oil");
		Map m5 = (Map) m.get("total");
		
		m1.put(EIConstrants.TIME_TYPE_CURRENT_YEAR, energyDetailCurrentYear.get("KWH_MEASURE"));
		m1.put(EIConstrants.TIME_TYPE_CURRENT_YEAR+"per", energyDetailCurrentYear.get(EIConstrants.KWH_PERCENTAGE));
		m2.put(EIConstrants.TIME_TYPE_CURRENT_YEAR, energyDetailCurrentYear.get("FLOW_MEASURE_FLOW"));
		m2.put(EIConstrants.TIME_TYPE_CURRENT_YEAR+"per", energyDetailCurrentYear.get(EIConstrants.FLOW_PERCENTAGE));
		m3.put(EIConstrants.TIME_TYPE_CURRENT_YEAR, energyDetailCurrentYear.get("WATER_MEASURE"));
		m3.put(EIConstrants.TIME_TYPE_CURRENT_YEAR+"per", energyDetailCurrentYear.get(EIConstrants.WATER_PERCENTAGE));
		m4.put(EIConstrants.TIME_TYPE_CURRENT_YEAR, energyDetailCurrentYear.get("OIL_MEASURE"));
		m4.put(EIConstrants.TIME_TYPE_CURRENT_YEAR+"per", energyDetailCurrentYear.get(EIConstrants.OIL_PERCENTAGE));
		m5.put(EIConstrants.TIME_TYPE_CURRENT_YEAR, energyDetailCurrentYear.get("TOTAL_MEASURE"));
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("kwhMeasure", m1);
		request.setAttribute("flowMeasureFlow", m2);
		request.setAttribute("waterMeasure", m3);
		request.setAttribute("oilMeasure", m4);
		request.setAttribute("totalMeasure", m5);
		return SUCCESS;
	}
	
	/**
	 * @title: queryOilEnergyInput
	 * @description: 获取油耗信息
	 * @author hj
	 * @date 2013-09-24
	 */
	public String queryOilEnergyInput() {
		Map energyOpt = new HashMap();
		
		for (int i = -3 ; i < 0 ; i++) {
			Calendar calendar = Calendar.getInstance();// 获取系统当前时间
			calendar.add(Calendar.MONTH, i); // 得到前一月

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			
			String value = year + "-" + month;
			
			energyOpt.put(i + "", value);
		}
		
		getRequest().setAttribute("energyOpt", energyOpt);
		
		
		List l = buildingEnergyDetailManager.queryOilEnergyInput();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("result", l);
		return SUCCESS;
	}
	
	/**
	 * @title: queryOilEnergyInputSave
	 * @description: 油耗信息保存
	 * @author hj
	 * @date 2013-09-24
	 */
	public void queryOilEnergyInputSave() {
		List<Org> lo = buildingEnergyDetailManager.findByHQL("from Org where PId = '2'");
		
		if (lo != null && lo.size() > 0) {
			for (Org o : lo) {
				// 该部门3个月前的油耗
				String oilG_3 = getParameter("oilG_" + o.getOrgId() + "_3");
				String oilD_3 = getParameter("oilD_" + o.getOrgId() + "_3");
				buildingEnergyDetailManager.insertOilMonthInfo(o.getOrgId(), oilG_3, oilD_3, 3);
				
				// 该部门2个月前的油耗
				String oilG_2 = getParameter("oilG_" + o.getOrgId() + "_2");
				String oilD_2 = getParameter("oilD_" + o.getOrgId() + "_2");
				buildingEnergyDetailManager.insertOilMonthInfo(o.getOrgId(), oilG_2, oilD_2, 2);
				
				// 该部门1个月前的油耗
				String oilG_1 = getParameter("oilG_" + o.getOrgId() + "_1");
				String oilD_1 = getParameter("oilD_" + o.getOrgId() + "_1");
				buildingEnergyDetailManager.insertOilMonthInfo(o.getOrgId(), oilG_1, oilD_1, 1);
			}
		}
	}

	/**
	 * @title: energyAnalysis
	 * @description: 能耗分析
	 * @author zh
	 * @date 2013-03-31
	 */
	public String energyAnalysis() {
		return SUCCESS;
	}

	/**
	 * @title: loadSWData
	 * @description: 加载三维数据
	 * @author zh
	 * @date 2013-04-21
	 */
	public String loadSWData() {
		JSONObject json = new JSONObject();
		String callBackFun = getParameter("callback");
		try {
			long orgId = 0; // Long.parseLong(getParameter("orgId"))
			// getParameter("energy")
			String energy = new String(getParameter("energy").getBytes(
					"ISO-8859-1"), "utf-8");
			List swData = energyManageManager.loadSWData(energy, orgId);

			json.put("swData", swData);
			json.put("status", "1");
			json.put("energy", energy);
			callBackFun = callBackFun + "(" + json.toString() + ")";
			Struts2Util.renderJson(callBackFun, "encoding:UTF-8",
					"no-cache:true", "content-type:application/json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @title: energyContribution
	 * @description: 节能贡献
	 * @author lwl
	 * @date 2013-07-04
	 */
	public String energyContribution() {
		return SUCCESS;
	}

	/**
	 * @title: energyContribution
	 * @description: 节能贡献(ajax返回json数据用)
	 * @author lwl
	 * @date 2013-07-04
	 */
	public String energyContributionJson() {
		String dayType = getParameter("dayType").toString();
		String[] dayArray = dayArraryOfCurrentMonth(dayType);

		String userId = Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString();
		myEnergyContributionManager.setUserid(userId);
		MyEnergyContributionModel model = myEnergyContributionManager
				.getMyEnergyContributionModel();
		MyEnergyChartModel chartModel = myEnergyContributionManager
				.getMyEnergyChartModel();
		HashMap<Integer, Double> buildingEverydayEnergy = chartModel
				.getBuildingEverydayEnergy();
		HashMap<Integer, Double> myEverydayEnergy = chartModel
				.getMyEverydayEnergy();
		HashMap<Integer, Double> buildingEverydayKwh = chartModel
				.getBuildingEverydayKwh();
		HashMap<Integer, Double> myEverydayKwh = chartModel.getMyEverydayKwh();

		// buildingEverydayEnergy和myEverydayEnergy长度一致
		double[] buildingArray = new double[buildingEverydayEnergy.size()];
		Iterator<Entry<Integer, Double>> iter1 = buildingEverydayEnergy
				.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<Integer, Double> entry = iter1.next();
			buildingArray[entry.getKey() - 1] = doubleFormat(entry.getValue());
		}

		double[] myArray = new double[myEverydayEnergy.size()];
		Iterator<Entry<Integer, Double>> iter2 = myEverydayEnergy.entrySet()
				.iterator();
		while (iter2.hasNext()) {
			Map.Entry<Integer, Double> entry = iter2.next();
			myArray[entry.getKey() - 1] = doubleFormat(entry.getValue());
		}

		double[] buildingKwhArray = new double[buildingEverydayKwh.size()];
		Iterator<Entry<Integer, Double>> iter3 = buildingEverydayKwh.entrySet()
				.iterator();
		while (iter3.hasNext()) {
			Map.Entry<Integer, Double> entry = iter3.next();
			buildingKwhArray[entry.getKey() - 1] = doubleFormat(entry
					.getValue());
		}

		double[] myKwhArray = new double[myEverydayKwh.size()];
		Iterator<Entry<Integer, Double>> iter4 = myEverydayKwh.entrySet()
				.iterator();
		while (iter4.hasNext()) {
			Map.Entry<Integer, Double> entry = iter4.next();
			myKwhArray[entry.getKey() - 1] = doubleFormat(entry.getValue());
		}

		JSONObject json = new JSONObject();
		try {
			json.put("buildingArray", buildingArray);
			json.put("myArray", myArray);
			json.put("buildingKwhArray", buildingKwhArray);
			json.put("myKwhArray", myKwhArray);

			json.put("dayArray", dayArray);
			json.put("xAxisMax", String.valueOf(dayArray.length - 1));
			// 大厦本月人均能耗
			json.put("averageEnergy",
					doubleFormat(model.getTomonthConsumePerson()));
			// 本月能耗
			json.put("energyCurrentMonth",
					doubleFormat(model.getTomonthConsume()));
			// 今日能耗
			json.put("energyToday", doubleFormat(model.getTodayConsume()));
			// 本月节能贡献
			json.put("energyContribution",
					doubleFormat(model.getTomonthConsumeContirbution()));
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		logger.info("energyContributionJson :" + json.toString());
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 获取当前房间能耗(ajax返回json数据用)
	 * 
	 * @return
	 */
	public String currentRoomEnergyJson() {
		String[] dayArray = dayArraryOfCurrentMonthRoom("false");
		roomEnergyManager.setRoomId(getParameter("roomId").toString());

		RoomEnergyChartModel roomEnergyChartModel = roomEnergyManager
				.getRoomEverydayEnergy();
		HashMap<Integer, Double> roomEverydayKwh = roomEnergyChartModel
				.getRoomEverydayKwh();
		double[] roomEverydayKwhArray = new double[roomEverydayKwh.size()];
		Iterator<Entry<Integer, Double>> iter1 = roomEverydayKwh.entrySet()
				.iterator();
		while (iter1.hasNext()) {
			Map.Entry<Integer, Double> entry = iter1.next();
			roomEverydayKwhArray[entry.getKey() - 1] = doubleFormat(entry
					.getValue());
		}

		JSONObject json = new JSONObject();
		try {
			json.put("roomEverydayKwhArray", roomEverydayKwhArray);
			json.put("dayArray", dayArray);
			json.put("xAxisMax", String.valueOf(dayArray.length - 1));
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		logger.info("currentRoomEnergyJson :" + json.toString());
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 能耗大厦历史查询
	 * 
	 * @return
	 */
	public String buildingEnergyHistory() {
		return SUCCESS;
	}

	/**
	 * 能耗大厦历史查询(ajax返回json数据用)
	 * 
	 * @return
	 */
	public String buildingEnergyHistoryJson() {
		String energyType = getParameter("energyType").toString();
		if (EIConstrants.ENERGY_ALL.equals(energyType)) {
			energyType = EIConstrants.TOTAL_MEASURE;
		} else if (EIConstrants.ENERGY_KWH.equals(energyType)) {
			energyType = EIConstrants.KWH_MEASURE;
		} else if (EIConstrants.ENERGY_WATER.equals(energyType)) {
			energyType = EIConstrants.WATER_MEASURE;
		} else if (EIConstrants.ENERGY_OIL.endsWith(energyType)) {
			energyType = EIConstrants.OIL_MEASURE;
		} else {
			energyType = EIConstrants.FLOW_MEASURE_FLOW;
		}
		String yearMonth = getParameter("yearmonth").toString();// 1:年 2：月
		Object[] yearArray = null;
		double[] energyArray = null;
		if ("1".equals(yearMonth)) {
			List<Integer> yearList = yearList();
			yearArray = new Integer[5];
			yearList.toArray(yearArray);
			energyArray = new double[5];
			HashMap<String, LinkedHashMap<Integer, Double>> history = buildingEnergyHistoryManager
					.getBuildingEnergyHistory();
			if (history != null) {
				LinkedHashMap<Integer, Double> map = history.get(energyType);
				for (int j = 0; j < yearArray.length; j++) {
					energyArray[j] = doubleFormat(map.get(yearArray[j]));
				}
			}
		} else {
			yearArray = new String[12];
			energyArray = new double[12];
			
			for (int i = 0; i < 12; i++) {
				int amount = i - 12;
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, amount);
				int month = calendar.get(Calendar.MONTH) + 1;
				int year = calendar.get(Calendar.YEAR);
				yearArray[i] = year+"/"+month;
				EiReport parameterEiReport = new EiReport();
				parameterEiReport.setYear(year);
				parameterEiReport.setMonth(month);
				parameterEiReport.setQueryTimeType(1);
				HashMap<String, Double> result = (HashMap<String, Double>) eiReportManager
						.getGeneralReport(parameterEiReport,true);
				if (result != null && result.size() != 0) {
					energyArray[i] = doubleFormat(result.get(energyType));
				}
			}
		}
		JSONObject json = new JSONObject();
		try {
			json.put("yearArray", yearArray);
			json.put("energyArray", energyArray);
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		logger.info("buildingEnergyHistoryJson :" + json.toString());
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 按单位历史查看
	 * 
	 * @return
	 */
	public String bureauEnergyHistory() {
		List<Integer> yearList = yearList();
		List<EiHistoryConsume> orgHistroyList = eiReportManager
				.queryHistoryOrganInfo(yearList);
         if(null == orgHistroyList || orgHistroyList.size()==0)
		{
        	 orgHistroyList = eiReportManager
				.queryHistoryOrganInfoNull();
		}
		List<String> bureauList = new ArrayList<String>();

		for (int i = 0; i < orgHistroyList.size(); i++) {
			EiHistoryConsume consume = orgHistroyList.get(i);
			if (!bureauList.contains(consume.getOrg_name().trim())) {
				bureauList.add(consume.getOrg_name().trim());
			}
		}
		String[] bureauArray = new String[bureauList.size()];
		if(bureauList.isEmpty())
		{
		    bureauArray = new String[]{"暂无数据"};
		}
		else
		{
		    bureauArray = bureauList.toArray(bureauArray);
		}
		// 获取部门列表
		ServletActionContext.getRequest().setAttribute("bureauArray",bureauArray);

		return SUCCESS;
	}

	/**
	 * 按单位历史查看(ajax返回json数据用)
	 * 
	 * @return
	 */
	public String bureauEnergyHistoryJson() {
		JSONObject json = new JSONObject();
		String bureau = getParameter("bureau").toString();
		String energyType = getParameter("energyType").toString();
		String yearMonth = getParameter("yearmonth").toString();// 1:年 2：月
		Object[] yearArray = null;
		double[] energyArray = null;
		if ("1".equals(yearMonth)) {
			List<Integer> yearList = yearList();
			List<EiHistoryConsume> orgHistroyList = eiReportManager
					.queryHistoryOrganInfo(yearList);

			yearArray = new Integer[5];
			yearList.toArray(yearArray);

			energyArray = new double[5];
			LinkedHashMap<Integer, Double> map = new LinkedHashMap<Integer, Double>();
			if (orgHistroyList != null) {
				// EiHistoryConsume consume_type 1：水；2：气；3：电
				// 页面 1：总能耗 2：总电耗 3：总水耗 4：总气耗
				int tempEnergyType = 5;
				if (EIConstrants.ENERGY_KWH.equals(energyType)) {
					tempEnergyType = 3;
				} else if (EIConstrants.ENERGY_WATER.equals(energyType)) {
					tempEnergyType = 1;
				} else if (EIConstrants.ENERGY_FLOW.equals(energyType)) {
					tempEnergyType = 2;
				} else if (EIConstrants.ENERGY_OIL.equals(energyType)) {
					tempEnergyType = 4;
				}
				if (tempEnergyType == 5) {
					// 算总能耗
					for (int i = 0; i < orgHistroyList.size(); i++) {
						EiHistoryConsume consume = orgHistroyList.get(i);
						if (bureau.equals(consume.getOrg_name().trim())) {
							int tempYear = consume.getHistory_year();
							if (map.containsKey(tempYear)) {
								double sum = new BigDecimal(String.valueOf(map
										.get(tempYear))).add(
										countAllEnergy(consume)).doubleValue();
								map.put(tempYear, sum);
							} else {
								map.put(tempYear, countAllEnergy(consume)
										.doubleValue());
							}
						}
					}

				} else {
					for (int i = 0; i < orgHistroyList.size(); i++) {
						EiHistoryConsume consume = orgHistroyList.get(i);
						if (bureau.equals(consume.getOrg_name().trim())
								&& consume.getConsume_type() == tempEnergyType) {
							map.put(consume.getHistory_year(),
									consume.getMeasure());
						}
					}
				}
			}
			for (int i = 0; i < yearArray.length; i++) {
				if (map.containsKey(yearArray[i])) {
					energyArray[i] = doubleFormat(map.get(yearArray[i]));
				}
			}
		} else {
			String tempEnergyType = EIConstrants.TOTAL_MEASURE;
			if (EIConstrants.ENERGY_KWH.equals(energyType)) {
				tempEnergyType = EIConstrants.KWH_MEASURE;
			} else if (EIConstrants.ENERGY_WATER.equals(energyType)) {
				tempEnergyType = EIConstrants.WATER_MEASURE;
			} else if (EIConstrants.ENERGY_FLOW.equals(energyType)) {
				tempEnergyType = EIConstrants.FLOW_MEASURE_FLOW;
			} else if (EIConstrants.ENERGY_OIL.equals(energyType)) {
				tempEnergyType = EIConstrants.OIL_MEASURE;
			}
			
			yearArray = new String[12];
			energyArray = new double[12];
			
			for (int i = 0; i < 12; i++) {
				int amount = i - 12;
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, amount);
				int month = calendar.get(Calendar.MONTH) + 1;
				int year = calendar.get(Calendar.YEAR);
				yearArray[i] = year+"/"+month;
				HashMap<String, LinkedHashMap<String, Double>> bureauAllEnergyMap = orgEnergyHistoryManager
						.getPreMonthEnergy(year, month);
				if (bureauAllEnergyMap != null) {
					LinkedHashMap<String, Double> bureauEnergyMap = bureauAllEnergyMap
							.get(tempEnergyType);
					if(null != bureauEnergyMap.get(bureau))
					{
					    energyArray[i] = doubleFormat(bureauEnergyMap
                            .get(bureau));
					}
					else
					{
					    energyArray[i] = 0;
					}
					
				}
			}
		}

		
		try {
			json.put("yearArray", yearArray);
			json.put("energyArray", energyArray);
		
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		logger.info("bureauEnergyHistoryJson :" + json.toString());
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 能耗监控首页column图数据获取（ajax返回json数据用）
	 * 
	 * @return
	 */
	public String energyMonitorColumnJson() {
		String columnEnergyType = getParameter("columnEnergyType").toString();
		String columnTimeType = getParameter("columnTimeType").toString();
		HashMap<String, LinkedHashMap<String, Double>> orgAllEnergyMap = orgEnergyDetailManager
				.getEnergyDetail(columnTimeType);

		String[] bureauArray = null;
		double[] columnArray = null;
		double[] lineArray = null;
		if (orgAllEnergyMap != null) {
			String key = EIConstrants.TOTAL_MEASURE;
			if (EIConstrants.ENERGY_KWH.endsWith(columnEnergyType)) {
				key = EIConstrants.KWH_MEASURE;
			} else if (EIConstrants.ENERGY_WATER.endsWith(columnEnergyType)) {
				key = EIConstrants.WATER_MEASURE;
			} else if (EIConstrants.ENERGY_FLOW.endsWith(columnEnergyType)) {
				key = EIConstrants.FLOW_MEASURE_FLOW;
			}
			LinkedHashMap<String, Double> orgEnergyMap = orgAllEnergyMap
					.get(key);

			Iterator<Entry<String, Double>> it = orgEnergyMap.entrySet()
					.iterator();
			BigDecimal sum = new BigDecimal(String.valueOf("0.0"));
			while (it.hasNext()) {
				Map.Entry<String, Double> entry = it.next();
				sum = sum.add(new BigDecimal(String.valueOf(entry.getValue())));
			}

			bureauArray = new String[orgEnergyMap.size()];
			columnArray = new double[orgEnergyMap.size()];
			lineArray = new double[orgEnergyMap.size()];
			int i = 0;
			it = orgEnergyMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Double> entry = it.next();
				bureauArray[i] = entry.getKey().trim();
				double temp = doubleFormat(entry.getValue());
				columnArray[i] = temp;
				lineArray[i] = countPercent(
						new BigDecimal(String.valueOf(entry.getValue()))
								.doubleValue(),
						sum.doubleValue());
				i++;
			}
		}
		JSONObject json = new JSONObject();
		try {
			json.put("bureauArray", bureauArray);
			json.put("columnArray", columnArray);
			json.put("lineArray", lineArray);
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		logger.info("energyMonitorColumnJson: " + json.toString());
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}
	
	/**
	 * 能耗监控首页column图数据获取（ajax返回json数据用）
	 * 
	 * @return
	 */
	public String energyMonitorJson() {
		String columnEnergyType = getParameter("columnEnergyType").toString();
		String columnTimeType = getParameter("columnTimeType").toString();
		HashMap<String, LinkedHashMap<String, Double>> orgAllEnergyMap = orgEnergyDetailManager
				.getEnergyMonitorDetail(columnTimeType);

		String[] bureauArray = null;
		double[] columnArray = null;
		double[] lineArray = null;
		if (orgAllEnergyMap != null) {
			String key = EIConstrants.TOTAL_MEASURE;
			if (EIConstrants.ENERGY_KWH.endsWith(columnEnergyType)) {
				key = EIConstrants.KWH_MEASURE;
			} else if (EIConstrants.ENERGY_WATER.endsWith(columnEnergyType)) {
				key = EIConstrants.WATER_MEASURE;
			} else if (EIConstrants.ENERGY_FLOW.endsWith(columnEnergyType)) {
				key = EIConstrants.FLOW_MEASURE_FLOW;
			} else if (EIConstrants.ENERGY_OIL.endsWith(columnEnergyType)) {
				key = EIConstrants.OIL_MEASURE;
			}
			LinkedHashMap<String, Double> orgEnergyMap = orgAllEnergyMap
					.get(key);

			Iterator<Entry<String, Double>> it = orgEnergyMap.entrySet()
					.iterator();
			BigDecimal sum = new BigDecimal(String.valueOf("0.0"));
			while (it.hasNext()) {
				Map.Entry<String, Double> entry = it.next();
				sum = sum.add(new BigDecimal(String.valueOf(entry.getValue())));
			}

			bureauArray = new String[orgEnergyMap.size()];
			columnArray = new double[orgEnergyMap.size()];
			lineArray = new double[orgEnergyMap.size()];
			int i = 0;
			it = orgEnergyMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Double> entry = it.next();
				bureauArray[i] = entry.getKey().trim();
				double temp = doubleFormat(entry.getValue());
				columnArray[i] = temp;
				lineArray[i] = countPercent(
						new BigDecimal(String.valueOf(entry.getValue()))
								.doubleValue(),
						sum.doubleValue());
				i++;
			}
		}
		JSONObject json = new JSONObject();
		try {
			json.put("bureauArray", bureauArray);
			json.put("columnArray", columnArray);
			json.put("lineArray", lineArray);
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		logger.info("energyMonitorColumnJson: " + json.toString());
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	@Override
	public EmOrgRes getModel() {
		// TODO Auto-generated method stub
		return emOrgRes;
	}

	@Override
	protected void prepareModel() throws Exception {
	}

	public EmOrgRes getEmOrgRes() {
		return emOrgRes;
	}

	public void setEmOrgRes(EmOrgRes emOrgRes) {
		this.emOrgRes = emOrgRes;
	}

	public EnergyManageManager getEnergyManageManager() {
		return energyManageManager;
	}

	public void setEnergyManageManager(EnergyManageManager energyManageManager) {
		this.energyManageManager = energyManageManager;
	}

	public MyEnergyContributionManager getMyEnergyContributionManager() {
		return myEnergyContributionManager;
	}

	public void setMyEnergyContributionManager(
			MyEnergyContributionManager myEnergyContributionManager) {
		this.myEnergyContributionManager = myEnergyContributionManager;
	}

	public RoomEnergyManager getRoomEnergyManager() {
		return roomEnergyManager;
	}

	public void setRoomEnergyManager(RoomEnergyManager roomEnergyManager) {
		this.roomEnergyManager = roomEnergyManager;
	}

	public BuildingEnergyDetailManager getBuildingEnergyDetailManager() {
		return buildingEnergyDetailManager;
	}

	public void setBuildingEnergyDetailManager(
			BuildingEnergyDetailManager buildingEnergyDetailManager) {
		this.buildingEnergyDetailManager = buildingEnergyDetailManager;
	}

	public BuildingEnergyHistoryManager getBuildingEnergyHistoryManager() {
		return buildingEnergyHistoryManager;
	}

	public void setBuildingEnergyHistoryManager(
			BuildingEnergyHistoryManager buildingEnergyHistoryManager) {
		this.buildingEnergyHistoryManager = buildingEnergyHistoryManager;
	}

	public OrgEnergyDetailManager getOrgEnergyDetailManager() {
		return orgEnergyDetailManager;
	}

	public void setOrgEnergyDetailManager(
			OrgEnergyDetailManager orgEnergyDetailManager) {
		this.orgEnergyDetailManager = orgEnergyDetailManager;
	}

	public EiReportManager getEiReportManager() {
		return eiReportManager;
	}

	public void setEiReportManager(EiReportManager eiReportManager) {
		this.eiReportManager = eiReportManager;
	}

	public OrgEnergyHistoryManager getOrgEnergyHistoryManager() {
		return orgEnergyHistoryManager;
	}

	public void setOrgEnergyHistoryManager(
			OrgEnergyHistoryManager orgEnergyHistoryManager) {
		this.orgEnergyHistoryManager = orgEnergyHistoryManager;
	}

	/**
	 * 格式化数据保留位小数
	 * 
	 * @param d
	 * @return
	 */
	private double doubleFormat(Object d) {
		return Double.parseDouble(new DecimalFormat("##0.0").format(d));
	}
	
	
	/**
	 * 获取
	 * 
	 * @param dayType
	 * @return
	 */
	private String[] dayArraryOfCurrentMonthRoom(String dayType) {
		Calendar calendar = Calendar.getInstance();
		int daysOfMonth = calendar.getActualMaximum(Calendar.DATE);
		String[] dayArray = new String[daysOfMonth];

		if ("false".equals(dayType)) {
			for (int i = 0; i < daysOfMonth; i++) {
				int day = i + 1;
				if (day % 5 == 0) {
					dayArray[i] = String.valueOf(day);
				} else {
					dayArray[i] = "";
				}
			}
		} else {
			for (int i = 0; i < daysOfMonth; i++) {
				dayArray[i] = String.valueOf(i + 1);
			}
		}
		return dayArray;
	}
	

	/**
	 * 获取
	 * 
	 * @param dayType
	 * @return
	 */
	private String[] dayArraryOfCurrentMonth(String dayType)
	{
		Calendar calendar = Calendar.getInstance();

		// 本月
		int month = calendar.get(Calendar.MONTH) + 1;

		// 上月一共多少天
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		time.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);// Calendar对象默认一月为0
		int lastDay = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数

		// 今天是本月的第几天
		int curday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		Map<Integer, String> totalMap = new TreeMap<Integer, String>();
       
		// 本月和上月累计放到map中
		for (int i = 1; i <= lastDay; i++)
		{
			totalMap.put(i, time.get(Calendar.MONTH)+1 + "." + i);
		}

		for (int i = 1; i <= curday; i++)
		{
			totalMap.put(i + lastDay, month + "." + i);
		}

		
		// 删掉多余的
		for (int i = 1; i <= curday + lastDay - 30; i++)
		{
			totalMap.remove(i);
		}

		String[] dayArray = null;
		// 特殊数据3.1号
		boolean isShort = curday + lastDay - 30 < 0;
		if (isShort)
		{
			Object s[] = totalMap.keySet().toArray();

			dayArray = new String[totalMap.size() + 1];
			dayArray[0] = "1.31";
			for (int i = 0; i < totalMap.size(); i++)
			{
				dayArray[i + 1] = totalMap.get(s[i]);
			}
		}
		else
		{
			Object s[] = totalMap.keySet().toArray();
			dayArray = new String[totalMap.size()];
			for (int i = 0; i < totalMap.size(); i++)
			{
				dayArray[i] = totalMap.get(s[i]);
			}
		}
		
		//小图
		if("false".equals(dayType) && null != dayArray)
		{
			for (int i = 0; i < dayArray.length; i++)
			{
				if (i%5!=0 && i!=dayArray.length-1)
				{
					dayArray[i] = "";
				}
			}
		}
		
		return dayArray;
}

	/**
	 * 计算百分比
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private double countPercent(double a, double b) {
		if (b != 0) {
			BigDecimal bda = new BigDecimal(Double.toString(a));
			BigDecimal bdb = new BigDecimal(Double.toString(b));
			BigDecimal bd100 = new BigDecimal(Double.toString(100));
			double percent = bda.multiply(bd100)
					.divide(bdb, 1, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (percent > 0 && percent < 0.1) {
				percent = 0.1;
			} else if (percent > 100) {
				percent = 100.0;
			}
			return percent;
		} else {
			return 0;
		}
	}

	/**
	 * 根据能源类型换算总能耗
	 * 
	 * @param consume
	 * @return
	 */
	private BigDecimal countAllEnergy(EiHistoryConsume consume) {
		// 1：水；2：气；3：电
		BigDecimal kwh = new BigDecimal(
				Double.toString(EIConstrants.POWER_TO_STANDARD_COAL));
		if (consume.getConsume_type() == 1) {
			BigDecimal bd = new BigDecimal(
					Double.toString(consume.getMeasure()));
			BigDecimal xx = new BigDecimal(
					Double.toString(EIConstrants.WATER_TO_STANDARD_COAL));
			return bd.multiply(xx).divide(kwh, 10, BigDecimal.ROUND_HALF_UP);
		} else if (consume.getConsume_type() == 2) {
			BigDecimal bd = new BigDecimal(Double.toString(consume
					.getMeasure_heat()));
			BigDecimal xx = new BigDecimal(
					Double.toString(EIConstrants.HEATING_POWER_TO_STANDARD_COAL));
			return bd.multiply(xx).divide(kwh, 10, BigDecimal.ROUND_HALF_UP);
		} else if (consume.getConsume_type() == 3) {
			BigDecimal bd = new BigDecimal(
					Double.toString(consume.getMeasure()));
			return bd;
		} else {
			return new BigDecimal("0.0");
		}
	}

	/**
	 * 获取历史查询的年份
	 * 
	 * @return
	 */
	private List<Integer> yearList() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		List<Integer> yearList = new ArrayList<Integer>();
		for (int years = year - 5; years <= year - 1; years++) {
			yearList.add(years);
		}
		return yearList;
	}
}
