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
 * 菜单发布表
 * FsDishesIssue entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FS_DISHES_ISSUE")
public class FsDishesIssue implements java.io.Serializable {

	public static final String FSD_FLAG_AM = "1"; 
	public static final String FSD_FLAG_NOON = "2"; 
	public static final String FSD_FLAG_PM = "3";  
	// Fields

	private Long fdiId;
	private String fdiType;
	private Long fdId;
	private String fdiFlag;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;
	private Long orderNum;

    @Column(name = "ORDER_NUM", length = 20)
	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	/** default constructor */
	public FsDishesIssue() {
	}

	/** minimal constructor */
	public FsDishesIssue(Long fdiId) {
		this.fdiId = fdiId;
	}

	

	// Property accessors
	@Id
	@Column(name = "FDI_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FS_DISHES_ISSUE")
	@SequenceGenerator(name="SEQ_FS_DISHES_ISSUE",allocationSize=1,initialValue=1, sequenceName="SEQ_FS_DISHES_ISSUE")
	public Long getFdiId() {
		return this.fdiId;
	}

	public void setFdiId(Long fdiId) {
		this.fdiId = fdiId;
	}

	

	@Column(name = "FDI_TYPE", length = 1)
	public String getFdiType() {
		return this.fdiType;
	}

	public void setFdiType(String fdiType) {
		this.fdiType = fdiType;
	}

	@Column(name = "FDI_FLAG", length = 1)
	public String getFdiFlag() {
		return this.fdiFlag;
	}

	public void setFdiFlag(String fdiFlag) {
		this.fdiFlag = fdiFlag;
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

	@Column(name = "MODIFY_ID", precision = 12, scale = 0)
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFY_DATE", length = 7)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Column(name = "FD_ID", length = 20)
	public Long getFdId() {
		return fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

}