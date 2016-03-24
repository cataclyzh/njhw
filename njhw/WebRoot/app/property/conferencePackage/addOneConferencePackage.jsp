<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>增加套餐</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />	
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
	
	<script type="text/javascript">
	
		function addOneConferencePackage(){
				$("#addform").submit();
			}
			
		function addConferencePackageCancel(){
			parent.closeAddConferencePackage();
		}
		
		$(document).ready(function(){

				$("#addform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
	     					add_conferencePackageName:{
							required: true,
							maxlength: 20
						},
						
						add_conferencePackageRoom:{
							required: true,
							maxlength: 10
						},
						
						add_conferencePackageLocation:{
							required: true,
							maxlength: 50
						},
						
						add_conferencePackageSeat:{
							required: true,
							maxlength: 50
						},
						
						add_conferencePackagePrice:{
							required: true,
							maxlength: 10
						},
						
						add_conferencePackageFacility:{
							maxlength: 50
						},
						
						add_conferencePackageStyle:{
							maxlength: 50
						},
						
						add_conferencePackageService:{
							maxlength:255
						}
						
					},
					messages:{
						add_conferencePackageName:{
							required: "请输入套餐名称",
						  	maxlength: "输入的套餐名称过长"
						},
						add_conferencePackageRoom:{
							required: "请填写会场编号",
							maxlength:"输入的会场编号内容过长"
						},
						
						add_conferencePackageLocation:{
							required: "请填写会场地址",
							maxlength:"输入的会场地址过长"
						},
						
						add_conferencePackageSeat:{
							required: "请填写会场座位",
							maxlength:"输入的会场座位内容过长"
						},
						
						add_conferencePackagePrice:{
							required: "请填写价目",
							maxlength:"输入的价目内容过长"
						},
						
						add_conferencePackageFacility:{
							maxlength:"输入的会场设施内容过长"
						},
						
						add_conferencePackageStyle:{
							maxlength:"输入的布局样式内容过长"
						},
						
						add_conferencePackageService:{
							maxlength:"输入的服务项目内容过长"
						}	
						
					}
				});
			});    
			
			
				function saveData(){		
					if (showRequest()) {

					var add_conferencePackageName=$("#add_conferencePackageName").val();
					var add_conferencePackageRoom=$("#add_conferencePackageRoom").val();
					  $.ajax({
				             type:"POST",
				             url:"checkAddConferencePackage.act",
				             data:{add_conferencePackageName:add_conferencePackageName,add_conferencePackageRoom:add_conferencePackageRoom},
				             dataType: 'json',
						     async : false,
						     success: function(json){
				             if (json.status == 0) {
				            
						            easyAlert("提示", json.message, "confirm");
				             }
			                 if(json.status == 1){
			                	 $('#addform').submit();   
			                  window.parent.frames["main_frame_left"].$('#btn_viewConferencePackage').parent().attr("class","main1_viewConferencePackage_select");
				 					window.parent.frames["main_frame_left"].$('#btn_addConferencePackage').parent().attr("class","main1_addConferencePackage");
			                 }    
				          }
				      });


					}
				}
			
			function showRequest(){
				 return $('#addform').valid();
			}
					function clearInput() {
		$('#addform').resetForm();
	}
	</script>
</head>


<body style="height: auto;background:#FFF;">
	<div class="main_border_01">
		<div class="main_border_02">发布套餐</div>
	</div>
	<div class="main_conter">
	<div class="form_right_move ">
		<div class="bgsgl_conter" style="border:0px">
		<div class="fkdj_lfrycx">	
	<form id="addform" name="addform" action="addOneConferencePackage.act" method="post">
	<div class="show_from" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    	<td width="10%"><font color="red">*</font>会议室名称</td>
    		<td style="text-align: left;padding-left:10px;" width="35%"><input name="add_conferencePackageName" id="add_conferencePackageName"  class="fkdj_input_hik" type="text" value=""/>
   		</td>
   		
	    <td width="19%"><font color="red">*</font>会场编号</td>
	    	<td style="text-align: left;padding-left:10px;" width="35%"><input name="add_conferencePackageRoom" id="add_conferencePackageRoom"  class="fkdj_input_hik" type="text" value=""/>
	    </td>	     
   </tr>
   <tr>
   		<td width="10%"><font color="red">*</font>会场地址</td>
	    	<td style="text-align: left;padding-left:10px;" width="35%"><input name="add_conferencePackageLocation" id="add_conferencePackageLocation"  class="fkdj_input_hik" type="text" value=""/>
	    </td>
   		<td width="19%"><font color="red">*</font>座位</td>
   			 <td style="text-align: left;padding-left:10px;" width="35%"><input name="add_conferencePackageSeat" id="add_conferencePackageSeat"  class="fkdj_input_hik" type="text" value=""/>
  		 </td>  		 
   </tr>
  
    <tr>  
    	<td width="10%"><font color="red">*</font>价目</td>
	    	<td style="text-align: left;padding-left:10px;" width="35%"><input name="add_conferencePackagePrice" id="add_conferencePackagePrice"  class="fkdj_input_hik" type="text" value=""/>
	    </td>  	
	     <td width="19%">其他设施</td>
   			 <td style="text-align: left;padding-left:10px;" width="35%"><input name="add_conferencePackageFacility" id="add_conferencePackageFacility"  class="fkdj_input_hik" type="text" value=""/>
  		 </td>     		      
    </tr>
	<tr>
		<td valign="top">会议室布局</td>
		<td style="text-align: left;padding-left:10px;" colspan="3"><textarea id="add_conferencePackageStyle" class="textarea_add_tow" cols="20" rows="10" name="add_conferencePackageStyle"></textarea>
		</td>
	</tr>
	<tr>
		<td valign="top">服务项目</td>
		<td style="text-align: left;padding-left:10px;" colspan="3"><textarea id="add_conferencePackageService" name="add_conferencePackageService"  class="textarea_add_tow" cols="20" rows="10"></textarea>
		</td>
	</tr>
	</table>	
	
	<div class="clear">
	</div>
	
	
	
	<!-- 
	<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_left" href="javascript:saveData()">确定</a>
	<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>
	</div>
	 -->
	<div class="energy_fkdj_botton_ls"><a class="fkdj_botton_right" href="javascript:saveData()">确定</a>
	<a class="fkdj_botton_left" href="javascript:clearInput()">重置</a>
	</div>
					
</div>
</form>
</div>
</div>
</div>
 </div> 
</body>
</html>
