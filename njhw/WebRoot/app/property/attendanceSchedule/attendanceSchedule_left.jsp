<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>My JSP 'fax_left.jsp' starting page</title>
<%@ include file="/common/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/app/property/attendanceSchedule/attendanceSchedule_left.js"></script>
<style type="text/css">

.main1_viewAttendanceSchedule_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewAttendanceSchedule_hover.jpg) left top no-repeat;
}

.main1_addAttendanceSchedule_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/addAttendanceSchedule_hover.jpg) left top no-repeat;
}

.main1_viewAttendance_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewAttendance_hover.jpg) left top no-repeat;
}
</style>
</head>

<body style="margin:0;padding:0;background: white;">
	<div class="main1_main2_left1">
		<ul>
			<li class="main1_viewAttendanceSchedule_select"><a id="btn_viewAttendanceSchedule" href="#"></a></li>
			<li class="main1_addAttendanceSchedule"><a id="btn_addAttendanceSchedule" href="#"></a></li>
			<li class="main1_viewAttendance"><a id="btn_viewAttendance" href="#"></a></li>
		</ul>
	</div>
</body>
</html>
