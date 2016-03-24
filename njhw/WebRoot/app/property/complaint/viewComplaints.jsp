<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>投诉处理流程一览</title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />


	
	</head>
	<body style=" background:#FFF">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl">
	 
<form action="addOneComplaint.act" method="post" id="addComplaints">
<div class="show_from">

      
      <c:if test="${complaint.complaintsState == 0}">
      	  <div class="tscl_wizard_bacrground">
<a class="tscl_top_site"></a><a class="tscl_top_site2_b"></a><a class="tscl_top_site3_b"></a><a class="tscl_top_site4_b"></a>
      </div>
      <div class="background_color">
 <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">投诉申请信息</div>
		</div>     
		 <div class="tscl_conter">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">标题：</td>
    <td width="35%"><input name="complaintsTitle" id="complaintsTitle" type="text" class="show_inupt" value="${complaint.complaintsTitle}" readonly="readonly"/></td>
    <td width="19%">投诉时间：</td>
    <td width="34%"><input name="complaintsInTime" id="complaintsInTime" type="text" class="show_inupt" value="<f:formatDate value="${complaint.complaintsInTime}" pattern="yyyy-MM-dd"/>" readonly="readonly"/></td>
  </tr>

  <tr>
    <td width="12%">投诉人：</td>
    <td width="35%"><input name="complaintsUser" id="complaintsUser" type="text" class="show_inupt" value="${complaint.complaintsUser}" readonly="readonly"/></td>
    <td width="19%">联系电话：</td>
    <td width="34%"><input name="complaintsTelephone" id="complaintsTelephone" type="text" class="show_inupt" value="${complaint.complaintsTelephone}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td valign="top">投诉内容：</td>
    <td colspan="3"><textarea name="complaintsDetail" id="complaintsDetail" class="show_textarea_tscl" cols="80" rows="15"  readonly="readonly" disabled="disabled">${complaint.complaintsDetail}</textarea></td>
  </tr>
</table>
</div>
</div>
</c:if>



<c:if test="${complaint.complaintsState == 1}">
      	  <div class="tscl_wizard_bacrground">
<a class="tscl_top_site_b"></a><a class="tscl_top_site2_b"></a><a class="tscl_top_site3"></a><a class="tscl_top_site4_b"></a>
      </div>
      <div class="background_color">
<div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">投诉申请信息</div>
		</div>   
		      <div class="tscl_conter">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">标题：</td>
    <td width="35%"><input name="complaintsTitle" id="complaintsTitle" type="text" class="show_inupt" value="${complaint.complaintsTitle}" readonly="readonly"/></td>
    <td width="19%">投诉时间：</td>
    <td width="34%"><input name="complaintsInTime" id="complaintsInTime" type="text" class="show_inupt" value="${complaint.complaintsInTime}" readonly="readonly"/></td>
  </tr>

  <tr>
    <td width="12%">投诉人：</td>
    <td width="35%"><input name="complaintsUser" id="complaintsUser" type="text" class="show_inupt" value="${complaint.complaintsUser}" readonly="readonly"/></td>
    <td width="19%">联系电话：</td>
    <td width="34%"><input name="complaintsTelephone" id="complaintsTelephone" type="text" class="show_inupt" value="${complaint.complaintsTelephone}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td valign="top">投诉内容：</td>
    <td colspan="3"><textarea name="complaintsDetail" id="complaintsDetail" class="show_textarea_tscl" cols="80" rows="15"  readonly="readonly" disabled="disabled">${complaint.complaintsDetail}</textarea></td>
  </tr>
</table>
</div>
</div>


      <div class="background_color">
<div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">投诉处理信息</div>
		</div>   
		      <div class="tscl_conter">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">投诉处理人：</td>
    <td width="35%"><input name="complaintsProcessUser" id="complaintsProcessUser" type="text" class="show_inupt" value="${complaint.complaintsProcessUser}" readonly="readonly" /></td>
    <td width="19%">投诉时间：</td>
    <td width="34%"><input name="complaintsOverTime" id="complaintsOverTime" type="text" class="show_inupt" value="${complaint.complaintsOverTime}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td width="12%">联系电话：</td>
    <td width="35%"><input name="complaintsProcessPhone" id="complaintsProcessPhone" type="text" class="show_inupt" value="${complaint.complaintsProcessPhone}" readonly="readonly"/></td>
    <td width="19%">&nbsp;</td>
    <td width="34%">&nbsp;</td>
  </tr>
  <tr>
    <td valign="top">处理结果：</td>
    <td colspan="3"><textarea name="complaintsProcessResult" id="complaintsProcessResult" class="show_textarea_tscl" cols="80" rows="15" readonly="readonly" disabled="disabled">${complaint.complaintsProcessResult}</textarea></td>
  </tr>
