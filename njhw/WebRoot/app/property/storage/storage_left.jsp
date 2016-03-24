<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>My JSP 'fax_left.jsp' starting page</title>
<%@ include file="/common/include/meta.jsp"%>
<script type="text/javascript" src="${ctx }/app/property/storage/storage_left.js"></script>
<style type="text/css">

.main1_addDeviceType_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/addDeviceType_hover.jpg) left top no-repeat;
}

.main1_viewDeviceType_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewDeviceType_hover.jpg) left top no-repeat;
}
.main1_addDevice_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/addDevice_hover.jpg) left top no-repeat;
}
.main1_viewDevice_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewDevice_hover.jpg) left top no-repeat;
}
.main1_viewStorage_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewStorage_hover.jpg) left top no-repeat;
}
.main1_inOutStorage_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/inOutStorage_hover.jpg) left top no-repeat;
}
</style>
</head>
<body style="margin:0;padding:0;background: white;">
	<div class="main1_main2_left1">
		<ul>
			<li class="main1_addDeviceType"><a id="btn_addDeviceType" href="javascript:void(0);"></a></li>
			<li class="main1_viewDeviceType"><a id="btn_viewDeviceType" href="javascript:void(0);"></a></li>
			<li class="main1_addDevice"><a id="btn_addDevice" href="javascript:void(0);"></a></li>
			<li class="main1_viewDevice"><a id="btn_viewDevice" href="javascript:void(0);"></a></li>
			<li class="main1_viewStorage_select"><a id="btn_viewStorage" href="javascript:void(0);"></a></li>
			<li class="main1_inOutStorage"><a id="btn_inOutStorage" href="javascript:void(0);"></a></li>
		</ul>
	</div>
</body>
</html>
