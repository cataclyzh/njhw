<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="org.jasig.cas.client.validation.Assertion"%>
<%@ page import="org.jasig.cas.client.util.AbstractCasFilter"%>
<%@ page import="java.util.*"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>智慧政务社区登录页</title>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/AjaxUtil.js"></script>
</head>
<body style="display:none;">
		<form id="fm1" action="${ctx}/sso/login/index.act" method="post"  autocomplete="off">
              <div class="login_main_right" id="login_main_right">
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
							<input id="j_password" type="password" style="width:185px;" name="pwd" tabindex="1" autocomplete="false" htmlEscape="true"/>
						</td>
					</tr>
					</table>
					
			<input type="hidden" id="person_card" />
			<input type="hidden" id="sid" name="sid" value="1"/>

			<div class="login_main_right_btn" id="login_main_right_btn">
				<div class="login_main_right_btn2" id="reset" name="reset"></div>
				<input class="btn-submit" style="display: block" id="cas_submit1"
					name="submit" accesskey="l"
					value='login'
					tabindex="4" type="submit"/>
				<input class="btn-reset" name="reset1" id="reset1"
					style="display: block" accesskey="c"
					value='reset'
					tabindex="5" type="reset" />
			</div>
		</div>
</div>
</form>

	<%
		Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
	%>
	<input type="hidden" id="username" value="<%= assertion.getPrincipal().getName() %>" />
	<script type="text/javascript">
	
		function login(){
			
  			var j_username = $("#username").val().replace(/ */g,"");
			//var j_password = "admin123";
			var url = "${ctx}/app/auth/index/queryPasswordByUserID.act";
			var data = {userId:j_username};
			var sucFun = function (json){
				// [Object { LOGIN_PWD="0192023A7BBD73250516"}]
					 if(json[0]){
					 	$("#j_username").val(j_username);
					 	$("#j_password").val(json[0].LOGIN_PWD);
					 	$("#cas_submit1").click();
				 }else{
					 //两处用户密码不一样
					 //reloadCode();
					 //alert("用户数据出问题，请联系管理员！");
					 window.location.href = "http://newcity.njnet.gov.cn:9080/logout?error=7";
					 //$("#meg").html("验证失败，请重新输入!");
					}
				  
				 };
			var errFun = function(json) {
				//$("#meg").html("验证失败，请重新输入!");
				//alert("用户数据出问题，请联系管理员！");
				window.location.href = "http://newcity.njnet.gov.cn:9080/logout?error=7";
			};
			ajaxQueryJSON(url,data,sucFun,errFun);
		}
	
	
		$(function(){
			
			/**
			* 登录运营管理中心带此参数
			* sid=" + Math.random()
			* window.location.search = 
			* ?sid=0.06451516620077202
			*/
			var param = window.location.search;
			
			if (param && param.indexOf("sid")!=-1){
				//跳转到运营管理中心
				$("#sid").val(0);
				login();
			}else if(param && param.indexOf("error")!= -1){
				//spring security 定义出错,退出 cas 并提示错误信息
				var type = param.split("=")[1];
				//$("#errorLogout").trigger("click");
				window.location.href = "http://newcity.njnet.gov.cn:9080/logout?error=" + type;
			}else{
				login();
			}
		}); 
	</script>	
</body>
</html>