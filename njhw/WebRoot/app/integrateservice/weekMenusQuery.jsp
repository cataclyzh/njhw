<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>本周菜单</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	    <link href="${ctx}/app/integrateservice/css/weekMenus/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/weekMenus/css/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/weekMenus/css/fest_index.css" rel="stylesheet" type="text/css" />
		<!-- Highcharts Start -->
		<script type="text/javascript" src="${ctx}/scripts/ca/highcharts.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/ca/exporting.js"></script>
		<!-- Highcharts End -->
		<script type="text/javascript">
  
    $(document).ready(function(){

      var fdiTypedd = ${fdiType};
      var FSD_FLAG =  ${FSD_FLAG};
	  for(var i = 1;i<8;i++)
	  {   
	       if(i == fdiTypedd)
	       {
				$("#week_"+i).removeClass("fest_a"+i);
				$("#week_"+i).addClass("fest_a"+i+"_c");
	       }
	       else
	       {
	       		$("#week_"+i).removeClass("fest_a"+i+"_c");
				$("#week_"+i).addClass("fest_a"+i);
	       }
	   }
	   for(var j=1;j<4;j++)
	   {
	       if(j == FSD_FLAG)
	       {
	    	   $("a","#fest_right_"+j).addClass("selected");
			   $("span", "#fest_right_"+j).addClass("selected");
	       }
	       else
	       {
	            $("a","#fest_right_"+j).removeClass("selected");
				$("span", "#fest_right_"+j).removeClass("selected");
	       }
	   }
		 var url = "${ctx}/app/integrateservice/ajaxWeekMenusQuery.act";
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
		for(var i=1;i<8;i++)
	    {   
	       if(i == id)
	       {
				$("#week_"+id).removeClass("fest_a"+id);
				$("#week_"+id).addClass("fest_a"+id+"_c");
	       }
	       else
	       {
	       		$("#week_"+i).removeClass("fest_a"+i+"_c");
				$("#week_"+i).addClass("fest_a"+i);
	       }
	   }
	  
	   
	   
	   
	   var url = "${ctx}/app/integrateservice/ajaxWeekMenusQuery.act?id="+id;
	   $("#div_uid").load(url);
	   	$("a","#fest_right_1").addClass("selected");
		$("span", "#fest_right_1").addClass("selected");
		$("a","#fest_right_2").removeClass("selected");
		$("span", "#fest_right_2").removeClass("selected");
		$("a","#fest_right_3").removeClass("selected");
		$("span", "#fest_right_3").removeClass("selected");
		var sucFun = function(data) {
			$("#div_uid").replaceWith(data);
		};
		var errFun = function() {
			alert("加载数据出错!");
		};
		//ajaxQueryHTML(url, null, sucFun, errFun);
	}
	
	
	
	function sendDayTime(id){

	    var faid = $("#fdiTypedd").val();
	    var url = "${ctx}/app/integrateservice/ajaxWeekMenusQuery.act";
		var sucFun = function(data) {
			$("#div_uid").replaceWith(data);

		};
		var errFun = function() {
			alert("加载数据出错!");
		};
		//ajaxQueryHTML(url, null, sucFun, errFun);
	   $("#div_uid").load(url, {id: faid, FSD_FLAG: id}, function() {
	   	   for(var i=1;i<4;i++)
	   {   
	       if(i==id)
	       {
				 $("a","#fest_right_"+i).addClass("selected");
				 $("span", "#fest_right_"+i).addClass("selected");
	       }
	       else
	       {
				 $("a","#fest_right_"+i).removeClass("selected");
				 $("span", "#fest_right_"+i).removeClass("selected");
	       }
		 
	   }
	   });
		

	}
 
</script>
</head>
	<body>
	<div class="main_index" style="margin: 0 50px;">
	<jsp:include page="/app/integrateservice/header.jsp">
			<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
    </jsp:include>
	<div class="index_bgs"  style="padding-bottom: 10px; padding-left: 10px; padding-right: 10px; padding-top: 0px;">
    <div class="bgsgl_border_left">
	  	<div class="bgsgl_border_right">本周菜单</div>
	</div>
	<div class="bgsgl_conter">
    	<div class="bgsgl_right_list_border">
			<div class="bgsgl_right_list_left">${time}</div>
		</div>
    <div class="clear"></div>
    <div class="fest_index_left">
    <div class="fest_border">本周日历</div>
    <div class="fest_left_list">
    	<a class="fest_a1" href="javascript:void(0);"  id = "week_1" onclick="sendWeek(1);"><span class="fest_span1" id="m11">星期一</span><span id="m1" class="fest_span2">${date1}</span></a>
    	<a class="fest_a2" href="javascript:void(0);"  id = "week_2" onclick="sendWeek(2);"><span class="fest_span1" id="m22">星期二</span><span id="m2" class="fest_span2">${date2}</span></a>
    	<a class="fest_a3" href="javascript:void(0);"  id = "week_3" onclick="sendWeek(3);"><span class="fest_span1" id="m33">星期三</span><span id="m3" class="fest_span2">${date3}</span></a>
    	<a class="fest_a4" href="javascript:void(0);"  id = "week_4" onclick="sendWeek(4);"><span class="fest_span1" id="m44">星期四</span><span  id="m4" class="fest_span2">${date4}</span></a>
    	<a class="fest_a5" href="javascript:void(0);"  id = "week_5" onclick="sendWeek(5);"><span class="fest_span1" id="m55">星期五</span><span  id="m5" class="fest_span2">${date5}</span></a>
    	<a class="fest_a6" href="javascript:void(0);"  id = "week_6" onclick="sendWeek(6);"><span class="fest_span1" id="m66">星期六</span><span  id="m6" class="fest_span2">${date6}</span></a>
    	<a class="fest_a7" href="javascript:void(0);"  id = "week_7" onclick="sendWeek(7);"><span class="fest_span1" id="m77">星期日</span><span  id="m7" class="fest_span2">${date7}</span></a>
    <div class="clear"></div>
    </div>
    </div>
    <div class="fest_index_right">
    <div class="fest_right_top">
      <div class="fest_right_top_a" id="fest_right_1"><a href="javascript:void(0);" onclick="sendDayTime(1);"><span class="fest_right_top_span1">早餐</span>  <span class="fest_right_top_span2">07:30~09:00</span></a></div>
      <div class="fest_right_top_a" id="fest_right_2"><a href="javascript:void(0);" onclick="sendDayTime(2);"><span class="fest_right_top_span1">午餐</span>  <span class="fest_right_top_span2">11:00~12:30</span></a></div>
      <div class="fest_right_top_a" id="fest_right_3"><a href="javascript:void(0);" onclick="sendDayTime(3);"><span class="fest_right_top_span1">晚餐</span>  <span class="fest_right_top_span2">18:00~19:00</span></a></div>
    </div>
    <div id="div_uid">
    <input type="hidden" name="fdiTypedd" id="fdiTypedd" value="${fdiType}" />
	<c:if test="${null!=list}">
		<c:forEach items="${list}" var="am" varStatus="num">
		<div class="fest_border_right">${am.FD_NAME}</div>
		<div class="fest_conter_right">
			<ul>
				<li><img src="${am.FD_PHOTO1}" width="138" height="78" /></li>
				<li><img src="${am.FD_PHOTO2}" width="138" height="78" /></li>
				<li><img src="${am.FD_PHOTO3}" width="138" height="78" /></li>
			</ul>
		<div class="clear"></div>
				<div class="fest_title">${fn:trim(am.FD_DESC)}</div>
		</div>
		</c:forEach>
	</c:if>
	<c:if test="${null == list}">
	<div class="clear"></div>
		<div class="fest_span1">${msg}</div>
	</c:if>
	</div>
    </div>
    <div class="bgsgl_clear"></div>
	</div>
	
	</div>
	<jsp:include page="/app/integrateservice/footer.jsp" />
</div>
</body>
	
</html>
