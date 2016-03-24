(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_addconference").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/addPrepare.act";
			$("#btn_addconference").parent().attr("class","main1_addconference_select");
			$("#btn_viewconference").parent().attr("class","main1_viewconference");
			$("#btn_addConferencePackage").parent().attr("class","main1_addConferencePackage");
			$("#btn_viewConferencePackage").parent().attr("class","main1_viewConferencePackage");
		});
				
		$("#btn_viewconference").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/showConferencesList.act";
			$("#btn_addconference").parent().attr("class","main1_addconference");
			$("#btn_viewconference").parent().attr("class","main1_viewconference_select");
			$("#btn_addConferencePackage").parent().attr("class","main1_addConferencePackage");
			$("#btn_viewConferencePackage").parent().attr("class","main1_viewConferencePackage");
		});
		
		$("#btn_addConferencePackage").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/addOneConferencePackagePrepare.act";
			$("#btn_addconference").parent().attr("class","main1_addconference");
			$("#btn_viewconference").parent().attr("class","main1_viewconference");
			$("#btn_addConferencePackage").parent().attr("class","main1_addConferencePackage_select");
			$("#btn_viewConferencePackage").parent().attr("class","main1_viewConferencePackage");
		});
		
		$("#btn_viewConferencePackage").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/showConferencePackagesList.act";
			$("#btn_addconference").parent().attr("class","main1_addconference");
			$("#btn_viewconference").parent().attr("class","main1_viewconference");
			$("#btn_addConferencePackage").parent().attr("class","main1_addConferencePackage");
			$("#btn_viewConferencePackage").parent().attr("class","main1_viewConferencePackage_select");
		});
				
	});
}()); // $(function()) 结束
