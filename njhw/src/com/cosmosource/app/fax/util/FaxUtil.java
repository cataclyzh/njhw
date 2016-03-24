package com.cosmosource.app.fax.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FaxUtil {
	
	private final static String TODAY = "TODAY";
	private final static String YESTERDAY = "YESTERDAY";
	private final static String EARLIEAR = "EARLIEAR";
	
	public static Date StringToDate(String s) {
		Date time = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				time = sd.parse(s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return time;
		}
	public static Date dateConverter(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static String dateCompare(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/**
		 * 比较日期，是否为今天
		 * 前提是系统时间必须为正常同步
		 */
		Date date = new Date();
		try {
			if (sdf.parse(str).equals(sdf.parse(sdf.format(date)))){
				//判断是否为今天
				return TODAY ;
			}else if (sdf.parse(str).equals(sdf.parse(sdf.format(getToday())))){
				//判断是否为昨天
				return YESTERDAY;
			}else{
				return EARLIEAR;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("static-access")
	public static Date getToday(){
		Date date = new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); //这个时间就是日期往后推一天的结果
		return date;

	}
	public static void main(String[] args) {
		System.out.println(getToday());
		System.out.println(dateCompare("2013-06-07 16:08:42"));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(StringToDate("2013-07-08 16:08:42")));
	}
}
