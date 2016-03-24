<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/meta.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营管理系统</title>
<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js" ></script>
<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
<script type="text/javascript">

	function loadRepair() {

		 showShelter('800','650','${ctx}/app/pro/repairListSimple.act');
	}
	function loadVisitor() {

		 showShelter('800','600','${ctx}/app/visitor/frontReg/noOrderInputSimple.act');
	}
	function deviceInfo() {
		 showShelter('650','550','${ctx}/app/omc/queryDeviceInfo.act');
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
				 
				//访客信息展示，头像等等..
				$.each(highcharts_data.visitorList,function(i,item){ 
					 var a ="<li class='oper_li_oper'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td rowspan='4' style='width:69px;'>";
				     var b="<img src=\""+item.res_bak2+"\" width='59' height='85' /></td>";
					 var c="<td class='oper_td_span'>姓  名：</td><td class='oper_td_span1'>"+item.user_name+"</td></tr>";
					 var d="<tr><td class='oper_td_span'>卡  号：</td><td class='oper_td_span1'>"+item.card_id+"</td></tr>";
					 var e="<tr><td valign='top' class='oper_td_span'>访问部门：</td><td class='oper_td_span1'>"+item.org_name+"</td></tr></table></li>";
					 $("#visitorInfo_ul").append(a+b+c+d+e);
				});
				
				//报修
				$("#allCount_omc").html(highcharts_data.allCount);
				$("#accomplishCount_omc").html(highcharts_data.accomplishCount);
				$("#dispatchedCount_omc").html(highcharts_data.dispatchedCount);
				$("#returnVisitCount_omc").html(highcharts_data.returnVisitCount);
				
				//column
				$('#container').highcharts({
	            chart: {
	                			type : 'column',
	                			width : 450,
								height : 180,
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
						pointWidth: 30,
	                    showInLegend: true
	                }
	            },
	
	            series: [{
	                name: '完好',
	                data: highcharts_data.normallyDevice
	            }, {
	                name: '受损',
	                data: highcharts_data.brokeDevice
	            }
				
				]
	        });
	        //pie
	             $('#container2').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            width : 180,
					height : 170,
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
		                ,showInLegend: false
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
		    
					},
				error : function(msg, status, e) {
				}
			});
		});

		function test1() {   //水、电、气耗
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getEnergyOfFloors.act",
	            dataType: 'json',
	            async : false,
	            success: function(jsonArray){
	            	if (jsonArray != null) {
				   	    $.each(jsonArray,function(i){
					   	    if(i==0){
					   	    	alert(jsonArray[i].floor);
					   	    	alert(jsonArray[i].color);
					   	    	alert(jsonArray[i].monitorValue_sum);
					   	    	alert(jsonArray[i].monitorValue_avg);
					   	    	alert(jsonArray[i].monitorUnit);
					   	    	alert(jsonArray[i].monitorTypeName);
					   	    }
						});
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
	            	 document.getElementById("test").value = "333";
	            	if (jsonArray != null) {
	            		 $.each(jsonArray,function(i){
						   	 if(i==0){
						   	    alert(jsonArray[i].floor);
						   	    alert(jsonArray[i].color);
						   	    alert(jsonArray[i].monitorValue_sum);
						   	    alert(jsonArray[i].monitorValue_avg);
						   	    alert(jsonArray[i].monitorUnit);
						   	    alert(jsonArray[i].monitorTypeName);
						   	 }
						});
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
	            		 $.each(jsonArray,function(i){
						   	 if(i==0){
						   	    alert(jsonArray[i].floor);
						   	    alert(jsonArray[i].color);
						   	    alert(jsonArray[i].monitorValue_sum);
						   	    alert(jsonArray[i].monitorValue_avg);
						   	    alert(jsonArray[i].monitorUnit);
						   	    alert(jsonArray[i].monitorTypeName);
						   	 }
						});
			    	}
	            }
	        });
		}
		function test4() {   //水耗
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getWaterEnergy.act",
	            dataType: 'json',
	            async : false,
	            success: function(jsonArray){
	            	if (jsonArray != null) {
	            		 $.each(jsonArray,function(i){
						   	if(i==0){
						   	    alert(jsonArray[i].floor);
						   	    alert(jsonArray[i].color);
						   	    alert(jsonArray[i].monitorValue_sum);
						   	    alert(jsonArray[i].monitorValue_avg);
						   	    alert(jsonArray[i].monitorUnit);
						   	    alert(jsonArray[i].monitorTypeName);
						   	}
						});
			    	}
	            }
	        });
		}
</script>

</head>

