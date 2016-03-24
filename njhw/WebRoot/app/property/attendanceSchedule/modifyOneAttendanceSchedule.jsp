<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>考勤排班修改</title>	
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
	
		function modifyOneAttendanceSchedule(){
				$('#modifyform').submit();
				closeEasyWin("modifyOneAttendanceSchedulePrepare");
			}
			
		function modifyAttendanceScheduleCancel(){
			closeEasyWin("modifyOneAttendanceSchedulePrepare");
		}
			
	$(document).ready(function() {
	
		var dayFlag="";
		jQuery.validator.methods.compareDate = function(value, element, param) {
	        var beginDay = jQuery(param).val();
	        if(beginDay<value){
	            dayFlag="1";
	            return true;
	        }else if(beginDay==value){
	            dayFlag="2";
	            return true;
	        }else{
	            dayFlag="";
	            return false;
	        }
	    };
	    jQuery.validator.methods.compareForce = function(value, element, param) {
	        var beginForceDay = $("#modify_attendanceScheduleInTime_dayStart").val();
	        var endForceDay = $("#modify_attendanceScheduleOutTime_dayEnd").val();
	        if(beginForceDay<endForceDay){
	            dayFlag="1";
	            return true;
	        }else if(beginForceDay==endForceDay){
	            dayFlag="2";
	            return true;
	        }else{
	            dayFlag="";
	            return false;
	        }
	    };
	    jQuery.validator.methods.compareNowDate = function(value, element, param) {
			return value >= "${nowDate}";
	    };
	    
	    jQuery.validator.methods.compareNowTime = function(value, element, param) {
	    	var myDay = jQuery(param).val();
	    	var myTime = myDay+" "+value;
	    	var ss="${nowTime}";
			return myTime >= "${nowTime}";
	    };
	      jQuery.validator.methods.compareTime = function(value, element, param) {
			var beginTime = jQuery(param).val();
			if(dayFlag==""){
			   return false;
			}else if(dayFlag=="1"){
			         if(beginTime!=value){
			                return true;
			          }else{
			                return false;
			          }
			}else if(dayFlag=="2"){
			          if(beginTime<value){
			                return true;
			          }else if(beginTime>value){
			          	    return false;
			          }else if(beginTime==value){
			                return false;
			          }
			}else{
			      return false;
			
			}
			
	    };		

			$("#modifyform").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {	
					
				modify_attendanceScheduleInTime_dayStart:{
					   required:true					   
					},
					
				modify_attendanceScheduleOutTime_dayEnd:{
					required:true,
					//compareNowDate:true,
					//compareDate: "#modify_attendanceScheduleInTime_dayStart"
				},
				
				modify_attendanceScheduleInTime_hourStart:{
					required:true
				},
				
				modify_attendanceScheduleOutTime_hourEnd:{
					   required:true,
					   //compareNowTime:"#modify_attendanceScheduleOutTime_dayEnd",
					   compareForce:true,
	 					//compareTime:"#modify_attendanceScheduleInTime_hourStart"
					},
				modify_resBak4:{
					   maxlength:255
					}
					
				},
				messages:{
					
					modify_attendanceScheduleInTime_dayStart:{
						required:"请填写排班日期起始日期"
					},
					
					modify_attendanceScheduleOutTime_dayEnd:{
						required:"请填写排班日期结束日期",
						//compareNowDate:"排班日期必须不晚于当前日期",
						//compareDate: "结束日期必须晚于开始日期"
					},
					
					modify_attendanceScheduleInTime_hourStart:{
						required:"请填写上班开始时间"
					},
					
					modify_attendanceScheduleOutTime_hourEnd:{
					    required:"请填写上班结束时间",
					    //compareNowTime:"排班时间必须晚于当前时间",
					    compareForce:"",
						//compareTime:"排班时间组合的结果中排班开始时间晚于排班结束时间"
					},
					modify_resBak4:{
						maxlength:"填写的备注字符过长"
					}
				}
			});
		});    
	

 	var isExistCheckFlag = "";
	function existCheck() {		
	 			
	 			var scheduleInTime = $("#modify_attendanceScheduleInTime_dayStart").val()+" "+$("#modify_attendanceScheduleInTime_hourStart").val();
	 			var scheduleHourEnd = $("#modify_attendanceScheduleOutTime_hourEnd").val();
	 			var scheduleOutTime = $("#modify_attendanceScheduleOutTime_dayEnd").val()+" "+scheduleHourEnd; 	
	 			var attendanceScheduleUserId = 	$("#modify_attendanceScheduleUserId").val();
	 			var attendanceScheduleId = $("#modify_attendanceScheduleId").val();	
	 			
	 			if(scheduleInTime!=null && ""!= scheduleInTime && scheduleOutTime!=null && ""!=scheduleOutTime && scheduleHourEnd!=null && ""!=scheduleHourEnd && attendanceScheduleUserId!=null && ""!=attendanceScheduleUserId){	 			
	 				
		             $.ajax({
			             type:"POST",
			             url:"${ctx}/app/pro/modifyAttendanceScheduleIsExist.act",
			             data:{scheduleInTime:scheduleInTime,scheduleOutTime:scheduleOutTime,attendanceScheduleUserId:attendanceScheduleUserId,attendanceScheduleId:attendanceScheduleId},
			             dataType:'text',
					     async : false,
					     success: function(responseText){
			            			if(responseText=='fail') { 
										easyAlert("提示信息", "被排班人在该排班时间段已有排班！","info", function(){
											isExistCheckFlag = "1";
											window.location.reload();
										});
								 	}else if(responseText=='success') {
								 		isExistCheckFlag = "2";
								 	}				        	 
				 			}
			  			});
			  			
			  			
		  		}				              
    	}
	
		function saveData(){
			var flag = isAddTime();

			if("1" == flag){
				easyAlert('提示信息','夜班：请将排班日期开始时间设置小于结束时间!','info',function(){
					return;
				});
			}else if("2" == flag){
				easyAlert('提示信息','白班：请将排班上班开始时间设置小于结束时间!','info',function(){
					return;
				});
			}else{
				if (showRequest()) {
					//existCheck();
					$('#modifyform').submit();
					closeEasyWin("modifyOneAttendanceSchedulePrepare");
					/*
					if(isExistCheckFlag == "2"){
						$('#modifyform').submit();
						closeEasyWin("modifyOneAttendanceSchedulePrepare");
		
					}else if(isExistCheckFlag == "1"){
						alert("请检查排班时间！");
					}*/
				}
			}
	}
		function isAddTime(){
			if($("#modify_attendanceScheduleInTime_hourStart").val() != "" && $("#modify_attendanceScheduleInTime_hourEnd").val() != "" && $("#modify_attendanceScheduleInTime_dayStart").val() != "" && $("#modify_attendanceScheduleInTime_dayEnd").val() != ""){
				if($("#modify_attendanceScheduleInTime_hourStart").val()>$("#modify_attendanceScheduleOutTime_hourEnd").val()){
					if($("#modify_attendanceScheduleInTime_dayStart").val()>=$("#modify_attendanceScheduleOutTime_dayEnd").val()){
						return 1;
					}
				}else if($("#modify_attendanceScheduleInTime_dayStart").val()<=$("#modify_attendanceScheduleOutTime_dayEnd").val()){
					if($("#modify_attendanceScheduleInTime_hourStart").val()>=$("#modify_attendanceScheduleOutTime_hourEnd").val()){
						return 2;
					}
				}else{
					return true;
				}
			}else{
				return false;
			}
		}
		function showRequest(){
			 return $("#modifyform").valid();
		}
	
	</script>
