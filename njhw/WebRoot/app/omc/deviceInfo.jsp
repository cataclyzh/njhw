<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>查看报修</title>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
			<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
			
<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>

<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
			<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
				rel="stylesheet" type="text/css" />
			<script src="${ctx}/scripts/basic/jquery.js.gzip"
				type="text/javascript"></script>
			<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
				type="text/javascript"></script>
			<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
			<script type="text/javascript"
				src="${ctx}/app/portal/toolbar/showModel.js"></script>
			<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

		<script type="text/javascript">
		$(function(){
			var deviceType = "${deviceType}";
			if(deviceType!=null && deviceType!="")
			{
				$("#deviceType").val(deviceType);
			}
			var status = "${status}";
			if(status!=null && status!="")
			{
				$("#status").val(status);
			}
		});

</script>
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div class="fkdj_index">
			<div class="oper_border_right">
				<div class="oper_border_left">
					设备状态信息
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
                      <form id="queryForm" name="queryForm" action="queryDeveiceToView.act" method="post" >				
                          <h:panel id="query_panel" width="100%" title="查询条件">
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name">
											设备类型
										</td>
										<td>
										<select  class="fkdj_from_input" name="deviceType" id="deviceType" style="height:30px;font-size:15px" >
										<option value="1">门锁</option>
										<option value="2">门锁通信机</option>
										<option value="3">闸机</option>
										<option value="4">门禁</option>
										</select>
										</td>
										<td class="fkdj_name">
											设备状态
										</td>
										<td>
										<select  class="fkdj_from_input" id="status" name="status" style="height:30px;font-size:15px" >
												<option value="1">正常</option>
												<option value="2">异常</option>
										</select>
										</td>
									</tr>
									<tr>
									<td class="fkdj_name"></td>
									<td class="fkdj_name"></td>
									<td colspan="2" align="center">
											<a class="botton_oper_a"
												
												onclick="querySubmit();"
												style="cursor:pointer;margin-right:46px;"
												>查询 </a>
									</td>
									</tr>
								</table>
							</h:panel>
							<h:page id="list_panel" width="100%" title="结果列表">
								<s:textfield name="page.pageSize" id="pageSize" theme="simple"
											cssStyle="display:none" onblur="return checkPageSize();"
											onkeyup="value=value.replace(/[^\d]/g,'');" />
								<d:table name="page.result" id="row" uid="row" export="false" class="table">	
								    <d:col class="display_leftalign" title="设备名称" property="TYPENAME">
									</d:col>
									<d:col class="display_leftalign" title="设备类型">
											<c:if test="${deviceType == 1}">
												<span class="display_centeralign">门锁</span>
											</c:if>
											<c:if test="${deviceType == 2}">
												<span class="display_centeralign">门锁通信机</span>
											</c:if>
											<c:if test="${deviceType == 3}">
												<span class="display_centeralign">闸机</span>
											</c:if>
											<c:if test="${deviceType == 4}">
												<span class="display_centeralign">门禁</span>
											</c:if>
									</d:col>
									<d:col class="display_leftalign" title="设备状态" >
										<c:if test="${status == 1}">
												<span class="display_centeralign">正常</span>
										</c:if>
										<c:if test="${status == 2}">
												<span class="display_centeralign">异常</span>
										</c:if>
									</d:col>
									</d:table>
							</h:page>
						</form>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
