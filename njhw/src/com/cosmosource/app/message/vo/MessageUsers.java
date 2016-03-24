package com.cosmosource.app.message.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageUsers {
	private long id;
	private long messageId;
	private long usersId;
	private String status;
	private Date readTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public long getUsersId() {
		return usersId;
	}
	public void setUsersId(long usersId) {
		this.usersId = usersId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	public Map getMapVo(){
		Map map = new HashMap();
		map.put("id", id);
		map.put("messageId", messageId);
		map.put("usersId", usersId);
		map.put("status", status);
		map.put("readTime", readTime);
		return map;
	}
	
	public MessageUsers setFromMap(Map map){
		this.id = Long.parseLong(map.get("ID").toString());
		this.messageId = Long.parseLong( map.get("MESSAGEID").toString());
		this.usersId = Long.parseLong( map.get("USERSID").toString());
		this.status = (String) map.get("STATUS");
		this.readTime = (Date) map.get("READTIME");
		return this;
	}
	
	@Override
	public String toString() {
		return "["+id+"]["+messageId+"]["+usersId+"]["
				+status+"]["+readTime+"]";
	}
}
