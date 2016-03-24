package com.cosmosource.app.attendance.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.base.util.DateUtil;

public class AttendanceUtil {

	protected static Logger logger = LoggerFactory
			.getLogger(AttendanceUtil.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List resolveAttendanceList(List<Map<String, Object>> list) {
		Map tMap = null;
		// 获取查询到员工数目
		Set<String> userIdSet = new LinkedHashSet<String>();

		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> empMap = (Map) list.get(j);
			userIdSet.add(empMap.get("USER_ID").toString());
		}

		List<HashMap> empList = new LinkedList<HashMap>();
		Map<String, Object> empMap = null;

		for (String _userId : userIdSet) {
			int lateCount = 0, leaveEarlyCount = 0;

			float normalCount = 0.0f, excepCount = 0.0f, sickLeave = 0.0f, affairLeave = 0.0f, businessLeave = 0.0f, otherStatus = 0.0f; // 缺勤总数

			empMap = new HashMap<String, Object>();

			for (int k = 0; k < list.size(); k++) {
				tMap = (Map) list.get(k);
				String innerUserID = tMap.get("USER_ID").toString();

				// 是同一个人的记录
				if (_userId.equals(innerUserID)) {

					// 考勤状态:0:正常 1:迟到 2:缺勤 3:早退
					// 上午正常上班
					if ("0".equals(tMap.get("MORNING_STAT").toString())) {
						normalCount += 0.5;
					}
					// 下午正常下班
					if ("0".equals(tMap.get("AFTERNOON_STAT").toString())) {
						normalCount += 0.5;
					}
					// 迟到次数
					if ("1".equals(tMap.get("MORNING_STAT").toString())) {
						lateCount++;
					}
					if ("1".equals(tMap.get("AFTERNOON_STAT").toString())) {
						lateCount++;
					}
					// 缺勤，上午缺勤
					if ("2".equals(tMap.get("MORNING_STAT").toString())) {
						excepCount += 0.5;
					}
					// 缺勤，下午缺勤
					if ("2".equals(tMap.get("AFTERNOON_STAT").toString())) {
						excepCount += 0.5;
					}
					// 早退次数
					if ("3".equals(tMap.get("MORNING_STAT").toString())) {
						leaveEarlyCount++;
					}
					if ("3".equals(tMap.get("AFTERNOON_STAT").toString())) {
						leaveEarlyCount++;
					}

					// 事假统计
					// 上午异常类型 1:病假 2:事假 3:公休 4:其它
					Object obj = tMap.get("ATTEN_MORNING_TYPE");
					int mornintType = 0;
					if (null != obj && !"".equals(obj)) {
						mornintType = Integer.valueOf(obj.toString());

					}

					switch (mornintType) {
					case 1:
						sickLeave += 0.5;
						break;
					case 2:
						affairLeave += 0.5;
						break;
					case 3:
						businessLeave += 0.5;
						break;
					case 4:
						otherStatus += 0.5;
						break;

					default:
						break;
					}

					// 下午午异常类型 1:病假 2:事假 3:公休 4:其它
					Object object = tMap.get("ATTEN_AFTERNOON_TYPE");
					int afternoonType = 0;
					if (null != object && !"".equals(object)) {
						afternoonType = Integer.valueOf(obj.toString());

					}

					switch (afternoonType) {
					case 1:
						sickLeave += 0.5;
						break;
					case 2:
						affairLeave += 0.5;
						break;
					case 3:
						businessLeave += 0.5;
						break;
					case 4:
						otherStatus += 0.5;
						break;

					default:
						break;
					}
				} // if (outUserID.equals(innerUserID)) {

			} // for (int k = 0; k < allAttendances.size(); k++) {

			String user_name = "";
			String org_name = "";

			for (int n = 0; n < list.size(); n++) {
				Map<String, Object> eMap = (Map) list.get(n);
				// System.out.println(eMap.get("USER_NAME").toString());

				if (_userId.equals(eMap.get("USER_ID").toString())) {
					user_name = eMap.get("USER_NAME").toString().trim();
					org_name = eMap.get("NAME").toString();
				}
			}

			empMap.put("userId", _userId); // 用户ID
			empMap.put("userName", user_name); // 姓名
			empMap.put("orgName", org_name); // 部门
			empMap.put("normalAttendance", String.valueOf(normalCount)); // 出勤总数
			empMap.put("execpAttendance", String.valueOf(excepCount)); // 缺勤总数
			empMap.put("lateCount", String.valueOf(lateCount)); // 迟到总数
			empMap.put("leaveEarlyCount", String.valueOf(leaveEarlyCount)); // 早退总数
			empMap.put("sickLeave", String.valueOf(sickLeave)); // 病假
			empMap.put("affairLeave", String.valueOf(affairLeave)); // 事假
			empMap.put("businessLeave", String.valueOf(businessLeave)); // 公休
			empMap.put("otherStatus", String.valueOf(otherStatus)); // 其它总数

			// System.out.println(empMap);
			empList.add((HashMap) empMap);

		}
		return empList;
	}

