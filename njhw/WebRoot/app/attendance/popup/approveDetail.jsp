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
<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<style type="text/css">
body{
	font-size: 10pt;
	color: #808080;
	background : white none repeat scroll 0% 0% 
}

table label{
	border:none;
	background:#eeeff3;
	height:25px;
	line-height:25px;
	color:#808080;
	padding-left:10px;
	float:left;
	width: 160px;
	margin:2px 10px;
}

table textarea{
	border:none;
	background:#eeeff3;
	height:25px;
	line-height:25px;
	color:#808080;
	padding:0 10px;
	float:left;
	width: 160px;
	height: 60px;
	margin:2px 10px;
	font-size: 10pt;
	width: 350px;
	height: 80px;
	resize: none;
}

.pop_bottom{
	margin: 5px 0 5px 0;
	border-top: 1px solid #7F90B3;
	padding: 5px 0;
}

.pop_bottom a:hover{
	width:110px;
	height:32px;
	line-height:32px;
	color:#fff;
	text-decoration:none;
	background:#ffc702;
	display:block;
	float:right;
	text-align:center;
	font-family:"微软雅黑";
	font-size:16px;
	font-weight:bold;
	margin-left:10px;
	cursor: pointer;
}

.pop_bottom a{
	width:110px;
	height:32px;
	line-height:32px;
	color:#fff;
	text-decoration:none;
	background:#8090b2;
	display:block;
	float:right;
	text-align:center;
	font-family:"微软雅黑";
	font-size:16px;
	font-weight:bold;
	margin-left:10px;
	cursor: pointer;
}

.error{
	color: red;
}
</style>

<script type="text/javascript">
function rejectApprove(id) {
	var rejectText = $("#rejectText").val();
	
	//if(!$("#tt").valid()){
	//	return;
	//}
	
	if(rejectText == ""){
		alert("请填写拒绝理由");
		$("#rejectText").focus();
		return;
	}
	
	var url = _ctx + "/app/attendance/rejectApprove.act";
	var pageNo=parent.document.getElementById("main_frame_right1").contentWindow.document.getElementById("pageNo").value;
	//alert(pageNo);
	//return;
	$.ajax({
		type : "POST",
		url : url,
		data : "id="+id+"&rejectText="+rejectText,
		dataType : 'json',
		async : false,
		success : function(json) {
			if(json.result == 'ok'){
				parent.document.getElementById("main_frame_right1").contentWindow.jumpPage(pageNo);
				parent.closeEasyWin('aaa');
			}else{
				alert(json.result);
			}
		},
		error : function(msg, status, e) {
			easyAlert("提示信息", "Ajax操作出错", msg);
		}
	});
}

function passApprove(id) {
	
	//获取顶级页面元素
	var dom = window.top.document.getElementById("main_frame_left");
	//转换为 jquery 对象
	var left_frame = $(dom);
	var count = left_frame.contents().find("#shenpi_num").html();
	
	if( count == 1){
		//取消 提示图标
		left_frame.contents().find("#btn_shenqingshenpi").css("background","url('images/qingjiashenpi_hover_normal.png')");
		
		left_frame.contents().find("#btn_shenqingshenpi").hover(
				function(){
					$(this).css("background","url('images/qingjiashenpi_normal.png')");
				},function(){
					$(this).css("background","url('images/qingjiashenpi_hover_normal.png')");
				});
		
		left_frame.contents().find("#shenpi_num").css("display","none");
		left_frame.contents().find(".m3_select").css("margin-bottom","0px");
	}
		left_frame.contents().find("#shenpi_num").html(count-1);
	
 	var url = _ctx + "/app/attendance/passApprove.act";
	var pageNo=parent.document.getElementById("main_frame_right1").contentWindow.document.getElementById("pageNo").value;
	//alert(pageNo);
	//return;
	$.ajax({
		type : "POST",
		url : url,
		data : "id="+id,
		dataType : 'json',
		async : false,
		success : function(json) {
			if(json.result == 'ok'){
				parent.document.getElementById("main_frame_right1").contentWindow.jumpPage(pageNo);
				parent.closeEasyWin('aaa');
				updateApproveCount();
			}else{
				alert(json.result);
			}
		},
		error : function(msg, status, e) {
			easyAlert("提示信息", "Ajax操作出错", msg);
		}
	}); 
}

function changeSel(ele){
	if($(ele).val()=='0'){
	    $('#rejectA').hide();
	    $('#rejectLine').hide();
	    $('#passA').show();
	}else{
		$('#rejectA').show();
		$('#rejectLine').show();
	    $('#passA').hide();
	    $("#rejectText").focus();
	}
}
	
$(document).ready(function() {
	$("table tr td:first-child").css({"width" : "100px"});
	
	/*
	$("#tt").validate({
		meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
		errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
		onsubmit: true,
		rules: {
			rejectText: {
			    required: true,
			    maxlength: 5
			}
		},
		messages:{
			rejectText: {
				required: "请填写退回理由",
				maxLength: "文本太长"
			}
		}
	});*/
});
</script>
</head>
<body>
<form id="tt" name="tt">
<table id="tt1" width="100%" border="0" cellspacing="0" cellpadding="0">
<tr><td>开始时间:</td><td><label>${startTime }</label></td><td><label>${s1 }</label></td></tr>
<tr><td>结束时间:</td><td><label>${endTime }</label></td><td><label>${s2 }</label></td></tr>
<tr><td>请假类型:</td><td colspan="2"><label style="width:360px">${typeStr }</label></td></tr>
<tr><td>请假事由:</td><td colspan="2"><textarea rows="3" readonly="true">${userDesc }</textarea></td></tr>
<%
	String locationLabel = String.valueOf(request.getAttribute("locationLabel"));
	String captureImg = String.valueOf(request.getAttribute("captureImg"));
	String locationUrl = String.valueOf(request.getAttribute("locationUrl"));
	
	if(!locationLabel.equalsIgnoreCase("null")){
		out.print("<tr><td>位置信息:</td><td colspan='2'><label style='width:360px'>"+locationLabel+"</label></td></tr>");
	}
	if(!captureImg.equalsIgnoreCase("null")){
		out.print("<tr><td>照片:</td><td colspan='2' style='padding-left:10px'>");
		out.print("<a href='"+locationUrl+"' target='_blank' >");
		out.print("<img style='width:370px;height:200px;' src="+locationUrl+"> </a></td></tr>");
	}
%>
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
</div>
</body>
</html>