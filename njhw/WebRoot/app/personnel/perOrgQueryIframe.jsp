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
	<title>人员机构信息查询</title>
	<%@ include file="/common/include/header-meta.jsp" %>
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

<body>
   <div class="main" style="width:800px;height: 1000px;border: 1px solid #ccc;">
        <div class="top" style="width:100%;height: 30px;border-bottom: 1px solid #ccc;">
            <div class="iframe1" onclick="changenow(this)" style="width:50px;cursor:pointer;line-height:30px;height: 30px;text-align:center;float: left;"><span style="font-size: 12px">组织</span></div>
            <div class="iframe2" onclick="changenow(this)" style="width:50px;margin-left:5px;cursor:pointer;line-height:30px;height: 30px;text-align:center;float: left;"><span style="font-size: 12px">人员</span></div>
        </div>
        <div id="main_main" class="main_main" style="width:1000px;height: 700px;">
            <iframe id="iframe1" src="${ctx}/app/personnel/unit/manage/getSonOrgs.act?orgId=${orgId}" frameborder="0" scrolling="no" style="width:100%;height: 100%;"></iframe>
            <iframe id="iframe2" src="${ctx}/app/personnel/unit/manage/getAllPerByOrg.act?orgId=${orgId}" frameborder="0" scrolling="no" style="display:none;width:100%;height: 100%;"></iframe>
        </div>
    </div>
   
</body>
</html>
