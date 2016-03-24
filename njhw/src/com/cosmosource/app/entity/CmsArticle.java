package com.cosmosource.app.entity;

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

/**
 * 内容管理-文章表
 * CmsArticle entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CMS_ARTICLE")
public class CmsArticle implements java.io.Serializable {
	
	public static final String NO_CLAIM = "0";
	public static final String CLAIM = "1";

	private Long id;
	private Long mid;
	private Long userid;
	private String createName;
	private String title;
	private int weight;
	private String content;
	private Date createtime;
	private Date edittime;
	private Long euid;
	private String flag;
	private String cartFlag;
	private String cartName;
	private String cartPhone;
	private String cartNum;
	private Date cartDate;
	private Long cartUserid;
	private String cartUname;
	private String cartMemo;

	// Constructors

	/** default constructor */
	public CmsArticle() {
	}

	/** minimal constructor */
	public CmsArticle(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CmsArticle(Long id, Long mid, Long userid, String title,
			int weight, String content, Date createtime, Date edittime,
			Long euid, String flag) {
		this.id = id;
		this.mid = mid;
		this.userid = userid;
		this.title = title;
		this.weight = weight;
		this.content = content;
		this.createtime = createtime;
		this.edittime = edittime;
		this.euid = euid;
		this.flag = flag;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CMS_ARTICLE")
	@SequenceGenerator(name="SEQ_CMS_ARTICLE",allocationSize=1,initialValue=1, sequenceName="SEQ_CMS_ARTICLE")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MID", precision = 12, scale = 0)
	public Long getMid() {
		return this.mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	@Column(name = "USERID", precision = 12, scale = 0)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "WEIGHT", precision = 38, scale = 0)
	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME", length = 7)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EDITTIME", length = 7)
	public Date getEdittime() {
		return this.edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	@Column(name = "EUID", precision = 12, scale = 0)
	public Long getEuid() {
		return this.euid;
	}

	public void setEuid(Long euid) {
		this.euid = euid;
	}

	@Column(name = "FLAG", length = 1)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name = "CREATE_NAME", length =20)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@Column(name = "CART_FLAG", length = 7)
	public String getCartFlag() {
		return cartFlag;
	}

	public void setCartFlag(String cartFlag) {
		this.cartFlag = cartFlag;
	}
	
	@Column(name = "CART_NAME", length = 20)
	public String getCartName() {
		return cartName;
	}

	public void setCartName(String cartName) {
		this.cartName = cartName;
	}
	@Column(name = "CART_PHONE", length = 20)
	public String getCartPhone() {
		return cartPhone;
	}

	public void setCartPhone(String cartPhone) {
		this.cartPhone = cartPhone;
	}
	@Column(name = "CART_NUM", length = 20)
	public String getCartNum() {
		return cartNum;
	}

	public void setCartNum(String cartNum) {
		this.cartNum = cartNum;
	}
	@Column(name = "CART_DATE", length = 7)
	public Date getCartDate() {
		return cartDate;
	}

	public void setCartDate(Date cartDate) {
		this.cartDate = cartDate;
	}
	
	@Column(name = "CART_USERID", precision = 12, scale = 0)
	public Long getCartUserid() {
		return cartUserid;
	}

	public void setCartUserid(Long cartUserid) {
		this.cartUserid = cartUserid;
	}
	
	@Column(name = "CART_UNAME", length = 20)
	public String getCartUname() {
		return cartUname;
	}

	public void setCartUname(String cartUname) {
		this.cartUname = cartUname;
	}

	@Column(name = "CART_MEMO", length = 200)
	public String getCartMemo() {
		return cartMemo;
	}

	public void setCartMemo(String cartMemo) {
		this.cartMemo = cartMemo;
	}

}