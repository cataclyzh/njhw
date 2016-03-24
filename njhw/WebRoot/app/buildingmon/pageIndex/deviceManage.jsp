﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
	<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>三维设备管理</title>
		<script type="text/javascript">
	var _ctx = '${ctx}';
</script>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<link href="${ctx}/app/buildingmon/pageIndex/css/3d_css.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript">
	function g(o) {
		return document.getElementById(o);
	}
	function HoverLi(n) {
		//如果有N个标签,就将i<=N;
		for ( var i = 1; i <= 2; i++) {
			g('tb_' + i).className = 'normaltab';
			g('tbc_0' + i).className = 'undis';
		}
		g('tbc_0' + n).className = 'dis';
		g('tb_' + n).className = 'hovertab';
	}
	//如果要做成点击后再转到请将__tag_34$15_中的onmouseover 改成 onclick;
</script>
		<script type="text/javascript" language="javascript">
	$(function() {
		document.domain = "njnet.gov.cn";
		var Device;
		var DeviceDefaultOption = {
			IsFrameMap : true
		}
		var Room;
		var RoomDefaultOption = {
			OnRoomSelected : function(RoomId, BlRoomId) {
				Room.FlyToRoom(RoomId);
			}
		}
		//初始化3D线框图
		var iframe = $("#threeDimensional")[0];
		if (iframe.attachEvent) {
			iframe.attachEvent("onload", function() {
				Room = window.frames['threeDimensional'].Init3D("Room",
						RoomDefaultOption);
				Device = window.frames['threeDimensional'].Init3D("Device",
						DeviceDefaultOption);
				Device.MenuContent();
			});
		} else {
			iframe.onload = function() {
				Room = window.frames['threeDimensional'].Init3D("Room",
						RoomDefaultOption);
				Device = window.frames['threeDimensional'].Init3D("Device",
						DeviceDefaultOption);
				Device.MenuContent();
			};
		}

		//定位设备
		//$("#device_localization").click(function(){
		$("a")
				.click(
						function() {
							var floorId;
							if ($("#floor_id").val() == '') {
								floorId = 0;
							} else {
								floorId = $("#floor_id").val();
							}
							var typeId = $(this).attr("name");
							if (typeId == '7') {
								if (Device != null)
									Device.ClearAllExistDevice();
								if (Room != null)
									Room.ResetAllRooms();
								RoomDefaultOption = {
									//选中房间事件
									OnRoomSelected : function(RoomId, BlRoomId) {
										if (!Room.EnableMultiSelect) {
											//飞向该房间
											Room.FlyToRoom(RoomId);
											//显示弹窗
											Room.ShowRoomDialog(RoomId,
													"Room.htm?id=" + RoomId);

										} else {
											alert("您选中了" + RoomId);
										}
									},
									//保存事件（该保存事件为3D工具栏中的保存按钮点击后触发）
									OnSaved : function(SelectedArray) {
										alert(SelectedArray.length);
									},
									//取消选中房间事件
									OnRoomUnSelected : function(RoomId,
											BlRoomId) {
										Room.HideRoom($.Xcds.FrameMap, RoomId);
										alert("房间号：" + RoomId + "已经关灯！");

									},
									//切换楼层事件
									OnFloorChanged : function(floorId) {
										//            Room.SwitchFloor(floorId, function () {
										//                Room.InitGetRoomBy3DRoomId("65", true, false);
										//            });
										Room.ShowRoomsByFloorId(floorId);
									},
									//查看灯光异常房间属性事件
									OnAbnormalLightRoomPropertyShow : function(
											RoomId, BlRoomId) {
										Room.ShowRoomDialog(RoomId, "http://"
												+ window.location.host
												+ "/test/RoomInfo.htm");
									},
									//是否起用多选
									EnableMultiSelect : false,
									//保存按键文本
									SaveButtonText : "保存结果",
									//是否为线框图
									IsFrameMap : true,
									//选中房间的颜色
									SelectedColor : "255,255,128,0.5",
									//未选中房间的颜色
									UnSelectedColor : "0,0,0,0",
									//透明度(0-1之间)
									RoomTranparent : 0.99
								}
								Room = frames['threeDimensional'].Init3D(
										"Room", RoomDefaultOption);
								Room
										.InitGetAbnormalLightRoom("54,67,32,25,78,55,43,41,64,65,121,32");
								Room.DisableSelectAll();
								Room.DisableSave();
								Room.DisableSwicher();

							}
							/*if (typeId == '8') {
								if (Room != null)
									Room.ResetAllRooms();
								Device.InitDevice();
								var liftArray = new Array();
								for ( var i = 1; i < 17; i++) {
									var liftNum;
									var liftInfo = new Array();
									var lift = new Object();
									liftNum = "lift_" + i.toString();
									lift.direction = 0;
									lift.type = 1;
									lift.status = Math.round(Math.random());
									lift.liftNum = liftNum + "_"
											+ Math.random();
									lift.floorNum = i;
									liftArray.push(lift);
								}
								Device.InitLiftList(liftArray);

							} else {
								if (Device != null) {
									Device.ClearAllExistDevice();
								}
								if (Room != null) {
									Room.ResetAllRooms();
								}	
								Device.InitDeviceList(floorId, 0, typeId, true);
							}*/
						});
	})
