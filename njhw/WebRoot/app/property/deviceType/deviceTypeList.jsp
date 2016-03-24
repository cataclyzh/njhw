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
		<title>查看设备类型</title>
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
		
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
			<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/property/calendarCompare.js" type="text/javascript" charset="UTF-8"></script>
		<script src="${ctx}/scripts/widgets/property/conference.js" type="text/javascript"></script> 
		<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>

	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_rig_ht">
					查看设备类型
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name="queryForm"
							action="deviceTypeListForQuery.act" method="post">
							
							
								<h:panel id="query_panel" width="100%" title="查询条件">
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name">
											设备类型编号
										</td>
										<td>
										    <s:textfield name="deviceType_No" id="deviceType_No" cssClass="fkdj_from_input"/>
										</td>
										<td class="fkdj_name">
											设备类型名称
										</td>
										<td>
											 <s:textfield name="deviceType_Name" id="deviceType_Name" cssClass="fkdj_from_input"/>

										</td>
										<td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="querySubmit();">查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>

							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:45" class="display_centeralign" title="序号">
										${row_rowNum+((page.pageNo-1)*page.pageSize)}
									</d:col>
									<d:col class="display_centeralign" title="设备类型编号">
									    <a style="cursor:pointer;color:black;" onclick="toViewDeviceType('${row.DEVICE_TYPE_ID}');">${row.DEVICE_TYPE_NO}</a>
									</d:col>
									<d:col property="DEVICE_TYPE_NAME" class="display_centeralign"
										title="设备类型名称" />
									<d:col class="display_centeralign" title="操作">
										<span class="display_centeralign"
											onmousemove = "this.style.color = '#ffae00'" onmouseout = "this.style.color= '#8090b4'" onclick="toEditDeviceType('${row.DEVICE_TYPE_ID}');" style="cursor:pointer">[修改]</span>
										<span class="display_centeralign"
											onmousemove = "this.style.color = '#ffae00'" onmouseout = "this.style.color= '#8090b4'" onclick="deleteDeviceType('${row.DEVICE_TYPE_ID}');" style="cursor:pointer">[删除]</span>
									</d:col>
								</d:table>
							</h:page>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<div class="clear"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	function closeWin(winId) {
		closeEasyWin(winId);
	}
	function toEditDeviceType(deviceTypeId) {
		var url = "toEditDeviceType.act?deviceTypeId=" + deviceTypeId;
		openEasyWin("editDeviceType", "设备类型-修改", url, "800", "470", true);
	}
	function toViewDeviceType(deviceTypeId) {
		var url = "toViewDeviceType.act?deviceTypeId=" + deviceTypeId;
		openEasyWin("viewDeviceType", "设备类型--查看", url, "800", "400", true);
	}
	//删除设备类型
	function deleteDeviceType(deviceTypeId) {
	    if (deviceTypeId > 0){
	    $.ajax({
            type: "POST",
            url: "${ctx}/app/pro/getDeviceCountByDeviceTypeId.act",
            data:{"deviceTypeId": deviceTypeId},
            dataType: 'json',
            async : false,
            success: function(json){
            	if (json.deviceCountByDeviceTypeId <= 0) {
		    			easyConfirm('提示', '确定删除？', function(r) {
			    if (r) {
				    url = "${ctx}/app/pro/deleteDeviceType.act?deviceTypeId=" + deviceTypeId;
				    window.location.href = url;
			    }
		      });
		     }
		     else{
		         easyAlert('无法删除！','存在设备与该设备类型关联!');
		     }
             }
         });
         }
	}
</script>
</html>