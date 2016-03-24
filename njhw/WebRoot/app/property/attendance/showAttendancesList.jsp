<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>考勤记录</title>
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
			function showAttendancesList(){
	 			$('#queryForm').submit();
	 		}
	 		
	 		function viewAttendance(attendanceId){
				var url = "${ctx}/app/pro/queryAttendanceById.act?attendanceId="+attendanceId;
				openEasyWin("viewLostFound","查看考勤详情",url,"850","400",false);
			}	
			
			function closeViewAttendance(){
				closeEasyWin("viewAttendance");
			}
			
			function exportAttendancePrepare(){
				var url = "${ctx}/app/pro/exportAttendancePrepare.act";
				openEasyWin("exportAttendancePrepare","导出考勤清单",url,"850","350",false);
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
						
							list_attendanceOutTime:{
								compareDate: "#list_attendanceInTime"
							}
								},
								messages:{
									list_attendanceOutTime:{
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
			<div class="fkdj_index">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						考勤记录
					</div>
				</div>
				<div class="bgsgl_conter">
				<div class="">
 				<div class="fkdj_from">
 						

 					<form id="queryForm" name="queryForm" action="showAttendancesList.act" method="post">

 						<h:panel id="query_panel" width="100%" title="查询条件">
 						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hide_table" class="form_table">
 							<tr>
 								<td class="fkdj_name">
 									被排班人
 								</td>
 								<td><s:textfield name="list_attendanceUserName" id="list_attendanceUserName" cssClass="fkdj_from_input"/></td>
 								<td	class="fkdj_name">
 									所属部门
 								</td>
 								<td><s:textfield name="list_attendanceOrgName" id="list_attendanceOrgName" cssClass="fkdj_from_input"/></td>												
 							</tr>
 							<tr>
 								<td class="fkdj_name">上班时间</td>
						    	<td><table border="0" cellspacing="0" cellpadding="0">
								      <tr>
								        <td>
								        <s:textfield name="list_attendanceInTime" id="list_attendanceInTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
								        <td class="fkdj_name_lkf">至</td>
								        <td><s:textfield name="list_attendanceOutTime" id="list_attendanceOutTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
								      </tr>
						    		</table>
						    	</td>
						    	<td	class="fkdj_name">
 									考勤判断
 								</td>
 								<td>
 											<select id="list_attendanceSta" name="list_attendanceSta"  class="fkdj_from_input">
													<option value="all">全部</option>
													<option value="0"<c:if test="${list_attendanceSta=='0'}">selected="selected"</c:if>>
														正常
													</option>
													<option value="1"<c:if test="${list_attendanceSta=='1'}">selected="selected"</c:if>>
														迟到
													</option>
													<option value="2"<c:if test="${list_attendanceSta=='2'}">selected="selected"</c:if>>
														早退
													</option>
													<option value="3"<c:if test="${list_attendanceSta=='3'}">selected="selected"</c:if>>
														迟到、早退
													</option>
													<option value="5"<c:if test="${list_attendanceSta=='5'}">selected="selected"</c:if>>
														缺勤
													</option>
													<option value="4"<c:if test="${list_attendanceSta=='4'}">selected="selected"</c:if>>
														数据异常
													</option>
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
 		<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
			<d:col property="ATTENDANCE_USERNAME" class="display_centeralign"
				title="被排班人" />
			<d:col property="ATTENDANCE_ORGNAME" class="display_centeralign"
				title="被排班人部门" />
			<d:col property="ATTENDANCE_SCHEDULE_INTIME" class="display_leftalign"
				title="排班起始时间" />
			<d:col property="ATTENDANCE_SCHEDULE_OUTTIME" class="display_centeralign"
				title="排班结束时间" />
			<d:col property="ATTENDANCE_INTIME" class="display_centeralign"
			title="刷卡起始时间" />	
			<d:col property="ATTENDANCE_OUTTIME" class="display_centeralign"
			title="刷卡结束时间" />
			<d:col property="RES_BAK2" class="display_centeralign"
				title="记录日期" />
			<d:col class="display_centeralign" title="考勤判断">
				<c:if test="${row.RES_BAK1 == 0}">
					<span class="display_centeralign">正常</span>
				</c:if>
				<c:if test="${row.RES_BAK1 == 1}">
					<span class="display_centeralign">迟到</span>
				</c:if>
				<c:if test="${row.RES_BAK1 == 2}">
					<span class="display_centeralign">早退</span>
				</c:if>	
				<c:if test="${row.RES_BAK1 == 3}">
					<span class="display_centeralign">迟到、早退</span>
				</c:if>
				<c:if test="${row.RES_BAK1 == 5}">
					<span class="display_centeralign">缺勤</span>
				</c:if>	
				<c:if test="${row.RES_BAK1 == 4}">
					<span class="display_centeralign">数据异常</span>
				</c:if>										
			</d:col>												
		</d:table>	
	</h:page>
</form>	
	<div class="fkdj_botton_ls">
				<a class="fkdj_botton_right" href="javascript:exportAttendancePrepare()">导出excel</a>
			</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
		</div>
		
	</div>
	</body>
</html>