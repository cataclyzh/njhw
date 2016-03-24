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
	<title>查看消息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript"></script>	
	
	
	<script type="text/javascript">
		$(function() {
			$("#receiver").focus();
			var options = {
				rules: {
					replyTitle:{
						required: true,
						minlength: 1
					},
					replyContent: {
						maxlength: 300
					}
				},
				messages: {
					replyTitle: {
						required: " 请输入标题",
						minlength: " 标题最少为1个字符"
					},
					replyContent: {
						maxlength: " 内容最多支持300个字符"
					}
				}
			}
			$("#message_form").validate(options);
			changebasiccomponentstyle();
		});
		
		function closeWin() {
			//closeEasyWin('winId');
			window.top.closeIframe();
		}
		
	</script>
	<style type="text/css">
		.textfield { 
			width:240px;
		}
		.textarea { 
			width:360px;
			height:120px;
		}
	</style>

</head>

<body style="background: #FFF;">
<div class="main_left_main1">
   <div class="main_conter" style="border:none;padding:0;">
<div class="main1_main2_right">
   <div class="main2_border_list">
     <p>我的通知</p>
   </div>
<form action="" method="post" id="sendMsg">
<div class="show_from">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="10%">发件人：</td>
    <td width="40%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${sender }" /></td>
    <td width="10%">发送时间：</td>
    <td width="40%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${sendTime }" /></td>
  </tr>
  <tr>
    <td>主题：</td>
    <td colspan="3"><input name="" readonly="readonly" type="text" class="show_inupt_name" value="${title }" /></td>
  </tr>
  <tr>
    <td valign="top">信息内容：</td>
    <td colspan="3"><textarea name="" readonly="readonly" style="overflow: auto;" class="show_textarea" >${content }</textarea></td>
  </tr>
</table>
</div>
 
<div class="show_pop_bottom">
	<a style="cursor: pointer;margin-right: 20px;" onclick="closeWin();">关闭窗口</a>
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
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','消息回复成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','消息回复失败，请稍后重试！','error',
	   function(){closeEasyWin('winId');}
	);
</c:if>
</script>
