/**
 * T_020_Building_Attendancers
 */
package com.cosmosource.app.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhangqw
 * 大厦考勤员
 */
@Entity
@Table(name = "T_020_Building_Attendancers")
public class BuildingAttendancers {
	private Long orgid;
	private String userid;
	
	@Id
	@Column(name = "ORG_ID", nullable = false, precision = 22, scale = 0)
	public Long getOrgid() {
		return orgid;
	}
	@Column(name = "USER_ID", length=1024)
	public String getUserid() {
		return userid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public BuildingAttendancers(Long orgid, String userid) {
		this.orgid = orgid;
		this.userid = userid;
	}
	public BuildingAttendancers() {
	}
	
}
