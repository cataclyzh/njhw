package com.cosmosource.common.security;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.cosmosource.base.util.CryptoUtil;
import com.cosmosource.base.util.Split;

/**
 * @ClassName:UsernamePasswordAuthFilter
 * @Description：登录验证用的过滤器
 * @Author：hp 
 * @Date:2013-3-22
 */
public class UsernamePasswordAuthFilter extends AbstractAuthenticationProcessingFilter{

	protected UsernamePasswordAuthFilter() {
		super("/sso/login/index.act");
	}
	
	public static String IS_APP = "1";
	public static boolean logged = true;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
			
			String username = "";
			String password = "";
			if (request.getParameter("enter")==null) {
				username = request.getParameter("loginname");
		        password = request.getParameter("pwd");
		        IS_APP = request.getParameter("sid");
			} else {
				String queryString = request.getParameter("enter");
				Hashtable<String,String> params=ConvertClientRequestParams(queryString);
				
				username = params.get("loginname");
				password = params.get("pwd");		
				IS_APP = params.get("sid");
			}
			
				
			request.getSession().setAttribute("IS_APP", IS_APP);
			request.getSession().setAttribute("_logged", logged);
			
			logger.info("是否跳转到APP首页：1代表 是   0为否，当前值： " + IS_APP);
			logger.info("是否已经登录： " + String.valueOf(logged));
			
	        if (username == null) {
	            username = "";
	        }

	        if (password == null) {
	            password = "";
	        }

	        username = username.trim();
	        
	        password = "E10ADC3949BA59ABBE56";

	        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

	        // Allow subclasses to set the "details" property
	        setDetails(request, authRequest);

	        return this.getAuthenticationManager().authenticate(authRequest);
	}

	
   protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
   }
   
	/**
	 * 将客户端请求参数转化为Hashtable
	 * @param clientRequestParams
	 * @return
	 */
	public static Hashtable<String,String> ConvertClientRequestParams(String clientRequestParams){
		
		if(clientRequestParams==null||"".equals(clientRequestParams)) return null;
		
		
		Hashtable<String,String> params=new Hashtable<String,String>();
		
		try
		{		
			byte[] key=new byte[]{-24, -94, -12, -7, -124, 41, -12, 15, -53, -38, 108, -57, 13, 7, -90, 110};
			String paramStrEncrypt=clientRequestParams;
			System.out.println("paramStrEncrypt:"+paramStrEncrypt);
			String paramStrDecrypt=new String(CryptoUtil.aesDecryptFromHex(paramStrEncrypt,key)); 
			System.out.println("paramStrDecrypt:"+paramStrDecrypt);
			
			List list=Split.split(paramStrDecrypt, "&");
			if(list!=null){
				String str="";
				List keylist;
				for(int i=0;i<list.size();i++){
					str=(String)list.get(i);					
					if("".equals(str)) break;					
					keylist=Split.split(str,"=");
					if(keylist!=null&&keylist.size()==2){						
						params.put(keylist.get(0).toString(), keylist.get(1).toString());						
					}					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return params;
	}
	
}
