<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>会务详情信息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
		function viewConferenceCancel(){
			parent.closeViewConference();
		}
		
	</script>
</head>

<body style="background:#FFF;">
<!-- -------------------------查看Block------------------------- -->
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

	<div class="form_right_move">
		
	<form id="viewform" name="viewform" action="" method="post">
	<input type="hidden" id="view_conferenceId" value="${view_conference.conferenceId}" />
	<div class="show_from_tr_td" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr> 
	    <td style="padding-right:10px;">会议名称</td>
    		<td width="306"><input name="view_conferenceName" id="view_conferenceName"  class="show_inupt" type="text" readonly="readonly" value="${view_conference.conferenceName}" disabled="disabled"/>
   		</td>
   		<td style="padding-right:10px;">会议申请人</td>
	    	<td width="306"><input name="view_conferenceUserName" id="view_conferenceUserName"  class="show_inupt" type="text" readonly="readonly" value="${view_conference.outName}"  disabled="disabled"/>
	    </td>
	  </tr> 
  
    <tr>
	    
	    <td style="padding-right:10px;">联系方式</td>
	    	<td><input name="view_conferenceUserPhone" id="view_conferenceUserPhone"  class="show_inupt" type="text" readonly="readonly" value="${view_conference.phonespan}"  disabled="disabled"/>
	    </td>
	     <td style="padding-right:10px;">申请人单位</td>
   			 <td><input name="view_conferenceUserORG" id="view_conferenceUserORG"  class="show_inupt" type="text" readonly="readonly" value="${view_conference.orgName}" disabled="disabled"/>
  		 </td>
   </tr>
  
    
    <tr>
   		<td style="padding-right:10px;">会议时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="show_inupt" style="width:125px;"  name="view_conferenceStartTime" id="view_conferenceStartTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="${view_conference.startTime}" readonly="readonly" disabled="disabled"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="show_inupt" name="view_conferenceEndTime" id="view_conferenceEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="${view_conference.endTime}" readonly="readonly" disabled="disabled"/></td>    
    		</tr>
			</table>
		</td>
	</tr>
	</table>
	</div>
	<div class="table_list">
	<table>
	<tr>
	<td class="fkdj_name" style="text-align:left"><strong>会议室选择</strong></td>
 		               <d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
			           <d:col property="CONFERENCE_PACKAGE_NAME" class="display_centeralign"
				             title="会议室名称" maxLength="10"/>
			          
			           <d:col property="CONFERENCE_PACKAGE_LOCATION" class="display_centeralign"
				             title="会场地址" maxLength="10"/>
				       <d:col property="CONFERENCE_PACKAGE_SEAT" class="display_centeralign"
				             title="座位" maxLength="10"/>
			           <d:col property="CONFERENCE_PACKAGE_STYLE" class="display_leftalign"
				             title="会议室布局" maxLength="10"/>
				       <d:col property="CONFERENCE_PACKAGE_FACILITY" class="display_centeralign"
				             title="其他设施" maxLength="10"/>
			           <d:col property="CONFERENCE_PACKAGE_PRICE" class="display_centeralign"
				             title="价目" maxLength="10"/>	
				       <d:col property="CONFERENCE_PACKAGE_SERVICE" class="display_centeralign"
				             title="服务项目" maxLength="15"/>															
		               </d:table>
    </tr>


	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="show_from" style="padding-top:10px;">
			<tr>
			  <td width="70" style="padding-right:10px;" valign="top">详细内容</td>
			  <td><textarea id="view_conferenceDetailService" name="view_conferenceDetailService"  class="show_textarea" cols="80" rows="15" readonly="readonly" disabled="disabled">${view_conference.conferenceDetailService }</textarea></td>
			</tr>
			</table>
		</td>
	</tr>	
	
	<c:if test="${view_conference.conferenceState == 2}">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="show_from">
		<tr>
			<td width="70" style="padding-right:10px;" valign="top">满意度</td>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_right_move">
		      <tr>    
		       <td style="text-align:left">
		       <span>
		       <c:if test="${view_conference.conferenceSatisfy == 0}">
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" checked="checked"/>非常满意
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" />满意
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" />不满意
				</c:if>
				<c:if test="${view_conference.conferenceSatisfy == 1}">
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" />非常满意
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" checked="checked"/>满意
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" />不满意
				</c:if>	
				<c:if test="${view_conference.conferenceSatisfy == 2}">
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" />非常满意
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" />满意
					<input type="radio" name="view_conferenceSatisfy" id="view_conferenceSatisfy" disabled="disabled" checked="checked"/>不满意
				</c:if>
		       	</span>
		       	</td>
		 	  </tr>
				</table>
			</td>
		</tr>
		</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="show_from">
		<tr>
			<td width="70" style="padding-right:10px;" valign="top">服务评价</td>
			<td colspan="3"><textarea id="view_conferenceClientValue" name="view_conferenceClientValue"  class="show_textarea" cols="40" rows="15"
								readonly="readonly" disabled="disabled">${view_conference.conferenceClientValue }</textarea></td>
		</tr>
		</table>
	</c:if>
	</table>
</div>
</form>


</div>
</div>
</div>	
  
</body>
</html>