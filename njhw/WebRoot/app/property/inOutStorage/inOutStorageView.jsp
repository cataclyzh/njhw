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
		<title>出入库--查看</title>
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
						<form id="viewInOutStorageForm" name="viewInOutStorageForm"
							action="viewInOutStorage.act" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="winzard_show_from_right" width="15%">
										出入库记录编号
									</td>
									<td width="50%">
										<input type="hidden" id="inOutStorageId" name="inOutStorageId"
											value="${inOutStorage.inoutStorageId}">
										<label class="show_pop_fist">
											${inOutStorage.inoutStorageId}
										</label>

									</td>
									<td class="winzard_show_from_right" width="12%">
										设备名称
									</td>
									<td width="50%">
										<c:forEach items="${deviceList }" var="device">
											<c:if
												test='${inOutStorage.deviceId == device.deviceId }'>
												<label class="show_pop_fist">
													${device.deviceName }
												</label>
											</c:if>
										</c:forEach>
									</td>
								</tr>

								<tr>
									<td class="winzard_show_from_right">
										厂家
									</td>
									<td width="50%">
										<c:forEach items="${deviceList }" var="device">
											<c:if
												test='${inOutStorage.deviceId == device.deviceId }'>
												<label class="show_pop_fist">
													${device.deviceCompany }
												</label>
											</c:if>

										</c:forEach>
									</td>
									<c:if test='${inOutStorage.inoutStorageFlag == 0 }'>
										<td class="winzard_show_from_right">
											入库人
										</td>
									</c:if>
									<c:if test='${inOutStorage.inoutStorageFlag == 1 }'>
										<td class="winzard_show_from_right" width="12%">
											出库人
										</td>
									</c:if>
									<td width="50%">
										<label class="show_pop_fist">
											${inOutStorage.lenderUserName }
										</label>
									</td>
								</tr>
								
								<tr>
									<c:if test='${inOutStorage.inoutStorageFlag == 0 }'>
										<td class="winzard_show_from_right" width="12%">
											入库数量
										</td>
									</c:if>
									<c:if test='${inOutStorage.inoutStorageFlag == 1 }'>
										<td class="winzard_show_from_right" width="12%">
											出库数量
										</td>
									</c:if>
									<td width="50%">
										<label class="show_pop_fist">
											<c:if test='${inOutStorage.inoutStorageFlag == 0 }'>
											    ${inOutStorage.inoutStorageInNumber}
											</c:if>
											<c:if test='${inOutStorage.inoutStorageFlag == 1 }'>
											    ${inOutStorage.inoutStorageOutNumber}
											</c:if>
										</label>
									</td>
									<td class="winzard_show_from_right" width="12%">
										时间
									</td>
									<td width="50%">
										<label class="show_pop_fist">${inOutStorage.inoutStorageTime}</label>
									</td>
								</tr>
								
								<tr>
									<td class="winzard_show_from_right" width="12%">
										详情
									</td>
									<td colspan="3">
										<div class="text_right_move">
											<c:if test='${inOutStorage.inoutStorageFlag == 0 }'>
											    <textarea class="show_textarea" style="height:80px;" cols="80" rows="5" readonly="readonly">${inOutStorage.inoutStorageInDetail}</textarea>
											</c:if>
											<c:if test='${inOutStorage.inoutStorageFlag == 1 }'>
											    <textarea class="show_textarea" style="height:80px;" cols="80" rows="5" readonly="readonly">${inOutStorage.inoutStorageOutDetail}</textarea>
											</c:if>
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