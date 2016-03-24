package com.cosmosource.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 用户车牌号
 * NjhwUsersPlatenum entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NJHW_USERS_PLATENUM")
public class NjhwUsersPlatenum implements java.io.Serializable {

	//2 占用内部车位 	免费停车
	public static final String PLATENUM_FASTEN = "2";
	//1  不占用内部车位 	收费停车
	public static final String PLATENUM_NO_FASTEN = "1";
	
	// Fields

	private String nupPn;
	private Long userid;
	private String nupFlag;
	private String nupExp1;
	private String nupExp2;
	private String nupExp3;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;

	// Constructors

	/** default constructor */
	public NjhwUsersPlatenum() {
	}

	/** minimal constructor */
	public NjhwUsersPlatenum(String nupPn) {
		this.nupPn = nupPn;
	}

	/** full constructor */
	public NjhwUsersPlatenum(String nupPn, Long userid, String nupFlag,
			String nupExp1, String nupExp2, String nupExp3, Long insertId,
			Date insertDate, Long modifyId, Date modifyDate) {
		super();
		this.nupPn = nupPn;
		this.userid = userid;
		this.nupFlag = nupFlag;
		this.nupExp1 = nupExp1;
		this.nupExp2 = nupExp2;
		this.nupExp3 = nupExp3;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}
	
	@Id
	@Column(name = "NUP_PN", unique = true, nullable = false, length = 20)
	public String getNupPn() {
		return this.nupPn;
	}

	public void setNupPn(String nupPn) {
		this.nupPn = nupPn;
	}

	@Column(name = "NUP_FLAG", length = 1)
	public String getNupFlag() {
		return this.nupFlag;
	}

	public void setNupFlag(String nupFlag) {
		this.nupFlag = nupFlag;
	}

	@Column(name = "NUP_EXP1", length = 20)
	public String getNupExp1() {
		return this.nupExp1;
	}

	public void setNupExp1(String nupExp1) {
		this.nupExp1 = nupExp1;
	}

	@Column(name = "NUP_EXP2", length = 20)
	public String getNupExp2() {
		return this.nupExp2;
	}

	public void setNupExp2(String nupExp2) {
		this.nupExp2 = nupExp2;
	}

	@Column(name = "NUP_EXP3", length = 20)
	public String getNupExp3() {
		return this.nupExp3;
	}

	public void setNupExp3(String nupExp3) {
		this.nupExp3 = nupExp3;
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

	@Column(name = "USERID", precision = 22, scale = 0)
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
}