<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>车位情况</title>	
	<%@ include file="/common/include/meta.jsp" %>
		<link href="${ctx}/app/property/css/wizard_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/block.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet" type="text/css" />
</head>

<body style=" background:#FFF">
<!-- -------------------------查看Block------------------------- -->
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

		<div style="padding-left:200px">
		
		<form action="" method="post" id="carManageViewForm" name="carManageViewForm">
<div class="show_from" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
    <td width="12%">单位名称：</td>
    <td width="50%"><input name="carManageViewTo" id="carManageViewTo"  class="show_inupt" type="text" readonly="readonly" value="${parkingInfo.parkingInfoOrgName}"/>
   </td>
    </tr>
    
    <tr>
   <td width="12%">车位数量：</td>
    <td width="50%"><input name="carManageViewTo" id="carManageViewTo"  class="show_inupt" type="text" readonly="readonly" value="${parkingInfo.parkingInfoNumber}"/>
   </td>
     <td width="13px">位</td>
    <td></td>
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