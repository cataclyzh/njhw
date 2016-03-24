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
 * TCommonVendorQuest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_VENDOR_QUEST")
public class TCommonVendorQuest implements java.io.Serializable {
	
	private static final long serialVersionUID = 4467488489955607220L;
	private Long questId;
	private String questUser;
	private String questTitle;
	private String questCode;
	private String questCompany;
	private Date questTime;
	private String questType;
	private String questLevel;
	private String questStatus;
	private String vendorRegion;
	private String closedUser;
	private Date closedTime;
	private String dcFlag;
	private String loginName;

	// Constructors

	/** default constructor */
	public TCommonVendorQuest() {
	}

	/** minimal constructor */
	public TCommonVendorQuest(Long questId) {
		this.questId = questId;
	}

	/** full constructor */
	public TCommonVendorQuest(Long questId, String questUser,
			String questTitle, String questCode, String questCompany,
			Date questTime, String questType, String questLevel,
			String questStatus, String vendorRegion, String closedUser,
			Date closedTime, String dcFlag) {
		this.questId = questId;
		this.questUser = questUser;
		this.questTitle = questTitle;
		this.questCode = questCode;
		this.questCompany = questCompany;
		this.questTime = questTime;
		this.questType = questType;
		this.questLevel = questLevel;
		this.questStatus = questStatus;
		this.vendorRegion = vendorRegion;
		this.closedUser = closedUser;
		this.closedTime = closedTime;
		this.dcFlag = dcFlag;
	}

	// Property accessors
	@Id
	@Column(name = "QUEST_ID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_VENDOR_QUEST")
	@SequenceGenerator(name="SEQ_COMMON_VENDOR_QUEST",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_VENDOR_QUEST")
	public Long getQuestId() {
		return this.questId;
	}

	public void setQuestId(Long questId) {
		this.questId = questId;
	}

	@Column(name = "QUEST_USER", length = 20)
	public String getQuestUser() {
		return this.questUser;
	}

	public void setQuestUser(String questUser) {
		this.questUser = questUser;
	}

	@Column(name = "QUEST_TITLE", length = 60)
	public String getQuestTitle() {
		return this.questTitle;
	}

	public void setQuestTitle(String questTitle) {
		this.questTitle = questTitle;
	}

	@Column(name = "QUEST_CODE", length = 20)
	public String getQuestCode() {
		return this.questCode;
	}

	public void setQuestCode(String questCode) {
		this.questCode = questCode;
	}

	@Column(name = "QUEST_COMPANY", length = 60)
	public String getQuestCompany() {
		return this.questCompany;
	}

	public void setQuestCompany(String questCompany) {
		this.questCompany = questCompany;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUEST_TIME", length = 7)
	public Date getQuestTime() {
		return this.questTime;
	}

	public void setQuestTime(Date questTime) {
		this.questTime = questTime;
	}

	@Column(name = "QUEST_TYPE", length = 2)
	public String getQuestType() {
		return this.questType;
	}

	public void setQuestType(String questType) {
		this.questType = questType;
	}

	@Column(name = "QUEST_LEVEL", length = 2)
	public String getQuestLevel() {
		return this.questLevel;
	}

	public void setQuestLevel(String questLevel) {
		this.questLevel = questLevel;
	}

	@Column(name = "QUEST_STATUS", length = 2)
	public String getQuestStatus() {
		return this.questStatus;
	}

	public void setQuestStatus(String questStatus) {
		this.questStatus = questStatus;
	}

	@Column(name = "VENDOR_REGION", length = 2)
	public String getVendorRegion() {
		return this.vendorRegion;
	}

	public void setVendorRegion(String vendorRegion) {
		this.vendorRegion = vendorRegion;
	}

	@Column(name = "CLOSED_USER", length = 20)
	public String getClosedUser() {
		return this.closedUser;
	}

	public void setClosedUser(String closedUser) {
		this.closedUser = closedUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLOSED_TIME", length = 7)
	public Date getClosedTime() {
		return this.closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}

	@Column(name = "DC_FLAG", length = 2)
	public String getDcFlag() {
		return this.dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	@Column(name = "LOGINNAME", length = 20)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	

}