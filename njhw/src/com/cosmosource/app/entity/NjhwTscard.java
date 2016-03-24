package com.cosmosource.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cosmosource.app.fax.model.NjhwTscardPK;



/**
 * NjhwTscard entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NJHW_TSCARD")
@IdClass(NjhwTscardPK.class)
public class NjhwTscard implements java.io.Serializable {
	/*市民卡是否有效*/
	//public static final String CARD_FLAG_USED = "1";	//有效
	//public static final String CARD_FLAG_UNUSED = "2";	//无效
	 /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8743424029912282776L;


	/*市民卡是否有效*/
	public static final String CARD_STATUS_USED = "0";	//有效
	public static final String CARD_STATUS_UNUSED = "1";	//无效
	
	/*是否内部挂失*/
	public static final String SYSTEM_LOSTED_UNLOSTED = "0";	//正常
	public static final String SYSTEM_LOSTED_LOSTED = "1";		//挂失
	
	private String cardId;		// 市民卡号
	private String cardType;	// 卡类型
	private String userDiff;	// 用户区分
	private Long   userId;		// 对应的USERID
	private String userName;	// 用户名称
	private String residentNo;	// 身份证号
	private String contactInfo;	// 联系方式 和mobile重复了
	private Double cardBalance;	// 卡内余额	接口返回值没有
	private String cardUid;
	private String cardExp1;    // 放部门id 满足客户一个card对应过个用户的需求
	private String cardExp2;
	private String cardExp3;
	private String cardExp4;
	//private String cardFlag;
	private Long insertId;		// 插入人ID
	private Date insertDate;	// 插入时间
	private Long modifyId;		// 修改人ID
	private Date modifyDate;	// 修改时间
	private String cardLosted;	// 是否挂失
	private String socitype;	// 证件类型
	private String custcode;	// 卡内号
	private String custsex;		// 性别
	private String domaddr;		// 户籍地址
	private String comaddr;		// 居住地址
	private String moblie;		// 手机
	private String puicstatus;	// 通卡状态（1.开卡; 0.未开）
	private String cardstatus;	// 卡状态
	private String userPhoto;	// 用户图片
	
	private String systemLosted;	// 系统内部挂失

	// Constructors

	/** default constructor */
	public NjhwTscard() {
	}

	/** minimal constructor */
	public NjhwTscard(String cardId, String cardType) {
		this.cardId = cardId;
		this.cardType = cardType;
	}

	/** full constructor */
	public NjhwTscard(String cardId,
			String cardType, String userDiff, String userName,
			String residentNo, String contactInfo, Double cardBalance,
			String cardUid, String cardExp1, String cardExp2, String cardExp3, String cardExp4,
			String cardFlag, Long insertId, Date insertDate, Long modifyId,
			Date modifyDate, String cardLosted, String socitype,
			String custcode, String custsex, String domaddr, String comaddr,
			String moblie, String puicstatus, String cardstatus,
			String userPhoto) {
		this.cardId = cardId;
		this.cardType = cardType;
		this.userDiff = userDiff;
		this.userName = userName;
		this.residentNo = residentNo;
		this.contactInfo = contactInfo;
		this.cardBalance = cardBalance;
		this.cardUid = cardUid;
		this.cardExp1 = cardExp1;
		this.cardExp2 = cardExp2;
		this.cardExp3 = cardExp3;
		this.cardExp4 = cardExp4;
		//this.cardFlag = cardFlag;
		this.insertId = insertId;
		this.insertDate = insertDate;
		this.modifyId = modifyId;
		this.modifyDate = modifyDate;
		this.cardLosted = cardLosted;
		this.socitype = socitype;
		this.custcode = custcode;
		this.custsex = custsex;
		this.domaddr = domaddr;
		this.comaddr = comaddr;
		this.moblie = moblie;
		this.puicstatus = puicstatus;
		this.cardstatus = cardstatus;
		this.userPhoto = userPhoto;
	}

	// Property accessors
	@Id
	@Column(name = "CARD_ID", unique = false, nullable = false, length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Column(name = "CARD_TYPE", nullable = false, precision = 4, scale = 0)
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Column(name = "USER_DIFF", length = 1)
	public String getUserDiff() {
		return this.userDiff;
	}

	public void setUserDiff(String userDiff) {
		this.userDiff = userDiff;
	}
	
	@Column(name = "USER_ID", precision = 12, scale = 0)
	@Id
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "RESIDENT_NO", length = 20)
	public String getResidentNo() {
		return this.residentNo;
	}

	public void setResidentNo(String residentNo) {
		this.residentNo = residentNo;
	}

	@Column(name = "CONTACT_INFO", length = 20)
	public String getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	@Column(name = "CARD_BALANCE", precision = 6)
	public Double getCardBalance() {
		return this.cardBalance;
	}

	public void setCardBalance(Double cardBalance) {
		this.cardBalance = cardBalance;
	}

	@Column(name = "CARD_EXP1", length = 20)
	public String getCardExp1() {
		return this.cardExp1;
	}

	public void setCardExp1(String cardExp1) {
		this.cardExp1 = cardExp1;
	}

	@Column(name = "CARD_EXP2", length = 20)
	public String getCardExp2() {
		return this.cardExp2;
	}

	public void setCardExp2(String cardExp2) {
		this.cardExp2 = cardExp2;
	}

	@Column(name = "CARD_EXP3", length = 20)
	public String getCardExp3() {
		return this.cardExp3;
	}

	public void setCardExp3(String cardExp3) {
		this.cardExp3 = cardExp3;
	}

	@Column(name = "CARD_EXP4", length = 20)
	public String getCardExp4() {
		return this.cardExp4;
	}

	public void setCardExp4(String cardExp4) {
		this.cardExp4 = cardExp4;
	}

