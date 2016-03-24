package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * TAcUserrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_USERROLE",  uniqueConstraints = @UniqueConstraint(columnNames = {
		"USERID", "ROLEID" }))
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcUserrole implements java.io.Serializable {

	private static final long serialVersionUID = -2684261161249764314L;
	private Long userroleid;
	private Long userid;
	private Long roleid;

	// Constructors

	/** default constructor */
	public TAcUserrole() {
	}

	/** full constructor */
	public TAcUserrole(Long userroleid, Long userid, Long roleid) {
		this.userroleid = userroleid;
		this.userid = userid;
		this.roleid = roleid;
	}

	// Property accessors
	@Id
	@Column(name = "USERROLEID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_USERROLE")
	@SequenceGenerator(name="SEQ_AC_USERROLE",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_USERROLE")
	public Long getUserroleid() {
		return this.userroleid;
	}

	public void setUserroleid(Long userroleid) {
		this.userroleid = userroleid;
	}

	@Column(name = "USERID", nullable = false, precision = 10, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "ROLEID", nullable = false, precision = 10, scale = 0)
	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

}