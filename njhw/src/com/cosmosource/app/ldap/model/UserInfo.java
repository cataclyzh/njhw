package com.cosmosource.app.ldap.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for userInfo complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="userInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardUid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeLoginPwdFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="feeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginPwdMD5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginUid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="plateNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqTelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="residentNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roomId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roomInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telMAC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tmpCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepBak1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepBak2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepBak3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepBak4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepHobby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepMail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepMobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepPhoto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepSex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepSkill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uepType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="webFax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userInfo", propOrder = { "activeFlag", "cardId", "cardType",
		"cardUid", "changeLoginPwdFlag", "cn", "displayName", "feeType",
		"isSystem", "jobId", "loginPwd", "loginPwdMD5", "loginUid", "memo",
		"orderNum", "orgId", "orgName", "plateNum", "reqTelId", "residentNo",
		"roomId", "roomInfo", "sn", "stationId", "stationName", "telId",
		"telMAC", "telNum", "tmpCard", "uepBak1", "uepBak2", "uepBak3",
		"uepBak4", "uepFax", "uepFlag", "uepHobby", "uepMail", "uepMobile",
		"uepPhoto", "uepSex", "uepSkill", "uepType", "uid", "userPassword",
		"validity", "webFax", "pId", "pName", "uCode", "uType" })
public class UserInfo {

	protected String activeFlag;
	protected String cardId;
	protected String cardType;
	protected String cardUid;
	protected String changeLoginPwdFlag;
	protected String cn;
	protected String displayName;
	protected String feeType;
	protected String isSystem;
	protected String jobId;
	protected String loginPwd;
	protected String loginPwdMD5;
	protected String loginUid;
	protected String memo;
	protected String orderNum;
	protected String orgId;
	protected String orgName;
	protected String pId;
	protected String plateNum;
	protected String pName;
	protected String reqTelId;
	protected String residentNo;
	protected String roomId;
	protected String roomInfo;
	protected String sn;
	protected String stationId;
	protected String stationName;
	protected String telId;
	protected String telMAC;
	protected String telNum;
	protected String tmpCard;
	protected String uCode;
	protected String uepBak1;
	protected String uepBak2;
	protected String uepBak3;
	protected String uepBak4;
	protected String uepFax;
	protected String uepFlag;
	protected String uepHobby;
	protected String uepMail;
	protected String uepMobile;
	protected String uepPhoto;
	protected String uepSex;
	protected String uepSkill;
	protected String uepType;
	protected String uid;
	protected String userPassword;
	protected String uType;
	protected String validity;
	protected String webFax;

