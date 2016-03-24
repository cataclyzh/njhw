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
		<title>添加设备类型</title>
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
		<script src="${ctx}/app/property/deviceType/js/addDeviceType.js" type="text/javascript"></script>
		<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
		<div class="bgsgl_border_left">
				<div class="bgsgl_border_rig_ht">
					添加设备类型
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="fkdj_lfrycx">
					<div class="winzard_show_from">
						<form id="addDeviceTypeForm" name="addDeviceTypeForm"
							action="addDeviceType.act" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="winzard_show_from_right" width="12%">
										<font color="red">*</font>设备类型编号
									</td>
									<td width="50%">
										<input name="deviceTypeNo" id="deviceTypeNo"
											class="fkdj_from_input" type="text" />

									</td>
								</tr>
								
								<tr>
									<td class="winzard_show_from_right" width="12%">
										<font color="red">*</font>设备类型
									</td>
									<td width="50%">
										<input name="deviceTypeName" id="deviceTypeName"
											class="fkdj_from_input" type="text" />

									</td>
								</tr>
								<tr>
									<td class="winzard_show_from_right" valign="top">
										描述
									</td>
									<td colspan="1">
										<div class="text_right_move">
											<textarea name="deviceTypeDescribe" id="deviceTypeDescribe"
											 cols="80" rows="15"></textarea>
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
								<a class="fkdj_botton_right" style="cursor:pointer" onclick="saveData()">确定</a>
								<a class="fkdj_botton_left" style="cursor:pointer" onclick="clearInput()">重置</a>
								
							</div>
							
							
							
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	function addDeviceType() {
	    $('#addDeviceTypeForm').submit();
	}
	function cancelDeviceType() {
		 $('#addDeviceTypeForm').resetForm();
	}
</script>
</html>