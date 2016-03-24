<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html>
	<head>
	<title>运营管理中心</title>
	<%@ include file="/common/include/meta.jsp" %>
	<link type="text/css" rel="stylesheet" href="${ctx}/app/portal/operation/css/managerCenter.css" />
	<script type="text/javascript" src="${ctx}/app/portal/operation/js/managerCenter.js" ></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
	<script type="text/javascript">
		document.domain = "laisi.com";
	    
	    var Device;
	    var DeviceDefaultOption = {
	        IsFrameMap: true
	    }
	    
	    var Room;
	    var RoomDefaultOption = {
	        OnRoomSelected: function (RoomId, BlRoomId) {
        		Room.FlyToRoom(RoomId);
	        },
	        //切换楼层事件
	        OnFloorChanged: function (floorId) {},
	        IsFrameMap: true,					// 是否为线框图
	        SelectedColor: "255,0,0,0.5",		// 选中房间的颜色
	        UnSelectedColor: "255,180,0,0.5",	// 未选中房间的颜色
	       	RoomTranparent: 0.7					// 透明度(0-1之间)
	    }
		
		$(function() {
			loadMessage();
			setType("F");
		});
		
		function setType(typeName) {
			$("#type").val(typeName);
		}
		
		function initSW() {
			if (Device != null) Device.ClearAllExistDevice();
			if (Room != null) Room.ResetAllRooms();
			
			if ($("#type").val() == "F") {
				Device = frames['threeDimensional'].Init3D("Device", DeviceDefaultOption);
				Device.InitDevice();
				Device.InitDeviceList(parseInt($("#floorNo").val()), 0, 0, true);
			} else if ($("#type").val() == "D") {
				Device = frames['threeDimensional'].Init3D("Device", DeviceDefaultOption);
				Device.InitDevice();
				Device.InitDeviceList(0, 0, parseInt($("#deviceType").val()), true);
			} else if ($("#type").val() == "U") {
				Room = frames['threeDimensional'].Init3D("Room", RoomDefaultOption);
				Room.InitGetRoomsByFloorIdInOrg(0, true, false, parseInt($("#org").val()));
				Room.synchType = 2;
				Room.DisableSelectAll();	// 禁用全选和保存按钮
		   		Room.DisableSave();
			}
		}
	
	function loadMessage(){
		propertyMonitor();
		energyMonitor();
		enviromentMonitor();
	//	weatherMonitor();
		parkingMonitor();
		videoMonitor();
		//gatesMonitor();
	}
	//设置定时
	// setInterval("loadMessage();",10000);
	//能耗监控加载
	function energyMonitor(){
		var url="${ctx}/app/operation/energyMonitor.act";
		var data = {};
		sucFun = function (data){
			if(data.state =='1'){
				$("#energy_monitor").empty();
				var msg = "";
				msg+="<span class='meter_pic_span1'>"+data.electric+"</span>";
				msg+="<span class='meter_pic_span2'>"+data.water+"</span>";
				msg+="<span class='meter_pic_span3'>"+data.air+"</span>";
				$("#energy_monitor").append(msg);
			}
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//环境监控加载
	function enviromentMonitor(){
		var url="${ctx}/app/operation/enviromentMonitor.act";
		var data = {};
		sucFun = function (data){
			if(data.state =='1'){
				$("#enviroment_monitor").empty();
				var msg = '<div class="buildingIn">';
				msg+="<span class='buildingIn_span1'>"+data.temperature+"</span>";
				msg+="<span class='buildingIn_span2'>"+data.co2+"</span>";
				msg+="<span class='buildingIn_span3'>"+data.humidity+"</span>";
				msg+="</div><div class='buildingOut'>";
				msg+="<span class='buildingOut_span1'>"+data.temperature+"</span>";
				msg+="<span class='buildingOut_span2'>"+data.co2+"</span>";
				msg+="<span class='buildingOut_span3'>"+data.humidity+"</span>";
				msg+="</div>";
				$("#enviroment_monitor").append(msg);
			}
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
		
	}
	//摄像头监控
	function videoMonitor(){
		var url="${ctx}/app/operation/videoMonitor.act";
		var data = {};
		sucFun = function (data){
			if(data.state =='1'){
				$("#video_monitor").empty();
				var msg="<ul>";
				var j=0;
				$.each(data.list,function(i){
				 	j=i+1
					msg+="<li><img class=" +data.list[i].link + " onclick='javascript:videoHref(this);' src='${ctx}/app/portal/operation/images/monitor"+j+".png'/></li>";
				});
				msg+="</ul>";
				
				$("#video_monitor").append(msg);
			}
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	function videoHref(dom){
		//window.location.href=dom.className;
		window.open(dom.className);
	}
	//餐厅加载
	function dinlingMonitor(){
		var url="${ctx}/app/operation/dinlingMonitor.act";
		var data = {};
		sucFun = function (data){
			
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//文印加载
	function printMonitor(){
		var url="${ctx}/app/operation/printMonitor.act";
		var data = {};
		sucFun = function (data){
			
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//天气加载
	function weatherMonitor(){
		var url="${ctx}/app/operation/weatherMonitor.act";
		var data = {};
		sucFun = function (data){
			if(data.state =='1'){
				$("#weather_monitor").empty();
				var msg = "";
				msg+="<span style='float:right;color:red;'>天气预报："+data.msg+"</span><br/>";
				$("#weather_monitor").append(msg);
			}
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//停车场信息加载
	function parkingMonitor(){
		var url="${ctx}/app/operation/parkingMonitor.act";
		var data = {};
		sucFun = function (data){
			if(data.state =='1'){
				$("#parking_monitor_road").empty();
				$("#parking_monitor_A").text(data.num1);
				$("#parking_monitor_B").text(data.num2);
				var meg="";
				meg+="<p><span>"+data.roadE+"&nbsp&nbsp</span><span class='road_span'>"+data.roadE_info+"</span></p>";
				meg+="<p><span>"+data.roadW+"&nbsp&nbsp</span><span class='road_span'>"+data.roadW_info+"</span></p>";
				meg+="<p><span>奥体南路&nbsp&nbsp</span><span class='road_span'>畅通</span></p>";
				meg+="<p><span>奥体北路&nbsp&nbsp</span><span class='road_span'>畅通</span></p>";
				$("#parking_monitor_road").append(meg);
			}
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//异常事件处理
	function uneventMonitor(){
		var url="${ctx}/app/operation/uneventMonitor.act";
		var data = {};
		sucFun = function (data){
			
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//闸机口加载
	function gatesMonitor(){
		var url="${ctx}/app/operation/gatesMonitor.act";
		var data = {};
		sucFun = function (data){
			if(data.state=='1'){
				//$("#gates_monitor").empty();
				var div="";
				$.each(data.list,function(i){
					div+="<span onmouseover='gatesInfoMessage("+data.list[i][0]+");'>"+data.list[i][1]+"</span><br/>";
					
				});
				$("#gates_monitor").append(div);
			}
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	function gatesInfoMessage(gatesId){
		var url="${ctx}/app/operation/gatesMonitorByGatesId.act";
		var data = {gatesId:gatesId};
		sucFun = function (data){
			if(data.state=='1'){
				$("#gates_info").empty();
				var msg = "<div>";
				msg+="<span style='float:right;color:red;'>闸机名称："+data.name+"</span><br/>";
				msg+="<span style='float:right;color:red;'>闸机状态："+data.work+"</span><br/>";
				msg+="<span style='float:right;color:red;'>人流量："+data.num+"</span></div>";
				$("#gates_info").append(msg);
				$("#gates_info").show();
			}
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//三维地图加载
	function swMonitor(){
		var url="${ctx}/app/operation/swMonitor.act";
		var data = {};
		sucFun = function (data){
			
		};
		errFun = function (data){
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	//物业呼叫中心加载
	function propertyMonitor(){
		var url="${ctx}/app/operation/propertyMonitor.act";
		var data = {};
		sucFun = function (data){
			if(data.state=='1'){
				$("#property_monitor").empty();
				var table="<table class='table'><thead><tr><td align='center'>报修设备</td><td align='center'>处理状态</td><td align='center'>报修人</td></tr></thead><tbody>"
				$.each(data.list,function(i){
					table+="<tr>"
					table+="<td>"+data.list[i][0]+"</td>";
					if(data.list[i][1]=="01"){
						table+="<td>申请中</td>";
					}else if(data.list[i][1]=="02"){
						table+="<td>已派单</td>";
					}else if(data.list[i][1]=="03" || data.list[i][1]=="04"){
						table+="<td>已完成</td>";
					}
					table+="<td>"+data.list[i][2]+"</td>";
					table+="</tr>";
				});
				table+="</tbody></table>";
				$("#property_monitor").append(table);
			}
		};
		errFun = function (data){
			
		};
		ajaxQueryJSON(url,data,sucFun,errFun);
	}
	function propertyHref(){
		window.location.href="${ctx}/app/pro/init1.act";
	}

	//访客管理
	function caller_href(){
		window.location.href="${ctx}/app/caller/initBlackList.act";
	}
	//能耗管理
	function energyMonitor_href(){
		window.location.href="${ctx}/app/caller/initBlackList.act";
	}
	//人员管理
	function person_href(){
		window.location.href="${ctx}/app/caller/initBlackList.act";
	}
	//个人服务
	function service_href(){
		window.location.href="${ctx}/app/caller/initBlackList.act";
	}
	//楼宇监控
	function builing_href(){
		window.location.href="${ctx}/app/caller/initBlackList.act";
	}
	//能耗管理
	function property_href(){
		window.location.href="${ctx}/app/caller/initBlackList.act";
	}
	</script>
	
	</head>
<body>
<!--<jsp:include page="/app/portal/toolbar/toolbar_operation.jsp"></jsp:include> -->
	<div class="header"></div>	
	<div class="main">
		<iframe src="" class="threeDimensional_iframe" frameborder="0"></iframe>
		<div class="threeDimensional">
			<input type="hidden" id="type"/>
			<div class="threeDimensional_top" id="threeDimensional_top">
				<div class="threeDimensional_top1" onclick="change_bg(this); setType('F');" id="buliding">
					<span>楼层</span>
				</div>
				<div class="threeDimensional_top1" onclick="change_bg(this); setType('D');" id="equipment">
					<span>设备</span>
				</div>
				<div class="threeDimensional_top1" onclick="change_bg(this); setType('U');" id="unit">
					<span>单位</span>
				</div>
				<div class="threeDimensional_main" id="equipment_main">
					<span style="font-size:12px;color:#fff;">设备：</span>
					<select id="deviceType">
						<option value="1">闸机</option>
						<option value="2">门禁</option>
						<option value="3">门锁</option>
						<option value="4">摄像头</option>
						<option value="5">空调</option>
						<option value="6">电灯</option>
						<option value="7">电梯</option>
						<option value="8">泊车</option>
						<option value="9">温度</option>
						<option value="10">湿度</option>
						<option value="11">二氧化碳</option>
						<option value="12">一氧化碳</option>
					</select>
				</div>
				<div class="threeDimensional_main1" id="buliding_main">
					<span style="font-size:12px;color:#fff;">楼层：</span>
					<select id="floorNo">
						<option value="56">一楼</option>
						<option value="55">二楼</option>
						<option value="27">三楼</option>
						<option value="31">四楼</option>
						<option value="32">五楼</option>
						<option value="35">六楼</option>
						<option value="37">七楼</option>
						<option value="38">八楼</option>
						<option value="39">九楼</option>
						<option value="40">十楼</option>
						<option value="41">十一楼</option>
						<option value="42">十二楼</option>
						<option value="43">十三楼</option>
						<option value="44">十四楼</option>
						<option value="45">十五楼</option>
						<option value="46">十六楼</option>
						<option value="47">十七楼</option>
						<option value="48">十八楼</option>
						<option value="49">十九楼</option>
						<option value="50">二十楼</option>
						<option value="51">二十一楼</option>
					</select>
				</div>
				<div class="threeDimensional_main2" id="unit_main">
					<span style="font-size:12px;color:#fff;">单位：</span>
					<select id="org" name="org">
						<c:forEach items="${orgList}" var="org">
							<option value="${org.orgId }">${org.name }</option>
						</c:forEach>
					</select>
				</div>	
				<div class="threeDimensional_btn" onclick="initSW()"></div>	
			</div>
		</div>
		<div class="main_top">
			<div class="main_top_left">
				<div class="main_top_left1">
					<div class="main_top_left1_top">
						<div class="tip1"></div>
						<div class="tip_span"><span>能耗监控</span></div>
					</div>
					<div class="main_top_left1_main">
						<div class="all_meter">
							<div class="meter"></div>
							<div class="meter"></div>
							<div class="meter"></div>
						</div>
						<div class="all_meter_span">
							<span class="meter_span">总体能耗</span>
							<span class="meter_span">单位面积能耗</span>
							<span class="meter_span">人均能耗</span>
						</div>
						<div class="all_meter_pic" id="energy_monitor">
							<span class="meter_pic_span1">1411</span>
							<span class="meter_pic_span2">200</span>
							<span class="meter_pic_span3">100</span>
						</div>
					</div>
				</div>
				<div class="main_top_left2">
					<div class="main_top_left2_main">
						<div class="building" id="enviroment_monitor">
							<div class="buildingIn">
								<span class="buildingIn_span1">14</span>
								<span class="buildingIn_span2">14</span>
								<span class="buildingIn_span3">14</span>
							</div>
							<div class="buildingOut">
								<span class="buildingOut_span1">14</span>
								<span class="buildingOut_span2">14</span>
								<span class="buildingOut_span3">14</span>
							</div>
						</div>
					</div>
					<div class="main_top_left2_top">
						<div class="tip2"></div>
						<div class="tip_span"><span>安防监控</span></div>
					</div>
				</div>
			</div>
			<div class="main_top_main">
				<div class="main_top_main_top"></div>
				<div class="main_top_main_main">
					<iframe id="threeDimensional" name="threeDimensional" src="http://tuhui.laisi.com/test/index.htm" width="100%" height="100%"></iframe>
				</div>
			</div>
			<div class="main_top_right">
				<div class="main_top_right1">
					<img src="" id="weather_img" class="weather_img"/>
					<div class="weather_span">
						<span id="now_time" class="now_time"></span>
						<span class="word">南京</span>
						<span id="now_weather" class="now_weather"></span>
						<span id="now_temperature" class="now_temperature"></span>
				</div>
				</div>
				<div class="main_top_right2">
					<div class="main_top_right2_car">
						<div class="main_top_right2_car_span">
						<span id="parking_monitor_A"></span>
						</div>
						<div class="main_top_right2_car_span">
						<span id="parking_monitor_B"></span>
						</div>
					</div>
					<div class="main_top_right2_road" id="parking_monitor_road">
						<p><span>奥体南路&nbsp&nbsp</span><span class="road_span">畅通</span></p>
						<p><span>奥体南路&nbsp&nbsp</span><span class="road_span">畅通</span></p>
						<p><span>奥体南路&nbsp&nbsp</span><span class="road_span">畅通</span></p>
						<p><span>奥体南路&nbsp&nbsp</span><span class="road_span">畅通</span></p>
					</div>
					<div class="main_top_right2_btn">
						<div class="main_top_right2_btn1" onmouseover="hover(this,0,0)" onmouseout="hover(this,1,0)"></div>
						<div class="main_top_right2_btn2" onmouseover="hover(this,0,1)" onmouseout="hover(this,1,1)"></div>
						<div class="main_top_right2_btn3" onmouseover="hover(this,0,2)" onmouseout="hover(this,1,2)"></div>
						<div class="main_top_right2_btn4" onmouseover="hover(this,0,3)" onmouseout="hover(this,1,3)"></div>
					</div>
				</div>
				<div class="main_top_right3">
					<div class="main_top_right3_top">
						<div class="zhu_div"></div>
						<div class="zhu_div1"></div>
						<div class="zhu_div2"></div>
						<div class="zhu_div3"></div>
						<div class="zhu_div4"></div>
						<table class="main_top_right3_top_table">
							<tr>
								<td style="width:80px;"><span>运行状态</span></td>
								<td><span>正常</span></td>
								<td><span style="color:red">故障</span></td>
								<td><span>正常</span></td>
								<td><span>正常</span></td>
								<td><span>正常</span></td>
							</tr>
							<tr>
								<td><span>故障次数</span></td>
								<td><span class="table_num">3</span></td>
								<td><span class="table_num">1</span></td>
								<td><span class="table_num">2</span></td>
								<td><span class="table_num">1</span></td>
								<td><span >2</span></td>
							</tr>
						</table>
					</div>
					<div class="main_top_right3_main">
						<div class="main_top_right3_main_left">
							<p>报修任务数</p>
							<p>已派单数</p>
							<p>回访次数</p>
						</div>
						<div class="main_top_right3_main_main"></div>
						<div class="main_top_right3_main_right">
							<span style="margin-top:3px">新任务</span>
							<div class="main_top_right3_main_right_bg">
								<span>2</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="main_bottom">
		
		<div class="main_bottom1" id="video_monitor">
		<ul>
			<li><img src="${ctx}/app/portal/operation/images/monitor1.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor2.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor3.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor4.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor5.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor6.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor7.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor8.png"/></li>
			<li><img src="${ctx}/app/portal/operation/images/monitor9.png"/></li>
		</ul>
		</div>
		<div class="main_bottom3">
			<div class="main_bottom3_top">
				<div class="main_bottom3_top_left"></div>
				<div class="main_bottom3_top_right">
					<div style="margin:10px 0 0 10px"><span style="color:#003366;font-size:16px;">一号食堂</span></div>
					<div style="margin:0px 0 0 60px"><span style="color:#003366">空余餐位：</span><span class="some_num" style="color:#1C71B4;font-size:22px;">143</span></div>
					<div style="margin:10px 0 0 60px"><span style="color:#003366">人员流量：</span><span class="some_num" style="color:#1C71B4;font-size:22px;">22</span></div>
					<div class="main_bottom3_top_right1">
						<div class="main_bottom3_top_right1_left"></div>
						<div style="float:right;height:19px;line-height:19px;margin-right:5px;"><span style="color:#1C71B4;cursor:pointer">126评价</span></div>
					</div>
				</div>
			</div>
			<div class="main_bottom3_bottom">
				<div class="main_bottom3_bottom_left"></div>
				<div class="main_bottom3_bottom_right">
					<div style="margin:10px 0 0 10px"><span style="color:#003366;font-size:16px;">二号食堂</span></div>
					<div style="margin:0px 0 0 60px"><span style="color:#003366">空余餐位：</span><span class="some_num" style="color:#1C71B4;font-size:22px;">143</span></div>
					<div style="margin:10px 0 0 60px"><span style="color:#003366">人员流量：</span><span class="some_num" style="color:red;font-size:22px;">220</span></div>
					<div class="main_bottom3_top_right1">
						<div class="main_bottom3_top_right1_left"></div>
						<div style="float:right;height:19px;line-height:19px;margin-right:5px;"><span style="color:#1C71B4;cursor:pointer">126评价</span></div>
					</div>
				</div>
			</div>
		</div>
		<div class="main_bottom2"></div>
		<div class="main_bottom4">
			<div class="main_bottom4_main1">
				<div class="main_bottom4_main1_span">
					<span>资产构成分析</span>
				</div>
				<div class="main_bottom4_main1_main"></div>
			</div>
			<div class="main_bottom4_main2">
				<div class="main_bottom4_main3">
				<span style="margin-top:3px;">新任务</span>
				<div class="main_top_right3_main_right_bg">
					<span>2</span>
				</div>
			</div>
			<div class="main_bottom4_main2_span">
				<span>资产异常报警前五名</span>
			</div> 
			<div class="main_bottom4_main2_search"></div>
			<div class="main_bottom4_main2_main"></div>
			</div>
		</div>
	</div>
	<div class="main_bottom_float" id="main_bottom_float">
		<div class="main_bottom_float_img1"  onmouseover="float_hover(this)" onmouseout="float_out(this)"></div>
		<div class="main_bottom_float_img2" onmouseover="float_hover(this)" onmouseout="float_out(this)"></div>
		<div class="main_bottom_float_img3" onmouseover="float_hover(this)" onmouseout="float_out(this)"></div>
		<div class="main_bottom_float_img4" onmouseover="float_hover(this)" onmouseout="float_out(this)"></div>
		<div class="main_bottom_float_img5" onmouseover="float_hover(this)" onmouseout="float_out(this)"></div>
		<div class="main_bottom_float_img6" onmouseover="float_hover(this)" onmouseout="float_out(this)"></div>
	</div>
	<div class="main_bottom_float2" onmouseover="float_show()"></div>
	<div class="main_bottom_float3" id="main_bottom_float3" onmouseover="float_leave()"></div>
	</body>
</html>