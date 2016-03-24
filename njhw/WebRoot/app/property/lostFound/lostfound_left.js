(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_addLostFound").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/addOneLostFoundPrepare.act";
			$("#btn_addLostFound").parent().attr("class","main1_addLostFound_select");
			$("#btn_viewLostFound").parent().attr("class","main1_viewLostFound");
		});
				
		$("#btn_viewLostFound").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/showLostFoundsList.act";
			$("#btn_addLostFound").parent().attr("class","main1_addLostFound");
			$("#btn_viewLostFound").parent().attr("class","main1_viewLostFound_select");
		});
	});
}()); // $(function()) 结束
