package com.cosmosource.app.port.model;

/**
 * @ClassName:SiteStatus
 * @Description：各系统设备状态
 * @Author：hp 
 * @Date:2013-3-30
 */
public class SiteStatus {

	private String nodeId;//资源ID
	private String extResType;//设备类型   如:闸机、门禁
	private String extResName;//设备名称
	private String statusCode;//状态码
	private String statusName;//状态值
	
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getExtResType() {
		return extResType;
	}
	public void setExtResType(String extResType) {
		this.extResType = extResType;
	}
	public String getExtResName() {
		return extResName;
	}
	public void setExtResName(String extResName) {
		this.extResName = extResName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}
