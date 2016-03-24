<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>失物详情信息</title>	
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
	
	
	<script type="text/javascript">
		function viewLostFoundCancel(){
			parent.closeViewLostFound();
		}
		
	</script>
</head>

<body style="background:#fff;">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

	<div class="form_right_move">
		
	<form id="viewform" name="viewform" action="" method="post">
	<div class="show_from" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    	<td width="12%" style="padding-right:10px;">标题</td>
    		<td width="35%"><input name="view_lostFoundTitle" id="view_lostFoundTitle"  class="show_inupt" type="text" value="${view_lostfound.lostFoundTitle}" readonly="readonly" disabled="disabled"/>
   		</td>
   		
	    <td width="19%" style="padding-right:10px;">失物名称</td>
	    	<td width="35%"><input name="view_lostFoundThingName" id="view_lostFoundThingName"  class="show_inupt" type="text" value="${view_lostfound.lostFoundThingName}" readonly="readonly" disabled="disabled"/>
	    </td>	     
   </tr>
   <tr>
   		<td width="12%" style="padding-right:10px;">失物上交者</td>
	    	<td width="35%"><input name="view_lostFoundPickUser" id="view_lostFoundPickUser"  class="show_inupt" type="text" readonly="readonly" value="${view_lostfound.lostFoundPickUser}" readonly="readonly" disabled="disabled"/>
	    </td>
   		<td width="19%" style="padding-right:10px;">上交者单位</td>
   			 <td width="35%"><input name="view_lostFoundPickUserOrg" id="view_lostFoundPickUserOrg"  class="show_inupt" type="text" value="${view_lostfound.lostFoundPickUserOrg}" readonly="readonly" disabled="disabled"/>
  		 </td>  		 
   </tr>
  
    <tr>
    	 <td width="12%" style="padding-right:10px;">捡到地点</td>
	    	<td width="35%"><input name="view_lostFoundLocation" id="view_lostFoundLocation"  class="show_inupt" type="text" value="${view_lostfound.lostFoundLocation}" readonly="readonly" disabled="disabled"/>
	    </td>
	    
	     <td width="19%" style="padding-right:10px;">失物登记者</td>
   			 <td width="35%"><input name="view_lostFoundKeeper" id="view_lostFoundKeeper"  class="show_inupt" type="text" value="${view_lostfound.lostFoundKeeper}" readonly="readonly" disabled="disabled"/>
  		 </td>   
    </tr>
    
    <tr>
   		<td width="12%" style="padding-right:10px;">登记时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="show_inupt" name="view_lostFoundIntime" id="view_lostFoundIntime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="<f:formatDate value="${view_lostfound.lostFoundIntime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>   
    		</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td valign="top" width="12%" style="padding-right:10px;">详细描述</td>
		<td colspan="3"><textarea id="view_lostFoundDetail" name="view_lostFoundDetail"  class="show_textarea" cols="80" rows="15" readonly="readonly" disabled="disabled">${view_lostfound.lostFoundDetail }</textarea></td>
	</tr>
	
	<c:if test="${view_lostfound.lostFoundState == 1}">
		<tr>
			<td width="12%" style="padding-right:10px;">失物认领人</td>
			<td width="35%"><input name="view_lostFoundLostUser" id="view_lostFoundLostUser"  class="show_inupt" type="text" value="${view_lostfound.lostFoundLostUser}" readonly="readonly" disabled="disabled"/>
			</td>

			<td width="19%" style="padding-right:10px;">认领人联系方式</td>
			<td width="35%"><input name="view_lostFoundPhone" id="view_lostFoundPhone"  class="show_inupt" type="text" value="${view_lostfound.lostFoundPhone}" readonly="readonly" disabled="disabled"/>
			</td>
		</tr>
				
		<tr>
			<td width="12%" style="padding-right:10px;">认领人单位</td>
			<td width="35%"><input name="view_lostFoundLostUserOrg" id="view_lostFoundLostUserOrg"  class="show_inupt" type="text" value="${view_lostfound.lostFoundLostUserOrg}" readonly="readonly" disabled="disabled"/>
			</td>

			<td width="19%" style="padding-right:10px;">认领时间</td>
			<td><table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="35%"><input type="text" class="show_inupt" name="view_lostFoundOverTime" id="view_lostFoundOverTime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="<f:formatDate value="${view_lostfound.lostFoundOverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>   
  					</tr>
				</table>
			</td>
		</tr>
	</c:if>
	</table>					
</div>
</form>
</div>
</div>
</div>
  
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>