<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>综合办公首页</title>
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
	<script src="${ctx}/app/integrateservice/js/util.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/js/newtree.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/js/IntegratedOffice.js" type="text/javascript"></script>
	<script src="${ctx}/app/portal/js/jquery.min.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/ca/highcharts.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/ca/exporting.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/js/index.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/js/energy.js" type="text/javascript"></script>
	
	<link href="${ctx}/app/integrateservice/css/newtree.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/jienen.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div class="main_index">
 
<!-- 存入返回上一级菜单的连接   如果没有上一级连接 可不存入数据     null "" 0 1 都是隐藏上一级菜单及首页菜单-->
<!-- 存放上一级菜单连接的 样例：${ctx}/app/integrateservice/init.act -->
<!-- name="gotoParentPath"  这个写法固定，不许自定义 ，如果name 不相同，则无法取到数据-->
<jsp:include page="header.jsp">
	<jsp:param value="0" name="gotoParentPath"/>
</jsp:include>
<input type="hidden" id="treeUnitValue" value="tree" />
<div class="main_index">
<div>
	<div class="mina1_left">
		<div class="main_left_main1">
			<div class="main_border_01">
				<div class="main_border_02">天气预报</div>
			</div>
			<div class="main_conter">
			<div class="min_title">今天是</div>
				<div class="now_time">
				<span id="now_date"></span><span id="now_date1"></span><span>&nbsp;</span><span id="now_week"></span>&nbsp;<a style="cursor: pointer;" onclick="showShelter('670','520','${ctx}/app/integrateservice/weather.html');"><img src="images/more.jpg" width="39" height="17" border="0" /></a></div>
		        <div class="weather_change" onclick="showShelter('670','520','/app/integrateservice/weather.html');" title="更多"></div>
		        <img id="weather_img" class="weather_img" src="images/s_1.png"/>
		        <div class="main1_span3"><span id="today_min"></span><span>~</span><span id="today_max"></span><span>℃</span></div>
		        <div class="main1_span2"><span id="today_weather"></span><br /><span id="today_fengxiang"></span><span id="today_fengji"></span>级</div>
		        <div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="main_left_main1">
			<div class="main_border_01">
				<div class="main_border_02">空调设定</div>
			</div>
			<div id="conditioner_normal" class="main_conter1" style="display: none">
				<div class="clear"></div>
					<div class="main_left_kt" ><span id="room_temperature" class="main_left_kt" style="margin-right: 15px">26</span><span class="main_left_kt" style="font-size: 15px;font-weight: bold;padding-top:5px;">℃</span><br />
						<span class="main_kt_span">当前室温</span>
					</div>
					<div class="main_right_kt">
						<a class="mr_a1" onclick="controllerDevice('main_right1_img2_cool', 'conditioner', 'reduce');" href="javascript:void(0)"></a>
					<div class="mr_a2"><span id="conditioner_temperature" class="main_spans1">24</span><span class="main_spans2">℃</span></div><a class="mr_a1" onclick="controllerDevice('main_right1_img2_cool', 'conditioner', 'add');" href="javascript:void(0)"></a>
					<br/>
					<span class="main_kt_span"  style="float:left">设定温度</span>
				</div>
				<div class="clear"></div>
			</div>
			<div id="conditioner_error"  style="width:100%;" >
				<img src="images/conditioner_error.jpg" width="100%" height="100%" alt="" />
			</div>
		</div>
		<div class="clear"></div>
		<div class="main_left_main1">
			<div class="main_border_01">
				<div class="main_border_02">我的节能贡献</div>
			</div>
			<div class="main_conter">
				<div class="main_img1" style="position: relative;">
					<div id="container"></div>
					<!-- <img src="images/t_2.jpg" width="280" height="130" /> -->
					<br /> 
					<div style="position: absolute;top: 190px;left: 200px;">
						<a style="cursor: pointer;" onclick="showLargeChart();" target="_blank"><img class="main_imgs1"	src="images/t_3.jpg" width="72" height="17" border="0" /></a>
					</div>
					<br />
					<div style="width: 280px; height: 86px;">
						<table width="100%" border="0" id="table_energy_id"></table>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<div class="mina_center">
	<!--1-->
	<div class="main_left_main1">
	   <div class="main_border_01">
	     <div class="main_border_02">我的信息</div>
	   </div>
	   <div class="main_conter">
	<div class="main_left_x1">
	  <ul id="tongzhi">
	    <li><a name="tongzhi" class="tongzhi" id="queryMsgListId" onclick="UpAction('queryMsgList','queryMsgListId')"><span class="x1_main">通知</span><span class="x1_main2">未读<span id="msgNotReadNum" class="x1_main3">[0条]</span></span><span class="x1_main1">NOTIFICATION</span></a></li>
	    <li><a name="tongzhi" class="tongzhi" id="queryMyCallListId" onclick="UpAction('queryMyCallList','queryMyCallListId')"><span class="x1_main">访客</span><span class="x1_main2">申请中<span id="visitorNotReadCount" class="x1_main3">[0条]</span></span><span class="x1_main1">VISITORS</span></a></li>
	    <li><a name="tongzhi" class="tongzhi" id="queryMyMatterId" onclick="UpAction('queryMyMatter','queryMyMatterId')"><span class="x1_main">待办事项</span><span class="x1_main2">待办<span class="x1_main3" id="matterCount">[0条]</span></span><span class="x1_main1">BACKLOG</span></a></li>
	    <li><a name="tongzhi" class="tongzhi" id="queryFaxId" onclick="UpAction('queryFax','queryFaxId')"><span class="x1_main">传真</span><span class="x1_main2">未读<span id="faxNotReadNum" class="x1_main3">[0条]</span></span><span class="x1_main1">FACSIMILE</span></a></li>
	  	<input type="hidden" id="action" value="queryMsgList" />
	  </ul>
	</div>
	<div class="main_right_x1" style="position: relative;">
	  <div id="divMsgBox">
	  </div>
	  <div style="top:140px;left:310px;position: absolute;">
	  	<a style="cursor: pointer;" onclick="JumpUrl()"><img id="imageSrc" class="st_img1" src="images/t_4.jpg" width="72" height="17" border="0" /></a>
	  </div>
	  </div>
	     <div class="clear"></div>
	  </div>
	</div>
	<!--/1-->
	     <div class="clear"></div>
	<!--2-->
	<!-- 今日菜单功能项开始 -->
	<div class="main_left_main1" style="overflow: hidden;">
		<div class="main_border_01">
			<div class="main_border_02">今日菜单</div>
		</div>
		<div class="main_conter" style="height:178px;">
			<div class="main_main1">
				<ul>
					<li class="treeNone" id="amEatId"><a style="cursor: pointer;" onclick="upClassEat('amEatId');eatSwitch('amEat')">早餐</a></li>
					<li class="treeOn" id="bmEatId"><a style="cursor: pointer;" onclick="upClassEat('bmEatId');eatSwitch('bmEat')">午餐</a></li>
					<li class="treeNone" id="pmEatId"><a style="cursor: pointer;" onclick="upClassEat('pmEatId');eatSwitch('pmEat')">晚餐</a></li>
					<script type="text/javascript">
						//切换选择菜单像
						function eatSwitch(id){
							$(".main_main2").siblings().css('display','none');
							$("#"+id).css('display','block');
						}
						function upClassEat(id){
							if("amEatId" == id){
								$("#amEatId").attr('class','treeOn');
								$("#bmEatId").attr('class','treeNone');
								$("#pmEatId").attr('class','treeNone');
							}else if("bmEatId" == id){
								$("#amEatId").attr('class','treeNone');
								$("#bmEatId").attr('class','treeOn');
								$("#pmEatId").attr('class','treeNone');
							}else if("pmEatId" == id){
								$("#amEatId").attr('class','treeNone');
								$("#bmEatId").attr('class','treeNone');
								$("#pmEatId").attr('class','treeOn');
							}
						}
					</script>
				</ul>
				<div class="main_img2">
					<a style="cursor: pointer;" onclick="refectory()">
						<img src="images/shipin_1.jpg" width="15" height="9" />
					</a>
				</div>
			</div>
			<div>
				<!-- 早餐选项开始 -->
				<div class="main_main2" id="amEat" style="display:none;">
					<c:if test="${empty listBigMeatAM}">
					<!-- 
					<div class="main_main2_img" style="height:178px;">
					 -->
					<div class="main_main2_img">
						<span style="color: green; margin-top: 30px;">      早餐未发布                  </span> 
					</div>                
		            </c:if>
					<c:if test="${not empty listBigMeatAM}">
						<!-- 图片展示 -->
						<div style="float:left;display:inline-block;width:105px;height:78px;">
							<img id="menuPhotoAM" src="${ctx}/app/integrateservice/images/food_no.jpg" width="105" height="78" />
							<script type="text/javascript">
								if (${not empty listMenuPhotoAM}) {
									var idpa = 0;
									var idpal = ${fn:length(listMenuPhotoAM)};
									var lMenuPhotoAM = new Array();
									<c:forEach items="${listMenuPhotoAM}" var="mpAM" varStatus="stat">
										lMenuPhotoAM.push('${mpAM.SRC}');
									</c:forEach>
									$('#menuPhotoAM').attr("src", lMenuPhotoAM[idpa]);
									if (idpal > 1) {
										refreshMenuPhoteAM();
									}
									function refreshMenuPhoteAM() {
										setTimeout(function() {
											idpa++;
											if (idpa >= idpal) {
												idpa = 0;
											}
											$('#menuPhotoAM').fadeOut(500, function() {
												$('#menuPhotoAM').attr("src", lMenuPhotoAM[idpa]);
												$('#menuPhotoAM').fadeIn(500);
											});
											refreshMenuPhoteAM();
										}, 10000);
									}
								}
							</script>
						</div>
						<div style="float:left;display:inline-block;overflow:hidden;width:510px;">
						<div class="menu_left" id="leftBtnAm" style="display:none"></div>
						<div class="menu_right" id="rightBtnAm" style="display:none"></div>
						<script type="text/javascript">
							if (${fn:length(listBigMeatAM)} > 3) {
								$("#rightBtnAm").show();
								var countAm = parseInt(${fn:length(listBigMeatAM)}) - parseInt(3);
								var iam = 0;
								$("#rightBtnAm").click(function() {
									iam++;
									if (iam == 1) {
										$("#leftBtnAm").show();
									}
									if (iam == countAm) {
										$("#rightBtnAm").hide();
									}
									var ml = iam*170;
									$("#menuDataAm").css("margin-left", "-"+ml+"px");
								});
								$("#leftBtnAm").click(function() {
									iam--;
									if (iam == 0) {
										$("#leftBtnAm").hide();
									}
									if (iam == countAm-1) {
										$("#rightBtnAm").show();
									}
									var ml = iam*170;
									$("#menuDataAm").css("margin-left", "-"+ml+"px");
								});
							}
						</script>
						<div style="width:1500px;" id="menuDataAm">
							<c:forEach items="${listBigMeatAM}" var="listBigMeatAMDate" varStatus="status">
									<c:if test="${status.index == 0 }">
										<div class="main_main2_img" style="border-left:solid 1px #fff;">
									</c:if>
									<c:if test="${status.index != 0 }">
										<div class="main_main2_img" style="border-left:solid 1px #ccc;">
									</c:if>
										<p>
										<span class="main1_cx">${listBigMeatAMDate[0].FM_NAME}</span><br />
										<script type="text/javascript">
											var desc = '${listBigMeatAMDate[0].FD_NAME }';
											var arr = desc.split(',');
											var text = "";
											for (var i=0;i<arr.length;i++) {
												if (i == 3) {
													if (arr[2].length <= 11) {
														text += "...";
													}
													break;
												}
												if (i == 2) {
													if (arr[i].length > 11) {
														text += arr[i].substring(0,8)+"...";
													} else {
														text += arr[i].substring(0,8);
													}
												} else {
													if (arr[0].length > 11) {
														text += arr[i].substring(0,8) + "...<br>";
													} else {
														text += arr[i].substring(0,11) + "<br>";
													}
												}
 											}
											document.write(text);
										</script>
										</p>
									</div>
							</c:forEach>
						</div>
						</div>
					</c:if>
					<div class="clear"></div>
				</div>
				<!-- 早餐选项结束 -->
				<!-- 午餐选项开始 -->
				<div class="main_main2" id="bmEat" style="display: block;">
					<c:if test="${empty listBigMeatNOON}">
						<div class="main_main2_img">
							<span style="color: green;margin-top: 30px;">    午餐未发布   </span> 
						</div>
		            </c:if>
		            <c:if test="${not empty listBigMeatNOON}">
		            	<!-- 图片展示 -->
						<div style="float:left;width:105px;height:78px;">
							<img id="menuPhotoNOON" src="${ctx}/app/integrateservice/images/food_no.jpg" width="105" height="78" />
							<script type="text/javascript">
								if (${not empty listMenuPhotoNOON}) {
									var idpn = 0;
									var idpnl = ${fn:length(listMenuPhotoNOON)};
									var lMenuPhotoNOON = new Array();
									<c:forEach items="${listMenuPhotoNOON}" var="mpNOON" varStatus="stat">
										lMenuPhotoNOON.push('${mpNOON.SRC}');
									</c:forEach>
									$('#menuPhotoNOON').attr("src", lMenuPhotoNOON[idpn]);
									if (idpnl > 1) {
										refreshMenuPhoteNOON();
									}
									function refreshMenuPhoteNOON() {
										setTimeout(function() {
											idpn++;
											if (idpn >= idpnl) {
												idpn = 0;
											}
											$('#menuPhotoNOON').fadeOut(500, function() {
												$('#menuPhotoNOON').attr("src", lMenuPhotoNOON[idpn]);
												$('#menuPhotoNOON').fadeIn(500);
											});
											refreshMenuPhoteNOON();
										}, 10000);
									}
								}
							</script>										
						</div>
						<div style="float:left;width:510px;">
						<div class="menu_left" id="leftBtnNoon" style="display:none"></div>
						<div class="menu_right" id="rightBtnNoon" style="display:none"></div>
						<script type="text/javascript">
							if (${fn:length(listBigMeatNOON)} > 3) {
								$("#rightBtnNoon").show();
								var countNoon = parseInt(${fn:length(listBigMeatNOON)}) - parseInt(3);
								var inoon = 0;
								$("#rightBtnNoon").click(function() {
									inoon++;
									if (inoon == 1) {
										$("#leftBtnNoon").show();
									}
									if (inoon == countNoon) {
										$("#rightBtnNoon").hide();
									}
									var ml = inoon*170;
									$("#menuDataNoon").css("margin-left", "-"+ml+"px");
								});
								$("#leftBtnNoon").click(function() {
									inoon--;
									if (inoon == 0) {
										$("#leftBtnNoon").hide();
									}
									if (inoon == countNoon-1) {
										$("#rightBtnNoon").show();
									}
									var ml = inoon*170;
									$("#menuDataNoon").css("margin-left", "-"+ml+"px");
								});
							}
						</script>
						<div style="width:510px;position:absolute;z-index:0;overflow:hidden;">
						<div style="width:1500px;" id="menuDataNoon">
							<c:forEach items="${listBigMeatNOON}" var="listBigMeatNOONDate" varStatus="status">
									<c:if test="${status.index == 0 }">
										<div class="main_main2_img" style="border-left:solid 1px #fff;">
									</c:if>
									<c:if test="${status.index != 0 }">
										<div class="main_main2_img" style="border-left:solid 1px #ccc;">
									</c:if>
										<p>
										<span class="main1_cx">${listBigMeatNOONDate[0].FM_NAME}</span><br />
										<c:if test="${not empty listBigMeatNOONDate[0].FM_BAK2}">
										<span class="main1_xz">${listBigMeatNOONDate[0].FM_BAK2}</span><br />
										</c:if>
										<script type="text/javascript">
											var count = 3;
											if (${not empty listBigMeatNOONDate[0].FM_BAK2}) {
												count = 2;
											}
											var desc = '${listBigMeatNOONDate[0].FD_NAME }';
											var arr = desc.split(',');
											var text = "";
											for (var i=0;i<arr.length;i++) {
												if (i == count) {
													if (arr[count-1].length <= 11) {
														text += "...";
													}
													break;
												}
												if (i == count-1) {
													if (arr[i].length > 11) {
														text += arr[i].substring(0,8)+"...";
													} else {
														text += arr[i].substring(0,8);
													}
												} else {
													if (arr[i].length > 11) {
														text += arr[i].substring(0,8) + "...<br>";
													} else {
														text += arr[i].substring(0,11) + "<br>";
													}
												}
 											}
											document.write(text);
										</script>
										</p>
									</div>
							</c:forEach>
						</div>
						</div>
						</div>
					</c:if>
					<div class="clear"></div>
				</div>
				<!-- 午餐选项结束 -->
				<!-- 晚餐选项开始 -->
				<div class="main_main2" id="pmEat" style="display: none;">
					<c:if test="${empty listBigMeatPM}">
						<div class="main_main2_img">
						<span style="color: green;margin-top: 30px;">      晚餐未发布     </span>
						</div>
					</c:if>
					 <c:if test="${not empty listBigMeatPM}">
					 	<!-- 图片展示 -->
						<div style="float:left;display:inline-block;width:105px;height:78px;">
							<img id="menuPhotoPM" src="${ctx}/app/integrateservice/images/food_no.jpg" width="105" height="78" />
							<script type="text/javascript">
								if (${not empty listMenuPhotoPM}) {
									var idpp = 0;
									var idppl = ${fn:length(listMenuPhotoPM)};
									var lMenuPhotoPM = new Array();
									<c:forEach items="${listMenuPhotoPM}" var="mpPM" varStatus="stat">
										lMenuPhotoPM.push('${mpPM.SRC}');
									</c:forEach>
									$('#menuPhotoPM').attr("src", lMenuPhotoPM[idpp]);
									if (idppl > 1) {
										refreshMenuPhotePM();
									}
									function refreshMenuPhotePM() {
										setTimeout(function() {
											idpp++;
											if (idpp >= idppl) {
												idpp = 0;
											}
											$('#menuPhotoPM').fadeOut(500, function() {
												$('#menuPhotoPM').attr("src", lMenuPhotoPM[idpp]);
												$('#menuPhotoPM').fadeIn(500);
											});
											refreshMenuPhotePM();
										}, 10000);
									}
								}
							</script>
						</div>
						<div style="float:left;display:inline-block;overflow:hidden;width:510px;">
						<div class="menu_left" id="leftBtnPm" style="display:none"></div>
						<div class="menu_right" id="rightBtnPm" style="display:none"></div>
						<script type="text/javascript">
							if (${fn:length(listBigMeatPM)} > 3) {
								$("#rightBtnPm").show();
								var countPm = parseInt(${fn:length(listBigMeatPM)}) - parseInt(3);
								var ipm = 0;
								$("#rightBtnPm").click(function() {
									ipm++;
									if (ipm == 1) {
										$("#leftBtnPm").show();
									}
									if (ipm == countPm) {
										$("#rightBtnPm").hide();
									}
									var ml = ipm*170;
									$("#menuDataPm").css("margin-left", "-"+ml+"px");
								});
								$("#leftBtnPm").click(function() {
									ipm--;
									if (ipm == 0) {
										$("#leftBtnPm").hide();
									}
									if (ipm == countPm-1) {
										$("#rightBtnPm").show();
									}
									var ml = ipm*170;
									$("#menuDataPm").css("margin-left", "-"+ml+"px");
								});
							}
						</script>
						<div style="width:1500px;" id="menuDataPm">
							<c:forEach items="${listBigMeatPM}" var="listBigMeatPMDate" varStatus="status">
									<c:if test="${status.index == 0 }">
										<div class="main_main2_img" style="border-left:solid 1px #fff;">
									</c:if>
									<c:if test="${status.index != 0 }">
										<div class="main_main2_img" style="border-left:solid 1px #ccc;">
									</c:if>
										<p>
										<span class="main1_cx">${listBigMeatPMDate[0].FM_NAME}</span><br />
										<script type="text/javascript">
											var desc = '${listBigMeatPMDate[0].FD_NAME }';
											var arr = desc.split(',');
											var text = "";
											for (var i=0;i<arr.length;i++) {
												if (i == 3) {
													if (arr[2].length <= 11) {
														text += "...";
													}
													break;
												}
												if (i == 2) {
													if (arr[i].length > 11) {
														text += arr[i].substring(0,8)+"...";
													} else {
														text += arr[i].substring(0,8);
													}
												} else {
													if (arr[i].length > 11) {
														text += arr[i].substring(0,8) + "...<br>";
													} else {
														text += arr[i].substring(0,11) + "<br>";
													}
												}
 											}
											document.write(text);
										</script>
										</p>
									</div>
							</c:forEach>
						</div>
						</div>
					</c:if>
					<div class="clear"></div>
				</div>
				<!-- 晚餐选项结束 -->
			</div>
			<!-- 查看更多选项 -->
			<a style="cursor: pointer;" href="${ctx}/app/integrateservice/weekMenusQuery.act">
				<img class="st_img1" src="images/t_4.jpg" width="72" height="17" border="0" />
			</a>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 今日菜单功能项结束 -->
	<!--/2-->
	     <div class="clear"></div>
	<!--	物业通知开始	-->
	<div class="wuye_main">
		<div class="main_border_01">
			<div class="main_border_02">物业信息</div>
		</div>
		<div class="main_conter" style="height:172px;">
			<div class="mina2_left">
				<div class="main2_border_01">
					<p>通知信息</p>
				</div>
				<ul id="boardNoti">
				</ul>
					<a href="${ctx}/common/bulletinMessage/msgBoardAction_query.act"><img class="st_img1" src="images/t_4.jpg" width="72" height="17" border="0" /></a>
			</div>
			<div class="main2_right">
				<div class="main2_border_01">
					<p>失物招领</p>
				</div>
				<ul id="article">
				</ul>
					<a href="${ctx}/app/pro/showMoreLostFounds.act" style="display: ${lostFoundTotal != 0 ? 'block' : 'none'}" href="javascript:void(0);"><img  class="st_img1" src="images/t_4.jpg" width="72" height="17" border="0" /></a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<!--	物业通知结束	-->
	</div>
	<!-- 右边功能栏开始 -->
	<div class="mina_right" style="height:673px;overflow: hidden;">
		<div class="main_left_main1" style="display: block;overflow: hidden;" id="multFun">
			<ul>
				<script type="text/javascript">
					var menuIconNum = 0;
					getMenuList();
					function getMenuList(){
						var url=_ctx+"/app/auth/index/ajaxQueryNavMenu.act";
						$.ajax({
							type: "POST",
							url: url,
							dataType: 'json',
							async : false,
							success: function(json){								
								if(json.length > 0) {
									
									$.each(json, function(i) {
										//if(i >= menuIconNum){
											//return;
										//}
										var str = "<li class='main_nav'>";
										if(json[i].openStatus == 0){
											//str += "<a style='cursor:pointer;' title="+json[i].TITLE+" target='_self' onclick=\"showShelter('860','710','${ctx}/"+json[i].LINK+"')\">";
											str += "<a style='cursor:pointer;' title="+json[i].TITLE+" target='_self' onclick=\"showGuide('640','564')\">";
										}else if(json[i].openStatus == 1){
											str += "<a style='cursor:pointer;' title="+json[i].TITLE+" target='_self' onclick=\"showShelter('660','446','${ctx}/"+json[i].LINK+"')\">";
										}else if(json[i].openStatus == 10){
											str += "<a style='cursor:pointer;' title="+json[i].TITLE+" target='_self' onclick=\"showShelter('560','510','${ctx}/"+json[i].LINK+"')\">";
										}else if(json[i].openStatus == 2){
											str += "<a style='cursor:pointer;' title="+json[i].TITLE+" target='_self' onclick=\"showShelter('800','710','${ctx}/"+json[i].LINK+"')\">";
										}else if(json[i].openStatus == 3){
											str += "<a style='cursor:pointer;' title="+json[i].TITLE+" target='_self' onclick=\"showShelter('750','430','${ctx}/"+json[i].LINK+"')\">";
										}else{
											if(json[i].isZYGL == 80){
												str += "<a title="+json[i].TITLE+" target='_self' href="+json[i].LINK+">";
											}else{
												str += "<a title="+json[i].TITLE+" target='_self' href="+_ctx+"/"+json[i].LINK+">";	
											}
										}
											
										str += "<img src='" + _ctx + "/" + json[i].ICO + "' ";
										str += "onmousemove=\"this.src='"+_ctx+"/" + json[i].imgHover + "'\" ";
										str += "onmouseout=\"this.src='"+_ctx+"/"+json[i].ICO+"'\" ";
										str += "width='109' height='67' border='0' />";
										str += "</a>"; 
										str += "</li>";		
										
										var title = json[i].TITLE; 
										
										if(title.indexOf("管理局") != -1 ){
											$("#gljId").attr("href", _ctx+"/"+json[i].LINK);
											$("#gljId").show();
										}else if(title.indexOf("物业首页") != -1){
											$("#wuyeId").attr("href", _ctx+"/"+json[i].LINK);
											$("#wuyeId").show();
										}else if(json[i].TITLE == "超级管理员首页"){
											$("#superAdminId").attr("href", _ctx+"/"+json[i].LINK);
											$("#superAdminId").show();
										}else{
											menuIconNum++;
											document.write(str);
										}
									});
								}else{
									menuIconNum = 0;
								}
							 },
							 error: function(msg, status, e) {
								 easyAlert("提示信息", "Ajax操作出错","info");
							 }
						 });
				     }
				</script>
			</ul>
		</div>
		<div class="clear" style="height: 0px;"></div>
		<div  align="center" style="border: 0px solid red;height:10px;cursor: pointer;" onclick="upOrDownCheck()">
			<img id="upOrDown" src="images/upto.png" alt="" style="width: 100%;height:10px;bottom: 5px;"/>
		</div>
		
		<jsp:include page="newtree.jsp" />
		
	     <div class="clear"></div>
	  </div>
	</div>
	</div>
	
	<!-- 右边功能栏结束 -->
	<DIV class="clear"></DIV>
