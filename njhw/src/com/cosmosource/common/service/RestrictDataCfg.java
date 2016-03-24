package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限配置信息
 * @author WXJ
 *
 */
public class RestrictDataCfg {
	
	private List<RestrictDataPrinciple> principleList = new ArrayList<RestrictDataPrinciple>();
	private String dataValue;
	private String dataType;
	private String restrictColumn;
	private String company;

	public List<RestrictDataPrinciple> getPrincipleList() {
		return principleList;
	}
	
	public void setPrincipleList(List<RestrictDataPrinciple> principleList) {
		this.principleList = principleList;
	}
	
	public String getDataValue() {
		return dataValue;
	}
	
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getRestrictColumn() {
		return restrictColumn;
	}
	
	public void setRestrictColumn(String restrictColumn) {
		this.restrictColumn = restrictColumn;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void addRestrictDataPrinciple(RestrictDataPrinciple principle){
		principleList.add(principle);
	}
	
	public void addRestrictDataPrinciple(String sid, String sidType){
		principleList.add(new RestrictDataPrinciple(sid, sidType));
	}
	
	public void addRestrictDataPrincipleUser(String userid){
		addRestrictDataPrinciple(userid, RestrictDataContext.PRINCIPLE_USER);
	}
	
	public void addRestrictDataPrincipleOrg(String orgid){
		addRestrictDataPrinciple(orgid, RestrictDataContext.PRINCIPLE_ORG);
	}
	
	public void addRestrictDataPrincipleRole(String roleid){
		addRestrictDataPrinciple(roleid, RestrictDataContext.PRINCIPLE_ROLE);
	}
}
