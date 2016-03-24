<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
    <script src="${ctx}/app/omc/js/Qmap3d-core-1.1.js" type="text/javascript"></script>
    <script src="${ctx}/app/omc/js/jquery.Qmap3d.js" type="text/javascript"></script>
    <script src="${ctx}/app/omc/js/Xcds.total.js" type="text/javascript"></script>
    <script src="${ctx}/app/omc/js/MainMonitorNew.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营管理系统</title>
<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
<style type="text/css">
        div,td,input
        {
        	font-size:12px;
        }
        .ptzBtn
        {
        	width:32px;
        }
        #Select1
        {
            width: 67px;
        }
        #SelectWnd
        {
            width: 70px;
        }
    </style>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js" ></script>
<script type="text/javascript" src="${ctx}/app/omc/js/showModel.js" ></script>

<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
<script type="text/javascript">
document.domain = "njnet.gov.cn";
</script>
<script type="text/javascript">
$(function(){
//document.domain = "njnet.gov.cn";
var iframe = window.document.getElementById("threeDimensional");
if (iframe.attachEvent){
    iframe.attachEvent("onload", function(){
	setTimeout(function(){
	try{
	  test1();
	   }
	   catch(e){
	  test1();
	   }
	},100); 
    });
} else {
    iframe.onload = function(){
		setTimeout(function(){
		try{
		  test1();
		   }
		   catch(e){
		  test1();
		   }
		},1000);    
    };
}
	
	//停车场监控信息  
	$.post(
			"${ctx}/app/parkinglot/parkinglotDailyCount.act",
			function(returnedData,status){
				if (returnedData){
					
					//今日总流量
					//var todayCount= $("#parkingMonitor li:eq(0) span:eq(0)").html();
					$("#parkingMonitor li:eq(0) span:eq(1)").html(returnedData.totalCount);
					
					//出库流量
					//var outCount = $("#parkingMonitor li:eq(1) span:eq(0)").html();
					$("#parkingMonitor li:eq(1) span:eq(1)").html(returnedData.totalOut);
					
					//入库流量
					//var inCount = $("#parkingMonitor li:eq(2) span:eq(0)").html();
					$("#parkingMonitor li:eq(2) span:eq(1)").html(returnedData.totalCount-returnedData.totalOut);
					
					//内部车辆
					//var outCarCount = $("#parkingMonitor li:eq(3) span:eq(0)").html();
					$("#parkingMonitor li:eq(3) span:eq(1)").html(returnedData.totalInner);
					
					//外部车辆
					//var inCarCount = $("#parkingMonitor li:eq(4) span:eq(0)").html();
					$("#parkingMonitor li:eq(4) span:eq(1)").html(returnedData.totalCount-returnedData.totalInner);
					
					var list = returnedData.realTimeCarsList;
					for (var i=0;i<list.length;i++){
						var addSpace = list[i].substr(0,2) + " " + list[i].substr(2);
						$("#realTimeSituation li:eq(" + i +") span:eq(0)").html(addSpace);
					}
				};
			});
	//后台查询 数据
	//实时车辆信息   ul  id=realTimeSituation
	window.setInterval(function(){
		$.post(
			"${ctx}/app/parkinglot/parkinglotDailyCount.act",
			function(returnedData,status){
				if (returnedData){
					
					//今日总流量
					//var todayCount= $("#parkingMonitor li:eq(0) span:eq(0)").html();
					$("#parkingMonitor li:eq(0) span:eq(1)").html(returnedData.totalCount);
					
					//出库流量
					//var outCount = $("#parkingMonitor li:eq(1) span:eq(0)").html();
					$("#parkingMonitor li:eq(1) span:eq(1)").html(returnedData.totalOut);
					
					//入库流量
					//var inCount = $("#parkingMonitor li:eq(2) span:eq(0)").html();
					$("#parkingMonitor li:eq(2) span:eq(1)").html(returnedData.totalCount-returnedData.totalOut);
					
					//内部车辆
					//var outCarCount = $("#parkingMonitor li:eq(3) span:eq(0)").html();
					$("#parkingMonitor li:eq(3) span:eq(1)").html(Math.round(returnedData.totalCount*0.8));
					
					//外部车辆
					//var inCarCount = $("#parkingMonitor li:eq(4) span:eq(0)").html();
					$("#parkingMonitor li:eq(4) span:eq(1)").html(Math.round(returnedData.totalCount*0.2));
					
					var list = returnedData.realTimeCarsList;
					for (var i=0;i<list.length;i++){
						var addSpace = list[i].substr(0,2) + " " + list[i].substr(2);
						$("#realTimeSituation li:eq(" + i +") span:eq(0)").html(addSpace);
					}
				};
			});
		},120000);
});
</script>
<script type="text/javascript">
//document.domain = "njnet.gov.cn";
function test1() {   //水、电、气耗
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getEnergyOfFloors.act",
	            dataType: 'json',
	            async : false,
	            success: function(jsonArray){
	            	if (jsonArray != null) {
	            	var MonitorDataJson = new Array(19);
				   	    $.each(jsonArray,function(i){
					var mData = {						// 定义三维能耗显示
                    Floor: parseFloat(jsonArray[i].floor), 						// 楼层号
                    // 颜色
                    color: jsonArray[i].color,
                    monitorValue: parseFloat(jsonArray[i].monitorValue_sum), 	// 要显示的本层数值
                    monitorUnit: jsonArray[i].monitorUnit, // 要显示的本层数值的计量单位
                    monitorValuePerMeter: parseFloat(jsonArray[i].monitorValue_avg),//单位面积的值
                    monitorTypeName: jsonArray[i].monitorTypeName 		// 指标项，如：温度，气等
                       };
                    MonitorDataJson[i] = mData;    	    
						});
					var MonitorDataJson1 = new Array(19);	
					for(var j=1;j<=MonitorDataJson.length;j++)
					{
					  MonitorDataJson1[j] = MonitorDataJson[j-1];
					}	
				    //$("#threeDimensional")[0].Init(MonitorDataJson);
				    window.frames["threeDimensional"].Init(MonitorDataJson1);	 		
			    	}
	            }
	        });
		}
		function test2() {  //电耗
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getElectricityEnergy.act",
	            dataType: 'json',
	            async : false,
	            success: function(jsonArray){
	            	if (jsonArray != null) {
	            	     var MonitorDataJson = new Array(19);
	            		 $.each(jsonArray,function(i){
						   	 /*if(i==0){
						   	    alert(jsonArray[i].floor);
						   	    alert(jsonArray[i].color);
						   	    alert(jsonArray[i].monitorValue_sum);
						   	    alert(jsonArray[i].monitorValue_avg);
						   	    alert(jsonArray[i].monitorUnit);
						   	    alert(jsonArray[i].monitorTypeName);
						   	 }*/
					var mData = {						// 定义三维能耗显示
                    Floor: parseFloat(jsonArray[i].floor), 						// 楼层号
                    // 颜色
                    color: jsonArray[i].color,
                    monitorValue: parseFloat(jsonArray[i].monitorValue_sum), 	// 要显示的本层数值
                    monitorUnit: jsonArray[i].monitorUnit, // 要显示的本层数值的计量单位
                    monitorValuePerMeter:parseFloat(jsonArray[i].monitorValue_avg),//单位面积的值
                    monitorTypeName: jsonArray[i].monitorTypeName 		// 指标项，如：温度，气等
                       };
                        MonitorDataJson[i] = mData;
						});
					var MonitorDataJson1 = new Array(19);	
					for(var j=1;j<=MonitorDataJson.length;j++)
					{
					  MonitorDataJson1[j] = MonitorDataJson[j-1];
					}
					//$("#threeDimensional")[0].Init(MonitorDataJson);
					window.frames["threeDimensional"].Init(MonitorDataJson1);
			    	}
	            }
	        });
		}
		function test3() {   //气耗
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getFlowEnergy.act",
	            dataType: 'json',
	            async : false,
	            success: function(jsonArray){
	            	if (jsonArray != null) {
	            	var MonitorDataJson = new Array(19);
	            		 $.each(jsonArray,function(i){
						   	 /*if(i==0){
						   	    alert(jsonArray[i].floor);
						   	    alert(jsonArray[i].color);
						   	    alert(jsonArray[i].monitorValue_sum);
						   	    alert(jsonArray[i].monitorValue_avg);
						   	    alert(jsonArray[i].monitorUnit);
						   	    alert(jsonArray[i].monitorTypeName);
						   	 }*/
					var mData = {						// 定义三维能耗显示
                    Floor: parseFloat(jsonArray[i].floor), 						// 楼层号
                    // 颜色
                    color: jsonArray[i].color,
                    monitorValue: parseFloat(jsonArray[i].monitorValue_sum), 	// 要显示的本层数值
                    monitorUnit: jsonArray[i].monitorUnit, // 要显示的本层数值的计量单位
                    monitorValuePerMeter:parseFloat(jsonArray[i].monitorValue_avg),//单位面积的值
                    monitorTypeName: jsonArray[i].monitorTypeName 		// 指标项，如：温度，气等
                       };
                    MonitorDataJson[i] = mData;
						});
					var MonitorDataJson1 = new Array(19);	
					for(var j=1;j<=MonitorDataJson.length;j++)
					{
					  MonitorDataJson1[j] = MonitorDataJson[j-1];
					}
					// $("#threeDimensional")[0].Init(MonitorDataJson);
					window.frames["threeDimensional"].Init(MonitorDataJson1);
			    	}
	            }
	        });
		}
		/*function Init(MdataArray) {
            for (var i = 1; i <= 21; i++) {
                if (MdataArray[i] != null) {
                    $.Xcds.AddFloorData(MdataArray[i]);
                }
            }
            $.Xcds.InitialMonitorMap();
            $.Xcds.RefreshMap();
        }*/
		function test4() {  
		 //水耗
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getWaterEnergy.act",
	            dataType: 'json',
	            async : false,
	            success: function(jsonArray){
	            	if (jsonArray != null) {
	            	var MonitorDataJson = new Array(19);
	            		 $.each(jsonArray,function(i){
						   /*	if(i==0){
						   	    alert(jsonArray[i].floor);
						   	    alert(jsonArray[i].color);
						   	    alert(jsonArray[i].monitorValue_sum);
						   	    alert(jsonArray[i].monitorValue_avg);
						   	    alert(jsonArray[i].monitorUnit);
						   	    alert(jsonArray[i].monitorTypeName);
						   	} */
					var mData = {						// 定义三维能耗显示
                    Floor: parseFloat(jsonArray[i].floor), 						// 楼层号
                    // 颜色
                    color: jsonArray[i].color,
                    monitorValue: parseFloat(jsonArray[i].monitorValue_sum), 	// 要显示的本层数值
                    monitorUnit: jsonArray[i].monitorUnit, // 要显示的本层数值的计量单位
                    monitorValuePerMeter:parseFloat(jsonArray[i].monitorValue_avg),//单位面积的值
                    monitorTypeName: jsonArray[i].monitorTypeName 		// 指标项，如：温度，气等
                       };
                    MonitorDataJson[i] = mData;  	
						});
					var MonitorDataJson1 = new Array(19);	
					for(var j=1;j<=MonitorDataJson.length;j++)
					{
					  MonitorDataJson1[j] = MonitorDataJson[j-1];
					}	
				   //$("#threeDimensional")[0].Init(MonitorDataJson);
				   window.frames["threeDimensional"].Init(MonitorDataJson1);	
			    	}
	            }
	        });
		}
