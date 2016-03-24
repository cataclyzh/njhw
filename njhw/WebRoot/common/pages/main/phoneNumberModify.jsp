<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
<script src="${ctx}/scripts/ca/MD5.js" type="text/javascript"></script>
<link href="${ctx}/common/pages/main/css/member.css" rel="stylesheet" type="text/css" />
<html>
<head>
<script>
 //手机号的验证
$(document).ready(function() {
 jQuery.validator.addMethod("isMobile", function(value, element) { 
				var length = value.length; 
				var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
				return this.optional(element) || (length == 11 && mobile.test(value)); 
			}, "请正确填写您的手机号码");	
			
			
			$("#phoneModify").validate({		// 为inputForm注册validate函数
				onfocusout: false,
				onkeyup: false,
				onclick: false,
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					verificationPhone:{
						required: true,
						isMobile:true,
						maxlength: 20
					}
			
				},
				messages:{
				
					verificationPhone:{
						required: "请输入手机号",
					  	isMobile: "请输入正确的手机号",
					  	maxlength: "手机号长度不能大于20个字符"
					}
     
					
				
				}
			});
		});





//发送手机验证码
var v="";
var i=0;
function changeIphonefun(){
    var res = $("#phoneModify").valid();
    if(!res){
		return;
	}
    
   $('#verificationPhone').attr("disabled","disabled");
	var  phoneNumber = $("#verificationPhone").val();

	var url="${ctx}/common/userOrgMgr/sendPhoneNumberVerificationCode.act";
	var data = {phones:phoneNumber};
	sucFun = function (data){
		//alert(data);
		//获取后台传过来的验证码
		if(data.state =='1'){
			v=data.list;
			//alert(v);

			i++;
		  $("#aa").html('请使用第'+i+"次验证码");
         
		  for(y=1;y<=secs;y++) {
			     window.setTimeout("doUpdate(" + y + ")", y * 1000);
			   }
			   window.setTimeout("Timer()", wait);
			   //alert("发送成功！！");
		}
		 
		else
		{
			alert("发送失败！");
		}
	};
	errFun = function (data){
	};
	ajaxQueryJSON(url,data,sucFun,errFun);
	
}

//更新手机号码
function updateIphone(){
	$("#aa").hide();
	//获取前台页面的验证码
	var  verificationCode = $("#verificationCode").val();
	//var code = MD5(verificationCode);
	//code = code.substr(0,20);
	  //alert(verificationCode);
	//进行判断（前台的验证码和后台的验证码）
	//alert(code+"--------"+v.validateCode);
	var  phone = $("#verificationPhone").val();
	if (phone == "") {
		parent.$("#phone").val(phone);
		parent.$("#ggsj").window("close");
		return;
	}
	if(verificationCode==''){
		 $("#show_order").text('请输入验证码');
		 $("#show_order").show();
		 return;
		}
	if(v!=verificationCode){
		$("#show_order").text('验证码不对');
		$("#msg_alert").hide();
		$("#show_order").show();
		return;
		}
	parent.$("#phone").val(phone);
	parent.$("#ggsj").window("close");
}


//发送验证码倒计时Start
   var secs = 20; 
	 var wait = secs * 1000;
 function doUpdate(num) {
    if(num == '60') {
      document.forms.phoneModify.sendcode.value = " 重新发送 ";
    } else 
	{
      document.forms.phoneModify.sendcode.disabled =true;
      wut = secs-num;
      document.forms.phoneModify.sendcode.value = " 发送手机验证码 (" + wut + ")...";
      if(wut ==0)
      {
    	  document.forms.phoneModify.sendcode.value = "发送验证码";
    	  $('#verificationPhone').removeAttr("disabled");
    	  // alert("发送成功！！");
    	 //$("#show_or").show();
    	 
          }
      
     }
 }
 function Timer() {
  document.forms.phoneModify.sendcode.disabled =false;
 }


//发送验证码倒计时End

</script>
<title>手机号码变更</title>

</head>
<body style="background-color: #fff;">
	<form id="phoneModify"  >
	   <div class="show_from_member" style="height:141px;background-color: #fff;">
	   <table align="center" border="0" width="100%"   >
	       <tr>
				<td class="member_form_txt" nowrap>
					手机号：
				</td>
				<td>
					<div  style="display: inline-block;">
					<input type="text" style="width:150px;" name="verificationPhone" id="verificationPhone" maxlength="20" class="member_form_input"  value= "${phone}" />
					</div>
					<a href="javascript:void(0);" style="display: inline-block; margin-left: 10px;width:100px;"
											class="form_update_btn"
											onclick="javascript:changeIphonefun();">发送验证码</a>
				</td>
			</tr>
			<tr>
				<td class="member_form_txt" nowrap>
					验证码：
				</td>
				<td>
					<div  style="display: inline-block;">
					<s:textfield name="verificationCode" id="verificationCode"
						maxlength="20" cssClass="member_form_input" cssStyle="display: inline-block; width:150px;"/>
					</div><span id="show_order" style="display: inline-block; color: red; display: none;">验证失败</span>
						<span style="display: inline-block;color: blue; display: none;" id="msg_alert">验证成功</span>
						<span style="display: inline-block;color: blue;" id="aa"></span>
				</td>
			</tr>
		</table>
		<a href="javascript:void(0);" style="margin: 0 auto;" class="form_update_btn" id="submitbutton" onclick="javascript:updateIphone();">确定</a>
		</div>
	</form>
</body>
</html>