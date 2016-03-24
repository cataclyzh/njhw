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
		<title>编辑员工定位卡</title>
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
		
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		
	
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

					<script src="${ctx}/app/property/patrol/js/editPositionCard.js" type="text/javascript"></script>
			

		
	</head>
	<body style="background:#fff;">
	<div class="khfw_wygl">
	    <div class="bgsgl_border_left">
		    <div class="bgsgl_border_rig_ht">编辑员工定位卡</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
			<form action="editPositionCard.act" method="post" id="editPositionCardForm" name="editPositionCardForm" target="main_frame_right">
			<s:hidden id="patrolPositionCardId" name="patrolPositionCardId"></s:hidden>
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
							  <font color="red">*</font>
								员工姓名
							</td>
							<td width="35%">
								<s:textfield name="userName" id="userName" cssClass="fkdj_from_input" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								<font color="red">*</font>
								定位卡号
							</td>
							<td colspan="3">
								 <input class="fkdj_from_input" name="cardNo" type="text" value="${cardNo }">
								 ${cardNo_error }
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
