<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: WXJ
  - Date: 2011-12-1 11:48:03
  - Description:莲花供应商满意度调查
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>莲花供应商满意度调查</title>	
	<%@ include file="/common/include/meta.jsp" %>	
	
</head>
<body topmargin="0" leftmargin="0" rightmargin="0"> 
	
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	using('messager', function () {
		$.messager.alert('提示信息','调查表提交成功，谢谢您的支持，请重新登录！','info',
	   function(){top.location.href = '${ctx}/j_spring_security_logout';});
	});	
</c:if>
<c:if test="${isSuc=='false'}">
	using('messager', function () {
		$.messager.alert('提示信息','调查表提交失败，请重新登录！','info',
	   function(){top.location.href = '${ctx}/j_spring_security_logout';});
	});	
</c:if>
</script>