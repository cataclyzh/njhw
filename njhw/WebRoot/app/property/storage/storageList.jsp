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
		<title>查看库存</title>
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

	</head>

	<body style="background: #fff;">
		<div class="fkdj_index">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_rig_ht">
					查看库存
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
						<form id="queryForm" name="queryForm" action="queryStorageList.act"
							method="post">
							<h:panel id="query_panel" width="100%" title="查询条件">
								<s:hidden name="storageId" id="storageId" />
								<table align="center" id="hide_table" border="0" width="100%"
									class="form_table">
									<tr>
										<td class="fkdj_name">
											设备类型
										</td>
										<td>
											<select id="deviceTypeId" class="fkdj_from_input" name="deviceTypeId"
												onchange="getDeviceByType()">
												<option value="0">
													全部
												</option>
												<c:forEach items="${deviceTypeList }" var="deviceType">
													<option value="${deviceType.deviceTypeId }"
														<c:if test='${deviceTypeId == deviceType.deviceTypeId }'> selected="selected" </c:if>>
														${deviceType.deviceTypeName }
													</option>
												</c:forEach>
											</select>
										</td>
										<td class="fkdj_name">
											设备名称
										</td>
										<td>
											<select id="deviceId" class="fkdj_from_input" name="deviceId">
												<option value="0">
													全部
												</option>
												<c:forEach items="${deviceList }" var="device">
													<option value="${device.deviceId }" <c:if test='${deviceId == device.deviceId }'>  selected="selected"</c:if>>
														${device.deviceName }
													</option>
												</c:forEach>
											</select>
										</td>
									  <td>
											<a class="property_query_button" href="javascript:void(0);" onmousemove="this.style.background='FFC600'" onmouseout="this.style.background = '#8090b4'" onclick="querySubmit();">查询
											</a>
										</td>
									</tr>
								</table>
							</h:panel>
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="true" requestURI="queryStorageList.act"  
									cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:45" class="display_centeralign" title="序号">
										${row_rowNum+((page.pageNo-1)*page.pageSize)}
									</d:col>
									<d:col class="display_centeralign" title="库存编号" media="html">
									    <a style="cursor:pointer;color:black;" onclick="toViewStorage('${row.DEVICE_ID}');">${row.STORAGE_ID}</a>
									</d:col>
									<d:col class="display_centeralign" title="设备类型">
									    <c:forEach items="${deviceTypeList }" var="deviceType">
											<c:if
												test='${row.DEVICE_TYPE_ID == deviceType.deviceTypeId }'>${deviceType.deviceTypeName }
									        </c:if>
										</c:forEach>
									</d:col>
									<d:col class="display_centeralign" title="设备名称">
										<c:forEach items="${deviceDisplayList }" var="device">
											<c:if
												test='${row.DEVICE_ID == device.deviceId }'>${device.deviceName }
									        </c:if>
										</c:forEach>
									</d:col>
									<d:col class="display_centeralign" title="备注">
										<c:forEach items="${deviceDisplayList }" var="device">
											<c:if
												test='${row.DEVICE_ID == device.deviceId }'>${device.deviceDescribe }
									        </c:if>
										</c:forEach>
									</d:col>
									<d:col property="STORAGE_INVENTORY" class="display_centeralign"
										title="数量" />
									<d:col class="display_centeralign" title="操作" media="html">
										<span class="display_centeralign" onmousemove="this.style.color= '#ffae00'" onmouseout="this.style.color= '#8090b4'" onclick="toInStorage('${row.DEVICE_ID}');" style="cursor:pointer">[入库]</span>
										<span class="display_centeralign" <c:if test='${row.STORAGE_INVENTORY >0}'>onclick="toOutStorage('${row.DEVICE_ID}');" onmousemove="this.style.color= '#ffae00'" onmouseout="this.style.color= '#8090b4'" style="cursor:pointer"</c:if>onmousemove="this.style.color= '#8090b4'" onmouseout="this.style.color= '#8090b4'" style="cursor:default">[出库]</span>
									</d:col>
									<d:setProperty name="export.excel.filename" value="storage.xls"/>
								</d:table>
							</h:page>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							
							<div class="clear"></div>
						</form>
					</div>
				</div>
			</div>
	</body>
	<script type="text/javascript">
	function toInStorage(deviceId) {
		var url = "toInStorage.act?deviceId=" + deviceId;
		openEasyWin("inStorage", "库存--入库", url, "800", "310", false);
	}
	function toOutStorage(deviceId) {
		var url = "toOutStorage.act?deviceId=" + deviceId;
		openEasyWin("outStorage", "库存--出库", url, "800", "310", false);
	}
			
	function toViewStorage(deviceId) {
		var url = "toViewStorage.act?deviceId=" + deviceId;
		openEasyWin("viewStorage", "库存--查看", url, "800", "310", false);
	}
	
	function closeWin(winId) {
		closeEasyWin(winId);
	}

	// 根据设备类型，加载该设备类型的设备
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
</script>
</html>