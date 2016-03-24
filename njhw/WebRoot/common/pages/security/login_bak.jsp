<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
		<meta http-equiv="Cache-Control" content="no-store"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/>
		<script type="text/javascript">var _ctx = '${ctx}';</script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<title>登录页面</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/app/portal/css/login.css" />
		<script language="JavaScript" src="${ctx}/app/portal/js/login.js" type="text/JavaScript"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/util.js"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script type="text/javascript">
					$(function() {
				    	<%if ("true".equals(application.getInitParameter("userJCaptchaLogin"))) {%>
				    	    $("#jcaptchaText").show();
				    	<%} else if ("false".equals(application.getInitParameter("userJCaptchaLogin"))) {%>
				    	    $("#jcaptchaText").hide();
				    	<%}%>
					});
				
					
					function refreshCaptcha() {
						var url = '${ctx}/security/jcaptcha.jpg?';
						var params = Math.floor(Math.random()*100);
						url += params;
						$('#captchaImg').hide().attr('src', url).fadeIn();
					}
					
					function validation(){
					
						var param = {'j_captcha':$('#j_captcha').val()};
						var url='${ctx}/common/code/validationcaptchaAction.act';
						$.post(url,
								param,
								function(data){
									if(data=='false'){
									     // alert('执行false');   
										
										$("#meg").html("验证码错误,请重试！");
											$("#j_captcha").val("").focus();
											refreshCaptcha();
											
										
									}else{
									//alert('执行true'); 
								
									 submitButton();
									
									}
								},'text');
													
					}
					function submitButton(){
					  	//  alert("运行from");
						$("#loginForm").submit();
					
					}
					
					$(document).keyup(function(event){
						  if(event.keyCode ==13){
						    $("#Button1").trigger("click");
						  }
					});
		</script>
		<!--[if IE 6]> 
		<script type="text/javascript" src="js/ie_png.js"></script>
		<script type="text/javascript">
		ie_png.fix('div, img');  //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
		</script>
		<![endif]-->
	</head>
	<body>
	<form id="loginForm" action="${ctx}/sso/login/index.act" method="post"  autocomplete="off">
		<div class="bg_01" id="bg_01">
			<img src="${ctx}/app/portal/images/login_logo.png" alt="" class="logo" />
			<div class="login_box">
				<span class="error_tip" id="error_tip"></span>
				<ul class="title">
					<li>
						用户名<input id="j_username" class="wid" type="text" value="" name="loginname" tabindex="1" autocomplete="off"
							disableautocomplete="" onblur="leave(this)" onfocus="inner(this)">
						<span >用户名不能为空</span>
					</li>
					<li>
						密 码 <input id="j_password" class="wid" type="password" value="" name="pwd" tabindex="1"
							autocomplete="off" disableautocomplete="" onblur="leave(this)" onfocus="inner(this)">
						<span>密码不能为空</span>
					</li>
					<li>
					<div id="jcaptchaText" style="display:none">
						验证码<input type='text' name='j_captcha' id="j_captcha" size='5' maxlength="5" class="wid" />
						  <%if ("true".equals(application.getInitParameter("userJCaptchaLogin"))) {%>
						  <img id="captchaImg" width="80px" height="22px" style="position:absolute;right:80px" src="${ctx}/security/jcaptcha.jpg" onclick="refreshCaptcha()" title="单击刷新验证码"/>
						  <%}%>
					</div>
					</li>
				</ul>
				<div id="meg" style="color: red;font-size:14px;text-align:center;" >
				<c:choose>
			        <c:when test="${param.error eq '1'}">用户名或密码错误,请重试！</c:when>
			        <c:when test="${param.error eq '2'}">用户不存在,请重试！</c:when>
			        <c:when test="${param.error eq '3'}">密码错误,请重试！</c:when>
			        <c:when test="${param.error eq '4'}">用户被锁定,请联系管理员！</c:when>
			        <c:when test="${param.error eq '5'}">密码过期,请修改密码！</c:when>
			        <c:when test="${param.error eq '6'}">用户被停用,请联系管理员！</c:when>
			      
			    </c:choose>
				</div>
		
				<div class="login_img">
					<input type="button" onclick="validation()" value="登 录"  class="bottom_01" ID="Button1" onmouseover="btn1_over(this, 0)" onmouseout="btn1_over(this, 1)" hidefocus/>
					<input type="reset"  class="bottom_02" value="重 置" onclick="login_reset()" ID="Button2" onmouseover="btn2_over(this, 0)" onmouseout="btn2_over(this, 1)" hidefocus/>
					<a href="${ctx}/getPassword.act" class="wenzi2">忘记密码</a>
				</div>
			</div>
		</div>
		<div class="bg_02">
			<img src="${ctx}/app/portal/images/main_bg.jpg" alt="" />
		</div>
		<div class="login_loading" id="login_loading">请等待...</div>
	</form>
	</body>
</html>