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
		<title>库存--入库</title>
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
		<script src="${ctx}/app/property/storage/js/storageIn.js" type="text/javascript"></script>

	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
				<div class="fkdj_lfrycx">
					<div class="winzard_show_from">
						<form id="inStorageForm" name="inStorageForm"
							action="inStorage.act" method="post" target="main_frame_right">
							<input type="hidden" name="storageId" id="storageId" value="${storage.storageId }"/>
							<input type="hidden" name="deviceTypeId" id="deviceType" value="${device.deviceTypeId }"/>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="winzard_show_from_right" width="20%">
										设备
									</td>
									<td width="50%">
									    <input type="hidden" name="deviceId" id="deviceId" value="${device.deviceId }"/>
										<label class="show_pop_fist">
											${device.deviceName }
										</label>
									</td>
									<td class="winzard_show_from_right">
										库存数量
									</td>
									<td width="50%">
									    <input type="hidden" id="storageInventory" name="storageInventory" value="${storage.storageInventory }">
										<label class="show_pop_fist">
											${storage.storageInventory }
										</label>
									</td>
								</tr>
								
								<tr>
									<td class="winzard_show_from_right">
									<font color="red">*</font>本次入库数量
									</td>
									<td width="50%">
										<input name="inoutStorageInNumber" id="inoutStorageInNumber"
											class="fkdj_from_input" type="text" value="" />
									</td>

									<td class="winzard_show_from_right">
										<font color="red">*</font>入库人
										<input type="hidden" name="userid" id="userid" value="" />
										<input type="hidden" name="orgId" id="orgId" value="" />
										<input type="hidden" id="orgName" name="orgName" value="" />
									</td>
									<td>
										<input type="text" class="fkdj_from_input" name="outName"
											id="outName" readonly="readonly" value="" />
											<img src="${ctx}/app/integrateservice/images/fkdj_pho.jpg"
											width="22" height="18" onclick="selectRespondents();"/>
									</td>
								</tr>
								<tr>
									<td class="winzard_show_from_right" valign="top">
										备注
									</td>
									<td colspan="3">
										<div class="text_right_move">
											<textarea name="inoutStorageInDetail" id="inoutStorageInDetail"
											 cols="80" rows="5"></textarea>
										</div>
									</td>
								</tr>
							</table>
							
							<!-- 
								<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_left" onclick="saveData()">确定</a>
									<a class="fkdj_botton_right" href="javascript:cancelInStorage()">取消</a>
								</div>
							
							 -->
								<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_right" onclick="saveData()">确定</a>
								</div>
							
							
							
						</form>
					</div>
				</div>
			</div>
	</body>
	<script type="text/javascript">
	function inStorage() {
		$('#inStorageForm').submit();
		closeEasyWin("inStorage");
	}
	function cancelInStorage() {
		$('#inStorageForm').resetForm();
	}
</script>

<script type="text/javascript">
//机构树
	function selectRespondents() {

		var url = "${ctx}/app/visitor/frontReg/respondentsTreeSelect.act";
		var params = "";
		url += params;

		var width = 300;
		var left = (document.body.scrollWidth - width) /2;
		$('#companyWin').show();
		$('#companyWin').window({
			title : '请选择入库人',
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