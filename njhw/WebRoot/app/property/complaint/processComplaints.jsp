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
		<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip"
		type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
		type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript"
		src="${ctx}/app/portal/toolbar/showModel.js"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		
			function closeB(){
                parent.closeOther();

				}
			$(document).ready(function(){
				
				jQuery.validator.addMethod("checkLength", function(value, element) { 
					var length = value.length; 
					return this.optional(element) || (length <= 20); 
				}, "输入的字符过长");
                
				jQuery.validator.addMethod("checkDetail", function(value, element) { 
					var length = value.length; 
					return this.optional(element) || (length <= 255); 
				}, "输入的字符过长");
                
				jQuery.validator.addMethod("checkMobile", function(value, element) { 
					var isMobile=/^(?:13\d|15\d)\d{5}(\d{3}|\*{3})$/;   
					var isPhone=/^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;	
					return this.optional(element) || (isMobile.test(value)||isPhone.test(value)); 
				}, "请输入正确的电话号码");

				
				$("#processComplaints").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					complaintsProcessUser:{
							required: true,
							checkLength:true,
							maxlength: 10
						},
						complaintsOverTime:{
							required: true
						},	
						complaintsProcessPhone:{
							required: true,
							checkLength:true,
							checkMobile:true
						},
						complaintsProcessResult:{
							required: true,
							checkDetail:true,
							maxlength: 255
							
						}
					},
					messages:{
						complaintsProcessUser:{
							required: "请输入投诉处理人",
						  	checkLength: "输入的字符过长",
						  	maxlength: "输入的字符过长"
						},
						complaintsOverTime:{
							required: "请填写处理日期"
						},	
						complaintsProcessPhone:{
							required: "请填写处理人电话",
                            checkMobile:"请填写正确的电话号码"
							},
						complaintsProcessResult:{
								required: "请输入处理结果",
							  	checkDetail: "输入的字符过长"
							}
					}
				});
			});    
			
			
				function saveData(){		
				if (showRequest()) {

		
							$('#processComplaints').submit();
							closeEasyWin("orgInput");
					
				}
			}
			
			function showRequest(){
				 return $("#processComplaints").valid();
			}
			function cancelAddDevice() {
				$('#processComplaints').resetForm();
			}
		</script>
	</head>
	<body style=" background:#FFF">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl">
	  
<form action="processComplaintSub.act" method="post" id="processComplaints" name="processComplaints" target="main_frame_right">
<div class="show_from">
	  <div class="tscl_wizard_bacrground">
<a class="tscl_top_site_b"></a><a class="tscl_top_site2"></a><a class="tscl_top_site3_b"></a><a class="tscl_top_site4_b"></a>
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
    
    <td colspan="3"><textarea name="complaintsDetails" id="complaintsDetails" class="show_textarea_tscl" cols="80" rows="15"  readonly="readonly" disabled="disabled" >${complaint.complaintsDetail}</textarea></td>
  </tr>
</table>
</div>
</div>
      <div class="background_color">
       <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">投诉申请处理</div>
		</div>
<div class="fkdj_from">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td style="text-align:left"><font color="red">*</font>投诉处理人：</td>
    <td style="text-align:left"><input name="complaintsProcessUser" id="complaintsProcessUser" type="text" style="width:250px;height:25px" /></td>
    <td style="text-align:left"><font color="red">*</font>处理时间：</td>
    <td style="text-align:left"><input name="complaintsOverTime" id="complaintsOverTime" type="text" style="width:250px;height:25px" value="${nowDate}" readonly="readonly"/></td>
  </tr>
  <tr>
    <td style="text-align:left"><font color="red">*</font>联系电话：</td>
    <td style="text-align:left"><input name="complaintsProcessPhone" id="complaintsProcessPhone" type="text" style="width:250px;height:25px"   /></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  </table>
  <table>
  <tr>
    <td valign="top"><font color="red">*</font>处理结果：</td>
    <td><textarea name="complaintsProcessResult" id="complaintsProcessResult" style="width:610px;height:200px;margin-left:15px"></textarea></td>
  </tr>
</table>

</div>
</div>
</div>
    <input name="complaintsDetail" id="complaintsDetail" type="hidden"value="${complaint.complaintsDetail}"/>

          <input type="hidden" name="complaintsId" id="complaintsId" value="${complaint.complaintsId}"/> 

</form>
</div>
						<!-- 
						<div class="energy_fkdj_botton_ls">
					<a class="fkdj_botton_left"  onclick="javascript:saveData()">提交</a>　
			<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>
					

</div>
						 -->
						
						
							<div class="energy_fkdj_botton_ls">
					<a class="fkdj_botton_right"  onclick="javascript:saveData()">提交</a>　
					

</div>
   </div>
</body>
</html>
