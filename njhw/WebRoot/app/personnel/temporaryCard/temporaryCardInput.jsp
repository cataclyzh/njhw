<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 临时卡管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>临时卡录入</title>
    <script type="text/javascript">	

    $(document).ready(function() {
		//聚焦第一个输入框
		$("#cardId").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				cardId: {
					required: false
				},
				rfid: {
					required: true
				}
			}
		});
	});
    
		function saveData(){
		  var frm = $('#inputForm');
 		  frm.submit();
		}
	</script>
  </head>
  
  <body>
    <form  id="inputForm" action="inputTemporaryCard.act"  method="post"  autocomplete="off" >
    <s:hidden name="cardId"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td class="form_label"><font style="color:red">*</font>RFID：</td>
        <td>
        	<input type="text" name="rfid" value="${rfid}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
      		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>    
        </td>
      </tr>
  </table>
  </form>
  </body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
</script>