	/**
	 * 根据leaveType 来解析各种请假信息
	 * 
	 * @param list
	 * @param leaveType
	 *            请假分类 分类 1:病假 2:事假 3:公休 4:其它
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List resoleLeaveInfo(List list, String leaveType) {

		List finalList = new ArrayList();
		Map finalMap = null;

		// 最后组装成 日期 ： 审批人 ： 事由 格式
		// name time text
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);

			logger.info(map.toString());

			Object morningDealStat = map.get("MORNING_DEAL_STAT");
			Object afternoonDealStat = map.get("AFTERNOON_DEAL_STAT");

			// 上午申请已经审批解决,2 表示已经处理过
			if (morningDealStat != null
					&& "2".equals(morningDealStat.toString())) {
				Object attenMoringType = map.get("ATTEN_MORNING_TYPE");
				if (attenMoringType != null) {
					// 上午类型匹配
					if (leaveType.equals(attenMoringType.toString())) {
						finalMap = new HashMap();
						finalMap.put("time", map.get("TIME") + " 上午"); // 时间
						finalMap.put("name", map.get("ACCEPT_MORNING_NAME")
								.toString().trim());
						finalMap.put("text",
								map.get("ATTEN_MORNING_DESC") == null ? ""
										: map.get("ATTEN_MORNING_DESC"));
						finalList.add(finalMap);
					}
				}
			}

			// 下午的申请记录已经处理，而且已经处理完毕
			if (afternoonDealStat != null
					&& "2".equals(afternoonDealStat.toString())) {
				// 下午有异常记录
				Object attenAfternoonType = map.get("ATTEN_AFTERNOON_TYPE");
				if (attenAfternoonType != null) {
					// 下午类型匹配
					if (leaveType.equals(attenAfternoonType.toString())) {
						finalMap = new HashMap();
						finalMap.put("time", map.get("TIME") + " 下午"); // 时间
						finalMap.put("name", map.get("ACCEPT_AFTERNOON_NAME")
								.toString().trim());
						finalMap.put("text",
								map.get("ATTEN_AFTERNOON_DESC") == null ? ""
										: map.get("ATTEN_AFTERNOON_DESC"));
						finalList.add(finalMap);
					}
				}
			}

			/*
			 * // 2 表示已经处理 if
			 * ("2".equals(map.get("MORNING_DEAL_STAT").toString()) ||
			 * "2".equals(map.get("AFTERNOON_DEAL_STAT").toString())) { String
			 * attenMoringType = map.get("ATTEN_MORNING_TYPE").toString();
			 * String attenAfternoonType =
			 * map.get("ATTEN_AFTERNOON_TYPE").toString();
			 * 
			 * // 上午有异常记录 if (null != attenMoringType) { // 上午类型匹配 if
			 * (leaveType.equals(attenMoringType)) { finalMap = new HashMap();
			 * finalMap.put("time", map.get("TIME") + " 上午"); // 时间
			 * finalMap.put("name",
			 * map.get("ACCEPT_MORNING_NAME").toString().trim());
			 * finalMap.put("text", map.get("ATTEN_MORNING_DESC"));
			 * finalList.add(finalMap); } }
			 * 
			 * // 下午有异常记录 if (null != attenAfternoonType) { // 下午类型匹配 if
			 * (leaveType.equals(attenAfternoonType)) { finalMap = new
			 * HashMap(); finalMap.put("time", map.get("TIME") + " 下午"); // 时间
			 * finalMap.put("name",
			 * map.get("ACCEPT_AFTERNOON_NAME").toString().trim());
			 * finalMap.put("text", map.get("ATTEN_AFTERNOON_DESC"));
			 * finalList.add(finalMap); } }
			 */

