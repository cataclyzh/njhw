<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>发消息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript">
	</script>
	<style type="text/css">
     html{
     	height:100%;
     }
    </style>
</head>
<body style="height:100%;">
	<div class="easyui-tabs" style="height:600px;">
		<div title="我的消息" style="padding: 20px;height:100%;">
			<iframe style="width:100%;height:100%" src="${ctx}/common/bulletinMessage/msgBoxAction_queryReceiverbox.act"></iframe>
		</div>
		<div title="已发消息" style="padding: 20px;height:100%;">
			<iframe style="width:100%;height:100%" src="${ctx}/common/bulletinMessage/msgBoxAction_querySenderbox.act"></iframe>
		</div>
		<div title="写消息" style="padding: 20px;height:100%;">
			<iframe style="width:100%;height:100%" src="${ctx}/common/bulletinMessage/msgBoxAction_init.act"></iframe>
		</div>
	</div>
</body>
</html>
