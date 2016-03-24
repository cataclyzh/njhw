package com.cosmosource.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * IP电话
 * TcIpTel entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TC_IP_TEL")
public class TcIpTel implements java.io.Serializable {

	// Fields

	private Long telId;
	private String telNum;
	private String ndAddr;
	private String telMac;
	private Short telSta;
	private String telFlag;
	private String telExt;
	private String telIdd;
	private String telddd;
	private String telForword;
	private String telCw;
	private String telCornet;
	private String telLocal;
	private Long oldUser;
	private String activeFlag;
	private Date reqDate;
	private Date activeDate;
	private String telType;
	
	
	// Constructors

	/** default constructor */
	public TcIpTel() {
	}

	/** minimal constructor */
	public TcIpTel(Long telId, String telNum) {
		this.telId = telId;
		this.telNum = telNum;
	}

	/** full constructor */
	public TcIpTel(Long telId, String telMac, String ndAddr, String telNum,
			Short telSta, String telFlag) {
		this.telId = telId;
		this.telMac = telMac;
		this.ndAddr = ndAddr;
		this.telNum = telNum;
		this.telSta = telSta;
		this.telFlag = telFlag;
	}

	// Property accessors
	@Id
	@Column(name = "TEL_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TC_IP_TEL")
	@SequenceGenerator(name="SEQ_TC_IP_TEL",allocationSize=1,initialValue=1, sequenceName="SEQ_TC_IP_TEL")
	public Long getTelId() {
		return this.telId;
	}

	public void setTelId(Long telId) {
		this.telId = telId;
	}

	@Column(name = "TEL_NUM", nullable = false, length = 20)
	public String getTelNum() {
		return this.telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	@Column(name = "TEL_MAC", length = 20)
	public String getTelMac() {
		return this.telMac;
	}

	public void setTelMac(String telMac) {
		this.telMac = telMac;
	}

	@Column(name = "TEL_STA", precision = 4, scale = 0)
	public Short getTelSta() {
		return this.telSta;
	}

	public void setTelSta(Short telSta) {
		this.telSta = telSta;
	}

	@Column(name = "TEL_FLAG", length = 1)
	public String getTelFlag() {
		return this.telFlag;
	}

	public void setTelFlag(String telFlag) {
		this.telFlag = telFlag;
	}
	
	@Column(name = "ND_ADDR", length = 50)
	public String getNdAddr() {
		return this.ndAddr;
	}

	public void setNdAddr(String ndAddr) {
		this.ndAddr = ndAddr;
	}
	@Column(name = "TEL_EXT")
	public String getTelExt() {
		return telExt;
	}

	public void setTelExt(String telExt) {
		this.telExt = telExt;
	}
	
	@Column(name = "TEL_IDD")
	public String getTelIdd() {
		return telIdd;
	}
	
	public void setTelIdd(String telIdd) {
		this.telIdd = telIdd;
	}
	
	@Column(name = "TEL_DDD")
	public String getTelddd() {
		return telddd;
	}

	public void setTelddd(String telddd) {
		this.telddd = telddd;
	}
	
	@Column(name = "TEL_FORWARD")
	public String getTelForword() {
		return telForword;
	}

	public void setTelForword(String telForword) {
		this.telForword = telForword;
	}
	
	@Column(name = "TEL_CW")
	public String getTelCw() {
		return telCw;
	}

	public void setTelCw(String telCw) {
		this.telCw = telCw;
	}
	
	@Column(name = "TEL_CORNET")
	public String getTelCornet() {
		return telCornet;
	}

	public void setTelCornet(String telCornet) {
		this.telCornet = telCornet;
	}
	
	@Column(name = "TEL_LOCAL")
	public String getTelLocal() {
		return telLocal;
	}

	public void setTelLocal(String telLocal) {
		this.telLocal = telLocal;
	}
	
	@Column(name = "OLD_USERID")
	public Long getOldUser() {
		return oldUser;
	}

	public void setOldUser(Long oldUser) {
		this.oldUser = oldUser;
	}

	@Column(name = "ACTIVE_FLAG")
	public String getActiveFlag() {
		return activeFlag;
	}
	
	
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	@Column(name = "REQ_DATE")
	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	
	@Column(name = "ACTIVE_DATE")
	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	@Column(name = "TEL_TYPE")
	public String getTelType() {
		return telType;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

}