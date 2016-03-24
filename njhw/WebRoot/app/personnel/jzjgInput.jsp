<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/metaIframe.jsp" %>
<title>资源信息录入</title>
<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
	$(document).ready(function() {
		$("#title").focus();
		$("#inputForm").validate({
			errorElement :"div",
			rules: {
				title: "required",
				name: "required"
			},
			messages: {
				title: {
					required: "请输入资源名称!"
				},
				name: {
					required: "请输入资源编码!"
				}
			}
		});					
		changebasiccomponentstyle();
		$("#extResType").val("${entity.extResType}".replace(/\s+/g, ""));
		$("#blank").val("${entity.blank}".replace(/\s+/g, ""));
		$("#sort").val("${entity.sort}".replace(/\s+/g, ""));
		showMinfo();
	});
	
	function showMinfo() {
	   //alert($("#extResType").val());
		if ($("#extResType").val() == "M") $("#M_Info").show();
		else {
			$("#M_Info").hide();
			$("#ico").val("");
			$("#link").val("");
			$("#sort").val("0");
			$("#blank").val("0");
		}
	}
	
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
<form  id="inputForm" action="jzjgSave.act"  method="post"  autocomplete="off" >
 <s:hidden name="PId"/>
 <s:hidden name="nodeId"/>
 <input id="res" name="res" value="${res}" type="hidden"/>
    <table align="center" border="0" width="100%" class="form_table">
      
      <tr>  
        <td class="form_label"><font style="color:red">*</font>门牌号码：</td>
        <td><s:textfield name="name" theme="simple" size="30" maxlength="20"/></td> 
      </tr>
      <tr>
		<td class="form_label"><font style="color:red">*</font>房间名称：</td>
        <td><s:textfield name="title" theme="simple" size="30" maxlength="20"/></td>
      </tr>
      
      <!-- 
      <tr>
        <td class="form_label"><font style="color:red"></font>资源KEY：</td>
        <td><s:textfield name="keyword" theme="simple" size="30" maxlength="20"/></td>   
      </tr>
      <tr>
		<td class="form_label">上级资源：</td>
		<td> <s:textfield name="parentOrgname" theme="simple" size="30" readonly="true"/></td>
      </tr>
      -->
      <tr style="display: none;">   
        <td class="form_label">资源类型：</td>
        <td>
         
        <select id="extResType" name="extResType" style="width: 80px;" onchange="selectJzjgValue();">
        	<option value="R" selected="selected">房间</option>
        </select>
	    
        <!--   <s:select list="#application.DICT_EXT_RES_TYPE" headerKey="" headerValue="请选择..."  listKey="dictcode" listValue="dictname" name="extResType" id="extResType" onchange="showMinfo()"/>-->
        </td>
        <!-- 添加时   修改时-->
        <td>
     </tr>
</table>
<div style = "height:20px;"></div>
<table align="right" border="0" width="100%" class="form_table">  
	<tr>
		<td colspan="2" align="right">
			<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;float:none;" id="resetbutton" onclick="saveData();">保存</a>
			<!-- <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;float:none;" id="searchbutton" onclick="closeEasyWin('jzjgId')">关闭</a> -->
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
		closeEasyWin('jzjgId');
	   }
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
<c:if test="${isSuc=='error'}">
	easyAlert('提示信息','输入的门牌号已存在,保存资源失败！','error');
</c:if>
function selectJzjgValue(){
	alert($("#extResType").val());	
}
</script>

