<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

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
			$("#email").focus();
			var options = {
				rules: {
					email: {
						required: true
					},
					loginname: {
						required: true
					}
				},
				messages: {
					email: {
						required: " 请输入邮箱!"
					},
					loginname: {
						required: " 请输入用户名!"
					}
				},
				submitHandler: function(form) {
						form.submit();
						var obj=$("button");
						$.each(obj, function(i,item){
							$(this).attr("disabled","disabled");
						});				
				}
			}
			$("#queryForm").validate(options);
		});
		function doSend() {
			$("#email").val($("#email").val().replace(/\s/ig,''));
			$("#loginname").val($("#loginname").val().replace(/\s/ig,''));
			$("#queryForm").submit();
		}
		</script>
	<style type="text/css">
		.textfield { 
			width:240px;
		}
	</style>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"> 
	<br/><br/><br/>
	<form id="queryForm" action="sendEmail.act" method="post">
		<table align="center" border="0" width="600px" class="form_table">
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;邮箱：</td>
				<td><s:textfield name="email" theme="simple" maxlength="20" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span></td>
			</tr>
			<tr>
				<td class="form_label" width="160px" nowrap>&nbsp;&nbsp;用户名：</td>
				<td><s:textfield name="loginname" theme="simple" maxlength="16" cssClass="textfield"/><span style="color:red;">&nbsp;&nbsp;*</span>&nbsp;<!-- <img id="imgs" align="absmiddle" src="${ctx}/images/pwstrength/wu.gif"> --></td>
			</tr>
			<tr>
				<td colspan="2" class="form_bottom">
					<a href="javascript:void(0);" class="easyui-linkbutton" id="submitbutton" iconCls="icon-ok" onclick="javascript:doSend();">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	using('messager', function () {
		$.messager.alert('提示信息','${retMsg}','info',
	   function(){top.location.href='${ctx}/security/login.act';});
	});	
</c:if>
<c:if test="${isSuc=='false'}">
	using('messager', function () {
		$.messager.alert('提示信息','${retMsg}','info');
	});	
</c:if>
</script>