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

<script type="text/javascript">
			function submitForm(){
				$("#feedbackComplaints").submit();
                parent.closeOther();
                
				}
			function closeB(){
                parent.closeOther();

				}

$(document).ready(function(){
				
				
				$("#feedbackComplaints").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					complaintsSatisfy:{
							required: true
						},
						complaintsFeedback:{
							maxlength: 255
							
						}
						
					},
					messages:{
						complaintsSatisfy:{
							required: "请选择满意程度"
						},
						complaintsFeedback:{
                       maxlength:"输入的字符过长"
                        }
					}
				});
			});    
			
			
				function saveData(){		
				if (showRequest()) {


						$('#feedbackComplaints').submit();
						closeEasyWin("orgInput");
				
					
					
				}
			}
			
			function showRequest(){
				 return $("#feedbackComplaints").valid();
			}
			function cancelAddDevice() {
				$('#feedbackComplaints').resetForm();
			}
		</script>
	
	</head>
	<body style=" background:#FFF">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl">
	  
<form action="feedbackComplaintSub.act" method="post" id="feedbackComplaints" name="feedbackComplaints" target="main_frame_right">
<div class="show_from">
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
    <td colspan="3"><textarea name="complaintsDetails" id="complaintsDetails" class="show_textarea_tscl" cols="80" rows="15"  readonly="readonly" disabled="disabled">${complaint.complaintsDetail}</textarea></td>
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
    <td width="34%"><input name="complaintsOverTime" id="complaintsOverTime" type="text" class="show_inupt" value="<f:formatDate value="${complaint.complaintsOverTime}" pattern="yyyy-MM-dd"/>" readonly="readonly"/></td>
  </tr>
  <tr>
    <td width="12%">联系电话：</td>
    <td width="35%"><input name="complaintsProcessPhone" id="complaintsProcessPhone" type="text" class="show_inupt" value="${complaint.complaintsProcessPhone}" readonly="readonly"/></td>
    <td width="19%">&nbsp;</td>
    <td width="34%">&nbsp;</td>
  </tr>
  <tr>
    <td valign="top">处理结果：</td>
    <td colspan="3"><textarea name="complaintsProcessResults" id="complaintsProcessResults" class="show_textarea_tscl" cols="80" rows="15" readonly="readonly" disabled="disabled">${complaint.complaintsProcessResult}</textarea></td>
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
    <td width="12%"><font color="red">*</font>满意度评价：</td>
    <td width="35%" style="text-align:left"><select name="complaintsSatisfy" id="complaintsSatisfy" style="width:200px;height:25px">
    <option></option>
        <option>非常满意</option>
        <option>满意</option>
        <option>不满意</option>
    </select>
    </td>
    <td></td>
    <td></td>
  </tr>

  </table>
    <table>
  <tr>
    <td valign="top">反馈信息：</td>
    <td><textarea name="complaintsFeedback" id="complaintsFeedback" style="width:610px;height:200px;margin-left:15px"></textarea></td>
  </tr>
</table>
</div>
</div>



</div>
    <input name="complaintsDetail" id="complaintsDetail" type="hidden"value="${complaint.complaintsDetail}"/>
    <input name="complaintsProcessResult" id="complaintsProcessResult" type="hidden"value="${complaint.complaintsProcessResult}"/>

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
