<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>单位能耗历史查看</title>
		<%@ include file="../../common/include/meta.jsp" %>
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/scripts/basic/jquery.js.gzip"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/exporting.js"></script>
		<style type="text/css">
			#select_bureau_id{
				width: 160px;
				margin: 0 5px;
			}
			.history_title_class{
				font-size:12px;
				color:#7a7a7a;
			}
		</style>
		<script type="text/javascript">
			$(function () {
				bureauEnergyHistory();
				$("#select_bureau_id,#select_column_yearmonth_id,#select_column_energytype_id").change(function(){
					bureauEnergyHistory();
		    	});
				/* $('#button_column_id').click(function(){
					bureauEnergyHistory();
				}); */
		    });
			function bureauEnergyHistory(){
				var bureau = $("#select_bureau_id").val();
				var yearmonth = $("#select_column_yearmonth_id").val();
				var energyType = $("#select_column_energytype_id").val();
				$.ajax({
					type : 'POST',
					url : '${ctx}/app/energyint/bureauEnergyHistoryJson.act',
					data : {
						"energyType": energyType,
						"bureau": bureau,
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
				var bureau_name = $("#select_bureau_id").find("option:selected").text();
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
		                		s = '<b>'+bureau_name +'</b><br>' + this.x  + year_month_name + ' 总能耗: '+ this.y +' kWh';
		                	}else if( energy_type == 2){
		                		s = '<b>'+bureau_name +'</b><br>' + this.x  + year_month_name + ' 总电耗: '+ this.y + ' kWh';
		                	}else if( energy_type == 3){
		                		s = '<b>'+bureau_name +'</b><br>' + this.x  + year_month_name + ' 总水耗: '+ this.y + ' m³';
		                	}else if( energy_type ==4 ){
		                		s = '<b>'+bureau_name +'</b><br>' + this.x  + year_month_name + ' 总气耗: '+ this.y + ' m³';
		                	}else if( energy_type ==5 ){
		                		s = '<b>'+bureau_name +'</b><br>' + this.x  + year_month_name + ' 总油耗: '+ this.y + ' L';
		                	}
		                    return s;
		                }
		            },
		            legend : {
						enabled: false
					},
		            series: [{
		                type: 'column',
		                name: bureau_name,
		                data: json.energyArray
		            }, {
		                type: 'line',
		                name: bureau_name,
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
	<body>
		<div class="main1_main2_right_khfw">
			<div class="khfw_wygl">
	    		<div class="bgsgl_right_list_border">
		  			<div class="bgsgl_right_list_left">单位历史能耗查询</div>
				</div>
				<div class="clear"></div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
 					<tr>
 						<td>&nbsp;</td>
					    <td width="13%">&nbsp;</td>
					    <td class="energy_radioks"></td>
					    <td width="16%"></td>
					    <td width="74%">
					    	<table width="100%" border="0">
								<tr>
								    <td class="energy_radioks" style="width: 10%;">选择单位</td>
									<td class="energy_radios">
									<c:if test="${empty bureauArray }">
										<select id="select_bureau_id">
											<option value="1">暂无数据</option>
										</select>
									</c:if>
									<c:if test="${not empty bureauArray }">
										<select id="select_bureau_id">
												<c:forEach items="${bureauArray }" var="bureauName" varStatus="status">
													<option value="${bureauName}">${bureauName}</option>
												</c:forEach>
										</select>
									</c:if>
									</td>
									<td class="energy_radiols_li">年/月</td>
									<td class="energy_radiols_li">
								    	<select id="select_column_yearmonth_id">
											<option value="1" selected="selected">年</option>
											<option value="2">月</option>
								    	</select>
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
								    <td width="5%"></td>
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
					<!-- <img src="images/energy_szt_ls.jpg" width="717" height="282" /> -->
				</div>
			</div>
		</div>
	</body>
</html>