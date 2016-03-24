﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
	<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>三维能耗监控管理</title>
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
		<script>
	document.domain = "njnet.gov.cn";
	var Room;
	var Device;
	var RoomDefaultOption = {
		//选中房间事件
		OnRoomSelected : function(RoomId, BlRoomId) {
			if (!Room.EnableMultiSelect) {
				//飞向该房间
				Room.FlyToRoom(RoomId);
				//显示弹窗
				//Room.ShowRoomDialog(RoomId, "Room.htm?id=" + RoomId);

			} else {
				alert("您选中了" + RoomId);
			}
		},
		//保存事件（该保存事件为3D工具栏中的保存按钮点击后触发）
		OnSaved : function(SelectedArray) {
			alert(SelectedArray.length);
		},
		//取消选中房间事件
		OnRoomUnSelected : function(RoomId, BlRoomId) {
			alert("您取消选择了" + RoomId);
		},
		//切换楼层事件
		OnFloorChanged : function(floorId) {
			Room.InitGetRoomMonitorValueByFloorId(floorId, 3);
		},
		//入口函数结束事件
		OnEndInitial : function() {
			//客户端此时可以访问本页面的Room对象
			Room.DisableSelectAll();
			Room.DisableSave();
			Room.DisableSwicher();
		},
		//是否起用多选
		EnableMultiSelect : true,
		//保存按键文本
		SaveButtonText : "保存结果",
		//是否为线框图
		IsFrameMap : false,
		//选中房间的颜色
		SelectedColor : "255,0,0,0.5",
		//未选中房间的颜色
		UnSelectedColor : "255,180,0,0.5",
		//透明度(0-1之间)
		RoomTranparent : 0.7
	}
</script>
<script type="text/javascript">
function initRoom()
{
  setTimeout(function(){
		try{
		   Room = frames['threeDimensional'].Init3D("Room",
						RoomDefaultOption);
           Room.HideToolBar();						
		   }
		   catch(e){
		   initRoom();
		   }
		},1000); 
}
</script>
		<script>
	$(function() {
		$("#check_energy_consumption").click(function() {
          var floor_id = $("#floor_id").val();
          var type_id = $("#energy_consumption").val();
          //alert(floor_id);
          //alert(type_id);
          if(floor_id=='' && type_id!='')
          {
           alert("您忘了选择要查看的楼层！");
           $("#floor_id").focus();
          }
          if(floor_id!='' && type_id=='')
          {
           alert("您忘了选择要查看的能耗类型！");
           $("#energy_consumption").focus();
          }
          if(floor_id=='' && type_id=='')
          {
           alert("请选择要查看的楼层及能耗类型！");
           $("#floor_id").focus();
          }
          if(floor_id!='' && type_id!='')
          {
          Room.InitGetRoomMonitorValueByFloorId(floor_id, type_id);
          }
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
	<body class="oper_body" onload="initRoom();">
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header1.jsp">
				<jsp:param value="/app/omc/newIndex.jsp"
					name="gotoParentPath" />
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="oper_border_right">
					<!--此处写页面的标题 -->
					<div class="oper_border_left">
						三维能耗管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 550px">
					<!--此处写页面的内容 -->
					<div class="fla_left">
						请选择楼层
						<div class="fla_top">
							<select id="floor_id" class="fla_input">
								<option value="" selected="selected">
									请选择
								</option>
								<option value="56">
									01楼
								</option>
								<option value="55">
									02楼
								</option>
								<option value="27">
									03楼
								</option>
								<option value="31">
									04楼
								</option>
								<option value="32">
									05楼
								</option>
								<option value="35">
									06楼
								</option>
								<option value="37">
									07楼
								</option>
								<option value="38">
									08楼
								</option>
								<option value="39">
									09楼
								</option>
								<option value="40">
									10楼
								</option>
								<option value="41">
									11楼
								</option>
								<option value="42">
									12楼
								</option>
								<option value="43">
									13楼
								</option>
								<option value="44">
									14楼
								</option>
								<option value="45">
									15楼
								</option>
								<option value="46">
									16楼
								</option>
								<option value="47">
									17楼
								</option>
								<option value="48">
									18楼
								</option>
								<option value="49">
									19楼
								</option>
							</select>
							<!-- <input name="" class="fla_input" id="room_num" type="text" />
							 <span>注意:由字母和数字组成，例如：D410</span>
							<a class="fla_botton" id="search_room" href="javascript:void(0);">定位房间</a>-->
						</div>
						<div class="fla_top">
							请选择能耗类型
							<select id="energy_consumption" class="fla_input">
							    <option value="">
							            请选择
							    </option>
								<!-- <option value="1">
									CO
								</option>
								<option value="2">
									CO2
								</option>
								<option value="3">
									温度
								</option>
								<option value="4">
									湿度
								</option> -->
								<option value="5">
									电量
								</option>
							</select>
							<a class="fla_botton" id="check_energy_consumption" style="background: #77abba;color: #fff;"
								href="javascript:void(0);">查看能耗</a>
						</div>
						<div class="fla_top">
							<strong>三维操作提示：</strong>
							<br />
							<span>1、三维地图对显卡要求较高，建议使用1G以上独 立显卡<br />
								2、首次使用三维地图请务必按照页面提示安装所 需插件<br /> 3、三维地图首次加载可能较慢，请务必在地图加
								载完毕后再进行定位操作，否则可能导致浏览 器崩溃</span>
						</div>
					</div>
					<div class="swfk_dj_right"
						style="float: right; width: 1000px; height: 550px;">
						<iframe id="threeDimensional" name="threeDimensional"
							src="http://3d.njnet.gov.cn/1/index.htm" width="100%"
							height="100%"></iframe>
					</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer1.jsp" />
		</div>
	</body>

</html>