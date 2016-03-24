<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="js/MD5.js"></script>
<script type="text/javascript" src="js/config.js"></script>
<OBJECT id="WebRiaReader" codeBase="" classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
<script type="text/javascript">
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
		
		
		function validateCodeExpired(){
			validateCode = VALIDATE_CODE_EXPIRE;
			validateCodeTimer = null;
		}
		
		function effectValidateExpired(){
			playValidateControls("unlock");
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
		
		function changeLogType(type) {
				$("#nav_content div").hide();
				if (type == 'login') {
					$("#login>div").show();
					$("#login").show();
					authType = AUTH_TYPE_LOGIN;
				} else if (type == 'phonecodeAuth') {
					$("#phonecodeAuth").show();
					authType = AUTH_TYPE_PHONE_CODE;
				} else if (type == 'cardAuth') {
					$("#cardAuth").show();
					authType = AUTH_TYPE_PERSON_CARD;
				} else if (type == 'caAuth') {
					$("#caAuth").show();
					authType = AUTH_TYPE_CA;
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
	                 	$("#cas_submit").click();
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
	                 	$("#cas_submit").click();
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
		 	playValidateControls("lock");
			effectValidateTimer = setTimeout("effectValidateExpired()",PHONE_VALIDATE_CODE_REQUET_PEROID);
			var phoneNo = $("#phone_no").val();
			 var url = AUTH_SERVER_URL+"?authType="+authType+"&phoneNo="+phoneNo;
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
	                 	validateCode = $.trim(json.validateCode); 
	                 	validateCodeTimer = setTimeout("validateCodeExpired()",PHONE_VALIDATE_CODE_EXPIRED);
	                 	alert("手机号认证成功,验证码已发送!60秒内有效.");
	                 	
	                 } else if (statusCode==2) {
	                 	alert("手机认证失败！");
	                 } 
	             },
	             error:function(){
	                 alert('app njhw server request error!!!');
	             }
	         });		
		}
		/**
		* @descripion phone code login
		*/
		function phoneCodeLogin(){
			var vc = $("#validate_code").val();
			if ("" != validateCode && VALIDATE_CODE_EXPIRE == validateCode){
				alert("验证码过期!");
				return;
			} else if(validateCode != MD5(vc)) {
				alert("验证错误!");
				return;
			}
			$("#cas_submit").click();
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
		
		$(document).ready(function (){//parse cookie,init users last auth type!
			var cookieStr = document.cookie;
			cookieStr = decodeURI(cookieStr);
			cookieStr = cookieStr.substr(cookieStr.indexOf(COOKIE_STR),COOKIE_STR.length+1);
			var authType = cookieStr.substr(cookieStr.lastIndexOf("_")+1,1);
			var type = "login";
			if (authType == AUTH_TYPE_LOGIN){
				type = "login";
			} else if (authType == AUTH_TYPE_PHONE_CODE) {
				type = "phonecodeAuth";
			} else if (authType == AUTH_TYPE_PERSON_CARD) {
				type = "cardAuth";
			} else if (authType == AUTH_TYPE_CA) {
				type = "caAuth";
			}
			changeLogType(type);
			initPhotoActiveX();
		});
		
		/**
		* init photo activeX
		*/
		function initPhotoActiveX(){
			WebRiaReader.LinkReader();
		}
		
		/**
		* window close
		*/
		function close()
		{
			//关闭市民卡扫描插件
			WebRiaReader.RiaCloseReader();
		}
		/*write cookie*/
		function writeCookie(authType){
			var str = COOKIE_STR + authType;
			str = encodeURI(str);
			document.cookie = str;
		}
		
		/*
		*扫描市民卡
		*/
		function readCard()
		{
			var str;
			str = WebRiaReader.RiaReadIDCard();
			$("#person_card").val(str);
		}
		
</script>
<jsp:directive.include file="includes/top.jsp" />
			<div id="log_nav">
				<a href="javascript:changeLogType('login');">用户名密码</a>&nbsp;&nbsp;||&nbsp;&nbsp;<a href="javascript:changeLogType('phonecodeAuth');">手机验证码</a>&nbsp;&nbsp;||&nbsp;&nbsp;<a href="javascript:changeLogType('cardAuth');">市民卡</a>&nbsp;&nbsp;||&nbsp;&nbsp;<a href="javascript:changeLogType('caAuth');">CA证书</a>
			</div>
			<div id = "nav_content">
			<form:form method="post" onsubmit="return check()"  id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
			    <form:errors path="*" cssClass="errors" id="status" element="div" />
                <div class="box" id="login">
               			<!--<spring:message code="screen.welcome.welcome" />-->
                    <h2><spring:message code="screen.welcome.instructions" /></h2>
                    <div class="row">
                        <label for="username"><spring:message code="screen.welcome.label.netid" /></label>
						<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>
						<c:if test="${empty sessionScope.openIdLocalId}">
						<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
						<form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						</c:if>
                    </div>
                    <div class="row">
                        <label for="password"><spring:message code="screen.welcome.label.password" /></label>
						<%--
						NOTE: Certain browsers will offer the option of caching passwords for a user.  There is a non-standard attribute,
						"autocomplete" that when set to "off" will tell certain browsers not to prompt to cache credentials.  For more
						information, see the following web page:
						http://www.geocities.com/technofundo/tech/web/ie_autocomplete.html
						--%>
						<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
						<form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
                    </div>
                    <div class="row check">
                        <input id="warn" name="warn" value="true" tabindex="3" accesskey="<spring:message code="screen.welcome.label.warn.accesskey" />" type="checkbox" />
                        <label for="warn"><spring:message code="screen.welcome.label.warn" /></label>
                    </div>
                    <div class="row btn-row">
						<input type="hidden" name="lt" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />

                        <input class="btn-submit" id="cas_submit" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" />
                        <input class="btn-reset" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="reset" />
                    </div>
                </div>
                </form:form>
                <div id="phonecodeAuth" style="display:none;">
                	手机号：<input type="text" id = "phone_no"/> <input type="button" id = "btn_validate_code" value="获取验证码" onclick="javascript:phoneCodeAuth();"><br/>
                	验证码：<input type="text" id = "validate_code"/> <input type="button" value="登录" onclick="javascript:phoneCodeLogin();">
                </div>
                <div id="cardAuth" style="display:none;">
                	<input type="text" id = "person_card"/> <input type="button" value="获取市民卡" onclick="javascript:readCard();"> <input type="button" value="登录" onclick="javascript:personCardLogin();">
                </div>
                <div id="caAuth" style="display:none;">
                	caAuth
                </div>
               </div>
               <!--
	            <div id="sidebar">
	                <p><spring:message code="screen.welcome.security" /></p>
	                <div id="list-languages">
						<c:set var="query" value='<%=request.getQueryString() == null ? "" : request.getQueryString().replaceAll("&locale=([A-Za-z][A-Za-z]_)?[A-Za-z][A-Za-z]|^locale=([A-Za-z][A-Za-z]_)?[A-Za-z][A-Za-z]", "")%>'/>
						<c:set var="loginUrl" value="login?${query}${not empty query ? '&' : ''}locale=" />
	                    <h3>Languages:</h3>
						<ul
							><li class="first"><a href="login?${query}${not empty query ? '&' : ''}locale=en">English</a></li
							><li><a href="${loginUrl}es">Spanish</a></li				
							><li><a href="${loginUrl}fr">French</a></li
							><li><a href="${loginUrl}ru">Russian</a></li
							><li><a href="${loginUrl}nl">Nederlands</a></li
							><li><a href="${loginUrl}sv">Svenskt</a></li
							><li><a href="${loginUrl}it">Italiano</a></li
							><li><a href="${loginUrl}ur">Urdu</a></li
							><li><a href="${loginUrl}zh_CN">Chinese (Simplified)</a></li
							><li><a href="${loginUrl}de">Deutsch</a></li
							><li><a href="${loginUrl}ja">Japanese</a></li
							><li><a href="${loginUrl}hr">Croatian</a></li
							><li><a href="${loginUrl}cs">Czech</a></li
							><li class="last"><a href="${loginUrl}pl">Polish</a></li
						></ul>
	                </div>
	            </div>
        		-->
<jsp:directive.include file="includes/bottom.jsp" />
