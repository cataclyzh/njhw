package com.cosmosource.common.jasig;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class RandImgServlet extends HttpServlet{
	
	private static final Log log = LogFactory.getLog(RandImgServlet.class);
	
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
		log.info("access to system for validateCode ............");
		String img = (String) request.getSession().getAttribute("code");
		String validateCode = (String) request.getParameter("validateCode");
//		String callbackparam = request.getParameter("callbackparam");
		String flag = "true";
		response.setContentType("text/plain");
		response.addHeader("encoding:UTF-8", "no-cache:true");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(img.equals(validateCode.toUpperCase())){
			json.put("flag", flag);
			out.write(json.toString());
			//out.write(callbackparam+"("+json.toString()+")");
			out.flush();
			out.close();
		}else{
			flag = "false";
			json.put("flag", flag);
			out.write(json.toString());
			//out.write(callbackparam+"("+json.toString()+")");
			out.flush();
			out.close();
		}
	}
}
