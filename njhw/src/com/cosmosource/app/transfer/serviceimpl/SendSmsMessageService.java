package com.cosmosource.app.transfer.serviceimpl;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.base.util.Constants;
import com.royasoft.mas.api.SMSAgent;
import com.royasoft.mas.api.model.SMSStatus;
import com.royasoft.mas.api.model.SMSSubmitMessage;

/**
 * @ClassName:SendSmsMessage
 * @Description：关于短信的操作
 * @Author：hp 
 * @Date:2013-3-17
 */
public class SendSmsMessageService{
	
	private static final Log log = LogFactory.getLog(SendSmsMessageService.class);
	
	/**
	* @Description：发送短信的具体操作
	* @Author：hp
	* @Date：2013-3-18
	* @param sms  短信对象，属性：手机号、发送内容
	* @return 成功与否     成功为空字符     失败为未发送成功的短信对象
	**/
	public String send(SmsMessage sms){
		SMSAgent.initialize(Constants.DBMAP.get("WS_SMS_JDBCADDRESS_APP"));//初始化链接
		log.info("短信数据库初始化成功............"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		StringBuffer sb = new StringBuffer();
		while (true) {
			try {
				SMSSubmitMessage message=new SMSSubmitMessage(sms.getUuid(),Constants.DBMAP.get("WS_SMS_EXTCODE"),sms.getPhoneNumber(),sms.getSendContent(),
						Constants.DBMAP.get("WS_SMS_APPLICATIONID"),Constants.DBMAP.get("WS_SMS_IPADDRESS"),Constants.DBMAP.get("WS_SMS_USERNAME"),Constants.DBMAP.get("WS_SMS_PASSWORD"));
				message.setReqDeliveryReport(1);//需要状态报告
				message.setSendMethod(2);//以长短信方式发送
				SMSAgent.sendSMS(message);
				log.info("手机号为："+sms.getPhoneNumber()+" 短信内容为:"+sms.getSendContent()+"----发送成功! "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				return "";
			} catch (Exception e) {
				log.info("手机号为："+sms.getPhoneNumber()+" 短信内容为:"+sms.getSendContent()+"----发送失败! "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				e.printStackTrace();
				sb.append(sms.getPhoneNumber());
				return sb.toString();
			}
		}
	}

	/**
	* @Description：得到发送短信的结果状态
	* @Author：hp
	* @Date：2013-3-18
	**/
	public List<SMSStatus> getSendResult(){
		return SMSAgent.getAllSMSStatus();
	}


}
