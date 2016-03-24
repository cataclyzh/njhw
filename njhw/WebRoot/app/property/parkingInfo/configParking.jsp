<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>车位配置</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet"
			type="text/css" />
<script type="text/javascript">
$(document).ready(function() {
		
		jQuery.validator.addMethod("checkNumber", function(value, element) { 
			var length = value.length; 
			var checkParking = /^[1-9]*[1-9][0-9]*$/; 
			return this.optional(element) || (length <= 9 && checkParking.test(value)); 
		}, "请正确填写车位数量");

	jQuery.validator.addMethod("checkLefNumber", function(value, element) { 
		    var minValue =  $("#carNumber").attr("value");
			return this.optional(element) || (eval(value)>=eval(minValue)); 
		}, "不能小于已分配的车位数");


		$("#carManageConfigForm").validate({		// 为inputForm注册validate函数
			meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",		// 使用"div"标签标记错误，
			rules: {
				
				totalNum:{
					required: true,
					checkNumber:true,
					checkLefNumber:true,
					maxlength: 9
				}
			},
			messages:{
				
				totalNum:{
					required: "请输入车位数量",
				  	checkNumber: "请输入正确的车位数量",
				  	checkLefNumber: "不能小于已分配的车位数",
				  	maxlength: "输入的车位数量过大"
				}
			}
		});
	});    
	
	
		function saveData(){		
		if (showRequest()) {
		
		    easyAlert("提示", "配置成功!", "confirm");
			
			    $('#carManageConfigForm').submit();
			    
			 
		}
	}
	
	function showRequest(){
		 return $("#carManageConfigForm").valid();
	}
</script>

<%@ include file="/common/include/meta.jsp"%>
			<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
				rel="stylesheet" type="text/css" />
			<script src="${ctx}/scripts/basic/jquery.js.gzip"
				type="text/javascript"></script>
			<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
				type="text/javascript"></script>
			<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
			<script type="text/javascript"
				src="${ctx}/app/portal/toolbar/showModel.js"></script>
			<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>		
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"
	style="background: white;">

	<div class="main_border_01">
		<div class="main_border_02">总车位配置</div>
	</div>
	
	
	<div class="main_conter">
		<div class="main3_left_01" style="width: 100%;float: none;">
			<div style="" align="center">
				<form action="configParkingLoad.act" method="post" id="carManageConfigForm" name="carManageConfigForm" onkeydown="if(event.keyCode==13){return false;}">
					<div class="show_from" style="width: 400px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="18%">总车位：</td>
								<td width="5%"><input name="totalNum" id="totalNum" size="20" value="${totalNum}"/>
								<td>
								<div id="buttonbox_complete" class="allbox_back" >
									<div class="energy_fkdj_botton_ls_complaint" align="right" style="height: 20px;">
										<a class="fkdj_botton_right" style="float: none;height: 19px;line-height: 19px;" onclick="saveData()">确定</a>
									</div>
								</div>
								</td>
							</td>
						</table>
					</div>
					<div style="width:100%;height:1px;font-size:0;background:rgb(127, 144, 179);"></div>
					<div class="show_from" style="width: 400px;border: 0px solid red;margin-top: 10px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="3%">已分配车位：</td>
								<td width="2%"><input name="sureCarNum" id="sureCarNum" disabled="disabled" size="15" value="${carNumber}"/>
								<td width="3%">未分配车位：</td>
								<td width="2%"><input name="noCarNum" id="noCarNum" disabled="disabled" size="15" value="${totalNum - carNumber}"/>
							</td>
						</table>
					</div>
					<input type="hidden" name="carNumber" id="carNumber" value="${carNumber}"/> 
				</form>
			</div>
		</div>
		
		<div style="width: 28%;float: left;margin-right: 10px;">
			<div class="clear"></div>
			

			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>

</body>
</html>



