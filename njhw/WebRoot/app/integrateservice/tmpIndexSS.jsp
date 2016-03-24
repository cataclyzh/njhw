<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-4-18
- Description: 综合办公首页
--%>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<title>综合办公首页</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
	<script src="${ctx}/app/integrateservice/js/util.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/js/newtree.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/js/IntegratedOffice.js" type="text/javascript"></script>
	<link href="${ctx}/app/integrateservice/css/newtree.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/TmpIntegrated-office.css" type="text/css" rel="stylesheet"  />
	<style type="text/css">
		#limi{
		  display:block;
		  width: 500px;
		  overflow: hidden;
		  white-space: nowrap;
		  text-overflow: ellipsis;
		}
	</style>
</head>
  
<body onload="setInterval(function(){ loadMsg(); getSSWD(); }, 600000);" style="overflow: auto;">
	<div class="main">
		<div class="main_left">
			<div class="main_left_main1">
				<div class="now_time"><span id="now_date"></span><span>&nbsp;</span><span id="now_week"></span></div>
                    <div class="weather_change" onclick="showShelter('670','520','${ctx}/app/integrateservice/weather.html');" title="更多"></div>
                    <img id="weather_img" class="weather_img" src="images/loading.gif"/>
                    <div class="main1_span1"><span style="font-size:24px;" id="today_temperature"></span><span>℃</span></div>
                    <div class="main1_span2"><span>今</span><span>&nbsp;</span><span id="today_weather"></span></div>
                    <div class="main1_span3"><span id="today_min"></span><span>-</span><span id="today_max"></span><span>℃</span></div>

                    <img id="weather_img2" class="weather_img2" src="images/loading.gif"/>
                    <div class="main2_span2"><span>明</span><span>&nbsp;</span><span id="tomorrow_weather"></span></div>
                    <div class="main2_span3"><span id="tomorrow_min"></span><span>-</span><span id="tomorrow_max"></span><span>℃</span></div>
			</div>
			<div class="main_left_main3">
				<div class="main_left_main3_more" title="更多"></div>
				<div class="refresh_main_left_main3_more" title="刷新" onclick="loadTraffic();"></div>
				<span id="refreshTime"></span>
				<table class="main_left_main3_table" border="0" id="trafficList"></table>	<!-- 交通信息 -->
			</div>
			<div class="main_left_main5" style="height: 310px;" title="更多">
				<div class="main_left_main5_more" onclick="javascript:parent.open_main3('64' ,'物业通知' ,'${ctx}/common/bulletinMessage/msgBoardAction_query.act');"></div>
				<div class="refresh_main_left_main5_more" title="刷新" onclick="loadBoard()"></div>
				<div id="boardList"> </div>
			</div>
			<div class="main_main">
				<div class="main_main1" id="divMsgBox">
					<div class="main_main1_more" onclick="javascript:parent.open_main3('66' ,'我的消息' ,'${ctx}/common/bulletinMessage/msgBoxAction_queryReceiverbox.act');" title="更多"></div>
					<div class="refresh_main_main1_more" id="refresh_main_main1_more" title="刷新" onclick="loadMsg();"></div>
					<div class="main_main1_sendNewMsg" id="main_main1_sendNewMsg" title="发送新消息" onclick="openEasyWin('winId','发送新消息','${ctx}/common/bulletinMessage/msgBoxAction_init.act','600','500',true)"></div><!--showShelter('700','600','${ctx}/common/bulletinMessage/msgBoxAction_init.act');  -->
					<div class="main_main1_left" id="main_main1_left" onclick="togglePage('left')" style="display: none;"></div>
					<div class="main_main1_right" id="main_main1_right" onclick="togglePage('right')" style="display: none;"></div>
					<div class="main_main1_bottom" id="main_main1_bottom"></div>
					<input type="hidden" id="maxPageNum" value=""/>
					<input type="hidden" id="currentPageNum" value="1"/>
				</div>
				<div class="main_main2">
					<div class="main_main2_tip">
						<div onclick="breakfast(this)" id="breakfast1"><span>早餐</span></div>
						<div class="main_main2_tip_select" id="lunch1" onclick="lunch(this)"><span style="font-weight:bold;">午餐</span></div>
						<div onclick="dinner(this)" id="dinner1"><span>晚餐</span></div>
					</div>
					<div class="main_main2_more2" onclick="weekMenus()"  title="更多"></div>
					<div class="main_main2_more3" title="食堂视频" onclick="refectory()"></div>
					<div class="refresh_main_main2_more2" onclick="refreshMenu()"  title="刷新"></div>
			   	<input type ="hidden" id= "jcont"/>
						<div class="main_left_main2_main" id="main_left_main2_breakfast" style="display: none; ">
							<!--早上 -->
							<c:if test="${null!=listBigMeatAM}">
								<c:forEach items="${listBigMeatAM}" var="listBigMeatAM"
								 	 varStatus="num">
									<div class="main_left_main2_div"
										title="${listBigMeatAM.FD_DESC}">							

                                    <span id="limi">
									<a style="text-decoration: none; font-weight: bold;"
											href="javaScript:selectMenus('${listBigMeatAM.FD_ID}')">${listBigMeatAM.FD_NAME}&nbsp;&nbsp;&nbsp;&nbsp;</a>
											${listBigMeatAM.FD_DESC}<a
											style="text-decoration: none; float: right;"
											href="javaScript:selectMenus('${listBigMeatAM.FD_ID}')">>></a>
										</span>

										<div class="dotted2"></div>
									</div>

								</c:forEach>
							</c:if>
				 	   <c:if test="${null==listBigMeatAM}">
                                    <span style="color: green; margin-top: 30px;">      早餐未发布                  </span>                 
                         </c:if>
				 		
						</div>
						<!-- 中午 -->
						<div class="main_left_main2_main" style="display: none;"
							id="main_left_main2_lunch">
							<!--中午 -->
							<c:if test="${null!=listBigMeatNOON}">
								<c:forEach items="${listBigMeatNOON}" var="listBigMeatNOON"
								begin="0"  end="5"	varStatus="num">
									<div class="main_left_main2_div"
										title="${listBigMeatNOON.FD_DESC}">
										<span id="limi">
										<a style="text-decoration: none; font-weight: bold;"
											href="javaScript:selectMenus('${listBigMeatNOON.FD_ID}')"> ${listBigMeatNOON.FD_NAME}</a>
											&nbsp;&nbsp;&nbsp;&nbsp; 
											${listBigMeatNOON.FD_DESC} <a
											style="text-decoration: none; float: right;"
											href="javaScript:selectMenus('${listBigMeatNOON.FD_ID}')">>></a>
										</span>
										<div class="dotted2"></div>
									</div>

								</c:forEach>
							</c:if>
                         <c:if test="${null==listBigMeatNOON}">
                                                              <span style="color: green;margin-top: 30px;">    午餐未发布   </span>  
                         </c:if>
						</div>
						<div class="main_left_main2_main" style="display: none;"
							id="main_left_main2_dinner" >
							<c:if test="${null!=listBigMeatPM}">
								<c:forEach items="${listBigMeatPM}" var="listBigMeatPM"
									varStatus="num">
									<div class="main_left_main2_div"
										title="${listBigMeatPM.FD_DESC}">
										<span id="limi">

												<a style="text-decoration: none; font-weight: bold;"
											href="javaScript:selectMenus('${listBigMeatPM.FD_ID}')">${listBigMeatPM.FD_NAME}&nbsp;&nbsp;&nbsp;&nbsp;</a>
											${listBigMeatPM.FD_DESC}<a
											style="text-decoration: none; float: right;"
											href="javaScript:selectMenus('${listBigMeatPM.FD_ID}')">>></a> 
											
											</span>
										<div class="dotted2"></div>
									</div>

								</c:forEach>
							</c:if>
                                  <c:if test="${null==listBigMeatPM}">
                               <span style="color: green;margin-top: 30px;">      晚餐未发布     </span>
                                                                              
                         </c:if>
						</div>


					</div>
				</div>
				
			</div>
			<div class="main_right">
			    <div style="top:0px;height:90px;">
			         <img id="main_right1_img2_light_on" class="weather_img" style="top:0px;cursor: pointer;" onclick="controllerDevice('main_right1_img2_light', 'light', 'on');" src="images/main_right1_img2_light_on.png"/>
			         <img id="main_right1_img2_light_off" class="weather_img" style="top:0px;cursor: pointer;" onclick="controllerDevice('main_right1_img2_light', 'light', 'off');" src="images/main_right1_img2_light_off.png"/>
				</div>
				<div class="main_right3" style="top:100px;height:568px;">
					<jsp:include page="/app/integrateservice/publicBook.jsp?orgId=${orgId}&type=tel&smac=${smac}&stel=${stel}"></jsp:include>
				</div>
			</div>
		</div>
		<div class="footer">
			<p align="center" style="margin-top: 5px;" ><span style="color: #7d7d7d;">市信息中心服务热线：68789555　 服务邮箱：NJIC@njnet.gov.cn</span></p>
			<p align="center" style="color: #7d7d7d;" >市机关物业管理服务中心服务热线：68789666　 服务邮箱：NJIC@njnet.gov.cn</p>
		</div>
	  </body>
  
