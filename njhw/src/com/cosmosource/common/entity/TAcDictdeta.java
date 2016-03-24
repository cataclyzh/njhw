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
 * TAcDictdeta entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_DICTDETA")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcDictdeta  extends AuditableEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1031630104455647592L;
	private Long dictid;
	private Long dicttype;
	private String dictname;
	private String sortno;
	private Byte status;
	private String dictcode;

	// Constructors

	/** default constructor */
	public TAcDictdeta() {
	}

	/** minimal constructor */
	public TAcDictdeta(Long dictid, Long dicttype, String dictname,
			String dictcode) {
		this.dictid = dictid;
		this.dicttype = dicttype;
		this.dictname = dictname;
		this.dictcode = dictcode;
	}

	/** full constructor */
	public TAcDictdeta(Long dictid, Long dicttype, String dictname,
			String sortno, Byte status, String dictcode) {
		this.dictid = dictid;
		this.dicttype = dicttype;
		this.dictname = dictname;
		this.sortno = sortno;
		this.status = status;
		this.dictcode = dictcode;
	}

	// Property accessors
	@Id
	@Column(name = "DICTID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_DICTDETA")
	@SequenceGenerator(name="SEQ_AC_DICTDETA",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_DICTDETA")
	public Long getDictid() {
		return this.dictid;
	}

	public void setDictid(Long dictid) {
		this.dictid = dictid;
	}

	@Column(name = "DICTTYPE", nullable = false, precision = 10, scale = 0)
	public Long getDicttype() {
		return this.dicttype;
	}

	public void setDicttype(Long dicttype) {
		this.dicttype = dicttype;
	}

	@Column(name = "DICTNAME", nullable = false)
	public String getDictname() {
		return this.dictname;
	}

	public void setDictname(String dictname) {
		this.dictname = dictname;
	}

	@Column(name = "SORTNO", length = 10)
	public String getSortno() {
		return this.sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno;
	}

	@Column(name = "STATUS", precision = 2, scale = 0)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Column(name = "DICTCODE", nullable = false)
	public String getDictcode() {
		return this.dictcode;
	}

	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}

}