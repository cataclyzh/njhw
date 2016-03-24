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
 * 菜单评价表
 * FsFeedback entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FS_FEEDBACK")
public class FsFeedback implements java.io.Serializable {

	// Fields

	private Long fsfId;
	private String fsfType;
	private String fsfTitle;
	private String fsfContent;
	private Long fdId;
	private String fdName;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;
	private String fsfRcontent;

	// Constructors

	/** default constructor */
	public FsFeedback() {
	}

	/** minimal constructor */
	public FsFeedback(Long fsfId) {
		this.fsfId = fsfId;
	}

	/** full constructor */
	public FsFeedback(Long fsfId, String fsfType, String fsfTitle,
			String fsfContent, Long fdId, String fdName, Long insertId,
			Date insertDate, Long modifyId, Date modifyDate, String fsfRcontent) {
		this.fsfId = fsfId;
		this.fsfType = fsfType;
		this.fsfTitle = fsfTitle;
		this.fsfContent = fsfContent;
		this.fdId = fdId;
		this.fdName = fdName;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
		this.fsfRcontent = fsfRcontent;
	}

	// Property accessors
	@Id
	@Column(name = "FSF_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FS_FEEDBACK")
	@SequenceGenerator(name="SEQ_FS_FEEDBACK",allocationSize=1,initialValue=1, sequenceName="SEQ_FS_FEEDBACK")
	public Long getFsfId() {
		return this.fsfId;
	}

	public void setFsfId(Long fsfId) {
		this.fsfId = fsfId;
	}

	@Column(name = "FSF_TYPE", length = 10)
	public String getFsfType() {
		return this.fsfType;
	}

	public void setFsfType(String fsfType) {
		this.fsfType = fsfType;
	}

	@Column(name = "FSF_TITLE", length = 10)
	public String getFsfTitle() {
		return this.fsfTitle;
	}

	public void setFsfTitle(String fsfTitle) {
		this.fsfTitle = fsfTitle;
	}

	@Column(name = "FSF_CONTENT", length = 1000)
	public String getFsfContent() {
		return this.fsfContent;
	}

	public void setFsfContent(String fsfContent) {
		this.fsfContent = fsfContent;
	}

	@Column(name = "FD_ID", precision = 12, scale = 0)
	public Long getFdId() {
		return this.fdId;
	}

	public void setFdId(Long fdId) {
		this.fdId = fdId;
	}

	@Column(name = "FD_NAME", length = 30)
	public String getFdName() {
		return this.fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
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

	@Column(name = "FSF_RCONTENT", length = 1000)
	public String getFsfRcontent() {
		return this.fsfRcontent;
	}

	public void setFsfRcontent(String fsfRcontent) {
		this.fsfRcontent = fsfRcontent;
	}

}