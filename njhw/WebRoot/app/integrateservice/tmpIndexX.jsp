<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合办公首页</title>
<%@ include file="/common/include/meta.jsp" %>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<script src="${ctx}/app/integrateservice/js/util.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/newtree.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/IntegratedOffice.js" type="text/javascript"></script>

<link href="${ctx}/app/integrateservice/css/newtree.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/app/portal/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/scripts/ca/exporting.js"></script>

<!-- link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet" type="text/css" /> -->
<style type="text/css">
	.main_left_x1 a{
		cursor: pointer;
	}

	/***************节能START***********************/
	.jienen_titie{
		font-size:12px;
		width:39%;
		color:#7a7a7a;
	}
	.jienen_titie_unit{
		font-size:12px;
		width:29%;
		color:#7a7a7a;
	}
	.jienen_text{
		font-size:16px;
		color:#7d93bc;
		font-weight:bold;
	}
	.table_css{
		margin:5px 20px;
	}
	/***************节能END***********************/
</style>
</head>
<body>
<div class="main_index">
 
<jsp:include page="header.jsp" />

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
			<div class="main_conter1">
				<div class="clear"></div>
					<div class="main_left_kt" ><span id="room_temperature" class="main_left_kt" style="margin-right: 15px">26</span><span class="main_left_kt" style="font-size: 15px;font-weight: bold;padding-top:5px;">℃</span><br />
						<span class="main_kt_span">当前室温</span>
					</div>
					<div class="main_right_kt">
						<a class="mr_a1" onclick="controllerDevice('main_right1_img2_cool', 'conditioner', 'reduce');" href="javascript:void(0)"></a>
					<div class="mr_a2"><span id="conditioner_temperature" class="main_spans1">24</span><span class="main_spans2">℃</span></div><a class="mr_a1" onclick="controllerDevice('main_right1_img2_cool', 'conditioner', 'add');" href="javascript:void(0)"></a>
					<br/>
					<span class="main_kt_span"  style="float:left">空调温度</span>
				</div>
				<div class="clear"></div>
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
	  <ul>
	    <li><a class="none" id="queryMsgListId" onclick="UpAction('queryMsgList','queryMsgListId')"><span class="x1_main">通知</span><span class="x1_main2">未读<span id="msgNotReadNum" class="x1_main3">[0条]</span></span><span class="x1_main1">NOTIFICATION</span></a></li>
	    <li><a class="none" id="queryMyCallListId" onclick="UpAction('queryMyCallList','queryMyCallListId')"><span class="x1_main">访客</span><span class="x1_main2">申请中<span id="visitorNotReadCount" class="x1_main3">[0条]</span></span><span class="x1_main1">VISITORS</span></a></li>
	    <li><a class="none" id="queryMyMatterId" onclick="UpAction('queryMyMatter','queryMyMatterId')"><span class="x1_main">待办事项</span><span class="x1_main2">待办<span class="x1_main3" id="matterCount">[0条]</span></span><span class="x1_main1">BACKLOG</span></a></li>
	    <li><a class="none" id="queryFaxId" onclick="UpAction('queryFax','queryFaxId')"><span class="x1_main">传真</span><span class="x1_main2">未读<span id="faxNotReadNum" class="x1_main3">[0条]</span></span><span class="x1_main1">FACSIMILE</span></a></li>
	  	<input type="hidden" id="action" value="queryMsgList" />
	  </ul>
	</div>
	<div class="main_right_x1" style="position: relative;">
	  <div id="divMsgBox">
	  </div>
	  <div style="top:140px;left:310px;position: absolute;">
	  	<a style="cursor: pointer;" onclick="JumpUrl()"><img class="st_img1" src="images/t_4.jpg" width="72" height="17" border="0" /></a>
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
						<ul>
							<c:forEach items="${listBigMeatAM}" var="listBigMeatAMDate" varStatus="status">
								<c:if test="${status.index == 0 }">
									<div class="main_main2_img">
										<!-- 图片展示 -->
										<div class="img_main">
											<img src="images/index4_1.jpg" width="105" height="78" />
										</div>
										<p>
										<span class="main1_cx">${listBigMeatAMDate.FD_NAME }</span><br />
										<span class="main1_xz">四选一</span><br />
										<script type="text/javascript">
											var desc = '${listBigMeatAMDate.FD_DESC }';
											var arr = desc.split('，');
											var text = arr[0] + "<br>" + arr[1];
											document.write(text.substring(0,12) + "...");
										</script>
										
									</div>
								</c:if>
								<c:if test="${status.index == 1 }">
									<li>
										<p>
											<span class="main1_cx">${listBigMeatAMDate.FD_NAME }</span><br />
											<span class="main1_xz">四选一</span><br />
											<script type="text/javascript">
												var desc = '${listBigMeatAMDate.FD_DESC }';
												var arr = desc.split('，');
												var text = arr[0] + " " + arr[1] + "<br>";
												var texter = arr[2] + " " + arr[3];
												
												document.write(text.substring(0,12) + "<br>");
												if("undefined undefined" != texter)
													document.write(texter.substring(0,12) + "...");
											</script>
										</p>
									</li>
								</c:if>
								<c:if test="${status.index == 2 }">
									<li>
										<p>
											<span class="main1_cx">${listBigMeatAMDate.FD_NAME }</span><br />
											<span class="main1_xz">四选二</span><br />
											<script type="text/javascript">
												var desc = '${listBigMeatAMDate.FD_DESC }';
												var arr = desc.split('，');
												var text = arr[0] + " " + arr[1] + "<br>";
												var texter = arr[2] + " " + arr[3];
												document.write(text.substring(0,12) + "<br>");
												if("undefined undefined" != texter)
													document.write(texter.substring(0,12) + "...");
											</script>
										</p>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if>
					<div class="clear"></div>
				</div>
				<!-- 早餐选项结束 -->
				<!-- 午餐选项开始 -->
				<div class="main_main2" id="bmEat" style="display: block;">
					<c:if test="${empty listBigMeatNOON}">
						<span style="color: green;margin-top: 30px;">    午餐未发布   </span>  
		            </c:if>
		            <c:if test="${not empty listBigMeatNOON}">
						<ul>
							<c:forEach items="${listBigMeatNOON}" var="listBigMeatNOONDate" varStatus="status">
								<c:if test="${status.index == 0 }">
									<div class="main_main2_img">
										<!-- 图片展示 -->
										<div class="img_main">
										<!-- 
											<img src="${listBigMeatNOONDate.FD_PHOTO1 }" width="105" height="78" />
											 -->
											<img src="images/index4_1.jpg" width="105" height="78" />											
										</div>
										<p>
										<span class="main1_cx">${listBigMeatNOONDate.FD_NAME }</span><br />
										<span class="main1_xz">四选一</span><br />
										<script type="text/javascript">
											var desc = '${listBigMeatNOONDate.FD_DESC }';
											var arr = desc.split('，');
											var text = arr[0] + "<br>" + arr[1];
											document.write(text.substring(0,13) + "...");
										</script>
										</p>
									</div>
								</c:if>
								<c:if test="${status.index == 1 }">
									<li>
										<p>
											<span class="main1_cx">${listBigMeatNOONDate.FD_NAME }</span><br />
											<span class="main1_xz">四选一</span><br />
											<script type="text/javascript">
												var desc = '${listBigMeatNOONDate.FD_DESC }';
												var arr = desc.split('，');
												var text = arr[0] + " " + arr[1] + "<br>";
												var texter = arr[2] + " " + arr[3];
												document.write(text.substring(0,11) + "<br>");
												if("undefined undefined" != texter)
													document.write(texter.substring(0,10) + "...");
											</script>
										</p>
									</li>
								</c:if>
								<c:if test="${status.index == 2 }">
									<li>
										<p>
											<span class="main1_cx">${listBigMeatNOONDate.FD_NAME }</span><br />
											<span class="main1_xz">四选二</span><br />
											<script type="text/javascript">
												var desc = '${listBigMeatNOONDate.FD_DESC }';
												var arr = desc.split('，');
												var text = arr[0] + " " + arr[1] + "<br>";
												var texter = arr[2] + " " + arr[3];
												document.write(text.substring(0,11) + "<br>");
												if("undefined undefined" != texter)
													document.write(texter.substring(0,10) + "...");
											</script>
										</p>
									</li>
								</c:if>
							</c:forEach>
						</ul>
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
						<ul>
							<c:forEach items="${listBigMeatPM}" var="listBigMeatPMDate" varStatus="status">
								<c:if test="${status.index == 0 }">
									<div class="main_main2_img">
										<!-- 图片展示 -->
										<div class="img_main">
											<img src="images/index4_1.jpg" width="105" height="78" />
										</div>
										<p>
										<span class="main1_cx">${listBigMeatPMDate.FD_NAME }</span><br />
										<span class="main1_xz">四选一</span><br />
										<script type="text/javascript">
											var desc = '${listBigMeatPMDate.FD_DESC }';
											var arr = desc.split('，');
											var text = arr[0] + "<br>" + arr[1];
											document.write(text);
										</script>
										
									</div>
								</c:if>
								<c:if test="${status.index == 1 }">
									<li>
										<p>
											<span class="main1_cx">${listBigMeatPMDate.FD_NAME }</span><br />
											<span class="main1_xz">四选一</span><br />
											<script type="text/javascript">
												var desc = '${listBigMeatPMDate.FD_DESC }';
												var arr = desc.split('，');
												var text = arr[0] + " " + arr[1] + "<br>";
												var texter = arr[2] + " " + arr[3];
												document.write(text);
												if("undefined undefined" != texter)
													document.write(texter.substring(0,12) + "...");
											</script>
										</p>
									</li>
								</c:if>
								<c:if test="${status.index == 2 }">
									<li>
										<p>
											<span class="main1_cx">${listBigMeatPMDate.FD_NAME }</span><br />
											<span class="main1_xz">四选一</span><br />
											<script type="text/javascript">
												var desc = '${listBigMeatPMDate.FD_DESC }';
												var arr = desc.split('，');
												var text = arr[0] + " " + arr[1] + "<br>";
												var texter = arr[2] + " " + arr[3];
												document.write(text);
												if("undefined undefined" != texter)
													document.write(texter.substring(0,12) + "...");
											</script>
										</p>
									</li>
								</c:if>
							</c:forEach>
						</ul>
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
					<a href="${ctx}/app/cms/channelcms/newListArticle.act?mid=1" style="display: ${lostFoundTotal != 0 ? 'block' : 'none'}" href="javascript:void(0);"><img class="st_img1" src="images/t_4.jpg" width="72" height="17" border="0" /></a>
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
				<!--
				<script type="text/javascript">
					for(i=0;i<10;i++){
						var text = '<li class="main_nav"><a onclick="showShelter(\'670\',\'520\',\'${ctx}/app/prebyvisit/byVisitInput.act\');" ><img src="images/nav_01.jpg" width="118" height="86" border="0" /></a></li>';
						document.write(text);
					}
				</script>
				-->
				<li class="main_nav">
					<a title="收发传真" target="_self" href="${ctx}/app/fax/index.act">
						<img onmousemove="this.src='images/navPics/list_fest01_hover.jpg'" onmouseout="this.src='images/navPics/list_fest01.jpg'" src="images/navPics/list_fest01.jpg" src="images/navPics/list_fest01.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				<li class="main_nav">
					<a title="三维地图" target="_self" href="${ctx}/app/buildingmon/indexInit.act" >
						<img onmousemove="this.src='images/navPics/list_fest02_hover.jpg'" onmouseout="this.src='images/navPics/list_fest02.jpg'" src="images/navPics/list_fest02.jpg" width="109" height="67" border="0" />
					</a>
				</li>

				<li class="main_nav">
					<a style="cursor: pointer;" title="主动约访" onclick="showShelter('670','428','${ctx}/app/prebyvisit/byVisitInput.act');">
						<img onmousemove="this.src='images/navPics/list_fest03_hover.jpg'" onmouseout="this.src='images/navPics/list_fest03.jpg'" src="images/navPics/list_fest03.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				<li class="main_nav">
					<a title="电话管理" target="_self" href="${ctx}/app/personnel/telAndNumber/telAndNumber.act">
						<img onmousemove="this.src='images/navPics/list_fest12_hover.jpg'" onmouseout="this.src='images/navPics/list_fest12.jpg'"  src="images/navPics/list_fest12.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				<li class="main_nav">
					<a title="房间管理" target="_self" href="${ctx}/app/personnel/unit/roomDistributeNew.act?pageNo=1">
						<img onmousemove="this.src='images/navPics/list_fest09_hover.jpg'" onmouseout="this.src='images/navPics/list_fest09.jpg'"  src="images/navPics/list_fest09.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				<li class="main_nav">
					<a title="人员管理" target="_self" href="${ctx}/app/per/orgTreeUserCheckinFrame.act">
						<img onmousemove="this.src='images/navPics/list_fest06_hover.jpg'" onmouseout="this.src='images/navPics/list_fest06.jpg'"  src="images/navPics/list_fest06.jpg" width="109" height="67" border="0" />
					</a>
				</li>

				<li class="main_nav">
					<a title="通卡管理" target="_self" href="${ctx}/app/personnel/unit/playCardIndex.act">
						<img onmousemove="this.src='images/navPics/list_fest08_hover.jpg'" onmouseout="this.src='images/navPics/list_fest08.jpg'"  src="images/navPics/list_fest08.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				
				<li class="main_nav">
					<a title="发布通知" target="_self" href="${ctx}/common/bulletinMessage/msgBoxAction_init.act">
						<img onmousemove="this.src='images/navPics/list_fest05_hover.jpg'" onmouseout="this.src='images/navPics/list_fest05.jpg'"  src="images/navPics/list_fest05.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				
				<li class="main_nav">
					<a title="物业信息" target="_self" href="${ctx}/app/personnel/cusservice/index.act">
						<img onmousemove="this.src='images/navPics/list_fest25_hover.jpg'" onmouseout="this.src='images/navPics/list_fest26.jpg'"  src="images/navPics/list_fest26.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				
				<li class="main_nav">
					<a title="个人设置" target="_self" href="${ctx}/common/userOrgMgr/personInfoChreach.act">
						<img onmousemove="this.src='images/navPics/list_fest04_hover.jpg'" onmouseout="this.src='images/navPics/list_fest04.jpg'"  src="images/navPics/list_fest04.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				
				<li class="main_nav">
					<a style="cursor: pointer;" title="大厦指南" onclick="fun()">
						<img onmousemove="this.src='images/navPics/list_fest11_hover.jpg'" onmouseout="this.src='images/navPics/list_fest11.jpg'"  src="images/navPics/list_fest11.jpg" width="109" height="67" border="0" />
					</a>
				</li>
				<!--  onclick="showShelter('670','520','${ctx}/app/prebyvisit/byVisitInput.act');"  -->
			</ul>
		</div>
		<div class="clear" style="height: 0px;"></div>
		<div align="center" style="border: 0px solid red;height:10px;" onclick="upOrDownCheck()">
			<img id="upOrDown" src="images/upto.png" alt="" style="width: 100%;height:10px;bottom: 5px;"/>
		</div>
		
		<div class="main_left_main1" id="treeComm" style="display: block;">
		<div class="main_border_01">
		  <div class="main_border_02">通讯录</div>
		</div>
		<div class="main_conter" id="treeComm_div" style="height:366px;">
		<div class="main_right3">
				     <div class="clear"></div>
						<div class="main_right3_input">
				<input type="text" id="searchTJ" onfocus="inputFun(this, 0)" onblur="inputFun(this, 1)" value="请输入姓名或电话"  class="input_txt"/><input name="" class="input_button" type="button"  onclick="searchInfo()" id="search"/>
			</div>
			<div class="reportTips_tips1">
	            <div style="cursor: pointer;" class="tips1" id="bigTreeId" onclick="upClassTree('bigTreeId');tipsChange(this, 'tree')">大厦</div>
	            <div style="cursor: pointer;" class="tips2" id="treeUnitId" onclick="upClassTree('treeUnitId');tipsChange(this, 'treeUnit')">单位</div>
	            <div style="cursor: pointer;" class="tips3" id="pabListId" onclick="upClassTree('pabListId');tipsChange(this, 'pabList')">个人</div>
	            <script type="text/javascript">
	            	function upClassTree(id){
	            		if("bigTreeId" == id){
	            			$("#bigTreeId").attr('class','tips1');
	            			$("#treeUnitId").attr('class','tips2');
	            			$("#pabListId").attr('class','tips3');
	            		}else if("treeUnitId" == id){
	            			$("#bigTreeId").attr('class','tips2');
	            			$("#treeUnitId").attr('class','tips1');
	            			$("#pabListId").attr('class','tips3');
	            		}else if("pabListId" == id){
	            			$("#bigTreeId").attr('class','tips2');
	            			$("#treeUnitId").attr('class','tips3');
	            			$("#pabListId").attr('class','tips1');
	            		}
	            	}
	            	
	            	//大厦指南事件
	            	function fun() {

	            		showGuide('800', '705', _ctx + '/app/guide/guide.html');
	            	}
	            </script>
	      <div class="main_img2"><a href="javascript:void(0)" onclick="refreshAddList();" title="刷新" id="refresh_List" ><img src="images/shipin_2.jpg" width="15" height="9" /></a></div>
	        </div>
	        <div class="input_bg"></div>
			<div class="main_right3_more">
				<div class="main_right3_more_sz" id="peopleSet" style="display: none;float:left;margin-right:7px" onclick="showShelter('1010','410','${ctx}/app/paddressbook/init.act');" title="设置"></div>
				<div class="main_right3_more_fd" id="amplifyA" style="display: block;float:left" onclick="amplify();" title="放大"></div>
				<div class="refresh_main_right3_more" id="refresh_List" style="float:left" onclick="refreshAddList();" title="刷新"></div>
			</div>
			     <div class="clear"></div>
			</div>
	        <div class="close_search" id="close_search" title="清除" onclick="clear_search(this)"></div>
			<!--  <div class="search_span"><span class="num" onclick="searchInfo()" id="search"></span></div>-->
			<input type="hidden" id="showType" name="showType"/>
	
			<div class="address_list" id="address_list" style="height:580px;">
				<div id="tree"></div>
				<div id="treeUnit" style="display: none;"></div>
				<div id="pabList" style="display: none;"></div>
				<div id="searchList" style="display: none; overflow: auto"></div>
			</div>
		</div>
	</div>
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
var orgIdAddress = '${orgId}';
var typeAddress = '${type}'; //	"tel"|"fax"
var selfMacAddress = '${smac}';//	当前用户的ip电话MAC地址
var selfTelAddress = '${stel}';//	当前用户的ip电话号码
</script>
<script type="text/javascript">
		var flag1 = true; //上下切换变量
		var num = 10; //存放根据权限所显示的功能数量
		var bottomMin = 35;
		var sizeMax = 673;  //右边框总高度
		var o , t ;
		
		//判断当前功能有多少行
		if((parseInt(num % 3)) != 0)
			num = parseInt(num / 3) + 1;
		else
			num = parseInt(num / 3);
		
		var treeStyles = document.getElementById("multFun").style; //功能栏对象
		var hei = treeStyles.height;  //取得功能栏对象的高度
		var size = parseInt(sizeMax) - parseInt(72 * num); // 取得功能栏高度之外的高度
		var treeCommHeight = document.getElementById("treeComm").style; //树对象
		
		o = parseInt(size) - 49 ;      //取得外框高度
		t = parseInt(size) - 49 - 92 ; //取得树框高度
		
		//当size 小于 body高度 - 功能框高度
		if(size > 35){
			treeCommHeight.height = size + "px"; //动态获取通讯录高度
			$("#address_list").animate({height:t + "px"});  //动态获取树内部高度
			$("#treeComm_div").animate({height:o + "px"}); //动态获取树外部高度
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
					$("#treeComm_div").animate({height:o +"px"});
					$("#address_list").animate({height:t +"px"});
				}
				$("#upOrDown").attr('src','images/upto.png');
				flag1 = true;
			}
		}
		</script>

