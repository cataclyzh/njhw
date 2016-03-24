<!DOCTYPE html PUBliC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-04-03
- Description: 楼宇监控管理首页
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>楼宇监控管理</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/app/buildingmon/pageIndex/css/monitor.css" />
	<script type="text/javascript" src="${ctx}/app/buildingmon/pageIndex/js/monitor.js"></script>
	<script type="text/javascript">
		document.domain = "laisi.com";
	    var Device;
	    var DeviceDefaultOption = {
	        IsFrameMap: true
	    }
	    $(function() {
	    	inits();
			$("#radRoom").attr("checked", "checked");
			refreshWarningDevice();
			
			Device = frames['threeDimensional'].Init3D("Device", DeviceDefaultOption);
			Device.InitDevice();
		})
		
		var Room;
	    var RoomDefaultOption = {
	    	OnRoomSelected: function (RoomId, BlRoomId) {
	        	Room.FlyToRoom(RoomId);
	        },
	        //切换楼层事件
	        OnFloorChanged: function (floorId) {
	            //Room.SwitchFloor(floorId, function () {
	            //	if (Room.IsFrameMap)
	            //    	Room.InitGetRoomsByFloorIdInOrg(floorId, true, false, parseInt($("#org").val()));
	            //    else 
	            //    	Room.InitGetRoomsByFloorIdInOrg(floorId, false, false, parseInt($("#org").val()));
	            //});
	        },
	        IsFrameMap: true,					// 是否为线框图
	        SelectedColor: "255,0,0,0.5",		// 选中房间的颜色
	        UnSelectedColor: "255,180,0,0.5",	// 未选中房间的颜色
	       	RoomTranparent: 0.7					// 透明度(0-1之间)
	    }
	    
	    var PositionRoom;
	    var PositionRoomDefaultOption = {
	    	OnRoomSelected: function (RoomId, BlRoomId) {
	        	Room.FlyToRoom(RoomId);
	        },
	        //切换楼层事件
	        OnFloorChanged: function (floorId) {
	            //PositionRoom.SwitchFloor(floorId, function () {
	            //	if (PositionRoom.IsFrameMap)
	            //    	PositionRoom.InitGetRoomsByFloorIdInOrg(floorId, true, false, 0);
	            //    else 
	            //   	PositionRoom.InitGetRoomsByFloorIdInOrg(floorId, false, false, 0);
	            //});
	        },
	        IsFrameMap: true,					// 是否为线框图
	        SelectedColor: "255,0,0,0.5",		// 选中房间的颜色
	        UnSelectedColor: "255,180,0,0.5",	// 未选中房间的颜色
	       	RoomTranparent: 0.7					// 透明度(0-1之间)
	    }
	    
		// 快速定位
		function quickPosition(code){
			// 清空模型
			if (PositionRoom != null) PositionRoom.ResetAllRooms();
			if (Device != null) Device.ClearAllExistDevice();
			if (Room != null) Room.ResetAllRooms();
			Device.InitDeviceList(0, 0, code, true);
		}
		
		// 搜索定位
		function searchPosition(){
			var radValue = "", seaValue = "";
			$(":radio[id^=rad]:checked").each(function(){
				radValue = $(this).val();
				if (radValue == "r") seaValue = $("#RoomNum").val();
				else if (radValue == "u") seaValue = $("#org").val();
				else if (radValue == "v") seaValue = $("#visitor").val();
			});
			
			// 清空模型
			if (PositionRoom != null) PositionRoom.ResetAllRooms();
			if (Device != null) Device.ClearAllExistDevice();
			if (Room != null) Room.ResetAllRooms();
			
			//初始化
			if (radValue == "r")  {
				PositionRoom = frames['threeDimensional'].Init3D("Room", PositionRoomDefaultOption);
				PositionRoom.InitGetRoomByRoomId(parseInt(seaValue));
				PositionRoom.synchType = 2;
				PositionRoom.ShowFrameMap();
				PositionRoom.DisableSelectAll();	// 禁用全选和保存按钮
		   		PositionRoom.DisableSave();
			} else if (radValue == "u") {
				Room = frames['threeDimensional'].Init3D("Room", RoomDefaultOption);
				Room.InitGetRoomsByFloorIdInOrg(0, true, false, parseInt($("#org").val()));
				Room.synchType = 2;
				Room.DisableSelectAll();	// 禁用全选和保存按钮
		   		Room.DisableSave();
			} else if (radValue == "v") {
				// 通过ajax获得请求数据处理之后传给三维展示
				if (seaValue.replace(/(^\s*)|(\s*$)/g, "") != "") {
					$.ajax({
						type: "POST",
						url: "${ctx}/app/buildingmon/visitorPosition.act",
						data: {"visitorName": seaValue},
						dataType: 'json',
						async : false,
						success: function(json){
							if (json.visitorInfo == null) {
								alert("访客："+seaValue+"，无法定位！");
							} else {
								var SWVisitorObj = new Object();
								var SWVisitorInfo = new Array();
								SWVisitorInfo.push("访客名称：", json.visitorInfo.VI_NAME);
								SWVisitorObj.DeviceIds = json.points;
								SWVisitorObj.Visitor = SWVisitorInfo;
								
								Device = frames['threeDimensional'].Init3D("Device", DeviceDefaultOption);
								Device.InitVisitorPoint(SWVisitorObj);
							}
						}
					});
				} else {
					alert("请输入访客名称后定位");
				}
			}
		}
		
		// 报警处理
		function warningOperate(id){
			alert(id);
		}
		
		function showSW() {
			// 清空模型
			if (PositionRoom != null) PositionRoom.ResetAllRooms();
			if (Device != null) Device.ClearAllExistDevice();
			if (Room != null) Room.ResetAllRooms();
			Device.InitBadDeviceList("1,2,3",true);
		}
		
		function refreshWarningDevice() {
			$.ajax({
				type: "POST",
				url: "${ctx}/app/buildingmon/refreshWarningDevice.act",
				dataType: 'json',
				async : false,
				success: function(json){
				  	$("#warn_table tr:gt(0)").remove();
					$.each(json,function(i){
						$("#warn_table").append("<tr><td align='center'>"+(i+1)+"</td><td><a onclick='warningOperate("+json[i].nodeId+")' href='javascript:void(0)'>"+json[i].extResName+"</a></td><td>"+json[i].extResType+"</td><td>"+json[i].statusName+"</td></tr>")
					})
				}
			 });
	    }
	    
	    function showSearchCondition(type) {
	    	$("tr[id^='tr_']").hide();
	    	$(":radio[id^=rad]").removeAttr("checked");
	    	if (type == "r") {
	    		$("#tr_room").show();
	    		$("#radRoom").attr("checked", "checked");
	    	} else if (type == "u") {
	    		$("#tr_unit").show();
	    		$("#radUnit").attr("checked", "checked");
	    	} else if (type == "v") {
	    		$("#tr_visitor").show();
	    		$("#radVisitor").attr("checked", "checked");
	    	}
	    }
	</script>
