(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_viewAttendanceSchedule").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/showAttendanceSchedulesList.act";
			$("#btn_viewAttendanceSchedule").parent().attr("class","main1_viewAttendanceSchedule_select");
			$("#btn_addAttendanceSchedule").parent().attr("class","main1_addAttendanceSchedule");
			$("#btn_viewAttendance").parent().attr("class","main1_viewAttendance");
		});
						
		$("#btn_addAttendanceSchedule").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/addOneAttendanceSchedulePrepare.act";
			$("#btn_viewAttendanceSchedule").parent().attr("class","main1_viewAttendanceSchedule");
			$("#btn_addAttendanceSchedule").parent().attr("class","main1_addAttendanceSchedule_select");
			$("#btn_viewAttendance").parent().attr("class","main1_viewAttendance");
		});
		
		$("#btn_viewAttendance").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/showAttendancesList.act";
			$("#btn_viewAttendanceSchedule").parent().attr("class","main1_viewAttendanceSchedule");
			$("#btn_addAttendanceSchedule").parent().attr("class","main1_addAttendanceSchedule");
			$("#btn_viewAttendance").parent().attr("class","main1_viewAttendance_select");
		});
	});
}()); // $(function()) 结束
