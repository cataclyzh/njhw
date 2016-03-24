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

import com.cosmosource.base.dao.AuditableEntity;

/**
 * TAcRegistkey entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_REGISTKEY")
public class TAcRegistkey extends AuditableEntity implements java.io.Serializable {


	private static final long serialVersionUID = -6319857596618298293L;
	private Long regid;
	private String taxno;
	private String taxname;
	private String sys;
	private String uptomon;
	private String registkey;
	private String operuser;
	private Date operdate;
	private String startdate;
	private String validflag;

	// Constructors

	/** default constructor */
	public TAcRegistkey() {
	}

	/** minimal constructor */
	public TAcRegistkey(Long regid, String taxname, String validflag) {
		this.regid = regid;
		this.taxname = taxname;
		this.validflag = validflag;
	}

	/** full constructor */
	public TAcRegistkey(Long regid, String taxno, String taxname, String sys,
			String uptomon, String registkey, String operuser, Date operdate,
			String startdate, String validflag) {
		this.regid = regid;
		this.taxno = taxno;
		this.taxname = taxname;
		this.sys = sys;
		this.uptomon = uptomon;
		this.registkey = registkey;
		this.operuser = operuser;
		this.operdate = operdate;
		this.startdate = startdate;
		this.validflag = validflag;
	}

	// Property accessors
	@Id
	@Column(name = "REGID", unique = true, nullable = false, precision = 11, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_REGISTKEY")
	@SequenceGenerator(name="SEQ_AC_REGISTKEY",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_REGISTKEY")
	public Long getRegid() {
		return this.regid;
	}

	public void setRegid(Long regid) {
		this.regid = regid;
	}

	@Column(name = "TAXNO", length = 20)
	public String getTaxno() {
		return this.taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}

	@Column(name = "TAXNAME", nullable = false, length = 150)
	public String getTaxname() {
		return this.taxname;
	}

	public void setTaxname(String taxname) {
		this.taxname = taxname;
	}

	@Column(name = "SYS", length = 2)
	public String getSys() {
		return this.sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	@Column(name = "UPTOMON", length = 10)
	public String getUptomon() {
		return this.uptomon;
	}

	public void setUptomon(String uptomon) {
		this.uptomon = uptomon;
	}

	@Column(name = "REGISTKEY", length = 50)
	public String getRegistkey() {
		return this.registkey;
	}

	public void setRegistkey(String registkey) {
		this.registkey = registkey;
	}

	@Column(name = "OPERUSER", length = 100)
	public String getOperuser() {
		return this.operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERDATE", length = 7)
	public Date getOperdate() {
		return this.operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	@Column(name = "STARTDATE", length = 10)
	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	@Column(name = "VALIDFLAG", nullable = false, length = 1)
	public String getValidflag() {
		return this.validflag;
	}

	public void setValidflag(String validflag) {
		this.validflag = validflag;
	}

}