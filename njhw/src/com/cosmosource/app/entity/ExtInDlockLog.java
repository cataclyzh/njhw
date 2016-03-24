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
 * 门锁记录日志表
 * ExtInDlockLog entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EXT_IN_DLOCK_LOG")
public class ExtInDlockLog implements java.io.Serializable {

	// Fields

	private Long dlId;
	private String adrsStore;
	private String adrsComm;
	private String cardId;
	private String dlStatus;
	private Date dlDate;
	private String dlFlag;
	private String exp1;
	private String exp2;
	private String exp3;
	private String exp4;
	private String exp5;
	private Long insertId;
	private Date insertDate;

	// Constructors

	/** default constructor */
	public ExtInDlockLog() {
	}

	/** minimal constructor */
	public ExtInDlockLog(Long dlId, String cardId) {
		this.dlId = dlId;
		this.cardId = cardId;
	}

	/** full constructor */
	public ExtInDlockLog(Long dlId, String adrsStore, String adrsComm,
			String cardId, String dlStatus, Date dlDate, String dlFlag,
			String exp1, String exp2, String exp3, String exp4, String exp5,
			Long insertId, Date insertDate) {
		this.dlId = dlId;
		this.adrsStore = adrsStore;
		this.adrsComm = adrsComm;
		this.cardId = cardId;
		this.dlStatus = dlStatus;
		this.dlDate = dlDate;
		this.dlFlag = dlFlag;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
		this.exp5 = exp5;
		this.insertId = insertId;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@Column(name = "DL_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EXT_IN_DLOCK_LOG")
	@SequenceGenerator(name="SEQ_EXT_IN_DLOCK_LOG",allocationSize=1,initialValue=1, sequenceName="SEQ_EXT_IN_DLOCK_LOG")
	public Long getDlId() {
		return this.dlId;
	}

	public void setDlId(Long dlId) {
		this.dlId = dlId;
	}

	@Column(name = "ADRS_STORE", length = 3)
	public String getAdrsStore() {
		return this.adrsStore;
	}

	public void setAdrsStore(String adrsStore) {
		this.adrsStore = adrsStore;
	}

	@Column(name = "ADRS_COMM")
	public String getAdrsComm() {
		return this.adrsComm;
	}

	public void setAdrsComm(String adrsComm) {
		this.adrsComm = adrsComm;
	}

	@Column(name = "CARD_ID", nullable = false, length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "DL_STATUS", length = 1)
	public String getDlStatus() {
		return this.dlStatus;
	}

	public void setDlStatus(String dlStatus) {
		this.dlStatus = dlStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DL_DATE", length = 7)
	public Date getDlDate() {
		return this.dlDate;
	}

	public void setDlDate(Date dlDate) {
		this.dlDate = dlDate;
	}

	@Column(name = "DL_FLAG", length = 1)
	public String getDlFlag() {
		return this.dlFlag;
	}

	public void setDlFlag(String dlFlag) {
		this.dlFlag = dlFlag;
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