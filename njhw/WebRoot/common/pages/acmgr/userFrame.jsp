<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 12:21:10
- Description: 用户管理框架页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户管理框架</title>
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	</script>
</head>
<body style="height:100%;width:100%;"  class="easyui-layout">
	<div region="west" split="true" title="机构树" style="width:150px;height:auto;background:#fafafa;padding:0px;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" name="ifrmOrgTree" id="ifrmOrgTree"
		 src="${ctx}/common/userMgr/orgTree.act"
		border="0" marginheight="0" marginwidth="0" frameborder="0" 
		style="width:100%;height:100%;padding:0px;"></iframe>		
	</div>
	<div region="center" title="用户信息" style="padding:0px;background:#fafafa;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" name="ifrmUserList" id="ifrmUserList" 
		src="${ctx}/common/include/body.jsp" style="width:100%;height:100%;padding:0px;"></iframe>
	</div>
</body>
</html>
