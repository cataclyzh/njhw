(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_addInfo").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../bulletinMessage/msgBoardAction_init.act";
			$("#btn_addInfo").parent().attr("class","main1_addInfo_select");
			$("#btn_viewInfo").parent().attr("class","main1_viewInfo");
		});
				
		$("#btn_viewInfo").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../bulletinMessage/msgBoardAction_save.act";
			$("#btn_addInfo").parent().attr("class","main1_addInfo");
			$("#btn_viewInfo").parent().attr("class","main1_viewInfo_select");
		});
	});
}()); 
