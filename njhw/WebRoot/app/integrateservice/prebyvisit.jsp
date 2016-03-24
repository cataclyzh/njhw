<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp"%>
	<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
	<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="padding_bok" style="height: 100%">
		<div class="bgsgl_right_list_border" style="width:97%">
			<div class="bgsgl_right_list_left">主动约访</div>
		</div>
		<div class="clear"></div>
		<form id="inputForm" action="save.act" method="post" autocomplete="off">
			<input type="hidden" name="cityCard" id="cityCard"/>
			<input type="hidden" name="cardId" id="cardId"/>
			<input type="hidden" name="visitTypeFlag" id="visitTypeFlag"/>
			<table width="600" border="0" align="center" cellpadding="0" cellspacing="0" class="form_table_visit">
				<tr>
					<td class="form_previsit_label"> <font style="color: red">*</font>姓名</td>
					<td><input id="userName" name="userName" class="form_table_visit_input"/></td>
				</tr>
				<tr>
					<td class="form_previsit_label"><font style="color: red">*</font>预约方式</td>
					<td><select id="visitType" class="form_table_visit_ciext" name="visitType" onchange="showInfo();">
						<option value="0">市民卡预约</option>
					 	<option value="1">身份证预约</option>
					</select></td>
				</tr>
				<tr id="tr_citycard">
					<td class="form_previsit_label"><font style="color: red">*</font>市民卡号</td>
					<td id="td_citycard" style="position: relative;">
						<input id="input_cityCard" onmouseover="document.getElementById('card_prompt').style.display='block';" onmouseout="document.getElementById('card_prompt').style.display='none';" class="form_table_visit_input" name="input_cityCard" value="0000" onchange="validCityCard()"/>
						
						<div id="card_prompt" class="prompt" style="display:none; padding: 0 10px;z-index: 9;">
				    		<div class="bgsgl_right_list_border">
							  <div class="bgsgl_right_list_left" style="width:100%;font-size:13px;">市民卡号帮助</div>
							</div>
				    		<div style="width:250px;margin:0 auto;height: 180px;">
				    			<div class="yellow_panel" style="height: 15px;margin-left: 30px;"></div>
				    			<font style="color:#808080;font-size:11px;float:left;padding-top:-25px;height: 10px;">黄色部分为市民卡号</font>
				    			
				    			<font style="color:#808080;font-size:11px;float:left;padding-top:2px;height: 10px;">卡号开头默认添加0000</font>
				    			<div style="clear:both;height:10px;"></div>
				    			<div class="card_help" style="clear:both;margin-top: 5px;"></div>
				    		</div>
				    	</div>
						
						<div id="showCityCardInfo" style="display: none;">市民卡不存在</div>
						<div id="showCityCardValidateError" style="display: none;">市民卡号验证服务出错</div>
						<div id="showCityCardDiff" style="display: none;">无法预约大厦内部用户</div>
						<div style="position: absolute;top: 3px;left: 160px;display: none;">
							<div style="color:red;width: 180px;height: 25px;line-height:25px;font-size: 12px;">请在4个0之后填写您的市民卡卡号 </div>
						</div>						
					</td>
				</tr>
				<tr id="tr_idcard" style="display: none;">
					<td class="form_previsit_label"><font style="color: red">*</font>身份证号</td>
					<td id="td_citycard" style="position: relative;">
						<input id="input_cardId" class="form_table_visit_input" name="input_cardId" onchange="loadVisitorInfo()"/>
						<div id="showCardIdInfo" style="display: none;z-index: 100;position: absolute;top:0px;left: 150px;color: red">该身份证无使用记录，请手动输入信息！</div>
					</td>
				</tr>
				<tr>
					<td class="form_previsit_label"> <font style="color: red">*</font>手机号</td>
					<td> <input name="phone" class="form_table_visit_input" id="phone" /> </td>
				</tr>
				<tr>
					<td class="form_previsit_label"> <font style="color: red">*</font>来访人数</td>
					<td> <input name="comeNum" class="form_table_visit_input" maxlength="4" id="comeNum" /> </td>
				</tr>
				<tr>
					<td class="form_previsit_label">车牌号 </td>
					<td style="position: relative;"> 
						<input name="carId" maxlength="20"  class="form_table_visit_input" id="carId"  />
						<div style="position: absolute;top: 0px;left: 160px;color: red;display: none;">车牌号长度最多为20位数字</div>
					</td>
				</tr>
				<tr>
					<td class="form_previsit_label"> <font style="color: red"></font>邮箱</td>
					<td colspan="3"> <input name="email" class="form_table_visit_input" id="email" /> </td>
				</tr>
				<tr>
					<td class="form_previsit_label" > <font style="color: red">*</font>开始时间</td>
					<td colspan="3" style="position: relative;">
						<input style="cursor:pointer;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="beginTime" name="beginTime" class="form_table_visit_inputs" /> 
						<div style="position: absolute;color: red;top: 0px;left: 370px;z-index: 100;display: none;">结束时间大于开始时间</div>
					</td>
				</tr>
				<tr style="display: none;">
					<td class="form_previsit_label"> <font style="color: red">*</font>结束时间</td>
					<td colspan="3">
						<input style="cursor:pointer;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="endTime" name="endTime" class="form_table_visit_inputs" />
					</td>
				</tr>
				<tr>
					<td class="form_previsit_label"><font style="color: red">*</font>来访事由</td>
					<td colspan="3"> <textarea name="comeWhy" style="overflow: auto;" class="form_table_visit_ciext" id="comeWhy" onpropertychange="if(value.length>100) value=value.substr(0,100)" class="form_table_visit_input" rows="5"></textarea></td>
				</tr>
		</table>
		<div style="margin-top: 10px;"></div>
		<div class="fkdj_botton_ls" align="center;" style="text-align: center;padding-left: 160px; width:73%">
			<a style="text-align: center; margin-left:40px;" class="fkdj_botton_left" href="javascript:void(0);" onclick="saveData();" id="savebut">确认提交</a>
			<a style="text-align: center;" class="fkdj_botton_left" href="javascript:void(0);" onclick="window.location.reload();">重新填写</a>

		</div>
		</form>
	</div>
