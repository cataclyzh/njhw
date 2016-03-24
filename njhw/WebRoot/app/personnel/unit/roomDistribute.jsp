﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>房间分配及授权管理</title>
		<link type="text/css" rel="stylesheet" href="css/roomDistribute.css" />
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<style type="text/css">
		/* tooltip */
		#tooltip{
			position:absolute;
			border:1px solid #333;
			background:#f7f5d1;
			padding:1px;
			color:#333;
			display:none;
		}
		#show_info_div{
			position:absolute;
			border:1px solid #333;
			background:#8ABEEA;
			padding:1px;
			color:#333;
			width:200px;
			display:none;
			left:400px;
			z-index:9999;
		}
		
		</style>
		<script type="text/javascript">
			$(function(){
			    var x = 10;  
				var y = 20;
				/*绑定展示ip电话接入*/
				$("div [class^='main_tel']").mouseover(function(e){
					var roomId = $(this).attr("roomId");
					this.myTitle = this.title;
					var myTitle = this.title;
					this.title = "";
					
					var url = "${ctx}/app/personnel/unit/showIPPhoneInner.act?roomId="+roomId;
					var sucFun = function(data) {
					
					    var tooltipContent = "";
					    var roomTelMacList = data.roomTelMacList;
					    var innerTelMacList = data.innerTelMacList;
						/*if (null != roomTelMacList && roomTelMacList.length > 0){
							tooltipContent +=  "<br/>" +"分配电话[mac]列表:";
							for (var i = 0 ; i < roomTelMacList.length ; i++){
								tooltipContent += "<br/>" + $.trim(roomTelMacList[i].TEL_MAC);
							}
						}*/
						if (null != innerTelMacList && innerTelMacList.length > 0){
							//tooltipContent +=  "<br/>" +"接入电话[mac]列表:";
							for (var i = 0 ; i < innerTelMacList.length ; i++){
								tooltipContent +=  $.trim(innerTelMacList[i].TEL_MAC)+"<br/>" ;
							}
						} else {
							tooltipContent = myTitle;
						}
						
					    var tooltip = "<div id='tooltip'>"+tooltipContent+"<\/div>"; //创建 div 元素
					    
						$("body").append(tooltip);	//把它追加到文档中
						$("#tooltip")
							.css({
								"z-index":"9999",
								"top": (e.pageY + y) + "px",
								"left": (e.pageX  + x) + "px"
							}).show("fast");	  //设置x坐标和y坐标，并且显示
					};
					var errFun = function() {
						var tooltip = "<div id='tooltip'>"+myTitle+"<\/div>"; //创建 div 元素
						$("body").append(tooltip);	//把它追加到文档中
						$("#tooltip")
							.css({
								"z-index":"9999",
								"top": (e.pageY + y) + "px",
								"left": (e.pageX  + x) + "px"
							}).show("fast");	  //设置x坐标和y坐标，并且显示
					};
					ajaxQueryJSON(url, null, sucFun, errFun);
			    }).mouseout(function(){		
					this.title = this.myTitle;
					$("#tooltip").remove();   //移除 
			    });
			    
			    /*绑定展示人员房间分配*/
				$("div [class^='people_img']").mouseover(function(e){
					var roomId = $(this).attr("roomId");
					this.myTitle = this.title;
					var myTitle = this.title;
					this.title = "";
					
					var url = "${ctx}/app/personnel/unit/showDistributeRoom.act?roomId="+roomId;
					var sucFun = function(data) {
						var tooltipContent = "";
						if (null != data && data.length > 0){
							for (var i = 0 ; i < data.length ; i++){
								var tel = $.trim(data[i].TEL_NUM);
								if ("" == tel) {
									tel = "未分配IP电话";
								}
								tooltipContent +=  $.trim(data[i].DISPLAY_NAME) +"["+tel+"]" + "<br/>";
							}
						} else {
							tooltipContent = myTitle;
						}
						
					    var tooltip = "<div id='tooltip'>"+tooltipContent+"<\/div>"; //创建 div 元素
						$("body").append(tooltip);	//把它追加到文档中
						$("#tooltip")
							.css({
								"z-index":"9999",
								"top": (e.pageY + y) + "px",
								"left": (e.pageX  + x) + "px"
							}).show("fast");	  //设置x坐标和y坐标，并且显示
					};
					var errFun = function() {
					    var tooltip = "<div id='tooltip'>"+myTitle+"<\/div>"; //创建 div 元素
						$("body").append(tooltip);	//把它追加到文档中
						$("#tooltip")
							.css({
								"z-index":"9999",
								"top": (e.pageY + y) + "px",
								"left": (e.pageX  + x) + "px"
							}).show("fast");	  //设置x坐标和y坐标，并且显示
					};
					ajaxQueryJSON(url, null, sucFun, errFun);
			    }).mouseout(function(){		
					this.title = this.myTitle;
					$("#tooltip").remove();   //移除 
			    });
			    
			    /*绑定展示门锁权限分配*/
				$("div [class^='main_key']").mouseover(function(e){
					var roomId = $(this).attr("roomId");
			       	this.myTitle = this.title;
					var myTitle = this.title;
					this.title = "";
					
				    var url = "${ctx}/app/personnel/unit/showDistributeLock.act?roomId="+roomId;
					var sucFun = function(data) {
						var tooltipContent = "";
						if (null != data && data.length > 0){
							var permitPersons = 0;
							for (var i = 0 ; i < data.length ; i++){
								//if (0 != i){
								//	tooltipContent += "<br/>";
								//}
								//tooltipContent += "门锁:" + $.trim(data[i].NAME);
								var uList = data[i].userList;
								if (null != uList){
									permitPersons += uList.length;
								}
								if (uList!=null && uList.length > 0) {
									for (var j = 0 ; j < uList.length ; j++){
										//tooltipContent += "<br/>&nbsp;&nbsp;" +uList[j].DISPLAY_NAME;
										var id = uList[j].ID;
										var add = "";
										if (id == "0"){
											add = $.trim(uList[j].DISPLAY_NAME) + '(未授权)' + "<br/>";
										} else {
											add = $.trim(uList[j].DISPLAY_NAME) + "<br/>";
										}
										tooltipContent +=  add;
									}
								}
									
							}
							if (permitPersons < 1) {//没有一个人授权
								tooltipContent = myTitle;
							}
						} else {
							tooltipContent = myTitle;
						}
						
					    var tooltip = "<div id='tooltip'>"+tooltipContent+"<\/div>"; //创建 div 元素
						
						$("body").append(tooltip);	//把它追加到文档中
						$("#tooltip")
							.css({
								"z-index":"9999",
								"top": (e.pageY + y) + "px",
								"left": (e.pageX  + x) + "px"
							}).show("fast");	  //设置x坐标和y坐标，并且显示
					};
					var errFun = function() {
					    var tooltip = "<div id='tooltip'>"+myTitle+"<\/div>"; //创建 div 元素
						$("body").append(tooltip);	//把它追加到文档中
						$("#tooltip")
							.css({
								"z-index":"9999",
								"top": (e.pageY + y) + "px",
								"left": (e.pageX  + x) + "px"
							}).show("fast");	  //设置x坐标和y坐标，并且显示
					};
					ajaxQueryJSON(url, null, sucFun, errFun);
			    }).mouseout(function(){		
					this.title = this.myTitle;
					$("#tooltip").remove();   //移除 
			    });
			    
			    //check_door();
			});
			
			/*分页方法*/
			function goPage(pageNo){
				if (pageNo==null || pageNo == undefined){
					pageNo = 1;
				}
				$("#cur_page_no").val(pageNo);
				parent.loadRoomDistribute(pageNo);
			}
			/**
			* 人员房间分配
			*/
			function getOrgUserTreeList(obj,roomId){
				var url = "${ctx}/app/per/orgUserTree.act?roomId="+roomId+"&dtype=room";
				//openEasyWin("orgInput","房间人员分配",url,"500","500",true);
				windowDialog("房间人员分配",url,'500','400');
			}
			/*修改房间信息*/
			function editRoomInfo(obj,roomId){
				var url = "${ctx}/app/personnel/unit/editRoomInfo.act?roomId="+roomId+"&pageNo=${page.pageNo}";
				//openEasyWin("orgInput","修改房间信息",url,"500","500",true);
				windowDialog("修改房间信息",url,'400','200');
			}
			
			/**
			* 获取鼠标相对位置
			*/
			function getY(obj){    
		        var parObj=obj;    
		        var top=obj.offsetTop;    
		        while(parObj = parObj.offsetParent){    
		            top+=parObj.offsetTop;    
		        }    
		     	return top;    
		    }    
			
			/**
			* 门锁权限分配
			*/
			function distributeDoorAuth(obj,roomId){
				//var top = getY(obj) +10; 
				
				var url = "${ctx}/app/per/orgUserTree.act?roomId="+roomId+"&dtype=lock";
				//openEasyWin("orgInput","门锁权限分配",url,"500","500",true);
				windowDialog("门锁权限分配",url,'500','400');
			}
			
			
			function windowDialog(title,url,w,h,refresh){
			 	var body =  window.document.body;
				var left =  body.clientWidth/2 - w/2;
				var top =  body.clientHeight/2 - h/2;
				var scrollTop = document.body.scrollTop;
				//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
				top = top + scrollTop;
				$("#companyWin").show();
				$("#companyWin").window({
					title : title,
					modal : true,
					shadow : false,
					closed : false,
					width : w,
					height : h,
					top : top,
					left : left,
					onBeforeClose:function(){
						if (refresh){//设置关闭窗口刷新页面
							goPage(${page.pageNo});
						}
					}
				});
				$("#companyIframe").attr("src", url);
			}
			
			function close_door(roomId) {
				$("#close_door_"+roomId).show();
				$("#open_door_"+roomId).hide();
			}
			
			function openDoor(roomId,roomName) {
				easyConfirm('提示', '确定开门？', function(r){
					if (r) {
						$.ajax({
							type: "POST",
							url: "${ctx}/app/integrateservice/controllerDevice.act",
							data: {"deviceType": "door", "optType": "38", "roomId": roomId},
							dataType: 'text',
							async : false,
							success: function(json){
								  var result = eval("(" + json + ")");
							      if (result.result == "false") {
							      	easyAlert("提示信息",roomName+":自动关锁失败,请联系系统管理员!","info");
							      } else {
							        var times = 0;
							        
							        checkOpen();
							        
							        function checkOpen() {
							        	setTimeout(function(){
							      			$.ajax({
												type: "POST",
												url: "${ctx}/app/personnel/unit/checkOpenDoorResult.act",
												data: {"msgId": result.msgId},
												dataType: 'text',
												async : false,
												success: function(json2){
												    var result2 = eval("(" + json2 + ")");
													hasOpen = result2.msg;
													if (hasOpen == "YES") {
														easyAlert("提示信息",roomName+":开锁成功!","info");
													} else if (hasOpen == "NO") {
														easyAlert("提示信息",roomName+":开锁失败!","info");
													} else {
														if (times == 3) {
															easyAlert("提示信息",roomName+":开锁超时!","info");
															return;
														}
														times = parseInt(times) + parseInt(1);
														checkOpen();
													}
												}
											});
										}, 300);
							        }
							      }
							 },
							 error: function(msg, status, e){
								 easyAlert("提示信息", "操作出错","info");
							 }
						 });
					}
				});
			}
			
			function check_door() {	// 做定时任务去刷新每个门的开关状态（针对锁的）
				$.ajax({
					type: "POST",
					url: "${ctx}/app/integrateservice/refreshDoorStatus.act",
					data: {"checkType": "many"},
					dataType: 'json',
					async : false,
					success: function(msg){
						if (json.length > 0) {
							$.each(json, function(i) {
								var roomId = i.ROOM_ID;
								if (i.lockStatus=='00') {//关门
									$("close_door_"+roomId).show();	// 隐藏关门层
							  		$("open_door_"+roomId).hide();	// 显示开门层
								} else if (i.lockStatus=='01') {//开门
									$("close_door_"+roomId).hide();	// 隐藏关门层
							  		$("open_door_"+roomId).show();	// 显示开门层
								}
								
							});
						}
					 },
					 error: function(msg, status, e){
						 easyAlert("提示信息", "刷新房间门状态出错","info");
					 }
				 });
			}
			/**
			* @type lock:门锁分配;room:房间分配
			* @roomId 房间id
			*/
			function closeWindow(type,roomId){
				$("#companyWin").window("close");
				var url = "${ctx}/app/personnel/unit/disResultPage.act?type="+type+"&roomId="+roomId;
				var title = "";
				if (type=="lock"){
					title = "门锁授权情况";
				} else {
					title = "人员房间分配情况";
				}
				if (type != null &&　type != "") {
					windowDialog(title,url,400,300,true);
				} else {
					goPage(${page.pageNo});
				}
				
			}
		</script>
	</head>
	<body>		<!--onload="setInterval(function(){ check_door(); }, 600000);" -->
	<div class="header">
        <div class="header_left">
            <span>房间分配及授权管理&nbsp;&nbsp;&nbsp;&nbsp;</span>
        </div>
        <form action="exportRoomInfo.act" name="form1" id="form1">
			 <!-- 表单内容 -->
			<input  class="excel" type="submit" value="" title="房间分配及授权清单导出"/>
		</form>
        <div class="header_right">
            <div class="tel1"></div>
            <div class="header_right_span"><span>有电话接入</span></div>
            <!-- 
            <div class="tel2"></div>
            <div class="header_right_span"><span>电话错误接入</span></div>
             -->
            <div class="tel3"></div>
            <div class="header_right_span"><span>无电话接入</span></div>
        </div>
	</div>
    <div class="main">
    	<input type = "hidden" id = "cur_page_no" value="1">
    	<c:forEach items="${page.result}" var = "data">
    		<c:if test="${data.RES_ID != '0'}">
		    	<div class="main_body">
		            <div class="main_header">
		                <div title="无电话接入" roomId = "${data.RES_ID}"  <c:if test="${data.INNER_STATUS=='1'}">class="main_tel3"</c:if> <c:if test="${data.INNER_STATUS=='2' || data.INNER_STATUS=='3'}">class="main_tel"</c:if> ></div>
		                <div class="main_num">
		                    <span title = "${data.KEYWORD}">${fn:substring(data.KEYWORD,0,6)}</span>
		                </div>
		                <div title="未授权" roomId = "${data.RES_ID}" class="main_key"  onclick="distributeDoorAuth(this,'${data.RES_ID}');"></div>
		            </div>
		            <div class="room_name">
		                <a href="javascript:void(0);" hidefocus title="${data.TITLE}"><div onclick="javascript:editRoomInfo(this,'${data.RES_ID}');">${fn:substring(data.TITLE,0,6)}</div></a>
		            </div>
		            <div class="people">
		                <div <c:if test="${data.USER_NAMES == ''}">class="nopeople_img"</c:if> <c:if test="${data.USER_NAMES != ''}">class="people_img"</c:if> roomId = "${data.RES_ID}" title="未分配"  onclick="javascript:getOrgUserTreeList(this,'${data.RES_ID}');"></div>
		                <div class="people_name">
		                    <span title="${data.USER_NAMES}">
		                    	<c:if test="${fn:length(data.USER_NAMES)>4}">
		                    		${fn:substring(data.USER_NAMES,0,4)}...
		                    	</c:if>
		                    	<c:if test="${fn:length(data.USER_NAMES)<=4 && fn:length(data.USER_NAMES) > 0}">
		                    		${fn:substring(data.USER_NAMES,0,4)}
		                    	</c:if>
		                    </span>
		                </div>
		                <div id="close_door_${data.RES_ID}" title = "开门" class="close_door"  onclick="openDoor('${data.RES_ID}','${data.KEYWORD}')"></div>
		                <div id="open_door_${data.RES_ID}" title = "开门" class="open_door" style="display: none;"></div>
		            </div>
		        </div>
	        </c:if>
	        <c:if test="${data.RES_ID == '0'}">
	        	<div class="main_body"></div>
	        </c:if>
    	</c:forEach>
    	<div class="footer"></div>
    	<div class="empty_div"></div>
    </div>
	<div id='companyWin' class='easyui-window' collapsible='false'
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<iframe id='companyIframe' name='companyIframe'
			style='width: 100%; height: 100%;' frameborder='0'></iframe>
	</div>
	</body>
</html>