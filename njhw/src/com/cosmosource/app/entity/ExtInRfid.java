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
 * 无线定位 实体类
 * ExtInRfid entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXT_IN_RFID")
public class ExtInRfid implements java.io.Serializable {

	// Fields

	private Long eirId;
	private Long eventId;
	private String tagid;
	private String mac;
	private String status;
	private String x;
	private String y;
	private String z;
	private Date uptime;
	private String exp1;
	private String exp2;
	private String exp3;
	private String exp4;
	private String exp5;
	private String exp6;
	private String exp7;
	private String exp8;
	private String exp9;
	private Long insertId;
	private Date insertDate;

	// Constructors

	/** default constructor */
	public ExtInRfid() {
	}

	/** minimal constructor */
	public ExtInRfid(Long eirId) {
		this.eirId = eirId;
	}

	/** full constructor */
	public ExtInRfid(Long eirId, Long eventId, String tagid, String mac,
			String status, String x, String y, String z, Date uptime,
			String exp1, String exp2, String exp3, String exp4, String exp5,
			String exp6, String exp7, String exp8, String exp9, Long insertId,
			Date insertDate) {
		this.eirId = eirId;
		this.eventId = eventId;
		this.tagid = tagid;
		this.mac = mac;
		this.status = status;
		this.x = x;
		this.y = y;
		this.z = z;
		this.uptime = uptime;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
		this.exp5 = exp5;
		this.exp6 = exp6;
		this.exp7 = exp7;
		this.exp8 = exp8;
		this.exp9 = exp9;
		this.insertId = insertId;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@Column(name = "EIR_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EXT_IN_RFID")
	@SequenceGenerator(name="SEQ_EXT_IN_RFID",allocationSize=1,initialValue=1, sequenceName="SEQ_EXT_IN_RFID")
	public Long getEirId() {
		return this.eirId;
	}

	public void setEirId(Long eirId) {
		this.eirId = eirId;
	}

	@Column(name = "EVENT_ID", precision = 12, scale = 0)
	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Column(name = "TAGID", length = 20)
	public String getTagid() {
		return this.tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

	@Column(name = "MAC", length = 60)
	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name = "STATUS", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "X", length = 20)
	public String getX() {
		return this.x;
	}

	public void setX(String x) {
		this.x = x;
	}

	@Column(name = "Y", length = 20)
	public String getY() {
		return this.y;
	}

	public void setY(String y) {
		this.y = y;
	}

	@Column(name = "Z", length = 20)
	public String getZ() {
		return this.z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPTIME", length = 7)
	public Date getUptime() {
		return this.uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
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

	@Column(name = "EXP5", length = 20)
	public String getExp5() {
		return this.exp5;
	}

	public void setExp5(String exp5) {
		this.exp5 = exp5;
	}

	@Column(name = "EXP6", length = 20)
	public String getExp6() {
		return this.exp6;
	}

	public void setExp6(String exp6) {
		this.exp6 = exp6;
	}

	@Column(name = "EXP7", length = 20)
	public String getExp7() {
		return this.exp7;
	}

	public void setExp7(String exp7) {
		this.exp7 = exp7;
	}

	@Column(name = "EXP8", length = 20)
	public String getExp8() {
		return this.exp8;
	}

	public void setExp8(String exp8) {
		this.exp8 = exp8;
	}

	@Column(name = "EXP9", length = 20)
	public String getExp9() {
		return this.exp9;
	}

	public void setExp9(String exp9) {
		this.exp9 = exp9;
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

}