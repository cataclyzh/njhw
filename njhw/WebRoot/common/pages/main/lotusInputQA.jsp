<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: WXJ
  - Date: 2011-12-1 11:48:03
  - Description:莲花供应商满意度调查
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>莲花供应商满意度调查</title>	
	<%@ include file="/common/include/meta.jsp" %>	
		<script type="text/javascript">		
		$(function() {
			$('body').bind('contextmenu', function() {
		    	return false;
		    });
			$('body').bind('paste', function() {
		    	return false;
		    });
			$("#content_form").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				rules: {
					"modelQA.f12":{
						maxlength: 200
					}
				},
				messages: {
					"modelQA.f12":{
						maxlength: "最多100汉字!"
					}
				},
				submitHandler: function(form) {
					$("#loadingdiv").show();
					$('a.easyui-linkbutton').linkbutton('disable');
					form.submit();
				}
			});
		});	
		function doSubmit(){
			$("#content_form").submit();	
		}
	</script>
	<style type="text/css">
		.textfield { 
			width:240px;
		}
	</style>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"> 
	<form id="content_form" action="saveQa.act" method="post"  autocomplete="off">
		<s:hidden name="userid" value="%{#session._userid}"/>
		<table align="center" border="0" width="800px" class="form_table">
		<caption>供应商满意度调查表</caption>
			<tr>
				<td class="form_label" colspan="2" style="color:red;font-size:16px;" nowrap>
尊敬的用户：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卜蜂莲花供应商对账系统已上线一年，为切实了解您的实际需求，完善与提升我们的服务质量， <br>我们诚挚邀请您参加本次用户满意度调查。 <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请您就下列问题发表宝贵意见，您的答案对于我们不断改进各项工作具有非常重要的价值。 <br>非常感谢您的配合！

				</td>
			</tr>

			<tr>
				<td class="form_label" nowrap colspan="2">一、	JCosmo</td>
			</tr>
			<tr>
				<td colspan="2" nowrap>1、使用方便、易操作：<s:radio name="modelQA.f1" list="#{'0':'非常满意','1':'满意','2':'一般','3':'差'}" value="1" listKey="key" listValue="value" ></s:radio></td>
			</tr>
			<tr>
				<td colspan="2" nowrap>2、数据抽取快捷、准确：<s:radio name="modelQA.f2" list="#{'0':'非常满意','1':'满意','2':'一般','3':'差'}" value="1" listKey="key" listValue="value" ></s:radio></td>
			</tr>			
			<tr>
				<td class="form_label" nowrap colspan="2">二、	JCosmo</td>
			</tr>
			<tr>
				<td colspan="2" nowrap>1、界面友好、美观：　<s:radio name="modelQA.f3" list="#{'0':'非常满意','1':'满意','2':'一般','3':'差'}" value="1" listKey="key" listValue="value" ></s:radio></td>
			</tr>
			<tr>
				<td colspan="2" nowrap>2、操作流程简洁、易操作：<s:radio name="modelQA.f4" list="#{'0':'非常满意','1':'满意','2':'一般','3':'差'}" value="1" listKey="key" listValue="value" ></s:radio></td>
			</tr>
			<tr>
				<td colspan="2" nowrap>3、系统功能完善，有效提高工作效率：<s:radio name="modelQA.f5" list="#{'0':'非常满意','1':'满意','2':'一般','3':'差'}" value="1" listKey="key" listValue="value" ></s:radio></td>
			</tr>
			<tr>
				<td class="form_label" nowrap colspan="2">三、	售后服务</td>
			</tr>
			<tr>
				<td colspan="2" nowrap>1、服务态度良好、热情周到：<s:radio name="modelQA.f6" list="#{'0':'非常满意','1':'满意','2':'一般','3':'差'}" value="1" listKey="key" listValue="value" ></s:radio></td>
			</tr>
			<tr>
				<td colspan="2" nowrap>2、服务质量高效、问题及时有效解决：<s:radio name="modelQA.f7" list="#{'0':'非常满意','1':'满意','2':'一般','3':'差'}" value="1" listKey="key" listValue="value" ></s:radio></td>
			</tr>
			<tr>
				<td class="form_label" width="160px" nowrap>四、其它意见或建议：</td>
				<td>
          			<s:textarea id="f12" name="modelQA.f12" theme="simple" rows="4" cols="50"></s:textarea>
        		</td>   
			</tr>
			<tr>
				<td colspan="2" class="form_bottom">
					<a href="javascript:void(0);" class="easyui-linkbutton" id="submitbutton" iconCls="icon-ok" onclick="doSubmit();">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#content_form').resetForm();">重置</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/j_spring_security_logout" class="easyui-linkbutton" id="logoutbutton" iconCls="icon-reload" >注销</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="loadingdiv" class="icon-loding" style="display: none" >正在执行请稍后</span>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	using('messager', function () {
		$.messager.alert('提示信息','调查表提交成功，谢谢您的支持，请重新登录！','info',
	   function(){top.location.href = '${ctx}/j_spring_security_logout';});
	});	
</c:if>
<c:if test="${isSuc=='false'}">
	using('messager', function () {
		$.messager.alert('提示信息','调查表提交失败！','info');
	});	
</c:if>
</script>