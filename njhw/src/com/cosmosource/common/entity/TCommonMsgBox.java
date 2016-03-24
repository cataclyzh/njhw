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
 * TCommonMsgBox entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMON_MSG_BOX")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class TCommonMsgBox implements java.io.Serializable {

	private static final long serialVersionUID = -5980753566580706875L;

	public static final String MSG_TYPE_SYS = "0"; // 系统消息
	public static final String MSG_TYPE_VISIT = "1"; // 访客消息
	public static final String MSG_TYPE_BIRTHDAY = "2"; // 生日消息
	public static final String MSG_TYPE_HEALTH = "3"; // 健康消息
	public static final String MSG_TYPE_PRINT = "4"; // 文印消息
	public static final String MSG_TYPE_CAR = "5"; // 洗车消息

	// Fields
	private Long msgid;
	private String receiver;
	private String sender;
	private Date msgtime;
	private String title;
	private String content;
	private String status;
	private Long receiverid;
	private String msgtype;
	private Long senderId;
	private String busnId;

	private Long notificationId;

	/**
	 * @return the notificationId
	 */

	@Column(name = "NOTIFICATION_ID")
	public Long getNotificationId() {
		return notificationId;
	}

	/**
	 * @param notificationId
	 *            the notificationId to set
	 */
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	@Column(name = "SENDERID")
	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	@Column(name = "BUSN_ID")
	public String getBusnId() {
		return busnId;
	}

	public void setBusnId(String busnId) {
		this.busnId = busnId;
	}

	/** default constructor */
	public TCommonMsgBox() {

	}

	/** full constructor */

	public TCommonMsgBox(Long msgid, String receiver, String sender,
			Date msgtime, String title, String content, String status,
			Long receiverid, String msgtype,Long senderId) {
		this.msgid = msgid;
		this.receiver = receiver;
		this.sender = sender;
		this.msgtime = msgtime;
		this.title = title;
		this.content = content;
		this.status = status;
		this.receiverid = receiverid;
		this.msgtype = msgtype;
		this.senderId = senderId;
	}

	// Property accessors
	@Id
	@Column(name = "MSGID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMMON_MSG_BOX")
	@SequenceGenerator(name = "SEQ_COMMON_MSG_BOX", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_COMMON_MSG_BOX")
	public Long getMsgid() {
		return this.msgid;
	}

	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}

	@Column(name = "RECEIVER", nullable = false, length = 40)
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "SENDER", nullable = false, length = 40)
	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MSGTIME", nullable = true, length = 7)
	public Date getMsgtime() {
		return this.msgtime;
	}

	public void setMsgtime(Date msgtime) {
		this.msgtime = msgtime;
	}

	@Column(name = "TITLE", nullable = false, length = 150)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", nullable = false, length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "RECEIVERID", nullable = true)
	public Long getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(Long receiverid) {
		this.receiverid = receiverid;
	}

	@Column(name = "MSGTYPE", nullable = true, length = 2)
	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}