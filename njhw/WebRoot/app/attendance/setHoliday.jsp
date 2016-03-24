<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@ include file="/common/include/meta.jsp" %>
<title>考勤设定</title>
<%@ include file="/common/include/header-meta.jsp" %>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/qbyj_index.css" rel="stylesheet" type="text/css" />
<%-- 		<link href="${ctx}/common/pages/misc/css/table.css" rel="stylesheet" type="text/css" /> --%>
		<link href="${ctx}/common/pages/misc/css/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/personinfo/css/table_per.css" rel="stylesheet" type="text/css" />
		
		<link href='css/fullcalendar.css' rel='stylesheet' />
        <link href='css/fullcalendar.print.css' rel='stylesheet' media='print' />
<%--         <link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/> --%>
        
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script src="${ctx}/common/pages/misc/js/ctt.js" type="text/javascript"></script>



<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<!-- full calendar required js -->
<script src='js/calendar/moment.min.js'></script>
<script src='js/calendar/mustache.js'></script>
<script src='js/calendar/fullcalendar.min.js'></script>
<script src='js/calendar/zh-cn.js'></script>
<script type="text/javascript" src="js/calendar/jquery.inputmask.bundle.min.js"></script>

<!-- full calendar required js -->
<style type="text/css">
  body {
  	background: transparent;
    font-size: 14px;
    color: #808080;
  }
  
  .bgsgl_border_right {
    font-family: "微软雅黑";
  }
  
/*
  * Redefine Tab style
  ********************************************************************
  */
  .main_conter, .main_main2{
  	border: none;
  }
  
  .main_main1 li {
  	width: 100px;
  	
  }
  .main_main1 li a {
    width: 100px;
    background: url(images/attendance_set.png) left top no-repeat;
  }
  .main_main1 li a:hover {
  	width: 100px;
  	background: url(images/attendance_set_h.png) left top no-repeat;
  }
  
  #set_holiday_tab_nav {
  	height: 31px;
  }
  
  #set_holiday_tab_nav .treeOn a, #set_holiday_tab_nav li a:hover {
    width: 100px;
    background: url(images/attendance_set_h.png) left top no-repeat;
	line-height: 32px;
	text-align: center;
	text-decoration: none;
	font-family: "微软雅黑";
	font-size: 14px;
	font-weight: bold;
	color: #fff;
  }
  
  #set_holiday_tab_nav .treeNone a {
  	display:block;
	background:url(images/attendance_set.png) left top no-repeat;
	line-height:32px; 
	text-align:center; 
	text-decoration:none; 
	font-family:"微软雅黑"; 
	font-size:14px; 
	font-weight:bold; 
	color:#808080;
  }
  
  #set_commute_time {
  	background: #f6f5f1;
  	margin: 100px;
  	height: 200px;
  	padding: 50px;
  }
  
  #set_commute_time div {
  	text-align:center;
  	padding: 10px;
  	margin: 20px auto;
  }
  
  #set_commute_time div label{
  	display: inline-block;
  	width: 100px;
  	line-height: 18px;
  }
  
  .mandatory {
  	color: #e74c3c;
  	padding: 5px;
  	vertical-align: middle;
  }
  
  .show_pop_bottom a{
	background:#8090b2;
  }
  .show_pop_bottom a:hover{
	background:#ffc702;
  }
  
  #set_commute_time div span {
  	display: inline-block;
  	line-height: 18px;
  }
  
  #set_commute_time div input {
  	width: 150px;
  }
  
/*
  * Define calendar style
  ********************************************************************
  */
  #calendar .fc-content table tbody {
    background: #EEEDE8;
  }
  
  #calendar .fc-day-number {
    padding-top: 10px;
  }
  
  #calendar #remove_evt_btn {
  	margin: 5px;
  }
  
  #calendar .fc-event {
  	height: 18px;
  	padding: 3px 0;
  }
  
  #calendar .fc-event-title {
  	font-size: 18px;
  }
  
  #calendar .fc-button-today {
  	margin-right: 5px;
  }

  #calendar .fc-header-right .fc-button{
    border-radius: 0;
    padding: 0 .2em;
  }
  
  #calendar > table.fc-header {
  	background: #e4e2da;
  }
  #calendar > table.fc-header .fc-header-title {
  	margin-top: 8px;
  }
  
  #calendar > table.fc-header .fc-header-title h2 {
  	font-family: "微软雅黑";
  }
  
  #calendar > table.fc-header .fc-header-right .fc-button {
  	margin-bottom: 5px;
  	margin-top: 5px;
  }
  
  #calendar table.fc-border-separate thead tr{
  	height: 38px;
  }
  #calendar table.fc-border-separate thead tr th {
  	line-height: 18px;
  	padding: 10px 0;
  }

  .fc-content {
    color: #8090B2;
  }

  .fc-event-inner {
    text-align: center;
  }
  
