package com.cosmosource.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * GR_CONFERENCE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_CONFERENCE_PACKAGE")
public class GrConferencePackage implements Serializable {
	private Long conferencePackageId;
	private String conferencePackageName;
	private String conferencePackageRoom;
	private String conferencePackageLocation;
	private String conferencePackageSeat;
	private String conferencePackageStyle;
	private String conferencePackageFacility;
	private String conferencePackagePrice;
	private String conferencePackageService;
	private String conferencePackageState;
	
	private String resBak1;
	private String resBak2;
	private String resBak3;
	private String resBak4;
	
	// Constructors

	/** default constructor */
	public GrConferencePackage(){
		
	}
	
	/** minimal constructor */
	public GrConferencePackage(Long conferencePackageId){
		this.conferencePackageId = conferencePackageId;
	}
	
	/** full constructor */
	public GrConferencePackage(Long conferencePackageId,String conferencePackageName,String conferencePackageRoom,
								String conferencePackageLocation,String conferencePackageSeat,String conferencePackageStyle,
								String conferencePackageFacility,String conferencePackagePrice,String conferencePackageService,
								String conferencePackageState,String resBak1,String resBak2,String resBak3,String resBak4
			){
		this.conferencePackageId=conferencePackageId;
		this.conferencePackageName=conferencePackageName;
		this.conferencePackageRoom=conferencePackageRoom;
		this.conferencePackageLocation=conferencePackageLocation;
		this.conferencePackageSeat=conferencePackageSeat;
		this.conferencePackageStyle=conferencePackageStyle;
		this.conferencePackageFacility=conferencePackageFacility;
		this.conferencePackagePrice=conferencePackagePrice;
		this.conferencePackageService=conferencePackageService;
		this.conferencePackageState=conferencePackageState;
		this.resBak1=resBak1;
		this.resBak2=resBak2;
		this.resBak3=resBak3;
		this.resBak4=resBak4;
	}
	
	@Id
	@Column(name = "CONFERENCE_PACKAGE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_CONFERENCE_PACKAGE")
	@SequenceGenerator(name = "SEQ_GR_CONFERENCE_PACKAGE", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_CONFERENCE_PACKAGE")
	public Long getConferencePackageId() {
		return conferencePackageId;
	}
	public void setConferencePackageId(Long conferencePackageId) {
		this.conferencePackageId = conferencePackageId;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_NAME", length = 20)
	public String getConferencePackageName() {
		return conferencePackageName;
	}
	public void setConferencePackageName(String conferencePackageName) {
		this.conferencePackageName = conferencePackageName;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_ROOM", length = 20)
	public String getConferencePackageRoom() {
		return conferencePackageRoom;
	}
	public void setConferencePackageRoom(String conferencePackageRoom) {
		this.conferencePackageRoom = conferencePackageRoom;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_LOCATION", length = 50)
	public String getConferencePackageLocation() {
		return conferencePackageLocation;
	}
	public void setConferencePackageLocation(String conferencePackageLocation) {
		this.conferencePackageLocation = conferencePackageLocation;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_SEAT", length = 50)
	public String getConferencePackageSeat() {
		return conferencePackageSeat;
	}
	public void setConferencePackageSeat(String conferencePackageSeat) {
		this.conferencePackageSeat = conferencePackageSeat;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_STYLE", length = 50)
	public String getConferencePackageStyle() {
		return conferencePackageStyle;
	}
	public void setConferencePackageStyle(String conferencePackageStyle) {
		this.conferencePackageStyle = conferencePackageStyle;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_FACILITY", length = 50)
	public String getConferencePackageFacility() {
		return conferencePackageFacility;
	}
	public void setConferencePackageFacility(String conferencePackageFacility) {
		this.conferencePackageFacility = conferencePackageFacility;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_PRICE", length = 20)
	public String getConferencePackagePrice() {
		return conferencePackagePrice;
	}
	public void setConferencePackagePrice(String conferencePackagePrice) {
		this.conferencePackagePrice = conferencePackagePrice;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_SERVICE", length = 255)
	public String getConferencePackageService() {
		return conferencePackageService;
	}
	public void setConferencePackageService(String conferencePackageService) {
		this.conferencePackageService = conferencePackageService;
	}
	
	@Column(name = "CONFERENCE_PACKAGE_STATE", length = 1)
	public String getConferencePackageState() {
		return conferencePackageState;
	}
	public void setConferencePackageState(String conferencePackageState) {
		this.conferencePackageState = conferencePackageState;
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
	
	@Column(name = "RES_BAK4", length = 20)
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
