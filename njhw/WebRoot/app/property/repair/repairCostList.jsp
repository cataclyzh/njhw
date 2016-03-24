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
		<title>维修耗材</title>
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
				<script src="${ctx}/app/property/repair/js/repairCostList.js" type="text/javascript"></script>

	</head>

	<body style="background:#fff;">
		<div class="fkdj_index">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_rig_ht">
					维修耗材
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name="queryForm" action="queryRepairCostList.act"
							method="post">
							<h:panel id="query_panel" width="100%" title="查询条件">
							<s:hidden name="repairId" id="repairId" />
								<table align="center" id="hide_table" border="0" width="100%"
									class="form_table">
									<tr>
										<td class="fkdj_name">
											报修人
										</td>
										<td>
										    <s:textfield name="reportUserName" id="reportUserName" cssClass="fkdj_from_input"/>
										</td>
										<td class="fkdj_name">
											单位名称
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
										<td>
											&nbsp;
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
														<s:textfield name="repairCostStartTime" id="repairCostStartTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
													</td>
													<td class="fkdj_name_lkf">
														至
													</td>
													<td>
														<s:textfield name="repairCostEndTime" id="repairCostEndTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
													</td>
												</tr>
											</table>
										</td>
										<td></td>
										<td style="padding-left:100px;">
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="saveData();">查询 </a>
										</td>
									</tr>
								</table>
								<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							</h:panel>
							
							<h:page id="list_panel" width="100%" title="结果列表">							
								<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:45" class="display_centeralign" title="序号">
										${row_rowNum+((page.pageNo-1)*page.pageSize)}
									</d:col>
									<d:col class="display_centeralign" title="报修主题">
									    <a style="cursor:pointer;color:black;" onclick="toViewRepairCost('${row.REPAIR_COST_ID}');">${row.REPAIR_COST_TITLE}</a>
									</d:col>
									<d:col property="REPORT_USER_NAME" class="display_centeralign" title="报修人" />
									<d:col property="ORG_NAME" class="display_centeralign" title="单位名称" />
									<d:col class="display_centeralign" title="耗材">
										<c:forEach items="${deviceList }" var="device">
											<c:if
												test='${row.DEVICE_COST_ID == device.deviceId }'>${device.deviceName }
									        </c:if>
										</c:forEach>
									</d:col>
									<d:col property="DEVICE_NUMBER" class="display_centeralign"
										title="数量" />
								</d:table>
							</h:page>
						</form>
							<div class="clear"></div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	function toViewRepairCost(repairCostId) {
		var url = "toViewRepairCost.act?repairCostId=" + repairCostId;
		openEasyWin("toViewRepairCost", "维修耗材--查看", url, "850", "300", false);
	}
	
	function queryRepairCost(){
	   $('#repairCostForm').submit();	   
	}
</script>
</html>