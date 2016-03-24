package com.cosmosource.app.entity.mxw;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * mup的菜单表
 * SLeftmenu entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "S_LEFTMENU")
public class SLeftmenu implements java.io.Serializable {

	// Fields
	private long id;
	private String titleName;
	private String ico;
	private String nodeId;
	private String link;
	private long sort;
	private String blank;
	private String fix;
	private String defaultLink;
	private String memo;
	private long levelNum;
	private String rankfirst;
	private String ranksecond;
	private String isSystem;
	private String isShowNext;
	private long parentId;
	
	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 38, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_S_LEFTMENU")
	@SequenceGenerator(name="SEQ_S_LEFTMENU",allocationSize=1,initialValue=1, sequenceName="SEQ_S_LEFTMENU")
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	@Column(name = "TITLE_NAME", length = 100)
	public String getTitleName() {
		return this.titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	@Column(name = "ICO", length = 300)
	public String getIco() {
		return this.ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	@Column(name = "NODE_ID", length = 10)
	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "LINK", length = 300)
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "SORT", precision = 38, scale = 0)
	public long getSort() {
		return this.sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	@Column(name = "BLANK", length = 2)
	public String getBlank() {
		return this.blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	@Column(name = "FIX", length = 2)
	public String getFix() {
		return this.fix;
	}

	public void setFix(String fix) {
		this.fix = fix;
	}

	@Column(name = "DEFAULT_LINK", length = 2)
	public String getDefaultLink() {
		return this.defaultLink;
	}

	public void setDefaultLink(String defaultLink) {
		this.defaultLink = defaultLink;
	}

	@Column(name = "MEMO", length = 500)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "LEVEL_NUM", precision = 38, scale = 0)
	public long getLevelNum() {
		return this.levelNum;
	}

	public void setLevelNum(long levelNum) {
		this.levelNum = levelNum;
	}

	@Column(name = "RANKFIRST", length = 20)
	public String getRankfirst() {
		return this.rankfirst;
	}

	public void setRankfirst(String rankfirst) {
		this.rankfirst = rankfirst;
	}

	@Column(name = "RANKSECOND", length = 20)
	public String getRanksecond() {
		return this.ranksecond;
	}

	public void setRanksecond(String ranksecond) {
		this.ranksecond = ranksecond;
	}

	@Column(name = "IS_SYSTEM", length = 10)
	public String getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	@Column(name = "IS_SHOW_NEXT", length = 2)
	public String getIsShowNext() {
		return this.isShowNext;
	}

	public void setIsShowNext(String isShowNext) {
		this.isShowNext = isShowNext;
	}

}