<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-02 15:27:22
- Description: 权限管理框架页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/header-meta.jsp" %>
	<title>权限管理框架</title>
	<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	</script>
</head>
<body style="height:100%;width:100%;"  class="easyui-layout">
	<div region="east" title="权限信息" split="true" style="width:500px;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" name="ifrmMenuTree" id="ifrmMenuTree" 
		src="" style="width:100%;height:100%;padding:0px;"></iframe>
	</div>

	<div region="center" title="角色列表" style="padding:0px;background:#fafafa;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" name="ifrmRoleList" id="ifrmRoleList" 
		src="${ctx}/common/rightMgr/roleList.act?nodeId=<c:out value='${_orgRoot}'/>" style="width:100%;height:100%;padding:0px;"></iframe>
	</div>
</body>
</html>
