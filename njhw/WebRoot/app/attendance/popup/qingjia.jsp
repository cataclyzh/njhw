<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>请假申请</title>	
<%@ include file="/common/include/meta.jsp" %>
<style type="text/css">
  .panel-header {
  	background: #fff;
  }
   body {
    font-size: 14px;
    color: #808080;
  }
  
  .main_conter {
  	border: none;
  }
  .show_from .left-td {
    text-align: left;
  }
  .form-table-apply-type, 
  .form-table-apply-accepter,
  .form-apply-begin-date-type,
  .form-apply-end-date-type {
	width: 94%;
	border: none;
	height: 25px;
	background: #eeeff3;
  }
  .show_from {
  	padding-bottom: 10px;
  	border-bottom: 1px solid #7F90B5;
  }
  
  .show_textarea {
  	height: 160px;
  	resize: none;
  }
  
  .show_pop_bottom a{
  	margin-left: 178px;
	background:#8090b2;
  }
  .show_pop_bottom a:hover{
  	margin-left: 178px;
	background:#ffc702;
  }
  
</style>
<script>

	$(function(){
		
		$("#apply_reason").focus();
		
		
		var parentDoc = parent.document.getElementById("main_frame_right1").contentWindow.document,
		    parentWin = parent.document.getElementById("main_frame_right1").contentWindow.window,
		    morningObj = parentDoc.getElementById("apply_morning"),
		    morningDate =  morningObj && morningObj.rel,
		    afternoonObj = parentDoc.getElementById("apply_afternoon"),
		    afternoonDate= afternoonObj && afternoonObj.rel,
		    userId = parentDoc.getElementById("userId").value,
		    userIdParam = parentDoc.getElementById("userIdParam").value, //帮别人申请的id
		    accepters = $.parseJSON(parentDoc.getElementById("accepters").value),
		    $selectedDayPart = $(parentDoc).find('.selected-day-part'),
		    $calendar = parentWin.Commute.getCalendar();
		
		if($selectedDayPart.attr('id') === 'apply_morning'){//默认选择上午
			$('#apply_day_begin_part, #apply_day_end_part').val('0');
		}else{
			$('#apply_day_begin_part, #apply_day_end_part').val('1');
		}
	
		//console.log($(parentDoc).find('.selected-day-part'))
		//console.log("上午请假时间: " + morningDate)
		//console.log("下午请假时间: " + afternoonDate)
        //console.log("审批人: " + accepters)
        //console.log($(morningObj))
		//初始化申请表单值
		if(morningDate && afternoonDate) { //上午下午
			$('#apply_morning_date').val(morningDate);
			$('#apply_afternoon_date').val(afternoonDate);
		}else if(!morningDate && afternoonDate){ //下午
			$('#apply_morning_date').val(afternoonDate);
			$('#apply_afternoon_date').val(afternoonDate);
		}else if(morningDate && !afternoonDate){ //上午
			$('#apply_morning_date').val(morningDate);
			$('#apply_afternoon_date').val(morningDate);
		}
		
		var msg = '请假申请提交成功，请等待审批!';
		if(userIdParam !== ''){//如果不为空，表示考勤管理员帮人申请，审批人是自己
			$('#apply_user_id').val(userIdParam);
			$('#accepter_field').replaceWith('<input name="accepter" id="apply_accepter" value="${_userid}" type="hidden"/>')
			msg = '请假申请成功!';
		}else{
			//动态添加审批人
	        var acceptersLen = accepters.length;
			for(var i = 0; i < acceptersLen; i++) {
				$('#apply_accepter').append('<option value="' + accepters[i].user_id + '">' + accepters[i].username +'</option>');
			}
		}

		$('#send_application').click(function(e){
			e.preventDefault();
			if($('#apply_reason').val() === '') {
				alert('请假事由不能为空!');
			}else if($('#apply_reason').val().length > 200){
				alert('请假事由内容过长!');
			}else if($('#apply_day_begin_part').val() === '1' && $('#apply_day_end_part').val() === '0'){
				alert('开始时间与结束时间范围不正确!');
			}else{
				$.ajax({
					type: 'POST',
					url: '${ctx}/app/attendance/approve.act',
					data: $("#apply_form").serialize(),
					dataType: 'json'
				}).done(function(res){
					if(res.success === 'true'){
						if ($('#apply_accepter').val() === $('#apply_user_id').val()){
							msg = '请假申请成功!';
						}
						alert(msg);
						//重新刷新calendar事件
						$calendar.fullCalendar( 'refetchEvents');						
					}else{
						alert(res.msg);
					}
					parent.jumpPage(1);
					parent.closeEasyWin('qingjia');
				}).fail(function(){
					alert("您的请假申请失败，请联系管理员!");
					parent.jumpPage(1);
					parent.closeEasyWin('qingjia');
				});
			}
		})
		
	});
	
</script>
</head>
<body style="background: #FFF;font-size: 14px; color: #808080;">
  
  <div class="main_left_main1">
	<div class="main_conter">
		<div class="main1_main2_right">
			<form action="TODO.act" method="post" autocomplete="off" id="apply_form">
				<input name="user_id" id="apply_user_id" value="${_userid}" type="hidden"/>
				<div class="show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="15%">开始时间：</td>
							<td width="40%"><input name="begin_date" readonly="readonly" type="text" class="show_inupt" value="" id="apply_morning_date" /></td>
							<td widht="5%"></td>
							<td width="40%" class="left-td">
								<select id="apply_day_begin_part" name="day_begin_part" class="form-apply-begin-date-type valid">
					              <option value="0">上午</option>
					              <option value="1">下午</option>
					            </select>
							</td>
						</tr>
						<tr>
							<td width="15%">结束时间：</td>
							<td width="40%"><input name="end_date"  readonly="readonly" type="text" class="show_inupt" value="" id="apply_afternoon_date" /></td>
							<td widht="5%"></td>
							<td width="40%" class="left-td">
								<select id="apply_day_end_part" name="day_end_part" class="form-apply-end-date-type valid">
					              <option value="0">上午</option>
					              <option value="1" selected="selected">下午</option>
					            </select>
							</td>
						</tr>
						<tr>
							<td width="15%">请假类型：</td>
							<td width="40%" class="left-td">
								<select id="apply_type" class="form-table-apply-type valid" name="type">
						            <option value="3">公出</option>
						            <option value="2">事假</option>
						            <option value="1">病假</option>
						            <option value="4">其它</option>
								</select>
							</td>
						</tr>
						<tr id="accepter_field">
							<td width="15%">审批人：</td>
							<td width="40%" class="left-td">
								<select id="apply_accepter" name="accepter" class="form-table-apply-accepter valid"></select>
							</td>
						</tr>
						<tr>
							<td valign="top">事由：</td>
							<td colspan="3">
							<textarea id="apply_reason" name="reason" style="overflow: auto;" class="show_textarea" maxlength="200">
							</textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="show_pop_bottom">
					<a style="float: left; cursor: pointer;" id="send_application">发送</a>	
				</div>
			</form>
		</div>
		<div class="clear"></div>
	</div>
  </div>
</body>
</html>
  