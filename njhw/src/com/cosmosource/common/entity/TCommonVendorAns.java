package com.cosmosource.common.entity;

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
 * TCommonVendorAns entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_VENDOR_ANS")
public class TCommonVendorAns implements java.io.Serializable {

	private static final long serialVersionUID = -2548316547461837651L;
	private Long ansId;
	private Long questId;
	private String ansUser;
	private String ansCompany;
	private Date ansTime;
	private String ansContent;
	private String ansType;

	// Constructors

	/** default constructor */
	public TCommonVendorAns() {
	}

	/** minimal constructor */
	public TCommonVendorAns(Long ansId) {
		this.ansId = ansId;
	}

	/** full constructor */
	public TCommonVendorAns(Long ansId, Long questId, String ansUser,
			String ansCompany, Date ansTime, String ansContent, String ansType) {
		this.ansId = ansId;
		this.questId = questId;
		this.ansUser = ansUser;
		this.ansCompany = ansCompany;
		this.ansTime = ansTime;
		this.ansContent = ansContent;
		this.ansType = ansType;
	}

	// Property accessors
	@Id
	@Column(name = "ANS_ID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_KNOWLEDGE")
	@SequenceGenerator(name="SEQ_COMMON_KNOWLEDGE",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_KNOWLEDGE")
	public Long getAnsId() {
		return this.ansId;
	}

	public void setAnsId(Long ansId) {
		this.ansId = ansId;
	}

	@Column(name = "QUEST_ID", precision = 16, scale = 0)
	public Long getQuestId() {
		return this.questId;
	}

	public void setQuestId(Long questId) {
		this.questId = questId;
	}

	@Column(name = "ANS_USER", length = 20)
	public String getAnsUser() {
		return this.ansUser;
	}

	public void setAnsUser(String ansUser) {
		this.ansUser = ansUser;
	}

	@Column(name = "ANS_COMPANY", length = 60)
	public String getAnsCompany() {
		return this.ansCompany;
	}

	public void setAnsCompany(String ansCompany) {
		this.ansCompany = ansCompany;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ANS_TIME", length = 7)
	public Date getAnsTime() {
		return this.ansTime;
	}

	public void setAnsTime(Date ansTime) {
		this.ansTime = ansTime;
	}

	@Column(name = "ANS_CONTENT", length = 1000)
	public String getAnsContent() {
		return this.ansContent;
	}

	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}

	@Column(name = "ANS_TYPE", length = 2)
	public String getAnsType() {
		return this.ansType;
	}

	public void setAnsType(String ansType) {
		this.ansType = ansType;
	}

}