(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_inbox").click(function() {
			window.parent.frames["main_frame_right"].location.replace("../../bulletinMessage/msgBoxAction_queryReceiverbox.act");
		});
				
		$("#btn_send").click(function() {
			window.parent.frames["main_frame_right"].location.replace("../../bulletinMessage/msgBoxAction_init.act");
			$("#btn_send").parent().attr("class","message_send_selected");
			$("#btn_outbox").parent().attr("class","message_outbox");
		});
				
		$("#btn_outbox").click(function() {
			window.parent.frames["main_frame_right"].location.replace("../../bulletinMessage/msgBoxAction_querySenderbox.act");
			$("#btn_send").parent().attr("class","message_send");
			$("#btn_outbox").parent().attr("class","message_outbox_selected");
		});
				
	});
}()); // $(function()) 结束
