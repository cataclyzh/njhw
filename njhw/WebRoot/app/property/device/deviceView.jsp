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
		<title>设备--查看</title>
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
						<form id="viewDeviceForm" name="viewDeviceForm"
							action="viewDevice.act" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="winzard_show_from_right" width="12%">
										设备编号
									</td>
									<td width="50%">
										<input type="hidden" id="deviceId" name="deviceId"
											value="${device.deviceId}">
										<label class="show_pop_fist">
											${device.deviceNo}
										</label>

									</td>
									<td class="winzard_show_from_right" width="12%">
										设备类型
									</td>
									<td>
										<c:forEach items="${deviceTypeList }" var="deviceType">
											<c:if
												test='${device.deviceTypeId == deviceType.deviceTypeId }'>
												<label class="show_pop_fist">
													${deviceType.deviceTypeName }
												</label>
											</c:if>

										</c:forEach>
									</td>
								</tr>

								<tr>
									<td class="winzard_show_from_right" width="12%">
										设备名称
									</td>
									<td width="50%">
										<label class="show_pop_fist">
											${device.deviceName }
										</label>
									</td>
									<td class="winzard_show_from_right" width="12%">
										厂家
									</td>
									<td width="50%">
										<label class="show_pop_fist">
											${device.deviceCompany }
										</label>
									</td>
								</tr>

								<tr>
									<td class="winzard_show_from_right" width="12%">
										序列号
									</td>
									<td width="50%">
										<label class="show_pop_fist">
											${device.deviceSequence }
										</label>

									</td>
									<td class="winzard_show_from_right" width="12%">
										生产日期
									</td>
									<td width="50%">
										<label class="show_pop_fist">
											${device.deviceProduceTime }
										</label>
									</td>
								</tr>

								<tr>
									<td class="winzard_show_from_right" width="12%">
										购买日期
									</td>
									<td width="50%">
										<label class="show_pop_fist">
											${device.deviceBuyTime }
										</label>
									</td>
									<td class="winzard_show_from_right" width="12%">
										保修时间/月
									</td>
									<td width="50%">
										<label class="show_pop_fist">
											${device.deviceWarrantyTime }
										</label>
									</td>
								</tr>

								<tr>
									<td class="winzard_show_from_right" valign="top">
										描述
									</td>
									<td colspan="3">
										<div class="text_right_move">
											<textarea name="deviceDescribe" id="deviceDescribe"
												class="show_textarea" cols="80" rows="5" style="height:80px;margin-bottom:10px;"
												readonly="readonly">${device.deviceDescribe}</textarea>
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