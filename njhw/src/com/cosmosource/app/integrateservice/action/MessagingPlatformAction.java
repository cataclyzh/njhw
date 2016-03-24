package com.cosmosource.app.integrateservice.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.TCommonSmsBox;
import com.cosmosource.app.integrateservice.service.MessagingPlatformManager;
import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.port.serviceimpl.SmsSendMessageService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings({ "serial", "unchecked" })
public class MessagingPlatformAction extends BaseAction<TCommonSmsBox> {
	
	private Page<TCommonSmsBox> page = new Page<TCommonSmsBox>(6);//默认每页20条记录
	private TCommonSmsBox tCommonSmsBox = new TCommonSmsBox();
	private MessagingPlatformManager messagingPlatformManager;
	private SmsSendMessageService smsSendMessage;
	/**
	 * 
	* @title: messagingPlatformInit 
	* @description: 初始化短信发送平台
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String messagingPlatformInit(){
		try {
			String mobile = "", name="";
			if(getParameter("mobile") != null && StringUtil.isNotEmpty(getParameter("mobile"))) 
					mobile = getParameter("mobile");
			
			if(getParameter("name") != null && StringUtil.isNotEmpty(getParameter("name")))
				name = new String(getParameter("name").getBytes("ISO-8859-1"),"utf-8");
			
			getRequest().setAttribute("mobile", mobile);
			getRequest().setAttribute("name", name);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入发送短信页面
	* @title: sendNoteInit 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-5-16 下午05:20:40     
	* @throws
	 */
    public String sendNoteInit(){
		getRequest().setAttribute("mobile", getParameter("mobile"));
    	getRequest().setAttribute("name", getParameter("name"));
    	return SUCCESS;
    }
    
  
    /**
     * 查看邮件
    * @title: checkOutbox 
    * @description: TODO
    * @author gxh
    * @return
    * @date 2013-5-17 下午01:56:08     
    * @throws
     */
    public String checkOutbox(){
    	
    	return SUCCESS;
    	
    }
    /**
     * 返回主页 
    * @title: reutenPage 
    * @description: TODO
    * @author gxh
    * @return
    * @date 2013-5-17 下午05:16:12     
    * @throws
     */
    public String reutenPage(){
    	return SUCCESS;
    }
	@Override
	protected void prepareModel() throws Exception {
		tCommonSmsBox = new TCommonSmsBox();
	}

	@Override
	public TCommonSmsBox getModel() {
		return tCommonSmsBox;
	}
	
