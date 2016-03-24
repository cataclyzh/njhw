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
 * GR_PATROL_SCHEDULE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_PATROL_SCHEDULE")
public class GrPatrolSchedule implements Serializable {
	private Long scheduleId;
	private Long orgId;
	private String orgName;
	private Long userId;
	private String userName;
	private Long patrolLineId;
	private String patrolLineName;
	private String patrolLineDesc;
	private String patrolNodes;
	private Date scheduleStartDate;
	private Date scheduleEndDate;
	private String scheduleStartTime;
	private String scheduleEndTime;
	private String scheduleDesc;
	private String patrolScheduleState;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrPatrolSchedule() {

	}

	/** minimal constructor */
	public GrPatrolSchedule(Long scheduleId, Long orgId,
			String orgName, String userName, String patrolLineName,
			String patrolLineDesc, String scheduleStartTime,
			String scheduleEndTime, Date scheduleStartDate, Date scheduleEndDate,
			String scheduleDesc, Long userId, Long patrolLineId,
			String patrolNodes, String resBak1, String resBak2, String resBak3,
			String resBak4) {
		this.scheduleId = scheduleId;
		this.orgId = orgId;
		this.userId = userId;
		this.patrolLineId = patrolLineId;
		this.orgName = orgName;
		this.userName = userName;
		this.patrolLineName = patrolLineName;
		this.patrolLineDesc = patrolLineDesc;
		this.patrolNodes = patrolNodes;
		this.scheduleStartDate = scheduleStartDate;
		this.scheduleEndDate = scheduleEndDate;
		this.scheduleStartTime = scheduleStartTime;
		this.scheduleEndTime = scheduleEndTime;
		this.scheduleDesc = scheduleDesc;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	/** full constructor */
	public GrPatrolSchedule(Long scheduleSeqId, Long scheduleId, Long orgId,
			Long userId, Long patrolLineId,Date scheduleStartDate, Date scheduleEndDate,
			String scheduleStartTime, String scheduleEndTime) {
		this.scheduleId = scheduleId;
		this.orgId = orgId;
		this.userId = userId;
		this.patrolLineId = patrolLineId;
		this.scheduleStartDate = scheduleStartDate;
		this.scheduleEndDate = scheduleEndDate;
		this.scheduleStartTime = scheduleStartTime;
		this.scheduleEndTime = scheduleEndTime;
	}

	// Property accessors
	@Id
	@Column(name = "SCHEDULE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_PATROL_SCHEDULE")
	@SequenceGenerator(name = "SEQ_GR_PATROL_SCHEDULE", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_PATROL_SCHEDULE")
	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name = "ORG_ID", precision = 12, scale = 0)
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ORG_NAME", length = 90)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "USER_ID", precision = 12, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PATROL_LINE_ID", precision = 12, scale = 0)
	public Long getPatrolLineId() {
		return patrolLineId;
	}

	public void setPatrolLineId(Long patrolLineId) {
		this.patrolLineId = patrolLineId;
	}

	@Column(name = "PATROL_LINE_NAME", length = 90)
	public String getPatrolLineName() {
		return patrolLineName;
	}

	public void setPatrolLineName(String patrolLineName) {
		this.patrolLineName = patrolLineName;
	}

	@Column(name = "PATROL_LINE_DESC", length = 550)
	public String getPatrolLineDesc() {
		return patrolLineDesc;
	}

	public void setPatrolLineDesc(String patrolLineDesc) {
		this.patrolLineDesc = patrolLineDesc;
	}

	@Column(name = "PATROL_NODES", length = 550)
	public String getPatrolNodes() {
		return patrolNodes;
	}

	public void setPatrolNodes(String patrolNodes) {
		this.patrolNodes = patrolNodes;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SCHEDULE_START_DATE", length = 7)
	public Date getScheduleStartDate() {
		return scheduleStartDate;
	}

	public void setScheduleStartDate(Date scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SCHEDULE_END_DATE", length = 7)
	public Date getScheduleEndDate() {
		return scheduleEndDate;
	}

	public void setScheduleEndDate(Date scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}

	@Column(name = "SCHEDULE_START_TIME", length = 7)
	public String getScheduleStartTime() {
		return scheduleStartTime;
	}

	public void setScheduleStartTime(String scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	@Column(name = "SCHEDULE_END_TIME", length = 7)
	public String getScheduleEndTime() {
		return scheduleEndTime;
	}

	public void setScheduleEndTime(String scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}

	@Column(name = "SCHEDULE_DESC", length = 550)
	public String getScheduleDesc() {
		return scheduleDesc;
	}

	public void setScheduleDesc(String scheduleDesc) {
		this.scheduleDesc = scheduleDesc;
	}
	
	@Column(name = "PATROL_SCHEDULE_STATE", length = 1)
	public String getPatrolScheduleState() {
		return patrolScheduleState;
	}

	public void setPatrolScheduleState(String patrolScheduleState) {
		this.patrolScheduleState = patrolScheduleState;
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
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
