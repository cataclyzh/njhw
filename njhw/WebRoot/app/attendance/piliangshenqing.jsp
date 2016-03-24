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
<%@ include file="/common/include/meta.jsp"%>
<link href='css/fullcalendar.css' rel='stylesheet' />
<link href='css/fullcalendar.print.css' rel='stylesheet' media='print' />
<style type="text/css">
  
  body {
  	background: transparent;
    font-size: 14px;
    color: #808080;
    font-family: 'Microsoft YaHei', '微软雅黑','HelveticaNeue', Arial, Helvetica, sans-serif
  }
  
  .bgsgl_border_right {
    font-family: 'Microsoft YaHei', '微软雅黑','HelveticaNeue', Arial, Helvetica, sans-serif
  }

  #apply_many {
    display: none;
  	width: 580px;
  	height: 400px;
  	margin: 0 auto;
  	background: #F6F5F1;
  	padding-top: 10px;
  }
  
  #apply_many .show_inupt {
  	width: 95%;
  }
  
  .form-table-apply-type, 
  .form-table-apply-accepter,
  .form-apply-begin-date-type,
  .form-apply-end-date-type {
	width: 100%;
	border: none;
	height: 25px;
	background: #eeeff3;
  }
  
  .show_textarea {
  	height: 160px;
  	resize: none;
  	width: 97%;
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
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script>
  // 对Date的扩展，将 Date 转化为指定格式的String
  // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
  // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
  // 例子： 
  // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2014-07-02 08:09:04.423 
  // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2014-7-2 8:9:4.18 
  Date.prototype.format = function (fmt) { //author: meizz 
      var o = {
          "M+": this.getMonth() + 1, //月份 
          "d+": this.getDate(), //日 
          "h+": this.getHours(), //小时 
          "m+": this.getMinutes(), //分 
          "s+": this.getSeconds(), //秒 
          "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
          "S": this.getMilliseconds() //毫秒 
      };
      if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
      for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      return fmt;
  }
  
  Date.prototype.addDays = function(d){
	 this.setDate(this.getDate() + d);
	 return this;
  };

  Date.prototype.substractDays = function(d){
    this.setDate(this.getDate() - d);
	return this;
  };

  
  $(function () {
	  var who = decodeURIComponent(window.location.search.split('=')[2]);
	  if(window.location.search === ''){
		  $('#whois').text("我")
	  }else{
		  $('#whois').text(who.replace(/ /g, ''));
	  }
	  
	  $('#apply_morning_date, #apply_afternoon_date').click(function(){
		  WdatePicker({dateFmt:'yyyy-MM-dd'});
	  });
	  
	  $.ajax({
		    type: 'GET',
		    url: '${ctx}/app/attendance/getAccepters.act',
		    data: {applicant_id: ${_userid}},
		    dataType: 'json'
		  }).done(function(res){
			//JSON.stringify(res.accepters)
			//var res = {accepters:  '[{"user_id":2095,"username":"何军"},{"user_id":1033,"username":"中心管理员"}]'}; 
			if(res.accepters === '') {
			  alert('请假失败，暂时没有人审批请假');
		    }else{
		    	if($('#userIdParam').val()){//如果不为空，表示考勤管理员帮人申请，审批人是自己
					$('#apply_user_id').val(userIdParam);
					$('#accepter_field').replaceWith('<input name="accepter" id="apply_accepter" value="${_userid}" type="hidden"/>')
				}else{
					//动态添加审批人
					var accepters = $.parseJSON(res.accepters),
			            acceptersLen = accepters.length;
					for(var i = 0; i < acceptersLen; i++) {
						$('#apply_accepter').append('<option value="' + accepters[i].user_id + '">' + accepters[i].username +'</option>');
					}
				}
		    	$('#apply_many').show();
			}
		  }).fail(function(){
		    alert('请假失败，请联系管理员');
      });
	  
	  $('#send_application').click(function(e){
			e.preventDefault();
			var   endTimeVal = $('#apply_afternoon_date').val(),
			    startTimeVal = $('#apply_morning_date').val() ,
			         endTime = endTimeVal + '-' + $('#apply_day_end_part').val(),
		           startTime = startTimeVal + '-' + $('#apply_day_begin_part').val();
			
			//apply_type
			var type = jQuery("#apply_type option:selected").val();
			if(type == '0'){
				alert('请选择请假类型');
				return;
			}
			
			if($('#apply_reason').val() === '') {
				alert('请假事由不能为空!');
			}else if((startTimeVal === '1' && endTimeVal === '0') || endTime < startTime){
				alert('开始时间与结束时间范围不正确!');
			}else if($('#apply_reason').val().length > 200){
				alert('请假事由内容过长!');
			}else{
				$.ajax({
					type: 'POST',
					url: '${ctx}/app/attendance/approve.act',
					data: $("#apply_form").serialize(),
					dataType: 'json'
				}).done(function(res){
					if(res.success === 'true'){
						//alert('您的请假申请已经成功提交，请等待审批!');
						alert('您的请假申请已经成功提交!');
						$("#apply_form")[0].reset();
					}else{
						alert(res.msg);
					}
				}).fail(function(){
					alert("您的请假申请失败，请联系管理员!");
				});
			}
	   });
	  

  });
</script>
</head>
<body>
	<div class="fkdj_index">
		<div class="bgsgl_border_left">
		<div class="bgsgl_border_right"><label id="whois"></label>的长假申请</div>
		</div>
		<div class="bgsgl_conter">
			<div id="container">
			   <div id="hidden_fields">
			     <input type="hidden" id="userIdParam" value="${param.userId}" />
				 <input type="hidden" id="userName" value="${param.userName}" />
				 <input type="hidden" id="userId" value="${_userid}" />
				 <input type="hidden" id="accepters" value="" />
			   </div>
			   <div id="apply_many">
				  <form action="TODO.act" method="post" autocomplete="off" id="apply_form">
						<input name="user_id" id="apply_user_id" value="${_userid}" type="hidden"/>
						<div class="show_from">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="15%">开始时间：</td>
									<td width="38%"><input name="begin_date" type="text" class="show_inupt" value="" id="apply_morning_date" /></td>
									<td widht="9%"></td>
									<td width="37%" class="left-td">
										<select id="apply_day_begin_part" name="day_begin_part" class="form-apply-begin-date-type valid">
							              <option value="0">上午</option>
							              <option value="1">下午</option>
							            </select>
									</td>
									<td widht="1%"></td>
								</tr>
								<tr>
									<td width="15%">结束时间：</td>
									<td width="38%"><input name="end_date"  type="text" class="show_inupt" value="" id="apply_afternoon_date" /></td>
									<td widht="9%"></td>
									<td width="37%" class="left-td">
										<select id="apply_day_end_part" name="day_end_part" class="form-apply-end-date-type valid">
							              <option value="0">上午</option>
							              <option value="1" selected="selected">下午</option>
							            </select>
									</td>
									<td widht="1%"></td>
								</tr>
								<tr>
									<td width="12%">请假类型：</td>
									<td width="37%" class="left-td">
										<select id="apply_type" class="form-table-apply-type valid" name="type">
											<option value="0"></option>
								            <option value="3">公出</option>
								            <option value="2">事假</option>
								            <option value="1">病假</option>
								            <option value="4">其它</option>
										</select>
									</td>
								</tr>
								<tr id="accepter_field">
									<td width="12%">审批人：</td>
									<td width="38%" class="left-td">
										<select id="apply_accepter" name="accepter" class="form-table-apply-accepter valid"></select>
									</td>
								</tr>
								<tr>
									<td valign="top">事由：</td>
									<td colspan="3"><textarea id="apply_reason" name="reason" style="overflow: auto;" class="show_textarea" maxlength="200" ></textarea></td>
								</tr>
							</table>
						</div>
						<div class="show_pop_bottom">
							<a style="float: left; cursor: pointer;" id="send_application">发送</a>	
						</div>
					  </form>
               </div>
	        </div>
        </div><!-- bgsgl_conter -->
     </div><!-- fkdj_index -->
</body>
</html>
  