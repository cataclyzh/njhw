<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<title>制作通卡</title>
		<%@ include file="/common/include/meta.jsp" %>
		<link href="css/smart_wizard.css" rel="stylesheet" type="text/css"/>
		<link href="css/count_down.css" rel="stylesheet" type="text/css"/>
		<link href="css/wizard_css.css" rel="stylesheet" type="text/css"/>
		<script src="${ctx}/scripts/juiportal/jquery.lwtCountdown-1.0.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/juiportal/misc.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/juiportal/jquery.smartWizard-2.0.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
		<OBJECT id="WebRiaReader" codeBase="" classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
		<script type="text/javascript">
    		$(document).ready(function(){
    			// Smart Wizard    	
  				var wizardObj = $('#wizard').smartWizard({
  					keyNavigation: false,
  					enableAllSteps: false,
  					transitionEffect: 'fade',
  					enableFinishButton: false,
  					labelNext:'下一步>',
  					labelFirst:'第一步',
  					labelPrevious:'<上一步',
  					labelFinish:'完成',
  					onLeaveStep:leaveAStepCallback,
  					onShowStep: showStepCallback,
  					onFinish: onFinishCallback});
      
      			function showStepCallback(obj){ 
        			var step_num= obj.attr('rel'); // get the current step number 
        			return initSteps(step_num); // return false to stay on step and true to continue navigation  
      			}
      			
      			function leaveAStepCallback(obj){ 
        			var step_num= obj.attr('rel'); // get the current step number     
        			return validateSteps(step_num); // return false to stay on step and true to continue navigation  
      			}
      			
      			function initSteps(step_num) {
      				if (step_num == 2) {
      					$("#tkCardId").html($('#cardId').val());
      					$(".buttonFirst").hide();
      					$(".buttonPrevious").show();
      					$(".buttonNext").show();
      					$(".buttonFinish").hide();
      					$(".actionBar").show();
      					
						// 选中全部
						$("#check_all").click(function() {
							$("#page_div input[type='checkbox']").each(function(i,item) {
								if ($(item).attr("disabled")!="disabled")	{
									if ($("#check_all").attr("checked")){
										$(item).attr("checked", "true");
									} else {
										$(item).removeAttr("checked");
									}
								}
							});
						});
						if ($("#isReloadTable").val() == "true") {
							var isCheckAll = true;
      						roomList = new Array();
    			
    						$("#page_div input[type='checkbox']").each(function(i,item) {
    							var roomObj = new Object();
    							roomObj.nodeId = $(item).val();
								if ($(item).attr("checked"))	{
									roomObj.checked = true;
								} else {
									roomObj.checked = false;
									isCheckAll = false;
								}
								roomObj.opt = "none";
								roomList[i] = roomObj;
							});
							
							if (isCheckAll) {
								$("#check_all").attr("checked", "true");
							}
							
							$("#isReloadTable").val("false");
							
							if ($("#opt").val() == 'add') {
								$("#check_all").attr("checked", "true");
								$("#check_all").click();
								$("#check_all").attr("checked", "true");
							}
						}						
      				} else if (step_num == 1) {
      					var steps = $("ul > li > a[href^='#step-']");
      					var stepRoom = steps.eq(1);
      					stepRoom.addClass("disabled");
      					$("span", stepRoom).addClass("disabled");
      					
      					var stepAdmin = steps.eq(2);
      					stepAdmin.addClass("disabled");
      					$("span", stepAdmin).addClass("disabled");
      					$("#step-1").height(430);
      					$(".swMain .stepContainer").height(430);
      					$('#readPubCardPic').show();
						$('#errorPubCardPic').hide();
      					$(".buttonFirst").hide();
      					$(".buttonPrevious").hide();
      					$(".buttonNext").hide();
      					$(".buttonFinish").hide();
      					$(".actionBar").hide();
						//$('#cardId').val("0000990163854300");
						//$(".buttonNext").show();
						//$(".buttonNext").click();
						//$(".buttonNext").hide();
						var cardId =  null;
      					iIdForPub = setInterval(function(){ readPubCard(); }, 500);
      					var ifLinkChecked = false;
      					function readPubCard() {
      						if (ifLinkChecked) {
      							return;
      						}
      						if (!WebRiaReader.LinkReader()) {
      							easyAlert('提示信息','无法读取市民卡，请检查读卡器连接状态和市民卡插件安装情况！','error');
      							ifLinkChecked = true;
      							return;
      						} else {
      							ifLinkChecked = false;
      						}
      			    		var ReadCardId = WebRiaReader.RiaReadCardEngravedNumber();
      						if (cardId == ReadCardId) {
      							return;
      						} else {
      							cardId = ReadCardId;
      						}
      						
							if (cardId != "FoundCard error!"){
								$.ajax({
									type: "POST",
									url: "${ctx}/app/personnel/unit/checkPubCardNo.act",
									data: {"cardId": cardId,
										   "opt": $("#opt").val()},
									dataType: 'text',
									async : false,
									success: function(json){
										json = eval('(' + json + ')');
										if (json.isRight == 'true') {
											clearInterval(iIdForPub);
											$('#cardId').val(cardId);
											$('#cardIdForRoom').val(cardId);
											$(".buttonNext").show();
											$(".buttonNext").click();
											$(".buttonNext").hide();
										} else {
											if (json.error == 'A') {
												easyAlert('提示信息','通卡必须是不记名卡！','error');
											} else if (json.error == 'B') {
												easyAlert('提示信息','市民卡接口异常！请稍后再试！','error');
											} else if (json.error == '1') {
												easyAlert('提示信息','此卡已经被使用，不能新建通卡！','error');
											} else if (json.error == '2') {
												easyAlert('提示信息','此卡不是通卡，不能更改通卡内容！','error');
											}
											$('#readPubCardPic').hide();
											$('#errorPubCardPic').show();
										}
									}
						 		});
							}
      					}
      				} else if (step_num == 3) {
      					var canEnd = false;
						$('#readAdminCardPic1').show();
						$('#adminCardInfo1').hide();
						$('#countdown_dashboard').countDown({
	        				targetOffset: {
	        					'day':      0,
								'month':    0,
								'year':     0,
								'hour':     0,
								'min':      0,
								'sec':      59},
	        				onComplete: function() {
								canEnd = true;
							} 
	    				});

	    				$("#firstAdmin").val("");

      					$(".buttonFirst").hide();
      					$(".buttonPrevious").show();
      					$(".buttonNext").hide();
      					$(".buttonFinish").hide();
      					
      					
      					//setTimeout(function(){
							//canEnd = true;
						//}, 60000);
      					//$(".buttonNext").show();
						//$(".buttonNext").click();
						//$(".buttonNext").hide();
						var cardId =  null;
      					iIdForAdmin = setInterval(function(){ readAdminCard(); }, 500);
      					
      					function readAdminCard() {
      						if (canEnd) {
								clearInterval(iIdForAdmin);
								$(".buttonFirst").show();
								$(".buttonFirst").click();
								$(".buttonFirst").hide();
								return;
							}
      			    		WebRiaReader.LinkReader();
      			    		var ReadCardId = WebRiaReader.RiaReadCardEngravedNumber();
      						if (cardId == ReadCardId) {
      							return;
      						} else {
      							cardId = ReadCardId;
      						}

							if (cardId != "FoundCard error!") {
								$.ajax({
									type: "POST",
									url: "${ctx}/app/personnel/unit/checkAdminCard.act",
									data: {"cardId": cardId},
									dataType: 'text',
									async : false,
									success: function(json){
										json = eval('(' + json + ')');
										if (json.isAdmin) {
											clearInterval(iIdForAdmin);
											$('#readAdminCardPic1').hide();
											$('#adminCardInfo1').show();
											$("#adminCard1").html(cardId);
											$("#adminName1").html(json.name);
											$("#firstAdmin").val(cardId);
											$(".buttonNext").show();
											$(".buttonNext").click();
											$(".buttonNext").hide();
										} else {
											easyAlert('提示信息','必须通过管理员卡号进行验证！','error');
										}
									}
						 		});
							}
      					}
      				} else if (step_num == 4) {
      					$(".buttonFirst").hide();
      					$(".buttonPrevious").hide();
      					$(".buttonNext").hide();
      					$(".buttonFinish").show();
      					var checkedNode = '';
      					$("#page_div input[type='checkbox']").each(function(i,item) {
							if ($(item).attr("checked")){
								checkedNode = checkedNode + $(item).val() + ",";
							}
						});
      					$.ajax({
							type: "POST",
							url: "${ctx}/app/personnel/unit/playCardSave.act",
							data: {"cardId": $("#cardId").val(),
								   "checked": checkedNode,
								   "opt": $("#opt").val()},
							dataType: 'text',
							async : false
						});
      					var i = 0;
      					function dealPubCard() {
      						if (parseInt(i) >= $("#page_div input[type='checkbox']").length) {
      							$('#confirm_msg').html("通卡制作完成");
      							return;
      						}
      						if (roomList[i].opt == "none") {
      							var roomInfo = $("#page_div_confirm td:eq(" + i + ")").html();
      							$("#confirm_page td:eq(" + i + ")").html(roomInfo);
      							i = parseInt(i) + parseInt(1);
								dealPubCard();
      						} else {
      							$.ajax({
									type: "POST",
									url: "${ctx}/app/personnel/unit/showPubCardConfirm.act?",
									data: {"cardId": $('#cardId').val(),
									   	   "nodeId": roomList[i].nodeId,
									   	   "opt": roomList[i].opt},
									dataType: 'text',
									//async : false,
									success: function(json){
										json = eval('(' + json + ')');
										if (json.isSuccess) {
											var roomInfo = $("#page_div_confirm td:eq(" + i + ")").html();
											$("#confirm_page td:eq(" + i + ")").html(roomInfo);
											$("#confirm_page td:eq(" + i + ")").addClass("wizerd_green_color");
										} else {
											var roomInfo = $("#page_div_confirm td:eq(" + i + ")").html();
											$("#confirm_page td:eq(" + i + ")").html(roomInfo);
											$("#confirm_page td:eq(" + i + ")").addClass("wizerd_read_color");
										}
										
										i = parseInt(i) + parseInt(1);
										dealPubCard();
									} 
								});
      						}
      					}
      					dealPubCard();
      					
      				}
      			}
        
        		function validateSteps (step_num) {
        			var isStepValid = true;
        			if (step_num == 1) {
        				clearInterval(iIdForPub);
        				$("#step-1").height(389);
        				$(".swMain .stepContainer").height(389);
        				$("#isReloadTable").val("true");
      					$.ajax({
							type: "POST",
							url: "${ctx}/app/personnel/unit/playCardData.act",
							data: {"cardId": $("#cardId").val()},
							dataType: 'text',
							async : false,
							success: function(data) {
								$("#page_div").empty().html(data);
							}
						});
        			} else if (step_num == 2) {
        				$("#page_div_confirm").empty();
        				$("#page_div_confirm_2").empty();
        				$("#confirm_page").empty();
        				$("#page_div_confirm").html($("#page_div").html());
        				$("#page_div_confirm_2").html($("#page_div").html());
        				$("#confirm_page").html($("#page_div").html());
        				$("#page_div input[type='checkbox']").each(function(i,item) {
        					$("#page_div_confirm td:eq(" + i + ") input[type='checkbox']").remove();
        					$("#page_div_confirm_2 td:eq(" + i + ") input[type='checkbox']").remove();
        					$("#confirm_page td:eq(" + i + ")").empty();
							if ($(item).attr("checked")){
								if (!roomList[i].checked) {
									roomList[i].opt = "add";
								}
								$("#page_div_confirm td:eq(" + i + ")").addClass("wizard_tdno");
								$("#page_div_confirm_2 td:eq(" + i + ")").addClass("wizard_tdno");
							} else {
								if (roomList[i].checked) {
									roomList[i].opt = "del";
								}
							}
						});
						
        			} else if (step_num == 3) {
        				clearInterval(iIdForAdmin);

        				$('#countdown_dashboard').stopCountDown();
        			}
        			return isStepValid;
        		}
        
      			function onFinishCallback(){ 
       				var url = "${ctx}/app/personnel/unit/playCardIndex.act";
					window.location.href = url;
      			}
			});
		</script>
	</head>

	<body>
		<form id="page_search_form" name="searchForm" action="${ctx}/app/personnel/unit/playCardSave.act" method="post">
		<div class="wizard_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/personnel/unit/playCardIndex.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="wizard_main">
			<div class="bgsgl_border_left">
	  			<div class="bgsgl_border_right">通卡管理</div>
			</div>
					<!-- Smart Wizard -->
					<div id="wizard" class="swMain">
						<div class="wizard_mbx">
							<ul>
								<li>
									<a href="#step-1" class="wizard_mbx_a1"><span class="wizard_mbx_span1">STEP1：</span><span class="wizard_mbx_span2">通卡读卡</span></a>
								</li>
								<li>
									<a href="#step-2" class="wizard_mbx_a2"><span class="wizard_mbx_span1">STEP2：</span><span class="wizard_mbx_span2">选择房间</span></a>
								</li>
								<li>
									<a href="#step-3" class="wizard_mbx_a3"><span class="wizard_mbx_span1">STEP3：</span><span class="wizard_mbx_span2">管理员确认</span></a>
								</li>
								<li>
									<a href="#step-4" class="wizard_mbx_a5"><span class="wizard_mbx_span1">STEP4：</span><span class="wizard_mbx_span2">完成</span></a>
								</li>
							</ul>
						</div>
						<div class="wizard_list_border"></div>
						<div id="wizard_content">
						
						<div id="step-1">
							<div class="wizard_list">
  								<div class="wizerd_borders">提示</div>
  								<div class="wizerd_border">通卡必须是不记名卡</div>
  							</div>
  							<div id="readPubCardPic" class="wizerd_sqconterfs"></div>
  							<div id="errorPubCardPic" class="wizerd_sqconters"></div>
						</div>
						<div id="step-2">
  							<div class="wizard_list">
  								<div class="wizard_kh_no"><span class="wizard_no">卡&nbsp;&nbsp;&nbsp;号</span><span class="wizard_data" id="tkCardId"></span></div>
  							</div> 
  							<div class="wizerd_sqconter">
  								<div id="page_div" class="wizard_list_wangchens">
  								</div>
  							</div>
  							
  							<table width="820px" border="0" cellspacing="0" cellpadding="0" style="margin-top: 1px; margin-left: auto; margin-right: auto;">
								<tr class="wizard_list_td">
									<td style="height:32px;line-height:32px;text-align:center;font-family:Arial, Helvetica, sans-serif;
									font-weight:bold;color:#7f90b3;">
										<input
										type="checkbox" id="check_all"/>全选
									</td>
								</tr>
							</table>
  							
						</div>
						<div id="step-3">
							<div class="wizard_list">
  								<div class="wizerd_border">请确认授权房间</div>
  							</div>
							<div id="page_div_confirm" class="wizard_list_conter"></div>
							<div class="wizard_list">
								<div class="wizerd_border1">完成请刷卡到下一步</div>
							</div>
							<div id="countdown_dashboard" class="wizard_list_conter1">
								<div class="dash seconds_dash">
									<div class="countdown_left_space"></div>
									<div class="countdown_top_space"></div>
									<div class="digit">0</div>
									<div class="digit">0</div>
								</div>
								<div class="wizard_right"></div>
								<div class="bgsgl_clear"></div>
							</div>
						</div>
						<div id="step-4">
							<div class="wizard_list">
  								<div class="wizerd_border" id="confirm_msg">正在授权中...</div>
  							</div>
  							<div class="wizerd_sqconter">
  								<div class="wizard_list_wangchen" style="height: 333px; background:#f6f5f1;">
  									<div id="confirm_page" class="wizard_list_wangchens"style="height: 256px;"></div>
  									<div class="wizard_sqs_color">
										<span class="wizerd_green_color wizard_grbne"></span>
										<span>授权成功</span>
										<span class="wizerd_read_color wizard_grbne"></span>
										<span>授权失败</span>
									</div>
								</div>
							</div>
						</div>
						
						</div>
					</div>
					<!-- End SmartWizard Content -->
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
		<input type="hidden" id="cardId" name="cardId" />
		<input type="hidden" id="firstAdmin" name="firstAdmin" />
		<input type="hidden" id="opt" name="opt" value="${opt}" />
		<input type="hidden" id="isReloadTable" name="isReloadTable"/>
		</form>
	</body>
</html>