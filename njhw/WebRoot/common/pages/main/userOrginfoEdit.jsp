<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: sjy
  - Date: 2010-12-23 11:48:03
  - Description: 修改个人信息页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>修改用户信息</title>	
	<%@ include file="/common/include/meta.jsp" %>	
		
	<script type="text/javascript">
		$(function() {
			$("#taxno").focus();
			var options={
				rules: {
					username:{
						required: true
					},
					phone:{
						required: true,
						isTelPhone:true
					},
					address:{
						required: true
					},
					email:{
						required: true,
						email: true
					}
				},
				messages: {
					username:{
						required: "请输入用户名称!"
					},
					phone:{
						required: " 请输入电话!"
					},
					address:{
						required: " 请输入联系地址!"
					},
					email:{
						required: " 请输入电子邮箱!",
						email: " 请输入合法电子邮箱!"
					}
				}
			}
						
			$("#editForm").validate(options);
		});
	</script>
	<style type="text/css">
		.textfield { 
			width:240px;
		}
	</style>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"> 
	<form id="editForm" action="update.act" method="post">		
		</br>
		<table align="center" border="0" width="600px" class="form_table">
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;用户名称：</td>
				<td><s:textfield name="username" theme="simple" maxlength="20" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr>
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;联系地址：</td>
				<td><s:textfield name="address" theme="simple" maxlength="100" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr>
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;联系电话：</td>
				<td><s:textfield name="phone" theme="simple" maxlength="12" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr>
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;电子邮箱：</td>
				<td><s:textfield name="email" theme="simple" maxlength="40" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr>
			<tr>
				<td class="form_label" colspan="2" style="color:red;" nowrap>&nbsp;&nbsp;请确认电子邮箱填写正确，当遗忘密码时，将发送重置密码邮件至此邮箱。</td>
			</tr>
			<tr>
				<td colspan="2" class="form_bottom">
					<a href="javascript:void(0);" class="easyui-linkbutton" id="submitbutton" iconCls="icon-ok" onclick="javascript:$('#editForm').submit();">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#editForm').resetForm();">重置</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

				</td>
			</tr>
		</table>
		<s:hidden name="status" value="0"></s:hidden>
	</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','修改用户成功!','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','修改用户失败!','error');
</c:if>
</script>