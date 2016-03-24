<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 组织机构信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>入驻单位</title>
	<%@ include file="/common/include/metaIframe.jsp" %>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<style>
		.inpu_trst_a{
			margin:0 atuo;
			width: 68px;
			height: 22px;
			background:#8090b2;
			color:#fff;
			line-height:22px;
			display:block;
			text-align:center;
			font-family:"微软雅黑";
			text-decoration:none;
			margin:0 5px 0 auto;
		}
	</style>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#title").focus();
			$("#inputForm").validate({
				errorElement :"div", //当div使用有样式问题时，使用span
				rules: {
					shortName: {
						required: true,
						maxlength: 30
					},
					name: {
						required: true,
						maxlength: 30
					},
					orderNum:{
						required:true,
						maxlength:6
					},
					memo:{
						maxlength: 30
					}
				},
				messages: {
					shortName: {
						required: "请输入简称!",
						maxlength: "简称长度不能大于30个字符"
					},
					name: {
						required: "请输入名称!",
						maxlength: "名称长度不能大于30个字符"
					},
					orderNum: {
						required: "请输入排序号",
						maxlength: "排序号长度不能大于6个数字"
					},
				}
			});					
			changebasiccomponentstyle();
			$("#extResType").val("${entity.extResType}".replace(/\s+/g, ""));
			$("#blank").val("${entity.blank}".replace(/\s+/g, ""));
			$("#sort").val("${entity.sort}".replace(/\s+/g, ""));
			showMinfo();
		});
		
		function showMinfo() {
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
			var reg=new RegExp("[0-9]+");
			
			if(!reg.test($("#orderNum").val())){
				alert("排序码请输入数字!");
				return;
			}
			if($('#inputForm').valid()){
				openAdminConfirmWin(saveDataFn);
			}
		}
		
		function saveDataFn() {
			$('#inputForm').submit();
		}
		
		function closeWin() {
			parent.window.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckin.act";
			parent.window.top.$("#orgInput").window("close");
		}
	</script>
</head>
<body style="background:#fff;height: 150px;">
<form  id="inputForm" action="rzdwSave.act"  method="post"  autocomplete="off" >
	<s:hidden name="PId" />
	<input type="hidden" id="orgId" name="orgId" value="${param.orgId}"/>
	<div class="infrom_ryjbxx">
	<table align="center" border="0" width="100%">
		<tr>
			<td class="form_label"><div class="infrom_td"><font style="color:red">*</font>名称：</div></td>
			<td> <s:textfield name="name" theme="simple" size="30" maxlength="30"/></td>
		</tr>
		<tr>  
			<td class="form_label"><div class="infrom_td"><font style="color:red">*</font>简称：</div></td>
			<td> <s:textfield name="shortName" theme="simple" size="30" maxlength="30"/></td> 
		</tr>
		<tr>
			<td class="form_label"><div class="infrom_td"><font style="color:red">*</font>排序码：</div></td>
			<td><s:textfield name="orderNum" theme="simple" size="30" maxlength="6"/></td>
		</tr>
		<!-- tr>
		<td class="form_label"><font style="color:red"></font>备注：</td>
		<td><s:textfield name="memo" theme="simple" size="30" maxlength="30"/></td>   
		</tr> -->
		<tr style="display: none;">
		<td class="form_label">上级组织机构：</td>
		<td> <s:text name="parentOrgname"/></td>
		</tr>
		
	</table>
	</div>
	<div style = "height:5px;"></div>
	<table align="center" border="0" width="100%" class="form_table">  
		<tr>
			<td colspan="2" align="center">
				<a href="javascript:void(0);" class="inpu_trst_a" id="resetbutton" onclick="saveData();">保存</a>
<%--				<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;float:none;" id="searchbutton" onclick="closeEasyWin('winId')">关闭</a> --%>
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
<c:if test="${isSuc=='error'}">
easyAlert('提示信息','输入的入驻单位名称已存在,保存资源失败！','error');
</c:if>
</script>