<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<!-- <meta http-equiv="X-UA-Compatible" content="IE=8" /> -->
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
		<meta http-equiv="Cache-Control" content="no-store"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/>
		<script type="text/javascript">var _ctx = '${ctx}';</script>
		<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
 		<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>
		
		<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>车位管理 </title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/timepicker/jquery.timepicker.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/datatable/jquery.dataTables.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/json2.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/datatable/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/datatable/jquery.dataTables.min.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/underscore-min.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/jquery.timepicker.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			function toFocus(){
			  $("#A_CAR_NO").focus();
		  }
		
		  $(document).ready(function() {
			  
			  function showParkingNum(){
				  $.ajax({
					   type: "POST",
					   url: "${ctx}/app/pro/getParkingNum.act",
					   data: "orgId=${orgId}",
					   dataType: "json",
					   success: function(json){
							var parkingNum = json.parkingNum;
							var countAllot = json.countAllot;
							var info = parkingNum + "/" + countAllot;
							$("#parkingNum").html(parkingNum);
							$("#countAllot").html(countAllot);
					   }
					   /*error: function (XMLHttpRequest, textStatus, errorThrown) {
							alert(textStatus);
							alert(errorThron);
					   }*/
					});
			  }
			  
			  showParkingNum();
			  
			  function windowDialog(title,url,w,h,refresh){
				 	var body =  window.document.body;
					var left =  body.clientWidth/2 - w/2;
					var top =  body.clientHeight/2+h/4;
					var scrollTop = document.body.scrollTop;
					//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
					top = top + scrollTop;
				    openEasyWin("setParkings",title,url,w,h,false);
				    $('div.window-header').css({"width": "480px"});
			  }
			  //IE8 Array indexOf
			  if (!Array.prototype.indexOf)
			  {
			    Array.prototype.indexOf = function(elt /*, from*/)
			    {
			      var len = this.length >>> 0;  
			       var from = Number(arguments[1]) || 0;
			      from = (from < 0)
			           ? Math.ceil(from)
			           : Math.floor(from);
			      if (from < 0)
			        from += len; 
			       for (; from < len; from++)
			      {
			        if (from in this &&
			            this[from] === elt)
			          return from;
			      }
			      return -1;
			    };
			  }
			   
			  //切换tab
			  $('#add_id').click(function(){
				  $('#A_CAR_OWNER_NAME').val("");
				  $(this).removeClass('treeNone').addClass('treeOn');
				  $('#search_id').removeClass('treeOn').addClass('treeNone');
				  $('#edit_id').removeClass('treeOn').addClass('treeNone');
				  $('#add_tab').show();
				  $('#search_tab').hide();
				  $('#edit_tab').hide();
			  });
			  $('#search_id').click(function(){
				  $('#S_CAR_OWNER_NAME').val("");
				  $(this).removeClass('treeNone').addClass('treeOn');
				  $('#add_id').removeClass('treeOn').addClass('treeNone');
				  $('#edit_id').removeClass('treeOn').addClass('treeNone');
				  $('#search_tab').show();
				  $('#add_tab').hide();
				  $('#edit_tab').hide();
			  });
			  $('#edit_id').click(function(){
				  $(this).removeClass('treeNone').addClass('treeOn');
				  $('#add_id').removeClass('treeOn').addClass('treeNone');
				  $('#search_id').removeClass('treeOn').addClass('treeNone');
				  $('#edit_tab').show();
				  $('#add_tab').hide();
				  $('#search_tab').hide();
			  });
			  //折叠panel
			  $('.panel-table .panel-title div').toggle(function(){
				  $(this).removeClass('PANEL_OPEN_BUTTON').addClass('PANEL_CLOSE_BUTTON');
				  $('.panel-table .panel-body').hide();	  
			  }, function(){
				  $(this).removeClass('PANEL_CLOSE_BUTTON').addClass('PANEL_OPEN_BUTTON');
				  $('.panel-table .panel-body').show();	  
			  });
			  
			  $('.select_user').click(function(e){
				  e.preventDefault();
				  //windowDialog("人员选择","${ctx}/app/visitor/frontReg/respondentsTreeSelectForIframe.act?state=notAll",'500','600');
				  windowDialog("人员选择","${ctx}/app/property/parkingInfo/popup/selectUser.jsp?state=notAll",'500','600');
			  });
			
			  //控制通道radio
			  var car1_3_time_range = [["00:00:00", "05:59:59", "1"], ["06:00:00", "11:59:59", "1"], ["12:00:00", "17:59:59", "1"], ["18:00:00", "23:59:59", "1"]],
			      car4_time_range = [["18:30:00", "07:30:00", "1"], ["07:30:01", "10:59:59", "0"], ["11:00:00", "11:59:59", "0"], ["12:00:00", "18:29:59", "0"]];
			  
			  $('.chk').click(function(){
				var currId = $(this).attr('id');
			    if(currId == 'car_1' || currId == 'car_2' || currId == 'car_3') {
			    	$('#A_START_TIME_1').val(car1_3_time_range[0][0]);
			    	$('#A_END_TIME_1').val(car1_3_time_range[0][1]);
			    	$('#A_TIME_1_USE_FLAG')[0].checked = true;
			    	$('#A_START_TIME_2').val(car1_3_time_range[1][0]);
			    	$('#A_END_TIME_2').val(car1_3_time_range[1][1]);
			    	$('#A_TIME_2_USE_FLAG')[0].checked = true;
			    	$('#A_START_TIME_3').val(car1_3_time_range[2][0]);
			    	$('#A_END_TIME_3').val(car1_3_time_range[2][1]);
			    	$('#A_TIME_3_USE_FLAG')[0].checked = true;
			    	$('#A_START_TIME_4').val(car1_3_time_range[3][0]);
			    	$('#A_END_TIME_4').val(car1_3_time_range[3][1]);
			    	$('#A_TIME_4_USE_FLAG')[0].checked = true;
			    }else{
			    	$('#A_START_TIME_1').val(car4_time_range[0][0]);
			    	$('#A_END_TIME_1').val(car4_time_range[0][1]);
			    	$('#A_TIME_1_USE_FLAG')[0].checked = true;
			    	$('#A_START_TIME_2').val(car4_time_range[1][0]);
			    	$('#A_END_TIME_2').val(car4_time_range[1][1]);
			    	$('#A_TIME_2_USE_FLAG')[0].checked = false;
			    	$('#A_START_TIME_3').val(car4_time_range[2][0]);
			    	$('#A_END_TIME_3').val(car4_time_range[2][1]);
			    	$('#A_TIME_3_USE_FLAG')[0].checked = false;
			    	$('#A_START_TIME_4').val(car4_time_range[3][0]);
			    	$('#A_END_TIME_4').val(car4_time_range[3][1]);
			    	$('#A_TIME_4_USE_FLAG')[0].checked = false;
			    }
			  });
			  
			  //控制通道check
			  /* var channelInfo,
			      channels;
			  $('.chk').click(function(e){
				  var $this = $(this),
				      val = $this.val(),
				      parent = $this.parent().parent(),
				      otherChks = parent.find('input.s-chk'),
				      channelInfo = parent.find('input[type="hidden"]'),
				  	  channels = channelInfo.val().split(',');
				  	  
				
			    if(this.checked){
				     if($(this).hasClass('chk-all')){//全选
				    	 channels = [];
				    	 otherChks.attr('disabled', 'disabled');
				    	 otherChks.each(function(){this.checked = false;})
				    	 if((channels.length == 1 && channels[0] == "") || channels.indexOf(val) == -1){
					       channels.push(val);
					     }
				     }else{
				    	 otherChks.removeAttr('disabled');
				    	 if((channels.length == 1 && channels[0] == "") || channels.indexOf(val) == -1){
					    	 channels.push(val);
					     }
				     }
			    }else{
			     if($(this).hasClass('chk-all')){//取消全选
				    otherChks.removeAttr('disabled');	
			     	channels = [];
				 }else{
					if(channels.indexOf(val) > -1){
				      channels.splice(channels.indexOf(val), 1);
				    }
				 }
			    }
			    
				if(parent.find('input.s-chk:checked').length == 6){
					parent.find('input.chk-all')[0].checked = true;
					parent.find('input.s-chk:checked').each(function(){this.checked = false; $(this).attr('disabled', 'disabled');})
					channelInfo.val("0");
				}else{
					if(parent.find('input.chk-all')[0].checked){
						parent.find('input.s-chk').each(function(){this.checked = false; $(this).attr('disabled', 'disabled');})
					}else{
						parent.find('input.s-chk').each(function(){$(this).removeAttr('disabled');})
					}
					channelInfo.val(_.compact(channels).join(','));
				}
				
			    
			  }); */
			  
			  var table;
			  
			  $('.datepicker').click(function(){
				  WdatePicker({dateFmt:'yyyy-MM-dd'});
			  });
			  
			  $('.time-range').timepicker({
			        timeFormat: 'HH:mm:ss',
			        // year, month, day and seconds are not important
			        minTime: new Date(0, 0, 0, 0, 0, 0),
			        maxTime: new Date(0, 0, 0, 23, 59, 59),
			        // time entries start being generated at 6AM but the plugin 
			        // shows only those within the [minTime, maxTime] interval
			        startHour: 6,
			        // the value of the first item in the dropdown, when the input
			        // field is empty. This overrides the startHour and startMinute 
			        // options
			        startTime: new Date(0, 0, 0, 0, 0, 0),
			        // items in the dropdown are separated by at interval minutes
			        interval: 15,
			        change: function(time) {
			            // the input field
			            var element = $(this), text;
			            var id = element.attr('id')
			            // get access to this TimePicker instance
			            var one = Date.parse('2000-01-01 '+element.timepicker().format(time));
						var another;
			            if(id.indexOf('START') != -1) {
			            	var near = element.next();//$('#'+id.replace('START', 'END'));
			            	if(near.val() !== ''){
			            		another = Date.parse('2000-01-01 '+near.val());
			            		if(one >= another){
			            			alert("时间段开始时间不能大于结束时间");
			            			//near.val("");
									element.addClass('time-error');
									near.addClass('time-error');
			            		}else{
			            			element.removeClass('time-error');
			            			near.removeClass('time-error');
			            		}
			            	}
			            }else{
			            	var near = element.prev();//$('#'+id.replace('END', 'START'));
			            	if(near.val() !== ''){
			            		another = Date.parse('2000-01-01 '+near.val());
			            		if(one <= another){
			            			alert("时间段开始时间不能大于结束时间");
			            			//near.val("");
			            			element.addClass('time-error');
			            			near.addClass('time-error');
			            		}else{
			            			element.removeClass('time-error');
			            			near.removeClass('time-error');
			            		}
			            	}
			            }
			            
			     
			        }
			   });

			  //编辑
			  var editParking = function(){
				  $('.chk').click(function(){
						var currId = $(this).attr('id');
					    if(currId == 'car_1' || currId == 'car_2' || currId == 'car_3') {
					    	$('#E_START_TIME_1').val(car1_3_time_range[0][0]);
					    	$('#E_END_TIME_1').val(car1_3_time_range[0][1]);
					    	$('#E_TIME_1_USE_FLAG')[0].checked = true;
					    	$('#E_START_TIME_2').val(car1_3_time_range[1][0]);
					    	$('#E_END_TIME_2').val(car1_3_time_range[1][1]);
					    	$('#E_TIME_2_USE_FLAG')[0].checked = true;
					    	$('#E_START_TIME_3').val(car1_3_time_range[2][0]);
					    	$('#E_END_TIME_3').val(car1_3_time_range[2][1]);
					    	$('#E_TIME_3_USE_FLAG')[0].checked = true;
					    	$('#E_START_TIME_4').val(car1_3_time_range[3][0]);
					    	$('#E_END_TIME_4').val(car1_3_time_range[3][1]);
					    	$('#E_TIME_4_USE_FLAG')[0].checked = true;
					    }else{
					    	$('#E_START_TIME_1').val(car4_time_range[0][0]);
					    	$('#E_END_TIME_1').val(car4_time_range[0][1]);
					    	$('#E_TIME_1_USE_FLAG')[0].checked = true;
					    	$('#E_START_TIME_2').val(car4_time_range[1][0]);
					    	$('#E_END_TIME_2').val(car4_time_range[1][1]);
					    	$('#E_TIME_2_USE_FLAG')[0].checked = false;
					    	$('#E_START_TIME_3').val(car4_time_range[2][0]);
					    	$('#E_END_TIME_3').val(car4_time_range[2][1]);
					    	$('#E_TIME_3_USE_FLAG')[0].checked = false;
					    	$('#E_START_TIME_4').val(car4_time_range[3][0]);
					    	$('#E_END_TIME_4').val(car4_time_range[3][1]);
					    	$('#E_TIME_4_USE_FLAG')[0].checked = false;
					    }
				  });
				  
				  $('.edit-parking').click(function(){
					  $('#edit_parking_f')[0].reset();
					  $('#edit_id').trigger('click');
					  var data = table.row($(this).parents('tr')[0]).data();
					  
					  //alert(data["LOG_ID"] + " | " + data["CAR_NO"]);
					  
					  //车主的名字不能修改
					  //$('#E_CAR_OWNER_NAME').val(data["CAR_OWNER_NAME"]).attr('disabled', 'disabled');
					  $('#E_CAR_OWNER_NAME').val(data["CAR_OWNER_NAME"]);
					  //车牌如果修改的话，属于转让车位，管理员需要审核
					  $('#E_CAR_NO').val(data["CAR_NO"]);
					  $('#E_CAR_OWNER_PHONE').val(data["CAR_OWNER_PHONE"]);
					  var date = data["START_DATE_AND_END_DATE"].split('~');
					  $('#E_START_DATE').val(date[0]);
					  $('#E_END_DATE').val(date[1]);
					  //$('#E_START_DATE').val(date[0]).attr('disabled', 'disabled');
					  //$('#E_END_DATE').val(date[1]).attr('disabled', 'disabled');
					  var channel = data["CAR_CHANNEL_INFO"];// 0|1|2|3|4|5|6|7|8|9|10
					  //console.log(channel)
					  if(channel === '0'){
					    $('#edit_parking_f .chk')[0].checked = true;
					  }else if(channel === "2|3|4|5|6|7|8|9|10"){
						$('#edit_parking_f .chk')[1].checked = true;  
					  }else if(channel === "2|5|6"){
						$('#edit_parking_f .chk')[2].checked = true;
					  }else{
						$('#edit_parking_f .chk')[3].checked = true;
					  }
					  $('#E_CAR_CHANNEL_INFO').val(channel);
			/* 			$('#edit_parking_f .chk-all')[0].checked = false;
						var group = data["CAR_CHANNEL_INFO"].split('|');
						var vals = [];
		            	if(group.indexOf('北门1号岗入口1') > -1){
		            		$('#edit_parking_f input.chk-1')[0].checked = true;
		            		vals.push('1');
		            	}
						if(group.indexOf('北门1号岗入口2') > -1){
		            		$('#edit_parking_f input.chk-2')[0].checked = true;
		            		vals.push('2');
		            	}
						if(group.indexOf('南门地面安全岛入口') > -1){
		            		$('#edit_parking_f input.chk-3-4')[0].checked = true;
		            		vals.push('3|4');
		            	}
						if(group.indexOf('E座地面安全岛入口') > -1){
		            		$('#edit_parking_f input.chk-5-6')[0].checked = true;
		            		vals.push('5|6');
		            	}
						if(group.indexOf('南门3号岗入口') > -1){
		            		$('#edit_parking_f input.chk-7-8')[0].checked = true;
		            		vals.push('7|8');
		            	}
						if(group.indexOf('北门2号岗入口') > -1){
		            		$('#edit_parking_f input.chk-9-10')[0].checked = true;
		            		vals.push('9|10');
		            	}
						$('#E_CAR_CHANNEL_INFO').val(vals.join(',')); */
					  
					  var times = _.map(data["START_TIME_AND_END_TIME"], function(e){return _.values(e)});
					  
					  $('#E_START_TIME_1').val(times[0][1]);
					  $('#E_END_TIME_1').val(times[0][0]);
					  if(times[0][2] === "1"){
						  $('#E_TIME_1_USE_FLAG')[0].checked = true;
					  }
					  $('#E_START_TIME_2').val(times[1][1]);
					  $('#E_END_TIME_2').val(times[1][0]);
					  if(times[1][2] === "1"){
						  $('#E_TIME_2_USE_FLAG')[0].checked = true;
					  }
					  $('#E_START_TIME_3').val(times[2][1]);
					  $('#E_END_TIME_3').val(times[2][0]);
					  if(times[2][2] === "1"){
						  $('#E_TIME_3_USE_FLAG')[0].checked = true;
					  }
					  $('#E_START_TIME_4').val(times[3][1]);
					  $('#E_END_TIME_4').val(times[3][0]);
					  if(times[3][2] === "1"){
						  $('#E_TIME_4_USE_FLAG')[0].checked = true;
					  }
					  //记住原始车牌信息
					  $('#edit_parking_f #old_car_no').val($('#E_CAR_NO').val());
				  });
			  }
			  
			  //删除
			  var removeParking = function(){
				  $('.remove-parking').click(function(){
					  var carNo = $(this).attr('id').split('_')[1];
					  $.ajax({
				    		type: "POST",
				    		url: '/park/delParkLicense',
				    		dataType: "json",
				    		data: {CAR_NO: carNo},
				    		success: function(res){
				    		  if(res["stat"] === 'success'){
				    			  alert('车位删除成功');
				    			  table.ajax.reload();
				    			  showParkingNum();
				    		  }else{
				    			  alert(res["content"]);
				    		  }
				    		},
				    		error: function(res){
				    		  alert('车位删除失败，请联系技术人员');
				    		}
				      });
				  })
			  }
			  
			  //验证时间段
			  var validTimeRange = function(timeArr){
				  var result = true;
			
				  switch(timeArr.length){
				      case 2:
				    	  result = true;
				    	  break;
				      case 4:
					      if((timeArr[2] > timeArr[0] && timeArr[2] < timeArr[1]) || (timeArr[3] > timeArr[0] && timeArr[3] < timeArr[1])){
					    	  result = false;
					      }else if((timeArr[0] > timeArr[2] && timeArr[0] < timeArr[3]) || (timeArr[1] > timeArr[2] && timeArr[1] < timeArr[3])){
					    	  result = false;
					      }else{
					    	  result = true;
					      }
					      break;
				      case 6:
				    	  var rangeGroup = [ 
				    		  [timeArr[0], timeArr[1]],
				    		  [timeArr[2], timeArr[3]],
				    		  [timeArr[4], timeArr[5]]
				    		  ];
	
				    	  var timesGroup =[ 
				    		  [timeArr[2], timeArr[3], timeArr[4], timeArr[5]],
				    		  [timeArr[0], timeArr[1], timeArr[4], timeArr[5]],
				    		  [timeArr[0], timeArr[1], timeArr[2], timeArr[3]]
				    		  ];
				    	  
				    	  var times = 3;
				    	  
				    	  for(var i = 0; i < times; i++){
				    	    for(var j = 0; j < timesGroup[i].length; j++){
				    		  if(timesGroup[i][j] > rangeGroup[i][0] && timesGroup[i][j] < rangeGroup[i][1]){
				    			result = false;
				    			break;
				    		  }
				    	    }
				    	    if(!result){
				    	    	break;
				    	    }
				    	  }
			
				    	  break;	    	  
				      case 8:
				    	  var rangeGroup = [ 
							  [timeArr[0], timeArr[1]],
							  [timeArr[2], timeArr[3]],
							  [timeArr[4], timeArr[5]],
							  [timeArr[6], timeArr[7]]
							  ];
					
						  var timesGroup =[ 
							 [timeArr[2], timeArr[3], timeArr[4], timeArr[5], timeArr[6], timeArr[7]],
							 [timeArr[0], timeArr[1], timeArr[4], timeArr[5], timeArr[6], timeArr[7]],
						     [timeArr[0], timeArr[1], timeArr[2], timeArr[3], timeArr[6], timeArr[7]],
						     [timeArr[0], timeArr[1], timeArr[2], timeArr[3], timeArr[4], timeArr[5]]
							 ];
								    	  
						  var times = 4;  
						  for(var i = 0; i < times; i++){
							for(var j = 0; j < timesGroup[i].length; j++){
							  if(timesGroup[i][j] > rangeGroup[i][0] && timesGroup[i][j] < rangeGroup[i][1]){
								 result = false;
								 break;    		  
							  }
								    	   
							}
							if(!result){
							  break;
							}
						  }
						  break;
					  default:
						  result = true;
				  }
				  return result;
			  }
			  
			  var jsonpReq = function(url, from){
				  var emptyArr = [];
				  var formData;
				  
				  if(from === 'A_'){//添加
					  /* if(!isLicenseNo($('#A_CAR_OWNER_NAME').val())){
					  	alert("车牌号码不正确");
					  	return false;
					  } */
					  
					  if($('#A_CAR_NO').val().length != 7){
						  alert('车牌录入有误');
						  return;
					  }
					  
					  $('#add_panel input.required').each(function(e){
					      if($(this).val() === ''){
					    	  emptyArr.push($(this).attr('title'));
					      }
					   });
					  if(emptyArr.length){
					    	alert(emptyArr.join(',') + ' 是必填字段');
					    	return false;
					  }
					  if($('#add_panel input.chk:checked').length == 0){
					    	alert('通道至少选择一项');
					    	return false;
					  }
					  
					  //$('#action_type').val("action_add");					  
					  //$('#A_CAR_CHANNEL_INFO').val(_.compact($('#A_CAR_CHANNEL_INFO').val().split(',')).join('|'));
					  formData = $('#parking_f').serialize();					  
				  }else{//编辑
					  
					  /* if(!isLicenseNo($('#E_CAR_OWNER_NAME').val())){
						  	alert("车牌号码不正确");
						  	return false;
					  } */
					  /* if($('#edit_parking_f #old_car_no').val() != $('#E_CAR_NO').val()){
						  //车牌变更了
					    $('#edit_parking_f #new_car_no').val($('#E_CAR_NO').val());
					  }else{
						$('#edit_parking_f #new_car_no').val($('#E_CAR_NO').val());
					  } */
					  $('#edit_parking_f #new_car_no').val($('#E_CAR_NO').val());
					  $('#edit_panel input.required').each(function(e){
					      if($(this).val() === ''){
					    	  emptyArr.push($(this).attr('title'));
					      }
					   });
					  if(emptyArr.length){
					    	alert(emptyArr.join(',') + ' 是必填字段');
					    	return false;
					  }
					  if($('#edit_panel input.chk:checked').length == 0){
					    	alert('通道至少选择一项');
					    	return false;
					  };
					  //$('#E_CAR_CHANNEL_INFO').val($('#E_CAR_CHANNEL_INFO').val().split(',').join('|'))
					  //$('#action_type').val("action_edit");
					  formData = $('#edit_parking_f').serialize();
				  }
				  var dataArr = formData.split('&');
				  //alert(formData)
				  formData = decodeURIComponent((_.compact(_.map(dataArr, function(e){if(e.split('=')[1] == ""){return false}else{return e}}))).join('&'));
				  //alert(formData)
				  
				  if(Date.parse($('#'+from + 'START_DATE').val()) > Date.parse($('#'+from + 'END_DATE').val())){
				    	alert("有效期范围不合法");
				    	return false;
				  }
				    
				    
				    var time1 = [$('#'+from + 'START_TIME_1').val(), $('#'+from + 'END_TIME_1').val()],
				    	time2 = [$('#'+from + 'START_TIME_2').val(), $('#'+from + 'END_TIME_2').val()],
				    	time3 = [$('#'+from + 'START_TIME_3').val(), $('#'+from + 'END_TIME_3').val()],
				    	time4 = [$('#'+from + 'START_TIME_4').val(), $('#'+from + 'END_TIME_4').val()],
				    	time1Str = time1.join(""),
				    	time2Str = time2.join(""),
				    	time3Str = time3.join(""),
				    	time4Str = time4.join("");

				    var timeArr = [time1[0], time1[1], time2[0], time2[1], time3[0], time3[1], time4[0], time4[1]],
				    	compactTime = _.compact(timeArr);
				    
				    if(from === 'A_'){
					    if($('#add_panel .time-range').hasClass('time-error')){
					    	alert("存在不合法的时间段");
					    	return false;
					    }
				    }else{
				    	if($('#edit_panel .time-range').hasClass('time-error')){
					    	alert("存在不合法的时间段");
					    	return false;
					    }
				    }
				    
				    if(time1Str == '' && time1Str == '' && time1Str == '' && time1Str == ''){
				    	alert('时间段至少配置一项');
				    	return false;
				    }else if(_.compact(time1).length == 1 || _.compact(time2).length == 1 || _.compact(time3).length == 1 || _.compact(time4).length == 1){
				    	alert('时间段配置必须成对');
				    	return false;
				    }else if (_.uniq(compactTime).length < compactTime.length){
				    	alert('时间段配置出现重叠');
				    	return false;
				    }else if (!validTimeRange(compactTime)){
				    	alert('时间段配置出现冲突');
				    	return false;
				    }else{
				    	$.ajax({
				    		type: "POST",
				    		async: false,
				    		url: url,
				    		dataType: "json",
				    		data: formData,
				    		//jsonp: "jsonpCallback",
				    		//jsonpCallback: 'success_jsonpCallback',
				    		//contentType: "application/jsonp; charset=utf-8",
				    		success: function(res){
				    		  if(res["stat"] === 'success'){
				    			  alert('车位设置成功');
				    			  table.ajax.reload();
				    		  }else{
				    			  //console.log(res["content"])
				    			  alert(res["content"]);
				    		  }
				    		},
				    		error: function(res){
				    		 /*  alert(res["responseText"])
				    		  console.log(res["responseText"]) */
				    		  JSON.stringify(res["responseText"])
				    		  var result = JSON.parse(res["responseText"]);
				    		  if(result["content"]){
				    			  alert(result["content"]);
				    		  }else{
				    			  alert('车位设置失败，请联系技术人员');  
				    		  }
				    		}
				    	});
				    }
				    
			  }
				
			  table = $('#dep_parking_t').DataTable({
			    	"sDom": '<"title">t<"bottom"p><"clear">', //'<"top"i><"title">lt<"bottom"p><"clear">'
			         "columns": [
			           { "data": "ORG_NAME"},
			           { "data": "DEPARTMENT"},
			           { "data": "CAR_OWNER_NAME" },
			           { "data": "CAR_NO" },
			           { "data": "CAR_OWNER_PHONE" },
			           { "data": "CAR_CHANNEL_INFO" },
			           { "data": "START_TIME_AND_END_TIME" },
			           { "data": "START_DATE_AND_END_DATE" },
			           { "data": "STATUS" },
			           { "data": "CAR_NO" },
			         ],
			        "bFilter": true,
				    "processing": true,
				    "oLanguage": {
				    	"sProcessing": "正在加载中......",
				    	"sLengthMenu": "每页显示 _MENU_ 条记录",
				    	"sZeroRecords": "查询无数据！",
				    	"sEmptyTable": "查询无数据！",
				    	"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				    	"sInfoEmpty": "显示0到0条记录",
				    	"sInfoFiltered": "数据表中共为 _MAX_ 条记录",
				    	"sSearch": "当前数据搜索",
				    	"oPaginate": {
					     "sFirst": "<span><<</span>",
					     "sPrevious": "<span><</span>",
				    	 "sNext": "<span>></span>",
				    	 "sLast": "<span>>></span>"
				    	}
				    },
				    "serverSide": true, 
				    "searchable": true,
				    "sServerMethod": "POST",
				    "bSort": false,
				    "sPaginationType": "full_numbers",
				    "pageLength": 7,
				    "bAutoWidth": false,
				    "aoColumnDefs": [
						{ "sWidth": "80px",  "aTargets": [0], "visible": false},
						{ "sWidth": "80px",  "aTargets": [1], "visible": false},
				        { "sWidth": "80px",  "aTargets": [2] },
				        { "sWidth": "120px", "aTargets": [3] },
				        { "sWidth": "80px",  "aTargets": [4] },
				        { "sWidth": "250px", "aTargets": [5] },
				        { "sWidth": "200px", "aTargets": [6] },
				        { "sWidth": "150px", "aTargets": [7] },
				        { "sWidth": "80px", "aTargets": [8] },
				        { "sWidth": "60px",  "aTargets": [3] },
				    ],
 				    "ajax": {
				        "url": "/park/getParkLicenseList", //"/park/getParkLicenseList",
				        "dataType": "json",
				        "data": function ( d ) {
				            /*       	
				            return $.extend( {}, d, {
				              "extra_search": $('#extra').val()
				            } ); */
				        	return {
				        		draw: d["draw"],
				        		start: d["start"],
				        		length: d["length"],
				        		search: d["search"]["value"],
				        		setParkings: 1
				        	}
				         }
				    },
				    "fnInitComplete": function() {
				    	var oListSettings = this.fnSettings();
				        var wrapper = this.parent();
				    	$('#dep_parking_t_length').hide();
				    },
				    "drawCallback": function(){
				    	$('#dep_parking_t_length').hide();
				    	editParking();
				    	removeParking();
				    },
				    "createdRow": function ( row, data, index ) {
				    	//出入口
			        	var output = "";
			        	if(data["CAR_CHANNEL_INFO"] == '0') {
			        	  	output = "<span class='e-info'>公车</span>";
			        	}else if (data["CAR_CHANNEL_INFO"] == '2|3|4|5|6|7|8|9|10') {
			        		output = "<span class='e-info'>ABCD私车</span>";
			        	    /* var group = data["CAR_CHANNEL_INFO"].split('|'),
			            	      len = group.length,
			            	   length = len;
				            while(len--){
				              output += "<span class='e-info'>"+group[length-len-1]+"</span>";
				              if(len % 2 == 0){
				            	output += "<br/>";
				              }
				            } */
			        	}else if (data["CAR_CHANNEL_INFO"] == '2|5|6') {
			        		output = "<span class='e-info'>E座私车</span>";
			        	}else{
			        		output = "<span class='e-info'>环道车辆</span>";
			        	}
			            $('td', row).eq(3).html(output);
			          
			            //时间段
			            var times = _.map(data["START_TIME_AND_END_TIME"], function(e){return _.values(e)}),
		            	    len2 = times.length,
		            	    length2 = len2,
		            	    output2 = "";
		            	while(len2--){
		            		var index = length2-len2;
		            		if(times[index - 1][2] == '1'){
		            			output2 += "<span class='t-info'>"+times[index - 1][1] + ' ~ ' + times[index - 1][0] +"</span>";
		            		}else{
		            			output2 += "<span class='t-info not-used'>"+times[index - 1][1] + ' ~ ' + times[index - 1][0] +"</span>";
		            		}
		            			
		             		if(length2 >=2  && len2 % 2 == 0){
		            			output2 += "<br/>"
		            		}
		            	}
		            	$('td', row).eq(4).html(output2);
		            	
		            	//有效期
		            	$('td', row).eq(5).html("<span class='t-info'>"+data["START_DATE_AND_END_DATE"]+"</span>");
		            	//状态
		            	var status = "";
		            	switch(data["STATUS"]){
		            	case '0':
		            		status = "审核通过";
		            		break;
		            	case '1':
		            		status = '等待审核';
		            		break;
		            	case '2':
		            		status = '审核拒绝';
		            		break;
		            	default:
		            		status = '未知';
		            	}
		            	$('td', row).eq(6).html(status);
		            	//操作
		            	$('td', row).eq(7).html('<div class="actions"><div class="table_btn1 btn-sm edit-parking" id="edit_'+data["CAR_NO"]+'">编辑</div> <div class="table_btn1 btn-sm remove-parking" id="remove_'+data["CAR_NO"]+'">删除</div></div>');
			            
			        }
			    });
			  	
			    //添加
			  	$('#add_btn').click(function(){
					 jsonpReq("/park/setParkLicense", 'A_');
					 showParkingNum();
				});
				
			    //编辑保存
				$('#save_btn').click(function(){
					 jsonpReq("/park/setParkLicense", 'E_'); 
				});
			    
			    
				  
				$('#search_btn').click(function(){
					//var searchStr = "\"";
					var searchStr = "";
					
					if($('#S_CAR_OWNER_NAME').val() !== ''){
						if(searchStr.length == 1){
							searchStr += "CAR_OWNER_NAME="+$('#S_CAR_OWNER_NAME').val();	
						}else{
							searchStr += "&CAR_OWNER_NAME="+$('#S_CAR_OWNER_NAME').val();	
						}
						//alert(searchStr);
					}
					if($('#S_CAR_NO').val() !== ''){
						if(searchStr.length == 1){
							searchStr += "CAR_NO="+$('#S_CAR_NO').val();	
						}else{
							searchStr += "&CAR_NO="+$('#S_CAR_NO').val();	
						}
					}
					if($('#S_CAR_OWNER_PHONE').val() !== ''){
						if(searchStr.length == 1){
							searchStr += "CAR_OWNER_PHONE="+$('#S_CAR_OWNER_PHONE').val();	
						}else{
							searchStr += "&CAR_OWNER_PHONE="+$('#S_CAR_OWNER_PHONE').val();	
						}
					}
					if($('#S_START_DATE').val() !== ''){
						if(searchStr.length == 1){
							searchStr += "START_DATE="+$('#S_START_DATE').val();	
						}else{
							searchStr += "&START_DATE="+$('#S_START_DATE').val();	
						}
					}
					if($('#S_END_DATE').val() !== ''){
						if(searchStr.length == 1){
							searchStr += "END_DATE="+$('#S_END_DATE').val();	
						}else{
							searchStr += "&END_DATE="+$('#S_END_DATE').val();	
						}
					}
					if($('#S_STATUS').val() !== ''){
						if(searchStr.length == 1){
							searchStr += "STATUS="+$('#S_STATUS').val();	
						}else{
							searchStr += "&STATUS="+$('#S_STATUS').val();	
						}
					}
					//searchStr += "\"";
					table.search(searchStr).draw();
				  });
				  
				  $('#reset_btn').click(function(){
					 $('#search_form')[0].reset();
					 table.search("").draw();
				  })
			  	
		  });
		</script>
		<style>
			.table_btn1
			{
			    width: 68px;
			    height: 29px;
			    background:#8090b2;
				color:#fff;
				line-height:29px;
				text-align:center;
				font-family:"微软雅黑";
				margin-top:10px;
				margin-right:0px;
				float:left;
				cursor:pointer;
			}
			#dep_parking_t {
				border-bottom: none;
			}
			.dataTables_info {
				background: #f6f5f1 !important;
				width: 100%;
				color: #808080 !important;
				/* padding: 2px 10px; */
				padding: 5px auto;
			}
			.dataTables_info {
				color: #808080;
			    font-size: 10pt;
			    font-weight: bold;
			}
			table.dataTable thead {
				background: #ecece4;
				color: #6699cc;
			}
			table.dataTable thead th {
				font-size: 14px;
				text-align: center;
				border-bottom: none;
			}
			table.dataTable tbody tr {
				background: #f6f5f1;
			}
			
			table.dataTable tbody tr td {
				text-align: center;
				color: #808080;
				border-bottom: 1px dotted #c7c6c2;
			}
			.bottom {
				text-align: center !important;
			}
			div.dataTables_paginate {
			    float: left !important;
			    margin: 0 auto;
			    background: #E0E3EA;
			    width: 100%;
			    text-align: center !important;
			}
			
			.dataTables_paginate .paginate_button{
			  width: 24px;
			  height: 24px;
			  line-height: 24px;
			  display: inline-block;
			  background: #98a6be;
			  margin: 5px;
			  text-decoration: none;
			  font-weight: bold;
			  overflow: hidden;
			  text-align: center;
			}
			.dataTables_paginate .current, .dataTables_paginate .current {
				margin: 5px;
				color: #fff !important;
				text-decoration: none;
				text-align: center;
				background: #ccc !important;
			}
			.e-info {
				background: #1abc9c;
				color: #fff;
				padding: 2px;
				margin: 2px 5px;
				display: inline-block;
				width: 108px;
			}
			.t-info {
				background: #1abc9c;
				color: #fff;
				padding: 2px;
				margin: 2px 5px;
				display: inline-block;
			}
			
			  .main_conter, .main_main2{
		  	padding-top:1px;
		  	border: none;
		  }
			.main_main1 {
			   	height:32px;
				margin-top:10px;
				background:url(${ctx}/app/property/parkingInfo/images/border_00_3.jpg) left bottom repeat-x;
		    }
		   
		   .main_main1 li {
		  	  width: 100px;
		   }
		    /* .main_main1 li a {
		      width: 100px;
		      background: url(${ctx}/app/property/parkingInfo/images/bg.png) left top no-repeat;
		     }
		     .main_main1 li a:hover {
		  	   width: 100px;
		  	   background: url(${ctx}/app/property/parkingInfo/images/bg_h.png) left top no-repeat;
		      } */
		  	#parking_nav {
		  		height: 31px;
		  	}
			
			  
			  #parking_nav .treeNone a {
			  	display:block;
				background:url(${ctx}/app/property/parkingInfo/images/bg.png) left top no-repeat;
				line-height:32px; 
				text-align:center; 
				text-decoration:none; 
				font-family:"微软雅黑"; 
				font-size:14px; 
				font-weight:bold; 
				color:#808080;
			  }
			  #parking_nav .treeOn a, #parking_nav li a:hover {
			  	display:block;
			    width: 100px;
			    background: url(${ctx}/app/property/parkingInfo/images/bg_h.png) left top no-repeat;
				line-height: 32px;
				text-align: center;
				text-decoration: none;
				font-family: "微软雅黑";
				font-size: 14px;
				font-weight: bold;
				color: #fff;
			  }
			  .not-used {
			  	background: #8e8e8e;
			  }
			  .btn-sm {
			  	width: 48px;
			  	height: 26px;
			  	margin-right: 5px;
			  }
			  .actions {
			  	width: 108px;
			  	margin: 0 auto;
			  	height: 30px;
			  }
			  .chk, .flag-chk {
			  	height: 13px !important;
			  }
			  .time-part {
			  	display: inline-block;
			  	margin-right: 20px;
			  }
			  .chk-label {
			  	width: 70px;
			  	margin: auto 5px;
			  }
			  .chk-label:hover {
			  	cursor: pointer;
			  }
			  #save_btn, #add_btn, #search_btn {
			  	float: right;
			  }
			  .time-error {
			  	background: rgb(255, 240, 240);
			  }
			  
			  .select_user {
			  	cursor:pointer;
			  }
		</style>
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;" id="set_parking_doc">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						车位管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px;height: auto;">
					
				
				 <div class="main_conter">
				  <div class="main_main1" id="parking_nav">
				    <ul>
				     <li class="treeOn" id="search_id"><a style="cursor: pointer;">搜索</a></li>
				     <li class="treeNone" id="add_id"><a style="cursor: pointer;">添加</a></li>
				     <li class="treeNone" id="edit_id"><a style="cursor: pointer;">编辑</a></li>               
				    </ul>	
				  </div>
				 </div>
				 
				 <div>
				  	<div class="main_main2" id="search_tab" style="display: block;">
				  	  <h:panel id="query_panel" width="100%" title="查询条件">
				  	    <form id="search_form">
					    <table align="center"  id="search_table" border="0" width="100%" class="form_table">
							<tbody>
								<tr>
									<td class="form_label" style="width:5%;text-align:right;">
									  <span>姓名</span>				
									</td>
									<td style="width:6%">
									  <input name="CAR_OWNER_NAME" id="S_CAR_OWNER_NAME" style="width: 120px; height: 18px;" type="text" class="required" title="姓名"/>
									  <input name="CAR_OWNER_USER_ID" id="S_CAR_OWNER_USER_ID" type="hidden" value="10" />
									</td>
									<td style="width:3%"><img width="22" height="18" class="select_user" src="/app/integrateservice/images/fkdj_pho.jpg"></td>
									<td style="width:5%;text-align:right;">
									  <span>车牌</span>				
									</td>
									<td style="width:6%">
									  <input name="CAR_NO" id="S_CAR_NO" style="width: 120px; height: 18px;" type="text" class="required"  title="车牌"/>
									</td>
								    <td style="width:5%;text-align:right;">
									  <span>电话号码</span>
								    </td>
									<td style="width:8%;text-align:left;">
									  <input name="CAR_OWNER_PHONE" id="S_CAR_OWNER_PHONE" style="width: 120px; height: 18px;" type="text" class="required"  title="电话号码"/>
									</td>
									<td style="width:5%;text-align:right;">
									  <span>有效期</span>				
									</td>
									<td style="width:16%">
									  <input name="START_DATE", id="S_START_DATE" style="width: 120px; height:18px;" type="text" class="datepicker"  title="有效期开始日期" /> ~ 
									  <input name="END_DATE", id="S_END_DATE" style="width: 120px; height:18px;" type="text" class="datepicker"  title="有效期结束日期"/>
									</td>
									<td style="width:5%;text-align:right;">
									  <span>状态</span>				
									</td>
									<td style="width:6%">
									  <select name="STATUS" id="S_STATUS" style="width: 120px; height: 18px; margin-right: 20px;">
										<option selected="selected"></option>
										<option value="0">审核通过</option>
										<option value="1">等待审核</option>
										<option value="2">审核拒绝</option>
									</select>
									</td>
								</tr>
								
								<tr>
									<td colspan="9" width="90%">
									</td>
									<td colspan="2" align="right" valign="middle" style="padding-right: 30px;">
										<div class="table_btn1 btn-sm" style="float: right;" id="reset_btn">重置</div>
										<div class="table_btn1 btn-sm" style="float: right;" id="search_btn">查询</div>
									</td>	
								</tr>
							</tbody>
						</table>
					  	</form>
					  </h:panel>
				  	</div>
					
				  	<div class="main_main2" id="add_tab" style="display: none;">
				  		<h:panel id="add_panel" width="100%" title="添加条件">
						  <form id="parking_f" action="#" method="post" autocomplete="off">
						    <input type="hidden" name="STATUS", value="1" />
						    <input type="hidden" name="CAR_NEW_NO", value="NO"/>
						    <input type="hidden" name="action_type" id="action_type" value="action_add"/>
						    <table align="center"  id="query_table" border="0" width="100%" class="form_table">
								<tbody>
									<tr style="height: 30px">
										<td style="width:8%;text-align:right;">
											<span>姓名</span>				
										</td>
										<td style="width:6%">
											<input name="CAR_OWNER_NAME" id="A_CAR_OWNER_NAME" style="width: 120px; height: 18px; background-color: #D8D8D8" type="text" class="required" title="姓名" readonly="readonly" />
											<input name="CAR_OWNER_USER_ID" id="A_CAR_OWNER_USER_ID" type="hidden" value="10" />
									  	</td>
									  	<td style="width:3%"><img width="22" height="18" class="select_user" src="/app/integrateservice/images/fkdj_pho.jpg"></td>
									   	<td style="width:8%;text-align:right;">
											<span>车牌</span>				
									   	</td>
									   	<td style="width:6%">
										  <input name="CAR_NO" id="A_CAR_NO" style="width:120px; height: 18px;" type="text" class="required"  title="车牌"/>
									    </td>
										<td style="width:8%;text-align:right;">
											<span>电话号码</span>
										</td>
										<td style="width:8%;text-align:left;">
										  <input name="CAR_OWNER_PHONE" id="A_CAR_OWNER_PHONE" style="width: 120px; height: 18px;" type="text" class="required"  title="电话号码"/>
										</td>
										<td style="width:8%;text-align:right;">
											<span>有效期</span>				
										</td>
										<td style="width:45%">
											<input name="START_DATE", id="A_START_DATE" style="width: 120px; height: 18px;" type="text" class="required datepicker"  title="有效期开始日期" /> ~ 
											<input name="END_DATE", id="A_END_DATE" style="width: 120px; height: 18px;" type="text" class="required datepicker"  title="有效期结束日期"/>
									 	 </td>
									</tr>
									<tr style="height: 60px;">
										<td style="width: 5%;text-align:right;">
											<span>车辆类型</span>
										</td>
										<td colspan="8">
											<table width="100%" border="0">
									  			<tr>
									  				<td colspan="7">
									  				    <!-- <input type="hidden", name="CAR_CHANNEL_INFO" id="A_CAR_CHANNEL_INFO" /> -->
									  					<label class="chk-label">
															<input value="0" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_1">
															<span style="font-size: 13px; color: #4c83ec;">公车</span>
														</label>
														<label class="chk-label">
															<input value="2|3|4|5|6|7|8|9|10" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_2">
															<span style="font-size: 13px; color: #4c83ec;">ABCD私车</span>
														</label>
														<label class="chk-label">
															<input value="2|5|6" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_3">
															<span style="font-size: 13px; color: #4c83ec;">E座私车</span>
														</label>
														<label class="chk-label">
															<input value="1|4" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_4">
															<span style="font-size: 13px; color: #4c83ec;">环道车辆</span>
														</label>
														
														<!-- <label class="chk-label">
															<input value="1" type="checkbox" class="chk chk-1 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">北门1号岗入口1</span>
														</label>
														
														<label class="chk-label">
															<input value="2" type="checkbox" class="chk chk-2 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">北门1号岗入口2</span>
														</label>
														
														<label class="chk-label">
															<input value="3|4" type="checkbox" class="chk chk-3-4 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">南门地面安全岛出入口</span>
														</label>
														
														<label class="chk-label">
															<input value="5|6" type="checkbox" class="chk chk-5-6 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">E座地面安全岛出入口</span>
														</label>
														
														<label class="chk-label">
															<input value="7|8" type="checkbox" class="chk chk-7-8 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">南门3号岗出入口</span>
														</label>
														
														<label class="chk-label">
															<input value="9|10" type="checkbox" class="chk s-chk">
															<span style="font-size: 13px; color: #4c83ec;">北门2号岗出入口</span>
														</label> -->
												    </td>
													
									  			</tr>
											</table>
										</td>
																
									</tr>
									<tr>
										<td style="width: 5%;text-align:right;">
											<span>出入时间段</span>
										</td>
										<td colspan="8">
											<table width="100%" border="0">
												<tr style="text-align:left">
													<td colspan="7" style="padding-left: 10px;">
														<span class="time-part">时间段1<input type="text" name="START_TIME_1" id="A_START_TIME_1" style="width:70px;"  class="time-range" value=""/> ~ <input value="" type="text" name="END_TIME_1" id="A_END_TIME_1" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_1_USE_FLAG" id="A_TIME_1_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													    <span class="time-part">时间段2<input type="text" name="START_TIME_2" id="A_START_TIME_2" style="width:70px;"  class="time-range" value=""/> ~ <input value="" type="text" name="END_TIME_2" id="A_END_TIME_2" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_2_USE_FLAG" id="A_TIME_2_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													    <span class="time-part">时间段3<input type="text" name="START_TIME_3" id="A_START_TIME_3" style="width:70px;"  class="time-range" value=""/> ~ <input value="" type="text" name="END_TIME_3" id="A_END_TIME_3" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_3_USE_FLAG" id="A_TIME_3_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													    <span class="time-part">时间段4<input type="text" name="START_TIME_4" id="A_START_TIME_4" style="width:70px;"  class="time-range" value=""/> ~ <input value="" type="text" name="END_TIME_4" id="A_END_TIME_4" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_4_USE_FLAG" id="A_TIME_4_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr>
										<td colspan="9" align="right" valign="middle" style="padding-right: 30px;">
											<div class="table_btn1 btn-sm" id="add_btn">添加</div>
										</td>	
									</tr>
								</tbody>
							</table>
						  </form>
						</h:panel>
				  	</div>
				
					<div class="main_main2" id="edit_tab" style="display: none;">
				  		<h:panel id="edit_panel" width="100%" title="编辑条件">
						  <form id="edit_parking_f" action="#" method="post" autocomplete="off">
						    <input type="hidden" name="STATUS" value="1" />
						    <input type="hidden" name="CAR_NO" id="old_car_no"/>
						    <input type="hidden" name="CAR_NEW_NO" id="new_car_no"/>
						    <input type="hidden" name="action_type" id="action_type" value="action_edit"/>
						    <table align="center" border="0" width="100%" class="form_table">
								<tbody>
									<tr style="height: 30px">
										<td style="width:7%;text-align:right;">
											<span>姓名</span>				
										</td>
										<td style="width:8%;">
											<input name="CAR_OWNER_NAME" id="E_CAR_OWNER_NAME" style="width: 120px; height: 18px;" type="text" class="required" title="姓名" disabled="disabled"/>
									  	</td>
									   	<td style="width: 7%;text-align:right;">
											<span>车牌</span>				
									   	</td>
									   	<td style="width:8%">
										  <input id="E_CAR_NO" style="width: 120px; height: 18px;" type="text" class="required"  title="车牌"/>
									    </td>
										<td style="width: 10%;text-align:right;">
											<span>电话号码</span>
										</td>
										<td style="width:10%;text-align:left;">
										  <input name="CAR_OWNER_PHONE" id="E_CAR_OWNER_PHONE" style="width: 120px; height: 18px;" type="text" class="required"  title="电话号码"/>
										</td>
										<td style="width:8%;text-align:right;">
											<span>有效期</span>				
										</td>
										<td style="width:45%">
											<input name="START_DATE", id="E_START_DATE" style="width: 120px; height: 18px;" type="text" class="required datepicker"  title="有效期开始日期" /> ~ 
											<input name="END_DATE", id="E_END_DATE" style="width: 120px; height: 18px;" type="text" class="required datepicker"  title="有效期结束日期"/>
									 	 </td>
									</tr>
									<tr style="height: 60px;">
										<td style="width: 5%;text-align:right;">
											<span>车辆类型</span>
										</td>
										<td colspan="8">
											<table width="100%" border="0">
									  			<tr>
									  				<td colspan="7">
									  				    <!-- <input type="hidden", name="CAR_CHANNEL_INFO" id="A_CAR_CHANNEL_INFO" /> -->
									  					<label class="chk-label">
															<input value="0" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_1">
															<span style="font-size: 13px; color: #4c83ec;">公车</span>
														</label>
														<label class="chk-label">
															<input value="2|3|4|5|6|7|8|9|10" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_2">
															<span style="font-size: 13px; color: #4c83ec;">ABCD私车</span>
														</label>
														<label class="chk-label">
															<input value="2|5|6" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_3">
															<span style="font-size: 13px; color: #4c83ec;">E座私车</span>
														</label>
														<label class="chk-label">
															<input value="1|4" type="radio" class="chk" name="CAR_CHANNEL_INFO" id="car_4">
															<span style="font-size: 13px; color: #4c83ec;">环道车辆</span>
														</label>
														
														<!-- <label class="chk-label">
															<input value="1" type="checkbox" class="chk chk-1 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">北门1号岗入口1</span>
														</label>
														
														<label class="chk-label">
															<input value="2" type="checkbox" class="chk chk-2 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">北门1号岗入口2</span>
														</label>
														
														<label class="chk-label">
															<input value="3|4" type="checkbox" class="chk chk-3-4 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">南门地面安全岛出入口</span>
														</label>
														
														<label class="chk-label">
															<input value="5|6" type="checkbox" class="chk chk-5-6 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">E座地面安全岛出入口</span>
														</label>
														
														<label class="chk-label">
															<input value="7|8" type="checkbox" class="chk chk-7-8 s-chk">
															<span style="font-size: 13px; color: #4c83ec;">南门3号岗出入口</span>
														</label>
														
														<label class="chk-label">
															<input value="9|10" type="checkbox" class="chk s-chk">
															<span style="font-size: 13px; color: #4c83ec;">北门2号岗出入口</span>
														</label> -->
												    </td>
													
									  			</tr>
											</table>
										</td>
																
									</tr>
									<tr>
										<td style="width: 5%;text-align:right;">
											<span>出入时间段</span>
										</td>
										<td colspan="8">
											<table width="100%" border="0">
												<tr style="text-align:left">
													<td colspan="7" style="padding-left: 10px;">
														<span class="time-part">时间段1<input type="text" name="START_TIME_1" id="E_START_TIME_1" style="width:70px;"  class="time-range"/> ~ <input type="text" name="END_TIME_1" id="E_END_TIME_1" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_1_USE_FLAG" id="E_TIME_1_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													    <span class="time-part">时间段2<input type="text" name="START_TIME_2" id="E_START_TIME_2" style="width:70px;"  class="time-range"/> ~ <input type="text" name="END_TIME_2" id="E_END_TIME_2" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_2_USE_FLAG" id="E_TIME_2_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													    <span class="time-part">时间段3<input type="text" name="START_TIME_3" id="E_START_TIME_3" style="width:70px;"  class="time-range"/> ~ <input type="text" name="END_TIME_3" id="E_END_TIME_3" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_3_USE_FLAG" id="E_TIME_3_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													    <span class="time-part">时间段4<input type="text" name="START_TIME_4" id="E_START_TIME_4" style="width:70px;"  class="time-range"/> ~ <input type="text" name="END_TIME_4" id="E_END_TIME_4" style="width:70px;"  class="time-range"/><label class="label-flag"><input type="checkbox" name="TIME_4_USE_FLAG" id="E_TIME_4_USE_FLAG" value="1" class="flag-chk"/>启用</label></span>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr>
										<td colspan="9" align="right" valign="middle" style="padding-right: 30px;">
											<div class="table_btn1 btn-sm" id="save_btn">保存</div>
										</td>	
									</tr>
								</tbody>
							</table>
						  </form>
						</h:panel>
				  	</div>
				 </div>
					
				<div style="color:#808080; padding:1px 5px; margin: 0 1px; font-size:12px">
				 	<span style="padding:0 0 0 5px">单位已分配车位数:</span>
				 	<label id="countAllot" ></label> 
				 	<span style="padding:0 0 0 5px;">单位可分配车位总数:</span>
				 	<label id="parkingNum"></label>
				</div>
					<!-- table list -->
					<table id="dep_parking_t" class="pt" cellspacing="0" width="100%">
					    <thead>
					        <tr>
					        	<th>单位</th>
					            <th>处室</th>
					            <th>姓名</th>
					            <th>车牌</th>
					            <th>电话号码</th>
					            <th>通道</th>
					            <th>时间段</th>
					            <th>有效期</th>
					            <th>状态</th>
					            <th>操作</th>
					        </tr>
					    </thead>
					</table>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
	</body>
</html>