<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<title>My JSP 'fax_left.jsp' starting page</title>

<%@ include file="/common/include/meta.jsp"%>
<style type="text/css">

.main1_addconference_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/addConference_hover.jpg) left top no-repeat;
}

.main1_viewconference_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewConference_hover.jpg) left top no-repeat;
}
.main1_addConferencePackage_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/addPackage_hover.jpg) left top no-repeat;
}

.main1_viewConferencePackage_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewPackage_hover.jpg) left top no-repeat;
}
</style>
</head>

<body style="margin:0;padding:0;background: white;" scroll=no>
	<div class="main1_main2_left1">
		<ul>
			<li class="main1_addconference"><a id="btn_addconference" href="#"></a></li>
			<li class="main1_viewconference_select"><a id="btn_viewconference" href="#"></a></li>
			<li class="main1_addConferencePackage"><a id="btn_addConferencePackage" href="#"></a></li>
			<li class="main1_viewConferencePackage"><a id="btn_viewConferencePackage" href="#"></a></li>
		</ul>
	</div>
</body>

<script type="text/javascript" src="${ctx}/app/property/conference/conferenceManage_left.js"></script>
</html>
