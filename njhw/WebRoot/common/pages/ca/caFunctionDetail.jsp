<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA操作明细</title>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0">
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td width="20%" class="form_label">操作名称：</td>
        <td width="30%">
         <s:property value="actionname"/>
        </td>
        <td width="20%" class="form_label">操作代码：</td>
        <td width="30%">
         <s:property value="actioncode"/>
        </td> 
      </tr> 
     <tr>   
        <td class="form_label">操作描述：</td>
        <td colspan="3">
          <s:property value="actiondesc"/>
        </td>   
      </tr>
 
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
       		<a href="javascript:closeEasyWin('winId');" class="easyui-linkbutton" id="resetbutton">关闭</a> 	        
        </td>
      </tr>
  </table>
</body>
</html>
