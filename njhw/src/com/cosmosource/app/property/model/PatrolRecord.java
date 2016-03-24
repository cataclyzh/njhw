package com.cosmosource.app.property.model;

import java.util.Date;

public class PatrolRecord {
	private Long recordId;
	private Long scheduleId;
	private Long orgId;
	private String orgName;
	private Long userId;
	private String cardNo;
	private String userName;
	private Long patrolLineId;
	private String patrolLineName;
	private String patrolLineDesc;
	private String patrolNodes;
	private Date scheduleStartTime;
	private Date scheduleEndTime;
	private String isDeal;
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getPatrolLineId() {
		return patrolLineId;
	}
	public void setPatrolLineId(Long patrolLineId) {
		this.patrolLineId = patrolLineId;
	}
	public String getPatrolLineName() {
		return patrolLineName;
	}
	public void setPatrolLineName(String patrolLineName) {
		this.patrolLineName = patrolLineName;
	}
	public String getPatrolLineDesc() {
		return patrolLineDesc;
	}
	public void setPatrolLineDesc(String patrolLineDesc) {
		this.patrolLineDesc = patrolLineDesc;
	}
	public String getPatrolNodes() {
		return patrolNodes;
	}
	public void setPatrolNodes(String patrolNodes) {
		this.patrolNodes = patrolNodes;
	}
	public Date getScheduleStartTime() {
		return scheduleStartTime;
	}
	public void setScheduleStartTime(Date scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}
	public Date getScheduleEndTime() {
		return scheduleEndTime;
	}
	public void setScheduleEndTime(Date scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}
	public String getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}
}
