package com.cosmosource.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
 * GR_CONFERENCE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_CONFERENCE")
public class GrConference implements Serializable {
	private Long conferenceId;
	private String conferenceName;
	private Date conferenceStartTime;
	private Date conferenceEndTime;
	private Long conferenceUserId;
	private String outName;
	private String orgName;
	private String phonespan;
	private String conferenceRoom;
	private String conferenceManCount;
	private String conferenceState;
	private String conferenceHasService;
	// private String conferenceService;//0000,0001,0010,0011...Multiple choice
	private String conferenceDetailService;
	private String conferenceSatisfy;
	private String conferenceClientValue;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4
	private Long conferencePackageId;
	
	private String startTime;
	private String endTime;

	// Constructors

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/** default constructor */
	public GrConference() {

	}

	/** minimal constructor */
	public GrConference(Long conferenceId, String conferenceName) {
		this.conferenceId = conferenceId;
		this.conferenceName = conferenceName;
	}

	/** full constructor */
	public GrConference(Long conferenceId, String conferenceName,
			Date conferenceStartTime, Date conferenceEndTime,Long conferenceUserId,
			String conferenceUserName, String conferenceUserORG,String conferenceUserPhone,
			String conferenceRoom,String conferenceManCount,
			String conferenceState, String conferenceHasService,
			String conferenceDetailService, String conferenceSatisfy,
			String conferenceClientValue, String resBak1, String resBak2,
			String resBak3, String resBak4,Long conferencePackageId) {
		this.conferenceId = conferenceId;
		this.conferenceName = conferenceName;
		this.conferenceStartTime = conferenceStartTime;
		this.conferenceEndTime = conferenceEndTime;
		this.conferenceUserId=conferenceUserId;
		this.outName = conferenceUserName;
		this.orgName=conferenceUserORG;
		this.phonespan = conferenceUserPhone;
		this.conferenceRoom=conferenceRoom;
		this.conferenceManCount=conferenceManCount;
		this.conferenceState = conferenceState;
		this.conferenceHasService = conferenceHasService;
		// this.conferenceService=conferenceService;
		this.conferenceDetailService = conferenceDetailService;
		this.conferenceSatisfy = conferenceSatisfy;
		this.conferenceClientValue = conferenceClientValue;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
		this.conferencePackageId=conferencePackageId;
	}

	// Property accessors
	@Id
	@Column(name = "CONFERENCE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_CONFERENCE")
	@SequenceGenerator(name = "SEQ_GR_CONFERENCE", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_CONFERENCE")
	public Long getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(Long conferenceId) {
		this.conferenceId = conferenceId;
	}

	@Column(name = "CONFERENCE_NAME", length = 20)
	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONFERENCE_STARTTIME", length = 7)
	public Date getConferenceStartTime() {
		return conferenceStartTime;
	}

	public void setConferenceStartTime(Date conferenceStartTime) {
		this.conferenceStartTime = conferenceStartTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONFERENCE_ENDTIME", length = 7)
	public Date getConferenceEndTime() {
		return conferenceEndTime;
	}

	public void setConferenceEndTime(Date conferenceEndTime) {
		this.conferenceEndTime = conferenceEndTime;
	}

	@Column(name = "CONFERENCE_USERID", precision = 12, scale = 0)
	public Long getConferenceUserId(){
		return conferenceUserId;
	}
	
	public void setConferenceUserId(Long conferenceUserId){
		this.conferenceUserId=conferenceUserId;
	}
	
	@Column(name = "CONFERENCE_USERNAME", length = 20)
	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}
	
	@Column(name = "CONFERENCE_USERORG", length = 90)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "CONFERENCE_USERPHONE", length = 20)
	public String getPhonespan() {
		return phonespan;
	}

	public void setPhonespan(String phonespan) {
		this.phonespan = phonespan;
	}
	
	@Column(name = "CONFERENCE_ROOM", length = 20)
	public String getConferenceRoom() {
		return conferenceRoom;
	}

	public void setConferenceRoom(String conferenceRoom) {
		this.conferenceRoom = conferenceRoom;
	}
	
		
	@Column(name = "CONFERENCE_MANCOUNT", length = 20)
	public String getConferenceManCount() {
		return conferenceManCount;
	}

	public void setConferenceManCount(String conferenceManCount) {
		this.conferenceManCount = conferenceManCount;
	}

	@Column(name = "CONFERENCE_STATE", length = 1)
	public String getConferenceState() {
		return conferenceState;
	}

	public void setConferenceState(String conferenceState) {
		this.conferenceState = conferenceState;
	}

	@Column(name = "CONFERENCE_HASSERVICE", length = 1)
	public String getConferenceHasService() {
		return conferenceHasService;
	}

	public void setConferenceHasService(String conferenceHasService) {
		this.conferenceHasService = conferenceHasService;
	}

	@Column(name = "CONFERENCE_DETAIL_SERVICE", length = 255)
	public String getConferenceDetailService() {
		return conferenceDetailService;
	}

	public void setConferenceDetailService(String conferenceDetailService) {
		this.conferenceDetailService = conferenceDetailService;
	}

	@Column(name = "CONFERENCE_SATISFY", length = 1)
	public String getConferenceSatisfy() {
		return conferenceSatisfy;
	}

	public void setConferenceSatisfy(String conferenceSatisfy) {
		this.conferenceSatisfy = conferenceSatisfy;
	}

	@Column(name = "CONFERENCE_CLIENTVALUE", length = 255)
	public String getConferenceClientValue() {
		return conferenceClientValue;
	}

	public void setConferenceClientValue(String conferenceClientValue) {
		this.conferenceClientValue = conferenceClientValue;
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
	
	public Long getConferencePackageId() {
		return conferencePackageId;
	}

	public void setConferencePackageId(Long conferencePackageId) {
		this.conferencePackageId = conferencePackageId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
