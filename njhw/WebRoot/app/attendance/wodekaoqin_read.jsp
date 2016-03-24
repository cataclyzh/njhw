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

  /*
  * Define user commute style
  ********************************************************************
  */

  #commute_content {
    min-height: 510px !important;
  /*   overflow: auto; */
  }
  
  #calendar {
   float: left;
   width: 49.5%
  }

  #commute_detail {
  margin-left: 1%;
  width: 49.5%;
  display: inline-block;
  }

  #commute_info {
  	margin-top: 0;
  	min-height: 88px;
  	background-color: #F6F5F1;
  }
  
  #leave_info {
  	min-height: 117px;
    margin-top: 10px;
    background-color: #F6F5F1;
  }
  
  #punch_card_list {
    margin-top: 10px;
  	background-color: #F6F5F1;
  }
  
  #punch_card_list .commute-detail-content {
  	min-height: 197px;
  	max-height: 197px;
  	overflow: auto;
  }
  
  #punch_card_list table {
  	margin-left: 20px;
  }
  
  #punch_card_list table tr{
  	height: 28px;
  }
  
  #punch_card_list table td {
  	width: 150px;
  }

  .commute-detail-title {
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
  }

  .commute-detail-content {
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
    background-color: #F6F5F1;
    padding: 20px 0;
    font-size: 9pt;
  }

  .commute-detail-title {
    color: #8090B2;
    background-color: #ECECE4;
    padding: 10px 0 10px 10px;
  }

  .commute-detail-content a {
    text-decoration: none;
    color: #8090B2;
  }

  .commute-detail-content ul {
    margin: 0;
    list-style: none;
    padding: 0;
  }

  .commute-detail-content ul li {
    margin: 5px auto;
  }

  .commute-detail-content span {
    width: 200px;
    margin: auto 20px;
/*     padding: auto 20px; */
  }
  
  #commute_info .commute-detail-content span {
  	width: 300px;
  }

  /*
  * Define calendar style
  ********************************************************************
  */
  
  #calendar .fc-content .fc-border-separate thead th {
  	border-left: none;
  	border-right: none;
  }
  
/*   #calendar .fc-content .fc-border-separate tbody td:hover {
  	background: #95c4e2;
    border-color: #95c4e2;
  } */
  
  #calendar .fc-content table tbody {
    /* background: #EEEDE8; */
  }
  
  #calendar .holiday-weekend {
  	/* margin-left: 20px; */
  	color: #fafafa;
  	float: left;
  	background: #8e8e8e;
  }
  
   #calendar .holiday-desc {
  	margin-left: 10px;
  	color: #E27676;
  	text-overflow: ellipsis;
  	-o-text-overflow: ellipsis;
  	width: 58px;
  	overflow: hidden;
  	white-space: nowrap;
  }
  
  #calendar .holiday-desc:hover {
  	cursor: pointer;
  }
  
  #calendar .fc-selected {
  	background: #95c4e2;
    border-color: #95c4e2;
  }
  #calendar .fc-day-number {
    padding-top: 10px;
  }

  #calendar .fc-header-right .fc-button{
    border-radius: 0;
    padding: 0 .2em;
  }
  
  #calendar > table.fc-header {
  	background: #ECECE4;
  }
  #calendar > table.fc-header .fc-header-title {
  	margin-top: 8px;
  }
  
  #calendar > table.fc-header .fc-header-title h2 {
  	font-family: font-family: 'Microsoft YaHei', '微软雅黑','HelveticaNeue', Arial, Helvetica, sans-serif
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

  .fc-event-morning {
    border: none;
    font-size: 8pt;
    border-radius: none;
  }

  .fc-event-afternoon {
    border: none;
    font-size: 8pt;
    border-radius: none;
  }
  
  .hide-day-part .fc-event-inner{
    width: 0;
    height: 0;	
  }
  
  #commute_info_tip {
  	height: 24px;
  	width: 1024px;
    margin-right: 5px;
  }
  
  #commute_info_tip span.color-block {
  /* 	float: right; */
  	margin-right: 5px;
  }
  
  #normal_color {
    width: 32px;
    height: 12px; 
    display: inline-block;
    background: #1abc9c;
    margin-right:5px;
  }
  
  #abnormal_color {
    width: 32px;
    height: 12px; 
    display: inline-block;
    background: #e74c3c;
    margin-right:5px;
  }
  
  #passed_color {
    width: 32px;
    height: 12px; 
    display: inline-block;
    background: #00a6fc;
    margin-right:5px;
  }
  
  #wait_color {
    width: 32px;
    height: 12px; 
    display: inline-block;
    background: #f39c12;
    margin-right:5px;
  }
  
  #holiday_color {
    width: 32px;
    height: 12px; 
    display: inline-block;
    background: #627586;
    margin-right:5px;
  }
  
  #exception_flag{
  	float: left;
  	border: 1px dotted RED;
  	padding: 0 3px;
  }
  
  #normal_flag{
  	float: left;
  	border: 1px dotted GREEN;
  	padding: 0 3px;
  	margin: 0 2px 0 0;
  }
