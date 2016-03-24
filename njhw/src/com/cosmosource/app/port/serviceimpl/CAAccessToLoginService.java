package com.cosmosource.app.port.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.Users;
import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.base.service.BaseManager;



/**
 * @ClassName:CAAccessToLoginService
 * @Description：CA认证处理
 * @Author：hp 
 * @Date:2013-4-16
 */
public class CAAccessToLoginService extends BaseManager{
	
	private static final Log log = LogFactory.getLog(CAAccessToLoginService.class);
	private static final String SUCCESS = "1";//表示库中数据存在
	private static final String FAIL = "2";//表示库中无数据 
	
	private SmsSendMessageService smsSendMessage;
	
	/**
	* @Description：市民卡查询用户名密码登录
	* @Author：hp
	* @Date：2013-4-18
	* @param personCard
	* @return
	**/
	@SuppressWarnings("unchecked")
	public String searchAuthPersonCardLogin(String personCard){
		JSONObject json = new JSONObject();
		if(StringUtils.isBlank(personCard)){
			log.info(personCard+": 参数为空!");
			json.put("statusCode", FAIL);
			json.put("username", "");
			json.put("password", "");
		}else{
			Map map = new HashMap();
			map.put("personCard", personCard);
			List<Users> users = this.findListBySql("PortSQL.authPersonCardLogin", map);
			log.info("根据市民卡号查询出用户信息数："+users.size());
			if(users != null && users.size() >0){
				json.put("statusCode", SUCCESS);
				json.put("username", users.get(0).getLoginUid());
				json.put("password", users.get(0).getLoginPwd());
			}else{
				json.put("statusCode", FAIL);
				json.put("username", "");
				json.put("password", "");
			}
		}
		return json.toString();
	}
	
	/**
	* @Description：手机号查询用户名密码登录
	* @Author：hp
	* @Date：2013-4-18
	* @param phoneNo
	* @return
	**/
	@SuppressWarnings("unchecked")
	public String searchPhoneLogin(String phoneNo){
		JSONObject json = new JSONObject();
		if(StringUtils.isBlank(phoneNo)){
			log.info(phoneNo+": 参数为空!");
			json.put("statusCode", FAIL);
			json.put("username", "");
			json.put("password", "");
			json.put("validateCode","");
		}else{
			Map map = new HashMap();
			map.put("mobile", phoneNo);
			List<Users> users = this.findListBySql("PortSQL.authPhoneLogin", map);
			log.info("根据手机号查询出用户信息数："+users.size());
			if(users != null && users.size() ==1){
				String code = randomValidateCode();
				String valideCode = DigestUtils.md5Hex(code).toUpperCase();
				log.info("手机验证码加密信息："+valideCode);
				SmsMessage sms = new SmsMessage();
				sms.setPhoneNumber(phoneNo);
				sms.setSendContent(code);
				log.info(phoneNo + "的明文验证码为："+code);
				smsSendMessage.sendToQuarz(sms);
				json.put("statusCode", SUCCESS);
				json.put("username", users.get(0).getLoginUid());
				json.put("password", users.get(0).getLoginPwd());
				json.put("validateCode", valideCode);
			}else{
				json.put("statusCode", FAIL);
				json.put("username", "");
				json.put("password", "");
				json.put("validateCode","");
			}
		}
		return json.toString();
	}
	
	
	
	/**
	* @Description：生成手机验证码
	* @Author：hp
	* @Date：2013-4-18
	* @return
	**/
	public String randomValidateCode(){
		String validateCode = String.valueOf(Math.random()*1000000).substring(0,6);
		char[] c = validateCode.toCharArray();
		String result = "";
		if(isNumeric(validateCode) == true){
			if(c.length == 6){
				result = validateCode;
			}else if(c.length == 5){
				result =  "0"+validateCode;
			}else if(c.length == 4){
				result =  "00"+validateCode;
			}else if(c.length == 3){
				result =  "000"+validateCode;
			}else if(c.length == 2){
				result = "0000"+validateCode;
			}else{
				randomValidateCode();
			}
		}
		return result;
	}
	
	/**
	* @Description：判断字符串是否为数字
	* @Author：hp
	* @Date：2013-4-19
	* @param str
	* @return
	**/
	public static boolean isNumeric(String str){   
	    Pattern pattern = Pattern.compile("[0-9]*");   
	    return pattern.matcher(str).matches();      
	} 
	
	public static void main(String[] args) {
		System.out.println(CAAccessToLoginService.isNumeric("234"));
//		System.out.println((String.valueOf(Math.random()*1000000)).split(".")[0]);
	}

	public SmsSendMessageService getSmsSendMessage() {
		return smsSendMessage;
	}

	public void setSmsSendMessage(SmsSendMessageService smsSendMessage) {
		this.smsSendMessage = smsSendMessage;
	}
}