</script>
	<script type="text/javascript">
	//初始化天气
	$(function(){
		$.ajax({
	dataType:'script',
	scriptCharset:'gb2312',
	url:'http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=2&city=南京&dfc=3',
	success:function(){
	
		//注视掉的代码都是新版页面上没有用到的，如需要用，可以在页面上添加该功能项的ID
		var str = "";
        var date = new Date();
		var datestr = window.SWther.add["now"].split(" ")[0];
        var week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];// 获取星期
		window.SWther.w["南京"][0].s1;// 今天天气图标
        //document.getElementById("weather_img2").src = dis_img(window.SWther.w["南京"][1].s1);// 明天天气图标
		 window.SWther.w["南京"][0].s1;
        //document.getElementById("weather_img2").title = window.SWther.w["南京"][0].s2;
        //document.getElementById("today_temperature").innerHTML = window.SWther.w["南京"][0].t1;// 今日温度
        var maxW = window.SWther.w["南京"][0].t1;// 今日最高温度
        var minW = window.SWther.w["南京"][0].t2;// 今日最低温度
        //document.getElementById("tomorrow_max").innerHTML = window.SWther.w["南京"][1].t1;// 明日最高温度
        //document.getElementById("tomorrow_min").innerHTML = window.SWther.w["南京"][1].t2;// 明日最低温度
		 window.SWther.add["now"].split(" ")[0];
		var now_tq=window.SWther.w["南京"][0].s1;// 今日天气
		 var fx = window.SWther.w["南京"][0].d1;// 今日风向
		var fj = window.SWther.w["南京"][0].p1;// 今日风级
        //document.getElementById("tomorrow_weather").innerHTML = window.SWther.w["南京"][1].s1;// 明日天气
		
		$("#tianqi_span").html("<span class='oper_contet_table'>"+datestr+"</span><span class='oper_contet_table1'>"+week+"</span><span class='oper_contet_table1'>"+now_tq+"&nbsp;&nbsp;&nbsp;"+minW+"~"+maxW+"℃"+"&nbsp;&nbsp;&nbsp;"+fx+"&nbsp;&nbsp;&nbsp;"+fj+"级</span>");
		}
	});
	});
	</script>
