<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/metaIframe.jsp"%>
		<title>菜单发布</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	    <link href="${ctx}/app/dining/theMenu/css/menu_css.css" rel="stylesheet" type="text/css" />
		<!-- Highcharts Start -->
		<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/exporting.js"></script>
		<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
		<!-- Highcharts End -->
		<script type="text/javascript">
  
    $(document).ready(function(){
      var fdiTypedd = ${fdiType};
      var FSD_FLAG =  ${FSD_FLAG};
	  for(var i = 1;i<15;i++)
	  {   
	       if(i == fdiTypedd)
	       {
				$("#week_"+i).addClass("selected");
	       }
	       else
	       {
	       		$("#week_"+i).removeClass("selected");
	       }
	   }
	   for(var j=1;j<4;j++)
	   {
	       if(j == FSD_FLAG)
	       {
	    	   $("#tb_"+j).addClass("selected");
	       }
	       else
	       {
	           $("#tb_"+j).removeClass("selected");
	       }
	   }
	   var url = "${ctx}/app/din/ajaxInitNewWeekMenu.act";
	   $("#div_uid").load(url, {id: fdiTypedd, FSD_FLAG: FSD_FLAG});
    });
    function GetDateStr(AddDayCount) 
    { 
	    var dd = new Date(); 
	    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
	    var y = dd.getYear(); 
	    var m = dd.getMonth()+1;//获取当前月份的日期 
	    var d = dd.getDate(); 
	    return y+"-"+m+"-"+d; 
    }
    
	function sendWeek(id)
	{
		for(var i=1;i<15;i++)
	    {   
	       if(i == id)
	       {
				$("#week_"+id).addClass("selected");
	       }
	       else
	       {
	       		$("#week_"+i).removeClass("selected");
	       }
	   }
	  
	   $("#fdiTypedd").val(id);
	   $("#fdiFlag").val(1);

	   var url = "${ctx}/app/din/ajaxInitNewWeekMenu.act?";
	   $("#div_uid").load(url, {id: id, FSD_FLAG: 1});
	   	$("#tb_1").addClass("selected");
		$("#tb_2").removeClass("selected");
		$("#tb_3").removeClass("selected");
	}
	
	
	
	function sendDayTime(id){

	    var faid = $("#fdiTypedd").val();
	    var url = "${ctx}/app/din/ajaxInitNewWeekMenu.act";
		var sucFun = function(data) {
			$("#div_uid").replaceWith(data);

		};
		var errFun = function() {
			alert("加载数据出错!");
		};
		
		$("#fdiFlag").val(id);
		
		//ajaxQueryHTML(url, null, sucFun, errFun);
	   $("#div_uid").load(url, {id: faid, FSD_FLAG: id}, function() {
	   	   for(var i=1;i<4;i++)
	   {   
	       if(i==id)
	       {
				 $("#tb_"+i).addClass("selected");
	       }
	       else
	       {
				 $("#tb_"+i).removeClass("selected");
	       }
		 
	   }
	   });
	}
	
	function publishMenu() {
		openEasyWin('cdfb','菜单发布','${ctx}/app/din/queryPublishDishes.act?fdiFlagFlag='+$("#fdiFlag").val()+'&fdiTypeType='+$("#fdiTypedd").val(), '720' ,'700');
	}
 
 	function goNextWeek() {
 		$('#fdiTypedd').val(8);
 		$("#fdiFlag").val(1);
 	    for(var i = 1;i<15;i++) {   
	       if(i == 8)
	       {
				$("#week_"+i).addClass("selected");
	       }
	       else
	       {
	       		$("#week_"+i).removeClass("selected");
	       }
	   }
	   for(var j=1;j<4;j++)
	   {
	       if(j == 1)
	       {
	    	   $("#tb_"+j).addClass("selected");
	       }
	       else
	       {
	           $("#tb_"+j).removeClass("selected");
	       }
	   }
	   
	   var url = "${ctx}/app/din/ajaxInitNewWeekMenu.act";
	   $("#div_uid").load(url, {id: 8, FSD_FLAG: 1});
 	
 		var iId = setInterval(function(){ readPubCard(); }, 25);
      	var i = 0
        function readPubCard() {
        	i = parseInt(i) + parseInt(1);
        	var ml = parseInt(i)*10;
        	$(".cdgl_nav_right").find("table").css("margin-left", "-"+ml+"%");
        	if (i == 10) {
        		clearInterval(iId);
        	}
        }
 	}
 	
 	function goPreWeek() {
 	    $('#fdiTypedd').val(1);
 		$("#fdiFlag").val(1);
 	   for(var i = 1;i<15;i++) {   
	       if(i == 1)
	       {
				$("#week_"+i).addClass("selected");
	       }
	       else
	       {
	       		$("#week_"+i).removeClass("selected");
	       }
	   }
	   for(var j=1;j<4;j++)
	   {
	       if(j == 1)
	       {
	    	   $("#tb_"+j).addClass("selected");
	       }
	       else
	       {
	           $("#tb_"+j).removeClass("selected");
	       }
	   }
	   
	   var url = "${ctx}/app/din/ajaxInitNewWeekMenu.act";
	   $("#div_uid").load(url, {id: 1, FSD_FLAG: 1});
 	
 		var iId = setInterval(function(){ readPubCard(); }, 25);
      	var i = 0
        function readPubCard() {
        	i = parseInt(i) + parseInt(1);
        	var ml = 100 - parseInt(i)*10;
        	if (i == 10) {
        		$(".cdgl_nav_right").find("table").css("margin-left", "0");
        		clearInterval(iId);
        	} else {
        		$(".cdgl_nav_right").find("table").css("margin-left", "-"+ml+"%");
        	}
        }
 	}
