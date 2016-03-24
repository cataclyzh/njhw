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
 * 内容管理-栏目表
 * CmsChannel entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CMS_CHANNEL")
public class CmsChannel implements java.io.Serializable {

	// Fields

	private Long id;
	private Long pid;
	private Long userid;
	private int weight;
	private String name;
	private Date createtime;
	private Date edittime;
	private Long euid;
	private String flag;
	private String leave;

	// Constructors

	/** default constructor */
	public CmsChannel() {
	}

	/** minimal constructor */
	public CmsChannel(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CmsChannel(Long id, Long pid, Long userid, int weight,
			String name, Date createtime, Date edittime, Long euid,
			String flag, String leave) {
		this.id = id;
		this.pid = pid;
		this.userid = userid;
		this.weight = weight;
		this.name = name;
		this.createtime = createtime;
		this.edittime = edittime;
		this.euid = euid;
		this.flag = flag;
		this.leave = leave;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CMS_CHANNEL")
	@SequenceGenerator(name="SEQ_CMS_CHANNEL",allocationSize=1,initialValue=1, sequenceName="SEQ_CMS_CHANNEL")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PID", precision = 12, scale = 0)
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "WEIGHT", precision = 6, scale = 0)
	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EDITTIME", length = 7)
	public Date getEdittime() {
		return this.edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	@Column(name = "EUID", precision = 12, scale = 0)
	public Long getEuid() {
		return this.euid;
	}

	public void setEuid(Long euid) {
		this.euid = euid;
	}

	@Column(name = "FLAG", length = 1)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "LEAVE", length = 1)
	public String getLeave() {
		return this.leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

}