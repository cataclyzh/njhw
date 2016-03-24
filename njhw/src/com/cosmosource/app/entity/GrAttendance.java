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
 * GR_ATTENDANCE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="GR_ATTENDANCE")
public class GrAttendance implements Serializable{
	private Long attendanceId;
	private Long attendanceScheduleId;
	private Long attendanceUserId;
	private String attendanceUserName;
	private Long attendanceOrgId;
	private String attendanceOrgName;
	private Long attendanceScheduleAdminId;
	private String attendanceScheduleAdminName;
	private Date attendanceScheduleInTime;
	private Date attendanceScheduleOutTime;
	private Date attendanceInTime;
	private Date attendanceOutTime;
	private String resBak1;//RES_BAK1
	private String resBak2;//RES_BAK2
	private String resBak3;//RES_BAK3
	private String resBak4;//RES_BAK4
	
	// Constructors

	/** default constructor */
	public GrAttendance(){
		
	}
	
	/** minimal constructor */
	
	public GrAttendance(Long attendanceId,Long attendanceScheduleId,Long attendanceUserId){
		this.attendanceId=attendanceId;
		this.attendanceScheduleId=attendanceScheduleId;
		this.attendanceUserId=attendanceUserId;
	}
	
	/** full constructor */
	public GrAttendance(Long attendanceId,Long attendanceScheduleId,Long attendanceUserId,String attendanceUserName,
			Long attendanceOrgId,String attendanceOrgName,Long attendanceScheduleAdminId,String attendanceScheduleAdminName,
			Date attendanceScheduleInTime,Date attendanceScheduleOutTime,Date attendanceInTime,Date attendanceOutTime,
			String resBak1,String resBak2,String resBak3,String resBak4){
		this.attendanceId=attendanceId;
		this.attendanceScheduleId=attendanceScheduleId;
		this.attendanceUserId=attendanceUserId;
		this.attendanceUserName=attendanceUserName;
		this.attendanceOrgId=attendanceOrgId;
		this.attendanceOrgName=attendanceOrgName;
		this.attendanceScheduleAdminId=attendanceScheduleAdminId;
		this.attendanceScheduleAdminName=attendanceScheduleAdminName;
		this.attendanceScheduleInTime=attendanceScheduleInTime;
		this.attendanceScheduleOutTime=attendanceScheduleOutTime;
		this.attendanceInTime=attendanceInTime;
		this.attendanceOutTime=attendanceOutTime;
		this.resBak1=resBak1;
		this.resBak2=resBak2;
		this.resBak3=resBak3;
		this.resBak4=resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "ATTENDANCE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GR_ATTENDANCE")
	@SequenceGenerator(name="SEQ_GR_ATTENDANCE",allocationSize=1,initialValue=1, sequenceName="SEQ_GR_ATTENDANCE")
	public Long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}
	
	@Column(name = "ATTENDANCE_SCHEDULE_ID", precision = 12, scale = 0)
	public Long getAttendanceScheduleId() {
		return attendanceScheduleId;
	}

	public void setAttendanceScheduleId(Long attendanceScheduleId) {
		this.attendanceScheduleId = attendanceScheduleId;
	}

	@Column(name = "ATTENDANCE_USERID", precision = 12, scale = 0)
	public Long getAttendanceUserId() {
		return attendanceUserId;
	}

	public void setAttendanceUserId(Long attendanceUserId) {
		this.attendanceUserId = attendanceUserId;
	}
	
	@Column(name = "ATTENDANCE_USERNAME", length = 20)
	public String getAttendanceUserName() {
		return attendanceUserName;
	}

	public void setAttendanceUserName(String attendanceUserName) {
		this.attendanceUserName = attendanceUserName;
	}
	
	@Column(name = "ATTENDANCE_ORGID", precision = 12, scale = 0)
	public Long getAttendanceOrgId() {
		return attendanceOrgId;
	}

	public void setAttendanceOrgId(Long attendanceOrgId) {
		this.attendanceOrgId = attendanceOrgId;
	}
	
	@Column(name = "ATTENDANCE_ORGNAME", length = 90)
	public String getAttendanceOrgName() {
		return attendanceOrgName;
	}

	public void setAttendanceOrgName(String attendanceOrgName) {
		this.attendanceOrgName = attendanceOrgName;
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

	@Temporal(TemporalType.DATE)
	@Column(name="ATTENDANCE_INTIME",length=7)
	public Date getAttendanceInTime() {
		return attendanceInTime;
	}

	public void setAttendanceInTime(Date attendanceInTime) {
		this.attendanceInTime = attendanceInTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="ATTENDANCE_OUTTIME",length=7)
	public Date getAttendanceOutTime() {
		return attendanceOutTime;
	}

	public void setAttendanceOutTime(Date attendanceOutTime) {
		this.attendanceOutTime = attendanceOutTime;
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

	@Column(name = "RES_BAK4", length = 255)
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
