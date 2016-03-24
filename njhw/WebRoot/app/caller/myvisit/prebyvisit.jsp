<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
		<title>被访者预约</title>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$("input :text").val("");
			$("input :hidden").val("");
			$("#visitType").val("0");
			$("#comeWhy").val("");
			$("#comeNum").val("1");
			//日期比较
			jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime < value;
	        };
	        
	        jQuery.validator.methods.compareNowDate = function(value, element, param) {
				return value >= "${nowDate}";
	        };
	        
	        // 身份证号码验证 
			jQuery.validator.addMethod("isIdCardNo", function(value, element) {
				return this.optional(element) || idCardNoUtil.checkIdCardNo(value);     
			}, "请正确输入您的身份证号码");
			
			jQuery.validator.addMethod("isMobile", function(value, element) { 
				var length = value.length; 
				var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
				return this.optional(element) || (length == 11 && mobile.test(value)); 
			}, "请正确填写您的手机号码");
			
			jQuery.validator.addMethod("isNeedValidCityCard", function(value, element) { 
				if ($("#visitType").val() == 0) 
					return value.length == 16;
				else 
					return true;
			}, "请输入正确的市民卡号");
			
			$("#input_cityCard").focus();
			$("#inputForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					userName: {
						required: true,
						maxlength: 20
					},
					comeWhy:{
						required: true,
						maxlength: 200
					},
					comeNum:{
						required: true,
						digits:true,
						min:1
					},
					phone:{
						required: true,
						isMobile:true,
						maxlength: 20
					},
					beginTime:{
						required: true,
						compareNowDate:true
					},
					endTime:{
						required: true,
						compareDate: "#beginTime"
					},
					input_cardId:{
						required: true,
						isIdCardNo: true,
						maxlength: 20
					},
					input_cityCard:{
					  isNeedValidCityCard: true,
					  maxlength: 20
					},
					email:{
	                    email:true
	               	}
				},
				messages:{
					userName: {
						required: "请输入访客名称",
						maxlength: "访客名称长度不能大于20个字符"
					},
					input_cardId:{
						required: "请输入身份证号",
					  	isIdCardNo:"请输入正确的身份证号",
					  	maxlength: "身份证号长度不能大于20个字符"
					},
					input_cityCard:{
					  	isNeedValidCityCard:"请输入正确的市民卡号",
					  	maxlength: "市民卡号长度不能大于20个字符"
					},
					phone:{
						required: "请输入手机号",
					  	isMobile: "请输入正确的手机号",
					  	maxlength: "手机号长度不能大于20个字符"
					},
					beginTime:{
						required: "开始时间不能为空",
	                    compareNowDate: "开始时间不能小于当前时间"
				 	},
	                endTime:{
	                    required: "结束时间不能为空",
	                    compareDate: "结束日期必须大于开始日期"
	               	},
	               	email:{
	                    isEmail: "请输入正确的邮箱号"
	               	},
	               	comeWhy:{
						required: "请输入来访事由",
						maxlength: "来访事由长度不能大于200个字符",
					}
				}
			});
		});
		
		function saveData(){
			$("#cardId").val($("#input_cardId").val().replace(/(^\s*)|(\s*$)/g, ""));
			$("#cityCard").val($("#input_cityCard").val().replace(/(^\s*)|(\s*$)/g, ""));
			
			if ($("#visitType").val() == 0 && !validCityCard()) return;
			if (showRequest()) {
				var options = {
			    dataType:'text',
			    success: function(responseText) {
					if(responseText=='success') {
						parent.closeIframe();
				 	} else if(responseText =='error') {
				 		
					} 
				}
			};
			$('#inputForm').ajaxSubmit(options);
			}
		}
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		// 根据市民卡号，验证市民卡的有效性
		function validCityCard(){
			var isOk = true;
			$("#showCityCardInfo").hide();
			var cityCard = $("#input_cityCard").val().replace(/(^\s*)|(\s*$)/g, "");
			
			if (cityCard.length == 16) {
				$.ajax({
		            type: "POST",
		            url: "${ctx}/app/prebyvisit/validCityCard.act",
		            data: "cityCard="+ cityCard,
		            dataType: 'json',
		            async : false,
		            success: function(json){
	                    if(json.status == 0){
	                		if (json.diff == "y") {
		                		$("#input_cardId").val(json.residentNo);
		                		$("#userName").val(json.VIName);
		                		//$("#phone").val(json.mobile);
		                		$("#showCityCardDiff").hide();
	                		} else {
	                			$("#showCityCardDiff").show();
	                			$("#userName").val("");
	                			isOk = false;
	                		}
	                    } else {
	                    	clearText("city");
	                    }
		            },
		            error: function(msg, status, e){
		            	easyAlert("提示信息", "加载访客信息出错！","info");
		            }
		         });
			}
			return isOk;
		}
		
		// 根据身份证号，加载访客信息
		function loadVisitorInfo() {
			var cardId = $("#input_cardId").val().replace(/(^\s*)|(\s*$)/g, "");
			// 效验身份证长度
			if ((/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(cardId))) {
		    	$.getJSON("${ctx}/app/prebyvisit/loadVisitorInfo.act",{cardId: cardId}, function(json){
		    		if (json.status == 0) {
		    			$("#userName").val(json.VIName);
						$("#phone").val(json.ViMobile);
						$("#input_cityCard").val(json.ResBak1);
						$("#carId").val(json.PlateNum);
						$("#email").val(json.ViMail);
						$("#showCardIdInfo").hide();
		    		} else {
	                	clearText("cardId");
	                }
				});
			}
		}
		
		function clearText(type) {
			if (type == "city") {	// 市民卡号查询不到数据时，清空身份证等其他信息
				$("#input_cardId").val("");
				$("#showCityCardInfo").show();
			}
			if (type == "cardId") {	// 身份证号码查询不到数据时，清空市民卡等其他信息
				$("#input_cityCard").val("");
				$("#showCardIdInfo").show();
			}
        	$("#userName").val("");
			$("#phone").val("");
			$("#carId").val("");
			$("#email").val("");
		}
		
		function showInfo() {
			if ($("#visitType").val() == "0") {	// 市民卡
				$("#tr_idcard").hide();
				$("#input_cardId").val("");
				
				$("#tr_citycard").show();
				$("#input_cityCard").focus();
				$("#showCardIdInfo").hide();
			} else {
				$("#tr_idcard").show();
				$("#input_cardId").focus();
				
				$("#tr_citycard").hide();
				$("#input_cityCard").val("");
				$("#showCityCardInfo").hide();
			}
		}
	</script>
	</head>

	<body style="background: #FFF;">
		<form id="inputForm" action="save.act" method="post" autocomplete="off">
			<input type="hidden" name="cityCard" id="cityCard"/>
			<input type="hidden" name="cardId" id="cardId"/>
			<table align="center" border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label"></td>
					<td> <a href="javascript:void(0);" style="color:#330000;" onclick="javascript:parent.open_main3('10048' ,'我的来访' ,'${ctx}/app/myvisit/init.act');">[我的来访]</a></td>
				</tr>
				<tr>
					<td class="form_label"><font style="color: red">*</font>预约方式：</td>
					<td><select id="visitType" name="visitType" onchange="showInfo();">
						<option value="0">市民卡预约</option>
					 	<option value="1">身份证预约</option>
					</select></td>
				</tr>
				<tr id="tr_citycard">
					<td class="form_label"><font style="color: red">*</font>市民卡号：</td>
					<td id="td_citycard">
						<s:textfield id="input_cityCard" name="input_cityCard" theme="simple" size="30" maxlength="16" onchange="validCityCard()"/>
						<div id="showCityCardInfo" style="display: none;">此市民卡号不存在，请确认！</div>
						<div id="showCityCardDiff" style="display: none;">只能针对访客的市民卡进行预约，请确认！</div>
					</td>
				</tr>
				<tr id="tr_idcard" style="display: none;">
					<td class="form_label"><font style="color: red">*</font>身份证号：</td>
					<td id="td_citycard">
						<s:textfield id="input_cardId" name="input_cardId" theme="simple" size="30" maxlength="20" onchange="loadVisitorInfo()"/>
						<div id="showCardIdInfo" style="display: none;">此身份证号没有使用记录，请手动输入访客信息！</div>
					</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red">*</font>访客姓名： </td>
					<td> <s:textfield name="userName" theme="simple" size="30" maxlength="20" /> </td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red">*</font>手机号： </td>
					<td> <s:textfield name="phone" id="phone" theme="simple" size="30" maxlength="20" /> </td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red">*</font>来访人数： </td>
					<td> <s:textfield name="comeNum" id="comeNum" theme="simple" size="30" maxlength="20" /> </td>
				</tr>
				<tr>
					<td class="form_label">车牌号： </td>
					<td> 
						<s:textfield name="carId" id="carId" theme="simple" size="30" maxlength="7"/>
					</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>邮箱： </td>
					<td colspan="3"> <s:textfield name="email" id="email" theme="simple" size="30" maxlength="30" /> </td>
				</tr>
				<tr>
					<td class="form_label" > <font style="color: red">*</font>预约时间段： </td>
					<td colspan="3">
						<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="beginTime" name="beginTime" cssClass="Wdate"/> - 
						<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="endTime" name="endTime" cssClass="Wdate"/>
					</td>
				</tr>
				<tr>
					<td class="form_label"><font style="color: red">*</font>来访事由： </td>
					<td colspan="3"> <s:textarea name="comeWhy" id="comeWhy" cols="50" rows="5"></s:textarea></td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="4">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" id="savebut">保存</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>