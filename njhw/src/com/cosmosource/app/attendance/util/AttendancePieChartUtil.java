package com.cosmosource.app.attendance.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.attendance.vo.Attendance;

public class AttendancePieChartUtil {

	protected static Logger logger = LoggerFactory
			.getLogger(AttendanceUtil.class);

	/**
	 * @param list
	 *            考勤记录
	 * @param org_name
	 *            部门ID
	 * @param baseCount
	 *            统计基数 出勤天数 * 人员数目 * 2 (上午、下午)
	 * @return Map
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map resolveDeptAttendanceList(List<Map<String, Object>> list,
			String org_name, int baseCount) {

		int lateCount = 0, leaveEarlyCount = 0; // 迟到、早退
		int normalCount = 0, absentCount = 0, sickLeave = 0; // 正常出勤、刷卡异常、病假
		int affairLeave = 0, businessLeave = 0, otherStatus = 0; // 事假、公出、其它

		// 统计部门总体出勤、早退、请假等状况
		for (Map<String, Object> tMap : list) {
			
//			logger.info(tMap.toString());
			// 把每个人的记录累加
			// 考勤状态:0:正常 1:迟到 2:刷卡异常 3:早退
			// 上午正常上班
			if ("0".equals(tMap.get("MORNING_STAT").toString()) &&
					"0".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
				normalCount += 1;
			}
			// 下午正常下班
			if ("0".equals(tMap.get("AFTERNOON_STAT").toString()) &&
					"0".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
				normalCount += 1;
			}
			// 迟到次数
			if ("1".equals(tMap.get("MORNING_STAT").toString())) {
				// 此记录是未处理,如果已处理则应该统计到请假类型 ATTEN_MORNING_TYPE 中
				if (!"2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					lateCount++;
				}
			}
			// 下午的记录为未处理，迟到
			if ("1".equals(tMap.get("AFTERNOON_STAT").toString())) {
				if (!"2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					lateCount++;
				}
			}
			// 上午刷卡异常，未处理状态
			if ("2".equals(tMap.get("MORNING_STAT").toString())) {
				if (!"2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					absentCount += 1;
				}
			}
			// 下午刷卡异常，未处理
			if ("2".equals(tMap.get("AFTERNOON_STAT").toString())) {
				if (!"2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					absentCount += 1;
				}
			}
			// 早退次数，未处理
			if ("3".equals(tMap.get("MORNING_STAT").toString())) {
				if (!"2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					leaveEarlyCount++;
				}
			}
			// 早退次数，未处理
			if ("3".equals(tMap.get("AFTERNOON_STAT").toString())) {
				if (!"2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					leaveEarlyCount++;
				}
			}

			// 请假统计
			// 上午异常类型 1:病假 2:事假 3:公出 4:其它
			Object obj = tMap.get("ATTEN_MORNING_TYPE");

			int morningType = 0;
			if (null != obj && !"".equals(obj)) {
				morningType = Integer.valueOf(obj.toString());

			}

			switch (morningType) {
			case 1:
				// 确保早上的记录是已处理过
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					sickLeave += 1;
				}
				break;
			case 2:
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					affairLeave += 1;
				}
				break;
			case 3:
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					businessLeave += 1;
				}
				break;
			case 4:
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					otherStatus += 1;
				}
				break;

			default:
				break;
			}

			// 下午午异常类型 1:病假 2:事假 3:公出 4:其它
			Object object = tMap.get("ATTEN_AFTERNOON_TYPE");
			int afternoonType = 0;
			if (null != object && !"".equals(object)) {
				afternoonType = Integer.valueOf(object.toString());

			}

			switch (afternoonType) {
			case 1:
				// 确保下午的记录是已经处理过的
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					sickLeave += 1;
				}
				break;
			case 2:
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					affairLeave += 1;
				}
				break;
			case 3:
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					businessLeave += 1;
				}
				break;
			case 4:
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					otherStatus += 1;
				}
				break;

			default:
				break;
			}

		}

		// 统计的结果放到 Map
		Map<String, Object> finalMap = new HashMap<String, Object>();
/*		
		//出勤比例,小数点留2位
		double normalPercent = Arith.div(normalCount, baseCount, 2);
		//刷卡异常比例,小数点留2位
		double absentPercent = Arith.div(absentCount, baseCount, 2);
		//迟到比例
		double latePercent = Arith.div(lateCount, baseCount, 2);
		//早退比例
		double leaveEarlyPercent = Arith.div(leaveEarlyCount, baseCount, 2);
		//病假比例
		double sickLeavePercent = Arith.div(sickLeave, baseCount, 2);
		//事假比例
		double affairLeavePercent = Arith.div(affairLeave, baseCount, 2);
		//公出比例
		double businessLeavePercent = Arith.div(businessLeave, baseCount, 2);
		//其它
		double otherPercent = Arith.div(otherStatus, baseCount, 2);
		
*/		
		finalMap.put("orgName", org_name); // 部门
		finalMap.put("baseCount", String.valueOf(baseCount)); // 基数
		finalMap.put("normalCount", String.valueOf(normalCount)); // 出勤总数
		finalMap.put("absentCount", String.valueOf(absentCount)); // 刷卡异常总数
		finalMap.put("lateCount", String.valueOf(lateCount)); // 迟到总数
		finalMap.put("leaveEarlyCount", String.valueOf(leaveEarlyCount)); // 早退总数
		finalMap.put("sickLeave", String.valueOf(sickLeave)); // 病假
		finalMap.put("affairLeave", String.valueOf(affairLeave)); // 事假
		finalMap.put("businessLeave", String.valueOf(businessLeave)); // 公出
		finalMap.put("otherStatus", String.valueOf(otherStatus)); // 其它总数

//		logger.info(finalMap.toString());

