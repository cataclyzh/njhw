<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>通卡列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="css/wizard_css.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#confirmBtn").hide();
			var i = 0;
			var isSuccess = true;
      		
      		var nodeIds = $("#nodeIds").val().split(",");
      		var rooms = $("#rooms").val().split(",");
      		if ($("#nodeIds").val() != "") {
      		$.ajax({
				type: "POST",
				url: "${ctx}/app/personnel/unit/playCardSave.act",
				data: {"cardId": $("#cardId").val(),
					   "opt": "del"},
				dataType: 'text',
				async : true
      		});
      			
      				function dealPubCard() {
						if (parseInt(i) >= nodeIds.length) {
      						$("#confirmBtn").show();
      						return;
      					}
      					$.ajax({
							type: "POST",
							url: "${ctx}/app/personnel/unit/showPubCardConfirm.act?",
							data: {"cardId": $('#cardId').val(),
							   "nodeId": nodeIds[i],
							   "opt": "del"},
							dataType: 'text',
							//async : false,
							success: function(json){
								json = eval('(' + json + ')');
								if (json.isSuccess) {
									$("#confirm_page").append("<div class='room ok'>"+rooms[i]+"</div>");
								} else {
									isSuccess = false;
									$("#confirm_page").append("<div class='room ng'>"+rooms[i]+"</div>");
								}
								
								i = parseInt(i) + parseInt(1);
								dealPubCard();
							} 
						});
					}
					dealPubCard();
				} else {
					$("#confirmBtn").show();
				}
      		
      		$('#confirmBtn').click(function() {
      			if (!isSuccess) {
      				$(window.parent.unit_nav_content.document.getElementById("failIndex")).val($('#delIndex').val());
      				parent.$("#tksc").window("close");
      			} else {
      				$.ajax({
					type: "POST",
					url: "${ctx}/app/personnel/unit/deletePubCard.act",
					data: {"cardId": $("#cardId").val()},
					dataType: 'text',
					async: true,
					success: function(json){
						parent.$("#tksc").window("close");
					}
					});
      			}
      		});
		});
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #fff;overflow: visable;">
	<div id="delPubContent">
	<div class="wizard_list" style="width: 80%;margin-top: 10px">
  		<div class="wizard_kh_no"style="width: 100%;"><span class="wizard_no">卡&nbsp;&nbsp;&nbsp;号</span><span class="wizard_data">${param.cardId}</span></div>
  	</div> 
	<div style="margin-top: 10px;position: absolute; width: 100%; height: 350px; overflow: hide;">
		<div id="confirm_page" class="room_content" style="height: 240px; margin-left: auto; margin-right: auto;"></div>
		<div class="wizard_sqs_color" style="width: 80%; margin-left: auto; margin-right: auto;text-align:center;">
			<span class="wizerd_green_color wizard_grbne"></span>
			<span>授权成功</span>
			<span class="wizerd_read_color wizard_grbne"></span>
			<span>授权失败</span>
		</div>
		<div style="width: 80%; margin-left: auto; margin-right: auto;text-align:center;">
			<a href="javascript:void(0);" class="buttonFinish" id="confirmBtn" style="text-decoration:none; float:right;">完了</a>
		</div>
	</div>
	<input type="hidden" id="cardId" value="${param.cardId}" />
	<input type="hidden" id="nodeIds" value="${param.nodeIds}" />
	<input type="hidden" id="rooms" value="${param.rooms}" />
	<input type="hidden" id="delIndex" value="${param.delIndex}" />
	</div>
</body>
</html>

	
