package com.cosmosource.common.entity;

//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

//import com.google.common.collect.Lists;
import com.cosmosource.base.dao.AuditableEntity;

/**
 * TAcRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_ROLE")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcRole extends AuditableEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3785984505267492108L;
	private Long roleid;
	private String rolename;
	private String rolecode;
	private Long orgid;
	private String roledesc;
	private String roletype;
	
//	private List<TAcUser> userList = Lists.newArrayList(); //有序的关联对象集合

	// Constructors

	/** default constructor */
	public TAcRole() {
	}

	/** minimal constructor */
	public TAcRole(Long roleid, String rolename, Long orgid) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.orgid = orgid;
	}

	/** full constructor */
	public TAcRole(Long roleid, String rolename, String rolecode, Long orgid,
			String roledesc, String roletype) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.rolecode = rolecode;
		this.orgid = orgid;
		this.roledesc = roledesc;
		this.roletype = roletype;
	}

	// Property accessors
	@Id
	@Column(name = "ROLEID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_ROLE")
	@SequenceGenerator(name="SEQ_AC_ROLE",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_ROLE")
	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	@Column(name = "ROLENAME", nullable = false, length = 40)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "ROLECODE", length = 40)
	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	@Column(name = "ORGID", nullable = false, precision = 10, scale = 0)
	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	@Column(name = "ROLEDESC", length = 300)
	public String getRoledesc() {
		return this.roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	@Column(name = "ROLETYPE", length = 1)
	public String getRoletype() {
		return this.roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
//	//多对多定义
//	@ManyToMany
//	//中间表定义,表名采用默认命名规则
//	@JoinTable(name = "T_AC_USERROLE", joinColumns = { @JoinColumn(name = "ROLEID") }, inverseJoinColumns = { @JoinColumn(name = "USERID") })
//	//Fecth策略定义
//	@Fetch(FetchMode.SUBSELECT)
//	//集合按id排序
//	@OrderBy("userid ASC")
//	public List<TAcUser> getUserList() {
//		return userList;
//	}
//
//	public void setUserList(List<TAcUser> userList) {
//		this.userList = userList;
//	}
//	@Transient
//	@JsonIgnore
//	public String getUserNames() {
//		return ConvertUtils.convertElementPropertyToString(userList, "username", ", ");
//	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}