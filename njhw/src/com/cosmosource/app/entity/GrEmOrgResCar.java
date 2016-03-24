package com.cosmosource.app.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 组织结构表
 * OrgId entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="ORG")
public class GrEmOrgResCar implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final Long serialVersionUID = -5527358045121319071L;
	
	public static final String LEVELNUM_2 = "2";  //  代表委办局
	
	private Long orgId;
	private String name;
	private String shortName;
	private String levelNum;
	private Long PId;
	private Long orderNum;
	private String memo;
	private String type; 
	private String orgCode;
	private String ext1;
	private String ext2;

	// Constructors

	/** default constructor */
	public GrEmOrgResCar() {
	}

	/** minimal constructor */
	public GrEmOrgResCar(Long orgId) {
		this.orgId = orgId;
	}

	/** full constructor */
	public GrEmOrgResCar(Long orgId, String name, String shortName,
			String levelNum, Long PId, Long orderNum, String memo,
			String type, String orgCode, String ext1, String ext2) {
		this.orgId = orgId;
		this.name = name;
		this.shortName = shortName;
		this.levelNum = levelNum;
		this.PId = PId;
		this.orderNum = orderNum;
		this.memo = memo;
		this.type = type;
		this.orgCode = orgCode;
		this.ext1 = ext1;
		this.ext2 = ext2;
	}

	// Property accessors

	@Id
	@Column(name = "ORG_ID", nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ORG")
	@SequenceGenerator(name="SEQ_ORG",allocationSize=1,initialValue=1, sequenceName="SEQ_ORG")
	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Column(name = "NAME", length = 90)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SHORT_NAME", length = 90)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "LEVEL_NUM", length = 10)
	public String getLevelNum() {
		return this.levelNum;
	}

	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}

	@Column(name = "P_ID", precision = 22, scale = 0)
	public Long getPId() {
		return this.PId;
	}

	public void setPId(Long PId) {
		this.PId = PId;
	}

	@Column(name = "ORDER_NUM", precision = 22, scale = 0)
	public Long getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "MEMO", length = 90)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "TYPE", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "ORG_CODE", length = 10)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "EXT1", length = 10)
	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	@Column(name = "EXT2", length = 10)
	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}


}