<script type="text/javascript">
 function logout(){
    	var url ="${ctx}/j_spring_security_logout" + '?t=' + Math.random();
    	window.open(url, "_self");
    }
    
	function loadRepair() {

		 showShelter('800','585','${ctx}/app/pro/repairListSimple.act');
	}
	function loadVisitor() {

		 showShelter('800','600','${ctx}/app/visitor/frontReg/noOrderInputSimple.act');
	}
	function deviceInfo() {
		 showShelter('650','600','${ctx}/app/omc/queryDeveiceToView.act');
	}
	function veicleAccessInfo() {
		 showShelter('650','600','${ctx}/app/omc/vehicleAccessInfo.act');
	}

	
		$(function() {
			$.ajax({
				type : 'POST',
				url : '${ctx}/app/omc/omcReportInit.act',
				dataType : 'JSON',
				async : true,
				success : function(data) {
				var highcharts_data = jQuery.parseJSON(data);
				
				//初始化闸机流量
			    $("#todayTotal").html(highcharts_data.todayTotal);
			    $("#insiderTotal").html(highcharts_data.insiderTotal);
			    $("#enterTotal").html(highcharts_data.enterTotal);
			    $("#outsiderTotal").html(highcharts_data.outsiderTotal);
			    $("#outTotal").html(highcharts_data.outTotal);
				
				//访客信息
				 $("#visitorCount").html(highcharts_data.visitorCount);
				 $("#outerNetVisitorCount").html(highcharts_data.outerNetVisitorCount);
				 $("#receptionVisitorCount").html(highcharts_data.receptionVisitorCount);
				 $("#nowVisitorCount").html(highcharts_data.nowVisitorCount);
				 $("#accordVisitorCount").html(highcharts_data.accordVisitorCount);
				 
				//访客信息展示，头像等等..
				$.each(highcharts_data.visitorList,function(i,item){ 
					 var a ="<li class='oper_li_oper'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td rowspan='4' style='width:69px;'>";
				     var b="<img src=\""+item.res_bak2+"\" width='59' height='85' /></td>";
					 var c="<td class='oper_td_span'>访客姓名：</td><td class='oper_td_span1'>"+item.vi_name+"</td></tr>";
					 var x="<tr><td class='oper_td_span'>受访姓名：</td><td class='oper_td_span1'>"+item.user_name+"</td></tr>";
					 var d="<tr><td class='oper_td_span'>卡号：</td><td class='oper_td_span1'>"+item.card_id+"</td></tr>";
					 var e="<tr><td valign='top' class='oper_td_span'>访问部门：</td><td class='oper_td_span1'>"+item.org_name+"</td></tr></table></li>";
					 $("#visitorInfo_ul").append(a+b+c+x+d+e);
				});
				
				//报修
				$("#allCount_omc").html(highcharts_data.allCount);
				$("#accomplishCount_omc").html(highcharts_data.accomplishCount);
				$("#dispatchedCount_omc").html(highcharts_data.dispatchedCount);
				$("#returnVisitCount_omc").html(highcharts_data.returnVisitCount);

				var mensuo = "";
				if(highcharts_data.brokeDevice[0]==0){
					mensuo = "	<td class='oper_span2'>"+highcharts_data.brokeDevice[0]+"</td>             ";
				}else{
					mensuo = "	<td class='oper_span3'>"+highcharts_data.brokeDevice[0]+"</td>             ";
				//	mensuo = "	<td class='oper_span2'>"+0+"</td>             ";
				}			
				
				var tongxunji  = "";
				if(highcharts_data.brokeDevice[1]==0){
					tongxunji = "	<td class='oper_span2'>"+highcharts_data.brokeDevice[1]+"</td>             ";
				}else{
					tongxunji = "	<td class='oper_span3'>"+highcharts_data.brokeDevice[1]+"</td>             ";
				}	
				
				var zhaji = "";
				if(highcharts_data.brokeDevice[2]==0){
					zhaji = "	<td class='oper_span2'>"+highcharts_data.brokeDevice[2]+"</td>             ";
				}else{
					zhaji = "	<td class='oper_span3'>"+highcharts_data.brokeDevice[2]+"</td>             ";
				//	zhaji = "	<td class='oper_span2'>"+0+"</td>             ";
				}
				
				var menjin = "";
				if(highcharts_data.brokeDevice[3]==0){
					menjin = "	<td class='oper_span2'>"+highcharts_data.brokeDevice[3]+"</td></tr>        ";
				}else{
					menjin = "	<td class='oper_span3'>"+highcharts_data.brokeDevice[3]+"</td></tr>        ";
				//	menjin = "	<td class='oper_span2'>"+0+"</td></tr>        ";
				}
				
				var tableDevice = 
				
				"<table width='100%' class='oper_table_top'>       "+
				"	<tbody><tr>                                        "+
				"	<td>&nbsp;</td>                                    "+
				"	<td class='oper_span1'>门锁</td>                                        "+
				"	<td class='oper_span1'>门锁通信机 <br></td>                                "+
				"	<td class='oper_span1'>闸机</td>                                        "+
				"	<td class='oper_span1'>门禁</td></tr>                                   "+
				"	<tr>                                               "+
				"	<td class='oper_span1'>正常</td>                                        "+
				"	<td class='oper_span2'>"+highcharts_data.normallyDevice[0]+"</td>         "+
				"	<td class='oper_span2'>"+highcharts_data.normallyDevice[1]+"</td>         "+
				"	<td class='oper_span2'>"+highcharts_data.normallyDevice[2]+"</td>         "+
				"	<td class='oper_span2'>"+highcharts_data.normallyDevice[3]+"</td></tr>    "+
				"	<tr>                                               "+
				"	<td class='oper_span1'>异常</td>                                        "+
				mensuo+
				tongxunji+
				zhaji+
				menjin+
				"	</tbody>                                           "+
				"</table>                                              ";
				$('#container').html(tableDevice);
				//column
				/*
				$('#container').highcharts({
	            chart: {
	                			type : 'column',
	                			width : 290,
								height : 170,
								backgroundColor:''
	            },
	            title: {
	                text: ' '
	            },
				exporting : {enabled : false
				},
				credits : {enabled : false
				},
	            xAxis: {
	                categories: ['门锁', '门锁通信机', '闸机', '门禁']
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: ' '
	                },
	                stackLabels: {
	                    enabled: true,
	                    style: {
	                        fontWeight: 'bold',
	                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
	                    }
	                }
	            },
	            legend: {
	                align: 'right',
	                x: -70,
	                verticalAlign: 'top',
	                y: 20,
	                floating: true,
	                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
	                borderColor: '#CCC',
	                borderWidth: 1,
	                shadow: false
	            },
	            tooltip: {
	              		crosshairs: true,
			            shared: true,
			            useHTML:true,
			            headerFormat: '<b>{point.x}</b><table>',
			            pointFormat: '<tr><td style="color:{series.color};">{series.name}:</td><td>{point.y}</td></tr>',
			            footFormat: '</table>'
	            },
	            plotOptions: {
	                column: {
	                    stacking: 'normal',
						pointPadding: 0.2,
						borderWidth: 0,
						pointWidth: 18,
	                    showInLegend: true
	                }
	            },
	
	            series: [{
	                name: '正常',
	                data: highcharts_data.normallyDevice
	            }, {
	                name: '异常',
	                data: highcharts_data.brokeDevice
	            }
				
				]
	        });
	        */
	        
	        //pie
	        if(highcharts_data.returnVisitCount != 0 
	            || highcharts_data.accomplishCount != 0 
	             ||  highcharts_data.dispatchedCount != 0)
	          {
	          			
			             $('#container2').highcharts({
				        chart: {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false,
				            width : 210,
							height : 190,
							backgroundColor:''
				        },
				        exporting : {enabled : false
						},
						credits : {enabled : false
						},
				        title: {
				            text: ' '
				        },
				        tooltip: {
				    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
				        },
				        plotOptions: {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: true,
				                    color: '#000000',
				                    distance:5,
				                    connectorColor: '#000000',
				                    format: '<b>{point.name}</b>'
				                }
				                ,showInLegend: true
				            }
				        },
				        series: [{
				            type: 'pie',
				            name: '百分比',
				            data: [['已回访',highcharts_data.returnVisitCount],
				            ['已解决',highcharts_data.accomplishCount]
				            ,['已派单',highcharts_data.dispatchedCount]]
				        }]
				    });
	          }
	          else
	          {
	            $("#container2").append("<img src='images/pie.JPG' width='207'></img>"); 
	          }
		    
			 },
			 error : function(msg, status, e) {
				}
			});
		});
		
		function toVideoIpSelectA(){
		    var url = "videoIpSelectA.jsp";
		    showShelterOmc('180','450',url);
		}
		function toVideoIpSelectB(){
		    var url = "videoIpSelectB.jsp";
		    showShelterOmc('180','440',url);
		}
		function toVideoIpSelectC(){
		    var url = "videoIpSelectC.jsp";
		    showShelterOmc('180','450',url);
		}
		function toVideoIpSelectD(){
		    var url = "videoIpSelectD.jsp";
		    showShelterOmc('180','470',url);
		}
		function toVideoIpSelectBall3(){
		    var url = "videoIpSelectBall3.jsp";
		    showShelterOmc('280','175',url);
		}
		function toVideoIpSelectBall19(){
		    var url = "videoIpSelectBall19.jsp";
		    showShelterOmc('280','160',url);
		}
		function toVideoIpSelectDR(){
		    var url = "videoIpSelectDR.jsp";
		    showShelterOmc('180','350',url);
		}
		function toVideoIpSelectDRB(){
		    var url = "videoIpSelectDRB.jsp";
		    showShelterOmc('270','250',url);
		}
		function toVideoIpSelectEL(){
		    var url = "videoIpSelectEL.jsp";
		    showShelterOmc('260','265',url);
		}
		function toVideoIpSelectFace(){
		    var url = "videoIpSelectFace.jsp";
		    showShelterOmc('280','185',url);
		}
		function toVideoIpSelectEast(){
		    var url = "videoIpSelectEast.jsp";
		    showShelterOmc('280','110',url);
		}
		
		function getWndSum(){
		    var OCXobj = document.getElementById("PlayViewOCX");
		    return OCXobj.GetWndNum();
		}
		
		function stopRealPlay(id){
		     var OCXobj = document.getElementById("PlayViewOCX");
		     OCXobj.StopRealPlay(id);
		}
		
		function getSelWnd(){
		     var OCXobj = document.getElementById("PlayViewOCX");
		     return OCXobj.GetSelWnd();
		}
		
		function getFreePreviewWnd(){
		     var OCXobj = document.getElementById("PlayViewOCX");
		     return OCXobj.GetFreePreviewWnd();
		}
		
		function isWndPreview(id){
		     var OCXobj = document.getElementById("PlayViewOCX");
		     return OCXobj.IsWndPreview(id);
		}
		
		function startTaskPreviewInWndV11(ip,id){
		    var OCXobj = document.getElementById("PlayViewOCX");
		    OCXobj.StartTask_Preview_InWnd_V11(ip,id);
		}
		
		function login(szUserName,szPassWord,szIP,lPort,lDataFetchType){
		    var OCXobj = document.getElementById("PlayViewOCX");
		    OCXobj. Login_V11(szUserName,szPassWord,szIP,lPort,lDataFetchType);
;
		}
		
