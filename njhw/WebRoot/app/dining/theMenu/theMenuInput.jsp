<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 菜单管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>菜单录入</title>
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
					required: false
				},
				fdPrice: {
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
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
  </head>
  
  <body>
    <form  id="inputForm" action="inputTheMenu.act"  method="post"  autocomplete="off" >
    <s:hidden name="fdId"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td class="form_label"><font style="color:red">*</font>菜名：</td>
        <td>
        	<input type="text" name="fdName" value="${fsDishesIssue.fdName}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>菜单号：</td>
        <td>
        	<input type="text" name="fdName" value="${fsDishesIssue.fmId}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>价格：</td>
        <td>
        	<input type="text" name="fdPrice" value="${fsDishesIssue.fdPrice}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>主料：</td>
        <td>
        	<input type="text" name="fdMain" value="${fsDishesIssue.fdMain}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>配料：</td>
        <td>
        	<input type="text" name="fdDosing" value="${fsDishesIssue.fdDosing}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>类型：</td>
        <td>
        	<input type="text" name="fdClass" value="${fsDishesIssue.fdClass}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>发布类型：</td>
        <td>
        	<input type="text" name="fdiType" value="${fsDishesIssue.fdiType}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>做法：</td>
        <td>
        	<input type="text" name="fdPract" value="${fsDishesIssue.fdPract}" theme="simple" size="29" maxlength="20" />
        </td> 
      </tr> 
      <tr>
        <td class="form_label"><font style="color:red">*</font>登记日期：</td>
        <td>
        	<s:textfield onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="insertDate" name="insertDate" size="29" cssClass="Wdate" ></s:textfield>
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