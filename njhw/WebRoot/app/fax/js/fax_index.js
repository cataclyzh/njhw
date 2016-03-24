$(function() {
	// checkActiveX();
	$("#main_frame_left").height(document.documentElement.clientHeight - 220);
	$("#main_frame_right").height(document.documentElement.clientHeight - 220);
	// alert($("#hid_sendStatus").val());
	$(
			"input[type=submit],input[type=button],input[type=reset], input[type=file],button")
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

var iframeModal = null;
/**
 * @description iFrame对话框
 * 
 * @param title
 *            标题
 * @param width
 *            宽度
 * @param height
 *            高度
 * @param src
 *            链接
 * @param func
 *            关闭对话框时的回调函数,可为空
 */
function showIframeModal(title, width, height, src, func) {
	$("#iframe-modal").attr("src", src);
	iframeModal = $("#dialog-show-iframe").clone().dialog({
		title : title,
		autoOpen : false,
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		height : height,
		width : width,
		buttons : {

			"关闭" : function() {
				$(this).dialog("close");
			}
		},
		open : function() {			
			$(".ui-button").blur();
		},
		close : function() {
			if (func != null)
				func();
		}

	});
	iframeModal.dialog("open");
}
/**
 * 关闭iFrame对话框
 */
function closeIframeModal() {
	if (iframeModal != null)
		iframeModal.dialog("close");
}

var contactEdit = null;
/**
 * @description 通讯录添加修改对话框
 * 
 * @param title
 * @param width
 * @param height
 * @param src
 * @param func
 */
function showContactEdit(title, width, height, src, func) {
	$("#iframe-contact-edit").attr("src", src);
	contactEdit = $("#dialog-contact-edit").dialog({
		title : title,
		autoOpen : false,
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		height : height,
		width : width,

		buttons : {

			"保存" : function() { //

				if ($("#iframe-contact-edit")[0].contentWindow.showRequest()) {
					$("#iframe-contact-edit")[0].contentWindow.saveData();
					$(this).dialog("close");
				}
			},
			"关闭" : function() {
				$(this).dialog("close");
			}
		},
		open : function() {			
			$(".ui-button").blur();
		},
		close : function() {
			if (func != null)
				func();
		}

	});
	contactEdit.dialog("open");
}
/**
 * 关闭通讯录添加修改对话框
 */
function closeContactEdit() {
	if (contactEdit != null)
		contactEdit.dialog("close");
}

/**
 * @description 消息对话框
 * 
 * @param content
 *            内容
 * @param func
 *            关闭对话框时的回调函数,可为空
 */
function showMessageBox(content, func) {
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
		close : function() {
			if (func != null)
				func();
		}
	});
}
/**
 * @description 带callback的消息对话框
 * 
 * @param content
 *            内容
 * @param func
 *            callback函数
 */
/*
 * function showMessageBoxCallback(content, func) {
 * $("#dialog-message").find("p").html(content);
 * 
 * $("#dialog-message").clone().dialog({ modal : true, draggable : true,
 * resizable : false, closeText : "关闭", buttons : {
 * 
 * "确定" : function() { //
 * $("#main_frame_right").contents().find(".main3_from").submit();
 * $(this).dialog("close"); }, }, close : function() { if (func != null) func(); }
 * }); }
 */

