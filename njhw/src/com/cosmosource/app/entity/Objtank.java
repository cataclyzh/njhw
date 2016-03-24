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
 * 系统资源表
 * Objtank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OBJTANK")
public class Objtank implements java.io.Serializable {
	//楼宇
	public static final String EXT_RES_TYPE_S = "S";
	//楼坐
	public static final String EXT_RES_TYPE_B = "B";
	//楼层
	public static final String EXT_RES_TYPE_F = "F";
	//房间
	public static final String EXT_RES_TYPE_R = "R";
	//1 闸机；
	public static final String EXT_RES_TYPE_1 = "1";
	//2 门禁；
	public static final String EXT_RES_TYPE_2 = "2";
	//3 门锁；
	public static final String EXT_RES_TYPE_3 = "3";
	//4 摄像头；
	public static final String EXT_RES_TYPE_4 = "4";
	//5 空调；
	public static final String EXT_RES_TYPE_5 = "5";
	//6 电灯；
	public static final String EXT_RES_TYPE_6 = "6";	
	//7电梯；
	public static final String EXT_RES_TYPE_7 = "7";
	//功能菜单；
	public static final String EXT_RES_TYPE_M = "M";
	
	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 4326372429491087624L;
	private Long nodeId;
	private String name;
	private String title;
	private String keyword;
	private Long isSystem;
	private Long PId;
	private String objType;
	private String objAttrib;
	private Long creator;
	private Date createDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private String memo;
	private String extResType;
	private String extIsBottom;
	private String ico;
	private String link;
	private Long sort;
	private String blank;
	private Long levelNum;

	// Constructors

	/** default constructor */
	public Objtank() {
	}

	/** minimal constructor */
	public Objtank(Long nodeId, Long isSystem) {
		this.nodeId = nodeId;
		this.isSystem = isSystem;
	}

	/** full constructor */
	public Objtank(Long nodeId, String name, String title,
			String keyword, Long isSystem, Long PId,
			String objType, String objAttrib, Long creator,
			Date createDate, Long lastUpdateBy, Date lastUpdateDate,
			String memo, String extResType, String extIsBottom,
			String ico, String link, Long sort, String blank,
			Long levelNum) {
		this.nodeId = nodeId;
		this.name = name;
		this.title = title;
		this.keyword = keyword;
		this.isSystem = isSystem;
		this.PId = PId;
		this.objType = objType;
		this.objAttrib = objAttrib;
		this.creator = creator;
		this.createDate = createDate;
		this.lastUpdateBy = lastUpdateBy;
		this.lastUpdateDate = lastUpdateDate;
		this.memo = memo;
		this.extResType = extResType;
		this.extIsBottom = extIsBottom;
		this.ico = ico;
		this.link = link;
		this.sort = sort;
		this.blank = blank;
		this.levelNum = levelNum;
	}

	@Id
	@Column(name = "NODE_ID", nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_OBJTANK")
	@SequenceGenerator(name="SEQ_OBJTANK",allocationSize=1,initialValue=1, sequenceName="SEQ_OBJTANK")
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "NAME", length = 512)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TITLE", length = 512)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "KEYWORD")
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "IS_SYSTEM", nullable = false, precision = 22, scale = 0)
	public Long getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(Long isSystem) {
		this.isSystem = isSystem;
	}

	@Column(name = "P_ID", precision = 22, scale = 0)
	public Long getPId() {
		return this.PId;
	}

	public void setPId(Long PId) {
		this.PId = PId;
	}

	@Column(name = "OBJ_TYPE", length = 10)
	public String getObjType() {
		return this.objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	@Column(name = "OBJ_ATTRIB", length = 10)
	public String getObjAttrib() {
		return this.objAttrib;
	}

	public void setObjAttrib(String objAttrib) {
		this.objAttrib = objAttrib;
	}

	@Column(name = "CREATOR", precision = 22, scale = 0)
	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "LAST_UPDATE_BY", precision = 22, scale = 0)
	public Long getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE", length = 7)
	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	

	@Column(name = "MEMO", length = 512)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "EXT_RES_TYPE", length = 1)
	public String getExtResType() {
		return this.extResType;
	}

	public void setExtResType(String extResType) {
		this.extResType = extResType;
	}

	@Column(name = "EXT_IS_BOTTOM", length = 1)
	public String getExtIsBottom() {
		return this.extIsBottom;
	}

	public void setExtIsBottom(String extIsBottom) {
		this.extIsBottom = extIsBottom;
	}

	@Column(name = "ICO", length = 300)
	public String getIco() {
		return this.ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	@Column(name = "LINK", length = 300)
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "SORT", precision = 38, scale = 0)
	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	@Column(name = "BLANK", length = 2)
	public String getBlank() {
		return this.blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	@Column(name = "LEVEL_NUM", precision = 38, scale = 0)
	public Long getLevelNum() {
		return this.levelNum;
	}

	public void setLevelNum(Long levelNum) {
		this.levelNum = levelNum;
	}

}