</style>
<script type="text/javascript">
//对Date的扩展，将 Date 转化为指定格式的String
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

var HolidaySetting = function () {
  var _calendar;
  var _selectedEvents = [];
	
  var popupDialog = function (title,url,w,h,refresh){
    var body =  window.document.body,
        left =  body.clientWidth/2 - w/2,
    	top =  body.clientHeight/2+h/4,
    	scrollTop = document.body.scrollTop;
    
    top = top + scrollTop;
    openEasyWin("setHoliday",title,url,w,h,refresh);
  }

  var initSettingCalendar = function () {
	//every time remove one event
	$('#calendar').delegate('#remove_evt_btn', 'click', function(){
		var $this = $(this);
		if(!$this.hasClass('fc-state-disabled')){
			//TODO
			//Ajax server with date range to remove server side data.
			//_selectedEvents just has one event in current situation 
			var selectEvtLen = _selectedEvents.length;
			var allEvts = $('#calendar').fullCalendar('clientEvents');
			var allEvtLen = allEvts.length;
			
			while(allEvtLen--) {
				//find select event id
				if(allEvts[allEvtLen].id === _selectedEvents[0]) {
					// delEndDate should subtract 1 day
					var delStartDate = (new Date(allEvts[allEvtLen].start.toString())).format('yyyy-MM-dd'),
					    delEndDate   = (new Date(allEvts[allEvtLen].end.toString())).format('yyyy-MM-dd');//.substractDays(1)
				    $.ajax({
						type: 'POST',
						url: '${ctx}/app/attendance/delHoliday.act',
						data: {
							from: delStartDate,
							to: delEndDate
						}
					}).done(function(res){
						if(res.status === 'ok') {
							//after delete holiday, disable delete button
							$('#calendar').fullCalendar('removeEvents', _selectedEvents[0]);
							$this.addClass('fc-state-disabled');
						}else{
							alert('删除失败，请联系管理员!')
						}
					}).fail(function(){
						alert('删除失败，请联系管理员!');
					});
				    break;
				}
			}
			if(!_selectedEvents.length) {
				$('#remove_evt_btn').addClass('fc-state-disabled');
			}
		}
	});
	
	
    _calendar =  $('#calendar').fullCalendar({
      lang: 'zh-cn',
      firstDay: 1,//每个星期从星期一开始
      weekMode: 'liquid', //每个月根据当月情况显示4,5,或6个星期行数
      height: 580,
      header: {
        left: '', //prev,next today',
        center: 'title',
        right: 'prev,next today' //'month,basicWeek,basicDay'
      },
      editable: false,
      selectable: true,
      selectHelper: true,
      eventClick: function(calEvent, jsEvent, view) {
        //alert('Event: ' + calEvent.title);
        //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
        //alert('View: ' + view.name);
        //each time just select one event
        _selectedEvents = null;
        _selectedEvents = [];
        $('.fc-event').css('border-color', 'transparent');
        // change the border color just for fun
        $(this).css('border-color', 'red');
        _selectedEvents.push(calEvent.id);
        $('#remove_evt_btn').removeClass('fc-state-disabled');
      },
      viewRender: function (view, element) {
    	  if(!$('#calendar .fc-header-left span').hasClass('remove-evt-btn')) {
    		  $('#calendar .fc-header-left').append('<span id="remove_evt_btn" class="remove-evt-btn fc-button fc-button-today fc-state-default fc-corner-left fc-corner-right fc-state-disabled" unselectable="on">删除</span>')
    	  }else{
    		  // switch to next or prev month, disable delete button
    		  if(!$('#remove_evt_btn').hasClass('fc-state-disabled')){
    			  $('#remove_evt_btn').addClass('fc-state-disabled');
    		  }
    	  }
      },
      select: function (startDate, endDate, allDay, jsEvent, view) {  
    	//var date2 = new Date(date.getYears(), date.getMonths(), date.getDate()+1);
    	var collide = false;
    	var events = $('#calendar').fullCalendar('clientEvents');
    	var eventsLen = events.length;
    	while(eventsLen--){
    	    var event = events[eventsLen];
    		var preStart = new Date (event.start.toString()),
    		    preEnd = (new Date (event.end.toString())).substractDays(1),
    		    newStart = new Date(startDate.toString()),
    		    newEnd = (new Date(endDate.toString())).substractDays(1);
    		
    		//console.log('preStart: ' + preStart.format('yyyy-MM-dd'))
    		//console.log('preEnd: ' + preEnd.format('yyyy-MM-dd'))
    		//console.log('newStart: ' + newStart.format('yyyy-MM-dd'))
    		//console.log('newEnd: ' + newEnd.format('yyyy-MM-dd'))
    		
    		if((preStart < newStart && preEnd < newStart) || (newStart < preStart && newEnd < preStart)) {
    	      collide = false;
    		}else{
    	      collide = true;
    	      break;
    		}
    	}
    	
    	if(collide) {
    	  alert('考勤设定有重叠，请重新设定!');
    	}else{
    	  $('#from_date').val((new Date(startDate.toString())).format('yyyy-MM-dd'));
    	  $('#to_date').val((new Date(endDate.toString())).format('yyyy-MM-dd'));
    	  popupDialog("考勤设定","popup/setholiday.jsp","500","250",false);
    	}
       
      },
      timeFormat: '',
      events: function( start, end, timezone, callback ) {
    	  var dateArr = (new Date()).format('yyyy-MM-DD').split('-');
	          m = dateArr[1],
	          y = dateArr[0];
    	  if(_calendar){
	    	  dateArr = _calendar.fullCalendar('getDate').format().split('-'),
		         y =  dateArr[0],
		         m    =  dateArr[1];
    	  }
          $.ajax({
        	  type: 'POST',
              url: '${ctx}/app/attendance/getHoliday.act',
              dataType: 'json',
              data: {
            	 year: y,
                 month: m
                  //from: (new Date(start.toString())).format('yyyy-MM-dd'),
                  //to: (new Date(end.toString())).substractDays(1).format('yyyy-MM-dd')
              },
              success: function(data) {
            	  
            	  //var evts = data.events,
            	  //    evtsLen = evts.length;
            	  
            	  //while(evtsLen--) {
            		  //delete(evts[evtsLen].id);
            		  //evts[evtsLen].start = (new Date(evts[evtsLen].start)).format('yyyy-MM-dd');
            		  //evts[evtsLen].end = (new Date(evts[evtsLen].end)).addDays(1).format('yyyy-MM-dd');
            		  
            	  //}
                  callback(data.events);
              }
          });
      } // request /holidays?start=2014-03-31&end=2014-05-12&_=1396332213809	  
    		  
    });
   
  }
  
  return {
	  
    /**
    * Set holiday in calendar
    *
    * @method initSettingHoliday
    */
    initSettingHoliday: function () {
      initSettingCalendar();
    },
    
    getCalendar: function(){
    	return _calendar;
    },
    
    getSelectedEvts: function(){
    	return _selectedEvents;
    }
    
  };

}();

