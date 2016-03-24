<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: HJ
- Date: 2013-08-16
- Description: 门禁闸机添加修改页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/metaIframe.jsp" %>
    <title>门禁闸机授权申请详细</title>
   
    <script type="text/javascript" src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
    <link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">	

    $(document).ready(function() {
		//聚焦第一个输入框
		$("#name").focus();
		$("#guardIds").val('${entity.authIdsG}');
		$("#accessIds").val('${entity.authIdsA}');
		$("#lockVer").val('${entity.lockVer}');
		$("#userid").val('${entity.userid}');
		$("#opt").val('${entity.nuacExp1}');
		
		if ($("#opt").val() == '1') {
			$("#optName").val("新增");
		} else if ($("#opt").val() == '2') {
			$("#optName").val("更新");
		} else if ($("#opt").val() == '3') {
			$("#optName").val("删除");
		}
		
		$("#name").attr("disabled", "");
		$("#guard").attr("disabled", "");
		$("#access").attr("disabled", "");
		
		if (${status} == '2' || ${status} == '3') {
			$(".botton_from").hide();
		}
		
		if (${status} == '1' ) {
			$("#applyReasonTr").show();
			$("#applyReason").val('${entity.appBak}');
		}
		
		if (${status} == '3' ) {
			$("#refuseReasonTr").show();
			$("#refuseReason").val('${entity.appBak}');
		}
		
		if (${status} == '5' ) {
			$("#failReasonTr").show();
			$("#failReason").val('${entity.appBak}');
		}
		
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				name:{
					required: true
				},
				guard:{
					required: true
				}
			},
			messages:{
				name:{
                    required: "名称不能为空！"
               	},
				guard:{
					required:"闸机权限不能为空！"
				}
			}
		});
	});
	 	
	 	function closeWin() {			
			window.top.$("#accessInput").window("close");
		}
		
		
	function agree(){
		var url = "${ctx}/app/personnel/property/json/agreeAccessApply.act";
		var sucFun = function (json){
			easyAlert("提示信息",json.msg,"info", function() {
				window.top.$('#queryForm').submit();
				window.top.$("#accessInput").window("close");
			});
		};
		var errFun = function (){
			alert("同意申请失败!");
		};
		var data = {id:'${entity.nuacId}',lockVer:'${entity.lockVer}'};
		$.ajax({
			url : url,
			data: data,
			dataType : 'json',//返回值类型 一般设置为json
			success : sucFun,
			error : errFun
		});
    }

	function refuse(){
		var url = "${ctx}/app/personnel/property/refuseInput.act?id=${entity.nuacId}&lockVer=${entity.lockVer}&type=3";
		openEasyWin("refuseInput","退回门禁闸机申请",url,"400","250");
	}

	</script>
  </head>
  
  <body style="background:#fff;">
  <form  id="inputForm"  action="saveAccess.act"  method="post">
     <div class="infrom_ryjbxx">
        <s:hidden name="nuacId"/>
        <s:hidden name="userid" id="userid"/>
        <s:hidden name="lockVer" id="lockVer"/>
        <s:hidden name="accessIds" id="accessIds"/>
        <s:hidden name="guardIds" id="guardIds"/>
        <s:hidden name="opt" id="opt"/>
				<table width="100%">
					<tr>
						<td width="30%">
							<div class="infrom_td">姓名：</div>
						</td>
						<td width="70%">
							<input style="width: 236px; padding-left: 0px; color: #818284;"
								type="text" name="name" id="name"
								value="${name}" readonly="readonly"/>
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td width="30%">
							<div class="infrom_td">操作：</div>
						</td>
						<td width="70%">
							<input style="width: 236px; padding-left: 0px; color: #818284;"
								type="text" name="optName" id="optName" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">闸机：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="guard" id="guard" style="width:246px;resize:none;color: #818284;" readonly="readonly">${guard}</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">门禁：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="access" id="access" style="width:246px;resize:none;color: #818284;margin-top:10px;" readonly="readonly">${access}</textarea>
						</td>
					</tr>
					<tr id="applyReasonTr" style="display:none;">
						<td>
							<div class="infrom_td">申请理由：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="applyReason" id="applyReason" style="width:246px;resize:none;color: #818284;margin-top:10px;" readonly="readonly"></textarea>
						</td>
					</tr>
					<tr id="refuseReasonTr" style="display:none;">
						<td>
							<div class="infrom_td">退回理由：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="refuseReason" id="refuseReason" style="width:246px;resize:none;color: #818284;margin-top:10px;" readonly="readonly"></textarea>
						</td>
					</tr>
					<tr id="failReasonTr" style="display:none;">
						<td>
							<div class="infrom_td">失败理由：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="failReason" id="failReason" style="width:246px;resize:none;color: #818284;margin-top:10px;" readonly="readonly"></textarea>
						</td>
					</tr>
				</table>
     </div>
     <div class="botton_from" style="margin-top:20px;">
     	<a href="javascript:void(0)" onclick="refuse();" style="float:right;">打&nbsp;&nbsp;回</a>
     	<a href="javascript:void(0)" onclick="agree();" style="float:right;">同&nbsp;&nbsp;意</a>
     </div>
  </form> 
  </body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('accessInput');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
<c:if test="${isSuc=='false1'}">
	easyAlert("提示信息","此条信息正在操作，无法更新!","error");
</c:if>
</script>