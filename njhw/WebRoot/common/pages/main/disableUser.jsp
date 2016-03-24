<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: WXJ
  - Date: 2012-4-17 12:40:03
  - Description: 用户停用提示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户停用提示页面</title>	
	<%@ include file="/common/include/meta.jsp" %>	
		<script type="text/javascript">		
		$(function() {
			$('body').bind('contextmenu', function() {
		    	return false;
		    });
			$('body').bind('paste', function() {
		    	return false;
		    });
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
				尊敬的用户您好：你的账号已经停用，如有问题请与客服人员或系统管理员联系。
			</tr>
			<tr>
				<td colspan="2" nowrap>&nbsp;&nbsp;</td>
			</tr>
			
			<tr>
				<td colspan="2" class="form_bottom">
					<a href="${ctx}/j_spring_security_logout" class="easyui-linkbutton" id="logoutbutton" iconCls="icon-reload" >注销</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
