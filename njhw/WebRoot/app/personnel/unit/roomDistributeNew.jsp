﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/common/include/meta.jsp"%>
		<title>房间分配及授权管理</title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
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
		<!-- Highcharts Start -->
		<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/exporting.js"></script>
		<!-- Highcharts End -->
		<link type="text/css" rel="stylesheet" href="css/room.css" />
		<script type="text/javascript">
		
			var iId = '';
		
			/*人员电话列表*/
			function showDistributeRoom(roomId){
				var url = "${ctx}/app/personnel/unit/showDistributeRoom.act?roomId="+roomId;
					var sucFun = function(data) {
						var tooltipContent = "";
						if (null != data && data.length > 0){
						    var length = data.length;
						    var flag = length % 2;
							for (var i = 0 ; i < data.length ; i++){
								var tel = $.trim(data[i].TEL_NUM);
								if ("" == tel) {
									tel = "未分配IP电话";
								}
// 								tooltipContent +=  $.trim(data[i].DISPLAY_NAME) +"["+tel+"]" + "<br/>";
 								if(i!=data.length-1){
									tooltipContent += "<li><span class='bgsgl_name'>"+$.trim(data[i].DISPLAY_NAME) +"</span><span class='bgsgl_poto'>"+tel+"</span></li>";
 								}else{
 									if(flag == 1){
 										tooltipContent += "<li><span class='bgsgl_name'>"+$.trim(data[i].DISPLAY_NAME) +"</span><span class='bgsgl_poto'>"+tel+"</span></li>";
 										tooltipContent += "<li><span class='bgsgl_name1'>&nbsp;</span><span class='bgsgl_poto1'>&nbsp;</span></li>";
 									}else{
 										tooltipContent += "<li><span class='bgsgl_name'>"+$.trim(data[i].DISPLAY_NAME) +"</span><span class='bgsgl_poto'>"+tel+"</span></li>";
 									}
 								}
							}
						} 
// 						else {
// 							tooltipContent = myTitle;
// 						}
						if(tooltipContent==""){
							tooltipContent = "<li style='width:100%'><span style='font-size:12px;color:#808080;float:left;line-height: 30px;font-weight:bold'>该房间未分配人员！</span></li>";
						}
						$("#showDistributeRoom").html();
						$("#showDistributeRoom").html(tooltipContent);
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
			}
			
			/*开门权限人员列表*/
			function showDistributeLock(roomId){
			
				var url = "${ctx}/app/personnel/unit/showDistributeLock.act?roomId="+roomId;
					var sucFun = function(data) {
						var tooltipContent = "";
						if (null != data && data.length > 0){
							var permitPersons = 0;
							for (var i = 0 ; i < data.length ; i++){
								var uList = data[i].userList;
								if (null != uList){
									permitPersons += uList.length;
								}
								if (uList!=null && uList.length > 0) {
									for (var j = 0 ; j < uList.length ; j++){
										var id = uList[j].ID;
										var add = "";
										if (id == "0"){
											add = "<li>&nbsp;&nbsp;"+$.trim(uList[j].DISPLAY_NAME) + '(正在授权中)' + "</li>";
										} else if (id == "1") {
											add = "<li>&nbsp;&nbsp;"+$.trim(uList[j].DISPLAY_NAME) + '(取消授权中)' + "</li>";
										} else {
											add = "<li>&nbsp;&nbsp;"+$.trim(uList[j].DISPLAY_NAME) + "</li>";
										} 
										tooltipContent +=  add;
									}
								}
									
							}
							if (permitPersons < 1) {//没有一个人授权
								tooltipContent = "";
							}
						}else {
							tooltipContent = "";
						}
						if(tooltipContent==""){
							tooltipContent =tooltipContent+ "<li><span style='font-size:12px;float:left;color:#808080;font-weight:bold'>该房间未分配人员！</span><li>";
						}
						$("#showDistributeLock").html("");
						$("#showDistributeLock").html(tooltipContent)
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
			}
		
		 function reSetRoomName(roomId){
			
				var url = "${ctx}/app/personnel/unit/getRoomById.act?roomId="+roomId;
					var sucFun = function(data) {
					    console.log(data);
						if (null != data && data.length > 0){
						
						} else {
						
						}
					};
					var errFun = function() {
					    alert("加载房间信息出错！");
					};
					ajaxQueryJSON(url, null, sucFun, errFun);
			}
		
			$(function(){
				$("div.bgsgl_left").find("a").click(function(e){
				    var removeClassId = $("#right_roomId").attr("roomId");
					if(removeClassId){
					      var roomObj = $("#room_"+removeClassId);
					      roomObj.removeClass("bgsgl_left_click");
					      roomObj.find("span").each(function(){
					      	 $(this).css("color","#7F90B3");
						  })
					}
					$(this).addClass("bgsgl_left_click");
					$(this).find("span").each(function(){
					      $(this).css("color","#FFFFFF");
					})
					var roomId = $(this).attr("roomId");
					var key = $(this).attr("key");
					var firstSpanText = $(this).find("span:first").text();
					var lastSpanText = $(this).find("span:last").text();
					var lastSpanTitle = $(this).find("span:last").attr("title");
					$("#right_roomId").html(firstSpanText);
					$("#right_roomId").attr("roomId",roomId);
					$("#right_title").html(lastSpanTitle.substr(0,12));
					$("#right_title").attr("title",lastSpanTitle);
// 					$("#right_title").attr("onclick","editRoomInfo(this,"+roomId+")");
					$("#right_title").unbind("click");
					$("#right_title").click(function(e){
						editRoomInfo(this,roomId);
					});
					$("#distributeDoorAuth").unbind("click");
					$("#distributeDoorAuth").click(function(e){
						distributeDoorAuth(this,roomId);
					});
					$("#getOrgUserTreeList").unbind("click");
					$("#getOrgUserTreeList").click(function(e){
						getOrgUserTreeList(this,roomId);
					});
                    $("#openDoor").unbind("click");
					$("#openDoor").click(function(e){
						openDoor(roomId,"'"+key+"'");
					});
					
					showDistributeRoom(roomId);
					showDistributeLock(roomId);
					
					currentRoomEnergy(roomId);
			    });
				
				$("div.bgsgl_left").find("a:first").click();
			});
			
			
			
			/*分页方法*/
			function goPage(pageNo){
				if (pageNo==null || pageNo == undefined){
					pageNo = 1;
				}
				$("#cur_page_no").val(pageNo);
				//parent.loadRoomDistribute(pageNo);
			}
			/**
			* 人员房间分配
			*/
			function getOrgUserTreeList(obj,roomId){
				var url = "${ctx}/app/per/orgUserTree.act?roomId="+roomId+"&dtype=room";
				//openEasyWin("orgInput","房间人员分配",url,"500","500",true);
				windowDialog("房间人员分配",url,'500','400',roomId);
			}
			/*修改房间信息*/
			function editRoomInfo(obj,roomId){
				var url = "${ctx}/app/personnel/unit/editRoomInfo.act?roomId="+roomId+"&pageNo=${page.pageNo}";
				//openEasyWin("orgInput","修改房间信息",url,"500","500",true);
				windowDialog("修改房间信息",url,'400','200',roomId);
			}
			
			function loadRoom(roomId){
				var url = "${ctx}/app/personnel/unit/loadRoom.act?roomId="+roomId;
				var data = {telNum:telNum,disStatus:disStatus,orgId:orgId,permit:permit,pageNo:pageNo};
				sucFun = function (data){
					$("#tel_num_list_div").replaceWith(data);
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
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
				windowDialog("门锁权限分配",url,'500','400',roomId);
			}
			
			
			function windowDialog(title,url,w,h,roomId){
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
						if(iId){
							clearInterval(iId);
						}
						/**
						if (refresh){//设置关闭窗口刷新页面
							goPage(${page.pageNo});
						}**/
					}
				});
				$("#companyIframe").attr("src", url);
			}
			
			function windowDialogNew(title,url,w,h,roomId){
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
						if(roomId){
							showDistributeRoom(roomId);
							showDistributeLock(roomId);
						}
						if(iId){
							clearInterval(iId);
						}
						/**
						if (refresh){//设置关闭窗口刷新页面
							goPage(${page.pageNo});
						}**/
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
														if (times == 10) {
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
					windowDialogNew(title,url,400,300,roomId);
				} else {
					//goPage(${page.pageNo});
				}
			}
			
			/**
			* @type 房间名称更新
			* @roomId 房间id
			*/
			function closeRoomEditWindow(roomId,roomName){
			    var displayName = roomName.substr(0,12);
			    var displayName1 = roomName.substr(0,6);
			    $("#right_title").html(displayName);
			    $("#right_title").attr("title",roomName);
			    var roomObj = $("#room_"+roomId);
			    if(roomObj){
			       roomObj.find(".bgsgl_h2").attr("title",roomName);
			       roomObj.find(".bgsgl_h2").html(displayName1);
			    }
				$("#companyWin").window("close");
				$("#right_title").focus();
			}
			
			/**
			* @type 根据roomid获取当前房间的电耗
			* @roomId 房间id
			*/
			function currentRoomEnergy(roomId){
				$.ajax({
					type : 'POST',
					url : '${ctx}/app/energyint/currentRoomEnergyJson.act',
					data : {"roomId":roomId},
					dataType : 'JSON',
					async : true,
					success : function(highcharts_data) {
						$('#container').highcharts({
							chart : {
								type : 'line',
								width : 358,
								height : 160
							},
							exporting : {
								enabled : false
							},
							credits : {
								enabled : false
							},
							title: {
				                text: '当月房间耗电量（kWh）',
				                x: -20 //center
				            },
							xAxis : {
								categories : highcharts_data.dayArray,
								max : highcharts_data.xAxisMax
							},
							yAxis : {
								labels: {
				                    format: '{value}',
				                    style: {
				                        color: '#4572A7'
				                    }
				                },
				                title: {
				                    text: ' '
				                }
				            },
							tooltip : {
								formatter: function() {  
									return '<b>'+ this.y+' kWh</b>';  
								}  
							},
							plotOptions: {
						        line: {
						            lineWidth: 1.0,
						            fillOpacity: 0.0,
									marker: {
						                enabled: false,
						                radius:2
						            },
						            shadow: false
						        }
						    },
							legend : {
								enabled: false
							},
							series : [{
								name : '房间能耗',
								color: '#058DC7',
								data : highcharts_data.roomEverydayKwhArray
							}]
						});
					},
					error : function(msg, status, e) {
						
					}
				});
				
			}
		</script>
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						办公室管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				    <!--此处写页面的内容 -->
					  <div class="bgsgl_left">
						  <ul>
						  	<c:forEach items="${page.result}" var = "data">
					    		<c:if test="${data.RES_ID != '0'}">
									<li>
										<a id="room_${data.RES_ID}" href="javascript:void(0);" class="bgsgl_left_unclick" roomId="${data.RES_ID}" key="${data.NAME}">
											 <span class="bgsgl_h1" title="${data.NAME}">${fn:substring(data.NAME,0,6)}</span><br />
											 <span class="bgsgl_h2" title="${data.TITLE}">${fn:substring(data.TITLE,0,6)}</span>
										</a>
									</li>
							    </c:if>
					    	</c:forEach>
						  </ul>
					  </div>
					  <div class="bgsgl_right">
					    <div class="bgsg1_right_len">
						  <div class="bgsgl_right_border">
						    <span class="bgsgl_right_title" id="right_roomId" ><!-- D402 --></span>
							<span class="bgsgl_right_move" id="right_title"><!-- 行政办公室 --></span>
						  </div>
						  <div class="bgsgl_right_list">
						    <div class="bgsgl_right_list_border">
							  <div class="bgsgl_right_list_left">房间人员</div>
							  <div class="bgsgl_right_left_move">
							  	<a href="javascript:void(0);" onclick="" id="getOrgUserTreeList">
							  		<font color="#7F90B3">编辑</font>
									<!--<img src="images/border_move.jpg" border="0" />-->
							  	</a>
							  </div>
							</div>
						  </div>
						  <div class="bgsgl_list1">
						  <ul id="showDistributeRoom">
