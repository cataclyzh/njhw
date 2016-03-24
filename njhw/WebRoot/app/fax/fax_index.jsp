<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">


<title>传真</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet"
	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />


<script type="text/javascript">
	var _ctx = '${ctx}';
</script>
<script src="${ctx}/app/portal/toolbar/showModel.js"
	type="text/javascript"></script>

<script type="text/javascript">
	function fun() {

		showGuide('800', '705', _ctx + '/app/guide/guide.html');
	}
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
	<!--  
	<h1 style="position: absolute;">
		<input type="button" value="大廈指南" onclick="fun();" />
	</h1>
-->
	<div class="main_index">
		<div>
			<!-- 存入返回上一级菜单的连接   如果没有上一级连接 可不存入数据     null "" 0 1 都是隐藏上一级菜单及首页菜单-->
			<!-- 存放上一级菜单连接的 样例：${ctx}/app/integrateservice/init.act -->
			<!-- name="gotoParentPath"  这个写法固定，不许自定义 ，如果name 不相同，则无法取到数据-->
			<%-- <jsp:include page="../../app/integrateservice/header.jsp">
				<jsp:param value="${ctx}/app/integrateservice/init.act"
					name="gotoParentPath" />
			</jsp:include> --%>
			<Iframe src="fax_header.jsp" width="100%" height="99" scrolling="no"
				frameborder="0"></iframe>
		</div>

		<div class="main_fax_left">
			<Iframe src="fax_left.jsp" width="100%" scrolling="no"
				name="main_frame_left" id="main_frame_left" frameborder="0"></iframe>
		</div>

		<div class="main_fax_right">

			<Iframe src="fax_inbox_list.jsp" width="100%" name="main_frame_right"
				id="main_frame_right" scrolling="auto" frameborder="0"></iframe>
		</div>
		<DIV class="clear"></DIV>
		<div>
			<Iframe src="fax_footer.jsp" width="100%" height="103" scrolling="no"
				frameborder="0"></iframe>
		</div>

	</div>
	<input type="hidden" id="hid_sendStatus" />
	<!-- 收件箱预览对话框 -->
	<div id="dialog-inbox-preview" class="modal" title="预览">

		<Iframe id="iframe-inbox-preview" src="fax_inbox_preview.jsp"
			width="425px" height="100%" scrolling="no" frameborder="0"></iframe>
	</div>
	<!-- 发件箱预览对话框 -->
	<div id="dialog-outbox-preview" class="modal" title="预览">

		<Iframe id="iframe-outbox-preview" src="fax_outbox_preview.jsp"
			width="425px" height="100%" scrolling="no" frameborder="0"></iframe>
	</div>
	<!-- iframe 对话框  -->
	<div id="dialog-show-iframe" class="modal" title="标题">

		<Iframe id="iframe-modal" src="" width="100%" height="100%"
			scrolling="no" frameborder="0"></iframe>
	</div>
	<!-- 通讯录编辑  -->
	<div id="dialog-contact-edit" class="modal" title="标题">

		<Iframe id="iframe-contact-edit" src="" width="100%" height="100%"
			scrolling="no" frameborder="0"></iframe>
	</div>
	<!-- 消息提醒 -->
	<div id="dialog-message" class="modal" title="消息提示">
		<p>content</p>
	</div>
	
	
	<!-- 添加传真收件人对话框 -->
	<div id="dialog-add-receiver" class="modal" title="添加收件人">

		<Iframe id="iframe-add-receiver" src="fax_add_receiver.jsp"
			width="425px" height="100px" scrolling="no" frameborder="0">
		</iframe>
	</div>
	
	<!-- 编辑传真收件人对话框 -->
	<div id="dialog-del-receiver" class="modal" title="编辑收件人">
		<Iframe id="iframe-del-receiver" src="fax_del_receiver.jsp"
			width="100%" height="100%" scrolling="auto" frameborder="0">
		</iframe>
	</div>





	<!-- 发改委代办事项导入 -->
	<div id="dialog-import-NDRC-do-list" class="modal" title="发改委代办事项导入">
		<form id="inputForm" action="upload.act" method="post"
			autocomplete="off" enctype="multipart/form-data">


			<table align="center" border="0"
				style="width: 80%;height: 100%; margin-top: 5%;">
				<tr>
					<td style="width: 25%;">上传文件：</td>
					<td><input name="file" type="file" style="width: 100%"
						id="file" /></td>
				</tr>
				<tr style="height: 40px;">
					<td colspan="2"><div id="progressbar" style="display: none;">
							<div class="progress-label">正在上传中,请等待......</div>
						</div></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;">
						<table style="width: 100%;">
							<tr>
								<td style="width: 50%;text-align: right;padding-right:50px;"><input
									type="button" onclick="saveData();" value="上传"
									style="width: 100px;" /></td>
								<td style="width: 50%;text-align: left;padding-left: 50px;"><input
									type="reset" id="resetbutton" value="重置" style="width: 100px;" /></td>
							</tr>
						</table>
					</td>

				</tr>
			</table>
		</form>

	</div>

	<!-- 大廈指南 -->
	<div id="dialog-guide" class="modal" title="大廈指南">
		<Iframe id="iframe-guide" src="../guide/index.html" width="100%"
			height="100%" scrolling="no" frameborder="0"> </iframe>
	</div>



	<script type="text/javascript" src="js/fax_index.js"></script>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>



</body>
</html>
