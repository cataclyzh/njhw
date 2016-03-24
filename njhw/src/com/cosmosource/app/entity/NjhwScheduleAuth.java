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
 * NjhwScheduleAuth entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NJHW_SCHEDULE_AUTH")
public class NjhwScheduleAuth implements java.io.Serializable {

	// Fields

	private Long dbid;
	private String portSys;
	private String cardId;
	private Long nodeId;
	private Long sendCnt;
	private Date inTime;
	private Date sendTime;
	private String optType;
	
	// Constructors

	/** default constructor */
	public NjhwScheduleAuth() {
	}

	/** minimal constructor */
	public NjhwScheduleAuth(Long dbid, String cardId, Long nodeId) {
		this.dbid = dbid;
		this.cardId = cardId;
		this.nodeId = nodeId;
	}

	/** full constructor */
	public NjhwScheduleAuth(Long dbid, String portSys, String cardId,
			Long nodeId, Long sendCnt, Date inTime, Date sendTime) {
		this.dbid = dbid;
		this.portSys = portSys;
		this.cardId = cardId;
		this.nodeId = nodeId;
		this.sendCnt = sendCnt;
		this.inTime = inTime;
		this.sendTime = sendTime;
	}

	// Property accessors
	@Id
	@Column(name = "DBID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_SCHEDULE_AUTH")
	@SequenceGenerator(name="SEQ_NJHW_SCHEDULE_AUTH",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_SCHEDULE_AUTH")
	public Long getDbid() {
		return this.dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	@Column(name = "PORT_SYS", length = 1)
	public String getPortSys() {
		return this.portSys;
	}

	public void setPortSys(String portSys) {
		this.portSys = portSys;
	}

	@Column(name = "CARD_ID", nullable = false, length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "NODE_ID", nullable = false, precision = 22, scale = 0)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "SEND_CNT", precision = 1, scale = 0)
	public Long getSendCnt() {
		return this.sendCnt;
	}

	public void setSendCnt(Long sendCnt) {
		this.sendCnt = sendCnt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IN_TIME", length = 7)
	public Date getInTime() {
		return this.inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SEND_TIME", length = 7)
	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@Column(name = "OPT_TYPE", length = 1)	
	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}
	
	
	
}