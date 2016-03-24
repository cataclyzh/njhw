package com.cosmosource.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * GR_PATROL_LINE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_PATROL_POSITION_CARD")
public class GrPatrolPositionCard implements Serializable {
	private Long patrolPositionCardId;
	private Long orgId;
	private String orgName;
	private Long userId;
	private String userName;
	private String patrolPositionCardNo;
	private String isAvaliable;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrPatrolPositionCard() {

	}

	/** minimal constructor */
	public GrPatrolPositionCard(Long patrolPositionCardId,Long orgId,Long userId,String resBak1, String resBak2, String resBak3,
			String resBak4) {
		this.patrolPositionCardId = patrolPositionCardId;
		this.orgId = orgId;
		this.userId = userId;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	/** full constructor */
	public GrPatrolPositionCard(Long patrolPositionCardId,Long orgId,Long userId,String orgName,String userName,String patrolPositionCardNo,String isAvaliable,String resBak1, String resBak2, String resBak3,
			String resBak4) {
		this.patrolPositionCardId = patrolPositionCardId;
		this.orgId = orgId;
		this.userId = userId;
		this.orgName = orgName;
		this.userName = userName;
		this.patrolPositionCardNo = patrolPositionCardNo;
		this.isAvaliable = isAvaliable;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "PATROL_POSITION_CARD_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_PATROL_POSITION_CARD")
	@SequenceGenerator(name = "SEQ_GR_PATROL_POSITION_CARD", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_PATROL_POSITION_CARD")
	public Long getPatrolPositionCardId() {
		return patrolPositionCardId;
	}
	
	public void setPatrolPositionCardId(Long patrolPositionCardId) {
		this.patrolPositionCardId = patrolPositionCardId;
	}
	
	@Transient
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "USER_ID", precision = 12, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
	@Transient
	public String getPatrolPositionCardNo() {
		return patrolPositionCardNo;
	}
	
	public void setPatrolPositionCardNo(String patrolPositionCardNo) {
		this.patrolPositionCardNo = patrolPositionCardNo;
	}
	
	@Column(name = "IS_AVALIABLE", length = 1)
	public String getIsAvaliable() {
		return isAvaliable;
	}

	public void setIsAvaliable(String isAvaliable) {
		this.isAvaliable = isAvaliable;
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


	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
