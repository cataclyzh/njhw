package com.cosmosource.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * GR_USER_DEVICE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_DEVICE_TYPE")
public class GrDeviceType implements Serializable {
	private Long deviceTypeId;
	private String deviceTypeNo;
	private String deviceTypeName;
	private String deviceTypeDescribe;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrDeviceType() {

	}

	/** minimal constructor */

	public GrDeviceType(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public GrDeviceType(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	/** full constructor */
	public GrDeviceType(Long deviceTypeId,String deviceTypeNo, String deviceTypeName,
			String deviceTypeDescribe, String resBak1, String resBak2,
			String resBak3, String resBak4) {
		this.deviceTypeId = deviceTypeId;
		this.deviceTypeNo = deviceTypeNo;
		this.deviceTypeName = deviceTypeName;
		this.deviceTypeDescribe = deviceTypeDescribe;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "DEVICE_TYPE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_DEVICE_TYPE_ID")
	@SequenceGenerator(name = "SEQ_GR_DEVICE_TYPE_ID", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_DEVICE_TYPE_ID")
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	
	@Column(name = "DEVICE_TYPE_NO", length = 40)
	public String getDeviceTypeNo() {
		return deviceTypeNo;
	}

	public void setDeviceTypeNo(String deviceTypeNo) {
		this.deviceTypeNo = deviceTypeNo;
	}

	@Column(name = "DEVICE_TYPE_NAME", length = 40)
	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	@Column(name = "DEVICE_TYPE_DESCRIBE", length = 550)
	public String getDeviceTypeDescribe() {
		return deviceTypeDescribe;
	}

	public void setDeviceTypeDescribe(String deviceTypeDescribe) {
		this.deviceTypeDescribe = deviceTypeDescribe;
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
