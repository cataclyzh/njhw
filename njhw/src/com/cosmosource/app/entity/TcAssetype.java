package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 物业管理    资产类别
 * TcAssetype entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TC_ASSETYPE")
public class TcAssetype implements java.io.Serializable {

	// Fields

	private Long aetId;
	private String aetName;
	private Long aetParent;
	private String aetSerices;
	private String aetFlag;

	// Constructors

	/** default constructor */
	public TcAssetype() {
	}

	/** minimal constructor */
	public TcAssetype(Long aetId) {
		this.aetId = aetId;
	}

	/** full constructor */
	public TcAssetype(Long aetId, String aetName, Long aetParent,
			String aetSerices, String aetFlag) {
		this.aetId = aetId;
		this.aetName = aetName;
		this.aetParent = aetParent;
		this.aetSerices = aetSerices;
		this.aetFlag = aetFlag;
	}

	// Property accessors
	@Id
	@Column(name = "AET_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TC_ASSETYPE")
	@SequenceGenerator(name="SEQ_TC_ASSETYPE",allocationSize=1,initialValue=1, sequenceName="SEQ_TC_ASSETYPE")	
	public Long getAetId() {
		return this.aetId;
	}

	public void setAetId(Long aetId) {
		this.aetId = aetId;
	}

	@Column(name = "AET_NAME", length = 20)
	public String getAetName() {
		return this.aetName;
	}

	public void setAetName(String aetName) {
		this.aetName = aetName;
	}

	@Column(name = "AET_PARENT", precision = 12, scale = 0)
	public Long getAetParent() {
		return this.aetParent;
	}

	public void setAetParent(Long aetParent) {
		this.aetParent = aetParent;
	}

	@Column(name = "AET_SERICES", length = 2)
	public String getAetSerices() {
		return this.aetSerices;
	}

	public void setAetSerices(String aetSerices) {
		this.aetSerices = aetSerices;
	}

	@Column(name = "AET_FLAG", length = 1)
	public String getAetFlag() {
		return this.aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

}