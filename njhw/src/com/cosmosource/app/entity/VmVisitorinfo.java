package com.cosmosource.app.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 访客管理   访客基本信息
 * VmVisitorinfo entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "VM_VISITORINFO")
public class VmVisitorinfo implements java.io.Serializable {
	//已加入黑名单
	public static final String IS_BLACK_YES = "1";
	//未加黑名单
	public static final String IS_BLACK_NO = "2";

	// Fields

	private Long viId;//访客编号  not null
	private Long nvrId;//访客注册ID
	private String viName;//访客名称
	private String viType;//访客类型
	private String residentNo;//身份证号码
	private String viMobile;//手机号码
	private String viMail;//邮箱
	private String plateNum;//车牌号码
	private String viWname;//网站用户名
	private String viOrigin;//来源
	private String resBak1; // 市民卡
	private String resBak2; // 访客照片
	private String resBak3;//备用
	private String resBak4;//备用
	private String viFlag;//是否认证
	private String useFlag;//有效与否
	private Long insertId;//登记人
	private Date insertDate;//登记时间
	private Long modifyId;//修改人
	private Date modifyDate;//修改时间
	private String isBlack;//是否黑名单
	private String blackReson;//加入黑名原因
	
	// Constructors

	/** default constructor */
	public VmVisitorinfo() {
	}

	/** minimal constructor */
	public VmVisitorinfo(Long viId) {
		this.viId = viId;
	}

	/** full constructor */
	public VmVisitorinfo(Long viId, String viName, String viType,
			String residentNo, String viMobile, String viMail, String plateNum,
			String viWname, String viOrigin, String resBak1, String resBak2,
			String resBak3, String resBak4, String viFlag, String useFlag,
			Long insertId, Date insertDate, Long modifyId, Date modifyDate,
			Set<NjhwTscard> njhwTscards, Set<VmVisit> vmVisits) {
		this.viId = viId;
		this.viName = viName;
		this.viType = viType;
		this.residentNo = residentNo;
		this.viMobile = viMobile;
		this.viMail = viMail;
		this.plateNum = plateNum;
		this.viWname = viWname;
		this.viOrigin = viOrigin;
		this.resBak1 = resBak1;
		this.resBak2 = resBak2;
		this.resBak3 = resBak3;
		this.resBak4 = resBak4;
		this.viFlag = viFlag;
		this.useFlag = useFlag;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
	}

	// Property accessors
	@Id
	@Column(name = "VI_ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VM_VISITORINFO")
	@SequenceGenerator(name="SEQ_VM_VISITORINFO",allocationSize=1,initialValue=1, sequenceName="SEQ_VM_VISITORINFO")
	public Long getViId() {
		return this.viId;
	}

	public void setViId(Long viId) {
		this.viId = viId;
	}
	
	@Column(name = "NVR_ID", unique = true, precision = 12, scale = 0)
	public Long getNvrId() {
		return nvrId;
	}

	public void setNvrId(Long nvrId) {
		this.nvrId = nvrId;
	}

	@Column(name = "VI_NAME", length = 20)
	public String getViName() {
		return this.viName;
	}

	public void setViName(String viName) {
		this.viName = viName;
	}

	@Column(name = "VI_TYPE", length = 2)
	public String getViType() {
		return this.viType;
	}

	public void setViType(String viType) {
		this.viType = viType;
	}

	@Column(name = "RESIDENT_NO", length = 20)
	public String getResidentNo() {
		return this.residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	@Column(name = "VI_MOBILE", length = 20)
	public String getViMobile() {
		return this.viMobile;
	}

	public void setViMobile(String viMobile) {
		this.viMobile = viMobile;
	}

	@Column(name = "VI_MAIL", length = 50)
	public String getViMail() {
		return this.viMail;
	}

	public void setViMail(String viMail) {
		this.viMail = viMail;
	}

	@Column(name = "PLATE_NUM", length = 20)
	public String getPlateNum() {
		return this.plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	@Column(name = "VI_WNAME", length = 20)
	public String getViWname() {
		return this.viWname;
	}

	public void setViWname(String viWname) {
		this.viWname = viWname;
	}

	@Column(name = "VI_ORIGIN", length = 200)
	public String getViOrigin() {
		return this.viOrigin;
	}

	public void setViOrigin(String viOrigin) {
		this.viOrigin = viOrigin;
	}

	@Column(name = "RES_BAK1", length = 20)
	public String getResBak1() {
		return this.resBak1;
	}

	public void setResBak1(String resBak1) {
		this.resBak1 = resBak1;
	}

	@Column(name = "RES_BAK2", length = 20)
	public String getResBak2() {
		return this.resBak2;
	}

	public void setResBak2(String resBak2) {
		this.resBak2 = resBak2;
	}

	@Column(name = "RES_BAK3", length = 20)
	public String getResBak3() {
		return this.resBak3;
	}

	public void setResBak3(String resBak3) {
		this.resBak3 = resBak3;
	}

	@Column(name = "RES_BAK4", length = 20)
	public String getResBak4() {
		return this.resBak4;
	}

	public void setResBak4(String resBak4) {
		this.resBak4 = resBak4;
	}

	@Column(name = "VI_FLAG", length = 1)
	public String getViFlag() {
		return this.viFlag;
	}

	public void setViFlag(String viFlag) {
		this.viFlag = viFlag;
	}

	@Column(name = "USE_FLAG", length = 1)
	public String getUseFlag() {
		return this.useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	@Column(name = "INSERT_ID", precision = 12, scale = 0)
	public Long getInsertId() {
		return this.insertId;
	}

	public void setInsertId(Long insertId) {
		this.insertId = insertId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INSERT_DATE", length = 7)
	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	@Column(name = "MODIFY_ID", precision = 12, scale = 0)
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFY_DATE", length = 7)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Column(name = "IS_BLACK", length = 1)
	public String getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(String isBlack) {
		this.isBlack = isBlack;
	}
	
	@Column(name = "BLACK_RESON", length = 1)
	public String getBlackReson() {
		return blackReson;
	}

	public void setBlackReson(String blackReson) {
		this.blackReson = blackReson;
	}
	
}