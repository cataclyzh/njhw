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
 * 物业管理   处理单（预留多重派单）表
 * CsRepairPro entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CS_REPAIR_PRO")
public class CsRepairPro implements java.io.Serializable {
	/*报修处理状态*/		
	//未完成 01 
	public static final String CRP_FLAG_INIT = "01";
	//完成 02 
	public static final String CRP_FLAG_FINISH = "02";
	//已评价 03 
	public static final String CRP_FLAG_EVAL = "03";
	
	// Fields

	private Long crpId;
	private Long crfId;
	private Long crpMid;
	private String crpMname;
	private String crpFlag;
	private Date crpPt;
	private String crfBak1;
	private String crfBak2;
	private String crfBak3;
	private String crfBak4;

	// Constructors

	/** default constructor */
	public CsRepairPro() {
	}

	/** minimal constructor */
	public CsRepairPro(Long crpId) {
		this.crpId = crpId;
	}

	/** full constructor */
	public CsRepairPro(Long crpId,Long crpMid,
			String crpMname, String crpFlag, Date crpPt, String crfBak1,
			String crfBak2, String crfBak3, String crfBak4) {
		this.crpId = crpId;
		this.crpMid = crpMid;
		this.crpMname = crpMname;
		this.crpFlag = crpFlag;
		this.crpPt = crpPt;
		this.crfBak1 = crfBak1;
		this.crfBak2 = crfBak2;
		this.crfBak3 = crfBak3;
		this.crfBak4 = crfBak4;
	}

	// Property accessors
	@Id
	@Column(name = "CRP_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CS_REPAIR_PRO")
	@SequenceGenerator(name="SEQ_CS_REPAIR_PRO",allocationSize=1,initialValue=1, sequenceName="SEQ_CS_REPAIR_PRO")
	public Long getCrpId() {
		return this.crpId;
	}

	
	@Column(name = "CRF_ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getCrfId() {
		return crfId;
	}

	public void setCrfId(Long crfId) {
		this.crfId = crfId;
	}

	public void setCrpId(Long crpId) {
		this.crpId = crpId;
	}

	@Column(name = "CRP_MID", precision = 12, scale = 0)
	public Long getCrpMid() {
		return this.crpMid;
	}

	public void setCrpMid(Long crpMid) {
		this.crpMid = crpMid;
	}

	@Column(name = "CRP_MNAME", length = 20)
	public String getCrpMname() {
		return this.crpMname;
	}

	public void setCrpMname(String crpMname) {
		this.crpMname = crpMname;
	}

	@Column(name = "CRP_FLAG", length = 2)
	public String getCrpFlag() {
		return this.crpFlag;
	}

	public void setCrpFlag(String crpFlag) {
		this.crpFlag = crpFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CRP_PT", length = 7)
	public Date getCrpPt() {
		return this.crpPt;
	}

	public void setCrpPt(Date crpPt) {
		this.crpPt = crpPt;
	}

	@Column(name = "CRF_BAK1", length = 20)
	public String getCrfBak1() {
		return this.crfBak1;
	}

	public void setCrfBak1(String crfBak1) {
		this.crfBak1 = crfBak1;
	}

	@Column(name = "CRF_BAK2", length = 20)
	public String getCrfBak2() {
		return this.crfBak2;
	}

	public void setCrfBak2(String crfBak2) {
		this.crfBak2 = crfBak2;
	}

	@Column(name = "CRF_BAK3", length = 20)
	public String getCrfBak3() {
		return this.crfBak3;
	}

	public void setCrfBak3(String crfBak3) {
		this.crfBak3 = crfBak3;
	}

	@Column(name = "CRF_BAK4", length = 20)
	public String getCrfBak4() {
		return this.crfBak4;
	}

	public void setCrfBak4(String crfBak4) {
		this.crfBak4 = crfBak4;
	}

}