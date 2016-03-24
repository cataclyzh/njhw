<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp"%>
<link href="${ctx}/app/integrateservice/css/wizard_css.css"
	rel="stylesheet" type="text/css" />
<title>查看联系人</title>
<style>
.text {
	width: 100%;
	border: none;
	overflow: hidden;
	vertical-align: middle;
	font: inherit;
}
</style>
<script type="text/javascript">
	function closeWin() {
		closeEasyWin('winId');
	}
</script>
</head>

<body style="background: white;">
	<div class="bgsgl_right_list_border" style="display: none;">
		<div class="bgsgl_right_list_left">个人详细信息</div>
	</div>
	<table align="center" border="0" width="100%" class="form_table"
		style="font-size:14px;">
		<tr>
			<td class="form_label" width="120px;"><font style="color: red">*</font>所属分组：
			</td>
			<td style="">${groupName }</td>
		</tr>
		<tr>
			<td class="form_label"><font style="color: red">*</font>姓名：</td>
			<td>${addEntity.nuaName}</td>
		</tr>
		<tr >
			<td class="form_label" style="padding-bottom: 8px;">昵称：</td>
			<td style="padding-bottom: 8px;">${addEntity.nuaName1}</td>
		</tr>
		<tr>
			<td class="form_label"><textarea class="text" style="text-align: right;">手机：</textarea></td>
			<td>
				<textarea class="text">${addEntity.nuaPhone}</textarea>
			</td>
		</tr>
		<tr>
			<td class="form_label"><textarea class="text" style="text-align: right;">住宅电话：</textarea></td>
			<td>
				<textarea class="text">${addEntity.nuaTel1}</textarea>
			</td>
		</tr>
		<tr>
			<td class="form_label"><textarea class="text" style="text-align: right;">办公电话：</textarea></td>
			<td>
				<textarea class="text">${addEntity.nuaTel2}</textarea>
			</td>
		</tr>
		<tr>
			<td class="form_label"><textarea class="text" style="text-align: right;">传真：</textarea></td>
			<td>
				<textarea class="text">${addEntity.nuaTel3}</textarea>
			</td>
		</tr>
		<tr>
			<td class="form_label"><textarea class="text" style="text-align: right;">邮箱：</textarea></td>
			<td>
				<textarea class="text">${addEntity.nuaMail}</textarea>
			</td>
		</tr>
	</table>

	<table align="center" border="0" width="100%" class="form_table"
		style="">
		<tr class="">
			<td colspan="4">				
					<a  class="fkdj_botton_right"
						href="javascript:void(0);" onclick="parent.closeIframe1('win');window.top.closeIframe();"
						id="closeBut">关闭</a>
			</td>
		</tr>
	</table>
</body>
</html>