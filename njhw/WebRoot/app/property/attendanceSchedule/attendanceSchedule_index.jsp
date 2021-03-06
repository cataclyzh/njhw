<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp"%>
<title>考勤排班一览</title>

<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
<script type="text/javascript">
	var _ctx = '${ctx}';
</script>
<script src="${ctx}/app/portal/toolbar/showModel.js"
	type="text/javascript">
</script>

<style>
body {
	font-size: medium;
	/* font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
		"sans-serif"; */
}

p {
	padding: 10px;
}
.green {
	color: green;
}

.red {
	color: red;
}

.orange {
	color: orange;
}

.black {
	color: black;
}

.white {
	color: white;
}

.blue {
	color: blue;
}

.yellow {
	color: yellow;
}

.grey {
	color: grey;
}
.main_fax_container {
	width: 100%;
	border: 0;
	cellpadding: 0;
	cellspacing: 0;
	border-style: none;
	padding: 0;
	margin: 0;
	frame: void;
}

.main_fax_container_grid {
	width: 100%;
	border: 0;
	border-style: none;
	padding: 0;
	margin: 0;
}

.main_fax_left {
	width: 26%;
	float: left;
}

.main_fax_right {
	width: 73%;
	padding-right: 10px;
	float: left;
}

.modal {
	overflow: hidden;
	display: none;
	text-align: center;
	padding: 0;
}
</style>
</head>

<body style="margin:0;padding:0;">
	<div class="main_index">
				<c:if test="${param.type eq 'A'}">
					<jsp:include page="/app/integrateservice/headerWy.jsp">
					    <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
					</jsp:include>
				</c:if>
				<c:if test="${param.type ne 'A'}">
					<jsp:include page="/app/integrateservice/header.jsp">
					    <jsp:param value="/app/pro/propertyIndex.act" name="gotoParentPath"/>
					</jsp:include>
				</c:if>

		<div class="main_fax_left">
			<Iframe src="${ctx}/app/property/attendanceSchedule/attendanceSchedule_left.jsp" width="100%" scrolling="no"
				name="main_frame_left" id="main_frame_left" frameborder="0" height="800"></iframe>
		</div>

		<div class="main_fax_right">

			<Iframe src="${ctx}/app/pro/showAttendanceSchedulesList.act" width="100%" name="main_frame_right"
				id="main_frame_right" scrolling="auto" frameborder="0" height="800"></iframe>
		</div>
		<div>
			<Iframe src="${ctx}/app/property/attendanceSchedule/attendanceSchedule_footer.jsp" width="100%" height="103" scrolling="no"
				frameborder="0"></iframe>
		</div>

	</div>

	

	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>



</body>
</html>
