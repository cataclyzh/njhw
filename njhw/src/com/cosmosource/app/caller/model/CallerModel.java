package com.cosmosource.app.caller.model;

import java.util.Date;

/**
 * 
* @description: 模型类(用于访客和访客事务)
* @author cjw
* @date 2013-3-20 下午06:23:22
 */
public class CallerModel {

	private String userName;
	private String cardId;
	private String cityCard;
	private String phone;
	private String carId;
	private String orgName;
	private String orgId;
	private String outName;
	private String outId;
	private String comeWhy;
	private Short comeNum;	
	private String fatherCardId;
	private Short one = 1;

	public Short getOne() {
		return one;
	}
	public void setOne(Short one) {
		this.one = one;
	}
	private String beginTime;
	private String endTime;
	
	private String state;
	
	private String registerName;
	private String password;
	private String rePassword;
	private String email;
    private String orderType;
	private String vmName;
	private String cardType;
	//来源
	private String vmDepart;
	//车牌号码
	private String plateNum;
	private String photoName;
	private String userid;
    private String dbPhotoName;
    private Date vsStadd;
    private Date vsEtadd;
	private String residentNo;
	private Date edvsEtadd;
	
	private String certificateNo;
	
	private String locatorCard;
	
	private String pawn;
	
	private String certificateName;
	
	  

	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public Date getEdvsEtadd() {
		return edvsEtadd;
	}
	public void setEdvsEtadd(Date edvsEtadd) {
		this.edvsEtadd = edvsEtadd;
	}
	public String getDbPhotoName() {
		return dbPhotoName;
	}
	public String getResidentNo() {
		return residentNo;
	}
	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}
	public void setDbPhotoName(String dbPhotoName) {
		this.dbPhotoName = dbPhotoName;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public String getVmDepart() {
		return vmDepart;
	}
	public void setVmDepart(String vmDepart) {
		this.vmDepart = vmDepart;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Short getComeNum() {
		return comeNum;
	}
	public void setComeNum(Short comeNum) {
		this.comeNum = comeNum;
	}
  
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOutName() {
		return outName;
	}
	public void setOutName(String outName) {
		this.outName = outName;
	}
	public String getComeWhy() {
		return comeWhy;
	}
	public void setComeWhy(String comeWhy) {
		this.comeWhy = comeWhy;
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	public Date getVsStadd() {
		return vsStadd;
	}
	public void setVsStadd(Date vsStadd) {
		this.vsStadd = vsStadd;
	}
	public Date getVsEtadd() {
		return vsEtadd;
	}
	public void setVsEtadd(Date vsEtadd) {
		this.vsEtadd = vsEtadd;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getLocatorCard() {
		return locatorCard;
	}
	public void setLocatorCard(String locatorCard) {
		this.locatorCard = locatorCard;
	}
	
	public String getPawn() {
		return pawn;
	}
	public void setPawn(String pawn) {
		this.pawn = pawn;
	}
	public String getFatherCardId() {
		return fatherCardId;
	}
	public void setFatherCardId(String fatherCardId) {
		this.fatherCardId = fatherCardId;
	}
}
