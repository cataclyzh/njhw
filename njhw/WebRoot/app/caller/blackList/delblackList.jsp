<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-29 15:27:22
- Description: 取消黑名单页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>取消黑名单</title>
	<%@ include file="/common/include/meta.jsp" %>
	
	<script type="text/javascript">	
		$(document).ready(function() {
		//聚焦第一个输入框
		$("#viName").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				viName: {
					required: false
				}
			}
		});
	});
	
		function delBlackList(){
			var frm = $('#inputForm');
			frm.submit();
		}
		
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="delBlackListButton.act"  method="post"  autocomplete="off" >
	<s:hidden name="viId"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>  
        <td class="form_label"><font style="color:red">*</font>访客姓名：</td>
        <td>${viName}</td> 
      </tr> 
      <tr>  
        <td class="form_label"><font style="color:red">*</font>身份证号：</td>
        <td>${residentNo}</td> 
      </tr> 
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
      		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="delBlackList();">保存</a>    
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','成功取消黑名单！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','取消黑名单失败！','error');
</c:if>
</script>
<s:actionmessage theme="custom" cssClass="success"/>