﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager_min.tld" prefix="w" %>
<script type="text/javascript" language="javascript">
	var _ctx = '${ctx}';
</script>
<html>
<head>
<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三维访客管理</title>
<%@ include file="/common/include/meta.jsp"%>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<link href="${ctx}/app/buildingmon/pageIndex/css/3d_css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="javascript">
    document.domain = "njnet.gov.cn";
    var Device;
    var DeviceDefaultOption = {
		IsFrameMap : true,
		OnVisitorSelected: function(){}
	}
</script>

<script type="text/javascript" language="javascript">
function initDevice(){
	setTimeout(function(){
	try{
	   Device = frames['threeDimensional'].Init3D("Device", DeviceDefaultOption);
	   Device.MenuContent();
	   }
	   catch(e){
	   initDevice();
	   }
	},100); 
}
</script>

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

	function postionFunction(json){
		var VisitorObjArray = new Array();
		var array = json.jsonArrayVisitor;
		if(array.length > 0){
	        for (var i = 0; i < array.length; i++) {
	        	if(array[i].DEVICEID=='NONE'){
	         		alert("此访客没有佩戴无线定位卡！");
	         		//continue;
	         		return;
	        	}
				var info = new Array();
				info.push("访客名称：,"+array[i].NAME);
				info.push("证件类型：,"+array[i].CARD_TYPE);
				info.push("证件号码：,"+array[i].CARD_ID);
				info.push("目前位置：,"+array[i].LOCATION);
				info.push("访问人：,"+array[i].USER_NAME);
				info.push("访问单位：,"+array[i].ORG_NAME);
				//alert(info + array[i].DEVICEID);
				var VisitorObj = new Object();
	            VisitorObj.DeviceId = array[i].DEVICEID;
				VisitorObj.ShowInfo = info;
	           	VisitorObjArray.push(VisitorObj);
	           	Device.InitVisitorPoint(VisitorObj.DeviceId, VisitorObjArray);
			}
		}else{
			alert("访客不存在，无法定位");
		}
	}

	function visitor_location(){
   		var visitorName = $("#visitor_name").val();
   		var visitorID = 'NONE';
   		if (visitorName.replace(/(^\s*)|(\s*$)/g, "") != "") {
			$.ajax({
				type: "POST",
				url: "${ctx}/app/buildingmon/visitorPosition.act",
				data: {"visitorName": visitorName,"visitorID": visitorID},
				dataType: 'json',
				async : false,
				success: function(json){
					postionFunction(json);
				}
			});
		} else {
			alert("请输入访客名称后定位");
		}
	}

	function pushPoint(id) {
		var visitorName = 'NONE';
		var visitorID = id;
		$.ajax({
			type: "POST",
			url: "${ctx}/app/buildingmon/visitorPosition.act",
			data: {"visitorName": visitorName,"visitorID": id},
			dataType: 'json',
			async : false,
			success: function(json){
				postionFunction(json);
			}
		});
	}

	function pushErrorPoint(id, time) {
		$.ajax({
			type: "POST",
			url: "${ctx}/app/3d/getVisitorIDs.act",
			data: {"visitorID": id, "timeIn": time},
			dataType: 'json',
			async: false,
			success: function(json){
				var visitorArray = json.jsonArrayVisitor;
				if(visitorArray.length > 0){
					var errVisitor = visitorArray[0];
		        	if(errVisitor.DEVICEID == 'NONE'){
		         		alert("此访客没有佩戴无线定位卡！");
		         		return;
		        	}
		        	
					var info = new Array();
					info.push("访客名称：,"+errVisitor.NAME);
					info.push("证件类型：,"+errVisitor.CARD_TYPE);
					info.push("证件号码：,"+errVisitor.CARD_ID);
					info.push("访问人：,"+ errVisitor.USER_NAME);
					info.push("访问单位：,"+errVisitor.ORG_NAME);

					//alert(info + errVisitor.DEVICEID);
					
					//var VisitorObj = new Object();
					//VisitorObj.DeviceIds = errVisitor.DEVICEID;
	                //VisitorObj.ShowInfo = info;
	                //Device.InitVisitorLine(VisitorObj);
	                
	                var Security = new Object();
	                Security.ShowInfo = info;
	                Security.OrderPoint = errVisitor.DEVICEID;
	                Security.DeviceIds = errVisitor.DEVICEID;
	                Device.InitPatrolLine(Security);
				}else{
					alert("访客不存在，无法定位");
				}
			}
		});
	}
</script>
</head>
<body class="oper_body" onload="initDevice();">
<div class="main_index">
	<jsp:include page="/app/integrateservice/header1.jsp">
		<jsp:param value="/app/omc/newIndex.jsp" name="gotoParentPath" />
	</jsp:include>
	<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
		<div class="oper_border_right">
			<div class="oper_border_left">三维访客管理</div>
		</div>
		<div class="bgsgl_conter" style="min-height: 550px">
			<div style="float:left; width:358px;">
				<div class="w936">
					<div id="tb_" class="tb_"  style="border-bottom:solid 1px #77abba;">
					<ul>
						<li id="tb_1" class="hovertab" onclick="x:HoverLi(1);">访客定位</li>
						<li id="tb_2" class="normaltab" onclick="i:HoverLi(2);">访客异常</li>
					</ul>
					</div>
					<div class="ctt">
						<div class="dis" id="tbc_01">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>访客姓名：</td>
								<td><input name="" id="visitor_name" class="input_swfk" type="text" /></td>
							</tr>
							</table>
							<div class="oper_botton">
								<a href="javascript:void(0);" class="botton_oper_a" id="visitor_location" onClick="visitor_location()">定&nbsp;&nbsp;位</a>
							</div>
							<div class="bgsgl_right_list_border" style="border-bottom:solid 4px #77abba;">
								<div class="bgsgl_right_list_left">访客列表</div>
							</div>
							<div>
							    <Iframe src="${ctx}/app/3d/visitorList.act" width="100%" height="230" scrolling="no"
					                name="main_frame_left" id="main_frame_left" frameborder="0">
					            </iframe>
					        </div>
							<div class="undis_span">
								<strong>访客定位操作提示：</strong>
								<br />
								1、三维地图对显卡要求较高，建议使用1G以上独立显卡
								<br />
								2、首次使用三维地图请务必按照页面提示安装所需插件
								<br />
								3、三维地图首次加载可能较慢，请务必在地图加载完毕后再进行定位操作，否则可能导致浏览器崩溃
							</div>
						</div>
						<div class="undis" id="tbc_02">
							<Iframe src="${ctx}/app/3d/visitorErrorList.act" width="100%" height="230" scrolling="no"
				                        name="main_frame_left" id="main_frame_left" frameborder="0">
				            </iframe>
						</div>
					</div>
				</div>
			</div>
			<div style="float: right; width:900px; height: 550px; margin-left:14px">
				<iframe id="threeDimensional" name="threeDimensional" 
					src="http://3d.njnet.gov.cn/1/index.htm" width="100%" height="100%"></iframe>
			</div>
			<div class="bgsgl_clear"></div>
		</div>
		<jsp:include page="/app/integrateservice/footer1.jsp" />
	</div>
</div>
</body>
</html>