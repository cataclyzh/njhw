package com.cosmosource.app.message.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MsgRead {
	/*
	   (	"RMSGID" NUMBER(16,0) NOT NULL ENABLE, 
				"MSGID" NUMBER(16,0), 
				"USERID" NUMBER(12,0), 
				"RDATE" DATE DEFAULT SYSDATE, */
	private long rMsgId;
	private long msgId;
	private long userId;
	private Date rDate;
	public long getrMsgId() {
		return rMsgId;
	}
	public void setrMsgId(long rMsgId) {
		this.rMsgId = rMsgId;
	}
	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getrDate() {
		return rDate;
	}
	public void setrDate(Date rDate) {
		this.rDate = rDate;
	}
	
	public Map getMapVo(){
		Map map = new HashMap();
		map.put("rMsgId", rMsgId);
		map.put("msgId", msgId);
		map.put("userId", userId);
		map.put("rDate", rDate);
		return map;
	}
	
	public MsgRead setFromMap(Map map){
		this.rMsgId = Long.parseLong(map.get("RMSGID").toString());
		this.msgId = Long.parseLong(map.get("MSGID").toString());
		this.userId = Long.parseLong(map.get("USERID").toString());
		this.rDate = (Date) map.get("RDATE");
		return this;
	}
}
