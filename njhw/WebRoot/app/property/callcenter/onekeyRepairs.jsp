<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>一键报修</title>
		<script type="text/javascript">
		$(document).ready(function() {
			 jQuery.validator.methods.compareValue = function(value, element) {
	           if(value=="0"){
	        	   return false;
	           }
	        	return true;
	        };
			$("#inputForm").validate({
				meta :"validate",
				errorElement :"div",
				rules: {
					aetId: {
						required: true,
						compareValue:true
					},
					easId:{
						required: true,
						compareValue:true
					},
					crfDesc:{
						maxlength: 200
					}
				},
				messages: {
					aetId:{
						compareValue:"请选择设备类型"
					},
					easId:{
						compareValue:"请选择设备"
					},
					crfDesc:{
						maxlength: "备注长度不能大于200个字符"
					}
				}
			});
		});
		
		function saveData(){
			$("#aetName").val($("#aetId option:selected").text());
			$("#easName").val($("#easId option:selected").text());
			if (showRequest()) {
				var options = {
			    dataType:'text',
			    success: function(responseText) { 
					if(responseText=='success') { 
						easyAlert("提示信息", "提交成功！","info", function(){
							window.location.reload();
						});
				 	} else if(responseText =='error') {
				 		easyAlert("提示信息", "提交访客信息出错！","info");
					} 
				}
			};
			$('#inputForm').ajaxSubmit(options);
			}
		}
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		// 根据设备类型，加载设备信息
		function Assetinfo() {
			var aetId = $("#aetId").val();
			if (aetId > 0) {
		    	$.getJSON("${ctx}/app/pro/loadAssetinfo.act",{aetId: aetId}, function(json){
		    		if (json.status == 0) {
		    			$("#easId").empty();
		    			$("#easId").append("<option value=0>请选择...</option>");
						$.each(json.list,function(i){
							$("#easId").append("<option value="+json.list[i][0]+">"+json.list[i][1]+"</option>") 
						})
		    		}
				});
			}
		}
	</script>
	</head>

	<body>
		<form id="inputForm" action="save.act" method="post" autocomplete="off">
			<table align="center" border="0" width="100%" class="form_table">
				<input name="easName" id="easName" type="hidden"/>
				<input name="aetName" id="aetName" type="hidden"/>
				<tr>
					<td class="form_label"> <font style="color: red">*</font>设备类型： </td>
					<td>
						<select id="aetId" name="aetId" onchange="Assetinfo()">
							<option value="0">请选择...</option>
							<c:forEach items="${typeList}" var="type">
								<option value="${type.aetId }">${type.aetName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red">*</font>设备名称： </td>
					<td> <select id="easId" name="easId">
						<option value="0">请选择...</option>
					</select> </td>
				</tr>
				<tr>
					<td class="form_label">报修说明： </td>
					<td> <s:textarea name="crfDesc" id="crfDesc" cols="50" rows="5"></s:textarea></td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="6">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" id="savebut">报修</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
