<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-01 9:29:20
- Description: 角色信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>角色信息录入</title>
	<%@ include file="/common/include/meta.jsp" %>
	

	<script type="text/javascript">	
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#rolename").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					rolename: "required",
					roledesc:{
 	                    maxlength:100
                	},
                	rolecode: "required"
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
 <s:hidden name="orgid"/>
 <s:hidden name="roleid"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label"><font style="color:red">*</font>角色名称：</td>
        <td>
         <s:textfield name="rolename" theme="simple" size="18" maxlength="20"/>
        </td>   
        <td class="form_label"><font style="color:red">*</font>角色编码：</td>
        <td>
           <s:textfield name="rolecode" theme="simple" size="18" maxlength="20"/>
        </td> 
      </tr> 
     <tr>   
        <td class="form_label">角色描述：</td>
        <td colspan="3">
          <s:textarea  name="roledesc" theme="simple" rows="5" cols="40"></s:textarea>
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
	easyAlert('提示信息','保存角色成功！','info',
	   function(){closeEasyWin('winRoleId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存角色失败！','error');
</c:if>
</script>