	/**
	 * @throws JSONException 
	 * 
	* @title: sendPhoneMessage 
	* @description: 短信发送平台--发送按钮
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String sendPhoneMessage() throws JSONException{
		JSONObject json = new JSONObject();
		try {
			String isNoSave = getParameter("isNoSave");
			String messReceiverId = getParameter("messReceiverId");//获得收件人ID
			String messReceiverName = getParameter("messReceiverName");//获得收件人姓名
			String userIdTrees[] = messReceiverId.split(",");
			String userNames[] = messReceiverName.split(",");
			String message = getParameter("messageContent");//获得短信内容
			String userSendMessageId ;
			String userSendMessageName ;
			//保存到   T_COMMON_SMS_BOX
			
		   if("1".equals(isNoSave)){
			
			TCommonSmsBox tCommonSmsBox;
			for(int i=0;i<userIdTrees.length;i++){
				tCommonSmsBox = new TCommonSmsBox();
				
				userSendMessageId = userIdTrees[i];
				userSendMessageName = userNames[i];
				
				tCommonSmsBox.setReceiver(userSendMessageName);       //收件人姓名
				if(StringUtil.isNotBlank(userSendMessageId) && StringUtil.isNotBlank(getSession().getAttribute(Constants.USER_ID)+"") ){
					tCommonSmsBox.setReceiverid(Long.parseLong(userSendMessageId));  //收件人ID 
					tCommonSmsBox.setSenderid((Long)getSession().getAttribute(Constants.USER_ID));  //发送人ID
				}else{
					tCommonSmsBox.setReceiverid(Long.parseLong("00"));  //收件人ID 不在树中选存"00"
				}
				tCommonSmsBox.setSender((String)this.getSession().getAttribute(Constants.LOGIN_NAME)); //发送人姓名
				List list = messagingPlatformManager.findByHQL(" from NjhwUsersExp n where n.userid = ? ",(Long)getSession().getAttribute(Constants.USER_ID));
				
				HashMap map = new HashMap();
				if(StringUtil.isNotBlank(userSendMessageId)){
					map.put("userSendMessageId", Long.parseLong(userSendMessageId));
				}
				List<HashMap> listHashMap = messagingPlatformManager.findRecipientMessage(map);
				if(listHashMap.size()>0){
					HashMap hashMap = listHashMap.get(0);
				
				if(list.size()>0){
					NjhwUsersExp njhwUsersExp = (NjhwUsersExp)list.get(0);
					tCommonSmsBox.setReceivermobile(hashMap.get("TEL_NUM").toString()); //接收人手机号码
					tCommonSmsBox.setSendermobile(njhwUsersExp.getTelNum());//发送人手机号
					
					//调用发送短信接口
					SmsMessage sms = new SmsMessage();
				    String messagePhone = getParameter("messReceiverName");
					if(StringUtil.isNotBlank(njhwUsersExp.getTelNum())){
						sms.setPhoneNumber(njhwUsersExp.getTelNum());
					}
					if(StringUtil.isNotBlank(messagePhone)){
						sms.setPhoneNumber(messagePhone);
					}
					sms.setSendContent(message);
					smsSendMessage.send(sms);
					tCommonSmsBox.setSmstime(DateUtil.getSysDate());       //发送时间
					tCommonSmsBox.setContent(message);       //发送内容
					messagingPlatformManager.savePhoneMessage(tCommonSmsBox);
						}
					}
				}
				json.put("status", "true");
			}else if(isNoSave.equals("2")){
				//调用发送短信接口
				SmsMessage sms = new SmsMessage();
			    String messagePhone = getParameter("messReceiverName");//收件人电话
				if(StringUtil.isNotBlank(messagePhone)){
					sms.setPhoneNumber(messagePhone);
				}
				sms.setSendContent(message);
				smsSendMessage.send(sms);
			}
			} catch (Exception e) {
				json.put("status", "false");
				e.printStackTrace();
			}
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
			"no-cache:true");
			return null;
	}
	
	/**
	 * 
	* @title: findMessageButton 
	* @description: 短信发送平台--查找 按钮
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String findMessageButton() throws Exception{
		//JSONObject json = new JSONObject();
		try {
			String sendMessagePeople =""; //getParameter("messageName"); //得到的INPUT内的值
			Map localMap = ConvertUtils.pojoToMap(tCommonSmsBox);
			localMap.put("sendMessagePeople", sendMessagePeople);
			int p1 = 1;
			p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
					.valueOf(this.getRequest().getParameter("pageNo").trim());
			page.setPageNo(p1);
			page = messagingPlatformManager.findMessageButton(page, localMap);
			System.out.print(page.getPageSize());
			System.out.print(page.getResult().get(0));
			//json.put("page", page);
			
		} catch(Exception e){
			//json.put("page", "null");
			e.printStackTrace();
			
		}
		//Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
		//"no-cache:true");
		return SUCCESS;
	}
	
	
	public String findMessageButton1() throws Exception{
		//JSONObject json = new JSONObject();
		try {
			
			String sendMessagePeople =getParameter("messageName"); //得到的INPUT内的值
			//String pageNo =getParameter("pageNo");
			Map localMap = ConvertUtils.pojoToMap(tCommonSmsBox);
			localMap.put("sendMessagePeople", sendMessagePeople);
			int p1 = 1;
			p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
					.valueOf(this.getRequest().getParameter("pageNo").trim());
			page.setPageNo(p1);

			page = messagingPlatformManager.findMessageButton(page, localMap);
			//System.out.print(page.getPageSize());
			//System.out.print(page.getResult().get(0));
		
			
		} catch(Exception e){
		
			e.printStackTrace();
			
		}
	
		return SUCCESS;
	}

	/**
	 * 
	* @Title: orgUserToPhoneMess 
	* @Description: 组织机构-人员信息查询
	* @author SQS
	* @date 2013-5-17 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String orgUserToPhoneMess() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: deleteOutBoxMessages 
	* @Description: 点击一条进行查看
	* @author SQS
	* @date 2013-5-17 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String chickPhoneMessage(){
		String chickId = Struts2Util.getParameter("smid");//得到ID 
		try{
		TCommonSmsBox tCommonSmsBox = (TCommonSmsBox)messagingPlatformManager.findById(TCommonSmsBox.class, Long.parseLong(chickId));
		this.getRequest().setAttribute("tCommonSmsBox",tCommonSmsBox);
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * @throws Exception 
	* @Title: deleteOutBoxMessages 
	* @Description: 删除发件箱的的消息
	* @author SQS
	* @date 2013-5-17 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String deleteOutBoxMessages() throws Exception{	
		try {
			String ids = Struts2Util.getParameter("idNum");//得到ID   //得到ID串
			String messageIds[] = ids.split(",");
			for(int i=0;i<messageIds.length;i++){
				messagingPlatformManager.deleteOutBoxMessages(Long.parseLong(messageIds[i]));
				
				String sendMessagePeople =null; //得到的INPUT内的值
				Map localMap = ConvertUtils.pojoToMap(tCommonSmsBox);
				localMap.put("sendMessagePeople", sendMessagePeople);
				int p1 = 1;
				p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
						.valueOf(this.getRequest().getParameter("pageNo").trim());
				page.setPageNo(p1);

				page = messagingPlatformManager.findMessageButton(page, localMap);
				
				
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public MessagingPlatformManager getMessagingPlatformManager() {
		return messagingPlatformManager;
	}
	public void setMessagingPlatformManager(
			MessagingPlatformManager messagingPlatformManager) {
		this.messagingPlatformManager = messagingPlatformManager;
	}
	public SmsSendMessageService getSmsSendMessage() {
		return smsSendMessage;
	}
	public void setSmsSendMessage(SmsSendMessageService smsSendMessage) {
		this.smsSendMessage = smsSendMessage;
	}
	public TCommonSmsBox gettCommonSmsBox() {
		return tCommonSmsBox;
	}
	public void settCommonSmsBox(TCommonSmsBox tCommonSmsBox) {
		this.tCommonSmsBox = tCommonSmsBox;
	}
	public Page<TCommonSmsBox> getPage() {
		return page;
	}
	public void setPage(Page<TCommonSmsBox> page) {
		this.page = page;
	}
	
}
