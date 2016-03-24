package com.cosmosource.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间处理工具类
 * 
 * @author ptero
 * 
 */
public final class DateUtilPtero {

	/**
	 * FORMAT_1
	 */
	private static final SimpleDateFormat FORMAT_1 = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * FORMAT_2
	 */
	private static final SimpleDateFormat FORMAT_2 = new SimpleDateFormat(
			"HHmmss");

	/**
	 * FORMAT_3
	 */
	private static final SimpleDateFormat FORMAT_3 = new SimpleDateFormat(
			"yyyyMMdd");

	/**
	 * 获取昨天日期
	 * 
	 * @return 昨天日期
	 */
	public static String getYesterday() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, -1);
		return FORMAT_1.format(calendar.getTime());

	}

	/**
	 * 或者昨日日期Map
	 * 
	 * @return Map
	 */
	public static Map<String, Object> getYesterdayDateMap() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, -1);
		String[] dateInfo = FORMAT_1.format(calendar.getTime()).split("-");
		Map<String, Object> dateMap = new HashMap<String, Object>();
		dateMap.put("year", dateInfo[0]);
		dateMap.put("month", dateInfo[1]);
		dateMap.put("date", dateInfo[2]);
		return dateMap;
	}

	/**
	 * 或者今日日期Map
	 * 
	 * @return Map
	 */
	public static Map<String, Object> getTodayDateMap() {
		GregorianCalendar calendar = new GregorianCalendar();
		String[] dateInfo = FORMAT_1.format(calendar.getTime()).split("-");
		Map<String, Object> dateMap = new HashMap<String, Object>();
		dateMap.put("year", dateInfo[0]);
		dateMap.put("month", dateInfo[1]);
		dateMap.put("date", dateInfo[2]);
		return dateMap;
	}

	/**
	 * 获取时间
	 * 
	 * @param time
	 *            时间
	 * @return 处理过的时间
	 */
	public static String getFormatTime(String time) {
		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":"
				+ time.substring(4);
	}

	/**
	 * 获取两个日期内所有日期
	 * 
	 * @param from
	 *            开始日期
	 * @param to
	 *            截止日期
	 * @return 两个日期之间的所有日期
	 */
	public static String[] getHolidays(String from, String to) {
		String[] days = null;
		try {
			long toDay = FORMAT_1.parse(to).getTime();
			long fromDay = FORMAT_1.parse(from).getTime();
			long i = (toDay - fromDay) / (1000 * 60 * 60 * 24);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(FORMAT_1.parse(from));
			days = new String[Integer.valueOf(String.valueOf(i)) + 1];
			days[0] = from;
			for (int j = 1; j <= i; j++) {
				calendar.add(Calendar.DATE, 1);
				days[j] = FORMAT_1.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return days;
	}

	/**
	 * 获取指定日期下当月所有剩下日期
	 * 
	 * @param date
	 *            指定日期
	 * @return 当月剩下日期(包含date)
	 */
	public static String[] getMonthRestDays(String date,
			boolean containTodayFlag) {
		String[] dateInfo = date.split("-");
		Calendar calendar = new GregorianCalendar(Integer.valueOf(dateInfo[0]),
				Integer.valueOf(dateInfo[1]) - 1, Integer.valueOf(dateInfo[2]));
		int rest = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
				- Integer.valueOf(dateInfo[2]);
		String[] days = null;
		if (containTodayFlag) {
			days = new String[rest + 1];
			days[0] = date;
			for (int i = 0; i < rest; i++) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				days[i + 1] = FORMAT_1.format(calendar.getTime());
			}
		} else {
			days = new String[rest];
			for (int i = 0; i < rest; i++) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				days[i] = FORMAT_1.format(calendar.getTime());
			}
		}
		return days;
	}

	public static void main(String[] args) {
		// String time = "080910";
		// System.out.println(getFormatTime(time));
		// getHolidays("2014-04-22", "2014-04-26");
		// getMonthRestDays("2014-05-02", false);
		System.out.println(getNowTime());
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getToday() {
		GregorianCalendar calendar = new GregorianCalendar();
		return FORMAT_1.format(calendar.getTime());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		GregorianCalendar calendar = new GregorianCalendar();
		return FORMAT_2.format(calendar.getTime());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getNowYYMMDD() {
		GregorianCalendar calendar = new GregorianCalendar();
		return FORMAT_3.format(calendar.getTime());
	}

	/**
	 * 获取日期格式
	 * 
	 * @param date
	 *            date
	 * @return date
	 */
	public static String getFormatDate(Date date) {
		return FORMAT_1.format(date);
	}

}
