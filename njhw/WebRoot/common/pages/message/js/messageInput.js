
$(function() {
	//$("#send").focus();
	
	selectRecieveid();
	// changebasiccomponentstyle();
	// 控制父页面高度
	
	
	$("#main_frame_left", window.parent.document).height(
			$(".main_conter").height() + 100);
	$("#main_frame_right", window.parent.document).height(
			$(".main_conter").height() + 100);
			
});
var options = {
	onkeyup : false,
	rules : {
		title : {
			maxlength : 300,
			required : true,
		},
		content : {
			maxlength : 300,
			required : true,
		},
		receiver : {
			required : true
		/**
		 * , remote: { url: 'msgBoxAction_checkReciver.act', data: { receiver:
		 * function() { return $("#receiver").val(); } } }
		 */
		}
	},
	messages : {
		title : {
			maxlength : " 标题最多支持300个字符",
			required : " 请输入标题",

		},
		content : {
			maxlength : " 内容最多支持300个字符",
			required : " 请输入内容"
		},
		receiver : {
			required : " 请填写收件人信息"
		/***********************************************************************
		 * , remote: " 您所在的机构不存在此收件人, 请确认."
		 **********************************************************************/
		}
	}
}
function formSubmit() {

	if ($.trim($("#title").val()) == "") {
		window.parent.showMessageBox("<p>请输入标题</p>");
	} else if ($.trim($("#content").val()) == "") {
		window.parent.showMessageBox("<p>请输入内容</p>");
	} else if ($.trim($("#receiver").val()) == "") {
		window.parent.showMessageBox("<p>请填写收件人信息</p>");
	} else if ($.trim($("#content").val()).length > 300) {
		window.parent.showMessageBox("<p>内容最多支持300个字符</p>");
	} else {
		$('#message_form').attr('action', 'msgBoxAction_save.act');
		/*
		openAdminConfirmWin(function() {
			$('#message_form').submit();
			$("#main_frame_left", window.parent.document).contents().find("#btn_send").parent().attr("class","message_send");
			$("#main_frame_left", window.parent.document).contents().find("#btn_outbox").parent().attr("class","message_outbox_selected");
	
		});*/

		$('#message_form').submit();
		$("#main_frame_left", window.parent.document).contents().find("#btn_send").parent().attr("class","message_send");
		$("#main_frame_left", window.parent.document).contents().find("#btn_outbox").parent().attr("class","message_outbox_selected");

	}
	// $("#message_form").validate(options);
	// $('#message_form').attr('action', 'msgBoxAction_save.act');
	// $('#message_form').submit();
}
function upFile() {
	$("#message_form").validate(options);
	$("#title").rules("remove");
	$("#content").rules("remove");
	$("#receiver").rules("remove");
	$('#message_form').attr('action', 'msgBoxAction_uploadVendorsFiles.act');
	$('#message_form').submit();
}
function selectRecieveid() {
	var url = "../../app/per/orgUserSelectToMess.act?treeUserId="
			+ $("#treeUserId").val();
	/*
	 * $("#companyWin").show(); $("#companyWin").window({ title : '请选择接收人',
	 * modal : true, shadow : false, closed : false, width : 450, height : 430,
	 * top : 10, left : 30, });
	 */
	$("#iframe-contact").attr("src", url);

}
