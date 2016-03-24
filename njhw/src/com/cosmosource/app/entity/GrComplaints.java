package com.cosmosource.app.entity;

import java.io.Serializable;
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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * GR_COMPLAINTS entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="GR_COMPLAINTS")
public class GrComplaints implements Serializable{
	private Long complaintsId;
	private String complaintsTitle;
	private Date complaintsInTime;
	private Long complaintsUserId;
	private String complaintsUser;
	private String complaintsTelephone;
	private String complaintsSatisfy;
	private String complaintsDetail;
	private String complaintsFeedback;
	private String complaintsState;
	private Date complaintsOverTime;
	private String complaintsProcessUser;
	private String complaintsProcessPhone;
	private String complaintsProcessResult;
	private String resBak1;//RES_BAK1
	private String resBak2;//RES_BAK2
	private String resBak3;//RES_BAK3
	private String resBak4;//RES_BAK4
	
	// Constructors

	/** default constructor */
	public GrComplaints(){
		
	}
	
	/** minimal constructor */
	public GrComplaints(Long complaintsId){
		this.complaintsId=complaintsId;
	}
	
	/** full constructor */
	public GrComplaints(Long complaintsId,String complaintsTitle,Date complaintsInTime,Long complaintsUserId,
			String complaintsUser,String complaintsTelephone,String complaintsSatisfy,
			String complaintsDetail,String complaintsFeedback,String complaintsState,Date complaintsOverTime,
			String complaintsProcessUser,String complaintsProcessPhone,String complaintsProcessResult,
			String resBak1,String resBak2,String resBak3,String resBak4){
		this.complaintsId=complaintsId;
		this.complaintsTitle=complaintsTitle;
		this.complaintsInTime=complaintsInTime;
		this.complaintsUserId=complaintsUserId;
		this.complaintsUser=complaintsUser;
		this.complaintsTelephone=complaintsTelephone;
		this.complaintsSatisfy=complaintsSatisfy;
		this.complaintsDetail=complaintsDetail;
		this.complaintsFeedback=complaintsFeedback;
		this.complaintsState=complaintsState;
		this.complaintsOverTime=complaintsOverTime;
		this.complaintsProcessUser=complaintsProcessUser;
		this.complaintsProcessPhone=complaintsProcessPhone;
		this.complaintsProcessResult=complaintsProcessResult;
		this.resBak1=resBak1;
		this.resBak2=resBak2;
		this.resBak3=resBak3;
		this.resBak4=resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "COMPLAINTS_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GR_COMPLAINTS")
	@SequenceGenerator(name="SEQ_GR_COMPLAINTS",allocationSize=1,initialValue=1, sequenceName="SEQ_GR_COMPLAINTS")
	public Long getComplaintsId() {
		return complaintsId;
	}

	public void setComplaintsId(Long complaintsId) {
		this.complaintsId = complaintsId;
	}

	@Column(name="COMPLAINTS_TITLE",length=20)
	public String getComplaintsTitle() {
		return complaintsTitle;
	}

	public void setComplaintsTitle(String complaintsTitle) {
		this.complaintsTitle = complaintsTitle;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="COMPLAINTS_INTIME",length=7)
	public Date getComplaintsInTime() {
		return complaintsInTime;
	}

	public void setComplaintsInTime(Date complaintsInTime) {
		this.complaintsInTime = complaintsInTime;
	}
	
	@Column(name="COMPLAINTS_USERID",precision=12,scale=0)
	public Long getComplaintsUserId() {
		return complaintsUserId;
	}

	public void setComplaintsUserId(Long complaintsUserId) {
		this.complaintsUserId = complaintsUserId;
	}

	@Column(name="COMPLAINTS_USER",length=20)
	public String getComplaintsUser() {
		return complaintsUser;
	}

	public void setComplaintsUser(String complaintsUser) {
		this.complaintsUser = complaintsUser;
	}

	@Column(name="COMPLAINTS_TELEPHONE",length=20)
	public String getComplaintsTelephone() {
		return complaintsTelephone;
	}

	public void setComplaintsTelephone(String complaintsTelephone) {
		this.complaintsTelephone = complaintsTelephone;
	}

	@Column(name="COMPLAINTS_SATISFY",length=1)
	public String getComplaintsSatisfy() {
		return complaintsSatisfy;
	}

	public void setComplaintsSatisfy(String complaintsSatisfy) {
		this.complaintsSatisfy = complaintsSatisfy;
	}

	@Column(name="COMPLAINTS_DETAIL",length=255)
	public String getComplaintsDetail() {
		return complaintsDetail;
	}

	public void setComplaintsDetail(String complaintsDetail) {
		this.complaintsDetail = complaintsDetail;
	}
	
	@Column(name="COMPLAINTS_FEEDBACK",length=255)
	public String getComplaintsFeedback() {
		return complaintsFeedback;
	}

	public void setComplaintsFeedback(String complaintsFeedback) {
		this.complaintsFeedback = complaintsFeedback;
	}

	@Column(name="COMPLAINTS_STATE",length=1)
	public String getComplaintsState() {
		return complaintsState;
	}

	public void setComplaintsState(String complaintsState) {
		this.complaintsState = complaintsState;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="COMPLAINTS_OVERTIME",length=7)
	public Date getComplaintsOverTime() {
		return complaintsOverTime;
	}

	public void setComplaintsOverTime(Date complaintsOverTime) {
		this.complaintsOverTime = complaintsOverTime;
	}

	@Column(name="COMPLAINTS_PROCESS_USER",length=20)
	public String getComplaintsProcessUser() {
		return complaintsProcessUser;
	}

	public void setComplaintsProcessUser(String complaintsProcessUser) {
		this.complaintsProcessUser = complaintsProcessUser;
	}

	@Column(name="COMPLAINTS_PROCESS_PHONE",length=20)
	public String getComplaintsProcessPhone() {
		return complaintsProcessPhone;
	}

	public void setComplaintsProcessPhone(String complaintsProcessPhone) {
		this.complaintsProcessPhone = complaintsProcessPhone;
	}

	@Column(name="COMPLAINTS_PROCESS_RESULT",length=20)
	public String getComplaintsProcessResult() {
		return complaintsProcessResult;
	}

	public void setComplaintsProcessResult(String complaintsProcessResult) {
		this.complaintsProcessResult = complaintsProcessResult;
	}
	

	@Column(name = "RES_BAK1", length = 20)
	public String getResBak1() {
		return resBak1;
	}

	public void setResBak1(String resBak1) {
		this.resBak1 = resBak1;
	}

	@Column(name = "RES_BAK2", length = 20)
	public String getResBak2() {
		return resBak2;
	}

	public void setResBak2(String resBak2) {
		this.resBak2 = resBak2;
	}

	@Column(name = "RES_BAK3", length = 20)
	public String getResBak3() {
		return resBak3;
	}

	public void setResBak3(String resBak3) {
		this.resBak3 = resBak3;
	}

	@Column(name = "RES_BAK4", length = 255)
	public String getResBak4() {
		return resBak4;
	}

	public void setResBak4(String resBak4) {
		this.resBak4 = resBak4;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
}