			/*
			 * String acceptMorningUser =
			 * map.get("ATTEN_MORNING_USER").toString(); //上午请假审批人 String
			 * accepAfternonUser = map.get("ATTEN_AFTERNOON_USER").toString();
			 * //下午请假审批人
			 * 
			 * String attenMorningDesc =
			 * map.get("ATTEN_MORNING_DESC").toString(); //上午请假内容 String
			 * attenAfternoonDesc = map.get("ATTEN_AFTERNOON_DESC").toString();
			 * //下午请假内容
			 */
		}
		logger.info(finalList.toString());
		return finalList;

	}

	/**
	 * 根据传入参数判断请假类型
	 */
	public static String getAttenType(String str) {
		// 上午异常类型 1:病假 2:事假 3:公休 4:其它
		String type = null;
		int i = Integer.valueOf(str);
		switch (i) {
		case 1:
			type = "病假";
			break;
		case 2:
			type = "事假";
			break;

		case 3:
			type = "公出";
			break;

		case 4:
			type = "其它";
			break;

		default:
			break;
		}

		return type;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List resolveAcceptList(List list) {
		Map tMap = null;
		// 获取查询到员工数目
		Set<String> userIdSet = new LinkedHashSet<String>();

		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> empMap = (Map) list.get(j);
			userIdSet.add(empMap.get("USER_ID").toString());
		}

		List<HashMap> empList = new LinkedList<HashMap>();
		Map<String, Object> empMap = null;

		for (String _userId : userIdSet) {
			int lateCount = 0, leaveEarlyCount = 0;

			float normalCount = 0.0f, excepCount = 0.0f, sickLeave = 0.0f, affairLeave = 0.0f, businessLeave = 0.0f, otherStatus = 0.0f; // 缺勤总数

			empMap = new HashMap<String, Object>();

			for (int k = 0; k < list.size(); k++) {
				tMap = (Map) list.get(k);
				String innerUserID = tMap.get("USER_ID").toString();

				// 是同一个人的记录
				if (_userId.equals(innerUserID)) {

					// 考勤状态:0:正常 1:迟到 2:缺勤 3:早退
					// 上午正常上班
					if ("0".equals(tMap.get("MORNING_STAT").toString())) {
						normalCount += 0.5;
					}
					// 下午正常下班
					if ("0".equals(tMap.get("AFTERNOON_STAT").toString())) {
						normalCount += 0.5;
					}
					// 迟到次数
					if ("1".equals(tMap.get("MORNING_STAT").toString())) {
						lateCount++;
					}
					if ("1".equals(tMap.get("AFTERNOON_STAT").toString())) {
						lateCount++;
					}
					// 缺勤，上午缺勤
					if ("2".equals(tMap.get("MORNING_STAT").toString())) {
						excepCount += 0.5;
					}
					// 缺勤，下午缺勤
					if ("2".equals(tMap.get("AFTERNOON_STAT").toString())) {
						excepCount += 0.5;
					}
					// 早退次数
					if ("3".equals(tMap.get("MORNING_STAT").toString())) {
						leaveEarlyCount++;
					}
					if ("3".equals(tMap.get("AFTERNOON_STAT").toString())) {
						leaveEarlyCount++;
					}

					// 事假统计
					// 上午异常类型 1:病假 2:事假 3:公休 4:其它
					Object obj = tMap.get("ATTEN_MORNING_TYPE");
					int mornintType = 0;
					if (null != obj && !"".equals(obj)) {
						mornintType = Integer.valueOf(obj.toString());

					}

					switch (mornintType) {
					case 1:
						sickLeave += 0.5;
						break;
					case 2:
						affairLeave += 0.5;
						break;
					case 3:
						businessLeave += 0.5;
						break;
					case 4:
						otherStatus += 0.5;
						break;

					default:
						break;
					}

					// 下午午异常类型 1:病假 2:事假 3:公休 4:其它
					Object object = tMap.get("ATTEN_AFTERNOON_TYPE");
					int afternoonType = 0;
					if (null != object && !"".equals(object)) {
						afternoonType = Integer.valueOf(obj.toString());

					}

					switch (afternoonType) {
					case 1:
						sickLeave += 0.5;
						break;
					case 2:
						affairLeave += 0.5;
						break;
					case 3:
						businessLeave += 0.5;
						break;
					case 4:
						otherStatus += 0.5;
						break;

					default:
						break;
					}
				} // if (outUserID.equals(innerUserID)) {

			} // for (int k = 0; k < allAttendances.size(); k++) {

			String user_name = "";
			String org_name = "";

			for (int n = 0; n < list.size(); n++) {
				Map<String, Object> eMap = (Map) list.get(n);
				// System.out.println(eMap.get("USER_NAME").toString());

				if (_userId.equals(eMap.get("USER_ID").toString())) {
					user_name = eMap.get("USER_NAME").toString().trim();
					org_name = eMap.get("NAME").toString();
				}
			}

			empMap.put("userId", _userId); // 用户ID
			empMap.put("userName", user_name); // 姓名
			empMap.put("orgName", org_name); // 部门
			empMap.put("normalAttendance", String.valueOf(normalCount)); // 出勤总数
			empMap.put("execpAttendance", String.valueOf(excepCount)); // 缺勤总数
			empMap.put("lateCount", String.valueOf(lateCount)); // 迟到总数
			empMap.put("leaveEarlyCount", String.valueOf(leaveEarlyCount)); // 早退总数
			empMap.put("sickLeave", String.valueOf(sickLeave)); // 病假
			empMap.put("affairLeave", String.valueOf(affairLeave)); // 事假
			empMap.put("businessLeave", String.valueOf(businessLeave)); // 公休
			empMap.put("otherStatus", String.valueOf(otherStatus)); // 其它总数

			System.out.println(empMap);
			empList.add((HashMap) empMap);

		}
		return empList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List resolveApplyList(List result, Map paramMap) {
		List finalList = new ArrayList();
		try {
			// 遍历 list 获取每个时间信息
			// 页面传过来参数
			String startTime = paramMap.get("startTime").toString();
			String endTime = paramMap.get("endTime").toString();

			// 获取时间分割list
			List daysList = DateUtil.getDiffDaysList(startTime, endTime,
					"yyyy-MM-dd", false);

			for (int i = 0; i < daysList.size(); i++) {

				logger.info(DateUtil.date2Str((Date) daysList.get(i),
						"yyyy-MM-dd"));

				Date currentTime = (Date) daysList.get(i);

				for (int j = 0; j < result.size(); j++) {
					Map tMap = (Map) result.get(j);
					logger.info(tMap.toString());
					// 请假开始时间
					String start_date = tMap.get("START_DATE").toString()
							.substring(0, 10).replaceAll("-", "");
					// 上午标志
					String start_type = tMap.get("START_DATE").toString()
							.substring(10);
					// 请假结束时间
					String end_date = tMap.get("END_DATE").toString()
							.substring(0, 10).replaceAll("-", "");
					// 下午标志
					String end_type = tMap.get("END_DATE").toString()
							.substring(10);

					Date sDate = DateUtil.str2Date(start_date, "yyyy-MM-dd");
					Date eDate = DateUtil.str2Date(end_date, "yyyy-MM-dd");

					/*
					 * //如果当前遍历日期小于请假开始时间 //分三个区间判断 if (currentTime.getTime() <
					 * sDate.getTime() ) { //存放一条 记录到 map 添加到最后返回的 finalList
					 * tMap.put("START_DATE", start_date + start_type);
					 * tMap.put("END_DATE", end_date + end_type); }
					 * //当前时间大于请假结束时间 if (currentTime.getTime() >
					 * eDate.getTime()) {
					 * 
					 * }
					 */
					// 当前时间在请假时间范围之内
					String cTime = DateUtil.date2Str(currentTime, "yyyy-MM-dd")
							.replaceAll("-", "");
					logger.info("开始 ：" + start_date);
					logger.info("判断 ： " + cTime);
					logger.info("结束 ： " + end_date);
					// if (currentTime.getTime() >= sDate.getTime() &&
					// currentTime.getTime() <= eDate.getTime()) {
					if (Integer.valueOf(start_date) <= Integer.valueOf(cTime)
							&& Integer.valueOf(cTime) <= Integer
									.valueOf(end_date)) {

						Map map = new HashMap();
						map.put("START_DATE",
								DateUtil.date2Str(currentTime, "yyyy-MM-dd")
										+ start_type);
						map.put("END_DATE",
								DateUtil.date2Str(currentTime, "yyyy-MM-dd")
										+ end_type);

						// tMap.put("START_DATE", DateUtil.date2Str(currentTime,
						// "yyyy-MM-dd") + start_type);
						// tMap.put("END_DATE", DateUtil.date2Str(currentTime,
						// "yyyy-MM-dd") + end_type);
						finalList.add(map);
						logger.info("返回数据 ： " + tMap.toString());
					}
				}

				// 如果当前输入时间在请假日期范围之内

			}

			return finalList;
			/*
			 * Object startTime = paramMap.get("startTime"); Object endTime =
			 * paramMap.get("endTime");
			 * 
			 * 
			 * if (null != paramMap.get("flag")) { //如果开始时间不为空 if (null !=
			 * startTime) { //取符合条件的结果集 // 如 startTime 在 start_date 跟 end_date
			 * 之间 for (int i = 0; i < result.size(); i++) { Map tMap = (Map)
			 * result.get(0); boolean isCorrect = false; if
			 * (isCorrectDate(tMap.get
			 * ("start_date"),startTime,tMap.get("end_date"))) { } } }
			 * //如果结束时间不为空 }
			 * 
			 * return null;
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalList;
	}

	/**
	 * 
	 * @param start_date
	 *            数据库查询出的开始时间
	 * @param startTime
	 *            页面选择的开始时间
	 * @param end_date
	 *            数据库查询出的结束时间
	 * @return
	 */
	private static boolean isCorrectDate(Object start_date, Object startTime,
			Object end_date) {

		if (DateUtil.str2Date(startTime.toString(), "yyyy-MM-dd").getTime() < DateUtil
				.str2Date(start_date.toString(), "yyyy-MM-dd").getTime()) {

		}

		return false;
	}

	public static boolean isCorrectDate(String str) {
		boolean flag = false;
		Date start = DateUtil.str2Date(str, "yyyy-MM-dd");

		return flag;
	}

	/**
	 * @param empAttendanceList
	 *            单个员工考勤信息
	 * @return 统计返回 map 如 ： 缺勤 10 天 请假 2天 ..
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map analyseAttendanceList(List empAttendanceList) {
		Map empMap = new HashMap<String, Object>();

		int lateCount = 0, // 迟到次数
		leaveEarlyCount = 0; // 早退次数

		float normalCount = 0.0f, // 正常出勤
		absentCount = 0.0f, // 缺勤
		sickLeave = 0.0f, // 病假
		affairLeave = 0.0f, // 事假
		businessLeave = 0.0f, // 公出
		otherStatus = 0.0f; // 其它

		Map tMap = null;

		for (int i = 0; i < empAttendanceList.size(); i++) {

			/**
			 * 异常情况处理标志:0:无需处理 1:未处理 2:已处理 3:正在申请 异常类型 1:病假 2:事假 3:公休 4:其它
			 * 考勤状态:0:正常 1:迟到 2:缺勤 3:早退 此字段为原始记录，不变动
			 */

			tMap = (Map) empAttendanceList.get(i);

			// logger.info(tMap.toString());
			// 考勤状态:0:正常 1:迟到 2:缺勤 3:早退
			// 上午正常上班,上午记录无须处理
			if ("0".equals(tMap.get("MORNING_STAT").toString()) &&
					"0".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
				normalCount += 0.5;
			}
			// 下午正常下班,下午记录无须处理状态
			if ("0".equals(tMap.get("AFTERNOON_STAT").toString()) &&
					"0".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
				normalCount += 0.5;
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
			// 上午缺勤，未处理状态
			if ("2".equals(tMap.get("MORNING_STAT").toString())) {
				if (!"2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					absentCount += 0.5;
				}
			}
			// 下午缺勤，未处理
			if ("2".equals(tMap.get("AFTERNOON_STAT").toString())) {
				if (!"2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					absentCount += 0.5;
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
				//确保早上的记录是已处理过
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					sickLeave += 0.5;
				}
				break;
			case 2:
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					affairLeave += 0.5;
				}
				break;
			case 3:
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					businessLeave += 0.5;
				}
				break;
			case 4:
				if ("2".equals(tMap.get("MORNING_DEAL_STAT").toString())) {
					otherStatus += 0.5;
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
				//确保下午的记录是已经处理过的
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					sickLeave += 0.5;
				}
				break;
			case 2:
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					affairLeave += 0.5;
				}
				break;
			case 3:
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					businessLeave += 0.5;
				}
				break;
			case 4:
				if ("2".equals(tMap.get("AFTERNOON_DEAL_STAT").toString())) {
					otherStatus += 0.5;
				}
				break;

			default:
				break;
			}

		}

		// 统计信息添加到 empMap
		// 继续添加一些基本信息
		empMap.put("userId", tMap.get("USER_ID")); // 用户ID
		empMap.put("userName", tMap.get("USER_NAME")); // 姓名
		empMap.put("unitName", tMap.get("ORG_NAME")); // 单位
		empMap.put("orgName", tMap.get("DEPARTMENT_NAME")); // 部门
		empMap.put("normalAttendance", String.valueOf(normalCount)); // 出勤总数
		empMap.put("execpAttendance", String.valueOf(absentCount)); // 缺勤总数
		empMap.put("lateCount", String.valueOf(lateCount)); // 迟到总数
		empMap.put("leaveEarlyCount", String.valueOf(leaveEarlyCount)); // 早退总数
		empMap.put("sickLeave", String.valueOf(sickLeave)); // 病假
		empMap.put("affairLeave", String.valueOf(affairLeave)); // 事假
		empMap.put("businessLeave", String.valueOf(businessLeave)); // 公出
		empMap.put("otherStatus", String.valueOf(otherStatus)); // 其它总数

		return empMap;
	}

	public static List<String> getWeekends(String startTime, String endTime) {
		List<String> weekendsList = new ArrayList<String>();
		List<Date> diffDays = DateUtil.getDiffDaysList(startTime, endTime,
				"yyyy-MM-dd", false);
		// 根据用户选择的日期，获取中间的周末信息

		for (Date date : diffDays) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			// 如果当前日期是周六或者周日
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				weekendsList.add(DateUtil.date2Str(date, "yyyy-MM-dd"));
			}
		}

		return weekendsList;
	}

	public static void main(String[] args) {
		String start = "2014-03-01";
		String end = "2014-03-31";
		List<String> list = getWeekends(start, end);
		// for (Object object : list) {
		// System.out.println(object);
		// }

		System.out.println(DateUtil.date2Str(new Date(), "HHmmss"));
	}

	/**
	 * 根据leaveType解析考勤异常信息 状态:0:正常 1:迟到 2:缺勤 3:早退
	 * 
	 * @param list
	 * @param valueOf
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List resoleAttendanceUnusualInfo(List list, String leaveType) {
		List finalList = new ArrayList();
		Map finalMap = null;

		// 最后组装成 日期 ： 审批人 ： 事由 格式
		// name time text
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);

			logger.info(map.toString());

			Object morningDealStat = map.get("MORNING_DEAL_STAT");
			Object afternoonDealStat = map.get("AFTERNOON_DEAL_STAT");

			// 统计状态为未处理记录，如果已处理则应该统计到请假类型中
			// MORNING_STAT 上午状态:0:正常 1:迟到 2:缺勤 3:早退
			if (morningDealStat != null
					&& !"2".equals(morningDealStat.toString())) {
				Object attenMoringType = map.get("MORNING_STAT");
				if (attenMoringType != null) {
					// 上午类型匹配
					if (leaveType.equals(attenMoringType.toString())) {
						finalMap = new HashMap();
						finalMap.put("time", map.get("TIME") + " 上午"); // 时间
//						finalMap.put("name", map.get("ACCEPT_MORNING_NAME")
//								.toString().trim());
//						finalMap.put("text",
//								map.get("ATTEN_MORNING_DESC") == null ? ""
//										: map.get("ATTEN_MORNING_DESC"));
						finalList.add(finalMap);
					}
				}
			}

			// 下午的记录状态为未处理，如果已处理则应该统计到请假类型中
			if (afternoonDealStat != null
					&& !"2".equals(afternoonDealStat.toString())) {
				// 下午有异常记录
				Object attenAfternoonType = map.get("AFTERNOON_STAT");
				if (attenAfternoonType != null) {
					// 下午类型匹配
					if (leaveType.equals(attenAfternoonType.toString())) {
						finalMap = new HashMap();
						finalMap.put("time", map.get("TIME") + " 下午"); // 时间
//						finalMap.put("name", map.get("ACCEPT_AFTERNOON_NAME")
//								.toString().trim());
//						finalMap.put("text",
//								map.get("ATTEN_AFTERNOON_DESC") == null ? ""
//										: map.get("ATTEN_AFTERNOON_DESC"));
						finalList.add(finalMap);
					}
				}
			}
		}
		logger.info(finalList.toString());
		return finalList;
	}
}
