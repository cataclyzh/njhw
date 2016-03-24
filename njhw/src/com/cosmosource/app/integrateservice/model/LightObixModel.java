package com.cosmosource.app.integrateservice.model;

public class LightObixModel {
	//访问的ip地址
	private String uri;
	//用户名
	private String username;
	//密码
	private String password;
	//操作的URL地址
	private String operUri;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOperUri() {
		return operUri;
	}
	public void setOperUri(String operUri) {
		this.operUri = operUri;
	}
	
}