		return finalMap;

	}


	/**
	 * 解析合并单位底下部门考勤信息
	 * @param list
	 * @return map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> resoleUnitAttendanceList(
			List<Map<String, Object>> list,String org_name) {
		
		int lateCount = 0, leaveEarlyCount = 0; // 迟到、早退
		int normalCount = 0, absentCount = 0, sickLeave = 0; // 正常出勤、刷卡异常、病假
		int affairLeave = 0, businessLeave = 0, otherStatus = 0; // 事假、公出、其它
		int baseCount = 0;
		
		Map finalMap = new HashMap();
		for (Map<String, Object> map : list) {
			baseCount += Integer.valueOf(map.get("baseCount").toString()); // 基数
			lateCount += Integer.valueOf(map.get("lateCount").toString());  // 迟到总数
			normalCount += Integer.valueOf(map.get("normalCount").toString()); // 出勤总数
			absentCount += Integer.valueOf(map.get("absentCount").toString()); // 刷卡异常总数
			leaveEarlyCount += Integer.valueOf(map.get("leaveEarlyCount").toString()); // 早退总数
			sickLeave += Integer.valueOf(map.get("sickLeave").toString()); // 病假
			affairLeave += Integer.valueOf(map.get("affairLeave").toString()); // 事假
			businessLeave += Integer.valueOf(map.get("businessLeave").toString()); // 公出
			otherStatus += Integer.valueOf(map.get("otherStatus").toString()); // 其它
		}
		
		finalMap.put("orgName", org_name); // 单位
		finalMap.put("baseCount", String.valueOf(baseCount)); // 基数
		finalMap.put("normalCount", String.valueOf(normalCount)); // 出勤总数
		finalMap.put("absentCount", String.valueOf(absentCount)); // 刷卡异常总数
		finalMap.put("lateCount", String.valueOf(lateCount)); // 迟到总数
		finalMap.put("leaveEarlyCount", String.valueOf(leaveEarlyCount)); // 早退总数
		finalMap.put("sickLeave", String.valueOf(sickLeave)); // 病假
		finalMap.put("affairLeave", String.valueOf(affairLeave)); // 事假
		finalMap.put("businessLeave", String.valueOf(businessLeave)); // 公出
		finalMap.put("otherStatus", String.valueOf(otherStatus)); // 其它总数
//		logger.info(finalMap.toString());
		
		return finalMap;
	}
	
	
	
	/**
	 * 考勤list 解析成json数据
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  static String list2JsonObject(List<Map<String, Object>> list,boolean flag){
		
		/**
		 * 		this.orgName = orgName;
		this.normalCount = normalCount;
		this.totalCount = totalCount;
		this.sickAffairLeaveCount = sickAffairLeaveCount;
		this.absentCount = absentCount;
		this.lateLeaveEarlyCount = lateLeaveEarlyCount;
		this.otherCount = otherCount;
		 */
		
		
		List sortList = new ArrayList<Attendance>();
		//查询的map转换为 attendance 对象，实现排序
		for (Map<String, Object> map : list) {
			
			int totalCount = getAttendanceTotalCount(map,true);
			
			Attendance attendance = new Attendance(
					map.get("orgName").toString(), 
					Integer.valueOf(map.get("normalCount").toString()) + 
					Integer.valueOf(map.get("businessLeave").toString()), 
					totalCount,
					Integer.valueOf(map.get("affairLeave").toString()) + 
					Integer.valueOf(map.get("sickLeave").toString()),
					
					Integer.valueOf(map.get("absentCount").toString()),
					
					Integer.valueOf(map.get("lateCount").toString()) +
					Integer.valueOf(map.get("leaveEarlyCount").toString()), 
					
					Integer.valueOf(map.get("otherStatus").toString()));
			
			sortList.add(attendance);
		}
		
		//排序
		Collections.sort(sortList, new Attendance());
		
		
		JSONObject finalObject = new JSONObject();
		JSONArray finalArray = new JSONArray();
		JSONArray categoryArray = new JSONArray();
		
		JSONObject normalObject = new JSONObject();;
		normalObject.put("name", "出勤、公出");
		List normalList = new LinkedList();
		
		JSONObject absentObject = new JSONObject();;
		absentObject.put("name", "刷卡异常");
		List absentList = new LinkedList();
		
		JSONObject lateObject = new JSONObject();;
		lateObject.put("name", "迟到、早退");
		List lateList = new LinkedList();
		
		JSONObject affairLeaveObject = new JSONObject();;
		affairLeaveObject.put("name", "病假、事假");
		List affairLeaveList = new LinkedList();
		
		JSONObject otherStatusObject = new JSONObject();;
		otherStatusObject.put("name", "其它");
		List otherStatusList = new LinkedList();
		
		//解析排序之后的结果返回页面信息
		for (Object object : sortList) {
			Attendance attendance = (Attendance) object;
			/**
			 * {"categoryArray":["南京市地震局","南京市发展和改革委员会","南京市国有资产监督管理委员会"],
	 			"series":[{ name: '出勤、公出', data: [5, 3, 4, 7, 2]}, 
	 			{name: '刷卡异常', data: [2, 2, 3, 2, 1]}, 
	 			{name: '迟到,早退',data: [3, 4, 4, 2, 5]},
	 			{name: '病假,事假',data: [3, 4, 4, 2, 5]},
	 			{name: '其他',data: [3, 4, 4, 2, 5]}]
			 */
			
			//部门、单位列表
			categoryArray.add(attendance.getOrgName());
			
			//其它
			otherStatusList.add(attendance.getOtherCount());
			
			//刷卡异常
			absentList.add(attendance.getAbsentCount());
			
			//迟到、早退
			lateList.add(attendance.getLateLeaveEarlyCount());
			
			//病假、事假
			affairLeaveList.add(attendance.getSickAffairLeaveCount());
			
			//出勤、公出
			normalList.add(attendance.getNormalCount());
			
		}
		
		//单位名称
		finalObject.put("categoryArray", categoryArray);
		
		//其它
		otherStatusObject.put("data",otherStatusList);
		finalArray.add(otherStatusObject);
		
		//刷卡异常
		absentObject.put("data", absentList);
		finalArray.add(absentObject);
		
		//迟到、早退
		lateObject.put("data", lateList);
		finalArray.add(lateObject);
		
		//事假、病假
		affairLeaveObject.put("data",affairLeaveList );
		finalArray.add(affairLeaveObject);
		
		//出勤、公出
		normalObject.put("data", normalList);
		finalArray.add(normalObject);
		
		finalObject.put("series", finalArray);
		logger.info(finalObject.toString());
		
		return finalObject.toString();
	}
	
	/**
	 * 考勤list 解析成json数据
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  static String list2JsonObject(List<Map<String, Object>> list){
		
		/**
		 * {"categoryArray":["南京市地震局","南京市发展和改革委员会","南京市国有资产监督管理委员会"],
 			"series":[{ name: '出勤、公出', data: [5, 3, 4, 7, 2]}, 
 			{name: '刷卡异常', data: [2, 2, 3, 2, 1]}, 
 			{name: '迟到,早退',data: [3, 4, 4, 2, 5]},
 			{name: '病假,事假',data: [3, 4, 4, 2, 5]},
 			{name: '其他',data: [3, 4, 4, 2, 5]}]
		 */
		JSONObject finalObject = new JSONObject();
		JSONArray finalArray = new JSONArray();
		
		JSONArray categoryArray = new JSONArray();
		
		logger.info("本次解析记录数目为 ： " + list.size());
		
