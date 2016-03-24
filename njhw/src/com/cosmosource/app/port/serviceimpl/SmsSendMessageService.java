package com.cosmosource.app.port.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cosmosource.app.entity.NjhwScheduleMesg;
import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.transfer.serviceimpl.SendSmsMessageService;
import com.cosmosource.base.service.BaseManager;
import com.royasoft.mas.api.model.SMSStatus;

/**
 * @ClassName:SmsSendMessage
 * @Description：对于app客户端交互的短信功能模块
 * @Author：hp 
 * @Date:2013-3-17
 */
public class SmsSendMessageService extends BaseManager{

	private SendSmsMessageService smsMessage;
	
	/**
	* @Description：发送短信  ,保存数据到短信表中
	* @Author：hp
	* @Date：2013-3-17
	* @param sms
	**/
	public String send(SmsMessage sms){
		String result = null;
		List<SmsMessage> messages =sendSmss(sms.getPhoneNumber(), sms.getSendContent());
		for (SmsMessage msg : messages) {
			NjhwScheduleMesg smsMsg = new NjhwScheduleMesg();
			msg.setUuid(UUID.randomUUID().toString());
			result = smsMessage.send(msg);
			smsMsg.setRecvMobile(msg.getPhoneNumber());
			smsMsg.setRecvMesg(msg.getReceiveContent());
			smsMsg.setUuid(msg.getUuid());
			smsMsg.setSendCnt(9L);
			dao.save(smsMsg);
		}
		return result;
	}
	
	
	/**
	* @Description：针对调度任务所用的发送短信功能
	* @Author：hp
	* @Date：2013-3-29
	* @param sms
	* @return
	**/
	public String sendToQuarz(SmsMessage sms){
		sms.setUuid(UUID.randomUUID().toString());
		return smsMessage.send(sms);
	}
	
	
	
	/**
	* @Description：针对调度任务得到发送短信的结果状态
	* @Author：hp
	* @Date：2013-3-29
	* @param mesg
	* @return
	**/
	public void getSmsStatus(NjhwScheduleMesg mesg){
		List<SMSStatus> statuss = smsMessage.getSendResult();
		for (SMSStatus status : statuss) {
			int sendResult = status.getSendResult();
			if(sendResult == 4000){
				//把计数设置成0 表示发送已经成功，然后自动删除这条记录
				mesg.setSendCnt(0L);
				dao.update(mesg);
			}
			if(sendResult == 3002){
				//把计数设置成1  表示调度任务继续发送
				mesg.setSendCnt(1L);
				dao.update(mesg);
			}
		}
	}
	
	
	/**
	* @Description：得到一条或是多条记录
	* @Author：hp
	* @Date：2013-3-18
	* @param phoneNumber
	* @param sendContent
	* @return
	**/
	public List<SmsMessage> sendSmss(String phoneNumber,String sendContent){
		List<SmsMessage> smsMessages = new ArrayList<SmsMessage>(10);
		String[] phones = phoneNumber.split(",");
		if(phones.length == 0){
			return null;
		}
		for (String phone : phones) {
			SmsMessage sms = new SmsMessage();
			sms.setPhoneNumber(phone);
			sms.setSendContent(sendContent);
			smsMessages.add(sms);
		}
		return smsMessages;
	}


	public SendSmsMessageService getSmsMessage() {
		return smsMessage;
	}


	public void setSmsMessage(SendSmsMessageService smsMessage) {
		this.smsMessage = smsMessage;
	}
	
	
	

}
