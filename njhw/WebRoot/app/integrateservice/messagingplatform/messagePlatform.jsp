<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>短信平台</title>
    <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	
	<script type="text/javascript">	
	$(function() {
		if ("${mobile}" != "" && "${name}" != "") {
			sendNote("${mobile}", "${name}")
		}
	});
	
	function sendNote(mobile, name){
	    var url = "${ctx}/app/messagingplatform/sendNoteInit.act";
		var sucFun = function(data) {
			$("#message_min").replaceWith(data);
		};
		var errFun = function() {
			alert("加载数据出错!");
		};
		ajaxQueryHTML(url, {"mobile": mobile, "name": name}, sucFun, errFun);
	}
	
	function sendMail(){
		   var url = "${ctx}/app/messagingplatform/findMessageButton.act";//sendMailInit.act
			var sucFun = function(data) {
				$("#message_min").replaceWith(data);
			};
			var errFun = function() {
				alert("加载数据出错!");
			};
			ajaxQueryHTML(url, null, sucFun, errFun);

		}

	function nodeon(){
		  $("#node").css("backgroundImage","url('${ctx}/app/integrateservice/messagingplatform/images/send-show.png')");

		}
	function nodeout(){
		  $("#node").css("backgroundImage","url('${ctx}/app/integrateservice/messagingplatform/images/send-bu.png')");
	}
	function mailon(){
		 $("#mail").css("backgroundImage","url('${ctx}/app/integrateservice/messagingplatform/images/box-show.png')");
	}
	function mailout(){
		 $("#mail").css("backgroundImage","url('${ctx}/app/integrateservice/messagingplatform/images/box-bu.png')");
	}
	
	
	</script>
  </head>

<body style=" text-align: center;">
<div id="message_public_div" style="margin:0 auto;width: 301px;height: 585px; text-align: left; background:url('${ctx}/app/integrateservice/messagingplatform/images/iphone.png'); border: 1px solid transparent;" > 
<div id="message_min"  style="margin: 62px 0px 0px 22px ; background: url('${ctx}/app/integrateservice/messagingplatform/images/page.png'); width: 261px;height: 444px;">
<div id ="node" 
style="cursor:pointer; float:left;  width: 95px; height: 125px;  margin: 64px 0px 0px 20px;
 background:url('${ctx}/app/integrateservice/messagingplatform/images/send-bu.png');" 
 onclick="sendNote('','')" onmouseover="nodeon()"  onmouseout="nodeout()">
 </div>
<div id="mail"
style="cursor:pointer; float:left; width: 95px; height: 125px; margin: 64px 0px 0px 20px; 
background:url('${ctx}/app/integrateservice/messagingplatform/images/box-bu.png');" 
onclick="sendMail()" onmouseover="mailon()"  onmouseout="mailout()">
</div>

</div>


</div>
  
  </body>
</html>
