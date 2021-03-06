package com.cosmosource.app.port.serviceimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.cosmosource.base.service.SpringContextHolder;

@SuppressWarnings("serial")
public class CasLoginWayServlet extends HttpServlet{
	
	private static final String PERSONCARD = "personCard";
	private static final String AUTHTYPE = "authType";//验证登录类型
	private static final String AUTH_TYPE_LOGIN = "1";//普通登录
	private static final String AUTH_TYPE_PHONE_CODE="2";//手机号登录
	private static final String AUTH_TYPE_PERSON_CARD ="3";//市民卡登录
	private static final String AUTH_TYPE_CA = "4";//CA登录
	
	private static final Log log = LogFactory.getLog(CasLoginWayServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		this.doPost(request, response);
	}
	
	
	/**
	* @Description：多方式登录
	* @Author：hp
	* @Date：2013-4-18
	* @param request
	* @param response
	* @throws IOException
	* @throws ServletException
	**/
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
		CAAccessToLoginService cAAccessToLoginService = (CAAccessToLoginService) applicationContext.getBean("cAAccessToLoginService");
		
		SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) applicationContext.getBean("jdbcTemplate");
		jdbcTemplate.update("update NJHW_USERS_EXP t set t.UEP_MOBILE = '12345' where t.UEP_MOBILE = '18301005442'");
		
		String personCard = request.getParameter(PERSONCARD);
		String callbackparam = request.getParameter("callbackparam");
		String authType = request.getParameter(AUTHTYPE);
		String phoneNo = request.getParameter("phoneNo");
		String credentials = null;
		//用市民卡登录
		if(authType.equals(AUTH_TYPE_PERSON_CARD)){
			credentials = cAAccessToLoginService.searchAuthPersonCardLogin(personCard);
			log.info("用户市民卡登录，市民卡号为："+personCard+",登录时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		}
		//手机号登录
		if(authType.equals(AUTH_TYPE_PHONE_CODE)){
			credentials = cAAccessToLoginService.searchPhoneLogin(phoneNo);
			log.info("用户手机号登录，手机号为："+phoneNo+",登录时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		}
		response.setContentType("text/plain");
		response.addHeader("encoding:UTF-8", "no-cache:true");
		PrintWriter out = response.getWriter();
		out.write(callbackparam+"("+credentials+")");
		out.flush();
		out.close();
	}
}
