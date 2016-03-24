package com.cosmosource.app.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.base.util.Constants;

public class AuthorFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try {
			if(req instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)req;
				HttpServletResponse response = (HttpServletResponse)resp;
				
				String contentPath = request.getContextPath();
				String path = request.getRequestURI();
				String requestPath = path.replace((contentPath), "");				
				
				if(requestPath.startsWith("/")){
					requestPath = requestPath.substring(1);
				}
				
				HttpSession httpSession = request.getSession(false);
				if(httpSession != null){
					WebApplicationContext ct = WebApplicationContextUtils.getWebApplicationContext(
							httpSession.getServletContext());
					DevicePermissionToAppService device = (DevicePermissionToAppService) ct.getBean("devicePermissionToApp");
					Object userIdObj = httpSession.getAttribute(Constants.USER_ID);
					if(userIdObj != null){
						String userId = userIdObj.toString();
						List<Map> allNavMenuList = device.queryAllNavMenu();
						List<String> allNavLinkList = getLinkList(allNavMenuList);
						
						List<Map> authNavMenuList = device.queryNavMenuByUserId(userId);
						List<String> authLink = getLinkList(authNavMenuList);
						
						if(!authLink.contains(requestPath) && isContain(allNavLinkList, requestPath)){
							String host = request.getContextPath();
							String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+host+"/";
							response.sendRedirect(basePath + "app/integrateservice/errorPage.act");					
						}else{
							execChain(req, resp, chain);
						}
					}else{
						execChain(req, resp, chain);
					}
				}else{
					execChain(req, resp, chain);
				}
			}
		} catch (Exception e) {
			execChain(req, resp, chain);
		}		
	}
	
	private void execChain(ServletRequest req, ServletResponse resp,
			FilterChain chain){
		try {
			chain.doFilter(req, resp);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
//			if(e instanceof java.lang.IllegalStateException){}else{
//				log.error(e.getMessage(),e);
//			}
		}
	}
	
	private List<String> getLinkList(List<Map> menuList){
		List<String> result = new ArrayList<String>();
		for(Map m : menuList){
			Object obj = m.get("LINK");
			if(obj != null){
				String link = (String) obj;
				if(link.trim().length() != 0){
					String s1 = link.trim();
					int index = s1.lastIndexOf("?");							
					if(index >=0 ){
						s1 = s1.substring(0, index);
					}							
					result.add(s1);
				}
			}
		}
		return result;
	}
	
	private boolean isContain(List<String> list, String value){
		for(String s : list){
			if(s.trim().equals(value.trim())){
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
