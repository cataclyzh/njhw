package com.cosmosource.app.entity;

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

/**
 * 用户扩展表信息
 * NjhwUsersExp entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NJHW_USERS_EXP")
public class NjhwUsersExp implements java.io.Serializable {
	
	public static final String UEP_FLAG_ONLINE = "1";
	public static final String UEP_FLAG_OFFLINE = "2";
	
	// 用户类型01:内部办公人员，02：运营管理人员，03：周边商户
	public static final String USER_TYPE_OFFICE = "01";
	public static final String USER_TYPE_MANAGER = "02";
	public static final String USER_TYPE_BUINESS = "03";
	// Fields

	private Long uepId;
	private Long userid;
	private String uepSex;
	private String residentNo;
	private String uepPhoto;
	private String uepMobile;
	private String uepMail;
	private String plateNum;
	private Long roomId;
	private String roomInfo;
	private Long stationId;
	private String stationName;
	private Long telId;
	private String uepFax;
	private String webFax;
	private Long jobId;
	private String uepHobby;
	private String uepType;
	private String uepSkill;
	private String uepFlag;
	//市民卡号
	private String cardUid; //卡的唯一编码
	private String uepBak1;		// 通卡用户标识 1：通卡；	默认为null（常规用户）；
	private String uepBak2;
	private String uepBak3;
	private String uepBak4;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;
	private String telMac;  //mac地址
	private String telNum;  //电话号码
	private String feeType;	//停车场是否收费
	private String tmpCard;	//临时卡
	private String cardType;//卡类型
	private String reqTelId;//重新分配的ip电话号码
	private String accessId;
	
	
	
   
	// Constructors

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getTmpCard() {
		return tmpCard;
	}

	public void setTmpCard(String tmpCard) {
		this.tmpCard = tmpCard;
	}

	public String getTelMac() {
		return telMac;
	}

	public void setTelMac(String telMac) {
		this.telMac = telMac;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	/** default constructor */
	public NjhwUsersExp() {
	}

	/** minimal constructor */
	public NjhwUsersExp(Long uepId) {
		this.uepId = uepId;
	}

	/** full constructor */
	public NjhwUsersExp(Long uepId, Long userid, String uepSex,
			String residentNo, String uepPhoto, String uepMobile,
			String uepMail, String plateNum, Long roomId, String roomInfo,
			Long stationId, String stationName, Long telId, String uepFax,
			Long jobId, String uepHobby, String uepType, String uepSkill,
			String uepFlag, String cardUid,String uepBak1, String uepBak2, String uepBak3,
			String uepBak4, Long insertId, Date insertDate, Long modifyId,
			Date modifyDate,String telMac,String telNum,String feeType,String tmpCard, String accessId) {
		this.uepId = uepId;
		this.userid = userid;
		this.uepSex = uepSex;
		this.residentNo = residentNo;
		this.uepPhoto = uepPhoto;
		this.uepMobile = uepMobile;
		this.uepMail = uepMail;
		this.plateNum = plateNum;
		this.roomId = roomId;
		this.roomInfo = roomInfo;
		this.stationId = stationId;
		this.stationName = stationName;
		this.telId = telId;
		this.uepFax = uepFax;
		this.jobId = jobId;
		this.uepHobby = uepHobby;
		this.uepType = uepType;
		this.uepSkill = uepSkill;
		this.uepFlag = uepFlag;
		this.cardUid = cardUid;
		this.uepBak1 = uepBak1;
		this.uepBak2 = uepBak2;
		this.uepBak3 = uepBak3;
		this.uepBak4 = uepBak4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
		this.telMac = telMac;
		this.telNum = telNum;
		this.feeType = feeType;
		this.tmpCard = tmpCard;
		this.accessId = accessId;
	}

	// Property accessors
	@Id
	@Column(name = "UEP_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_USERS_EXP")
	@SequenceGenerator(name="SEQ_NJHW_USERS_EXP",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_USERS_EXP")
	public Long getUepId() {
		return this.uepId;
	}

	public void setUepId(Long uepId) {
		this.uepId = uepId;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "UEP_SEX", length = 1)
	public String getUepSex() {
		return this.uepSex;
	}

	public void setUepSex(String uepSex) {
		this.uepSex = uepSex;
	}

	@Column(name = "RESIDENT_NO", length = 20)
	public String getResidentNo() {
		return this.residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	@Column(name = "UEP_PHOTO")
	public String getUepPhoto() {
		return this.uepPhoto;
	}

	public void setUepPhoto(String uepPhoto) {
		this.uepPhoto = uepPhoto;
	}

	@Column(name = "UEP_MOBILE", length = 12)
	public String getUepMobile() {
		return this.uepMobile;
	}

	public void setUepMobile(String uepMobile) {
		this.uepMobile = uepMobile;
	}

	@Column(name = "UEP_MAIL", length = 50)
	public String getUepMail() {
		return this.uepMail;
	}

	public void setUepMail(String uepMail) {
		this.uepMail = uepMail;
	}

	@Column(name = "PLATE_NUM", length = 20)
	public String getPlateNum() {
		return this.plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	@Column(name = "ROOM_ID", precision = 12, scale = 0)
	public Long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	@Column(name = "ROOM_INFO", length = 50)
	public String getRoomInfo() {
		return this.roomInfo;
	}

	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}

	@Column(name = "STATION_ID", precision = 12, scale = 0)
	public Long getStationId() {
		return this.stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	@Column(name = "STATION_NAME", length = 50)
	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Column(name = "TEL_ID", precision = 12, scale = 0)
	public Long getTelId() {
		return this.telId;
	}

	public void setTelId(Long telId) {
		this.telId = telId;
	}

	@Column(name = "UEP_FAX", length = 20)
	public String getUepFax() {
		return this.uepFax;
	}

	public void setUepFax(String uepFax) {
		this.uepFax = uepFax;
	}

	@Column(name = "JOB_ID", precision = 12, scale = 0)
	public Long getJobId() {
		return this.jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	@Column(name = "UEP_HOBBY", length = 200)
	public String getUepHobby() {
		return this.uepHobby;
	}

	public void setUepHobby(String uepHobby) {
		this.uepHobby = uepHobby;
	}

	@Column(name = "UEP_TYPE", length = 2)
	public String getUepType() {
		return this.uepType;
	}

	public void setUepType(String uepType) {
		this.uepType = uepType;
	}

	@Column(name = "UEP_SKILL", length = 200)
	public String getUepSkill() {
		return this.uepSkill;
	}

	public void setUepSkill(String uepSkill) {
		this.uepSkill = uepSkill;
	}

	@Column(name = "UEP_FLAG", length = 1)
	public String getUepFlag() {
		return this.uepFlag;
	}

	public void setUepFlag(String uepFlag) {
		this.uepFlag = uepFlag;
	}

	@Column(name = "UEP_BAK1", length = 20)
	public String getUepBak1() {
		return this.uepBak1;
	}

	public void setUepBak1(String uepBak1) {
		this.uepBak1 = uepBak1;
	}

	@Column(name = "UEP_BAK2", length = 20)
	public String getUepBak2() {
		return this.uepBak2;
	}

	public void setUepBak2(String uepBak2) {
		this.uepBak2 = uepBak2;
	}

	@Column(name = "UEP_BAK3", length = 20)
	public String getUepBak3() {
		return this.uepBak3;
	}

	public void setUepBak3(String uepBak3) {
		this.uepBak3 = uepBak3;
	}

	@Column(name = "UEP_BAK4", length = 20)
	public String getUepBak4() {
		return this.uepBak4;
	}

	public void setUepBak4(String uepBak4) {
		this.uepBak4 = uepBak4;
	}

	@Column(name = "INSERT_ID", precision = 12, scale = 0)
	public Long getInsertId() {
		return this.insertId;
	}

	public void setInsertId(Long insertId) {
		this.insertId = insertId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INSERT_DATE", length = 7)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Column(name = "MODIFY_ID", precision = 12, scale = 0)
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFY_DATE", length = 7)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "CARD_TYPE", length = 1)
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Column(name = "REQ_TEL_ID")
	public String getReqTelId() {
		return reqTelId;
	}

	public void setReqTelId(String reqTelId) {
		this.reqTelId = reqTelId;
	}

	@Column(name = "WEB_FAX")
	public String getWebFax() {
		return webFax;
	}

	public void setWebFax(String webFax) {
		this.webFax = webFax;
	}
	
	@Column(name = "CARD_UID", length = 20)
	public String getCardUid() {
		return cardUid;
	}

	public void setCardUid(String cardUid) {
		this.cardUid = cardUid;
	}
	
	@Column(name = "ACCESS_ID", length = 10)
	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

}