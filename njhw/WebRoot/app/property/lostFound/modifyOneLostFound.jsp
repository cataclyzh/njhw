<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>失物信息修改</title>	
	<%@ include file="/common/include/meta.jsp" %>
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
		<script src="${ctx}/app/property/lostFound/js/modifyOneLostFound.js" type="text/javascript"></script>
		
	<script type="text/javascript">
			
			function modifyOneLostFound(){
				$("#modifyform").submit();
				closeEasyWin("modifyOneLostFoundPrepare");
			}
			
			function modifyLostFoundCancel(){
				closeEasyWin("modifyOneLostFoundPrepare");
			}
	</script>
</head>

<body style="background: #fff;">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >
	<form id="modifyform" name="modifyform" action="modifyOneLostFound.act" method="post" target="main_frame_right">
		<div class="show_from">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr style="display: none;">
	    <td width="12%"><font color="red">*</font>登记编号</td>
	    	<td width="50%" style="text-align: left;padding-left:10px;"><input name="modify_lostFoundId" id="modify_conferenceId"  class="show_inupt" type="text" value="${view_lostfound.lostFoundId}" readonly="readonly"/>
	    </td>    
	   
	  </tr>
  
    <tr>
    	 <td width="19%"><font color="red">*</font>标题</td>
    		<td width="35%" style="text-align: left;padding-left:10px;"><input name="modify_lostFoundTitle" id="modify_lostFoundTitle"  class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundTitle}"/>
   		</td>
	    <td width="19%"><font color="red">*</font>失物名称</td>
	    	<td width="35%" style="text-align: left;padding-left:10px;"><input name="modify_lostFoundThingName" id="modify_lostFoundThingName"  class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundThingName}"/>
	    </td>    
	    
   </tr>
   
   <tr>
   		<td><font color="red">*</font>失物上交者</td>
	    	<td style="text-align: left;padding-left:10px;"><input name="modify_lostFoundPickUser" id="modify_lostFoundPickUser"  class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundPickUser}"/>
	    </td>
   		 <td ><font color="red">*</font>上交者单位</td>
   			 <td style="text-align: left;padding-left:10px;"><input name="modify_lostFoundPickUserOrg" id="modify_lostFoundPickUserOrg"  class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundPickUserOrg}"/>
  		 </td>
  		 
   </tr>
  
    <tr>
    	 <td><font color="red">*</font>捡到地点</td>
	    	<td style="text-align: left;padding-left:10px;"><input name="modify_lostFoundLocation" id="modify_lostFoundLocation"  class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundLocation}"/>
	    </td>
	    
	     <td><font color="red">*</font>失物登记者</td>
   			 <td style="text-align: left;padding-left:10px;"><input name="modify_lostFoundKeeper" id="modify_lostFoundKeeper"  class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundKeeper}"/>
  		 </td>   
    </tr>
    
    <tr>
   		<td class="fkdj_name"><font color="red">*</font>登记时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="text-align: left;padding-left:10px;"><input type="text" class="fkdj_from_input" name="modify_lostFoundIntime" id="modify_lostFoundIntime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'${nowDate}'})"
							value="<f:formatDate value="${view_lostfound.lostFoundIntime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/></td>				    
    		</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">详细描述</td>
		<td colspan="3" style="text-align: left;padding-left:10px;"><textarea id="modify_lostFoundDetail" name="modify_lostFoundDetail" cols="80" rows="5">${view_lostfound.lostFoundDetail }</textarea></td>
	</tr>
</table>					
		</div>
	</form>					
</div>

<!-- 
<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" onclick="saveData()">确定</a>
				<a class="fkdj_botton_right" onclick="javascript:modifyLostFoundCancel()">取消</a>
			</div>

 -->
<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			</div>
		
</div>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>