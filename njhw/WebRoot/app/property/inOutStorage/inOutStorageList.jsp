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
		<title>出入库记录</title>
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
        <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
						<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
			
	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_rig_ht">
					出入库记录
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name="queryForm" action="queryInOutStorageInfoList.act"
							method="post">
							<h:panel id="query_panel" width="100%" title="查询条件">
							<s:hidden name="repairId" id="repairId" />
								<table align="center" id="hide_table" border="0" width="100%"
									class="form_table">
									<tr>
										<td class="fkdj_name">
											出入库人
										</td>
										<td>
										    <s:hidden name="lenderUserId" id="lenderUserId"></s:hidden>
										    <s:textfield name="lenderUserName" id="lenderUserName" cssClass="fkdj_from_input"/>
										</td>
										<td class="fkdj_name">
											所属单位
										</td>
										<td>
											<select id="orgIdSelect" class="fkdj_select" name="orgIdSelect">
											    <option value="0">全部</option>
												<c:forEach items="${orgList}" var="org">
													<option value="${org.orgId }" <c:if test='${orgIdSelect == org.orgId }'> selected="selected" </c:if>>
														${org.name }
													</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td class="fkdj_name">
											设备类型
										</td>
										<td>
											<select id="deviceTypeId" class="fkdj_select" name="deviceTypeId"
												onchange="getDeviceByType()" style="width:168px;">
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
											<select id="deviceId" class="fkdj_select" name="deviceId" style="width:168px;">
												<option value="0">
													全部
												</option>
												<c:forEach items="${deviceList }" var="device">
													<option value="${device.deviceId }"  <c:if test='${deviceId == device.deviceId }'>selected="selected"</c:if>>
														${device.deviceName }
													</option>
												</c:forEach>
											</select>
										</td>
									</tr>

									<tr>
										<td class="fkdj_name">
											出入库日期
										</td>
										<td>
											<table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td>
													    <s:textfield name="inOutStorageStartTime" id="inOutStorageStartTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
													</td>
													<td class="fkdj_name_lkf">
														至
													</td>
													<td>
														<s:textfield name="inOutStorageEndTime" id="inOutStorageEndTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
													</td>
												</tr>
											</table>
										</td>
										<td class="fkdj_name">
											出入库类型
										</td>
										<td>
											<select name="inoutStorageFlag" class="fkdj_select"
												id="inoutStorageFlag">
												<option value="All">
													全部
												</option>
												<option value="0" <c:if test="${inoutStorageFlag=='0'}">selected="selected"</c:if>>
													入库
												</option>
												<option value="1" <c:if test="${inoutStorageFlag=='1'}">selected="selected"</c:if>>
													出库
												</option>
											</select>
										</td>
										<td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="queryInOutStorage()">查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="true" requestURI="queryInOutStorageInfoList.act" cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:45" class="display_centeralign" title="序号">
										${row_rowNum+((page.pageNo-1)*page.pageSize)}
									</d:col>
									<d:col class="display_centeralign" title="出入库记录单号" media="html">
										<a style="cursor:pointer;color:black;" onclick="toViewInOutStorage('${row.INOUT_STORAGE_ID }');">${row.INOUT_STORAGE_ID }</a>
								    </d:col>
									<d:col class="display_centeralign" title="设备名称">
										<c:forEach items="${deviceDisplayList }" var="device">
											<c:if
												test='${row.DEVICE_ID == device.deviceId }'>${device.deviceName }
									        </c:if>
										</c:forEach>
									</d:col>
									<d:col property="LENDER_USER_NAME" class="display_centeralign" title="出入库人"/>
									<d:col property="LENDER_USER_ORG_NAME" class="display_centeralign" title="单位名称"/>
									<d:col property="INOUT_STORAGE_TIME" class="display_centeralign" title="日期"/>
									<d:col class="display_centeralign" title="数量">
									    <c:if test='${row.INOUT_STORAGE_FLAG == 0 }'>${row.INOUT_STORAGE_IN_NUMBER }</c:if>
									    <c:if test='${row.INOUT_STORAGE_FLAG == 1 }'>${row.INOUT_STORAGE_OUT_NUMBER }</c:if>
									</d:col>
									<d:col class="display_centeralign" title="类型">
									    <c:if test='${row.INOUT_STORAGE_FLAG == 0 }'>入库</c:if>
									    <c:if test='${row.INOUT_STORAGE_FLAG == 1 }'>出库</c:if>
									</d:col>
									<d:setProperty name="export.excel.filename" value="inoutStorage.xls"/>
									<d:setProperty name="export.amount" value="list"/>
								</d:table>
							</h:page>
							<div class="clear"></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	function toViewInOutStorage(inOutStorageId) {
		var url = "toViewInOutStorage.act?inOutStorageId=" + inOutStorageId;
		openEasyWin("viewInOutStorage", "出入库记录--查看", url, "800", "300", true);
	}
	
	function closeWin(winId) {
		closeEasyWin(winId);
	}
	
	function queryInOutStorage(){
	    querySubmit(); 
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