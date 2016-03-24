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
 * GR_INOUT_STORANGE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_INOUT_STORANGE")
public class GrInOutStorage implements Serializable {
	private Long inoutStorageId;
	private Long deviceTypeId;
	private Long deviceId;
	private Long lenderUserId;
	private String lenderUserName;
	private Long lenderUserOrg;
	private String lenderUserOrgName;
	private Long authorUserId;
	private Long inoutStorageFlag;
	private String inoutStorageInDetail;
	private Long inoutStorageInNumber;
	private Date inoutStorageTime;
	private String inoutStorageOutDetail;
	private Long inoutStorageOutNumber;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrInOutStorage() {

	}

	/** minimal constructor */

	public GrInOutStorage(Long inoutStorageId, Long deviceTypeId,Long deviceId,
			Long lenderUserId, Long authorUserId) {
		this.inoutStorageId = inoutStorageId;
		this.deviceId = deviceId;
		this.lenderUserId = lenderUserId;
		this.authorUserId = authorUserId;
	}

	/** full constructor */
	public GrInOutStorage(Long inoutStorageId, Long deviceId,Long deviceTypeId,
			Long lenderUserId, Long authorUserId, String inoutStorageOutDetail,
			Long inoutStorageOutNumber,String inoutStorageInDetail, Long inoutStorageInNumber,
			Date inoutStorageTime, String inoutStorageRemark,
			String inoutStorageState, String resBak1, String resBak2,
			String resBak3, String resBak4) {
		this.inoutStorageId = inoutStorageId;
		this.deviceId = deviceId;
		this.lenderUserId = lenderUserId;
		this.authorUserId = authorUserId;
		this.inoutStorageOutDetail = inoutStorageOutDetail;
		this.inoutStorageOutNumber = inoutStorageOutNumber;
		this.inoutStorageInDetail = inoutStorageInDetail;
		this.inoutStorageInNumber = inoutStorageInNumber;
		this.inoutStorageTime = inoutStorageTime;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "INOUT_STORANGE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_INOUT_STORANG")
	@SequenceGenerator(name = "SEQ_GR_INOUT_STORANG", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_INOUT_STORANG")
	public Long getInoutStorageId() {
		return inoutStorageId;
	}

	public void setInoutStorageId(Long inoutStorageId) {
		this.inoutStorageId = inoutStorageId;
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

	@Column(name = "LENDER_USER_ID", precision = 12, scale = 0)
	public Long getLenderUserId() {
		return lenderUserId;
	}

	public void setLenderUserId(Long lenderUserId) {
		this.lenderUserId = lenderUserId;
	}

	@Column(name = "AUTHOR_USER_ID", precision = 12, scale = 0)
	public Long getAuthorUserId() {
		return authorUserId;
	}

	public void setAuthorUserId(Long authorUserId) {
		this.authorUserId = authorUserId;
	}

	@Column(name = "INOUT_STORAGE_FLAG", precision = 12, scale = 0)
	public Long getInoutStorageFlag() {
		return inoutStorageFlag;
	}

	public void setInoutStorageFlag(Long inoutStorageFlag) {
		this.inoutStorageFlag = inoutStorageFlag;
	}

	@Column(name = "INOUT_STORAGE_OUT_DETAIL", length = 550)
	public String getInoutStorageOutDetail() {
		return inoutStorageOutDetail;
	}

	public void setInoutStorageOutDetail(String inoutStorageOutDetail) {
		this.inoutStorageOutDetail = inoutStorageOutDetail;
	}

	@Column(name = "INOUT_STORAGE_OUT_NUMBER", length = 12)
	public Long getInoutStorageOutNumber() {
		return inoutStorageOutNumber;
	}

	public void setInoutStorageOutNumber(Long inoutStorageOutNumber) {
		this.inoutStorageOutNumber = inoutStorageOutNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INOUT_STORAGE_TIME", length = 7)
	public Date getInoutStorageTime() {
		return inoutStorageTime;
	}

	public void setInoutStorageTime(Date inoutStorageTime) {
		this.inoutStorageTime = inoutStorageTime;
	}

	@Column(name = "INOUT_STORAGE_IN_DETAIL", length = 550)
	public String getInoutStorageInDetail() {
		return inoutStorageInDetail;
	}

	public void setInoutStorageInDetail(String inoutStorageInDetail) {
		this.inoutStorageInDetail = inoutStorageInDetail;
	}

	@Column(name = "INOUT_STORAGE_IN_NUMBER", length = 12)
	public Long getInoutStorageInNumber() {
		return inoutStorageInNumber;
	}

	public void setInoutStorageInNumber(Long inoutStorageInNumber) {
		this.inoutStorageInNumber = inoutStorageInNumber;
	}

	@Column(name = "LENDER_USER_NAME", length = 20)
	public String getLenderUserName() {
		return lenderUserName;
	}

	public void setLenderUserName(String lenderUserName) {
		this.lenderUserName = lenderUserName;
	}

	@Column(name = "LENDER_USER_ORG", precision = 12, scale = 0)
	public Long getLenderUserOrg() {
		return lenderUserOrg;
	}

	public void setLenderUserOrg(Long lenderUserOrg) {
		this.lenderUserOrg = lenderUserOrg;
	}

	@Column(name = "LENDER_USER_ORG_NAME", length = 90)
	public String getLenderUserOrgName() {
		return lenderUserOrgName;
	}

	public void setLenderUserOrgName(String lenderUserOrgName) {
		this.lenderUserOrgName = lenderUserOrgName;
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
