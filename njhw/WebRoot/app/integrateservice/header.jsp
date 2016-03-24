<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<script type="text/javascript">
    function logout(){
    	var url ="<%=path%>/j_spring_security_logout" + '?t=' + Math.random();
    	window.open(url, "_self");
    }
    
    function returnFrontPage(){
    	var url = '<%=path%>/app/integrateservice/init.act';
    	window.location.href=url;
    }
    //返回上一级
    function gotoUrl(){
    	var url ="<%=path%>" + $('#urlButton').val();
    	window.location.href =  url;
    }
    //返回首页
    function goHome(){
    	var url = $("#urlHome").val();
    	window.location.href = url;
    }
    //加载是否有首页和上一级菜单
    $(document).ready(function(){
    	var urlValue = $("#urlButton").val();
    	if(urlValue != "" && urlValue != null && "1" != urlValue && "0" != urlValue){
    		$("#homeId").css('visibility','visible');
    		$("#prveId").css('visibility','visible');
    	}
	});
</script>
<style type="css/text">
.header_right a{
	cursor:position;
}
</style>
</head>
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
<div class="main_header1">
	<div style="float:left;width:400px;height:80px;cursor:pointer;" onclick="returnFrontPage();">&nbsp;</div>
	<!-- 接收返回首页连接 -->
	<input type="hidden" id="urlHome" value="<%=path %>/app/integrateservice/init.act" />
	<!-- 接收返回上一级菜单连接 -->
	<input type="hidden" id="urlButton" value="${empty param.gotoParentPath ? 0 : param.gotoParentPath}" />
  	<div class="header_right" style="width:435px;" id="headWidth">
  		<div style="width: 75px;float: left;"><a id="homeId" style="visibility:hidden;cursor:pointer;color:#9eadcc;background-image: url('<%=path%>/app/integrateservice/images/index_top.jpg');text-decoration: none;" onclick="goHome()">返回首页</a></div>
   		<div style="width: 118px;float: left;"><a id="prveId" style="visibility:hidden;cursor:pointer;color:#9eadcc;background-image: url('<%=path%>/app/integrateservice/images/index_tip.jpg');text-decoration: none;" onclick="gotoUrl()">返回上一级菜单</a></div>
 		<div style="width: 165px;float: left;"><a href="javascript:void(0);" onclick="showShelter('750','430','<%=path %>/common/userOrgMgr/personInfoChreach.act')" style="color:#9eadcc;background-image: url('<%=path%>/app/integrateservice/images/top_image.png');text-decoration: none;" title="${_username}">当前用户：<script type="text/javascript">var len = '${_username}';if(len.replace(/(^\s*)|(\s*$)/g, "").length > 6){var text = len.substring(0,6) + "..."}else{text = len}document.write(text);</script></a></div>
   		<div style="width: 75px;margin-top: 0px;float: right;text-align: right;"><a href="javascript:void(0);" onclick="logout();return false" style="color: #FFF;text-decoration: none;">退出登录</a></div>
		<p>&nbsp;</p><a id="gljId" style="margin:5px 0px 0 0;background-image:none;float:right;display:none;cursor:pointer;color:#FEC600;font-weight: bold;">管理局功能入口</a>
  		<a id="wuyeId" style="margin:5px 0px 0 0;background-image:none;float:right;display:none;cursor:pointer;color:#FEC600;font-weight: bold;">物业功能入口</a>
  		<a id="superAdminId" style="margin:5px 0px 0 0;background-image:none;float:right;display:none;cursor:pointer;color:#FEC600;font-weight: bold;">超级管理员功能入口</a>
    </div>
</div>
</html>