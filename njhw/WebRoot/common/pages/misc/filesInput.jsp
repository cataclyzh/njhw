<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2011-03-04 19:27:22
- Description:文件下载信息录入页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>文件下载信息录入</title>
	<%@ include file="/common/include/meta.jsp" %>
	

	<script type="text/javascript">	
		$(document).ready(function() {
			$("#menuname").focus();
			$("#inputForm").validate({
				meta :"validate",
				errorElement :"div",
				rules: {
					company: "required",
					softtype: "required",
					filetype: "required",
					softver: "required",
					softname: "required",
					description:{
 	                    maxlength:200
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
 <s:hidden name="downid"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label"><font style="color:red">*</font>所属公司：</td>
        <td>
          <s:select list="#application.DICT_COMPANY" name="company" 
           listKey="dictcode" listValue="dictname"/>
        </td>   
         <td class="form_label"><font style="color:red">*</font>所属公司：</td>
        <td>
          <s:select list="#{'5':'购方','8':'销方','6':'购销双方'}" name="orgtype" emptyOption="false" />
        </td>   
      </tr> 
      <tr>   
        <td class="form_label"><font style="color:red">*</font>软件类型：</td>
        <td>
          <s:select list="#application.DICT_SOFTDOWN_TYPE" name="softtype" 
           listKey="dictcode" listValue="dictname"/>
        </td> 
        <td class="form_label"><font style="color:red">*</font>文件类型：</td>
        <td>
          <s:select list="#application.DICT_SOFTDOWN_FILETYPE" name="filetype" 
           listKey="dictcode" listValue="dictname"/>
        </td>     
      </tr>
      <tr>   
        <td class="form_label"><font style="color:red">*</font>软件名称：</td>
        <td colspan="1">
          <s:textfield name="softname" theme="simple" size="28"/>  
        </td> 
        <td class="form_label"><font style="color:red">*</font>文件名称：</td>
        <td>
          <s:textfield name="filename" theme="simple" size="28"/>  
        </td> 

      </tr>
      <tr>   
        <td class="form_label">版本号：</td>
        <td>
          <s:textfield name="softver" theme="simple" size="20"/>  
        </td>   
        <td class="form_label">发布日期：</td>
        <td colspan="3">
        <s:date id="format_releasedate" name="releasedate" format="yyyy-MM-dd"/>
        <s:textfield name="releasedate" theme="simple" value="%{format_releasedate}" readonly="true" size="18"/>
        <img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="releasedate_img"/>
	    </td>

      </tr>
      <tr>   
        <td class="form_label"><font style="color:red">*</font>外部文件地址：</td>
        <td colspan="3">
          <s:textarea  name="outurl" theme="simple" rows="3" cols="50"></s:textarea> 
        </td>   
      </tr>
     <tr>   
        <td class="form_label">软件描述：</td>
        <td colspan="3">
          <s:textarea  name="description" theme="simple" rows="5" cols="50"></s:textarea>
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
	inputField  : "releasedate",	 
	ifFormat	: "%Y-%m-%d",	 
	button	  : "releasedate_img"	
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