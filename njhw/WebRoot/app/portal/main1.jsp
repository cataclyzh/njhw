<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<%@ include file="/common/include/meta.jsp"%>
		<title>智慧政务社区信息系统</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/app/portal/css/main.css" />
		<style type="text/css">
		</style>
		<!-- 
		<script type="text/javascript" src="${ctx}/app/portal/js/main.js"></script>
		 -->
		<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/util.js"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/AjaxUtil.js"></script>
		<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>
		<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
		
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		//var loginurl = basePath+'?command=login&uid=${loginname}&pwd_md5=${loginpwd}&t=' + Math.random();
		//alert(loginurl);
		//document.getElementById("loginframe").src = loginurl;
		
		window.location.href = "${ctx}/app/integrateservice/init.act";
			//window.location.href = loginurl;
			
			//window.location.href = "${ctx}/app/integrateservice/init.act";
			
		</script>
	</head>
	<body>
	
	<!--
	<iframe  id="loginframe" style="position:absolute;display:none;"></iframe> 
	<jsp:include page="/app/portal/toolbar/toolbar.jsp"></jsp:include>
	<iframe id="mainPage" src="${ctx}/app/integrateservice/init.act"></iframe>
	-->
	
	</body>
</html>