package com.cosmosource.app.message.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message {
	private long id;
	private String type;
	private String title;
	private String content;
	private Date sendTime;
	private long senderId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public Map getMapVo(){
		Map map = new HashMap();
		map.put("id", id);
		map.put("type", type);
		map.put("title", title);
		map.put("content", content);
		map.put("sendTime", sendTime);
		return map;
	}
	
	public Message setFromMap(Map map){
		this.id = Long.parseLong(map.get("ID").toString());
		this.type = (String) map.get("TYPE");
		this.title = (String) map.get("TITLE");
		this.content = (String) map.get("CONTENT");
		this.sendTime = (Date) map.get("SENDTIME");
		return this;
	}
	
	@Override
	public String toString() {
		return "["+id+"]["+type+"]["+title+"]["
				+content+"]["+sendTime+"]";
	}
}