<script type="text/javascript">
	$(function() {

		getTimees();
  		loadMsg();		// 消息
  		loadTraffic();	// 交通
  		loadBoard();	// 物业
  		inits();
  		setTimeout("getSSWD()", 1000);
  		
  		$("#main_right1_img1_door_${doorStatus}").hide();	// 设备控制
  		$("#main_right1_img2_light_${lightStatus}").hide();
  		$("#main_right1_img3_airCondition_${airConditionStatus}").hide();
  	});
  	
  	function getSSWD(){
  		var wd = $("#today_temperature").text();
		$.ajax({
			type: "POST",
			url: "${ctx}/app/integrateservice/getWD.act",
			dataType: 'text',
			async : false,
			success: function(jsontext){
				if (jsontext != "") {
					var jsonObj = jQuery.parseJSON(jsontext);
					$("#today_temperature").text(jsonObj.temp);
				} else {
					$("#today_temperature").text(wd);
				}
			},
			error: function(msg, status, e){
				$("#today_temperature").text(wd);
			}
		 });
	}
  	
  	// 加载交通信息
  	function loadTraffic() {
  		$.getJSON("${ctx}/app/integrateservice/loadTraffic.act", function(jsonObj){
	        $("#trafficList").children().remove();
	        $("#refreshTime").text(jsonObj.refreshTime+"更新");
	        if (jsonObj.list.length == 0) {
	        	$("#trafficList").append("<tr><td align='center'>暂无交通信息</td></tr>");
	        } else {
	        	var json = jsonObj.list;
		        $.each(json, function(i) {
		        	if (json[i].ROAD_CONDITION == "A")
		        		$("#trafficList").append("<tr><td><span>"+json[i].ROAD_NAME+"</span></td><td><span style='color: #E93614'>"+json[i].ROAD_CONDITION_NAME+"</span></td><td><span>"+parseInt(json[i].AVG_SPD)+"Km/h</span></td></tr>");
		        	else if (json[i].ROAD_CONDITION == "B")
		        		$("#trafficList").append("<tr><td><span>"+json[i].ROAD_NAME+"</span></td><td><span style='color: #F6A93F'>"+json[i].ROAD_CONDITION_NAME+"</span></td><td><span>"+parseInt(json[i].AVG_SPD)+"Km/h</span></td></tr>");
		        	else if (json[i].ROAD_CONDITION == "C")
		        		$("#trafficList").append("<tr><td><span>"+json[i].ROAD_NAME+"</span></td><td><span style='color: #51B744'>"+json[i].ROAD_CONDITION_NAME+"</span></td><td><span>"+parseInt(json[i].AVG_SPD)+"Km/h</span></td></tr>");
				});
	        }
        });	
  	}
  	
  	// 加载物业通知信息
  	function loadBoard() {
  		$.getJSON("${ctx}/app/integrateservice/queryMsgBoard.act", function(json){
	        $("#boardList").children().remove();
	        $.each(json, function(i) {
	        	var title =  json[i].TITLE.length > 18 ? json[i].TITLE.substring(0,18)+"..." : json[i].TITLE;
	        	var content = json[i].CONTENT.length > 50 ? json[i].CONTENT.substring(0,50)+"..." : json[i].CONTENT;
	        	var id = json[i].MSGID;
	        	if (i<=2) {
	        		if (json[i].ISREAD == "false") {
	        			$("#boardList").append("<div class='main_left_main5_main'> <div class='main_left_main5_div'> <a href='javascript:void(0);' style='text-decoration: none' onclick='showBoard("+id+")'><span style='text-align:left; font-weight: bold;' id='board_content_"+id+"'>"+title+"</span><br/><span style='text-align:left;'>"+content+"</span></a><a style='text-align:right; float: right;' href='javascript:void(0)'  onclick='showBoard("+id+");'>详情</a></div><div class='dotted'></div></div>");
	        		} else {
	        			$("#boardList").append("<div class='main_left_main5_main'> <div class='main_left_main5_div'> <a href='javascript:void(0);' style='text-decoration: none' onclick='showBoard("+id+")'><span style='text-align:left;' id='board_content_"+id+"'>"+title+"</span><br/><span style='text-align:left;'>"+content+"</span></a><a style='text-align:right; float: right;' href='javascript:void(0)'  onclick='showBoard("+id+");'>详情</a></div><div class='dotted'></div></div>");
	        		}
	        	}
			});
        });	
  	}
  	
  	function showBoard(msgid) {
  		//showShelter('800','450','${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId='+msgid);
  		var url = "${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId="+msgid;
		openEasyWinNotResizable('winId','物业通知',url,'800','450',false);
  		$("#board_content_"+msgid).css("fontWeight", "");
  	}
  	
  	function refectory() {
  		var url = "${ctx}/app/integrateservice/refectoryInformation.jsp";
  		//openEasyWin('winId','食堂视频',url,'660','500',false);
  		showShelter('660','510',url);
  	}
  	
 	// 加载系统消息 
	function loadMsg() {
    	$.getJSON("${ctx}/app/integrateservice/queryMsgBox.act", function(json){
    		$("div [id^='div_msg_']").remove();
    		$("#main_main1_bottom div").remove();
    		
    		if (json != null && json.length > 0) {
    			var len = parseInt(json.length / 5);			// 页数
    			var lenY = json.length % 5;
    			if (lenY > 0)	len += 1;
    			var pageNum = 0,id = 0, title = "", divNum = 0;	// 记录页数ID
    			
   				$.each(json,function(i){
   					if ((i+1) % 5 == 1) {						// 每页显示的消息数量：5条
   						pageNum += 1;
   						if (pageNum == 1)	$("#main_main1_bottom").append("<div id='main_main1_bottom_"+pageNum+"' class='main_main1_pointe1' onclick='show("+pageNum+")'></div>");
   						else  $("#main_main1_bottom").append("<div id='main_main1_bottom_"+pageNum+"' class='main_main1_pointe' onclick='show("+pageNum+")'></div>");
   					}
   					//显示信息
 					id = json[i].MSGID;
 					content = json[i].CONTENT;
 					title = json[i].TITLE;
					
					if (json[i].MSGTYPE == "task") content = title+" ("+content+")";
					else {
						if (title.length >= 30) {
							content = title.substring(0,29);
						} else if (title.length >=10 && title.length < 30) {
							content = title;
						} else if (title.length < 10) {
							if (content.length >=20) content = title+"　　"+content.substring(0,20)+"...";
							else content = title+"　　"+content.substring(0,20);
						}
					}
					
					// div标签id
					if ((i+1) % 5 == 0) divNum = 5;
					else divNum = (i+1) % 5;
					if (json[i].STATUS == "0") {	//未读
						if (json[i].MSGTYPE == null || json[i].MSGTYPE == "0") {
							$("#divMsgBox").append("<div class='main_main1_div"+divNum+"' id='div_msg_"+pageNum+"_"+(i+1)+"'> <div class='main_main1_div_logo_def'></div> <div class='main_main1_div_span'><a href='javascript:void(0);' style='text-decoration: none' onclick='showInfo("+id+")'><span style='font-weight: bold;' id='content_"+id+"'>"+content+"</span></a></div><div class='main_main1_div_last1' onclick='showInfo("+id+")'></div></div>");
						} else if (json[i].MSGTYPE == "task") {
							$("#divMsgBox").append("<div class='main_main1_div"+divNum+"' id='div_msg_"+pageNum+"_"+(i+1)+"'> <div class='main_main1_div_logo_def'></div> <div class='main_main1_div_span'><a href='javascript:void(0);' style='text-decoration: none' onclick='showList(&quot;"+title+"&quot;,&quot;"+json[i].TASKLINK+"&quot;)'><span style='font-weight: bold;' id='content_"+id+"'>"+content+"</span></a></div><div class='main_main1_div_last1'  onclick='showList(&quot;"+title+"&quot;,&quot;"+json[i].TASKLINK+"&quot;)'></div></div>");
						} else {
							if (json[i].MSGTYPE == "1") {
								$("#divMsgBox").append("<div class='main_main1_div"+divNum+"' id='div_msg_"+pageNum+"_"+(i+1)+"'> <div class='main_main1_div_logo"+json[i].MSGTYPE+"'></div> <div class='main_main1_div_span'><a href='javascript:void(0);' style='text-decoration: none' onclick='showInfo("+id+")'><span style='font-weight: bold;' id='content_"+id+"'>"+content+"</span></a></div><div class='main_main1_div_last1' onclick='showInfo("+id+")'></div></div>");
							} else {
								$("#divMsgBox").append("<div class='main_main1_div"+divNum+"' id='div_msg_"+pageNum+"_"+(i+1)+"'> <div class='main_main1_div_logo"+json[i].MSGTYPE+"'></div> <div class='main_main1_div_span'><a href='javascript:void(0);' style='text-decoration: none' onclick='showInfo("+id+")'><span style='font-weight: bold;' id='content_"+id+"'>"+content+"</span></a></div><div class='main_main1_div_last_msg' onclick='showInfo("+id+")'></div></div>");
							}
						}
					} else {
						if (json[i].MSGTYPE == null || json[i].MSGTYPE == "0") {
							$("#divMsgBox").append("<div class='main_main1_div"+divNum+"' id='div_msg_"+pageNum+"_"+(i+1)+"'> <div class='main_main1_div_logo_def'></div> <div class='main_main1_div_span'><a href='javascript:void(0);' style='text-decoration: none' onclick='showInfo("+id+")'><span  id='content_"+id+"'>"+content+"</span></a></div><div class='main_main1_div_last1' onclick='showInfo("+id+")'></div></div>");
						} else {
							if (json[i].MSGTYPE == "1") {
								$("#divMsgBox").append("<div class='main_main1_div"+divNum+"' id='div_msg_"+pageNum+"_"+(i+1)+"'> <div class='main_main1_div_logo"+json[i].MSGTYPE+"'></div> <div class='main_main1_div_span'><a href='javascript:void(0);' style='text-decoration: none' onclick='showInfo("+id+")'><span id='content_"+id+"'>"+content+"</span></a></div><div class='main_main1_div_last1' onclick='showInfo("+id+")'></div></div>");
							} else {
								$("#divMsgBox").append("<div class='main_main1_div"+divNum+"' id='div_msg_"+pageNum+"_"+(i+1)+"'> <div class='main_main1_div_logo"+json[i].MSGTYPE+"'></div> <div class='main_main1_div_span'><a href='javascript:void(0);' style='text-decoration: none' onclick='showInfo("+id+")'><span id='content_"+id+"'>"+content+"</span></a></div><div class='main_main1_div_last_msg' onclick='showInfo("+id+")'></div></div>");
							}
						}
					}
				});
				
				if (len >= 2) {
					for (var idx = 2; idx <= len; idx++) {
						$("#divMsgBox [id^=div_msg_"+idx+"]").hide();	// 除了第一页，属于其他页的数据隐藏
					}
				}
    		} else {
    			$("#divMsgBox").append("<div class='main_main1_div1' id='div_msg_0_0'><div class='main_main1_div_logo_def'></div><div class='main_main1_div_span'><span>无消息</span></div></div>");
   			}
		});
	}
	
	function togglePage(type) {
		if (type == "") {
		
		}
	}
	
	function showList(title, link) {
		if (title == '我的来访') {
			var url = _ctx+"/app/myvisit/queryPopup.act";
			//showShelter('950','600',url)
			openEasyWinNotResizable('winVisitPopup','待处理来访',url,'950','600',false);
		} else if (title == '我的派单' || title == '报修清单') {
			var id = "", linkUrl = _ctx+link;
			if (title == '报修清单')  id = "10093";
			else id = "10149";
			if (id != "" && title != "" && linkUrl != "") {
				parent.open_main3(id,title,linkUrl+"?isIndexPage=y");
			}
		}
	}
	
	// 显示指定页的数据
	function show(idx) {
		$("#divMsgBox div[id^=div_msg_]").hide();			// 隐藏
		$("#divMsgBox div[id^=div_msg_"+idx+"_]").show();	// 显示指定页
		
		$("#main_main1_bottom div[id^='main_main1_bottom_']").removeClass();	// 设置所有的按钮为统一色
		$("#main_main1_bottom div[id^='main_main1_bottom_']").addClass("main_main1_pointe");
		$("#main_main1_bottom_"+idx).addClass('main_main1_pointe1');	// 高亮显示指定按钮
	}
	
	function showInfo(msgId) {
		var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
		openEasyWin('winId','查看消息',url,'700','500',true);
		//showShelter("700", "600", url);
		$("#content_"+msgId).css("fontWeight", "");
	}
	
	function close_door() {
		$("#main_right1_img1_door_on").show();
		$("#main_right1_img1_door_off").hide();
	}
	
	function controllerDevice(idPrefix, deviceType, optType){
		$.ajax({
			type: "POST",
			url: "${ctx}/app/integrateservice/controllerDevice.act",
			data: {"deviceType": deviceType, "optType": optType},
			dataType: 'text',
			async : false,
			success: function(msg){
				  if(msg == "success") {
				  	if (deviceType == "door") {
					  	$("#"+idPrefix+"_on").hide();
		  				$("#"+idPrefix+"_off").show();
				  		setTimeout("close_door()", 1000);	// 开门1秒之后关门
				  	} else {
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
		 });
	}
    
	function breakfast(dom){
		for(var i = 0; i < 3; i++) {
			dom.parentNode.getElementsByTagName("div")[i].className = "";
			dom.parentNode.getElementsByTagName("div")[i].getElementsByTagName("span")[0].style.fontWeight = "100";
		}
		dom.className = "main_main2_tip_select";
		dom.getElementsByTagName("span")[0].style.fontWeight = "bold";
    	$("#main_left_main2_breakfast").show();
    	$("#main_left_main2_lunch").hide();
    	$("#main_left_main2_dinner").hide();
	}
	
 	function lunch(dom){
 		for(var i = 0; i < 3; i++) {
			dom.parentNode.getElementsByTagName("div")[i].className = "";
			dom.parentNode.getElementsByTagName("div")[i].getElementsByTagName("span")[0].style.fontWeight = "100";
		}
		dom.className = "main_main2_tip_select";
		dom.getElementsByTagName("span")[0].style.fontWeight = "bold";
		$("#main_left_main2_breakfast").hide();
    	$("#main_left_main2_lunch").show();
    	$("#main_left_main2_dinner").hide();
	}

  	function dinner(dom){
  	  	for(var i = 0; i < 3; i++) {
			dom.parentNode.getElementsByTagName("div")[i].className = "";
			dom.parentNode.getElementsByTagName("div")[i].getElementsByTagName("span")[0].style.fontWeight = "100";
		}
		dom.className = "main_main2_tip_select";
		dom.getElementsByTagName("span")[0].style.fontWeight = "bold";
		$("#main_left_main2_breakfast").hide();
	  	$("#main_left_main2_lunch").hide();
	  	$("#main_left_main2_dinner").show();
	}
	
 	function weekMenus(){
 		parent.open_main3('cdcx' ,'一周菜单' ,'${ctx}/app/integrateservice/weekMenusQuery.act');
	}
	function getTimees(){
		var myDate = new Date();
		var time =myDate.getHours()

     	if(6<=time&&time<9){
         	
	     	//$("#main_left_main2_breakfast").show();
	       	//$("#main_left_main2_lunch").hide();
	       	//$("#main_left_main2_dinner").hide();
        	breakfast(document.getElementById("breakfast1"));
        	$("#jcont").val("1");
		}else if(time>=9&&time<14){
		
			//$("#main_left_main2_breakfast").hide();
	       	//$("#main_left_main2_lunch").show();
	       	//$("#main_left_main2_dinner").hide();
	       	lunch(document.getElementById("lunch1"));
	       	$("#jcont").val("2");
		}else {
			
	      	//  $("#main_left_main2_breakfast").hide();
	        //	$("#main_left_main2_lunch").hide();
	        //	$("#main_left_main2_dinner").show();
       		dinner(document.getElementById("dinner1"))
       		$("#jcont").val("3");
        }
	}

	function selectMenus(fdId){
		var url = "${ctx}/app/integrateservice/menusOneQuery.act?fdId="+fdId;
		//showShelter("600", "300", url);
		openEasyWinNotResizable('winId','菜单详情',url,'590','330',false);
	}

	  function refreshMenu(){
	    	var fdiFlag = $("#jcont").val();
	    	$.ajax({
	            type:"POST",
	            url:"${ctx}/app/integrateservice/refreshMenu.act",
	            data:{fdiFlag:fdiFlag},
	            dataType: 'json',
			    async : false,
			    success: function(json){
		             if (json!=null) {  
						if(fdiFlag=="1"){
		            	   $("#main_left_main2_breakfast").empty();
			            }else if(fdiFlag=="2"){
			            	$("#main_left_main2_lunch").empty();
				        }else if(fdiFlag=="3"){
					        $("#main_left_main2_dinner").empty();
					    }
		       	        
			           	$.each(json,function(i,item){ 
			           		 var trContent = "<div class='main_left_main2_div' title="+item.FD_DESC+"><span id='limi'><a style='text-decoration: none; font-weight: bold;' href='javaScript:selectMenus("+item.FD_ID+")'>"+item.FD_NAME+"　　</a>"+item.FD_DESC+"<a style='text-decoration: none; float: right;' href='javascript:selectMenus("+item.FD_ID+")'>>></a></span><div class='dotted2'></div></div>";
						     if(fdiFlag=="1"){
				            	$("#main_left_main2_breakfast").append(trContent);
					         }else if(fdiFlag=="2"){
					            $("#main_left_main2_lunch").append(trContent);
						     }else if(fdiFlag=="3"){
						        $("#main_left_main2_dinner").append(trContent);
							 }
					  	});
		          }
	         }
	    	 });
	   }
</script>
</html>
