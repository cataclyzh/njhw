<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>会议评价</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/member.css" rel="stylesheet" type="text/css" />
	
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/property/block.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/property/calendarCompare.js" type="text/javascript" charset="UTF-8"></script>
	<script src="${ctx}/scripts/widgets/property/conference.js" type="text/javascript"></script> 
	<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip"
		type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
		type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript"
		src="${ctx}/app/portal/toolbar/showModel.js"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script src="${ctx}/app/property/conference/js/valueOneConference.js" type="text/javascript"></script>
</head>

<body style="height: auto;background:#FFF;">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

	<div class="bgsgl_right_list_border">
		  			<div class="bgsgl_right_list_left">会议详情</div>
				</div>
	<form id="valueform" name="valueform" action="valueOneConference.act" method="post" target="main_frame_right">
	<input type="hidden" id="conferencePackageId" name="conferencePackageId" value="${view_conference.conferencePackageId}"/>
	<input type="hidden" id="conferenceId" name="conferenceId" value="${view_conference.conferenceId}"/>
		<div class="show_from">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  
    <tr>
    	<td class="fkdj_name" style="padding-right:10px;">会议名称</td>
    		<td width="350"><input name="value_conferenceName" id="value_conferenceName"  class="show_inupt" type="text" value="${view_conference.conferenceName}" readonly="readonly"/>
   		</td>
   		
	    <td style="padding-right:10px;">会议申请人</td>
	    	<td width="336"><input name="value_conferenceUserName" id="value_conferenceUserName"  class="show_inupt" type="text" value="${view_conference.outName}" readonly="readonly"/>
	    </td>	     
   </tr>
   <tr>
   		<td class="fkdj_name" style="padding-right:10px;">联系方式</td>
	    	<td><input name="value_conferenceUserPhone" id="value_conferenceUserPhone"  class="show_inupt" type="text" readonly="readonly" value="${view_conference.phonespan}" readonly="readonly"/>
	    </td>
   		<td style="padding-right:10px;">申请人单位</td>
   			 <td><input name="value_conferenceUserORG" id="value_conferenceUserORG"  class="show_inupt" type="text" value="${view_conference.orgName}" readonly="readonly"/>
  		 </td>  		 
   </tr>
  

    
      <tr>
   		<td class="fkdj_name" style="padding-right:10px;">会议时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" style="width:150px;" name="value_conferenceStartTime" id="value_conferenceStartTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="${view_conference.startTime}" readonly="readonly" disabled="disabled"/></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" style="width:150px;" name="value_conferenceEndTime" id="value_conferenceEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="${view_conference.endTime}" readonly="readonly" disabled="disabled"/></td>    
    		</tr>
			</table>
		</td>
	</tr>
	</table>
	</div>
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
			           <d:col property="CONFERENCE_PACKAGE_STYLE" class="display_centeralign"
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
			  <td width="70" style="padding-right:10px;" valign="top">备注</td>
			  <td><textarea id="value_conferenceDetailService" name="value_conferenceDetailService" class="show_textarea" cols="80" rows="15">${view_conference.conferenceDetailService }</textarea></td>
			</tr>
			</table>
								</td>
							</tr>
</table>				
		
		<div class="show_from">
			<div class="bgsgl_right_list_border">							
				<div class="bgsgl_right_list_left">会议评价</div>
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
				<tr>
					<td width="70" style="padding-right:10px;" valign="top">满意度评价</td>
					<td><table width="50%" border="0" cellspacing="0" cellpadding="0" class="table_right_move">
		      <tr>    
		       <td>
		       <span>
		       		<c:choose>
	       			<c:when test="${view_conference.conferenceSatisfy == 0}">
		       			<input type="radio" name="value_conferenceSatisfy" id="value_conferenceSatisfy" value="0" checked="checked"/>非常满意
		       		</c:when>
		       		<c:otherwise>
		       			<input type="radio" name="value_conferenceSatisfy" id="value_conferenceSatisfy" value="0"/>非常满意
		       		</c:otherwise>
		       		</c:choose>
	
		       		<c:choose>
	       			<c:when test="${view_conference.conferenceSatisfy == 1}">
		       			<input type="radio" name="value_conferenceSatisfy" id="value_conferenceSatisfy" value="1" checked="checked"/>满意
		       		</c:when>
		       		<c:otherwise>
		       			<input type="radio" name="value_conferenceSatisfy" id="value_conferenceSatisfy" value="1"/>满意
		       		</c:otherwise>
		       		</c:choose>

		       		<c:choose>
	       			<c:when test="${view_conference.conferenceSatisfy == 2}">
		       			<input type="radio" name="value_conferenceSatisfy" id="value_conferenceSatisfy" value="2" checked="checked"/>不满意
		       		</c:when>
		       		<c:otherwise>
		       			<input type="radio" name="value_conferenceSatisfy" id="value_conferenceSatisfy" value="2"/>不满意
		       		</c:otherwise>
		       		</c:choose>
		       	</span>
		       	</td>
		 	  </tr>
				</table>
			</td>

		</tr>
	</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="70" style="padding-right:10px;" valign="top">服务评价</td>
					<td colspan="3" style="text-align:left;"><textarea id="value_conferenceClientValue" name="value_conferenceClientValue" class="member_form_inp_textarea_modify" cols="80" rows="10"></textarea></td>
				</tr>							
			</table>
					<div class="clear"></div>
					
					
					
					<!-- 
					<div class="energy_fkdj_botton_ls">
					<a class="fkdj_botton_left"  onclick="javascript:saveData()">提交</a>　
					<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>
					 </div>
					
					 -->
<div class="energy_fkdj_botton_ls">
					<a class="fkdj_botton_right"  onclick="javascript:saveData()">提交</a>　
					 </div>
<div class="clear"></div>
				   <div class="clear"></div>		
		</div>
	</form>					
</div>
</div>

</body>
</html>