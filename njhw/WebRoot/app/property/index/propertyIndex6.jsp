<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>物业首页</title>
		
		<%@ include file="/common/include/meta.jsp"%>
		<script src="${ctx}/app/integrateservice/js/newtree.js" type="text/javascript"></script>
		<link href="${ctx}/app/integrateservice/css/newtree.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript">
	function toSuggest() {
        var url = "${ctx}/app/suggest/initProperty.act?type=A";
        window.location.href = url;
    }	
		
    function toConferenceList() {
        var url = "${ctx}/app/pro/showConferencesIndex.act?type=A";
        window.location.href = url;
    }
    
    function toAttendanceSchedule() {
        var url = "${ctx}/app/pro/attendanceScheduleIndex.act?type=A";
        window.location.href = url;
    }
    
    function toInfoList() {
		var url = "${ctx}/common/bulletinMessage/msgIndex.act?type=A";
		window.location.href = url;
	}		

	function toRepairList() {
		var url = "${ctx}/app/pro/repairIndex.act?type=A";
		window.location.href = url;
	}
	
	function toStorageList() {
	    var url = "${ctx}/app/pro/storageIndex.act?type=A";
		window.location.href = url;
	}
	
	function toLostFoundList() {
	    var url = "${ctx}/app/pro/lostFoundIndex.act?type=A";
		window.location.href = url;
	}
	
	function toPatrolList(){
	    var url = "${ctx}/app/pro/patrolIndex.act?type=A";
		window.location.href = url;
	}
	
	function toPackingList(){
	    var url = "${ctx}/app/pro/parkingIndex.act?type=A";
		window.location.href = url;
	}
	
	function toVisitorList(){
	    var url = "${ctx}/app/visitor/frontReg/noOrderInput.act?type=A";
		window.location.href = url;
	}
	
	function toComplaintList(){
	    var url = "${ctx}/app/pro/complaintsIndex.act?type=A";
		window.location.href = url;
	}
	
	function toViewConference(conferenceId) {
		var url = "${ctx}/app/pro/queryConferenceById.act?conferenceId="+conferenceId;
		openEasyWin("viewConference", "会议预定详细", url, "800", "570", false);
	}
	
	
	function toViewRepair(repairId) {
		var url = "${ctx}/app/pro/toViewRepair.act?repairId=" + repairId;
		openEasyWin("viewRepair", "物业报修详细", url, "800", "570", false);
	}
	
	function toViewInfo(msgId){
	    showShelter('800','500','${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId='+msgId);
	}

	function toViewLostFound(lostFoundId){
		showShelter('700','500','${ctx}/app/pro/viewLostFoundById.act?lostFoundId='+lostFoundId);
	}
	
	function toViewEnergy(repairId) {
		var url = "${ctx}/app/energyint/initEnergy.act";
		window.location.href = url;
	}

	function initNavi(i){
		//$("#navi" + selected).removeClass("selected");
		//selected = i;
		//$("#navi" + selected).addClass("selected");
		var url = "";
		if (i == 8) {
			url = "${ctx}/app/personnel/unit/playCardAllIndex.act";
		}else if (i == 11) {
			url = "${ctx}/app/personnel/property/queryNjhwAccess_kezhangb2.act";
	        window.location.href = url;
		}else if (i == 12) {
			url = "${ctx}/app/per/orgTreeUserCheckinFrameOther.act";
		}
		//$("#njhwAdmin").attr("src",url);
	}
	
