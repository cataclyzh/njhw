/**
 * 
 */
package com.ptero.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author ptero
 * 
 */
public class ParkServlet extends HttpServlet {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9014930437799925127L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userId = req.getSession().getAttribute("_userid").toString();
		// 将所有请求转发
		String requestURI = req.getRequestURI();
		String newURL = "http://10.101.1.37:38080"
				+ requestURI.substring(requestURI.indexOf("/park"));
//		String newURL = "http://localhost:8087"
//				+ requestURI.substring(requestURI.indexOf("/park"));
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpMethod = new HttpPost(newURL);
		httpMethod.setHeader("userId", userId);
		httpMethod.setEntity(new InputStreamEntity(req.getInputStream(), req
				.getContentLength()));
		HttpResponse response = httpClient.execute(httpMethod);
		// String response = httpClient.execute(httpMethod,
		// new BasicResponseHandler());
		resp.setStatus(response.getStatusLine().getStatusCode());
		resp.getWriter().write(EntityUtils.toString(response.getEntity()));
		resp.flushBuffer();
	}
}