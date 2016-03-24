/**
 * T_019_org_Attendance_Admin
 */
package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhangqw
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_019_org_Attendance_Admin")
public class OrgAttendanceAdmin {
	private Long orgid;
	private Long userid;
	
	@Id
	@Column(name = "ORG_ID", nullable = false, precision = 22, scale = 0)
	public Long getOrgid() {
		return orgid;
	}
	@Column(name = "USERID", unique = true, nullable = false,precision = 12, scale = 0)
	public Long getUserid() {
		return userid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public OrgAttendanceAdmin(Long orgid, Long userid) {
		this.orgid = orgid;
		this.userid = userid;
	}
	public OrgAttendanceAdmin() {
	}
	
}
