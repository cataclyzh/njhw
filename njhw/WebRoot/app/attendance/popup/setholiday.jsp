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
<title>考勤设定</title>	
<%@ include file="/common/include/meta.jsp" %>
<link href='../css/fullcalendar.css' rel='stylesheet' />
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
  	height: 100px;
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
<!-- full calendar required js -->
<script src='../js/calendar/moment.min.js'></script>
<script src='../js/calendar/mustache.js'></script>
<script src='../js/calendar/fullcalendar.min.js'></script>
<script src='../js/calendar/zh-cn.js'></script>
<!-- full calendar required js -->
<script>
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2014-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2014-7-2 8:9:4.18 
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
};

Date.prototype.substractDays = function(d){
    this.setDate(this.getDate() - d);
    return this;
};

$(function(){
  var parentDoc = parent.document,
	  from      = parentDoc.getElementById("from_date").value,
	  to        = parentDoc.getElementById("to_date").value,
	  $calendar = parent.window.HolidaySetting.getCalendar();
		
  //console.log('from date: ' + from);
  //console.log('to date: ' + to);
  //console.log(parentDoc.HolidaySetting)
		
  $('#set_holiday').click(function(e){
    e.preventDefault();
    //alert(from)
    //alert((new Date(to.replace(/-/g, "/"))).substractDays(1).format('yyyy-MM-dd'))
	if($('#holiday_desc').val() === '') {
	  alert('描述不能为空!');
	}else{
	$.ajax({
	    type: 'POST',
		url: '${ctx}/app/attendance/setHoliday.act',
		data: {
	      from: from,
		  to: (new Date(to.replace(/-/g, "/"))).substractDays(1).format('yyyy-MM-dd'), //传输的时候减少一天，获取的时候增加一天, IE just parse to by '/'
		  desc: $('#holiday_desc').val()
		},
		dataType: 'json'
		}).done(function(res){
		  //res => {status: 'ok'} or {status: 'error'}
		  var eventData;
		    if(res.status === 'ok'){
			  eventData = {
			    title: $('#holiday_desc').val(),
			    start: from,
			    end: to,
			    id: from+'_'+to
		      };
			  //var f = new Date('2014-04-09')
			  //var t = new Date('2014-04-20')
			  //var days = (t - f) / (24 * 60 * 60 * 1000)
			  alert('考勤设定成功');
			  $calendar.fullCalendar('renderEvent', eventData, true);
			  $calendar.fullCalendar('unselect');
			}else{
			  alert("考勤设定失败，请联系管理员!");
			}
			parent.jumpPage(1);
			parent.closeEasyWin('setHoliday');
	  }).fail(function(){
			alert("考勤设定失败，请联系管理员!");
			parent.jumpPage(1);
			parent.closeEasyWin('setHoliday');
      });
	}
  });
		
});
</script>
</head>
<body style="background: #FFF;font-size: 14px; color: #808080;">
  
  <div class="main_left_main1">
	<div class="main_conter">
		<div class="main1_main2_right">
			<form action="TODO.act" method="post" autocomplete="off" id="set_holiday_form">
				<div class="show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top">描述：</td>
							<td colspan="3"><textarea id="holiday_desc" name="desc" style="overflow: auto;" class="show_textarea" ></textarea></td>
						</tr>
					</table>
				</div>
				<div class="show_pop_bottom">
					<a style="float: left; cursor: pointer;" id="set_holiday">设定</a>	
				</div>
			</form>
		</div>
		<div class="clear"></div>
	</div>
  </div>
</body>
</html>
  