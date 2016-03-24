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
		<title>查看员工定位卡</title>
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
		    <div class="bgsgl_border_rig_ht">查看员工定位卡</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
			<form action="viewPatrolPositionCard.act" method="post" id="viewPatrolPositionCardForm" name="viewPatrolPositionCardForm" target="main_frame_right">
			<s:hidden id="patrolPositionCardId" name="patrolPositionCardId"></s:hidden>
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
								员工姓名
							</td>
							<td width="35%">
								<s:textfield cssClass="show_inupt" name="userName" id="userName" readonly="true"/>
							</td>
							<td class="winzard_show_from_right" valign="top">
								部门名称
							</td>
							<td width="35%">
								<s:textfield cssClass="show_inupt" name="orgName" id="orgName" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								定位卡号
							</td>
							<td width="35%">
								<s:textfield cssClass="show_inupt" name="patrolPositionCardNo" id="patrolPositionCardNo" readonly="true"/>
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
