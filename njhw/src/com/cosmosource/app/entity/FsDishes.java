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
 * 食堂菜肴
 * FsDishes entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FS_DISHES")
public class FsDishes implements java.io.Serializable {
	public static final String FSD_CALSS_BIGMEAT = "1"; 
	public static final String FSD_CALSS_SMAMEAT = "2"; 
	public static final String FSD_CALSS_MEAL="3"; 
	public static final String FSD_CALSS_DRINK = "4"; 
	public static final String FSD_CALSS_VEGEB="5"; 
	
	// Fields

	private Long fdId;
	private Long fmId;
	private String fdName;	// 名称
	private String fdMain;
	private String fdDosing;
	private String fdPract;
	private Double fdPrice;
	private String fdPhoto1;
	private String fdPhoto2;
	private String fdPhoto3;
	private String fdDesc;
	private String fdClass;
	private Date fdSt;
	private Date fdEt;
	private String fdFlag;
	private String fdBak1;	// 菜单备注 
	private String fdBak2;
	private String fdBak3;
	private String fdBak4;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;
	// Constructors

	/** default constructor */
	public FsDishes() {
	}

	/** minimal constructor */
	public FsDishes(Long fdId) {
		this.fdId = fdId;
	}

	/** full constructor */
	public FsDishes(Long fdId, Long fmId, String fdName, String fdMain,
			String fdDosing, String fdPract, Double fdPrice, String fdPhoto1,String fdPhoto2,String fdPhoto3,
			String fdDesc, String fdClass, Date fdSt, Date fdEt, String fdFlag,
			String fdBak1, String fdBak2, String fdBak3, String fdBak4,
			Long insertId, Date insertDate, Long modifyId, Date modifyDate) {
		this.fdId = fdId;
		this.fmId = fmId;
		this.fdName = fdName;
		this.fdMain = fdMain;
		this.fdDosing = fdDosing;
		this.fdPract = fdPract;
		this.fdPrice = fdPrice;
		this.fdPhoto1 = fdPhoto1;
		this.fdPhoto2 = fdPhoto2;
		this.fdPhoto3 = fdPhoto3;
		this.fdDesc = fdDesc;
		this.fdClass = fdClass;
		this.fdSt = fdSt;
		this.fdEt = fdEt;
		this.fdFlag = fdFlag;
		this.fdBak1 = fdBak1;
		this.fdBak2 = fdBak2;
		this.fdBak3 = fdBak3;
		this.fdBak4 = fdBak4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}

	// Property accessors
	@Id
	@Column(name = "FD_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FS_DISHES")
	@SequenceGenerator(name="SEQ_FS_DISHES",allocationSize=1,initialValue=1, sequenceName="SEQ_FS_DISHES")
	public Long getFdId() {
		return this.fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	@Column(name = "FM_ID", precision = 12, scale = 0)
	public Long getFmId() {
		return this.fmId;
	}

	public void setFmId(Long fmId) {
		this.fmId = fmId;
	}

	@Column(name = "FD_NAME", length = 30)
	public String getFdName() {
		return this.fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	@Column(name = "FD_MAIN", length = 100)
	public String getFdMain() {
		return this.fdMain;
	}

	public void setFdMain(String fdMain) {
		this.fdMain = fdMain;
	}

	@Column(name = "FD_DOSING", length = 100)
	public String getFdDosing() {
		return this.fdDosing;
	}

	public void setFdDosing(String fdDosing) {
		this.fdDosing = fdDosing;
	}

	@Column(name = "FD_PRACT", length = 100)
	public String getFdPract() {
		return this.fdPract;
	}

	public void setFdPract(String fdPract) {
		this.fdPract = fdPract;
	}

	@Column(name = "FD_PRICE", precision = 6)
	public Double getFdPrice() {
		return this.fdPrice;
	}

	public void setFdPrice(Double fdPrice) {
		this.fdPrice = fdPrice;
	}

	@Column(name = "FD_PHOTO1", length = 200)
	public String getFdPhoto1() {
		return fdPhoto1;
	}

	public void setFdPhoto1(String fdPhoto1) {
		this.fdPhoto1 = fdPhoto1;
	}
	
	@Column(name = "FD_PHOTO2", length = 200)
	public String getFdPhoto2() {
		return fdPhoto2;
	}

	public void setFdPhoto2(String fdPhoto2) {
		this.fdPhoto2 = fdPhoto2;
	}
	
	@Column(name = "FD_PHOTO3", length = 200)
	public String getFdPhoto3() {
		return fdPhoto3;
	}

	public void setFdPhoto3(String fdPhoto3) {
		this.fdPhoto3 = fdPhoto3;
	}

	@Column(name = "FD_DESC", length = 200)
	public String getFdDesc() {
		return this.fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	@Column(name = "FD_CLASS", length = 2)
	public String getFdClass() {
		return this.fdClass;
	}

	public void setFdClass(String fdClass) {
		this.fdClass = fdClass;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FD_ST", length = 7)
	public Date getFdSt() {
		return this.fdSt;
	}

	public void setFdSt(Date fdSt) {
		this.fdSt = fdSt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FD_ET", length = 7)
	public Date getFdEt() {
		return this.fdEt;
	}

	public void setFdEt(Date fdEt) {
		this.fdEt = fdEt;
	}

	@Column(name = "FD_FLAG", length = 1)
	public String getFdFlag() {
		return this.fdFlag;
	}

	public void setFdFlag(String fdFlag) {
		this.fdFlag = fdFlag;
	}

	@Column(name = "FD_BAK1", length = 20)
	public String getFdBak1() {
		return this.fdBak1;
	}

	public void setFdBak1(String fdBak1) {
		this.fdBak1 = fdBak1;
	}

	@Column(name = "FD_BAK2", length = 20)
	public String getFdBak2() {
		return this.fdBak2;
	}

	public void setFdBak2(String fdBak2) {
		this.fdBak2 = fdBak2;
	}

	@Column(name = "FD_BAK3", length = 20)
	public String getFdBak3() {
		return this.fdBak3;
	}

	public void setFdBak3(String fdBak3) {
		this.fdBak3 = fdBak3;
	}

	@Column(name = "FD_BAK4", length = 20)
	public String getFdBak4() {
		return this.fdBak4;
	}

	public void setFdBak4(String fdBak4) {
		this.fdBak4 = fdBak4;
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