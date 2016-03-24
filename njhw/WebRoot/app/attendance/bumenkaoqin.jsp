<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ page import="java.util.*,java.lang.*,com.opensymphony.xwork2.util.*" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="/common/include/meta.jsp"%>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
       <link href="${ctx}/styles/property/css.css" rel="stylesheet"		type="text/css" />
	<link href="${ctx}/styles/property/block.css" rel="stylesheet"		type="text/css" />
	<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet"	type="text/css" />
	<link href="${ctx}/styles/property/css_body.css" rel="stylesheet"	type="text/css" />
	
	<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="css/attendance.css" />
	<script src="js/attendance.js" type="text/javascript"></script>
	<!-- 
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>
	 -->	 
	<script src="${ctx}/scripts/ca/highcharts.js"></script>
	 <script src="${ctx}/scripts/ca/exporting.js"></script>
	<style type="text/css">

  body {
  	background: transparent;
    font-size: 13px;
    color: #808080;
    font-family: 'Microsoft YaHei', '微软雅黑','HelveticaNeue', Arial, Helvetica, sans-serif
  }
  
  .bgsgl_border_right {
    font-family: "微软雅黑";
  }
  
/*
  * Redefine Tab style
  ********************************************************************
  */
  .main_conter, .main_main2{
  	padding-top:1px;
  	border: none;
  }
  
   .main_main1 {
	   	height:32px;
		margin-top:10px;
		background:url(images/border_00_3.jpg) left bottom repeat-x;
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
  	width: 80px;
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
  

		#hide_table{
			height:40px;
			padding: 10px 0 0 30px;
		}
		
		#hide_table label{
			font-size:14px;
			color:#808080;
			margin: 0 5px 0 0;
		}
		
		#hide_table input, select{
			width:168px;
			height:22px !important;
			line-height:22px;
		}
		#realTimeCounts{
			float: left;
		    margin-top: 2px;
		}
		#counts_table tr td{
			height:10px;
			padding-left: 10px;
		}
		
	</style>
	
	<script type="text/javascript">
	
		function showAttendancesList(){
 			$('#queryForm').submit();
 		}
 		
 		function viewAttendance(attendanceId){
			var url = "${ctx}/app/pro/queryAttendanceById.act?attendanceId="+attendanceId;
			openEasyWin("viewLostFound","查看考勤详情",url,"850","400",false);
		}	
		
		function closeViewAttendance(){
			closeEasyWin("viewAttendance");
		}
		
		function exportAttendancePrepare(){
			var url = _ctx + "/app/pro/exportAttendancePrepare.act";
			openEasyWin("exportAttendancePrepare","导出考勤清单",url,"850","350",false);
		}
		
		$(document).ready(function(){
			getCharts();
			if("${opt}" == "query"){
				$('#attlist_id').removeClass('treeNone').addClass('treeOn');
				$('#attlist_tab').show();
				$('#macrostats_id').removeClass('treeOn').addClass('treeNone');
				$('#macrostats_tab').hide();
				$('#firstPageTag').css({'margin-left': '380px'});
			}else{
				$('#macrostats_id').removeClass('treeNone').addClass('treeOn');
				$('#macrostats_tab').show();
				$('#attlist_id').removeClass('treeOn').addClass('treeNone');
				$('#attlist_tab').hide();
			}
			
			//显示考勤一栏
			$('#attlist_id').click(function(){
				$(this).removeClass('treeNone').addClass('treeOn');
				$('#attlist_tab').show();
				$('#macrostats_id').removeClass('treeOn').addClass('treeNone');
				$('#macrostats_tab').hide();
				$('#firstPageTag').css({'margin-left': '380px'});
			});
			
			//显示宏观统计
			$('#macrostats_id').click(function(){
				$(this).removeClass('treeNone').addClass('treeOn');
				$('#macrostats_tab').show();
				$('#attlist_id').removeClass('treeOn').addClass('treeNone');
				$('#attlist_tab').hide();
			});
			
			jQuery.validator.methods.compareDate = function(value, element, param) {
		        var beginTime = jQuery(param).val();
		        if(value!=""){
		    	    return beginTime <= value;
		        }else {
		        	return true;
		        }
		    };  
			
			// 为inputForm注册validate函数
			$("#queryForm").validate({
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				onsubmit: true,
				rules: {
					endTime:{
						compareDate: "#startTime"
					}
				},
				messages:{
					endTime:{
						compareDate: "结束时间必须晚于开始时间"
					}
				}
			});
			
			//$('#selectDepartment').val("${selectDepartment}");
			
			
			//加载内部人员实时状态（大厦之内、之外）
			$("#internalEmpCount").html("${realTimeAttendanceList[0]}");
			$("#internalEmp_inBuilding").html("${realTimeAttendanceList[1]}");
			$("#internalEmp_outOfBuilding").html("${realTimeAttendanceList[2]}");
			$("#internalEmp_unknown").html("${realTimeAttendanceList[3]}");
			
			//加载临时人员实时状态（大厦之内、之外）
			$("#tempWorkerCount").html("${realTimeAttendanceList[4]}");
			$("#tempWorker_inBuilding").html("${realTimeAttendanceList[5]}");
			$("#tempWorker_outOfBuilding").html("${realTimeAttendanceList[6]}");
			$("#tempWorker_unknown").html("${realTimeAttendanceList[7]}");
			
			
		});    
		
		function queryInfo(){
			//设置查询参数
			$("#name").val($("#name_show").val());
			$("#startTime").val($("#startTime_show").val());
			$("#endTime").val($("#endTime_show").val());
			
			var a = $("#startTime").val().replace(/-/g, "/");
			var b = $("#endTime").val().replace(/-/g, "/");
			var starttime = new Date(a);
			var lktime = new Date(b);
			if (Date.parse(starttime) - Date.parse(lktime) > 0) {
				alert('结束时间不能小于开始时间!');
		        return false;
		    }else{
		    	querySubmit();
		    }
			
		}
		
		function showRequest(){
			 return $("#queryForm").valid();
		}
		
		function popWindow(userId){
			var url = _ctx + "/app/attendance/attendanceDetail.act?userId="+userId
				+"&startTime=${startTime}"
				+"&endTime=${endTime}";
				//alert(url);
			windowDialog("考勤明细",url,"500","450",false);
		}
		
		function realTimePopWindow(scope,type){
			//type ：0 为内部人员  1为临时人员
			var url = _ctx + "/app/attendance/getRealTimeDetail.act?scope="+scope
			+"&personType=" +  type;
			windowDialog("实时状况",url,"600","500",false);
		}
		
		function gotoWodekaoqin_read(userId, userName){
			var url = _ctx + "/app/attendance/wodekaoqin_read.jsp?userId="+userId+"&userName="+encodeURIComponent(userName);
			windowDialog("考勤明细",url,"1090","680",false);
		}
	</script>
