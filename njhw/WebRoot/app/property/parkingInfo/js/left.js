(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_totalConfigPacking").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/configParking.act";
			$("#btn_totalConfigPacking").parent().attr("class","main1_totalConfigPacking_select");
			$("#btn_viewConfigPacking").parent().attr("class","main1_viewConfigPacking");
			$("#btn_viewConfigPackings").parent().attr("class","main1_viewConfigPacking_selects");
		});
				
		$("#btn_viewConfigPacking").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/parkingList.act";
			$("#btn_totalConfigPacking").parent().attr("class","main1_totalConfigPacking");
			$("#btn_viewConfigPacking").parent().attr("class","main1_viewConfigPacking_select");
			$("#btn_viewConfigPackings").parent().attr("class","main1_viewConfigPacking_selects");
		});
		
		$("#btn_viewConfigPackings").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/configParkings.act";
			$("#btn_viewConfigPackings").parent().attr("class","main1_totalConfigPacking_selects");
			$("#btn_viewConfigPacking").parent().attr("class","main1_viewConfigPacking");
			$("#btn_totalConfigPacking").parent().attr("class","main1_totalConfigPacking");
		});
		
	});
}()); 
