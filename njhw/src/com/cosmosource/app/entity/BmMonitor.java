package com.cosmosource.app.entity;


import java.util.Date;
import java.util.LinkedHashMap;
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
import javax.persistence.Transient;

/**
 * 日志表
 * BmMonitor entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "BM_MONITOR")
public class BmMonitor implements java.io.Serializable {

	// Fields

	private Long bmId;
	private String bmType;
	private Long resId;
	private String bmOri;
	private String bmDetail;
	private String bmExp1;
	private String bmExp2;
	private String bmExp3;
	private String bmExp4;
	private Long insertId;
	private Date insertDate;
	private String insertName;

	// Constructors

	/** default constructor */
	public BmMonitor() {
	}

	/** minimal constructor */
	public BmMonitor(Long bmId) {
		this.bmId = bmId;
	}

	/** full constructor */
	public BmMonitor(Long bmId, String bmType, Long resId, String bmOri,
			String bmDetail, String bmExp1, String bmExp2, String bmExp3,
			String bmExp4, Long insertId, Date insertDate,String insertName) {
		this.bmId = bmId;
		this.bmType = bmType;
		this.resId = resId;
		this.bmOri = bmOri;
		this.bmDetail = bmDetail;
		this.bmExp1 = bmExp1;
		this.bmExp2 = bmExp2;
		this.bmExp3 = bmExp3;
		this.bmExp4 = bmExp4;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.insertName = insertName;
	}

	// Property accessors
	@Id
	@Column(name = "BM_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_BM_MONITOR")
	@SequenceGenerator(name="SEQ_BM_MONITOR",allocationSize=1,initialValue=1, sequenceName="SEQ_BM_MONITOR")
	public Long getBmId() {
		return this.bmId;
	}

	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}

	@Column(name = "BM_TYPE", length = 1)
	public String getBmType() {
		return this.bmType;
	}

	public void setBmType(String bmType) {
		this.bmType = bmType;
	}

	@Column(name = "RES_ID", precision = 12, scale = 0)
	public Long getResId() {
		return this.resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	@Column(name = "BM_ORI", length = 1)
	public String getBmOri() {
		return this.bmOri;
	}

	public void setBmOri(String bmOri) {
		this.bmOri = bmOri;
	}

	@Column(name = "BM_DETAIL", length = 1000)
	public String getBmDetail() {
		return this.bmDetail;
	}

	public void setBmDetail(String bmDetail) {
		this.bmDetail = bmDetail;
	}

	@Column(name = "BM_EXP1", length = 200)
	public String getBmExp1() {
		return this.bmExp1;
	}

	public void setBmExp1(String bmExp1) {
		this.bmExp1 = bmExp1;
	}

	@Column(name = "BM_EXP2", length = 200)
	public String getBmExp2() {
		return this.bmExp2;
	}

	public void setBmExp2(String bmExp2) {
		this.bmExp2 = bmExp2;
	}

	@Column(name = "BM_EXP3", length = 200)
	public String getBmExp3() {
		return this.bmExp3;
	}

	public void setBmExp3(String bmExp3) {
		this.bmExp3 = bmExp3;
	}

	@Column(name = "BM_EXP4", length = 200)
	public String getBmExp4() {
		return this.bmExp4;
	}

	public void setBmExp4(String bmExp4) {
		this.bmExp4 = bmExp4;
	}

	@Column(name = "INSERT_ID", precision = 12, scale = 0)
	public Long getInsertId() {
		return this.insertId;
	}

	public void setInsertId(Long insertId) {
		this.insertId = insertId;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_DATE", length = 7)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	@Column(name = "INSERT_NAME", precision = 200, scale = 0)
	public String getInsertName() {
		return insertName;
	}

	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}
	
	@Transient
	public String getBmTypeString(){
		if(this.getBmType()!=null){
			return getDeviceTypeMap().get(getBmType());
		}
		return null;
	}
	/**
	 * 用于授权
	 * @return
	 */
	@Transient
	public static Map<String,String> getDeviceTypeMap(){
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_1,"闸机门禁");
//		map.put(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_2,"门禁");
		map.put(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_3,"门锁");
		//map.put(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_4,"摄像头");
		map.put(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_5,"空调");
		map.put(com.cosmosource.app.entity.Objtank.EXT_RES_TYPE_6,"电灯");
		return map;
	}

}