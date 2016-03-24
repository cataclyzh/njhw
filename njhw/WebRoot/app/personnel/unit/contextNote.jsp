<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link type="text/css" rel="stylesheet" href="css/tabMian.css" />
		<script type="text/javascript" src="js/tabMain.js"></script>
		<style type="text/css">
		.reportTips_tips1
{
    left:110px;
}
.reportTips_tips2
{
    left:110px;
}
.reportTips_tips3
{
    left:110px;
}
.main_right3_input input
{
    width:240px;
    height:22px;
    line-height:22px;
    background-color: #CCD3E4;
}
.search_span
{
    position:absolute;
    top:49px;
    left:330px;
}
.main_right3
{
    width:414px;
}
.address_list
{
    width:369px;
    right:30px;
}
.close_search
{
    right:110px;
}
		
.input_bg
 {
     background:url(images/search.png) 0 0 no-repeat;
    position: absolute;
    top: 46px;
    left: 22px;
    width: 356px;
    height: 26px;
 }
		</style>
	</head>
	<body>
	<!-- linkUrl = "${ctx}/app/integrateservice/publicBook.jsp?orgId=${_orgid}&type=tel"; -->
    <jsp:include page="/app/integrateservice/publicBook.jsp?orgId=${_orgid}&type=fax&smac=${smac}&stel=${stel}"></jsp:include>
	</body>
</html>