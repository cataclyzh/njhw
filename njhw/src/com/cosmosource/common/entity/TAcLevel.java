package com.cosmosource.common.entity;

//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cosmosource.base.dao.AuditableEntity;

/**
 * TAcRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_AC_LEVEL")
@org.hibernate.annotations.Entity(dynamicInsert = true,dynamicUpdate = true)
public class TAcLevel extends AuditableEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7363053765996887918L;
	private Long levelId;
	private String levelCode;
	private String levelName;
	private String company;
	private String remark;
	
	public TAcLevel(){
		
	}
	
	public TAcLevel(Long levelId, String levelCode,
			String levelName, String company, String remark){
		this.levelId = levelId;
		this.levelCode = levelCode;
		this.levelName = levelName;
		this.company = company;
		this.remark = remark;
	}
	
	@Id
	@Column(name = "LEVEL_ID", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AC_LEVEL")
	@SequenceGenerator(name="SEQ_AC_LEVEL",allocationSize=1,initialValue=1, sequenceName="SEQ_AC_LEVEL")
	public Long getLevelId() {
		return levelId;
	}
	
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	
	@Column(name = "LEVEL_CODE", length = 20)
	public String getLevelCode() {
		return levelCode;
	}
	
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	
	@Column(name = "LEVEL_NAME", length = 40)
	public String getLevelName() {
		return levelName;
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	@Column(name = "COMPANY", length = 40)
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "REMARK", length = 400)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
