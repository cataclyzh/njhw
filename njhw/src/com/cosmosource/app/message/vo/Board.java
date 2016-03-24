package com.cosmosource.app.message.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Board {
	  /* (	"MSGID" NUMBER(16,0) NOT NULL ENABLE, 
				"AUTHOR" VARCHAR2(40) NOT NULL ENABLE, 
				"TITLE" VARCHAR2(150) NOT NULL ENABLE, 
				"CONTENT" VARCHAR2(4000) NOT NULL ENABLE, 
				"MSGTIME" DATE DEFAULT sysdate NOT NULL ENABLE, 
		*/		
	private long msgId;
	
	private String author;
	
	private String title;
	
	private String content;
	
	private Date msgTime;
	
	private String startDate;
	
	private String endDate;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Date getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map getMapVo(){
		Map map = new HashMap();
		map.put("msgId", msgId);
		map.put("author", author);
		map.put("title", title);
		map.put("content", content);
		map.put("msgTime", msgTime);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return map;
	}
	
	public Board setFromMap(Map map){
		this.msgId = Long.parseLong(map.get("MSGID").toString());
		this.author = (String) map.get("author");
		this.title = (String) map.get("title");
		this.content = (String) map.get("content");
		this.msgTime = (Date) map.get("msgTime");
		return this;
	}
	
	@Override
	public String toString() {
		return "["+msgId+"]["+author+"]["+title+"]["
				+content+"]["+msgTime+"]";
	}
}
