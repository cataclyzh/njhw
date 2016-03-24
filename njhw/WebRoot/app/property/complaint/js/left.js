(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_viewComplaint").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/showComplaints.act";
			$("#btn_viewComplaint").parent().attr("class","main1_viewComplaint_select");
			$("#btn_addComplaint").parent().attr("class","main1_addComplaint");
		});
				
		$("#btn_addComplaint").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/addComplaints.act";
			$("#btn_viewComplaint").parent().attr("class","main1_viewComplaint");
			$("#btn_addComplaint").parent().attr("class","main1_addComplaint_select");
		});
	});
}()); 
