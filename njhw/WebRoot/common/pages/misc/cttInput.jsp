<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2011-10-31 15:27:22
- Description: 常量信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
	<title>常量信息录入</title>
	<style type="text/css">
	    body{background:#f6f5f1;}
	</style>
		<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#cttKey").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				errorElement :"div",
				rules: {
					cttKey: "required",
					cttValue: "required"
				}
			});					
			changebasiccomponentstyle();		
		});
	</script>
</head>
<body topmargin="0" leftmargin="0" background="#f6f5f1" >
<form  id="inputForm" action="save.act"  method="post"  autocomplete="off" >
 <s:hidden name="cttid"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label">常量键：</td>
        <td>
         <s:textfield name="cttKey" theme="simple" size="20" maxlength="40" disabled="true"/>
        <font style="color:red">*</font>
        </td>   
        <td class="form_label">常量值：</td>
        <td>
           <s:textfield name="cttValue" theme="simple" size="60" maxlength="4000"/><font style="color:red">*</font>
        </td> 
      </tr>
      <tr>
        <td class="form_label">常量类型：</td>
        <td colspan="3">
          <s:select list="#application.DICT_COMPANY" name="cttType" cssClass="input180box"
          emptyOption="false" 
           listKey="dictcode" listValue="dictname" disabled="true"/>
        </td>   
      </tr>
      <tr>
        <td class="form_label">说明：</td>
        <td colspan="3">
          <s:textarea  name="cttDesc" theme="simple" rows="4" cols="80"></s:textarea>
        </td>        
      </tr>
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
        <a style="float:right" href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
        <a style="float:right" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	    function(){
		    closeEasyWin('cttInput');
		    parent.updateCallBack();
	    }
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
function saveData(){
	$('#inputForm').submit();
}
</script>