<script type="text/javascript">
	$(function() {
		showConditioner();
		init();
		
		//读取我的待办事项消息数量
  		$.getJSON("${ctx}/message/queryMyMatterCount.act", function(json){
			var num = json.NUM;
  			$("#matterCount").text('['+num+'条]');
  			if(num > 0)
  				$("#matterCount").css('color','red');
  		});
		
		inits(); //初始化加载天气功能
		//setInterval(init,1000);
		setInterval(showConditioner,180000);
		showEnergy(); //初始化加载节能功能
  	});

	function showConditioner(){
		$.getJSON("${ctx}/app/integrateservice/queryConditionerTemperature.act", function(json){
			var temperature = json.temperature;
			var conditionerTemperature = json.conditionerTemperature;
			var coolId = json.coolId;
		    $("#room_temperature").html(temperature);
		    $("#conditioner_temperature").html(conditionerTemperature);
		    $("#conditioner_temperature").attr("coolId",coolId);
  		});
    }
	function refectory() {
  		var url = "${ctx}/app/integrateservice/refectoryInformation.jsp";
  		//openEasyWin('winId','食堂视频',url,'660','500',false);
  		showShelter('660','510',url);
  	}
	
	function init(){
		//获取action值
		var action = $("#action").val();
		
		//读取未读消息数量
  		$.getJSON("${ctx}/message/queryMsgNotReadNum.act", function(json){
			var num = json.NUM;
  			$("#msgNotReadNum").text('['+num+'条]');
  			if(num > 0)
  				$("#msgNotReadNum").css('color','red');
  		});
		
		// 读取我的访客未读消息数量
  		$.getJSON("${ctx}/message/getVisitorNotReadCount.act", function(json){
			var num = json.NUM;
  			$("#visitorNotReadCount").text('['+num+'条]');
  			if(num > 0)
  				$("#visitorNotReadCount").css('color','red');
  		});
		
		//读取我的待办事项消息数量
		/*
  		$.getJSON("${ctx}/message/queryMyMatterCount.act", function(json){
			var num = json.NUM;
  			$("#matterCount").text('['+num+'条]');
  			if(num > 0)
  				$("#matterCount").css('color','red');
  		});*/
		
		//默认加载我的物业通知
  		loadBoard();
		
  		loadArticle();
		
		//默认加载内容
  		if(action == "queryMsgList")
  			loadMsg();
  		if(action == "queryMyCallList")
  			loadVisitor();
  		if(action == "queryMyMatter")
  			LoadMatter();
  		
  		//点击浏览器返回按钮，根据action来定位之前点击的内容
		$("#" + action + "Id").removeClass();
		$("#" + action + "Id").addClass("on");
  		
		//读取未读传真数量
  		$.getJSON("${ctx}/message/queryFaxUnReadNum.act", function(json){
			var num = json.NUM;
  			$("#faxNotReadNum").text('['+num+'条]');
  			if(num > 0)
  				$("#faxNotReadNum").css('color','red');
  		});
	}
  	
 	// 加载系统消息 
	function loadMsg() {
    	$.getJSON("${ctx}/message/queryMsgJSON.act", function(json){
    		$("div [id^='div_msg_']").remove();
    		if (json != null && json.length > 0) {
   				$.each(json,function(i){
   					if(i >=5 ) return;
   					var id= json[i].MSGID;
 					var	title = json[i].TITLE;
 					var time = json[i].T;
 					var status = json[i].STATUS;
 					var statusStr;
 					var maxLen = 15;
 					var classStr;
 					if(title.length >= maxLen){
 						var tmp =  title.substr(0, maxLen);
 						title = tmp + "...";
 					}
 					
 					if(status == 1){
 						statusStr = " 已读 ";
 						classStr = "main_spant1";
 					}else{
 						statusStr = " 未读 ";
 						classStr = "main3_list4";
 					}
 					
 					$("#divMsgBox").append("<li id='div_msg_msg"+i+"' onclick='shouNewInfo("+id+");'><a class='main_lefts2' href='#'>" + title  + "</a> <a class='main_lefts1' href='#'>" + time + "</a>&nbsp; <a class='"+classStr+"' href='#' id='notice"+id+"'>" + statusStr + "</a></li>");
				});
    		}//showInfo("+id+")
    	});
 	}
	
 	//load加载我的访客数据 5条
 	function loadVisitor(){
 		$.getJSON("${ctx}/message/queryVisitJSON.act", function(json){
 			$("div [id^='div_msg_']").remove();
 			if (json != null && json.length > 0) {
   				$.each(json,function(i){
   					if(i >=5 ) return;
   					var id= json[i].VS_ID;
   					var	username = json[i].VI_NAME;
 					var	starttime = json[i].VSST;
 					var status = json[i].VS_FLAG;
 					var statusStr;
 					var maxLen = 15;
 					var contentStr;
 					var classStr;
 					if(username.length >= maxLen){
 						var tmp =  username.substr(0, maxLen);
 						username = tmp + "...";
 					}
 					if(status == "00"){
 						statusStr = " 初始预约 ";
 						classStr = "main_spant1";
 					}
 					 if(status == "01"){
 						statusStr = " 预约申请 ";
 						classStr = "main3_list4";
 					}
 					 if(status == "02"){
 						statusStr = " 申请确认 ";
 						classStr = "main_spant1";
 					}
 					 if(status == "03"){
 						statusStr = " 申请拒绝 ";
 						classStr = "main_spant1";
 					}
 					 if(status == "04"){
 						statusStr = " 到访 ";
 						classStr = "main_spant1";
 					}
 					 if(status == "05"){
 						statusStr = " 正常结束";
 						classStr = "main_spant1";
 					}
 					 if(status == "06"){
 						statusStr = " 异常结束 ";
 						classStr = "main_spant1";
 					}
 					if(status == "99"){
 						statusStr = " 取消预约";
 						classStr = "main_spant1";
 					}
 					
 					contentStr = username +" 在 "+starttime+" 来访 ";
 					$("#divMsgBox").append('<li id="div_msg_Vis'+i+'" onclick="shouNewInfo1(\''+id+'\');"><a class="main_lefts3" href="javascript:void(0);">' + contentStr + '</a><a class="'+classStr+'" href="javascript:void(0);" id="ReadMyCalls'+id+'">' + statusStr + '</a></li>');
   				});
 			};
 		});
 	}
 	
 	//待办事项
 	function LoadMatter(){
 		$.getJSON("${ctx}/message/queryMatter.act", function(json){
 			$("div [id^='div_msg_']").remove();
 			if (json != null && json.length > 0) {
   				$.each(json,function(i){
   					if(i >=5 ) return;
   					var id= json[i].MessageItemGuid;
 					var	starttime = json[i].GenerateDate;
 					var title = json[i].Title;
 					var maxLen = 18;
 					if(title.length >= maxLen){
 						var tmp =  title.substr(0, maxLen);
 						title = tmp + "...";
 					}
 					$("#divMsgBox").append('<li id="div_msg_Vis'+i+'" onclick="shouNewInfo2(\''+id+'\');"><a class="main_db2" style="cursor:pointer;">' + title + '</a> <a class="main_lefts1" href="javascript:void(0);">' + starttime + '</a><a class="main_spant1" href="javascript:void(0);" id="notice'+id+'"></a></li>');
   				});
 			}
 		});
 	}
 	
 	//加载我的物业通知数据
 	function loadBoard(){
 		$.getJSON("${ctx}/common/bulletinMessage/msgBoardAction_queryBoardJSON.act", function(json){
 			if (json != null && json.length > 0) {
 				$("div [id^='board_msg']").remove();
   				$.each(json,function(i){
   					if(i >=4 ) return;
   					var id= json[i].MSGID;
 					var	starttime = json[i].MSGTIME;
 					var title = json[i].TITLE;
 					var maxLen = 18;
 					if(title.length >= maxLen){
 						var tmp =  title.substr(0, maxLen);
 						title = tmp + "...";
 					}
 					$("#boardNoti").append('<li id="board_msg'+i+'" class="mina2_h"><a style="cursor: pointer;" onclick="showBoard('+id+');" id="board_content_'+id+'">'+title+'</a><span style="float:right;color:#808080;">'+starttime+'</span></li>');
   				});
 			}
 		});
 	}
 	
 	//失物认领
 	function loadArticle(){
 		$.getJSON("${ctx}/app/personnel/cusservice/queryArticleJSON.act", function(json){
 			if (json != null && json.length > 0) {
 				$("div [id^='article_msg']").remove();
   				$.each(json,function(i){
   					if(i >=4 ) return;
   					var id= json[i].ID;
 					var	starttime = json[i].CREATETIME;
 					var title = json[i].TITLE;
 					var maxLen = 18;
 					if(title.length >= maxLen){
 						var tmp =  title.substr(0, maxLen);
 						title = tmp + "...";
 					}
 					$("#article").append("<li id='article_msg"+i+"' class='mina2_h'><a style='cursor: pointer;' onclick=\"showShelter('670','480','${ctx}/app/cms/channelcms/searchCmsDetail.act?id="+id+"','700','450',true);\">"+title+"</a><span style='float:right;color:#808080;'>"+starttime+"</span></li>");
   				});
 			}
 		});
 	}
 	
 	//显示我的物业通知详细信息
 	function showBoard(msgid) {
  		var url = "${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId="+msgid;
		//openEasyWinNotResizable('winId','物业通知',url,'800','450',false);
		showShelter('670','520',url);
  		$("#board_content_"+msgid).css("fontWeight", "");
  	}
	
 	//新版我的消息弹框效果
 	function shouNewInfo(msgId){
 		$("#notice"+msgId).html("已读");
 		var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
 		showShelter('700','520',url);
 		init();
 	}
 	
 	//我的访客新弹框
 	function shouNewInfo1(msgId){
 		var url = "${ctx}/app/myvisit/showOpt.act?vsid=" + msgId;
 		showShelter('670','520',url);
 		init();
 	}
 	
 	//我的待办事项新弹框
 	function shouNewInfo2(msgId){
 		var url = "${ctx}/message/queryMyMatterInfo.act?MessageItemGuid=" + msgId;
 		showShelter('670','520',url);
 		init();
 	}
 	
 	//显示我的消息详细页面
	function showInfo(msgId) {
		$("#notice"+msgId).html("已读");
		var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
		openEasyWin('winId','查看消息',url,'700','500',true);
		$("#content_"+msgId).css("fontWeight", "");
		init();
	}
	
	//改变更多连接跳转的Action
	function UpAction(action,Id){
		$("#action").attr('value',action);
		$("div [class='on']").removeClass("on").addClass("none");
		$("#"+Id).removeClass("none").addClass("on");
		if("queryMsgList" == action)
			loadMsg();
		if("queryMyCallList" == action)
			loadVisitor();
		if("queryMyMatter" == action)
			LoadMatter();
		if("queryFax" == action){
			url = "${ctx}/app/fax/index.act";
			//window.open(url);	
			window.location.href = url;
		}
	}
	
	//更多连接跳转
	function JumpUrl(){
		if(JumpUrl == "" || JumpUrl == null){
			$("#action").attr('value',"queryMsgList");
		}
		if($("#action").val() == "queryFax")
			return ;
		url = "${ctx}/message/"+$("#action").val() + ".act";
		window.location.href = url;
		//window.parent.location.href = url;
	}
	
	//显示我的访客详细页面
	function showVis(vsid){
		//$("#ReadMyCalls"+vsid).html("已读");
		var url = "${ctx}/app/myvisit/showOpt.act?vsid="+vsid;
		openEasyWin("winId","访问申请",url,"700","500",true);
		init();
	}
	
	//显示我的待办事项详细信息页面
	function showMatter(MessageItemGuid){
		var url = "${ctx}/message/queryMyMatterInfo.act?MessageItemGuid="+MessageItemGuid;
		openEasyWin("winId","待办事项",url,"700","500",true);
	}
	
	//点击放大我的节能功能
	function showLargeChart(){
		var url = "${ctx}/app/energyint/energyContribution.act";
		//openEasyWin('winId','我的节能贡献',url,'700','500',true);
		showShelter('670','500',url);
	}
	
	//节能显示功能
	function showEnergy(){
		$.ajax({
			type : 'POST',
			url : '${ctx}/app/energyint/energyContributionJson.act',
			data : {"dayType": false},
			dataType : 'JSON',
			async : true,
			success : function(data) {
				var highcharts_data = jQuery.parseJSON(data);
				$('#container').highcharts({
					chart : {
						type : 'line',
						width : 280,
						height : 180
					},
					exporting : {
						enabled : false
					},
					credits : {
						enabled : false
					},
					title : {
						text : ' ',
						x : -20
					//center
					},
					xAxis : {
						categories : highcharts_data.dayArray,
						max : highcharts_data.xAxisMax
					},
					yAxis : [{
						title : {
							text : '',
							style: {
								fontSize: '10px'
							}
						},
						plotLines : [{
							value : 0,
							width : 1,
							color : '#808080'
						}]
					},{ // Secondary yAxis
						labels: {
		                    format: '{value}',
		                    style: {
		                        color: '#4572A7'
		                    }
		                },
		                title: {
		                    text: '',
		                    style: {
								fontSize: '10px'
		                    }
		                },
		                opposite: true
		            }],
					tooltip : {
						enabled: false
					},
					plotOptions: {
				        line: {
				            lineWidth: 1.0,
				            fillOpacity: 0.0,
							marker: {
				                enabled: false,
				                states: {
				                    hover: {
				                        enabled: false
				                    }
				                }
				            },
				            shadow: false
				        }
				    },
					legend : {
						layout : 'horizontal',
						align : 'left',
						x : 20,
						verticalAlign : 'top',
						y : -10,
						floating : true,
						backgroundColor : '#FFFFFF',
						borderColor : '#FFFFFF',
						style: {
							fontSize: '10px'
						}
					},
					series : [{
						name : '大厦平均能耗',
						color: '#058DC7',
						data : highcharts_data.buildingArray,
						tooltip: {
		                    valueSuffix: ' 吨标准煤'
		                }
					}, {
						name : '我的能耗',
						color: '#50B432',
						data : highcharts_data.myArray,
						tooltip: {
		                    valueSuffix: ' 吨标准煤'
		                }
					},{
		                name: '大厦平均电耗',
		                color: '#ED561B',
		                type: 'line',
		                yAxis: 1,
		                data: highcharts_data.buildingKwhArray,
		                tooltip: {
		                    valueSuffix: ' KWH'
		                }
		    
		            },{
		                name: '我的电耗',
		                color: '#A757A8',
		                type: 'line',
		                yAxis: 1,
		                data: highcharts_data.myKwhArray,
		                tooltip: {
		                    valueSuffix: ' KWH'
		                }
		    
		            }]
				});
				
				$("#table_energy_id").append(
					"<tr>"+
						"<td class='jienen_titie'>大厦本月平均能耗</td>"+
					    "<td class='jienen_text'>"+highcharts_data.averageEnergy+"</td>"+
					    "<td class='jienen_titie_unit'>吨标准煤</td>"+
				    "</tr>"+
					"<tr>"+
					    "<td class='jienen_titie'>我的本月能耗</td>"+
					    "<td class='jienen_text'>"+highcharts_data.energyCurrentMonth+"</td>"+
					    "<td class='jienen_titie_unit'>吨标准煤</td>"+
					"</tr>"+
					"<tr>"+
					    "<td class='jienen_titie'>我的今日能耗</td>"+
					    "<td class='jienen_text'>"+highcharts_data.energyToday+"</td>"+
					    "<td class='jienen_titie_unit'>吨标准煤</td>"+
					"</tr>"+
					"<tr>"+
					    "<td class='jienen_titie'>本月节能贡献</td>"+
					    "<td class='jienen_text'>"+highcharts_data.energyContribution+"</td>"+
					    "<td class='jienen_titie_unit'>吨标准煤</td>"+
					"</tr>"
			 	);
			},
			error : function(msg, status, e) {
			}
		});
	}
	
	//通讯录
	$(function() {
		$(".tips1").click();
	});
	
	var fn3;
	var mobilenum = "", pname="", info = "", type="";
	
	function refreshAddList() {
		var id = $("#showType").val();
		$("#"+id).children().remove();
		
        var tv = new treeview("","");
        var url = "", orgId = 0;
        if (id == "tree" ) {				// 大厦
	        tv.create("tree");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        loadtree(url, tv, orgId);
        } else if (id == "treeUnit") { 		// 部门 
        	tv.create("treeUnit");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        orgId = orgIdAddress;
	        loadtree(url, tv, orgId);
        } else if (id == "pabList") {		// 个人
        	tv.create("pabList");
	        url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
	        loadaddresstree(url, tv, 0);
        }
        
        //setTimeout("initBindTag()", 1000);
	}
	
	function amplify() {
		var url = "${ctx}/app/integrateservice/amplify.act";	//?showType="+$("#showType").val();
		parent.open_main3('10260' ,'通讯录' ,url);
	}
  	
    // 加载大厦/部门树节点
    function loadtree(url, tv, orgId) {
    	
    	var firsturl = "";
    	if (orgId >= 0) firsturl = url + orgId;
    	else firsturl = url;
    	
        $.getJSON(firsturl, function(json){
	        var len = json.sonOrgList.length;
	        for(var i = 0; i < len; i++) {
	            var id = json.sonOrgList[i].ORG_ID;
	            var text = json.sonOrgList[i].NAME.replace(/(^\s*)|(\s*$)/g, "");
	            
	            var node1 = new node(id, text, "");
	            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
	        
	        // 加载用户
	        var perlen = json.sonPersonList.length;
	        for(var i = 0; i < perlen; i++) {
	            var id = json.sonPersonList[i].USERID;
	            var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var fax = json.sonPersonList[i].UEP_FAX ? json.sonPersonList[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var unitInfo = id+"_"+name+"_"+mobile;
	            
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if (typeAddress == "tel") {
		           if ($("#showType").val() == "treeUnit") {
	            		if (mobile != "") {
			           		text = text + "<div><a href='#' info="+unitInfo+"  phoneNum="+mobile+"  pname="+name+" type='unit' onclick='dialing(&quot;"+mobile+"&quot;, &quot;"+name+"&quot;)'>"+mobile+"</a>(手机)</div>";
							title = title + "　手机:" + mobile;
			            }
	            	}
		            if (tel != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+tel+"&quot;, &quot;"+name+"&quot;)'>"+tel+"</a>(电话)</div>";
						title = title + "　电话:" + tel;
		            }
		            if (fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+fax+"&quot;, &quot;"+name+"&quot;)'>"+fax+"</a>(传真)</div>";
						title = title + "　传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+web_fax+"&quot;, &quot;"+name+"&quot;)'>"+web_fax+"</a>(传真)</div>";
						title = title + "　网络传真:" + web_fax;
		            }

	            } else if (typeAddress == "fax") {
	            	if (fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+name+"&quot;,&quot;"+tel+"&quot;,&quot;"+fax+"&quot;)'>"+fax+"</a>(传真)</div>";
		           		title = title + "　传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+web_fax+"&quot;,&quot;"+tel+"&quot;,&quot;"+web_fax+"&quot;)'>"+web_fax+"</a>(传真)</div>";
		           		title = title + "　网络传真:" + fax;
		            }
	            }
	            text = text + "</td></tr></table>";
	            
	            var node1 = new node(id, text, "", "true", title);
	            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
        });
    }
    
    // 加载大厦/部门节点信息
    function loadnode(url, Pnode) {
     	var firsturl = url + Pnode.id;
	    $.getJSON(firsturl, function(json){
	        // 加载组织
	        var orglen = json.sonOrgList.length;
	        for(var i = 0; i < orglen; i++) {
	            var id = json.sonOrgList[i].ORG_ID;
	            var text = json.sonOrgList[i].NAME.replace(/(^\s*)|(\s*$)/g, "");
	            
	            var node1 = new node(id, text, "");
	            node1.dom.style.display = "block";
	            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
	            var loadnode = new node("", "请等待...", "");
	            node1.add(loadnode);
	            Pnode.add(node1);
	        }
	        // 加载用户
	        var perlen = json.sonPersonList.length;
	        for(var i = 0; i < perlen; i++) {
	        
	            var id = json.sonPersonList[i].USERID;
	            var name = json.sonPersonList[i].DISPLAY_NAME ? json.sonPersonList[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var mobile = json.sonPersonList[i].UEP_MOBILE ? json.sonPersonList[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var tel = json.sonPersonList[i].TEL ? json.sonPersonList[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var fax = json.sonPersonList[i].UEP_FAX ? json.sonPersonList[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var web_fax = json.sonPersonList[i].WEB_FAX ? json.sonPersonList[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var unitInfo = id+"_"+name+"_"+mobile;
	            
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if (typeAddress == "tel") {
		           if ($("#showType").val() == "treeUnit") {
	            		if (mobile != "") {
			           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' class='tooltip' info="+unitInfo+"  phoneNum="+mobile+"  pname="+name+" type='unit' style='text-decoration: none' onclick='dialing(&quot;"+mobile+"&quot;, &quot;"+name+"&quot;)'>"+mobile+"</a>(手机)</div>";
							title = title + "　手机:" + mobile;
			            }
	            	}
		            if (tel != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+tel+"&quot;, &quot;"+name+"&quot;)'>"+tel+"</a>(电话)</div>";
						title = title + "　电话:" + tel;
		            }
		            if (fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+fax+"&quot;, &quot;"+name+"&quot;)'>"+fax+"</a>(传真)</div>";
						title = title + "　传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+web_fax+"&quot;, &quot;"+name+"&quot;)'>"+web_fax+"</a>(传真)</div>";
						title = title + "　网络传真:" + web_fax;
		            }

	            } else if (typeAddress == "fax") {
	            	if (fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+name+"&quot;,&quot;"+tel+"&quot;,&quot;"+fax+"&quot;)'>"+fax+"</a>(传真)</div>";
		           		title = title + "　传真:" + fax;
		            }
		            if (web_fax != "") {
		           		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='sendFax(&quot;"+web_fax+"&quot;,&quot;"+tel+"&quot;,&quot;"+web_fax+"&quot;)'>"+web_fax+"</a>(传真)</div>";
		           		title = title + "　网络传真:" + fax;
		            }
	            }
	            text = text + "</td></tr></table>";
	            var node1 = new node(id, text, "", "true", title);
	            node1.dom.style.display = "block";
	            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
	            Pnode.add(node1);
	        }
	        Pnode.childNode[0].removeNode();
	        // 没有子节点，则移除展开图标以及单击和双击事件
	        if(orglen == 0 && perlen == 0) {
	            Pnode.SetSingle();// 设置该节点没有子节点
	        }
        });
    }
    
    // 加载个人通讯录树节点
    function loadaddresstree(url, tv, gId) {
    	var firsturl = "";
    	if (gId >= 0) firsturl = url + gId;
    	else firsturl = url;
    	
        $.getJSON(firsturl, function(json){
	        var len = json.groupList.length;
	        for(var i = 0; i < len; i++) {
	            var id = json.groupList[i].NAG_ID;
	            var text = json.groupList[i].NAG_NAME.replace(/(^\s*)|(\s*$)/g, "");
	            
	            var node1 = new node(id, text, "");
	            var loadnode = new node("", "请等待...", "", "true");// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
	        
	        // 加载组内人员
	        var orglen = json.personList.length;
	        for(var i = 0; i < orglen; i++) {
	            var id = json.personList[i].NUA_ID;
	            var name = json.personList[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "");
	            var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var addressInfo = "";
	            
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if (phone != "") {		// 多电话循环
	            	if (phone.indexOf(',') > 0) {
	            		var phoneList = phone.split(',');
	            		for ( var j = 0; j <  phoneList.length; j++) {
	            			addressInfo = id+"-"+phoneList[j]+"_"+name+"_"+phoneList[j];
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' class='tooltip' phoneNum="+phoneList[j]+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phoneList[j]+"&quot;, &quot;"+name+"&quot;)'>"+phoneList[j]+"</a>(手机)</div>";
							title = title + "　手机:" + phoneList[j];
						}
	            	} else {
	            		addressInfo = id+"_"+name+"_"+phone;
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);'  class='tooltip' phoneNum="+phone+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phone+"&quot;, &quot;"+name+"&quot;)'>"+phone+"</a>(手机)</div>";
						title = title + "　手机:" + phone;
	            	}
	            }
	            if (telHome != "") {
	            	if (telHome.indexOf(',') > 0) {
	            		var telHomeList = telHome.split(',');
	            		for ( var j = 0; j <  telHomeList.length; j++) {
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telHomeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telHomeList[j]+"</a>(住宅)</div>";
							title = title + "　住宅:" + telHomeList[j];
						}
	            	} else {
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telHome+"&quot;, &quot;"+name+"&quot;)'>"+telHome+"</a>(住宅)</div>";
	           			title = title + "　住宅:" + telHome;
	            	}
	            }
	            if (telOffice != "") {
	            	if (telOffice.indexOf(',') > 0) {
	            		var telOfficeList = telOffice.split(',');
	            		for ( var j = 0; j <  telOfficeList.length; j++) {
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+telOfficeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOfficeList[j]+"</a>(办公)</div>";
							title = title + "　办公:" + telOfficeList[j];
						}
	            	} else {
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOffice+"&quot;, &quot;"+name+"&quot;)'>"+telOffice+"</a>(办公)</div>";
	           			title = title + "　办公:" + telOffice;
	            	}
	            }
	            if (telOther != "") {
	            	if (telOther.indexOf(',') > 0) {
	            		var telOtherList = telOther.split(',');
	            		for ( var j = 0; j <  telOtherList.length; j++) {
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOtherList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOtherList[j]+"</a>(其他)</div>";
							title = title + "　其他:" + telOtherList[j];
						}
	            	} else {
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOther+"&quot;, &quot;"+name+"&quot;)'>"+telOther+"</a>(其他)</div>";
	           			title = title + "　其他:" + telOther;
	            	}
	            }
	            text = text + "</td></tr></table>";
	            
	            var node1 = new node(id, text, "", "true", title);
	            var loadnode = new node("", "请等待...", "", "true");	// 不能展开
	            node1.add(loadnode);
	            tv.add(node1);
	        }
        });
    }
    
    // 加载个人通讯录节点信息
    function loadaddressnode(url, Pnode) {
        var firsturl = url + Pnode.id;
	    $.getJSON(firsturl, function(json){
	        // 加载组内人员
	        var orglen = json.personList.length;
	        for(var i = 0; i < orglen; i++) {
	            var id = json.personList[i].NUA_ID;
	            var name = json.personList[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "");
	            var phone = json.personList[i].NUA_PHONE ? json.personList[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telHome = json.personList[i].NUA_TEL1 ? json.personList[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOffice = json.personList[i].NUA_TEL2 ? json.personList[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var telOther = json.personList[i].NUA_TEL3 ? json.personList[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
	            var addressInfo = "";
	            var text = "<table width='100%'><tr><td width='20%'><span>"+name+"</span></td><td width='80%' style='color: #7d7d7d;'>", title=name;
	            if (phone != "") {		// 多电话循环
	            	if (phone.indexOf(',') > 0) {
	            		var phoneList = phone.split(',');
	            		for ( var j = 0; j <  phoneList.length; j++) {
	            			addressInfo = id+"-"+phoneList[j]+"_"+name+"_"+phoneList[j];
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' class='tooltip' phoneNum="+phoneList[j]+" info="+addressInfo+"  pname="+name+" type='address' style='text-decoration: none' onclick='dialing(&quot;"+phoneList[j]+"&quot;, &quot;"+name+"&quot;)'>"+phoneList[j]+"</a>(手机)</div>";
							title = title + "　手机:" + phoneList[j];
						}
	            	} else {
	            		addressInfo = id+"_"+name+"_"+phone;
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);'  class='tooltip' phoneNum="+phone+" info="+addressInfo+"  pname="+name+" type='address'  style='text-decoration: none' onclick='dialing(&quot;"+phone+"&quot;, &quot;"+name+"&quot;)'>"+phone+"</a>(手机)</div>";
						title = title + "　手机:" + phone;
	            	}
	            }
	            if (telHome != "") {
	            	if (telHome.indexOf(',') > 0) {
	            		var telHomeList = telHome.split(',');
	            		for ( var j = 0; j <  telHomeList.length; j++) {
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telHomeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telHomeList[j]+"</a>(住宅)</div>";
							title = title + "　住宅:" + telHomeList[j];
						}
	            	} else {
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telHome+"&quot;, &quot;"+name+"&quot;)'>"+telHome+"</a>(住宅)</div>";
	           			title = title + "　住宅:" + telHome;
	            	}
	            }
	            if (telOffice != "") {
	            	if (telOffice.indexOf(',') > 0) {
	            		var telOfficeList = telOffice.split(',');
	            		for ( var j = 0; j <  telOfficeList.length; j++) {
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOfficeList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOfficeList[j]+"</a>(办公)</div>";
							title = title + "　办公:" + telOfficeList[j];
						}
	            	} else {
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOffice+"&quot;, &quot;"+name+"&quot;)'>"+telOffice+"</a>(办公)</div>";
	           			title = title + "　办公:" + telOffice;
	            	}
	            }
	            if (telOther != "") {
	            	if (telOther.indexOf(',') > 0) {
	            		var telOtherList = telOther.split(',');
	            		for ( var j = 0; j <  telOtherList.length; j++) {
							text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOtherList[j]+"&quot;, &quot;"+name+"&quot;)'>"+telOtherList[j]+"</a>(其他)</div>";
							title = title + "　其他:" + telOtherList[j];
						}
	            	} else {
	            		text = text + "<div style='float:left; width:125px;'><a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+telOther+"&quot;, &quot;"+name+"&quot;)'>"+telOther+"</a>(其他)</div>";
	           			title = title + "　其他:" + telOther;
	            	}
	            }
	            text = text + "</td></tr></table>";
	            
	            var node1 = new node(id, text, "", "true", title);
	            node1.dom.style.display = "block";
	            node1.dom.style.width = Pnode.dom.offsetWidth - 20 + "px";
	            var loadnode = new node("", "请等待...", "", "true");
	            node1.add(loadnode);
	            Pnode.add(node1);
	        }
	        Pnode.childNode[0].removeNode();
	        // 没有子节点，则移除展开图标以及单击和双击事件
	        if(orglen == 0) {
	            Pnode.SetSingle();// 设置该节点没有子节点
	        }
        });
    }
    
    function dialing(called, pname) {
  		var url = "${ctx}/app/integrateservice/inputCallPhone.act?selfMac="+selfMacAddress+"&selfTel="+selfTelAddress+"&called="+called+"&pname="+pname;
		openEasyWinNotResizable("winId","拨号确认",url,"400","170",true);
    }
    
    function sendMsg(info, name, type) {
  		//showShelter('350','600','${ctx}/app/messagingplatform/messagingPlatformInit.act?mobile='+called+"&name="+name);
  		var url = "${ctx}/app/sendsms/inputSMS.act?info="+info+"&name="+name+"&type="+type;
		openEasyWin("winId","发送短信",url,"650","400",true);
  	}
    
    function callback(node) {
	    // 子节点对象集合
	    if(node.childNode.length == 1 && node.childNode[0].text == "请等待...") {
	    	if(document.getElementById("pabList").style.display != "none") {
			    var url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
			    loadaddressnode(url, node);
		    } else {
		    	var url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
			    loadnode(url, node);
		    }
	    }
	    //setTimeout("initBindTag()", 2000);
    }
    
    $(document).keyup(function(event){
		  if(event.keyCode ==13){
		    $("#search").trigger("click");
		  }
	});
    
  	function searchInfo(){
  		var showType = $("#showType").val();
  		var tjVal = $("#searchTJ").val().replace(/(^\s*)|(\s*$)/g, "");
  		if (tjVal == "" || tjVal == "请输入姓名或电话") {
  			return;
  		} else {
  			$("#address_list").children("div").hide();
        	$("#searchList").show();
        	$("#close_search").show();
  			$("#searchList").children().remove();  			
  			var url = "", orgId = 0;
  			
  			if (showType == "pabList") 
  				url = "${ctx}/app/integrateservice/queryAddress.act";	// 个人
  			else if (showType == "treeUnit") {
  				url = "${ctx}/app/integrateservice/queryContact.act";	// 部门
  				orgId = orgIdAddress;
  			} else if (showType == "tree") {
  				url = "${ctx}/app/integrateservice/queryContact.act";	// 大厦
  			}
  			
	  		$.ajax({
				type: "POST",
				url: url,
				data: {"tjVal": tjVal, "type": showType, "orgId": orgId},
				dataType: 'json',
				async : false,
				success: function(json){
					if(json.length > 0) {
						$("#searchList").append("<table style='margin-left:5px; width:500px;' cellspacing='20' id='contact_tab'></table>");
						if (showType == "pabList") {
							$("#contact_tab").append("<tr><td>姓名</td><td>手机</td><td>办公</td><td>住宅</td></tr>");
						} else if (showType == "treeUnit") {
							$("#contact_tab").append("<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td><td>手机</td></tr>");
						} else if (showType == "tree") {
							$("#contact_tab").append("<tr><td>单位</td><td>姓名</td><td>电话</td><td>传真</td><td>网络传真</td></tr>");
						}
						
						var name, num1, num2, num3, num2web, _info;
						$.each(json, function(i) {
							var text1 = "", text2 = "", text3 = "", text4 = "";
							if (showType == "pabList") {
								name = json[i].NUA_NAME ? json[i].NUA_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
								
				            	num1 = json[i].NUA_PHONE ? json[i].NUA_PHONE.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num1.indexOf(',') > 0)	text1 = num1.substring(0,11)+"...";
				            	else {
				            		_info = json[i].NUA_ID+"_"+name+"_"+num1;
				            		text1 = "<a href='javascript:void(0);'  class='tooltip'  phoneNum="+num1+" info="+_info+" type='address'  pname="+name+" style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;, &quot;"+name+"&quot;)'>"+num1+"</a>";
				            	}
				            	
				            	num2 = json[i].NUA_TEL2 ? json[i].NUA_TEL2.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num2.indexOf(',') > 0)	text2 = num2.substring(0,11)+"...";
				            	else text2 = "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;, &quot;"+name+"&quot;)'>"+num2+"</a>";
				            	
				            	num3 = json[i].NUA_TEL1 ? json[i].NUA_TEL1.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	if (num3.indexOf(',') > 0)	text3 = num3.substring(0,11)+"...";
				            	else text3 = "<a href='javascript:void(0);' style='text-decoration: none' onclick='dialing(&quot;"+num3+"&quot;, &quot;"+name+"&quot;)'>"+num3+"</a>";
				            	
				            	//num4 = json[i].NUA_TEL3 ? json[i].NUA_TEL3.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	//if (num4.indexOf(',') > 0)	num4 = num4.substring(0,11)+"...";
				            	
				            	$("#contact_tab").append("<tr><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text3+"</td></tr>");
							} else if (showType == "treeUnit") { 
								unitname = json[i].DEPARTMENT_NAME ? json[i].DEPARTMENT_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";	// 本单位下的部门名称
								name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
				           	 	num1 = json[i].TEL ? json[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2 = json[i].UEP_FAX ? json[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2web = json[i].WEB_FAX ? json[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num3 = json[i].UEP_MOBILE ? json[i].UEP_MOBILE.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	_info = json[i].NUA_ID+"_"+name+"_"+num3;
				            	
				            	text1 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;, &quot;"+name+"&quot;)'>"+num1+"</a>";
				            	text2 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;, &quot;"+name+"&quot;)'>"+num2+"</a>";
				            	text4 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2web+"&quot;, &quot;"+name+"&quot;)'>"+num2web+"</a>";
				            	text3 = "<a href='javascript:void(0);'  class='tooltip' phoneNum="+num3+"  info="+_info+" type='unit'  pname="+name+" style='text-decoration: none' onclick='dialing(&quot;"+num3+"&quot;, &quot;"+name+"&quot;)'>"+num3+"</a>";
				            	
				            	$("#contact_tab").append("<tr><td>"+unitname+"</td><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text4+"</td><td>"+text3+"</td></tr>");
							} else if (showType == "tree") { 
								unitname = json[i].ORG_NAME ? json[i].ORG_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";		// 一级单位
								name = json[i].DISPLAY_NAME ? json[i].DISPLAY_NAME.replace(/(^\s*)|(\s*$)/g, "") : "";
				           	 	num1 = json[i].TEL ? json[i].TEL.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2 = json[i].UEP_FAX ? json[i].UEP_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	num2web = json[i].WEB_FAX ? json[i].WEB_FAX.replace(/(^\s*)|(\s*$)/g, "") : "";
				            	
				            	text1 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num1+"&quot;, &quot;"+name+"&quot;)'>"+num1+"</a>";
				            	text2 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2+"&quot;, &quot;"+name+"&quot;)'>"+num2+"</a>";
				            	text4 = "<a href='javascript:void(0);'  style='text-decoration: none' onclick='dialing(&quot;"+num2web+"&quot;, &quot;"+name+"&quot;)'>"+num2web+"</a>";
				            	
				            	$("#contact_tab").append("<tr><td>"+unitname+"</td><td>"+name+"</td><td>"+text1+"</td><td>"+text2+"</td><td>"+text4+"</td></tr>");
							}
						});
					}
				 },
				 error: function(msg, status, e) {
					 easyAlert("提示信息", "操作出错","info");
				 }
			 });
			 //setTimeout("initBindTag()", 1000);
		 }
  	}
	
    // 查询输入框
    function inputFun(dom, num) {
        if(num == 0) dom.value = "";
        if(num == 1 && dom.value == "") dom.value = "请输入姓名或电话";
    }
    
    // 清除按钮
    function clear_search(dom) {
    	var st = $("#showType").val();	// 退出显示最后一次显示的标签页
    	$("#"+st).show();
        document.getElementById("searchList").style.display = "none";
        document.getElementById("searchTJ").value = "请输入姓名或电话";
        dom.style.display = "none";
    }
	
	function tipsChange(dom, id) {
		//alert(dom.className);
        dom.parentNode.className = 'reportTips_' + dom.className;
        if(dom.className == "tips3") {
            document.getElementById("peopleSet").style.display = "block";
        } else {
            document.getElementById("peopleSet").style.display = "none";
        }
        
        $("#address_list").children("div").hide();
        $("#"+id).show();
        //$("#searchType").val(type);
        $("#showType").val(id);
        
        // 加载数据
        var tv = new treeview("","");
        var url = "", orgId = 0;
        if (id == "tree" && document.getElementById("tree").innerHTML == "") {						// 大厦
	        tv.create("tree");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        loadtree(url, tv, orgId);
        } else if (id == "treeUnit" && document.getElementById("treeUnit").innerHTML == "") { 		// 部门
        	tv.create("treeUnit");
	        url = "${ctx}/app/integrateservice/loadSonInfo.act?orgId=";
	        orgId = orgIdAddress;
	        loadtree(url, tv, orgId);
        } else if (id == "pabList" && document.getElementById("pabList").innerHTML == "") {			// 个人
        	tv.create("pabList");
	        url = "${ctx}/app/integrateservice/loadAddressInfo.act?gId=";
	        loadaddresstree(url, tv, 0);
        }
    }
    
    function controllerDevice(idPrefix, deviceType, optType){
        var temperature = $("#conditioner_temperature").html();
        var coolId =  $("#conditioner_temperature").attr("coolId");
		$.ajax({
			type: "POST",
			url: "${ctx}/app/integrateservice/controllerDevice.act",
			data: {"deviceType": deviceType, "optType": optType,"temperature": temperature,"coolId":coolId},
			dataType: 'text',
			async : false,
			success: function(msg){
				  if(msg == "success") {
				  	if (deviceType == "door") {
					  	$("#"+idPrefix+"_on").hide();
		  				$("#"+idPrefix+"_off").show();
				  		setTimeout("close_door()", 1000);	// 开门1秒之后关门
				  	}else if(deviceType == "conditioner"){
                        if(optType == "add"){
                        	temperature = parseFloat(temperature) + 0.5;
                        }else{
                        	temperature = parseFloat(temperature) - 0.5;
                        }
                        $("#conditioner_temperature").html(temperature);
					}else {
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
    
    function logout(){
    	var url ="${ctx}/j_spring_security_logout" + '?t=' + Math.random();
    	window.open(url, "_self");
    }
</script>
</html>