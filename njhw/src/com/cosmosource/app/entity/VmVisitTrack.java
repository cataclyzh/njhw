package com.cosmosource.app.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 访客管理   访问事务资源跟踪
 * VmVisitTrack entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VM_VISIT_TRACK")
public class VmVisitTrack implements java.io.Serializable {

	// Fields

	private Long vtId;
	private VmVisit vmVisit;
	private String vtInfo;
	private String vtClass;
	private String resBak1;
	private String resBak2;
	private String resBak3;
	private String resBak4;
	private Long insertId;
	private Date insertDate;
	private Long modifyId;
	private Date modifyDate;

	// Constructors

	/** default constructor */
	public VmVisitTrack() {
	}

	/** minimal constructor */
	public VmVisitTrack(Long vtId, VmVisit vmVisit) {
		this.vtId = vtId;
		this.vmVisit = vmVisit;
	}

	/** full constructor */
	public VmVisitTrack(Long vtId, VmVisit vmVisit, String vtInfo,
			String vtClass, String resBak1, String resBak2, String resBak3,
			String resBak4, Long insertId, Date insertDate, Long modifyId,
			Date modifyDate) {
		this.vtId = vtId;
		this.vmVisit = vmVisit;
		this.vtInfo = vtInfo;
		this.vtClass = vtClass;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}

	// Property accessors
	@Id
	@Column(name = "VT_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VM_VISIT_TRACK")
	@SequenceGenerator(name="SEQ_VM_VISIT_TRACK",allocationSize=1,initialValue=1, sequenceName="SEQ_VM_VISIT_TRACK")
	public Long getVtId() {
		return this.vtId;
	}

	public void setVtId(Long vtId) {
		this.vtId = vtId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VS_ID", nullable = false)
	public VmVisit getVmVisit() {
		return this.vmVisit;
	}

	public void setVmVisit(VmVisit vmVisit) {
		this.vmVisit = vmVisit;
	}

	@Column(name = "VT_INFO", length = 50)
	public String getVtInfo() {
		return this.vtInfo;
	}

	public void setVtInfo(String vtInfo) {
		this.vtInfo = vtInfo;
	}

	@Column(name = "VT_CLASS", length = 50)
	public String getVtClass() {
		return this.vtClass;
	}

	public void setVtClass(String vtClass) {
		this.vtClass = vtClass;
	}

	@Column(name = "RES_BAK1", length = 50)
	public String getResBak1() {
		return this.resBak1;
	}

	public void setResBak1(String resBak1) {
		this.resBak1 = resBak1;
	}

	@Column(name = "RES_BAK2", length = 50)
	public String getResBak2() {
		return this.resBak2;
	}

	public void setResBak2(String resBak2) {
		this.resBak2 = resBak2;
	}

	@Column(name = "RES_BAK3", length = 50)
	public String getResBak3() {
		return this.resBak3;
	}

	public void setResBak3(String resBak3) {
		this.resBak3 = resBak3;
	}

	@Column(name = "RES_BAK4", length = 50)
	public String getResBak4() {
		return this.resBak4;
	}

	public void setResBak4(String resBak4) {
		this.resBak4 = resBak4;
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

}