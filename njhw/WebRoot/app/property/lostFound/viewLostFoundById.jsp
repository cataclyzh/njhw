<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>失物详情信息</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
	
	</head>
<body style="background: #FFF;">
<div class="main_left_main1">
<div class="main1_main2_right">
<div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">失物招领信息</div>
		</div>
<div class="show_from">

<h1 align="center">${view_lostfound.lostFoundTitle}</h1>

   <div class="clear"></div>
<div align="center" style="width:470px;height:120px;padding-left:100px;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
							
									<td style="padding-right: 10px;">
										失物名称
									</td>
									<td >
									<input class="show_inupt" value="${view_lostfound.lostFoundThingName}" readonly="readonly" disabled="disabled"/>
									</td>

									<td  style="padding-right: 10px;">
										目前状态
									</td>
									<td >
										<c:if test="${view_lostfound.lostFoundState == 0}">
							<input class="show_inupt" value="未认领" readonly="readonly" disabled="disabled"/>
										
                                  </c:if>
                              <c:if test="${view_lostfound.lostFoundState == 1}">
                         	<input class="show_inupt" value="已认领" readonly="readonly" disabled="disabled"/>
                            
                                   </c:if>
									</td>
								
								</tr>
								<tr>
									<td style="padding-right: 10px;">
										捡到地点
									</td>
									<td>
									<input class="show_inupt" value="${view_lostfound.lostFoundLocation}" readonly="readonly" disabled="disabled"/>
									</td>
									<td style="padding-right: 10px;">
										登记时间
									</td>
									<td >
							            <input class="show_inupt" value="<f:formatDate value="${view_lostfound.lostFoundIntime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/>
									</td>
								</tr>
</table>
</div>
</div>
   <div style="padding-left:103px;color:gray">
   <table>
   <tr>
   <td valign="top">
  详细信息
   </td>
   <td>
      <textarea id="content" name="content" class = "show_textarea" readonly="readonly" style="border:0px;width:410px; height:200px;overflow-y:auto" >${view_lostfound.lostFoundDetail}</textarea>
   
   </td>
   </tr>
   </table>
</div>
   </div>
   <div class="clear"></div>
</div>
</body>
</html>
