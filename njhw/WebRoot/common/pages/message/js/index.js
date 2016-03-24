$(function() {
	// $("#main_frame_left").height(document.documentElement.clientHeight -
	// 220);
	// $("#main_frame_right").height(document.documentElement.clientHeight -
	// 220);
	// alert($("#hid_sendStatus").val());
	$(
			"input[type=submit],input[type=button],input[type=reset], input[type=file],a, button")
			.button().click(function(event) {
				// event.preventDefault();
			});
	var progressbar = $("#progressbar"), progressLabel = $(".progress-label");

	progressbar.progressbar({
		value : false,

	});

});
$(window).resize(function() {
	// $("#main_frame_left").height(document.documentElement.clientHeight -
	// 220);
	// $("#main_frame_right").height(document.documentElement.clientHeight -
	// 220);
});
var sendStatus = null;
/* 消息对话框 */
function showMessageBox(content) {
	$("#dialog-message").find("p").css("font-family","Verdana,Arial,sans-serif").html(content);

	$("#dialog-message").clone().dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		buttons : {

			"确定" : function() {
				$(this).dialog("close");
			}
		},
		open : function() {			
			$(".ui-button").blur();
		},
	});
}

/* 确认对话框 */
/**
 * @param content
 *            消息内容
 * @param func
 *            确定按钮触发函数
 */
function showConfirmBox(content, func) {
	$("#dialog-message").find("p").css("font-family","Verdana,Arial,sans-serif").html(content);

	$("#dialog-message").clone().dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		buttons : {

			"确定" : function() {
				func();
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		},
		open : function() {			
			$(".ui-button").blur();
		},
	});
}
function showAlertBox(content, func) {
	$("#dialog-message").find("p").css("font-family","Verdana,Arial,sans-serif").html(content);

	$("#dialog-message").clone().dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		buttons : {

			"确定" : function() {				
				// $("#main_frame_right").contents().find(".main3_from").submit();
				$(this).dialog("close");
			},
			
		},
		open : function() {			
			$(".ui-button").blur();
		},
		close : function() {
			func();
		}
	});
}
function showMessageDetail(msgId, notificationId) {
	$("#iframe-message-detail").attr(
			"src",
			"msgBoxAction_queryOutboxStatus.act?status=-1&msgId=" + msgId
					+ "&notificationId=" + notificationId);
	$("#dialog-message-detail").dialog({

		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		height : 400,
		width : 540,
		buttons : {

			"关闭" : function() {
				$(this).dialog("close");
			},
		},
		open : function() {			
			$(".ui-button").blur();
		},
	/*
	 * modal : true, draggable : true, resizable : false, dialogClass : "guide",
	 * closeText : "关闭", height : 600, width : 600
	 */

	});

}

/* 收件箱预览函数 */
function showMsg(msgId, sender, title, receiverid,senderId) {
	var src = _ctx
			+ "/common/bulletinMessage/msgBoxAction_checkSendMsg.act";
			//+ "/common/bulletinMessage/msgBoxAction_detailReciverbox.act";
	$("#iframe-inbox-preview").attr("src", src + "?msgId=" + msgId);

	$("#dialog-inbox-preview").dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		height : 460,
		width : 728,
		/* width="425px" height="510px" */
		/* width="700px" height="500px" */

		buttons : {
			/*	
			"回复" : function() {

				replyMsg(msgId, sender, title, receiverid,senderId);
				$(this).dialog("close");
			},
			*/
			"关闭" : function() {
				$(this).dialog("close");
			},
		},
		open : function() {			
			$(".ui-button").blur();
		},
		close : function() {
			// alert("关闭");
		}
	});
}
/* 收件箱显示函数 */
function replyMsg(msgId, sender, title, receiverid,senderId) {

	/**
	 * 修改元素属性，改为发送消息
	 */
	/*
	 * $(".ui-button-text").html("发送");
	 * 
	 * $("button[type=button]").click(function(){
	 * 
	 * //后台发送消息，跳转到发件箱 alert("Fuck");
	 * 
	 * });
	 */
	// 参数带过去
	/**
	 * ent.setContent(replyContent); ent.setTitle(replyTitle);
	 * ent.setReceiver(replyer); ent.setReceiverid(replyerId);
	 */

	var src = _ctx + "/common/pages/message/replyMsg.jsp";
	$("#iframe-reply-Msg").attr(
			"src",
			src + "?sender=" + sender + "&title=" + title + "&receiverid="
					+ receiverid + "&senderId=" + senderId);

	$("#dialog-reply-Msg")
			.dialog(
					{
						modal : true,
						draggable : true,
						resizable : false,
						closeText : "关闭",
						height : 460,
						width : 728,
						/* width="425px" height="510px" */
						/* width="700px" height="500px" */

						buttons : {
							/*
							 * "确定" : function() { //alert(sid + "," + id); },
							 */
							"发送" : function() {
								// alert($("#iframe-reply-Msg").contents().find("input[name=replyer]").val());
								// $("#iframe-reply-Msg").contents().find("#sendMsg").submit();
								// $(this).dialog("close");
								// $("#main_frame_right").contents().find(".main3_from").submit();

								$
										.post(
												_ctx
														+ "/common/bulletinMessage/msgBoxAction_reply.act",
												{
													replyer : $(
															"#iframe-reply-Msg")
															.contents()
															.find(
																	"input[name=replyer]")
															.val(),
													title : $(
															"#iframe-reply-Msg")
															.contents()
															.find(
																	"input[name=replyTitle]")
															.val(),
													replyerId : $(
															"#iframe-reply-Msg")
															.contents()
															.find(
																	"input[name=receiverid]")
															.val(),
													replyContent : $(
															"#iframe-reply-Msg")
															.contents()
															.find(
																	".show_textarea")
															.val(),
													senderId : $(
															"#iframe-reply-Msg")
															.contents()
															.find(
															"input[name=senderId]")
															.val()
												},
												function(result, status) {

													// console.log(result);

													// 回到发件箱
													if (result == "ok") {

														showMessageBox("回复成功！");

														$("#dialog-reply-Msg")
																.dialog("close");

														$("#main_frame_right")
																.attr("src",
																		"msgBoxAction_querySenderbox.act");
														/*
														 * window.frames("main_frame_right").location.href =
														 * "msgBoxAction_querySenderbox.act";
														 */
													}

												}

										);
							},
							"关闭" : function() {
								$("#dialog-reply-Msg")
								.dialog("close");
							},

						},
						open : function() {			
							$(".ui-button").blur();
						},
						close : function() {
							// alert("关闭");
							$(this).dialog("close");
						}
					});

}
