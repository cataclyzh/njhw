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
		<title>设备类型--修改</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/block.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" />
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
	 <script src="${ctx}/app/property/deviceType/js/editDeviceType.js" type="text/javascript"></script>
		
	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
				<div class="fkdj_lfrycx">
					<div class="winzard_show_from">
						<form id="editDeviceTypeForm" name="editDeviceTypeForm"
							action="editDeviceType.act" method="post" target="main_frame_right">
							
					<input type="hidden" class="fkdj_from_input" id="deviceTypeId" name="deviceTypeId" value="${deviceType.deviceTypeId}">
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="winzard_show_from_right" width="12%">
									<font color="red">*</font>设备类型编号
									</td>
									<td class="winzard_show_from_right" width="50%" style="text-align: left;">
										<input type="text" class="fkdj_from_input" id="deviceTypeNo" name="deviceTypeNo" value="${deviceType.deviceTypeNo}">
								    </td>
								</tr>
								<tr>
									<td class="winzard_show_from_right"  width="12%">
									<font color="red">*</font>设备类型名称
									</td>
									<td class="winzard_show_from_right" width="50%" style="text-align: left;">
										<input type="text" class="fkdj_from_input" id="deviceTypeName" name="deviceTypeName" value="${deviceType.deviceTypeName}">
									</td>
								</tr>
								<tr>
									<td class="winzard_show_from_right" valign="top">
										描述
									</td>
									<td colspan="1">
										<div class="text_right_move">
											<textarea name="deviceTypeDescribe" id="deviceTypeDescribe"
											 cols="75" rows="15">${deviceType.deviceTypeDescribe }</textarea>
										</div>
									</td>
								</tr>
							</table>
							<!-- 
							<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_left" onclick="saveData()">确定</a>
									<a class="fkdj_botton_right"
										href="javascript:cancelDeviceType()">取消</a>
								</div>
							 -->
							
							<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_right" onclick="saveData()">确定</a>
								
								</div>
								
						</form>
					</div>
				</div>
			</div>
	</body>

	<script type="text/javascript">
	function editDeviceType() {
		$('#editDeviceTypeForm').submit();
		closeEasyWin("editDeviceType");
	}

	function cancelDeviceType() {
		$('#editDeviceTypeForm').resetForm();
	}
</script>
</html>