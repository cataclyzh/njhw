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
@Table(name = "T_COMMON_LOG_OPERATION")
public class TCommonLogOperation implements java.io.Serializable {

	private static final long serialVersionUID = 4941763256437558390L;
	private Long operationId;
	private String loginname;
	private String opIp;
	private Date opTime;
	private String actionId;
	private String actionName;
	private String sessionId;
	private String opType;


	/** default constructor */
	public TCommonLogOperation() {
	}

	/** minimal constructor */
	public TCommonLogOperation(Long operationId) {
		this.operationId = operationId;
	}

	/** full constructor */
	public TCommonLogOperation(Long operationId, String loginname, String opIp,
			Date opTime, String actionId, String actionName, String sessionId,
			String opType) {
		this.operationId = operationId;
		this.loginname = loginname;
		this.opIp = opIp;
		this.opTime = opTime;
		this.actionId = actionId;
		this.actionName = actionName;
		this.sessionId = sessionId;
		this.opType = opType;
	}

	// Property accessors
	@Id
	@Column(name = "OPERATION_ID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_LOG_OPERATION")
	@SequenceGenerator(name="SEQ_COMMON_LOG_OPERATION",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_LOG_OPERATION")
	public Long getOperationId() {
		return this.operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	@Column(name = "LOGINNAME", length = 40)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "OP_IP", length = 40)
	public String getOpIp() {
		return this.opIp;
	}

	public void setOpIp(String opIp) {
		this.opIp = opIp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OP_TIME", length = 7)
	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	@Column(name = "ACTION_ID", length = 100)
	public String getActionId() {
		return this.actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	@Column(name = "ACTION_NAME", length = 100)
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name = "SESSION_ID", length = 100)
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "OP_TYPE", length = 2)
	public String getOpType() {
		return this.opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

}