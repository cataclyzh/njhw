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
 * GR_DEVICE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_DEVICE")
public class GrDevice implements Serializable {
	private Long deviceId;
    private String deviceNo;
	private Long deviceTypeId;
	private String deviceName;
	private String deviceDescribe;
	private Date deviceProduceTime;
	private Date deviceBuyTime;
	private Long deviceWarrantyTime;
	private String deviceCompany;
	private String deviceSequence;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrDevice() {

	}

	/** minimal constructor */
	public GrDevice(Long deviceId,String deviceNo, Long deviceTypeId) {
		this.deviceNo = deviceNo;
		this.deviceId = deviceId;
		this.deviceTypeId = deviceTypeId;
	}

	/** full constructor */
	public GrDevice(Long deviceId,String deviceNo,Long deviceTypeId, String deviceName,
			String deviceDescribe, Date deviceProduceTime, Date deviceBuyTime,
			String deviceCompany, String deviceSequence, String resBak1,
			String resBak2, String resBak3, String resBak4) {
		this.deviceId = deviceId;
		this.deviceNo = deviceNo;
		this.deviceTypeId = deviceTypeId;
		this.deviceName = deviceName;
		this.deviceDescribe = deviceDescribe;
		this.deviceProduceTime = deviceProduceTime;
		this.deviceBuyTime = deviceBuyTime;
		this.deviceCompany = deviceCompany;
		this.deviceSequence = deviceSequence;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "DEVICE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_DEVICE")
	@SequenceGenerator(name = "SEQ_GR_DEVICE", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_DEVICE")
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
	@Column(name = "DEVICE_NO", length = 40)
	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	@Column(name = "DEVICE_TYPE_ID", precision = 12, scale = 0)
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	@Column(name = "DEVICE_NAME", length = 40)
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "DEVICE_DESCRIBE", length = 550)
	public String getDeviceDescribe() {
		return deviceDescribe;
	}

	public void setDeviceDescribe(String deviceDescribe) {
		this.deviceDescribe = deviceDescribe;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEVICE_PRODUCE_TIME", length = 7)
	public Date getDeviceProduceTime() {
		return deviceProduceTime;
	}

	public void setDeviceProduceTime(Date deviceProduceTime) {
		this.deviceProduceTime = deviceProduceTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEVICE_BUY_TIME", length = 7)
	public Date getDeviceBuyTime() {
		return deviceBuyTime;
	}

	public void setDeviceBuyTime(Date deviceBuyTime) {
		this.deviceBuyTime = deviceBuyTime;
	}

	@Column(name = "DEVICE_WARRANTY_TIME", length = 7)
	public Long getDeviceWarrantyTime() {
		return deviceWarrantyTime;
	}

	public void setDeviceWarrantyTime(Long deviceWarrantyTime) {
		this.deviceWarrantyTime = deviceWarrantyTime;
	}

	@Column(name = "DEVICE_COMPANY", length = 90)
	public String getDeviceCompany() {
		return deviceCompany;
	}

	public void setDeviceCompany(String deviceCompany) {
		this.deviceCompany = deviceCompany;
	}

	@Column(name = "DEVICE_SEQUENCE", length = 90)
	public String getDeviceSequence() {
		return deviceSequence;
	}

	public void setDeviceSequence(String deviceSequence) {
		this.deviceSequence = deviceSequence;
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
