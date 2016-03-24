package com.cosmosource.app.attendance.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cosmosource.app.attendance.constant.PteroConstants;
import com.cosmosource.app.attendance.service.PteroAttendManager;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.utils.DateUtilPtero;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("rawtypes")
public class PteroAttendAction extends BaseAction {

	/**
	 * 设置节假日信息
	 */
	public void setHolidays() {
		// 开始日
		String fromDay = getRequest().getParameter("from");
		// 截止日
		String toDay = getRequest().getParameter("to");
		// 放假描述
		String desc = getRequest().getParameter("desc");

		boolean stat = pteroAttendManager.setHolidays(fromDay, toDay, desc);
		JSONObject jsonObject = new JSONObject();
		if (stat) {
			jsonObject.put("status", "ok");
		} else {
			jsonObject.put("status", "error");
		}
		Struts2Util.renderJson(jsonObject.toString(), "encoding:UTF-8",
				"no-cache:true");
		return;
	}

	/**
	 * 设置上班时间
	 */
	public void setTime() {
		// 开始日
		String fromTime = getRequest().getParameter("from");
		// 截止日
		String toTime = getRequest().getParameter("to");

		boolean stat = pteroAttendManager.setTimes(fromTime, toTime);
		JSONObject jsonObject = new JSONObject();
		if (stat) {
			jsonObject.put("success", "true");
		} else {
			jsonObject.put("success", "false");
			jsonObject.put("msg", "系统异常");
		}
		Struts2Util.renderJson(jsonObject.toString(), "encoding:UTF-8",
				"no-cache:true");
		return;
	}

	/**
	 * 大厦考勤员
	 */
	public void getAttendancers() {
		List<Users> users = pteroAttendManager.getAttendancers();

		if (!users.isEmpty()) {
			StringBuffer st = new StringBuffer();
			for (Users user : users) {
				st.append(user.getDisplayName().trim()).append(",");
			}
			Struts2Util.renderText(st.substring(0, st.length() - 1),
					"encoding:UTF-8", "no-cache:true");
		} else {
			Struts2Util.renderJson("", "encoding:UTF-8", "no-cache:true");
		}
	}

	/**
	 * 获取工作时间
	 */
	public void getTime() {
		List timeList = pteroAttendManager.getTime();
		if (timeList != null && timeList.size() > 0) {
			HashMap hashMap = (HashMap) timeList.get(0);
			String morningStartTime = (String) hashMap
					.get("MORNING_START_TIME");
			String morningEndTime = (String) hashMap.get("MORNING_END_TIME");
			String afternoonStartTime = (String) hashMap
					.get("AFTERNOON_START_TIME");
			String afternoonEndTime = (String) hashMap.get("AFTER_END_TIME");
			JSONObject jsonObject = new JSONObject();
			String tmp_1 = morningStartTime.substring(0, 4);
			String tmp_2 = morningEndTime.substring(0, 4);
			String tmp_3 = afternoonStartTime.substring(0, 4);
			String tmp_4 = afternoonEndTime.substring(0, 4);
			jsonObject.put("from",
					tmp_1.substring(0, 2) + ":" + tmp_1.substring(2) + "-"
							+ tmp_2.substring(0, 2) + ":" + tmp_2.substring(2));
			jsonObject.put("to",
					tmp_3.substring(0, 2) + ":" + tmp_3.substring(2) + "-"
							+ tmp_4.substring(0, 2) + ":" + tmp_4.substring(2));
			Struts2Util.renderJson(jsonObject.toString(), "encoding:UTF-8",
					"no-cache:true");
		} else {
			Struts2Util.renderJson("{}", "encoding:UTF-8", "no-cache:true");
		}
	}

	/**
	 * 删除节假日信息
	 */
	public void delHolidays() {
		// 开始日
		String fromDay = getRequest().getParameter("from");
		// 截止日
		String toDay = getRequest().getParameter("to");
		boolean stat = pteroAttendManager.delHolidays(fromDay, toDay);
		JSONObject jsonObject = new JSONObject();
		if (stat) {
			jsonObject.put("status", "ok");
		} else {
			jsonObject.put("status", "error");
		}
		Struts2Util.renderJson(jsonObject.toString(), "encoding:UTF-8",
				"no-cache:true");
		return;
	}