//		for (Map<String, Object> map : list) {
//			categoryArray.add(map.get("orgName"));
//		}
//		//单位名称
//		finalObject.put("categoryArray", categoryArray);
//		
		
		JSONObject normalObject = new JSONObject();;
		normalObject.put("name", "出勤、公出");
		List normalList = new LinkedList();
		
		JSONObject absentObject = new JSONObject();;
		absentObject.put("name", "刷卡异常");
		List absentList = new LinkedList();
		
		JSONObject lateObject = new JSONObject();;
		lateObject.put("name", "迟到、早退");
		List lateList = new LinkedList();
		
		JSONObject affairLeaveObject = new JSONObject();;
		affairLeaveObject.put("name", "病假、事假");
		List affairLeaveList = new LinkedList();
		
		JSONObject otherStatusObject = new JSONObject();;
		otherStatusObject.put("name", "其它");
		List otherStatusList = new LinkedList();
		
		for (Map<String, Object> map : list) {
			
			//部门、单位列表
			categoryArray.add(map.get("orgName"));
			
			//出勤、公出
			normalList.add(Integer.valueOf(map.get("normalCount").toString()) + 
					Integer.valueOf(map.get("businessLeave").toString()));
			
			//刷卡异常
			absentList.add(Integer.valueOf(map.get("absentCount").toString()));
			
			//迟到、早退
			lateList.add(Integer.valueOf(map.get("lateCount").toString()) +
					Integer.valueOf(map.get("leaveEarlyCount").toString()));
			//病假、事假
			affairLeaveList.add(Integer.valueOf(map.get("affairLeave").toString()) + 
					Integer.valueOf(map.get("sickLeave").toString()));
			//其它
			otherStatusList.add(Integer.valueOf(map.get("otherStatus").toString()));
		}
		
		//单位名称
		finalObject.put("categoryArray", categoryArray);
		
		//出勤
		normalObject.put("data", normalList);
		finalArray.add(normalObject);
		
		//刷卡异常
		absentObject.put("data", absentList);
		finalArray.add(absentObject);
		
		//迟到
		lateObject.put("data", lateList);
		finalArray.add(lateObject);
		
		//事假
		affairLeaveObject.put("data",affairLeaveList );
		finalArray.add(affairLeaveObject);
		
		//其它
		otherStatusObject.put("data",otherStatusList);
		finalArray.add(otherStatusObject);
		
		finalObject.put("series", finalArray);
		logger.info(finalObject.toString());
		
		return finalObject.toString();
	}

	/**
	 * list 转换为 饼图需要jsonobject数据 
	 * @param deptAttendanceList
	 * @return
	 */
	public static String list2PieChartJsonObject(
			List<Map<String, Object>> deptAttendanceList) {
		
		/**
		 * 数据源
		 * - {affairLeave=0, leaveEarlyCount=2, businessLeave=0, otherStatus=0, 
		 * absentAttendance=15, sickLeave=0, baseCount=1, lateCount=2,
		 *  orgName=信息资源管理处, normalAttendance=18}
		 */
		
		/**
		 * 解析之后结果
		 ['出勤',260.8],
		 ['迟到，早退',30.5],
		 ['病假，事假',80.5],
		 ['其他',60.2],
		 ['公出',70],
		 */
		JSONArray finalArray = new JSONArray();
		
//		JSONArray categoryArray = new JSONArray();
//		for (Map<String, Object> map : deptAttendanceList) {
//			categoryArray.add(map.get("orgName"));
//		}
//		//单位名称
//		finalObject.put("categoryArray", categoryArray);
		
		
		JSONArray normalObject = new JSONArray();
		JSONArray absentObject = new JSONArray();
		JSONArray lateObject = new JSONArray();
		JSONArray leaveObject = new JSONArray();
		JSONArray otherObject = new JSONArray();
		
		int normalCount = 0;
		int absentCount = 0;
		int lateCount = 0;
		int leaveCount = 0;
		int otherCount = 0;
		
		for (Map<String, Object> map : deptAttendanceList) {
			//出勤、公出
			normalCount += Integer.valueOf(map.get("normalCount").toString()) + 
					Integer.valueOf(map.get("businessLeave").toString());
			
			//刷卡异常
			absentCount += Integer.valueOf(map.get("absentCount").toString());
			
			//迟到、早退
			lateCount += Integer.valueOf(map.get("lateCount").toString()) +
					Integer.valueOf(map.get("leaveEarlyCount").toString());
			
			//病假、事假
			leaveCount += Integer.valueOf(map.get("affairLeave").toString()) + 
					Integer.valueOf(map.get("sickLeave").toString());
			//其它
			otherCount += Integer.valueOf(map.get("otherStatus").toString());
		}
		
		normalObject.add("出勤、公出");
		normalObject.add(normalCount);
		
		
		absentObject.add("刷卡异常");
		absentObject.add(absentCount);
		
		lateObject.add("迟到，早退");
		lateObject.add(lateCount);
		
		
		leaveObject.add("病假、事假");
		leaveObject.add(leaveCount);
		
		
		otherObject.add("其它");
		otherObject.add(otherCount);
		
		finalArray.add(normalObject);
		finalArray.add(absentObject);
		finalArray.add(lateObject);
		finalArray.add(leaveObject);
		finalArray.add(otherObject);
		
		logger.info(finalArray.toString());
		JSONObject finalObject = new JSONObject();
		finalObject.put("data", finalArray);
		
		return finalObject.toString();
	}

	
	/**
	 * 大厦考勤宏观统计数据处理
	 * @param buildingAttendanceList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String buildingList2Json(
			List<Map<String, Object>> buildingAttendanceList) {

		
		/**
		 * {"categoryArray":["南京市地震局","南京市发展和改革委员会","南京市国有资产监督管理委员会"],
 			"series":[{ name: '出勤、公出', data: [5, 3, 4, 7, 2]}, 
 			{name: '刷卡异常', data: [2, 2, 3, 2, 1]}, 
 			{name: '迟到,早退',data: [3, 4, 4, 2, 5]},
 			{name: '病假,事假',data: [3, 4, 4, 2, 5]},
 			{name: '其他',data: [3, 4, 4, 2, 5]}]
		 */
		JSONObject finalObject = new JSONObject();
		JSONArray finalArray = new JSONArray();
		
		JSONArray categoryArray = new JSONArray();
		
		logger.info("本次解析记录数目为 ： " + buildingAttendanceList.size());
		
		/**
		 * {AFTERNOON_BUSINESS_LEAVE=6, AFTERNOON_SICK_AFFAIR_LEAVE=0, 
		 * MORNING_BUSINESS_LEAVE=5, AFTERNOON_LATE_LEAVE_EARLY=309,
		 * ORG_NAME=南京市信息中心, MORNING_SICK_AFFAIR_LEAVE=0, AFTERNOON_NORMAL=504,
		 *  AFTERNOON_ABSENT=1152, MORNING_NORMAL=435, MORNING_ABSENT=1180, 
		 *  MORNING_LATE_LEAVE_EARLY=354, MORNING_OTHER=0, AFTERNOON_OTHER=0}
		 */
		
		JSONObject normalObject = new JSONObject();;
		normalObject.put("name", "出勤、公出");
		List normalList = new LinkedList();
		
		JSONObject absentObject = new JSONObject();;
		absentObject.put("name", "刷卡异常");
		List absentList = new LinkedList();
		
		JSONObject lateObject = new JSONObject();;
		lateObject.put("name", "迟到、早退");
		List lateList = new LinkedList();
		
		JSONObject affairLeaveObject = new JSONObject();;
		affairLeaveObject.put("name", "病假、事假");
		List affairLeaveList = new LinkedList();
		
		JSONObject otherStatusObject = new JSONObject();;
		otherStatusObject.put("name", "其它");
		List otherStatusList = new LinkedList();
		
		//各单位出勤、公出数组，按百分比计算
		double[] percents = new double[buildingAttendanceList.size()];
		
		for (int i=0; i< buildingAttendanceList.size() ; i++) {
			
			Map<String, Object> map = buildingAttendanceList.get(i);
			
			percents[i] = Arith.div(getAttendanceNormalCount(map), 
					getAttendanceTotalCount(map,false));
		}
		
		//数组排序
		Arrays.sort(percents);
		
		
		for (int i = percents.length -1 ; i >= 0 ; i--) {
			double temp = percents[i];
			
			//按考勤正常的次数排序
			for (Map<String, Object> map : buildingAttendanceList) {
				
				double percent = Arith.div(getAttendanceNormalCount(map), 
						getAttendanceTotalCount(map,false));
				
				//次数由大到小
				if (percent == temp) {
					
					//部门、单位列表
					categoryArray.add(map.get("ORG_NAME"));
					
					//出勤、公出
//					normalList.add(Integer.valueOf(map.get("MORNING_BUSINESS_LEAVE").toString()) + 
//							Integer.valueOf(map.get("AFTERNOON_BUSINESS_LEAVE").toString()) +
//							Integer.valueOf(map.get("MORNING_NORMAL").toString()) +
//							Integer.valueOf(map.get("AFTERNOON_NORMAL").toString()));
					
					normalList.add(str2int(map.get("MORNING_BUSINESS_LEAVE")) +
							str2int(map.get("AFTERNOON_BUSINESS_LEAVE")) + 
							str2int(map.get("MORNING_NORMAL")) + 
							str2int(map.get("AFTERNOON_NORMAL"))
							);
					
					//刷卡异常
//					absentList.add(Integer.valueOf(map.get("MORNING_ABSENT").toString()) +
//							Integer.valueOf(map.get("AFTERNOON_ABSENT").toString()));
					absentList.add(str2int(map.get("MORNING_ABSENT")) +
								str2int(map.get("AFTERNOON_ABSENT")));
					
					//迟到、早退
//					lateList.add(Integer.valueOf(map.get("MORNING_LATE_LEAVE_EARLY").toString()) +
//							Integer.valueOf(map.get("AFTERNOON_LATE_LEAVE_EARLY").toString()));
					lateList.add(str2int(map.get("MORNING_LATE_LEAVE_EARLY")) + 
							str2int(map.get("AFTERNOON_LATE_LEAVE_EARLY")));
					
					//病假、事假
//					affairLeaveList.add(Integer.valueOf(map.get("MORNING_SICK_AFFAIR_LEAVE").toString()) +
//							Integer.valueOf(map.get("AFTERNOON_SICK_AFFAIR_LEAVE").toString()));
					affairLeaveList.add(str2int(map.get("MORNING_SICK_AFFAIR_LEAVE")) +
							str2int(map.get("AFTERNOON_SICK_AFFAIR_LEAVE")));
					
					//其它
//					otherStatusList.add(Integer.valueOf(map.get("MORNING_OTHER").toString()) + 
//							Integer.valueOf(map.get("AFTERNOON_OTHER").toString()));
					otherStatusList.add(str2int(map.get("MORNING_OTHER")) + 
							str2int(map.get("AFTERNOON_OTHER")));
					
				}
				
			}
			
		}
		
		
