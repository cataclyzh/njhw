<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: csq
- Date: 2011-12-20
- Description: CA用户操作功能
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA用户操作功能</title>
	<%@ include file="/common/include/meta.jsp" %>
    <script type="text/javascript">
	function saveData(){
        $("#loadingdiv").show();
        $("#submit").attr("disabled",true);
		$('#userActionForm').submit();
	}   

    function closeDetail(){
    	closeEasyWin('winId');
    } 
    </script>	
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <form id="userActionForm" action="update.act" method="post">
	<s:hidden name="causeractionid" id="causeractionid"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td width="20%" class="form_label" nowrap>用户登录名：<font style="color:red">*</font></td>
        <td width="30%">
           <s:textfield name="loginname" theme="simple" readonly="true" style="background-color: aliceblue;"/>
        </td>
        <td width="20%" class="form_label" nowrap>操作编码：<font style="color:red">*</font></td>
        <td width="30%">
           <s:textfield name="actioncode" theme="simple" readonly="true" style="background-color: aliceblue;"/>
        </td> 
      </tr>
      <tr>
         <td class="form_label" nowrap>是否应用CA：<font style="color:red">*</font></td>
         <td colspan="3">
            <s:select list="#application.DICT_CA_ISVALIDCA" name="isuseca" 
         			emptyOption="false" listKey="dictcode" listValue="dictname" cssClass="input180box"/>
         </td>
      </tr> 
	</table>
    <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td>
       		<a id="submit" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp; 	
       		<a id="reset" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" onclick="$('#userActionForm').resetForm();">重置</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a id="close" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDetail();">关闭</a>   
            <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>          
        </td>
      </tr>
    </table>    
	</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','CA用户操作修改成功！','info',function(){closeEasyWin('winId');});
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','CA用户操作修改失败！','error');
</c:if>
</script>