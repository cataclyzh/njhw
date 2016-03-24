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
		if($('#NjhwDoorComm').val() == ""){
			easyAlert('提示信息','请选择通讯机','error');
			return;
		}
		if($("#adrsStore").val() == ""){
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
		
		var urls = "${ctx}/app/per/queryLockCountJSON.act?adrsStore="+$('#adrsStore').val()+"&selectValue="+$('#NjhwDoorComm').val()+"&setId="+$("#lockId").val();
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
					$('#inputForm').submit();
				}
			},
			error:function(json){
				easyAlert('提示信息','AJAX执行出错','error');
				return;
			}
		});
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
	<form  id="inputForm" action="jzsbInfoLockUpdate.act"  method="post"  autocomplete="off" >
		<input type="hidden" id="selectValue" name="selectValue" value="" />
		<input type="hidden" id="nodeId" name="nodeId" value="${param.nodeId}" />
		<input type="hidden" id="lockId" name="lockId" value="${param.lockId }" />
		<div style="width: 100%;height: 50%;" align="center">
			<!-- 电灯div -->
			<div id="light" style="display:block;width: 50%;height: 100px;border: 0px solid red;">
				<table align="center" border="0" width="100%" class="form_table">
					<tr>
						<td class="form_label"><font style="color:red">*</font>门锁名称：</td>
						<td><input type="text" name="name" id="name" size="30" maxlength="20" value="${resultList.NAME }" /></td>
					</tr>
					<tr>
						<td class="form_label"><font style="color:red">*</font>通讯机：</td>
						<td>
							<select style="width: 215px;" id="NjhwDoorComm">
							<option value='' selected='selected'>请选择通讯机</option>
								<script type="text/javascript">
									var url = "${ctx}/app/per/queryNjhwDoorCommJSON.act";	
									$.ajax({
										type:'POST',
										url:url,
										dateType:'json',
										async:false,
										success:function(json){
											if(json != null){
												var options;
												if(json.length > 0) {
													$.each(json, function(i) {
														var list = '${list}';
														if(list != ""){
															var commId = '${list.COMM_ID}';
															if(commId == json[i].COMM_ID){
																options = "<option id='"+json[i].COMM_ID+"' value='"+json[i].ID+"' selected='selected'>"+json[i].MEMO+"</option>";
															}else{
																options = "<option id='"+json[i].COMM_ID+"' value='"+json[i].ID+"'>"+json[i].MEMO+"</option>";
															}
															document.write(options);
														}else{
															options = "<option id='"+json[i].COMM_ID+"' value='"+json[i].ID+"'>"+json[i].MEMO+"</option>";
															document.write(options);
														}
													});
												}
											}else{
												options = "<option id='"+json[i].COMM_ID+"' value='"+json[i].ID+"'>"+json[i].MEMO+"</option>";
												document.write(options);
											}
										},
										error:function(json){
											easyAlert('提示信息','下拉框AJAX 操作错误！','error');
										}
									});
								</script>
							</select>
						</td>   
					</tr>
					<tr>
						<td class="form_label"><font style="color:red">*</font>门锁地址：</td>
						<td><input type="text" name="adrsStore" id="adrsStore" size="30" maxlength="20" value="${resultList.ADRS_STORE }" /></td>   
					</tr>
					<tr>
						<td class="form_label"><font style="color:red"></font>备注：</td>
						<td><input type="text" name="title" id="title" size="30" maxlength="20" value="${resultList.TITLE }" /></td>   
					</tr>
				</table>
			</div>
		</div>
		<div style="margin-top: 30px;"></div>
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

