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
		<title>设备--修改</title>
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
		<script src="${ctx}/app/property/device/js/deviceEdit.js" type="text/javascript"></script>

	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
				<div class="fkdj_lfrycx">
					<div class="winzard_show_from">
						<form id="editDeviceForm" name="editDeviceForm"
							action="editDevice.act" method="post" target="main_frame_right">
							<input name="deviceId" id="deviceId"
											class="fkdj_from_input" type="hidden" value="${device.deviceId}" />
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							    <tr>
									<td class="winzard_show_from_right" width="12%">
									<font color="red">*</font>设备编号
									</td>
									<td width="50%">
									   <input name="deviceNo" id="deviceNo"
											class="fkdj_from_input" type="text" value="${device.deviceNo}" />
									</td>
									<td class="winzard_show_from_right" width="12%">
									<font color="red">*</font>设备类型
									</td>
									<td>
										<select id="deviceTypeId" class="fkdj_from_input" name="deviceTypeId">
											<option value="0">
												请选择...
											</option>
											<c:forEach items="${deviceTypeList }" var="deviceType">
												<option value="${deviceType.deviceTypeId }"
													<c:if test='${device.deviceTypeId == deviceType.deviceTypeId }'> selected="selected" </c:if>>
													${deviceType.deviceTypeName }
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>

								<tr>
									<td class="winzard_show_from_right" width="12%">
									<font color="red">*</font>设备名称
									</td>
									<td width="50%">
										<input name="deviceName" id="deviceName"
											class="fkdj_from_input" type="text" value="${device.deviceName}" />

									</td>
									<td class="winzard_show_from_right" width="12%">
										厂家
									</td>
									<td width="50%">
										<input name="deviceCompany" id="deviceCompany"
											class="fkdj_from_input" type="text" value="${device.deviceCompany}" />

									</td>
								</tr>
								
								<tr>
									<td class="winzard_show_from_right" width="12%">
										序列号
									</td>
									<td width="50%">
										<input name="deviceSequence" id="deviceSequence"
											class="fkdj_from_input" type="text" value="${device.deviceSequence}"/>

									</td>
									<td class="winzard_show_from_right" width="12%">
										生产日期
									</td>
									<td width="50%">
										<input type="text" class="fkdj_from_input"
															name="deviceProduceTime" id="deviceProduceTime"
															onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
															value="${device.deviceProduceTime}" />
									</td>
								</tr>
								
								<tr>
									<td class="winzard_show_from_right" width="12%">
										购买日期
									</td>
									<td width="50%">
										<input type="text" class="fkdj_from_input"
															name="deviceBuyTime" id="deviceBuyTime"
															onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
															value="${device.deviceBuyTime}" />
									</td>
									<td class="winzard_show_from_right" width="12%">
										保修时间/月
									</td>
									<td width="50%">
										<input name="deviceWarrantyTime" id="deviceWarrantyTime"
											class="fkdj_from_input" type="text" value="${device.deviceWarrantyTime}" />

									</td>
								</tr>

								<tr>
									<td class="winzard_show_from_right" valign="top">
										描述
									</td>
									<td colspan="3">
										<div class="text_right_move">
											<textarea name="deviceDescribe" id="deviceDescribe"
											 cols="75" rows="5">${device.deviceDescribe}</textarea>
										</div>
									</td>
								</tr>
							</table>
							
							<!-- 
							<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_left" onclick="saveData()">确定</a>
									<a class="fkdj_botton_right" href="javascript:cancelEditDevice()">取消</a>
								</div>
							
							 -->
							
							<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_right" onclick="saveData()">确定</a>
								</div>
							
							
							
								
							<input name="nowDate" id="nowDate" value="${nowDate}" type="hidden" />
								
						</form>
					</div>
				</div>
			</div>
	</body>
	<script type="text/javascript">
	function editDevice() {
		$('#editDeviceForm').submit();
		closeEasyWin("editDevice");
	}
	function cancelEditDevice() {
		$('#editDeviceForm').resetForm();
	}
</script>
</html>