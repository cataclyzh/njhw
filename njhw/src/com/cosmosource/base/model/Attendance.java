package com.cosmosource.base.model;

public class Attendance {
	/*
	 * {list=[{ATTENDANCE_ORGNAME=物业管理部, 
	 * ATTENDANCE_SCHEDULE_ID=70,
	 * ATTENDANCE_SCHEDULE_ADMINNAME=许瑞 , 
	 * ATTENDANCE_USERNAME=邱绍鹏,
	 * ATTENDANCE_SCHEDULE_INTIME=2013-10-09 08:30:00, 
	 * RES_BAK4=null,
	 * RES_BAK3=null, 
	 * ATTENDANCE_OUTTIME=2013-10-09 12:27:14,
	 * RES_BAK2=2013-10-09, 
	 * ATTENDANCE_SCHEDULE_OUTTIME=2013-10-09 12:00:00, 
	 * RES_BAK1=正常, 
	 * ATTENDANCE_USERID=4522,
	 * ATTENDANCE_ORGID=218, 
	 * ATTENDANCE_INTIME=2013-10-09 08:27:44,
	 * ATTENDANCE_ID=52, 
	 * ATTENDANCE_SCHEDULE_ADMINID=4533},....}], dateStr=2013-10-28}
	 */
	
//	private String attendance_userid; //被排班用户
//	private String attendance_schedule_adminid; //排班人ID

	private String attendance_id; //考勤ID
	private String attendance_schedule_id; //考勤排版ID
	private String attendance_username; //被排班用户名称
	private String attendance_orgname;	//所属部门名称
	private String attendance_schedule_adminname; //排班人名称
	private String attendance_schedule_intime;  //计划起始时间
	private String attendance_schedule_outtime; //计划结束时间
	private String attendance_intime; //用户一天中最早的刷卡时间
	private String attendance_outtime; //用户一天中最晚的刷卡时间
	private String res_bak2; //考勤记录时间
	private String res_bak1; //考勤判断0   正常   1	  迟到	 2	 早退	 3	迟到、早退   4 数据异常
	public Attendance(String attendance_id, String attendance_schedule_id,
			String attendance_username, String attendance_orgname,
			String attendance_schedule_adminname,
			String attendance_schedule_intime,
			String attendance_schedule_outtime, String attendance_intime,
			String attendance_outtime, String res_bak2, String res_bak1) {
		super();
		this.attendance_id = attendance_id;
		this.attendance_schedule_id = attendance_schedule_id;
		this.attendance_username = attendance_username;
		this.attendance_orgname = attendance_orgname;
		this.attendance_schedule_adminname = attendance_schedule_adminname;
		this.attendance_schedule_intime = attendance_schedule_intime;
		this.attendance_schedule_outtime = attendance_schedule_outtime;
		this.attendance_intime = attendance_intime;
		this.attendance_outtime = attendance_outtime;
		this.res_bak2 = res_bak2;
		this.res_bak1 = res_bak1;
	}
	public String getAttendance_id() {
		return attendance_id;
	}
	public void setAttendance_id(String attendance_id) {
		this.attendance_id = attendance_id;
	}
	public String getAttendance_schedule_id() {
		return attendance_schedule_id;
	}
	public void setAttendance_schedule_id(String attendance_schedule_id) {
		this.attendance_schedule_id = attendance_schedule_id;
	}
	public String getAttendance_username() {
		return attendance_username;
	}
	public void setAttendance_username(String attendance_username) {
		this.attendance_username = attendance_username;
	}
	public String getAttendance_orgname() {
		return attendance_orgname;
	}
	public void setAttendance_orgname(String attendance_orgname) {
		this.attendance_orgname = attendance_orgname;
	}
	public String getAttendance_schedule_adminname() {
		return attendance_schedule_adminname;
	}
	public void setAttendance_schedule_adminname(
			String attendance_schedule_adminname) {
		this.attendance_schedule_adminname = attendance_schedule_adminname;
	}
	public String getAttendance_schedule_intime() {
		return attendance_schedule_intime;
	}
	public void setAttendance_schedule_intime(String attendance_schedule_intime) {
		this.attendance_schedule_intime = attendance_schedule_intime;
	}
	public String getAttendance_schedule_outtime() {
		return attendance_schedule_outtime;
	}
	public void setAttendance_schedule_outtime(String attendance_schedule_outtime) {
		this.attendance_schedule_outtime = attendance_schedule_outtime;
	}
	public String getAttendance_intime() {
		return attendance_intime;
	}
	public void setAttendance_intime(String attendance_intime) {
		this.attendance_intime = attendance_intime;
	}
	public String getAttendance_outtime() {
		return attendance_outtime;
	}
	public void setAttendance_outtime(String attendance_outtime) {
		this.attendance_outtime = attendance_outtime;
	}
	public String getRes_bak2() {
		return res_bak2;
	}
	public void setRes_bak2(String res_bak2) {
		this.res_bak2 = res_bak2;
	}
	public String getRes_bak1() {
		return res_bak1;
	}
	public void setRes_bak1(String res_bak1) {
		this.res_bak1 = res_bak1;
	}
}
