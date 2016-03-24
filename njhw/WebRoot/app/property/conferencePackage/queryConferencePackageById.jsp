<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>套餐详情信息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" type="text/css" />
	
	
	<script type="text/javascript">
		function viewConferencePackageCancel(){
			parent.closeViewConferencePackage();
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
    	<td width="12%" style="padding-right:10px;">会议室名称</td>
    		<td width="35%"><input name="view_conferencePackageName" id="view_conferencePackageName"  class="show_inupt" type="text" value="${view_conferencePackage.conferencePackageName}" readonly="readonly" disabled="disabled"/>
   		</td>
   		
	    <td width="19%" style="padding-right:10px;">会场编号</td>
	    	<td width="35%"><input name="view_conferencePackageRoom" id="view_conferencePackageRoom"  class="show_inupt" type="text" value="${view_conferencePackage.conferencePackageRoom}" readonly="readonly" disabled="disabled"/>
	    </td>	     
   </tr>
   <tr>
   		<td width="12%" style="padding-right:10px;">会场地址</td>
	    	<td width="35%"><input name="view_conferencePackageLocation" id="view_conferencePackageLocation"  class="show_inupt" type="text" readonly="readonly" value="${view_conferencePackage.conferencePackageLocation}" readonly="readonly" disabled="disabled"/>
	    </td>
   		<td width="19%" style="padding-right:10px;">座位</td>
   			 <td width="35%"><input name="view_conferencePackageSeat" id="view_conferencePackageSeat"  class="show_inupt" type="text" value="${view_conferencePackage.conferencePackageSeat}" readonly="readonly" disabled="disabled"/>
  		 </td>  		 
   </tr>
  
    <tr>   	
	     <td width="12%" style="padding-right:10px;">其他设施</td>
   			 <td width="35%"><input name="view_conferencePackageFacility" id="view_conferencePackageFacility"  class="show_inupt" type="text" value="${view_conferencePackage.conferencePackageFacility}" readonly="readonly" disabled="disabled"/>
  		 </td> 
	    <td width="19%" style="padding-right:10px;">价目</td>
	    	<td width="35%"><input name="view_conferencePackagePrice" id="view_conferencePackagePrice"  class="show_inupt" type="text" value="${view_conferencePackage.conferencePackagePrice}" readonly="readonly" disabled="disabled"/>
	    </td>	      
    </tr>
   <tr>
	    <td width="19%" style="padding-right:10px;">会议室布局</td>
	    	<td width="35%"><input name="view_conferencePackageStyle" id="view_conferencePackageStyle"  class="show_inupt" type="text" value="${view_conferencePackage.conferencePackageStyle}" readonly="readonly" disabled="disabled"/>
	    </td>    	    
	  </tr>
	
	<tr>
		<td valign="top" width="12%" style="padding-right:10px;">服务项目</td>
		<td colspan="3"><textarea id="view_conferencePackageService" name="view_conferencePackageService"  class="show_textarea" cols="80" rows="15" readonly="readonly" disabled="disabled">${view_conferencePackage.conferencePackageService }</textarea></td>
		<td class="fkdj_name"><br></td>
    	<td>&nbsp;</td>						
	</tr>
	

	<tr>
		<td width="19%" style="padding-right:10px;">套餐启用状态</td>
		<td><table width="50%" border="0" cellspacing="0" cellpadding="0" class="table_right_move">
	      <tr>    
	       <td>
	       		<c:choose>
	       			<c:when test="${view_conferencePackage.conferencePackageState == 0}">
	       				<input type="radio" name="view_conferencePackageState" id="view_conferencePackageState" checked="checked" disabled="disabled"/>停用
	       			</c:when>
	       			<c:otherwise>
	       				<input type="radio" name="view_conferencePackageState" id="view_conferencePackageState" disabled="disabled"/>停用
	       			</c:otherwise>
	       		</c:choose>
	       		<c:choose>
	       			<c:when test="${view_conferencePackage.conferencePackageState == 1}">
	       				<input type="radio" name="view_conferencePackageState" id="view_conferencePackageState" checked="checked" disabled="disabled"/>启用
	       			</c:when>
	       			<c:otherwise>
	       				<input type="radio" name="view_conferencePackageState" id="view_conferencePackageState" disabled="disabled"/>启用
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
</div>
</div>
  
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>