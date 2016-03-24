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
 * GR_STORANGE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_STORANGE")
public class GrStorage implements Serializable {

	private Long storageId;
	private Long deviceId;
	private Long deviceTypeId;
	private Long storageInventory;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrStorage() {

	}

	/** minimal constructor */
	public GrStorage(Long storageId) {
		this.storageId = storageId;
	}

	/** full constructor */
	public GrStorage(Long storageId, Long deviceId, Long storageInventory,
			String resBak1, String resBak2, String resBak3, String resBak4) {
		this.storageId = storageId;
		this.deviceId = deviceId;
		this.storageInventory = storageInventory;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "STORANGE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_STORANG")
	@SequenceGenerator(name = "SEQ_GR_STORANG", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_STORANG")
	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
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

	@Column(name = "STORAGE_INVENTORY", length = 20)
	public Long getStorageInventory() {
		return storageInventory;
	}

	public void setStorageInventory(Long storageInventory) {
		this.storageInventory = storageInventory;
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
