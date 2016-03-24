<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-04-03
- Description: 访客三维轨迹
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>访客三维轨迹</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript">
		document.domain = "laisi.com";
	    
	    var Device;
	    var DeviceDefaultOption = {
	        IsFrameMap: true
	    }
	    $(function() {
			var SWVisitorObj = new Object();
			var SWVisitorInfo = new Array();
			SWVisitorInfo.push("访客名称：", "${visitorInfo.VI_NAME}");
			SWVisitorObj.DeviceIds = "${points}";
			SWVisitorObj.Visitor = SWVisitorInfo;
			
			Device = frames['threeDimensional'].Init3D("Device", DeviceDefaultOption);
			Device.InitVisitorPoint(SWVisitorObj);
		});
	</script>
</head>
<body>
	<h:panel id="sanwei_panel" width="100%" title="三维视图">
	  	<iframe id="threeDimensional" name="threeDimensional" src="http://tuhui.laisi.com/test/index.htm" width="100%" height="580"></iframe>
	</h:panel>
</body>
</html>