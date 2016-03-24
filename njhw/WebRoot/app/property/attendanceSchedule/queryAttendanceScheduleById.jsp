<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>考勤排班详情信息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" type="text/css" />
	
	
	<script type="text/javascript">
		function viewAttendanceScheduleCancel(){
			parent.closeViewAttendanceSchedule();
		}
		
	</script>
</head>

<body style="background:#fff;">
<!-- -------------------------查看Block------------------------- -->
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

	<div class="form_right_move">
		
	<form id="viewform" name="viewform" action="" method="post">
	<div class="show_from" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="12%" style="padding-right:10px;">排班编号</td>
	    	<td width="35%"><input name="view_attendanceScheduleId" id="view_attendanceScheduleId"  class="show_inupt" type="text" value="${view_attendanceSchedule.attendanceScheduleId}" readonly="readonly" disabled="disabled"/>
	    </td>
	    <td width="12%" style="padding-right:10px;">排班人</td>
   			 <td width="35%"><input name="view_attendanceScheduleAdminName" id="view_attendanceScheduleAdminName"  class="show_inupt" type="text" value="${view_attendanceSchedule.attendanceScheduleAdminName}" readonly="readonly" disabled="disabled"/>
  		 </td> 	    
	  </tr>
  
    <tr>   		
	    <td width="12%" style="padding-right:10px;">被排班人</td>
	    	<td width="35%"><input name="view_attendanceScheduleUserName" id="view_attendanceScheduleUserName"  class="show_inupt" type="text" value="${view_attendanceSchedule.attendanceScheduleUserName}" readonly="readonly" disabled="disabled"/>
	    </td>
	    <td width="19%" style="padding-right:10px;">被排班人部门</td>
	    	<td width="35%"><input name="view_attendanceScheduleOrgName" id="view_attendanceScheduleOrgName"  class="show_inupt" type="text" readonly="readonly" value="${view_attendanceSchedule.attendanceScheduleOrgName}" readonly="readonly" disabled="disabled"/>
	    </td>	     
   </tr>
   
    <tr>
   		<td width="12%" style="padding-right:10px;">排班日期</td>
	    <td width="35%"><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="show_inupt" style="width:120px;" name="view_attendanceScheduleInTime_dayStart" id="view_attendanceScheduleInTime_dayStart"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="<f:formatDate value="${view_attendanceSchedule.attendanceScheduleInTime }" pattern="yyyy-MM-dd"/>" readonly="readonly" disabled="disabled"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="show_inupt" style="width:120px;" name="view_attendanceScheduleOutTime_dayEnd" id="view_attendanceScheduleOutTime_dayEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="<f:formatDate value="${view_attendanceSchedule.attendanceScheduleOutTime }" pattern="yyyy-MM-dd"/>" readonly="readonly" disabled="disabled"/></td>    
    		</tr>
			</table>
		</td>
	    
   		<td width="19%" style="padding-right:10px;">上班时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="show_inupt" style="width:120px;" name="view_attendanceScheduleInTime_hourStart" id="view_attendanceScheduleInTime_hourStart" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="${view_attendanceSchedule.resBak1}" readonly="readonly" disabled="disabled"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="show_inupt" style="width:120px;" name="view_attendanceScheduleOutTime_hourEnd" id="view_attendanceScheduleOutTime_hourEnd" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="${view_attendanceSchedule.resBak2}" readonly="readonly" disabled="disabled"/></td>    
    		</tr>
			</table>
		</td>   		  			 
   </tr>
   <tr>
		<td valign="top" width="12%" style="padding-right:10px;">排班备注</td>
		<td colspan="3"><textarea id="view_resBak4" name="view_resBak4"  class="show_textarea" cols="80" rows="15" readonly="readonly" disabled="disabled">${view_attendanceSchedule.resBak4 }</textarea></td>
	</tr>	
</table>					
</div>
</form>
</div>
</div>
</div>
  
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>