package com.cosmosource.app.attendance.action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cosmosource.app.attendance.service.AttendancePieChartManager;
import com.cosmosource.app.attendance.service.NewCityAttendanceManager;
import com.cosmosource.app.attendance.util.AttendancePieChartUtil;
import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class AttendancePieChartAction extends BaseAction<Object> {

	private AttendancePieChartManager pieChartManager;
	private NewCityAttendanceManager newCityAttendanceManager;
	private long uid; //当前登录用户id
	private String org_id; //当前登录用户部门id 如:信息中心
	private Map paramsMap = new HashMap<String, Object>();

	/**
	 * Generate serivalVersionUID
	 */
	private static final long serialVersionUID = 8823759504317132859L;

	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录

	/**
	 * 获取考勤宏观统计数据
	 * 柱状图
	 */
	public void getAttendanceChartData(){
		
		uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		org_id = this.getRequest().getSession()
				.getAttribute(Constants.ORG_ID).toString();
		
//		uid = 1033;
//		org_id = "70";
		paramsMap.put("user_id",uid);
		paramsMap.put("top_org_id",org_id);
		
		
		String start_time = getRequest().getParameter("mastartTime");
		String end_time = getRequest().getParameter("maendTime");
		String type = getRequest().getParameter("type");
		
//		String start_time = null;
//		String end_time = null;
//		String type = "0";
		
		if (start_time == null || start_time.length() == 0) {
			start_time = DateUtil.getStrMonthFirstDay();
		}
		
		if (end_time == null || end_time.length() == 0) {
			end_time = DateUtils.getCurrentDateStr();
		}
		
		
		//处理时间格式，如果是相同的年月、只判定日期，提高查询效率
//		paramsMap = AttendancePieChartUtil.splitAttendanceTime(start_time,end_time,paramsMap);
		
		paramsMap.put("start_time", start_time.replaceAll("-", ""));
		paramsMap.put("end_time", end_time.replaceAll("-", ""));
		
		getRequest().setAttribute("mastartTime", start_time);
		getRequest().setAttribute("maendTime", end_time);
		
		int key = 2;
		if (type != null && type.length() != 0) {
			key = Integer.valueOf(type);
		}
		
		switch (key) {
		case 0:
			//大厦考勤宏观数据
			List<Map<String, Object>> buildingAttendanceList = pieChartManager.queryBuildingAttendancePieChart(paramsMap);
//			logger.info(AttendancePieChartUtil.buildingList2Json(buildingAttendanceList));
			Struts2Util.renderJson(AttendancePieChartUtil.buildingList2Json(buildingAttendanceList), "encoding:UTF-8", "no-cache:true");
			break;
			
		case 1:
			/**
			 * 单位考勤宏观数据
			 * 参数需要单位 top_org_id
			 */
			List<Map<String, Object>> unitAttendanceList = pieChartManager.queryUnitAttendancesList(paramsMap);
//			logger.info(AttendancePieChartUtil.list2JsonObject(unitAttendanceList));
			Struts2Util.renderJson(AttendancePieChartUtil.list2JsonObject(unitAttendanceList,true), "encoding:UTF-8", "no-cache:true");
			break;
			
		case 2:
			/**
			 * 部门考勤宏观数据
			 * 只需要饼图信息
			 * 需要 userid
			 */
			List<Map<String, Object>> deptAttendanceList = pieChartManager.queryDeptAttendanceList(paramsMap);
//			logger.info(AttendancePieChartUtil.list2JsonObject(deptAttendanceList));
			Struts2Util.renderJson(AttendancePieChartUtil.list2JsonObject(deptAttendanceList), "encoding:UTF-8", "no-cache:true");
			break;

		default:
			Struts2Util.renderJson("访问出错.....", "encoding:UTF-8", "no-cache:true");
			break;
		}
		
	}
	
	
	/**
	 *  获取考勤饼图数据
	 *  ['出勤',260.8],
		 ['迟到，早退',30.5],
		 ['病假，事假',80.5],
		 ['其他',60.2],
		 ['公出',70],
	 * 
	 */
	public void getAttendancePieChartData() throws Exception{
		
		String start_time = getRequest().getParameter("mastartTime");
		String end_time = getRequest().getParameter("maendTime");
		String type = getRequest().getParameter("type");
		
		if (start_time == null || start_time.length() == 0) {
			start_time = DateUtil.getStrMonthFirstDay();
		}
		
		if (end_time == null || end_time.length() == 0) {
			end_time = DateUtils.getCurrentDateStr();
		}
		
//		paramsMap.put("start_time", start_time);
//		paramsMap.put("end_time", end_time);
		
		//处理时间格式，表索引建在 年、月、日上，所以拆分开开比较
//		paramsMap = AttendancePieChartUtil.splitAttendanceTime(start_time,end_time,paramsMap);
		
		paramsMap.put("start_time", start_time.replaceAll("-", ""));
		paramsMap.put("end_time", end_time.replaceAll("-", ""));
		
		
		getRequest().setAttribute("mastartTime", start_time);
		getRequest().setAttribute("maendTime", end_time);
		
		uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		org_id = this.getRequest().getSession()
				.getAttribute(Constants.ORG_ID).toString();
		
		paramsMap.put("user_id",uid);
		paramsMap.put("top_org_id",org_id);
		
		int key = 3;
		if (type != null && type.length() != 0) {
			key = Integer.valueOf(type);
		}
		
		switch (key) {
		case 0:
			//大厦考勤宏观数据
			List<Map<String, Object>> buildingAttendanceList = pieChartManager.queryBuildingAttendancePieChart(paramsMap);
//			logger.info(AttendancePieChartUtil.buildingList2PieChartJson(buildingAttendanceList));
			Struts2Util.renderJson(AttendancePieChartUtil.buildingList2PieChartJson(buildingAttendanceList), "encoding:UTF-8", "no-cache:true");
			break;
			
		case 1:
			/**
			 * 单位考勤宏观数据
			 * 参数需要单位 top_org_id
			 */
			
			List<Map<String, Object>> unitAttendanceList = pieChartManager.queryUnitAttendancesPieChart(paramsMap);
			Struts2Util.renderJson(AttendancePieChartUtil.buildingList2PieChartJson(unitAttendanceList), "encoding:UTF-8", "no-cache:true");
			break;
			
		case 2:
			/**
			 * 部门考勤宏观数据
			 * 只需要饼图信息
			 * 需要 userid
			 */
			List<Map<String, Object>> deptAttendanceList = pieChartManager.queryDeptAttendanceList(paramsMap);
			Struts2Util.renderJson(AttendancePieChartUtil.list2PieChartJsonObject(deptAttendanceList), "encoding:UTF-8", "no-cache:true");
			break;

		default:
			Struts2Util.renderJson("访问出错.....", "encoding:UTF-8", "no-cache:true");
			break;
		}
		
	}
	
	
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public AttendancePieChartManager getPieChartManager() {
		return pieChartManager;
	}
	public void setPieChartManager(AttendancePieChartManager pieChartManager) {
		this.pieChartManager = pieChartManager;
	}
	public Page<HashMap> getPage() {
		return page;
	}
	public void setPage(Page<HashMap> page) {
		this.page = page;
	}
	public NewCityAttendanceManager getNewCityAttendanceManager() {
		return newCityAttendanceManager;
	}
	public void setNewCityAttendanceManager(
			NewCityAttendanceManager newCityAttendanceManager) {
		this.newCityAttendanceManager = newCityAttendanceManager;
	}
	public Map getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(Map paramsMap) {
		this.paramsMap = paramsMap;
	}
	
}
