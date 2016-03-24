<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=basePath %>/app/portal/toolbar/toolbar.js"></script>
<script type="text/javascript" src="<%=basePath %>/app/portal/toolbar/showModel.js"></script>


<!-- 修改密码 --> 
<div id='companyWin' class='easyui-window' collapsible='false' draggable="true" resizable="false"
	minimizable='false' maximizable='true'
	style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
	closed='true'>
	<iframe id='companyIframe' name='companyIframe'
		style='width: 100%; height: 100%;' frameborder='0'></iframe>
</div>
