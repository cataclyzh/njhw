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
 * TCommonSmsBox entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_SMS_BOX")
public class TCommonSmsBox implements java.io.Serializable {

	// Fields

	private Long smsid;
	private String receiver;
	private Long receiverid;
	private String receivermobile;
	private String sender;
	private Long senderid;
	private String sendermobile;
	private Date smstime;
	private String content;

	// Constructors

	/** default constructor */
	public TCommonSmsBox() {
	}

	/** minimal constructor */
	public TCommonSmsBox(Long smsid, Long receiverid,
			String receivermobile, Long senderid, Date smstime,
			String content) {
		this.smsid = smsid;
		this.receiverid = receiverid;
		this.receivermobile = receivermobile;
		this.senderid = senderid;
		this.smstime = smstime;
		this.content = content;
	}

	/** full constructor */
	public TCommonSmsBox(Long smsid, String receiver, Long receiverid,
			String receivermobile, String sender, Long senderid,
			String sendermobile, Date smstime, String content) {
		this.smsid = smsid;
		this.receiver = receiver;
		this.receiverid = receiverid;
		this.receivermobile = receivermobile;
		this.sender = sender;
		this.senderid = senderid;
		this.sendermobile = sendermobile;
		this.smstime = smstime;
		this.content = content;
	}

	// Property accessors
	@Id
	@Column(name = "SMSID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_SMS_BOX")
	@SequenceGenerator(name="SEQ_COMMON_SMS_BOX",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_SMS_BOX")
	public Long getSmsid() {
		return this.smsid;
	}

	public void setSmsid(Long smsid) {
		this.smsid = smsid;
	}

	@Column(name = "RECEIVER", length = 40)
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "RECEIVERID", precision = 22, scale = 0)
	public Long getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(Long receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name = "RECEIVERMOBILE", nullable = false, length = 20)
	public String getReceivermobile() {
		return this.receivermobile;
	}

	public void setReceivermobile(String receivermobile) {
		this.receivermobile = receivermobile;
	}

	@Column(name = "SENDER", length = 40)
	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Column(name = "SENDERID", nullable = false, precision = 22, scale = 0)
	public Long getSenderid() {
		return this.senderid;
	}

	public void setSenderid(Long senderid) {
		this.senderid = senderid;
	}

	@Column(name = "SENDERMOBILE", length = 20)
	public String getSendermobile() {
		return this.sendermobile;
	}

	public void setSendermobile(String sendermobile) {
		this.sendermobile = sendermobile;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SMSTIME", nullable = false, length = 7)
	public Date getSmstime() {
		return this.smstime;
	}

	public void setSmstime(Date smstime) {
		this.smstime = smstime;
	}

	@Column(name = "CONTENT", length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}