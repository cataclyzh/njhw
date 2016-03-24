<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--
- Author: jitm
- Date: 2011-02-07
--%>
<html>
<head>
<title>首页导航</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script type="text/javascript">
function tasklist_widow(taskname,tasklink){
	window.parent.tasklist_widow(taskname,tasklink);
}
</script>
</head>
<body style="margin: 0 0 0 0;" >
	<s:iterator value="taskList" var="taskInfoModel">
		<div style="width:70%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 12px;">
			<span style="float: left;"> 
			
			<img src="../images/home_sxdslicon.gif" width="21" height="21" style="vertical-align: middle;"></img>
			<c:if test="${taskInfoModel.tasklink != ''}">
			<a href="javascript:void(0);" style="text-decoration:none;color: blue;"
					onclick="javascript:tasklist_widow('${taskInfoModel.labname}','${ctx}${taskInfoModel.tasklink}');">${taskInfoModel.taskname}</a>
			</c:if>
			<c:if test="${taskInfoModel.tasklink == ''}">
				${taskInfoModel.taskname}
			</c:if>
			</span> <span style="float: right;"> <a href="javascript:void(0);" style="text-decoration:none;color: blue;">${taskInfoModel.taskinfo}</a>
			</span>
		</div>
	</s:iterator>
</body>

</html>
<s:actionmessage theme="custom" cssClass="success" />
