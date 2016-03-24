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
 * GR_PARKINGINFO entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name="GR_PARKINGINFO")
public class GrParkingInfo implements Serializable{
	private Long parkingInfoId;
	private Long parkingInfoOrgId;
	private String parkingInfoOrgName;
	private String parkingInfoNumber;
	private String resBak1;//RES_BAK1
	private String resBak2;//RES_BAK2
	private String resBak3;//RES_BAK3
	private String resBak4;//RES_BAK4
	
	// Constructors

		/** default constructor */
		public GrParkingInfo(){
			
		}
		
		/** minimal constructor */
		
		public GrParkingInfo(Long parkingInfoId){
			this.parkingInfoId=parkingInfoId;
		}
		
		/** full constructor */
		public GrParkingInfo(Long parkingInfoId,Long parkingInfoOrgId,String parkingInfoOrgName,String parkingInfoNumber,
				String resBak1,String resBak2,String resBak3,String resBak4){
			this.parkingInfoId=parkingInfoId;
			this.parkingInfoOrgId=parkingInfoOrgId;
			this.parkingInfoOrgName=parkingInfoOrgName;
			this.parkingInfoNumber=parkingInfoNumber;
			this.resBak1=resBak1;
			this.resBak2=resBak2;
			this.resBak3=resBak3;
			this.resBak4=resBak4;
		}

		// Property accessors
		@Id
		@Column(name = "PARKINGINFO_ID", unique = true, nullable = false, precision = 12, scale = 0)
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GR_PARKINGINFO")
		@SequenceGenerator(name="SEQ_GR_PARKINGINFO",allocationSize=1,initialValue=1, sequenceName="SEQ_GR_PARKINGINFO")
		public Long getParkingInfoId() {
			return parkingInfoId;
		}

		public void setParkingInfoId(Long parkingInfoId) {
			this.parkingInfoId = parkingInfoId;
		}

		@Column(name = "PARKINGINFO_ORG_ID", precision = 12, scale = 0)
		public Long getParkingInfoOrgId() {
			return parkingInfoOrgId;
		}

		public void setParkingInfoOrgId(Long parkingInfoOrgId) {
			this.parkingInfoOrgId = parkingInfoOrgId;
		}
		
		@Column(name = "PARKINGINFO_ORGNAME", length=20)
		public String getParkingInfoOrgName() {
			return parkingInfoOrgName;
		}

		public void setParkingInfoOrgName(String parkingInfoOrgName) {
			this.parkingInfoOrgName = parkingInfoOrgName;
		}
		
		@Column(name = "PARKINGINFO_NUMBER", length=20)
		public String getParkingInfoNumber() {
			return parkingInfoNumber;
		}

		public void setParkingInfoNumber(String parkingInfoNumber) {
			this.parkingInfoNumber = parkingInfoNumber;
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