	/**
	 * 根据用户ID,年月查询用户当月考勤
	 * 
	 * @return json格式数据
	 */
	public void getUserMonthAttendanceList() {
		try {
			String userId = getRequest().getParameter("user_id");
			String month = getRequest().getParameter("month");
			String year = getRequest().getParameter("year");
//			logger.error("userID :" + userId + "month :" + month + " year:"
//					+ year);
			JSONObject jsonAllData = null, jsonObjectMorning = null, jsonObjectAfternoon = null;
			Map<String, Object> todayDateMap = DateUtilPtero.getTodayDateMap();
			// 开始组织json数据
			JSONArray jsonArray = new JSONArray();
			String tmpDateString = null;
			List monthList = pteroAttendManager
					.queryUserMonthAttendanceListByUserId(Long.valueOf(userId),
							year, month);
			// 查询跨月所有考勤信息
			if (monthList == null || monthList.size() == 0) {
				// 获取该用户自当日以后所在月所有请假信息
				String todayDate = year + "-" + month + "-" + "01";
				getMonthAllPrivateDay(userId, jsonArray, todayDate, true);
				// 生成当日数据
				genTodayData(userId, month, year, todayDateMap, jsonArray);
				jsonAllData = new JSONObject();
				jsonAllData.put("events", jsonArray);
				Struts2Util.renderJson(jsonAllData.toString(),
						"encoding:UTF-8", "no-cache:true");
				return;
			} else {
				for (Object oneRecord : monthList) {
					HashMap hashMap = (HashMap) oneRecord;
					// 判断当日是否为假期,假期不返回数据
					List holidayList = pteroAttendManager.isHolidayOrNot(
							(String) hashMap.get("ATTEND_YEAR"),
							(String) hashMap.get("ATTEND_MONTH"),
							(String) hashMap.get("ATTEND_DATE"));
					if (holidayList != null && holidayList.size() > 0) {
						continue;
					}
					tmpDateString = (String) hashMap.get("ATTEND_YEAR") + "-"
							+ (String) hashMap.get("ATTEND_MONTH") + "-"
							+ (String) hashMap.get("ATTEND_DATE");
					jsonObjectMorning = new JSONObject();
					jsonObjectMorning.put("title", "上午");
					jsonObjectMorning.put("className", "fc-event-morning");
					jsonObjectMorning.put("start", tmpDateString);
					if (hashMap.get("MORNING_STAT").toString().equals("0")) {
						jsonObjectMorning.put("status", "0");
					}
					// 领导处理完毕
					if (hashMap.get("MORNING_DEAL_STAT").toString().equals("2")) {
						jsonObjectMorning.put("status", "2");
					}
					// 无需处理
					else if (hashMap.get("MORNING_DEAL_STAT").toString()
							.equals("0")) {
						jsonObjectMorning.put("status", "0");
					}
					// 正在申请中
					else if (hashMap.get("MORNING_DEAL_STAT").toString()
							.equals("3")) {
						jsonObjectMorning.put("status", "4");
					}
					// 不正常
					else {
						jsonObjectMorning.put("status", "1");
					}
					jsonArray.add(jsonObjectMorning);
					jsonObjectAfternoon = new JSONObject();
					jsonObjectAfternoon.put("title", "下午");
					jsonObjectAfternoon.put("className", "fc-event-afternoon");
					jsonObjectAfternoon.put("start", tmpDateString);
					if (hashMap.get("AFTERNOON_STAT").toString().equals("0")) {
						jsonObjectAfternoon.put("status", "0");
					}
					// 领导处理完毕
					if (hashMap.get("AFTERNOON_DEAL_STAT").toString()
							.equals("2")) {
						jsonObjectAfternoon.put("status", "2");
					}
					// 无需处理
					else if (hashMap.get("AFTERNOON_DEAL_STAT").toString()
							.equals("0")) {
						jsonObjectAfternoon.put("status", "0");
					}
					// 正在申请中
					else if (hashMap.get("AFTERNOON_DEAL_STAT").toString()
							.equals("3")) {
						jsonObjectAfternoon.put("status", "4");
					}
					// 不正常
					else {
						jsonObjectAfternoon.put("status", "1");
					}
					jsonArray.add(jsonObjectAfternoon);
				}
				// 判断是否需要加入今日考勤数据判断
				if (month.equals((String) (todayDateMap.get("month")))) {
					genTodayData(userId, month, year, todayDateMap, jsonArray);
				}
				// 获取该用户自当日以后所在月所有请假信息
				String todayDate = todayDateMap.get("year").toString() + "-"
						+ todayDateMap.get("month").toString() + "-"
						+ todayDateMap.get("date").toString();
				getMonthAllPrivateDay(userId, jsonArray, todayDate, false);
				jsonAllData = new JSONObject();
				jsonAllData.put("events", jsonArray);
				Struts2Util.renderJson(jsonAllData.toString(),
						"encoding:UTF-8", "no-cache:true");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算当日考勤
	 * 
	 * @param userId
	 *            用户ID
	 * @param month
	 *            查询月份
	 * @param year
	 *            查询年份
	 * @param todayDateMap
	 *            今天日期
	 * @param jsonArray
	 *            jsonArray
	 */
	@SuppressWarnings("unchecked")
	private void genTodayData(String userId, String month, String year,
			Map<String, Object> todayDateMap, JSONArray jsonArray) {
		JSONObject jsonObjectMorning;
		JSONObject jsonObjectAfternoon;
		jsonObjectMorning = new JSONObject();
		jsonObjectAfternoon = new JSONObject();
		// 判断当日是否为假期,假期不返回数据
		List holidayList = pteroAttendManager.isHolidayOrNot(year, month,
				todayDateMap.get("date").toString());
		if (holidayList == null || holidayList.size() == 0) {
			jsonObjectMorning.put("title", "上午");
			jsonObjectMorning.put("className", "fc-event-morning");
			jsonObjectMorning.put("start", (String) (todayDateMap.get("year"))
					+ "-" + (String) (todayDateMap.get("month")) + "-"
					+ (String) (todayDateMap.get("date")));
			jsonObjectAfternoon.put("title", "下午");
			jsonObjectAfternoon.put("className", "fc-event-afternoon");
			jsonObjectAfternoon.put("start",
					(String) (todayDateMap.get("year")) + "-"
							+ (String) (todayDateMap.get("month")) + "-"
							+ (String) (todayDateMap.get("date")));
			// 当日根据当天时间进行判断
			long nowTime = Integer.valueOf(DateUtilPtero.getNowTime());
			// 当前时间小于上午上班时间,上午下午都可以请假
			if (nowTime < Long.valueOf(Constants.DBMAP
					.get("MORNING_START_TIME"))) {
				jsonObjectMorning.put("status", "3");
				jsonObjectAfternoon.put("status", "3");
			}
			// 在上午上班时间内,上午九点前存在打卡记录则正常,否则异常,下午可以请假
			// 在中午空闲时间内,上午9点前存在打卡记录则正常,否则异常,下午可以请假
			// 在下午上班时间内,上午9点钱存在打卡记录则正常,否则异常,下午可以请假
			if ((nowTime >= Long.valueOf(Constants.DBMAP
					.get("MORNING_START_TIME")) && nowTime <= Long
					.valueOf(Constants.DBMAP.get("MORNING_END_TIME")))
					|| (nowTime > Long.valueOf(Constants.DBMAP
							.get("MORNING_END_TIME")) && nowTime < Long
							.valueOf(Constants.DBMAP
									.get("AFTERNOON_START_TIME")))
					|| (nowTime > Long.valueOf(Constants.DBMAP
							.get("AFTERNOON_START_TIME")) && nowTime <= Long
							.valueOf(Constants.DBMAP.get("AFTERNOON_END_TIME")))) {
				// 今日考勤数据判断,查询用户第一次打卡信息
				List firstAttendanceList = pteroAttendManager
						.queryTodayFirstAttendanceListByUserId(
								Long.valueOf(userId), year, month,
								(String) (todayDateMap.get("date")));
				HashMap firstMap = (HashMap) firstAttendanceList.get(0);
				// 当日无考勤记录处理
				if (firstMap.get("ATTEND_TIME") == null) {
					jsonObjectMorning.put("status", "1");
				}
				// 该用户存在打卡信息
				else {
					long logTime = Long.valueOf(String.valueOf(firstMap
							.get("ATTEND_TIME")));
					if (logTime <= Long.valueOf(Constants.DBMAP
							.get("MORNING_START_TIME"))) {
						jsonObjectMorning.put("status", "0");
					} else {
						jsonObjectMorning.put("status", "1");
					}
				}
				jsonObjectAfternoon.put("status", "3");
			}
			// 在下午下班时间以后,上午9点前存在打卡记录则正常,否则异常,下午18点后存在打卡记录则正常,否则异常
			if (nowTime > Long.valueOf(Constants.DBMAP
					.get("AFTERNOON_END_TIME"))) {
				// 今日考勤数据判断,查询用户第一次打卡信息
				List firstAttendanceList = pteroAttendManager
						.queryTodayFirstAttendanceListByUserId(
								Long.valueOf(userId), year, month,
								(String) (todayDateMap.get("date")));
				HashMap firstMap = (HashMap) firstAttendanceList.get(0);
				// 当日无考勤记录处理
				if (firstMap.get("ATTEND_TIME") == null) {
					jsonObjectMorning.put("status", "1");
				}
				// 该用户存在打卡信息
				else {
					long logTime = Long.valueOf(String.valueOf(firstMap
							.get("ATTEND_TIME")));
					if (logTime <= Long.valueOf(Constants.DBMAP
							.get("MORNING_START_TIME"))) {
						jsonObjectMorning.put("status", "0");
					} else {
						jsonObjectMorning.put("status", "1");
					}
				}
				// 今日考勤数据判断,查询用户最后一次打卡信息
				List lastAttendanceList = pteroAttendManager
						.queryTodayLastAttendanceListByUserId(
								Long.valueOf(userId), year, month,
								(String) (todayDateMap.get("date")));
				HashMap lastMap = (HashMap) lastAttendanceList.get(0);
				// 当日无考勤记录处理
				if (lastMap.get("ATTEND_TIME") == null) {
					jsonObjectAfternoon.put("status", "1");
				}
				// 该用户存在打卡信息
				else {
					long logTime = Long.valueOf(String.valueOf(lastMap
							.get("ATTEND_TIME")));
					if (logTime >= Long.valueOf(Constants.DBMAP
							.get("AFTERNOON_END_TIME"))) {
						jsonObjectAfternoon.put("status", "0");
					} else {
						jsonObjectAfternoon.put("status", "3");
					}
				}
			}
		}
		// 判断当日是否存在请假信息
		// 请假处理状态map
		List<Map<String, String>> dealStatMapList = null;
		Map<String, String> dealStatMap = null;
		Map<String, String> resultMap = null;
		List<Map<String, Object>> privateDayList = null;
		// 请假状态,请假开始日期,截止日期
		String privateDayStatus = null, privateDayStart = null, privateDayEnd = null;
		// 请假首,末日上下午标志位
		String privateDayStartType = null, privateDayEndType = null;
		String tmpDate = year + "-" + month + "-"
				+ (String) (todayDateMap.get("date"));
		privateDayList = pteroAttendManager.isPrivateDayOrNot(
				Long.valueOf(userId), tmpDate);
		// 存在请假信息
		if (privateDayList != null && privateDayList.size() > 0) {
			dealStatMapList = new ArrayList<Map<String, String>>();
			for (Map<String, Object> onePrivateDayInfo : privateDayList) {
				dealStatMap = new HashMap<String, String>();
				// 取得请假状态
				privateDayStatus = String.valueOf(onePrivateDayInfo
						.get("STATUS"));
				dealStatMap.put("privateDayStatus", privateDayStatus);
				// 取得请假首日期和截止日期
				privateDayStart = (String) (onePrivateDayInfo.get("START_DATE"));
				dealStatMap.put("privateDayStart", privateDayStart);
				privateDayEnd = (String) (onePrivateDayInfo.get("END_DATE"));
				dealStatMap.put("privateDayEnd", privateDayEnd);
				// 取得请假首,末日上下午标志位
				privateDayStartType = (String) (onePrivateDayInfo
						.get("START_TYPE"));
				dealStatMap.put("privateDayStartType", privateDayStartType);
				privateDayEndType = (String) (onePrivateDayInfo.get("END_TYPE"));
				dealStatMap.put("privateDayEndType", privateDayEndType);
				dealStatMapList.add(dealStatMap);
			}
			resultMap = getDealStatMore(dealStatMapList, tmpDate);
			if (resultMap.get("morningDealStat") != null) {
				// 已处理
				if (resultMap.get("morningDealStat").equals("2")) {
					jsonObjectMorning.put("status", "2");
				}
				// 驳回
				else if (resultMap.get("morningDealStat").equals("4")) {
					jsonObjectMorning.put("status", "2");
				}
				// 正在申请
				else if (resultMap.get("morningDealStat").equals("3")) {
					jsonObjectMorning.put("status", "4");
				}
			}
			if (resultMap.get("afternoonDealStat") != null) {
				// 已处理
				if (resultMap.get("afternoonDealStat").equals("2")) {
					jsonObjectAfternoon.put("status", "2");
				}
				// 驳回
				else if (resultMap.get("afternoonDealStat").equals("4")) {
					jsonObjectAfternoon.put("status", "2");
				}
				// 正在申请
				else if (resultMap.get("afternoonDealStat").equals("3")) {
					jsonObjectAfternoon.put("status", "4");
				}
			}
		}
		jsonArray.add(jsonObjectMorning);
		jsonArray.add(jsonObjectAfternoon);
	}

	/**
	 * 根据用户ID,年月查询用户当日考勤
	 * 
	 * @return json格式数据
	 */
	@SuppressWarnings("unchecked")
	public void getUserTodayAttendanceList() {
		try {
			String userId = getRequest().getParameter("user_id");
			String month = getRequest().getParameter("month");
			String year = getRequest().getParameter("year");
			String date = getRequest().getParameter("date");
			Map<String, Object> todayDateMap = DateUtilPtero.getTodayDateMap();
//			logger.error("userID :" + userId + "month :" + month + " year:"
//					+ year + " date :" + date);
			JSONObject jsonAllData = null;
			jsonAllData = new JSONObject();
			jsonAllData.put("user_id", userId);
			// 过滤假期
			List holidayList = pteroAttendManager.isHolidayOrNot(year, month,
					date);
			// 不为假期
			if (holidayList == null || holidayList.size() == 0) {
				// 请假状态,请假开始日期,截止日期
				String privateDayStatus = null, privateDayStart = null, privateDayEnd = null;
				// 请假首,末日上下午标志位
				String privateDayStartType = null, privateDayEndType = null;
				// 请假处理状态map
				List<Map<String, String>> dealStatMapList = null;
				Map<String, String> dealStatMap = null;
				Map<String, String> resultMap = null;
				List<Map<String, Object>> privateDayList = null;
				String tmpDate = year + "-" + month + "-" + date;
				privateDayList = pteroAttendManager.isPrivateDayOrNot(
						Long.valueOf(userId), tmpDate);
				// 存在请假信息
				if (privateDayList != null && privateDayList.size() > 0) {
					dealStatMapList = new ArrayList<Map<String, String>>();
					// 查询用户原始考勤信息
					List orginalAttendance = pteroAttendManager
							.queryUserOnedayAttendanceListByUserId(
									Long.valueOf(userId), year, month, date);
					if (orginalAttendance != null
							&& orginalAttendance.size() > 0) {
						HashMap oneDayInfo = (HashMap) orginalAttendance.get(0);
						String tmpMornintDealStat = (String) oneDayInfo
								.get("MORNING_DEAL_STAT");
						// 无需处理
						if (tmpMornintDealStat.equals("0")) {
							jsonAllData.put("commute_morning", true);
							jsonAllData
									.put("commute_morning_time",
											(String) oneDayInfo
													.get("ATTEND_YEAR")
													+ "-"
													+ (String) oneDayInfo
															.get("ATTEND_MONTH")
													+ "-"
													+ (String) oneDayInfo
															.get("ATTEND_DATE")
													+ " "
													+ DateUtilPtero
															.getFormatTime((String) oneDayInfo
																	.get("ATTEND_FIRST_TIME")));
						}
						// 未处理
						else if (tmpMornintDealStat.equals("1")) {
							jsonAllData.put("commute_morning_" + "0", true);
							jsonAllData.put("commute_morning_date", year + "-"
									+ month + "-" + date);
						}
						String tmpAfternoonDealStat = (String) oneDayInfo
								.get("AFTERNOON_DEAL_STAT");
						// 无需处理
						if (tmpAfternoonDealStat.equals("0")) {
							jsonAllData.put("commute_afternoon", true);
							jsonAllData
									.put("commute_afternoon_time",
											(String) oneDayInfo
													.get("ATTEND_YEAR")
													+ "-"
													+ (String) oneDayInfo
															.get("ATTEND_MONTH")
													+ "-"
													+ (String) oneDayInfo
															.get("ATTEND_DATE")
													+ " "
													+ DateUtilPtero
															.getFormatTime((String) oneDayInfo
																	.get("ATTEND_LAST_TIME")));
						}
						// 未处理
						else if (tmpAfternoonDealStat.equals("1")) {
							jsonAllData.put("commute_afternoon_" + "0", true);
							jsonAllData.put("commute_afternoon_date", year
									+ "-" + month + "-" + date);
						}
					}
					JSONArray leaveJsonArray = new JSONArray();
					for (Map<String, Object> onePrivateDayInfo : privateDayList) {
						dealStatMap = new HashMap<String, String>();
						// 取得请假状态
						privateDayStatus = String.valueOf(onePrivateDayInfo
								.get("STATUS"));
						dealStatMap.put("privateDayStatus", privateDayStatus);
						// 取得请假首日期和截止日期
						privateDayStart = (String) (onePrivateDayInfo
								.get("START_DATE"));
						dealStatMap.put("privateDayStart", privateDayStart);
						privateDayEnd = (String) (onePrivateDayInfo
								.get("END_DATE"));
						dealStatMap.put("privateDayEnd", privateDayEnd);
						// 取得请假首,末日上下午标志位
						privateDayStartType = (String) (onePrivateDayInfo
								.get("START_TYPE"));
						dealStatMap.put("privateDayStartType",
								privateDayStartType);
						privateDayEndType = (String) (onePrivateDayInfo
								.get("END_TYPE"));
						dealStatMap.put("privateDayEndType", privateDayEndType);
						dealStatMapList.add(dealStatMap);
						// 请假信息
						String userDesc = (String) onePrivateDayInfo
								.get("USER_DESC");
						String startDate = (String) onePrivateDayInfo
								.get("START_DATE");
						String endDate = (String) onePrivateDayInfo
								.get("END_DATE");
						String privateDayType = ((BigDecimal) onePrivateDayInfo
								.get("TYPE")).toString();
						JSONObject jsonObject = new JSONObject();
						jsonObject
								.put("leave_time",
										startDate
												+ (privateDayStartType
														.equals("0") ? "上午"
														: "下午")
												+ " 至 "
												+ endDate
												+ (privateDayEndType
														.equals("0") ? "上午"
														: "下午"));
						jsonObject.put("leave_desc", userDesc);
						if (privateDayType.equals("1")) {
							jsonObject.put("leave_type", "病假");
						} else if (privateDayType.equals("2")) {
							jsonObject.put("leave_type", "事假");
						} else if (privateDayType.equals("3")) {
							jsonObject.put("leave_type", "公出");
						} else if (privateDayType.equals("4")) {
							jsonObject.put("leave_type", "其它");
						}
						leaveJsonArray.add(jsonObject);
						jsonAllData.put("leave_infos", leaveJsonArray);
					}
					resultMap = getDealStatMore(dealStatMapList, tmpDate);
					if (resultMap.get("morningDealStat") != null) {
						// 已处理
						if (resultMap.get("morningDealStat").equals("2")) {
							jsonAllData.put("commute_morning_2", true);
						}
						// 驳回
						else if (resultMap.get("morningDealStat").equals("4")) {
							jsonAllData.put("commute_morning_0", true);
						}
						// 正在申请
						else if (resultMap.get("morningDealStat").equals("3")) {
							jsonAllData.put("commute_morning_1", true);
						}
						jsonAllData.put("commute_morning_date", tmpDate);
					} else {
						// 排除正常情况
						if (jsonAllData.get("commute_morning") == null) {
							jsonAllData.put("commute_morning_0", true);
							jsonAllData.put("commute_morning_date", tmpDate);
						}
					}
					if (resultMap.get("afternoonDealStat") != null) {
						// 已处理
						if (resultMap.get("afternoonDealStat").equals("2")) {
							jsonAllData.put("commute_afternoon_2", true);
						}
						// 驳回
						else if (resultMap.get("afternoonDealStat").equals("4")) {
							jsonAllData.put("commute_afternoon_0", true);
						}
						// 正在申请
						else if (resultMap.get("afternoonDealStat").equals("3")) {
							jsonAllData.put("commute_afternoon_1", true);
						}
						jsonAllData.put("commute_afternoon_date", tmpDate);
					} else {
						// 排除正常情况
						if (jsonAllData.get("commute_afternoon") == null) {
							jsonAllData.put("commute_afternoon_0", true);
							jsonAllData.put("commute_afternoon_date", tmpDate);
						}
					}
				}
				// 不存在请假信息
				else {
					String todayDate = todayDateMap.get("year") + "-"
							+ todayDateMap.get("month") + "-"
							+ todayDateMap.get("date");
					// 查询今日特别处理
					if (tmpDate.equals(todayDate)) {
						// 当日根据当天时间进行判断
						long nowTime = Integer.valueOf(DateUtilPtero
								.getNowTime());
						// 当前时间小于上午上班时间,上午下午都可以请假
						if (nowTime < Long.valueOf(Constants.DBMAP
								.get("MORNING_START_TIME"))) {
							jsonAllData.put("commute_afternoon_date", year
									+ "-" + month + "-" + date);
							jsonAllData.put("commute_morning_date", year + "-"
									+ month + "-" + date);
							jsonAllData.put("commute_morning_" + "0", true);
							jsonAllData.put("commute_afternoon_" + "0", true);
						}
						// 在上午上班时间内,上午九点前存在打卡记录则正常,否则异常,下午可以请假
						// 在中午空闲时间内,下午可以请假,上午9点前存在打卡记录则正常,否则异常
						// 在下午上班时间内,上午9点钱存在打卡记录则正常,否则异常,下午可以请假
						if ((nowTime >= Long.valueOf(Constants.DBMAP
								.get("MORNING_START_TIME")) && nowTime <= Long
								.valueOf(Constants.DBMAP
										.get("MORNING_END_TIME")))
								|| (nowTime > Long.valueOf(Constants.DBMAP
										.get("MORNING_END_TIME")) && nowTime < Long
										.valueOf(Constants.DBMAP
												.get("AFTERNOON_START_TIME")))
								|| (nowTime > Long.valueOf(Constants.DBMAP
										.get("AFTERNOON_START_TIME")) && nowTime <= Long
										.valueOf(Constants.DBMAP
												.get("AFTERNOON_END_TIME")))) {
							// 今日考勤数据判断,查询用户第一次打卡信息
							List firstAttendanceList = pteroAttendManager
									.queryTodayFirstAttendanceListByUserId(
											Long.valueOf(userId), year, month,
											(String) (todayDateMap.get("date")));
							HashMap firstMap = (HashMap) firstAttendanceList
									.get(0);
							// 当日无考勤记录处理
							if (firstMap.get("ATTEND_TIME") == null) {
								jsonAllData.put("commute_morning_date", year
										+ "-" + month + "-" + date);
								jsonAllData.put("commute_morning_" + "0", true);
							}
							// 该用户存在打卡信息
							else {
								long logTime = Long.valueOf(String
										.valueOf(firstMap.get("ATTEND_TIME")));
								if (logTime <= Long.valueOf(Constants.DBMAP
										.get("MORNING_START_TIME"))) {
									jsonAllData.put("commute_morning", true);
									jsonAllData
											.put("commute_morning_time",
													year
															+ "-"
															+ month
															+ "-"
															+ date
															+ " "
															+ DateUtilPtero
																	.getFormatTime((String) firstMap
																			.get("ATTEND_TIME")));
								} else {
									jsonAllData.put("commute_morning_date",
											year + "-" + month + "-" + date);
									jsonAllData.put("commute_morning_" + "0",
											true);
								}
							}
							jsonAllData.put("commute_afternoon_" + "0", true);
							jsonAllData.put("commute_afternoon_date", year
									+ "-" + month + "-" + date);
						}
						// 在下午下班时间以后,上午9点前存在打卡记录则正常,否则异常,下午18点后存在打卡记录则正常,否则异常
						if (nowTime > Long.valueOf(Constants.DBMAP
								.get("AFTERNOON_END_TIME"))) {
							// 今日考勤数据判断,查询用户第一次打卡信息
							List firstAttendanceList = pteroAttendManager
									.queryTodayFirstAttendanceListByUserId(
											Long.valueOf(userId), year, month,
											(String) (todayDateMap.get("date")));
							HashMap firstMap = (HashMap) firstAttendanceList
									.get(0);
							// 当日无考勤记录处理
							if (firstMap.get("ATTEND_TIME") == null) {
								jsonAllData.put("commute_morning_date", year
										+ "-" + month + "-" + date);
								jsonAllData.put("commute_morning_" + "0", true);
							}
							// 该用户存在打卡信息
							else {
								long logTime = Long.valueOf(String
										.valueOf(firstMap.get("ATTEND_TIME")));
								if (logTime <= Long.valueOf(Constants.DBMAP
										.get("MORNING_START_TIME"))) {
									jsonAllData.put("commute_morning", true);
									jsonAllData
											.put("commute_morning_time",
													year
															+ "-"
															+ month
															+ "-"
															+ date
															+ " "
															+ DateUtilPtero
																	.getFormatTime((String) firstMap
																			.get("ATTEND_TIME")));
								} else {
									jsonAllData.put("commute_morning_date",
											year + "-" + month + "-" + date);
									jsonAllData.put("commute_morning_" + "0",
											true);
								}
							}
							// 今日考勤数据判断,查询用户最后一次打卡信息
							List lastAttendanceList = pteroAttendManager
									.queryTodayLastAttendanceListByUserId(
											Long.valueOf(userId), year, month,
											(String) (todayDateMap.get("date")));
							HashMap lastMap = (HashMap) lastAttendanceList
									.get(0);
							// 当日无考勤记录处理
							if (lastMap.get("ATTEND_TIME") == null) {
								jsonAllData.put("commute_afternoon_" + "0",
										true);
								jsonAllData.put("commute_afternoon_date", year
										+ "-" + month + "-" + date);
							}
							// 该用户存在打卡信息
							else {
								long logTime = Long.valueOf(String
										.valueOf(lastMap.get("ATTEND_TIME")));
								if (logTime >= Long.valueOf(Constants.DBMAP
										.get("AFTERNOON_END_TIME"))) {
									jsonAllData.put("commute_afternoon", true);
									jsonAllData
											.put("commute_afternoon_time",
													year
															+ "-"
															+ month
															+ "-"
															+ date
															+ " "
															+ DateUtilPtero
																	.getFormatTime((String) lastMap
																			.get("ATTEND_TIME")));
								} else {
									jsonAllData.put("commute_afternoon_" + "0",
											true);
									jsonAllData.put("commute_afternoon_date",
											year + "-" + month + "-" + date);
								}
							}
						}
					}
					// 查询非今日
					else {
						List deptList = pteroAttendManager
								.queryUserOnedayAttendanceListByUserId(
										Long.valueOf(userId), year, month, date);
						if (deptList != null && deptList.size() > 0) {
							HashMap hashMap = (HashMap) deptList.get(0);
							// 上午状态
							String morningDealFlag = (String) hashMap
									.get("MORNING_DEAL_STAT");
							if (morningDealFlag != null) {
								// 未处理
								if (morningDealFlag.equals("1")) {
									jsonAllData.put("commute_morning_" + "0",
											true);
									jsonAllData
											.put("commute_morning_date",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE"));
								}
								// 已处理
								else if (morningDealFlag.equals("2")) {
									jsonAllData.put("commute_morning_" + "2",
											true);
									jsonAllData
											.put("commute_morning_date",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE"));
								}
								// 正在申请
								else if (morningDealFlag.equals("3")) {
									jsonAllData.put("commute_morning_" + "1",
											true);
									jsonAllData
											.put("commute_morning_date",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE"));
								}
								// 无需处理
								else if (morningDealFlag.equals("0")) {
									jsonAllData.put("commute_morning", true);
									jsonAllData
											.put("commute_morning_time",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE")
															+ " "
															+ DateUtilPtero
																	.getFormatTime((String) hashMap
																			.get("ATTEND_FIRST_TIME")));
								}
							}
							// 下午状态
							String afternoonDealFlag = (String) hashMap
									.get("AFTERNOON_DEAL_STAT");
							if (afternoonDealFlag != null) {
								if (afternoonDealFlag.equals("1")) {
									jsonAllData.put("commute_afternoon_" + "0",
											true);
									jsonAllData
											.put("commute_afternoon_date",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE"));
								} else if (afternoonDealFlag.equals("2")) {
									jsonAllData.put("commute_afternoon_" + "2",
											true);
									jsonAllData
											.put("commute_afternoon_date",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE"));
								} else if (afternoonDealFlag.equals("3")) {
									jsonAllData.put("commute_afternoon_" + "1",
											true);
									jsonAllData
											.put("commute_afternoon_date",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE"));
								} else if (afternoonDealFlag.equals("0")) {
									jsonAllData.put("commute_afternoon", true);
									jsonAllData
											.put("commute_afternoon_time",
													(String) hashMap
															.get("ATTEND_YEAR")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_MONTH")
															+ "-"
															+ (String) hashMap
																	.get("ATTEND_DATE")
															+ " "
															+ DateUtilPtero
																	.getFormatTime((String) hashMap
																			.get("ATTEND_LAST_TIME")));
								}
							}
						}
						// 查询今天之后之前的某一天
						else {
							jsonAllData.put("commute_morning_" + "0", true);
							jsonAllData.put("commute_morning_date", year + "-"
									+ month + "-" + date);
							jsonAllData.put("commute_afternoon_" + "0", true);
							jsonAllData.put("commute_afternoon_date", year
									+ "-" + month + "-" + date);
						}
					}
				}
			}
			// 打卡记录
			List t010List = pteroAttendManager.queryUserOnedayAttendanceLog(
					Long.valueOf(userId), year, month, date);
			JSONObject attendLogJsonObjectg = null;
			JSONArray attendLogJsonArray = new JSONArray();
			for (Object oneRecord : t010List) {
				attendLogJsonObjectg = new JSONObject();
				HashMap oneHashMap = (HashMap) oneRecord;
				attendLogJsonObjectg.put(
						"punch_time",
						year
								+ "-"
								+ month
								+ "-"
								+ date
								+ " "
								+ DateUtilPtero
										.getFormatTime((String) oneHashMap
												.get("ATTEND_TIME")));
				attendLogJsonObjectg.put("punch_addr",
						oneHashMap.get("EQUIPMENT_NAME"));
				String equipType = (String) oneHashMap.get("EQUIPMENT_TYPE");
				String inOrOutFlag = (String) oneHashMap.get("IN_OR_OUT_FLAY");
				String inOut = inOrOutFlag.equals("1") ? "进" : "出";
				String eType = equipType.equals("1") ? "门禁" : "闸机";
				attendLogJsonObjectg.put("punch_tag", inOut + " " + eType);
				attendLogJsonArray.add(attendLogJsonObjectg);
			}
			jsonAllData.put("punch_infos", attendLogJsonArray);
			Struts2Util.renderJson(jsonAllData.toString(), "encoding:UTF-8",
					"no-cache:true");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置节假日信息
	 */
	public void getHolidays() {
		// 年份
		String year = getRequest().getParameter("year");
		// 月份
		String month = getRequest().getParameter("month");
		List list = pteroAttendManager.getHolidays(year, month);
		JSONObject allData = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Object oneRecord : list) {
			HashMap hashMap = (HashMap) oneRecord;
			// year
			String tmpYear = (String) hashMap.get("YEAR");
			// month
			String tmpMonth = (String) hashMap.get("MONTH");
			// FROM_DATE
			String FROM_DATE = (String) hashMap.get("FROM_DATE");
			// TO_DATE
			// String TO_DATE = (String) hashMap.get("TO_DATE");
			// HOL_DESC
			String HOL_DESC = (String) hashMap.get("HOL_DESC");
			JSONObject oneJsonObject = new JSONObject();
			oneJsonObject.put("id", tmpYear + "-" + tmpMonth + "-" + FROM_DATE);
			oneJsonObject.put("start", tmpYear + "-" + tmpMonth + "-"
					+ FROM_DATE);
			oneJsonObject
					.put("end", tmpYear + "-" + tmpMonth + "-" + FROM_DATE);
			oneJsonObject.put("title", HOL_DESC);
			oneJsonObject.put("className", "fc-event-holiday");
			jsonArray.add(oneJsonObject);
		}
		allData.put("events", jsonArray);
		Struts2Util.renderJson(allData.toString(), "encoding:UTF-8",
				"no-cache:true");
		return;
	}

	/**
	 * 处理请假信息
	 * 
	 * @param paraList
	 *            请假信息记录数据
	 * @param nowDate
	 *            当日日期
	 * @return 当日请假状态
	 */
	private Map<String, String> getDealStatMore(
			List<Map<String, String>> paraList, String nowDate) {
		// return deal stat map
		HashMap<String, String> dealSHashMap = new HashMap<String, String>();
		// 对所有请假记录表中的数据进行分析
		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
		for (Map<String, String> onePara : paraList) {
			Map<String, String> oneResultMap = getDealStat(onePara, nowDate);
			resultMapList.add(oneResultMap);
		}
		// 处理分析结果
		for (Map<String, String> oneResultMap : resultMapList) {
			if (oneResultMap.get("morningDealStat") != null) {
				dealSHashMap.put("morningDealStat",
						oneResultMap.get("morningDealStat"));
			}
			if (oneResultMap.get("afternoonDealStat") != null) {
				dealSHashMap.put("afternoonDealStat",
						oneResultMap.get("afternoonDealStat"));
			}
		}
		return dealSHashMap;
	}

	/**
	 * 返回用户所在月份所有请假信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param jsonArray
	 *            jsonArray
	 * @param todayDate
	 *            起始日期
	 */
	@SuppressWarnings("unchecked")
	private void getMonthAllPrivateDay(String userId, JSONArray jsonArray,
			String startDate, boolean containTodayFlag) {
		// 如果预请假半天的特殊处理,暂时封掉
		// int now = Integer.valueOf(DateUtilPtero.getNowYYMMDD());
		JSONObject jsonObjectMorning;
		JSONObject jsonObjectAfternoon;
		String[] days = DateUtilPtero.getMonthRestDays(startDate,
				containTodayFlag);
		// 请假状态,请假开始日期,截止日期
		String privateDayStatus = null, privateDayStart = null, privateDayEnd = null;
		// 请假首,末日上下午标志位
		String privateDayStartType = null, privateDayEndType = null;
		// 请假处理状态map
		List<Map<String, String>> dealStatMapList = null;
		Map<String, String> dealStatMap = null;
		Map<String, String> resultMap = null;
		List<Map<String, Object>> privateDayList = null;
		for (String oneDate : days) {
			// 判断当日是否为假期,假期不显示
			String[] tmpDate = oneDate.split("-");
			// int tmpOneDate = Integer.valueOf(tmpDate[0] + tmpDate[1]
			// + tmpDate[2]);
			List tmpList = pteroAttendManager.isHolidayOrNot(tmpDate[0],
					tmpDate[1], tmpDate[2]);
			if (tmpList != null && tmpList.size() > 0) {
				continue;
			}
			privateDayList = pteroAttendManager.isPrivateDayOrNot(
					Long.valueOf(userId), oneDate);
			// 存在请假信息
			if (privateDayList != null && privateDayList.size() > 0) {
				dealStatMapList = new ArrayList<Map<String, String>>();
				for (Map<String, Object> onePrivateDayInfo : privateDayList) {
					dealStatMap = new HashMap<String, String>();
					// 取得请假状态
					privateDayStatus = String.valueOf(onePrivateDayInfo
							.get("STATUS"));
					dealStatMap.put("privateDayStatus", privateDayStatus);
					// 取得请假首日期和截止日期
					privateDayStart = (String) (onePrivateDayInfo
							.get("START_DATE"));
					dealStatMap.put("privateDayStart", privateDayStart);
					privateDayEnd = (String) (onePrivateDayInfo.get("END_DATE"));
					dealStatMap.put("privateDayEnd", privateDayEnd);
					// 取得请假首,末日上下午标志位
					privateDayStartType = (String) (onePrivateDayInfo
							.get("START_TYPE"));
					dealStatMap.put("privateDayStartType", privateDayStartType);
					privateDayEndType = (String) (onePrivateDayInfo
							.get("END_TYPE"));
					dealStatMap.put("privateDayEndType", privateDayEndType);
					dealStatMapList.add(dealStatMap);
				}
				resultMap = getDealStatMore(dealStatMapList, oneDate);
				if (resultMap.get("morningDealStat") != null) {
					jsonObjectMorning = new JSONObject();
					jsonObjectMorning.put("title", "上午");
					jsonObjectMorning.put("className", "fc-event-morning");
					jsonObjectMorning.put("start", oneDate);
					// 已处理
					if (resultMap.get("morningDealStat").equals("2")) {
						jsonObjectMorning.put("status", "2");
					}
					// 正在申请
					else if (resultMap.get("morningDealStat").equals("3")) {
						jsonObjectMorning.put("status", "4");
					}
					// 驳回
					else if (resultMap.get("morningDealStat").equals("1")) {
						jsonObjectMorning.put("status", "1");
					}
					jsonArray.add(jsonObjectMorning);
				} else {
					// if (tmpOneDate <= now) {
					jsonObjectMorning = new JSONObject();
					jsonObjectMorning.put("title", "上午");
					jsonObjectMorning.put("className", "fc-event-morning");
					jsonObjectMorning.put("start", oneDate);
					jsonObjectMorning.put("status", "1");
					jsonArray.add(jsonObjectMorning);
					// }
				}
				if (resultMap.get("afternoonDealStat") != null) {
					jsonObjectAfternoon = new JSONObject();
					jsonObjectAfternoon.put("title", "下午");
					jsonObjectAfternoon.put("className", "fc-event-afternoon");
					jsonObjectAfternoon.put("start", oneDate);
					// 已处理
					if (resultMap.get("afternoonDealStat").equals("2")) {
						jsonObjectAfternoon.put("status", "2");
					}
					// 正在申请
					else if (resultMap.get("afternoonDealStat").equals("3")) {
						jsonObjectAfternoon.put("status", "4");
					}
					// 驳回
					else if (resultMap.get("afternoonDealStat").equals("4")) {
						jsonObjectAfternoon.put("status", "1");
					}
					jsonArray.add(jsonObjectAfternoon);
				} else {
					// if (tmpOneDate <= now) {
					jsonObjectAfternoon = new JSONObject();
					jsonObjectAfternoon.put("title", "下午");
					jsonObjectAfternoon.put("className", "fc-event-afternoon");
					jsonObjectAfternoon.put("start", oneDate);
					jsonObjectAfternoon.put("status", "1");
					jsonArray.add(jsonObjectAfternoon);
					// }
				}
			}
		}
	}

	/**
	 * 返回请假处理状态
	 * 
	 * @param para
	 *            算法入口参数
	 * @param nowDate
	 *            当前日期
	 * @return 考勤处理状态
	 */
	private Map<String, String> getDealStat(Map<String, String> para,
			String nowDate) {
		// now date
		int now = Integer.valueOf(nowDate.replace("-", ""));
		// return deal stat map
		HashMap<String, String> dealSHashMap = new HashMap<String, String>();
		// 请假状态,请假开始日期,截止日期
		String privateDayStatus = para.get("privateDayStatus");
		String privateDayStart = para.get("privateDayStart");
		int startDate = Integer.valueOf(privateDayStart.replace("-", ""));
		String privateDayEnd = para.get("privateDayEnd");
		int endDate = Integer.valueOf(privateDayEnd.replace("-", ""));
		// 请假首,末日上下午标志位
		String privateDayStartType = para.get("privateDayStartType");
		String privateDayEndType = para.get("privateDayEndType");
		// 请假跨度一天特殊处理
		if (startDate == endDate) {
			if (now == startDate) {
				// 请上午半天
				if (privateDayStartType.equals("0")
						&& privateDayEndType.equals("0")) {
					// 申请通过
					if (privateDayStatus.equals("1")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DEALLED);
					}
					// 申请驳回
					else if (privateDayStatus.equals("2")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.REFUSED);
					}
					// 申请中
					else {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DOING_NOW);
					}
				}
				// 请全天
				else if (privateDayStartType.equals("0")
						&& privateDayEndType.equals("1")) {
					// 申请通过
					if (privateDayStatus.equals("1")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DEALLED);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DEALLED);
					}
					// 申请驳回
					else if (privateDayStatus.equals("2")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.REFUSED);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.REFUSED);
					}
					// 申请中
					else {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DOING_NOW);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DOING_NOW);
					}
				}
				// 请下午半天
				else if (privateDayStartType.equals("1")
						&& privateDayEndType.equals("1")) {
					// 申请通过
					if (privateDayStatus.equals("1")) {
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DEALLED);
					}
					// 申请驳回
					else if (privateDayStatus.equals("2")) {
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.REFUSED);
					}
					// 申请中
					else {
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DOING_NOW);
					}
				}
			}
		}
		// 请假天数跨度大于一天处理
		else {
			// 当前时间正好为请假起始时间
			if (startDate == now) {
				// 请全天
				if (privateDayStartType.equals("0")) {
					// 申请通过
					if (privateDayStatus.equals("1")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DEALLED);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DEALLED);
					}
					// 申请驳回
					else if (privateDayStatus.equals("2")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.REFUSED);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.REFUSED);
					}
					// 申请中
					else {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DOING_NOW);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DOING_NOW);
					}
				}
				// 请下午半天
				else if (privateDayStartType.equals("1")) {
					// 申请通过
					if (privateDayStatus.equals("1")) {
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DEALLED);
					}
					// 申请驳回
					else if (privateDayStatus.equals("2")) {
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.REFUSED);
					}
					// 申请中
					else {
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DOING_NOW);
					}
				}
			}
			// 当前时间正好为请假截止时间
			if (endDate == now) {
				// 请全天
				if (privateDayEndType.equals("1")) {
					// 申请通过
					if (privateDayStatus.equals("1")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DEALLED);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DEALLED);
					}
					// 申请驳回
					else if (privateDayStatus.equals("2")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.REFUSED);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.REFUSED);
					}
					// 申请中
					else {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DOING_NOW);
						dealSHashMap.put("afternoonDealStat",
								PteroConstants.DOING_NOW);
					}
				}
				// 请上午半天
				else if (privateDayEndType.equals("0")) {
					// 申请通过
					if (privateDayStatus.equals("1")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DEALLED);
					}
					// 申请驳回
					else if (privateDayStatus.equals("2")) {
						dealSHashMap.put("morningDealStat",
								PteroConstants.REFUSED);
					}
					// 申请中
					else {
						dealSHashMap.put("morningDealStat",
								PteroConstants.DOING_NOW);
					}
				}

			}
			// 当前时间在请假起始时间与截止时间之间
			if (startDate < now && now < endDate) {
				// 申请通过
				if (privateDayStatus.equals("1")) {
					dealSHashMap.put("morningDealStat", PteroConstants.DEALLED);
					dealSHashMap.put("afternoonDealStat",
							PteroConstants.DEALLED);
				}
				// 申请驳回
				else if (privateDayStatus.equals("2")) {
					dealSHashMap.put("morningDealStat", PteroConstants.REFUSED);
					dealSHashMap.put("afternoonDealStat",
							PteroConstants.REFUSED);
				}
				// 申请中
				else {
					dealSHashMap.put("morningDealStat",
							PteroConstants.DOING_NOW);
					dealSHashMap.put("afternoonDealStat",
							PteroConstants.DOING_NOW);
				}
			}
		}
		return dealSHashMap;
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4889814407793730023L;

	/**
	 * DAO
	 */
	private PteroAttendManager pteroAttendManager;

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	/**
	 * @return the pteroAttendManager
	 */
	public PteroAttendManager getPteroAttendManager() {
		return pteroAttendManager;
	}

	/**
	 * @param pteroAttendManager
	 *            the pteroAttendManager to set
	 */
	public void setPteroAttendManager(PteroAttendManager pteroAttendManager) {
		this.pteroAttendManager = pteroAttendManager;
	}

}
