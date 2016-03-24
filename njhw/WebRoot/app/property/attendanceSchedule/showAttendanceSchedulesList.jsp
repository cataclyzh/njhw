<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>查看排班</title>
		<%@ include file="/common/include/meta.jsp"%>
		
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/styles/property/css.css" rel="stylesheet"		type="text/css" />
		<link href="${ctx}/styles/property/block.css" rel="stylesheet"		type="text/css" />
		<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet"	type="text/css" />
		<link href="${ctx}/styles/property/css_body.css" rel="stylesheet"	type="text/css" />
			
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
		
		<script type="text/javascript">
			function showAttendanceSchedulesList(){
	 			querySubmit();
	 		}
	 		
	 		function viewAttendanceSchedule(attendanceScheduleId){
				var url = "${ctx}/app/pro/queryAttendanceScheduleById.act?attendanceScheduleId="+attendanceScheduleId;
				openEasyWin("viewAttendanceSchedule","查看考勤排班",url,"850","500",false);
			}
			
			
			function modifyOneAttendanceSchedulePrepare(attendanceScheduleId){
				var list_attendanceScheduleUserName = $("#list_attendanceScheduleUserName").val();
				var list_attendanceScheduleOrgName = $("#list_attendanceScheduleOrgName").val();
				var list_attendanceScheduleInTime = $("#list_attendanceScheduleInTime").val();
				var list_attendanceScheduleOutTime = $("#list_attendanceScheduleOutTime").val();
				var res_bak1 = $("#res_bak1").val();
				var res_bak2 = $("#res_bak2").val();
				var list_attendanceScheduleState = $("#list_attendanceScheduleState option[selected='selected']").val();
				var pageNum = $("#main_peag .current").text();
				
				var url = "${ctx}/app/pro/modifyOneAttendanceSchedulePrepare.act?attendanceScheduleId="+attendanceScheduleId
						+"&pageNum="+pageNum+"&list_attendanceScheduleState="+list_attendanceScheduleState
						+"&list_attendanceScheduleUserName="+list_attendanceScheduleUserName
						+"&list_attendanceScheduleOrgName="+list_attendanceScheduleOrgName
						+"&list_attendanceScheduleInTime="+list_attendanceScheduleInTime
						+"&list_attendanceScheduleOutTime="+list_attendanceScheduleOutTime
						+"&res_bak1="+res_bak1+"&res_bak2="+res_bak2;
				openEasyWin("modifyOneAttendanceSchedulePrepare","考勤排班修改",url,"850","600",false);
			}
			
			function suspendOneAttendanceSchedule(attendanceScheduleId){
				var list_attendanceScheduleUserName = $("#list_attendanceScheduleUserName").val();
				var list_attendanceScheduleOrgName = $("#list_attendanceScheduleOrgName").val();
				var list_attendanceScheduleInTime = $("#list_attendanceScheduleInTime").val();
				var list_attendanceScheduleOutTime = $("#list_attendanceScheduleOutTime").val();
				var res_bak1 = $("#res_bak1").val();
				var res_bak2 = $("#res_bak2").val();
				var list_attendanceScheduleState = $("#list_attendanceScheduleState option[selected='selected']").val();
				var pageNum = $("#main_peag .current").text();
				
	 			easyConfirm('提示', '确定删除该考勤排班计划？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/suspendOneAttendanceSchedule.act?attendanceScheduleId="+attendanceScheduleId
							+"&pageNum="+pageNum+"&list_attendanceScheduleState="+list_attendanceScheduleState
							+"&list_attendanceScheduleUserName="+list_attendanceScheduleUserName
							+"&list_attendanceScheduleOrgName="+list_attendanceScheduleOrgName
							+"&list_attendanceScheduleInTime="+list_attendanceScheduleInTime
							+"&list_attendanceScheduleOutTime="+list_attendanceScheduleOutTime
							+"&res_bak1="+res_bak1+"&res_bak2="+res_bak2;
				}
			});
	 			
	 		}		
			
			function closeViewAttendanceSchedule(){
				closeEasyWin("viewAttendanceSchedule");
			}
			
			
			function closeModifyAttendanceSchedule(){
				closeEasyWin("modifyOneAttendanceSchedulePrepare");
			}
			
			$(document).ready(function(){
				jQuery.validator.methods.compareDate = function(value, element, param) {
			        var beginTime = jQuery(param).val();
			        if(value!=""){
			        return beginTime <= value;
			        }else {
			        return true;
			        }
			    };  
							$("#queryForm").validate({		// 为inputForm注册validate函数
								meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
								errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
								onsubmit: true,
								rules: {
						
							list_attendanceScheduleOutTime:{
								compareDate: "#list_attendanceScheduleInTime"
							}
								},
								messages:{
									list_attendanceScheduleOutTime:{
										compareDate: "结束时间必须晚于开始时间"
									}
								}
							});
			});    
			
			function saveData(){		
				if (showRequest()) {
					querySubmit();
				}
			}
			
			function showRequest(){
				 return $("#queryForm").valid();
			}
		</script>
	</head>
	<body style="background: #fff;">
			<div class="index_bgs">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						查看排班
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				    <!--此处写页面的内容 -->
				<div class="fkdj_from">

 			

 					<form id="queryForm" name="queryForm" action="showAttendanceSchedulesList.act" method="post">

 						
 						<h:panel id="query_panel" width="100%" title="查询条件">
 						
 						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hide_table" class="form_table">
 							<tr>
 								<td class="fkdj_name">
 									被排班人
 								</td>
 								<td><s:textfield name="list_attendanceScheduleUserName" id="list_attendanceScheduleUserName" cssClass="fkdj_from_input"/></td>								
 								<td class="fkdj_name">
 									所属部门
 								</td>
 								<td><s:textfield name="list_attendanceScheduleOrgName" id="list_attendanceScheduleOrgName" cssClass="fkdj_from_input"/></td> 												
 							</tr>
							
							<tr>
 								<td class="fkdj_name">排班日期</td>
						    	<td><table border="0" cellspacing="0" cellpadding="0">
						    		<tr>
								        <td>
								        <s:textfield name="list_attendanceScheduleInTime" id="list_attendanceScheduleInTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
								        <td class="fkdj_name_lkf">至</td>
								        <td><s:textfield name="list_attendanceScheduleOutTime" id="list_attendanceScheduleOutTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
								      </tr>
						    		</table>
						    	</td>
 							</tr>
 							<tr>
 								<td class="fkdj_name">排班时间</td>
						    	<td><table border="0" cellspacing="0" cellpadding="0">
						    		<tr>
								        <td>
								        <s:textfield name="res_bak1" id="res_bak1" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'HH:mm:ss'})"/></td>
								        <td class="fkdj_name_lkf">至</td>
								        <td><s:textfield name="res_bak2" id="res_bak2" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'HH:mm:ss'})"/></td>
								      </tr>
						    		</table>
						    	</td>
						    	<td class="fkdj_name">状态</td>
							    <td>
							    	<select name="list_attendanceScheduleState" class="fkdj_select" id="list_attendanceScheduleState">							    		
							    		<option id="list_alllState" value="all" <c:if test="${list_attendanceScheduleState=='all'}">selected="selected"</c:if>>全部</option>
								        <option id="list_active" value="1" <c:if test="${list_attendanceScheduleState=='1'}">selected="selected"</c:if>>活动中</option>
								        <option id="list_stop" value="0" <c:if test="${list_attendanceScheduleState=='0'}">selected="selected"</c:if>>已停止</option>
							      	</select>
							    </td>
							    <td class="fkdj_name">&nbsp;</td>
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
			<d:col property="ATTENDANCE_SCHEDULE_USERNAME" maxLength="5" class="display_centeralign"
				title="被排班人" />
			<d:col property="ATTENDANCE_SCHEDULE_ORGNAME" maxLength="5" class="display_centeralign"
				title="被排班人部门" />
			<d:col property="ATTENDANCE_SCHEDULE_INTIME" maxLength="10" class="display_leftalign"
				title="起始日期" />
			<d:col property="ATTENDANCE_SCHEDULE_OUTTIME" maxLength="10" class="display_leftalign"
				title="结束日期" />
			<d:col property="RES_BAK1" class="display_centeralign"
				title="开始时间" />
			<d:col property="RES_BAK2" class="display_centeralign"
				title="结束时间" />
			<d:col class="display_centeralign" title="活动状态">
				<c:if test="${row.ATTENDANCE_SCHEDULE_STATE == 1}">
					<span class="display_centeralign">活动中</span>
				</c:if>
				<c:if test="${row.ATTENDANCE_SCHEDULE_STATE == 0}">
					<span class="display_centeralign">已停止</span>
				</c:if>									
			</d:col>											
			<d:col class="display_centeralign" title="操作">
										
										<c:if test="${row.ATTENDANCE_SCHEDULE_STATE == 1}">
										
										<span class="display_centeralign"
												onclick="javascript:modifyOneAttendanceSchedulePrepare('${row.ATTENDANCE_SCHEDULE_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color=''"
												style="cursor:pointer">
												[修改]&nbsp;
										</span>											
										
										<span class="display_centeralign"
												onclick="javascript:suspendOneAttendanceSchedule('${row.ATTENDANCE_SCHEDULE_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color=''" style="cursor:pointer">&nbsp;[删除]&nbsp; 
											</span>
										</c:if>
										<c:if test="${row.ATTENDANCE_SCHEDULE_STATE == 0}">
											<span class="display_centeralign"
													onclick=""
													onmousemove="this.style.color='#ffae00'"
													onmouseout="this.style.color=''" style="cursor:default;">
													[修改]&nbsp;
											</span>											
											
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color=''"
												style="cursor:default;"
												>&nbsp;[删除]&nbsp; 
											</span>
										</c:if>
										<span class="display_centeralign"
											onclick="javascript:viewAttendanceSchedule('${row.ATTENDANCE_SCHEDULE_ID}')"
											onmousemove="this.style.color='#ffae00'"
											onmouseout="this.style.color=''"
											style="cursor:pointer;"
											>&nbsp;[详细]&nbsp; 
										</span>					
									</d:col>						
		</d:table>	
	</h:page>
</form>	
					<div class="bgsgl_clear"></div>
				</div>
			</div>
		
	</div>
	</body>

</html>