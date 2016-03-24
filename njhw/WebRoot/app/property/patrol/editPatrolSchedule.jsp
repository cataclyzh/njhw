<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>编辑巡查排班</title>
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
		<script src="${ctx}/scripts/property/calendarCompare.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>

		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script src="${ctx}/app/property/patrol/js/editPatrolSchedule.js" type="text/javascript"></script>
		
	</head>
	<body style="background:#fff;">
	<div class="khfw_wygl">
	    <div class="bgsgl_border_left">
		    <div class="bgsgl_border_rig_ht">编辑巡查排班</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
			<form action="editPatrolSchedule.act" method="post" id="editPatrolScheduleForm" name="editPatrolScheduleForm" target="main_frame_right">
				<input type="hidden" id="scheduleId" name="scheduleId" value="${patrolSchedule.scheduleId}"/>
					<input type="hidden" name="nowDate" id="nowDate" value="${nowDate}" />
				<input type="hidden" name="nowTime" id="nowTime" value="${nowTime}" />
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
							  <font color="red">*</font>
								员工姓名
							</td>
							<td width="20%">
								<s:textfield name="userName" id="userName" readonly="true" cssClass="fkdj_from_input"/>
							</td>
							<td class="winzard_show_from_right" valign="top" width="8%">
								<font color="red">*</font>巡查路线
							</td>
							<td colspan="3">
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
							<td class="winzard_show_from_right" valign="top">
								<font color="red">*</font>当班时段
							</td>
							<td>
								<s:textfield cssClass="fkdj_from_input" name="scheduleStartTime" id="scheduleStartTime" onclick="WdatePicker({dateFmt:'HH:00:00'})"/>
							</td>
						    <td class="fkdj_name_lkf" style="text-align: center;">
						                 至
						    </td>
						    <td>
						        <s:textfield cssClass="fkdj_from_input" name="scheduleEndTime" id="scheduleEndTime" onclick="WdatePicker({dateFmt:'HH:00:00'})"/>						
						    </td>
						</tr>
						<tr>
						    <td class="winzard_show_from_right" width="12%">
							  <font color="red">*</font>有效日期
							</td>
							<td>
								<s:textfield cssClass="fkdj_from_input" name="scheduleStartDate" id="scheduleStartDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
							</td>
						    <td class="fkdj_name_lkf" style="text-align: center;">
						                      至
						    </td>
						    <td>
						        <s:textfield cssClass="fkdj_from_input" name="scheduleEndDate" id="scheduleEndDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>	
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								备注
							</td>
							<td colspan="3">
								<textarea name="scheduleDesc" style="width:98%;height:80px;overflow-y:auto"
									id="scheduleDesc" cols="90"
									rows="15">${patrolSchedule.scheduleDesc}</textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="energy_fkdj_botton_ls" style="margin-right:10px;">
				    <a class="fkdj_botton_right" href="javascript:void(0);" onclick="saveData()">提交</a>　
				</div>				
			</form>
			</div>
			</div>
		</div>
</body>
</html>