	/**
	 * Gets the value of the activeFlag property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	 * Gets the value of the cardId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * Gets the value of the cardType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * Gets the value of the cardUid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCardUid() {
		return cardUid;
	}

	/**
	 * Gets the value of the changeLoginPwdFlag property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getChangeLoginPwdFlag() {
		return changeLoginPwdFlag;
	}

	/**
	 * Gets the value of the cn property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * Gets the value of the displayName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Gets the value of the feeType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFeeType() {
		return feeType;
	}

	/**
	 * Gets the value of the isSystem property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getIsSystem() {
		return isSystem;
	}

	/**
	 * Gets the value of the jobId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Gets the value of the loginPwd property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * Gets the value of the loginPwdMD5 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLoginPwdMD5() {
		return loginPwdMD5;
	}

	/**
	 * Gets the value of the loginUid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLoginUid() {
		return loginUid;
	}

	/**
	 * Gets the value of the memo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * Gets the value of the orderNum property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOrderNum() {
		return orderNum;
	}

	/**
	 * Gets the value of the orgId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * Gets the value of the orgName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * Gets the value of the pId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPId() {
		return pId;
	}

	/**
	 * Gets the value of the plateNum property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPlateNum() {
		return plateNum;
	}

	/**
	 * Gets the value of the pName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPName() {
		return pName;
	}

	/**
	 * Gets the value of the reqTelId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReqTelId() {
		return reqTelId;
	}

	/**
	 * Gets the value of the residentNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getResidentNo() {
		return residentNo;
	}

	/**
	 * Gets the value of the roomId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * Gets the value of the roomInfo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRoomInfo() {
		return roomInfo;
	}

	/**
	 * Gets the value of the sn property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * Gets the value of the stationId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * Gets the value of the stationName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * Gets the value of the telId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTelId() {
		return telId;
	}

	/**
	 * Gets the value of the telMAC property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTelMAC() {
		return telMAC;
	}

	/**
	 * Gets the value of the telNum property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTelNum() {
		return telNum;
	}

	/**
	 * Gets the value of the tmpCard property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTmpCard() {
		return tmpCard;
	}

	/**
	 * Gets the value of the uCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUCode() {
		return uCode;
	}

	/**
	 * Gets the value of the uepBak1 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepBak1() {
		return uepBak1;
	}

	/**
	 * Gets the value of the uepBak2 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepBak2() {
		return uepBak2;
	}

	/**
	 * Gets the value of the uepBak3 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepBak3() {
		return uepBak3;
	}

	/**
	 * Gets the value of the uepBak4 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepBak4() {
		return uepBak4;
	}

	/**
	 * Gets the value of the uepFax property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepFax() {
		return uepFax;
	}

	/**
	 * Gets the value of the uepFlag property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepFlag() {
		return uepFlag;
	}

	/**
	 * Gets the value of the uepHobby property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepHobby() {
		return uepHobby;
	}

	/**
	 * Gets the value of the uepMail property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepMail() {
		return uepMail;
	}

	/**
	 * Gets the value of the uepMobile property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepMobile() {
		return uepMobile;
	}

	/**
	 * Gets the value of the uepPhoto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepPhoto() {
		return uepPhoto;
	}

	/**
	 * Gets the value of the uepSex property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepSex() {
		return uepSex;
	}

	/**
	 * Gets the value of the uepSkill property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepSkill() {
		return uepSkill;
	}

	/**
	 * Gets the value of the uepType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUepType() {
		return uepType;
	}

	/**
	 * Gets the value of the uid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Gets the value of the userPassword property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * Gets the value of the uType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUType() {
		return uType;
	}

	/**
	 * Gets the value of the validity property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValidity() {
		return validity;
	}

	/**
	 * Gets the value of the webFax property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getWebFax() {
		return webFax;
	}

	/**
	 * Sets the value of the activeFlag property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setActiveFlag(String value) {
		this.activeFlag = value;
	}

	/**
	 * Sets the value of the cardId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCardId(String value) {
		this.cardId = value;
	}

	/**
	 * Sets the value of the cardType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCardType(String value) {
		this.cardType = value;
	}

	/**
	 * Sets the value of the cardUid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCardUid(String value) {
		this.cardUid = value;
	}

	/**
	 * Sets the value of the changeLoginPwdFlag property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setChangeLoginPwdFlag(String value) {
		this.changeLoginPwdFlag = value;
	}

	/**
	 * Sets the value of the cn property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCn(String value) {
		this.cn = value;
	}

	/**
	 * Sets the value of the displayName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDisplayName(String value) {
		this.displayName = value;
	}

	/**
	 * Sets the value of the feeType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFeeType(String value) {
		this.feeType = value;
	}

	/**
	 * Sets the value of the isSystem property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setIsSystem(String value) {
		this.isSystem = value;
	}

	/**
	 * Sets the value of the jobId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setJobId(String value) {
		this.jobId = value;
	}

	/**
	 * Sets the value of the loginPwd property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLoginPwd(String value) {
		this.loginPwd = value;
	}

	/**
	 * Sets the value of the loginPwdMD5 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLoginPwdMD5(String value) {
		this.loginPwdMD5 = value;
	}

	/**
	 * Sets the value of the loginUid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLoginUid(String value) {
		this.loginUid = value;
	}

	/**
	 * Sets the value of the memo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMemo(String value) {
		this.memo = value;
	}

	/**
	 * Sets the value of the orderNum property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOrderNum(String value) {
		this.orderNum = value;
	}

	/**
	 * Sets the value of the orgId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOrgId(String value) {
		this.orgId = value;
	}

	/**
	 * Sets the value of the orgName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOrgName(String value) {
		this.orgName = value;
	}

	/**
	 * Sets the value of the pId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPId(String value) {
		this.pId = value;
	}

	/**
	 * Sets the value of the plateNum property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPlateNum(String value) {
		this.plateNum = value;
	}

	/**
	 * Sets the value of the pName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPName(String value) {
		this.pName = value;
	}

	/**
	 * Sets the value of the reqTelId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReqTelId(String value) {
		this.reqTelId = value;
	}

	/**
	 * Sets the value of the residentNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setResidentNo(String value) {
		this.residentNo = value;
	}

	/**
	 * Sets the value of the roomId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRoomId(String value) {
		this.roomId = value;
	}

	/**
	 * Sets the value of the roomInfo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRoomInfo(String value) {
		this.roomInfo = value;
	}

	/**
	 * Sets the value of the sn property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSn(String value) {
		this.sn = value;
	}

	/**
	 * Sets the value of the stationId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStationId(String value) {
		this.stationId = value;
	}

	/**
	 * Sets the value of the stationName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStationName(String value) {
		this.stationName = value;
	}

	/**
	 * Sets the value of the telId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTelId(String value) {
		this.telId = value;
	}

	/**
	 * Sets the value of the telMAC property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTelMAC(String value) {
		this.telMAC = value;
	}

	/**
	 * Sets the value of the telNum property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTelNum(String value) {
		this.telNum = value;
	}

	/**
	 * Sets the value of the tmpCard property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTmpCard(String value) {
		this.tmpCard = value;
	}

	/**
	 * Sets the value of the uCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUCode(String value) {
		this.uCode = value;
	}

	/**
	 * Sets the value of the uepBak1 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepBak1(String value) {
		this.uepBak1 = value;
	}

	/**
	 * Sets the value of the uepBak2 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepBak2(String value) {
		this.uepBak2 = value;
	}

	/**
	 * Sets the value of the uepBak3 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepBak3(String value) {
		this.uepBak3 = value;
	}

	/**
	 * Sets the value of the uepBak4 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepBak4(String value) {
		this.uepBak4 = value;
	}

	/**
	 * Sets the value of the uepFax property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepFax(String value) {
		this.uepFax = value;
	}

	/**
	 * Sets the value of the uepFlag property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepFlag(String value) {
		this.uepFlag = value;
	}

	/**
	 * Sets the value of the uepHobby property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepHobby(String value) {
		this.uepHobby = value;
	}

	/**
	 * Sets the value of the uepMail property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepMail(String value) {
		this.uepMail = value;
	}

	/**
	 * Sets the value of the uepMobile property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepMobile(String value) {
		this.uepMobile = value;
	}

	/**
	 * Sets the value of the uepPhoto property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepPhoto(String value) {
		this.uepPhoto = value;
	}

	/**
	 * Sets the value of the uepSex property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepSex(String value) {
		this.uepSex = value;
	}

	/**
	 * Sets the value of the uepSkill property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepSkill(String value) {
		this.uepSkill = value;
	}

	/**
	 * Sets the value of the uepType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUepType(String value) {
		this.uepType = value;
	}

	/**
	 * Sets the value of the uid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUid(String value) {
		this.uid = value;
	}

	/**
	 * Sets the value of the userPassword property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserPassword(String value) {
		this.userPassword = value;
	}

	/**
	 * Sets the value of the uType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUType(String value) {
		this.uType = value;
	}

	/**
	 * Sets the value of the validity property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValidity(String value) {
		this.validity = value;
	}

	/**
	 * Sets the value of the webFax property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setWebFax(String value) {
		this.webFax = value;
	}

	@SuppressWarnings("rawtypes")
	public HashMap<String, Object> toHashMap() throws IllegalArgumentException,
			IllegalAccessException {

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Class clazz = this.getClass();
		List<Class> clazzs = new ArrayList<Class>();

		do {
			clazzs.add(clazz);
			clazz = clazz.getSuperclass();
		} while (!clazz.equals(Object.class));

		for (Class iClazz : clazzs) {
			Field[] fields = iClazz.getDeclaredFields();
			for (Field field : fields) {
				Object objVal = null;
				field.setAccessible(true);
				objVal = field.get(this);
				hashMap.put(field.getName(), objVal);
			}
		}

		return hashMap;
	}

	@SuppressWarnings("rawtypes")
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			Class t = this.getClass();
			Field[] fields = t.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				sb.append("{");
				sb.append(field.getName());
				sb.append(":");
				if (field.getType() == Integer.class) {
					sb.append(field.getInt(this));
				} else if (field.getType() == Long.class) {
					sb.append(field.getLong(this));
				} else if (field.getType() == Boolean.class) {
					sb.append(field.getBoolean(this));
				} else if (field.getType() == char.class) {
					sb.append(field.getChar(this));
				} else if (field.getType() == Double.class) {
					sb.append(field.getDouble(this));
				} else if (field.getType() == Float.class) {
					sb.append(field.getFloat(this));
				} else
					sb.append(field.get(this));
				sb.append("}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
