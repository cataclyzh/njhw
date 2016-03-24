<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: ls
  - Date: 2010-11-20 17:46:46
  - Description: 收件人消息内容显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>查看物业通知详细信息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<style type="text/css">
		.textfield { 
			width:240px;
		}
		.textarea { 
			width:360px;
			height:120px;
		}
	</style>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet"
			type="text/css" />
		
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
		parent.closeIframe();
	}
}

function showRequest(){
	 return $("#bulletin_form").valid();
}

function cancelAddDevice() {
	$('#bulletin_form').resetForm();
}
</script>
</head>
<body style="background: #FFF;">
	<div class="main_left_main1">
		<div class="main1_main2_right">
			<div class="bgsgl_right_list_border">
				<div class="bgsgl_right_list_left">物业信息</div>
			</div>
		<div class="show_from">
		<form id="bulletin_form" action="updateMsg.act" method="post"  enctype="multipart/form-data" target="main_frame_right">
			<div align="center" style="color:gray;">标题：<input id="title" name="title" id="title" value="${entity.title}" style="width:100px"/></div>
			<div class="clear"></div>
			<div align="center" style="color:gray;"><tt>发布人：${entity.author}</tt><tt style="padding-left:15px">发布时间：${msgTime}</tt></div>
			<input type="hidden" name="msgid" id="msgid" value="${entity.msgid}"/> 
			<div class="clear"></div>
			<div class="clear"></div>
			<div align="center">
				<table>
					<tr>
						<td valign="top">
						正文：
						</td>
						<td>
							<textarea id="content" name="content"  style="width:600px; height:320px;overflow-y:auto"  >${entity.content}</textarea>
						</td>
					</tr>
				</table>
			</div>
		</form>
		</div>
			<div id="buttonbox_complete" class="allbox_back">
				<div class="energy_fkdj_botton_ls">
					<a class="fkdj_botton_right"  onclick="javascript:saveData()">提交</a>　
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>