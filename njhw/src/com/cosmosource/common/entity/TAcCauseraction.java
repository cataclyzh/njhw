package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_AC_CAUSERACTION")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcCauseraction implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5121417587032390134L;
	private Long causeractionid;
	private String actioncode;
	private String loginname;
	private String isuseca;

	public TAcCauseraction() {
	}

	/** minimal constructor */
	public TAcCauseraction(Long causeractionid, String actioncode, String loginname) {
		this.causeractionid = causeractionid;
		this.actioncode = actioncode;
		this.loginname = loginname;
	}

	/** full constructor */
	public TAcCauseraction(Long causeractionid, String actioncode, String loginname,
			String actiondesc, String isuseca) {
		this.causeractionid = causeractionid;
		this.actioncode = actioncode;
		this.loginname = loginname;
		this.isuseca = isuseca;
	}

	// Property accessors
	@Id
	@Column(name = "CAUSERACTIONID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getCauseractionid() {
		return this.causeractionid;
	}

	public void setCauseractionid(Long causeractionid) {
		this.causeractionid = causeractionid;
	}

	@Column(name = "ACTIONCODE", nullable = false, length = 40)
	public String getActioncode() {
		return this.actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}

	@Column(name = "LOGINNAME", nullable = false, length = 40)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "ISUSECA", length = 1)
	public String getIsuseca() {
		return this.isuseca;
	}

	public void setIsuseca(String isuseca) {
		this.isuseca = isuseca;
	}

}