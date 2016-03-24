<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/common/include/meta.jsp"%>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/property/css.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/property/block.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/styles/property/css_body.css" rel="stylesheet"
	type="text/css" />

<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
	type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<style type="text/css">
body {
	font-size: 10pt;
	color: #808080;
	background: white none repeat scroll 0% 0%
}

table label {
	border: none;
	background: #eeeff3;
	height: 25px;
	line-height: 25px;
	color: #808080;
	padding-left: 10px;
	float: left;
	width: 160px;
	margin: 2px 10px;
}

table textarea {
	border: none;
	background: #eeeff3;
	height: 25px;
	line-height: 25px;
	color: #808080;
	padding: 0 10px;
	float: left;
	width: 160px;
	height: 60px;
	margin: 2px 10px;
	font-size: 10pt;
	width: 350px;
	height: 80px;
	resize: none;
}

.pop_bottom {
	margin: 5px 0 5px 0;
	border-top: 1px solid #7F90B3;
	padding: 5px 0;
}

.pop_bottom a:hover {
	width: 110px;
	height: 32px;
	line-height: 32px;
	color: #fff;
	text-decoration: none;
	background: #ffc702;
	display: block;
	float: right;
	text-align: center;
	font-family: "微软雅黑";
	font-size: 16px;
	font-weight: bold;
	margin-left: 10px;
	cursor: pointer;
}

.pop_bottom a {
	width: 110px;
	height: 32px;
	line-height: 32px;
	color: #fff;
	text-decoration: none;
	background: #8090b2;
	display: block;
	float: right;
	text-align: center;
	font-family: "微软雅黑";
	font-size: 16px;
	font-weight: bold;
	margin-left: 10px;
	cursor: pointer;
}

.error {
	color: red;
}

.panel-title {
    color: #808080;
    float: left;
    font-size: 10pt;
    font-weight: bold;
    margin-top: 5px;
}
#selectDiv{
	float:right;
	padding-top:1px;
}
#selectDiv .property_query_button{
	line-height:20px;
}
</style>

<script type="text/javascript">


	function changeSel(ele) {
		if ($(ele).val() == '0') {
			$('#rejectA').hide();
			$('#rejectLine').hide();
			$('#passA').show();
		} else {
			$('#rejectA').show();
			$('#rejectLine').show();
			$('#passA').hide();
		}
	}

	$(document).ready(function() {
/* 		$("table tr td:first-child").css({
			"width" : "100px"
		}); */
		
		var status = "${status_checked}";
		if(status != ""){
			
			$("input[name=status_checked]").each(function(){
				
				if($(this).val() == status){
					$(this).attr("checked","checked");					
				}
				
			});
		}
		
		
	});
	
	function queryInfo(){
		
		//设置查询参数
		$("#status_hidden").val($("input[name=status_checked]:checked").val());
	    querySubmit();
		
	}
	
</script>
</head>
<body>
	<form id="queryForm" name="queryForm" action="getRealTimeDetail.act?scope=${scope }&personType=${personType}" method="post">
	
				
		<div id="selectDiv">
		
		<label style="margin:0 5px 0 0">状态</label>
		
		<input  name="status_checked" type="radio" value="ALL" checked="checked"/>全体
		<input  name="status_checked" type="radio" value="1"/>在单位
		<input  name="status_checked" type="radio" value="0"/>不在单位
		<input  name="status_checked" type="radio" value="2"/>未知
		
		<input id="status_hidden" name="status_hidden" type="hidden" value="${status_checked}" />
		
		<a style="margin:2px 0 0 15px" class="property_query_button" href="javascript:void(0);"
									onmousemove="this.style.background='#FFC600'"
									onmouseout="this.style.background='#8090b4'"
									onclick="queryInfo()">查询 </a>
		</div>

	<s:textfield name="page.pageSize" id="pageSize" value="10"  theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
	
	
	<h:page id="list_panel" width="100%" title="结果列表">
		<d:table name="page.result" id="row" uid="row" cellspacing="0"
			cellpadding="0" class="table">
			<d:col property="userName" class="display_centeralign" title="姓名" />
<%-- 			<d:col property="status" class="display_centeralign" title="状态" />
 --%>			<d:col property="recordTime" class="display_leftalign" title="最后刷卡时间" />
		</d:table>
	</h:page>
	</form>
	<%-- <form id="tt" name="tt">
<table id="tt1" width="100%" border="0" cellspacing="0" cellpadding="0">
<tr><td>开始时间:</td><td><label>${startTime }</label></td><td><label>${s1 }</label></td></tr>
<tr><td>结束时间:</td><td><label>${endTime }</label></td><td><label>${s2 }</label></td></tr>
<tr><td>请假类型:</td><td colspan="2"><label style="width:360px">${typeStr }</label></td></tr>
<tr><td>请假事由:</td><td colspan="2"><textarea rows="3" readonly="true">${userDesc }</textarea></td></tr>
<tr>
<td>审批意见:</td>
<td colspan="2">
<select style="width:100px; margin: 2px 10px" id="rejectSelect" onchange="changeSel(this)">
		<option value="0">审批通过</option>
		<option value="1">审批驳回</option>
	</select>
</td>
</tr>
<tr id="rejectLine" style="display:none"><td><span>退回理由:</span></td><td colspan="2"><textarea id="rejectText" name="rejectText" rows="3"></textarea></td></tr>
</table>
</form>
<div class="pop_bottom">
	<a id="rejectA" style="float: left; display: none" onclick="javascript:rejectApprove(${id})">确定</a>
	<a id="passA" style="float: left;" onclick="javascript:passApprove(${id})">确定</a>
	<!-- <a style="float: right;" onclick="parent.closeEasyWin('aaa')">关闭窗口</a>  -->
</div> --%>
</body>
</html>