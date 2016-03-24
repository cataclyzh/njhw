<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>考勤清单导出</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" type="text/css" />
	
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/property/block.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/property/calendarCompare.js" type="text/javascript" charset="UTF-8"></script>
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
		function exportAttendanceCancel(){
			closeEasyWin("exportAttendancePrepare");
		}
		
		function exportAttendance(){
			$('#exportform').submit();
			closeEasyWin("exportAttendancePrepare");
		}
		
		$(document).ready(function() {
		
				jQuery.validator.methods.diffDays = function(value, element, param) {
					var startTime = jQuery(param).val().split('-');
					var endTime = $("#export_attendanceOutTime").val().split('-');
					
					var start = new Date(startTime[0],startTime[1]-1,startTime[2],0,0);
					var end = new Date(endTime[0],endTime[1]-1,endTime[2],0,0);					
					var result = (end.getTime() - start.getTime())/86400000;
					if(result>93){
						return false;
					}else{
						return true;
					}
						
				};

				jQuery.validator.methods.compareDate = function(value, element, param) {
			        var beginTime = jQuery(param).val();
			        if(value!=""){
			        return beginTime < value;
			        }else {
			        return true;
			        }
			    }; 
		    
			
				$("#exportform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {						
					export_attendanceInTime:{
						required: true
					},
					
					export_attendanceOutTime:{
							required: true,
							compareDate: "#export_attendanceInTime",
		 					diffDays: "#export_attendanceInTime"
						}
						
					},
					messages:{
						
						export_attendanceInTime:{
							required: "请输入开始时间"
						},
						
						export_attendanceOutTime:{
							required: "请输入结束时间",
							compareDate: "结束时间必须晚于开始时间",
							diffDays: "最多只能导出3个月的数据"
						}
					}
				});
			});    
	
		
			function saveData(){		
			if (showRequest()) {
			      
				$('#exportform').submit();
				closeEasyWin("exportAttendancePrepare");
				
			}
		}
		
			function showRequest(){
				 return $("#exportform").valid();
			}
	</script>
</head>

<body style="background:#fff;">
<!-- -------------------------查看Block------------------------- -->
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

	<div class="form_right_move">
		
	<form id="exportform" name="exportform" action="exportAttendance.act" method="post" target="main_frame_right">
	<div class="show_from" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  
   <tr>
   		<td width="26%" style="padding-right:20px;"><font color="red">*</font>请选择要导出的考勤时间段</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" name="export_attendanceInTime" id="export_attendanceInTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="${export_attendanceInTime}"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" name="export_attendanceOutTime" id="export_attendanceOutTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="${export_attendanceOutTime}"/></td>    
    		</tr>
			</table>
		</td>
   </tr>	
</table>					
</div>
</form>
</div>

<!-- 

<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_left" onclick="saveData()">确定</a> <a class="fkdj_botton_right" href="javascript:exportAttendanceCancel()">取消</a></div>

 -->
<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_right" onclick="saveData()">确定</a> </div>

</div>
</div>
  
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>