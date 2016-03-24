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
<%@ include file="/common/include/meta.jsp" %>
<title>资源信息录入</title>
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
	<form  id="inputForm" action="objSave.act"  method="post"  autocomplete="off" >
		<input type="hidden" id="pId" name="pId" value="${param.PId }" />
		<input id="res" name="res" value="${res}" type="hidden"/>
		<div style="width: 100%;height: 50%;" align="center">
			<!-- 门锁div -->
			<div id="doorLock" style="display: block;width: 50%;height: 100px;border: 1px solid red;">
				门锁
				<table align="center" border="0" width="100%" class="form_table">
					<tr>
						<td class="form_label"><font style="color:red">*</font>资源名称：</td>
						<td><s:textfield name="title" theme="simple" size="30" maxlength="20"/></td>
					</tr>
					<tr>
						<td class="form_label"><font style="color:red"></font>资源KEY：</td>
						<td><s:textfield name="keyword" theme="simple" size="30" maxlength="20"/></td>   
					</tr>
					<!--  
					<tr>
					<td class="form_label">上级资源：</td>
					<td> <s:textfield name="parentOrgname" theme="simple" size="30" readonly="true"/></td>
					</tr>
					-->
				</table>
			</div>
			<!-- 空调div -->
			<div id="airCondition" style="display: none;width: 50%;height: 100px;border: 1px solid red;">
				空调
				<table align="center" border="0" width="100%" class="form_table">
					<tr>
						<td class="form_label"><font style="color:red">*</font>资源名称：</td>
						<td><s:textfield name="title" theme="simple" size="30" maxlength="20"/></td>
					</tr>
					<tr>
						<td class="form_label"><font style="color:red"></font>资源KEY：</td>
						<td><s:textfield name="keyword" theme="simple" size="30" maxlength="20"/></td>   
					</tr>
					<!--  
					<tr>
					<td class="form_label">上级资源：</td>
					<td> <s:textfield name="parentOrgname" theme="simple" size="30" readonly="true"/></td>
					</tr>
					-->
				</table>
			</div>
			<!-- 电灯div -->
			<div id="light" style="display:none;width: 50%;height: 100px;border: 1px solid red;">
				电灯
				<table align="center" border="0" width="100%" class="form_table">
					<tr>
						<td class="form_label"><font style="color:red">*</font>资源名称：</td>
						<td><s:textfield name="title" theme="simple" size="30" maxlength="20"/></td>
					</tr>
					<tr>
						<td class="form_label"><font style="color:red"></font>资源KEY：</td>
						<td><s:textfield name="keyword" theme="simple" size="30" maxlength="20"/></td>   
					</tr>
					<!--  
					<tr>
					<td class="form_label">上级资源：</td>
					<td> <s:textfield name="parentOrgname" theme="simple" size="30" readonly="true"/></td>
					</tr>
					-->
				</table>
			</div>
			<div style="display:block;width: 50%;height: 100px;border: 1px solid red;">
				<table border="0" width="100%" class="form_table">
					<tr>   
						<td class="form_label">资源类型：</td>
						<td>
							<select id="extResType" name="extResType" style="width: 80px;" onchange="selectJzjgValue();">
								<option value="3">门锁</option>
								<option value="5">空调</option>
								<option value="6">电灯</option>
							</select>
							<!--功能菜单
							<s:select list="#application.DICT_RES_MENU_TYPE" headerKey=""  listKey="dictcode" listValue="dictname" name="extResType" id="extResType" onchange="showMinfo()"/>
							-->
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<table align="center" border="0" width="100%" class="form_table" >  
			<tr style="padding: 10px 0 5px 8px;text-align: center;background: #fffff;height: 50px;">
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
	easyAlert('提示信息','保存资源成功！','info',
	   function(){
		  parent.updateCallBack();
	   }
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>

function selectJzjgValue(){
	var type = $("#extResType").val();
	if("3" == type){
		$("#doorLock").show();
		$("#airCondition").hide();
		$("#light").hide();
	}
	if("5" == type){
		$("#airCondition").show();
		$("#doorLock").hide();
		$("#light").hide();
	}
	if("6" == type){
		$("#light").show();
		$("#airCondition").hide();
		$("#doorLock").hide();
	}
}
</script>

