package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cosmosource.base.dao.AuditableEntity;

/**
 * TAcDicttype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_DICTTYPE",  uniqueConstraints = @UniqueConstraint(columnNames = "DICTTYPECODE"))
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcDicttype extends AuditableEntity implements java.io.Serializable {

	private static final long serialVersionUID = -1081542064686297371L;
	private Long dicttypeid;
	private String dicttypename;
	private String dicttypedesc;
	private String dicttypecode;

	// Constructors

	/** default constructor */
	public TAcDicttype() {
	}

	/** minimal constructor */
	public TAcDicttype(Long dicttypeid, String dicttypename, String dicttypecode) {
		this.dicttypeid = dicttypeid;
		this.dicttypename = dicttypename;
		this.dicttypecode = dicttypecode;
	}

	/** full constructor */
	public TAcDicttype(Long dicttypeid, String dicttypename,
			String dicttypedesc, String dicttypecode) {
		this.dicttypeid = dicttypeid;
		this.dicttypename = dicttypename;
		this.dicttypedesc = dicttypedesc;
		this.dicttypecode = dicttypecode;
	}

	// Property accessors
	@Id
	@Column(name = "DICTTYPEID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_DICTTYPE")
	@SequenceGenerator(name="SEQ_AC_DICTTYPE",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_DICTTYPE")
	public Long getDicttypeid() {
		return this.dicttypeid;
	}

	public void setDicttypeid(Long dicttypeid) {
		this.dicttypeid = dicttypeid;
	}

	@Column(name = "DICTTYPENAME", nullable = false)
	public String getDicttypename() {
		return this.dicttypename;
	}

	public void setDicttypename(String dicttypename) {
		this.dicttypename = dicttypename;
	}

	@Column(name = "DICTTYPEDESC")
	public String getDicttypedesc() {
		return this.dicttypedesc;
	}

	public void setDicttypedesc(String dicttypedesc) {
		this.dicttypedesc = dicttypedesc;
	}

	@Column(name = "DICTTYPECODE", unique = true, nullable = false)
	public String getDicttypecode() {
		return this.dicttypecode;
	}

	public void setDicttypecode(String dicttypecode) {
		this.dicttypecode = dicttypecode;
	}

}