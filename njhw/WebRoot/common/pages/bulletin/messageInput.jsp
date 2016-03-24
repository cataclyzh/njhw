<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
  - Author: ls
  - Date: 2010-11-20 17:14:25
  - Description: 消息发送页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发送新消息</title>
<%@ include file="/common/include/meta.jsp"%>



<style type="text/css">
textarea {
	width: 100%;
	font-size: medium;
}

input[type="text"] {
	height: 22px;
	width: 100%;
	font-size: medium;
	font-weight: normal;
}

#submitbutton {
	cursor:pointer;
}

#message_form label.error {
	color: red;
	font-size: small;
}
</style>

</head>

<body topmargin="0" leftmargin="0" rightmargin="0"
	style="background: white;">

	<div class="main_border_01">
		<div class="main_border_02">发通知</div>
	</div>
	<div class="main_conter">
		<div class="main3_left_01">
			<form id="message_form" action="msgBoxAction_save.act" method="post"
				autocomplete="off" enctype="multipart/form-data">
				<input type="hidden" name="treeUserId" id="treeUserId"
					value="${receivers}" />
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="main_from_left">接收人</td>
						<td style="padding-right: 50px;"><s:textfield name="receiver"
								id="receiver" theme="simple" value="%{#request.receivers}"
								cssClass="main_input" size="70" readonly="true"
								cssStyle="width:100%;" /></td>
					</tr>
					<tr>
						<td class="main_from_left">标题</td>
						<td style="padding-right: 50px;"><s:textfield name="title"
								theme="simple" maxlength="50" cssClass="main_input" size="70"
								cssStyle="width:100%;" /></td>
					</tr>
					<tr>
						<td valign="top" class="main_from_left">正文</td>
						<td style="padding-top:5px;padding-right: 50px;"><s:textarea
								name="content" theme="simple" cssClass="main_input" cols="70"
								rows="18" cssStyle="width:100%;" /></td>
					</tr>



					<tr>
						<td class="main_from_left">&nbsp;</td>
						<td style="padding-top: 8px;"><input name="send"
							class="input_button3" type="button" id="submitbutton"
							onclick="javascript: formSubmit();" />
					</tr>
				</table>
			</form>
		</div>

		<div style="width: 28%;float: left;margin-right: 10px;">
			<div class="clear"></div>
			<iframe id='iframe-contact' name='iframe-contact' width="100%"
				height="430px" scrolling="no" frameborder="0"></iframe>

			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>


	<%-- <script type="text/javascript"
		src="${ctx}/common/pages/message/js/jquery.validate.js"></script> --%>
	<script type="text/javascript"
		src="${ctx}/common/pages/message/js/messageInput.js"></script>

	<script type="text/javascript">
		<c:if test="${isSuc=='true'}">
		window.parent.showMessageBox("发送成功!");
		window.parent.frames["main_frame_right"].location.href = "msgBoxAction_querySenderbox.act";

		//easyAlert("提示信息", "发送成功!", "info", function() {closeEasyWin('winId');});
		</c:if>
		<c:if test="${isSuc=='false'}">
		window.parent.showMessageBox("发送失败!");

		//easyAlert("提示信息", "发送失败!", "error");
		</c:if>
	</script>
</body>
</html>
