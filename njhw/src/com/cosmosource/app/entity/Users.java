package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USERS")
public class Users implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private Long userid;
	private String displayName;
	private String loginUid;
	private String loginPwd;
	private Long activeFlag;
	private Long isSystem;
	private Long creator;
	private String creatDate;
	private Long lastUpdateBy;
	private String lastUpdateDate;
	private String memo;
	private Long changeLoginPwdFlag;
	private Long validity;
	private Long UType;
	private Long PId;
	private Long orgId;
	private String UCode;
	private Integer orderNum;
	private String loginPwdMd5;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(Long userid, Long isSystem,
			Long changeLoginPwdFlag) {
		this.userid = userid;
		this.isSystem = isSystem;
		this.changeLoginPwdFlag = changeLoginPwdFlag;
	}

	/** full constructor */
	public Users(Long userid, String displayName, String loginUid,
			String loginPwd, Long activeFlag, Long isSystem,
			Long creator, String creatDate, Long lastUpdateBy,
			String lastUpdateDate, String memo, Long changeLoginPwdFlag,
			Long validity, Long UType, Long PId,
			Long orgId, String UCode, Integer orderNum,String loginPwdMd5) {
		this.userid = userid;
		this.displayName = displayName;
		this.loginUid = loginUid;
		this.loginPwd = loginPwd;
		this.activeFlag = activeFlag;
		this.isSystem = isSystem;
		this.creator = creator;
		this.creatDate = creatDate;
		this.lastUpdateBy = lastUpdateBy;
		this.lastUpdateDate = lastUpdateDate;
		this.memo = memo;
		this.changeLoginPwdFlag = changeLoginPwdFlag;
		this.validity = validity;
		this.UType = UType;
		this.PId = PId;
		this.orgId = orgId;
		this.UCode = UCode;
		this.orderNum = orderNum;
		this.loginPwdMd5=loginPwdMd5;
	}

	// Property accessors
	@Id
	@Column(name = "USERID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USERS")
	@SequenceGenerator(name="SEQ_USERS",allocationSize=1,initialValue=1, sequenceName="SEQ_USERS")
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "DISPLAY_NAME", length = 20)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Column(name = "LOGIN_UID", length = 20)
	public String getLoginUid() {
		return this.loginUid;
	}

	public void setLoginUid(String loginUid) {
		this.loginUid = loginUid;
	}

	@Column(name = "LOGIN_PWD", length = 20)
	public String getLoginPwd() {
		return this.loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	@Column(name = "ACTIVE_FLAG", precision = 22, scale = 0)
	public Long getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(Long activeFlag) {
		this.activeFlag = activeFlag;
	}

	@Column(name = "IS_SYSTEM", nullable = false, precision = 22, scale = 0)
	public Long getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(Long isSystem) {
		this.isSystem = isSystem;
	}

	@Column(name = "CREATOR", precision = 22, scale = 0)
	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	@Column(name = "CREAT_DATE", length = 20)
	public String getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	@Column(name = "LAST_UPDATE_BY", precision = 22, scale = 0)
	public Long getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	@Column(name = "LAST_UPDATE_DATE", length = 20)
	public String getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "MEMO")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "CHANGE_LOGIN_PWD_FLAG", nullable = false, precision = 22, scale = 0)
	public Long getChangeLoginPwdFlag() {
		return this.changeLoginPwdFlag;
	}

	public void setChangeLoginPwdFlag(Long changeLoginPwdFlag) {
		this.changeLoginPwdFlag = changeLoginPwdFlag;
	}

	@Column(name = "VALIDITY", precision = 22, scale = 0)
	public Long getValidity() {
		return this.validity;
	}

	public void setValidity(Long validity) {
		this.validity = validity;
	}

	@Column(name = "U_TYPE", precision = 22, scale = 0)
	public Long getUType() {
		return this.UType;
	}

	public void setUType(Long UType) {
		this.UType = UType;
	}

	@Column(name = "P_ID", precision = 22, scale = 0)
	public Long getPId() {
		return this.PId;
	}

	public void setPId(Long PId) {
		this.PId = PId;
	}

	@Column(name = "ORG_ID", precision = 22, scale = 0)
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "U_CODE", length = 10)
	public String getUCode() {
		return this.UCode;
	}

	public void setUCode(String UCode) {
		this.UCode = UCode;
	}

	@Column(name = "ORDER_NUM", length = 10)
	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "LOGIN_PWD_MD5", length = 32)
	public String getLoginPwdMd5() {
		return loginPwdMd5;
	}

	public void setLoginPwdMd5(String loginPwdMd5) {
		this.loginPwdMd5 = loginPwdMd5;
	}

}