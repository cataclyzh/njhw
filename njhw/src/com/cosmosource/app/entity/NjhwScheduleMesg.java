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
 * NjhwScheduleMesg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NJHW_SCHEDULE_MESG" )
public class NjhwScheduleMesg implements java.io.Serializable {

	// Fields

	private Long dbid;
	private String recvMobile;
	private String recvMesg;
	private Long sendCnt;
	private Date inTime;
	private Date sendTime;
	private String uuid;

	// Constructors

	/** default constructor */
	public NjhwScheduleMesg() {
	}

	/** minimal constructor */
	public NjhwScheduleMesg(Long dbid) {
		this.dbid = dbid;
	}

	/** full constructor */
	public NjhwScheduleMesg(Long dbid, String recvMobile, String recvMesg,
			Long sendCnt, Date inTime, Date sendTime) {
		this.dbid = dbid;
		this.recvMobile = recvMobile;
		this.recvMesg = recvMesg;
		this.sendCnt = sendCnt;
		this.inTime = inTime;
		this.sendTime = sendTime;
	}

	// Property accessors
	@Id
	@Column(name = "DBID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_SCHEDULE_MESG")
	@SequenceGenerator(name="SEQ_NJHW_SCHEDULE_MESG",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_SCHEDULE_MESG")
	public Long getDbid() {
		return this.dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	@Column(name = "RECV_MOBILE", length = 20)
	public String getRecvMobile() {
		return this.recvMobile;
	}

	public void setRecvMobile(String recvMobile) {
		this.recvMobile = recvMobile;
	}

	@Column(name = "RECV_MESG", length = 4000)
	public String getRecvMesg() {
		return this.recvMesg;
	}

	public void setRecvMesg(String recvMesg) {
		this.recvMesg = recvMesg;
	}

	@Column(name = "SEND_CNT", precision = 1, scale = 0)
	public Long getSendCnt() {
		return this.sendCnt;
	}

	public void setSendCnt(Long sendCnt) {
		this.sendCnt = sendCnt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "IN_TIME", length = 7)
	public Date getInTime() {
		return this.inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SEND_TIME", length = 7)
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@Column(name = "UUID", length = 40)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}