</script>
	</head>
	<body class="oper_body">
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header1.jsp">
				<jsp:param value="/app/omc/newIndex.jsp"
					name="gotoParentPath" />
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="oper_border_right">
					<!--此处写页面的标题 -->
					<div class="oper_border_left">
						三维设备管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 550px;">
					<!--此处写页面的内容 -->
					<div class="swfk_dj_left">
						<div class="w936">
							<div id="tb_" class="tb_" style="border-bottom:solid 1px #77abba;">
								<ul>
									<li id="tb_1" class="hovertab" onclick="x:HoverLi(1);">
										设备查看
									</li>
									<li id="tb_2" class="normaltab" onclick="i:HoverLi(2);">
										设备状态
									</li>
								</ul>
							</div>
							<div class="ctt">
								<div class="dis" id="tbc_01">
								<!-- 页签1 -->
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr class="swfk_tar">
											<td>
												楼层：
											</td>
											<td>
												<select class="select_swfk" name="" id="floor_id">
													<option value="" selected="selected">
														请选择
													</option>
													<option value="65">
														-&nbsp;2楼
													</option>
													<option value="64">
														-&nbsp;1楼
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
											</td>
										</tr>
									</table>
																<div class="bgsgl_right_list_border" style="border-bottom:solid 4px #77abba;">
								<div class="bgsgl_right_list_left">
									设备类型
								</div>
						</div>
													<ul class="swfk_ul">
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav1" name="4"
										id="camera"></a>
								</li>
								<li class="swfk_li_right">
									<a href="javascript:void(0);" class="swfk_nav2" name="1"
										id="zhaji"></a>
								</li>
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav3" name="2"
										id="menjin"></a>
								</li>
								<li class="swfk_li_right">
									<a href="javascript:void(0);" class="swfk_nav4" name="3"
										id="mensuo"></a>
								</li>
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav5" name="5"
										id="kongtiao"></a>
								</li>
								<li class="swfk_li_right">
									<a href="javascript:void(0);" class="swfk_nav6" name="7"
										id="light"></a>
								</li>
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav7" name="8"
										id="lift"></a>
								</li>
								<li class="swfk_li_right" style="height:120px; display:block;"></li>
								<div class="bgsgl_clear"></div>
							</ul>
						<div class="fla_top" style="color:#808080">
							<strong>三维操作提示：</strong>
							<br />
							<span>1、三维地图对显卡要求较高，建议使用1G以上独 立显卡<br />
								2、首次使用三维地图请务必按照页面提示安装所 需插件<br /> 3、三维地图首次加载可能较慢，请务必在地图加
								载完毕后再进行定位操作，否则可能导致浏览 器崩溃</span>
						</div>
						<!-- 页签1 -->
									</div>
									<div class="undis" id="tbc_02">
									<!-- 页签2 -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr class="swfk_tar">
											<td>
												楼层：
											</td>
											<td>
												<select class="select_swfk" name="" id="floor_id">
													<option value="" selected="selected">
														请选择
													</option>
													<option value="65">
														-&nbsp;2楼
													</option>
													<option value="64">
														-&nbsp;1楼
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
											</td>
										</tr>
									</table>
																<div class="bgsgl_right_list_border" style="border-bottom:solid 4px #77abba;">
								<div class="bgsgl_right_list_left">
									设备类型
								</div>
						</div>
													<ul class="swfk_ul">
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav1" name="4"
										id="camera"></a>
								</li>
								<li class="swfk_li_right">
									<a href="javascript:void(0);" class="swfk_nav2" name="1"
										id="zhaji"></a>
								</li>
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav3" name="2"
										id="menjin"></a>
								</li>
								<li class="swfk_li_right">
									<a href="javascript:void(0);" class="swfk_nav4" name="3"
										id="mensuo"></a>
								</li>
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav5" name="5"
										id="kongtiao"></a>
								</li>
								<li class="swfk_li_right">
									<a href="javascript:void(0);" class="swfk_nav6" name="7"
										id="light"></a>
								</li>
								<li class="swfk_li_left">
									<a href="javascript:void(0);" class="swfk_nav7" name="8"
										id="lift"></a>
								</li>
								<li class="swfk_li_right" style="height:120px; display:block;"></li>
								<div class="bgsgl_clear"></div>
							</ul>
						<div class="fla_top" style="color:#808080">
							<strong>三维操作提示：</strong>
							<br />
							<span>1、三维地图对显卡要求较高，建议使用1G以上独 立显卡<br />
								2、首次使用三维地图请务必按照页面提示安装所 需插件<br /> 3、三维地图首次加载可能较慢，请务必在地图加
								载完毕后再进行定位操作，否则可能导致浏览 器崩溃</span>
						</div>
						<!-- 页签2 -->
									</div>
								</div>
							</div>
					</div>

					<div class="swfk_dj_right"
						style="float: right; width: 1000px; height: 550px;">
						<iframe id="threeDimensional" name="threeDimensional"
							src="http://3d.njnet.gov.cn/1/index.htm" width="100%" height="100%"></iframe>
					</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer1.jsp" />
	</body>

</html>