package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_AC_CAACTION")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcCaaction implements java.io.Serializable {
	private static final long serialVersionUID = -5709673863855395941L;
	private Long actionid;
	private String actioncode;
	private String actionname;
	private String actiondesc;

	public TAcCaaction() {
	}

	/** minimal constructor */
	public TAcCaaction(Long actionid, String actionname) {
		this.actionid = actionid;
		this.actionname = actionname;
	}

	/** full constructor */
	public TAcCaaction(Long actionid, String actioncode, String actionname, String actiondesc) {
		this.actionid = actionid;
		this.actioncode = actioncode;
		this.actionname = actionname;
		this.actiondesc = actiondesc;
	}

	// Property accessors
	@Id
	@Column(name = "ACTIONID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_CAACTION")
	@SequenceGenerator(name="SEQ_AC_CAACTION",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_CAACTION")
	public Long getActionid() {
		return this.actionid;
	}

	public void setActionid(Long actionid) {
		this.actionid = actionid;
	}

	@Column(name = "ACTIONCODE", nullable = false, length = 40)
	public String getActioncode() {
		return actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}

	@Column(name = "ACTIONNAME", nullable = false, length = 100)
	public String getActionname() {
		return this.actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	@Column(name = "ACTIONDESC", length = 500)
	public String getActiondesc() {
		return this.actiondesc;
	}

	public void setActiondesc(String actiondesc) {
		this.actiondesc = actiondesc;
	}

}