package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cosmosource.base.dao.AuditableEntity;

/**
 * TAcOrg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_ORG")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcOrg extends AuditableEntity implements HibernateTree,java.io.Serializable {

	private static final long serialVersionUID = -3730007599805352992L;
	private Long orgid;
	private String orgcode;
	private String orgname;
	private String taxno;
	private String taxname;
	private Long parentid;
	private String orgtype;
	private String linkman;
	private String phone;
	private String address;
	private String email;
	private String postcode;
	private String bank;
	private String account;
	private String extracttype;
	private String extractsoftzone;
	private String scansoftzone;
	private String isbottom;
	private Long treelevel;
	private String treelayer;
	private String company;
	private String remark;
	private String sortno;
	private String extf0;
	private String extf1;
	private String extf2;
	private String extf3;
	private String extf4;

	// Constructors

	/** default constructor */
	public TAcOrg() {
	}

	/** minimal constructor */
	public TAcOrg(Long orgid, String orgname, Long parentid, String orgtype) {
		this.orgid = orgid;
		this.orgname = orgname;
		this.parentid = parentid;
		this.orgtype = orgtype;
	}

	/** full constructor */
	public TAcOrg(Long orgid, String orgcode, String orgname, String taxno,
			String taxname, Long parentid, String orgtype, String linkman,
			String phone, String address, String email, String postcode,
			String bank, String account, String extracttype,
			String extractsoftzone, 
			String scansoftzone,  String isbottom, Long treelevel,
			String treelayer, String company, String remark,String sortno,
			String extf0,String extf1,String extf2,String extf3,String extf4) {
		this.orgid = orgid;
		this.orgcode = orgcode;
		this.orgname = orgname;
		this.taxno = taxno;
		this.taxname = taxname;
		this.parentid = parentid;
		this.orgtype = orgtype;
		this.linkman = linkman;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.postcode = postcode;
		this.bank = bank;
		this.account = account;
		this.extracttype = extracttype;
		this.extractsoftzone = extractsoftzone;
		this.scansoftzone = scansoftzone;
		this.isbottom = isbottom;
		this.treelevel = treelevel;
		this.treelayer = treelayer;
		this.company = company;
		this.remark = remark;
		this.sortno = sortno;
		this.extf0 = extf0;
		this.extf1 = extf1;
		this.extf2 = extf2;
		this.extf3 = extf3;
		this.extf4 = extf4;
	}

	// Property accessors
	@Id
	@Column(name = "ORGID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_ORG")
	@SequenceGenerator(name="SEQ_AC_ORG",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_ORG")
	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	@Column(name = "ORGCODE", length = 40)
	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	@Column(name = "ORGNAME", nullable = false, length = 150)
	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	@Column(name = "TAXNO", length = 40)
	public String getTaxno() {
		return this.taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}

	@Column(name = "TAXNAME", length = 150)
	public String getTaxname() {
		return this.taxname;
	}

	public void setTaxname(String taxname) {
		this.taxname = taxname;
	}

	@Column(name = "PARENTID", nullable = false, precision = 10, scale = 0)
	public Long getParentid() {
		return this.parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	@Column(name = "ORGTYPE", nullable = false, length = 2)
	public String getOrgtype() {
		return this.orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	@Column(name = "LINKMAN", length = 20)
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "POSTCODE", length = 6)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "BANK", length = 200)
	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "ACCOUNT", length = 40)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "EXTRACTTYPE", length = 40)
	public String getExtracttype() {
		return this.extracttype;
	}

	public void setExtracttype(String extracttype) {
		this.extracttype = extracttype;
	}

	@Column(name = "EXTRACTSOFTZONE", length = 40)
	public String getExtractsoftzone() {
		return this.extractsoftzone;
	}

	public void setExtractsoftzone(String extractsoftzone) {
		this.extractsoftzone = extractsoftzone;
	}


	@Column(name = "SCANSOFTZONE", length = 40)
	public String getScansoftzone() {
		return this.scansoftzone;
	}

	public void setScansoftzone(String scansoftzone) {
		this.scansoftzone = scansoftzone;
	}

	@Column(name = "ISBOTTOM", length = 1)
	public String getIsbottom() {
		return this.isbottom;
	}

	public void setIsbottom(String isbottom) {
		this.isbottom = isbottom;
	}

	@Column(name = "ORGLEVEL", precision = 5, scale = 0)
	public Long getTreelevel() {
		return this.treelevel;
	}

	public void setTreelevel(Long treelevel) {
		this.treelevel = treelevel;
	}

	@Column(name = "ORGLAYER", length = 400)
	public String getTreelayer() {
		return this.treelayer;
	}

	public void setTreelayer(String treelayer) {
		this.treelayer = treelayer;
	}

	@Column(name = "COMPANY", length = 100)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "SORTNO", length = 20)
	public String getSortno() {
		return this.sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno;
	}	
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
}