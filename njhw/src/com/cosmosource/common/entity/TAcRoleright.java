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
 * TAcRoleright entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_ROLERIGHT", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ROLEID", "MENUID" }))
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcRoleright implements java.io.Serializable {

	private static final long serialVersionUID = 4899984647481203409L;
	private Long rolerightid;
	private Long roleid;
	private Long menuid;

	// Constructors

	/** default constructor */
	public TAcRoleright() {
	}

	/** full constructor */
	public TAcRoleright(Long rolerightid, Long roleid, Long menuid) {
		this.rolerightid = rolerightid;
		this.roleid = roleid;
		this.menuid = menuid;
	}

	// Property accessors
	@Id
	@Column(name = "ROLERIGHTID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_ROLERIGHT")
	@SequenceGenerator(name="SEQ_AC_ROLERIGHT",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_ROLERIGHT")
	public Long getRolerightid() {
		return this.rolerightid;
	}

	public void setRolerightid(Long rolerightid) {
		this.rolerightid = rolerightid;
	}

	@Column(name = "ROLEID", nullable = false, precision = 10, scale = 0)
	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	@Column(name = "MENUID", nullable = false, precision = 10, scale = 0)
	public Long getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

}