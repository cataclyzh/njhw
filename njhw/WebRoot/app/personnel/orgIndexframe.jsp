<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源管理框架页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@ include file="/common/include/header-meta.jsp" %>
<title>人员机构信息查询</title>
<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
<script type="text/javascript">
  function changenow(dom)
       {
           var id = dom.className;
           var iframeDom = document.getElementById("main_main").getElementsByTagName("iframe");
           for(var i = 0; i < iframeDom.length; i++)
           {
               if(iframeDom[i].id == id)
                   iframeDom[i].style.display = "block";
               else
                   iframeDom[i].style.display = "none";
           }
       }
</script>
</head>

<body style="background:#fff !important;">
	<div id="tt"  style="width:100%;height:470px;">	
		 <iframe id="iframe2" src="${ctx}/app/personnel/perInfoCheckin.act?orgId=${orgId}&opt=${opt}" frameborder="0" scrolling="yes" style="width:100%;height: 100%;"></iframe>
   	</div>
</body>
</html>
