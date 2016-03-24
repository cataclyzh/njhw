﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>三维物业管理</title>
		<link href="css/3d_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
	    var _ctx = '${ctx}';
        </script>
		<%@ include file="/common/include/meta.jsp"%>
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
		<link href="${ctx}/app/buildingmon/pageIndex/css/3d_css.css" rel="stylesheet" type="text/css" />	
		<!-- Device begin -->
<script type="text/javascript" language="javascript">
        document.domain = "njnet.gov.cn";
        var Device;
	    var DeviceDefaultOption = {
		IsFrameMap : true
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
<!-- Device end -->
<script type="text/javascript" language="javascript">
	//<!CDATA[
	function g(o) {
		return document.getElementById(o);
	}
	function HoverLi(n) {
		//如果有N个标签,就将i<=N;
		for ( var i = 1; i <= 3; i++) {
			g('tb_' + i).className = 'normaltab';
			g('tbc_0' + i).className = 'undis';
		}
		g('tbc_0' + n).className = 'dis';
		g('tb_' + n).className = 'hovertab';
	}
	//如果要做成点击后再转到请将__tag_45$15_中的onmouseover 改成 onclick;
	
	// 根据部门类型加载人员信息
	function getUserByOrg() {
		var org = $("#org").val();
		if (org > 0) {
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getUserByOrg.act",
	            data:{"orgId": org},
	            dataType: 'json',
	            async : false,
	            success: function(json){
	            	if (json.mapList != null) {
			    		$("#user").empty();
			    		$("#time").empty();
		    			$("#user").append("<option value='0'>--------------请选择--------------</option>");
		    			$("#time").append("<option value='0'>--------------请选择--------------</option>");
						$.each(json.mapList,function(i){
							$("#user").append("<option value="+json.mapList[i].ID+">"+json.mapList[i].NAME+"</option>"); 
						});
			    	}
	            }
	        });
		}
	}
	
	// 根据人员信息加载当班时段
	function getTimeByUser() {
		var org = $("#org").val();
		var user = $("#user").val();
		if (user > 0) {
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getTimeByUser.act",
	            data:{"orgId": org, "userId": user},
	            dataType: 'json',
	            async : false,
	            success: function(json){
	            	if (json.mapList != null) {
			    		$("#time").empty();
		    			$("#time").append("<option value='0'>--------------请选择--------------</option>");
						$.each(json.mapList,function(i){
							//[{START_DATE=2014/01/01, END_DATE=2014/01/31, SCHEDULE_ID=100041, END_TIME=18:00:00, START_TIME=08:00:00}]
							$("#time").append("<option value='"+json.mapList[i].START_DATE+" "+json.mapList[i].START_TIME+","+json.mapList[i].END_DATE+" "+json.mapList[i].END_TIME+"'>"+
									json.mapList[i].START_DATE+' '+json.mapList[i].START_TIME+" ~ "+json.mapList[i].END_DATE+' '+json.mapList[i].END_TIME+
									"</option>"); 
						});
			    	}
	            }
	        });
		}
	}
	
    //根据部门Id取当班路线
	function getLineByOrg() {
		var org = $("#org_line").val();
		if (org > 0) {
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getLineByOrg.act",
	            data:{"orgId": org},
	            dataType: 'json',
	            async : false,
	            success: function(json){
	            	if (json.mapList != null) {
			    		$("#line").empty();
			    		$("#user_line").empty();
			    		$("#time_line").empty();
			    		$("#line").append("<option value='0'>--------------请选择--------------</option>");
		    			$("#user_line").append("<option value='0'>--------------请选择--------------</option>");
		    			$("#time_line").append("<option value='0'>--------------请选择--------------</option>");
						$.each(json.mapList,function(i){
							$("#line").append("<option value="+json.mapList[i].LINEID+">"+json.mapList[i].LINENAME+"</option>"); 
						});
			    	}
	            }
	        });
		}
	}
	
    //取人员信息
	function getUserByLine() {
		var org = $("#org_line").val();
		var line = $("#line").val();
		if (org > 0) {
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getUserByLine.act",
	            data:{"orgId": org, "lineId": line},
	            dataType: 'json',
	            async : false,
	            success: function(json){
	            	if (json.mapList != null) {
			    		$("#user_line").empty();
			    		$("#time_line").empty();
		    			$("#user_line").append("<option value='0'>--------------请选择--------------</option>");
		    			$("#time_line").append("<option value='0'>--------------请选择--------------</option>");
						$.each(json.mapList,function(i){
							$("#user_line").append("<option value="+json.mapList[i].USERID+">"+json.mapList[i].USERNAME+"</option>"); 
						});
			    	}
	            }
	        });
		}
	}
	
    //取当班时段
	function getTime() {
		var org = $("#org_line").val();
		var line = $("#line").val();
		var user = $("#user_line").val();
		if (org > 0) {
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/3d/getTime.act",
	            data:{"orgId": org, "lineId": line, "userId": user},
	            dataType: 'json',
	            async : false,
	            success: function(json){
	            	if (json.mapList != null) {
			    		$("#time_line").empty();
		    			$("#time_line").append("<option value='0'>--------------请选择--------------</option>");
						$.each(json.mapList,function(i){
							$("#time_line").append("<option value='"+json.mapList[i].START_DATE+" "+json.mapList[i].START_TIME+","+json.mapList[i].END_DATE+" "+json.mapList[i].END_TIME+"'>"+
									json.mapList[i].START_DATE+' '+json.mapList[i].START_TIME+" ~ "+json.mapList[i].END_DATE+' '+json.mapList[i].END_TIME+
									"</option>"); 
						});
			    	}
	            }
	        });
		}
	}

	// 显示巡查详细信息
	function getPatrolDetail(sid,startTime,endTime) {
		url = "${ctx}/app/3d/patrolExceptionDetail.act?scheduleId="+sid+"&startTime="+startTime+"&endTime="+endTime;
		showShelter("600","430",url);
	}

	function test1(){
		var org = $("#org").val();
		var staffID = $("#user").val();//userID
		var time = $("#time").val();
		      if(org=='0')
		      {
	           	alert("请选择人员类型！");      
		      }
		      else if(staffID=='0')
		      {
	           	alert("请选择人员姓名！");       
		      }
		     else if(time=='0')
		      {
	           	alert("请选择当班时段！");             
		      }
		    else{
		    	drawPoints(staffID,time);
				}
	}
	function test2(){
		var org_line = $("#org_line").val();
		var line = $("#line").val();
		var staffID = $("#user_line").val();//userID
		var time = $("#time_line").val();

		 if(org_line=='0')
		      {
	           	alert("请选择人员类型！");      
		      }
		      else if(line=='0')
		      {
	           	alert("请选择当班路线！");       
		      }
		      else if(staffID=='0')
              {
              	alert("请选择人员姓名！");   
              }
		     else if(time=='0')
		      {
	           	alert("请选择当班时段！");             
		      }
		    else{
		    	drawPoints(staffID,time);
				}
		}
	function test3(userId, startDate, endDate){
		var time = startDate + "," + endDate;
		drawPoints(userId,time);
	}
	
	
	function drawPoints(staffID,time){
		$.ajax({
			type: "POST",
			url: "${ctx}/app/3d/getStaffIDs.act",
			data: {"staffID": staffID,"time":time},
			dataType: 'json',
			async : false,
			success: function(json){
				/*
	              alert('success!');
	              alert(json.orgName);
	              alert(json.lineName);
	              alert(json.staffName);
	              alert(json.lineDescription);
	              //alert(json.ids);
	              alert(json.orderPoint);
	              alert(json.deviceIds);
	              */
	              if(json.lineName=='loss')
	                {
	                alert("此人不存在！");
	                }
	                else{
                  var Security = new Object();
                  var info = new Array();
                  info.push("执勤人员： ,"+json.staffName);
                  info.push("所属部门： , "+json.orgName);
                  info.push("执勤路线： ,"+json.lineName);
                  info.push("路线描述： ,"+json.lineDescription);
                  Security.ShowInfo = info;
                  //Security.PathId = "1"; TH修改接口后不需要PathId需要OrderPoint
                  //Security.OrderPoint = "2000234,2000235,2000232,2000233,2000236"; 
                  //Security.DeviceIds = "2000234,2000235,2000232,2000233,2000236"; 
                  Security.OrderPoint = json.orderPoint;
                  Security.DeviceIds = json.deviceIds;
				  //Security.DeviceIds = "2000225,2000226,2000227,2000228,2000231,2000229,2000230";
                  Device.InitPatrolLine(Security);
                    }
			}
		});
	}
