package com.cosmosource.app.attendance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.BuildingAttendancers;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.utils.DateUtilPtero;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.DateUtil;

public class PteroAttendManager extends BaseManager {
	/**
	 * 根据年月及用户ID查询用户当月考勤信息
	 * 
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @return 考勤结果集
	 */
	@SuppressWarnings("rawtypes")
	public List queryUserMonthAttendanceListByUserId(long uid, String year,
			String month) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		paramMap.put("year", year);
		paramMap.put("month", month);
		try {
			list = sqlDao.findList("AttendanceSQL.getUserMonthAttendanceList",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 查询用户当日考勤明细
	 * 
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @param date
	 *            查询日期
	 * @return 考勤结果集
	 */
	@SuppressWarnings("rawtypes")
	public List queryUserOnedayAttendanceListByUserId(long uid, String year,
			String month, String date) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		paramMap.put("year", year);
		paramMap.put("month", month);
		paramMap.put("date", date);
		try {
			list = sqlDao.findList("AttendanceSQL.getUserOnedayAttendanceList",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 查询用户最后一次打卡记录信息
	 * 
	 * @param uid
	 *            用户ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param date
	 *            日
	 * @return 打卡信息
	 */
	@SuppressWarnings("rawtypes")
	public List queryTodayLastAttendanceListByUserId(long uid, String year,
			String month, String date) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		paramMap.put("year", year);
		paramMap.put("month", month);
		paramMap.put("date", date);
		try {
			list = sqlDao.findList("AttendanceSQL.getUserLastAttendanceList",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 查询用户某日打卡流水
	 * 
	 * @param uid
	 *            用户ID
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @param date
	 *            日
	 * @return 打卡信息
	 */
	@SuppressWarnings("rawtypes")
	public List queryUserOnedayAttendanceLog(long uid, String year,
			String month, String date) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		paramMap.put("year", year);
		paramMap.put("month", month);
		paramMap.put("date", date);
		try {
			list = sqlDao.findList("AttendanceSQL.getUserOnedayAttendanceLog",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 设置日期
	 * 
	 * @param from
	 *            开始日期
	 * @param to
	 *            截止日期
	 * @param desc
	 *            假期描述
	 * @return 日期列表
	 */
	public boolean setHolidays(String from, String to, String desc) {

		Map<String, Object> paramMap = null;
		// 计算日期
		String[] days = DateUtilPtero.getHolidays(from, to);
		String[] dateInfo = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (String oneday : days) {
			paramMap = new HashMap<String, Object>();
			dateInfo = oneday.split("-");
			paramMap.put("year", dateInfo[0]);
			paramMap.put("month", dateInfo[1]);
			paramMap.put("date", dateInfo[2]);
			paramMap.put("desc", desc);
			list.add(paramMap);
		}
		try {
			sqlDao.batchInsert("AttendanceSQL.setHolidays", list);
			// insertBySql("AttendanceSQL.setHolidays", paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 设置日期
	 * 
	 * @param from
	 *            开始日期
	 * @param to
	 *            截止日期
	 * @param desc
	 *            假期描述
	 * @return 日期列表
	 */
	public boolean delHolidays(String from, String to) {

		Map<String, Object> paramMap = null;
		// 计算日期
		String[] days = DateUtilPtero.getHolidays(from, to);
		String[] dateInfo = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (String oneday : days) {
			paramMap = new HashMap<String, Object>();
			dateInfo = oneday.split("-");
			paramMap.put("year", dateInfo[0]);
			paramMap.put("month", dateInfo[1]);
			paramMap.put("date", dateInfo[2]);
			list.add(paramMap);
		}
		try {
			sqlDao.batchDelete("AttendanceSQL.delHolidays", list);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return 大厦考勤员姓名
	 */
	@SuppressWarnings("unchecked")
	public List<Users> getAttendancers() {
		Object o = dao.findById(BuildingAttendancers.class, 2l);
		if (o != null) {
			return (List<Users>) dao
					.findByHQL("select u from Users u where u.userid in ("
							+ ((BuildingAttendancers) o).getUserid() + ")");
		}
		return new ArrayList<Users>();
	}

	/**
	 * 获取用户第一次打卡记录
	 * 
	 * @param uid
	 *            用户ID
	 * @param year
	 *            查询年份
	 * @param month
	 *            查询月份
	 * @param date
	 *            查询日期
	 * @return 考勤结果集
	 */
	@SuppressWarnings("rawtypes")
	public List queryTodayFirstAttendanceListByUserId(long uid, String year,
			String month, String date) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		paramMap.put("year", year);
		paramMap.put("month", month);
		paramMap.put("date", date);
		try {
			list = sqlDao.findList("AttendanceSQL.getUserFirstAttendanceList",
					paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 获取假期信息
	 * 
	 * @param year
	 *            获取年份
	 * @param month
	 *            获取月份
	 * @return 假期信息
	 */
	@SuppressWarnings("rawtypes")
	public List getHolidays(String year, String month) {
		List list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("year", year);
		paramMap.put("month", month);
		try {
			list = sqlDao.findList("AttendanceSQL.getHolidays", paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 设置上班时间
	 * 
	 * @param fromTime
	 *            上午上班时间
	 * 
	 * @param toTime
	 *            下午上班时间
	 * 
	 * @return true-success or false
	 */
	public boolean setTimes(String fromTime, String toTime) {
		try {
			deleteBySql("AttendanceSQL.delTimes", null);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			return false;
		}
		String[] morningInfo = fromTime.split("-");
		String[] afternoonInfo = toTime.split("-");

		Map<String, Object> paramMap = null;
		// 计算日期
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		paramMap = new HashMap<String, Object>();
		paramMap.put("ms", morningInfo[0].replace(":", "") + "00");
		paramMap.put("me", morningInfo[1].replace(":", "") + "00");
		paramMap.put("as", afternoonInfo[0].replace(":", "") + "00");
		paramMap.put("ae", afternoonInfo[1].replace(":", "") + "00");
		list.add(paramMap);
		try {
			sqlDao.batchInsert("AttendanceSQL.setTimes", list);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 获取工作时间接口
	 * 
	 * @return 工作时间
	 */
	@SuppressWarnings("rawtypes")
	public List getTime() {
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.getTimes", null);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
		}
		return list;
	}

	/**
	 * 查询当前日期是否为假日
	 * 
	 * @return 假日列表
	 */
	@SuppressWarnings("rawtypes")
	public List isHolidayOrNot(String year, String month, String date) {
		Map<String, Object> paramMap = null;
		paramMap = new HashMap<String, Object>();
		paramMap.put("year", year);
		paramMap.put("month", month);
		paramMap.put("date", date);
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.isHolidayOrNot", paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			return null;
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List isPrivateDayOrNot(long userId, String date) {
		Map<String, Object> paramMap = null;
		paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("date", date);
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.isPrivateDay", paramMap);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			return null;
		}
		return list;
	}
	
	/**
	 * 查询工作时间设置
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getWorkingTimes() {
		List list = null;
		try {
			list = sqlDao.findList("AttendanceSQL.getWorkingTimeSettings", null);
		} catch (Exception e) {
			logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " : "
					+ e.getMessage());
			return null;
		}
		return list;
	}
}
