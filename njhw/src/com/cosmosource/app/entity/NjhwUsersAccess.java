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
 * 用户门禁闸机管理表信息
 * NjhwUsersAccess entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NJHW_USERS_ACCESS")
public class NjhwUsersAccess implements java.io.Serializable {

	private Long nuacId;
	private Long userid;
	private String authIdsG;
	private String authIdsA;
	private Date appTime;
	private String appStatus;
	private String appBak;
	private String nuacExp1;
	private String nuacExp2;
	private String nuacExp3;
	private Long insertId;
	private Date insertDate;
	private Long updateId;
	private Date updateDate;
	private Long lockVer;

	/** default constructor */
	public NjhwUsersAccess() {
	}

	/** minimal constructor */
	public NjhwUsersAccess(Long nuacId) {
		this.nuacId = nuacId;
	}

	/** full constructor */
	public NjhwUsersAccess(Long nuacId, Long userid, String authIdsG, String authIdsA,
			Date appTime, String appStatus, String appBak,
			String nuacExp1, String nuacExp2, String nuacExp3,
			Long insertId, Date insertDate, Long updateId, Date updateDate,
			Long lockVer) {
		this.nuacId = nuacId;
		this.userid = userid;
		this.authIdsG = authIdsG;
		this.authIdsA = authIdsA;
		this.appTime = appTime;
		this.appStatus = appStatus;
		this.appBak = appBak;
		this.nuacExp1 = nuacExp1;
		this.nuacExp2 = nuacExp2;
		this.nuacExp3 = nuacExp3;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.updateId = updateId;
		this.updateDate = updateDate;
		this.lockVer = lockVer;
	}

	// Property accessors
	@Id
	@Column(name = "NUAC_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_USERS_ACCESS")
	@SequenceGenerator(name="SEQ_NJHW_USERS_ACCESS",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_USERS_ACCESS")
	public Long getNuacId() {
		return this.nuacId;
	}

	public void setNuacId(Long nuacId) {
		this.nuacId = nuacId;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "AUTH_IDS_G", length = 512)
	public String getAuthIdsG() {
		return this.authIdsG;
	}

	public void setAuthIdsG(String authIdsG) {
		this.authIdsG = authIdsG;
	}
	
	@Column(name = "AUTH_IDS_A", length = 512)
	public String getAuthIdsA() {
		return this.authIdsA;
	}

	public void setAuthIdsA(String authIdsA) {
		this.authIdsA = authIdsA;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APP_TIME", length = 7)
	public Date getAppTime() {
		return this.appTime;
	}

	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}
	
	@Column(name = "APP_STATUS", length = 2)
	public String getAppStatus() {
		return this.appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	
	@Column(name = "APP_BAK", length = 500)
	public String getAppBak() {
		return this.appBak;
	}

	public void setAppBak(String appBak) {
		this.appBak = appBak;
	}
	
	@Column(name = "NUAC_EXP1", length = 50)
	public String getNuacExp1() {
		return this.nuacExp1;
	}

	public void setNuacExp1(String nuacExp1) {
		this.nuacExp1 = nuacExp1;
	}
	
	@Column(name = "NUAC_EXP2", length = 50)
	public String getNuacExp2() {
		return this.nuacExp2;
	}

	public void setNuacExp2(String nuacExp2) {
		this.nuacExp2 = nuacExp2;
	}
	
	@Column(name = "NUAC_EXP3", length = 50)
	public String getNuacExp3() {
		return this.nuacExp3;
	}

	public void setNuacExp3(String nuacExp3) {
		this.nuacExp3 = nuacExp3;
	}

	@Column(name = "INSERT_ID", precision = 12, scale = 0)
	public Long getInsertId() {
		return this.insertId;
	}

	public void setInsertId(Long insertId) {
		this.insertId = insertId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_DATE", length = 7)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Column(name = "UPDATE_ID", precision = 12, scale = 0)
	public Long getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", length = 7)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "LOCK_VER", precision = 12, scale = 0)
	public Long getLockVer() {
		return this.lockVer;
	}
	
	public void setLockVer(Long lockVer) {
		this.lockVer = lockVer;
	}
}