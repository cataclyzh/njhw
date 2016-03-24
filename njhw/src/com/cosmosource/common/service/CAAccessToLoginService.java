package com.cosmosource.common.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.transfer.serviceimpl.SendSmsMessageService;



/**
 * @ClassName:CAAccessToLoginService
 * @Description：CA认证处理
 * @Author：hp 
 * @Date:2013-4-16
 */
public class CAAccessToLoginService{
	
	private static final Log log = LogFactory.getLog(CAAccessToLoginService.class);
	private static final String SUCCESS = "1";//表示库中数据存在
	private static final String FAIL = "2";//表示库中无数据 
	
	private SendSmsMessageService smsMessage;
	
	/**
	* @Description：市民卡查询用户名密码登录
	* @Author：hp
	* @Date：2013-4-18
	* @param personCard
	* @return
	**/
	public String searchAuthPersonCardLogin(String personCard,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(StringUtils.isBlank(personCard)){
			log.info(personCard+": 参数为空!");
			json.put("statusCode", FAIL);
			json.put("username", "");
			json.put("password", "");
		}else{
			System.out.println("SELECT u.USERID as userid,u.LOGIN_PWD as loginPwd,u.LOGIN_UID as loginUid from NJHW_TSCARD t ,USERS u where t.USER_ID = u.USERID and t.CARD_ID = '"+personCard+"'");
			List<Map<String, Object>> users = getJdbcTemplate(request).queryForList("SELECT u.USERID as userid,u.LOGIN_PWD as loginPwd,u.LOGIN_UID as loginUid from NJHW_TSCARD t ,USERS u where t.USER_ID = u.USERID and t.CARD_ID = '"+personCard+"'");
			log.info("根据市民卡号查询出用户信息数："+users.size());
			if(users != null && users.size() >0){
				json.put("statusCode", SUCCESS);
				json.put("username", users.get(0).get("loginUid"));
				json.put("password", users.get(0).get("loginPwd"));
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
	public String searchPhoneLogin(String phoneNo,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(StringUtils.isBlank(phoneNo)){
			log.info(phoneNo+": 参数为空!");
			json.put("statusCode", FAIL);
			json.put("username", "");
			json.put("password", "");
			json.put("validateCode","");
		}else{
			List<Map<String, Object>> users = getJdbcTemplate(request).queryForList("SELECT t.USERID as userid,t.LOGIN_PWD as loginPwd,t.LOGIN_UID as loginUid from USERS t ,NJHW_USERS_EXP u where t.USERID = u.USERID and u.UEP_MOBILE = '"+phoneNo+"'");
			log.info("根据手机号查询出用户信息数："+users.size());
			if(users != null && users.size() ==1){
				String code = randomValidateCode();
				String valideCode = DigestUtils.md5Hex(code).toUpperCase();
				log.info("手机验证码加密信息："+valideCode);
				SmsMessage sms = new SmsMessage();
				sms.setPhoneNumber(phoneNo);
				sms.setSendContent("智慧政务系统平台登录的短信验证码为："+code);
				sms.setUuid(UUID.randomUUID().toString());
				log.info(phoneNo + "的明文验证码为："+code);
				smsMessage.send(sms);
				json.put("statusCode", SUCCESS);
				json.put("username", users.get(0).get("loginUid"));
				json.put("password", users.get(0).get("loginPwd"));
				json.put("validateCode", valideCode);
				log.info(users.get(0).get("loginUid")+"******"+users.get(0).get("loginPwd")+"********"+valideCode);
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
		String result = "";
		if(StringUtils.isNotBlank(validateCode)){
			char[] c = validateCode.toCharArray();
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
	
	
	/**
	* @Description： 得到spring的jdbctemplate，用来操作数据库
	* @Author：hp
	* @Date：2013-4-27
	* @param request
	* @return
	**/
	public static SimpleJdbcTemplate getJdbcTemplate(HttpServletRequest request){
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) context.getBean("jdbcTemplate");
		return jdbcTemplate;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(CAAccessToLoginService.isNumeric("234"));
//		System.out.println((String.valueOf(Math.random()*1000000)).split(".")[0]);
	}

	
	public SendSmsMessageService getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(SendSmsMessageService smsMessage) {
		this.smsMessage = smsMessage;
	}


}
