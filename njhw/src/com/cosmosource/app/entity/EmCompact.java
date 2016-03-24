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
 * 物业管理  资产合同
 * EmCompact entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EM_COMPACT")
public class EmCompact implements java.io.Serializable {

	// Fields

	private Long ecpId;
	private String ecpName;
	private Date ecpCd;
	private Date ecpDue;
	private String ecpDesc;

	// Constructors

	/** default constructor */
	public EmCompact() {
	}

	/** minimal constructor */
	public EmCompact(Long ecpId) {
		this.ecpId = ecpId;
	}

	/** full constructor */
	public EmCompact(Long ecpId, String ecpName, Date ecpCd, Date ecpDue,
			String ecpDesc) {
		this.ecpId = ecpId;
		this.ecpName = ecpName;
		this.ecpCd = ecpCd;
		this.ecpDue = ecpDue;
		this.ecpDesc = ecpDesc;
	}

	// Property accessors
	@Id 
	@Column(name = "ECP_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EM_COMPACT")
	@SequenceGenerator(name="SEQ_EM_COMPACT",allocationSize=1,initialValue=1, sequenceName="SEQ_EM_COMPACT")	
	public Long getEcpId() {
		return this.ecpId;
	}

	public void setEcpId(Long ecpId) {
		this.ecpId = ecpId;
	}

	@Column(name = "ECP_NAME", length = 100)
	public String getEcpName() {
		return this.ecpName;
	}

	public void setEcpName(String ecpName) {
		this.ecpName = ecpName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ECP_CD", length = 7)
	public Date getEcpCd() {
		return this.ecpCd;
	}

	public void setEcpCd(Date ecpCd) {
		this.ecpCd = ecpCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ECP_DUE", length = 7)
	public Date getEcpDue() {
		return this.ecpDue;
	}

	public void setEcpDue(Date ecpDue) {
		this.ecpDue = ecpDue;
	}

	@Column(name = "ECP_DESC", length = 200)
	public String getEcpDesc() {
		return this.ecpDesc;
	}

	public void setEcpDesc(String ecpDesc) {
		this.ecpDesc = ecpDesc;
	}

}