<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<title>通知</title>
<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet"
	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
	
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript"></script>	
<style>
body {
	font-size: medium;/* font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
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
</head>
<body style="margin:0;padding:0;">
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
<div class="main_index">
  <div>
    <Iframe src="${ctx}/common/pages/message/header.jsp" width="100%"
				height="99" scrolling="no" frameborder="0"></iframe>
  </div>
  <div class="main_left">
    <Iframe src="${ctx}/common/pages/message/left.jsp" width="100%"
				scrolling="no" name="main_frame_left" id="main_frame_left"
				frameborder="0"></iframe>
  </div>
  <div class="main_right">
    <Iframe src="msgBoxAction_init.act" width="100%"
				name="main_frame_right" id="main_frame_right" scrolling="no"
				frameborder="0" height="auto"></iframe>
  </div>
  <DIV class="clear"></DIV>
  <div>
    <Iframe src="${ctx}/common/pages/message/footer.jsp" width="100%"
				height="103" scrolling="no" frameborder="0"></iframe>
  </div>
</div>
<input type="hidden" id="hid_sendStatus" />
<div id="dialog-message" class="modal" title="消息提示">
  <p>content</p>
</div>
<div id="dialog-message-detail" class="modal" title="阅读状态">
  <Iframe id="iframe-message-detail" src="" width="100%"
			height="100%" scrolling="auto" frameborder="0"> </iframe>
</div>
<!-- 收件箱显示对话框 -->
<div id="dialog-inbox-preview" class="modal" title="消息内容">
  <Iframe id="iframe-inbox-preview" src=""
			width="700px" height="326px" scrolling="no" frameborder="0"></iframe>
</div>
<!-- 回复消息 -->
<div id="dialog-reply-Msg" class="modal" title="回复消息">
  <Iframe id="iframe-reply-Msg" src="${ctx}/common/pages/message/replyMsg.jsp"
			width="700px" height="326px" scrolling="no" frameborder="0"></iframe>
</div>
<script type="text/javascript"
		src="${ctx}/common/pages/message/js/index.js"></script>
<script type="text/javascript"
		src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"></script>
</body>
</html>
