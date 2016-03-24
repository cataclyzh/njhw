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
	<title>查看待办事项详细信息</title>	
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

</head>

<body style="background: #FFF;">
<div class="main_left_main1">
   <div class="main_conter">
<div class="main1_main2_right">
   <div class="main2_border_list">
     <p>我的待办事项</p>
   </div>
<input name="MessageItemGuid" id="MessageItemGuid" value="${mapMatter.MessageItemGuid}" type="hidden"/>
<div class="show_from">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="10%">创建人：</td>
    <td width="40%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${mapMatter.FromDispName  }" /></td>
    <td width="10%">创建时间：</td>
    <td width="40%"><input name="vsSt" id="vsSt" readonly="readonly" type="text" class="show_inupt" value="${mapMatter.GenerateDate }" /></td>
  </tr>
  <tr>
  	<td widht="10%">截止时间：</td>
  	<td width="40%"><input name="vsEt" id="vsEt" type="text" readonly="readonly" class="show_inupt" value="${mapMatter.LastFileDate }" /></td>
  </tr>
  <tr>
    <td valign="top">待办事项：</td>
    <td colspan="3"><textarea name="vsCommets" style="overflow: auto;" readonly="readonly" class="show_textarea" >${mapMatter.Title  }</textarea></td>
  </tr>
</table>
</div>
<div class="show_pop_bottom">
	<a style="float: right;cursor: pointer;" onclick="parent.closeIframe()">关闭窗口</a>
</div>
   </div>
   <div class="clear"></div>
  </div>
</div>
<div style="display: none" id="sendDiv">
</div>
</body>
</html>