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
 * GR_REPAIR entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_REPAIR")
public class GrRepair implements Serializable {
	private Long repairId;
	private Long deviceTypeId;
	private Long deviceId;
	private String orgName;
	private String reportUserName;
	private String repairUserName;
	private String reportUserTel;
	private Long reportUserOrg;
	private Long reportUserId;
	private Long repairUserId;
	private Long distributeUserId;
	private String repairTheme;
	private String repairDetail;
	private Date repairStartTime;
	private Date repairEndTime;
	private String repairState;
	private Date distributeTime;
	private String repairReceipt;
	private String repairSatisfy;
	private String repairEvaluate;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrRepair() {

	}

	/** minimal constructor */

	public GrRepair(Long repairId,Long deviceTypeId, Long deviceId, Long reportUserId) {
		this.repairId = repairId;
		this.deviceTypeId = deviceTypeId;
		this.deviceId = deviceId;
		this.reportUserId = reportUserId;
	}

	/** full constructor */

	public GrRepair(Long repairId,Long deviceTypeId, Long deviceId, Long reportUserOrg,
			Long reportUserId, Long repairUserId, Long distributeUserId,
			String repairDetail, Date repairStartTime, Date repairEndTime,
			String repairState, Date distributeTime, String repairTheme,
			String repairReceipt, String repairSatisfy, String repairEvaluate,
			String reportUserName, String repairUserName, String reportUserTel,
			String orgName, String resBak1, String resBak2, String resBak3,
			String resBak4) {
		this.repairId = repairId;
		this.deviceTypeId = deviceTypeId;
		this.deviceId = deviceId;
		this.reportUserOrg = reportUserOrg;
		this.reportUserId = reportUserId;
		this.repairUserId = repairUserId;
		this.distributeUserId = distributeUserId;
		this.repairDetail = repairDetail;
		this.repairStartTime = repairStartTime;
		this.repairEndTime = repairEndTime;
		this.repairState = repairState;
		this.distributeTime = distributeTime;
		this.repairTheme = repairTheme;
		this.repairReceipt = repairReceipt;
		this.repairSatisfy = repairSatisfy;
		this.repairEvaluate = repairEvaluate;
		this.orgName = orgName;
		this.reportUserName = reportUserName;
		this.repairUserName = repairUserName;
		this.reportUserTel = reportUserTel;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "REPAIR_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_REPAIR")
	@SequenceGenerator(name = "SEQ_GR_REPAIR", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_REPAIR")
	public Long getRepairId() {
		return repairId;
	}

	public void setRepairId(Long repairId) {
		this.repairId = repairId;
	}
	
	@Column(name = "DEVICE_TYPE_ID", precision = 12, scale = 0)
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Column(name = "DEVICE_ID", precision = 12, scale = 0)
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "REPORT_USERID", precision = 12, scale = 0)
	public Long getReportUserId() {
		return reportUserId;
	}

	public void ReportUserId(Long reportUserId) {
		this.reportUserId = reportUserId;
	}

	@Column(name = "REPAIR_USERID", precision = 12, scale = 0)
	public Long getRepairUserId() {
		return repairUserId;
	}

	public void setRepairUserId(Long repairUserId) {
		this.repairUserId = repairUserId;
	}

	@Column(name = "DISTRIBUTE_USERID", precision = 12, scale = 0)
	public Long getDistributeUserId() {
		return distributeUserId;
	}

	public void setDistributeUserId(Long distributeUserId) {
		this.distributeUserId = distributeUserId;
	}

	@Column(name = "REPAIR_DETAIL", length = 550)
	public String getRepairDetail() {
		return repairDetail;
	}

	public void setRepairDetail(String repairDetail) {
		this.repairDetail = repairDetail;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAIR_START_TIME", length = 7)
	public Date getRepairStartTime() {
		return repairStartTime;
	}

	public void setRepairStartTime(Date repairStartTime) {
		this.repairStartTime = repairStartTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAIR_END_TIME", length = 7)
	public Date getRepairEndTime() {
		return repairEndTime;
	}

	public void setRepairEndTime(Date repairEndTime) {
		this.repairEndTime = repairEndTime;
	}

	@Column(name = "REPAIR_STATE", length = 1)
	public String getRepairState() {
		return repairState;
	}

	public void setRepairState(String repairState) {
		this.repairState = repairState;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DISTRIBUTE_TIME", length = 7)
	public Date getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	@Column(name = "REPAIR_RECEIPT", length = 550)
	public String getRepairReceipt() {
		return repairReceipt;
	}

	public void setRepairReceipt(String repairReceipt) {
		this.repairReceipt = repairReceipt;
	}

	@Column(name = "REPAIR_SATISFY", length = 1)
	public String getRepairSatisfy() {
		return repairSatisfy;
	}

	@Column(name = "REPORT_USER_ORG", length = 12)
	public Long getReportUserOrg() {
		return reportUserOrg;
	}

	public void setReportUserOrg(Long reportUserOrg) {
		this.reportUserOrg = reportUserOrg;
	}

	@Column(name = "REPAIR_THEME", length = 60)
	public String getRepairTheme() {
		return repairTheme;
	}

	public void setRepairTheme(String repairTheme) {
		this.repairTheme = repairTheme;
	}

	public void setReportUserId(Long reportUserId) {
		this.reportUserId = reportUserId;
	}

	public void setRepairSatisfy(String repairSatisfy) {
		this.repairSatisfy = repairSatisfy;
	}

	@Column(name = "REPAIR_EVALUATE", length = 550)
	public String getRepairEvaluate() {
		return repairEvaluate;
	}

	public void setRepairEvaluate(String repairEvaluate) {
		this.repairEvaluate = repairEvaluate;
	}

	@Column(name = "ORG_NAME", length = 90)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "REPORT_USER_NAME", length = 20)
	public String getReportUserName() {
		return reportUserName;
	}

	public void setReportUserName(String reportUserName) {
		this.reportUserName = reportUserName;
	}

	@Column(name = "REPAIR_USER_NAME", length = 20)
	public String getRepairUserName() {
		return repairUserName;
	}

	public void setRepairUserName(String repairUserName) {
		this.repairUserName = repairUserName;
	}

	@Column(name = "REPORT_USER_TEL", length = 20)
	public String getReportUserTel() {
		return reportUserTel;
	}

	public void setReportUserTel(String reportUserTel) {
		this.reportUserTel = reportUserTel;
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
