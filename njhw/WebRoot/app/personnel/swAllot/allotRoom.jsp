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
	        OnRoomSelected: function (RoomId, BlRoomId) { },
	        //取消选中房间事件
	        OnRoomUnSelected: function (RoomId, BlRoomId) {  },
	        OnSaved: function (SelectedArray) {
	            //alert("选定的房间数组:"+SelectedArray);
	            if (SelectedArray.length == 0) {
	            	var r = confirm("您未选中任何房间，继续保存将清空已分配给“"+$("#orgId option:selected").text()+"”的所有房间，是否继续?");
					if (r)	save("", "")		// 保存
	 			} else {
	 				saveValid(SelectedArray);	// 提交前的验证
	 			}
	        },
	        //切换楼层事件
	        OnFloorChanged: function (floorId) {
	            Room.SwitchFloor(floorId, function () {
	            	if (Room.IsFrameMap)
	                	Room.InitGetRoomsByFloorIdInOrg(floorId, true, true, parseInt($("#orgId").val()));
	                else 
	                	Room.InitGetRoomsByFloorIdInOrg(floorId, false, true, parseInt($("#orgId").val()));
	            });
	        },
	        EnableMultiSelect: true,			// 是否起用多选
	        SaveButtonText: "保存结果",			// 保存按键文本
	        IsFrameMap: true,					// 是否为线框图
	        SelectedColor: "255,0,0,0.5",		// 选中房间的颜色
	        UnSelectedColor: "255,180,0,0.5",	// 未选中房间的颜色
	       	RoomTranparent: 0.7					// 透明度(0-1之间)
	    }
	    
	    $(function() {
		    Room = frames['threeDimensional'].Init3D("Room", RoomDefaultOption);
		    // 初始化房间分配函数，楼层id为0加载出该部门所有楼层的房间分配情况
			Room.InitGetRoomsByFloorIdInOrg(0, true, true, parseInt($("#orgId").val()));
	    });
	    
		function loadSW() {
			if (Room != null)	 Room.ResetAllRooms();
			Room = frames['threeDimensional'].Init3D("Room", RoomDefaultOption);
			Room.InitGetRoomsByFloorIdInOrg(0, true, true, parseInt($("#orgId").val()));
		}
		
		function saveValid(ids) {
			$.ajax({	// 提交前验证
				type: "POST",
				url: "${ctx}/app/swallot/validAllotInfo.act",
				data: {"orgId": $("#orgId").val(), "ids": ids.toString()},
				dataType: 'json',
				async : false,
				success: function(json){
					  if(json.status == 0){
							save(json.roomids, ids.toString());	// 保存
					  } else if(json.status == 1){
					  	var r = confirm("您计划分配的房间中："+json.names+"已分配给其他委办局，点击确认，将会跳过已分配的房间！");
					 	if (r)	save(json.roomids, ids.toString());	// 保存
						//easyConfirm("提示信息", 
						//	"您计划分配的房间中："+json.names+"已分配给其他委办局！<br/>"+
						//	"点击确认，将会跳过已分配的房间！<br/>"+
						//	"点击取消，退出分配！",
						//	function(r){
						//	if (r) {
						//		save(json.roomids, ids.toString());	// 保存
						//	}
						//});
					  }
				 },
				 error: function(msg, status, e){
				 	alert("验证出错！");
					//easyAlert("提示信息", "验证分配出错！","info");
				 }
			 });
		}
		
		// 使用ajax提交后台保存
		function save(skipRoomIds, allotIds) {
			$.ajax({
				type: "POST",
				url: "${ctx}/app/swallot/allotSave.act",
				data: {
						"orgId": $("#orgId").val(), 
						"orgName": $("#orgId option:selected").text(),
						"allotIds": allotIds,
						"skipRoomIds": skipRoomIds
					   },
				dataType: 'text',
				async : false,
				success: function(msg){
					  if(msg == "true"){
					  	alert("分配完成！");
					  	Room.InitGetRoomsByFloorIdInOrg(0, true, true, $("#orgId").val());
						//easyAlert("提示信息", "分配完成！", "info", function(){
						//	alert("分配完成，刷新三维"); 
						//	Room.InitGetRoomsByFloorIdInOrg(0, true, true, $("#orgId").val());
						//});
					  } else if(msg == "false"){
						alert("分配失败！");
						//easyAlert("提示信息", "分配失败！","info");
					  }
				 },
				 error: function(msg, status, e){
				 	alert("分配出错！");
					//easyAlert("提示信息", "执行分配出错！","info");
				 }
			 });
		}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" method="post"  autocomplete="off">
		<input type="hidden" name="skipRoomIds" id="skipRoomIds"/>
	 	<h:panel id="query_panel" width="100%" title="控制台">	
	      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
	        <tr>
	          <td class="form_label" width="20%" align="left">委办局：</td>
	          <td width="30%">
	          	<select id="orgId" name="orgId">
	          		<c:forEach items="${orgList}" var="org">
	          			<c:if test="${org.orgId == orgId }"><option value="${org.orgId }" selected="selected">${org.name }</option></c:if>
	          			<c:if test="${org.orgId != orgId }"><option value="${org.orgId }">${org.name }</option></c:if>
	          		</c:forEach>
	          	</select>
	          </td>
	          <td align="center">
	          	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="loadSW();">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;
	          </td>
	        </tr>
	      </table>
	   </h:panel>
	  </form>
	  
	  <h:panel id="sanwei_panel" width="100%" title="三维视图">
	  	<iframe id="threeDimensional" name="threeDimensional" src="http://tuhui.laisi.com/test/index.htm" width="100%" height="400"></iframe>
	  </h:panel>
</body>
</html>