</style>
<script src='js/calendar/moment.min.js'></script>
<script src='js/calendar/jquery.js'></script>
<script src='js/calendar/mustache.js'></script>
<script src='js/calendar/jquery-ui-1.10.3.custom.min.js'></script>
<script src='js/calendar/fullcalendar.min.js'></script>
<script src='js/calendar/zh-cn.js'></script>
<script src='js/calendar/easyui/easyloader.js'></script>
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

  var CommuteUtil = function (){
	  
	var _hasHoliday = false;

    var popupDialog = function (title,url,w,h,refresh, userId, applyDate){
    	var body =  window.document.body,
    		left =  body.clientWidth/2 - w/2,
    		top =  body.clientHeight/2+h/4,
    		scrollTop = document.body.scrollTop;
    
    	top = top + scrollTop;
        openEasyWin("qingjia",title,url,w,h,refresh);
    }

    var renderCommuteDetail = function (params) {
      //var data = {"user_id":1,"commute_title":"考勤信息","leave_title":"请假信息","leave_infos":[{"leave_type":"公出","leave_time":"上午","leave_desc":"至河西指挥部送相关材料"},{"leave_type":"公出","leave_time":"下午","leave_desc":"至河西指挥部送相关材料"}],"punch_card_title":"刷卡记录列表","punch_infos":[{"punch_time":"2014/04/01 16:45:34","punch_addr":"D-1F人行通道1-1出口","punch_tag":"出 闸机"},{"punch_time":"2014/04/01 16:45:34","punch_addr":"D-1F人行通道1-1出口","punch_tag":"出 闸机"}],"commute_morning_0":true,"commute_morning_date": "2014/04/03","commute_afternoon_0":true, "commute_afternoon_date": "2014/04/03"}
      //TODO 当天不显示请假
      var today = (new Date()).format("yyyy-MM-dd"),
          dateParam = params.year + '-' + params.month + '-' + params.date;
      
      $.ajax({
        type: 'POST',
        url: ' ${ctx}/app/attendance/getOneday.act',
        data: params,
        dataType: 'json'
      }).done(function (data) {
        $('#commute_detail').html(Mustache.to_html($('#commute_detail_template').html(), data));
        //TODO 当天不显示请假
        /* if(today === dateParam) {
      	  $('#commute_info .commute-detail-content').html("");
        } */
      }).fail(function(){
        alert('显示详细信息出错,请联系管理员');
      });
      return false;
    }

     var applyCommute = function(userId, applyDate) {
      $.ajax({
	    type: 'GET',
	    url: '${ctx}/app/attendance/getAccepters.act',
	    data: {applicant_id: userId},
	    dataType: 'json'
	  }).done(function(res){
		//JSON.stringify(res.accepters)
		//var res = {accepters:  '[{"user_id":2095,"username":"何军"},{"user_id":1033,"username":"中心管理员"}]'}; 
		if(res.accepters === '[]' && userId === '${_userid}') {
		  alert('请假失败，暂时没有人审批请假');
	    }else{
		  $('#accepters').val(res.accepters);
		  //popupDialog("刷卡异常说明","popup/qingjia.jsp","500","450",false, userId, applyDate);
		}
	  }).fail(function(){
	    alert('请假失败，请联系管理员');
	  });
    }

    var modifyUserState = function () {
        alert('修改状态');
    }
    
    var getCommuteEvts = function(userId, y, m, callback){
    	var today = (new Date()).format('yyyy-MM-dd');
    	$.ajax({
        	  type: 'POST',
              url: '${ctx}/app/attendance/getMonth.act',
              dataType: 'json',
              data: {
                'user_id': userId,
            	year: y,
                month: m
              }
          }).done(function(data){
             //考勤event
             /* {
               className: "fc-event-morning"
          	 start: "2014-04-04"
          	 status: "2"
          	 title: "上午"
             } */
             //return data.events;
             //allEvts = allEvts.concat(data.events);
             //console.log(allEvts)
             if(data.events){ // data = {}
	             var evts    = data.events,
	                 evtsLen = evts.length;
	             while(evtsLen--) {
	            	 evts[evtsLen].className = evts[evtsLen].className + " " + evts[evtsLen].className+'-'+evts[evtsLen].start;
	            	 if(evts[evtsLen].start > today && evts[evtsLen].status === "1") {//未来某天，异常，不显示
	            		 evts[evtsLen].className = evts[evtsLen].className + " hide-day-part";
	            	 }
	             }
	             
	             callback(evts);
             }
             //$('#calendar').fullCalendar( 'addEventSource', data.events )
          }).fail(function(data){
             alert('获取考勤信息失败');
          });	
    }
    
    var getHolidayEvts = function(y, m, callback){
    	$.ajax({
            type: 'POST',
            url: '${ctx}/app/attendance/getHoliday.act',
            dataType: 'json',
            data: {
              year: y,
              month: m
            }
        }).done(function(data){
        	//假日event
            /* {
                end: "2014/04/05"
                id: "2014-04-05_2014-04-05"
           	    start: "2014/04/05"
                title: "周末"
            } */

            var evts    = data.events,
                evtsLen = evts.length;
                td      = null,
                _hasHoliday = false;
                
            while(evtsLen--) {
              td = $('.fc-day[data-date="' + evts[evtsLen].id + '"]');
              if(evts[evtsLen].title === '周末' && td.find('.holiday-weekend').length == 0){//目前没有"休"字
            	  td.css({background: '#FFF0F0'})
            	    .find('.fc-day-number')
            	    .before('<div class="holiday-weekend">休</div>');
              }else if(evts[evtsLen].title !== '周末'){
            	  _hasHoliday = true;
            	  if(td.find('.holiday-weekend').length == 0){
            	    td.css({background: '#FFF0F0'})
              	      .find('.fc-day-number')
              	      .before('<div class="holiday-weekend">休</div>');
            	  }
            	  td.find('.fc-day-content>div')
            	    .html('<div class="holiday-desc" title="' +evts[evtsLen].title+'">'+evts[evtsLen].title+'</div>');
            	   
              }
            }
            
        }).fail(function(res){
        	alert('获取假日信息失败')
        })
    }

    //reveal API
    return {
      renderCommuteDetail: function(params){ renderCommuteDetail(params); },
             applyCommute: function(userId, applyDate){ applyCommute(userId, applyDate); },
          modifyUserState: function(userId){ modifyUserState(userId); },
           getCommuteEvts: function(userId, y, m, callback){return getCommuteEvts(userId, y, m, callback);},
           getHolidayEvts: function(y, m, callback){return getHolidayEvts(y, m, callback);},
              getBHoliday: function(){return _hasHoliday;}
    };
  }();

  var Commute = function () {
	var _calendar;
	
    var initCalendar = function () {
    	_calendar = $('#calendar').fullCalendar({
        lang: 'zh-cn',
        height: 460,
        contentHeight: 460,
        firstDay: 1,//每个星期从星期一开始
        weekMode: 'fixed', //每个月根据当月情况显示4,5,或6个星期行数
        header: {
          left: '', //prev,next today',
          center: 'title',
          right: 'prev,next today'
        },
        allDayText: "foobar",
        editable: false, // Determines whether the events on the calendar can be modified
        selectable: true,
        selectHelper: true,
        select: function(start, end, allDay) {},
        handleWindowResize: false, // 在IE浏览器中，window resize后，events:会发送多次请求
        dayClick: function (date, allDay, jsEvent, view) {
          //给选择当天添加样式
          $('#calendar td.fc-selected').removeClass('fc-selected');
          $(this).addClass('fc-selected');
          //当天的日期
          var userId = $('#userIdParam').val() === '' ? undefined : $('#userIdParam').val();
          var currDate = new Date(date.toString()),
              dateArr = currDate.format("yyyy-MM-dd").split('-'),
              year = dateArr[0],
              month = dateArr[1],
              day = dateArr[2],
              params = {
                'user_id': userId || ${_userid},
                year: year,
                month: month,
                date: day
              }
          CommuteUtil.renderCommuteDetail(params);
        },
        select: function (startDate, endDate, allDay, jsEvent, view) {
          var fromDate = (new Date(startDate.toString())).format('yyyy-MM-dd'),
              toDate = (new Date(endDate.toString())).format('yyyy-MM-dd');
        },
        eventAfterAllRender: function (view) {
          var today   = (new Date()).format('yyyy-MM-dd'),
              dateArr = today.split('-'),
		            d = dateArr[2],
		            m = dateArr[1],
		            y = dateArr[0];
		  //$('#userIdParam').val() 存在情况下，是帮别人申请
		  var userId = $('#userIdParam').val() === '' ? undefined : $('#userIdParam').val();		             
		
		  var data = {
		    'user_id': userId || ${_userid},
		     year: y,
		     month: m,
		     date: d
		  };
		  
		  var moment = $('#calendar').fullCalendar('getDate').stripTime().format();
		  if($('#current_date').val() !== ''){//第一次渲染为空
		      var currDate   = $('#current_date').val().split('-');
			      data.year  =  currDate[0];
			      data.month = currDate[1];
			      data.date  = currDate[2];
			  CommuteUtil.renderCommuteDetail(data);
		  }else if(moment === today) {//第一次渲染默认显示当天的详细信息
			  CommuteUtil.renderCommuteDetail(data);
		  }
        },
        eventRender: function(event, el, view) {
        },
        eventAfterRender: function (event, el, view) {
          var width = $(el).width(),
              height = $(el).height(),
              halfWidth = width / 2,
              tdHeight = $('.fc-day').height(),
              numHeight = $('.fc-day-number').height(),
              marginTop = tdHeight - height - numHeight,
              top = $(el).css('top'),
              spanWidth = (halfWidth + 1) + 'px';
          
          var delta = 12; // default value
          
          /* if(!CommuteUtil.getBHoliday()){
        	  delta = 8;
          } */

          // status:
          // 0: 刷卡正常   #1abc9c
          // 1: 刷卡异常   #e74c3c
          // 2: 说明已提交 #00a6fc
          // 3: 其他      #627586
          // 4: 说明已确认 #f39c12
          var bgColor = '#1abc9c',
              status = event.status;

          if (status === '1') {
            bgColor = '#e74c3c';
          }else if (status === '2') {
            bgColor = '#00a6fc';
          }else if (status === '3') {
            bgColor = '#627586';
          }else if (status === '4') {
            bgColor = '#f39c12';
          }
          //针对IE浏览器调整上午下午的宽度
          if ($.browser.msie){
        	  spanWidth = (halfWidth + 3) + 'px';
          }

          //设置上午下午
          $(el).css({
            width: spanWidth, // morning div share (4px - 2px) / 2
            'margin-top': (marginTop - delta) + 'px', //.fc-day-number margin-top 10px
            'background-color': bgColor
          }).offset({
            left: $(el).offset().left - 2
          })
          //针对下午模块
          if($(el).hasClass('fc-event-afternoon')) {
            var left = $(el).prev().offset().left;
            if ($.browser.msie){
              left = left + 2;
            }
            $(el).offset({
              left: left + halfWidth + 3
            }).css({
              width: spanWidth, // morning div share (4px - 2px) / 2
              top: $(el).prev().css('top'), // refer to previous morning span
            }); // moring and afternoon space is 2px
          }
        },
        timeFormat: '', // morning & afternoon text has no prefix
        events: function( start, end, timezone, callback ) {
      	  var dateArr = (new Date()).format('yyyy-MM-DD').split('-'),
  	                m = dateArr[1],
  	                y = dateArr[0];
      	  
      	  //点击">"或者"<"显示前一个月，或者后一个月
      	  if(_calendar){
  	    	  dateArr = _calendar.fullCalendar('getDate').format().split('-'),
  		            y = dateArr[0],
  		            m = dateArr[1];
      	  }
      	  
      	  var userId = ($('#userIdParam').val() === '' ? undefined : $('#userIdParam').val()) || ${_userid};
      	  CommuteUtil.getCommuteEvts(userId, y, m, callback);
      	  CommuteUtil.getHolidayEvts(y, m, callback);
        }
        		
      });
    }

    return {
    	
      /**
      * Initialize my commute and add some event listeners
      *
      * @method init
      */
      init: function() {
        $('#commute_detail').delegate('.user-apply-commute', 'click', function (e) {
           //先移除所有被选中的请假链接class
           $('#commute_detail .user-apply-commute').removeClass('selected-day-part');
           $(this).addClass('selected-day-part');//为了后面请假成功后修改界面文字
           e.preventDefault();
           var userId = $(this).attr('href').slice(1),
               applyDate = $(this).attr('rel');
           //当前请假的日期，为了calendar refetchEvents时跳转到当天
           $('#current_date').val(applyDate);
           CommuteUtil.applyCommute(userId, applyDate);
        });

        $('#commute_detail').delegate('.modify_user_state', 'click', function (e) {
          e.preventDefault();
          var userId = $(this).attr('href').slice(1);
          CommuteUtil.modifyUserState(userId);
        });
      },

      /**
      * Initialize my commute in calendar
      *
      * @method initCalendar
      */
      initCalendar: function(){
        initCalendar();
      },
      
      getCalendar: function(){
    	  return _calendar;
      }
    };

  }();

  $(function () {
	  var who = decodeURIComponent(window.location.search.split('=')[2]);
	  if(window.location.search === ''){
		  $('#whois').text("我")
	  }else{
		  $('#whois').text(who.replace(/ /g, ''));
	  }
	  Commute.init();
      Commute.initCalendar();
  });
