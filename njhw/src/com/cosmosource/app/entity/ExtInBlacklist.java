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
 * 黑名单
 * ExtInBlacklist entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXT_IN_BLACKLIST")
public class ExtInBlacklist implements java.io.Serializable {

	// Fields

	private String blCardId;
	private String blVersion;
	private Date blDate;
	private String blBak1;
	private String blBak2;
	private String blBak3;
	private String blBak4;
	private Long insertId;
	private Date insertDate;

	// Constructors

	/** default constructor */
	public ExtInBlacklist() {
	}

	/** minimal constructor */
	public ExtInBlacklist(String blCardId) {
		this.blCardId = blCardId;
	}

	/** full constructor */
	public ExtInBlacklist(String blCardId, String blVersion, Date blDate,
			String blBak1, String blBak2, String blBak3, String blBak4,
			Long insertId, Date insertDate) {
		this.blCardId = blCardId;
		this.blVersion = blVersion;
		this.blDate = blDate;
		this.blBak1 = blBak1;
		this.blBak2 = blBak2;
		this.blBak3 = blBak3;
		this.blBak4 = blBak4;
		this.insertId = insertId;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@Column(name = "BL_CARD_ID", unique = true, nullable = false, length = 20)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EXT_IN_BLACKLIST")
	@SequenceGenerator(name="SEQ_EXT_IN_BLACKLIST",allocationSize=1,initialValue=1, sequenceName="SEQ_EXT_IN_BLACKLIST")
	public String getBlCardId() {
		return this.blCardId;
	}

	public void setBlCardId(String blCardId) {
		this.blCardId = blCardId;
	}

	@Column(name = "BL_VERSION", length = 20)
	public String getBlVersion() {
		return this.blVersion;
	}

	public void setBlVersion(String blVersion) {
		this.blVersion = blVersion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BL_DATE", length = 7)
	public Date getBlDate() {
		return this.blDate;
	}

	public void setBlDate(Date blDate) {
		this.blDate = blDate;
	}

	@Column(name = "BL_BAK1", length = 20)
	public String getBlBak1() {
		return this.blBak1;
	}

	public void setBlBak1(String blBak1) {
		this.blBak1 = blBak1;
	}

	@Column(name = "BL_BAK2", length = 20)
	public String getBlBak2() {
		return this.blBak2;
	}

	public void setBlBak2(String blBak2) {
		this.blBak2 = blBak2;
	}

	@Column(name = "BL_BAK3", length = 20)
	public String getBlBak3() {
		return this.blBak3;
	}

	public void setBlBak3(String blBak3) {
		this.blBak3 = blBak3;
	}

	@Column(name = "BL_BAK4", length = 20)
	public String getBlBak4() {
		return this.blBak4;
	}

	public void setBlBak4(String blBak4) {
		this.blBak4 = blBak4;
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

}