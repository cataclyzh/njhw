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
		<title>维修耗材--查看页面</title>
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
			<form action="" method="post" id="viewRepairCostForm"
				name="viewRepairCostForm">
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="winzard_show_from_right" width="10%">
								维修单号
							</td>
							<td width="35%">
								<input name="repairId" id="repairId" type="text"
									class="show_inupt" value="${repairCost.repairId}"
									readonly="readonly" />
							</td>
							<td class="winzard_show_from_right" width="10%">
								维修主题
							</td>
							<td width="35%">
								<input name="repairCostTitle" id="repairCostTitle" type="text"
									class="show_inupt" value="${repairCost.repairCostTitle}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="10%">
								报修人
							</td>
							<td width="34%">
								<input name="reportUserName" id="reportUserName" type="text"
									class="show_inupt" value="${repairCost.reportUserName}"
									readonly="readonly" />
							</td>
							<td class="winzard_show_from_right" width="10%">
								报修人单位
							</td>
							<td width="34%">
								<input name="orgName" id="orgName" type="text"
									class="show_inupt" value="${repairCost.orgName}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="10%">
								耗材设备
							</td>
							<td width="35%">
								<c:forEach items="${deviceList }" var="device">
									<c:if test='${repairCost.deviceCostId == device.deviceId }'>
										<input name="deviceName" id="deviceName" type="text"
											class="show_inupt" value="${device.deviceName}"
											readonly="readonly" />
									</c:if>
								</c:forEach>
							</td>
							<td class="winzard_show_from_right" width="10%">
								数量
							</td>
							<td width="34%">
								<input name="deviceCostNumber" id="deviceCostNumber" type="text"
									class="show_inupt" value="${repairCost.deviceNumber}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								 耗材时间
							</td>
							<td width="34%">
								<input name="repairCostTime" id="repairCostTime" type="text"
									class="show_inupt" value="${repairCost.repairCostTime}"
									readonly="readonly" />
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</body>
</html>
