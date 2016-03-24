package com.cosmosource.app.message.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MsgBox {
	
	//消息
	public static final String TYPE_NOTIFICATION = "10";
	//访客
	public static final String TYPE_VISITOR = "1";
	//待办事项
	public static final String TYPE_BACKLOG = "12";
	//传真
	public static final String TYPE_FACSIMILE = "13";

   	private long msgId; 
	private String receiver; 
	private String sender; 
	private Date msgTime; 
	private String title; 
	private String content; 
	private String status; 
	private long receiverId; 
	private long senderId; 
	private String msgType; 
	private String busnId;
	private int pageMax; //分页上行
	public int getPageMax() {
		return pageMax;
	}
	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
	}
	public int getPageMin() {
		return pageMin;
	}
	public void setPageMin(int pageMin) {
		this.pageMin = pageMin;
	}

	private int pageMin; //分页下行
	
	private String t;
	
	private String startDate;
	
	private String endDate;

	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getBusnId() {
		return busnId;
	}
	public void setBusnId(String busnId) {
		this.busnId = busnId;
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
	
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getReadStatus(){
		if(status != null && status.equals("1")){
			return "已读";
		}else{
			return "未读";
		}
	}
	public Map getMapVo(){
		Map map = new HashMap();
		map.put("msgId", msgId);
		map.put("receiver", receiver);
		map.put("sender", sender);
		map.put("msgTime", msgTime);
		map.put("title", title);
		map.put("content", content);
		map.put("status", status);
		if(receiverId != 0) map.put("receiverId", receiverId);
		if(senderId != 0) map.put("senderId", senderId);
		map.put("msgType", msgType);
		map.put("busnId", busnId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("pageMax", pageMax);
		map.put("pageMin", pageMin);
		return map;
	}
	
	public MsgBox setFromMap(Map map){
		this.msgId = Long.parseLong(map.get("MSGID").toString());
		this.receiver = (String) map.get("RECEIVER");
		this.sender = (String) map.get("SENDER");
		this.msgTime = (Date) map.get("MSGTIME");
		this.title = (String) map.get("TITLE");
		this.content = (String) map.get("CONTENT");
		this.status = (String) map.get("STATUS");
		this.receiverId = parseLongValue(map.get("RECEIVERID"));
		this.senderId = parseLongValue(map.get("SENDERID")); 
		this.msgType = (String) map.get("MSGTYPE");
		this.busnId = (String) map.get("BUSN_ID");
		this.t = (String) map.get("T");
		return this;
	}
	
	@Override
	public String toString() {
		return "["+msgId+"]["+receiver+"]["+sender+"]["
				+msgTime+"]["+title+"]["+content+"]["+status+"]["+receiverId
				+"]["+senderId+"]["+msgType+"]["+busnId+"]";
	}
	
	private long parseLongValue(Object obj){
		long result = 0;
		if(obj != null){
			try {
				result = Long.parseLong(obj.toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
