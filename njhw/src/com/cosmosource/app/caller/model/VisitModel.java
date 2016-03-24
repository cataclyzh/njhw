package com.cosmosource.app.caller.model;

import java.io.Serializable;
import java.util.Date;

public class VisitModel implements Serializable{
	
	private String userName;	//访客名称(用户名称)
	private String cardId;		// 身份证号
	private String cityCard;	// 市民卡号
	private String email;		// 邮箱
	private String phone;		// 联系方式
	private String carId;		// 车牌号
	private String orgName;		// 被访者组织机构
	private String comeWhy;		// 来访事由
	private Short comeNum;		// 来访人数
	private Date beginTime;		// 预约开始时间
	private Date endTime;		// 预约结束时间
	private String visitType;   // 预约类型 0:市民卡 1:身份证
	
	private String visitTypeFlag;   // 预约类型 0:市民卡 1:身份证
	
	public String getVisitTypeFlag() {
		return visitTypeFlag;
	}
	public void setVisitTypeFlag(String visitTypeFlag) {
		this.visitTypeFlag = visitTypeFlag;
	}
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCityCard() {
		return cityCard;
	}
	public void setCityCard(String cityCard) {
		this.cityCard = cityCard;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getComeWhy() {
		return comeWhy;
	}
	public void setComeWhy(String comeWhy) {
		this.comeWhy = comeWhy;
	}
	public Short getComeNum() {
		return comeNum;
	}
	public void setComeNum(Short comeNum) {
		this.comeNum = comeNum;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
//	public VisitModel(String userName, String cardId, String cityCard,
//			String email, String phone, String carId, String orgName,
//			String comeWhy, Short comeNum, Date beginTime, Date endTime) {
//		super();
//		this.userName = userName;
//		this.cardId = cardId;
//		this.cityCard = cityCard;
//		this.email = email;
//		this.phone = phone;
//		this.carId = carId;
//		this.orgName = orgName;
//		this.comeWhy = comeWhy;
//		this.comeNum = comeNum;
//		this.beginTime = beginTime;
//		this.endTime = endTime;
//	}
	
	public VisitModel() {
		super();
	}
	
}
