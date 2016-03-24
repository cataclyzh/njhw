package com.cosmosource.app.property.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.property.service.AttendanceManager;
import com.cosmosource.app.property.service.AttendanceScheduleManager;
import com.cosmosource.base.util.DateUtil;

/** 
* @description: 考勤定时任务
*/ 
@SuppressWarnings("unchecked")
public class AttendanceJob {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	//正常
	public static final String NORMAL_STATUS = "0";
	//迟到
	public static final String LATE_STATUS = "1";
	//早退
	public static final String EARLY_STATUS = "2";
	//迟到+早退
	public static final String LATE_EARLY_STATUS = "3";
	//异常
	public static final String EXCEPTION_STATUS = "4";
	//缺勤
	public static final String ABSENCE_STATUS = "5";
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private AttendanceManager attendanceManager;
	private AttendanceScheduleManager attendanceScheduleManager;
	
	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	public AttendanceScheduleManager getAttendanceScheduleManager() {
		return attendanceScheduleManager;
	}

	public void setAttendanceScheduleManager(
			AttendanceScheduleManager attendanceScheduleManager) {
		this.attendanceScheduleManager = attendanceScheduleManager;
	}
	
	public List<String> getScheduleDates(String sDate, String eDate) throws Exception{
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
	public static void main(String[] args) {
		AttendanceJob job = new AttendanceJob();
		try {
			System.out.println(job.getScheduleDates("2013-11-11", "2013-12-31").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Map<String, String> executeOnDateSchedule(String currentDate, String startTime, 
			String endTime, String cardNo) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		
		
		/**
		 * String startTime = as.get("RES_BAK1").toString();  08:00:00
		 * String endTime = as.get("RES_BAK2").toString();	17:30:00
		 */
		String currentStartTime = currentDate + " " + startTime;
		String currentEndTime = currentDate + " " + endTime;
		result.put("currentStartTime", currentStartTime);
		result.put("currentEndTime", currentEndTime);
		
		Date cst = format.parse(currentStartTime);
		Date cet = format.parse(currentEndTime);
		
		boolean hasBeforeRecord = false;
		boolean hasMiddleRecord = false;
		boolean hasAfterRecord = false;
		
		/**
		 * currentDate ： [2013-11-11, 2013-11-12, 2013-11-13....] 
		 * 获取一天刷卡时间记录
		 * SELECT to_char(b.RECORDTIME,'yyyy-MM-dd hh24:mi:ss') as RT FROM 
			(SELECT a.*, to_char(a.RECORDTIME,'yyyy-MM-dd') AS dd FROM VM_PUNCH_RECORD a) b 
			WHERE dd = #date#
			AND	b.CARD_NO = #cardNo#
			ORDER BY RT
		 */
		List<Map> recordList = attendanceScheduleManager.getAttendanceRecord(currentDate, cardNo);
		
		//获取考勤日前一天最后1小时的刷卡记录
		try{
			String beforeDay = getBeforeDay(currentDate);
			recordList.addAll(attendanceScheduleManager.getBeforeDayRecord(beforeDay, cardNo));
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		//获取考勤日后一天最早1小时的刷卡记录
		try{
			String d = getNextDay(currentDate);
			recordList.addAll(attendanceScheduleManager.getAfterDayRecord(d, cardNo));
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		if(recordList.size() == 0){
			//缺勤
			result.put("status", ABSENCE_STATUS);
			return result;
		}
		
		result.put("sRecord", "");
		result.put("eRecord", "");
		result.put("sMiddleRecord", "");
		result.put("eMiddleRecord", "");
		for(Map record : recordList){
			String recordTime = record.get("RT").toString();
			log.info("recordTime: 日期 ：" + currentDate + "刷卡记录为：" + recordTime);
			Date rt = format.parse(recordTime);
			
			// 刷卡时间小于排班开始时间，说明没迟到
			// 刷卡时间超过排版时间在60秒内的, 算没有迟到
			if((rt.getTime() - cst.getTime()) <= 60 * 1000){
//				if(!hasBeforeRecord){
//					result.put("sRecord", recordTime);
//				}
				
				hasBeforeRecord = true;
				result.put("sRecord", recordTime);
			}
			
			//刷卡记录时间 大于排班开始时间，而且小于排班结束时间，为早退
			if(rt.getTime() > cst.getTime() && rt.getTime() < cet.getTime()){
				if(!hasMiddleRecord){
					result.put("sMiddleRecord", recordTime);
				}
				
				if(!result.get("sMiddleRecord").equals(recordTime)){
					result.put("eMiddleRecord", recordTime);
				}
				hasMiddleRecord = true;
			}
			
			//刷卡时间晚于排班时间，正常下班
			if(rt.getTime() >= cet.getTime()){
//				result.put("eRecord", recordTime);
//				hasAfterRecord = true;
				if(!hasAfterRecord){
					result.put("eRecord", recordTime);
					hasAfterRecord = true;
				}
			}
		}
		
		result.put("status", getStatus(hasBeforeRecord, hasMiddleRecord, hasAfterRecord));
		
		return result;
	}
	
	private String getStatus(boolean hasBeforeRecord, boolean hasMiddleRecord, boolean hasAfterRecord){
		log.debug("hasBeforeRecord: " + hasBeforeRecord);
		log.debug("hasMiddleRecord: " + hasMiddleRecord);
		log.debug("hasAfterRecord: " + hasAfterRecord);
		
		if(hasBeforeRecord && hasAfterRecord){
			//正常
			return NORMAL_STATUS;
		}else if(hasBeforeRecord && !hasAfterRecord){
			//早退
			return EARLY_STATUS;
		}else if(!hasBeforeRecord && hasMiddleRecord && hasAfterRecord){
			//迟到
			return LATE_STATUS;
		}else if(!hasBeforeRecord && hasMiddleRecord && !hasAfterRecord){
			//早退+迟到
			return LATE_EARLY_STATUS;
		}else{
			//异常
			return EXCEPTION_STATUS;
		}
	}
	
	public String getNextDay(String currentDay){
		String result = "";
		try{
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = f1.parse(currentDay);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			c1.add(Calendar.DAY_OF_MONTH, 1);
			result = f1.format(c1.getTime());
		}catch (ParseException e){
			log.error(e.getMessage());
		}
		
		return result; 
	}
	
	public String getBeforeDay(String currentDay){
		String result = "";
		try {
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = f1.parse(currentDay);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d1);
			c1.add(Calendar.DAY_OF_MONTH, -1);
			result = f1.format(c1.getTime());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		
		return result;
	}
	
	private int getTimeInterval(String s1, String s2) throws Exception{
		//这个日期用来组装合法的日期对象
		String ss = "2013-01-01";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = format.parse(ss + " " + s2);
		Date d2 = format.parse(ss + " " + s1);
		return (int)(d2.getTime() - d1.getTime())/2;
	}
	
	private int divisionTime(String s1, String s2) throws Exception{
		//这个日期用来组装合法的日期对象
		String ss = "2013-01-01";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = format.parse(ss + " " + s1);
		Date d2 = format.parse(ss + " " + s2);
		return (int)(d2.getTime() - d1.getTime());
	}
	
	private Map<String, String> executeTwoDateSchedule(String currentDate, String startTime, 
			String endTime, String cardNo) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		
		String nextDate = getNextDay(currentDate);
		
		String currentStartTime = currentDate + " " + startTime;
		String currentEndTime = nextDate + " " + endTime;
		result.put("currentStartTime", currentStartTime);
		result.put("currentEndTime", currentEndTime);
		
		int interval = getTimeInterval(startTime, endTime);
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(currentStartTime));
		c.add(Calendar.MILLISECOND, (-1)*interval);
		log.debug(format.format(c.getTime()));
		Date t1 = c.getTime();
		
		c.setTime(format.parse(currentEndTime));
		c.add(Calendar.MILLISECOND,  (int)interval);
		log.debug(format.format(c.getTime()));
		Date t2 = c.getTime();
		
		log.debug("currentStartTime:" + currentStartTime);
		log.debug("currentEndTime:" +currentEndTime);
		
		Date cst = format.parse(currentStartTime);
		Date cet = format.parse(currentEndTime);
		
		boolean hasBeforeRecord = false;
		boolean hasMiddleRecord = false;
		boolean hasAfterRecord = false;
		boolean isFirstExRecord = true;
		
		List<Map> recordList = attendanceScheduleManager.getAttendanceRecord(currentDate, cardNo);
		recordList.addAll(attendanceScheduleManager.getAttendanceRecord(nextDate, cardNo));
		
		if(recordList.size() == 0){
			//缺勤
			result.put("status", ABSENCE_STATUS);
			return result;
		}
		
		result.put("sRecord", "");
		result.put("eRecord", "");
		result.put("sMiddleRecord", "");
		result.put("eMiddleRecord", "");
		result.put("exRecord1", "");
		result.put("exRecord2", "");
		for(Map record : recordList){
			String recordTime = record.get("RT").toString();
			log.debug("recordTime: " + recordTime);
			Date rt = format.parse(recordTime);
			
			if(rt.getTime() >= t1.getTime() && rt.getTime() <= cst.getTime()){
				if(!hasBeforeRecord){
					result.put("sRecord", recordTime);
				}
				hasBeforeRecord = true;
			}
			
			if(rt.getTime() >= cst.getTime() && rt.getTime() <= cet.getTime()){
				if(!hasMiddleRecord){
					result.put("sMiddleRecord", recordTime);
				}
				
				if(!result.get("sMiddleRecord").equals(recordTime)){
					result.put("eMiddleRecord", recordTime);
				}
				hasMiddleRecord = true;
			}
			
			if(rt.getTime() >= cet.getTime() && rt.getTime() <= t2.getTime()){
				result.put("eRecord", recordTime);
				hasAfterRecord = true;
			}
			
			if(rt.getTime() < t1.getTime()){
				if(isFirstExRecord){
					result.put("exRecord1", recordTime);
					isFirstExRecord = false;
				}
			}
			
			if(rt.getTime() > t2.getTime()){
				result.put("exRecord2", recordTime);
			}
		}
		
		result.put("status", getStatus(hasBeforeRecord, hasMiddleRecord, hasAfterRecord));
		
		return result;
	}
	
	public void execute(){
		Date currentTime = new Date();
		log.info("考勤定时任务开始["+DateUtil.date2Str(currentTime, "yyyy-MM-dd HH:mm:ss")+"]");
		
		String today = DateUtil.date2Str(currentTime, "yyyy-MM-dd");
		String yesterday = getBeforeDay(today);
		
		/**
		 * FROM GR_ATTENDANCE_SCHEDULE t, NJHW_TSCARD b
		 * 获取考勤排班记录
		 */
		List<Map> attendanceSchedules = attendanceScheduleManager.queryAttendanceSchedules();
		if(attendanceSchedules==null){
			return;
		}
		
		List<Map> addAttendanceList = new ArrayList<Map>();
		
		for(Map as : attendanceSchedules){
			try{
				long aScheduleId = Long.parseLong(as.get("ATTENDANCE_SCHEDULE_ID").toString());
				long userId = Long.parseLong(as.get("ATTENDANCE_SCHEDULE_USERID").toString());
				String startTime = as.get("RES_BAK1").toString();
				String endTime = as.get("RES_BAK2").toString();
				String cardNo = as.get("CARDNO").toString();
				String scheduleId = as.get("ATTENDANCE_SCHEDULE_ID").toString();
				String userName = as.get("ATTENDANCE_SCHEDULE_USERNAME").toString();
				String orgId = as.get("ATTENDANCE_SCHEDULE_ORGID").toString();
				String orgName = as.get("ATTENDANCE_SCHEDULE_ORGNAME").toString();
				String adminId = as.get("ATTENDANCE_SCHEDULE_ADMINID").toString();
				String adminName = as.get("ATTENDANCE_SCHEDULE_ADMINNAME").toString();
				String startDate = as.get("ATTENDANCE_SCHEDULE_INTIME").toString();
				String endDate = as.get("ATTENDANCE_SCHEDULE_OUTTIME").toString();
				String type = as.get("RES_BAK3").toString();
				
				//startDate 2013/11/27 endDate 2013/12/31
				//[2013-11-11, 2013-11-12, 2013-11-13....]
				List<String> dates = getScheduleDates(startDate, endDate);
				
				Map<String, String> exeResult = null;
				
				Date todayTime = DateUtil.str2Date(today, "yyyy-MM-dd");
				Date yesterdayTime = DateUtil.str2Date(yesterday, "yyyy-MM-dd");
				for(String s : dates){
					try {
						if(attendanceManager.isAttendanceExist(aScheduleId,s)){
							log.info("考勤记录已存在[" + aScheduleId + "|" + s + "]");
							//如果记录已经存在，则停止往下判断，回头for循环开始
							continue;
						}
						
						if(type.equals("0")){//普通考勤
							/**
							 * String s : dates 为日期
							 */
							log.info("普通考勤[" + s + "]");
							Date d = DateUtil.str2Date(s, "yyyy-MM-dd");
							
							if(d.getTime() >= todayTime.getTime()){
								continue;
							}
							
							//不处理时间不合法的排班记录
							if(divisionTime(startTime, endTime) <= 0){
								continue;
							}
							
							/**
							 * String startTime = as.get("RES_BAK1").toString();
							 * String endTime = as.get("RES_BAK2").toString();
							 * 获取一天考勤记录，迟到、早退等
							 */
							exeResult = executeOnDateSchedule(s, startTime, endTime, cardNo);
							log.info("一天考勤情况：" + exeResult.toString());
							
							//统计一天刷卡记录，最后插入数据库的时候应该只插入一条记录
							HashMap attendance = new HashMap();
							attendance.put("scheduleId", scheduleId);
							attendance.put("userId", userId);
							attendance.put("userName", userName);
							attendance.put("orgId", orgId);
							attendance.put("orgName", orgName);
							attendance.put("adminId", adminId);
							attendance.put("adminName", adminName);
							attendance.put("scheduleInTime", exeResult.get("currentStartTime"));
							attendance.put("scheduleOutTime", exeResult.get("currentEndTime"));
							attendance.put("resBak2", s);
							String status = exeResult.get("status"); //考勤结果，迟到、早退、旷工						
							attendance.put("resBak1", status);
							
							if(status.equals(NORMAL_STATUS)){
								attendance.put("inTime", exeResult.get("sRecord"));
								attendance.put("outTime", exeResult.get("eRecord"));
							}else if(status.equals(EARLY_STATUS)){
								attendance.put("inTime", exeResult.get("sRecord"));
								
								String outTime = exeResult.get("eMiddleRecord");
								if(outTime.equals("")){
									outTime = exeResult.get("sMiddleRecord");
								}
								attendance.put("outTime", outTime);
							}else if(status.equals(LATE_STATUS)){
								attendance.put("inTime", exeResult.get("sMiddleRecord"));
								attendance.put("outTime", exeResult.get("eRecord"));
							}else if(status.equals(LATE_EARLY_STATUS)){
								attendance.put("inTime", exeResult.get("sMiddleRecord"));
								attendance.put("outTime", exeResult.get("eMiddleRecord"));
							}else if(status.equals(EXCEPTION_STATUS)){
								attendance.put("inTime", exeResult.get("sRecord"));
								attendance.put("outTime", exeResult.get("eRecord"));
							}else{
								attendance.put("inTime", "");
								attendance.put("outTime", "");
							}
							
							log.info("考勤记录: " + attendance);
							addAttendanceList.add(attendance);
						}else if(type.equals("1")){//夜班考勤
							log.info("夜班考勤[" + s + "]");
							Date d = DateUtil.str2Date(s, "yyyy-MM-dd");
							
							if(d.getTime() >= yesterdayTime.getTime()){
								continue;
							}
							
							//不处理时间不合法的排班记录
							if(divisionTime(startTime, endTime) > 0){
								continue;
							}
							
							exeResult = executeTwoDateSchedule(s, startTime, endTime, cardNo);
							
							HashMap attendance = new HashMap();
							attendance.put("scheduleId", scheduleId);
							attendance.put("userId", userId);
							attendance.put("userName", userName);
							attendance.put("orgId", orgId);
							attendance.put("orgName", orgName);
							attendance.put("adminId", adminId);
							attendance.put("adminName", adminName);
							attendance.put("scheduleInTime", exeResult.get("currentStartTime"));
							attendance.put("scheduleOutTime", exeResult.get("currentEndTime"));
							attendance.put("resBak2", s);
							String status = exeResult.get("status");						
							attendance.put("resBak1", status);
							
							if(status.equals(NORMAL_STATUS)){
								attendance.put("inTime", exeResult.get("sRecord"));
								attendance.put("outTime", exeResult.get("eRecord"));
							}else if(status.equals(EARLY_STATUS)){
								attendance.put("inTime", exeResult.get("sRecord"));
								String outTime = exeResult.get("eMiddleRecord");
								if(outTime.equals("")){
									outTime = exeResult.get("sMiddleRecord");
								}
								attendance.put("outTime", outTime);
							}else if(status.equals(LATE_STATUS)){
								attendance.put("inTime", exeResult.get("sMiddleRecord"));
								attendance.put("outTime", exeResult.get("eRecord"));
							}else if(status.equals(LATE_EARLY_STATUS)){
								attendance.put("inTime", exeResult.get("sMiddleRecord"));
								attendance.put("outTime", exeResult.get("eMiddleRecord"));
							}else if(status.equals(EXCEPTION_STATUS)){
								attendance.put("inTime", exeResult.get("exRecord1"));
								attendance.put("outTime", exeResult.get("exRecord2"));
							}else{
								attendance.put("inTime", "");
								attendance.put("outTime", "");
							}
							
							log.info("考勤记录: " + attendance);
							addAttendanceList.add(attendance);
						}else{
							log.error("考情排班无效记录: " + as);
							continue;
						}
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
				
				/*设置过期的考勤排班状态
				 * 设置为0表示考勤状态为停止
				 */
				if(type.equals("0")){
					if(isDateSmaller(endDate, today)){
						log.info("设置考勤状态0: " + endDate);
						attendanceScheduleManager.updateAttendanceScheduleStateById(aScheduleId,"0");
					}
				}else if(type.equals("1")){
					if(isDateSmaller(endDate, yesterday)){
						log.info("设置考勤状态0: " + endDate);
						attendanceScheduleManager.updateAttendanceScheduleStateById(aScheduleId,"0");
					}
				}else{
					log.info("无效排班["+aScheduleId+"]");
					attendanceScheduleManager.updateAttendanceScheduleStateById(aScheduleId,"0");
				}
				
			}catch(Exception ex){
				log.error(ex.getMessage());
			}
		}
		
		log.info(addAttendanceList.toString());
		attendanceManager.addAttendanceList(addAttendanceList);
		
		log.info("考勤定时任务结束["+DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")+"]");
		
	}
	
	private boolean isDateSmaller(String s1, String s2) throws Exception{
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = f1.parse(s1);
		Date d2 = f1.parse(s2);
		return d1.getTime() < d2.getTime();
	}
}
