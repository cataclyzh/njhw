<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">


<title>传真</title>
<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet"
	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
<style>
body {
	font-size: medium;
	/* font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
		"sans-serif"; */
}

p {
	padding: 10px;
}

.main_left {
	width: 26%;
	float: left;
}

.main_right {
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
<script type="text/javascript">
	function fun() {

		showMessageBox("传真服务器连接失败！",function(){
			window.location.href = "${ctx}/app/integrateservice/init.act";
		});
		//window.open("fax_index.html");
		//window.location.href = "${ctx}/app/integrateservice/init.act";
		//alert("传真服务器连接失败！");
	}
	
	function showMessageBox(content, func) {
	$("#dialog-message").find("p").css("font-family","Verdana,Arial,sans-serif").html(content);

	$("#dialog-message").dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		buttons : {

			"确定" : function() {				
				// $("#main_frame_right").contents().find(".main3_from").submit();
				$(this).dialog("close");
			},
			
		},
		open : function() {			
			$(".ui-button").blur();
		},
		close : function() {
			if (func != null)
				func();
		}
	});
}
</script>
</head>

<body onload="fun();">
	<div id="dialog-message" class="modal" title="消息提示">
		<p>content</p>
	</div>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>
</body>
</html>
