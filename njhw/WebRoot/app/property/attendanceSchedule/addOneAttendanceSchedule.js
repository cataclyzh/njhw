$(function() {
	$("#receiver").focus();

	// changebasiccomponentstyle();
	// 控制父页面高度
	selectRecieveid2();
	/*
	$("#main_frame_left", window.parent.document).height(
			$(".main_conter").height() + 100);
	$("#main_frame_right", window.parent.document).height(
			$(".main_conter").height() + 100);
			*/
});

function formSubmit() {
		$('#addform').attr('action', 'addOneAttendanceSchedule.act');
		$('#addform').submit();

	// $("#addform").validate(options);
	// $('#addform').attr('action', 'msgBoxAction_save.act');
	// $('#addform').submit();
}

function selectRecieveid2() {
	var url = "../../app/pro/attendanceOrgTreeSelect.act";
	/*
	 * $("#companyWin").show(); $("#companyWin").window({ title : '请选择接收人',
	 * modal : true, shadow : false, closed : false, width : 450, height : 430,
	 * top : 10, left : 30, });
	 */
	$("#iframe-contact2").attr("src", url);

}