//	@Column(name = "CARD_FLAG", length = 1)
//	public String getCardFlag() {
//		return this.cardFlag;
//	}
//
//	public void setCardFlag(String cardFlag) {
//		this.cardFlag = cardFlag;
//	}

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

	@Column(name = "MODIFY_ID", precision = 12, scale = 0)
	public Long getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 7)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "CARD_LOSTED", length = 1)
	public String getCardLosted() {
		return this.cardLosted;
	}

	public void setCardLosted(String cardLosted) {
		this.cardLosted = cardLosted;
	}

	@Column(name = "SOCITYPE", length = 10)
	public String getSocitype() {
		return this.socitype;
	}

	public void setSocitype(String socitype) {
		this.socitype = socitype;
	}

	@Column(name = "CUSTCODE", length = 20)
	public String getCustcode() {
		return this.custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}

	@Column(name = "CUSTSEX", length = 1)
	public String getCustsex() {
		return this.custsex;
	}

	public void setCustsex(String custsex) {
		this.custsex = custsex;
	}

	@Column(name = "DOMADDR", length = 100)
	public String getDomaddr() {
		return this.domaddr;
	}

	public void setDomaddr(String domaddr) {
		this.domaddr = domaddr;
	}

	@Column(name = "COMADDR", length = 100)
	public String getComaddr() {
		return this.comaddr;
	}

	public void setComaddr(String comaddr) {
		this.comaddr = comaddr;
	}

	@Column(name = "MOBLIE", length = 20)
	public String getMoblie() {
		return this.moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}

	@Column(name = "PUICSTATUS", length = 1)
	public String getPuicstatus() {
		return this.puicstatus;
	}

	public void setPuicstatus(String puicstatus) {
		this.puicstatus = puicstatus;
	}

	@Column(name = "CARDSTATUS", length = 1)
	public String getCardstatus() {
		return this.cardstatus;
	}

	public void setCardstatus(String cardstatus) {
		this.cardstatus = cardstatus;
	}

	@Column(name = "USER_PHOTO", length = 100)
	public String getUserPhoto() {
		return this.userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	
	@Column(name = "CARD_UID", length = 20)
	public String getCardUid() {
		return cardUid;
	}

	public void setCardUid(String cardUid) {
		this.cardUid = cardUid;
	}
	@Column(name = "SYSTEM_LOSTED", length = 1)
	public String getSystemLosted() {
		return systemLosted;
	}

	public void setSystemLosted(String systemLosted) {
		this.systemLosted = systemLosted;
	}
}