</script>
<script type="text/javascript" language="javascript">
$(function(){
		$.ajax({
            type: "POST",
            url: "${ctx}/app/3d/getOrgIds.act",
            dataType: 'json',
            async : false,
            success: function(json){
            	if (json.mapList != null) {
		    		$("#org").empty();
	    			$("#org").append("<option value='0'>--------------请选择--------------</option>");
					$.each(json.mapList,function(i){
						$("#org").append("<option value="+json.mapList[i].orgId+">"+json.mapList[i].orgName+"</option>"); 
					});

					$("#org_line").empty();
	    			$("#org_line").append("<option value='0'>--------------请选择--------------</option>");
					$.each(json.mapList,function(i){
						$("#org_line").append("<option value="+json.mapList[i].orgId+">"+json.mapList[i].orgName+"</option>"); 
					});
		    	}
            }
        });
})
</script>
	</head>
	<body onload="initDevice();">
	<body>
	<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/pro/patrolIndex.act?type=A"
					name="gotoParentPath" />
			</jsp:include>
<div class="fkdj_index">
<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
    <div class="bgsgl_border_left">
	  <div class="bgsgl_border_right">三维物业管理</div>
	</div>
	<div class="bgsgl_conter">
    <div class="swfk_dj_left">
	    <!--div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">设备管理</div>
		</div-->
        <div class="w936">
 <div id="tb_" class="tb_">
   <ul>
    <li id="tb_1" class="hovertab" onclick="x:HoverLi(1);">
    按人员查看</li>
    <li id="tb_2" class="normaltab" onclick="i:HoverLi(2);">
    按路线查看</li>
    <li id="tb_3" class="normaltab" onclick="i:HoverLi(3);">
    异常查看</li>
   </ul>
 </div>
 <div class="ctt">
  <div class="dis" id="tbc_01">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr class="swfk_tar">
    <td>人员类型：</td>
    <td>
        <select class="input_swfk" id="org" name="org" onchange="getUserByOrg()">
        <option value="0">
           --------------请选择--------------
        </option>
    </select>
    </td>
  </tr>
  <tr class="swfk_tar">
    <td>人员姓名：</td>
    <td>
	    <select class="input_swfk" id="user" name="user" onchange="getTimeByUser()">
	      <option value="0">
	      	--------------请选择--------------
	      </option>
	    </select>
    </td>
  </tr>
  <tr class="swfk_tar">
    <td>当班时段：</td>
    <td>
	    <select class="input_swfk" id="time" name="time">
	      <option value="0">
	      	--------------请选择--------------
	      </option>
	    </select>
    </td>
  </tr>
