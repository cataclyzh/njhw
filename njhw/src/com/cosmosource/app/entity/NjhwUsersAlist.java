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
 * 个人通讯录
 * NjhwUsersAlist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NJHW_USERS_ALIST")
public class NjhwUsersAlist implements java.io.Serializable {

	// Fields

	private Long nuaId;
	private Long nuaGroup;
	private Long userid;
	private String userName;
	private String nuaName;
	private String nuaName1;
	private String nuaPhone;
	private String nuaTel1;
	private String nuaTel2;
	private String nuaTel3;
	private String nuaMail;
	private String nuaExp1;
	private String nuaExp2;
	private String nuaExp4;
	private String nuaExp3;
	private Long insertId;
	private Date insertDate;

	// Constructors

	/** default constructor */
	public NjhwUsersAlist() {
	}

	/** minimal constructor */
	public NjhwUsersAlist(Long nuaId) {
		this.nuaId = nuaId;
	}

	/** full constructor */
	public NjhwUsersAlist(Long nuaId, Long nuaGroup,
			Long userid, String userName, String nuaName, String nuaName1,
			String nuaPhone, String nuaTel1, String nuaTel2, String nuaTel3,
			String nuaMail, String nuaExp1, String nuaExp2, String nuaExp4,
			String nuaExp3, Long insertId, Date insertDate) {
		this.nuaId = nuaId;
		this.nuaGroup = nuaGroup;
		this.userid = userid;
		this.userName = userName;
		this.nuaName = nuaName;
		this.nuaName1 = nuaName1;
		this.nuaPhone = nuaPhone;
		this.nuaTel1 = nuaTel1;
		this.nuaTel2 = nuaTel2;
		this.nuaTel3 = nuaTel3;
		this.nuaMail = nuaMail;
		this.nuaExp1 = nuaExp1;
		this.nuaExp2 = nuaExp2;
		this.nuaExp4 = nuaExp4;
		this.nuaExp3 = nuaExp3;
		this.insertId = insertId;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@Column(name = "NUA_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_USERS_ALIST")
	@SequenceGenerator(name="SEQ_NJHW_USERS_ALIST",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_USERS_ALIST")
	public Long getNuaId() {
		return this.nuaId;
	}

	public void setNuaId(Long nuaId) {
		this.nuaId = nuaId;
	}

	@Column(name = "NUA_GROUP", precision = 12, scale = 0)
	public Long getNuaGroup() {
		return nuaGroup;
	}

	public void setNuaGroup(Long nuaGroup) {
		this.nuaGroup = nuaGroup;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "NUA_NAME", length = 20)
	public String getNuaName() {
		return this.nuaName;
	}

	public void setNuaName(String nuaName) {
		this.nuaName = nuaName;
	}

	@Column(name = "NUA_NAME1", length = 20)
	public String getNuaName1() {
		return this.nuaName1;
	}

	public void setNuaName1(String nuaName1) {
		this.nuaName1 = nuaName1;
	}

	@Column(name = "NUA_PHONE", length = 20)
	public String getNuaPhone() {
		return this.nuaPhone;
	}

	public void setNuaPhone(String nuaPhone) {
		this.nuaPhone = nuaPhone;
	}

	@Column(name = "NUA_TEL1", length = 20)
	public String getNuaTel1() {
		return this.nuaTel1;
	}

	public void setNuaTel1(String nuaTel1) {
		this.nuaTel1 = nuaTel1;
	}

	@Column(name = "NUA_TEL2", length = 20)
	public String getNuaTel2() {
		return this.nuaTel2;
	}

	public void setNuaTel2(String nuaTel2) {
		this.nuaTel2 = nuaTel2;
	}

	@Column(name = "NUA_TEL3", length = 20)
	public String getNuaTel3() {
		return this.nuaTel3;
	}

	public void setNuaTel3(String nuaTel3) {
		this.nuaTel3 = nuaTel3;
	}

	@Column(name = "NUA_MAIL", length = 50)
	public String getNuaMail() {
		return this.nuaMail;
	}

	public void setNuaMail(String nuaMail) {
		this.nuaMail = nuaMail;
	}

	@Column(name = "NUA_EXP1", length = 20)
	public String getNuaExp1() {
		return this.nuaExp1;
	}

	public void setNuaExp1(String nuaExp1) {
		this.nuaExp1 = nuaExp1;
	}

	@Column(name = "NUA_EXP2", length = 20)
	public String getNuaExp2() {
		return this.nuaExp2;
	}

	public void setNuaExp2(String nuaExp2) {
		this.nuaExp2 = nuaExp2;
	}

	@Column(name = "NUA_EXP4", length = 20)
	public String getNuaExp4() {
		return this.nuaExp4;
	}

	public void setNuaExp4(String nuaExp4) {
		this.nuaExp4 = nuaExp4;
	}

	@Column(name = "NUA_EXP3", length = 20)
	public String getNuaExp3() {
		return this.nuaExp3;
	}

	public void setNuaExp3(String nuaExp3) {
		this.nuaExp3 = nuaExp3;
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