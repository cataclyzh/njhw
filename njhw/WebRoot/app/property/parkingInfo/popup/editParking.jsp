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
<title>编辑车牌信息</title>	
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
  	height: 90px;
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
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script>

	$(function(){
		var parentDoc = parent.document.getElementById("main_frame_right").contentWindow.document,
		    parentWin = parent.document.getElementById("main_frame_right").contentWindow.window,
		    org_name = parentDoc.getElementById("org_name_hidden").value,
		    department = parentDoc.getElementById("department_hidden").value,
		    car_owner_name = parentDoc.getElementById("car_owner_name_hidden").value,
		    car_owner_phone = parentDoc.getElementById("car_owner_phone_hidden").value,
		    car_no = parentDoc.getElementById("car_no_hidden").value,
		    start_date_and_end_date = parentDoc.getElementById("start_date_and_end_date_hidden").value.split('~'),
		    start_time_and_end_time = parentDoc.getElementById("start_time_and_end_time_hidden").value,
		    status = parentDoc.getElementById("status_hidden").value,
		    car_channel_info = parentDoc.getElementById("car_channel_info_hidden").value;
		
		$('#org_name').val(org_name);
		$('#department').val(department);
		$('#car_owner_name').val(car_owner_name);
		$('#car_no').val(car_no);
		$('#car_no_hidden').val(car_no);
		$('#car_owner_phone').val(car_owner_phone);
		$('#start_date_and_end_date_from').val(start_date_and_end_date[0]);
		$('#start_date_and_end_date_to').val(start_date_and_end_date[1]);
		$('#car_channel_info').val(car_channel_info);
		$('#start_time_and_end_time').val(start_time_and_end_time);
		
		$('.datepicker').click(function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd'});
		});
		
		if(status == '0'){//审核通过不能修改了
	      $('#status').replaceWith('<input readonly="readonly" type="text" class="show_inupt" value="审核通过" />');
		}else{
	      $('#status').val(status);
		}
		
		$('#update_parking').click(function(e){
			e.preventDefault();
			//续费和转让修改$('#car_no_new_hidden')，其他为NO
			//转让
			
			if(car_no != $('#car_no').val()){
			  $('#car_no_new_hidden').val($('#car_no').val());
			}
			
			//续费
			if($('#start_date_and_end_date_from').val() != start_date_and_end_date[0] || $('#start_date_and_end_date_to').val() != start_date_and_end_date[1]){
			  $('#car_no_new_hidden').val(car_no);
			}
			if($('#start_date_and_end_date_from').val() === '' || $('#start_date_and_end_date_to').val() === '') {
				alert('有效期不能为空!');
			}else{
				var params = $("#parking_form").serialize();
				if(status == '0'){//审核通过不能修改了
					params += "&STATUS=0";
				}
				
				//alert(params)
				
				$.ajax({
					type: 'POST',
					url: '/park/setParkLicense', //'http://localhost:3000/parkings/modify', //'${ctx}/app/attendance/approve.act',
					data: params,
					dataType: 'json'
				}).done(function(res){
					parentWin.gParkingsTable.draw();
					if(res.stat === 'success'){
						alert('设置车牌信息成功');					
					}else{
						alert(res.content);
					}
					parent.jumpPage(1);
					parent.closeEasyWin('edit_parking');
				}).fail(function(){
					alert("设置车牌信息失败，请联系管理员!");
					parent.jumpPage(1);
					parent.closeEasyWin('edit_parking');
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
			
				<input name="user_id" id="apply_user_id" value="${_userid}" type="hidden"/>
				<div class="show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="10%">单位：</td>
							<td width="40%"><input name="ORG_NAME" readonly="readonly" type="text" class="show_inupt" value="" id="org_name" /></td>
							<td width="10%">处室：</td>
							<td width="40%" class="left-td">
								<input name="DEPARTMENT" readonly="readonly" type="text" class="show_inupt" value="" id="department" />
							</td>
						</tr>
						<tr>
							<td width="10%">姓名：</td>
							<td width="40%"><input name="CAR_OWNER_NAME" readonly="readonly" type="text" class="show_inupt" value="" id="car_owner_name" /></td>
							<td width="10%">车牌号：</td>
							<td width="40%" class="left-td">
								<input name="CAR_NO" type="text" class="show_inupt" value="" id="car_no" />
							</td>
						</tr>
						<form action="TODO.act" method="post" autocomplete="off" id="parking_form">
						<input name="CAR_NO" type="hidden" value="" id="car_no_hidden" />
						<input name="CAR_NEW_NO" type="hidden" value='NO' id="car_no_new_hidden" />
						<tr>
							<td width="10%">电话：</td>
							<td width="40%"><input disabled="disabled" name="CAR_OWNER_PHONE" readonly="readonly" type="text" class="show_inupt" value="" id="car_owner_phone" /></td>
							<td width="10%">状态：</td>
							<td width="40%" class="left-td">
								<select id="status" class="form-table-apply-type valid" name="STATUS">
						            <option value="0">审核通过</option>
						            <option value="1">等待审核</option>
						            <option value="2">审核拒绝</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="10%">有效期：</td>
							<td width="40%"><input name="START_DATE" type="text" class="show_inupt datepicker" value="" id="start_date_and_end_date_from" /></td>
							<td width="10%">至：</td>
							<td width="40%" class="left-td">
								<input name="END_DATE" type="text" class="show_inupt datepicker" value="" id="start_date_and_end_date_to" />
							</td>
						</tr>
						</form>
						<tr>
							<td width="10%">通道：</td>
							<td width="40%">
								<textarea readonly="readonly" id="car_channel_info" name="CAR_CHANNEL_INFO" style="overflow: auto; width: 88%;" class="show_textarea" maxlength="200">
								</textarea>
							</td>
							<td width="10%">时间段：</td>
							<td width="40%" class="left-td">
							<textarea readonly="readonly" id="start_time_and_end_time" name="START_TIME_AND_END_TIME" style="overflow: auto; width: 88%;" class="show_textarea" maxlength="200">
							</textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="show_pop_bottom">
					<a style="float: left; cursor: pointer;" id="update_parking">更新</a>	
				</div>

		</div>
		<div class="clear"></div>
	</div>
  </div>
</body>
</html>
  