</script>
	</head>

	<body > 
	<div class="main_index">
			
            <jsp:include page="/app/integrateservice/header.jsp"></jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div class="fkdj_index">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						物业客户服务
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 320px">
				<div class="mian_list_left">
					<div class="khfu_div">
						<div class="bgsgl_right_list_border">
							<div class="bgsgl_right_list_left">
								会议服务
							</div>
						</div>
						<div class="fkdj_sxry" style="height:320px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								style="margin: 0 auto;">
								<tr class="fkdj_sxry_brack">
									<td width="16%">
										会议名称
									</td>
									<td width="16%">
										会议申请人
									</td>
									<td width="20%">
										会议申请时间
									</td>
									<td width="10%">
										会议状态
									</td>
									<td class="fkdj_check_time">
										详细
									</td>
								</tr>
							</table>
							<div style="height:230px">
							<table id="conferenceTable" width="100%" border="0" cellspacing="0" cellpadding="0"
								style="margin: 0 auto;">
								<c:forEach items="${conferenceList }" var="conference">
										<tr>
											<td width="16%">
												${conference.conferenceName }
											</td>
											<td width="16%">
												${conference.outName }
											</td>
											<td width="20%">
												
												${conference.startTime}
											</td>
											<td width="10%">
												<c:if test="${conference.conferenceState == 0}">
													<span class="display_centeralign">申请中</span>
												</c:if>
												<c:if test="${conference.conferenceState == 1}">
													<span class="display_centeralign">已完成</span>
												</c:if>	
												<c:if test="${conference.conferenceState == 2}">
													<span class="display_centeralign">已评价</span>
												</c:if>	
												<c:if test="${conference.conferenceState == 3}">
													<span class="display_centeralign">已取消</span>
												</c:if>	
												<c:if test="${conference.conferenceState == 4}">
													<span class="display_centeralign">已确认</span>
												</c:if>			
											</td>
											<td class="fkdj_check_time">
												<span class="fkdj_cbox" onclick="toViewConference(${conference.conferenceId})">[详细]</span>
											</td>
										</tr>
									</c:forEach>
							</table>
							</div>
							<div class="fkdj_ch_tables">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="26%">
											&nbsp;
										</td>
										<td width="18%"></td>
										<td width="27%">
											<a href="javascript:void(0);" onclick="toConferenceList();">会议管理</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="khfu_div_right">
						<div class="bgsgl_right_list_border">
							<div class="bgsgl_right_list_left">
								通知发布
							</div>
						</div>
						<div class="fkdj_sxry " style="height:320px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								style="margin: 0 auto;">
								<tr class="fkdj_sxry_brack">
									<td width="14%">
										主题
									</td>
									<td width="14%">
										内容摘要
									</td>
									<td width="14%">
										发布时间
									</td>
									<td class="fkdj_check_time">
										详细
									</td>
								</tr>
							</table>
							<div style="height:230px">
							<table id="msgTable" width="100%" border="0" cellspacing="0" cellpadding="0"
								style="margin: 0 auto;">
								<c:forEach items="${commonMsgBoard }" var="commonMsgBoard">
								<tr>
									<td width="14%">
									${commonMsgBoard.title}
									</td>
									<td width="14%">
									<div>
									${commonMsgBoard.content}
									</div>
									</td>
									<td width="14%">
									${commonMsgBoard.msgtime}
									</td>
								
									<td class="fkdj_check_time">
										<span class="fkdj_cbox" onclick="toViewInfo(${commonMsgBoard.msgid});">[详细]</span>
									</td>
								</tr>
						
								</c:forEach>
							</table>
							</div>
							<div class="fkdj_ch_tables">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="26%">
											&nbsp;
										</td>
										<td width="6%"></td>
										<td width="23%"></td>
										<td width="18%">
										</td>
										<td width="27%">
											<a href="javascript:void(0);" onclick="toInfoList()">通知管理</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					
					<div class="clear"></div>
					
					
					
					<div class="khfu_div">
							<div class="bgsgl_right_list_border">
								<div class="bgsgl_right_list_left">
									物业维修
								</div>
							</div>
							<div class="fkdj_sxry " style="height:323px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									style="margin: 0 auto;">
									<tr class="fkdj_sxry_brack">
										<td width="16%">
											维修单号
										</td>
										<td width="16%">
											维修标题
										</td>
										
										<td width="20%">
											维修时间
										</td>
										<td width="10%">
											报修人
										</td>
										<td class="fkdj_check_time">
											详细
										</td>
									</tr>
								</table>
								<div style="height:230px">
								<table id="repairTable" width="100%" border="0" cellspacing="0" cellpadding="0"
									style="margin: 0 auto;">
									<c:forEach items="${repairList }" var="repair">
										<tr>
											<td width="16%">
												${repair.repairId }
											</td>
											<td width="16%">
												${repair.repairTheme }
											</td>
											<td width="20%">
											${repair.repairStartTime }
											</td>
											<td width="10%">
											${repair.reportUserName }
											</td>
											
											<td class="fkdj_check_time">
												<span class="fkdj_cbox" onclick="toViewRepair(${repair.repairId})">[详细]</span>
											</td>
										</tr>
									</c:forEach>
								</table>
								</div>
								<div class="fkdj_ch_tables">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="26%">
												&nbsp;
											</td>
											<td width="27%">
												<a href="javascript:void(0);" onclick="toRepairList();">维修管理</a>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					<div class="khfu_div_right">
						<div class="bgsgl_right_list_border">
							<div class="bgsgl_right_list_left" style="width:300px">
								失物招领
							</div>
						</div>
						<div class="fkdj_sxry " style="height:323px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								style="margin: 0 auto;">
								<tr class="fkdj_sxry_brack">
									<td width="14%">
										标题
									</td>
									<td width="14%">
										认领状态
									</td>
									<td width="14%">
										登记时间
									</td>
									
									<td class="fkdj_check_time">
										详细
									</td>
								</tr>
							</table>
							<div style="height:230px">
							<table id="lostFoundTable" width="100%" border="0" cellspacing="0" cellpadding="0"
								style="margin: 0 auto;">
								<c:forEach items="${lostFoundsList }" var="lostFound">
									<tr>
										<td width="14%">
											${lostFound.lostFoundTitle }
										</td>
										
										<td width="14%">
											<c:if test="${lostFound.lostFoundState == 0}">
												<span class="display_centeralign">未认领</span>
											</c:if>
											<c:if test="${lostFound.lostFoundState == 1}">
												<span class="display_centeralign">已认领</span>
											</c:if>			
										</td>
										<td width="14%">
										<f:formatDate value="${lostFound.lostFoundIntime }" pattern="yyyy-MM-dd"></f:formatDate>
										</td>
										<td class="fkdj_check_time">
											<span class="fkdj_cbox" onclick="toViewLostFound(${lostFound.lostFoundId})">[详细]</span>
										</td>
									</tr>
								</c:forEach>
							</table>
							</div>
							<div class="fkdj_ch_tables">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="26%">
											&nbsp;
										</td>
										<td width="18%"></td>
										<td width="6%">
											
										</td>
										<td width="23%">
											
										</td>
										<td width="27%">
											<a href="javascript:void(0);" onclick="toLostFoundList();">失物管理</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="mina_right">
					<div class="main_left_mainf_k1">
						<ul>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="toVisitorList();"><img
										src="${ctx}/images/property/customer_navs1.jpg"
										onmousemove="this.src='${ctx}/images/property/customer_navs1_hover.jpg'"
										onmouseout="this.src='${ctx}/images/property/customer_navs1.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="toAttendanceSchedule();"><img
										src="${ctx}/images/property/customer_navs2.jpg"
										onmousemove="this.src='${ctx}/images/property/customer_navs2_hover.jpg'"
										onmouseout="this.src='${ctx}/images/property/customer_navs2.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);"><img
										src="${ctx}/images/property/st_lest_nav_a1.jpg" onclick="toPatrolList();"
										onmousemove="this.src='${ctx}/images/property/st_lest_nav_h1.jpg'"
										onmouseout="this.src='${ctx}/images/property/st_lest_nav_a1.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="toPackingList();"><img
										src="${ctx}/images/property/customer_navs4.jpg"
										onmousemove="this.src='${ctx}/images/property/customer_navs4_hover.jpg'"
										onmouseout="this.src='${ctx}/images/property/customer_navs4.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="toStorageList();"><img
										src="${ctx}/images/property/customer_navs5.jpg"
										onmousemove="this.src='${ctx}/images/property/customer_navs5_hover.jpg'"
										onmouseout="this.src='${ctx}/images/property/customer_navs5.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="toComplaintList();"><img
										src="${ctx}/images/property/customer_navs6.jpg"
										onmousemove="this.src='${ctx}/images/property/customer_navs6_hover.jpg'"
										onmouseout="this.src='${ctx}/images/property/customer_navs6.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="toSuggest();"><img
										src="${ctx}/images/property/list_fest36.jpg"
										onmousemove="this.src='${ctx}/images/property/list_fest36_hover.jpg'"
										onmouseout="this.src='${ctx}/images/property/list_fest36.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="initNavi(11)"><img
										src="${ctx}/app/integrateservice/images/navPics/list_fest38.jpg"
										onmousemove="this.src='${ctx}/app/integrateservice/images/navPics/list_fest38_hover.jpg'"
										onmouseout="this.src='${ctx}/app/integrateservice/images/navPics/list_fest38.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							<li class="main_nav">
								<a href="javascript:void(0);" onclick="toViewEnergy(11)"><img
										src="${ctx}/app/integrateservice/images/gljNavPics/nav_news_a4.jpg"
										onmousemove="this.src='${ctx}/app/integrateservice/images/gljNavPics/nav_news_h4.jpg'"
										onmouseout="this.src='${ctx}/app/integrateservice/images/gljNavPics/nav_news_a4.jpg'"
										width="168" height="86" border="0" /> </a>
							</li>
							
							<a id="navi11" class="navi_button_a11" href="javascript:void(0);" onclick="javascript:initNavi(11)"></a>
							
						</ul>
						<div class="clear"></div>
					</div>
					<div class="main_left_main1">
					<!-- 
						<iframe src="${ctx}/app/integrateservice/contact.act?height=388" width="100%" height="378" scrolling="no" frameborder="0"></iframe>
					 -->
						<jsp:include page="/app/integrateservice/newtree.jsp" />
					</div>
				</div>
				<div class="bgsgl_clear"></div>
			</div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
</body>
</html>