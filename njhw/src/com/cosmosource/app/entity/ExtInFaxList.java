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
 * 收发传真表
 * ExtInFaxList entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXT_IN_FAX_LIST")
public class ExtInFaxList implements java.io.Serializable {

	// Fields

	private Long flId;
	private Long userid;
	private Integer total;
	private String userId;
	private String username;
	private String caller;
	private String called;
	private String extn;
	private String subject;
	private String fromCsid;
	private String fromName;
	private Integer sendCount;
	private Integer curCount;
	private Integer duration;
	private Integer pages;
	private String status;
	private String statusname;
	private String receiver;
	private String replyAddress;
	private String priority;
	private String forwardStatus;
	private Date createOn;
	private String nextProcTime;
	private String macAdr;
	private String flFlag;
	private String exp1;
	private String exp2;
	private String exp3;
	private String exp4;
	private Long insertId;
	private Date insertDate;
	private Long faxId;
	private String readFlag;

	// Constructors

	/** default constructor */
	public ExtInFaxList() {
	}

	/** minimal constructor */
	public ExtInFaxList(Long flId) {
		this.flId = flId;
	}

	/** full constructor */
	public ExtInFaxList(Long flId, Long userid, Integer total, String userId,
			String username, String caller, String called, String extn,
			String subject, String fromCsid, String fromName,
			Integer sendCount, Integer curCount, Integer duration,
			Integer pages, String status, String statusname, String receiver,
			String replyAddress, String priority, String forwardStatus,
			Date createOn, String nextProcTime, String macAdr, String flFlag,
			String exp1, String exp2, String exp3, String exp4, Long insertId,
			Date insertDate,Long faxId,String readFlag) {
		this.flId = flId;
		this.userid = userid;
		this.total = total;
		this.userId = userId;
		this.username = username;
		this.caller = caller;
		this.called = called;
		this.extn = extn;
		this.subject = subject;
		this.fromCsid = fromCsid;
		this.fromName = fromName;
		this.sendCount = sendCount;
		this.curCount = curCount;
		this.duration = duration;
		this.pages = pages;
		this.status = status;
		this.statusname = statusname;
		this.receiver = receiver;
		this.replyAddress = replyAddress;
		this.priority = priority;
		this.forwardStatus = forwardStatus;
		this.createOn = createOn;
		this.nextProcTime = nextProcTime;
		this.macAdr = macAdr;
		this.flFlag = flFlag;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.faxId=faxId;
		this.readFlag = readFlag;
	}

	// Property accessors
	@Id
	@Column(name = "FL_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EXT_IN_FAX_LIST")
	@SequenceGenerator(name="SEQ_EXT_IN_FAX_LIST",allocationSize=1,initialValue=1, sequenceName="SEQ_EXT_IN_FAX_LIST")
	public Long getFlId() {
		return this.flId;
	}

	public void setFlId(Long flId) {
		this.flId = flId;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "TOTAL", precision = 6, scale = 0)
	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name = "USER_ID", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USERNAME", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "CALLER", length = 20)
	public String getCaller() {
		return this.caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	@Column(name = "CALLED", length = 20)
	public String getCalled() {
		return this.called;
	}

	public void setCalled(String called) {
		this.called = called;
	}

	@Column(name = "EXTN", length = 10)
	public String getExtn() {
		return this.extn;
	}

	public void setExtn(String extn) {
		this.extn = extn;
	}

	@Column(name = "SUBJECT", length = 50)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "FROM_CSID", length = 20)
	public String getFromCsid() {
		return this.fromCsid;
	}

	public void setFromCsid(String fromCsid) {
		this.fromCsid = fromCsid;
	}

	@Column(name = "FROM_NAME", length = 20)
	public String getFromName() {
		return this.fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	@Column(name = "SEND_COUNT", precision = 6, scale = 0)
	public Integer getSendCount() {
		return this.sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	@Column(name = "CUR_COUNT", precision = 6, scale = 0)
	public Integer getCurCount() {
		return this.curCount;
	}

	public void setCurCount(Integer curCount) {
		this.curCount = curCount;
	}

	@Column(name = "DURATION", precision = 6, scale = 0)
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Column(name = "PAGES", precision = 6, scale = 0)
	public Integer getPages() {
		return this.pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	@Column(name = "STATUS", length = 6)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "STATUSNAME", length = 10)
	public String getStatusname() {
		return this.statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	@Column(name = "RECEIVER", length = 20)
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "REPLY_ADDRESS", length = 50)
	public String getReplyAddress() {
		return this.replyAddress;
	}

	public void setReplyAddress(String replyAddress) {
		this.replyAddress = replyAddress;
	}

	@Column(name = "PRIORITY", length = 10)
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Column(name = "FORWARD_STATUS", length = 6)
	public String getForwardStatus() {
		return this.forwardStatus;
	}

	public void setForwardStatus(String forwardStatus) {
		this.forwardStatus = forwardStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_ON", length = 7)
	public Date getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	@Column(name = "NEXT_PROC_TIME", length = 20)
	public String getNextProcTime() {
		return this.nextProcTime;
	}

	public void setNextProcTime(String nextProcTime) {
		this.nextProcTime = nextProcTime;
	}

	@Column(name = "MAC_ADR", length = 20)
	public String getMacAdr() {
		return this.macAdr;
	}

	public void setMacAdr(String macAdr) {
		this.macAdr = macAdr;
	}

	@Column(name = "FL_FLAG", length = 1)
	public String getFlFlag() {
		return this.flFlag;
	}

	public void setFlFlag(String flFlag) {
		this.flFlag = flFlag;
	}

	@Column(name = "EXP1", length = 20)
	public String getExp1() {
		return this.exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}

	@Column(name = "EXP2", length = 20)
	public String getExp2() {
		return this.exp2;
	}

	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}

	@Column(name = "EXP3", length = 20)
	public String getExp3() {
		return this.exp3;
	}

	public void setExp3(String exp3) {
		this.exp3 = exp3;
	}

	@Column(name = "EXP4", length = 20)
	public String getExp4() {
		return this.exp4;
	}

	public void setExp4(String exp4) {
		this.exp4 = exp4;
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
	@Column(name = "FAX_ID", length = 20)
	public Long getFaxId() {
		return faxId;
	}

	public void setFaxId(Long faxId) {
		this.faxId = faxId;
	}
	
	@Column(name = "READ_FLAG", length = 1)
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
	

}