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
		<title>设备类型--查看</title>
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

	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
				<div class="fkdj_lfrycx">
					<div class="winzard_show_from">
						<form id="viewDeviceTypeForm" name="viewDeviceTypeForm"
							action="viewDeviceType.act" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="winzard_show_from_right" width="12%">
										设备类型编号
									</td>
									<td width="50%">
											<input id="deviceTypeNo" name="deviceTypeNo" readonly="readonly" type="text"
									class="show_inupt"  value="${deviceType.deviceTypeNo}"/>

									</td>
								</tr>
								<tr>
									<td class="winzard_show_from_right" width="12%">
										设备类型名称
									</td>
									<td width="50%">
											<input id="deviceTypeName" name="deviceTypeName" readonly="readonly" type="text"
									class="show_inupt"  value="${deviceType.deviceTypeName}"/>

									</td>
								</tr>
								<tr>
									<td class="winzard_show_from_right" valign="top">
										描述
									</td>
									<td width="50%">
										<div class="text_right_move">
											<textarea name="deviceTypeDescribe" id="deviceTypeDescribe" class="show_textarea"
											 cols="80" rows="15" readonly="readonly">${deviceType.deviceTypeDescribe}</textarea>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
	</body>
</html>