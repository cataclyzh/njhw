package com.cosmosource.app.port.model;

/**
 * @ClassName:SmsMessage
 * @Description：短信对象
 * @Author：hp 
 * @Date:2013-3-17
 */
public class SmsMessage {
	
	private String uuid;
	private String phoneNumber;//手机号码
	private String sendContent;//发送内容
	private String receiveContent;//接收内容

	public SmsMessage(){
	}
	
	public SmsMessage(String phoneNumber,String sendContent){
		this.phoneNumber = phoneNumber;
		this.sendContent = sendContent;
	}
	
	public SmsMessage(String phoneNumber,String sendContent,String receiveContent){
		this.phoneNumber = phoneNumber;
		this.sendContent = sendContent;
		this.receiveContent = receiveContent;
	}

	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getReceiveContent() {
		return receiveContent;
	}

	public void setReceiveContent(String receiveContent) {
		this.receiveContent = receiveContent;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	
	
}
