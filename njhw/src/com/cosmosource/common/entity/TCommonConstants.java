package com.cosmosource.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_COMMON_CONSTANTS")
public class TCommonConstants implements java.io.Serializable {
	
	private static final long serialVersionUID = -95827029862935371L;
	private Long cttid;
	private String cttKey;
	private String cttValue;
	private String cttDesc;
	private String cttType;

	/** default constructor */
	public TCommonConstants() {
	}

	/** minimal constructor */
	public TCommonConstants(Long cttid) {
		this.cttid = cttid;
	}

	/** full constructor */
	public TCommonConstants(Long cttid, String cttKey, String cttValue,
			String cttDesc, String cttType) {
		this.cttid = cttid;
		this.cttKey = cttKey;
		this.cttValue = cttValue;
		this.cttDesc = cttDesc;
		this.cttType = cttType;
	}

	// Property accessors
	@Id
	@Column(name = "CTTID", unique = true, nullable = false, precision = 16, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_COMMON_CONSTANTS")
	@SequenceGenerator(name="SEQ_COMMON_CONSTANTS",allocationSize=1,initialValue=1, sequenceName="SEQ_COMMON_CONSTANTS")
	public Long getCttid() {
		return this.cttid;
	}

	public void setCttid(Long cttid) {
		this.cttid = cttid;
	}

	@Column(name = "CTT_KEY", length = 40)
	public String getCttKey() {
		return this.cttKey;
	}

	public void setCttKey(String cttKey) {
		this.cttKey = cttKey;
	}

	@Column(name = "CTT_VALUE", length = 4000)
	public String getCttValue() {
		return this.cttValue;
	}

	public void setCttValue(String cttValue) {
		this.cttValue = cttValue;
	}

	@Column(name = "CTT_DESC", length = 200)
	public String getCttDesc() {
		return this.cttDesc;
	}

	public void setCttDesc(String cttDesc) {
		this.cttDesc = cttDesc;
	}

	@Column(name = "CTT_TYPE", length = 20)
	public String getCttType() {
		return this.cttType;
	}

	public void setCttType(String cttType) {
		this.cttType = cttType;
	}

}