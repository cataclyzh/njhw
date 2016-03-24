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
 * 访客管理  访问事务
 * VmVisit entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VM_VISIT")
public class VmVisit implements java.io.Serializable {
	/*访问事务状态*/
	//未提交 00 
	public static final String VS_FLAG_NONE = "00";
	//申请中 01 
	public static final String VS_FLAG_APP = "01";
	//已确认 02 
	public static final String VS_FLAG_SURE = "02";
	//被拒绝 03   
	public static final String VS_FLAG_REFUSED = "03";	
	//到访 04 
	public static final String VS_FLAG_COME = "04";
	//正常结束 05 
	public static final String VS_FLAG_FINISH = "05";
	//异常结束 06
	public static final String VS_FLAG_ERROR = "06";
	//取消的预约 99 
	public static final String VS_FLAG_CANCELED = "99";
	/*访问事务状态*/
	//访客是否在大厦 1在，2不在
	public static final String VISITOR_IN = "1";
	public static final String VISITOR_OUT = "2";
	
	public static final String VS_CARTYPE_CITYCARD ="1";//市民卡
	public static final String VS_CARTYPE_TEMCARD ="2";//临时卡
	public static final String VS_RETURN_RYES ="1";//归还临时卡
	public static final String VS_RETURN_RNO ="2";//未归还临时卡
	public static final String VS_RET_SUB_YES ="1";//归还副卡
	public static final String VS_RET_SUB_NO ="2";//未归还副卡
	
	
	// Fields
	private Long vsId;
	private String viName;
	private Long userId;
	private Long orgId;
	private String userName;
	private String orgName;
	private String vsInfo;
	private Short vsNum;
	private Date vsSt;
	private Date vsEt;
	private Date vsSt1;
	private Date vsSt1Lea;
	private Date vsSt1Acc;
	private Date vsEt1;
	private String vsYn;
	private String vsType;
	private String cardId;
	private String cardType;
	private String vsFlag;
	private String vsCommets;
	private String vsReturn;
	private String resBak1;
	private String resBak2;
	private String resBak3;
	private String resBak4;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;
	private String vsretSub;
	//取消原因
	private String cancelInfo;
	private String status;
	
	private String inOut;
	
	private Long viId;
	// Constructors

	/** default constructor */
	public VmVisit() {
	}

	/** minimal constructor */
	public VmVisit(Long vsId) {
		this.vsId = vsId;
	}

	/** full constructor */
	public VmVisit(Long vsId,String viName,
			Long userId, String userName, String orgName, String vsInfo,
			Short vsNum, Date vsSt, Date vsEt, Date vsSt1, Date vsSt1Lea,
			Date vsSt1Acc, Date vsEt1, String vsYn, String vsType,
			String cardId, String cardType, String vsFlag, String vsCommets,
			String vsReturn, String resBak1, String resBak2, String resBak3,
			String resBak4, Long insertId, Date insertDate, Long modifyId,
			Date modifyDate,String vsretSub ,String status) {
		this.vsId = vsId;
		this.viName = viName;
		this.userId = userId;
		this.userName = userName;
		this.orgName = orgName;
		this.vsInfo = vsInfo;
		this.vsNum = vsNum;
		this.vsSt = vsSt;
		this.vsEt = vsEt;
		this.vsSt1 = vsSt1;
		this.vsSt1Lea = vsSt1Lea;
		this.vsSt1Acc = vsSt1Acc;
		this.vsEt1 = vsEt1;
		this.vsYn = vsYn;
		this.vsType = vsType;
		this.cardId = cardId;
		this.cardType = cardType;
		this.vsFlag = vsFlag;
		this.vsCommets = vsCommets;
		this.vsReturn = vsReturn;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
		this.vsretSub = vsretSub;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "VS_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VM_VISIT")
	@SequenceGenerator(name="SEQ_VM_VISIT",allocationSize=1,initialValue=1, sequenceName="SEQ_VM_VISIT")
	public Long getVsId() {
		return this.vsId;
	}

	public void setVsId(Long vsId) {
		this.vsId = vsId;
	}
	
	
	@Column(name = "VI_ID", unique = true, precision = 12, scale = 0)
	public Long getViId() {
		return viId;
	}

	public void setViId(Long viId) {
		this.viId = viId;
	}

	@Column(name = "VI_NAME", length = 20)
	public String getViName() {
		return this.viName;
	}

