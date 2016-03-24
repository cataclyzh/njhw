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
 * 菜单表
 * FsMenu entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FS_MENU")
public class FsMenu implements java.io.Serializable {

	// Fields

	private Long fmId;
	private String fmName;
	private Double fmNorm;
	private String fmBak1;
	private String fmBak2;
	private String fmBak3;
	private String fmBak4;
	private String fmFlag;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;

	// Constructors

	/** default constructor */
	public FsMenu() {
	}

	/** minimal constructor */
	public FsMenu(Long fmId) {
		this.fmId = fmId;
	}

	/** full constructor */
	public FsMenu(Long fmId, String fmName, Double fmNorm, String fmBak1,
			String fmBak2, String fmBak3, String fmBak4, String fmFlag,
			Long insertId, Date insertDate, Long modifyId, Date modifyDate) {
		this.fmId = fmId;
		this.fmName = fmName;
		this.fmNorm = fmNorm;
		this.fmBak1 = fmBak1;
		this.fmBak2 = fmBak2;
		this.fmBak3 = fmBak3;
		this.fmBak4 = fmBak4;
		this.fmFlag = fmFlag;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}

	// Property accessors
	@Id
	@Column(name = "FM_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FS_MENU")
	@SequenceGenerator(name="SEQ_FS_MENU",allocationSize=1,initialValue=1, sequenceName="SEQ_FS_MENU")
	public Long getFmId() {
		return this.fmId;
	}

	public void setFmId(Long fmId) {
		this.fmId = fmId;
	}

	@Column(name = "FM_NAME", length = 30)
	public String getFmName() {
		return this.fmName;
	}

	public void setFmName(String fmName) {
		this.fmName = fmName;
	}

	@Column(name = "FM_NORM", precision = 6)
	public Double getFmNorm() {
		return this.fmNorm;
	}

	public void setFmNorm(Double fmNorm) {
		this.fmNorm = fmNorm;
	}

	@Column(name = "FM_BAK1", length = 20)
	public String getFmBak1() {
		return this.fmBak1;
	}

	public void setFmBak1(String fmBak1) {
		this.fmBak1 = fmBak1;
	}

	@Column(name = "FM_BAK2", length = 20)
	public String getFmBak2() {
		return this.fmBak2;
	}

	public void setFmBak2(String fmBak2) {
		this.fmBak2 = fmBak2;
	}

	@Column(name = "FM_BAK3", length = 20)
	public String getFmBak3() {
		return this.fmBak3;
	}

	public void setFmBak3(String fmBak3) {
		this.fmBak3 = fmBak3;
	}

	@Column(name = "FM_BAK4", length = 20)
	public String getFmBak4() {
		return this.fmBak4;
	}

	public void setFmBak4(String fmBak4) {
		this.fmBak4 = fmBak4;
	}

	@Column(name = "FM_FLAG", length = 1)
	public String getFmFlag() {
		return this.fmFlag;
	}

	public void setFmFlag(String fmFlag) {
		this.fmFlag = fmFlag;
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