</script>




<SCRIPT>
    function videoload()
	{
	  StartPlayView(0);
	  StartPlayView(1);
	  StartPlayView(2);
	  StartPlayView(3);
	  
	}
    /*****实时预览******/
    function StartPlayView(id)
    {
        var OCXobj = document.getElementById("PlayViewOCX");
		//OCXobj.SetWndMode(0);
        OCXobj.SelWindow(id);
        strIP = document.getElementById("TextIP" + id).value;
        strPort = document.getElementById("TextPort").value;
        strName = document.getElementById("TextName").value;
        strPwd = "12345";
        ChanNum = document.getElementById("SelectChan").value;
		checkstream = false;
		streamIp = document.getElementById("streamIP").value;
		rtsp = document.getElementById("rtspPort").value;
		deviceType = document.getElementById("SelectDeviceType").value;
		strXML = "<?xml version='1.0'?><Parament><DeviceIP>" + strIP + "</DeviceIP><DevicePort>" + strPort + "</DevicePort><DeviceType>" + deviceType + "</DeviceType><User>"+ strName +"</User><Password>" + strPwd + "</Password><ChannelNum>"+ ChanNum +"</ChannelNum><ProtocolType>0</ProtocolType><StreamType>0</lStreamType>";
		if(checkstream){
			strXML += "<Transmits><Transmit><SrvIp>"+streamIp+":"+rtsp+"</SrvIp></Transmit></Transmits></Parament>";
		}else{
			strXML += "<Transmits></Transmits></Parament>";
		}
		OCXobj.StartTask_Preview(strXML);
    }
    /*****指定窗口实时预览******/
    function StartPlayView_InWnd()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        strIP = document.getElementById("TextIP").value;
        strPort = document.getElementById("TextPort").value;
        strName = document.getElementById("TextName").value;
        strPwd = "12345";//document.getElementById("Textpwd").value;
        ChanNum = document.getElementById("SelectChan").value;
		checkstream = false;//document.getElementById("checkStream").checked;
		streamIp = document.getElementById("streamIP").value;
		rtsp = document.getElementById("rtspPort").value;
		deviceType = document.getElementById("SelectDeviceType").value;
        WndIndex = document.getElementById("SelectWnd").value;
		strXML = "<?xml version='1.0'?><Parament><DeviceIP>" + strIP + "</DeviceIP><DevicePort>" + strPort + "</DevicePort><DeviceType>" + deviceType + "</DeviceType><User>"+ strName +"</User><Password>" + strPwd + "</Password><ChannelNum>"+ ChanNum +"</ChannelNum><ProtocolType>0</ProtocolType><StreamType>0</lStreamType>";
		if(checkstream){
			strXML += "<Transmits><Transmit><SrvIp>"+streamIp+":"+rtsp+"</SrvIp></Transmit></Transmits></Parament>";
		}else{
			strXML += "<Transmits></Transmits></Parament>";
		}
		
		OCXobj.StartTask_Preview_InWnd(strXML,parseInt(WndIndex));
    }
    /*****空闲窗口实时预览******/
    function StartPlayView_Free()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        strIP = document.getElementById("TextIP").value;
        strPort = document.getElementById("TextPort").value;
        strName = document.getElementById("TextName").value;
        strPwd = "12345";//document.getElementById("Textpwd").value;
        ChanNum = document.getElementById("SelectChan").value;
        checkstream = false;//document.getElementById("checkStream").checked;
		streamIp = document.getElementById("streamIP").value;
		rtsp = document.getElementById("rtspPort").value;
		deviceType = document.getElementById("SelectDeviceType").value;
		strXML = "<?xml version='1.0'?><Parament><DeviceIP>" + strIP + "</DeviceIP><DevicePort>" + strPort + "</DevicePort><DeviceType>" + deviceType + "</DeviceType><User>"+ strName +"</User><Password>" + strPwd + "</Password><ChannelNum>"+ ChanNum +"</ChannelNum><ProtocolType>0</ProtocolType><StreamType>0</lStreamType>";
		if(checkstream){
			strXML += "<Transmits><Transmit><SrvIp>"+streamIp+":"+rtsp+"</SrvIp></Transmit></Transmits></Parament>";
		}else{
			strXML += "<Transmits></Transmits></Parament>";
		}
		
		OCXobj.StartTask_Preview_FreeWnd(strXML);
		OCXobj.SetWndMode(0);
    }
    /*****停止所有预览******/
    function StopPlayView()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        OCXobj.StopAllPreview();
    }
    /*****设置抓图格式为JPG******/
    function CatchPicJPG()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        OCXobj.SetCapturParam("C:\\pic",0);
    }
    /*****设置抓图格式为BMP******/
    function CatchPicBMP()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        OCXobj.SetCapturParam("C:\\pic",1);
    }
   
    /*****获取视频参数******/
    function GetVideoEffect()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        retXML = OCXobj.GetVideoEffect();
        var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async="false";
        xmlDoc.loadXML(retXML);
        document.getElementById("TextBright").value = xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue;
        document.getElementById("TextConstrast").value = xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
        document.getElementById("TextSaturation").value = xmlDoc.documentElement.childNodes[2].childNodes[0].nodeValue;
        document.getElementById("TextHue").value = xmlDoc.documentElement.childNodes[3].childNodes[0].nodeValue;
    }
    /*****设置视频参数******/
    function SetVideoEffect()
    {
        var OCXobj = document.getElementById("PlayViewOCX");
        BrightValue = document.getElementById("TextBright").value;
        ContrastValue = document.getElementById("TextConstrast").value;
        SaturationValue = document.getElementById("TextSaturation").value;
        HueValue = document.getElementById("TextHue").value;
        OCXobj.SetVideoEffect(parseInt(BrightValue),parseInt(ContrastValue),parseInt(SaturationValue),parseInt(HueValue));
    }
    
    </SCRIPT>
    
		<!-- 以下是事件触发函数接口 -->  
		<script language="javascript" for="PlayViewOCX" event="FireWindowsNumber(iSel)">   
			szMsg = "窗口分割消息,窗口数" + iSel;  
			
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireSelectWindow(iSel)"> 
			szMsg = "窗口选择消息,窗口" + iSel;  
			
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStartRealPlay(iSel)">
			szMsg = "开始预览消息" + iSel;  

		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStopRealPlay(iSel)">   
			szMsg = "停止预览消息" + iSel ;  
			
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStopPreviewAll()">   
			szMsg = "停止所有预览消息" ;  
			
		</script>
		<script language="javascript" for="PlayViewOCX" event="FireChangeWindow(iFrom,iTo)">   
			szMsg = "互换窗口消息"+ "从" +iFrom+"到"+iTo;  
		</script>
		<script language="javascript" for="PlayViewOCX" event="FireCatchPic(szPath,iWindowID)">   
			szMsg = "抓图"+ "路径" +szPath+"窗口"+iWindowID;  
	
		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStartPlayBack(lWindowID)">  

		</script>

		<script language="javascript" for="PlayViewOCX" event="FireStopPlayBack(lWindowID)">  
			
		</script>

