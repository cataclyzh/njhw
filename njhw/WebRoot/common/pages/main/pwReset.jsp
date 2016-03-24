<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: ls
  - Date: 2010-12-17 11:48:03
  - Description: 密码重置页面
  - update by sjy 页面校验 2010-12-23
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>密码重置</title>	
	<%@ include file="/common/include/meta.jsp" %>	
		<script type="text/javascript">		
		$(function() {
			$('body').bind('contextmenu', function() {
		    	return false;
		    });
			$('body').bind('paste', function() {
		    	return false;
		    });
			$("#content_form").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				rules: {
					newpw1:{
						required: true,
						minlength: 8,
						checkPass: true
					},
					newpw2:{
						required: true,
						equalTo: "#newpw1"
					},
					email:{
						required: true,
						email: true
					}
				},
				messages: {
					newpw1:{
						required: " 请输入新密码!",
						minlength: "新密码需要八位以上!"
					},
					newpw2:{
						required: " 请输入确认密码!"
					},
					email:{
						required: " 请输入电子邮箱!",
						email: " 请输入合法电子邮箱!"
					}
				},
				submitHandler: function(form) {
					$("#loadingdiv").show();
					$('a.easyui-linkbutton').linkbutton('disable');
					form.submit();
				}
			});
		});	
		function doSubmit(){
			$("#content_form").submit();	
		}
	</script>
	<style type="text/css">
		.textfield { 
			width:240px;
		}
	</style>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"> 

	<form id="content_form" action="mainConfirm.act" method="post"  autocomplete="off">
		<s:hidden name="userid" value="%{#session._userid}"/>
		<br><br><br><br>
		<table align="center" border="0" width="600px" class="form_table">
			<tr>
				<td class="form_label" colspan="2" style="color:red;font-size:16px;" nowrap>&nbsp;&nbsp;
				尊敬的用户您好：为了您的用户安全，初次登录本系统请修改密码后再操作。
			</tr>
			<tr>
				<td colspan="2" nowrap>&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td class="form_label" colspan="2" style="color:red;" nowrap>&nbsp;&nbsp;
				请确认电子邮箱填写正确，当遗忘密码时，将发送重置密码邮件至此邮箱。</td>
			</tr>
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;电子邮箱：</td>
				<td><s:textfield name="email" theme="simple" maxlength="40" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr>
			<tr>
				<td colspan="2" nowrap>&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td class="form_label" colspan="2" style="color:red;" nowrap>&nbsp;&nbsp;新密码长度8-16位之间，必须包含（数字、字母、下划线、特殊字符）两种以上。请不要右键粘贴。</td>
			</tr>
		<%--	<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;旧密码：</td>
				<td><s:password name="password" theme="simple" maxlength="16" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr> --%>
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;新密码：</td>
				<td><s:password name="newpw1" theme="simple" maxlength="16" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span>&nbsp;<!-- <img id="imgs" align="absmiddle" src="${ctx}/images/pwstrength/wu.gif"> --></td>
			</tr>
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;重复新密码：</td>
				<td><s:password name="newpw2" theme="simple" maxlength="16" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr>
			<tr>
				<td colspan="2" class="form_bottom">
					<a href="javascript:void(0);" class="easyui-linkbutton" id="submitbutton" iconCls="icon-ok" onclick="doSubmit();">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#content_form').resetForm();">重置</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/j_spring_security_logout" class="easyui-linkbutton" id="logoutbutton" iconCls="icon-reload" >注销</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="loadingdiv" class="icon-loding" style="display: none" >正在执行请稍后</span>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	using('messager', function () {
		$.messager.alert('提示信息','修改密码成功，请重新登录！','info',
	   function(){top.location.href = '${ctx}/j_spring_security_logout';});
	});	
</c:if>
<c:if test="${isSuc=='false'}">
	using('messager', function () {
		$.messager.alert('提示信息','修改密码失败！','info');
	});	
</c:if>
</script>