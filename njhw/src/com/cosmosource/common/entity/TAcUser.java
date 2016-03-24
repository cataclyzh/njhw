package com.cosmosource.common.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
//import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

//import com.google.common.collect.Lists;
import com.cosmosource.base.dao.AuditableEntity;


@Entity
@Table(name = "T_AC_USER", uniqueConstraints = @UniqueConstraint(columnNames = "LOGINNAME"))
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcUser extends AuditableEntity implements java.io.Serializable {
	private static final long serialVersionUID = 3943803077747565669L;
	private Long userid;
	private String username;
	private String usercode;
	private String password;
	private String loginname;
	private String sex;
	private Date birthday;
	private String email;
	private Long orgid;
	private String phone;
	private String cellphone;
	private String address;
	private String usertype;
	private String plainpassword;
	private String status;
	private String bankName;
	private String bankAccount;
	private String extf0;
	private String extf1;
	private String extf2;
	private String extf3;
	private String extf4;
	private Date pwdModifyTime;
	private Long pwdWrongCount;
	private Date lockTime;
//	private List<TAcRole> roleList = Lists.newArrayList(); //有序的关联对象集合

	// Constructors

	/** default constructor */
	public TAcUser() {
	}

	/** minimal constructor */
	public TAcUser(Long userid, String username, String loginname, Long orgid,String usertype) {
		this.userid = userid;
		this.username = username;
		this.loginname = loginname;
		this.orgid = orgid;
		this.usertype = usertype;
	}

	/** full constructor */
	public TAcUser(Long userid, String username, String usercode,
			String password, String loginname, String sex, Date birthday,
			String email, Long orgid, String phone, String cellphone,
			String address,String usertype,String plainpassword,String status
			,String bankName,String bankAccount
			,String extf0,String extf1,String extf2,String extf3,String extf4) {
		this.userid = userid;
		this.username = username;
		this.usercode = usercode;
		this.password = password;
		this.loginname = loginname;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
		this.orgid = orgid;
		this.phone = phone;
		this.cellphone = cellphone;
		this.address = address;
		this.usertype = usertype;
		this.plainpassword = plainpassword;
		this.status = status;
		this.bankName = bankName;
		this.bankAccount = bankAccount;
		this.extf0 = extf0;
		this.extf1 = extf1;
		this.extf2 = extf2;
		this.extf3 = extf3;
		this.extf4 = extf4;
	}

	// Property accessors
	@Id
	@Column(name = "USERID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_USER")
	@SequenceGenerator(name="SEQ_AC_USER",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_USER")
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "USERNAME", nullable = false, length = 150)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "USERCODE", length = 40)
	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	@Column(name = "PASSWORD", length = 40)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "LOGINNAME", unique = true, nullable = false, length = 40)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "SEX", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ORGID", nullable = false, precision = 10, scale = 0)
	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "CELLPHONE", length = 20)
	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "USERTYPE", length = 1)
	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	@Column(name = "PLAINPASSWORD", length = 40)
	public String getPlainpassword() {
		return this.plainpassword;
	}

	public void setPlainpassword(String plainpassword) {
		this.plainpassword = plainpassword;
	}
	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Column(name = "BANK_NAME", length = 200)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Column(name = "BANK_ACCOUNT", length = 100)
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	
//	//多对多定义
//	@ManyToMany
//	//中间表定义,表名采用默认命名规则
//	@JoinTable(name = "T_AC_USERROLE", joinColumns = { @JoinColumn(name = "USERID") }, inverseJoinColumns = { @JoinColumn(name = "ROLEID") })
//	//Fecth策略定义
//	@Fetch(FetchMode.SUBSELECT)
//	//集合按id排序
//	@OrderBy("roleid ASC")
//	public List<TAcRole> getRoleList() {
//		return roleList;
//	}
//
//	public void setRoleList(List<TAcRole> roleList) {
//		this.roleList = roleList;
//	}
//	@Transient
//	@JsonIgnore
//	public String getRoleNames() {
//		return ConvertUtils.convertElementPropertyToString(roleList, "rolename", ", ");
//	}

	
	
	@Column(name = "EXTF0",length = 100)
	public String getExtf0() {
		return this.extf0;
	}

	public void setExtf0(String extf0) {
		this.extf0 = extf0;
	}
	@Column(name = "EXTF1",length = 100)
	public String getExtf1() {
		return this.extf1;
	}

	public void setExtf1(String extf1) {
		this.extf1 = extf1;
	}
	@Column(name = "EXTF2",length = 100)
	public String getExtf2() {
		return this.extf2;
	}

	public void setExtf2(String extf2) {
		this.extf2 = extf2;
	}
	@Column(name = "EXTF3",length = 100)
	public String getExtf3() {
		return this.extf3;
	}

	public void setExtf3(String extf3) {
		this.extf3 = extf3;
	}
	@Column(name = "EXTF4",length = 100)
	public String getExtf4() {
		return this.extf4;
	}

	public void setExtf4(String extf4) {
		this.extf4 = extf4;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PWD_MODIFY_TIME", length = 7)
	public Date getPwdModifyTime() {
		return pwdModifyTime;
	}

	public void setPwdModifyTime(Date pwdModifyTime) {
		this.pwdModifyTime = pwdModifyTime;
	}

	@Column(name = "PWD_WRONG_COUNT", precision = 10, scale = 0)
	public Long getPwdWrongCount() {
		return pwdWrongCount;
	}

	public void setPwdWrongCount(Long pwdWrongCount) {
		this.pwdWrongCount = pwdWrongCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCK_TIME", length = 7)
	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}