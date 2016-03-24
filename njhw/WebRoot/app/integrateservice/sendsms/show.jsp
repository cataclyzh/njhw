<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>查看短信</title>
		<script type="text/javascript">
			function closeWin() {
				closeEasyWin('winId');
			}
		</script>
	</head>

	<body>
		<table align="center" border="0" width="100%" class="form_table">
			<tr>
				<td class="form_label" width="120px;"> 发送手机： </td>
				<td>${smsBox.sendermobile }</td>
			</tr>
			<tr>
				<td class="form_label"> 接收人： </td>
				<td>${smsBox.receiver }</td>
			</tr>
			<tr>
				<td class="form_label"> 接收人手机： </td>
				<td >${smsBox.receivermobile }</td>
			</tr>
			<tr>
				<td class="form_label"> 发送时间： </td>
				<td>${fn:substring(smsBox.smstime, 0, 16)}</td>
			</tr>
			<tr>
				<td class="form_label"> 内容： </td>
				<td style="word-wrap:break-word;">${smsBox.content }</td>
			</tr>
			<tr>
				<td colspan="2" class="form_bottom">
					<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton"  onclick="closeWin()">确定</a>
				</td>
			</tr>
		</table>
	</body>
</html>