</script>
</head>
<body>
	<div class="fkdj_index">
		<div class="bgsgl_border_left">
		<div class="bgsgl_border_right"><label id="whois"></label>的出勤</div>
		</div>
		<div class="bgsgl_conter">
			<div id="container">
			  <div id='commute_content'>
				    <div id="hidden_fields">
				      <input type="hidden" id="userIdParam" value="${param.userId}" />
				      <input type="hidden" id="userName" value="${param.userName}" />
				      <input type="hidden" id="userId" value="${_userid}" />
				      <input type="hidden" id="accepters" value="" />
				      <input type="hidden" id="current_date" value="" />
				    </div>
				    <div id='commute_info_tip'>
				    	<div id="normal_flag">
					    	<span class='color-block'>
					    		<span id="normal_color"></span><span>刷卡正常</span>
					    	</span>
				    	</div>
				    	<div id="exception_flag">
					    	<span class='color-block'>
					    		<span id="abnormal_color"></span><span>刷卡异常</span>
					    	</span>
					    	<span class='color-block'>
					    		<span id="passed_color"></span><span>说明已确认</span>
					    	</span>
					    	<span class='color-block'>
					    		<span id="wait_color"></span><span>说明已提交</span>
					    	</span>
					    	<span class='color-block'>
					    		<span id="holiday_color"></span><span>其他</span>
					    	</span>
					    </div>
				    </div>
				    <div style="clear:both;height:1px"></div>
				    <div id='calendar'></div>
				    <div id='commute_detail'></div>
				    <script type="text/html" id="commute_detail_template">
        				<div id='commute_info'>
          					<div class="commute-detail-title">考勤信息</div>
          					<div class="commute-detail-content">
            					<!--上午考勤-->
            					{{#commute_morning_0}}
              						<span><a href="#{{user_id}}" class="user-apply-commute fc-event-morning-{{commute_morning_date}}" rel="{{commute_morning_date}}" id="apply_morning"></a></span>
            					{{/commute_morning_0}}

            					{{#commute_morning_1}}
              						<span><span>上午刷卡说明已提交</span></span>
            					{{/commute_morning_1}}

            					{{#commute_morning_2}}
              						<span><span>上午刷卡说明已确认</span></span>
            					{{/commute_morning_2}}

            					{{#commute_morning_3}}
              						<span><a href="#{{user_id}}" class="user-apply-commute fc-event-morning-{{commute_morning_date}}" rel="{{commute_morning_date}}" id="apply_morning">重新刷卡异常说明</a></span>
            					{{/commute_morning_3}}

            					{{#commute_morning_4}}
              						<span>上班时间:  <a href="#{{user_id}}" class="modify_user_state">{{commute_morning_time}}</a></span>
            					{{/commute_morning_4}}

            					{{#commute_morning}}
              						<span>上班时间:  {{commute_morning_time}}</span>
            					{{/commute_morning}}

            					<!--下午考勤-->
            					{{#commute_afternoon_0}}
              						<span><a href="#{{user_id}}" class="user-apply-commute fc-event-afternoon-{{commute_afternoon_date}}" rel="{{commute_afternoon_date}}" id="apply_afternoon"></a></span>
            					{{/commute_afternoon_0}}

            					{{#commute_afternoon_1}}
              						<span><span>下午刷卡说明已提交</span></span>
            					{{/commute_afternoon_1}}

            					{{#commute_afternoon_2}}
              						<span><span>下午刷卡说明已确认</span></span>
            					{{/commute_afternoon_2}}

            					{{#commute_afternoon_3}}
              						<span><a href="#{{user_id}}" class="user-apply-commute fc-event-afternoon-{{commute_afternoon_date}}" rel="{{commute_afternoon_date}}" id="apply_afternoon">重新刷卡异常说明</a></span>
            					{{/commute_afternoon_3}}

            					{{#commute_afternoon}}
              						<span>下班时间:  {{commute_afternoon_time}}</span>
            					{{/commute_afternoon}}
          					</div>
        				</div>

        				<div id='leave_info'>
          					<div class="commute-detail-title">刷卡异常说明</div>
          					<div class="commute-detail-content">
           						<ul>
              						{{#leave_infos}}
                						<li><span>{{leave_type}}</span><span>{{leave_time}}</span><span>{{leave_desc}}</span></li>
              						{{/leave_infos}}
              						{{^leave_infos}}
                						<li><span>无刷卡异常说明</span></li>
              						{{/leave_infos}}
            					</ul>
          					</div>
        				</div>

        				<div id='punch_card_list'>
          					<div class="commute-detail-title">刷卡记录列表</div>
          					<div class="commute-detail-content">
                                {{^punch_infos}}
									<ul>
										<li><span>无刷卡记录</span></li>
									</ul>
								{{/punch_infos}}
								<table>
                                {{#punch_infos}}
									<tr>
										<td>{{punch_time}}</td>
										<td>{{punch_addr}}</td>
										<td>{{punch_tag}}</td>
                                    </tr>
								{{/punch_infos}}
           						</table>
          					</div>
        				</div>
    			</script>
		      </div><!-- commute_content -->
		      <div id='apply_commute_win' class='easyui-window' collapsible='false' draggable="true" resizable="false" minimizable='false' maximizable='true' style='padding: 0px; background: #fafafa; overflow: hidden; display: none;' closed='true'>
			     <iframe id='commute_iframe' name='commute_iframe' style='width: 100%; height: 100%;' frameborder='0'></iframe>
		      </div>
	        </div>
        </div><!-- bgsgl_conter -->
     </div><!-- fkdj_index -->
</body>
</html>
  