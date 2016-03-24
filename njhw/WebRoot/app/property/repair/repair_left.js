(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_addRepair").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/toAddRepair.act";
			$("#btn_addRepair").parent().attr("class","main1_addRepair_select");
			$("#btn_viewRepair").parent().attr("class","main1_viewRepair");
			$("#btn_repairCost").parent().attr("class","main1_repairCost");
		});
				
		$("#btn_viewRepair").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/repairList.act";
			$("#btn_addRepair").parent().attr("class","main1_addRepair");
			$("#btn_viewRepair").parent().attr("class","main1_viewRepair_select");
			$("#btn_repairCost").parent().attr("class","main1_repairCost");
		});
				
		$("#btn_repairCost").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/repairCostList.act";
			$("#btn_addRepair").parent().attr("class","main1_addRepair");
			$("#btn_viewRepair").parent().attr("class","main1_viewRepair");
			$("#btn_repairCost").parent().attr("class","main1_repairCost_select");
		});
				
	});
}()); // $(function()) 结束
