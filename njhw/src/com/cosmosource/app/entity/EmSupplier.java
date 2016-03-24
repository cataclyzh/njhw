package com.cosmosource.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 物业管理 供应商表
 * EmSupplier entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EM_SUPPLIER")
public class EmSupplier implements java.io.Serializable {

	// Fields

	private Long esuId;
	private String esuName;
	private String esuPname;
	private String esuPdname;
	private String esuAddr;
	private String esuLman;
	private String esuTel;
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
	public EmSupplier() {
	}

	/** minimal constructor */
	public EmSupplier(Long esuId) {
		this.esuId = esuId;
	}

	/** full constructor */
	public EmSupplier(Long esuId, String esuName, String esuPname,
			String esuPdname, String esuAddr, String esuLman, String esuTel,
			String resBak1, String resBak2, String resBak3, String resBak4,
			Long insertId, Date insertDate, Long modifyId, Date modifyDate) {
		this.esuId = esuId;
		this.esuName = esuName;
		this.esuPname = esuPname;
		this.esuPdname = esuPdname;
		this.esuAddr = esuAddr;
		this.esuLman = esuLman;
		this.esuTel = esuTel;
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
	@Column(name = "ESU_ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getEsuId() {
		return this.esuId;
	}

	public void setEsuId(Long esuId) {
		this.esuId = esuId;
	}

	@Column(name = "ESU_NAME", length = 50)
	public String getEsuName() {
		return this.esuName;
	}

	public void setEsuName(String esuName) {
		this.esuName = esuName;
	}

	@Column(name = "ESU_PNAME", length = 50)
	public String getEsuPname() {
		return this.esuPname;
	}

	public void setEsuPname(String esuPname) {
		this.esuPname = esuPname;
	}

	@Column(name = "ESU_PDNAME", length = 50)
	public String getEsuPdname() {
		return this.esuPdname;
	}

	public void setEsuPdname(String esuPdname) {
		this.esuPdname = esuPdname;
	}

	@Column(name = "ESU_ADDR", length = 50)
	public String getEsuAddr() {
		return this.esuAddr;
	}

	public void setEsuAddr(String esuAddr) {
		this.esuAddr = esuAddr;
	}

	@Column(name = "ESU_LMAN", length = 20)
	public String getEsuLman() {
		return this.esuLman;
	}

	public void setEsuLman(String esuLman) {
		this.esuLman = esuLman;
	}

	@Column(name = "ESU_TEL", length = 20)
	public String getEsuTel() {
		return this.esuTel;
	}

	public void setEsuTel(String esuTel) {
		this.esuTel = esuTel;
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