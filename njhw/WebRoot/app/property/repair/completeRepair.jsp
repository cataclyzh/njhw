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
		<title>物业报修--完成页面</title>
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
			<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
				<script src="${ctx}/app/property/repair/js/completeRepair.js" type="text/javascript"></script>
				<script src="${ctx}/app/property/repair/js/plusDevice.js" type="text/javascript"></script>
		
	</head>
	<body style="background:#fff;">
		<div class="khfw_wygl">
			<form action="completeRepair.act" method="post" id="completeRepairForm" name="completeRepairForm" target="main_frame_right">
				<div class="winzard_show_from">
				    <input type="hidden" id="reportUserId" name="reportUserId" value="${repair.reportUserId }"/>
				    <input type="hidden" id="repairUserId" name="repairUserId" value="${repair.repairUserId }"/>
				    <input type="hidden" id="reportUserOrg" name="reportUserOrg" value="${repair.reportUserOrg }"/>
				    <input type="hidden" id="repairTheme" name="repairTheme" value="${repair.repairTheme }"/>
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
								<input name="orgName" id="orgName" type="text"
									class="show_inupt" value="${repair.orgName}"
									readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="19%">
								报修人
							</td>
							<td width="34%">
								<input name="reportUserName" id="reportUserName" type="text"
									class="show_inupt" value="${repair.reportUserName}"
									readonly="readonly"/>
							</td>
							<td class="winzard_show_from_right" width="12%">
								电话号码
							</td>
							<td width="34%">
								<input name="reportUserTel" id="reportUserTel" type="text"
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
									class="show_inupt" value="${repair.repairUserName}" readonly="readonly" />
							</td>							
						</tr>
						<tr>
						    <td class="winzard_show_from_right" width="19%">
								故障描述
							</td>
							<td colspan="3">
								<div class="text_right_move">
									<textarea name="repairDetail" id="repairDetail"
										class="show_textarea" style="width:98%;height:80px;overflow-y:auto" cols="80" rows="5" readonly="readonly">${repair.repairDetail}</textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
								耗材
							</td>
							
							<td colspan="3">
							
									<table>
							<tr>
							
								<td width="24%">
								<select id="deviceTypeIdSelect" name="deviceTypeIdSelect"  class="fkdj_from_input"
									onchange="getDeviceByType()">
									<option value="0">
										请选择设备类型...
									</option>
									<c:forEach items="${deviceTypeList }" var="deviceType">
										<option value="${deviceType.deviceTypeId }"
											<c:if test='${deviceTypeIdSelect == deviceType.deviceTypeId }'> selected="selected" </c:if>>
											${deviceType.deviceTypeName }
										</option>
									</c:forEach>
								</select>
							</td>
							<td width="24%">
								<select id="deviceIdSelect" name="deviceIdSelect"  class="fkdj_from_input">
									<option value="0">
										请选择设备...
									</option>
								</select>
							</td>
							<td width="24%">
								<input type="text" style="line-height:30px" class="fkdj_from_input" name="deviceNumber"
									id="deviceNumber"
									onFocus="if (value =='--请输入设备数量--'){value =''}"
									onBlur="if (value ==''){value='--请输入设备数量--'}"
									value="--请输入设备数量--" />
							</td>
							
							
							
							</tr>
							</table>
							
							
							
							</td>
							<td width="16%">
								<a class="tables_bottm" id="CompletePlusButton"
									name="CompletePlusButton" onclick="plus()">添&nbsp;加</a>
							</td>
					
							
							
							
							
							
							
							
							
							
						
						</tr>
                        <tr>
                            <td class="winzard_show_from_right" width="12%">
								耗材情况
							</td>
							<td colspan="3">
							    <div id="deviceCostTotal">
                                    <table id="deviceCostTotalTable" class="fkdj_sxry comp_lete">
                                        <tr class="fkdj_sxry_brack keom_title_top">
                                            <td style="background:#e0e3ea;">材料名称</td>
                                            <td style="background:#e0e3ea;">数量</td>
                                            <td style="background:#e0e3ea;">操作</td>
                                        </tr>  
                                    </table>
							    </div>
							</td>
							</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								回执内容
							</td>
							<td colspan="3">
								<textarea name="completeRepairReceipt" style="width:98%;height:80px;overflow-y:auto"
									id="completeRepairReceipt" cols="90"
									rows="15"></textarea>
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		
		
		<!-- 
		<div id="buttonbox_complete" class="allbox_back">
		<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			</div>
		</div>
		 -->
		<div id="buttonbox_complete" class="allbox_back">
		<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			</div>
		</div>

		
	</body>
	
	<script type="text/javascript">
	function getDeviceByType() {
	var deviceTypeId = $("#deviceTypeIdSelect").val();
	if (deviceTypeId > 0) {
		$.ajax({
            type: "POST",
            url: "${ctx}/app/pro/getDeviceListByDeviceTypeId.act",
            data:{"deviceTypeId": deviceTypeId},
            dataType: 'json',
            async : false,
            success: function(json){
            	if (json.mapList != null) {
		    			$("#deviceIdSelect").empty();
		    			$("#deviceIdSelect").append("<option value=0>请选择设备...</option>");
						$.each(json.mapList,function(i){
							$("#deviceIdSelect").append("<option value="+json.mapList[i].DEVICE_ID+">"+json.mapList[i].DEVICE_NAME+"</option>"); 
						});
		    		}
             }
         });
         }
         }
	</script>
</html>