function displayPopup()
{
	if($(document).width() >= 768)
		return false;
	else
		return true;
}

$(function () {
	//显示节假日设定
	$('#setting_holiday_id').click(function(){
		$(this).removeClass('treeNone').addClass('treeOn');
		$('#setting_holiday_tab').show();
		$('#setting_building_id').removeClass('treeOn').addClass('treeNone');
		$('#setting_building_tab').hide();
		$('#setting_commute_id').removeClass('treeOn').addClass('treeNone');
		$('#setting_commute_tab').hide();
	});
	
	//显示考勤时间设定
	$('#setting_commute_id').click(function(){
		$(this).removeClass('treeNone').addClass('treeOn');
		$('#setting_commute_tab').show();
		$('#setting_building_id').removeClass('treeOn').addClass('treeNone');
		$('#setting_building_tab').hide();
		$('#setting_holiday_id').removeClass('treeOn').addClass('treeNone');
		$('#setting_holiday_tab').hide();
	});
	
	// 显示大厦考勤员设定
	$('#setting_building_id').click(function(){
		$(this).removeClass('treeNone').addClass('treeOn');
		$('#setting_building_tab').show();
		$('#setting_commute_id').removeClass('treeOn').addClass('treeNone');
		$('#setting_commute_tab').hide();
		$('#setting_holiday_id').removeClass('treeOn').addClass('treeNone');
		$('#setting_holiday_tab').hide();
	});
	
    //初始化节假日设定Calendar
    HolidaySetting.initSettingHoliday();
    
    var setDefaultTime = function() {
      //设置默认值
      $('#commute_morning_from').val('9:00');
      $('#commute_morning_end').val('12:00');
      $('#commute_afternoon_from').val('14:00');
      $('#commute_afternoon_end').val('18:00');
    }
    //初始化考勤时间设定
    $.ajax({
    	type: 'GET',
    	url: '${ctx}/app/attendance/getTime.act'
    }).done(function(res) {
      if(res.from === undefined && res.to === undefined) {
        setDefaultTime();
      }else{
    	var from = res.from.split('-'),
            to   = res.to.split('-');
	    $('#commute_morning_from').val(from[0]);
	    $('#commute_morning_end').val(from[1]);
	    $('#commute_afternoon_from').val(to[0]);
	    $('#commute_afternoon_end').val(to[1]);
      }
      
    }).fail(function(res){
      setDefaultTime();
    });
    
    
  //初始化大厦考勤员
    $.ajax({
    	type: 'GET',
    	url: '${ctx}/app/attendance/getAttendancers.act'
    }).done(function(msg) {
    	$('#building_attendance').val(msg);
 	 }).fail(function(msg){
    	alert('大厦考勤员获得失败!');
    });
    
    $('#commute_morning_from, #commute_morning_end, #commute_afternoon_from, #commute_afternoon_end').inputmask( 'h:s' );
    
    //保存考勤时间设定
    $('#save_commute_time').click(function(e){
    	e.preventDefault();
    	var validInput = true,
    	    inputs = $('.mandatory-field'),
    	    inputsLen = inputs.length,
    	    afternoonEnd = $('#commute_afternoon_end').val(),
    	    afternoonStart = $('#commute_afternoon_from').val(),
    	    morningEnd  = $('#commute_morning_end').val(),
    	    morningStart = $('#commute_morning_from').val();
    	
    	//高效率遍历
    	while(inputsLen--) {
    		if($(inputs[inputsLen]).val() === '') {
    			validInput = false;
    			break;
    		}
    	}
    	
    	if(!validInput){
    	  alert('必填字段不能为空!');
    	}else if(!(afternoonEnd > afternoonStart && afternoonStart > morningEnd && morningEnd > morningStart)){
    	  alert('时间范围不合法');
    	}else{
    	  var fromTime = $('#commute_morning_from').val()+'-'+$('#commute_morning_end').val(),
              toTime   = $('#commute_afternoon_from').val()+'-'+$('#commute_afternoon_end').val();
          $.ajax({
    	    type: 'POST',
    	    url: '${ctx}/app/attendance/setTime.act',
    	    data: {
    	      from: fromTime,
    	      to: toTime
    	    }
    	  }).done(function(res){
    	    if(res.success === 'true') {
    	      alert('考勤时间设定成功');
    	    }else{
    	      alert(res.msg);
    	    }
    	  }).fail(function(res){
    	    alert("考勤时间设定失败，请联系管理员.")
    	  });
    	}
    });
       
});

