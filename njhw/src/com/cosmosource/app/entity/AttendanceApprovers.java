/**
 * 考勤人品人员设定
 */
package com.cosmosource.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhangqw
 * @date 2014年4月3日10:48:24
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_017_ATTENDANCE_APPROVERS")
public class AttendanceApprovers implements java.io.Serializable{
	private Long userid;
	// 审批人员ids
	private String approvers;
	public AttendanceApprovers(Long userid, String approvers) {
		this.userid = userid;
		this.approvers = approvers;
	}
	public AttendanceApprovers() {
	}
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false,precision = 12, scale = 0)
	public Long getUserid() {
		return userid;
	}
	@Column(name = "APPROVERS", length = 1024)
	public String getApprovers() {
		return approvers;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}
	
}
