<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源管理资源树页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<title>资源树</title>	
<style>
.table_btn_orgframe
{
    width: 68px;
    height: 22px;
    background:#8090b2;
	color:#fff;
	line-height:22px;
	text-align:center;
	font-family:"微软雅黑";
	margin-right:10px;
	float:right;
	cursor:pointer;
	text-decoration: none;
}
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
.inpu_trst tr{height:40px;}
</style>
</head>
<body style="background: #FFF;">
<form  id="queryForm" action="jzjgLightInputSave.act"  method="post"  autocomplete="off" >
<table border="0" width="100%" class="form_table inpu_trst" style="margin-top:20px;">
 <tr>
	<td></td>
	<td class="form_label"><font style="color:red">*</font>名称</td>
	<td><input type="text" id="name" name="name" value=""></input></td>
	<td class="form_label"><font style="color:red">*</font>OBX点位</td>
	<td><input type="text" id="keyword" name="keyword" value=""></input></td>
	<td></td>
 </tr>
 <tr>
	<td></td>
	<td class="form_label"><font style="color:red"></font>备注</td>
	<td><input type="text" id="title" name="title" value=""></input></td><td class="form_label"><font style="color:red">*</font>房间选择</td>
	<td><input type="text" readonly="readonly" style="width:80px;float:left;" id="titleName" name="titleName" value=""></input>
	<input type="hidden" readonly="readonly" style="width:80px;float:left;" id="nodeId" name="nodeId" value=""></input>
	<a style="width:60px;float:left;margin-left:10px;height:24px;line-height:24px;" onclick="selectHome();" class="table_btn_orgframe" >选择房间</a></td>
	<td></td>
 </tr>
 <tr>
	<td colspan="5"><a href="javascript:void(0);" class="inpu_trst_a" iconCls="icon-save" onclick="saveData();">保存</a></td>
 </tr>
</table>
</form>
<script type="text/javascript">
function saveData(){
	var nodeId = $("#nodeId").val();
	if(nodeId == ""){
		easyAlert('提示信息','请选择具体房间！','error');
		return;
	}
	if($('#name').val() == ""){
		easyAlert('提示信息','请填写名称','error');
		return;
	}
	if($('#keyword').val() == ""){
		easyAlert('提示信息','请填写OBX点位','error');
		return;
	}
		
	$("#queryForm").submit();
				
}
function selectHome(){
	var url = "${ctx}/app/per/jzsbLockInputTest.act?nodeId="+${param.nodeId};
	openEasyWin("winIds","选择房间",url,"251","352",false);
	//$("#innerTree").load(url);
	//showShelter1("550","300",url,'dd');
	//showIframe("250","100","${ctx}/app/per/jzsbLockInputTest.act?nodeId="+${param.nodeId});
}
</script>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存资源成功！','info',function(){
		parent.closeEasyWin('winId');
	});
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
</script>
