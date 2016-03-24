<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-06 15:27:22
- Description: 业务字典录入页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>业务字典录入</title>
	<%@ include file="/common/include/meta.jsp" %>
	
	<script type="text/javascript">	
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#dictname").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					dictname: {
						required: true
					},
					dictcode: {
						required: true
					},
					sortno:"digits"
				}
			});
		});
		
		function saveData(){
			var frm = $('#inputForm');
			frm.submit();
		}
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="saveDeta.act"  method="post"  autocomplete="off" >
	<s:hidden name="dictid"/>
	<s:hidden name="dicttype"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label"><font style="color:red">*</font>业务字典名称：</td>
        <td>
         <s:textfield name="dictname" theme="simple" size="18" maxlength="40"/>
        </td>   
        <td class="form_label"><font style="color:red">*</font>业务字典编码：</td>
        <td>
           <s:textfield name="dictcode" theme="simple" size="18" maxlength="40"/>
        </td> 
      </tr> 
      <tr>
         <td class="form_label">排序：</td>
        <td>
           <s:textfield name="sortno" theme="simple" size="18" maxlength="10"/>
        </td> 
         <td class="form_label">状态：</td>
        <td colspan="3">
         <s:textfield name="status" theme="simple" size="18" maxlength="2"/>
        </td>   
      </tr>
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
      		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>    
      		<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>  
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存业务字典明细成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存业务字典明细失败！','error');
</c:if>
</script>