</head>
<body onload="setInterval(function(){ refreshWarningDevice() }, 60000);"  style="min-width: 1000px;">
	<div class="main">
		<div class="push" title="隐藏" onclick="push()" id="push"></div>
		<div class="show" title="显示" onclick="show()" id="show"></div>
		<div class="main_left" id="main_left">
			<div class="main_left_header" id="main_left_header">
				<div class="main_left_header_div_click" onclick="tab(0, this)" id="position"><span>快速定位</span></div>
				<div class="main_left_header_div" onclick="tab(1, this)" id="sreach"><span>搜索定位</span></div>
				<div class="main_left_header_div" onclick="tab(2, this); showSW();" id="report"><span>报警处理</span></div>
			</div>
			<div class="main_left_search_main" id="main_left_search_main">
				<div class="main_left_search">
					<form id="queryForm" method="post"  autocomplete="off">
				      <table align="center" id="search_table"  border="0" width="100%" class="form_table">
				      	<tr>
				      		<td class="form_label" >定位类型： </td>
							<td >
								<input type="radio" value="r" id="radRoom" onclick="showSearchCondition('r')"/>房间　　
								<input type="radio" value="u" id="radUnit" onclick="showSearchCondition('u')"/>单位　　
								<input type="radio" value="v" id="radVisitor" onclick="showSearchCondition('v')"/>访客
							</td>
				      	</tr>
				      	<tr id="tr_room">
				      		<td class="form_label" >房间号： </td>
							<td ><s:textfield id="RoomNum" name="RoomNum"/></td>
						</tr>
				      	<tr id="tr_unit" style="display: none;">
							<td class="form_label" >单位： </td>
							<td ><select id="org" name="org">
								<c:forEach items="${orgList}" var="org">
									<option value="${org.orgId }">${org.name }</option>
								</c:forEach>
							</select></td>
						</tr>
				      	<tr id="tr_visitor" style="display: none;">
							<td class="form_label" >访客名称： </td>
							<td ><s:textfield id="visitor" name="visitor"/></td>
				      	</tr>
				        <tr>
				          <td colspan="4" class="form_bottom">
				          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search"  onclick="searchPosition();">查询</a>
				          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
				          </td>
				        </tr>
				      </table>
				   	</form>
				</div>
				<div class="main_left_search_btn" style="display: none;"></div>
			</div>
			<div class="main_left_report" id="main_left_report">
		      <table align="center" id="warn_table"  border="0" width="100%" class="form_table">
		      	<tr>
		      		<td align='center'>序号 </td>
					<td align='center'>设备名称</td>
					<td align='center'>设备类型</td>
					<td align='center'>设备状态</td>
		      	</tr>
		      </table>
			</div>
			<div class="main_left_main1" id="main_left_main1">
				<div class="tab_top">
				<a class="main_left_tab1" title="电梯" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_DT)"  hidefocus ></a>
				<span>电梯</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab2" title="摄像头" href="javascript:void(0)" onclick="quickPosition(MON_TYPE_BUILD_SXT)" hidefocus></a>
				<span>摄像头</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab3" title="闸机" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_ZJ)"  hidefocus></a>
				<span>闸机</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab4" title="访客" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_FK)"  hidefocus></a>
				<span>访客</span>
				</div>
				<div class="tab_top">
				<a class="main_left_tab5" title="巡更人员" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_XGRY)"  hidefocus></a>
				<span>巡更人员</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab6" title="门禁" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_MJ)"  hidefocus></a>
				<span>门禁</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab7" title="泊车" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_BC)" hidefocus></a>
				<span>泊车</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab8" title="照明" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_DD)"  hidefocus></a>
				<span>照明</span>
				</div>
				<div class="tab_top">
				<a class="main_left_tab9" title="温度" href="javascript:void(0)"  onclick="quickPosition(RES_TYPE_BUILD_WD)"  hidefocus></a>
				<span>温度</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab10" title="湿度" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_SD)"  hidefocus></a>
				<span>湿度</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab11" title="CO2" href="javascript:void(0)"  onclick="quickPosition(RES_TYPE_BUILD_CO2)" hidefocus></a>
				<span>CO2</span>
				</div>
				<div class="tab_main">
				<a class="main_left_tab12" title="CO" href="javascript:void(0)" onclick="quickPosition(RES_TYPE_BUILD_CO)" hidefocus></a>
				<span>CO</span>
				</div>
			</div>
			<div class="main_left_main2" id="main_left_main2"></div>
			<div class="main_left_main3" id="main_left_main3">
				<span>快速定位说明：</span><br/>
				<span>·点击图标，大厦所有该类设备全部展示在三维图中。</span><br/>
				<span>·点击图中设备，可追踪该设备位置，安装时间，当前状态等信息。有故障设备将在图中高亮显示</span>
			</div>
		</div>
		<div class="main_right" id="main_right">
			<div class="main_right_header" id="main_right_header">
				<div class="FullScreen" title="全屏查看" onclick="push()"><img src="${ctx}/app/buildingmon/pageIndex/images/FullScreen.png"/><span>全屏查看</span></div>
				<div class="initializtion" title="初始化" onclick="show()"><img src="${ctx}/app/buildingmon/pageIndex/images/initializtion.png"/><span>初始化</span></div>
			</div>
			<div class="main_right_main" id="main_right_main">
				<iframe id="threeDimensional" name="threeDimensional" src="http://tuhui.laisi.com/test/index.htm" width="100%" height="100%"></iframe>
			</div>
		</div>
	</div>
</body>
</html>