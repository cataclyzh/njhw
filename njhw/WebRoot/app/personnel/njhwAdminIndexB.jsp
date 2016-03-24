﻿﻿﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
<head>
<%@ include file="/common/include/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理局管理</title>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/personnel/css/njhw_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var selected = 7;
	$(document).ready(function(){
		//默认第一个左边显示入驻单位信息
		var url = "${ctx}/app/din/initNewWeekMenu.act";
		//var url = "${ctx}/app/per/rzdwFrame.act";
  			$("#njhwAdmin").attr("src",url);
  			$("#navi"+selected).addClass("selected");
	});
	function initNavi(i){
		$("#navi" + selected).removeClass("selected");
		selected = i;
		$("#navi" + selected).addClass("selected");
		var url = "";
		if (i == 7) {
			url = "${ctx}/app/din/initNewWeekMenu.act";
		}
		$("#njhwAdmin").attr("src",url);
	}
</script>
</head>
<body>
	<div class="main_index">
		<jsp:include page="/app/integrateservice/header.jsp"></jsp:include>
		<div style="height:720px;padding: 0px 10px 10px 10px;">
		<div style="width:232px;height:720px;background:#fff;padding:0px;overflow:auto;float:LEFT;">
			<a id="navi7" class="navi_button_a7" href="javascript:void(0);" onclick="javascript:initNavi(7)"></a>
		</div>
		<div class="index_bgs" style="width:82%;float:LEFT;height:100%;">
			<iframe scrolling="auto" frameborder="0" name="njhwAdmin" id="njhwAdmin" src="${ctx}/common/include/body.jsp" style="width:100%;height:100%;padding:0px;"></iframe>
		</div>
		</div>
		<jsp:include page="/app/integrateservice/footer.jsp" />
	</div>
</body>
</html>