package com.cosmosource.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期处理工具类
 * 见main方法中的处理方式
 * @author WXJ 
 */
public class DateUtil  {
	
	public DateUtil() {
    	super();
    }
    
	/**
	 * 当前系统时间
	 */
	public static Date getSysDate() {
		return new Date();
	}
	/**
	 * 当前系统时间
	 */
	public static Calendar getSysCalendar() {
		return Calendar.getInstance();
	}
	
	/**
	 * 当前月第一天
	 * @创建时间： 2010-9-15 下午19:09:04 
	 * @作者： tt
	 */
	public static Date getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}
	/**
	 * 当前月最后一天
	 * @创建时间： 2011-03-06 下午15:11:02 
	 * @作者： WXJ
	 */
	public static Date getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.roll(Calendar.DAY_OF_MONTH, -1);  
		return calendar.getTime();
	}
	/**
	 * 当前月最后一天字符型
	 * @创建时间： 2011-03-06 下午15:11:02 
	 * @作者： WXJ
	 */
	public static String getStrMonthLastDay() {
		return date2Str(getMonthLastDay(), "yyyy-MM-dd");
	}
	
	/**
	 * 当前月第一天字符型
	 * @创建时间： 2010-11-15 下午15:02:22 
	 * @作者：WXJ
	 */
	public static String getStrMonthFirstDay() {
		return date2Str(getMonthFirstDay(), "yyyy-MM-dd");
	}
	/**
	 * 当前月加/减间隔参数月最后一天
	 * @创建时间： 2010-11-15 下午15:02:22 
	 * @作者：WXJ
	 */
	public static Date getMonthLastDay(int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+interval);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.roll(Calendar.DAY_OF_MONTH,-1);
		return calendar.getTime();
	}
	
	/**
	 * 当前月加/减间隔参数月最后一天字符型
	 * @创建时间： 2010-11-15 下午15:02:22 
	 * @作者：WXJ
	 */
	public static String getStrMonthLastDay(int interval) {
		return date2Str(getMonthLastDay(interval), "yyyy-MM-dd");
	}
	
	/**
	 * 当前月加/减间隔参数月第一天
	 * @创建时间： 2010-11-24 下午16:15:04 
	 * @作者： tt
	 */
	public static Date getMonthFirstDay(int interval) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+interval);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}
	
	/**
	 * 当前月加/减间隔参数月第一天字符型
	 * @创建时间：  2010-11-24 下午16:15:04 
	 * @作者：tt
	 */
	public static String getStrMonthFirstDay(int interval) {
		return date2Str(getMonthFirstDay(interval), "yyyy-MM-dd");
	}
	
    /**
     * 返回一个当前的时间，并按格式转换为字符串
     * 
     * @return String
     */
    public static String getDateTime(String... pattern){   

//        GregorianCalendar gcNow = new GregorianCalendar();
//        return DateFormatUtils.format(gcNow,pattern);
    	String sPtn = "yyyy-MM-dd HH:mm:ss";
		if (pattern != null&&pattern.length>0) {
			sPtn = pattern[0];
		}	
        SimpleDateFormat  formatter = new SimpleDateFormat (sPtn);
        return formatter.format(new Date());
    }	   
    /**
     * 将字符串转成日期
     * @param String str
     * @param String pattern
     * @return Date
     */
    public static Date str2Date(String str,String pattern) {    	
    	try {
			return org.apache.commons.lang.time.DateUtils.parseDate(str, new String[]{pattern});
		} catch (ParseException e) {
			return null;
		} 
    }
    /**
     * 将字符串转成日期
     * @param String str
     * @param String[] patterns
     * @return Date
     */
    public static Date str2Date(String str,String[] patterns) {    	
    	try {
			return org.apache.commons.lang.time.DateUtils.parseDate(str, patterns);
		} catch (ParseException e) {
			return null;
		} 
    } 

    /**
     * 将日期转成字符串
     * @param Date date
     * @param String[] patterns
     * @return String
     */
    public static String date2Str(Date date,String pattern) { 
//    	return DateFormatUtils.format(date, pattern);
    	try {
    		if(date!=null){
    	        SimpleDateFormat  formatter = new SimpleDateFormat (pattern);
    	        return formatter.format(date);
    		}else{
    			return "";
    		}
		} catch (Exception e) {
			return "";
		} 
    } 
    
   
    /**
     * 将日期转成字符串
     * @param Date date
     * @param String[] patterns
     * @return String
     */
    public static Date strJust2Date(String date,String pattern) { 
//    	return DateFormatUtils.format(date, pattern);
    	try {
    		SimpleDateFormat  formatter = new SimpleDateFormat (pattern);
    		if(date!=null && date.length() > 0){
    	        return formatter.parse(date);
    		}else{
    			return null;
    		}
		} catch (Exception e) {
			return null;
		} 
    } 
    
    /**
     * 返回当前年的年号
     * @return int
     */
    public static int getYear(){
        GregorianCalendar gcNow = new GregorianCalendar();
        return gcNow.get(GregorianCalendar.YEAR);
    }
    /**
     * 返回本月月号：从 1 开始
     * @return int
     */
    public static int getMonth(){
        GregorianCalendar gcNow = new GregorianCalendar();
        return gcNow.get(GregorianCalendar.MONTH)+1;
    }

    /**
     * 返回今天是本月的第几天
     * @return int 从1开始
     */
    public static int getDayOfMonth(){
        GregorianCalendar gcNow = new GregorianCalendar();
        return gcNow.get(GregorianCalendar.DAY_OF_MONTH);
    }
	/**
	 * 当前月字符型
	 * @创建时间： 2010-11-15 下午15:02:22 
	 * @作者：WXJ
	 */
	public static String getStrMonth(int... interval) {
		Calendar calendar = Calendar.getInstance();
		if(interval!=null&&interval.length>0){
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+interval[0]);
		}
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return date2Str(calendar.getTime(), "yyyyMM");
	}
	/**
	 * 日期字符型
	 * @创建时间： 2010-11-15 下午15:02:22 
	 * @作者：WXJ
	 */
	public static String getStrDate(int... interval) {
		Calendar c = new GregorianCalendar();
		if(interval!=null){
			c.add(Calendar.DATE, interval[0]);
		}
    	String sPtn = "yyyy-MM-dd";
		return (new SimpleDateFormat(sPtn)).format(c.getTime());
	}
	/**
	 * 取传入月的最后一天
	 * @创建时间： 2011-03-08 下午16:15:04 
	 * @作者： WXJ
	 */
	public static String getStrMonthLastDay(String month) {
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(str2Date(month+"01","yyyyMMdd"));
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		return date2Str(calendar.getTime(), "yyyyMMdd");
	}
	/**
	* @描述: 计算两个日期差的天数
	* @作者：WXJ
	* @日期：2011-12-1 上午11:03:17
	* @param startTime
	* @param endTime
	* @param pattern
	* @return
	* @return long
	 */
	public static long getDiffDay(String startTime, String endTime, String pattern) {
		
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//		long nh = 1000 * 60 * 60;// 一小时的毫秒数
//		long nm = 1000 * 60;// 一分钟的毫秒数
//		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		// 获得两个时间的毫秒时间差异
		diff = DateUtil.str2Date(endTime, pattern).getTime() - DateUtil.str2Date(startTime, pattern).getTime();
		long day = diff / nd;// 计算差多少天
//		long hour = diff % nd / nh;// 计算差多少小时
//		long min = diff % nd % nh / nm;// 计算差多少分钟
//		long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//		// 输出结果
//		System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟"
//				+ sec + "秒。");
		return day;
	}
	/**
	 * 日期字符型
	 * @创建时间： 2010-11-15 下午15:02:22 
	 * @作者：WXJ
	 */
	public static String getPreDate(String sPtn) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, -1);
		return (new SimpleDateFormat(sPtn)).format(c.getTime());
	}
	
	/**
	 * 日期字符型
	 * @创建时间： 2014-01-16 
	 * @作者：ChunJing
	 */
	public static Date getNextDate(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
	
	/**
	 * 说明：把String转化为日期
	 * @param time
	 * @return
	 */
	public static Date StringToDate(String time){
		try {
			SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StringUtil.isNotBlank(time)){
				return dataFormat.parse(time);
			}else{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 说明：把String转化为日期
	 * @param time
	 * @return
	 */
	public static Date StringToDateFormat(String time){
		try {
			SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(StringUtil.isNotBlank(time)){
				return dataFormat.parse(time);
			}else{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	* @描述: 计算两个日期间隔天集合
	* @作者：ChunJing
	* @日期：2014-01-16 上午11:03:17
	* @param startTime
	* @param endTime
	* @param pattern
	* @return
	* @return long
	 */
	public static List<Date> getDiffDaysList(String startTime, String endTime, String pattern,boolean crossNight){
		List<Date> list = new ArrayList<Date>();
		GregorianCalendar gcNow = new GregorianCalendar();
		
		//日期相差天数
		long count = getDiffDay(startTime, endTime, pattern);
		if (!crossNight) {
			count++;
		}
		
		//相差的每个日期放到 list 中
		for (int i = 0; i < count; i++) {
			gcNow.setTime(str2Date(startTime, pattern));
			gcNow.add(GregorianCalendar.DATE, i);
			//如果当前日期不等于结束日期则添加元素
//			if (gcNow.getTime().compareTo(str2Date(endTime, pattern)) != 0) {
				list.add(gcNow.getTime());
//			}
		}
		return list;
	}
	
    public static void main(String[] args) {    	
//    	System.out.println(DateFormatUtils.SMTP_DATETIME_FORMAT);
    	
//    	//日期及时间
//		System.out.println(DateUtil.getDateTime("yyyy年MM月dd日 HH时mm分ss秒"));
//    	//日期
//		System.out.println(DateUtil.getDateTime("yyyy年MM月dd日"));
//    	//时间
//		System.out.println(DateUtil.getDateTime("HH时mm分ss秒"));		
//    	//将字符串转成日期
//		System.out.println(DateUtil.str2Date("20100123", "yyyyMMdd"));
//    	//将日期转成字符串
//		System.out.println(DateUtil.date2Str(new Date(), "yyyyMMdd"));
//		
//		System.out.println(DateUtil.getYear()+"年"+DateUtil.getMonth()+"月"+DateUtil.getDayOfMonth()+"日");
//    	System.out.println(DigestUtils.md5Hex("123456"));
//    	System.out.println(DateUtil.getPreDate("yyyyMMdd"));
//    	System.out.println(date2Str(getSysDate(), "yyyy/MM/dd HH:mm:ss"));
//    	System.out.println(getYear());
//    	String pattern = "yyyy-MM-dd HH:mm:ss";
//    	String date =  date2Str(new Date(), "yyyy-MM-dd") +  " 11:12:30";
//    	System.out.println(date);
    	List list = getDiffDaysList("2014-01-06","2014-01-08","yyyy-MM-dd",true);
    	for (Object object : list) {
			System.out.println(date2Str((Date)object,"yyyy-MM-dd"));
		}
//    	Date before = str2Date(date, pattern);
//    	System.out.println(before);
    	
    }
    
   
}