</head>

<body style="background:#fff;">
<!-- -------------------------修改Block------------------------- -->
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >
	<div class="form_right_move">
		
	<form id="modifyform" name="modifyform" action="modifyOneAttendanceSchedule.act" method="post" target="main_frame_right">
	<div class="show_from" >
	
	<input type="hidden" name="list_attendanceScheduleUserName" id="list_attendanceScheduleUserName" value="${param.list_attendanceScheduleUserName }" />
	<input type="hidden" name="list_attendanceScheduleOrgName" id="list_attendanceScheduleOrgName" value="${param.list_attendanceScheduleOrgName }" />
	<input type="hidden" name="list_attendanceScheduleState" id="list_attendanceScheduleState" value="${param.list_attendanceScheduleState }" />
	<input type="hidden" name="pageNum" id="pageNum" value="${param.pageNum }" />
	<input type="hidden" name="list_attendanceScheduleInTime" id="list_attendanceScheduleInTime" value="${param.list_attendanceScheduleInTime }" />
	<input type="hidden" name="list_attendanceScheduleOutTime" id="list_attendanceScheduleOutTime" value="${param.list_attendanceScheduleOutTime }" />
	<input type="hidden" name="res_bak1" id="res_bak1" value="${param.res_bak1 }" />
	<input type="hidden" name="res_bak2" id="res_bak2" value="${param.res_bak2 }" />
	
	<input type="hidden" name="modify_attendanceScheduleUserId" id="modify_attendanceScheduleUserId"
					value="${view_attendanceSchedule.attendanceScheduleUserId }" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	    <td width="15%" style="padding-right:10px;">排班编号</td>
	    	<td width="35%"><input name="modify_attendanceScheduleId" id="modify_attendanceScheduleId"  class="show_inupt" type="text" value="${view_attendanceSchedule.attendanceScheduleId}" readonly="readonly"/>
	    </td>
	    <td width="18%" style="padding-right:10px;">排班人</td>
   			 <td width="35%"><input name="modify_attendanceScheduleAdminName" id="modify_attendanceScheduleAdminName"  class="show_inupt" type="text" value="${view_attendanceSchedule.attendanceScheduleAdminName}" readonly="readonly"/>
  		 </td>  	    
	 </tr>
  
    <tr>   		
	    <td width="15%" style="padding-right:10px;">被排班人</td>
	    	<td width="35%"><input name="modify_attendanceScheduleUserName" id="modify_attendanceScheduleUserName"  class="show_inupt" type="text" value="${view_attendanceSchedule.attendanceScheduleUserName}" readonly="readonly"/>
	    </td>
	    <td width="18%" style="padding-right:10px;">被排班人部门</td>
	    	<td width="35%"><input name="modify_attendanceScheduleOrgName" id="modify_attendanceScheduleOrgName"  class="show_inupt" type="text" readonly="readonly" value="${view_attendanceSchedule.attendanceScheduleOrgName}" readonly="readonly"/>
	    </td>	     
   </tr>
   <tr>
   		<td width="12%" style="padding-right:10px;">排班日期</td>
	    <td width="35%"><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="modify_attendanceScheduleInTime_dayStart" id="modify_attendanceScheduleInTime_dayStart"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="<f:formatDate value="${view_attendanceSchedule.attendanceScheduleInTime }" pattern="yyyy-MM-dd"/>"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="modify_attendanceScheduleOutTime_dayEnd" id="modify_attendanceScheduleOutTime_dayEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="<f:formatDate value="${view_attendanceSchedule.attendanceScheduleOutTime }" pattern="yyyy-MM-dd"/>"/></td>    
    		</tr>
			</table>
		</td>
	    
   		<td width="19%" style="padding-right:10px;">上班时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="modify_attendanceScheduleInTime_hourStart" id="modify_attendanceScheduleInTime_hourStart" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="${view_attendanceSchedule.resBak1}"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="modify_attendanceScheduleOutTime_hourEnd" id="modify_attendanceScheduleOutTime_hourEnd" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="${view_attendanceSchedule.resBak2}"/></td>    
    		</tr>
			</table>
		</td>   		  			 
   </tr>
   <tr>
		<td valign="top" width="15%" style="padding-right:10px;">排班备注</td>
		<td colspan="3" style="text-align: left;"><textarea id="modify_resBak4" name="modify_resBak4"  class="member_form_inp_textarea_modify" cols="72" rows="15">${view_attendanceSchedule.resBak4}</textarea></td>
	</tr>
</table>					
</div>
</form>
</div>

<!-- 
<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_left" href="javascript:saveData()">确定</a> <a class="fkdj_botton_right" href="javascript:modifyAttendanceScheduleCancel()">取消</a></div>


 -->

<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_right" href="javascript:saveData()">确定</a> </div>


</div>
</div>
  
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>