</head>
  
 
<body class="oper_body" onload="videoload()">
    
	<div class="oper_index">
		<div class="oper_header">
			<div class="oper_header_logo">
		   <div style="float:left;width:280px;height:80px;cursor:pointer;" onclick="location.href='${ctx}/app/integrateservice/init.act';">&nbsp;</div>
		   <div style="float:left;width:160px;height:80px;cursor:pointer;" onclick="location.href='${ctx}/app/omc/newIndex.jsp';">&nbsp;</div>
			
			
			</div>
			<div class="oper_header_right">
            	<span class="oper_name">当前用户：<script type="text/javascript">var len = '${_username}';if(len.replace(/(^\s*)|(\s*$)/g, "").length > 6){var text = len.substring(0,6) + "..."}else{text = len}document.write(text);</script></span>       <a class="oper_name_r" onclick="showShelter('780','510','${ctx}/common/userOrgMgr/personInfoChreach.act')" style="cursor:pointer">个人设置</a>       <a onclick="logout();return false" style="cursor:pointer" class="oper_name_i">退出登录</a>
            </div>
		</div>
		<div class="oper_contet">
			<div class="oper_contet_left">
            	<div class="oper_border_right">
                	<div class="oper_border_left">停车场监控</div>
                </div>
                <div class="oper_contet_list" style="padding: 10px;">
                
                    	<div class="oper_list_fest">
                    	 <div class="oper_clear_top"></div>
                    	
                        	<div class="oper_title">停车场监控</div>
                            <ul style="padding-bottom: 10px;" id="parkingMonitor">
                            	<li style="padding-bottom:4px;"><span class="oper_span1">今日总流量：</span><span class="oper_span2" style="padding-left:3px;">0</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1" style="letter-spacing:3px;">出库流量：</span><span class="oper_span2">0</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1" style="letter-spacing:3px;">入库流量：</span><span class="oper_span2">0</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1" style="letter-spacing:3px;">内部车辆：</span><span class="oper_span2">0</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1" style="letter-spacing:3px;">外部车辆：</span><span class="oper_span2">0</span></li>

                            </ul>
                        </div>
                    	<div class="oper_list_rightfest">
                    	   <div class="oper_clear_top"></div>
                    	
                        	<div class="oper_title" >实时车辆情况</div>
                            <ul style="padding-bottom: 10px;" id="realTimeSituation">
                            	<li style="padding-bottom:4px;"><span class="oper_span1">苏A 08152</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1">苏A 09059</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1">苏A 05396</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1">南L 10084</span></li>
                            	<li style="padding-bottom:4px;"><span class="oper_span1">苏A H4286</span></li>
                            </ul>
                        </div>
                        <div class="oper_clear"></div>
                        <div class="oper_botton" style="margin-right:0px;"><a onclick="javascript:veicleAccessInfo()" style="cursor:pointer;" class="botton_oper_a">查看更多</a></div>
                
              
                    <div class="oper_clear"></div>
                </div>
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">当日闸机监控</div>
                </div>
                <div class="oper_contet_list oper_img_back1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="oper_table_top">
				  <tr>
				    <td width="20%" class="oper_span1">今日总流量：</td>
				    <td width="10%" class="oper_span2"><span id="todayTotal"></span></td>
				    <td width="20%" class="oper_span1">内部人员流量：</td>
				    <td width="10%" class="oper_span2"><span id="insiderTotal"></span></td>
				    <td width="10%">&nbsp;</td>
				  </tr>
				  <tr>
				    <td class="oper_span1" style="letter-spacing:3px;">入闸流量：</td>
				    <td class="oper_span2"><span id="enterTotal"></span></td>
				    <td class="oper_span1">外部人员流量：</td>
				    <td class="oper_span2"><span id="outsiderTotal"></span></td>
				    <td>&nbsp;</td>
				  </tr>
				  <tr>
				    <td class="oper_span1" style="letter-spacing:3px;">出闸流量：</td>
				    <td class="oper_span2"><span id="outTotal"></span></td>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				  </tr>
				</table>
                </div>
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">设备状态监控</div>
                </div>
            <div class="oper_contet_list oper_bosake1">
					<div><div id="container" style="width: 260px"></div></div>
                        <div class="oper_botton" style="margin-right:0px;">
                        <a class="botton_oper_a" onclick="javascript:deviceInfo()" style="cursor:pointer;margin-left:10px;">查看更多</a>
                        <a class="botton_oper_a" href="${ctx}/app/3d/deviceSearch.act" style="cursor:pointer;width:100px;">三维设备管理</a>
                        </div>
              </div>
            </div>
            
            <!-- 中间 -->
			<div class="oper_contet_conter">
				<div style="height:27px; overflow: hidden;">
		            <table width="100%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td width="7"><img src="images/oper_time_left.jpg" width="7" height="27" /></td>
					    <td style="background:#77abba;" align="center"><span id="tianqi_span"></span></td>
					    <td style="background:#77abba;"><a style="cursor: pointer;" onclick="showShelter('665','505','${ctx}/app/integrateservice/weather.html');"><img src="images/more.jpg" width="39" height="17" border="0" /></a></td>
					    <td width="7"><img src="images/oper_time_right.jpg" width="7" height="27" /></td>
					  </tr>
					</table>
				</div>

                <div class="oper_contet_list oner_ovedr_left">
                    	<div class="oner_ovder_sp">
                    	<div  style=" color: red ; font-size: 14px;">
			          	&nbsp;&nbsp;&nbsp; 说明：首次使用，请下载并安装&nbsp;<a href="<%=path%>/scripts/cmsocx.exe" title="cmsocx.exe" target="_blank">视频驱动</a></td>
				        </div>
                        <div style="width:548px;margin-top: 2px;">
				        <input id="Hidden1" type="hidden" />   
				        <div style="width:548px;float:left;overflow:hidden; z-index: 510;">
				            <!-- 添加预览控件（需要先在windows下注册） -->
				            <object classid="clsid:D5E14042-7BF6-4E24-8B01-2F453E8154D7" id="PlayViewOCX"  width="548" height="380" name="ocx"  >
				            </object>
				        </div>
				   		</div>
   				 		<div class="oper_clear"></div>
                        </div>
                        <div class="oner_ovder_lc">
                       		<a onclick="javascript:toVideoIpSelectA();" class="oner_ovder_a" style="cursor:pointer">A座</a>
                       		<a onclick="javascript:toVideoIpSelectB();" style="cursor:pointer" class="oner_ovder_a" >B座</a>
                       		<a onclick="javascript:toVideoIpSelectC();" style="cursor:pointer" class="oner_ovder_a" >C座</a>
                       		<a onclick="javascript:toVideoIpSelectD();" class="oner_ovder_a" style="cursor:pointer">D座</a>
                       		<a onclick="javascript:toVideoIpSelectEast();" class="oner_ovder_a" style="cursor:pointer">东门厅</a>
                       		<a onclick="javascript:toVideoIpSelectFace();" class="oner_ovder_a" style="cursor:pointer">人脸抓拍机</a>
                       		<a onclick="javascript:toVideoIpSelectEL();" class="oner_ovder_a" style="cursor:pointer">电梯</a>
                       		<a onclick="javascript:toVideoIpSelectDR();" class="oner_ovder_a" style="cursor:pointer">食堂</a>
                       		<a onclick="javascript:toVideoIpSelectDRB();" class="oner_ovder_a" style="cursor:pointer">食堂厨房</a>
							<a href="ov.jsp" target="_self" class="oner_ovder_a" style="cursor:pointer">其他监控</a>
                        </div>
               </div>
            </div>
            <!-- 中间 -->
            
            
            <!-- 右边 -->
			<div class="oper_contet_right">
            	<div class="oper_border_right">
                	<div class="oper_border_left">能耗查看</div>
                </div>
            <div class="oper_contet_list oper_lest_bottom_conter">
					<div>
           		     <iframe id="threeDimensional" name="threeDimensional" src="http://3d.njnet.gov.cn/1/MainMonitor.htm" width="99.2%" height="223" scrolling="no"></iframe>
           		    </div>
                        <div class="oper_botton" style="margin-right:0px;"><a href="${ctx}/app/3d/energySearch.act" class="botton_oper_a">查看更多</a><a onclick="javascript:test3();" href="javascript:void(0);" class="botton_oper_a" style="margin-right:10px;">查看气耗</a><a onclick="javascript:test2();"  href="javascript:void(0);"class="botton_oper_a" style="margin-right:10px;">查看电耗</a><a  onclick="javascript:test4();" href="javascript:void(0);" class="botton_oper_a" style="margin-right:10px;">查看水耗</a></div>
              </div>
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">报修情况统计</div>
                </div>
      			<div class="oper_contet_list oper_bosakes">
      			<%-- 
      				<div id="container2">
      					<table width="100%" class=oper_table_top>
      						<tbody>
								<tr>
									<td class="oper_span1">报修总数</td>
									<td class="oper_span1">已解决</td>
									<td class="oper_span1">已派单</td>
									<td class="oper_span1">已回访</td></tr>
									<tr>                
									<td class="oper_span2" id="allCount_omc"></td>
									<td class="oper_span2" id="accomplishCount_omc"></td>
									<td class="oper_span2" id="dispatchedCount_omc"></td>
									<td class="oper_span2" id="returnVisitCount_omc"></td>
								</tr>
							</tbody>
						</table>
      				</div>
      				--%>
					<div style="width:40%;float:left;">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="oper_table_top">
						<tr>
						  <td width="60" class="oper_span1">报修总数：</td>
							    <td class="oper_span2"><span id="allCount_omc"></span></td>
						</tr>
						<tr>			    
						  <td width="60" class="oper_span1">已解决：</td>
							    <td class="oper_span2"><span id="accomplishCount_omc"></span></td>
						</tr>
						<tr>
						    <td class="oper_span1" style="letter-spacing:3px;">已派单：</td>
						    <td class="oper_span2"><span id="dispatchedCount_omc"></span></td>
						</tr>
						<tr>
						    <td class="oper_span1">已回访：</td>
						    <td class="oper_span2"><span id="returnVisitCount_omc"></span></td>
						</tr>
					</table>
				</div>
				<div style="border-left:1px solid #77abba; width:50%; padding-left:10px; float:right;">
				<div id="container2"></div>
				</div>
				<div class="oper_clear"></div>
                        <div class="oper_botton" style="margin-right:0px;"><a onclick="loadRepair()" class="botton_oper_a" style="cursor:pointer">查看更多</a></div>
                        
              </div>
                <div class="oper_clear"></div>
            </div>
            <div class="oper_clear"></div>
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">当日访客统计</div>
                </div>
                <div class="oper_contet_list oper_lest_bottom" style="padding-bottom:0;">
				<table width="220" border="0" cellspacing="0" cellpadding="0" class="oper_table_tops">
				  <tr>
				    <td class="oper_title" style="line-height:12px;">访客统计</td>
				    <td class="oper_span2" style="line-height:12px;"></td>
				  </tr>
				  <tr>
				    <td class="oper_span1" style="line-height:20px;">访客总数：</td>
				    <td class="oper_span2" style="line-height:20px;"><span id="visitorCount"></span></td>
				  </tr>
				  <tr>
				    <td class="oper_span1" style="line-height:20px;">公网预约：</td>
				    <td class="oper_span2" style="line-height:20px;"><span id="outerNetVisitorCount"></span></td>
				  </tr>
				  <tr>
				    <td class="oper_span1" style="line-height:20px;">前台登记：</td>
				    <td class="oper_span2" style="line-height:20px;"><span id="receptionVisitorCount"></span></td>
				  </tr>
				 <tr>
				    <td class="oper_span1" style="line-height:20px;">主动约访：</td>
				    <td class="oper_span2" style="line-height:20px;"><span id="accordVisitorCount"></span></td>
				  </tr>
				  <tr>
				    <td class="oper_span1" style="line-height:20px;">目前访客数：</td>
				    <td class="oper_span2" style="line-height:20px;"><span id="nowVisitorCount"></span></td>
				  </tr>
				</table>
				<div class="oper_table_left">
				<ul id="visitorInfo_ul">
				
				</ul>
				</div>
				<!-- 右边 -->
				
                    <div class="oper_clear"></div>
                        <div class="oper_botton" style="margin-right:0px;">
                        <a onclick="javascript:loadVisitor()" class="botton_oper_a" style="cursor:pointer;margin-left:10px;">查看更多</a>
                        <a href="${ctx}/app/3d/visitorLocation.act" class="botton_oper_a" style="cursor:pointer;width:100px;">三维访客管理</a>
                        </div>
                    <div class="oper_clear"></div>
                </div>
		</div>
		<div class="oper_fooer">
        	市信息中心服务热线：68789555　 服务邮箱：NJIC@njnet.gov.cn<br />市机关物业管理服务中心服务热线：68789666　 服务邮箱：NJIC@njnet.gov.cn
        </div>
	</div>
	
	
	    <input type="hidden" name="checkit" value = "10.250.247.158"  id="TextIP0"/>
		<input type="hidden" name="checkit"  value = "10.250.247.159"  id="TextIP1" />
		<input type="hidden" name="checkit" value = "10.250.247.194"  id="TextIP2"/>
		<!-- 
		<input type="hidden" name="checkit"  value = "10.250.247.134"  id="TextIP3"/>
		-->
		<input type="hidden" name="checkit"  value = "10.250.248.134"  id="TextIP3"/>
		
		 
		<input type="hidden" name="checkit" id="TextIP4" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP5" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP6" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP7" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP8" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP9" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP10" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP11" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP12" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP13" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP14" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP15" onclick="StartPlayView_Free()"/>
		<input type="hidden" name="checkit" id="TextIP16" onclick="StartPlayView_Free()"/>
         <!--  port:&nbsp;-->
        <input id="TextPort" type="hidden" value="8000" />&nbsp;&nbsp; 
         <!--user:&nbsp;-->
        <input id="TextName" type="hidden" value="admin" />&nbsp;&nbsp;&nbsp; 
          <!--pwd:&nbsp-->
        <input id="TextPwd" type="hidden"  />&nbsp;&nbsp;&nbsp;
         <!--通道列表：-->
		<input id="SelectChan" type="hidden"  value="0" name="D1"/>&nbsp;&nbsp;&nbsp; 
	    <div style="padding: 1px; margin: 1px;">
        <input id="streamIP" type="hidden" value="172.7.123.104" />
       <!-- rtsp端口:&nbsp;-->
        <input id="rtspPort" type="hidden" value="554" />
         <!--设备类型：-->
		<input id="SelectDeviceType" type="hidden" value="10001" />
      </div>
</body>
</html>
