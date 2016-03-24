<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>考勤详情信息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" type="text/css" />
	
	
	<script type="text/javascript">
		function viewAttendanceCancel(){
			closeViewAttendance();
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
	  	<td width="12%" style="padding-right:10px;">考勤编号</td>
	    	<td width="35%"><input name="view_attendanceId" id="view_attendanceId"  class="show_inupt" type="text" value="${view_attendance.attendanceId}" readonly="readonly" disabled="disabled"/>
	    </td>
	    <td width="19%" style="padding-right:10px;"></td>
	    <td width="35%"></td>  	     	    
	  </tr>
	  <tr>
	  	<td width="12%" style="padding-right:10px;">排班编号</td>
	    	<td width="35%"><input name="view_attendanceScheduleId" id="view_attendanceScheduleId"  class="show_inupt" type="text" value="${view_attendance.attendanceScheduleId}" readonly="readonly" disabled="disabled"/>
	    </td>
	    <td width="19%" style="padding-right:10px;">排班人</td>
   			 <td width="35%"><input name="view_attendanceScheduleAdminName" id="view_attendanceScheduleAdminName"  class="show_inupt" type="text" value="${view_attendance.attendanceScheduleAdminName}" readonly="readonly" disabled="disabled"/>
  		 </td>
	  </tr>
  
    <tr>    	   		
	    <td width="12%" style="padding-right:10px;">被排班人</td>
	    	<td width="35%"><input name="view_attendanceUserName" id="view_attendanceUserName"  class="show_inupt" type="text" value="${view_attendance.attendanceUserName}" readonly="readonly" disabled="disabled"/>
	    </td>
	    <td width="19%" style="padding-right:10px;">被排班人部门</td>
	    	<td width="35%"><input name="view_attendanceOrgName" id="view_attendanceOrgName"  class="show_inupt" type="text" readonly="readonly" value="${view_attendance.attendanceOrgName}" readonly="readonly" disabled="disabled"/>
	    </td>	     
   </tr>
   <tr>
   		<td width="12%" style="padding-right:10px;">排班日期</td>
	    <td width="35%"><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="view_attendanceScheduleInTime_dayStart" id="view_attendanceScheduleInTime_dayStart"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="<f:formatDate value="${view_attendance.attendanceScheduleInTime }" pattern="yyyy-MM-dd"/>" readonly="readonly" disabled="disabled"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="view_attendanceScheduleOutTime_dayEnd" id="view_attendanceScheduleOutTime_dayEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
							value="<f:formatDate value="${view_attendance.attendanceScheduleOutTime }" pattern="yyyy-MM-dd"/>" readonly="readonly" disabled="disabled"/></td>    
    		</tr>
			</table>
		</td>
	    
   		<td width="19%" style="padding-right:10px;">上班时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="view_attendanceScheduleInTime_hourStart" id="view_attendanceScheduleInTime_hourStart" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="<f:formatDate value="${view_attendance.attendanceScheduleInTime }" pattern="HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="view_attendanceScheduleOutTime_hourEnd" id="view_attendanceScheduleOutTime_hourEnd" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" 
							value="<f:formatDate value="${view_attendance.attendanceScheduleOutTime }" pattern="HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>    
    		</tr>
			</table>
		</td> 
   </tr>
   <tr>
   		<td width="12%" style="padding-right:10px;">记录时间</td>
   		<td width="35%"><input name="view_attendanceScheduleRecordTime" id="view_attendanceScheduleRecordTime"  class="show_inupt" type="text" value="${view_attendance.resBak2}" readonly="readonly" disabled="disabled"/>
	    </td>
   		<td width="19%" style="padding-right:10px;">考勤时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="view_attendanceInTime" id="view_attendanceInTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="<f:formatDate value="${view_attendance.attendanceInTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" style="width:120px;" name="view_attendanceOutTime" id="view_attendanceOutTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="<f:formatDate value="${view_attendance.attendanceOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>    
    		</tr>
			</table>
		</td>
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