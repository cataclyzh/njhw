<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ page import="java.util.*,com.opensymphony.xwork2.util.*,com.cosmosource.app.attendance.vo.*" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/common/include/meta.jsp"%>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
       <link href="${ctx}/styles/property/css.css" rel="stylesheet"		type="text/css" />
	<link href="${ctx}/styles/property/block.css" rel="stylesheet"		type="text/css" />
	<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet"	type="text/css" />
	<link href="${ctx}/styles/property/css_body.css" rel="stylesheet"	type="text/css" />
		
	<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script src="js/attendance.js" type="text/javascript"></script>
	
	<style type="text/css">
		#hide_table{
			height:40px;
			padding: 10px 0 0 30px;
		}
		
		#hide_table label{
			font-size:14px;
			color:#808080;
			margin: 0 5px 0 0;
		}
		
		#hide_table input, select{
			width:163px;
			height:22px !important;
			line-height:22px;
		}
	</style>
	
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
			
			// 为inputForm注册validate函数
			$("#queryForm").validate({
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				onsubmit: true,
				rules: {
					endTime:{
						compareDate: "#startTime"
					}
				},
				messages:{
					endTime:{
						compareDate: "结束时间必须晚于开始时间"
					}
				}
			});
			
			//$('#selectDepartment').val($('#department').val());
			
		});    
		
		function popWindow(id){
			var url = _ctx + "/app/attendance/approveDetail.act?id="+id;
			//alert(url);
			//windowDialog("考勤明细",url,"500","450",false);
			windowDialog("申请审批",url,"500","450",false);
		}
		
		function popWindow_show(id){
			var url = _ctx + "/app/attendance/approveDetailShow.act?id="+id;
			//alert(url);
			//windowDialog("考勤明细",url,"500","450",false);
			windowDialog("申请审批",url,"500","450",false);
		}
		
		function popWindow_show_pass(id){
			var url = _ctx + "/app/attendance/approveDetailShowPass.act?id="+id;
			//alert(url);
			//windowDialog("考勤明细",url,"500","450",false);
			windowDialog("申请审批",url,"500","350",false);
		}
		
		function queryInfo(){
			//设置查询参数
			$("#startTime").val($("#startTime_show").val());
			$("#endTime").val($("#endTime_show").val());
			$("#status").val($("#status_show").val());
			$("#type").val($("#type_show").val());
			
			var a = $("#startTime").val().replace(/-/g, "/");
			var b = $("#endTime").val().replace(/-/g, "/");
			var starttime = new Date(a);
			var lktime = new Date(b);
			if (Date.parse(starttime) - Date.parse(lktime) > 0) {
				alert('结束时间不能小于开始时间!');
		        return false;
		    }else{
		    	querySubmit();
		    }
/* 			if (showRequest()) {
				querySubmit();
			} */
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
			<div class="bgsgl_border_right">说明记录</div>
		</div>
		<div class="bgsgl_conter" style="height:530px">
	 		<div class="fkdj_from">
				<form id="queryForm" name="queryForm" action="${ctx}/app/attendance/wodeshenqing.act" method="post">
				<input type="hidden" id="startTime" name="startTime" value="${startTime }" />
				<input type="hidden" id="endTime" name="endTime" value="${endTime }" />
				<input type="hidden" id="status" name="status" value="${status }" />
				<input type="hidden" id="type" name="type" value="${type }" />
				
				<h:panel id="query_panel" width="100%" title="查询条件">
					<div id="hide_table">
						<label style="margin:0 5px 0 50px">时间</label>
						<input type="text" name="startTime_show" id="startTime_show" value="${startTime }" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						<label>至</label>
						<input type="text" name="endTime_show" id="endTime_show" value="${endTime }" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						<label style="margin:0 5px 0 20px">状态</label>
						<select id="status_show" name="status_show">
							<option value="all">全部</option>
							<c:forEach items="${statusList}" var="obj">
									<option value="${obj.id }" 
									<c:if test='${status == obj.id }'> selected="selected" </c:if>>
										${obj.name }
									</option>
							</c:forEach>
						</select>
						<label style="margin:0 5px 0 20px">类型</label>
						<select id="type_show" name="type_show">
							<option value="all">全部</option>
							<c:forEach items="${typeList}" var="item">
								<option value="${item.id }" 
								<c:if test='${type == item.id }'> selected="selected" </c:if>>
									${item.name }
								</option>
							</c:forEach>
						</select>
						<a style="margin:0 0 0 15px" class="property_query_button" href="javascript:void(0);"
													onmousemove="this.style.background='#FFC600'"
													onmouseout="this.style.background='#8090b4'"
													onclick="queryInfo()">查询 </a>
					</div>
				</h:panel>
				<s:textfield name="page.pageSize" id="pageSize" value="10"  theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
 						
 				<h:page id="list_panel" width="100%" title="结果列表">
 				<d:table name="page.result" id="row" uid="row" export="false" cellspacing="0" cellpadding="0" class="table">
 				
<%-- 					<d:col property="USER_NAME" class="display_centeralign" title="申请人" />
 --%>					
 					<d:col property="START_DATE" class="display_centeralign" title="开始时间" />
					<d:col property="END_DATE" class="display_centeralign" title="结束时间" />
					<d:col property="TYPE" class="display_leftalign" title="类型" />
					<d:col property="USER_DESC" class="display_centeralign" title="说明" maxLength="14"/>
					<d:col property="STATUS" class="display_centeralign" title="状态" />	
					<d:col class="display_centeralign" style="width:100px" title="查看详细">
						<c:if test="${row.STATUS eq '申请中'}">
								<label>--</label>
					</c:if>
					<c:if test="${row.STATUS eq '申请通过'}">
						<a
								onclick="javascript:popWindow_show_pass('${row.ID}')"
								style="cursor:pointer">
								[审批通过]
						</a>
								
					</c:if>
					<c:if test="${row.STATUS eq '申请驳回'}">
						<a
								onclick="javascript:popWindow_show('${row.ID}')"
								style="cursor:pointer">
								[申请驳回]
						</a>
					</c:if>
					</d:col>
				</d:table>	
				</h:page>
				</form>
				<!--	
				<div class="fkdj_botton_ls">
					 <a class="fkdj_botton_right" href="javascript:exportAttendancePrepare()">导出excel</a>  
				</div>
				-->
				<!-- <div class="bgsgl_clear"></div>  -->
			</div>
		</div>
	</div>
	</body>
</html>