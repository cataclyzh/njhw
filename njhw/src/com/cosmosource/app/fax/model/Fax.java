package com.cosmosource.app.fax.model;

public class Fax {
	
	/**
	 * 传真类
	 */
	private int id ;
	private int user_id; //发送用户id
	private int extn; //分机号码
	private String subject; //主题
	private String caller;  //主叫号码
	private String called ; //被叫号码
	private String createTime; //接收时间
	private int pages; //附件页数
	private boolean is_read; //阅读状态
	private int status ; //连接状态
	private int forward_status; //传送状态
	private String username;
	private String reason;
	private String ffStatusName ;
	private String path ;
	
	
	
	public String getCreateTime() {
		return createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getExtn() {
		return extn;
	}
	public void setExtn(int extn) {
		this.extn = extn;
	}
	public String getCalled() {
		return called;
	}
	public void setCalled(String called) {
		this.called = called;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getForward_status() {
		return forward_status;
	}
	public void setForward_status(int forward_status) {
		this.forward_status = forward_status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getFfStatusName() {
		return ffStatusName;
	}
	public void setFfStatusName(String ffStatusName) {
		this.ffStatusName = ffStatusName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public boolean isIs_read() {
		return is_read;
	}
	public void setIs_read(boolean is_read) {
		this.is_read = is_read;
	}
	
}
