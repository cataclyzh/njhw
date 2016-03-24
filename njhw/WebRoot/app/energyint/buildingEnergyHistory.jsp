<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>大厦能耗历史查看</title>
		<%@ include file="../../common/include/meta.jsp" %>
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/scripts/basic/jquery.js.gzip"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
		<script type="text/javascript">
			$(function () {
				buildingEnergyHistory();
				$("#select_column_energytype_id,#select_column_yearmonth_id").change(function(){
					buildingEnergyHistory();
		    	});
				
				/* $("#button_column_id").click(function(){
					buildingEnergyHistory();
				}); */
		    });
			function buildingEnergyHistory(){
				var energyType=$('#select_column_energytype_id').val();
				var yearmonth = $("#select_column_yearmonth_id").val();
				$.ajax({
					type : 'POST',
					url : '${ctx}/app/energyint/buildingEnergyHistoryJson.act',
					data : {"energyType": energyType,
							"yearmonth": yearmonth
					},
					dataType : 'JSON',
					async : true,
					success : function(json) {
						showEnergyChart(json);
					},
					error : function(msg, status, e) {
					}
				});
			}
			function showEnergyChart(json){
				var column_name = $("#select_column_energytype_id").find("option:selected").text();
				var year_month_name = $("#select_column_yearmonth_id").find("option:selected").text();
				$('#container').highcharts({
		            chart: {
		            	width: 717,
		            	height: 282
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
		                categories: json.yearArray
		            },
		            yAxis : {
						title : {
							text : ''
						}
		            },
		            tooltip: {
		                formatter: function() {
		                	var s;
		                	var energy_type = $('#select_column_energytype_id').val();
		                	if( energy_type == 1){
		                		s = '<b>'+ this.x + year_month_name + '</b><br>' + ' 总能耗: ' + this.y + ' kWh';
		                	}else if( energy_type == 2){
		                		s = '<b>'+ this.x + year_month_name + '</b><br>' + ' 总电耗: ' + this.y + ' kWh';
		                	}else if( energy_type == 3){
		                		s = '<b>'+ this.x + year_month_name + '</b><br>' + ' 总水耗: ' + this.y + ' m³';
		                	}else if( energy_type ==4 ){
		                		s = '<b>'+ this.x + year_month_name + '</b><br>' + ' 总气耗: ' + this.y + ' m³';
		                	}else if( energy_type ==5 ){
		                		s = '<b>'+ this.x + year_month_name + '</b><br>' + ' 总油耗: ' + this.y + ' L';
		                	}
		                    return s;
		                }
		            },
		            legend : {
						enabled: false
					},
		            series: [{
		                type: 'column',
		                name: column_name,
		                data: json.energyArray
		            }, {
		                type: 'line',
		                name: column_name,
		                data: json.energyArray,
		                marker: {
		                	lineWidth: 2,
		                	lineColor: Highcharts.getOptions().colors[1]
		                }			            
		            }]
		        });
			}
		</script>
	</head>
	<body style=" background:#000">
		<div class="main1_main2_right_khfw">
			<div class="khfw_wygl">
	    		<div class="bgsgl_right_list_border">
		  			<div class="bgsgl_right_list_left">大厦能耗历史查看</div>
				</div>
				<div class="clear"></div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
				  <tr>
				    <td>&nbsp;</td>
				    <td width="30%">&nbsp;</td>
				    <td class="energy_radioks"></td>
				    <td width="21%"></td>
				    <td width="34%">
						<table width="100%" border="0">
							<tr>
								<td class="energy_radiols">年/月</td>
									<td class="energy_radiols_li">
								    	<select id="select_column_yearmonth_id">
											<option value="1" selected="selected">年</option>
											<option value="2">月</option>
								    	</select>
								    </td>
							    <td class="energy_radiols">按类型</td>
							    <td class="energy_radios">
							    	<select id="select_column_energytype_id">
										<option value="1">总能耗</option>
										<option value="2">总电耗</option>
										<option value="3">总水耗</option>
										<option value="4">总气耗</option>
										<option value="5">总油耗</option>
							    	</select>
							    </td>
							   <!-- <td class="energy_radios">
									<a class="fkdj_botton_le_tft" href="javascript:void(0);" id="button_column_id">确&nbsp;定</a>
							   </td> -->
						  </tr>
						</table>
					</td>
			 	 </tr>
				</table>
				<div class="show_from">
					<div id="container"></div>
				</div>
			</div>
   		</div>
	</body>
</html>