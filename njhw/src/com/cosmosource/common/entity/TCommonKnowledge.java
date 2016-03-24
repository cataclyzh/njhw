package com.cosmosource.common.entity;

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
 * TCommonKnowledge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_KNOWLEDGE")
public class TCommonKnowledge implements java.io.Serializable {

	private static final long serialVersionUID = 4560139276658203262L;
	
	// Fields
	private Long knowledgeId;
	private String code;
	private String subject;
	private String content;
	private String question;
	private String answer;
	private Date createTime;
	private String createUser;
	private String vendorNo;
	private String company;
	private String state;
	private String type;
	private String restrictLevel;
	private String displayModel;
	private Date publishTime;
	private String publishUser;

	// Constructors

	/** default constructor */
	public TCommonKnowledge() {
	}

	/** minimal constructor */
	public TCommonKnowledge(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	/** full constructor */
	public TCommonKnowledge(Long knowledgeId, String code, String subject,
			String content, String question, String answer, Date createTime,
			String createUser, String vendorNo, String company, String state,
			String type, String restrictLevel, String displayModel,
			Date publishTime, String publishUser) {
		this.knowledgeId = knowledgeId;
		this.code = code;
		this.subject = subject;
		this.content = content;
		this.question = question;
		this.answer = answer;
		this.createTime = createTime;
		this.createUser = createUser;
		this.vendorNo = vendorNo;
		this.company = company;
		this.state = state;
		this.type = type;
		this.restrictLevel = restrictLevel;
		this.displayModel = displayModel;
		this.publishTime = publishTime;
		this.publishUser = publishUser;
	}

	// Property accessors
	@Id
	@Column(name = "KNOWLEDGE_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_KNOWLEDGE")
	@SequenceGenerator(name="SEQ_COMMON_KNOWLEDGE",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_KNOWLEDGE")
	public Long getKnowledgeId() {
		return this.knowledgeId;
	}

	public void setKnowledgeId(Long knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	@Column(name = "CODE", length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "SUBJECT", length = 100)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "CONTENT", length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "QUESTION", length = 1024)
	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Column(name = "ANSWER", length = 1024)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_USER", length = 50)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "VENDOR_NO", length = 20)
	public String getVendorNo() {
		return this.vendorNo;
	}

	public void setVendorNo(String vendorNo) {
		this.vendorNo = vendorNo;
	}

	@Column(name = "COMPANY", length = 20)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "STATE", length = 10)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "TYPE", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "RESTRICT_LEVEL", length = 10)
	public String getRestrictLevel() {
		return this.restrictLevel;
	}

	public void setRestrictLevel(String restrictLevel) {
		this.restrictLevel = restrictLevel;
	}

	@Column(name = "DISPLAY_MODEL", length = 10)
	public String getDisplayModel() {
		return this.displayModel;
	}

	public void setDisplayModel(String displayModel) {
		this.displayModel = displayModel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PUBLISH_TIME", length = 7)
	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Column(name = "PUBLISH_USER", length = 50)
	public String getPublishUser() {
		return this.publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

}