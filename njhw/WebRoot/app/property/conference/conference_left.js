(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_addconference").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/addOnePrepare.act";
			$("#btn_addconference").parent().attr("class","main1_addconference_select");
			$("#btn_viewconference").parent().attr("class","main1_viewconference");
		});
				
		$("#btn_viewconference").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/showMyConferencesList.act";
			$("#btn_addconference").parent().attr("class","main1_addconference");
			$("#btn_viewconference").parent().attr("class","main1_viewconference_select");
		});
				
	});
}()); // $(function()) 结束
