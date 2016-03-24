package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ObjPermMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OBJ_PERM_MAP")
public class ObjPermMap implements java.io.Serializable {

	// Fields

	private Long mapid;
	private Long personId;
	private Long nodeId;
	private String permCode;
	private Long denyFlag;
	private String type;

	// Constructors

	/** default constructor */
	public ObjPermMap() {
	}

	/** minimal constructor */
	public ObjPermMap(Long mapid, Long personId, Long nodeId,
			String permCode, Long denyFlag) {
		this.mapid = mapid;
		this.personId = personId;
		this.nodeId = nodeId;
		this.permCode = permCode;
		this.denyFlag = denyFlag;
	}

	/** full constructor */
	public ObjPermMap(Long mapid, Long personId, Long nodeId,
			String permCode, Long denyFlag, String type) {
		this.mapid = mapid;
		this.personId = personId;
		this.nodeId = nodeId;
		this.permCode = permCode;
		this.denyFlag = denyFlag;
		this.type = type;
	}

	// Property accessors
	@Id
	@Column(name = "MAPID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getMapid() {
		return this.mapid;
	}

	public void setMapid(Long mapid) {
		this.mapid = mapid;
	}

	@Column(name = "PERSON_ID", nullable = false, precision = 22, scale = 0)
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	@Column(name = "NODE_ID", nullable = false, precision = 22, scale = 0)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "PERM_CODE", nullable = false, length = 20)
	public String getPermCode() {
		return this.permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	@Column(name = "DENY_FLAG", nullable = false, precision = 22, scale = 0)
	public Long getDenyFlag() {
		return this.denyFlag;
	}

	public void setDenyFlag(Long denyFlag) {
		this.denyFlag = denyFlag;
	}

	@Column(name = "TYPE", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}