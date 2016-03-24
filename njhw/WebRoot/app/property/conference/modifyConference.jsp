<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>会议修改</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/member.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet" type="text/css" />
	
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
	<script type="text/javascript">
$(document).ready(function(){
				jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime <= value;
	        };
	        
	        jQuery.validator.methods.compareNowDate = function(value, element, param) {
				return value >= "${nowDate}";
	        };
				
				$("#modifyform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					modify_conferenceName:{
					required: true,
					maxlength:20
					},
					outName:{
					required: true,
					maxlength:90
					},
					_chk:{
					required: true
					},
					modify_conferenceStartTime:{
						required:true,
					},
					modify_conferenceEndTime:{
						required:true,
						compareDate: "#modify_conferenceStartTime"
					},
					modify_conferenceDetailService:{
						maxlength:255
					}
					
					},
					messages:{
						modify_conferenceName:{
						required: "请输入会议名称",
					  	maxlength: "输入的会议名称过长"
						},
						outName:{
						required: "请选择申请人",
						maxlength: "申请人名称过长"
						},
						_chk:{
						required: "请选择一个套餐"
						},
						modify_conferenceStartTime:{
					    required:"请填写开始时间",
						},
						modify_conferenceEndTime:{
							required:"请填写结束时间",
							compareDate: "开始时间必须早于结束时间"
						},
						modify_conferenceDetailService:{
							maxlength: "申请备注字符过长"
						}
					}
				});
			});    
			
			
				function saveData(){		
                  
				if (showRequest()) {
					//openAdminConfirmWin(function(){
						$('#modifyform').submit();
                        closeEasyWin("modifyOneConferencePrepare");
					//});					
				}
			}
			
			function showRequest(){
				 return $("#modifyform").valid();
			}	
</script>
	<script type="text/javascript">
			
			function modifyOneConference(){
				$("#modifyform").submit();
				parent.closeModifyConference();
			}
			
			function modifyConferenceCancel(){
				parent.closeModifyConference();
			}
			
			function selectPackage(){
			var fdiId = $("input:radio[name='_chk']:checked").val();
			$("#conferencePackageId").val(fdiId);
			
			}
			//机构树
	function selectRespondents() {

		var url = "${ctx}/app/visitor/frontReg/respondentsTreeSelect.act?state=all";
		var params = "";
		url += params;

		var width = 400;
		var left = (document.body.scrollWidth - width) / 2;
		$("#companyWin").show();
		$("#companyWin").window({
			title : '请选择会议申请人',
			modal : true,
			shadow : false,
			closed : false,
			width : width,
			height : 600,
			top : 50,
			left : left
		});
		$("#companyIframe").attr("src", url);

	}
	
	function cancelAddDevice() {
		$('#modifyform').resetForm();
	}
	
		</script>
</head>

<body style="background:#FFF;">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >

	<form id="modifyform" name="modifyform" action="modifyConference.act" method="post" target="main_frame_right">
	<input type="hidden" id="conferencePackageId" name="conferencePackageId" value="${view_conference.conferencePackageId}"/>
	<input type="hidden" id="modify_conferenceId" name="modify_conferenceId" value="${view_conference.conferenceId}"/>
		<div class="show_from">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr> 
	    <td><font color="red">*</font>会议名称</td>
    		<td width="336px;" style="padding-left:10px;"><input name="modify_conferenceName" id="modify_conferenceName" style="width:336px;" class="member_form_input"  type="text"  value="${view_conference.conferenceName}" />
   		</td>
   		<td style="padding-right:10px;"><font color="red">*</font>会议申请人</td>
	    	<td width="336px;"><input name="outName" id="outName"  class="member_form_input" style="width:336px;" type="text" value="${view_conference.outName}" /></td>
		<td></td>
	    
	  </tr> 
  
    <tr>
	    
	    <td>联系方式</td>
	    	<td width="336px;"><input name="phonespan" id="phonespan"  class="member_form_input" style="width:336px;" type="text" value="${view_conference.phonespan}" />
	    </td>
	     <td style="padding-right:10px;"><font color="red">*</font>申请人单位</td>
   			 <td width="336px;"><input name="orgName" id="orgName"  class="member_form_input" style="width:336px;" type="text" value="${view_conference.orgName}" />
  		 </td>
   </tr>
  
    
    <tr>
   		<td class="fkdj_name"><font color="red">*</font>会议时间</td>
		<td style="padding-left:10px;"><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="fkdj_from_input" style="width:153px;color:#808080;" name="modify_conferenceStartTime" id="modify_conferenceStartTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" 
							value="${view_conference.startTime}" /></td>
				<td class="fkdj_name_lkf">至</td>
				<td><input type="text" class="fkdj_from_input" style="color:#808080;" name="modify_conferenceEndTime" id="modify_conferenceEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" 
							value="${view_conference.endTime}" /></td>    
    		</tr>
			</table>
		</td>
	</tr>
	<tr>
	    <td class="fkdj_name" style="text-align:left"><font color="red">*</font><span style="font-size:12px;">会议室选择</span></td>
	</tr>
	</table>
		</div>
		<div class="ovdbok_height">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	           <tr>
 		               <d:table name="pagepackage.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
			           <d:col style="width:45" class="display_centeralign" title="全选">
				       <c:choose>
	       			   <c:when test="${row.CONFERENCE_PACKAGE_ID == view_conference.conferencePackageId}">
	       				<input type="radio" name="_chk" id="_chk" class="checkItem" value="${row.CONFERENCE_PACKAGE_ID}" checked="checked" onchange="selectPackage()"/>
	       			   </c:when>
	       			   <c:otherwise>
	       			    	<input type="radio" name="_chk" id="_chk" class="checkItem" value="${row.CONFERENCE_PACKAGE_ID}"  onchange="selectPackage()"/>
	       			   </c:otherwise>
	       		       </c:choose>
			           </d:col>
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
</table>
</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="show_from" style="padding-top:10px;">
			<tr>
			  <td width="70" style="padding-right:10px;" valign="top">备注</td>
			  <td style="text-align:left"><textarea id="modify_conferenceDetailService" name="modify_conferenceDetailService" class="member_form_inp_textarea_modify" cols="80" rows="15">${view_conference.conferenceDetailService }</textarea></td>
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
				
	</form>					
</div>
</div>
	
		<div id='companyWin' class='easyui-window' collapsible='false'
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<iframe id='companyIframe' name='companyIframe'
			style='width: 100%; height: 100%;' frameborder='0'></iframe>
	</div>

</body>
</html>



