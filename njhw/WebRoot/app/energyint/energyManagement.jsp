<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%--
- Author: sqs
- Date: 2013-3-29
- Description: 能耗管理首页
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/metaIframe.jsp" %>
		
		<title>能耗管理首页</title>
		
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js" ></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
		<style type="text/css">
			.jienen_titie{
				font-size:12px;
				width:20%;
				color:#7a7a7a;
			}
			.jienen_titie_s1{
				font-size:12px;
				width:20%;
				color:#7a7a7a;
				text-align:right;
			}
			.jienen_text{
				font-size:24px;
				color:#7d93bc;
				font-weight:bold;
				text-align: right;
				padding-right: 10px;
				width:20%
			}
.tooltip {
	display:none;
	width:180px;
	height:180px;
	position:absolute;
	z-index:9999;
	border:1px solid #333;
	/*background:#f7f5d1;*/
	background:#fff;
	padding:1px;
	color:#808080;
	font-size:13px;
	text-align:left;
}
		</style>
		<script type="text/javascript">
			var select = "";
			$(function () {
			$.each(($("td[id^=td_tooltip]")), function(i, n){
				$(n).click(function(e) {
					var id = $(n).attr("id");
					if (select == id) {
						$('#tooltip').hide();
						select = "";
						$(n).removeClass("selected");
					} else {
						select = id;
						showPieChart(n);
						var no = $(n).data("no");
						
						var top = $(n).offset().top + 35;
						var left = $(n).offset().left;
						if (no == '1' || no == 'a') {
							left = $(n).offset().left - 108;
						}
						$('#tooltip').css({
              				top:top,
              				left:left
        				}).show();
        				$.each(($("td[id^=td_tooltip]")), function(i2, n2){
        					$(n2).removeClass("selected");
        				});
        				$(n).addClass("selected");
					}
				})
			});
			
				energyMonitor();
		 		$("#select_column_energytype_id,#select_column_timetype_id").change(function(){
					 energyMonitorColumn();
		    	});
		    });
			//能源监控主页数据加载
			function energyMonitor(){
				//showPieChart();
				energyMonitorColumn();
			}
			
			//column对应radio变更
			function energyMonitorColumn(){
				var columnEnergyType = $("#select_column_energytype_id").val();
				var columnTimeType = $("#select_column_timetype_id").val();
				$.ajax({
					type : 'POST',
					url : '${ctx}/app/energyint/energyMonitorJson.act',
					data : {
						"columnEnergyType": columnEnergyType,
						"columnTimeType": columnTimeType
					},
					dataType : 'JSON',
					async : true,
					success : function(json) {
						showColumnChart(json);
					},
					error : function(msg, status, e) {
						alert("能耗监控获取数据失败！");
					}
				});
			}
			
			//column图的显示
			function showColumnChart(json){
				var energy_type = $("#select_column_energytype_id").val();
				var column_suffix = 'kWh';
				if(energy_type == 2){
					column_suffix = 'kWh';
				}else if(energy_type == 3){
					column_suffix = 'm³';
				}else if(energy_type == 4){
					column_suffix = 'm³';
				}else if(energy_type == 5){
					column_suffix = 'L';
				}
				
				var column_name = $("#select_column_timetype_id").find("option:selected").text() + "的" + $("#select_column_energytype_id").find("option:selected").text();
				$('#container').highcharts({
		            chart: {
		            	width : 998,
						height : 255
		            },
		            exporting : {
						enabled : false
					},
					credits : {
						enabled : false
					},
		            title: {
		                text: ' '
		            },
		            xAxis: {
		                categories: json.bureauArray,
		                labels: {
		                	rotation: 50, //字体倾斜
                            align: 'left',
                            style: {
                            	backgroundColor:"#ffffff"
                            }
						}
		            },
		            yAxis : [{//First yAxis
						title : {
							text : $("#select_column_energytype_id").find("option:selected").text() + '(' + column_suffix + ')'
						}
		            },{ // Secondary yAxis
		                title: {
		                    text: '百分比(%)'
		                },
		                opposite: true
		            }],
		            tooltip: {
		                shared: true,
		                useHTML:true,
		            	headerFormat: '<b>{point.x}</b><table>',
		                pointFormat: '<tr><td style="color:{series.color};">{series.name}:</td><td style="text-align:right;">{point.y}</td></tr>',
		                footFormat: '</table>'
		            },
		            plotOptions: {
	                	column: {
	                    stacking: 'normal',
						pointPadding: 0.1,
						borderWidth: 0,
						pointWidth: 8
	               		 }
           		    },
		            legend : {
						enabled: false,
						style:
						{
							backgroundColor:"#ffffff"
						}
					},
		            series: [{
		                type: 'column',
		                name: column_name,
		                data: json.columnArray,
		                tooltip: {
		                    valueSuffix: ' ' + column_suffix
		                }
		            }, {
		                type: 'line',
		                name: '占所有单位百分比',
		                yAxis: 1,
		                data: json.lineArray,
			            tooltip: {
		                    valueSuffix: '%'
		                }
		            }]
		        });
			}
			//pie图的显示
			function showPieChart(n){
				
				var seriesData = [];
				var no = $(n).data("no");

				var kwh = parseInt($("#kwhpercent_"+no+"_id").val()*1000);
				var water = parseInt($("#waterpercent_"+no+"_id").val()*1000);
				var flow = parseInt($("#flowpercent_"+no+"_id").val()*1000);
				var oil = parseInt($("#oilpercent_"+no+"_id").val()*1000);

				seriesData.push(["电", kwh]);
				seriesData.push(["水", water]);
				seriesData.push(["气", flow]);
				seriesData.push(["油", oil]);
				
				$('#pie_container').highcharts({
		            chart: {
		                plotBackgroundColor: null,
		                plotBorderWidth: null,
		                plotShadow: false
		            },
		            exporting : {
						enabled : false
					},
					credits : {
						enabled : false
					},
		            title: {
		                text: ''
		            },
		            tooltip: {
		            	formatter: function() {
		            		var percent = this.percentage + "";
		            		//alert(percent.length);
		            		if (percent.length > 4) {
		            			var dotIndex = percent.indexOf(".");
		            			percent = percent.substring(0, dotIndex + 2);
		            		}
		                    return '<b>'+ this.point.name +': '+ percent +'%</b>';
		                 }
		            },
		            plotOptions: {
		                pie: {
		                    allowPointSelect: true,
		                    cursor: 'pointer',
		                    dataLabels: {
		                        enabled: false
		                    },
		                    showInLegend: true
		                }
		            },
		            legend : {
						enabled: false
					},
		            series: [{
		                type: 'pie',
		                name: '百分比',
		                data: seriesData
		            }]
		        });
				
			}
			function frameDialogClose(){
			}
			
	function oilInput(){
		var url = "${ctx}/app/energyint/queryOilEnergyInput.act";
		openEasyWin("oilInput", "油耗采集录入", url, 750, 600, true, null, 'oilInputIframe');
	}
		</script>
	</head>

	<body style="background: #fff;width:1080px;">
	
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right_default" >
						能耗监控
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 660px">
				    <!--此处写页面的内容 -->
				    <div class="bgsgl_right_list_border">
						<div class="bgsgl_right_list_left">大厦各部门能耗横向对比</div>
					</div>
					<div class="energy_conter" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="energy_td_title"></td>
								<td width="15%">&nbsp;</td>
								<td>
									<table width="100%" border="0">
										<tr>
											<td class="energy_radiols_li">按时间</td>
										    <td class="energy_radiols_li">
										    	<s:select list="#request.energyOpt" id="select_column_timetype_id" listKey="key" listValue="value" name="select_column_timetype_id"/>
										    </td>
										    <td class="energy_radiols_li">按类型</td>
										    <td class="energy_radiols_li">
										    	<select id="select_column_energytype_id">
													<option value="1" selected="selected">总能耗</option>
													<option value="2">总电耗</option>
													<option value="3">总水耗</option>
													<option value="4">总气耗</option>
													<option value="5">总油耗</option>
										    	</select>
										    </td>
										    <td class="energy_radiols_li">
										    	<!-- <a class="fkdj_botton_le_tft" href="javascript:void(0);" id="button_column_id">确&nbsp;定</a> -->
										    </td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<div class="energy_flash_img" >
						<div id="container" ></div>
						<!-- <img src="images/energy_flash.jpg" /> -->
					</div>
					<div class="fkdj_sxry_list" >
						<div class="energy_fkdj_botton_ls">
							
							<a class="fkdj_botton_right" href="javascript:void(0);" onclick="showShelter('750','366','${ctx}/app/energyint/buildingEnergyHistory.act');" style="margin-left: 20px;">大厦历史查询</a>
							<a class="fkdj_botton_right" href="javascript:void(0);" onclick="showShelter('750','366','${ctx}/app/energyint/bureauEnergyHistory.act');" style="margin-left: 20px;">按单位历史查询</a>
							<a class="fkdj_botton_right" href="javascript:void(0);" onclick="javascript:oilInput()" style="margin-left: 20px;">油耗采集录入</a>
						</div>
					</div>
					<div class="bgsgl_clear"></div>
					
					<div class="bgsgl_right_list_border" >
					  <div class="bgsgl_right_list_left">大厦总能耗明细</div>
					</div>
					<div class="energy_conter" >
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="energy_title_fos" style="background:#ECECE4;">
								  <td class="energy_border_rights" width="8%">指标</td>
								  
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_1" data-no="12">${energyOpt['-12']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_2" data-no="11">${energyOpt['-11']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_3" data-no="10">${energyOpt['-10']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_4" data-no="9">${energyOpt['-9']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_5" data-no="8">${energyOpt['-8']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_6" data-no="7">${energyOpt['-7']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_7" data-no="6">${energyOpt['-6']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_8" data-no="5">${energyOpt['-5']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_9" data-no="4">${energyOpt['-4']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_10" data-no="3">${energyOpt['-3']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_11" data-no="2">${energyOpt['-2']}</td>
								  <td class="energy_border_rights_a" width="7%" id="td_tooltip_12" data-no="1">${energyOpt['-1']}</td>

								  <td class="energy_border_rights_a" width="8%" id="td_tooltip_13" data-no="a">当年累积</td>
								</tr>
								<tr style="background:#F6F5F1;">
								  <td class="energy_border_right">总能耗(kWh)</td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-12']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-11']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-10']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-9']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-8']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-7']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-6']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-5']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-3']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-2']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['-1']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_1_id">
									<fmt:formatNumber value="${totalMeasure['4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
								  </td>
								</tr>
								<tr style="background:#F0EFEB;">
								  <td class="energy_border_right">总电耗(kWh)</td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-12']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_12_id" value="<fmt:formatNumber value="${kwhMeasure['-12per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-11']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_11_id" value="<fmt:formatNumber value="${kwhMeasure['-11per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-10']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_10_id" value="<fmt:formatNumber value="${kwhMeasure['-10per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-9']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_9_id" value="<fmt:formatNumber value="${kwhMeasure['-9per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-8']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_8_id" value="<fmt:formatNumber value="${kwhMeasure['-8per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-7']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_7_id" value="<fmt:formatNumber value="${kwhMeasure['-7per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-6']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_6_id" value="<fmt:formatNumber value="${kwhMeasure['-6per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-5']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_5_id" value="<fmt:formatNumber value="${kwhMeasure['-5per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_4_id" value="<fmt:formatNumber value="${kwhMeasure['-4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-3']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_3_id" value="<fmt:formatNumber value="${kwhMeasure['-3per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-2']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_2_id" value="<fmt:formatNumber value="${kwhMeasure['-2per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['-1']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_1_id" value="<fmt:formatNumber value="${kwhMeasure['-1per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_2_id">
									<fmt:formatNumber value="${kwhMeasure['4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="kwhpercent_a_id" value="<fmt:formatNumber value="${kwhMeasure['4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								</tr>
								<tr style="background:#F6F5F1;">
								  <td class="energy_border_right">总水耗(m³)</td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-12']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_12_id" value="<fmt:formatNumber value="${waterMeasure['-12per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-11']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_11_id" value="<fmt:formatNumber value="${waterMeasure['-11per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-10']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_10_id" value="<fmt:formatNumber value="${waterMeasure['-10per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-9']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_9_id" value="<fmt:formatNumber value="${waterMeasure['-9per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-8']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_8_id" value="<fmt:formatNumber value="${waterMeasure['-8per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-7']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_7_id" value="<fmt:formatNumber value="${waterMeasure['-7per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-6']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_6_id" value="<fmt:formatNumber value="${waterMeasure['-6per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-5']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_5_id" value="<fmt:formatNumber value="${waterMeasure['-5per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_4_id" value="<fmt:formatNumber value="${waterMeasure['-4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-3']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_3_id" value="<fmt:formatNumber value="${waterMeasure['-3per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-2']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_2_id" value="<fmt:formatNumber value="${waterMeasure['-2per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['-1']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_1_id" value="<fmt:formatNumber value="${waterMeasure['-1per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_3_id">
									<fmt:formatNumber value="${waterMeasure['4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="waterpercent_a_id" value="<fmt:formatNumber value="${waterMeasure['4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								</tr>
								<tr style="background:#F0EFEB;">
								  <td class="energy_border_right">总气耗(m³)</td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-12']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_12_id" value="<fmt:formatNumber value="${flowMeasureFlow['-12per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-11']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_11_id" value="<fmt:formatNumber value="${flowMeasureFlow['-11per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-10']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_10_id" value="<fmt:formatNumber value="${flowMeasureFlow['-10per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-9']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_9_id" value="<fmt:formatNumber value="${flowMeasureFlow['-9per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-8']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_8_id" value="<fmt:formatNumber value="${flowMeasureFlow['-8per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-7']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_7_id" value="<fmt:formatNumber value="${flowMeasureFlow['-7per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-6']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_6_id" value="<fmt:formatNumber value="${flowMeasureFlow['-6per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-5']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_5_id" value="<fmt:formatNumber value="${flowMeasureFlow['-5per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_4_id" value="<fmt:formatNumber value="${flowMeasureFlow['-4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-3']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_3_id" value="<fmt:formatNumber value="${flowMeasureFlow['-3per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-2']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_2_id" value="<fmt:formatNumber value="${flowMeasureFlow['-2per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['-1']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_1_id" value="<fmt:formatNumber value="${flowMeasureFlow['-1per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${flowMeasureFlow['4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="flowpercent_a_id" value="<fmt:formatNumber value="${flowMeasureFlow['4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								</tr>
								<tr style="background:#F6F5F1;">
								  <td class="energy_border_right">总油耗(L)</td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-12']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_12_id" value="<fmt:formatNumber value="${oilMeasure['-12per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-11']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_11_id" value="<fmt:formatNumber value="${oilMeasure['-11per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-10']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_10_id" value="<fmt:formatNumber value="${oilMeasure['-10per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-9']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_9_id" value="<fmt:formatNumber value="${oilMeasure['-9per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-8']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_8_id" value="<fmt:formatNumber value="${oilMeasure['-8per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-7']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_7_id" value="<fmt:formatNumber value="${oilMeasure['-7per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-6']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_6_id" value="<fmt:formatNumber value="${oilMeasure['-6per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-5']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_5_id" value="<fmt:formatNumber value="${oilMeasure['-5per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_4_id" value="<fmt:formatNumber value="${oilMeasure['-4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-3']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_3_id" value="<fmt:formatNumber value="${oilMeasure['-3per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-2']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_2_id" value="<fmt:formatNumber value="${oilMeasure['-2per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['-1']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_1_id" value="<fmt:formatNumber value="${oilMeasure['-1per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								  <td class="energy_border_right" id="left_energy_4_id">
									<fmt:formatNumber value="${oilMeasure['4']}"  pattern="###.#" type="number" maxFractionDigits="1"/>
									<input type="hidden" id="oilpercent_a_id" value="<fmt:formatNumber value="${oilMeasure['4per']}"  pattern="0.###" type="number" maxFractionDigits="3"/>"/>
								  </td>
								</tr>
							</table>
						<div id="tooltip" class="tooltip">
							<div id="pie_container" style="width: 180px; height: 180px;"></div>
						</div>
				  	</div>
				</div>
	</body>
</html>