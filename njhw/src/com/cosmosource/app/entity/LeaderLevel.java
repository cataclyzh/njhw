package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author zhangquanwei
 * @see user.java
 * @date 2014年4月2日17:24:57
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_013_LEADER_LEVEL")
public class LeaderLevel implements java.io.Serializable {

	private Long userid;
	private Long stage;
	
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false,precision = 12, scale = 0)
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	@Column(name = "STAGE", precision = 12, scale = 0)
	public Long getStage() {
		return stage;
	}
	public void setStage(Long stage) {
		this.stage = stage;
	}
	
	public LeaderLevel(Long userid, Long stage) {
		this.userid = userid;
		this.stage = stage;
	}
	
	/** default constructor */
	public LeaderLevel() {
	}
}
