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
		<title>申请报修</title>
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
		<script src="${ctx}/app/property/repair/js/addRepair.js" type="text/javascript"></script>
			
	</head>
	<body style="background:#fff;">
		<div class="khfw_wygl">
		<div class="bgsgl_border_left">
				<div class="bgsgl_border_rig_ht">
					申请报修
				</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
			<form action="addRepair.act" method="post" id="addRepairForm"
				name="addRepairForm">
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="winzard_show_from_right" width="20%">
							<font color="red">*</font>	报修主题
							</td>
							<td colspan="3">
								<input id="repairTheme" name="repairTheme" type="text"
									class="fkdj_from_input" style="width:626px;" />
							</td>

						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
								<font color="red">*</font>	报修人
								<input type="hidden" name="userid" id="userid" value="" />
								<input type="hidden" name="orgId" id="orgId" value=""/>
								<input type="hidden" id="orgName" name="orgName" value=""/>
							</td>
							<td>
								<input type="text" class="fkdj_from_input" name="userName" id="userName" />
							</td>
							<td class="winzard_show_from_right" width="12%">
								电话号码
							</td>
							<td width="20%">
								<input id="phonespan" name="phonespan" type="text"
									class="fkdj_from_input"  value="${userExp.telNum}"/>
							</td>
							<td width="10%"></td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
								<font color="red">*</font>	报修人部门
							</td>
							<td>
								<input type="text" class="fkdj_from_input" name="userOrgName" id="userOrgName" />
							</td>
							<td class="winzard_show_from_right" width="12%">
							</td>
							<td width="20%">
							</td>
							<td width="10%"></td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" width="12%">
							<font color="red">*</font>	设备类型
							</td>
							<td width="20%">
								<select class="fkdj_from_input" id="deviceTypeId" name="deviceTypeId"
									onchange="getDeviceByType()">
									<option value="0">
										请选择...
									</option>
									<c:forEach items="${deviceTypeList }" var="deviceType">
										<option value="${deviceType.deviceTypeId }"
											<c:if test='${deviceTypeId == deviceType.deviceTypeId }'> selected="selected" </c:if>>
											${deviceType.deviceTypeName }
										</option>
									</c:forEach>
								</select>
							</td>
							<td class="winzard_show_from_right" width="19%">
							<font color="red">*</font>	设备名称
							</td>
							<td width="20%">
								<select class="fkdj_from_input" id="deviceId" name="deviceId">
									<option value="0">
										请选择...
									</option>
									<c:forEach items="${deviceList }" var="device">
										<option value="${device.deviceId }"
											<c:if test='${deviceId == device.deviceId }'> selected="selected" </c:if>>
											${device.deviceName }
										</option>
									</c:forEach>
								</select>
							</td>
							<td width="10%"></td>
						</tr>
						<tr>							
							<td class="winzard_show_from_right" valign="top">
								故障描述
							</td>
							<td colspan="3">
								<textarea id="repairDetail" name="repairDetail" cols="80"
									rows="15" style="overflow-y:auto"></textarea>
							</td>
						</tr>
					</table>
				</div>
				
				<!-- 
					<div class="energy_fkdj_botton_ls">
			<a class="fkdj_botton_left" onclick="saveData()">确定</a>
			<a class="fkdj_botton_right" href="javascript:cancelAddRepair()">取消</a>
		</div>
				 -->
				
				
					<div class="energy_fkdj_botton_ls">
			<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			<a class="fkdj_botton_left" href="javascript:clearInput()">重置</a>
			
		</div>
				
	
			</form>
			</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	    function addRepair(){
	        $('#addRepairForm').submit();
	    }
	    
	    function cancelAddRepair(){
	        $('#addRepairForm').resetForm();
	    }
	    
	    //根据设备类型，加载该设备类型的设备
function getDeviceByType() {
	var deviceTypeId = $("#deviceTypeId").val();
	if (deviceTypeId > 0) {
		$.ajax({
            type: "POST",
            url: "${ctx}/app/pro/getDeviceListByDeviceTypeId.act",
            data:{"deviceTypeId": deviceTypeId},
            dataType: 'json',
            async : false,
            success: function(json){
            	if (json.mapList != null) {
		    			$("#deviceId").empty();
		    			$("#deviceId").append("<option value=0>请选择...</option>");
						$.each(json.mapList,function(i){
							$("#deviceId").append("<option value="+json.mapList[i].DEVICE_ID+">"+json.mapList[i].DEVICE_NAME+"</option>"); 
						});
		    		}
             }
         });
	}
}

	//机构树
	function selectRespondents() {
		//window.parent.selectRespondents();
	}
	</script>
	

</html>
