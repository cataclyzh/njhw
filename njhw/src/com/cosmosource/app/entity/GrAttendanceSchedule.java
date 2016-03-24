package com.cosmosource.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * GR_ATTENDANCE_SCHEDULE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="GR_ATTENDANCE_SCHEDULE")
public class GrAttendanceSchedule implements Serializable{
	
	private Long attendanceScheduleId;
	private Long attendanceScheduleUserId;
	private String attendanceScheduleUserName;
	private Long attendanceScheduleOrgId;
	private String attendanceScheduleOrgName;
	private Long attendanceScheduleAdminId;
	private String attendanceScheduleAdminName;
	private Date attendanceScheduleInTime;
	private Date attendanceScheduleOutTime;
	private String attendanceScheduleState;
	private String resBak1;//RES_BAK1
	private String resBak2;//RES_BAK2
	private String resBak3;//RES_BAK3
	private String resBak4;//RES_BAK4
	
	// Constructors

	/** default constructor */
	public GrAttendanceSchedule(){
		
	}
	
	/** minimal constructor */
	
	public GrAttendanceSchedule(Long attendanceScheduleId,Long attendanceScheduleUserId){
		this.attendanceScheduleId=attendanceScheduleId;
		this.attendanceScheduleUserId=attendanceScheduleUserId;
	}
	
	/** full constructor */
	public GrAttendanceSchedule(Long attendanceScheduleId,Long attendanceScheduleUserId,String attendanceScheduleUserName,
								Long attendanceScheduleOrgId,String attendanceScheduleOrgName,Long attendanceScheduleAdminId,String attendanceScheduleAdminName,
								Date attendanceScheduleInTime,Date attendanceScheduleOutTime,String attendanceScheduleState,String resBak1
								,String resBak2,String resBak3,String resBak4){
		this.attendanceScheduleId=attendanceScheduleId;
		this.attendanceScheduleUserId=attendanceScheduleUserId;
		this.attendanceScheduleUserName=attendanceScheduleUserName;
		this.attendanceScheduleOrgId=attendanceScheduleOrgId;
		this.attendanceScheduleOrgName=attendanceScheduleOrgName;
		this.attendanceScheduleAdminId=attendanceScheduleAdminId;
		this.attendanceScheduleAdminName=attendanceScheduleAdminName;
		this.attendanceScheduleInTime=attendanceScheduleInTime;
		this.attendanceScheduleOutTime=attendanceScheduleOutTime;
		this.attendanceScheduleState=attendanceScheduleState;
		this.resBak1=resBak1;
		this.resBak2=resBak2;
		this.resBak3=resBak3;
		this.resBak4=resBak4;
	}
	
	// Property accessors
	@Id
	@Column(name = "ATTENDANCE_SCHEDULE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GR_ATTENDANCE_SCHEDULE")
	@SequenceGenerator(name="SEQ_GR_ATTENDANCE_SCHEDULE",allocationSize=1,initialValue=1, sequenceName="SEQ_GR_ATTENDANCE_SCHEDULE")
	public Long getAttendanceScheduleId() {
		return attendanceScheduleId;
	}
	public void setAttendanceScheduleId(Long attendanceScheduleId) {
		this.attendanceScheduleId = attendanceScheduleId;
	}
	
	@Column(name = "ATTENDANCE_SCHEDULE_USERID", precision = 12, scale = 0)
	public Long getAttendanceScheduleUserId() {
		return attendanceScheduleUserId;
	}
	public void setAttendanceScheduleUserId(Long attendanceScheduleUserId) {
		this.attendanceScheduleUserId = attendanceScheduleUserId;
	}
	
	@Column(name = "ATTENDANCE_SCHEDULE_USERNAME", length = 20)
	public String getAttendanceScheduleUserName() {
		return attendanceScheduleUserName;
	}
	public void setAttendanceScheduleUserName(String attendanceScheduleUserName) {
		this.attendanceScheduleUserName = attendanceScheduleUserName;
	}
	
	@Column(name = "ATTENDANCE_SCHEDULE_ORGID", precision = 12, scale = 0)
	public Long getAttendanceScheduleOrgId() {
		return attendanceScheduleOrgId;
	}
	public void setAttendanceScheduleOrgId(Long attendanceScheduleOrgId) {
		this.attendanceScheduleOrgId = attendanceScheduleOrgId;
	}
	
	@Column(name = "ATTENDANCE_SCHEDULE_ORGNAME", length = 90)
	public String getAttendanceScheduleOrgName() {
		return attendanceScheduleOrgName;
	}
	public void setAttendanceScheduleOrgName(String attendanceScheduleOrgName) {
		this.attendanceScheduleOrgName = attendanceScheduleOrgName;
	}
	
	@Column(name = "ATTENDANCE_SCHEDULE_ADMINID", precision = 12, scale = 0)
	public Long getAttendanceScheduleAdminId() {
		return attendanceScheduleAdminId;
	}

	public void setAttendanceScheduleAdminId(Long attendanceScheduleAdminId) {
		this.attendanceScheduleAdminId = attendanceScheduleAdminId;
	}

	@Column(name = "ATTENDANCE_SCHEDULE_ADMINNAME", length = 20)
	public String getAttendanceScheduleAdminName() {
		return attendanceScheduleAdminName;
	}

	public void setAttendanceScheduleAdminName(String attendanceScheduleAdminName) {
		this.attendanceScheduleAdminName = attendanceScheduleAdminName;
	}

	
	@Temporal(TemporalType.DATE)
	@Column(name="ATTENDANCE_SCHEDULE_INTIME",length=7)
	public Date getAttendanceScheduleInTime() {
		return attendanceScheduleInTime;
	}
	public void setAttendanceScheduleInTime(Date attendanceScheduleInTime) {
		this.attendanceScheduleInTime = attendanceScheduleInTime;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="ATTENDANCE_SCHEDULE_OUTTIME",length=7)
	public Date getAttendanceScheduleOutTime() {
		return attendanceScheduleOutTime;
	}
	public void setAttendanceScheduleOutTime(Date attendanceScheduleOutTime) {
		this.attendanceScheduleOutTime = attendanceScheduleOutTime;
	}
		
	@Column(name = "ATTENDANCE_SCHEDULE_STATE", length = 1)
	public String getAttendanceScheduleState() {
		return attendanceScheduleState;
	}

	public void setAttendanceScheduleState(String attendanceScheduleState) {
		this.attendanceScheduleState = attendanceScheduleState;
	}

	@Column(name = "RES_BAK1", length = 20)
	public String getResBak1() {
		return resBak1;
	}
	public void setResBak1(String resBak1) {
		this.resBak1 = resBak1;
	}
	
	@Column(name = "RES_BAK2", length = 20)
	public String getResBak2() {
		return resBak2;
	}
	public void setResBak2(String resBak2) {
		this.resBak2 = resBak2;
	}
	
	@Column(name = "RES_BAK3", length = 20)
	public String getResBak3() {
		return resBak3;
	}
	public void setResBak3(String resBak3) {
		this.resBak3 = resBak3;
	}
	
	@Column(name = "RES_BAK4", length = 20)
	public String getResBak4() {
		return resBak4;
	}
	public void setResBak4(String resBak4) {
		this.resBak4 = resBak4;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
}