</body>
</html>
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
		
		//$("#input_cityCard").focus();
		$("#inputForm").validate({		// 为inputForm注册validate函数
			meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"span",		// 使用"span"标签标记错误， 默认:"label"
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
				/**endTime:{
					required: true,
					compareDate: "#beginTime"
				},*/
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
                /**endTime:{
                    required: "时间不能为空",
                    compareDate: "结束日期必须大于开始日期"
               	},*/
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
	var count = 1;//提交限制
	function saveData(){
		var cards = $("#input_cityCard").val().replace(/(^\s*)|(\s*$)/g, "");
		$("#cardId").val($("#input_cardId").val().replace(/(^\s*)|(\s*$)/g, ""));
		$("#visitTypeFlag").val($("#visitType").val());
		
		/**if(cards.match(/^0000/) == null){
			$("#cityCard").val("0000"+cards); //赋予0000 不然无法执行
		}else*/ if(cards.length == 16){
			$("#cityCard").val(cards);
		}
		if ($("#visitType").val() == 0 && !validCityCard()) return;
		
		if (showRequest()) {
			$("#savebut").attr('disabled',true); //禁用事件
			var options = {
			    dataType:'text',
			    success: function(responseText) {
					if(responseText=='success') { 
						using('messager', function () {
							$.messager.alert("提示信息", "预约成功！","info", function(){
								//$('#inputForm').resetForm();
								//showInfo();
								parent.closeIframe();
							});
						});
				 	} else if(responseText =='error') {
				 		easyAlert("提示信息", "预约出错！","info");
					} else {
						easyAlert("提示信息", responseText,"info",function(){
							parent.closeIframe();
						});
						
					}
					$("#savebut").attr('disabled',false);//开放事件
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
		var cards;
		$("#showCityCardInfo").hide();
		$("#showCityCardValidateError").hide();		
		var cityCard = $("#input_cityCard").val().replace(/(^\s*)|(\s*$)/g, "");
		if (cityCard.length == 16) {
			cards = cityCard; // "0000" +
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/prebyvisit/validCityCard.act",
	            data: "cityCard="+ cards,
	            dataType: 'json',
	            async : false,
	            success: function(json){
                    if(json.status == 0 || json.status == 2){
                		if (json.diff == "y" ) {
                			if(json.LoseStatus != "0"){
                				isOk = false;
                				easyAlert("提示信息", "此市民卡已挂失!不能预约.","info");
                			}else{
		                		$("#input_cardId").val(json.residentNo);
		                		//$("#userName").val(json.VIName);
		                		$("#showCityCardDiff").hide();
                			}
                		} else {
                			$("#showCityCardDiff").show();
                			//$("#input_cityCard").val("");
                			isOk = false;
                		}
                    } else if(json.status == 9){                    	
            			clearText("noCityValidate");
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
	    			//$("#userName").val(json.VIName);
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
		if (type == "noCityValidate"){ // 市民卡验证接口服务器错误
			$("#input_cardId").val("");
			$("#showCityCardInfo").hide();
			$("#showCityCardValidateError").show();
		}
		/** 切换邀约身份  身份证/市民卡 用户信息保存着
       	$("#userName").val("");
		$("#phone").val("");
		$("#carId").val("");
		$("#email").val("");
		*/
	}
	
	function showInfo() {
		if ($("#visitType").val() == "0") {	// 市民卡
			$("#tr_idcard").hide();
			//$("#input_cardId").val("");
			
			$("#tr_citycard").show();
			$("#input_cityCard").focus();
			$("#showCardIdInfo").hide();
		} else {
			$("#tr_idcard").show();
			$("#input_cardId").focus();
			
			$("#tr_citycard").hide();
			//$("#input_cityCard").val("");
			$("#showCityCardInfo").hide();
			$("#showCityCardValidateError").hide();			
		}
	}
</script>