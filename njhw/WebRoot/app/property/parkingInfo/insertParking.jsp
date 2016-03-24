<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>车位增加</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet"
			type="text/css" />

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

	jQuery.validator.addMethod("checkLefNumber", function(value, element) { 
		    var maxValue =  $("#carNumberLeft").attr("value");
			return this.optional(element) || (eval(value)<=eval(maxValue)); 
		}, "剩余车位不足");


		$("#carManageAddForm").validate({		// 为inputForm注册validate函数
			meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
			rules: {
				
				parkingInfoNumber:{
					required: true,
					checkNumber:true,
					checkLefNumber:true,
					maxlength: 9
				}
			},
			messages:{
				
				parkingInfoNumber:{
					required: "请输入车位数量",
				  	checkNumber: "请输入正确的车位数量",
				  	checkLefNumber: "剩余车位不足",
				  	maxlength: "输入的车位数量过大"
				}
			}
		});
	});    
	
	
		function saveData(){		
		if (showRequest()) {
	
			$('#carManageAddForm').submit();
			
		}
	}
	
	function showRequest(){
		 return $("#carManageAddForm").valid();
	}
	
	
	
</script>


<style type="text/css">
textarea {
	width: 100%;
	font-size: medium;
}

input[type="text"] {
	height: 22px;
	width: 100%;
	font-size: medium;
	font-weight: normal;
}

#submitbutton {
	cursor:pointer;
}

#message_form label.error {
	color: red;
	font-size: small;
}
</style>
	
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"
	style="background: white;">

	<div class="main_border_01">
		<div class="main_border_02">分配车位</div>
	</div>
	
	
	<div class="main_conter">
		<div class="main3_left_01">
		<div style="padding-left:200px">
				<form action="addOneParkingInfo.act" method="post" id="carManageAddForm" name="carManageAddForm">
<div class="show_from" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="15%">剩余车位：</td>
    <td width="50%"><input name="carNumberLeft" id="carNumberLeft"  class="show_inupt" type="text" readonly="readonly" value="${carNumberLeft}"/>
    </td>
<td></td>
<td></td>
  </tr>
 
    <tr>
    <td width="15%"> <font color="red">*</font>单位名称：</td>
    <td width="50%" style="text-align:left">
    <select id="parkingInfoOrgName" name="parkingInfoOrgName" style="width:315px;height:25px">
		<c:forEach items="${orgList}" var="org">
		<option value="${org.orgId }">${org.name }</option>
		</c:forEach>
		</select>
    </td>
    <td></td>
    <td></td>
    
    </tr>
   
      <tr>
    <td width="15%"><font color="red">*</font>分配车位：</td>
    <td width="50%" style="text-align:left"><input name="parkingInfoNumber" id="parkingInfoNumber"  style="width:310px;height:20px" type="text"/>
    </td>
  
    
     </tr>
    
  
</table>


</div>
</form>


		</div>
		
</div>


<div style="padding-left:-500px">
<div class="clear"></div>
<div id="buttonbox_complete" class="allbox_back">
		<div class="energy_fkdj_botton_ls_complaint">
				<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			</div>
		</div>
<div class="clear"></div>
</div>
</div>
		<div style="width: 28%;float: left;margin-right: 10px;">
			<div class="clear"></div>
			

			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
<script type="text/javascript" src="${ctx}/app/property/parkingInfo/js/height.js"></script>

</body>
</html>

