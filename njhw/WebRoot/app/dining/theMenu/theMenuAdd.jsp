<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 增加菜肴页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>菜肴录入</title>
    <script type="text/javascript">	

    $(document).ready(function() {
		//聚焦第一个输入框
		$("#fdName").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				fdName: {
					required: true
				},
				fdPrice: {
					required: true
				},
				fdMain: {
					required: true
				},
				fdDosing: {
					required: true
				},
				fdPract: {
					required: true
				},
				fdClass: {
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
    <form  id="inputForm" action="saveDishes.act"  method="post"  autocomplete="off" >
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td class="form_label"><font style="color:red">*</font>菜名：</td>
        <td>
         <s:textfield name="fdName" id="fdName" theme="simple" size="18" maxlength="20"/>
        </td>   
        <td class="form_label"><font style="color:red">*</font>价格：</td>
        <td>
         <s:textfield name="fdPrice" id="fdPrice" theme="simple" size="18" maxlength="20"/>
        </td> 
     </tr>
     <tr>    
        <td class="form_label"><font style="color:red">*</font>主料：</td>
        <td>
         <s:textfield name="fdMain" id="fdMain" theme="simple" size="18" maxlength="20"/>
        </td> 
        <td class="form_label"><font style="color:red">*</font>配料：</td>
        <td>
         <s:textfield name="fdDosing" id="fdDosing" theme="simple" size="18" maxlength="20"/>
        </td>
     </tr>
     <tr> 
        <td class="form_label"><font style="color:red">*</font>做法：</td>
        <td>
         <s:textfield name="fdPract" id="fdPract" theme="simple" size="18" maxlength="20"/>
        </td> 
        <td class="form_label"><font style="color:red">*</font>分类：</td>
        <td>
<%--         <s:select list="#application.FD_CLASS" headerKey="" headerValue="---请选择---" onchange="javascript:changeReason();"  listKey="dictcode" listValue="dictname"  id="fdClass"/> --%>
         <s:textfield name="fdClass" id="fdClass" theme="simple" size="18" maxlength="20"/>
        </td> 
      </tr>
      <tr>  
        <td class="form_label">描述：</td>
        <td colspan="3">
         <s:textarea name="fdDesc" id="fdDesc" cols="71"  maxlength="99" />
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
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
</script>