</script>
</head>
<body style="background:#fff;">
<div class="fkdj_index">
    <div class="bgsgl_border_left">
	  	<div class="bgsgl_border_right">菜单发布</div>
	</div>
	<div class="bgsgl_conter">
    	<div class="bgsgl_right_list_border">
			<div class="bgsgl_right_list_left"><span style="display:inline-block;width: 100px;">一周菜单发布</span>
				<a href="javascript:void(0);" class="cdgl_btn" style="display:inline-block;" onclick="javascript:openEasyWin('cdgl','菜单管理','${ctx}/app/din/queryDishes.act', '720' ,'650');">菜单管理</a>
				<a href="javascript:void(0);" class="cdgl_btn" style="display:inline-block;" onclick="publishMenu();">菜单发布</a>
			</div>
		</div>
	<div class="w936">
    <div class="clear"></div>
     <div id="tb_" class="tb_cdgl_tab">
   <ul>
    <li id="tb_1" class="tab_cdgl" onclick="sendDayTime(1);">早餐</li>
    <li id="tb_2" class="tab_cdgl" onclick="sendDayTime(2);">
    午餐</li>
    <li id="tb_3" class="tab_cdgl" onclick="sendDayTime(3);">
    晚餐</li>
   </ul>
 </div>
  <div class="ctt_cdgl_tab">
 <!--早餐开始-->
  <div class="dis" id="tbc_01">
<div class="cdgl_nav_right" style="overflow:hidden;">
<table width="200%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_1" onclick="sendWeek(1);"><span class="cdgl_span">周一</span><br />${date1}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_2" onclick="sendWeek(2);"><span class="cdgl_span">周二</span><br />${date2}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_3" onclick="sendWeek(3);"><span class="cdgl_span">周三</span><br />${date3}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_4" onclick="sendWeek(4);"><span class="cdgl_span">周四</span><br />${date4}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_5" onclick="sendWeek(5);"><span class="cdgl_span">周五</span><br />${date5}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_6" onclick="sendWeek(6);"><span class="cdgl_span">周六</span><br />${date6}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_7" onclick="sendWeek(7);"><span class="cdgl_span">周日</span><br />${date7}</a></td>
    <td style="color:#9aa7c2;font-weight:bold;text-align:center;"><a class="cdgl_nav_a_right" href="javascript:void(0);" onclick="goNextWeek();"></a><br />下周</td>
    <td style="color:#9aa7c2;font-weight:bold;text-align:center;"><a class="cdgl_nav_a_left" href="javascript:void(0);" onclick="goPreWeek();"></a><br />上周</td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_8" onclick="sendWeek(8);"><span class="cdgl_span">周一</span><br />${date8}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_9" onclick="sendWeek(9);"><span class="cdgl_span">周二</span><br />${date9}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_10" onclick="sendWeek(10);"><span class="cdgl_span">周三</span><br />${date10}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_11" onclick="sendWeek(11);"><span class="cdgl_span">周四</span><br />${date11}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_12" onclick="sendWeek(12);"><span class="cdgl_span">周五</span><br />${date12}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_13" onclick="sendWeek(13);"><span class="cdgl_span">周六</span><br />${date13}</a></td>
    <td><a class="cdgl_nav_right_a" href="javascript:void(0);" id="week_14" onclick="sendWeek(14);"><span class="cdgl_span">周日</span><br />${date14}</a></td>
  </tr>
</table>
</div>
<input type="hidden" name="fdiTypedd" id="fdiTypedd" value="${fdiType}" />
<input type="hidden" name="fdiFlag" id="fdiFlag" value="${FSD_FLAG}" />
<div style="height:527px;border-top:solid 1px #98a6be;overflow:auto;" id="div_uid">
</div>
</div>
<!--早餐结束-->
  </div>
  <div class="bgsgl_clear"></div>
</div>
</div>
</div>
</body>
	
</html>
