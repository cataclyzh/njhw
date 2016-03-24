<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>My JSP 'header.jsp' starting page</title>
<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
body {
	font-size:12px;
}
</style>
<script type="text/javascript">
	function logout() {
		var url = '${ctx}/j_spring_security_logout' + '?t=' + Math.random();
		window.parent.open(url, "_self");
	}

	function returnFrontPage() {
		var url = '${ctx}/app/integrateservice/init.act';
		window.parent.location.href = url;
	}
	//返回上一级
	function gotoUrl() {
		//var url ="{ctx}" + $("#urlButton").val();
		var url = $("#urlHome").val();
		window.parent.location.href = url;
	}
	//返回首页
	function goHome() {
		var url = $("#urlHome").val();
		window.parent.location.href = url;
	}

	/*  //加载是否有首页和上一级菜单
	 window.onload = function load(){
	 	var urlValue = $("#urlButton").val();
	 	if(urlValue == "" || urlValue == null || "1" == urlValue || "0" == urlValue){
	 		$("#homeId").css('display','none');
	 		$("#prveId").css('display','none');
	 		$("#headWidth").css('width','260px');
	 	}
	 } */
</script>
</head>

<body>
	<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
	<div class="main_header1">
		<div style="float:left;width:400px;height:80px;cursor:pointer;"
			onclick="returnFrontPage();">&nbsp;</div>
		<!-- 接收返回首页连接 -->
		<input type="hidden" id="urlHome"
			value="${ctx}/app/integrateservice/init.act" />
		<!-- 接收返回上一级菜单连接 -->
		<input type="hidden" id="urlButton"
			value="${empty param.gotoParentPath ? 0 : param.gotoParentPath}" />
		<div class="header_right" style="width: 35%" id="headWidth">
			<a id="homeId"
				style="cursor:pointer;color:#9eadcc;background-image: url('${ctx}/app/integrateservice/images/index_top.jpg');text-decoration: none;"
				onclick="goHome()">返回首页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a> <a
				id="prveId"
				style="cursor:pointer;color:#9eadcc;background-image: url('${ctx}/app/integrateservice/images/index_tip.jpg');text-decoration: none;"
				onclick="gotoUrl()">返回上一级菜单 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			<a href="javascript:void(0)"
				onclick="window.top.showShelter1('750','430','${ctx}/common/userOrgMgr/personInfoChreach.act')"
				style="color:#9eadcc;background-image: url('${ctx}/app/integrateservice/images/top_image.png');text-decoration: none;"
				title="${_username}">当前用户：<script type="text/javascript">
					var len = '${_username}';
					if (len.replace(/(^\s*)|(\s*$)/g, "").length > 6) {
						var text = len.substring(0, 6) + "..."
					} else {
						text = len
					}
					document.write(text);
				</script>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</a> <a href="javascript:void(0);" onclick="logout();return false"
				style="color: #FFF;text-decoration: none;">退出登录</a>
		</div>
		<!--
		<div style="color: white;right:65px;top:60px;position: absolute; ">
			为保障您顺畅使用，请下载安装<a href="ca.zip" style="color: red;">根证书</a>
		</div>
		-->
	</div>
</body>
</html>