function selectBuildingAtt(){
	var url = "${ctx}/app/per/buildingAtts.act";
	openEasyWin("buildingAttInput","选择大厦考勤人员",url,"520","420",false);
	}
</script>
</head>
<body>
   <input id="pageNo" value="1" type="hidden">
   <div class="main_index">
     <jsp:include page="/app/integrateservice/header.jsp" ></jsp:include>
	 <div class="index_bgs" style="padding: 0px 10px 10px 10px;">
		<div style="min-height: 300px">
				    <div class="fkdj_index">
					    <div class="images_left">
							<%@ include file="/common/pages/misc/cttLeft.jsp" %>
						</div>
						<div class="images_right">
							<div class="bgsgl_border_left">
							  <div class="ctt_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">考勤设定</div>
							</div>
							<div class="bgsgl_conter"><!-- <<bgsgl_conter>> -->
							 <!-- Tab -->
							 <div class="main_conter"><!-- <<main_conter>> -->
			                    <div class="main_main1" id="set_holiday_tab_nav">
				                  <ul>
					                <li class="treeOn" id="setting_holiday_id"><a style="cursor: pointer;">节假日设定</a></li>
					                <li class="treeNone" id="setting_commute_id"><a style="cursor: pointer;">考勤时间设定</a></li>
					                <li class="treeNone" id="setting_building_id"><a style="cursor: pointer;">大厦考勤员设定</a></li>
				                 </ul>
				                 <!-- <div class="main_img2">
					                <a style="cursor: pointer;" onclick="refectory()">编辑</a>
				                 </div> -->
			                    </div>
				                <div> <!-- <<Tab Content>> -->
					               <!-- 节假日设定选项开始 -->
					               <div class="main_main2" id="setting_holiday_tab" style="display: block;">
								     <div id="hidden_fields">
								       <input type="hidden" id="from_date" value="" />
								       <input type="hidden" id="to_date" value="" />
								     </div>
							         <div id='calendar'></div>
							         <!-- 设置节假日，添加注释对话框 -->
							         <div id='apply_commute_win' class='easyui-window' collapsible='false' draggable="true" resizable="false" minimizable='false' maximizable='true' style='padding: 0px; background: #fafafa; overflow: hidden; display: none;' closed='true'>
						                <iframe id='commute_iframe' name='commute_iframe' style='width: 100%; height: 100%;' frameborder='0'></iframe>
						             </div>
			                         <div class="clear"></div>
					               </div>
					               <!-- 节假日设定选项结束 -->
					               
					               <!-- 考勤时间设定选项开始 -->
					               <div class="main_main2" id="setting_commute_tab" style="display: none;">					
										<div id="set_commute_time">
											<div>
												<label>上午工作时间: </label>
												<input type="text" name="commute_morning_from" id="commute_morning_from" class="commute-from mandatory-field"/> ~ 
												<input type="text" name="commute_morning_end" id="commute_morning_end" class="commute-from mandatory-field"/><span class="mandatory">*</span>
											</div>
											<div>
												<label>下午工作时间: </label>
												<input type="text" name="commute_afternoon_from" id="commute_afternoon_from" class="commute-to mandatory-field"/> ~ 
												<input type="text" name="commute_afternoon_end" id="commute_afternoon_end" class="commute-to mandatory-field"/><span class="mandatory">*</span>
											</div>
											<div class="show_pop_bottom">
												<span>
												  <a style="float: left; cursor: pointer;" id="save_commute_time">保存</a>	
												  <!-- <a style="float: left; cursor: pointer;" id="cancel_setting">取消</a> -->
												</span>
											</div>
										</div>
										<div id="time_picker"></div>
									    <div class="clear"></div> 
								   </div>
								   <!-- 考勤时间设定选项结束 -->
								   
								         <!-- 大厦考勤员设定-->
					               <div class="main_main2" id="setting_building_tab" style="display: none;">					
										<div id="set_commute_time">
											<div style="float:left;">
											
											<table>
											<tr>
											<td><label style="width:100px">大厦考勤人员: </label>
											</td>
											<td><input type="text" style="width: 500px;" id="building_attendance" class="commute-from mandatory-field" readonly/>
											</td>
											<td width="100px"><a class="infrom_a" style="width:60px"id="approvers"  href="javascript:void(0);" onclick="javascript:selectBuildingAtt();">变更</a>
											</td>
											</tr>
											</table>
												
											</div>
										</div>
									    <div class="clear"></div> 
								   </div>
								     <!-- 大厦考勤员设定-->
								   
				                </div><!-- <<Tab Content>> -->
		                      </div><!-- <<bgsgl_conter>> -->
							</div>
						</div>
				    </div>
				    <!--此处写页面的内容 -->
					<div class="bgsgl_clear"></div>
				</div>
	 </div>
			
	 <jsp:include page="/app/integrateservice/footer.jsp" />
   </div>
</body>
</html>
