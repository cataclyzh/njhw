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
 * 物业管理  报修单（维修计划）
 * CsRepairFault entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CS_REPAIR_FAULT")
public class CsRepairFault implements java.io.Serializable {
	/*报修单状态*/		
	//申请 01 
	public static final String CRF_FLAG_APP = "01";
	//派单 02 
	public static final String CRF_FLAG_SURE = "02";
	//完成 03 
	public static final String CRF_FLAG_FINISH = "03";
	//评论 04 
	public static final String CRF_FLAG_EVAL = "04";
	
	// Fields
	private Long crfId;
	private Long userId;
	private String userName;
	private Long staId;
	private String staName;
	private String crfTel;
	private Long aetId;
	private String aetName;
	private Long easId;
	private String easName;
	private String crfPriority;
	private String crfDesc;
	private String crfFlag;
	private java.util.Date crfRt;
	private Long crfNum;
	private Long crfUid;
	private String crfUname;
	private java.util.Date crfDt;
	private Long crfBak1;	// 维修人ID
	private String crfBak2;	// 维修人Name
	private java.util.Date crfPt;
	private String crfBak3;
	private String crfBak4;
	private Long orgId;
	private String orgName;
	private String crfRdesc;
	private String crfSatis;
	private String crfComment;
	private java.util.Date crfCt;
	private String crfLflag;
	private String crfType;
	// Constructors

	/** default constructor */
	public CsRepairFault() {
	}

	/** minimal constructor */
	public CsRepairFault(Long crfId, Long userId) {
		this.crfId = crfId;
		this.userId = userId;
	}

	/** full constructor */
	public CsRepairFault(Long crfId, Long userId, String userName, Long staId,
			String staName, String crfTel, Long aetId, String aetName,
			Long easId, String easName, String crfPriority, String crfDesc,
			String crfFlag, Date crfRt, Long crfNum, Long crfUid, String crfUname,
			Date crfDt, Long crfBak1, String crfBak2, Date crfPt,
			String crfBak3, String crfBak4, Long orgId, String orgName,
			String crfRdesc, String crfSatis, String crfComment, Date crfCt,
			String crfLflag, String crfType) {
		this.crfId = crfId;
		this.userId = userId;
		this.userName = userName;
		this.staId = staId;
		this.staName = staName;
		this.crfTel = crfTel;
		this.aetId = aetId;
		this.aetName = aetName;
		this.easId = easId;
		this.easName = easName;
		this.crfPriority = crfPriority;
		this.crfDesc = crfDesc;
		this.crfFlag = crfFlag;
		this.crfRt = crfRt;
		this.crfNum = crfNum;
		this.crfUid = crfUid;
		this.crfUname = crfUname;
		this.crfDt = crfDt;
		this.crfBak1 = crfBak1;
		this.crfBak2 = crfBak2;
		this.crfPt = crfPt;
		this.crfBak3 = crfBak3;
		this.crfBak4 = crfBak4;
		this.orgId = orgId;
		this.orgName = orgName;
		this.crfRdesc = crfRdesc;
		this.crfSatis = crfSatis;
		this.crfComment = crfComment;
		this.crfCt = crfCt;
		this.crfLflag = crfLflag;
		this.crfType = crfType;
	}

	// Property accessors
	@Id
	@Column(name = "CRF_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CS_REPAIR_FAULT")
	@SequenceGenerator(name="SEQ_CS_REPAIR_FAULT",allocationSize=1,initialValue=1, sequenceName="SEQ_CS_REPAIR_FAULT")
	public Long getCrfId() {
		return this.crfId;
	}

	public void setCrfId(Long crfId) {
		this.crfId = crfId;
	}

