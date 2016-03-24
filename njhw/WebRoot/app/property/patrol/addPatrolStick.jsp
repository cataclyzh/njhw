<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>绑定员工巡更棒</title>
<%@ include file="/common/include/meta.jsp"%>
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
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		
		<script src="${ctx}/app/property/patrol/js/addPatrolStick.js" type="text/javascript"></script>
		
        <script type="text/javascript">
	    $(function() {
		    $("#receiver").focus();
		    selectRecieveid();
		    // 控制父页面高度
		    $("#main_frame_left", window.parent.document).height(
				    $(".main_conter").height() + 100);
		    $("#main_frame_right", window.parent.document).height(
				    $(".main_conter").height() + 100);
	    });

	    function selectRecieveid() {
		    var url = "${ctx}/app/pro/attendanceOrgTreeSelect1.act";
		    $("#iframe-contact2").attr("src", url);
	    }
	    
	 
        </script>
</head>

<body style="background: white;">
	<div class="main_border_01">
		<div class="main_border_02">绑定员工巡更棒</div>
	</div>
	<div class="main_conter">
		
		<div class="main3_left_01" style="background:#F6F5F1">
			<form id="addPatrolStickForm" name="addPatrolStickForm" action="addPatrolStick.act" method="post">
				<input type="hidden" name="treeUserId" id="treeUserId" value="${receivers}" />
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="main_from_left"><font color="red">*</font>员工姓名</td>
						<td style="padding-right: 50px;">
						<s:textfield id="receiver" name="receiver" theme="simple" value="%{#request.receivers}" readonly="true"
								cssStyle="width:251px" /></td>																							
					</tr>
					<tr>
						<td class="main_from_left"><font color="red">*</font>巡更棒号</td>
						<td style="padding-right: 50px;">
						    <select name="sId">
						    	<option value="">请选择</option>
							    <c:forEach items="${grPatrolStickList}" var="grPatrolStick">
									<option value="${grPatrolStick.sId }">${grPatrolStick.sName }</option>							    	
							    </c:forEach>
						    </select>
						</td>																							
					</tr>
				</table>
			</form>
				<div class="energy_fkdj_botton_ls" style="margin-right:10px;">
				    <a class="fkdj_botton_right" href="javascript:void(0);" onclick="saveData()">提交</a>　
				    <a class="fkdj_botton_left" href="javascript:void(0);" onclick="clearInput()">重置</a>　
				    
				</div>				
		</div>
		<div style="width: 28%;float: left;margin-right: 10px;">
			<div class="clear"></div>
			<iframe id='iframe-contact2' name='iframe-contact2' width="100%"
				height="490px" scrolling="no" frameborder="0"></iframe>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
