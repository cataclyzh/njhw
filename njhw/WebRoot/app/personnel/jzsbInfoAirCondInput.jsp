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
	function saveData(){
		if($('#name').val() == ""){
			easyAlert('提示信息','请填写名称','error');
			return;
		}
		if($('#keyword').val() == ""){
			easyAlert('提示信息','请填写OBX点位','error');
			return;
		}
		$('#inputForm').submit();
	}
</script>
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
	margin:0 0 0 auto;
}
</style>
</head>
<body topmargin="0" leftmargin="0" style="background: #FFF;">
	<form  id="inputForm" action="jzsbInfoLightUpdate.act"  method="post"  autocomplete="off" >
		<input type="hidden" id="nodeId" name="nodeId" value="${param.nodeId}" />
		
		<div style="width: 100%;height: 50%;" align="center">
			<!-- 电灯div -->
			<div id="light" style="display:block;width: 50%;height: 100px;border: 0px solid red;">
				<table align="center" border="0" width="100%" class="form_table">
					<tr>
						<td class="form_label"><font style="color:red">*</font>名称：</td>
						<td><input type="text" id="name" name="name" size="30" maxlength="20" value="${objtank.name }" /></td>
					</tr>
					<tr>
						<td class="form_label"><font style="color:red">*</font>OBX点位：</td>
						<td><input type="text" id="keyword" name="keyword" size="30" maxlength="20" value="${objtank.keyword }" /></td>   
					</tr>
					<tr>
						<td class="form_label"><font style="color:red"></font>备注：</td>
						<td><input type="text" name="title" size="30" maxlength="20" value="${objtank.title }" /></td>   
					</tr>
				</table>
			</div>
		</div>
		
		<table align="center" border="0" width="100%" class="form_table" >  
			<tr style="padding: 10px 0 5px 8px;text-align: center;background: #fffff;height: 50px;">
				<td colspan="6">
					<a href="javascript:void(0);" class="inpu_trst_a" iconCls="icon-save" onclick="saveData();">保存</a>
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
		  parent.closeEasyWin('winId');
	   }
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
</script>