<!--						  	<li><span class="bgsgl_name">张三四</span><span class="bgsgl_poto">866443322</span></li>-->
						  </ul>
						  </div>
						  
						  <div class="bgsgl_right_list">
						    <div class="bgsgl_right_list_border">
							  <div class="bgsgl_right_list_left">开门权限</div>
							  <div class="bgsgl_right_left_move">
							  	<a href="javascript:void(0);" id="distributeDoorAuth">
							  		<font color="#7F90B3">编辑</font>
							  		<!-- <img src="images/border_move.jpg" border="0" /> -->
							  	</a>
							  </div>
							</div>
						  </div>
						  <div class="bgsgl_list1">
						    <ul class="bgsgl_list_kmqx" id="showDistributeLock">
<!--							  <li>张三</li>-->
							</ul>
						  </div>
						  
						  <div class="bgsgl_right_list">
						    <div class="bgsgl_right_list_border">
							  <div class="bgsgl_right_list_left">能源消耗</div>
							</div>
						  </div>
						  <div class="bgsgl_list1">
						  	<div id="container"></div>
						  </div>
						  
						  <div class="bgsgl_clear"></div>
						  <div class="bgsgl_border_km"><a class="bgsgl_onpl" href="javascript:void(0);" id="openDoor" >开门</a></div>
						</div>
					  </div>
					  <div class="bgsgl_clear"></div>
					</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
	</body>

</html>