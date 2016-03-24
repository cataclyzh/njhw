<%@ page contentType="image/jpeg" import="com.cosmosource.common.jasig.RandImgCreater" %>
	             
<%
	response.setHeader("Cache-Control","no-store");   //HTTP1.1
	response.setHeader("Pragma","no-cache"); //HTTP1.0
	response.setDateHeader("Expires",0); //Prevents catching at proxy server
	RandImgCreater rc = new RandImgCreater(response);
	String code = rc.createRandImage();
	session.setAttribute("code",code);
	response.flushBuffer();
	out.clear();
	out = pageContext.pushBody();
%>

                 