<body class="oper_body">
	<div class="oper_index">
		<div class="oper_header">
			<div class="oper_header_logo" onclick="location.href='/';"></div>
			<div class="oper_header_right">
            	<span class="oper_name">当前用户：王小利</span>       <a class="oper_name_r" href="#">个人设置</a>       <a href="#" class="oper_name_i">退出登录</a>
            </div>
		</div>
		<div class="oper_contet">
			<div class="oper_contet_left">
            	<div class="oper_border_right">
                	<div class="oper_border_left">停车场监控</div>
                </div>
                <div class="oper_contet_list">
                	<div class="oper_left_list">
                    	<div class="oper_list_fest">
                        	<div class="oper_title">停车场监控</div>
                            <ul>
                            	<li><span class="oper_span1">今日总流量：</span><span class="oper_span2" style="padding-left:3px;">313</span></li>
                            	<li><span class="oper_span1" style="letter-spacing:3px;">出库流量：</span><span class="oper_span2">150</span></li>
                            	<li><span class="oper_span1" style="letter-spacing:3px;">入库流量：</span><span class="oper_span2">163</span></li>
                            	<li><span class="oper_span1" style="letter-spacing:3px;">内部车辆：</span><span class="oper_span2">112</span></li>
                            	<li><span class="oper_span1" style="letter-spacing:3px;">外部车辆：</span><span class="oper_span2">403</span></li>

                            </ul>
                        </div>
                    	<div class="oper_list_rightfest">
                        	<div class="oper_title">实时车辆情况</div>
                            <ul>
                            	<li><span class="oper_span1">苏A UD791</span></li>
                            	<li><span class="oper_span1">苏A UD791</span></li>
                            	<li><span class="oper_span1">苏A UD791</span></li>
                            	<li><span class="oper_span1">苏A UD791</span></li>
                            	<li><span class="oper_span1">苏A UD791</span></li>
                            </ul>
                        </div>
                        <div class="oper_clear"></div>
                        <div class="oper_botton"><a href="#" class="botton_oper_a">查看更多</a></div>
                    </div>

                    <div class="oper_clear"></div>
                </div>
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">闸机监控</div>
                </div>
                <div class="oper_contet_list oper_img_back">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="oper_table_top">
  <tr>
    <td width="14%" class="oper_span1">今日总流量：</td>
    <td width="10%" class="oper_span2"><span id="todayTotal"></span></td>
    <td width="14%" class="oper_span1">内部人员流量：</td>
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
                	<div class="oper_border_left">访客统计</div>
                </div>
                <div class="oper_contet_list oper_lest_bottom">
<table width="100" border="0" cellspacing="0" cellpadding="0" class="oper_table_tops">
  <tr>
    <td class="oper_title">访客统计</td>
    <td class="oper_span2"></td>
  </tr>
  <tr>
    <td class="oper_span1">访客总数：</td>
    <td class="oper_span2"><span id="visitorCount"></span></td>
  </tr>
  <tr>
    <td class="oper_span1">公网预约：</td>
    <td class="oper_span2"><span id="outerNetVisitorCount"></span></td>
  </tr>
  <tr>
    <td class="oper_span1">前台登记：</td>
    <td class="oper_span2"><span id="receptionVisitorCount"></span></td>
  </tr>
  <tr>
    <td class="oper_span1">目前访客数：</td>
    <td class="oper_span2"><span id="nowVisitorCount"></span></td>
  </tr>
</table>
<div class="oper_table_right">
<ul id="visitorInfo_ul">

</ul>
</div>
                    <div class="oper_clear"></div>
                        <div class="oper_botton" style="margin-right:0px;"><a onclick="javascript:loadVisitor()" class="botton_oper_a" style="cursor:pointer">查看更多</a></div>
                    <div class="oper_clear"></div>
                </div>
            </div>
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
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">能耗查看</div>
                </div>
            <div class="oper_contet_list oper_lest_bottom_conter">
					<div><img src="images/sleft_st1.jpg" width="434" height="239" /></div>
                        <div class="oper_botton" style="margin-right:0px;"><a href="#" class="botton_oper_a">查看更多</a></div>
              </div>
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">设备状态监控</div>
                </div>
            <div class="oper_contet_list oper_bosake">
					<div><div id="container"></div></div>
                        <div class="oper_botton" style="margin-right:0px;"><a   class="botton_oper_a" onclick="javascript:deviceInfo()" style="cursor:pointer">查看更多</a></div>
              </div>
                <div class="oper_margin_bottom"></div>
            	<div class="oper_border_right">
                	<div class="oper_border_left">报修情况统计</div>
                </div>
      <div class="oper_contet_list oper_bosakes">
					<div style="width:40%;float:left;">
             <table width="100%" border="0" cellspacing="0" cellpadding="0" class="oper_table_top">
			  <tr>
			    <td width="60" class="oper_span1">报修总数：</td>
			    <td class="oper_span2"><span id="allCount_omc"></span></td>
			    <td width="60" class="oper_span1">已解决：</td>
			    <td class="oper_span2"><span id="accomplishCount_omc"></span></td>
			  </tr>
			  <tr>
			    <td class="oper_span1" style="letter-spacing:3px;">已派单：</td>
			    <td class="oper_span2"><span id="dispatchedCount_omc"></span></td>
			    <td class="oper_span1">已回访：</td>
			    <td class="oper_span2"><span id="returnVisitCount_omc"></span></td>
			  </tr>
			</table>
</div>
<div style="border-left:1px solid #77abba; width:50%; padding-left:10px; float:right;"><div id="container2"></div></div>
<div class="oper_clear"></div>
                        <div class="oper_botton" style="margin-right:0px;"><a onclick="loadRepair()" class="botton_oper_a" style="cursor:pointer">查看更多</a></div>
              </div>
            </div>
			<div class="oper_contet_right">3</div>
            <div class="oper_clear"></div>
		</div>
		<div class="oper_fooer">
        	市信息中心服务热线：68789555　 服务邮箱：NJIC@njnet.gov.cn<br />市机关物业管理服务中心服务热线：68789666　 服务邮箱：NJIC@njnet.gov.cn
        </div>
	</div>
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
</body>
</html>