</table>
</div>
</div>
</c:if>

<c:if test="${complaint.complaintsState == 2}">
     	  <div class="tscl_wizard_bacrground">
<a class="tscl_top_site_b"></a><a class="tscl_top_site2_b"></a><a class="tscl_top_site3_b"></a><a class="tscl_top_site4"></a>
      </div>
 <div class="background_color">
<div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">投诉申请信息</div>
		</div>
		      <div class="tscl_conter">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">标题：</td>
    <td width="35%"><input name="complaintsTitle" id="complaintsTitle" type="text" class="show_inupt" value="${complaint.complaintsTitle}" readonly="readonly"/></td>
    <td width="19%">投诉时间：</td>
    <td width="34%"><input name="complaintsInTime" id="complaintsInTime" type="text" class="show_inupt" value="${complaint.complaintsInTime}" readonly="readonly"/></td>
  </tr>

  <tr>
    <td width="12%">投诉人：</td>
    <td width="35%"><input name="complaintsUser" id="complaintsUser" type="text" class="show_inupt" value="${complaint.complaintsUser}" readonly="readonly"/></td>
    <td width="19%">联系电话：</td>
    <td width="34%"><input name="complaintsTelephone" id="complaintsTelephone" type="text" class="show_inupt" value="${complaint.complaintsTelephone}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td valign="top">投诉内容：</td>
    <td colspan="3"><textarea name="complaintsDetail" id="complaintsDetail" class="show_textarea_tscl" cols="80" rows="15"  readonly="readonly" disabled="disabled">${complaint.complaintsDetail}</textarea></td>
  </tr>
</table>
</div>
</div>


      <div class="background_color">
<div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">投诉处理信息</div>
		</div>
		      <div class="tscl_conter">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="12%">投诉处理人：</td>
    <td width="35%"><input name="complaintsProcessUser" id="complaintsProcessUser" type="text" class="show_inupt" value="${complaint.complaintsProcessUser}" readonly="readonly" /></td>
    <td width="19%">投诉时间：</td>
    <td width="34%"><input name="complaintsOverTime" id="complaintsOverTime" type="text" class="show_inupt" value="${complaint.complaintsOverTime}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td width="12%">联系电话：</td>
    <td width="35%"><input name="complaintsProcessPhone" id="complaintsProcessPhone" type="text" class="show_inupt" value="${complaint.complaintsProcessPhone}" readonly="readonly"/></td>
    <td width="19%">&nbsp;</td>
    <td width="34%">&nbsp;</td>
  </tr>
  <tr>
    <td valign="top">处理结果：</td>
    <td colspan="3"><textarea name="complaintsProcessResult" id="complaintsProcessResult" class="show_textarea_tscl" cols="80" rows="15" readonly="readonly" disabled="disabled">${complaint.complaintsProcessResult}</textarea></td>
  </tr>
</table>
</div>
</div>


      <div class="background_color">
<div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">投诉意见反馈</div>
		</div>
		      <div class="tscl_conter">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="15%">满意度评价：</td>
    <td width="35%">
    <c:if test="${complaint.complaintsSatisfy == 0}">
    <input name="complaintsSatisfy" id="complaintsSatisfy" type="text" class="show_inupt" value="非常满意" readonly="readonly"/>
</c:if>
 <c:if test="${complaint.complaintsSatisfy == 1}">
    <input name="complaintsSatisfy" id="complaintsSatisfy" type="text" class="show_inupt" value="满意" readonly="readonly"/>
</c:if>
 <c:if test="${complaint.complaintsSatisfy == 2}">
    <input name="complaintsSatisfy" id="complaintsSatisfy" type="text" class="show_inupt" value="不满意" readonly="readonly"/>
</c:if>
    </td>
    
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td valign="top">反馈信息：</td>
    <td colspan="3"><textarea name="complaintsFeedback" id="complaintsFeedback" class="show_textarea_tscl" cols="80" rows="15" readonly="readonly" disabled="disabled">${complaint.complaintsFeedback}</textarea></td>
   
  </tr>
</table>
</div>
</div>

</c:if>
</div>
</form>
</div>
   </div>
</body>
</html>
