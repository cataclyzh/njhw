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
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

		
		<script src="${ctx}/app/property/patrol/js/patrolScheduleList.js" type="text/javascript"></script>

	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div class="fkdj_index">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_right">
					巡查排班管理
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name ="queryForm" action="queryPatrolScheduleList.act" method="post">
							<h:panel id="query_panel" width="100%" title="查询条件">
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name" width="8%">
											员工姓名
										</td>
										<td width="20%">
										    <s:textfield name="userName" id="userName" cssClass="fkdj_from_input"/>
										</td>
										
										<td class="fkdj_name" width="8%">
											所属部门
										</td>
										<td width="20%">
										    <select class="fkdj_select" id="orgIdSelect" name="orgIdSelect">
											    <option value="0">全部</option>
												<c:forEach items="${orgList}" var="org">
													<option value="${org.orgId }" <c:if test='${orgIdSelect == org.orgId }'> selected="selected" </c:if>>
														${org.name }
													</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
									</tr>
									
									<tr>
										<td class="fkdj_name">
											路线名称
										</td>
										<td>
										    <select class="fkdj_select" id="patrolLineIdSelect" name="patrolLineIdSelect">
											    <option value="0">全部</option>
												<c:forEach items="${patrolLineList}" var="patrolLine">
													<option value="${patrolLine.patrolLineId }" <c:if test='${patrolLineIdSelect == patrolLine.patrolLineId }'> selected="selected" </c:if>>
														${patrolLine.patrolLineName }
													</option>
												</c:forEach>
											</select>
										</td>
										
										<td class="fkdj_name">
											状态
										</td>
										<td>
										   <select name="patrolScheduleState" class="fkdj_select"
												id="patrolScheduleState">
												<option value="All">
													全部
												</option>
												<option value="0" <c:if test="${patrolScheduleState=='0'}">selected="selected"</c:if>>
												          使用中
												</option>
												<option value="1" <c:if test="${patrolScheduleState=='1'}">selected="selected"</c:if>>
													停止中
												</option>
											</select>
										</td>
										<td></td>
									</tr>
									
									<tr>
										<td class="fkdj_name">
											班次有效日期
										</td>
										<td>
											<input name="scheduleStartQueryDate" id="scheduleStartQueryDate"
												class="fkdj_from_input"
												onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
										</td>
										<td class="fkdj_name_lkf" style="text-align: center;">
											至
										</td>
										<td>
											<input name="scheduleEndQueryDate" id="scheduleEndQueryDate"
												class="fkdj_from_input"
												onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'scheduleStartQueryDate\',{M:-0,d:0})}'})" />
										</td>
										<td></td>
								    </tr>
								    <tr>
										<td class="fkdj_name">
											当班时段
										</td>
										<td>
											<s:textfield name="scheduleStartQueryTime" id="scheduleStartQueryTime"
												cssClass="fkdj_from_input"
												onclick="WdatePicker({dateFmt:'HH:mm:ss'})" />
										</td>
										<td class="fkdj_name_lkf" style="text-align: center;">
											至
										</td>
										<td>
											<s:textfield name="scheduleEndQueryTime" id="scheduleEndQueryTime"
												cssClass="fkdj_from_input"
												onclick="WdatePicker({dateFmt:'HH:mm:ss'})" />
										</td>
										<td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="saveData()">查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="false" cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:45;" class="display_centeralign"  title="<input type=\"checkbox\" id=\"checkAll\" checked=\"checked\"/>">
						                <input checked="checked" type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.SCHEDULE_ID}"/>
			                        </d:col>
									<d:col class="display_centeralign" title="员工姓名">
										<a style="cursor:pointer;color:black;" onclick="toViewPatrolSchedule('${row.SCHEDULE_ID}');">${row.USER_NAME}</a>
									</d:col>
									<d:col property="ORGNAME" class="display_centeralign" title="所属部门" maxLength="15"/>
									<d:col property="SCHEDULE_START_DATE" class="display_centeralign" title="班次起始日期" maxLength="15"/>
									<d:col property="SCHEDULE_END_DATE" class="display_centeralign" title="班次结束日期" maxLength="15"/>
								    <d:col property="SCHEDULE_START_TIME" class="display_centeralign" title="班次起始时间" maxLength="15"/>
								    <d:col property="SCHEDULE_END_TIME" class="display_centeralign" title="班次结束时间" maxLength="15"/>
								    <d:col class="display_centeralign" title="状态">
								        <c:if test='${row.PATROL_SCHEDULE_STATE == 0}'>使用中</c:if>
								        <c:if test='${row.PATROL_SCHEDULE_STATE == 1}'>停止中</c:if>
								    </d:col>
									<d:col class="display_centeralign" title="操作">
										<span class="display_centeralign" 
										        onclick="toEditPatrolSchedule('${row.SCHEDULE_ID}');" 
										        style="cursor:pointer" 
										        onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'">
												[修改]</span>
									</d:col>
								</d:table>
							</h:page>
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" style="cursor: pointer;" onclick="toAddPatrolSchedule();">增加</a>
				<a class="fkdj_botton_right" href="javascript:deletePatrolSchedule()">停止</a>
			</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	//查询排班记录
	function saveData(){
	    querySubmit();
	}
	</script>
	
	<script type="text/javascript">
	//跳转至新增排班页面
	function toAddPatrolSchedule(){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toAddPatrolSchedule.act";
	}
	
	//跳转至编辑排班页面
	function toEditPatrolSchedule(scheduleId){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toEditPatrolSchedule.act?scheduleId=" + scheduleId;
	}
	
	//跳转至查看排班页面
	function toViewPatrolSchedule(scheduleId){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toViewPatrolSchedule.act?scheduleId=" + scheduleId;
	}
	
	
	
	
	$(document).ready(function(){
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changecheckboxstyle();
		});
		
   //删除排班记录
	function deletePatrolSchedule(patrolScheduleId) {
		easyConfirm('提示', '确定删除？', function(r) {
			if (r) {
			    var checkboxs = $("input[type='checkbox'][name='_chk'][checked]");
			    $.each(checkboxs, function(){
			            if(this.checked == true){
			            var scheduleId = this.value;
				        window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/deletePatrolSchedule.act?scheduleId=" + scheduleId;
				        }
				    });
			}
		});
	}
    </script>
</body>
</html>
