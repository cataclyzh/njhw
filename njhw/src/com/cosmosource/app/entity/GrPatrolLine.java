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
 * GR_PATROL_LINE entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "GR_PATROL_LINE")
public class GrPatrolLine implements Serializable {
	private Long patrolLineId;
	private String patrolLineName;
	private String patrolLineDesc;
	private String patrolNodes;
	private String isAvaliable;
	private String resBak1;// RES_BAK1
	private String resBak2;// RES_BAK2
	private String resBak3;// RES_BAK3
	private String resBak4;// RES_BAK4

	// Constructors

	/** default constructor */
	public GrPatrolLine() {

	}

	/** minimal constructor */
	public GrPatrolLine(Long patrolLineId,String resBak1, String resBak2, String resBak3,
			String resBak4) {
		this.patrolLineId = patrolLineId;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	/** full constructor */
	public GrPatrolLine(Long patrolLineId, String patrolLineName,String patrolLineDesc,String patrolNodes,String isAvaliable,String resBak1, String resBak2, String resBak3,
			String resBak4) {
		this.patrolLineId = patrolLineId;
		this.patrolLineName = patrolLineName;
		this.patrolLineDesc = patrolLineDesc;
		this.patrolNodes = patrolNodes;
		this.isAvaliable = isAvaliable;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
	}

	// Property accessors
	@Id
	@Column(name = "PATROL_LINE_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GR_PATROL_LINE")
	@SequenceGenerator(name = "SEQ_GR_PATROL_LINE", allocationSize = 1, initialValue = 1, sequenceName = "SEQ_GR_PATROL_LINE")
	public Long getPatrolLineId() {
		return patrolLineId;
	}

	public void setPatrolLineId(Long patrolLineId) {
		this.patrolLineId = patrolLineId;
	}

	@Column(name = "PATROL_LINE_NAME", length = 90)
	public String getPatrolLineName() {
		return patrolLineName;
	}

	public void setPatrolLineName(String patrolLineName) {
		this.patrolLineName = patrolLineName;
	}

	@Column(name = "PATROL_LINE_DESC", length = 550)
	public String getPatrolLineDesc() {
		return patrolLineDesc;
	}

	public void setPatrolLineDesc(String patrolLineDesc) {
		this.patrolLineDesc = patrolLineDesc;
	}
	
	@Column(name = "PATROL_NODES", length = 550)
	public String getPatrolNodes() {
		return patrolNodes;
	}

	public void setPatrolNodes(String patrolNodes) {
		this.patrolNodes = patrolNodes;
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
