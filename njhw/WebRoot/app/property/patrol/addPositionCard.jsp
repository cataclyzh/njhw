<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
<title>绑定员工定位卡</title>
<%@ include file="/common/include/meta.jsp"%>

<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!-- 		
<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>  
<script type="text/javascript" src="${ctx}/app/property/patrol/js/addPositionCard.js"  />
-->
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" />
<script type="text/javascript" src="${ctx}/common/main/layoutnew.js" />
<script type="text/javascript" src="${ctx}/scripts/basic/common.js" />
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js" />
<script type="text/javascript" src="${ctx}/app/portal/js/AjaxUtil.js" />
<script type="text/javascript" src="${ctx}/app/property/patrol/js/addPositionCard.js"  />
<script type="text/javascript">
function selectRecieveid() {
	var url = "${ctx}/app/pro/attendanceOrgTreeSelect.act";
 	$("#iframe-contact2").attr("src", url);
}
$(document).ready(function(){
	document.write("11111111111");
	alert(222222222222222)
	$("#receiver").focus();
	selectRecieveid();
 	// 控制父页面高度
	$("#main_frame_left", window.parent.document).height($(".main_conter").height() + 100);
	$("#main_frame_right", window.parent.document).height($(".main_conter").height() + 100);
});
</script>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
textarea {
	width: 100%;
	font-size: medium;
}

input[type="text"] {
	height: 22px;
	width: 100%;
	font-size: medium;
	font-weight: normal;
}

#submitbutton {
	cursor:pointer;
}

#message_form label.error {
	color: red;
	font-size: small;
}
</style>
</head>

<body style="background: white;">
	<div class="main_border_01">
		<div class="main_border_02">绑定员工定位卡</div>
	</div>
	<div class="main_conter">
		
		<div class="main3_left_01" style="background:#F6F5F1">
			<form id="addPatrolPositionCardForm" name="addPatrolPositionCardForm" action="addPositionCard.act" method="post">
				<input type="hidden" name="treeUserId" id="treeUserId" value="${receivers}" />
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="main_from_left"><font color="red">*</font>员工姓名</td>
						<td style="padding-right: 50px;">
							<s:textfield id="receiver" name="receiver" theme="simple" value="%{#request.receivers}" readonly="true" cssStyle="width:251px" />
							${userIds_error}
						</td>																							
					</tr>
					<tr>
						<td class="main_from_left"><font color="red">*</font>定位卡号</td>
						<td style="padding-right: 50px;">
						    <input id="cardNo" name="cardNo" type="text" value="${cardNo }" style="width:251px"></input>
						    ${cardNo_error}
						</td>																							
					</tr>
				</table>
			</form>
				<div class="energy_fkdj_botton_ls" style="margin-right:10px;">
				    <a class="fkdj_botton_right" href="javascript:void(0);" onclick="ss()">提交</a>　
				    <a class="fkdj_botton_left" href="javascript:void(0);" onclick="cc()">重置</a>
				</div>				
		</div>
		<div style="width: 28%;float: left;margin-right: 10px;">
			<div class="clear"></div>
			<iframe id='iframe-contact2' name='iframe-contact2' width="100%"
				height="490px" scrolling="no" frameborder="0" 
				src="${ctx}/app/pro/attendanceOrgTreeSelect.act"></iframe>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
<script>
	function ss(){
		//if (showRequest()) {
		if($('#receiver').val() == ''){
			alert('请选择用户');
			return;
		}
		if($('#cardNo').val() == ''){
			alert('请填写卡号');
			return;
		}
	    $('#addPatrolPositionCardForm').submit();
		//}
	}
	
	function cc(){
		document.getElementById("receiver").value="";
	    document.getElementById("cardNo").value="";
	}
	
	function saveData(){		
		if (showRequest()) {
	        $('#addPatrolPositionCardForm').submit();
		}
	}

	function showRequest(){
		 return $("#addPatrolPositionCardForm").valid();
	}

	function clearInput(){
		  document.getElementById("receiver").value="";
	      document.getElementById("patrolPositionCardNo").value="";
	}
</script>