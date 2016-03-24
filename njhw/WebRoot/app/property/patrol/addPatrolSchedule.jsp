<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
  - Author: ls
  - Date: 2010-11-20 17:14:25
  - Description: 消息发送页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>增加巡查排班</title>
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
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		
		<script src="${ctx}/app/property/patrol/js/addPatrolSchedule.js" type="text/javascript"></script>
		
		
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
		    var url = "${ctx}/app/pro/attendanceOrgTreeSelect.act";
		    $("#iframe-contact2").attr("src", url);
	    }
	    
	
        </script>
</head>

<body style="background: white;">
	<div class="main_border_01">
		<div class="main_border_02">增加巡查排班</div>
	</div>
	<div class="main_conter">
		
		<div class="main3_left_01" style="background:#F6F5F1">
			<form id="addPatrolScheduleForm" name="addPatrolScheduleForm" action="addPatrolSchedule.act" method="post">
				<input type="hidden" name="treeUserId" id="treeUserId" value="${receivers}" />
				<input type="hidden" name="nowDate" id="nowDate" value="${nowDate}" />
				<input type="hidden" name="nowTime" id="nowTime" value="${nowTime}" />
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="main_from_left"><font color="red">*</font>员工姓名</td>
						<td style="padding-right: 50px;"><s:textfield id="receiver" name="receiver" theme="simple" value="%{#request.receivers}"
								cssClass="main_input" size="70" readonly="true"
								cssStyle="width:98%;" /></td>																							
					</tr>
					<tr>
						<td class="main_from_left"><font color="red">*</font>巡查路线</td>
						<td style="padding-right: 50px;">
						    <select class="fkdj_from_input" id="patrolLineId" name="patrolLineId">
							    <option value="0">全部</option>
								<c:forEach items="${patrolLineList}" var="patrolLine">
								<option value="${patrolLine.patrolLineId }" <c:if test='${patrolLineId == patrolLine.patrolLineId }'> selected="selected" </c:if>>
								    ${patrolLine.patrolLineName }
								</option>
								</c:forEach>
							</select>
						</td>																							
					</tr>
					<tr>
					<td colspan="3">
					<table>
					<tr>
					
						<td class="main_from_left"><font color="red">*</font>当班时段</td>
						<td >
						<input type="text" style="width:260px" name="scheduleStartTime" id="scheduleStartTime" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="" maxlength="50"/><span style="color:#808080;">&nbsp;至</span></td>
							
							<td>
						<input type="text" style="width:260px" name="scheduleEndTime" id="scheduleEndTime" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="" maxlength="50"/>						
						</td>	
					
					
					</tr>
					
					</table>
					</td>
					</tr>
							<tr>
					<td colspan="3">
					<table>
					<tr>
					
						<td class="main_from_left"><font color="red">*</font>有效日期</td>
						<td >
						<input type="text" style="width:260px" name="scheduleStartDate" id="scheduleStartDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="" maxlength="50"/><span style="color:#808080;">&nbsp;至</span></td>
							
							<td>
						<input type="text" style="width:260px" name="scheduleEndDate" id="scheduleEndDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'scheduleStartDate\',{M:-0,d:0})}'})" 
							value="" maxlength="50"/>						
						</td>	
					
					
					</tr>
					
					</table>			     	
							</td>	
					</tr>
				
					
					<tr>
						<td valign="top" class="main_from_left">备注</td>
						<td style="padding-top:5px;padding-right: 50px;"><s:textarea
								name="scheduleDesc" id="scheduleDesc" theme="simple" cssClass="main_input" cols="70"
								rows="15" cssStyle="width:100%;" /></td>
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

<script type="text/javascript">
<c:if test="${isSuc=='true'}">
		    window.location.href="${ctx}/app/pro/patrolScheduleList.act";
</c:if>
<c:if test="${isSuc=='false'}">
	alert('${message}');
</c:if>
</script>