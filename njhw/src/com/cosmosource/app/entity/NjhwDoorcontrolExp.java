package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NjhwDoorcontrolExp entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NJHW_DOORCONTROL_EXP")
public class NjhwDoorcontrolExp implements java.io.Serializable {

	// Fields

	private Long id;
	private String cardId;
	private Long dlockId;
	private String adrsNum;
	private String exp1;
	private String exp2;
	private String exp3;
	private String exp4;

	// Constructors

	/** default constructor */
	public NjhwDoorcontrolExp() {
	}

	/** minimal constructor */
	public NjhwDoorcontrolExp(Long id) {
		this.id = id;
	}

	/** full constructor */
	public NjhwDoorcontrolExp(Long id, String cardId, Long dlockId,
			String adrsNum, String exp1, String exp2, String exp3, String exp4) {
		this.id = id;
		this.cardId = cardId;
		this.dlockId = dlockId;
		this.adrsNum = adrsNum;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NJHW_DOORCONTROL_EXP")
	@SequenceGenerator(name="SEQ_NJHW_DOORCONTROL_EXP",allocationSize=1,initialValue=1, sequenceName="SEQ_NJHW_DOORCONTROL_EXP")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CARD_ID", length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "DLOCK_ID", precision = 12, scale = 0)
	public Long getDlockId() {
		return this.dlockId;
	}

	public void setDlockId(Long dlockId) {
		this.dlockId = dlockId;
	}

	@Column(name = "ADRS_NUM", length = 3)
	public String getAdrsNum() {
		return this.adrsNum;
	}

	public void setAdrsNum(String adrsNum) {
		this.adrsNum = adrsNum;
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

}