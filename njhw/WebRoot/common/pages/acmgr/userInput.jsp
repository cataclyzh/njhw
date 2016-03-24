<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:23:12
- Description: 用户信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户信息录入</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript">	
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#username").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
<%--
wrapper:"li",// 使用"li"标签再把上边的errorELement包起来
errorClass :"validate-error",// 错误提示的css类名"error"
onsubmit:true,// 是否在提交是验证,默认:true
onfocusout:true,// 是否在获取焦点时验证,默认:true
onkeyup :true,// 是否在敲击键盘时验证,默认:true
onclick:false,// 是否在鼠标点击时验证（一般验证checkbox,radiobox）
focusCleanup:false,// 当未通过验证的元素获得焦点时,并移除错误提示
--%>
				rules: {
					username: "required",
					loginname: {
						required: true,
						remote: {
                			url: 'checkLoginname.act',
               				data: {
	                    		loginname: function() { 
	                    			return $("#loginname").val(); 
	                    		},
                    			tempLoginname: function() { 
	                    			return $("#tempLoginname").val(); 
	                    		}
                    		}
                    	}
					},
				//	password: "required",
					phone: "isPhoneNumber",
					cellphone: "isMobileNumber",
					email: "email",
					//birthday:"isDateBefore",
					address:{
 	                    maxlength:200
                	}
				},
				messages: {
					loginname: {
						remote:"此登录名已存在！"
					}
				}
			});
			$(document).ready(function(){			
				changebasiccomponentstyle();
			});
		});
		function saveData(){
		var sBir = $("#birthday").val();
		var d1 = new Date(); 		
		var d2 = new Date(sBir.replace(/-/g, "/")); 
  		if((Date.parse(d1)-Date.parse(d2))<0){
  			easyAlert('提示','生日不能大于当前日期！','warning');
  			return false;
  		}
			
			var frm = $('#inputForm');
			frm.submit();
		}
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="save.act"  method="post"  autocomplete="off" >
 <s:hidden name="orgid"/>
 <s:hidden name="userid"/>
 <s:hidden name="password"/>
  <s:hidden name="tempLoginname"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label">所属机构：</td>
        <td colspan="3">
         <s:textfield name="orgname" theme="simple" size="30" readonly="true"/>
        </td>   
      </tr> 
      <tr>
         <td class="form_label"><font style="color:red">*</font>用户名称：</td>
        <td>
         <s:textfield name="username" theme="simple" size="30" maxlength="20"  cssClass="textbox"/>
        </td>   
        <td class="form_label">用户编码：</td>
        <td>
           <s:textfield name="usercode" theme="simple" size="30" maxlength="20" cssClass="textbox"/>
        </td> 
      </tr> 
     <tr>
        <td class="form_label"><font style="color:red">*</font>登录名：</td>
        <td>
          <s:textfield name="loginname" theme="simple" size="30" maxlength="20"/>
        </td>      
          <%--  <td class="form_label"><font style="color:red">*</font>登录密码：</td>
        <td>
     <s:textfield name="password" theme="simple" size="30" maxlength="20"/>
         	<s:password name="password" theme="simple" size="30"/>
        </td>  --%>
      </tr>
     <tr>
        <td class="form_label">出生日期：</td>
        <td>
        <s:date id="format_birthday" name="birthday" format="yyyy-MM-dd"/>
        <s:textfield name="birthday" theme="simple" value="%{format_birthday}" readonly="true" size="30"/>
        <img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="birthday_img"/>
	    </td>
        <td class="form_label">性别：</td>
        <td>
          <s:select list="#application.DICT_SEX" name="sex" 
          emptyOption="false" headerKey=""  headerValue="---请选择---" 
           listKey="dictcode" listValue="dictname"/>  
        </td>  
      </tr>
      <tr>
        <td class="form_label">电话号码：</td>
        <td>
           <s:textfield name="phone" theme="simple" size="30" maxlength="20"/>
        </td> 
         <td class="form_label">手机号码：</td>
        <td>
         <s:textfield name="cellphone" theme="simple" size="30" maxlength="18"/>
        </td> 
      </tr> 
      <tr>
        <td class="form_label">电子邮箱：</td>
        <td>
           <s:textfield name="email" theme="simple" size="30" maxlength="30"/>
        </td> 
        <td class="form_label">用户状态：</td>
		<td class="form_label" width="20%">
			<s:select name="status"  theme="simple" list="#{0:'初始 ',1:'正常',2:'挂起 ',3:'锁定',4:'停用'}" onchange="changeDate(this);" listKey="key" listValue="value"/>
		</td>
      </tr> 
      <tr>
        <td class="form_label">联系地址：</td>
        <td colspan="3">
          <s:textarea  name="address" theme="simple" rows="5" cols="60"></s:textarea>
        </td>        
      </tr>
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
	Calendar.setup({
		inputField  : "birthday",	 
		ifFormat	: "%Y-%m-%d",	 
		button	  : "birthday_img"	
	});
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
</script>
