package com.cosmosource.app.transfer.serviceimpl;

import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.app.port.serviceimpl.SmsSendMessageService;
import com.cosmosource.base.action.BaseAction;

@SuppressWarnings({ "serial", "unchecked" })
public class SmsTestAction extends BaseAction{
	
	private SmsSendMessageService smsMessage;
	
	private DevicePermissionToAppService deviceService;

	@Override
	protected void prepareModel() throws Exception {
	}

	@Override
	public Object getModel() {
		return null;
	}
	
	public String init(){
		return INIT;
	}
	
	
	public String testDeivce(){
//		deviceService.getAuthDeviceList("87");
		return LIST;
	}

	public String test(){
		SmsMessage sms = new SmsMessage();
		sms.setPhoneNumber("18301005442");
		sms.setSendContent("test....");
		smsMessage.send(sms);
		return SUCCESS;
	}


	public DevicePermissionToAppService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DevicePermissionToAppService deviceService) {
		this.deviceService = deviceService;
	}

	public SmsSendMessageService getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(SmsSendMessageService smsMessage) {
		this.smsMessage = smsMessage;
	}

}
