<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA操作</title>
	<%@ include file="/common/include/meta.jsp" %>
		

	<script type="text/javascript">	
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#actionname").focus();
			
			var filledDesc = $("#actiondesc").val();
            var filledLength = filledDesc.length;
            var maxLength = 160;
			$("#remainCount").text(maxLength - filledLength);
			
		    $("#actiondesc").bind('keyup blur', function(){
	            var filledDesc = $("#actiondesc").val();
	            var filledLength = filledDesc.length;
	            var maxLength = 160;
	            if(filledLength >= maxLength){
	                $("#remainCount").text(0);
	                var desc = filledDesc.substr(0, maxLength);  
	                $("#actiondesc").val(desc);
	            }
	            else{
	                $("#remainCount").text(maxLength - filledLength);
	            }
       		 });
			
			//为inputForm注册validate函数
			$("#inputForm").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					actionname: {
						required: true
					},
					actioncode:{
 	                    required: true,
 	                    remote:{
							url:'actioncodeValidate.act',
							data:{
								actioncode: function(){
									return $("#actioncode").val();
								}
							}
 	                    }
                	}
				},
				messages: {
					actionname: {
						required:"请输入操作名称！"
					},
					actioncode: {
						required:"请输入操作代码！",
						remote:"操作代码已存在，请重新输入！"
					}
				},
				submitHandler : function(form) {
					disableButton();
					form.submit();
			    }
			});
		
		});
		
		function saveData(){
			var actionid = $("#actionid").val();
			var frm;
			if(actionid == null || actionid == ""){
			    frm = $('#inputForm').attr("action","save.act");
			    $('#inputForm').submit();
			} else {
				frm = $('#inputForm').attr("action","update.act");
				$('#inputForm').submit();
			}
			
		}	
		
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action=""  method="post"  autocomplete="off" >
	<s:hidden name="actionid" id="actionid"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td width="20%" class="form_label"><font style="color:red">*</font>操作名称：</td>
        <td width="30%">
         <s:textfield name="actionname" theme="simple" size="30" maxlength="30"/>
        </td>
        <td width="20%" class="form_label"><font style="color:red">*</font>操作代码：</td>
        <td width="30%">
         <c:if test="${actionid == null}">
         <s:textfield id="actioncode" name="actioncode" theme="simple" size="30" maxlength="13"/>
         </c:if>
         <c:if test="${actionid != null}">
         <s:property value="actioncode"/>
         </c:if>
        </td> 
      </tr> 
     <tr>   
        <td class="form_label">操作描述：</td>
        <td colspan="3">
          <s:textarea  name="actiondesc" theme="simple" rows="5" cols="40"></s:textarea>
          <span style="width: 50px">还可以输入</span>
          <span id="remainCount" style="color: red"></span>
          <span>个字符</span>
        </td> 
      </tr>
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
       		<a href="javascript:void(0);" id="submit" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" disable="true">保存</a> 	
       		<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>          
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
<c:if test="${isSuc=='del'}">
	easyAlert('提示信息','要修改的数据已被删除，请重新选择！','error');
</c:if>
</script>