<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
  - Author: ls
  - Date: 2010-11-20 17:14:25
  - Description: 消息发送页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>物业通知发布</title>
<%@ include file="/common/include/meta.jsp"%>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
		


<style type="text/css">
textarea {
	width: 100%;
	font-size: medium;
}

input[type="text"] {
	height: 22px;
	width: 100%;
	font-size: medium;
	font-weight: normal;
}

#submitbutton {
	cursor:pointer;
}

#message_form label.error {
	color: red;
	font-size: small;
}
</style>
	<script type="text/javascript">


$(document).ready(function() {
		


		$("#bulletin_form").validate({		// 为inputForm注册validate函数
			meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
			rules: {
				
				title:{
					required: true,
					maxlength: 25
				},
					content:{
					required: true,
					maxlength: 1800
				}
			},
			messages:{
				
				title:{
					required: "请输入通知标题",
				  	maxlength: "输入的标题字符过长"
				},
				content:{
				required: "请输入内容",
					maxlength: "输入的内容字符过长"
				}
			}
		});
	});    
	
	
		function saveData(){		
		if (showRequest()) {
	
			$('#bulletin_form').submit();
			window.parent.frames["main_frame_left"].$('#btn_viewInfo').parent().attr("class","main1_viewInfo_select");
 					window.parent.frames["main_frame_left"].$('#btn_addInfo').parent().attr("class","main1_addInfo");
				
		}
	}
	
	function showRequest(){
		 return $("#bulletin_form").valid();
	}
	
	function clearInput() {
		$('#bulletin_form').resetForm();
	}
	
</script>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"
	style="background: white;">

	<div class="main_border_01">
		<div class="main_border_02">发布通知</div>
	</div>
	
	
	<div class="main_conter">
	<div class="bgsgl_conter" style="border:0px">
		<div class="fkdj_lfrycx">
			<form id="bulletin_form" action="msgBoardAction_save.act" method="post"
				autocomplete="off" enctype="multipart/form-data">
			<div class="clear"></div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					
					<tr>
						<td class="main_from_left"><font color="red">*</font>标题</td>
						<td style="padding-top:5px;"><s:textfield name="title" id="title"
								theme="simple" maxlength="50"  size="50"
								cssStyle="width:770px;" /></td>
					</tr>
					<tr>
					<td>
		<div class="clear"></div>
					
					</td>
					</tr>
					<tr>
						<td valign="top" class="main_from_left" ><font color="red">*</font>正文</td>
						<td style="padding-top:5px;"><s:textarea
							id="content"	name="content" theme="simple"  cssStyle="width:770px; height:320px;" /></td>
					</tr>



				</table>
			</form>
			
			<!-- 
				<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_right" onclick="saveData()">确定</a>
									<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>
								</div>
			
			 -->
			
			
			
	<div class="energy_fkdj_botton_ls">
									<a class="fkdj_botton_right" onclick="saveData()">确定</a>
								<a class="fkdj_botton_left" style="cursor:pointer" onclick="clearInput()">重置</a>
									
								</div>
		</div>
		</div>
</div>
		<div style="width: 28%;float: left;margin-right: 10px;">
			<div class="clear"></div>
			

		
	
	</div>



	<script type="text/javascript"
		src="${ctx}/common/pages/bulletin/js/bulletinInput.js"></script>

</body>
</html>




<!-- 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%--
  - Author: ls
  - Date: 2010-11-16 17:32:37
  - Description: 物业通知发布页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>物业通知发布</title>	

	<script type="text/javascript">
	<c:if test="${isSuc=='true'}">
		easyAlert("提示信息","保存成功!","info",
		   function(){closeEasyWin('winId');}
		);
	</c:if>
	<c:if test="${isSuc=='false'}">
		easyAlert("提示信息","保存失败!","error");
	</c:if>
</script>
	<script type="text/javascript">
		$(function() {
			$("#title").focus();
			var options = {
				rules: {
					title: {
						required: true,
						minlength: 1
					},
					content: {
						required: true,
						maxlength: 2000,
						minlength: 1
					}
				},
				messages: {
					title: {
						required: " 请输入标题",
						minlength: " 标题最少为一个字符"
					},
					content: {
						required: " 请输入内容",
						maxlength: " 内容最多支持两千个字符",
						minlength: " 内容最少为一个字符"
					}
				}
			}
			$("#bulletin_form").validate(options);
			changebasiccomponentstyle();
		});
		
		function closeWin() {
			closeEasyWin('winId');
		}
		
		
		function submitAndClose(){
		$('#bulletin_form').submit();
		parent.closeInForm();
		}
	</script>
</head>






<body style="background: #FFF;">
<div class="main_left_main1">
   <div class="main_conter">
<div class="main1_main2_right">
<form id="bulletin_form" action="msgBoardAction_save.act" method="post"  autocomplete="off">
<div class="show_from">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
   <td width="10%"><span style="color: red">*</span>标题：</td>
<td width="70%"><s:textfield name="title" theme="simple" maxlength="60" cssClass="show_inupt"/></td>
<td width="20%"></td>    
  </tr>
  <tr>
    <td width="10%"><span style="color: red">*</span>内容：</td>
    <td width="70%"><textarea id="content" name="content" class="show_textarea" ></textarea></td>
    <td width="20%"></td>  
  </tr>
</table>
</div>
<div class="show_pop_bottom">
					<a style="float: left;cursor: pointer;"  onclick="javascript:submitAndClose()">发布</a>　　

	<a style="float: right;cursor: pointer;" onclick="parent.closeInForm()">关闭窗口</a>
</div>
</form>
   </div>
   <div class="clear"></div>
  </div>
</div>
<div style="display: none" id="sendDiv">
</div>
</body>







</html>




 -->





























