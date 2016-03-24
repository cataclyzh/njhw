<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style type="text/css">
			/***************节能***********************/
			.jienen_titie{
				font-size:12px;
				width:29%;
				color:#7a7a7a;
				}
			.jienen_text{
				font-size:20px;
				color:#7d93bc;
				font-weight:bold;
				}
			.table_css{margin:45px 0;}
		</style>
		<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/exporting.js"></script>
		<script type="text/javascript">
		$(function() {
			$.ajax({
				type : 'POST',
				url : '${ctx}/app/energyint/energyContributionJson.act',
				data : {"dayType": true},
				dataType : 'JSON',
				async : true,
				success : function(data) {
					var highcharts_data = jQuery.parseJSON(data);
					$('#container').highcharts({
						chart : {
							type : 'line',
							width : 660,
							height : 340
						},
						exporting : {
							enabled : false
						},
						credits : {
							enabled : false
						},
						title : {
							text : ' ',
							x : -20
						//center
						},
						xAxis : {
							categories : highcharts_data.dayArray,
							max : highcharts_data.xAxisMax,
							labels: { 
								rotation : 50
							},
							style:
							{
								backgroundColor:"#ffffff"
							}
						},
						yAxis : [{
							title : {
								text : '能耗(kWh)'
							},
							min:0,
							plotLines : [{
								value : 0,
								width : 1,
								color : '#808080'
							}]
						},{ // Secondary yAxis
			                title: {
			                    text: '电耗(kWh)'
			                },
			                min:0,
			                opposite: true
			            }],
			            tooltip: {
			                crosshairs: true,
			                shared: true,
			                useHTML:true,
			            	headerFormat: '<b>{point.x}日</b><table>',
			                pointFormat: '<tr><td style="color:{series.color};">{series.name}:</td><td style="text-align:right;">{point.y}</td></tr>',
			                footFormat: '</table>'
			            },
						legend : {
							layout : 'horizontal',
							align : 'left',
							x : 20,
							verticalAlign : 'top',
							y : -10,
							floating : true,
							backgroundColor : '#FFFFFF',
							borderColor : '#FFFFFF',
							style: {
								fontSize: '10px',
								backgroundColor:"#ffffff"
							}
						},
						series : [{
							name : '大厦平均能耗',
							color: '#058DC7',
							data : highcharts_data.buildingArray,
							tooltip: {
			                    valueSuffix: ' kWh'
			                }
						}, {
							name : '我的能耗',
							color: '#50B432',
							data : highcharts_data.myArray,
							tooltip: {
			                    valueSuffix: ' kWh'
			                }
						},{
			                name: '大厦平均电耗',
			                color: '#ED561B',
			                type: 'line',
			                yAxis: 1,
			                data: highcharts_data.buildingKwhArray,
			                tooltip: {
			                    valueSuffix: ' kWh'
			                }
			    
			            },{
			                name: '我的电耗',
			                color: '#A757A8',
			                type: 'line',
			                yAxis: 1,
			                data: highcharts_data.myKwhArray,
			                tooltip: {
			                    valueSuffix: ' kWh'
			                }
			    
			            }]
					});
					
					$("#table_energy_id").append(
						"<tr>"+
							"<td class='jienen_titie'>大厦本月平均能耗</td>"+
						    "<td class='jienen_text'>"+highcharts_data.averageEnergy+"</td>"+
						    "<td class='jienen_titie'>kWh</td>"+
						    "<td class='jienen_titie'>我的本月能耗</td>"+
						    "<td class='jienen_text'>"+highcharts_data.energyCurrentMonth+"</td>"+
						    "<td class='jienen_titie'>kWh</td>"+
						"</tr>"+
						"<tr>"+
						    "<td class='jienen_titie'>我的今日能耗</td>"+
						    "<td class='jienen_text'>"+highcharts_data.energyToday+"</td>"+
						    "<td class='jienen_titie'>kWh</td>"+
						    "<td class='jienen_titie'>本月节能贡献</td>"+
						    "<td class='jienen_text'>"+highcharts_data.energyContribution+"</td>"+
						    "<td class='jienen_titie'>kWh</td>"+
						"</tr>"
				 	);
				},
				error : function(msg, status, e) {
				}
			});
		});
		</script>
	</head>
	<body>
		<div style="width:58%;text-align:right;font-family:'微软雅黑';font-size:14px;font-weight:bold;color:#808080;">最近一月能耗走势图</div>
		<div id="container"></div>
		<table>
			<tr><td></td><td></td><td></td></tr>
		</table>
		<br /> 
		<div>
			<table width="100%" border="0" id="table_energy_id" style="margin-left: 30px;"></table>
		</div>
	</body>
</html>