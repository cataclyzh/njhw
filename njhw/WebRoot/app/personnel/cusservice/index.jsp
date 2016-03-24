﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>综合办公门户</title>
		<link type="text/css" rel="stylesheet" href="css/Property.css" />
		<script src="${ctx}/app/portal/toolbar/showModel.js"
			type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script type="text/javascript">
        function btn_hov(dom, num)
        {
            if(num == 0)
                dom.className = dom.className + "_hover";
            else
                dom.className = dom.className.replace("_hover", "");
        }
        $(function (){
        	refreshWorkSheet();
        });
        
        function refreshWorkSheet(){
        		var appType = "";
        		if($("#chk_work_sheet_type").attr("checked")=="checked"){
        			appType = "01";
        		} 
				var url = "${ctx}/app/personnel/cusservice/refreshWorkSheet.act?appType="+appType;
				var sucFun = function(data) {
					$("#table_work_sheet").replaceWith(data);
				};
				var errFun = function() {
					alert("加载数据出错!");
				};
				ajaxQueryHTML(url, null, sucFun, errFun);
			}
			
		function frameDialogClose(){
			window.location.href = "${ctx}/app/personnel/cusservice/index.act";
		}
		function closeRefresh(){
			$("#companyWin").window("close");
			window.location.href = "${ctx}/app/personnel/cusservice/index.act";
		}
		function windowDialog(title,url,top,w,h){
				$("#companyWin").show();
				var left = (document.body.scrollWidth - w) / 2;
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
						frameDialogClose();
					}
				});
				$("#companyIframe").attr("src", url);
		}
		
		function sendPhoneMsg(){
			var url = "${ctx}/app/messagingplatform/messagingPlatformInit.act";
			showShelter('610','600',url);
		}
		
		
		
		function sendNote(){
			//showShelter('600','350','${ctx}/common/bulletinMessage/msgBoardAction_init.act');
			windowDialog("物业通知","${ctx}/common/bulletinMessage/msgBoardAction_init.act",20,"700","450");
		}
		
		function sendWYZL(){
			windowDialog("失物招领","${ctx}/app/cms/channelcms/inputArticle.act?mid=1",20,"920","490");
		}
		
		function linkToFrontReg() {
			var url = "${ctx}/app/visitor/frontReg/noOrderInput.act";
			parent.document.location.href = url;
		}
		
		function sendMsg() {
			openEasyWin("winId","发送短信","${ctx}/app/sendsms/inputSMS.act","650","400",true);
	  	}
    
        </script>
	</head>
	<body>
		<div class="main">
			<div class="main_header"></div>
			<div class="main_link" onclick="linkToFrontReg();"></div>
			<div class="main_main1">
				<table border="0" cellspacing="0" class="main_main1_table">
					<tr>
						<td>
							<input type="checkbox" id="chk_work_sheet_type"
								onclick="javascript:refreshWorkSheet();">
						</td>
						<td style="padding: 3px 0 0 5px">
							<a href="javascript:void(0)" hidefocus>只显示未处理请求</a>
						</td>
						<td style="padding: 3px 20px 0 20px">
							<a href="javascript:void(0)"
								onclick="javascript:parent.open_main3('10093' ,'物业维修' ,'${ctx}/app/pro/init1.act');"
								hidefocus>更多>></a>
						</td>
					</tr>
				</table>
				<table id="table_work_sheet" border="0" cellspacing="0"
					class="main_main1_table1">
				</table>
			</div>
			<div class="main_main2">
				<table border="0" cellspacing="0" class="main_main1_table">
					<tr>
						<td style="padding: 3px 0 0 5px">
							<a href="javascript:void(0)"
								onclick="sendNote();"
								hidefocus>发布新通知</a>
						</td>
						<td style="padding: 3px 20px 0 43px">
							<a href="javascript:void(0)"
								onclick="javascript:parent.open_main3('64' ,'物业通知' ,'${ctx}/common/bulletinMessage/msgBoardAction_query.act');"
								hidefocus>更多>></a>
						</td>
					</tr>
				</table>
				<table border="0" cellspacing="0" class="main_main1_table1">
					<c:forEach items="${msgBoardList}" var="board">
						<tr>
							<td style="padding: 0 5px">
								<img src="images/point.png" alt="">
							</td>
							<td>
								<span>${board.MSGDATE}</span>
							</td>
							<td>
								<span title="${board.TITLE}">
								<c:if test="${fn:length(board.TITLE)>17}">
									${fn:substring(board.TITLE,0,17)}...
								</c:if>
								<c:if test="${fn:length(board.TITLE)<=17}">
									${board.TITLE}
								</c:if>
								</span>
							</td>
							<td>
								<span title="${board.CONTENT}">
									<c:if test="${fn:length(board.CONTENT)>10}">
										${fn:substring(board.CONTENT,0,10)}...
									</c:if>
									<c:if test="${fn:length(board.CONTENT)<=10}">
										${board.CONTENT}
									</c:if>
								</span>
							</td>
							<td>
								<a href="javascript:void(0)"
									onclick="showShelter('700','450','${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId=${board.MSGID }');"
									hidefocus>详情>></a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="main_main3">
				<table border="0" cellspacing="0" class="main_main1_table">
					<tr>
						<td style="padding: 3px 20px 0 43px">
							<a href="javascript:void(0)" hidefocus>更多>></a>
						</td>
					</tr>
				</table>
				<span class="main_main3_span num">0</span>
				<span class="main_main3_span1 num">0</span>
				<span class="main_main3_span2 num">0</span>
				<span class="main_main3_span3 num"></span>
			</div>
			<div class="main_main4">
				<table border="0" cellspacing="0" class="main_main1_table">
					<tr>
						<td style="padding: 3px 0 0 5px">
							<a href="javascript:void(0)" onclick="sendWYZL();" hidefocus>发布新消息</a>
						</td>
						<td style="padding: 3px 20px 0 43px">
							<a href="javascript:void(0)"
								onclick="javascript:parent.open_main3('10280' ,'失物招领' ,'${ctx}/app/cms/channelcms/article.act?mid=1');"
								hidefocus>更多>></a>
						</td>
					</tr>
				</table>

				<table border="0" cellspacing="0" class="main_main1_table1"
					style="margin-top: 38px">
					<c:forEach items="${cmsLostFoundList}" var="data">
						<tr>
							<td style="padding: 0 5px">
								<img src="images/point.png" alt="">
							</td>
							<td>
								<span>${data.TITLE}</span>
							</td>
							<td>
								<span>${data.EDITTIME}</span>
							</td>
							<td>
								<a href="javascript:void(0)" onclick="openEasyWin('DetailId','失物招领','${ctx}/app/cms/channelcms/searchCmsDetail.act?id=${data.ID}','700','450',true);" hidefocus>详情>></a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<span class="main_main4_span">现有无人认领物品<span style="color: red"> ${lostFoundTotal} </span> 件</span>
			</div>
			<div class="main_main5">
				<div class="main_main5_span1">
					<span>当前入住：</span><span>${info.innerUnit}</span><span>个单位&nbsp;共</span><span>${info.innerUser}</span><span>人</span>
				</div>
				<div class="main_main5_span2">
					<span>车位总数：</span><span>0</span><span>个&nbsp;空车位数</span><span>0</span><span>个</span>
				</div>
				<div class="main_main5_span3">
					<span>累计接待访客：</span><span>${info.sumVisitor}</span><span>人</span>
				</div>
				<div class="main_main5_span5">
					<span>今日闸机流量：</span><span>0</span><span>人</span>
				</div>
			</div>
			<div class="main_main6">
				<div class="main_main6_btn1" onmouseover="btn_hov(this, 0)"
					onmouseout="btn_hov(this, 1)"
					onclick="javascript:parent.open_main3('10200' ,'人员信息查询' ,'${ctx}/app/per/orgTreeUserListFrame.act');"></div>
				<div class="main_main6_btn2" onmouseover="btn_hov(this, 0)" onclick="sendMsg()"
					onmouseout="btn_hov(this, 1)"></div>
			</div>
			<div class="main_main7">
				<jsp:include
					page="/app/integrateservice/cusPublicBook.jsp?orgId=${_orgid}&type=tel&smac=${smac}&stel=${stel}"></jsp:include>
			</div>
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