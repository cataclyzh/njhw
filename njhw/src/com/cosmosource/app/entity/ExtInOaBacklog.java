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
 * ExtInOaBacklog entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXT_IN_OA_BACKLOG")
public class ExtInOaBacklog implements java.io.Serializable {

	// Fields

	private Long obid;
	private String username;
	private String openid;
	private String state;
	private Integer total;
	private String urgentLevel;
	private String desc1;
	private Date received;
	private String deadTim;
	private String staffes;
	private String subUrl;
	private String exp1;
	private String exp2;
	private String exp3;
	private String exp4;
	private Long insertId;
	private Date insertDate;

	// Constructors

	/** default constructor */
	public ExtInOaBacklog() {
	}

	/** minimal constructor */
	public ExtInOaBacklog(Long obid) {
		this.obid = obid;
	}

	/** full constructor */
	public ExtInOaBacklog(Long obid, String username, String openid,
			String state, Integer total, String urgentLevel, String desc1,
			Date received, String deadTim, String staffes, String subUrl,
			String exp1, String exp2, String exp3, String exp4, Long insertId,
			Date insertDate) {
		this.obid = obid;
		this.username = username;
		this.openid = openid;
		this.state = state;
		this.total = total;
		this.urgentLevel = urgentLevel;
		this.desc1 = desc1;
		this.received = received;
		this.deadTim = deadTim;
		this.staffes = staffes;
		this.subUrl = subUrl;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
		this.insertId = insertId;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@Column(name = "OBID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EXT_IN_OA_BACKLOG")
	@SequenceGenerator(name="SEQ_EXT_IN_OA_BACKLOG",allocationSize=1,initialValue=1, sequenceName="SEQ_EXT_IN_OA_BACKLOG")
	public Long getObid() {
		return this.obid;
	}

	public void setObid(Long obid) {
		this.obid = obid;
	}

	@Column(name = "USERNAME", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "OPENID", length = 20)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "STATE", length = 6)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "TOTAL", precision = 6, scale = 0)
	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name = "URGENT_LEVEL", length = 20)
	public String getUrgentLevel() {
		return this.urgentLevel;
	}

	public void setUrgentLevel(String urgentLevel) {
		this.urgentLevel = urgentLevel;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RECEIVED", length = 7)
	public Date getReceived() {
		return this.received;
	}

	public void setReceived(Date received) {
		this.received = received;
	}

	@Column(name = "DEAD_TIM", length = 20)
	public String getDeadTim() {
		return this.deadTim;
	}

	public void setDeadTim(String deadTim) {
		this.deadTim = deadTim;
	}

	@Column(name = "STAFFES", length = 50)
	public String getStaffes() {
		return this.staffes;
	}

	public void setStaffes(String staffes) {
		this.staffes = staffes;
	}

	@Column(name = "SUB_URL", length = 500)
	public String getSubUrl() {
		return this.subUrl;
	}

	public void setSubUrl(String subUrl) {
		this.subUrl = subUrl;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_DATE", length = 7)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	@Column(name = "desc1", length = 500)
	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	

}