package com.cosmosource.app.port.model;

import java.util.Date;

public class PersonNotRightCard {

	private String dataType;//数据类型
	private String dataCount;//黑名单数量
	private String version;//版本号
	private Date dataTime;//时间
	private String infoType;//记录类型
	private String record;//记录流水号
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataCount() {
		return dataCount;
	}
	public void setDataCount(String dataCount) {
		this.dataCount = dataCount;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public Date getDataTime() {
		return dataTime;
	}
	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
}
