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
 * GR_REPAIR_COST entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_REPAIR_COST")
public class GrRepairCost implements Serializable {
	private Long repairCostId;
	private Long repairId;
	private Long deviceCostId;
	private Long repairCostReportUserId;
	private Long repairCostRepairUserId;
	private String orgName;
	private String reportUserName;
	private Long orgId;
	private String repairCostTitle;
	private Long deviceNumber;
	private Date repairCostTime;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrRepairCost() {

	}

	/** minimal constructor */

	public GrRepairCost(Long repairCostId, Long repairId) {
		this.repairCostId = repairCostId;
		this.repairId = repairId;
	}

	/** full constructor */
	public GrRepairCost(Long repairCostId, Long repairId, Long deviceCostId,
			Long repairCostReportUserId, Long repairCostRepairUserId,
			Long orgId, String repairCostTitle, Long deviceNumber,
			Date repairCostTime, String resBak1, String resBak2,
			String resBak3, String resBak4) {
		this.repairCostId = repairCostId;
		this.repairId = repairId;
		this.deviceCostId = deviceCostId;
		this.repairCostReportUserId = repairCostReportUserId;
		this.repairCostRepairUserId = repairCostRepairUserId;
		this.orgId = orgId;
		this.repairCostTitle = repairCostTitle;
		this.deviceNumber = deviceNumber;
		this.repairCostTime = repairCostTime;
	}

	// Property accessors
	@Id
	@Column(name = "REPAIR_COST_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_REPAIR_COST")
	@SequenceGenerator(name = "SEQ_GR_REPAIR_COST", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_REPAIR_COST")
	public Long getRepairCostId() {
		return repairCostId;
	}

	public void setRepairCostId(Long repairCostId) {
		this.repairCostId = repairCostId;
	}

	@Column(name = "REPAIR_ID", precision = 12, scale = 0)
	public Long getRepairId() {
		return repairId;
	}

	public void setRepairId(Long repairId) {
		this.repairId = repairId;
	}

	@Column(name = "DEVICE_ID", precision = 12, scale = 0)
	public Long getDeviceCostId() {
		return deviceCostId;
	}

	public void setDeviceCostId(Long deviceCostId) {
		this.deviceCostId = deviceCostId;
	}

	@Column(name = "REPAIR_COST_REPORT_USER_ID", precision = 12, scale = 0)
	public Long getRepairCostReportUserId() {
		return repairCostReportUserId;
	}

	public void setRepairCostReportUserId(Long repairCostReportUserId) {
		this.repairCostReportUserId = repairCostReportUserId;
	}

	@Column(name = "REPAIR_COST_REPAIR_USER_ID", precision = 12, scale = 0)
	public Long getRepairCostRepairUserId() {
		return repairCostRepairUserId;
	}

	public void setRepairCostRepairUserId(Long repairCostRepairUserId) {
		this.repairCostRepairUserId = repairCostRepairUserId;
	}

	@Column(name = "ORG_ID", precision = 12, scale = 0)
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "REPAIR_COST_TITLE", length = 60)
	public String getRepairCostTitle() {
		return repairCostTitle;
	}

	public void setRepairCostTitle(String repairCostTitle) {
		this.repairCostTitle = repairCostTitle;
	}

	@Column(name = "DEVICE_NUMBER", length = 12)
	public Long getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(Long deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REPAIR_COST_TIME", length = 7)
	public Date getRepairCostTime() {
		return repairCostTime;
	}

	public void setRepairCostTime(Date repairCostTime) {
		this.repairCostTime = repairCostTime;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
