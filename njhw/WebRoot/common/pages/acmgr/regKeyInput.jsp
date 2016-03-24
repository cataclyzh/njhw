<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2011-01-27 15:27:22
- Description: 注册码生成录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>注册码生成录入</title>
	<%@ include file="/common/include/meta.jsp" %>
		
	<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#taxno").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				//meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					taxno: "required",
					taxname: "required"
				}
			});					
			changebasiccomponentstyle();		
		});
		function saveData(){
			var frm = $('#inputForm');
			frm.submit();
		}
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="save.act"  method="post"  autocomplete="off" >
 <s:hidden name="regid"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label"><font style="color:red">*</font>纳税人识别号：</td>
        <td>
         <s:textfield name="taxno" theme="simple" size="25" maxlength="20"/>         	
        </td>   
        <td class="form_label"><font style="color:red">*</font>纳税人名称：</td>
        <td>
           <s:textfield name="taxname" theme="simple" size="25" maxlength="20"/>
        </td> 
      </tr>
      <tr>
        <td class="form_label">系统：</td>
        <td>
         <s:select list="#application.DICT_REGKEY_SYS" name="sys" emptyOption="false" listKey="dictcode" listValue="dictname"/>  
        </td>   
        <td class="form_label">截止月份：</td>
        <td>
        <s:date id="format_uptomon" name="uptomon" format="yyyyMM"/>
        <s:textfield name="uptomon" theme="simple" readonly="true" size="18"/>
        <img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="uptomon_img"/>
        </td> 
      </tr>
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
        	<ahref="javascript:void(0);"" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>    
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
	Calendar.setup({
		inputField  : "uptomon",	 
		ifFormat	: "%Y%m",	 
		button	  : "uptomon_img"	
	});
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
<c:if test="${isSuc=='exist'}">
	easyAlert('提示信息','注册码已经存在,保存失败！','error');
</c:if>
</script>

