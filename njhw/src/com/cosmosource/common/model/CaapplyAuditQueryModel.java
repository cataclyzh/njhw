package com.cosmosource.common.model;

/**
 * @类描述: CA资料审核查询通用类
 * @作者： fengfj
 */
public class CaapplyAuditQueryModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8633620615211127593L;
	
	//机构名称
	private String orgname;
	//机构证件号码
	private String orgidnum;
	//申请人
	private String applyuser;
	//申请代码
	private String applyno;
	//申请开始时间
	private String startTime;
	//申请结束时间
	private String endTime;
	//审核状态
	private String auditstatus;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getOrgidnum() {
		return orgidnum;
	}
	public void setOrgidnum(String orgidnum) {
		this.orgidnum = orgidnum;
	}
	public String getApplyuser() {
		return applyuser;
	}
	public void setApplyuser(String applyuser) {
		this.applyuser = applyuser;
	}
	public String getApplyno() {
		return applyno;
	}
	public void setApplyno(String applyno) {
		this.applyno = applyno;
	}
	public String getAuditstatus() {
		return auditstatus;
	}
	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}
	
	
}