function activexMessage(content, func) {
	$("#dialog-message").find("p").css("font-family","Verdana,Arial,sans-serif").html(content);

	$("#dialog-message").dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		buttons : {

			"刷新" : function() {
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
/* 大廈指南 */
function openGuide() {

	$("#dialog-guide").dialog({

		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		height : 600,
		width : 540,
		buttons : {

			"确定" : function() {
				$(this).dialog("close");
			}
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

/**
 * @description 预览等待对话框
 * @param content
 *            内容
 */
function showPreviewWaiting(content) {

	$("#dialog-message").find("p").css("font-family","Verdana,Arial,sans-serif").html(content);

	$("#dialog-message").dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		buttons : null,
		close : null,
		open : function() {			
			$(".ui-button").blur();
		},
	});
}

/**
 * @description 确认对话框
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
				if (func != null)
					func();
				// $("#main_frame_right").contents().find(".main3_from").submit();
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
/**
 * @description 发改委代办事项导入
 * @param file
 */
function importNDRCDoList(file) {

	$("#dialog-import-NDRC-do-list").dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		height : 300,
		width : 453,

		buttons : {
			/*
			 * "确定" : function() { //alert(sid + "," + id); },
			 */
			"上传" : function() {
				var file = $("#file").val();
				file = file.substring(file.length - 3, file.length);
				if (file == "xml") {
					if (file != "") {
						$("#progressbar").show();
						// $("#loadingdiv").show();
						// $('a.easyui-linkbutton').linkbutton('disable');
						$("#inputForm").submit();
					} else {
						showMessageBox("上传文件不能为空！");
						// alert("提示信息', '上传文件不能为空！");
						// easyAlert('提示信息', '上传文件不能为空！', 'info');
					}
				} else {
					showMessageBox("请上传xml文档！");
					// alert("请上传xml文档！");
					// easyAlert('提示信息', '请上传xml文档！', 'info');
				}
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		},
		open : function() {			
			$(".ui-button").blur();
		},
		close : function() {
			if ($("#hid_sendStatus").val() == "") {
				// 取消发送
				// http://10.254.101.100/ipcom/index.php/Api/fo_cancel
				// 返回 ：{"error":null,"data":"ok"}
				$.post("faxCancel.act", function(result, status) {
					if (result.data == "ok") {
						$("#main_frame_right").attr("src", "fax_send.jsp");
						// window.frames("main_frame_right").location.href
						// = "fax_send.jsp";
					}

				}

				);
			}
		},

	});
}
/**
 * @description 收件箱预览函数
 * @param sid
 *            session ID
 * @param id
 *            传真ID
 */
function showInboxPreview(sid, id , func) {
	$("#iframe-inbox-preview").attr("src",
			"fax_inbox_preview.jsp?type=fi&session_id=" + sid + "&id=" + id);

	$("#dialog-inbox-preview").dialog({
		modal : true,
		draggable : true,
		resizable : false,
		closeText : "关闭",
		height : 630,
		width : 453,

		buttons : {
			/*
			 * "确定" : function() { //alert(sid + "," + id); },
			 */
			"关闭" : function() {

				$(this).dialog("close");
			}
		},
		beforeClose : function() {
			$("#iframe-inbox-preview").attr("src", "");
		},
		open : function() {			
			$(".ui-button").blur();
		},
		close : function() {
			if (func != null)
				func();
			// alert("关闭");
		}
	});
}
/* 发件箱预览函数 */
function showOutboxPreview(returnedData) {
	$("#iframe-outbox-preview").attr("src",
			"fax_outbox_preview.jsp?returnedData=" + returnedData);

	var flag = false ;
	
	$("#dialog-outbox-preview").dialog(
			{
				modal : true,
				draggable : true,
				resizable : false,
				closeText : "关闭",
				height : 630,
				width : 453,

				buttons : {
					/*
					 * "确定" : function() { //alert(sid + "," + id); },
					 */
					"发送" : function() {
						// 确认发送
						// http://10.254.101.100/ipcom/index.php/Api/fo_confirm
						// 返回 ：{"error":null,"data":"ok"}
						$.post("faxConfirm.act", function(result, status) {
							if (result.data == "ok") {

								showMessageBox("已发送到队列！");
								// 关闭窗口
								/*
								$("#main_frame_right").attr("src",
										"fax_outbox_list.jsp");
								*/	

							}

						}

						);
						flag = true ;
						$(this).dialog("close");
					},
					"取消" : function() {
						/*
						$("#hid_sendStatus").val(0);
						// 取消发送
						// http://10.254.101.100/ipcom/index.php/Api/fo_cancel
						// 返回 ：{"error":null,"data":"ok"}
						$.post("faxCancel.act", function(result, status) {
							if (result.data == "ok") {
								// alert("取消发送！");
								$("#main_frame_right").attr("src",
										"fax_send.jsp?sid=cancel");
								// window.frames("main_frame_right").location.href
								// = "fax_send.jsp";
							}

						}

						);
						*/
						$(this).dialog("close");
					}
				},
				beforeClose : function() {
					$("#iframe-outbox-preview").attr("src", "");
				},
				open : function() {			
					$(".ui-button").blur();
				},
				close : function() {
						// 取消发送
						// http://10.254.101.100/ipcom/index.php/Api/fo_cancel
						// 返回 ：{"error":null,"data":"ok"}
						if(flag){
							//跳转到发件箱
							$("#main_frame_right").attr("src",
							"fax_outbox_list.jsp");
							//发件箱按钮高亮
							$("#main_frame_left").contents().find("#btn_outbox").parent().attr("class","btn_outbox_selected");
							$("#main_frame_left").contents().find("#btn_inbox").parent().attr("class","main1_lnbox");
							$("#main_frame_left").contents().find("#btn_send").parent().attr("class","main1_send");
						}else{
							$.post("faxCancel.act", function(result, status) {
								if (result.data == "ok") {
									$("#main_frame_right").attr("src",
											"fax_send.jsp?sid=cancel");
								}
								
							}
							
							);
						}

				},

			});
}
function checkActiveX() {
	var NewObj;
	try {
		NewObj = new ActiveXObject("Alttiff.AlttiffCtl.1");
		var xx = typeof (NewObj);
		// alert(xx);
		if (typeof (NewObj) != 'undefined') {
			NewObj = null;
			// alert("存在111");

		}
	} catch (e) {
		NewObj = null;
		activexMessage(
				"<p>传真预览控件未安装，为保障您顺畅浏览传真，请安装完成后刷新页面。<p></p>点击下载  <a class=\"orange\" style=\"color:red;\" href=\"ca.zip\">根证书</a> 。</p>",
				function() {
					$("#main_frame_right").attr("src", "fax_inbox_list.jsp");

				});
		// alert("不存在222");

	}
}
function frameDialogClose() {

}
/**
 * @description 添加收件人对话框
 * @description 内容设置到收件人文本框
 */
function showAddReceiverBox() {

	// 清空文本框内容

	$("#iframe-add-receiver").contents().find("#fax-number").val("");

	$("#iframe-add-receiver").contents().find("#extension-number").val("");

	$("#dialog-add-receiver").dialog(
			{
				modal : true,
				draggable : true,
				resizable : false,
				closeText : "关闭",
				height : 220,
				width : 465,
				buttons : {

					"确定" : function() {

						var faxSendObj = $("#main_frame_right").contents();
						
						//收件人输入框
						var currentValue = faxSendObj.find("#fo_to").val();
						//隐藏域值
						var hiddenValue = faxSendObj.find("#fax-hidden").val();
						
						var faxReceiverObj = $("#iframe-add-receiver")
								.contents();

						var value = "";

						if (faxReceiverObj.find("#fax-number").val() == "") {

							showMessageBox("传真号码不能为空！");
							return;
						}
						
						if (isNaN(faxReceiverObj.find("#area-code").val())){
							
							showMessageBox("区号只能为数字！");
							faxReceiverObj.find("#area-code").val("");
							return;
							
						}
						if (isNaN(faxReceiverObj.find("#fax-number").val())){
							
							showMessageBox("传真号码只能为数字！");
							faxReceiverObj.find("#fax-number").val("");
							return;
							
						}
						if (isNaN(faxReceiverObj.find("#extension-number").val())){
							
							showMessageBox("分机号只能为数字！");
							faxReceiverObj.find("#extension-number").val("");
							return;
							
						}
						
						/**
						 * 假如有分机号，必须加逗号、句号处理，默认延时5秒播分机号码
						 * 用逗号分隔总机和分机号，可以加多个逗号或句号，每个逗号表示延时2秒,
						 * 句点表示延时5秒比如33685275,,8000.,,表示拨通33685275后，
						 * 延时4秒拨8000这个分机号(发送DTMF)，并且DTMF发完后再延时9秒后认为接通，
						 * 开始发送。具体的延时要根据网关和对方接通时间调整。
						 */
						faxReceiverObj.find("input[type=text]").each(
								function() {

									value += $(this).val();

								});
						//区号
						var areaCode = faxReceiverObj.find("#area-code").val();
						//传真号
						var faxNum = faxReceiverObj.find("#fax-number").val();
						//分机号
						var extNum = faxReceiverObj.find("#extension-number").val();
						//value += ".." + extNum;
						
						//最终显示的号码
						var showNum = (areaCode == "" ? areaCode : areaCode + "-") + faxNum + 
								(extNum == "" ? extNum : "-" + extNum) ;
							
						/**
						 * 假如在个人通讯录里，就显示名字 真实数据保存到fax-hidden
						 */
						$.post("getUserName.act", {
							
							//通讯录里保存的名字格式相符
							faxNumber : showNum
							
						}, function(result) {

							if (result != "UnFound") {
								
								
								//有查询到具体名字
								if (currentValue == "") {

									//收件人(显示)
									faxSendObj.find("#fo_to").val(result + "(" + showNum + ")");
									
									//判断分机号是否为空
									if (extNum == "" || null == extNum){
										//后台接收收件人数据
										faxSendObj.find("#fax-hidden").val(areaCode + faxNum);
									}else{
										//后台接收收件人数据
										faxSendObj.find("#fax-hidden").val(areaCode + faxNum + "." + extNum);
									}

								} else {

									//收件人(显示)
									faxSendObj.find("#fo_to").val(
											currentValue + ";" + result + "(" + showNum + ")");
									
									//是否有分机号
									if (extNum == "" || null == extNum){
										
										faxSendObj.find("#fax-hidden").val(
											hiddenValue + ";" + areaCode + faxNum);
									}else{
										
										faxSendObj.find("#fax-hidden").val(
											hiddenValue + ";" + areaCode + faxNum + "." + extNum);
									}
								}

							} else {
								
								//我的联系人为查询到具体名字
								if (currentValue == "") {
									
									//页面显示收件人信息
									faxSendObj.find("#fo_to").val(showNum);
									
									if (extNum == "" || null == extNum){
										faxSendObj.find("#fax-hidden").val(areaCode + faxNum);
									}else{
										faxSendObj.find("#fax-hidden").val(areaCode + faxNum + "." + extNum);
									}

								} else {
									
									//页面显示收件人信息
									faxSendObj.find("#fo_to").val(
											currentValue + ";" + showNum);
									
									//后台接收收件人数据
									if (extNum == "" || null == extNum){
										faxSendObj.find("#fax-hidden").val(
												hiddenValue + ";" + areaCode + faxNum);
									}else{

										faxSendObj.find("#fax-hidden").val(
												hiddenValue + ";" + areaCode + faxNum + "." + extNum);
									}
								}

							}

						});

						// $("#main_frame_right").contents().find(".main3_from").submit();
						$(this).dialog("close");
					} // end of function
				},
				// end of buttons
				open : function() {			
					$(".ui-button").blur();
				},			
			}); // end of dialog
}

/**
 * @description 删除收件人对话框
 * @description 剩下内容设置到收件人文本框
 */
function delReceiverBox() {
	//当前页面参数
	var receiver =  $("#main_frame_right").contents().find("input[name=fax_receiver]").val();
	var hidden = $("#main_frame_right").contents().find("#fax-hidden").val();
	//console.log("收件人----" + escape(receiver));
	//console.log("隐藏域----" + hidden);
	//参数传过去
	$("#iframe-del-receiver").attr("src","fax_del_receiver.jsp?receiver=" + escape(receiver) + "&hidden=" + hidden);
	//console.log($("#iframe-del-receiver").attr("src"));
	$("#dialog-del-receiver").dialog(
			{
				modal : true,
				draggable : true,
				resizable : false,
				closeText : "关闭",
				height : 320,
				width : 415,
				//width="380px" height="250px"
				buttons : {

					"确定" : function() {

						var faxSendObj = $("#main_frame_right").contents();

						var faxReceiverObj = $("#iframe-del-receiver")
								.contents();

						var value = "";

						faxReceiverObj.find("input[type=text]").each(
								function() {

									value += $(this).val() + ";";

								});
						
						/**
						 * 名字保存到 input id="fo_to"
						 * 真实数据保存到fax-hidden
						 */
						
						var data = faxReceiverObj.find("#data").val();
						
						faxSendObj.find("#fo_to").val(value.substring(0,value.length-1));
						faxSendObj.find("#fax-hidden").val(data);

						$(this).dialog("close");
					} // end of function
				},
				// end of buttons
				open : function() {			
					$(".ui-button").blur();
				},
			}); // end of dialog
	
}

