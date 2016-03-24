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

@Entity
@Table(name = "T_COMMON_LOG_AUDIT")
public class TCommonLogAudit implements java.io.Serializable {
	private static final long serialVersionUID = 9020072168184364746L;
	private Long logid;
	private String tablename;
	private String rowdata;
	private String operateBy;
	private Date operateTime;
	private String operateIp;
	private String operateType;
	private Long tableid;

	/** default constructor */
	public TCommonLogAudit() {
	}

	/** minimal constructor */
	public TCommonLogAudit(Long logid) {
		this.logid = logid;
	}

	/** full constructor */
	public TCommonLogAudit(Long logid, String tablename, String rowdata,
			String operateBy, Date operateTime, String operateIp,
			String operateType,Long tableid) {
		this.logid = logid;
		this.tablename = tablename;
		this.rowdata = rowdata;
		this.operateBy = operateBy;
		this.operateTime = operateTime;
		this.operateIp = operateIp;
		this.operateType = operateType;
		this.tableid = tableid;
	}

	// Property accessors
	@Id
	@Column(name = "LOGID", unique = true, nullable = false, precision = 20, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_LOG_AUDIT")
	@SequenceGenerator(name="SEQ_COMMON_LOG_AUDIT",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_LOG_AUDIT")
	public Long getLogid() {
		return this.logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	@Column(name = "TABLENAME", length = 50)
	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	@Column(name = "ROWDATA")
	public String getRowdata() {
		return this.rowdata;
	}

	public void setRowdata(String rowdata) {
		this.rowdata = rowdata;
	}

	@Column(name = "OPERATE_BY", length = 40)
	public String getOperateBy() {
		return this.operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATE_TIME", length = 7)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "OPERATE_IP", length = 40)
	public String getOperateIp() {
		return this.operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	@Column(name = "OPERATE_TYPE", length = 40)
	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	@Column(name = "TABLEID", precision = 20, scale = 0)
	public Long getTableid() {
		return this.tableid;
	}

	public void setTableid(Long tableid) {
		this.tableid = tableid;
	}
}