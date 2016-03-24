<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>智慧政务社区信息系统</title>
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/styles/layout.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js.gzip" type="text/javascript"></script>
	<c:if test="${fn:length(_orgsubsys)==0}">
	<script src="${ctx}/common/pages/main/layout.js" type="text/javascript"></script>
	</c:if>
	<c:if test="${fn:length(_orgsubsys)>0}">
	<script src="${ctx}/common/pages/main/layoutnew.js" type="text/javascript"></script>
	</c:if>
	<%--<script type="text/javascript" src="${ctx}/common/pages/main/jquery.blockUI.js"></script> --%>
	<script type="text/javascript">
		/*function mainIndex(){
			window.location.href = "${ctx}/app/portal/index.act";
		}*/
		var  navIndex = "${_defaultpage}";
		if (navIndex == null || navIndex.length < 1){
			navIndex = "${ctx}/app/portal/index.act";
		} else {
			navIndex = "${ctx}" + navIndex;
		}
		function mainIndex(){
			window.location.href = navIndex;
		}
	</script>
</head>
<body onload="javascript:mainIndex();">

</body>
</html>