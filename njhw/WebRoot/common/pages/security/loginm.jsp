<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资源信息录入</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link type="text/css" rel="stylesheet" href="${ctx}/common/pages/security/multlogin/css/SingleSignOn.css" />
		<style type="text/css">
			a:link {
				color: #FF0000;
				text-decoration: none;
			}  /* 未访问的链接 */
			a:visited {
				color: #FFFFFF;
				text-decoration: none;
			}  /* 已访问的链接 */
			a:hover {
				color: #FF0000;
				text-decoration: underline;
			}  /* 鼠标移动到链接上 */
			a:active {
				color: #FF0000
			}
		</style>
	<script type="text/javascript" src="${ctx}/common/scripts/cas/MD5.js"></script>
	<script type="text/javascript" src="${ctx}/common/pages/security/js/config.js"></script>
	<script type="text/javascript" src="${ctx}/common/pages/security/multlogin/js/SingleSignOn.js"></script>
	<script type="text/javascript" src="${ctx}/app/portal/js/AjaxUtil.js"></script>
	<script type="text/javascript">
			var secs =20; 
		 	var wait = secs * 1000;
			var AUTH_TYPE_LOGIN = "1";
			var AUTH_TYPE_PHONE_CODE = "2";
			var AUTH_TYPE_PERSON_CARD = "3";
			var AUTH_TYPE_CA = "4";
			var COOKIE_STR = "njhw_cookie_login_authType_";
			var VALIDATE_CODE_EXPIRE = "VALIDATE_CODE_EXPIRE";
			var validateCode = "";
			var authType = AUTH_TYPE_LOGIN;
			
			//验证码有效定时器
			var validateCodeTimer = null;
			//获取验证码不可能定时器
			var effectValidateTimer = null;
	
			window.onload = function inits()
			{
					alert(1);
					return;
					login_click(document.getElementById("login_main_left_img1"), '0');
					initPhotoActiveX();
			}
			
			function validateCodeExpired(){
				validateCode = VALIDATE_CODE_EXPIRE;
				validateCodeTimer = null;
			}
			
			function effectValidateExpired(){
				playValidateControls("unlock");
			}
	
			function validateMobile(){
				var value = $("#phone_no").val();
				var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
				var length = value.length;
				if(value == ""){
					$("#phoneError").html('手机号不能为空!');
					return false;
				}else{
					if(length != 11 && !mobile.test(value)){
						$("#phoneError").html('请正确填写您的手机号码');
						return false;
					}else{
						$("#phoneError").html('');
						return true;
					}
				}
				
			}
	
	
			//发送验证码倒计时Start
		   	 var secs = 20; 
			 var wait = secs * 1000;
			 function doUpdate(num) {
			    if(num == '60') {
			      document.forms.fm1.sendcode.value = " 重新发送 ";
			    } else 
				{
			      document.forms.fm1.sendcode.disabled =true;
			      wut = secs-num;
			      document.forms.fm1.sendcode.value = " 发送验证码 (" + wut + ")...";
			      if(wut ==0)
			      {
			    	  document.forms.fm1.sendcode.value = "发送验证码";
	<%--		    	  alert("发送成功！！");--%>
			          }
			     }
			 }
			 function Timer() {
			  	document.forms.fm1.sendcode.disabled =false;
			 }
			
			/**
			* 获取验证码功能 可不可用
			*/
			function playValidateControls(type){
				if (type == "lock"){
					$("#btn_validate_code").attr("disabled","disabled");
					$("#phone_no").attr("disabled","disabled");
				} else if (type == "unlock"){
					$("#btn_validate_code").removeAttr("disabled");
					$("#phone_no").removeAttr("disabled");
				}
			
			}
			
			/**
			* person card Login
			* @personCard 市民卡号
			* @status 1:成功;2:市民卡认证失败
			*/
			function personCardLogin(){
				 var url = AUTH_SERVER_URL+"?authType="+authType+"&personCard="+$("#person_card").val();
				 $.ajax({
		             type : "get",
		             async:false,
		             url : url,
		             dataType : "jsonp",
		             jsonp: "callbackparam",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
		             jsonpCallback:"success_jsonpCallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
		             success : function(json){
		                 var statusCode = $.trim(json.statusCode);
		                 if (statusCode=="1"){
		                	$("#username").val(json.username);
		                 	$("#password").val(json.password);
		                 	$("#cas_submit1").click();
		                 } else {
		                 	alert("市民卡认证失败!");
		                 }
		             },
		             error:function(){
		                 alert('app njhw server request error!!!');
		             }
		         });		
			}
			/**
			* CA auth
			*/
			function caAuthLogin(){
				 var url = AUTH_SERVER_URL+"?authType="+authType+"&caKey=key";
				 $.ajax({
		             type : "get",
		             async:false,
		             url : url,
		             dataType : "jsonp",
		             jsonp: "callbackparam",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
		             jsonpCallback:"success_jsonpCallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
		             success : function(json){
		            	 var statusCode = $.trim(json.statusCode);
		                 if (statusCode=="1"){
		                	$("#username").val(json.username);
		                 	$("#password").val(json.password);
		                 	$("#cas_submit1").click();
		                 } else {
		                 	alert("CA证书认证失败!");
		                 }
		             },
		             error:function(){
		                 alert('app njhw server request error!!!');
		             }
		         });		
			}
			/**
			* @descripion phone code auth
			* @param phoneNo:电话号码
			* @return validateCode
			*         status 1:成功;2:手机认证失败！
					  password
					  username
			*/
			function phoneCodeAuth(){
				var result = validateMobile();
				if(result == true){
				 	playValidateControls("lock");
					effectValidateTimer = setTimeout("effectValidateExpired()",PHONE_VALIDATE_CODE_REQUET_PEROID);
					var phoneNo = $("#phone_no").val();
					var url = AUTH_SERVER_URL+"?authType="+authType+"&phoneNo="+phoneNo;
					 $.ajax({
			             type : "post",
			             async:false,
			             url : url,
			             dataType : "jsonp",
			             jsonp: "callbackparam",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
			             jsonpCallback:"success_jsonpCallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
			             success : function(json){
			              	var statusCode = $.trim(json.statusCode);
			                 if (statusCode=="1"){
			                	$("#username").val(json.username);
			                 	$("#password").val(json.password);
			                 	validateCode = $.trim(json.validateCode); 
		<%--	                 	validateCodeTimer = setTimeout("validateCodeExpired()",PHONE_VALIDATE_CODE_EXPIRED);--%>
		<%--	                 	$("#phoneError").html("手机号认证成功,验证码已发送!60秒内有效!");--%>
									for(y=1;y<=20;y++) {
									    window.setTimeout("doUpdate(" + y + ")", y * 1000);
									  }
									  window.setTimeout("Timer()", wait);
			                 } else if (statusCode==2) {
			                	$("#phoneError").html("手机认证失败!");
			                 } 
			             },
			             error:function(){
			                 alert('app njhw server request error!!!');
			             }
			         });	
				}
			}
			/**
			* @descripion phone code login
			*/
			function phoneCodeLogin(){
				var phone_no = $("#phone_no").val();
				if(phone_no == ""){
					$("#phoneError").html("手机号不能为空!");
				}else{
					var vc = $("#validate_code").val();
					if ("" != validateCode && VALIDATE_CODE_EXPIRE == validateCode){
						$("#phoneError").html("验证码过期!");
						return;
					} else if(validateCode != MD5(vc)) {
						$("#phoneError").html("验证错误!");
						return;
					}
					$("#cas_submit1").click();
				}
			}
	
			/**
			* 处理表单和提交
			*/
			function check(){
				var password = $("#password").val();
				if(authType == AUTH_TYPE_LOGIN){//做加密处理
					password = MD5(password);
					password = password.substr(0,20);
					$("#password").val(password);
				}
				writeCookie(authType);
			}
	
			
			/*write cookie*/
			function writeCookie(authType){
				var str = COOKIE_STR + authType;
				str = encodeURI(str);
				document.cookie = str;
			}
	
		//校验验证码
		/*function randImgLogin(){
			var verifyCode = $("#verifyCode").val();
				var url = AUTH_SERVERIMG_URL+"?validateCode="+verifyCode;
				 $.ajax({
					 type : "get",
					 async:false,
					 url : url,
					 dataType : "jsonp",
					 jsonp: "callbackparam",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
					 jsonpCallback:"success_jsonpCallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
					 success : function(json){
						 if(json.flag == "true"){
							 $("#cas_submit1").click();
						 }else{
							 reloadCode();
							 $("#errorContent").html("输入验证码错误!");
						 }
					 },
					 error:function(){
						alert('校验验证码异常!');
					 }
				 });	
			}*/
		//校验验证码
		
		function randImgLogin(){
			var username = $("#username").val();
			var password = $("#password").val();
			var verifyCode = $("#verifyCode").val();
			if(username == ""){
					$("#errorContent").html("用户名不能为空!");
					return ;
			}
			if(password == ""){
					$("#errorContent").html("密码不能为空!");
					return ;
			}
			if(verifyCode == ""){
				$("#errorContent").html("请输入验证码!");
			 }else{
				var url = "/cas/randImgServlet?validateCode="+verifyCode;
				var data = null;
				var sucFun = function (json){
					 if(json.flag == "true"){
							$("#cas_submit1").click();
					 }else{
						 reloadCode();
						 $("#errorContent").html("输入验证码错误!");
						}
					 };
				var errFun = function(json) {
					$("#errorContent").html("校验验证码异常!");
				}
				ajaxQueryJSON(url,data,sucFun,errFun)
			}
		}
	
			
			
	//进入CA认证中的生成认证原文的servlet中
	function accessToCAinit(){
		var url = AUTH_SERVERCAINIT_URL;
		 $.ajax({
			 type : "get",
			 async:false,
			 url : url,
			 dataType : "jsonp",
			 jsonp: "callbackparam",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
			 jsonpCallback:"success_jsonpCallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
			 success : function(json){
				$("#appId").val(json.appId);
				$("#authURL").val(json.authURL);
				$("#original").val(json.original);
				$("#original_data").val(json.original_data);
				//alert("生成认证原文成功!");
			 }
		 });	
	}
			
	
	
			/**
			* window close
			*/
			function close()
			{
				//关闭市民卡扫描插件
				WebRiaReader.RiaCloseReader();
			}
	
		    /*
			*扫描市民卡
			*/
			function readCard()
			{
				var str;
				str = WebRiaReader.RiaReadCardEngravedNumber;
				if (str==undefined || "" == str){
					alert("请检查市民卡驱动是否正确安装。");
					return;
				}
				if($.trim(str) == "FoundCard error!"){
					alert("请放置市民卡。");
					return;
				}else{
					$("#person_card").val(str);			
				}
				personCardLogin();
			}
			
			/**
			* init photo activeX
			*/
			function initPhotoActiveX(){
				WebRiaReader.LinkReader();
			}
			
		 function reloadCode(){
		        var verify=document.getElementById('verfyCode');        
		        verify.setAttribute("src","RandImgCreater.jsp?"+Math.random());
		 }
	
		
		 function frmSubmitEnter() {
			if(window.event.keyCode==13) {
				randImgLogin();
			}	
		}
			function submitkey(obj){
			if (obj==1) {
					$("#username").val("njhwAdmin");
					$("#password").val("admin");
					$("#cas_submit1").click();
					
			}  
			if (obj==2) {
					$("#username").val("tjjAdmin");
					$("#password").val("admin");
					$("#cas_submit1").click();
					
			}    
			if (obj==3) {
					$("#username").val("djcry");
					$("#password").val("djcry");
					$("#cas_submit1").click();
					
			}   
			if (obj==4) {
					$("#username").val("gljry");
					$("#password").val("gljry");
					$("#cas_submit1").click();
					
			}   
			if (obj==5) {
					$("#username").val("tjjry");
					$("#password").val("tjjry");
					$("#cas_submit1").click();
					
			}  
			if (obj==6) {		
					$("#username").val("kfry");
					$("#password").val("123456");
					$("#cas_submit1").click();
					
			}   		
			if (obj==7) {		
					$("#username").val("zhcry");
					$("#password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==8) {		
					$("#username").val("dhbry");
					$("#password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==9) {		
					$("#username").val("fccry");
					$("#password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==10) {		
					$("#username").val("abcry");
					$("#password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==11) {		
					$("#username").val("gcbry");
					$("#password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==12) {		
					$("#username").val("itry");
					$("#password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==13) {		
					$("#username").val("infoAdmin");
					$("#password").val("admin");
					$("#cas_submit1").click();
					
			}   
			if (obj==0) {
					$("#username").val("admin");
					$("#password").val("zaq12wsx");
					$("#cas_submit1").click();
					
			}      
		} 
		function change(obj){
			if (obj==1) {
					$("#login_main_right").show();
					$("#login").hide();
			}  
			if (obj==2) {
					$("#login").show();
					$("#login_main_right").hide();
			}    
			
		}      
	
	
		 function  open_exe(shellp)  
		  {  
			  a=new ActiveXObject("wscript.shell");  
			  a.run(shellp);  
		  } 
		  
		  
	  //发送验证码倒计时Start
	     function doUpdate(num) {
	    
	    if(num == '60') {
	      document.forms.fm1.sendcode.value = " 重新发送 ";
	    } else 
		{
	      document.forms.fm1.sendcode.disabled =true;
	      wut = secs-num;
	      document.forms.fm1.sendcode.value = "发送手机验证码 (" + wut + ")...";
	      if(wut ==0)
	      {
	    	  document.forms.fm1.sendcode.value = "发送验证码";
	         }
	     }
	 }
	 function Timer() {
	  document.forms.fm1.sendcode.disabled =false;
	 }
	
		function reset(){
			$("#username").val('');
			$("#password").val('');
			$("#verifyCode").val('');
		}
	
	
	//发送验证码倒计时End
		function clear(){
			$("#phone_no").val('');
			$("#validate_code").val('');
			
		}
	
		function makeOther(type){
			window.location.href=type;
		}
	
	</script>
</head>
<body>
		<div class="login_main">
			<div class="login_main_left" id="login_main_left">
				<div id="login_main_left_img1" class="login_main_left_img1" onmouseover="login_hover(this, 0, 0)" onmouseout="login_hover(this, 1, 0)" onclick="login_click(this,0);window.location.href = 'login?statuss=8';"></div>
				<div id="login_main_left_img2" class="login_main_left_img2" onmouseover="login_hover(this, 0, 1)" onmouseout="login_hover(this, 1, 1)" onclick="login_click(this,1);window.location.href = 'samlValidate';"></div>
				<div id="login_main_left_img3" class="login_main_left_img3" onmouseover="login_hover(this, 0, 2)" onmouseout="login_hover(this, 1, 2)" onclick="login_click(this,2);window.location.href = 'login?statuss=9';"></div>
				<div id="login_main_left_img4" class="login_main_left_img4" onmouseover="login_hover(this, 0, 3)" onmouseout="login_hover(this, 1, 3)" onclick="login_click(this,3);window.location.href = 'validate';"></div>
			</div>
			
			
		<form method="post" onsubmit="return check()"  id="fm1" commandName="credentials" htmlEscape="true">
              <div class="login_main_right" id="login_main_right">
					<table class="login_main_right_table" id="login_main_right_table">
		
					<tr>
						<td>
								用户名:
						</td>
						<td>
							<input id="username" type="text" style="width:185px;" name="username" tabindex="1" autocomplete="false" htmlEscape="true" />
						</td>
					</tr>
                   	<tr>
						<td>
								密&nbsp;&nbsp;&nbsp;&nbsp;码:
						</td>
						<td>
							<input id="password" type="password" style="width:185px;" name="password" tabindex="1" autocomplete="false" htmlEscape="true"/>
						</td>
					</tr>
					<tr>
	                    <td><font style="font-size: 16px;">验证码：</font></td>      
			            <td>
			             <input name="verifyCode" style="width:185px;" id="verifyCode" type="text"  onKeyPress="javascript:frmSubmitEnter();">                                
			             <img src="RandImgCreater.jsp" border="0" align="top" alt="看不清验证码，请点击刷新！" id="verfyCode" onclick="reloadCode()"/>
	                    </td>
                  	</tr>
					
					<tr>
						<td></td>
						<td class="error_tip"><div id="errorContent" style="margin-bottom: 25px;text-align: center;width:160px;"><form:errors path="*" cssClass="errors" id="status" element="div" /></div></td>
					</tr>
								
					</table>
					
					<table class="login_main_right_table2" id="login_main_right_table2">
					<tr>
						<td style="text-align:right">手机号码：</td>
						<td colspan="2"><input type="text" id = "phone_no" style="width:85%;margin-left:5px"/></td>
					</tr>
					<tr>
						<td style="text-align:right">验证码：</td>
						<td><input class="get_input" id = "validate_code" onKeyPress="javascript:frmSubmitEnter();" style="width:80px;margin-left:5px" type="text"/></td>
						<td>
						<input type= "button"  name=sendcode  class="get_message"  style="width:110px; height: 27px;" value= "发送验证码" id ="btn_validate_code"  onclick="javascript:phoneCodeAuth();">
						</td>
<!--						<td><span class="get_message" style="font-size:12px;color:#046bbd;cursor:pointer" id = "btn_validate_code" onclick="javascript:phoneCodeAuth();">获取手机验证码</span></td>-->
					</tr>
					<tr>
						<td><font style="font-size: 12px;color: red;"><div class="tips_error" id="phoneError" style="position:absolute;top:85px;right:0px;width:230px;"></div></font></td>
					</tr>
					<input type="hidden" id="RootCADN" value="" width="30" />
					<input type="hidden" id="signed_data" name="signed_data" /> 
					<input type="hidden" id="original_jsp" name="original_jsp" /> 
					<input type="hidden" id="original_data" name="original_data" /> 
					<input type="hidden" id="appId" name="appId"/>
					<input type="hidden" id="authURL" name="authURL"/>
					<input type="hidden" id="original" name="original"/>
					</table>



	<form method="post" onsubmit="return check()" id="fm1"
		commandName="credentials" htmlEscape="true">
		<div class="login_main_right" id="login_main_right">
			<table class="login_main_right_table" id="login_main_right_table">

				<tr>
					<td>
						用户名:
					</td>
					<td>
						<input id="username" type="text" style="width: 185px;"
							name="username" tabindex="1" autocomplete="false"
							htmlEscape="true" />
					</td>
				</tr>
				<tr>
					<td>
						密&nbsp;&nbsp;&nbsp;&nbsp;码:
					</td>
					<td>
						<input id="password" type="password" style="width: 185px;"
							name="password" tabindex="1" autocomplete="false"
							htmlEscape="true" />
					</td>
				</tr>
				<tr>
					<td>
						<font style="font-size: 16px;">验证码：</font>
					</td>
					<td>
						<input name="verifyCode" style="width: 185px;" id="verifyCode"
							type="text" onKeyPress="javascript:frmSubmitEnter();">
						<img src="RandImgCreater.jsp" border="0" align="top"
							alt="看不清验证码，请点击刷新！" id="verfyCode" onclick="reloadCode()" />
					</td>
				</tr>

				<tr>
					<td></td>
					<td class="error_tip">
						<div id="errorContent"
							style="margin-bottom: 25px; text-align: center; width: 160px;">
							<form:errors path="*" cssClass="errors" id="status" element="div" />
						</div>
					</td>
				</tr>
			</table>

			<table class="login_main_right_table2" id="login_main_right_table2">
				<tr>
					<td style="text-align: right">
						手机号码：
					</td>
					<td colspan="2">
						<input type="text" id="phone_no"
							style="width: 200px; margin-left: 5px" />
					</td>
				</tr>
				<tr>
					<td style="text-align: right">
						验证码：
					</td>
					<td style="width: 120px">
						<input class="get_input" id="validate_code"
							onKeyPress="javascript:frmSubmitEnter();"
							style="width: 100px; margin-left: 5px" type="text" />
					</td>
					<td>
						<input type="button" name=sendcode
							style="width: 100px; height: 27px;" value="发送验证码"
							id="btn_validate_code" onclick="javascript:phoneCodeAuth();">
					</td>
				</tr>
				<tr>
					<td>
						<font style="font-size: 12px; color: red;"><div
								class="tips_error" id="phoneError"
								style="position: absolute; top: 85px; right: 0px; width: 230px;"></div>
						</font>
					</td>
				</tr>
				<input type="hidden" id="RootCADN" value="" width="30" />
				<input type="hidden" id="signed_data" name="signed_data" />
				<input type="hidden" id="original_jsp" name="original_jsp" />
				<input type="hidden" id="original_data" name="original_data" />
				<input type="hidden" id="appId" name="appId" />
				<input type="hidden" id="authURL" name="authURL" />
				<input type="hidden" id="original" name="original" />
			</table>

			<input type="hidden" id="person_card" />

			<div class="login_main_right_btn" id="login_main_right_btn">
				<input type="hidden" name="lt" value="${flowExecutionKey}" />
				<input type="hidden" name="_eventId" value="submit" />
				<div class="login_main_right_btn1" id="cas_submit" newtype=""
					onclick="loginEnter(this)" onmouseover="loginEnter_hover(this, 0)"
					onmouseout="loginEnter_hover(this, 1);">
				</div>
				<div class="login_main_right_btn2" id="reset" name="reset"></div>
				<input class="btn-submit" style="display: none" id="cas_submit1"
					name="submit" accesskey="l"
					value='login'
					tabindex="4" type="submit" />
				<input class="btn-reset" name="reset1" id="reset1"
					style="display: none" accesskey="c"
					value='reset'
					tabindex="5" type="reset" />
			</div>
		</div>
	</form>
</div>
</body>
</html>