<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<%--
- Author: WXJ
- Date: 2010-09-06 15:27:22
- Description: 业务字典类型录入页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
	<title>业务字典类型录入</title>
		

	<script type="text/javascript">	
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#dicttypename").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					dicttypename: {
						required: true
					},
					dicttypedesc:{
 	                    maxlength:100
                	},
					dicttypecode: {
						required: true,
						remote: {
                			url: 'checkDicttypecode.act',
               				data: {
	                    		dicttypecode: function() { 
	                    			return $("#dicttypecode").val(); 
	                    		},
                    			tempDicttypecode: function() { 
	                    			return $("#tempDicttypecode").val(); 
	                    		}
                    		}
                    	}
					}
				},
				messages: {
					dicttypecode: {
						remote:"此编码已存在！"
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
<form  id="inputForm" action="saveType.act"  method="post"  autocomplete="off" >
	<s:hidden name="dicttypeid"/>
	<s:hidden name="tempDicttypecode"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td class="form_label"><font style="color:red">*</font>字典类型名称：</td>
        <td>
         <s:textfield name="dicttypename" theme="simple" size="18" maxlength="20"/>
        </td>
        <td class="form_label"><font style="color:red">*</font>字典类型编码：</td>
        <td>
         <s:textfield name="dicttypecode" theme="simple" size="18" maxlength="20"/>
        </td> 
      </tr> 
     <tr>   
        <td class="form_label">字典类型描述：</td>
        <td colspan="3">
          <s:textarea  name="dicttypedesc" theme="simple" rows="5" cols="40"></s:textarea>
        </td>   
      </tr>
 <%--
     <tr>   
        <td class="form_label">YYY：</td>
        <td colspan="1">
       
          <s:select list="#application.DICT_ORG_TYPE" name="fplxx" 
          emptyOption="false" headerKey="%"  headerValue="---请选择---" 
           listKey="dictcode" listValue="dictname"/>
     
        </td>   
        <td class="form_label">XXX：</td>
        <td colspan="1">

          <s:select list="#application.DICT_ORG_TYPE" name="fplxxx" 
          emptyOption="false" headerKey="%"  headerValue="---请选择---" 
           listKey="dictcode" listValue="dictname"/>
     
        </td>   
      </tr>--%>
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
	easyAlert('提示信息','保存业务字典类型成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存业务字典类型失败！','error');
</c:if>
</script>