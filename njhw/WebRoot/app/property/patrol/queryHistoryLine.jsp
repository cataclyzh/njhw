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
<title>巡查排班管理</title>
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
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
	type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
</head>
<body style="height: auto; background: #fff;overflow:hidden">
	<!-- 分页一览页面 -->
	<div class="fkdj_index">
		<div class="bgsgl_border_left">
			<div class="bgsgl_border_right">巡更历史轨迹</div>
		</div>
		<div class="bgsgl_conter">
			<div class="">
				<div class="fkdj_from">
					<form id="queryForm" name="queryForm" action="queryHistoryLine.act"
						method="post">
						<h:panel id="query_panel" width="100%" title="查询条件">
							<table align="center" id="hide_table" border="0" width="100%">
								<tr>
									<td class="fkdj_name" width="8%">员工姓名</td>
									<td width="20%"><s:textfield name="userName" id="userName"
											cssClass="fkdj_from_input" /></td>
								</tr>
								<tr>
									<td class="fkdj_name">当班时段</td>
									<td><s:textfield name="startTime"
											id="scheduleStartQueryTime" cssClass="fkdj_from_input"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
									<td class="fkdj_name_lkf" style="text-align: center;">至</td>
									<td><s:textfield name="endTime" id="scheduleEndQueryTime"
											cssClass="fkdj_from_input"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
									<td><a class="property_query_button"
										href="javascript:void(0);"
										onmousemove="this.style.background='FFC600'"
										onmouseout="this.style.background = '#8090b4'"
										onclick="saveData()">查询 </a></td>
								</tr>
							</table>
						</h:panel>
						
						<h:page id="list_panel" width="100%" title="结果列表">
							<d:table name="page.result" id="row" uid="row" export="true"
								requestURI="queryHistoryLine.act?type=export" cellspacing="0"
								cellpadding="0" class="table">
								<d:col property="userName" class="display_centeralign"
									title="姓名" maxLength="15" />
								<d:col property="time"
									class="display_centeralign" title="时间" maxLength="25" />
								
								<d:col property="placeName"
									class="display_centeralign" title="地点" maxLength="15" />
								<d:setProperty name="export.excel.filename" value="巡更历史轨迹.xls" />
								<d:setProperty name="export.amount" value="list" />
								<d:setProperty name="export.xml" value="false" />
								<d:setProperty name="export.types" value="excel" />
							</d:table>
						</h:page>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//查询排班记录
		function saveData() {
			$("#pageNo").val("1");
			$("#queryForm").submit();
		}
	</script>
</body>
</html>
