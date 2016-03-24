package com.cosmosource.app.port.model;

import java.util.Date;

public class OperationModel {
	
	
	private String bmId;
	private String insertName;
	private Date insertDate;
	private String resName;
	private String bmDetail;
	private String bmExp2;
	
	public String getInsertName() {
		return insertName;
	}
	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getBmDetail() {
		return bmDetail;
	}
	public void setBmDetail(String bmDetail) {
		this.bmDetail = bmDetail;
	}
	public String getBmExp2() {
		return bmExp2;
	}
	public void setBmExp2(String bmExp2) {
		this.bmExp2 = bmExp2;
	}
	public String getBmId() {
		return bmId;
	}
	public void setBmId(String bmId) {
		this.bmId = bmId;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
}
