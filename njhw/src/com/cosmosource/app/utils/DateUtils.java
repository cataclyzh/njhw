package com.cosmosource.app.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	public static final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getCurrentDateStr(){
		String result = format1.format(new Date());
		return result;
	}

	public static String getFirstDayOfMonth(){
		String currentDayStr = getCurrentDateStr();
		String tmpStr = currentDayStr.substring(0,8);
		return tmpStr+"01";
	}
	
	public static String getFirstDayTimeOfMonth(){
		String currentDayStr = getCurrentDateStr();
		String tmpStr = currentDayStr.substring(0,8);
		return tmpStr+"01 00:00:00";
	}
	
	public static String getCurrentTime(){
		return format2.format(new Date());
	}
	
	/*
	 * 返回日期格式 yyyy-MM-dd 的String
	 */
	public static String getDateString(Date d){
		return format1.format(d);
	}
	
	/**
	 * 返回 year 字符串
	 * @param d 传入格式yyyy-MM-dd的字符串
	 * @return 返回 year 字符串 
	 */
	public static String getYearStr(String d){
		return d.substring(0, 4);
	}
	
	/**
	 * 返回 month 字符串
	 * @param d 传入格式yyyy-MM-dd的字符串
	 * @return 返回 month 字符串 
	 */
	public static String getMonthStr(String d){
		return d.substring(5, 7);
	}
	
	/**
	 * 返回 day 字符串
	 * @param d 传入格式yyyy-MM-dd的字符串
	 * @return 返回 day 字符串 
	 */
	public static String getDayStr(String d){
		return d.substring(8);
	}
	
	public static void main(String[] args) throws Exception{
//		System.out.println(getCurrentTime());
		
//		String startDate = "2014-08-23";
//		String endDate = "2014-09-30";
//		List<String> list = getSingleDate(startDate, endDate);
//		for(String s : list){
//			System.out.println(s);
//		}
		
		List<String> allDate = getScheduleDates("2014-08-23", "2014-09-30");
		for(String s : allDate){
			System.out.println(s);
		}
		
	}
	
	public static List<String> getSingleDate(String startDate, String endDate) throws Exception{
		List<String> result = new ArrayList<String>();
		List<String> allDate = getScheduleDates(startDate, endDate);
		for(String s : allDate){
			if(isSingleDate(s)){
				result.add(s);
			}
		}
		return result;
	}
	
	public static List<String> getDoubleDate(String startDate, String endDate) throws Exception{
		List<String> result = new ArrayList<String>();
		List<String> allDate = getScheduleDates(startDate, endDate);
		for(String s : allDate){
			if(!isSingleDate(s)){
				result.add(s);
			}
		}
		return result;
	}
	
	public static boolean isSingleDate(String date){
		String d = date.substring(date.length()-1, date.length());
//		System.out.println("d: " + d);
		List<String> singleList = new ArrayList<String>();
		singleList.add("1");
		singleList.add("3");
		singleList.add("5");
		singleList.add("7");
		singleList.add("9");
		
		if(singleList.contains(d)){
			return true;
		}else{
			return false;
		}
	}
	
	public static List<String> getScheduleDates(String sDate, String eDate) throws Exception{
		List<String> result = new ArrayList<String>();
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = f1.parse(sDate);
		Date d2 = f1.parse(eDate);
		long t = d2.getTime() - d1.getTime();
		double days = t / (24*3600.0*1000); //将一天转换为毫秒时间
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		result.add(f1.format(c1.getTime()));
		for(int i=0; i<days; i++){
			c1.add(Calendar.DAY_OF_MONTH, 1);	//日期加1		
			result.add(f1.format(c1.getTime()));
			//c1.add(Calendar.HOUR_OF_DAY, 1);  //时间加1小时
//			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(c1.getTime()));
		}
		return result;
	}
}
