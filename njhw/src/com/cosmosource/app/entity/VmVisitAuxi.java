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
 * 访客管理  多人访问附属信息
 * VmVisitAuxi entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "VM_VISIT_AUXI")
public class VmVisitAuxi implements java.io.Serializable {

	// Fields

	private Long vaId;
	private Long vsId;
	private String cardType;
	private String cardId;
	private String residentNo;
	private String vaBak1;//存放访客姓名
	private String vaBak2;//定位卡号
	private String vaBak3;//抵押物
	private String vaBak4;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;
    private String vaReturn;
    private Date startTime;
    private Date endTime;
    //还副卡
    public static final String VA_RETURN_YES = "1";
   //未还副卡
    public static final String VA_RETURN_NO = "2";
	// Constructors

	/** default constructor */
	public VmVisitAuxi() {
	}

	/** full constructor */
	public VmVisitAuxi(Long vaId,String cardType,
			String cardId, String residentNo, String vaBak1, String vaBak2,
			String vaBak3, String vaBak4, Long insertId, Date insertDate,
			Long modifyId, Date modifyDate,String vaReturn,Date startTime,Date endTime) {
		this.vaId = vaId;
		this.cardType = cardType;
		this.cardId = cardId;
		this.residentNo = residentNo;
		this.vaBak1 = vaBak1;
		this.vaBak2 = vaBak2;
		this.vaBak3 = vaBak3;
		this.vaBak4 = vaBak4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
		this.vaReturn = vaReturn;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Property accessors
	@Id
	@Column(name = "VA_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VM_VISIT_AUXI")
	@SequenceGenerator(name="SEQ_VM_VISIT_AUXI",allocationSize=1,initialValue=1, sequenceName="SEQ_VM_VISIT_AUXI")
	public Long getVaId() {
		return this.vaId;
	}

	public void setVaId(Long vaId) {
		this.vaId = vaId;
	}

	
	
	@Column(name = "VS_ID", unique = true, precision = 12, scale = 0)
	public Long getVsId() {
		return vsId;
	}

	public void setVsId(Long vsId) {
		this.vsId = vsId;
	}

	@Column(name = "CARD_TYPE", length = 1)
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Column(name = "CARD_ID", length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "RESIDENT_NO", length = 20)
	public String getResidentNo() {
		return this.residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	@Column(name = "VA_BAK1", length = 20)
	public String getVaBak1() {
		return this.vaBak1;
	}

	public void setVaBak1(String vaBak1) {
		this.vaBak1 = vaBak1;
	}

	@Column(name = "VA_BAK2", length = 20)
	public String getVaBak2() {
		return this.vaBak2;
	}

	public void setVaBak2(String vaBak2) {
		this.vaBak2 = vaBak2;
	}

	@Column(name = "VA_BAK3", length = 20)
	public String getVaBak3() {
		return this.vaBak3;
	}

	public void setVaBak3(String vaBak3) {
		this.vaBak3 = vaBak3;
	}

	@Column(name = "VA_BAK4", length = 20)
	public String getVaBak4() {
		return this.vaBak4;
	}

	public void setVaBak4(String vaBak4) {
		this.vaBak4 = vaBak4;
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

	@Column(name = "MODIFY_ID", precision = 12, scale = 0)
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFY_DATE", length = 7)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	@Column(name = "VA_RETURN", length = 1)
	public String getVaReturn() {
		return vaReturn;
	}

	public void setVaReturn(String vaReturn) {
		this.vaReturn = vaReturn;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VA_ST", length = 7)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VA_ET", length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}