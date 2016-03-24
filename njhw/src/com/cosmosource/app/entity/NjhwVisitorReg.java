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
 * 访客管理   外部访客注册表
 * NjhwVisitorReg entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NJHW_VISITOR_REG")
public class NjhwVisitorReg implements java.io.Serializable {

	// Fields

	private Long nvrId;
	private String nvrName;
	private String nvrPwd;
	private String nvrMobile;
	private String nvrPum;
	private String residentNo;
	private String nvrCard;
	private String nvrMail;
	private Date nvrRt;
	private String nvrExp1;
	private String nvrExp2;
	private String nvrExp3;
	private String nvrExp4;
	private String nvrFlag;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;
	// Constructors
	/** default constructor */
	public NjhwVisitorReg() {
	}

	/** minimal constructor */
	public NjhwVisitorReg(Long nvrId) {
		this.nvrId = nvrId;
	}

	/** full constructor */
	public NjhwVisitorReg(Long nvrId, String nvrName, String nvrPwd,
			String nvrMobile, String nvrPum, String residentNo, String nvrCard,
			String nvrMail, Date nvrRt, String nvrExp1, String nvrExp2,
			String nvrExp3, String nvrExp4, String nvrFlag, Long insertId,
			Date insertDate, Long modifyId, Date modifyDate) {
		this.nvrId = nvrId;
		this.nvrName = nvrName;
		this.nvrPwd = nvrPwd;
		this.nvrMobile = nvrMobile;
		this.nvrPum = nvrPum;
		this.residentNo = residentNo;
		this.nvrCard = nvrCard;
		this.nvrMail = nvrMail;
		this.nvrRt = nvrRt;
		this.nvrExp1 = nvrExp1;
		this.nvrExp2 = nvrExp2;
		this.nvrExp3 = nvrExp3;
		this.nvrExp4 = nvrExp4;
		this.nvrFlag = nvrFlag;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}

	// Property accessors
	@Id
	@Column(name = "NVR_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_VISITOR_REG")
	@SequenceGenerator(name="SEQ_NJHW_VISITOR_REG",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_VISITOR_REG")
	public Long getNvrId() {
		return this.nvrId;
	}

	public void setNvrId(Long nvrId) {
		this.nvrId = nvrId;
	}

	@Column(name = "NVR_NAME", length = 50)
	public String getNvrName() {
		return this.nvrName;
	}

	public void setNvrName(String nvrName) {
		this.nvrName = nvrName;
	}

	@Column(name = "NVR_PWD", length = 20)
	public String getNvrPwd() {
		return this.nvrPwd;
	}

	public void setNvrPwd(String nvrPwd) {
		this.nvrPwd = nvrPwd;
	}

	@Column(name = "NVR_MOBILE", length = 20)
	public String getNvrMobile() {
		return this.nvrMobile;
	}

	public void setNvrMobile(String nvrMobile) {
		this.nvrMobile = nvrMobile;
	}

	@Column(name = "NVR_PUM", length = 20)
	public String getNvrPum() {
		return this.nvrPum;
	}

	public void setNvrPum(String nvrPum) {
		this.nvrPum = nvrPum;
	}

	@Column(name = "RESIDENT_NO", length = 20)
	public String getResidentNo() {
		return this.residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	@Column(name = "NVR_CARD", length = 20)
	public String getNvrCard() {
		return this.nvrCard;
	}

	public void setNvrCard(String nvrCard) {
		this.nvrCard = nvrCard;
	}

	@Column(name = "NVR_MAIL", length = 50)
	public String getNvrMail() {
		return this.nvrMail;
	}

	public void setNvrMail(String nvrMail) {
		this.nvrMail = nvrMail;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "NVR_RT", length = 7)
	public Date getNvrRt() {
		return this.nvrRt;
	}

	public void setNvrRt(Date nvrRt) {
		this.nvrRt = nvrRt;
	}

	@Column(name = "NVR_EXP1", length = 20)
	public String getNvrExp1() {
		return this.nvrExp1;
	}

	public void setNvrExp1(String nvrExp1) {
		this.nvrExp1 = nvrExp1;
	}

	@Column(name = "NVR_EXP2", length = 20)
	public String getNvrExp2() {
		return this.nvrExp2;
	}

	public void setNvrExp2(String nvrExp2) {
		this.nvrExp2 = nvrExp2;
	}

	@Column(name = "NVR_EXP3", length = 20)
	public String getNvrExp3() {
		return this.nvrExp3;
	}

	public void setNvrExp3(String nvrExp3) {
		this.nvrExp3 = nvrExp3;
	}

	@Column(name = "NVR_EXP4", length = 20)
	public String getNvrExp4() {
		return this.nvrExp4;
	}

	public void setNvrExp4(String nvrExp4) {
		this.nvrExp4 = nvrExp4;
	}

	@Column(name = "NVR_FLAG", length = 1)
	public String getNvrFlag() {
		return this.nvrFlag;
	}

	public void setNvrFlag(String nvrFlag) {
		this.nvrFlag = nvrFlag;
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

}