/*		//按考勤正常的次数排序
		for (Map<String, Object> map : buildingAttendanceList) {
			
			
			//部门、单位列表
			categoryArray.add(map.get("ORG_NAME"));
			
			//出勤、公出
			normalList.add(Integer.valueOf(map.get("MORNING_BUSINESS_LEAVE").toString()) + 
					Integer.valueOf(map.get("AFTERNOON_BUSINESS_LEAVE").toString()) +
					Integer.valueOf(map.get("MORNING_NORMAL").toString()) +
					Integer.valueOf(map.get("AFTERNOON_NORMAL").toString()));
			
			//刷卡异常
			absentList.add(Integer.valueOf(map.get("MORNING_ABSENT").toString()) +
					Integer.valueOf(map.get("AFTERNOON_ABSENT").toString()));
			
			//迟到、早退
			lateList.add(Integer.valueOf(map.get("MORNING_LATE_LEAVE_EARLY").toString()) +
					Integer.valueOf(map.get("AFTERNOON_LATE_LEAVE_EARLY").toString()));
			
			//病假、事假
			affairLeaveList.add(Integer.valueOf(map.get("MORNING_SICK_AFFAIR_LEAVE").toString()) +
					Integer.valueOf(map.get("AFTERNOON_SICK_AFFAIR_LEAVE").toString()));
			
			//其它
			otherStatusList.add(Integer.valueOf(map.get("MORNING_OTHER").toString()) + 
					Integer.valueOf(map.get("AFTERNOON_OTHER").toString()));
		}*/
		
		//单位名称
		finalObject.put("categoryArray", categoryArray);
		
		//其它
		otherStatusObject.put("data",otherStatusList);
		finalArray.add(otherStatusObject);
		
		//刷卡异常
		absentObject.put("data", absentList);
		finalArray.add(absentObject);
		
		//迟到、早退
		lateObject.put("data", lateList);
		finalArray.add(lateObject);
		
		//事假、病假
		affairLeaveObject.put("data",affairLeaveList );
		finalArray.add(affairLeaveObject);
		
		//出勤、公出
		normalObject.put("data", normalList);
		finalArray.add(normalObject);
		
		finalObject.put("series", finalArray);
		logger.info(finalObject.toString());
		
		return finalObject.toString();
	
	}
	
	
	/**
	 * 获取正常出勤、公出次数
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getAttendanceNormalCount(Map map){
		
		int normalCount = 0;
		
		normalCount = Integer.valueOf(map.get("MORNING_BUSINESS_LEAVE").toString()) + 
				Integer.valueOf(map.get("AFTERNOON_BUSINESS_LEAVE").toString()) +
				Integer.valueOf(map.get("MORNING_NORMAL").toString()) +
				Integer.valueOf(map.get("AFTERNOON_NORMAL").toString()) ;
		
		return normalCount;
	}
	
	
	/**
	 * 获取考勤统计总数
	 * @param map
	 * @param flag 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getAttendanceTotalCount(Map map, boolean flag){

		int baseCount = 0;
		//单位考勤统计数据
		if (flag) {
			/**
			 * 数据源
			 * - {affairLeave=0, leaveEarlyCount=2, businessLeave=0, otherStatus=0, 
			 * absentCount=15, sickLeave=0, baseCount=1, lateCount=2,
			 *  orgName=信息资源管理处, normalAttendance=18}
			 */
			
			logger.info(map.toString());
			
			baseCount = Integer.valueOf(map.get("affairLeave").toString()) + 
					Integer.valueOf(map.get("leaveEarlyCount").toString()) + 
					Integer.valueOf(map.get("businessLeave").toString()) + 
					Integer.valueOf(map.get("otherStatus").toString()) + 
					Integer.valueOf(map.get("absentCount").toString()) + 
					Integer.valueOf(map.get("sickLeave").toString()) + 
					Integer.valueOf(map.get("lateCount").toString()) + 
					Integer.valueOf(map.get("normalCount").toString());
					
		}else {
			//大厦考勤数据
			baseCount = Integer.valueOf(map.get("MORNING_BUSINESS_LEAVE").toString()) + 
			Integer.valueOf(map.get("AFTERNOON_BUSINESS_LEAVE").toString()) +
			
			Integer.valueOf(map.get("MORNING_NORMAL").toString()) +
			Integer.valueOf(map.get("AFTERNOON_NORMAL").toString()) +
			
			Integer.valueOf(map.get("MORNING_SICK_AFFAIR_LEAVE").toString()) +
			Integer.valueOf(map.get("AFTERNOON_SICK_AFFAIR_LEAVE").toString()) +
			
			Integer.valueOf(map.get("MORNING_LATE_LEAVE_EARLY").toString()) +
			Integer.valueOf(map.get("AFTERNOON_LATE_LEAVE_EARLY").toString()) +
			
			Integer.valueOf(map.get("MORNING_ABSENT").toString()) +
			Integer.valueOf(map.get("AFTERNOON_ABSENT").toString()) +
			
			Integer.valueOf(map.get("MORNING_OTHER").toString()) +
			Integer.valueOf(map.get("AFTERNOON_OTHER").toString()) ;
		}
		return baseCount;
	}
	

	/**
	 * list 转换为 饼图需要jsonobject数据 
	 * @param deptAttendanceList
	 * @return
	 */
	public static String buildingList2PieChartJson(
			List<Map<String, Object>> deptAttendanceList) throws Exception{

		/**
		 * 数据源
		 * {AFTERNOON_BUSINESS_LEAVE=6, AFTERNOON_SICK_AFFAIR_LEAVE=0, 
		 * MORNING_BUSINESS_LEAVE=5, AFTERNOON_LATE_LEAVE_EARLY=309,
		 * ORG_NAME=南京市信息中心, MORNING_SICK_AFFAIR_LEAVE=0, AFTERNOON_NORMAL=504,
		 *  AFTERNOON_ABSENT=1152, MORNING_NORMAL=435, MORNING_ABSENT=1180, 
		 *  MORNING_LATE_LEAVE_EARLY=354, MORNING_OTHER=0, AFTERNOON_OTHER=0}
		 */
	
	/**
	 * 解析之后结果
	 ['出勤',260.8],
	 ['迟到，早退',30.5],
	 ['病假，事假',80.5],
	 ['其他',60.2],
	 ['公出',70],
	 */
	JSONArray finalArray = new JSONArray();
	
	
	JSONArray normalObject = new JSONArray();
	JSONArray absentObject = new JSONArray();
	JSONArray lateObject = new JSONArray();
	JSONArray leaveObject = new JSONArray();
	JSONArray otherObject = new JSONArray();
	
	int normalCount = 0;
	int absentCount = 0;
	int lateCount = 0;
	int leaveCount = 0;
	int otherCount = 0;
	
	for (Map<String, Object> map : deptAttendanceList) {
		
		try {
			//出勤、公出
			normalCount += str2int(map.get("MORNING_BUSINESS_LEAVE")) +
					str2int(map.get("AFTERNOON_BUSINESS_LEAVE")) + 
					str2int(map.get("MORNING_NORMAL")) + 
					str2int(map.get("AFTERNOON_NORMAL")) ;
			
			//刷卡异常
			absentCount += 	str2int(map.get("MORNING_ABSENT")) + 
					str2int(map.get("AFTERNOON_ABSENT")) ;
			
			//迟到、早退
			lateCount += str2int(map.get("MORNING_LATE_LEAVE_EARLY")) + 
					str2int(map.get("AFTERNOON_LATE_LEAVE_EARLY")) ;
			
			//病假、事假
			leaveCount += str2int(map.get("MORNING_SICK_AFFAIR_LEAVE")) + 
					str2int(map.get("AFTERNOON_SICK_AFFAIR_LEAVE")) ;
			
			//其它
			otherCount += str2int(map.get("MORNING_OTHER")) + 
					str2int(map.get("AFTERNOON_OTHER")) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	normalObject.add("出勤、公出");
	normalObject.add(normalCount);
	
	
	absentObject.add("刷卡异常");
	absentObject.add(absentCount);
	
	lateObject.add("迟到，早退");
	lateObject.add(lateCount);
	
	
	leaveObject.add("病假、事假");
	leaveObject.add(leaveCount);
	
	
	otherObject.add("其它");
	otherObject.add(otherCount);
	
	finalArray.add(normalObject);
	finalArray.add(absentObject);
	finalArray.add(lateObject);
	finalArray.add(leaveObject);
	finalArray.add(otherObject);
	
	logger.info(finalArray.toString());
	JSONObject finalObject = new JSONObject();
	finalObject.put("data", finalArray);
	
	return finalObject.toString();
	
	}
	
	public static int str2int(Object obj){
		
		int i = 0;
		
		try {
			if (obj != null && !"".equals(obj)) {
				String str = obj.toString();
				i = Integer.valueOf(str);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return i;
	} 

	
	/**
	 * 
	 * @param start_time
	 * @param end_time
	 * @param paramsMap 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map splitAttendanceTime(String start_time, String end_time, Map paramsMap) {
		
		/**
		 * start_time 2014-04-01
		 * end_time 2014-04-16
		 */
		String[] start = start_time.split("-");
		String[] end = end_time.split("-");
		
		
		paramsMap.put("year_start", start[0]);
		paramsMap.put("year_end", end[0]);
		paramsMap.put("month_start", start[1]);
		paramsMap.put("month_end", end[1]);
		paramsMap.put("day_start", start[2]); //开始时间的日期  01
		paramsMap.put("day_end", end[2]); //开始时间的日期  16
		
/*		//如果是年份不同
		if (!start[0].equals(end[0])) {
			paramsMap.put("year_start", start[0]);
			paramsMap.put("year_end", end[0]);
			paramsMap.put("month_start", start[1]);
			paramsMap.put("month_end", end[1]);
		}else{
			//如果月份不同
			if (!start[1].equals(end[1])) {
				paramsMap.put("month_start", start[1]);
				paramsMap.put("month_end", end[1]);
			}
		}
		
		paramsMap.put("day_start", start[2]); //开始时间的日期  01
		paramsMap.put("day_end", end[2]); //开始时间的日期  16
		*/
/*		//如果日期不同
		if (!start[2].equals(end[2])) {
			paramsMap.put("day_start", start[2]); //开始时间的日期  01
			paramsMap.put("day_end", end[2]); //开始时间的日期  16
		}*/
		
		return paramsMap;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Map map = splitAttendanceTime("2013-04-17", "2014-04-17", new HashMap());
		System.out.println(map.toString());
	}

}
