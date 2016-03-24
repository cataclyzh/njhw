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
		<title>查看巡查排班</title>
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
			<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	</head>
	<body style="background:#fff;">
	<div class="khfw_wygl">
	    <div class="bgsgl_border_left">
		    <div class="bgsgl_border_rig_ht">查看巡查排班</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx" style="padding:10px;">
			<form action="viewPatrolSchedule.act" method="post" id="viewPatrolScheduleForm" name="viewPatrolScheduleForm" target="main_frame_right">
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
								员工姓名
							</td>
							<td width="35%">
								<s:textfield name="userName" id="userName" readonly="true" cssClass="show_inupt" disabled="disabled"/>
							</td>
							<td class="winzard_show_from_right" valign="top" width="8%">
								巡查路线
							</td>
							<td colspan="3">
								<s:textfield name="patrolLineName" id="patrolLineName" readonly="true" cssClass="show_inupt" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								当班时段
							</td>
							<td>
								<s:textfield cssClass="show_inupt" name="scheduleStartTime" id="scheduleStartTime" readonly="true" disabled="disabled"/>
							</td>
						    <td class="fkdj_name_lkf" style="text-align:center;">
						                 至
						    </td>
						    <td>
						        <s:textfield cssClass="show_inupt" name="scheduleEndTime" id="scheduleEndTime" readonly="true" disabled="disabled"/>						
						    </td>
						</tr>
						<tr>
						    <td class="winzard_show_from_right" width="12%">
							  有效日期
							</td>
							<td>
								<s:textfield cssClass="show_inupt" name="scheduleStartDate" id="scheduleStartDate" readonly="true" disabled="disabled"/>
							</td>
						    <td class="fkdj_name_lkf" style="text-align:center;">
						                      至
						    </td>
						    <td>
						        <s:textfield cssClass="show_inupt" name="scheduleEndDate" id="scheduleEndDate" readonly="true" disabled="disabled"/>	
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								备注
							</td>
							<td colspan="3">
								<textarea name="scheduleDesc" style="width:98%;height:80px;overflow-y:auto"
									id="scheduleDesc" cols="90"
									rows="15" class="show_textarea" readonly="readonly" disabled="disabled">${patrolSchedule.scheduleDesc}</textarea>
							</td>
						</tr>
					</table>
				</div>
			</form>
			</div>
			</div>
		</div>
</body>
</html>
