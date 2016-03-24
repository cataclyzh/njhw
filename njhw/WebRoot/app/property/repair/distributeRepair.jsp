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
		<title>物业报修--派发页面</title>
		<%@ include file="/common/include/meta.jsp"%>
		<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
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
				<script src="${ctx}/app/property/repair/js/distributeRepair.js" type="text/javascript"></script>
		
	</head>
	<body style="background:#fff;">
		<div class="khfw_wygl">
			<form action="distributionRepair.act" method="post"
				id="distirbuteRepairForm" name="distirbuteRepairForm" target="main_frame_right">
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="8%">
								维修单号
							</td>
							<td width="35%">
								<input name="repairId" id="repairId" type="text"
									class="show_inupt" style="width:85%;"  value="${repair.repairId}"
									readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								单位名称
							</td>
							<td width="35%">
								<input name="deviceName" id="deviceName" type="text"
									class="show_inupt" style="width:85%;"  value="${repair.orgName}"
									readonly="readonly"/>
							</td>
							<td class="winzard_show_from_right" width="14%">
								报修人
							</td>
							<td width="34%">
								<input name="reportUserName" style="width:85%;" id="reportUserName" type="text"
									class="show_inupt" value="${repair.reportUserName}"
									readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								电话号码
							</td>
							<td width="34%">
								<input name="reportUserTel" id="reportUserTel" type="text"
									class="show_inupt" style="width:85%;"  value="${repair.reportUserTel}"
									readonly="readonly" />
							</td>
							<td class="winzard_show_from_right">
								设备名称
							</td>
							<td width="35%">
								<c:forEach items="${deviceList }" var="device">
									<c:if test='${repair.deviceId == device.deviceId }'>
										<input name="deviceName" id="deviceName" type="text"
											class="show_inupt" style="width:85%;"  value="${device.deviceName}"
											readonly="readonly" />
									</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								故障描述
							</td>
							<td colspan="3">
									<textarea name="repairDetail" id="repairDetail"
										class="show_textarea" cols="80" style="height:80px;overflow-y:auto" rows="5" readonly="readonly">${repair.repairDetail}</textarea>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								<font color="red">*</font>维修人
								<input type="hidden" name="userid" id="userid" value="" />
							</td>
							<td colspan="2">
								<input type="text" class="fkdj_from_input" name="repairUserName"
									id="repairUserName" />
							</td>

						</tr>
					</table>
				</div>
			</form>
		</div>
		
		<!-- 
		
			
		<div id="buttonbox_distribution" class="allbox">
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" onclick="saveData()">确定</a>
				<a class="fkdj_botton_right" href="javascript:cancelDistributeRepair()">取消</a>
			</div>
		</div>
		 -->
			
		<div id="buttonbox_distribution" class="allbox">
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			</div>
		</div>
		
	
	</body>
	<script type="text/javascript">

	function distributeRepair() {
		$('#distirbuteRepairForm').submit();
		closeEasyWin("distributionRepair");
	}

	function cancelDistributeRepair() {
		$('#distirbuteRepairForm').resetForm();
	}

	//机构树
	function selectRespondents() {

		var url = "${ctx}/app/visitor/frontReg/respondentsTreeSelect.act";
		var params = "";
		url += params;

		var width = 300;
		var left = (document.body.scrollWidth - width) /2;
		$('#companyWin').show();
		$('#companyWin').window({
			title : '请选择维修人',
			modal : true,
			shadow : false,
			closed : false,
			width : width,
			height : 600,
			top : 50,
			left : left
		});
		$('#companyIframe').attr("src", url);

	}
</script>
	
	<div id='companyWin' class='easyui-window' collapsible='false'
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<iframe id='companyIframe' name='companyIframe'
			style='width: 100%; height: 100%;' frameborder='0'></iframe>
	</div>
</html>
