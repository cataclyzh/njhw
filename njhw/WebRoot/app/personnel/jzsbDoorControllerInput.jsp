<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/metaIframe.jsp" %>
<title>资源信息录入</title>
<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
	jQuery.validator.addMethod("isIp", function(value, element) {  
	    var tel = /^((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))$/;
	    return this.optional(element) || (tel.test(value));
	}, "请正确填写您的ip地址");
	
	$(document).ready(function() {
		$("#inputForm").validate({
			errorElement :"span",
			rules: {
				commId:{
					required: true,
					digits:true,
					max:10000,
					remote: { //验证 通讯机号是否存在
　　						type:"POST",
　　						url:"${ctx}/app/per/checkCommId.act",
						async:false,
	　　 					data:{
	　　 						commId:function(){return $("#commId").val();},
							commIdOld:function(){return $("#commIdOld").val();}
	　　 					}
					}
				},
				commIp: {
					required: true,
					isIp:true,
					remote: { //验证 通讯机号是否存在
　　						type:"POST",
　　						url:"${ctx}/app/per/checkCommIp.act",
						async:false,
	　　 					data:{
	　　 						commIp:function(){return $("#commIp").val();},
							commIpOld:function(){return $("#commIpOld").val();}
	　　 					}
					}
				},
				memo:{
					required: true,
					maxlength:100
				}
				
			},
			messages: {
				commId: {
					required: "请输入通讯机号!",
					remote:"通讯机号已存在！"
				},
				commIp: {
					required: "请输入通讯机ip地址!",
					remote:"通讯机ip已存在！"
				},
				memo:{
					required: "请输入通讯机描述",
				}
			}
		});					
	});
	
	
	function saveData(){
		/**
		var reg=new RegExp("[0-9]+");
		if(!reg.test($("#sort").val())){
			alert("请输入数字!");
			return;
		}*/
		$('#inputForm').submit();
	}
</script>
</head>
<body topmargin="0" leftmargin="0" style="background: #FFF;">
<form  id="inputForm" action="${ctx}/app/per/jzsbDoorControllerSaveOrUpdate.act"  method="post"  autocomplete="off" >
	<input type="hidden" name="id" value="${doorControllerModel.id }"/>
    <table align="center" border="0" width="100%" class="form_table">
      
      <tr>  
        <td class="form_label"><font style="color:red">*</font>通讯机id：</td>
        <td>
        	<input type="text" name="commId" id="commId" size="30" maxlength="20" value="${doorControllerModel.commId}"/>
        	<input type="hidden" name="commIdOld" id="commIdOld" value="${doorControllerModel.commId}"/>
        </td> 
      </tr>
      <tr>
		<td class="form_label"><font style="color:red">*</font>通讯机ip：</td>
        <td>
        	<input type="text" name="commIp" id="commIp" size="30" maxlength="20" value="${doorControllerModel.commIp}"/>
        	<input type="hidden" name="commIpOld" id="commIpOld" value="${doorControllerModel.commIp}"/>
        </td>
      </tr>
      <tr>
		<td class="form_label"><font style="color:red">*</font>通讯机描述：</td>
        <td>
        	<textarea rows="4" cols="40" name="memo" style="resize:none">${doorControllerModel.memo}</textarea>
        	<%-- <input type="text" name="memo" size="30" maxlength="100" value="${doorControllerModel.memo}"/>--%>
        </td>
      </tr><tr><td valign="top"><br /></td><td valign="top"><br /></td></tr>
      
</table>
<div style = "height:20px;"></div>
<table align="center" border="0" width="100%" class="form_table">  
	<tr>
		<td colspan="2" align="center">
			<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;float:none;" id="resetbutton" onclick="saveData();">保存</a>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存资源成功！','info',
	   function(){
		closeEasyWin('dciId');
	   	var pageNo = parent.getElementById("pageNo").value;
	   	parent.jumpPage(pageNo);
		//parent.closeIframe();
	   }
	);
</c:if>
<c:if test="${isSuc=='false'}">
easyAlert('提示信息','保存资源失败！','error');
</c:if>

</script>

