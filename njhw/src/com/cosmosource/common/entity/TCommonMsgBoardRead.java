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
 * 物业已读记录表
 * TCommonMsgBoardRead entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_COMMON_MSG_BOARD_READ")
public class TCommonMsgBoardRead implements java.io.Serializable {

	// Fields

	private Long rmsgid;
	private Long msgid;
	private Long userid;
	private Date rdate;

	// Constructors

	/** default constructor */
	public TCommonMsgBoardRead() {
	}

	/** minimal constructor */
	public TCommonMsgBoardRead(Long rmsgid) {
		this.rmsgid = rmsgid;
	}

	public TCommonMsgBoardRead(Long rmsgid, Long msgid, Long userid, Date rdate) {
		this.rmsgid = rmsgid;
		this.msgid = msgid;
		this.userid = userid;
		this.rdate = rdate;
	}

	// Property accessors
	@Id
	@Column(name = "RMSGID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_T_COMMON_MSG_BOARD_READ")
	@SequenceGenerator(name="SEQ_T_COMMON_MSG_BOARD_READ",allocationSize=1,initialValue=1, sequenceName="SEQ_T_COMMON_MSG_BOARD_READ")
	public Long getRmsgid() {
		return this.rmsgid;
	}

	public void setRmsgid(Long rmsgid) {
		this.rmsgid = rmsgid;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RDATE", length = 7)
	public Date getRdate() {
		return this.rdate;
	}

	public void setRdate(Date rdate) {
		this.rdate = rdate;
	}

	@Column(name = "MSGID")
	public Long getMsgid() {
		return msgid;
	}

	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}

}