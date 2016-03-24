package com.cosmosource.app.message.vo;

import java.util.HashMap;
import java.util.Map;

public class Visitor {
	
	//访客申请
	public static final String APPLY = "apply";
	
	//同意申请
	public static final String AGREE = "agree";
	
	//拒绝申请
	public static final String REJECT = "reject";
	
	private long id;
	private String userName;
	private String startTime;
	private String endTime;
	private String comment;
	private String flag;
	private long userId;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Map getMapVo(){
		Map map = new HashMap();
		map.put("id", id);
		map.put("userName", userName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("comment", comment);
		map.put("flag", flag);
		map.put("userId", userId);
		return map;
	}
}
