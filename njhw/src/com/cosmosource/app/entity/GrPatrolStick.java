package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 巡更棒
 * @author zhujiabiao
 *
 */
@Entity
@Table(name="GR_PATROL_STICK")
public class GrPatrolStick {
	public Long sId;//id
	public String sNo;//卡号
	public String sName;//名称
	public String isAvaliable;//是否启用  
	public String resBak1;
	public String resBak2;
	
	@Id
	@Column(name = "S_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GR_PATROL_STICK")
	@SequenceGenerator(name="SEQ_GR_PATROL_STICK",allocationSize=1,initialValue=1, sequenceName="SEQ_GR_PATROL_STICK")
	public Long getsId() {
		return sId;
	}
	public void setsId(Long sId) {
		this.sId = sId;
	}
	
	@Column(name = "S_NO")
	public String getsNo() {
		return sNo;
	}
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}
	
	@Column(name = "S_NAME")
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	
	@Column(name = "IS_AVALIABLE")
	public String getIsAvaliable() {
		return isAvaliable;
	}
	public void setIsAvaliable(String isAvaliable) {
		this.isAvaliable = isAvaliable;
	}
	
	@Column(name = "RES_BAK1")
	public String getResBak1() {
		return resBak1;
	}
	public void setResBak1(String resBak1) {
		this.resBak1 = resBak1;
	}
	
	@Column(name = "RES_BAK2")
	public String getResBak2() {
		return resBak2;
	}
	public void setResBak2(String resBak2) {
		this.resBak2 = resBak2;
	}

	
}
