<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript"></script>
<style type="text/css">
body{
	font-size: 10pt;
	color: #808080;
	background : white none repeat scroll 0% 0% 
}


/* .content{
	overflow:hidden;
	width:60px;
	text-overflow:ellipsis;
} */

</style>

<script type="text/javascript">
$(document).ready(function(){
	$("table tr td:first-child").css({ "width":"120px" });
	$("table tr td:last-child").css({ "width":"200px","text-align":"center"});
});
	
</script>
</head>
<body>
<fieldset>
	<legend>事假</legend>
<table>
	<tr><td>时间</td><td>审批人</td><td>事由</td></tr>
	<c:forEach items="${shijiaList}" var="shijia">
	<tr>
		<td>${shijia.time }</td>
		<td>${shijia.name }</td>
		<td class="content">${shijia.text }</td>
	</tr>
	</c:forEach>
</table>
</fieldset>
<fieldset>
	<legend>病假</legend>
<table>
	<tr><td>时间</td><td>审批人</td><td>事由</td></tr>
	<c:forEach items="${bingjiaList}" var="bingjia">
	<tr>
		<td>${bingjia.time }</td>
		<td>${bingjia.name }</td>
		<td class="content">${bingjia.text }</td>
	</tr>
	</c:forEach>
</table>
</fieldset>
<fieldset>
	<legend>公出</legend>
<table>
	<tr><td>时间</td><td>审批人</td><td>事由</td></tr>
	<c:forEach items="${gongchuList}" var="gongchu">
	<tr>
		<td>${gongchu.time }</td>
		<td>${gongchu.name }</td>
		<td class="content">${gongchu.text }</td>
	</tr>
	</c:forEach>
</table>
</fieldset>
<fieldset>
	<legend>其他</legend>
<table>
	<tr><td>时间</td><td>审批人</td><td>事由</td></tr>
	<c:forEach items="${otherList}" var="other">
	<tr>
		<td>${other.time }</td>
		<td>${other.name }</td>
		<td class="content">${other.text }</td>
	</tr>
	</c:forEach>
</table>
</fieldset>
<fieldset>
	<legend>刷卡异常</legend>
<table>
	<c:forEach items="${absentList}" var="absent">
		<tr>
			<td>时间</td>
			<td>${absent.time }</td>
		</tr>	
	</c:forEach>
</table>
</fieldset>
<fieldset>
	<legend>迟到</legend>
<table>
	<c:forEach items="${lateList}" var="late">
		<tr>
			<td>时间</td>
			<td>${late.time }</td>
		</tr>	
	</c:forEach>
</table>
</fieldset>
<fieldset>
	<legend>早退</legend>
<table>
	<c:forEach items="${leaveEarlyList}" var="early">
		<tr>
			<td>时间</td>
			<td>${early.time }</td>
		</tr>
	</c:forEach>
</table>
</fieldset>

</body>
</html>