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
<form  id="queryForm" action="jzsbLockInputSave.act"  method="post"  autocomplete="off" >
<input type="hidden" id="selectValue" name="selectValue" value="" />
<table border="0" width="100%" class="form_table inpu_trst" style="margin-top:20px;">
 <tr>
	<td></td>
	<td class="form_label"><font style="color:red">*</font>门锁名称</td>
	<td><input type="text" id="name" name="name" value=""></input></td>
	<td class="form_label"><font style="color:red">*</font>通讯机</td>
	<td>
		<select style="width: 152px;" id="NjhwDoorComm">
			<script type="text/javascript">
				var url = "${ctx}/app/per/queryNjhwDoorCommJSON.act";	
				$.ajax({
					type:'POST',
					url:url,
					dateType:'json',
					async:false,
					success:function(json){
						if(json.length > 0) {
							$.each(json, function(i) {
								var options = "<option id='"+json[i].COMM_ID+"' value='"+json[i].ID+"'>"+json[i].MEMO+"</option>";
								document.write(options);
							});
						}
					},
					error:function(json){
						easyAlert('提示信息','下拉框AJAX 操作错误！','error');
					}
				});
			</script>
		</select>
	</td>
	<td></td>
 </tr>
 <tr>
	<td></td>
	<td class="form_label"><font style="color:red">*</font>门锁地址</td>
	<td><input type="text" id="adrsStore" name="adrsStore" value=""></input></td>
	<td class="form_label"><font style="color:red"></font>备注</td>
	<td><input type="text" id="title" name="title" value=""></input></td>
	<td></td>
 </tr>
 <tr>
	<td></td>
	<td class="form_label"><font style="color:red">*</font>房间选择</td>
	<td><input type="text" readonly="readonly" style="width:80px;float:left;" id="titleName" name="titleName" value=""></input>
	<input type="hidden" readonly="readonly" style="width:80px;float:left;" id="nodeId" name="nodeId" value=""></input>
	<a style="width:60px;float:left;margin-left:10px;height:24px;line-height:24px;" onclick="selectHome();" class="table_btn_orgframe" >选择房间</a></td>
	<td></td>
		<td></td>
	<td></td>
 </tr>
 <tr>
	<td colspan="5"><a href="javascript:void(0);" class="inpu_trst_a" iconCls="icon-save" onclick="saveData();">保存</a></td>
 </tr>
</table>
</form>
<script type="text/javascript">
var _ctx = '${ctx}';
function saveData(){
	var nodeId = $("#nodeId").val();
	var url = "${ctx}/app/per/queryExtTypeById.act?nodeId="+nodeId;
	
	$.ajax({
		type:'POST',
		url:url,
		dateType:'json',
		async:false,
		success:function(json){
			if(json.extType != "R"){
				easyAlert('提示信息','请选择具体房间！','error');
				return;
			}else{
				if(nodeId == ""){
					easyAlert('提示信息','请选择具体房间！','error');
					return;
				}
				if($('#name').val() == ""){
					easyAlert('提示信息','请填写名称','error');
					return;
				}
				if($('#NjhwDoorComm').val() == ""){
					easyAlert('提示信息','请选择通讯机','error');
					return;
				}
				if($('#adrsStore').val() == ""){
					easyAlert('提示信息','请填写门锁地址','error');
					return;
				}
				$("#selectValue").val($("#NjhwDoorComm").val());
				var reg=new RegExp("[0-9]+");
				if(!reg.test($("#adrsStore").val())){
					alert("门锁请输入数字!");
					return;
				}
				
				if(!(parseInt($("#adrsStore").val())>=0 && parseInt($("#adrsStore").val()) <=255)){
					alert("门锁区间为0-255之间！");
					return ;
				}
				
				var urls = "${ctx}/app/per/queryLockCountJSON.act?adrsStore="+$('#adrsStore').val()+"&selectValue="+$('#NjhwDoorComm').val();
				$.ajax({
					type:'POST',
					url:urls,
					dateType:'json',
					async:false,
					success:function(json){
						if(json.flag == 'N'){
							easyAlert('提示信息','该通讯机下的门锁地址已被占用，请换个通讯机地址！','error');
							return;
						}else{
							$("#queryForm").submit();
						}
					},
					error:function(json){
						easyAlert('提示信息','AJAX执行出错','error');
						return;
					}
				});
			}
		},
		error:function(json){
			easyAlert('提示信息','AJAX执行出错','error');
			return;
		}
	});
}
function selectHome(){
	var url = "${ctx}/app/per/jzsbLockInputTest.act?nodeId="+${param.nodeId};
	openEasyWin("winIds","选择房间",url,"260","360",false);
	//$("#innerTree").load(url);
	//showShelter1("550","300",url,'dd');
	//showIframe("250","100","${ctx}/app/per/jzsbLockInputTest.act?nodeId="+${param.nodeId});
}
</script>
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