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
			<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
	<style type="text/css">
		.textfield { 
			width:240px;
		}
		.textarea { 
			width:360px;
			height:120px;
		}
	</style>
<script type="text/javascript">	
function closess(){
	$('#queryForm',parent.window.document).submit();
	parent.closeIframe();
	

}
	</script>
</head>

<body style="background: #FFF;">
<div class="main_left_main1">
<div class="main1_main2_right">
    <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">物业通知信息</div>
		</div>
<div class="show_from">

<h1 align="center">${entity.title}</h1>

   <div class="clear"></div>

<div align="center"><tt>发布人：${entity.author}</tt><tt style="padding-left:15px">发布时间：${msgTime}</tt></div>

   <div class="clear"></div>
   <div class="clear"></div>

<div align="center"><textarea id="content" name="content" readonly="readonly" style="border:0px;width:600px; height:320px;overflow-y:auto"  >${entity.content}</textarea></div>

<!-- 

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="10%">发布人：</td>
    <td width="40%"><input id="author" name="author" readonly="readonly" type="text" class="show_inupt" value="${entity.author}" /></td>
    <td width="10%">发布时间：</td>
    <td width="40%"><input name="msgtime" id="msgtime" readonly="readonly" type="text" class="show_inupt" value="${entity.msgtime}" /></td>
  </tr>
  <tr>
    <td valign="top">详细信息：</td>
    <td colspan="3"><textarea id="content" name="content" readonly="readonly" class="show_textarea" >${entity.content}</textarea></td>
  </tr>
</table>

 -->


</div>
<!-- 

<div class="show_pop_bottom">
	<a style="float: right;cursor: pointer;" onclick="javascript:closess()">关闭窗口</a>
</div>
 -->


   </div>
   <div class="clear"></div>

</div>

</body>
</html>