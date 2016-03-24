<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ include file="/common/include/meta.jsp"%>

<title>考勤</title>

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
	width: 18%;
	float: left;
}

.main_fax_right {
	width: 81%;
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
		<jsp:include page="header.jsp">
		    <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
		</jsp:include>

		<div class="main_fax_left">
			<Iframe src="${navPage}" 
				width="100%" scrolling="no" frameborder="0" height="600"
				name="main_frame_left" id="main_frame_left"></iframe>
		</div>

		<div class="main_fax_right">
			<Iframe src="${ctx}/app/attendance/wodekaoqin.jsp"" width="100%" name="main_frame_right"
				id="main_frame_right1" scrolling="auto" frameborder="0" height="600"></iframe>
		</div>
		<div>
			<Iframe src="${ctx}/app/property/attendanceSchedule/attendanceSchedule_footer.jsp" width="100%" height="103" scrolling="no"
				frameborder="0"></iframe>
		</div>

	</div>

	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
</body>
</html>
