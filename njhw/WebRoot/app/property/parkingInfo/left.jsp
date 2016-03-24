<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<title>My JSP 'fax_left.jsp' starting page</title>

<%@ include file="/common/include/meta.jsp"%>
<style type="text/css">

.main1_totalConfigPacking_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/totalConfigPacking_hover.jpg) left top no-repeat;
}

.main1_viewConfigPacking_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/a_hover26.jpg) left top no-repeat;
}

.main1_totalConfigPacking_selects a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/a_hover27.jpg) left top no-repeat;
}

.main1_viewConfigPacking_selects a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/a_images27.jpg) left top no-repeat;
}
</style>
</head>

<body style="margin:0;padding:0;background: white;">
	<div class="main1_main2_left1">
		<ul>
			<% 
				String userId = request.getSession().getAttribute("_userid").toString();
				//out.print("userId: " + userId);
				//只有wyfwzxAdmin管理员可以管理车位数量
				//if(userId!=null && userId.trim().equals("118")){ 
				if(userId!=null && (userId.trim().equals("118") || userId.trim().equals("7627")) ){
			%>
				<li class="main1_totalConfigPacking"><a id="btn_totalConfigPacking" href="javascript:void(0);"></a></li>
				<li class="main1_viewConfigPacking"><a id="btn_viewConfigPacking" href="javascript:void(0);"></a></li>
			<% } %>
			<li class="main1_totalConfigPacking_selects"><a id="btn_viewConfigPackings" href="javascript:void(0);"></a></li>
		</ul>
	</div>

<script type="text/javascript" src="${ctx}/app/property/parkingInfo/js/left.js"></script>
</body>
</html>
