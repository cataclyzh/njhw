<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>智慧政务社区登录页</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link type="text/css" rel="stylesheet" href="${ctx}/common/pages/security/multlogin/css/SingleSignOn.css" />
		<style type="text/css">
			a:link {
			      color: #ffc602;
				/*color: #FF0000;
				text-decoration: none;*/
			}  /* 未访问的链接 */
			a:visited {
				color: #ffc602;
				text-decoration: none;
			}  /* 已访问的链接 */
			a:hover {
				/*color: #FF0000;*/
				text-decoration: underline;
			}  /* 鼠标移动到链接上 */
			a:active {
				color: #FF0000
			}
		</style>
	<script type="text/javascript" src="${ctx}/common/pages/security/multlogin/js/MD5.js"></script>
	<script type="text/javascript" src="${ctx}/common/pages/security/multlogin/js/config.js"></script>
	<script type="text/javascript" src="${ctx}/common/pages/security/multlogin/js/SingleSignOn.js"></script>
	<script type="text/javascript" src="${ctx}/app/portal/js/AjaxUtil.js"></script>
	<script type="text/javascript">
			var _ctx = '${ctx}';
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
					login_click(document.getElementById("login_main_left_img1"), '0');
					initPhotoActiveX();
					return;
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
				 var url = "${ctx}/casLoginWayServlet?authType="+authType+"&personCard="+$("#person_card").val();
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
		                	$("#j_username").val(json.username);
		                 	$("#j_password").val(json.password);
		                 	$("#cas_submit1").click();
		                 } else {
		                 	alert("市民卡认证失败!");
		                 }
		             },
		             error:function(){
		                 alert('市民卡认证异常!');
		             }
		         });		
			}
			/**
			* CA auth
			*/
			function caAuthLogin(){
				 var url = "${ctx}/auth?authType="+authType+"&caKey=key";
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
		                	$("#j_username").val(json.username);
		                 	$("#j_password").val(json.password);
		                 	$("#cas_submit1").click();
		                 } else {
		                 	alert("CA证书认证失败!");
		                 }
		             },
		             error:function(){
		                 alert('CA认证异常!');
		             }
		         });		
			}
			/**
			* @descripion phone code auth
			* @param phoneNo:电话号码
			* @return validateCode
			*         status 1:成功;2:手机认证失败！
					  j_password
					  j_username
			*/
			function phoneCodeAuth(){
				var result = validateMobile();
				if(result == true){
				 	playValidateControls("lock");
					effectValidateTimer = setTimeout("effectValidateExpired()",PHONE_VALIDATE_CODE_REQUET_PEROID);
					var phoneNo = $("#phone_no").val();
					var url = "${ctx}/casLoginWayServlet?authType="+authType+"&phoneNo="+phoneNo;
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
			                	$("#j_username").val(json.username);
			                 	$("#j_password").val(json.password);
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
			            	 $("#phoneError").html("手机认证异常!");
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
				var j_password = $("#j_password").val();
				if(authType == AUTH_TYPE_LOGIN){//做加密处理
					j_password = MD5(j_password);
					j_password = j_password.substr(0,20);
					$("#j_password").val(j_password);
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
							 $("#meg").html("输入验证码错误!");
						 }
					 },
					 error:function(){
						alert('校验验证码异常!');
					 }
				 });	
			}*/
		//校验验证码
		
		function randImgLogin(){
			var j_username = $("#j_username").val();
			var j_password = $("#j_password").val();
			
			if(j_username == ""){
					$("#meg").html("用户名不能为空!");
					return ;
			}
			if (j_password==""){
				$("#meg").html("密码不能为空!");
				return ;
			}
			
			var verifyCode = $("#verifyCode").val();
			
			
			//if(verifyCode == ""){
				//$("#meg").html("请输入验证码!");
			 //}else{
				var loginName = $("#j_username").val();
				var urls = "${ctx}/app/auth/index/queryAdminUserByIdJSON.act";
				var data = "loginName="+loginName;
				var dataType = "JSON";
				var sucFun = function(json){
					if(true){//json.isAdmin == "false"
						var url = "${ctx}/randImgServlet?validateCode="+verifyCode;
						var data = null;
						var sucFun = function (json){
							 //if(json.flag == "true"){
								 	if (j_password!=""){
										j_password = MD5(j_password);
										j_password = j_password.substr(0,20);
									}
								 	$("#j_password").val(j_password);
									
								 	$("#cas_submit1").click();
							 //}else{
								// reloadCode();
								// $("#meg").html("输入验证码错误!");
								//}
							 };
						var errFun = function(json) {
							$("#meg").html("校验验证码异常!");
						};
						ajaxQueryJSON(url,data,sucFun,errFun);
					}else{
						if(confirm("管理员请使用市民卡登录该系统!")){
							login_click(document.getElementById("login_main_left_img2"),1);
							loginsmk();
						}else{
						}
					}
				};
				var errFun = function(json){
					//alert("请检查登录帐号和密码是否正确!");
				};
				ajaxQuery("POST",urls,data,dataType,sucFun,errFun);
			//}
		}
	
			
			
	//进入CA认证中的生成认证原文的servlet中
	function accessToCAinit(){
		var url = "${ctx}/random";
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
					//alert("请检查市民卡驱动是否正确安装。");
					return;
				}
				if($.trim(str) == "FoundCard error!"){
					//alert("请放置市民卡。");
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
		        verify.setAttribute("src","${ctx }/common/pages/security/multlogin/RandImgCreater.jsp?"+Math.random());
		 }
	
		
		 function frmSubmitEnter(e) {
			 e = e || event;
		 
			if(e.keyCode==13) {
				randImgLogin();
			}	
		}
			function submitkey(obj){
			if (obj==1) {
					$("#j_username").val("njhwAdmin");
					$("#j_password").val("admin");
					$("#cas_submit1").click();
					
			}  
			if (obj==2) {
					$("#j_username").val("tjjAdmin");
					$("#j_password").val("admin");
					$("#cas_submit1").click();
					
			}    
			if (obj==3) {
					$("#j_username").val("djcry");
					$("#j_password").val("djcry");
					$("#cas_submit1").click();
					
			}   
			if (obj==4) {
					$("#j_username").val("gljry");
					$("#j_password").val("gljry");
					$("#cas_submit1").click();
					
			}   
			if (obj==5) {
					$("#j_username").val("tjjry");
					$("#j_password").val("tjjry");
					$("#cas_submit1").click();
					
			}  
			if (obj==6) {		
					$("#j_username").val("kfry");
					$("#j_password").val("123456");
					$("#cas_submit1").click();
					
			}   		
			if (obj==7) {		
					$("#j_username").val("zhcry");
					$("#j_password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==8) {		
					$("#j_username").val("dhbry");
					$("#j_password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==9) {		
					$("#j_username").val("fccry");
					$("#j_password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==10) {		
					$("#j_username").val("abcry");
					$("#j_password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==11) {		
					$("#j_username").val("gcbry");
					$("#j_password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==12) {		
					$("#j_username").val("itry");
					$("#j_password").val("123456");
					$("#cas_submit1").click();
					
			}   
			if (obj==13) {		
					$("#j_username").val("infoAdmin");
					$("#j_password").val("admin");
					$("#cas_submit1").click();
					
			}   
			if (obj==0) {
					$("#j_username").val("admin");
					$("#j_password").val("zaq12wsx");
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
			$("#j_username").val('');
			$("#j_password").val('');
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
		
		//自动读取市民卡
		function loginsmk(){
			$("#cas_submit").hide(); //隐藏登录按钮
			setInterval(readCard,1000);
		}
		
		$(function(){
			if ($("#msg").html()){
				$("#meg").html("用户名或密码错误,请重试！");
			}
			$(".login_top_a a").click(function(){
				var purpose = $(this).attr("id");
				switch (purpose){
					case "ITSM":
					{
						$("input[name=purpose]").val("http://itsm.njnet.gov.cn/");
						//$("input[name=purpose]").val("http://10.252.46.39:8090/XCITSM/");
						//$("#login_main_left div").not("#login_main_left_img1").attr("disabled","disabled");
						$("#sid").val(2);
						break;
					}
					case "MANAGE":
					{
						$("#sid").val(0);
						$("input[name=purpose]").val("http://newcity.njnet.gov.cn/security/login.act?sid=" + Math.random());
						//$("input[name=purpose]").val("http://10.250.51.15:8080/njhw/security/login.act?sid=" + Math.random());
						//$("#login_main_left div").not("#login_main_left_img1").attr("disabled","disabled");
					//	$("#login_main_left_img2").attr("readonly","readonly");
						break;
					}
					case "NJHW":
					{
						$("#sid").val(1);
						$("input[name=purpose]").val("http://newcity.njnet.gov.cn");
						//$("input[name=purpose]").val("http://10.250.51.15:8080/njhw/");
						//$("#login_main_left div").attr("disabled",false);
						break;
					}
					default :
					{
						//console.log("DAMN");		
						$("#sid").val(1);
						break;
					}
				};
				$(this).siblings("a").removeClass("login_top_active");
				$(this).addClass("login_top_active");
			})
		});
	</script>
</head>
<body>
		<div class="login_main">
			<div class="login_main_left" id="login_main_left">
				<div id="login_main_left_img1" class="login_main_left_img1" onmouseover="login_hover(this, 0, 0)" onmouseout="login_hover(this, 1, 0)" onclick="login_click(this,0);"></div>
				<div id="login_main_left_img2" class="login_main_left_img2" onmouseover="login_hover(this, 0, 1)" onmouseout="login_hover(this, 1, 1)" onclick="login_click(this,1);loginsmk();"></div>
				<div id="login_main_left_img3" class="login_main_left_img3" onmouseover="login_hover(this, 0, 2)" onmouseout="login_hover(this, 1, 2)" onclick="login_click(this,2);"></div>
				<div id="login_main_left_img4" class="login_main_left_img4" onmouseover="login_hover(this, 0, 3)" onmouseout="login_hover(this, 1, 3)" onclick="login_click(this,3);"></div>
			</div>
			
	<div id="objectDom" style="display: none">
		<object id='WebRiaReader' codeBase='WebRiaReader.cab' classid='clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB'></object>
	</div>
		<form id="fm1" action="${ctx}/sso/login/index.act" method="post"  autocomplete="off">
              <div class="login_main_right" id="login_main_right">

              <div class="login_top_a">
					<em style="font-size:14px;
						text-align:center;
						font-family:'微软雅黑';
						font-style: normal;
						position: relative;
						text-align: center;
					    top: 4px;
						left:15px;
						font-weight:bold;
						color:#415A88;">请选择登陆的系统</em>
					<a herf="#" id="ITSM" class="login_top_as">IT 运营系统</a>
					<a herf="#" id="MANAGE" class="login_top_as">运营管理中心</a>
					<a herf="#" class="login_top_as login_top_active" id="NJHW">智慧政务社区</a>
				</div>

					<table class="login_main_right_table" id="login_main_right_table">
		
					<tr>
						<td>
								<font style="font-size: 16px;">用户名:</font>
						</td>
						<td>
							<input id="j_username" type="text" style="width:185px;" name="loginname" tabindex="1" autocomplete="false" htmlEscape="true" />
						</td>
					</tr>
                   	<tr>
						<td>
								<font style="font-size: 16px;">密&nbsp;&nbsp;码:</font>
						</td>
						<td>
							<input id="j_password" type="password" style="width:185px;" name="pwd" onKeyPress="javascript:frmSubmitEnter(event);" tabindex="1" autocomplete="false" htmlEscape="true"/>
						</td>
					</tr>
					<tr>
	                    <td><font style="font-size: 16px;">验证码:</font></td>      
			            <td>
			             <input name="verifyCode" style="width:185px;" id="verifyCode" type="text"  onKeyPress="javascript:frmSubmitEnter(event);">                                
			             <img src="${ctx }/common/pages/security/multlogin/RandImgCreater.jsp" border="0" align="top" alt="看不清验证码，请点击刷新！" id="verfyCode" onclick="reloadCode()"/>
	                    </td>
                  	</tr>
					<tr>
						<td></td>

						<td class="error_tip">
						<div id="meg" style="margin-bottom: 25px;text-align: center;width:160px;color: red">
						<c:choose>
					        <c:when test="${param.error eq '1'}">用户名或密码错误,请重试！</c:when>
					        <c:when test="${param.error eq '2'}">用户不存在,请重试！</c:when>
					        <c:when test="${param.error eq '3'}">密码错误,请重试！</c:when>
					        <c:when test="${param.error eq '4'}">用户被锁定,请联系管理员！</c:when>
					        <c:when test="${param.error eq '5'}">密码过期,请修改密码！</c:when>
					        <c:when test="${param.error eq '6'}">用户被停用,请联系管理员！</c:when>					      
					    </c:choose>
						</div>
						</td>
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
						<td><div class="tips_error" id="phoneError" style="position:absolute;top:85px;right:0px;width:230px;font-size: 12px;color: red;"></div></td>
					</tr>
					<input type="hidden" id="RootCADN" value="" width="30" />
					<input type="hidden" id="signed_data" name="signed_data" /> 
					<input type="hidden" id="original_jsp" name="original_jsp" /> 
					<input type="hidden" id="original_data" name="original_data" /> 
					<input type="hidden" id="appId" name="appId"/>
					<input type="hidden" id="authURL" name="authURL"/>
					<input type="hidden" id="original" name="original"/>
					</table>
			<input type="hidden" id="person_card" />
			<input type="hidden" id="sid" name="sid" value="1"/>

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
					tabindex="4" type="submit"/>
				<input class="btn-reset" name="reset1" id="reset1"
					style="display: none" accesskey="c"
					value='reset'
					tabindex="5" type="reset" />
			</div>
		</div>
</div>
</form>
<div class="login_main_table">
     	<table>
	   		<tr>
	   			<td style=" color: white; font-size: 14px;">说明：首次登录，请下载并安装&nbsp;<a href="${ctx}/common/pages/security/multlogin/js/WebRiaSetup.exe" title="WebRiaSetup.exe"><font color="#ffc602" >市民卡驱动</font></a></td>
	   		</tr>
	   		<tr>
	   			<td style=" color: white; font-size: 14px;">手册下载：&nbsp;<a href="${ctx}/common/pages/security/multlogin/js/UnitManagerV1.0.pdf" title="智慧政务社区信息系统用户手册V1.0_单位管理员部分.pdf"><font color="#ffc602">单位管理员操作手册</font></a>
	   			&nbsp;<a href="${ctx}/common/pages/security/multlogin/js/ManagementBureauV1.0.pdf" title="智慧政务社区信息系统用户手册V1.0_管理局.pdf" ><font color="#ffc602">管理局操作手册</font></a>
	   			&nbsp;<a href="${ctx}/common/pages/security/multlogin/js/PropertyUserV1.0.pdf" title="智慧政务社区信息系统用户手册V1.0_物业.pdf" ><font color="#ffc602">物业用户操作手册</font></a>
	   			&nbsp;<a href="${ctx}/common/pages/security/multlogin/js/CommonUserV1.0.pdf" title="智慧政务社区信息系统用户手册V1.0.pdf" ><font color="#ffc602">普通用户操作手册</font></a>
	   			</td>
	   		</tr>
   		</table>
	</div>
</body>
</html>