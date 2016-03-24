<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>考勤排班</title>
<%@ include file="/common/include/meta.jsp"%>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />

<style type="text/css">
textarea {
	width: 100%;
	font-size: medium;
}

input[type="text"] {
	height: 22px;
	width: 100%;
	font-size: medium;
	font-weight: normal;
}

.main_from_left{
	width:13%;
	text-align:right;
	padding-right:10px;
	font-size:14px;
	color:#999999;
	height:30px;
	line-height:30px;
}


#submitbutton {
	cursor: pointer;
}

#message_form label.error {
	color: red;
	font-size: small;
}
</style>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/app/property/attendanceSchedule/addOneAttendanceSchedule.js" type="text/javascript"></script>
<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<script type="text/javascript">

function addOneAttendanceSchedule() {
	$("#addform").submit();
}
var isExistCheckFlag = false;

$(document).ready(
	function() {
		jQuery.focusblur = function(focusid) {
			var focusblurid = $(focusid);
			//var scheduleOutTime = $("#add_attendanceScheduleOutTime_dayEnd").val()+" "+$("#endTime0").val();
			focusblurid.blur(function() {
				var scheduleInTime = $("#add_attendanceScheduleInTime_dayStart").val();
				var scheduleHourEnd = $("#endTime0").val();
				var scheduleOutTime = $("#add_attendanceScheduleOutTime_dayEnd").val();
				var scheduleTreeUserId = $("#treeUserId").val();
				if (scheduleInTime != null
						&& "" != scheduleInTime
						&& scheduleOutTime != null
						&& "" != scheduleOutTime
						&& scheduleHourEnd != null
						&& "" != scheduleHourEnd
						&& scheduleTreeUserId != null
						&& "" != scheduleTreeUserId) {
					
					isExistCheckFlag = true;
					
					/*
					$.ajax({
						type : "POST",
						url : "${ctx}/app/pro/queryNewAttendanceScheduleIsExist.act",
						data : {
							scheduleInTime : scheduleInTime,
							scheduleOutTime : scheduleOutTime,
							scheduleTreeUserId : scheduleTreeUserId
						},
						dataType : 'text',
						async : false,
						success : function(responseText) {
									if (responseText == 'fail') {
										easyAlert("提示信息",
													"被排班人中有人在该排班时间段已有排班！",
													"info",
													function() {
														isExistCheckFlag = false;
														window.location.reload();
													});
									} else if (responseText == 'success') {
										isExistCheckFlag = true;
									}
								}
						});*/

				}
			});
		};

		var dayFlag = "";
		jQuery.validator.methods.compareDate = function(value, element, param) {
			var beginDay = jQuery(param).val();
			if (beginDay < value) {
				dayFlag = "1";
				return true;
			} else if (beginDay == value) {
				dayFlag = "2";
				return true;
			} else {
				dayFlag = "";
				return false;
			}
		};
		jQuery.validator.methods.compareNowDate = function(
				value, element, param) {
			return value >= "${nowDate}";
		};

		jQuery.validator.methods.compareNowTime = function(
				value, element, param) {
			var myDay = jQuery(param).val();
			var myTime = myDay + " " + value;
			var ss = "${nowTime}";
			return myTime >= "${nowTime}";
		};
		jQuery.validator.methods.compareTime = function(value,
				element, param) {
			var beginTime = jQuery(param).val();
			if (dayFlag == "") {
				return false;
			} else if (dayFlag == "1") {
				if (beginTime != value) {
					return true;
				} else {
					return false;
				}
			} else if (dayFlag == "2") {
				if (beginTime < value) {
					return true;
				} else if (beginTime > value) {
					return false;
				} else if (beginTime == value) {
					return false;
				}
			} else {
				return false;

			}

		};

		$.focusblur("#endTime0");

		$("#addform").validate({ // 为inputForm注册validate函数
			meta : "validate", // 采用meta String方式进行验证（验证内容与写入class中）
			errorElement : "div", // 使用"div"标签标记错误， 默认:"label"
			rules : {
				receiver : {
					required : true
				},
				add_attendanceScheduleInTime_dayStart : {
					required : true,
					compareNowDate : true
				},
				add_attendanceScheduleOutTime_dayEnd : {
					required : true,
					compareDate : "#add_attendanceScheduleInTime_dayStart"

				},

				startTime0 : {
					required : true,
				//compareNowTime:"#add_attendanceScheduleInTime_dayStart"
				},

				endTime0 : {
					required : true,
				//compareTime:"#startTime0"
				},

				add_attendanceScheduleRemark : {
					maxlength : 255
				}

			},
			messages : {
				receiver : {
					required : "请选择被排班人"
				},
				add_attendanceScheduleInTime_dayStart : {
					required : "请填写排班起始日期",
					compareNowDate : "开始日期必须不能早于当前日期"
				},
				add_attendanceScheduleOutTime_dayEnd : {
					required : "请填写排班结束日期",
					compareDate : "结束日期必须晚于开始日期"
				},
				startTime0 : {
					required : "请填写上班开始时间",
				//compareNowTime:"排班时间组合的结果中排班开始时间要晚于当前时间"
				},
				endTime0 : {
					required : "请填写上班结束时间",
				//compareTime:"上班结束时间要晚于开始时间"
				},
				add_attendanceScheduleRemark : {
					maxlength : "填写的备注字符过长"
				}
			}
		});

		//解决回车问题
		$("input").keydown(function(e) {
			if ($(this).attr("readonly") == 'readonly') {//readonly
				e.preventDefault();
			}
		});
	});

	function saveData() {
		var flag = isAddTime();

		/*
		if ("1" == flag) {
			easyAlert('提示信息', '夜班：请将排班日期开始时间设置小于结束时间!', 'info', function() {
				return;
			});
		} else if ("2" == flag) {
			easyAlert('提示信息', '白班：请将排班上班开始时间设置小于结束时间!', 'info', function() {
				return;
			});
		} else {
		*/
			if (showRequest()) {
				//if (isExistCheckFlag) {
					$('#addform').submit();
					window.parent.frames["main_frame_left"].$(
							'#btn_viewAttendanceSchedule').parent().attr(
							"class", "main1_viewAttendanceSchedule_select");
					window.parent.frames["main_frame_left"].$(
							'#btn_addAttendanceSchedule').parent().attr(
							"class", "main1_addAttendanceSchedule");
					window.parent.frames["main_frame_left"].$(
							'#btn_viewAttendance').parent().attr("class",
							"main1_viewAttendance");
				//} else {
				//	alert("请检查上班结束时间！");
				//}
			}
		//}
	}

	function isAddTime() {
		if ($("#startTime0").val() != ""
				&& $("#endTime0").val() != ""
				&& $("#add_attendanceScheduleInTime_dayStart").val() != ""
				&& $("#add_attendanceScheduleOutTime_dayEnd").val() != "") {
			if ($("#startTime0").val() > $(
					"#endTime0").val()) {
				if ($("#add_attendanceScheduleInTime_dayStart").val() >= $(
						"#add_attendanceScheduleOutTime_dayEnd").val()) {
					return 1;
				}
			} else if ($("#add_attendanceScheduleInTime_dayStart").val() <= $(
					"#add_attendanceScheduleOutTime_dayEnd").val()) {
				if ($("#startTime0").val() >= $(
						"#endTime0").val()) {
					return 2;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	function showRequest() {
		return $("#addform").valid();
	}
	function clearInput() {
		document.getElementById("receiver").value = "";
		document.getElementById("add_attendanceScheduleInTime_dayStart").value = "";
		document.getElementById("add_attendanceScheduleOutTime_dayEnd").value = "";
		document.getElementById("startTime0").value = "";
		document.getElementById("endTime0").value = "";
		document.getElementById("add_attendanceScheduleRemark").value = "";
	}

</script>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"
	style="background: white;">

	<div class="main_border_01">
		<div class="main_border_02">考勤排班</div>
	</div>
	<div class="main_conter">

		<div class="main3_left_01" style="background: #F6F5F1">
			<form id="addform" action="addOneAttendanceSchedule.act"
				method="post" autocomplete="off" enctype="multipart/form-data">
				<input type="hidden" name="treeUserId" id="treeUserId"
					value="${receivers}" />
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="main_from_left"><font color="red">*</font>被排班人</td>
						<td><s:textfield name="receiver" id="receiver" theme="simple"
								value="%{#request.receivers}" cssClass="main_input" size="70"
								readonly="true" cssStyle="width:98%;" /></td>
					</tr>
					<tr>
						<table>
							<tr>
								<td class="main_from_left"><font color="red">*</font>排班日期</td>
								<td><input type="text" style="width: 267px"
									name="add_attendanceScheduleInTime_dayStart"
									id="add_attendanceScheduleInTime_dayStart"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value=""
									maxlength="50" /><span style="color: #808080;">&nbsp;至</span></td>
								<td><input type="text" style="width: 267px"
									name="add_attendanceScheduleOutTime_dayEnd"
									id="add_attendanceScheduleOutTime_dayEnd"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value=""
									maxlength="50" /></td>
							</tr>
							<tr>
								<td class="main_from_left">日期类型</td>
								<td colspan="2">
									<input type="radio" name="datetype" value="none">不区分单双号</input>
									<input type="radio" name="datetype" value="single">排单号班</input>
									<input type="radio" name="datetype" value="double">排双号班</input>
								</td>
								
							</tr>
						</table>
					</tr>
					<tr>
						<table>
							<tr>
								<td class="main_from_left"><font color="red">*</font>上班时间1</td>
								<td><input type="text" style="width: 267px"
									name="startTime0"
									id="startTime0"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /><span style="color: #808080;">&nbsp;至</span></td>
								<td><input type="text" style="width: 267px"
									name="endTime0"
									id="endTime0"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /></td>
							</tr>
							<tr>	
								<td class="main_from_left"><font color="red"></font>上班时间2</td>
								<td><input type="text" style="width: 267px"
									name="startTime1"
									id="startTime1"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /><span style="color: #808080;">&nbsp;至</span></td>
								<td><input type="text" style="width: 267px"
									name="endTime1"
									id="endTime1"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /></td>
							</tr>
							<tr>		
								<td class="main_from_left"><font color="red"></font>上班时间3</td>
								<td><input type="text" style="width: 267px"
									name="startTime2"
									id="startTime2"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /><span style="color: #808080;">&nbsp;至</span></td>
								<td><input type="text" style="width: 267px"
									name="endTime2"
									id="endTime2"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /></td>
							</tr>
							<tr>	
								<td class="main_from_left"><font color="red"></font>上班时间4</td>
								<td><input type="text" style="width: 267px"
									name="startTime3"
									id="startTime3"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /><span style="color: #808080;">&nbsp;至</span></td>
								<td><input type="text" style="width: 267px"
									name="endTime3"
									id="endTime3"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /></td>
							</tr>
							<tr>	
								<td class="main_from_left"><font color="red"></font>上班时间5</td>
								<td><input type="text" style="width: 267px"
									name="startTime4"
									id="startTime4"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /><span style="color: #808080;">&nbsp;至</span></td>
								<td><input type="text" style="width: 267px"
									name="endTime4"
									id="endTime4"
									onclick="WdatePicker({dateFmt:'HH:mm:ss'})" value=""
									maxlength="50" /></td>
							</tr>

						</table>


					</tr>

					<tr>
						<td valign="top" class="main_from_left"><span
							style="color: #808080;">排班备注</span></td>
						<td style="padding-top: 5px; padding-right: 50px;">
						<s:textarea
								name="add_attendanceScheduleRemark"
								id="add_attendanceScheduleRemark" theme="simple"
								style="width:660px;height:205px;border:1px solid #CCC;" />
						</td>
					</tr>





				</table>

				<!-- 
								<div class="energy_fkdj_botton_ls" style="margin-right:10px;">
					<a class="fkdj_botton_left"  onclick="javascript:saveData()">提交</a>　
			<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>
					

</div>
				
				 -->
				<div class="energy_fkdj_botton_ls" style="margin-right: 10px;">
					<a class="fkdj_botton_right" onclick="javascript:saveData()">提交</a>
					<a class="fkdj_botton_left" onclick="javascript:clearInput()"
						style="cursor: pointer">重置</a>



				</div>



			</form>

		</div>
		<div style="width: 28%; float: left; margin-right: 10px; height: 100%">
			<div class="clear"></div>
			<iframe id='iframe-contact2' name='iframe-contact2' width="100%"
				height="505px" scrolling="no" frameborder="0" src=""></iframe>


		</div>
		<div class="clear"></div>
	</div>


	<%-- <script type="text/javascript"
		src="${ctx}/common/pages/message/js/jquery.validate.js"></script> --%>
	<script type="text/javascript"
		src="${ctx}/common/pages/message/js/messageInput.js"></script>
</body>
</html>
