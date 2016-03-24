<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会话失效</title>
<link href="${ctx}/scripts/widgets/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js.gzip" type="text/javascript"></script>
<style type="text/css">
.fnt_Red {
	COLOR: #e60012;FONT-SIZE: 18px;
}
</style>
<script type="text/javascript"> 
  if (top != self) top.location.href = location.href;
  
</script>
<body >
	<div class="easyui-window" title="提示信息" collapsible="false" minimizable="false" maximizable="false" closable="false" style="width:500px;height:200px;padding:5px;background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<b class="fnt_Red">系统已经退出, 请重新登录。</b>
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="location.href='${ctx}/'">重新登录</a>
			</div>
		</div>
	</div>
</body>
</html>

