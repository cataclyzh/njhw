<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: ls
  - Date: 2010-11-19 15:05:37
  - Description: 物业通知显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>物业通知</title>	
	<%@ include file="/common/include/meta.jsp" %>
</head>


<body style="background: #FFF;">
<div class="main_left_main1">
   <div class="main_conter">
<div class="main1_main2_right">
   <div class="main2_border_list">
     <p>物业信息</p>
   </div>
<div class="show_from">
<table width="100%" border="0" cellpadding="0" cellspacing="0">

  			<td  align="center"><span style="font-size:36px"><s:text name="title"/></span></td>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">

  <tr>
  
    <td width="10%">发布人：</td>
    <td width="10%"><s:text name="author"/></td>
    <td width="10%">发布时间：</td>
    <td width="10%"><s:date name="msgtime" format="yyyy-MM-dd HH:mm"/></td>
  </tr>
  <tr>
    <td valign="top">详细信息：</td>
    <td colspan="3"><s:text name="content" /></td>
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