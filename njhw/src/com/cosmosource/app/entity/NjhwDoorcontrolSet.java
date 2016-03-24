package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 门锁控制表
 * NjhwDoorcontrolSet entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NJHW_DOORCONTROL_SET")
public class NjhwDoorcontrolSet implements java.io.Serializable {

	// Fields

	private Long setId;
	private Long nodeId;//门的id
	private String name;
	private String title;
	private String keyword;
	private String adrsStore;//门锁地址
	private String adrsComm;//通讯机地址
	private String adrsNum;//一字节存储地址
	private String adrsIp;//ip地址

	// Constructors

	/** default constructor */
	public NjhwDoorcontrolSet() {
	}

	/** minimal constructor */
	public NjhwDoorcontrolSet(Long setId, Long nodeId) {
		this.setId = setId;
		this.nodeId = nodeId;
	}

	/** full constructor */
	public NjhwDoorcontrolSet(Long setId, Long nodeId, String name,
			String title, String keyword, String adrsStore, String adrsComm,
			String adrsNum, String adrsIp) {
		this.setId = setId;
		this.nodeId = nodeId;
		this.name = name;
		this.title = title;
		this.keyword = keyword;
		this.adrsStore = adrsStore;
		this.adrsComm = adrsComm;
		this.adrsNum = adrsNum;
		this.adrsIp = adrsIp;
	}

	// Property accessors
	@Id
	@Column(name = "SET_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getSetId() {
		return this.setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	@Column(name = "NODE_ID", nullable = false, precision = 22, scale = 0)
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

	@Column(name = "ADRS_STORE", length = 3)
	public String getAdrsStore() {
		return this.adrsStore;
	}

	public void setAdrsStore(String adrsStore) {
		this.adrsStore = adrsStore;
	}

	@Column(name = "ADRS_COMM")
	public String getAdrsComm() {
		return this.adrsComm;
	}

	public void setAdrsComm(String adrsComm) {
		this.adrsComm = adrsComm;
	}

	@Column(name = "ADRS_NUM", length = 3)
	public String getAdrsNum() {
		return this.adrsNum;
	}

	public void setAdrsNum(String adrsNum) {
		this.adrsNum = adrsNum;
	}

	@Column(name = "ADRS_IP", length = 20)
	public String getAdrsIp() {
		return this.adrsIp;
	}

	public void setAdrsIp(String adrsIp) {
		this.adrsIp = adrsIp;
	}

}