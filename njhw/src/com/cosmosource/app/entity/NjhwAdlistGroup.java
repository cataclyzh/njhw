package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 通讯录联系人分组
 * NjhwAdlistGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NJHW_ADLIST_GROUP")
public class NjhwAdlistGroup implements java.io.Serializable {
	//1 缺省分组 
	public static final String NAG_TYPE_DEFAULT = "1";
	//2 自定义分组
	public static final String NAG_TYPE_CUSTOM = "2";
	
	// Fields
	private Long nagId;
	private String nagName;
	private Long nagUser;
	private String nagType;

	// Constructors

	/** default constructor */
	public NjhwAdlistGroup() {
	}

	/** minimal constructor */
	public NjhwAdlistGroup(Long nagId) {
		this.nagId = nagId;
	}

	/** full constructor */
	public NjhwAdlistGroup(Long nagId, String nagName, Long nagUser,
			String nagType) {
		this.nagId = nagId;
		this.nagName = nagName;
		this.nagUser = nagUser;
		this.nagType = nagType;
	}

	// Property accessors
	@Id
	@Column(name = "NAG_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_ADLIST_GROUP")
	@SequenceGenerator(name="SEQ_NJHW_ADLIST_GROUP",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_ADLIST_GROUP")
	public Long getNagId() {
		return this.nagId;
	}

	public void setNagId(Long nagId) {
		this.nagId = nagId;
	}

	@Column(name = "NAG_NAME", length = 50)
	public String getNagName() {
		return this.nagName;
	}

	public void setNagName(String nagName) {
		this.nagName = nagName;
	}

	@Column(name = "NAG_USER", precision = 12, scale = 0)
	public Long getNagUser() {
		return this.nagUser;
	}

	public void setNagUser(Long nagUser) {
		this.nagUser = nagUser;
	}

	@Column(name = "NAG_TYPE", length = 2)
	public String getNagType() {
		return this.nagType;
	}

	public void setNagType(String nagType) {
		this.nagType = nagType;
	}
}