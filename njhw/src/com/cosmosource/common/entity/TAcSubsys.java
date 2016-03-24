package com.cosmosource.common.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TAcSubsys entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_SUBSYS")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcSubsys implements java.io.Serializable {

	private static final long serialVersionUID = 4511433716585799073L;
	private Long subsysid;
	private String subsysname;
	private String describe;
	private Date settime;
	private String setuser;

	// Constructors

	/** default constructor */
	public TAcSubsys() {
	}

	/** minimal constructor */
	public TAcSubsys(Long subsysid) {
		this.subsysid = subsysid;
	}

	/** full constructor */
	public TAcSubsys(Long subsysid, String subsysname, String describe,
			Date settime, String setuser) {
		this.subsysid = subsysid;
		this.subsysname = subsysname;
		this.describe = describe;
		this.settime = settime;
		this.setuser = setuser;
	}

	// Property accessors
	@Id
	@Column(name = "SUBSYSID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getSubsysid() {
		return this.subsysid;
	}

	public void setSubsysid(Long subsysid) {
		this.subsysid = subsysid;
	}

	@Column(name = "SUBSYSNAME", length = 40)
	public String getSubsysname() {
		return this.subsysname;
	}

	public void setSubsysname(String subsysname) {
		this.subsysname = subsysname;
	}

	@Column(name = "DESCRIBE", length = 100)
	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SETTIME", length = 7)
	public Date getSettime() {
		return this.settime;
	}

	public void setSettime(Date settime) {
		this.settime = settime;
	}

	@Column(name = "SETUSER", length = 10)
	public String getSetuser() {
		return this.setuser;
	}

	public void setSetuser(String setuser) {
		this.setuser = setuser;
	}

}