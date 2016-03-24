package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_AC_CAUSER")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcCauser implements java.io.Serializable {
	
	private static final long serialVersionUID = 2592173834722288019L;
	private Long causerid;
	private String loginname;
	private String cano;
	private String isvalidca;
	private String applyno;


	public TAcCauser() {
	}

	public TAcCauser(Long causerid, String loginname, String cano) {
		this.causerid = causerid;
		this.loginname = loginname;
		this.cano = cano;
	}

	/** full constructor */
	public TAcCauser(Long causerid, String loginname, String cano,
			String isvalidca, String applyno) {
		this.causerid = causerid;
		this.loginname = loginname;
		this.cano = cano;
		this.isvalidca = isvalidca;
		this.applyno = applyno;
	}

	// Property accessors
	@Id
	@Column(name = "CAUSERID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getCauserid() {
		return this.causerid;
	}

	public void setCauserid(Long causerid) {
		this.causerid = causerid;
	}

	@Column(name = "LOGINNAME", nullable = false, length = 40)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "CANO", nullable = false, length = 60)
	public String getCano() {
		return this.cano;
	}

	public void setCano(String cano) {
		this.cano = cano;
	}

	@Column(name = "ISVALIDCA", length = 1)
	public String getIsvalidca() {
		return this.isvalidca;
	}

	public void setIsvalidca(String isvalidca) {
		this.isvalidca = isvalidca;
	}

	@Column(name = "APPLYNO", length = 40)
	public String getApplyno() {
		return this.applyno;
	}

	public void setApplyno(String applyno) {
		this.applyno = applyno;
	}

}