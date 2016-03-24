<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'send_success.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	function fun() {

		var status = document.getElementById("status").value;
		if (status == "200") {
			window.parent.showMessageBox("已发送到队列！");

		} else {
			window.parent.showMessageBox("发送失败！");

		}
		//window.open("fax_index.html");
		window.location.replace("fax_outbox_list.jsp");
	}
</script>
</head>

<body onload="fun();">
	<input type="hidden" id="status" value="${status }" />
</body>
</html>
