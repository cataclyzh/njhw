<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-24
- Description: 房间分配
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>三维房间分配</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript">
	    document.domain = "laisi.com";
	    
	    var Room;
	    var RoomDefaultOption = {
	        //选中房间事件
	        OnRoomSelected: function (RoomId, BlRoomId) {
	        	var url = "http://www.laisi.com:8080${ctx}/app/swallot/swPersonAllotRoomInput.act?roomId=1&orgId=${orgId}";
	        	Room.ShowRoomDialog(RoomId, url);
	        },
	        //取消选中房间事件
	        OnRoomUnSelected: function (RoomId, BlRoomId) { },
	        //切换楼层事件
	        OnFloorChanged: function (floorId) {
	            Room.SwitchFloor(floorId, function () {
	            	if (Room.IsFrameMap)
	                	Room.InitGetRoomsByFloorIdInOrg(floorId, true, false, parseInt("${orgId}"));
	                else 
	                	Room.InitGetRoomsByFloorIdInOrg(floorId, false, false, parseInt("${orgId}"));
	            });
	        },
	        EnableMultiSelect: false,			// 是否起用多选
	        SaveButtonText: "保存结果",			// 保存按键文本
	        IsFrameMap: false,					// 是否为线框图
	        SelectedColor: "255,0,0,0.5",		// 选中房间的颜色
	        UnSelectedColor: "255,180,0,0.5",	// 未选中房间的颜色
	       	RoomTranparent: 0.7					// 透明度(0-1之间)
	    }
	    
	    $(function() {
	    	Room = frames['threeDimensional'].Init3D("Room", RoomDefaultOption);
		    // 初始化房间分配函数，楼层id为0加载出该部门所有楼层的房间分配情况
		    Room.InitGetRoomsByFloorIdInOrg(0, true, false, parseInt("${orgId}"));
		    Room.DisableSelectAll();
		    Room.DisableSave();
	    });
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	 <h:panel id="sanwei_panel" width="100%" title="三维视图">
	  	<iframe id="threeDimensional" name="threeDimensional" src="http://tuhui.laisi.com/test/index.htm" width="100%" height="400"/>
	 </h:panel>
</body>
</html>