</head>
<body style="background: #fff;overflow-x:hidden;">
	<div class="fkdj_index">
		<div class="bgsgl_border_left">
			<!--此处写页面的标题 -->
			<div class="bgsgl_border_right">部门考勤</div>
		</div>
		<div class="bgsgl_conter" style="float:left">
		 <div class="main_conter"><!-- <<main_conter>> -->
		   <div class="main_main1" id="set_holiday_tab_nav">
				                  <ul>
				                    <li class="treeNone" id="attlist_id"><a style="cursor: pointer;">考勤详细</a></li>
				                    <li class="treeOn" id="macrostats_id"><a style="cursor: pointer;">宏观统计</a></li>
				                 </ul>
						
					</div>
			 </div>
			 <div>	<!--tab content -->
			 	<div class="main_main2" id="attlist_tab" style="display: none;"><!--考勤一栏 -->
			 	<div class="fkdj_from"  style="padding-top: 0px;">
				<form id="queryForm" name="queryForm" action="bumenkaoqin.act?opt=query" method="post">
				<input type="hidden" id="name" name="name" value="${name }" />
				<input type="hidden" id="startTime" name="startTime" value="${startTime }" />
				<input type="hidden" id="endTime" name="endTime" value="${endTime }" />
				
				<h:panel id="query_panel" width="100%" title="查询条件">
					<div id="hide_table">
						<label style="margin:0 5px 0 0">姓名</label>
						<input id="name_show" name="name_show" type="text" value="${name}"></input>
						<label style="margin:0 5px 0 50px">时间</label>
						<input type="text" name="startTime_show" id="startTime_show" value="${startTime }" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						<label>至</label>
						<input type="text" name="endTime_show" id="endTime_show" value="${endTime }" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						<a style="margin:0 0 0 15px" class="property_query_button" href="javascript:void(0);"
													onmousemove="this.style.background='#FFC600'"
													onmouseout="this.style.background='#8090b4'"
													onclick="queryInfo()">查询 </a>
					</div>
				</h:panel>
				<s:textfield name="page.pageSize" id="pageSize" value="10"  theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
 						
 				<h:page id="list_panel" width="100%"   title="结果列表">
 				<d:table name="page.result" id="row" uid="row" export="true" requestURI="bumenkaoqin.act?type=export" cellspacing="0" cellpadding="0" class="table">
					<d:col property="userName" class="display_centeralign" title="姓名" />
					<d:col property="orgName" class="display_centeralign" title="部门" />
					<d:col property="normalAttendance" class="display_leftalign" title="出勤" />
					<d:col property="execpAttendance" class="display_centeralign" title="刷卡异常" />
					<d:col property="lateCount" class="display_centeralign" title="迟到" />	
					<d:col property="leaveEarlyCount" class="display_centeralign" title="早退" />
					<d:col property="sickLeave" class="display_centeralign" title="病假" />
					<d:col property="affairLeave" class="display_centeralign" title="事假" />
					<d:col property="businessLeave" class="display_centeralign" title="公出" />
					<d:col property="otherStatus" class="display_centeralign" title="其他"/>
					<d:col class="display_centeralign" style="width:100px" title="详细" media="html">
						<a
								onclick="javascript:popWindow('${row.userId}')"
								onmousemove="this.style.color='#ffae00'"
								style="color:#ffc702;cursor:pointer">
								[详细信息]
						</a>		
					</d:col>
					<c:if test="${isLinDao eq 1}">
						<d:col class="display_centeralign" style="width:70px" title="查看明细" media="html">
							<a
									onclick="javascript:gotoWodekaoqin_read('${row.userId}','${row.userName }')"
									onmousemove="this.style.color='#ffae00'"
									style="color:#ffc702;cursor:pointer">
									[明细]
							</a>		
						</d:col>
					</c:if>
					<d:setProperty name="export.excel.filename" value='部门考勤_${startTime }_${endTime}.xls'/>
					<d:setProperty name="export.amount" value="list"/>
					<d:setProperty name="export.xml" value="false"/>
					<d:setProperty name="export.types" value="excel"/>
				</d:table>	
				</h:page>
				</form>
			</div>
		</div><!-- 考勤一栏end -->
		<div class="main_main2" id="macrostats_tab" style="display: block;"><!--宏观统计 -->
				<!--此处写页面的内容 -->
					<div style="display:block;float:left:width:100%">
						<div id="realTimeCounts">
							<table id="counts_table" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<label>内部人员总数：</label><span id="internalEmpCount"></span>
									</td>
									<td>
										<label>大厦之内：</label><span id="internalEmp_inBuilding"></span>
									</td>
									<td>
										<label>大厦之外：</label><span id="internalEmp_outOfBuilding"></span>
									</td>
									<td>
										<label>未知：</label><span id="internalEmp_unknown"></span>
									</td>
									<td>
										<a style="color: rgb(255, 174, 0); cursor: pointer;" 
										onmousemove="this.style.color='#ffae00'" onclick="javascript:realTimePopWindow('3','0')"> [详情] </a>
									</td>
								</tr>
								<tr>
									<td>
										<label>临时人员总数：</label><span id="tempWorkerCount"></span>
									</td>
									<td>
										<label>大厦之内：</label><span id="tempWorker_inBuilding"></span>
									</td>
									<td>
										<label>大厦之外：</label><span id="tempWorker_outOfBuilding"></span>
									</td>
									<td>
										<label>未知：</label><span id="tempWorker_unknown"></span>
									</td>
									<td>
										<a style="color: rgb(255, 174, 0); cursor: pointer;" 
										onmousemove="this.style.color='#ffae00'" onclick="javascript:realTimePopWindow('3','1')"> [详情] </a>
									</td>
								</tr>
							</table>
						
						</div>
						
						<div  style="margin-right:14px;text-align: right;">
						<label style="margin:0 5px 0 50px">时间</label>
											<input type="text" name="mastartTime" id="mastartTime" value="${startTime}" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
											<label>至</label>
											<input type="text" name="mastartTime" id="maendTime" value="${endTime}" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
										
										    
										    	<a style="margin:0 0 0 15px" class="property_query_button" href="javascript:void(0);"
													onmousemove="this.style.background='#FFC600'"
													onmouseout="this.style.background='#8090b4'"
													onclick="getCharts()">查询 </a>
										    
					</div>
					</div>
		<img id="loadingImg" style="display:none; position:absolute;left:45%;top:50%"  src="images/loadingImg.gif"></img>
		<img id="nodata" style="display:none; position:absolute;left:20%;top:30%"  src="images/nodata.png"></img> 
		<div id="containerPie" style="min-width: 1045px; height: 400px; margin: 0 auto; float:left"></div>
		</div><!--宏观统计end -->
	</div><!-- tab content end -->
	</div><!-- main content end -->
	</div>
	</div>
	</body>
	<script>
	function getCharts(){
		$("#containerPie").empty();
		$("#container").empty();
		var a = $("#mastartTime").val().replace(/-/g, "/");
		var b = $("#maendTime").val().replace(/-/g, "/");
		var starttime = new Date(a);
		var lktime = new Date(b);
		if (Date.parse(starttime) - Date.parse(lktime) > 0) {
			alert('开始时间必须小于结束时间');
	        return false;
	    }

	    
		$.ajax({
	      	  type: 'POST',
	            url: '${ctx}/app/attendance/getPieChartsData.act',
	            dataType: 'json',
	            data: {"type": 2,"mastartTime":$("#mastartTime").val(),"maendTime":$("#maendTime").val(),},
	            complete: function(){
	            	$("#loadingImg").hide();	
	            },
	            beforeSend: function(){
	            	$("#loadingImg").show();
	            }
			}).done(function(data){
	      	  var series = data["data"];
	      	var pieNoData  = true;
	        for(var i=0;i<series.length;i++){
	        	pieNoData = pieNoData && (series[i][1] == 0);
	      	}
	        if(pieNoData){
	        	$("#nodata").show();
	        	$("#container").css("height","70px");
	        	return false;
	        }else{
	        	$("#nodata").hide();
	        	$("#container").css("height","400px");
	        }
	      	 $('#containerPie').highcharts({
	             chart: {
	                 plotBackgroundColor: null,
	                 plotBorderWidth: null,
	                 plotShadow: false
	             },
	             colors:[  '#1abc9c',
	                        '#e74c3c',
	                        '#f39c12',
	                       '#00a6fc',
	                       '#627586'
	                           
	                      ],
	             credits: {
	                 enabled: false
	             },
	             title: {
	                 text: '部门总览',
	                  style:{
	                	  fontSize:'17px',
	                      color: '#808080',
	                      fontFamily: '微软雅黑'
	                  }                 
	             },
	             tooltip: {
	         	    pointFormat: '<span style="color:{series.color}">{series.name}:</span> <b>{point.percentage:.1f}%</b>'
	             },
	             exporting: {
	                 enabled: false
	             },
	             plotOptions: {
	                 pie: {
	                     allowPointSelect: true,
	                     cursor: 'pointer',
	                     dataLabels: {
	                         enabled: true,
	                         color: '#000000',
	                         connectorColor: '#000000',
	                         format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                     }
	                 }
	             },
	             series: [{
	                 type: 'pie',
	                 name: '比例',
	                 data: series
	             }]
	         });
	      	      

	        }).fail(function(data){
	           //alert('获取考勤信息失败');
	        });		
	}
</script>
	
</html>