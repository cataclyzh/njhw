package com.cosmosource.app.property.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * DeviceCost entity. @author MyEclipse Persistence Tools
 * 
 * @author tangtq
 * 
 */
public class DeviceCost {
	private Long deviceId;
	private Long deviceNumber;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(Long deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
