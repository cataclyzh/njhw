<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>查看报修</title>
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
		
		
				<script src="${ctx}/app/property/repair/js/repairList.js" type="text/javascript"></script>
		
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div class="fkdj_index">
			<div class="oper_border_right">
				<div class="oper_border_left">
					查看报修
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name ="queryForm" action="queryRepairListSimple.act" method="post">
							<h:panel id="query_panel" width="100%" title="查询条件">
							<s:hidden name="repairId" id="repairId" />
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name">
											报修人
										</td>
										<td>
										    <s:textfield name="reportUserName" id="reportUserName" cssClass="fkdj_from_input"/>
										</td>
										<td class="fkdj_name">
											报修单位
										</td>
										<td>
											<select class="fkdj_select" id="orgIdSelect" name="orgIdSelect">
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
											<select id="deviceTypeIdSelect" name="deviceTypeIdSelect"
												onchange="getDeviceByType()" class="fkdj_select">
												<option value="0">
													请选择...
												</option>
												<c:forEach items="${deviceTypeList }" var="deviceType">
													<option value="${deviceType.deviceTypeId }"
														<c:if test='${deviceTypeIdSelect == deviceType.deviceTypeId }'> selected="selected" </c:if>>
														${deviceType.deviceTypeName }
													</option>
												</c:forEach>
											</select>
										</td>
										<td class="fkdj_name">
											设备名称
										</td>
										<td>
											<select id="deviceIdSelect" name="deviceIdSelect" class="fkdj_select">
												<option value="0">
													请选择...
												</option>
												<c:forEach items="${deviceList }" var="device">
													 
													<option value="${device.deviceId }"  <c:if test='${deviceIdSelect == device.deviceId }'>selected="selected"</c:if>>
														${device.deviceName }
													</option>
												</c:forEach>
											</select>
										</td>
									</tr>

									<tr>
										<td class="fkdj_name">
											报修日期
										</td>
										<td>
											<table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td>
													    <s:textfield name="repairStartTime" id="repairStartTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
													</td>
													<td class="fkdj_name_lkf">
														至
													</td>
													<td>
														<s:textfield name="repairEndTime" id="repairEndTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
													</td>
												</tr>
											</table>
										</td>
										<td class="fkdj_name">
											状态
										</td>
										<td>
											<select name="repairState" class="fkdj_select"
												id="repairState">
												<option value="stateAll">
													全部
												</option>
												<option value="0" <c:if test="${repairState=='0'}">selected="selected"</c:if>>
													已确认
												</option>
												<option value="1" <c:if test="${repairState=='1'}">selected="selected"</c:if>>
													已派发
												</option>
												<option value="2" <c:if test="${repairState=='2'}">selected="selected"</c:if>>
													已完成
												</option>
												<option value="3" <c:if test="${repairState=='3'}">selected="selected"</c:if>>
													已评价
												</option>
												<option value="4" <c:if test="${repairState=='4'}">selected="selected"</c:if>>
													已中止
												</option>
											</select>
										</td>
										<td>
											<a class="botton_oper_a" href="javascript:void(0);"
												
												onclick="saveData()">查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="false" pagesize="8"
									cellspacing="0" cellpadding="0" class="table">
									<d:col property="REPAIR_ID" class="display_centeralign"
										title="维修单号" maxLength="15" />
									<d:col property="REPAIR_THEME" class="display_centeralign"
										title="主题" maxLength="15"/>
									<d:col property="ORG_NAME" class="display_centeralign"
										title="报修单位名称" maxLength="15"/>
									<d:col property="REPORT_USER_NAME" class="display_centeralign"
										title="报修人" maxLength="15"/>
									<d:col property="REPAIR_START_TIME" class="display_centeralign"
										title="报修时间" maxLength="15"/>
									<d:col class="display_centeralign" title="状态">
										<c:if test="${row.REPAIR_STATE == 0}">
											<span class="display_centeralign">已确认</span>
										</c:if>
										<c:if test="${row.REPAIR_STATE == 1}">
											<span class="display_centeralign">已派发</span>
										</c:if>
										<c:if test="${row.REPAIR_STATE == 2}">
											<span class="display_centeralign">已完成</span>
										</c:if>
										<c:if test="${row.REPAIR_STATE == 3}">
											<span class="display_centeralign">已评价</span>
										</c:if>
										<c:if test="${row.REPAIR_STATE == 4}">
											<span class="display_centeralign">已中止</span>
										</c:if>
									</d:col>
									
								</d:table>
							</h:page>
						</form>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		//根据设备类型，加载该设备类型的设备
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
		    			$("#deviceIdSelect").append("<option value=0>请选择...</option>");
						$.each(json.mapList,function(i){
							$("#deviceIdSelect").append("<option value="+json.mapList[i].DEVICE_ID+">"+json.mapList[i].DEVICE_NAME+"</option>"); 
						});
		    		}
             }
         });
         }
         }
    </script>
	<script type="text/javascript">
	//查询维修记录
	function queryRepair(){
	    $('#repairForm').submit();
	}
	</script>
	
	<script type="text/javascript">
	
	//中止维修记录
	
	function suspendRepair(repairId) {
		easyConfirm('提示', '确定中止？', function(r) {
			if (r) {
				url = "${ctx}/app/pro/suspendRepair.act?repairId=" + repairId;
				window.location.href = url;
			}
		});
	}
</script>
	
	<script type="text/javascript">

	function closeWin(winId) {
		closeEasyWin(winId);
	}

	function loadDistributionRepair(repairId) {
		var url = "toDistributionRepair.act?repairId=" + repairId;
		openEasyWin("distributionRepair", "物业报修--派发页面", url, "850", "400", false);
	}

	function loadCompleteRepair(repairId) {
		var url = "toCompleteRepair.act?repairId=" + repairId;
		openEasyWin("completeRepair", "物业报修--完成页面", url, "850", "500", false);
	}

	function loadEvaluateRepair(repairId) {
		var url = "toEvaluateRepair.act?repairId=" + repairId;
		openEasyWin("evaluateRepair", "物业报修--回访页面", url, "880", "500", false);

	}

	function loadViewRepair(repairId) {
		var url = "toViewRepair.act?repairId=" + repairId;
		openEasyWin("viewRepair", "物业报修--查看页面", url, "850", "540", false);

	}
</script>
</body>
</html>
