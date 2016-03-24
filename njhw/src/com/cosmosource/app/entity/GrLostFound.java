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
 * GR_LOST_FOUND entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="GR_LOST_FOUND")
public class GrLostFound implements Serializable{
	private Long lostFoundId;
	private String lostFoundTitle;
	private String lostFoundThingName;
	private Date lostFoundIntime;
	private String lostFoundIntimeString;
	private String lostFoundPickUser;
	private String lostFoundPickUserOrg;
	private String lostFoundLocation;
	private String lostFoundDetail;
	private String lostFoundKeeper;
	private String lostFoundState;
	private String lostFoundLostUser;
	private String lostFoundLostUserOrg;
	private Date lostFoundOverTime;
	private String lostFoundPhone;
	private String resBak1;//RES_BAK1
	private String resBak2;//RES_BAK2
	private String resBak3;//RES_BAK3
	private String resBak4;//RES_BAK4
	
	// Constructors

	/** default constructor */
	public GrLostFound(){
		
	}
	
	/** minimal constructor */
	public GrLostFound(Long lostFoundId,String lostFoundTitle){
		this.lostFoundId=lostFoundId;
		this.lostFoundTitle=lostFoundTitle;
	}
	
	/** default constructor */
	public GrLostFound(Long lostFoundId,String lostFoundTitle,String lostFoundThingName,Date lostFoundIntime,
						String lostFoundPickUser,String lostFoundPickUserOrg,String lostFoundLocation,String lostFoundDetail,String lostFoundKeeper,
						String lostFoundState,String lostFoundLostUser,String lostFoundLostUserOrg,Date lostFoundOverTime,
						String lostFoundPhone,String resBak1,String resBak2,String resBak3,String resBak4){
		this.lostFoundId=lostFoundId;
		this.lostFoundTitle=lostFoundTitle;
		this.lostFoundThingName=lostFoundThingName;
		this.lostFoundIntime=lostFoundIntime;
		this.lostFoundPickUser=lostFoundPickUser;
		this.lostFoundPickUserOrg=lostFoundPickUserOrg;
		this.lostFoundLocation=lostFoundLocation;
		this.lostFoundDetail=lostFoundDetail;
		this.lostFoundKeeper=lostFoundKeeper;
		this.lostFoundState=lostFoundState;
		this.lostFoundLostUser=lostFoundLostUser;
		this.lostFoundLostUserOrg=lostFoundLostUserOrg;
		this.lostFoundOverTime=lostFoundOverTime;
		this.lostFoundPhone=lostFoundPhone;
		this.resBak1=resBak1;
		this.resBak2=resBak2;
		this.resBak3=resBak3;
		this.resBak4=resBak4;
	}

	public String getLostFoundIntimeString() {
		return lostFoundIntimeString;
	}

	public void setLostFoundIntimeString(String lostFoundIntimeString) {
		this.lostFoundIntimeString = lostFoundIntimeString;
	}

	// Property accessors
	@Id
	@Column(name = "LOST_FOUND_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GR_LOST_FOUND")
	@SequenceGenerator(name="SEQ_GR_LOST_FOUND",allocationSize=1,initialValue=1, sequenceName="SEQ_GR_LOST_FOUND")
	public Long getLostFoundId() {
		return lostFoundId;
	}

	public void setLostFoundId(Long lostFoundId) {
		this.lostFoundId = lostFoundId;
	}

	@Column(name="LOST_FOUND_TITLE",length=20)
	public String getLostFoundTitle() {
		return lostFoundTitle;
	}

	public void setLostFoundTitle(String lostFoundTitle) {
		this.lostFoundTitle = lostFoundTitle;
	}
	
	@Column(name="LOST_FOUND_THINGNAME",length=20)
	public String getLostFoundThingName() {
		return lostFoundThingName;
	}

	public void setLostFoundThingName(String lostFoundThingName) {
		this.lostFoundThingName = lostFoundThingName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="LOST_FOUND_INTIME",length=7)
	public Date getLostFoundIntime() {
		return lostFoundIntime;
	}

	public void setLostFoundIntime(Date lostFoundIntime) {
		this.lostFoundIntime = lostFoundIntime;
	}

	@Column(name="LOST_FOUND_PICKUSER",length=20)
	public String getLostFoundPickUser() {
		return lostFoundPickUser;
	}

	public void setLostFoundPickUser(String lostFoundPickUser) {
		this.lostFoundPickUser = lostFoundPickUser;
	}
	
	@Column(name="LOST_FOUND_PICKUSER_ORG",length=20)
	public String getLostFoundPickUserOrg() {
		return lostFoundPickUserOrg;
	}

	public void setLostFoundPickUserOrg(String lostFoundPickUserOrg) {
		this.lostFoundPickUserOrg = lostFoundPickUserOrg;
	}

	@Column(name="LOST_FOUND_LOCATION",length=20)
	public String getLostFoundLocation() {
		return lostFoundLocation;
	}

	public void setLostFoundLocation(String lostFoundLocation) {
		this.lostFoundLocation = lostFoundLocation;
	}

	@Column(name="LOST_FOUND_DETAIL",length=255)
	public String getLostFoundDetail() {
		return lostFoundDetail;
	}

	public void setLostFoundDetail(String lostFoundDetail) {
		this.lostFoundDetail = lostFoundDetail;
	}
	
	@Column(name="LOST_FOUND_KEEPER",length=20)
	public String getLostFoundKeeper() {
		return lostFoundKeeper;
	}

	public void setLostFoundKeeper(String lostFoundKeeper) {
		this.lostFoundKeeper = lostFoundKeeper;
	}
	
	@Column(name="LOST_FOUND_STATE",length=1)
	public String getLostFoundState() {
		return lostFoundState;
	}

	public void setLostFoundState(String lostFoundState) {
		this.lostFoundState = lostFoundState;
	}

	@Column(name="LOST_FOUND_LOSTUSER",length=20)
	public String getLostFoundLostUser() {
		return lostFoundLostUser;
	}

	public void setLostFoundLostUser(String lostFoundLostUser) {
		this.lostFoundLostUser = lostFoundLostUser;
	}
	
	@Column(name="LOST_FOUND_LOSTUSER_ORG",length=20)
	public String getLostFoundLostUserOrg() {
		return lostFoundLostUserOrg;
	}

	public void setLostFoundLostUserOrg(String lostFoundLostUserOrg) {
		this.lostFoundLostUserOrg = lostFoundLostUserOrg;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="LOST_FOUND_OVERTIME",length=7)
	public Date getLostFoundOverTime() {
		return lostFoundOverTime;
	}

	public void setLostFoundOverTime(Date lostFoundOverTime) {
		this.lostFoundOverTime = lostFoundOverTime;
	}

	@Column(name="LOST_FOUND_PHONE",length=20)
	public String getLostFoundPhone() {
		return lostFoundPhone;
	}

	public void setLostFoundPhone(String lostFoundPhone) {
		this.lostFoundPhone = lostFoundPhone;
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
