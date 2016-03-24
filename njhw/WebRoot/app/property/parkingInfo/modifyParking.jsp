<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>车位修改</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script type="text/javascript">
	function closeWindow(){
		parent.closeWin();
	}

	$(document).ready(function() {
		jQuery.validator.addMethod("checkNumber", function(value, element) { 
			var length = value.length; 
			var checkParking = /^[1-9]*[1-9][0-9]*$/; 
			return this.optional(element) || (length <= 9 && checkParking.test(value)); 
		}, "请正确填写车位数量");

		jQuery.validator.addMethod("checkMinNumber", function(value, element) {
		    var minValue =  $("#countAllot").attr("value");
			return this.optional(element) || (eval(value)>=eval(minValue)); 
		}, "必须大于已分配车辆");
		
		jQuery.validator.addMethod("checkLeftNumber", function(value, element) { 
		    var maxValue =  $("#parkingLeftTotal").attr("value");
			return this.optional(element) || (eval(value)<=eval(maxValue)); 
		}, "剩余车位不足");

		$("#carManageModifyForm").validate({
			meta :"validate",
			errorElement :"div",
			rules: {
				parkingInfoNumber:{
					required: true,
					checkNumber:true,
					checkLeftNumber:true,
					maxlength: 9,
					checkMinNumber:true
				}
			},
			messages: {
				parkingInfoNumber:{
					required: "请输入车位数量",
				  	checkNumber: "请输入正确的车位数量",
				  	checkLeftNumber: "剩余车位不足",
				  	maxlength: "输入的车位数量过大",
				  	checkMinNumber:"必须大于已分配车辆"
				}
			}
		});
	});    
	
	function saveData(){
		if (showRequest()) {
			$('#carManageModifyForm').submit();
			closeEasyWin("orgInput");
		}
	}
	
	function showRequest(){
		 return $("#carManageModifyForm").valid();
	}
	
	function leftNumber(x){
		var parkingInfoNumber=document.getElementById(x).value;
	    var parkingLeftTotal=document.getElementById("parkingLeftTotal").value;
	    var checkParking = /^[0-9]*[1-9][0-9]*$/;
		if(parkingInfoNumber==""||!checkParking.test(parkingInfoNumber)){
			parkingInfoNumber=0;
		}
		if((eval(parkingLeftTotal)-eval(parkingInfoNumber))>=0){
		    document.getElementById("carNumberLeft").value=eval(parkingLeftTotal)-eval(parkingInfoNumber);
		}else {
			document.getElementById("carNumberLeft").value=0;
		}
	}
</script>
</head>

<body style=" background:#FFF">
<div class="main1_main2_right_khfw">
	<div class="khfw_wygl" >
		<div style="padding-left:200px">
			<form action="modifyCarParking.act" method="post" id="carManageModifyForm" name="carManageModifyForm" target="main_frame_right">
				<div class="show_from" >
					<input id="parkingInfoId" name="parkingInfoId" type="hidden" value="${parkingInfoId}" />
					<input id="parkingLeftTotal" name="parkingLeftTotal" type="hidden" value="${parkingLeftTotal}" />
					<input id="orgId" name="orgId" type="hidden" value="${orgId}" />
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
				    <tr>
			   			<td width="12%">剩余车位：</td>
					    <td width="50%"><input name="carNumberLeft" id="carNumberLeft"  class="show_inupt" type="text" readonly="readonly" value="${carNumberLeft}"/></td>
						<td></td>
						<td></td>
					</tr>  
			     		<tr>
					    <td width="12%">单位名称：</td>
					    <td width="50%">
					    	<input name="parkingInfoOrgName" id="parkingInfoOrgName" class="show_inupt" type="text" readonly="readonly" value="${parkingOrgName}"/>
					    </td>
					    <td></td>
						<td></td>
			   		</tr>
			    	<tr>
		  				<td width="12%">本单位已用车位数：</td>
						<td width="50%" style="text-align:left">
							<input name="countAllot" id="countAllot" class="show_inupt" type="text"  value="${countAllot}" readonly="readonly"/>
						</td>
					</tr>
					<tr>
			   			<td width="12%"><font color="red">*</font>车位数：</td>
			    		<td width="50%" style="text-align:left">
			    			<input name="parkingInfoNumber" id="parkingInfoNumber" style="width:240px;height:20px" type="text"  value="${parkingOrgNum}" onkeyup="leftNumber(this.id)"/>
			   			</td>
			  			 </tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<div id="buttonbox_complete" class="allbox_back">
		<div class="energy_fkdj_botton_ls_complaint">
			<a class="fkdj_botton_right" onclick="saveData()">确定</a>
		</div>
	</div>
</div>
</body>
</html>
