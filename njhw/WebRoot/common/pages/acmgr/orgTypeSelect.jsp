<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-14 14:27:22
- Description: 机构类型录入页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>机构类型录入</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript">	
		function confirmSelect(){
			var frm = $('#inputForm');
			$('#orgtype').val($('#typeSelect').val());
			frm.submit();
		}
		$(document).ready(function(){			
			changebasiccomponentstyle();
		});
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="input.act"  method="post"  autocomplete="off" >
 <s:hidden name="parentid"/>
 <s:hidden name="orgid"/>
 <s:hidden name="treelevel"/>
 <s:hidden name="parentOrgtype"/>
 <s:hidden name="orgtype"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label" width="30%">机构类型：</td>
        <td>

        <c:if test="${parentOrgtype=='1'}">
         <s:select name="typeSelect" list="#{2:'购方虚机构',3:'销方虚机构',01:'部门'}"  listKey="key" listValue="value"/>
        </c:if>
        
        <c:if test="${parentOrgtype=='2' or parentOrgtype=='4'}">
         <s:select name="typeSelect" list="#{4:'管理机构',5:'购方',6:'购销双方'}"  listKey="key" listValue="value"/>
        </c:if>
        <c:if test="${parentOrgtype=='5' or parentOrgtype=='6'}">
         <s:select name="typeSelect" list="#{7:'门店'}"  listKey="key" listValue="value"/>
        </c:if>
        <c:if test="${parentOrgtype=='3'}">
         <s:select name="typeSelect" list="#{8:'供应商'}"  listKey="key" listValue="value"/>
        </c:if>
        </td>   
      </tr>
<s:if test="#parentOrgtype==3">　

</s:if>
    
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6"> 
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="confirmSelect();">确定</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeEasyWin('orgInput')">取消</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
