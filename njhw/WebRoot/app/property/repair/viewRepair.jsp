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
		<title>物业报修--查看页面</title>
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
	</head>
	<body style="background:#fff;">
		<div class="khfw_wygl">
			<form action="" method="post" id="viewRepairForm"
				name="viewRepairForm">
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="winzard_show_from_right" width="12%">
								维修单号
							</td>
							<td width="35%">
								<input name="repairId" style="width:86%;" id="repairId" type="text"
									class="show_inupt" value="${repair.repairId}"
									readonly="readonly" />
							</td>
							<td class="winzard_show_from_right" width="12%">
								单位名称
							</td>
							<td width="35%">
								<input name="deviceName" style="width:86%;" id="deviceName" type="text"
									class="show_inupt" value="${repair.orgName}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
								报修人
							</td>
							<td width="34%">
								<input name="reportUserName" style="width:86%;" id="reportUserName" type="text"
									class="show_inupt" value="${repair.reportUserName}"
									readonly="readonly" />
							</td>
							<td class="winzard_show_from_right" width="12%">
								电话号码
							</td>
							<td width="34%">
								<input name="reportUserTel" style="width:86%;" id="reportUserTel" type="text"
									class="show_inupt" value="${repair.reportUserTel}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
								设备名称
							</td>
							<td width="35%">
								<c:forEach items="${deviceList }" var="device">
									<c:if test='${repair.deviceId == device.deviceId }'>
										<input name="deviceName" style="width:86%;" id="deviceName" type="text"
											class="show_inupt" value="${device.deviceName}"
											readonly="readonly" />
									</c:if>
								</c:forEach>
							</td>
							<td class="winzard_show_from_right">
								维修人
							</td>
							<td width="34%">
								<input name="repairUserName" style="width:86%;" id="repairUserName" type="text"
									class="show_inupt" value="${repair.repairUserName}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								满意程度
							</td>
							<td colspan="3">
								<c:if test='${repair.repairSatisfy == "0"}'>
									<input name="repairSatisfy" id="repairSatisfy"
										type="text" class="show_inupt" value="非常满意"
										readonly="readonly" />
								</c:if>
								
								<c:if test='${repair.repairSatisfy == "1"}'>
									<input name="repairSatisfy" id="repairSatisfy"
										type="text" class="show_inupt" value="一般"
										readonly="readonly" />
								</c:if>
								
								<c:if test='${repair.repairSatisfy == "2"}'>
									<input name="repairSatisfy" id="repairSatisfy"
										type="text" class="show_inupt" value="不满意"
										readonly="readonly" />
								</c:if>
								
								<c:if test='${repair.repairSatisfy != "0" && repair.repairSatisfy != "1" && repair.repairSatisfy != "2"}'>
									<input name="repairSatisfy" id="repairSatisfy"
										type="text" class="show_inupt" value="未评价"
										readonly="readonly" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
								故障描述
							</td>
							<td colspan="3">
								<div class="text_right_move">
									<textarea name="repairDetail" id="repairDetail"
										class="show_textarea" style="height:80px;overflow-y:auto" cols="80" rows="15" readonly="readonly">${repair.repairDetail}</textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
								回执内容
							</td>
							<td colspan="3">
								<div class="text_right_move">
									<textarea name="completeRepairReceipt"
										id="completeRepairReceipt" style="height:80px;overflow-y:auto" class="show_textarea" cols="80"
										rows="15" readonly="readonly">${repair.repairReceipt}</textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								客户评价
							</td>
							<td colspan="3">
								<div class="text_right_move">
									<textarea name="repairEvaluate" id="repairEvaluate"
										class="show_textarea" cols="80" style="height:80px;overflow-y:auto" rows="15" readonly="readonly">${repair.repairEvaluate}</textarea>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</body>
</html>
