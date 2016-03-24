package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_AC_CAUSERAPPLY")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcCauserapply implements java.io.Serializable {
	
	private static final long serialVersionUID = 4028381199112270316L;
	private Long causerapplyid;
	private String loginname;
	private String applyno;
	private String canum;
	private String isgenca;

	// Constructors

	/** default constructor */
	public TAcCauserapply() {
	}

	/** minimal constructor */
	public TAcCauserapply(Long causerapplyid, String loginname, String canum) {
		this.causerapplyid = causerapplyid;
		this.loginname = loginname;
		this.canum = canum;
	}

	/** full constructor */
	public TAcCauserapply(Long causerapplyid, String loginname, String applyno,
			String canum, String isgenca) {
		this.causerapplyid = causerapplyid;
		this.loginname = loginname;
		this.applyno = applyno;
		this.canum = canum;
		this.isgenca = isgenca;
	}

	// Property accessors
	@Id
	@Column(name = "CAUSERAPPLYID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getCauserapplyid() {
		return this.causerapplyid;
	}

	public void setCauserapplyid(Long causerapplyid) {
		this.causerapplyid = causerapplyid;
	}

	@Column(name = "LOGINNAME", nullable = false, length = 40)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "APPLYNO", length = 40)
	public String getApplyno() {
		return this.applyno;
	}

	public void setApplyno(String applyno) {
		this.applyno = applyno;
	}

	@Column(name = "CANUM", nullable = false, length = 60)
	public String getCanum() {
		return this.canum;
	}

	public void setCanum(String canum) {
		this.canum = canum;
	}

	@Column(name = "ISGENCA", length = 1)
	public String getIsgenca() {
		return this.isgenca;
	}

	public void setIsgenca(String isgenca) {
		this.isgenca = isgenca;
	}

}