package com.cosmosource.app.test.action;

import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.port.serviceimpl.SmsSendMessageService;
import com.cosmosource.base.action.BaseAction;

@SuppressWarnings({ "serial", "unchecked" })
public class mySendMessageAction extends BaseAction {

	private SmsSendMessageService smsSendMessage;
    private String result;

	public String init() {
		return INIT;
	}

	public String  send() throws Exception {
       String phoneNumber = getRequest().getParameter("phoneNumber");
       String sendContent = getRequest().getParameter("sendContent");
       SmsMessage smsMessage = new SmsMessage();
       smsMessage.setPhoneNumber(phoneNumber);
       smsMessage.setSendContent(sendContent);
       result = smsSendMessage.send(smsMessage);
       return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public SmsSendMessageService getSmsSendMessage() {
		return smsSendMessage;
	}

	public void setSmsSendMessage(SmsSendMessageService smsSendMessage) {
		this.smsSendMessage = smsSendMessage;
	}
	
}
