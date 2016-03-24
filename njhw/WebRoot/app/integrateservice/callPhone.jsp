<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>发送短信</title>
		<script type="text/javascript">
		$(function() {
			$("#telnum").select();
		});
		
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			    $("#Button1").trigger("click");
			  }
		});
		
		function loadMacInfo(obj){
			if("one" == obj){
				if ($("#telnum").val().replace(/(^\s*)|(\s*$)/g, "").length == 8) {
					var telephone = /^[0-9]{8}$/;
			 		if (!telephone.test($("#telnum").val())) {
			 			easyAlert("提示信息", "IP电话由0到9之间的数字组成，长度8位，例:88668888","info");
			 			return false;
			 		}
			 		
					$.ajax({
			            type: "POST",
			            url: "${ctx}/app/integrateservice/loadMACByTel.act",
			            data: {"inputTel": $("#telnum").val()},
			            dataType: 'json',
			            async : false,
			            success: function(json){
		                    if(json.status == 0){
		                    	if(json.mac == 0){
		                    		//easyAlert("提示信息", "电话MAC地址出错,拨号将无法拨出！","info");
		                    	}else{
									$("#mac").val(json.mac);
		                    	}
		                    	$("#username").text(json.name);
		                    } else {
		                    	$("#username").html("<span style='color:red;'>系统无此号码</span>");
		                    }
			            },
			            error: function(msg, status, e){
			            	easyAlert("提示信息", "电话MAC地址出错,拨号将无法拨出！","info");
			            }
			         });
				}
			}else{
				if ($("#telPhone").val().replace(/(^\s*)|(\s*$)/g, "").length == 8) {
					var telephone = /^[0-9]{8}$/;
			 		if (!telephone.test($("#telPhone").val())) {
			 			easyAlert("提示信息", "IP电话由0到9之间的数字组成，长度8位，例:88668888","info");
			 			return false;
			 		}
			 		
					$.ajax({
			            type: "POST",
			            url: "${ctx}/app/integrateservice/loadMACByTel.act",
			            data: {"inputTel": $("#telPhone").val()},
			            dataType: 'json',
			            async : false,
			            success: function(json){
		                    if(json.status == 0){
								$("#mactwo").val(json.mac);
								$("#usernames").text(json.name);
								$("#orgName").text("("+json.shortName+")");
		                    } else {
		                    	$("#usernames").html("<span style='color:red;'>系统无此号码</span>");
		    					$("#orgName").html("<span style='color:red;'></span>");
		                    }
			            },
			            error: function(msg, status, e){
			            	easyAlert("提示信息", "加载电话信息出错！","info");
			            }
			         });
				}else{
					$("#usernames").html("<span style='color:red;'></span>");
					$("#orgName").html("<span style='color:red;'></span>");
				}
			}
		}
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		function closeWin() {
			closeEasyWin('winId');
		}
		
		function dialing() {
		 	var telephone = /^[0-9]{8}$/;
	 		if (!telephone.test($("#telnum").val())) {
	 			easyAlert("提示信息", "IP电话由0到9之间的数字组成，长度8位，例:88668888","info");
	 			return false;
	 		}
	 		
	 		var telPhones = /^[1][3-8]\d{9}$/;
	 		
	 		if (!telPhones.test($("#telPhone").val())) {
	 			if(!telephone.test($("#telPhone").val())){
	 				easyAlert("提示信息", "您输入呼叫的号码不正确!，例:手机：13812345678，IP电话：88668888","info");
		 			return false;
		 		}
	 		}
	 		
	 		if ($("#username").text() == "无此号码") return false;
		  	$.ajax({
				type: "POST",
				url:  "${ctx}/app/integrateservice/ipCall.act",
				data: {"selfMac": $("#mac").val(), "selfTel": $("#telnum").val(), "called": $("#telPhone").val()},
				dataType: 'text',
				async : false,
				success: function(msg){
					if(msg != "success")  easyAlert("提示信息", "拨号失败","info");
					else parent.closeIframe();
				 },
				 error: function(msg, status, e){
					 easyAlert("提示信息", "拨号出错","info");
				 }
			});
	    }
	</script>
	</head>

	<body style="background:#fff">
		<div>
			<div class="main2_border_list"><p>拨号确认</p></div>
			<table align="center" border="0" width="100%" class="form_table">
				<input type="hidden" id="tel" name="tel" value="${selfTel }"/>
				<input type="hidden" id="mac" name="mac" value="${selfMac }"/>
				<input type="hidden" id="mactwo" name="mactwo" value="${mactwo }"/>
				<input type="hidden" id="selfUserName" name="selfUserName" value="${_username }"/>
				<input type="hidden" id="called" name="called" value="${called }"/>
				<tr>
					<td class="form_label" width="140px">电话将从此号码拨出：</td>
					<td width="95px"><input style="background-color: #dcdcdc; border: none; height: 14px;" type="text" name="telnum" id="telnum" size="10" maxlength="8" value="${userCallId}" onkeyup="loadMacInfo('one')"/></td>
					<td id="username"><!-- ${_username } --></td>
				</tr>
				<tr>
					<td class="form_label">呼叫：</td>
					<td colspan="1"><span style="font-weight: bold;display:none">${pname }</span>
					<span style="font-weight: bold;">
					<input style="background-color: #dcdcdc; border: none; height: 14px;" type="text" name="telPhone" id="telPhone" size="11" maxlength="11" value="${called}" onkeyup="loadMacInfo('two')"/>
					</span></td>
					<td><c:if test="${not empty pname}"><span id="usernames">${pname }</span><span id="orgName">(${orgName})</span></c:if></td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" id="Button1" onclick="dialing();">拨号</a>　　
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="javascript:parent.closeIframe()">关闭</a>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
