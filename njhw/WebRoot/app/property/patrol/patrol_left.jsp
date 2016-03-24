<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>My JSP 'fax_left.jsp' starting page</title>

<%@ include file="/common/include/meta.jsp"%>
<script type="text/javascript" src="${ctx }/app/property/patrol/patrol_left1.js"></script>
<style type="text/css">

.main1_viewPatrolLine_select a{
		width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewPatrolLine_hover.jpg) left top no-repeat;
}

.main1_viewPatrolSchedule_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewPatrolSchedule_hover.jpg) left top no-repeat;
}
.main1_viewPositionCard_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewPositionCard_hover.jpg) left top no-repeat;
}

.main1_viewPatrolException_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewPatrolException_hover.jpg) left top no-repeat;
}

.main1_viewPatrolStick_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/patrolStick_hover.jpg) left top no-repeat;
}


.main1_viewHistory_select a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/viewPatrolHistoryLine_hover.jpg) left top no-repeat;
}
</style>
</head>

<body style="margin:0;padding:0;background: white;">
	<div class="main1_main2_left1">
		<ul>
			<li class="main1_viewPatrolLine_select"><a id="btn_viewPatrolLine" href="javascript:void(0);"></a></li>
			<li class="main1_viewPatrolSchedule"><a id="btn_viewPatrolSchedule" href="javascript:void(0);"></a></li>
			<li class="main1_viewPositionCard"><a id="btn_viewPositionCard" href="javascript:void(0);"></a></li>
			<li class="main1_viewPatrolException"><a id="btn_viewPatrolException" href="javascript:void(0);"></a></li>
			<li class="main1_viewPatrolStick"><a id="btn_patrolStick" href="javascript:void(0);"></a></li>
			<li class="main1_viewHistory"><a id="btn_histroryLine" href="javascript:void(0);"></a></li>
			<li class="main1_viewHistory"><a id="btn_getPatrolStickRecord" href="javascript:void(0);"></a></li>
		</ul>
	</div>
</body>
</html>