	public void setViName(String viName) {
		this.viName = viName;
	}

	@Column(name = "USER_ID", precision = 12, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "ORG_ID", precision = 12, scale = 0)
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "ORG_NAME", length = 100)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "VS_INFO", length = 200)
	public String getVsInfo() {
		return this.vsInfo;
	}

	public void setVsInfo(String vsInfo) {
		this.vsInfo = vsInfo;
	}

	@Column(name = "VS_NUM", precision = 4, scale = 0)
	public Short getVsNum() {
		return this.vsNum;
	}

	public void setVsNum(Short vsNum) {
		this.vsNum = vsNum;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VS_ST", length = 7)
	public Date getVsSt() {
		return this.vsSt;
	}

	public void setVsSt(Date vsSt) {
		this.vsSt = vsSt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VS_ET", length = 7)
	public Date getVsEt() {
		return this.vsEt;
	}

	public void setVsEt(Date vsEt) {
		this.vsEt = vsEt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VS_ST1", length = 7)
	public Date getVsSt1() {
		return this.vsSt1;
	}

	public void setVsSt1(Date vsSt1) {
		this.vsSt1 = vsSt1;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VS_ST1_LEA", length = 7)
	public Date getVsSt1Lea() {
		return this.vsSt1Lea;
	}

	public void setVsSt1Lea(Date vsSt1Lea) {
		this.vsSt1Lea = vsSt1Lea;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VS_ST1_ACC", length = 7)
	public Date getVsSt1Acc() {
		return this.vsSt1Acc;
	}

	public void setVsSt1Acc(Date vsSt1Acc) {
		this.vsSt1Acc = vsSt1Acc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VS_ET1", length = 7)
	public Date getVsEt1() {
		return this.vsEt1;
	}

	public void setVsEt1(Date vsEt1) {
		this.vsEt1 = vsEt1;
	}

	@Column(name = "VS_YN", length = 1)
	public String getVsYn() {
		return this.vsYn;
	}

	public void setVsYn(String vsYn) {
		this.vsYn = vsYn;
	}

	@Column(name = "VS_TYPE", length = 1)
	public String getVsType() {
		return this.vsType;
	}

	public void setVsType(String vsType) {
		this.vsType = vsType;
	}

	@Column(name = "CARD_ID", length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "CARD_TYPE", length = 1)
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Column(name = "VS_FLAG", length = 2)
	public String getVsFlag() {
		return this.vsFlag;
	}

	public void setVsFlag(String vsFlag) {
		this.vsFlag = vsFlag;
	}

	@Column(name = "VS_COMMETS", length = 200)
	public String getVsCommets() {
		return this.vsCommets;
	}

	public void setVsCommets(String vsCommets) {
		this.vsCommets = vsCommets;
	}

	@Column(name = "VS_RETURN", length = 1)
	public String getVsReturn() {
		return this.vsReturn;
	}

	public void setVsReturn(String vsReturn) {
		this.vsReturn = vsReturn;
	}

	@Column(name = "RES_BAK1", length = 20)
	public String getResBak1() {
		return this.resBak1;
	}

	public void setResBak1(String resBak1) {
		this.resBak1 = resBak1;
	}

	@Column(name = "RES_BAK2", length = 20)
	public String getResBak2() {
		return this.resBak2;
	}

	public void setResBak2(String resBak2) {
		this.resBak2 = resBak2;
	}

	@Column(name = "RES_BAK3", length = 20)
	public String getResBak3() {
		return this.resBak3;
	}

	public void setResBak3(String resBak3) {
		this.resBak3 = resBak3;
	}

	@Column(name = "RES_BAK4", length = 20)
	public String getResBak4() {
		return this.resBak4;
	}

	public void setResBak4(String resBak4) {
		this.resBak4 = resBak4;
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
	@Column(name = "VS_RET_SUB", length = 1)
	public String getVsretSub() {
		return vsretSub;
	}

	public void setVsretSub(String vsretSub) {
		this.vsretSub = vsretSub;
	}

	@Column(name = "CANCEL_INFO", length = 200)
	public String getCancelInfo() {
		return cancelInfo;
	}
	
	public void setCancelInfo(String cancelInfo) {
		this.cancelInfo = cancelInfo;
	}
	
	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "IN_OUT", length = 1)
	public String getInOut() {
		return this.inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
}