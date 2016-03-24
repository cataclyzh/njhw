(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	var btn_inbox = false;
	var btn_send = false;
	var btn_outbox = false;
	$(function($) {
		$("#btn_inbox").click(function() {
			window.parent.frames["main_frame_right"].location.replace("fax_inbox_list.jsp");
			$("#btn_inbox").parent().attr("class","btn_inbox_selected");
			$("#btn_send").parent().attr("class","main1_send");
			$("#btn_outbox").parent().attr("class","main1_been");
		});
			
		$("#btn_send").click(function() {
			window.parent.frames["main_frame_right"].location.replace("fax_send.jsp");
			$("#btn_inbox").parent().attr("class","main1_lnbox");
			$("#btn_send").parent().attr("class","btn_send_selected");
			$("#btn_outbox").parent().attr("class","main1_been");
		});
				
		$("#btn_outbox").click(function() {
			window.parent.frames["main_frame_right"].location.replace("fax_outbox_list.jsp");
			$("#btn_inbox").parent().attr("class","main1_lnbox");
			$("#btn_send").parent().attr("class","main1_send");
			$("#btn_outbox").parent().attr("class","btn_outbox_selected");
		});
				
	});
}()); // $(function()) 结束
