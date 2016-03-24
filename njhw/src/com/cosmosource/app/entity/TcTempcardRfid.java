package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 临时卡表
 * TcTempcardRfid entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TC_TEMPCARD_RFID")
public class TcTempcardRfid implements java.io.Serializable {

	// Fields

	private String cardId;
	private String rfid;

	// Constructors

	/** default constructor */
	public TcTempcardRfid() {
	}

	/** minimal constructor */
	public TcTempcardRfid(String cardId) {
		this.cardId = cardId;
	}

	/** full constructor */
	public TcTempcardRfid(String cardId, String rfid) {
		this.cardId = cardId;
		this.rfid = rfid;
	}

	// Property accessors
	@Id
	@Column(name = "CARD_ID", unique = true, nullable = false, length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "RFID", length = 20)
	public String getRfid() {
		return this.rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

}