</table>
<div class="dis_table_a">
  <a href="javascript:test1();">线路查看</a></div>
  <div class="undis_span">
<strong>访客定位操作提示：</strong><br />
1、三维地图对显卡要求较高，建议使用1G以上独立显卡<br />
2、首次使用三维地图请务必按照页面提示安装所需插件<br />
3、三维地图首次加载可能较慢，请务必在地图加载完毕后再进行定位操作，否则可能导致浏览器崩溃
   </div>
</div>
  <div class="undis" id="tbc_02">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr class="swfk_tar">
    <td>人员类型：</td>
    <td><select class="input_swfk" id="org_line" name="org_line" onchange="getLineByOrg()">
      <option value="0">
           --------------请选择--------------
      </option>
    </select></td>
  </tr>
  <tr class="swfk_tar">
    <td>当班路线：</td>
    <td>
	    <select class="input_swfk" id="line" name="line" onchange="getUserByLine()">
	      <option value="0">
	      	--------------请选择--------------
	      </option>
	    </select>
    </td>
  </tr>
  <tr class="swfk_tar">
    <td>人员姓名：</td>
    <td>
	    <select class="input_swfk" id="user_line" name="user_line" onchange="getTime()">
	      <option value="0">
	      	--------------请选择--------------
	      </option>
	    </select>
    </td>
  </tr>
  <tr class="swfk_tar">
    <td>当班时段：</td>
    <td>
	    <select class="input_swfk" id="time_line" name="time_line">
	      <option value="0">
	      	--------------请选择--------------
	      </option>
	    </select>
    </td>
  </tr>
</table>
<div class="dis_table_a">
  <a href="javascript:test2();">线路查看</a></div>
  <div class="undis_span">
<strong>访客定位操作提示：</strong><br />
1、三维地图对显卡要求较高，建议使用1G以上独立显卡<br />
2、首次使用三维地图请务必按照页面提示安装所需插件<br />
3、三维地图首次加载可能较慢，请务必在地图加载完毕后再进行定位操作，否则可能导致浏览器崩溃
   </div>
  </div>
  <div class="undis" id="tbc_03">
      <Iframe src="${ctx}/app/3d/patrolExceptionList.act" width="100%" height="230" scrolling="no"
	      name="main_frame_left" id="main_frame_left" frameborder="0">
	  </iframe>
  </div>
  </div>
 </div>
 </div>
 <div class="swfk_dj_right" style="float: right; width: 1000px; height: 550px;">
	    <iframe id="threeDimensional" name="threeDimensional" 
 							src="http://3d.njnet.gov.cn/1/index.htm" width="100%" height="100%">
	 	</iframe>
 </div>
<div class="bgsgl_clear"></div>
</div>
    </div>
</div>
</div>
</div>
<jsp:include page="/app/integrateservice/footer.jsp" />
	</body>

</html>