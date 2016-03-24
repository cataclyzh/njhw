(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_viewPatrolLine").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/patrolLineList.act";
			$("#btn_viewPatrolLine").parent().attr("class","main1_viewPatrolLine_select");
			$("#btn_viewPatrolSchedule").parent().attr("class","main1_viewPatrolSchedule");
			$("#btn_viewPositionCard").parent().attr("class","main1_viewPositionCard");
			$("#btn_viewPatrolException").parent().attr("class","main1_viewPatrolException");
			$("#btn_patrolStick").parent().attr("class","main1_viewPatrolStick");
			$("#btn_histroryLine").parent().attr("class","main1_viewHistory");
			$("#btn_getPatrolStickRecord").parent().attr("class","main1_viewHistory");
		});
				
		$("#btn_viewPatrolSchedule").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/patrolScheduleList.act";
			$("#btn_viewPatrolLine").parent().attr("class","main1_viewPatrolLine");
			$("#btn_viewPatrolSchedule").parent().attr("class","main1_viewPatrolSchedule_select");
			$("#btn_viewPositionCard").parent().attr("class","main1_viewPositionCard");
			$("#btn_viewPatrolException").parent().attr("class","main1_viewPatrolException");
			$("#btn_patrolStick").parent().attr("class","main1_viewPatrolStick");
			$("#btn_histroryLine").parent().attr("class","main1_viewHistory");
			$("#btn_getPatrolStickRecord").parent().attr("class","main1_viewHistory");
		});
				
		$("#btn_viewPositionCard").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/positionCardList.act";
			$("#btn_viewPatrolLine").parent().attr("class","main1_viewPatrolLine");
			$("#btn_viewPatrolSchedule").parent().attr("class","main1_viewPatrolSchedule");
			$("#btn_viewPositionCard").parent().attr("class","main1_viewPositionCard_select");
			$("#btn_viewPatrolException").parent().attr("class","main1_viewPatrolException");
			$("#btn_patrolStick").parent().attr("class","main1_viewPatrolStick");
			$("#btn_histroryLine").parent().attr("class","main1_viewHistory");
			$("#btn_getPatrolStickRecord").parent().attr("class","main1_viewHistory");
		});

		$("#btn_patrolStick").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/queryPatrolStickList.act";
			$("#btn_viewPatrolLine").parent().attr("class","main1_viewPatrolLine");
			$("#btn_viewPatrolSchedule").parent().attr("class","main1_viewPatrolSchedule");
			$("#btn_viewPositionCard").parent().attr("class","main1_viewPositionCard");
			$("#btn_viewPatrolException").parent().attr("class","main1_viewPatrolException");
			$("#btn_patrolStick").parent().attr("class","main1_viewPatrolStick_select");
			$("#btn_histroryLine").parent().attr("class","main1_viewHistory");
			$("#btn_getPatrolStickRecord").parent().attr("class","main1_viewHistory");
		});
		
		$("#btn_histroryLine").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/historyLine.act";
			$("#btn_viewPatrolLine").parent().attr("class","main1_viewPatrolLine");
			$("#btn_viewPatrolSchedule").parent().attr("class","main1_viewPatrolSchedule");
			$("#btn_viewPositionCard").parent().attr("class","main1_viewPositionCard");
			$("#btn_viewPatrolException").parent().attr("class","main1_viewPatrolException");
			$("#btn_histroryLine").parent().attr("class","main1_viewHistory_select");
			$("#btn_patrolStick").parent().attr("class","main1_viewPatrolStick");
			$("#btn_getPatrolStickRecord").parent().attr("class","main1_viewHistory");
		});
		
		$("#btn_getPatrolStickRecord").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/getPatrolStickRecord.act";
			$("#btn_viewPatrolLine").parent().attr("class","main1_viewPatrolLine");
			$("#btn_viewPatrolSchedule").parent().attr("class","main1_viewPatrolSchedule");
			$("#btn_viewPositionCard").parent().attr("class","main1_viewPositionCard");
			$("#btn_viewPatrolException").parent().attr("class","main1_viewPatrolException");
			$("#btn_histroryLine").parent().attr("class","main1_viewHistory");
			$("#btn_patrolStick").parent().attr("class","main1_viewPatrolStick");
			$("#btn_getPatrolStickRecord").parent().attr("class","main1_viewHistory_select");
		});
		
		$("#btn_viewPatrolException").click(function() {
			var url = "../../3d/propertySearch.act";
			window.open (url, "newwindow");
		});
				
	});
}()); // $(function()) 结束
