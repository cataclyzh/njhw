function showConditioner(){
	$.getJSON(_ctx+"/app/integrateservice/queryConditionerTemperature.act", function(json){
		var temperature = json.temperature;
		var conditionerTemperature = json.conditionerTemperature;
		var coolId = json.coolId;
		var openStatus  = json.openStatus;
		$("#room_temperature").html(temperature);
		$("#conditioner_temperature").html(conditionerTemperature);
		$("#conditioner_temperature").attr("coolId",coolId);
		if(openStatus == "close"){
			//2015-6-18
			$("#conditioner_normal").hide();
			$("#conditioner_error").show();
			
			//$("#conditioner_normal").show();
			//$("#conditioner_error").hide();
		}else{
			$("#conditioner_normal").show();
			$("#conditioner_error").hide();
		}
	});
}

//点击放大我的节能功能
function showLargeChart(){
	var url = _ctx+"/app/energyint/energyContribution.act";
	//openEasyWin('winId','我的节能贡献',url,'700','500',true);
	showShelter('670','500',url);
}

//节能显示功能
function showEnergy(){
	$.ajax({
		type : 'POST',
		url : _ctx+'/app/energyint/energyContributionJson.act',
		data : {"dayType": false},
		dataType : 'JSON',
		async : true,
		success : function(data) {
			var highcharts_data = jQuery.parseJSON(data);
			$('#container').highcharts({
				chart : {
					type : 'line',
					width : 280,
					height : 180
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
					max : highcharts_data.xAxisMax
				},
				yAxis : [{
					title : {
						text : '',
						style: {
							fontSize: '10px'
						}
					},
					plotLines : [{
						value : 0,
						width : 1,
						color : '#808080'
					}]
				},{ // Secondary yAxis
					labels: {
	                    format: '{value}',
	                    style: {
	                        color: '#4572A7'
	                    }
	                },
	                title: {
	                    text: '',
	                    style: {
							fontSize: '10px'
	                    }
	                },
	                opposite: true
	            }],
				tooltip : {
					enabled: false
				},
				plotOptions: {
			        line: {
			            lineWidth: 1.0,
			            fillOpacity: 0.0,
						marker: {
			                enabled: false,
			                states: {
			                    hover: {
			                        enabled: false
			                    }
			                }
			            },
			            shadow: false
			        }
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
						fontSize: '10px'
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
				    "<td class='jienen_titie_unit'>kWh</td>"+
			    "</tr>"+
				"<tr>"+
				    "<td class='jienen_titie'>我的本月能耗</td>"+
				    "<td class='jienen_text'>"+highcharts_data.energyCurrentMonth+"</td>"+
				    "<td class='jienen_titie_unit'>kWh</td>"+
				"</tr>"+
				"<tr>"+
				    "<td class='jienen_titie'>我的今日能耗</td>"+
				    "<td class='jienen_text'>"+highcharts_data.energyToday+"</td>"+
				    "<td class='jienen_titie_unit'>kWh</td>"+
				"</tr>"+
				"<tr>"+
				    "<td class='jienen_titie'>本月节能贡献</td>"+
				    "<td class='jienen_text'>"+highcharts_data.energyContribution+"</td>"+
				    "<td class='jienen_titie_unit'>kWh</td>"+
				"</tr>"
		 	);
		},
		error : function(msg, status, e) {
		}
	});
}

function controllerDevice(idPrefix, deviceType, optType){
	/*
    var temperature = $("#conditioner_temperature").html();
    var coolId =  $("#conditioner_temperature").attr("coolId");
    var tempTemperature = 0;
    if(parseFloat(temperature)==0.0){
    	easyAlert("提示信息", "房间"+roomName+"空调未开或无法连接服务器！","info");
    	return;
    }
    if(optType == "add"){
     	tempTemperature = parseFloat(temperature) + 0.5;
     	if(tempTemperature>35){
     		easyAlert("提示信息", "房间"+roomName+"空调温度不能高于35度！","info");
     		return;
     	}
     }else{
     	tempTemperature = parseFloat(temperature) - 0.5;
     	if(tempTemperature<5){
     		easyAlert("提示信息", "房间"+roomName+"空调温度不能低5度！","info");
     		return;
     	}
     }
	$.ajax({
		type: "POST",
		url: _ctx+"/app/integrateservice/controllerDevice.act",
		data: {"deviceType": deviceType, "optType": optType,"temperature": temperature,"coolId":coolId},
		dataType: 'text',
		async : false,
		success: function(msg){
			  if(msg == "success") {
			  	if (deviceType == "door") {
				  	$("#"+idPrefix+"_on").hide();
	  				$("#"+idPrefix+"_off").show();
			  		setTimeout("close_door()", 1000);	// 开门1秒之后关门
			  	}else if(deviceType == "conditioner"){
                    if(optType == "add"){
                    	temperature = parseFloat(temperature) + 0.5;
                    }else{
                    	temperature = parseFloat(temperature) - 0.5;
                    }
                    $("#conditioner_temperature").html(temperature);
				}else {
			  		if (optType == "on") {
			  			$("#"+idPrefix+"_on").hide();
			  			$("#"+idPrefix+"_off").show();
			  		} else {
			  			$("#"+idPrefix+"_on").show();
			  			$("#"+idPrefix+"_off").hide();
			  		}
			  	}
			  } else easyAlert("提示信息","操作失败","info");
		 },
		 error: function(msg, status, e){
			 easyAlert("提示信息", "操作出错","info");
		 }
	 });*/
}