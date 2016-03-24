<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>套餐修改</title>	
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
		<script src="${ctx}/app/property/conferencePackage/js/modifyOneConferencePackge.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	
		function modifyOneConferencePackage(){
				$("#modifyform").submit();
				closeEasyWin("modifyOneConferencePackagePrepare");
			}
			
		function modifyConferencePackageCancel(){
			parent.closeModifyConferencePackage();
		}
						function cancelAddDevice() {
		$('#modifyform').resetForm();
	}
	</script>
</head>

<body style="background:#fff;">
<!-- -------------------------修改Block------------------------- -->
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

	<div class="form_right_move">
		
	<form id="modifyform" name="modifyform" action="modifyOneConferencePackage.act" method="post" target="main_frame_right">
	<input type="hidden" id="modify_conferencePackageId" name="modify_conferencePackageId" value="${view_conferencePackage.conferencePackageId}" />
	<div class="show_from" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    	<td width="20%"><font color="red">*</font>会议室名称</td>
    	<td width="35%" style="text-align: left; padding-left: 10px;">
    		<input name="modify_conferencePackageName" id="modify_conferencePackageName"  class="fkdj_from_input" type="text" value="${view_conferencePackage.conferencePackageName}"/>
    		<input type="hidden" id="old_modify_conferencePackageName" value="${view_conferencePackage.conferencePackageName}"/>
   		</td>
   		
	    <td width="10%"><font color="red">*</font>会场编号</td>
	    <td width="35%" style="text-align: left; padding-left: 10px;">
	    	<input name="modify_conferencePackageRoom" id="modify_conferencePackageRoom"  class="fkdj_from_input" type="text" value="${view_conferencePackage.conferencePackageRoom}"/>
	    	<input type="hidden" id="old_modify_conferencePackageRoom" value="${view_conferencePackage.conferencePackageRoom}"/>
	    </td>	     
   </tr>
   <tr>
   		<td><font color="red">*</font>会场地址</td>
	    <td style="text-align: left; padding-left: 10px;">
	    	<input name="modify_conferencePackageLocation" id="modify_conferencePackageLocation"  class="fkdj_from_input" type="text" value="${view_conferencePackage.conferencePackageLocation}"/>
	    </td>
   		<td ><font color="red">*</font>座位</td>
   		<td  style="text-align: left; padding-left: 10px;"><input name="modify_conferencePackageSeat" id="modify_conferencePackageSeat"  class="fkdj_from_input" type="text" value="${view_conferencePackage.conferencePackageSeat}"/>
  		</td>  		 
   </tr>
  
    <tr>   	
	     <td>其他设施</td>
   		 <td  style="text-align: left; padding-left: 10px;"><input name="modify_conferencePackageFacility" id="modify_conferencePackageFacility"  class="fkdj_from_input" type="text" value="${view_conferencePackage.conferencePackageFacility}"/>
  		 </td>
  		 <td><font color="red">*</font>价目</td>
	     <td  style="text-align: left; padding-left: 10px;"><input name="modify_conferencePackagePrice" id="modify_conferencePackagePrice"  class="fkdj_from_input" type="text" value="${view_conferencePackage.conferencePackagePrice}"/>
	     </td>
    	 	      
    </tr>
    <tr>
		<td valign="top"">会议室布局</td>
		<td colspan="3" style="text-align: left; padding-left: 10px;"><textarea id="modify_conferencePackageStyle" name="modify_conferencePackageStyle"  class="member_form_inp_textarea_modify" cols="65" rows="5">${view_conferencePackage.conferencePackageStyle}</textarea></td>
	</tr>
	
	<tr>
		<td valign="top">服务项目</td>
		<td colspan="3" style="text-align: left; padding-left: 10px;"><textarea id="modify_conferencePackageService" name="modify_conferencePackageService"  class="member_form_inp_textarea_modify" cols="65" rows="5">${view_conferencePackage.conferencePackageService }</textarea></td>
	</tr>
	

	<tr>
		<td>启用状态</td>
		<td colspan="3"><table width="30%" border="0" cellspacing="0" cellpadding="0" class="table_right_move">
	      <tr>    
	       <td>
	       		<c:choose>
	       			<c:when test="${view_conferencePackage.conferencePackageState == 0}">
	       				<input type="radio" name="modify_conferencePackageState" id="modify_conferencePackageState" checked="checked" value="0"/>停用
	       			</c:when>
	       			<c:otherwise>
	       				<input type="radio" name="modify_conferencePackageState" id="modify_conferencePackageState" value="0"/>停用
	       			</c:otherwise>
	       		</c:choose>
	       		<c:choose>
	       			<c:when test="${view_conferencePackage.conferencePackageState == 1}">
	       				<input type="radio" name="modify_conferencePackageState" id="modify_conferencePackageState" checked="checked" value="1"/>启用
	       			</c:when>
	       			<c:otherwise>
	       				<input type="radio" name="modify_conferencePackageState" id="modify_conferencePackageState" value="1"/>启用
	       			</c:otherwise>
	       		</c:choose>
	       </td>
	 	  </tr>
			</table>
		</td>
	</tr>
	</table>					
</div>
</form>
</div>
<!-- 

<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_left" onclick="saveData()">确定</a>
<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>

</div>
 -->

<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_right" onclick="saveData()">确定</a>

</div>



</div>
</div>
  
</body>
</html>
