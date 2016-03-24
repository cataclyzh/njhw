<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>投诉申请和处理</title>
<%@ include file="/common/include/meta.jsp"%>
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>

<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript"></script>	
<style>
body {
	font-size: medium;/* font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
		"sans-serif"; */
}
p {
	padding: 10px;
}
.main_left {
	width: 26%;
	float: left;
}
.main_right {
	width: 73%;
	padding-right: 10px;
	float: left;
}
.modal {
	overflow: hidden;
	display: none;
	text-align: center;
	padding: 0;
}
</style>
<script type="text/javascript">
	function selectRespondents() {
		var url = "${ctx}/app/visitor/frontReg/respondentsTreeSelectForIframe.act?state=all";
		var params = "";
		url += params;

		var width = 400;
		var left = (document.body.scrollWidth - width) / 2;
		$("#companyWin").show();
		$("#companyWin").window({

			title : '请选择投诉人',
			modal : true,
			shadow : false,
			closed : false,
			width : width,
			height : 600,
			top : 50,
			left : left
		});
		$("#companyIframe").attr("src", url);


	}
</script>
</head>

<body style="margin:0;padding:0;height:500px">
<div class="main_index">
	<c:if test="${param.type eq 'A'}">
		<jsp:include page="/app/integrateservice/headerWy.jsp">
		    <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
		</jsp:include>
	</c:if>
	<c:if test="${param.type ne 'A'}">
		<jsp:include page="/app/integrateservice/header.jsp">
		    <jsp:param value="/app/pro/propertyIndex.act" name="gotoParentPath"/>
		</jsp:include>
	</c:if>
	
  <div class="main_left">
    <iframe src="${ctx}/app/property/complaint/left.jsp" width="100%" scrolling="no" name="main_frame_left" id="main_frame_left" frameborder="0"></iframe>
  </div>
  <div class="main_right">
    <iframe src="showComplaints.act" width="100%" height="100%" name="main_frame_right" id="main_frame_right" scrolling="auto" frameborder="0"></iframe>
  </div>
  <div class="clear"></div>
  <div>
    <iframe src="${ctx}/app/property/complaint/footer.jsp" width="100%" height="103" scrolling="no" frameborder="0"></iframe>
  </div>
</div>
</body>
</html>
