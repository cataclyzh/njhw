<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<title>制作通卡</title>
		<%@ include file="/common/include/header-meta.jsp" %>
		<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
	</head>

	<body>
	<div class="easyui-tabs" align="center" style="width: 750px; margin-left: auto;margin-right: auto; height:460px;">
		<div title="读卡制作通卡" style="overflow:hidden;width:100%;height:100%;">
			<iframe id="iframe1" src="${ctx}/app/personnel/unit/playCardDetail.jsp?type=1" frameborder="0" scrolling="yes" style="width:100%;height: 100%;"></iframe>
		</div>
		
		<div title="输入市民卡号制作通卡" style="overflow:hidden;width:100%;height:100%;">
			<iframe id="iframe2" src="${ctx}/app/personnel/unit/playCardDetail.jsp?type=2" frameborder="0" scrolling="yes" style="width:100%;height: 100%;"></iframe>
		</div>
	</div>
	</body>
</html>