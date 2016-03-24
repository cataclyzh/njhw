package com.cosmosource.app.entity;

import java.math.BigDecimal;
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
 * 物业管理  资产信息
 * EmAssetinfo entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EM_ASSETINFO")
public class EmAssetinfo implements java.io.Serializable {

	// Fields

	private Long easId;
	
	private Long ecpId;
	private Long esuId;
	private Long aetId;
	private String easName;
	private String aetName;
	private String ecpName;
	private String esuName;
	private String esuUnit;
	private Long orgId;
	private String orgName;
	private String easFlag;
	private BigDecimal easNum;
	private BigDecimal easLifeyear;
	private Date easPdate;
	private Date easUdate;
	private String easModel;
	private String easNorms;
	private String easPosition;
	private String resBak1;
	private String resBak2;
	private String resBak3;
	private String resBak4;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;

	// Constructors

	/** default constructor */
	public EmAssetinfo() {
	}

	/** minimal constructor */
	public EmAssetinfo(Long easId) {
		this.easId = easId;
	}

	/** full constructor */
	public EmAssetinfo(Long easId,String easName, String aetName,
			String ecpName, String esuName, String esuUnit, Long orgId,
			String orgName, String easFlag, BigDecimal easNum,
			BigDecimal easLifeyear, Date easPdate, Date easUdate,
			String easModel, String easNorms, String easPosition,
			String resBak1, String resBak2, String resBak3, String resBak4,
			Long insertId, Date insertDate, Long modifyId, Date modifyDate) {
		this.easId = easId;
		this.easName = easName;
		this.aetName = aetName;
		this.ecpName = ecpName;
		this.esuName = esuName;
		this.esuUnit = esuUnit;
		this.orgId = orgId;
		this.orgName = orgName;
		this.easFlag = easFlag;
		this.easNum = easNum;
		this.easLifeyear = easLifeyear;
		this.easPdate = easPdate;
		this.easUdate = easUdate;
		this.easModel = easModel;
		this.easNorms = easNorms;
		this.easPosition = easPosition;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}

	
	// Property accessors
	@Id
	@Column(name = "EAS_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EM_ASSETINFO")
	@SequenceGenerator(name="SEQ_EM_ASSETINFO",allocationSize=1,initialValue=1, sequenceName="SEQ_EM_ASSETINFO")	
	public Long getEasId() {
		return this.easId;
	}
	
	@Column(name = "ECP_ID", unique = true, precision = 12, scale = 0)
	public Long getEcpId() {
		return ecpId;
	}

	public void setEcpId(Long ecpId) {
		this.ecpId = ecpId;
	}
	
	@Column(name = "ESU_ID", unique = true, precision = 12, scale = 0)
	public Long getEsuId() {
		return esuId;
	}

	public void setEsuId(Long esuId) {
		this.esuId = esuId;
	}

	@Column(name = "AET_ID", unique = true, precision = 12, scale = 0)
	public Long getAetId() {
		return aetId;
	}

	public void setAetId(Long aetId) {
		this.aetId = aetId;
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

	@Column(name = "AET_NAME", length = 30)
	public String getAetName() {
		return this.aetName;
	}

	public void setAetName(String aetName) {
		this.aetName = aetName;
	}

	@Column(name = "ECP_NAME", length = 100)
	public String getEcpName() {
		return this.ecpName;
	}

	public void setEcpName(String ecpName) {
		this.ecpName = ecpName;
	}

	@Column(name = "ESU_NAME", length = 50)
	public String getEsuName() {
		return this.esuName;
	}

	public void setEsuName(String esuName) {
		this.esuName = esuName;
	}

	@Column(name = "ESU_UNIT", length = 50)
	public String getEsuUnit() {
		return this.esuUnit;
	}

	public void setEsuUnit(String esuUnit) {
		this.esuUnit = esuUnit;
	}

	@Column(name = "ORG_ID", precision = 12, scale = 0)
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "ORG_NAME", length = 100)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "EAS_FLAG", length = 2)
	public String getEasFlag() {
		return this.easFlag;
	}

	public void setEasFlag(String easFlag) {
		this.easFlag = easFlag;
	}

	@Column(name = "EAS_NUM", precision = 38, scale = 0)
	public BigDecimal getEasNum() {
		return this.easNum;
	}

	public void setEasNum(BigDecimal easNum) {
		this.easNum = easNum;
	}

	@Column(name = "EAS_LIFEYEAR", precision = 38, scale = 0)
	public BigDecimal getEasLifeyear() {
		return this.easLifeyear;
	}

	public void setEasLifeyear(BigDecimal easLifeyear) {
		this.easLifeyear = easLifeyear;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EAS_PDATE", length = 7)
	public Date getEasPdate() {
		return this.easPdate;
	}

	public void setEasPdate(Date easPdate) {
		this.easPdate = easPdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EAS_UDATE", length = 7)
	public Date getEasUdate() {
		return this.easUdate;
	}

	public void setEasUdate(Date easUdate) {
		this.easUdate = easUdate;
	}

	@Column(name = "EAS_MODEL", length = 30)
	public String getEasModel() {
		return this.easModel;
	}

	public void setEasModel(String easModel) {
		this.easModel = easModel;
	}

	@Column(name = "EAS_NORMS", length = 30)
	public String getEasNorms() {
		return this.easNorms;
	}

	public void setEasNorms(String easNorms) {
		this.easNorms = easNorms;
	}

	@Column(name = "EAS_POSITION", length = 30)
	public String getEasPosition() {
		return this.easPosition;
	}

	public void setEasPosition(String easPosition) {
		this.easPosition = easPosition;
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

}