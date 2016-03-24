<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 19:27:22
- Description:功能菜单信息录入页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>功能菜单信息录入</title>
	<%@ include file="/common/include/meta.jsp" %>
	
	<script type="text/javascript">	
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#menuname").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					menuname: "required",
					menulabel: "required",
					sortno:"digits",
					menudesc:{
 	                    maxlength:100
                	},
					menuaction:{
 	                    maxlength:100
                	}
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
 <s:hidden name="menuid"/>
 <s:hidden name="parentid"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label"><font style="color:red">*</font>菜单名称：</td>
        <td>
         <s:textfield name="menuname" theme="simple" size="30" maxlength="20"/>
        </td>   
        <td class="form_label">菜单编码：</td>
        <td>
           <s:textfield name="menucode" theme="simple" size="30" maxlength="20"/>
        </td> 
      </tr> 
      <tr>
         <td class="form_label"><font style="color:red">*</font>菜单显示名称：</td>
        <td>
         <s:textfield name="menulabel" theme="simple" size="30" maxlength="20"/>
        </td>   
        <td class="form_label">菜单排序列：</td>
        <td>
           <s:textfield name="sortno" theme="simple" size="30" maxlength="10"/>
        </td> 
      </tr> 
      <tr>   
        <td class="form_label">显示图片：</td>
        <td colspan="3">
          <s:textfield name="image" theme="simple" size="30" maxlength="30"/>
        </td>   
      </tr>
      <tr>   
        <td class="form_label">功能调用入口：</td>
        <td colspan="3">
          <s:textarea  name="menuaction" theme="simple" rows="5" cols="40"></s:textarea>
        </td>   
      </tr>
     <tr>   
        <td class="form_label">菜单描述：</td>
        <td colspan="3">
          <s:textarea  name="menudesc" theme="simple" rows="5" cols="40"></s:textarea>
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
	easyAlert('提示信息','保存功能菜单成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存功能菜单失败！','error');
</c:if>
</script>