</div>
<jsp:include page="footer.jsp" />
</div>
</body>
<script type="text/javascript">
	var flag1 = true; //上下切换变量
	var num = parseInt(menuIconNum);		
	var bottomMin = 35;
	var sizeMax = 673;  //右边框总高度
	var firstOutDiv , secondWithDiv ;
	
	//判断当前功能有多少行
	
	if((parseInt(num % 3)) != 0){
		num = parseInt(num / 3) + 1;
	}else{
		num = parseInt(num / 3);
	}
	
	var treeStyles = document.getElementById("multFun").style; //功能栏对象
	var hei = treeStyles.height;  //取得功能栏对象的高度
	var size = parseInt(sizeMax) - parseInt(72 * num); // 取得功能栏高度之外的高度
	var treeCommHeight = document.getElementById("treeComm").style; //树对象
	
	firstOutDiv = parseInt(size) - 49 ;      //取得外框高度
	secondWithDiv = parseInt(size) - 49 - 92 ; //取得树框高度
	
	//当size 小于 body高度 - 功能框高度
	if(size > 35){
		treeCommHeight.height = size + "px"; //动态获取通讯录高度
		$("#address_list").animate({height:secondWithDiv + "px"});  //动态获取树内部高度
		$("#treeComm_div").animate({height:firstOutDiv + "px"}); //动态获取树外部高度
	}
	else{
		treeStyles.height = sizeMax + "px";
		treeCommHeight.height = 35 + "px";
	}
	//点击事件
	function upOrDownCheck(){
		if(flag1){
			var treeStyle = document.getElementById("multFun").style;
			treeStyle.height = 0 + "px";
			$("#treeComm").animate({height:sizeMax+"px"});
			$("#address_list").animate({height:parseInt(sizeMax) - 140 + "px"});
			$("#treeComm_div").animate({height:parseInt(sizeMax) -50 +"px"});
			$("#upOrDown").attr('src','images/downto.png');
			flag1 = false;
		}else{
			var treeStyle = document.getElementById("treeComm").style;
			if(size < 35){
				treeStyle.height = 0 + "px";
				$("#multFun").animate({height:sizeMax+"px"});
			}
			else{
				//treeStyle.height = sizeMax - (67 * num) + "px";
				$("#multFun").animate({height:72 * num +"px"});
				$("#treeComm_div").animate({height:firstOutDiv +"px"});
				$("#address_list").animate({height:secondWithDiv +"px"});
			}
			$("#upOrDown").attr('src','images/upto.png');
			flag1 = true;
		}
	}

	$(function() {
		//获取action值
		var action = $("#action").val();
		//显示空调信息
		showConditioner();
		
		//读取消息
		//init();
		//boardInit();
		
		//setInterval(init,5000);
		//setInterval(boardInit,600000); //物业失物领取 1小时刷新一次
		//setInterval(showConditioner,180000);
		
		//读取未读传真数量
		$.getJSON(_ctx+"/message/queryFaxUnReadNum.act", function(json){
			var num = json.NUM;
			$("#faxNotReadNum").text('['+num+'条]');
			if(num > 0){
				$("#faxNotReadNum").css('color','red');
			}
		});
		
		//读取我的待办事项消息数量
  		$.getJSON("${ctx}/message/queryMyMatterCount.act", function(json){
			var num = json.NUM;
  			$("#matterCount").text('['+num+'条]');
  			if(num > 0)
  				$("#matterCount").css('color','red');
  		});
		
		//inits(); //初始化加载天气功能
		//showEnergy(); //初始化加载节能功能
		
		//点击浏览器返回按钮，根据action来定位之前点击的内容
		//$("#" + action + "Id").removeClass();
		//$("#" + action + "Id").addClass("on");
		$("#" + action + "Id").addClass("selected");
		
		if("queryMsgList" == action)
			loadMsg();
		if("queryMyCallList" == action)
			loadVisitor();
		if("queryMyMatter" == action)
			LoadMatter();
		if("queryFax" == action){
			loadFax();
		}
  	});
	
	//大厦指南事件
	function fun() {
		showGuide('640', '564', '${ctx}/app/guide/guide.html');
	}
</script>
</html>