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
			var i = -1;
			var isSuccess = true;
			
      		$.ajax({
				type: "POST",
				url: "${ctx}/app/personnel/unit/deleteCardSave.act",
				data: {"cardId": $("#cardId").val()},
				dataType: 'text',
				async : true
      		});
      			
						iIdForConfirm = setInterval(function(){ dealPubCard(); }, 1000);
								
      					function dealPubCard() {
      						$.ajax({
								type: "POST",
								url: "${ctx}/app/personnel/unit/showDeletePubCardConfirmForAll.act?",
								data: {"cardId": $('#cardId').val()},
								dataType: 'text',
								async : true,
								success: function(json){
									json = eval('(' + json + ')');
									i++;
									if (i == 0) {
										$("#confirm_page").html("正在删除中");
									} else if (i == 1) {
										$("#confirm_page").html("正在删除中.");
									} else if (i == 2) {
										$("#confirm_page").html("正在删除中..");
									} else if (i == 3) {
										$("#confirm_page").html("正在删除中...");
										i = -1;
									}
									
									
									
									if (json.complete == 'true') {
										clearInterval(iIdForConfirm);
										$("#confirm_page").html("删除完毕");
										$.ajax({
											type: "POST",
											url: "${ctx}/app/personnel/unit/deletePubCard.act",
											data: {"cardId": $("#cardId").val()},
											dataType: 'text',
											async: true
										});
										$("#confirmBtn").show();
									}
								} 
							});
      					}
      		
      		$('#confirmBtn').click(function() {
      			clearInterval(iIdForConfirm);
      			parent.$("#tksc").window("close");
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
		<div id="confirm_page" class="room_content" style="height: 40px; margin-left: auto; margin-right: auto; text-align:center; color:#868686;"></div>
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
	</div>
</body>
</html>

	
