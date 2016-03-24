<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/include/meta.jsp"%>
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
<head>
<title>维修</title>


<script src="${ctx}/app/portal/toolbar/showModel.js"
	type="text/javascript"></script>

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
<script type="text/javascript">
	function selectRespondents() {
		var url = "${ctx}/app/visitor/frontReg/respondentsTreeSelectForIframe.act?state=all";
		var params = "";
		url += params;

		var width = 400;
		var left = (document.body.scrollWidth - width) / 2;
		$("#companyWin").show();
		$("#companyWin").window({

			title : '请选择报修人',
			modal : true,
			shadow : false,
			closed : false,
			width : width,
			height : 600,
			top : 50,
			left : left
		});
		$("#companyIframe").attr("src", url);


	}
</script>
</head>

<body style="margin:0;padding:0;">
	<div class="main_index">
		<jsp:include page="/app/integrateservice/headerWy.jsp">
		    <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
		</jsp:include>

		<div class="main_fax_left">
			<iframe src="${ctx }/app/property/repair/repair_left.jsp" width="100%" scrolling="no" height="750"
				name="main_frame_left" id="main_frame_left" frameborder="0"></iframe>
		</div>

		<div class="main_fax_right">

			<iframe src="${ctx }/app/pro/repairList.act" width="100%" name="main_frame_right" height="750"
				id="main_frame_right" scrolling="none" frameborder="0"></iframe>
		</div>
		<div class="clear"></div>
		<div>
			<iframe src="${ctx }/app/property/repair/repair_footer.jsp" width="100%" height="103" scrolling="no"
				frameborder="0"></iframe>
		</div>

	</div>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>
</body>
	<div id='companyWin' class='easyui-window' collapsible='false'
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<iframe id='companyIframe' name='companyIframe'
			style='width: 100%; height: 100%;' frameborder='0'></iframe>
	</div>
</html>
