<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/include/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="css/attendance.css" />
<script src="js/attendance.js" type="text/javascript"></script>

</head>

<body>
	<div id="attendance_left">
		<ul>
			<li class="m1_select"><a id="btn_wodekaoqin" href="#"></a></li><!-- 我的考勤 -->
			<c:if test="${param.isNeedBumenkaoqin eq 1}">
				<li class="m2"><a id="btn_bumenkaoqin" href="#"></a></li><!-- 部门考勤 -->
			</c:if>
			<c:if test="${param.isNeedDanweikaoqin eq 1}">
				<li class="m5"><a id="btn_danweikaoqin" href="#"></a></li><!-- 单位考勤 -->
			</c:if>
			<c:if test="${param.isNeedXinChengkaoqin eq 1}">
				<li class="m6"><a id="btn_xinchengkaoqin" href="#"></a></li><!-- 大厦考勤 -->
			</c:if>
			<c:if test="${param.isNeedShenpi eq 1}">
				<li class="m3"><a id="btn_shenqingshenpi" href="#"></a><span id="shenpi_num"></span></li><!-- 申请审批 -->
			</c:if>
			<c:if test="${param.isNeedShenqing eq 1}">
				<li class="m4"><a id="btn_wodeshenqing" href="#"></a></li><!-- 我的申请 -->
				<li class="m7"><a id="btn_piliangshenqing" href="#"></a></li><!-- 批量申请 -->
			</c:if>
		</ul>
	</div>
	<intpu id="isNeedShenpi" type="hidden" value="${isNeedShenpi }" />
</body>
</html>
