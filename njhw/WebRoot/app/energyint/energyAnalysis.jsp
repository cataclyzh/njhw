<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-29
- Description: 能耗分析
--%>

<html xmlns="http://www.w3.org/1999/xhtml" style="height: 100%; overflow: auto">
<head>
	<title>能耗分析</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link type="text/css" rel="stylesheet" href="${ctx}/app/portal/energyint/css/energyManage.css" />
	<script type="text/javascript">
		document.domain = "laisi.com";
	</script>
</head>

<body>
	<iframe id="prod_fenxi" name="prod_fenxi" src="http://product.laisi.com/mxw/FileAccess.aspx?&FileName=/reports/energy/support/fenxi.link&pwd=admin&uid=admin" width="100%" height="600"/>
</body>
</html>