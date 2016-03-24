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
 * 组织结构分配房间、门禁、IP电话及登记车牌资源
 * EmOrgRes entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EM_ORG_RES")
public class EmOrgRes implements java.io.Serializable {
	//1 分配房间；
	public static final String EOR_TYPE_ROOM = "1";
	//2  分配门禁；
	public static final String EOR_TYPE_GLASS = "2";
	//3 分配IP电话；
	public static final String EOR_TYPE_PHONE = "3";
	//4 登记车牌;
	public static final String EOR_TYPE_CARD = "4";
	//5 分配电话号码;
	public static final String EOR_TYPE_TEL_NO = "5";
	//6 分配停车位;
	public static final String EOR_PARKING = "6";
	
	// Fields
	private Long eorId;
	private Long orgId;
	private String orgName;
	private Long resId;
	private String resName;
	private String eorType;
	private String porExp1;
	private String porExp2;
	private String porExp3;
	private String porExp4;
	private String porExp5;
	private Date porSt;
	private Date porEt;
	private String porFlag;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;

	// Constructors

	/** default constructor */
	public EmOrgRes() {
	}

	/** minimal constructor */
	public EmOrgRes(Long eorId) {
		this.eorId = eorId;
	}

	/** full constructor */
	public EmOrgRes(Long eorId, Long orgId, String orgName, Long resId,
			String resName, String eorType, String porExp1, String porExp2,
			String porExp3, String porExp4, String porExp5, Date porSt,
			Date porEt, String porFlag, Long insertId, Date insertDate,
			Long modifyId, Date modifyDate) {
		this.eorId = eorId;
		this.orgId = orgId;
		this.orgName = orgName;
		this.resId = resId;
		this.resName = resName;
		this.eorType = eorType;
		this.porExp1 = porExp1;
		this.porExp2 = porExp2;
		this.porExp3 = porExp3;
		this.porExp4 = porExp4;
		this.porExp5 = porExp5;
		this.porSt = porSt;
		this.porEt = porEt;
		this.porFlag = porFlag;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}

	// Property accessors
	@Id
	@Column(name = "EOR_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EM_ORG_RES")
	@SequenceGenerator(name="SEQ_EM_ORG_RES",allocationSize=1,initialValue=1, sequenceName="SEQ_EM_ORG_RES")
	public Long getEorId() {
		return this.eorId;
	}

	public void setEorId(Long eorId) {
		this.eorId = eorId;
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

	@Column(name = "RES_ID", precision = 12, scale = 0)
	public Long getResId() {
		return this.resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	@Column(name = "RES_NAME", length = 50)
	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	@Column(name = "EOR_TYPE", length = 1)
	public String getEorType() {
		return this.eorType;
	}

	public void setEorType(String eorType) {
		this.eorType = eorType;
	}

	@Column(name = "POR_EXP1", length = 50)
	public String getPorExp1() {
		return this.porExp1;
	}

	public void setPorExp1(String porExp1) {
		this.porExp1 = porExp1;
	}

	@Column(name = "POR_EXP2", length = 50)
	public String getPorExp2() {
		return this.porExp2;
	}

	public void setPorExp2(String porExp2) {
		this.porExp2 = porExp2;
	}

	@Column(name = "POR_EXP3", length = 50)
	public String getPorExp3() {
		return this.porExp3;
	}

	public void setPorExp3(String porExp3) {
		this.porExp3 = porExp3;
	}

	@Column(name = "POR_EXP4", length = 50)
	public String getPorExp4() {
		return this.porExp4;
	}

	public void setPorExp4(String porExp4) {
		this.porExp4 = porExp4;
	}

	@Column(name = "POR_EXP5", length = 50)
	public String getPorExp5() {
		return this.porExp5;
	}

	public void setPorExp5(String porExp5) {
		this.porExp5 = porExp5;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "POR_ST", length = 7)
	public Date getPorSt() {
		return this.porSt;
	}

	public void setPorSt(Date porSt) {
		this.porSt = porSt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "POR_ET", length = 7)
	public Date getPorEt() {
		return this.porEt;
	}

	public void setPorEt(Date porEt) {
		this.porEt = porEt;
	}

	@Column(name = "POR_FLAG", length = 1)
	public String getPorFlag() {
		return this.porFlag;
	}

	public void setPorFlag(String porFlag) {
		this.porFlag = porFlag;
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