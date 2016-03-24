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
 * TCommonMsgBoard entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_MSG_BOARD")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TCommonMsgBoard implements java.io.Serializable {

	private static final long serialVersionUID = -1475435223244556619L;
	
	// Fields
	private Long msgid;
	private String author;
	private String title;
	private String content;
	private Date msgtime;

	// Constructors

	/** default constructor */
	public TCommonMsgBoard() {
	}

	/** full constructor */
	public TCommonMsgBoard(Long msgid, String author, String title, String content,
			Date msgtime) {
		this.msgid = msgid;
		this.author = author;
		this.title = title;
		this.content = content;
		this.msgtime = msgtime;
	}

	// Property accessors
	@Id
	@Column(name = "MSGID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_MSG_BOARD")
	@SequenceGenerator(name="SEQ_COMMON_MSG_BOARD",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_MSG_BOARD")

	public Long getMsgid() {
		return this.msgid;
	}

	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}

	@Column(name = "AUTHOR", nullable = false, length = 40)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "TITLE", nullable = false, length = 150)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", nullable = false, length = 4000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MSGTIME", nullable = true, length = 7)
	public Date getMsgtime() {
		return this.msgtime;
	}

	public void setMsgtime(Date msgtime) {
		this.msgtime = msgtime;
	}

}