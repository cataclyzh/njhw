﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>三维房间单位定位</title>
		<script type="text/javascript">
	var _ctx = '${ctx}';
</script>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
			<link href="${ctx}/app/buildingmon/pageIndex/css/3d_css.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript" >
	document.domain = "njnet.gov.cn";
	var Device;
	var DeviceDefaultOption = {
		IsFrameMap : true
	}
	var Room;
	var RoomDefaultOption = {
		OnRoomSelected : function(RoomId, BlRoomId) {
			Room.FlyToRoom(RoomId);
		},
		//切换楼层事件
		OnFloorChanged : function(floorId) {
			//Room.SwitchFloor(floorId, function () {
		//	if (Room.IsFrameMap)
		//    	Room.InitGetRoomsByFloorIdInOrg(floorId, true, false, parseInt($("#org").val()));
		//    else 
		//    	Room.InitGetRoomsByFloorIdInOrg(floorId, false, false, parseInt($("#org").val()));
		//});
	},
	IsFrameMap : true, // 是否为线框图
		SelectedColor : "255,0,0,0.5", // 选中房间的颜色
		UnSelectedColor : "255,180,0,0.5", // 未选中房间的颜色
		RoomTranparent : 0.7
	// 透明度(0-1之间)
	}
	$(function() {
		//初始化3D线框图
		var iframe = $("#threeDimensional")[0];
		if (iframe.attachEvent) {
			iframe.attachEvent("onload", function() {
				Room = frames['threeDimensional'].Init3D("Room",
						RoomDefaultOption);
				Room.HideToolBar();		
				Device = frames['threeDimensional'].Init3D("Device",
						DeviceDefaultOption);
				Device.MenuContent();
			});
		} else {
			iframe.onload = function() {
				Room = frames['threeDimensional'].Init3D("Room",
						RoomDefaultOption);
				Room.HideToolBar();	
				Device = frames['threeDimensional'].Init3D("Device",
						DeviceDefaultOption);
				Device.MenuContent();		
			};
		}
		//定位房间
		$("#search_room").click(function() {
		    var room_num = $("#room_num").val();
		    //var room_rule = /^[A-Za-z0-9]+$/;
		    var room_rule = /[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/;
		     if(!room_rule.test(room_num) && room_num!='')
		   		  {
		   		    alert("房间号格式不正确，请重新输入！");
		   		    $("#room_num").val("");
		   		    $("#room_num").focus();
		   		    return ;
		   		  }
         // 通过ajax获得请求数据处理之后传给三维展示
		   		if (room_num.replace(/(^\s*)|(\s*$)/g, "") != ""){
		   		  room_num = room_num.toUpperCase();
		   		$.ajax({
						type: "POST",
						url: "${ctx}/app/3d/roomPosition.act",
						data: {"roomNum": room_num},
						dataType: 'json',
						async : false,
						success: function(json){
						   // alert(json.roomId);
							if (json.roomId == null) {
								alert("房间："+room_num+"不存在，无法定位！");
							} else {
							if (Room != null) Room.ResetAllRooms();
							Room = frames['threeDimensional'].Init3D("Room", RoomDefaultOption);
							Room.InitGetRoomByRoomId(parseInt(json.roomId), true, false);
							}
						},
						error:function(msg, status, e)
						{
						    alert(status);
						    alert(e);
						}
					});
		   		}else {
		   		alert("请输入房间号后定位");
		   		}
		});
		//定位单位	
		$("#search_unit").click(function() {
		  if (Room != null) Room.ResetAllRooms();
		  Room = frames['threeDimensional'].Init3D("Room", RoomDefaultOption);
          Room.InitGetRoomsByFloorIdInOrg(0, true, false, parseInt($("#org").val()));
		});

	})
</script>

		<style>
.fla_left {
	width: 254px;
	float: left;
	font-size: 14px;
	color: #b7b7b7;
}

.fla_botton {
	font-size: 12px;
	font-weight: bold;
	width: 72px;
	height: 17px;
	display: block;
	float: right;
	text-decoration: none;
	color: #fff;
	background: #ffc600;
	line-height: 17px;
	text-align: center;
	margin-top: 6px;
}

.fla_input {
	width: 250px;
}

.fla_top {
	margin-bottom: 60px;
	width: 254px;
}

.fla_left span {
	font-size: 12px;
	line-height: 18px;
}
</style>
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp" >
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
					<!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						三维房间单位定位
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 550px">
					<!--此处写页面的内容 -->
					<div class="fla_left">
						请输入房间号
						<div class="fla_top">
							<input name="" class="fla_input" id="room_num" type="text" />
							<span>注意:由字母和数字组成，例如：D410</span>
							<a class="fla_botton" id="search_room" href="javascript:void(0);">定位房间</a>
						</div>
						<div class="fla_top">
							请选择单位
							<select id="org" name="org" class="fla_input">
								<c:forEach items="${orgList}" var="org">
									<option value="${org.orgId }">
										${org.name }
									</option>
								</c:forEach>
							</select>
							<a class="fla_botton" id="search_unit" href="javascript:void(0);">定位单位</a>
						</div>
						<div class="fla_top">
							<strong>三维操作提示：</strong>
							<br />
							<span>1、三维地图对显卡要求较高，建议使用1G以上独 立显卡<br />
								2、首次使用三维地图请务必按照页面提示安装所 需插件<br /> 3、三维地图首次加载可能较慢，请务必在地图加
								载完毕后再进行定位操作，否则可能导致浏览 器崩溃</span>
						</div>
					</div>
					<div class="swfk_dj_right" style="float: right; width: 1000px; height: 550px;">
						<iframe id="threeDimensional" name="threeDimensional"
							src="http://3d.njnet.gov.cn/1/index.htm" width="100%" height="100%"></iframe>
					</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			
		</div>
		<jsp:include page="/app/integrateservice/footer.jsp" />
	</body>

</html>