	@Column(name = "USER_ID", nullable = false, precision = 12, scale = 0)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "STA_ID", precision = 12, scale = 0)
	public Long getStaId() {
		return this.staId;
	}

	public void setStaId(Long staId) {
		this.staId = staId;
	}

	@Column(name = "STA_NAME", length = 50)
	public String getStaName() {
		return this.staName;
	}

	public void setStaName(String staName) {
		this.staName = staName;
	}

	@Column(name = "CRF_TEL", length = 20)
	public String getCrfTel() {
		return this.crfTel;
	}

	public void setCrfTel(String crfTel) {
		this.crfTel = crfTel;
	}

	@Column(name = "AET_ID", precision = 12, scale = 0)
	public Long getAetId() {
		return this.aetId;
	}

	public void setAetId(Long aetId) {
		this.aetId = aetId;
	}

	@Column(name = "AET_NAME", length = 30)
	public String getAetName() {
		return this.aetName;
	}

	public void setAetName(String aetName) {
		this.aetName = aetName;
	}

	@Column(name = "EAS_ID", precision = 12, scale = 0)
	public Long getEasId() {
		return this.easId;
	}

	public void setEasId(Long easId) {
		this.easId = easId;
	}

	@Column(name = "EAS_NAME", length = 30)
	public String getEasName() {
		return this.easName;
	}

	public void setEasName(String easName) {
		this.easName = easName;
	}

	@Column(name = "CRF_PRIORITY", length = 2)
	public String getCrfPriority() {
		return this.crfPriority;
	}

	public void setCrfPriority(String crfPriority) {
		this.crfPriority = crfPriority;
	}

	@Column(name = "CRF_DESC", length = 200)
	public String getCrfDesc() {
		return this.crfDesc;
	}

	public void setCrfDesc(String crfDesc) {
		this.crfDesc = crfDesc;
	}

	@Column(name = "CRF_FLAG", length = 2)
	public String getCrfFlag() {
		return this.crfFlag;
	}

	public void setCrfFlag(String crfFlag) {
		this.crfFlag = crfFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CRF_RT", length = 7)
	public java.util.Date getCrfRt() {
		return this.crfRt;
	}

	public void setCrfRt(java.util.Date crfRt) {
		this.crfRt = crfRt;
	}

	@Column(name = "CRF_NUM", precision = 38, scale = 0)
	public Long getCrfNum() {
		return this.crfNum;
	}

	public void setCrfNum(Long crfNum) {
		this.crfNum = crfNum;
	}

	@Column(name = "CRF_UID", length = 12)
	public Long getCrfUid() {
		return this.crfUid;
	}

	public void setCrfUid(Long crfUid) {
		this.crfUid = crfUid;
	}
	
	@Column(name = "CRF_UNAME", length = 20)
	public String getCrfUname() {
		return this.crfUname;
	}

	public void setCrfUname(String crfUname) {
		this.crfUname = crfUname;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CRF_DT", length = 7)
	public java.util.Date getCrfDt() {
		return this.crfDt;
	}

	public void setCrfDt(java.util.Date crfDt) {
		this.crfDt = crfDt;
	}

	@Column(name = "CRF_BAK1", precision = 12, scale = 0)
	public Long getCrfBak1() {
		return this.crfBak1;
	}

	public void setCrfBak1(Long crfBak1) {
		this.crfBak1 = crfBak1;
	}

	@Column(name = "CRF_BAK2", length = 20)
	public String getCrfBak2() {
		return this.crfBak2;
	}

	public void setCrfBak2(String crfBak2) {
		this.crfBak2 = crfBak2;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CRF_PT", length = 7)
	public java.util.Date getCrfPt() {
		return this.crfPt;
	}

	public void setCrfPt(java.util.Date crfPt) {
		this.crfPt = crfPt;
	}

	@Column(name = "CRF_BAK3", length = 20)
	public String getCrfBak3() {
		return this.crfBak3;
	}

	public void setCrfBak3(String crfBak3) {
		this.crfBak3 = crfBak3;
	}

	@Column(name = "CRF_BAK4", length = 20)
	public String getCrfBak4() {
		return this.crfBak4;
	}

	public void setCrfBak4(String crfBak4) {
		this.crfBak4 = crfBak4;
	}

	@Column(name = "ORG_ID", precision = 12, scale = 0)
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ORG_NAME", length = 50)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "CRF_RDESC", length = 200)
	public String getCrfRdesc() {
		return this.crfRdesc;
	}

	public void setCrfRdesc(String crfRdesc) {
		this.crfRdesc = crfRdesc;
	}

	@Column(name = "CRF_SATIS", length = 2)
	public String getCrfSatis() {
		return this.crfSatis;
	}

	public void setCrfSatis(String crfSatis) {
		this.crfSatis = crfSatis;
	}

	@Column(name = "CRF_COMMENT", length = 200)
	public String getCrfComment() {
		return this.crfComment;
	}

	public void setCrfComment(String crfComment) {
		this.crfComment = crfComment;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CRF_CT", length = 7)
	public java.util.Date getCrfCt() {
		return this.crfCt;
	}

	public void setCrfCt(java.util.Date crfCt) {
		this.crfCt = crfCt;
	}

	@Column(name = "CRF_LFLAG", length = 2)
	public String getCrfLflag() {
		return this.crfLflag;
	}

	public void setCrfLflag(String crfLflag) {
		this.crfLflag = crfLflag;
	}

	@Column(name = "CRF_TYPE", length = 1)
	public String getCrfType() {
		return this.crfType;
	}

	public void setCrfType(String crfType) {
		this.crfType = crfType;
	}
}