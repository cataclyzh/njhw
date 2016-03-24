<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<html>
<head>
	<title>JCosmo</title>
	<%@ include file="/common/include/header-meta.jsp" %>
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
    $("#button").trigger("click");
  }
});
	</script>
	
	<style type="text/css">
body{padding:0;margin:0;font-size:12px; text-align:center; background-image:url(../images/main/main_bg.jpg)}

.wenzi{
	font-family: "宋体";	font-size: 14px;	color: #ffffff;
}
.errcss{
	font-family: "宋体";	font-size: 13px;	color: #ec9704;
}
.inputbox {
	background-color: #FFFFFF;
	border: 1px solid #5D67AF;
	width: 160px;
	height: 18px;
}
a.wenzi:link {
	font-family: "宋体";	font-size: 14px;	color: #ffffff;	text-decoration: none;
}
a.wenzi:visited {
	font-family: "宋体";	font-size: 14px;	color: #ffffff;	text-decoration: none;
}
a.wenzi:hover {
	font-family: "宋体";	font-size: 14px;	color: #ffffff;	text-decoration: none;
}
.wz{
	font-family: "黑体";	font-size: 27px;	color: #4789bb;
}
.wz2{
	font-family: "黑体";	font-size: 14px;	color: #4789bb;
}
.wz3{
	font-family: "黑体";	font-size: 22px;	color: #216399;
}
.wenzi1{
	font-family: "宋体";	font-size: 13px;	color: #000000;	line-height:18px; padding: 1px;	
	border-bottom:#4364c5 1px solid; border-left:#4364c5 1px solid; border-top:#4364c5 1px solid;
}
.wenzi11{
	font-family: "宋体";	font-size: 13px;	color: #000000;	line-height:18px; 
}
a.wenzi1:link {
	font-family: "宋体";	font-size: 13px;	color: #000000;	text-decoration: none;
}
a.wenzi1:visited {
	font-family: "宋体";	font-size: 13px;	color: #000000;	text-decoration: none;
}
a.wenzi1:hover {
	font-family: "宋体";	font-size: 13px;	color: #292766;	text-decoration: none;
}
.wenzi2{
	font-family: "宋体";	font-size: 13px;	color: #c02a05;	line-height:18px;
}
a.wenzi2:link {
	font-family: "宋体";	font-size: 13px;	color: #c02a05;	text-decoration: none;
}
a.wenzi2:visited {
	font-family: "宋体";	font-size: 13px;	color: #c02a05;	text-decoration: none;
}
a.wenzi2:hover {
	font-family: "宋体";	font-size: 13px;	color: #c02a05;	text-decoration: none;
}
a.lt:link {
	font-family: "宋体";	color: #a72303;	text-decoration: none;
}
a.lt:visited {
	font-family: "宋体";	color: #a72303;	text-decoration: none;
}
a.lt:hover {
	font-family: "宋体";	color: #a72303;	text-decoration: none;
}
#box{width:1024px; height:100px; margin:0px auto;padding:0px;}
#top{width:1024px; margin-top:100px;}
#top2{width:499px; height:111px; float:left; }
#top3{width:525px; height:111px; float:left;}
#top4{width:1024px; float:left; }
#top5{clear:both;width:499px; height:240px; float:left; }
#top6{width:525px; height:240px; float:left; }
#top62{width:525px; height:62px; float:left; }
#top7{clear:both;width:1024px;margin-top:20px; float:left; }
#demo{float:left;}
#deemo a {
width:100%;
overflow:hidden;
font:12px/16px tahoma;
display:block;
text-decoration:none;
margin:2px;
color:#4a551c;
padding-left:2px;
text-align:left;
}
#deemo a:hover {
color:#ff6600;
}
.inputbox {
	background-color: #FFFFFF;
	border: 1px solid #5D67AF;
	width: 160px;
	height: 18px;
}

欢迎页
#bg{ width:90%;margin-top:100px; }
#wet{width:90%; margin:25px 0px 10px 10px; }
#wetop{width:90%; height:23px; background:#0033FF;border:1px solid #8bb6e3;}
#wetop1{width:90%; height:25px; background:#b2d4ed;}
#wetop2{width:90%; height:2px; background:#ffffff;}
#wetop3{width:90%; height:23px; background:#2570b1;}
#wetop4{width:90%; height:23px;margin-top:20px;  }
</style>
</head>
<body>
<div id="box">
<div id="top">
<div id="top2"></div>
<div id="top3"><table width="100%" height="112" border="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td valign="bottom" class="wz"><strong>JCosmo</strong></td>
  </tr>
</table>
</div>
</div>
<div id="top4">
<div id="top5"></div>
<div id="top6">
      <form id="loginForm" action="${ctx}/sso/login/index.act" method="post"  autocomplete="off">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <th colspan="2" scope="col">&nbsp;</th>
    </tr>
    <tr>
      <th width="57%" align="left" valign="top" scope="row">

      <table width="108%" height="150" border="0" align="center">
        <tr>
          <td width="24%">&nbsp;</td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right" class="wenzi">用户名：</div></td>
          <td colspan="2">
				<input type='text' name='loginname' size='20' maxlength="50" id="j_username"  class="inputbox"
				<s:if test="not empty param.error">
					value='<%=session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</s:if>/>
		</td>
        </tr>
        <tr>
          <td><div align="right" class="wenzi">密码：</div></td>
          <td colspan="2">
			<input type='password' size='20' name='pwd' id="j_password" maxlength="30" class="inputbox"/>
          </td>
        </tr>
        <tr id="jcaptchaText" style="display:none">
		  <td><div align="right" class="wenzi">验证码：</div></td>
		  <td><input type='text' name='j_captcha' id="j_captcha" size='5' maxlength="5" />
		  <%if ("true".equals(application.getInitParameter("userJCaptchaLogin"))) {%>
		  <img id="captchaImg" width="80px" height="22px" src="${ctx}/security/jcaptcha.jpg" onclick="refreshCaptcha()" title="单击刷新验证码"/>
		  <%}%>
		  </td>
		</tr>
		<tr>
			<td colspan='2' align="center" id="meg" style="color: red;font-size:13px">
			    <c:choose>
			        <c:when test="${param.error eq '1'}">用户名或密码错误,请重试！</c:when>
			        <c:when test="${param.error eq '2'}">用户不存在,请重试！</c:when>
			        <c:when test="${param.error eq '3'}">密码错误,请重试！</c:when>
			        <c:when test="${param.error eq '4'}">用户被锁定,请联系管理员！</c:when>
			        <c:when test="${param.error eq '5'}">密码过期,请修改密码！</c:when>
			        <c:when test="${param.error eq '6'}">用户被停用,请联系管理员！</c:when>
			      
			    </c:choose>
            </td>
		</tr>
        <tr>
          <td>&nbsp;</td>
          <td nowrap><input name="button" id="button" type="button" class="wz2" value="   登录   " onclick="validation()">
          <a href="${ctx}/getPassword.act" class="wenzi2">忘记密码</a>
          </td>
        </tr>
      </table></th>
      <th width="40%" scope="row"><table width="100%" height="70%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <th scope="col" class="wenzi">


          </th>
        </tr>
      </table>
      </th>
    </tr>
    </table>
  </form>  
</div>
</div>



</body>
</html>


