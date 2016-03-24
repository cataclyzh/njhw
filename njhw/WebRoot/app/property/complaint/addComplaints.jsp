



<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>投诉处理流程</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
		
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
			<script type="text/javascript" src="${ctx}/app/property/complaint/js/height.js"></script>
	
	<script type="text/javascript">
	function addDevice() {
		$('#addComplaints').submit();
	}
	function clearInput() {
        document.getElementById("complaintsTitle").value="";
        document.getElementById("outName").value="";
        document.getElementById("phonespan").value="";
        document.getElementById("complaintsDetail").value="";   
	}
</script>
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


				$("#addComplaints").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
	     					complaintsTitle:{
							required: true,
							checkLength:true,
							maxlength: 20,
						},
						complaintsDetail:{
							required: true,
							checkDetail:true,
						},
						complaintsTelephone:{
							checkMobile:true
						}

						
					},
					messages:{
						complaintsTitle:{
							required: "请输入投诉标题",
						  	checkLength: "输入的标题字符过长",
						  	maxlength: "输入的标题字符过长",
						},
						complaintsDetail:{
							required: "请填写投诉内容",
								checkDetail:"输入的投诉内容过长",
							},
							complaintsTelephone:{
								checkMobile:"请输入正确的投诉人电话"
							}	
						
					},
				});
			});    
			
			
				function saveData(){		
				if (showRequest()) {
			
					$('#addComplaints').submit();
					window.parent.frames["main_frame_left"].$('#btn_viewComplaint').parent().attr("class","main1_viewComplaint_select");
					window.parent.frames["main_frame_left"].$('#btn_addComplaint').parent().attr("class","main1_addComplaint");
					
					parent.closeOther();
				}
			}
			
			function showRequest(){
				 return $("#addComplaints").valid();
			}
			function selectRespondents() {
				window.parent.selectRespondents();
				}
		</script>
	</head>

	<body style="background:#fff;">
		<div class="main_border_01">
  <div class="main_border_02">申请投诉</div>
</div>
		
			<div class="main_conter">
			<div class="bgsgl_conter" style="border:0px">
				<div class="fkdj_lfrycx">
					<div class="winzard_show_from">
					<form action="addOneComplaint.act" method="post" id="addComplaints" name="addComplaints">

							<table width="100%" border="0" cellpadding="0" cellspacing="0">
					

								<tr>
									<td class="winzard_show_from_right"  width="10%">
									<font color="red">*</font>标题
									</td>
									<td>
										<input name="complaintsTitle" id="complaintsTitle"
											class="fkdj_from_input" type="text" />

									</td>
									<td class="winzard_show_from_right" width="10%">
									<font color="red">*</font>	投诉时间
									</td>
									<td>
										<input name="complaintsInTime" id="complaintsInTime"
											class="fkdj_from_input" type="text" value="${nowDate}" readonly="readonly" />

									</td>
								</tr>
								
								<tr>
									<td class="winzard_show_from_right" width="10%">
										投诉人
									</td>
									<td width="50%">
										<input name="outName" id="outName"
											class="fkdj_from_input" type="text" />

                                   <img src="${ctx}/app/integrateservice/images/fkdj_pho.jpg"
									width="22" height="18" onclick="selectRespondents();"/>

									</td>
									<td class="winzard_show_from_right" width="10%">
										投诉人电话
									</td>
									<td>
										<input name="phonespan" id="phonespan"
											class="fkdj_from_input" type="text" value="${userExp.telNum}"/>
									</td>
								</tr>
								
								

								<tr>
									<td class="winzard_show_from_right" valign="top" width="10%">
									<font color="red">*</font>	投诉内容
									</td>
									<td colspan="3">
										<div class="text_right_move">
											<textarea name="complaintsDetail" id="complaintsDetail"
												cols="90" rows="15"></textarea>
										</div>
									</td>
								</tr>
							</table>
							
							<!-- 
							<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_left" onclick="saveData()">确定</a>
									<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>
								</div>
							 -->
							<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_right" onclick="saveData()" >确定</a>
									<a class="fkdj_botton_left" onclick="clearInput()" style="cursor:pointer">重置</a>
									
								</div>
							
								
							<input name="nowDate" id="nowDate" value="${nowDate}" type="hidden" />
						</form>
					</div>
				</div>
			</div>
		</div>
		
	</body>

</html>





