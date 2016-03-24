package com.cosmosource.common.service;

/**
 * 数据权限身份信息
 * @author WXJ
 *
 */
public class RestrictDataPrinciple {

	/**
	 * 身份id
	 */
	private String sid;
	
	/**
	 * 身份类型
	 */
	private String sidType;
	
	public RestrictDataPrinciple(){
		
	}
	
	public RestrictDataPrinciple(String sid, String sidType){
		this.sid = sid;
		this.sidType = sidType;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSidType() {
		return sidType;
	}

	public void setSidType(String sidType) {
		this.sidType = sidType;
	}
	
	
}
