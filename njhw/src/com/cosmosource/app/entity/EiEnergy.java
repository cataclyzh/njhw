package com.cosmosource.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 能源表
 * EiEnergy entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "unused", "serial" })
@Entity
@Table(name = "EI_ENERGY")
public class EiEnergy implements java.io.Serializable {

    public static final String EETYPE_W="1";//水
    public static final String EETYPE_D="2";//电
    public static final String EETYPE_Q="3";//气
	
	// Fields

	private Long eeId;
	private Long eeStaid;
	private String eeType;
	private String eeGroup;
	private String eeUnit;
	private Double eeLnum;
	private Double eeTnum;
	private Double eeNum;
	private String eeExp1;
	private String eeExp2;
	private String eeExp3;
	private String eeExp4;
	private String eeExp5;
	private Long insertId;
	private Date insertDate;

	// Constructors

	/** default constructor */
	public EiEnergy() {
	}

	/** minimal constructor */
	public EiEnergy(Long eeId) {
		this.eeId = eeId;
	}

	/** full constructor */
	public EiEnergy(Long eeId, Long eeStaid, String eeType, String eeGroup,
			String eeUnit, Double eeLnum, Double eeTnum, Double eeNum,
			String eeExp1, String eeExp2, String eeExp3, String eeExp4,
			String eeExp5, Long insertId, Date insertDate) {
		this.eeId = eeId;
		this.eeStaid = eeStaid;
		this.eeType = eeType;
		this.eeGroup = eeGroup;
		this.eeUnit = eeUnit;
		this.eeLnum = eeLnum;
		this.eeTnum = eeTnum;
		this.eeNum = eeNum;
		this.eeExp1 = eeExp1;
		this.eeExp2 = eeExp2;
		this.eeExp3 = eeExp3;
		this.eeExp4 = eeExp4;
		this.eeExp5 = eeExp5;
		this.insertId = insertId;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@Column(name = "EE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EI_ENERGY")
	@SequenceGenerator(name="SEQ_EI_ENERGY",allocationSize=1,initialValue=1, sequenceName="SEQ_EI_ENERGY")	
	public Long getEeId() {
		return this.eeId;
	}

	public void setEeId(Long eeId) {
		this.eeId = eeId;
	}

	@Column(name = "EE_STAID", precision = 12, scale = 0)
	public Long getEeStaid() {
		return this.eeStaid;
	}

	public void setEeStaid(Long eeStaid) {
		this.eeStaid = eeStaid;
	}

	@Column(name = "EE_TYPE", length = 1)
	public String getEeType() {
		return this.eeType;
	}

	public void setEeType(String eeType) {
		this.eeType = eeType;
	}

	@Column(name = "EE_GROUP", length = 2)
	public String getEeGroup() {
		return this.eeGroup;
	}

	public void setEeGroup(String eeGroup) {
		this.eeGroup = eeGroup;
	}

	@Column(name = "EE_UNIT", length = 10)
	public String getEeUnit() {
		return this.eeUnit;
	}

	public void setEeUnit(String eeUnit) {
		this.eeUnit = eeUnit;
	}

	@Column(name = "EE_LNUM", precision = 6)
	public Double getEeLnum() {
		return this.eeLnum;
	}

	public void setEeLnum(Double eeLnum) {
		this.eeLnum = eeLnum;
	}

	@Column(name = "EE_TNUM", precision = 6)
	public Double getEeTnum() {
		return this.eeTnum;
	}

	public void setEeTnum(Double eeTnum) {
		this.eeTnum = eeTnum;
	}

	@Column(name = "EE_NUM", precision = 6)
	public Double getEeNum() {
		return this.eeNum;
	}

	public void setEeNum(Double eeNum) {
		this.eeNum = eeNum;
	}

	@Column(name = "EE_EXP1", length = 50)
	public String getEeExp1() {
		return this.eeExp1;
	}

	public void setEeExp1(String eeExp1) {
		this.eeExp1 = eeExp1;
	}

	@Column(name = "EE_EXP2", length = 50)
	public String getEeExp2() {
		return this.eeExp2;
	}

	public void setEeExp2(String eeExp2) {
		this.eeExp2 = eeExp2;
	}

	@Column(name = "EE_EXP3", length = 50)
	public String getEeExp3() {
		return this.eeExp3;
	}

	public void setEeExp3(String eeExp3) {
		this.eeExp3 = eeExp3;
	}

	@Column(name = "EE_EXP4", length = 50)
	public String getEeExp4() {
		return this.eeExp4;
	}

	public void setEeExp4(String eeExp4) {
		this.eeExp4 = eeExp4;
	}

	@Column(name = "EE_EXP5", length = 50)
	public String getEeExp5() {
		return this.eeExp5;
	}

	public void setEeExp5(String eeExp5) {
		this.eeExp5 = eeExp5;
	}

	@Column(name = "INSERT_ID", precision = 12, scale = 0)
	public Long getInsertId() {
		return this.insertId;
	}

	public void setInsertId(Long insertId) {
		this.insertId = insertId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INSERT_DATE", length = 7)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

}