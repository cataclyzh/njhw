<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>智慧政务社区登录页</title>
<link href="${ctx}/scripts/widgets/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/jquery.form.js" type="text/javascript"></script>
<style type="text/css">
.fnt_Red {
	COLOR: #e60012;FONT-SIZE: 18px;
}
</style>
<script type="text/javascript"> 
  if (top != self) top.location.href = location.href;
  
</script>
<body >
	<div class="easyui-window" title="提示信息" collapsible="false" minimizable="false" maximizable="false" closable="false" style="width:500px;height:350px;padding:5px;background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<b class="fnt_Red">本系统不支持IE6及以下浏览器，请升级或更换浏览器后重新登录。<br>
				本系统支持的浏览器包括：
				<br><a href="http://windows.microsoft.com/zh-CN/internet-explorer/downloads/ie-8" target="_blank">IE7及以上版本</a>、
				<br><a href="http://firefox.com.cn/" target="_blank">Firefox(火狐)</a>、
				<!-- <br><a href="http://se.360.cn/" target="_blank">360浏览器</a>、 -->
				<br><a href="http://www.opera.com/browser/download/" target="_blank">Opera浏览器</a>、
				<br><a href="http://www.google.com/chrome?hl=zh-CN" target="_blank">谷歌浏览器(Google chrome)</a>、
				<!-- <br><a href="http://tt.qq.com/" target="_blank">QQ浏览器</a>、 -->
				<!-- <br><a href="http://ie.sogou.com/" target="_blank">搜狗浏览器</a>、 -->
				<!-- <br><a href="http://www.maxthon.cn/mx3/" target="_blank">傲游浏览器</a>、 -->等
				</b>
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
				<%-- <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="location.href='${ctx}/j_spring_security_logout'"></a>
			--%>
			</div>
		</div>
	</div>
</body>
</html>


