package com.cosmosource.app.attendance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.attendance.util.AttendancePieChartUtil;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.DateUtil;

public class AttendancePieChartManager extends BaseManager {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryDeptAttendanceList(Map paramsMap) {
		List attendanceList = null;
		List finalList = new ArrayList<Map<String, Object>>();
		try {

			// 部门 ID 部门名称
			String org_id = null, org_name = null;
			int holidaysCount = 0;
			int empCount = 0;

			List orgList = sqlDao.findList("AttendanceSQL.getOrgIdByUserId",
					paramsMap);

			if (!orgList.isEmpty() && orgList.size() > 0) {
				org_id = ((Map) orgList.get(0)).get("ORG_ID").toString();
				org_name = ((Map) orgList.get(0)).get("ORG_NAME").toString();
				paramsMap.put("org_id", org_id);
			}

			// 获取部门下人员数目，计算考勤基数
			// 所有记录总数转换为 人次(单位)
			// 出勤天数 * 人员数目 * 2 (上午、下午)
			// 遍历 list 获取部门人员总数

			List countList = sqlDao.findList(
					"AttendancePieChartSQL.getEmpCountByOrgId", paramsMap);

			if (!countList.isEmpty() && countList.size() > 0) {
				empCount = Integer.valueOf(((Map) countList.get(0))
						.get("COUNT").toString());
			}

			// 获取当前选择时间范围之内的假日数目
			List holidaysCountList = sqlDao.findList(
					"AttendancePieChartSQL.getHolidaysCount", paramsMap);

			if (!holidaysCountList.isEmpty() && holidaysCountList.size() > 0) {
				holidaysCount = Integer
						.valueOf(((Map) holidaysCountList.get(0)).get("COUNT")
								.toString());
			}

			// 获取当前选择时间范围之内的间隔天数
			List diffDaysList = DateUtil.getDiffDaysList(
					paramsMap.get("start_time").toString(),
					paramsMap.get("end_time").toString(), "yyyyMMdd", false);

			int baseCount = (diffDaysList.size() - holidaysCount) * 2
					* empCount;

			attendanceList = sqlDao.findList(
					"AttendancePieChartSQL.getDeptAttendancesList", paramsMap);

			// 解析数据
			logger.info("查询到数据条目为： " + attendanceList.size());

			// 封装到返回给页面的page对象result中
			if (!attendanceList.isEmpty() && attendanceList.size() > 0) {

				finalList.add(AttendancePieChartUtil.resolveDeptAttendanceList(
						attendanceList, org_name, baseCount));

			}

		} catch (Exception e) {

			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage(),e);
			e.printStackTrace();
		}

		return finalList;
	}

	/**
	 * 查询单位所有考勤信息
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryUnitAttendancesList(Map paramMap) {
		List finalList = new LinkedList();
		List<Map<String, Object>> deptList = null;
		try {
			// 获取单位底下部门信息
			deptList = sqlDao.findList(
					"AttendancePieChartSQL.getDeptListByOrgId", paramMap);
			// 按部门获取考勤统计信息
			for (Map<String, Object> map : deptList) {
				paramMap.put("org_id", map.get("ORG_ID"));
				List attendanceList = sqlDao.findList(
						"AttendancePieChartSQL.getDeptAttendancesList",
						paramMap);

				if (!attendanceList.isEmpty() && attendanceList.size() > 0) {
					finalList.add(AttendancePieChartUtil
							.resolveDeptAttendanceList(attendanceList,
									map.get("ORG_NAME").toString(),
									attendanceList.size() * 2));
				}
			}

		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage(),e);
			e.printStackTrace();
		}
		return finalList;
	}
	
	
	/**
	 * 查询大厦所有单位考勤信息
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryBuildingAttendancePieChart(Map paramMap){
//		List finalList = new LinkedList();
		List<Map<String, Object>> unitList = null;
		try {
			
			unitList = sqlDao.findList("AttendancePieChartSQL.getBuildingAttandanceList", paramMap);
/*			// 获取大厦底下单位信息
			unitList = sqlDao.findList(
					"AttendancePieChartSQL.getUnitList", null);
			
			for (Map<String, Object> map : unitList) {
				paramMap.put("top_org_id", map.get("ORG_ID"));
				//获取单位考勤
				List<Map<String, Object>> list = this.queryUnitAttendancesList(paramMap);
				String org_name = map.get("ORG_NAME").toString();
				//遍历 list 合并部门考勤信息
				finalList.add(AttendancePieChartUtil.resoleUnitAttendanceList(list,org_name));
			}*/

		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage(),e);
			e.printStackTrace();
		}
		return unitList;
	}
	
	
	/**
	 * 单位考勤统计 包括单位底下部门人员跟直接挂在单位下面的人员  level_num = 2  
	 * @param paramsMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> queryUnitAttendancesPieChart(Map paramsMap) {

		List<Map<String, Object>> unitList = null;
		try {
			unitList = sqlDao.findList("AttendancePieChartSQL.getUnitAttandanceList", paramsMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage(),e);
			e.printStackTrace();
		}
		return unitList;
	}

}
