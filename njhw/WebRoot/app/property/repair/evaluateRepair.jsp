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
		<title>物业报修--回访页面</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
   		<script src="${ctx}/app/property/repair/js/evaluateRepair.js" type="text/javascript"></script>
		
	</head>
	<body style="background:#fff;">
		<div class="khfw_wygl">
			<form action="evaluateRepair.act" method="post" id="evaluateRepairForm" name="evaluateRepairForm" target="main_frame_right">
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
								维修单号
							</td>
							<td width="35%">
								<input name="repairId" id="repairId" type="text"
									class="show_inupt" value="${repair.repairId}"
									readonly="readonly"/>
							</td>
							<td class="winzard_show_from_right" width="12%">
								单位名称
							</td>
							<td width="35%">
								<input name="deviceName" style="width:86%;" id="deviceName" type="text"
									class="show_inupt" value="${repair.orgName}"
									readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								报修人
							</td>
							<td width="34%">
								<input name="reportUserName" id="reportUserName" type="text"
									class="show_inupt" value="${repair.reportUserName}"
									readonly="readonly"/>
							</td>
							<td class="winzard_show_from_right">
								电话号码
							</td>
							<td width="34%">
								<input name="reportUserTel" id="reportUserTel" type="text"
									class="show_inupt" style="width:86%;" value="${repair.reportUserTel}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								设备名称
							</td>
							<td width="35%">
								<c:forEach items="${deviceList }" var="device">
									<c:if test='${repair.deviceId == device.deviceId }'>
										<input name="deviceName" id="deviceName" type="text"
											class="show_inupt" value="${device.deviceName}"
											readonly="readonly" />
									</c:if>
								</c:forEach>
							</td>
							<td class="winzard_show_from_right">
								维修人
							</td>
							<td width="34%">
							    <input name="repairUserName" id="repairUserName" type="text"
									class="show_inupt" style="width:86%;" value="${repair.repairUserName}" readonly="readonly" />
							</td>
						</tr>
						<tr>
						    <td class="winzard_show_from_right">
								故障描述
							</td>
							<td colspan="3">
							    <div class="text_right_move">
									<textarea name="repairDetail" id="repairDetail"
										class="show_textarea" cols="80" rows="5" style="height:80px;overflow-y:auto" readonly="readonly">${repair.repairDetail}</textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								回执内容
							</td>
							<td colspan="3">
							    <div class="text_right_move">
									<textarea name="completeRepairReceipt" id="completeRepairReceipt"
										class="show_textarea" cols="80" rows="5" style="height:80px;overflow-y:auto" readonly="readonly">${repair.repairReceipt}</textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
							<font color="red">*</font>	满意程度
							</td>
							<td colspan="3">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<input type="radio" name="repairSatisfy"
												id="repairSatisfy" value="0" />
											非常满意
										</td>
										<td>
											<input type="radio" name="repairSatisfy"
												id="repairSatisfy" value="3" />
											满意
										</td>
										<td>
											<input type="radio" name="repairSatisfy"
												id="repairSatisfy" value="1" />
											一般
										</td>
										<td>
											<input type="radio" name="repairSatisfy"
												id="repairSatisfy" value="2" />
											不满意
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								客户评价
							</td>
							<td colspan="3">
								<textarea name="repairEvaluate" id="repairEvaluate"
								cols="91" rows="5" style="overflow-y:auto"></textarea>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		
		<!-- 
		<div id="buttonbox_evaluate" class="allbox_back">
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" onclick="saveData()">确定</a>
				<a class="fkdj_botton_right" href="javascript:cancelEvaluateRepair()">取消</a>
			</div>
		</div>
		 -->
		<div id="buttonbox_evaluate" class="allbox_back">
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			</div>
		</div>
		
		
		
	</body>
	<script type="text/javascript">
	    function evaluateRepair(){
	        $('#evaluateRepairForm').submit();
		    closeEasyWin("evaluateRepair");
	    }
	    
	    function cancelEvaluateRepair(){
	        $('#evaluateRepairForm').resetForm();
	    }
	</script>
</html>
