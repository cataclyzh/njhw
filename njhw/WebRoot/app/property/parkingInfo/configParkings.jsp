<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>车牌号分配管理</title>	
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/datatable/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>


<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/datatable/jquery.dataTables.css" rel="stylesheet" type="text/css" />

<%-- <script src="${ctx}/scripts/basic/jquery-migrate-1.2.1.js" type="text/javascript"></script> --%>
<script src="${ctx}/scripts/datatable/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/jquery.timepicker.js" type="text/javascript"></script>
		
<style type="text/css">
.e-info {
    background: #1abc9c;
    color: #fff;
	padding: 2px;
	margin: 2px 5px;
	display: inline-block;
    width: 108px;
}
.e-info-sm {
	width: 70px;
}
.btn-sm {
	width: 48px;
	height: 26px;
	margin-right: 5px;
}
.actions {
	width: 38px;
	margin: 0 auto;
	height: 30px;
	text-align: center;
}
.table_btn1{
    width: 36px;
    height: 24px;
    background:#8090b2;
	color:#fff;
	line-height:24px;
	text-align:center;
	font-family:"微软雅黑";
	margin: 0 auto;
	float:left;
	cursor:pointer;
}
.table_btn_orgframe
{
    width: 68px;
    height: 22px;
    background:#8090b2;
	color:#fff;
	line-height:22px;
	text-align:center;
	font-family:"微软雅黑";
	margin-right:10px;
	float:right;
	cursor:pointer;
	text-decoration: none;
}
.in_sss_left{
	margin:0;
	padding:0;
	border:0;
}
/*------------------------*/
#parking_t {
	border-bottom: none;
}
.dataTables_info {
	background: #f6f5f1 !important;
	width: 100%;
	color: #808080 !important;
	/* padding: 2px 10px; */
	padding: 5px auto;
}
.dataTables_info {
	color: #808080;
    font-size: 10pt;
    font-weight: bold;
}
table.dataTable thead {
	background: #ecece4;
	color: #6699cc;
}
table.dataTable thead th {
	font-size: 14px;
	text-align: center;
	border-bottom: none;
}
table.dataTable tbody tr {
	background: #f6f5f1;
}

table.dataTable tbody tr td {
	text-align: center;
	color: #808080;
	border-bottom: 1px dotted #c7c6c2;
}
.bottom {
	text-align: center !important;
}
div.dataTables_paginate {
    float: left !important;
    margin: 0 auto;
    background: #E0E3EA;
    width: 100%;
    text-align: center !important;
}

.dataTables_paginate .paginate_button{
  width: 24px;
  height: 24px;
  line-height: 24px;
  display: inline-block;
  background: #98a6be;
  margin: 5px;
  text-decoration: none;
  font-weight: bold;
  overflow: hidden;
  text-align: center;
}
.dataTables_paginate .current, .dataTables_paginate .current {
	margin: 5px;
	color: #fff !important;
	text-decoration: none;
	text-align: center;
	background: #ccc !important;
}
</style>
<script type="text/javascript">
/*清空充值查询条件 */
function clearSearch(){
	$("#orgname").val("");
	$("#department").val("");
	$("#username").val("");
	$("#platenumber").val("");
}
/*dataTable(www.datatables.net)*/
$(document).ready(function() {
	
	$('.datepicker').click(function(){
		WdatePicker({dateFmt:'yyyy-MM-dd'});
	});

  function windowDialog(title,url,w,h,refresh){
	 	var body =  window.document.body;
		var left =  body.clientWidth/2 - w/2;
		var top =  body.clientHeight/2+h/4;
		var scrollTop = document.body.scrollTop;
		//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
		top = top + scrollTop;
		// $("#companyWin").show();

	    openEasyWin("edit_parking",title,url,w,h,false);
	    $('div.window-header').css({"width": "480px"});
  }
	
 
	
	//IE8 Array indexOf
	  if (!Array.prototype.indexOf)
	  {
	    Array.prototype.indexOf = function(elt /*, from*/)
	    {
	      var len = this.length >>> 0;  
	       var from = Number(arguments[1]) || 0;
	      from = (from < 0)
	           ? Math.ceil(from)
	           : Math.floor(from);
	      if (from < 0)
	        from += len; 
	       for (; from < len; from++)
	      {
	        if (from in this &&
	            this[from] === elt)
	          return from;
	      }
	      return -1;
	    };
	  }
	
    var table = $('#parking_t').DataTable({
    	"sDom": '<"title">lt<"bottom"p><"clear">',
         "columns": [
           { "data": "ORG_NAME" },
           { "data": "DEPARTMENT" },
           { "data": "CAR_OWNER_NAME" },
           { "data": "CAR_NO" },
           { "data": "CAR_OWNER_PHONE" },
           { "data": "CAR_CHANNEL_INFO" },
           { "data": "START_DATE_AND_END_DATE" },
           { "data": "STATUS" },
           { "data": "STATUS" }
         ],
        "bFilter": true,
	    "processing": true,
	    "oLanguage": {
	    	"sProcessing": "正在加载中......",
	    	"sLengthMenu": "每页显示 _MENU_ 条记录",
	    	"sZeroRecords": "查询无数据！",
	    	"sEmptyTable": "查询无数据！",
	    	"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	    	"sInfoEmpty": "显示0到0条记录",
	    	"sInfoFiltered": "数据表中共为 _MAX_ 条记录",
	    	"sSearch": "当前数据搜索",
	    	"oPaginate": {
	    	  "sFirst": "<span><<</span>",
			  "sPrevious": "<span><</span>",
		      "sNext": "<span>></span>",
		      "sLast": "<span>>></span>"
	    	}
	    },
	    "serverSide": true, 
	    "searchable": true,
	    "sServerMethod": "POST",
	    "bSort": false,
	    "sPaginationType": "full_numbers",
	    "pageLength": 8,
	    "aoColumnDefs": [
	      { "sWidth": "120px",  "aTargets": [0] },
	      { "sWidth": "120px", "aTargets": [1] },
	      { "sWidth": "60px", "aTargets": [2] },
	      { "sWidth": "55px", "aTargets": [3] },
	      { "sWidth": "70px", "aTargets": [4] },
	      { "sWidth": "150px", "aTargets": [5] },
	      { "sWidth": "80px", "aTargets": [6] },
	      { "sWidth": "50px", "aTargets": [7] },
	      { "sWidth": "35px", "aTargets": [8] }
	    ],
	    "ajax": {
	 	  "url": "/park/getParkLicenseList", //"/park/getParkLicenseList","http://localhost:3000/parkings/list",//
	 	  "dataType": "json",
	 	  "data": function ( d ) {
	 	   /*       	
	 		return $.extend( {}, d, {
	 		  "extra_search": $('#extra').val()
	 		}); */
	 		 return {
	 		   draw: d["draw"],
	 		   start: d["start"],
	 		   length: d["length"],
	 		   search: d["search"]["value"]
	 		 }
	 	   }
	 	 },
	    "fnInitComplete": function() {
	    	var oListSettings = this.fnSettings();
	        var wrapper = this.parent();
	    	$('#parking_t_length').hide();
	    },
	    "drawCallback": function(){
	    	editParking();
	    },
	    "createdRow": function ( row, data, index ) {
	    	//出入口
        	/* var output = "";
        	if(data["CAR_CHANNEL_INFO"] == '所有出入口') {
        	  	output = "<span class='e-info'>"+data["CAR_CHANNEL_INFO"]+"</span>";
        	}else{
        	    var group = data["CAR_CHANNEL_INFO"].split('|');
        	    if (group.indexOf('北门1号岗入口1') != -1){
        	    	output += "<span class='e-info'>北门1号岗入口1</span>";
        	    }
        	    if (group.indexOf('北门1号岗入口2') != -1){
        	    	output += "<span class='e-info'>北门1号岗入口2</span>";
        	    }
        	    if (group.indexOf('南门3号岗入口') != -1){
        	    	output += "<span class='e-info'>南门3号岗出入口</span>";
        	    }
        	    if (group.indexOf('北门2号岗入口') != -1){
        	    	output += "<span class='e-info'>北门2号岗出入口</span>";
        	    }
        	    if (group.indexOf('南门地面安全岛入口') != -1){
        	    	output += "<span class='e-info'>南门地面安全岛出入口</span>";
        	    }
        	    if (group.indexOf('E座地面安全岛入口') != -1){
        	    	output += "<span class='e-info'>E座地面安全岛出入口</span>";
        	    }
	            
        	} */
        	//出入口
        	var output = "";
        	if(data["CAR_CHANNEL_INFO"] == '0') {
        	  	output = "<span class='e-info'>公车</span>";
        	}else if (data["CAR_CHANNEL_INFO"] == '2|3|4|5|6|7|8|9|10') {
        		output = "<span class='e-info'>ABCD私车</span>";
        	}else if (data["CAR_CHANNEL_INFO"] == '2|5|6') {
        		output = "<span class='e-info'>E座私车</span>";
        	}else{
        		output = "<span class='e-info'>环道车辆</span>";
        	}
        	var validDate = data["START_DATE_AND_END_DATE"].split("~");
        	var status = "";
        	switch(data["STATUS"]){
        	case '0':
        		status = "审核通过";
        		break;
        	case '1':
        		status = '等待审核';
        		break;
        	case '2':
        		status = '审核拒绝';
        		break;
        	default:
        		status = '未知';
        	}
			
            $('td', row).eq(5).html(output);
            $('td', row).eq(6).html('<span class="e-info e-info-sm">'+validDate[0]+'</span><span class="e-info e-info-sm">'+validDate[1]+'</span>');
            $('td', row).eq(7).html(status);
            $('td', row).eq(8).html('<div class="actions"><div class="table_btn1 btn-sm auth-btn" id="auth_'+data["CAR_NO"]+'">编辑</div></div>');
	    }
    });
    
    /* $.fn.dataTable.ext.search.push(
    	    function( settings, data, dataIndex ) {
    	        var orgname = $('#orgname').val();
    	        var department = $('#department').val();
    	        var username = $('#username').val();
    	        var platenumber = $('#platenumber').val();
    	        return true;
    	    }
   	); */
   	//编辑Dialog回调刷新Table,作为引用
   	gParkingsTable = table;
    
    $('#resetbutton').click(function(e){
		$('#queryForm')[0].reset();
		/* table.search(
		        $('#orgname').val(),
		        $('#department').val(),
		        $('#username').val(),
		        $('#platenumber').val()
		).draw(); */
		table.search("").draw();
	});
	$('#searchbutton').click(function(e){
		var searchStr = "\"";
		if($('#orgname').val() !== ''){
			if(searchStr.length == 1){
				searchStr += "ORG_NAME="+$('#orgname').val();	
			}else{
				searchStr += "&ORG_NAME="+$('#orgname').val();	
			}
			
		}
		if($('#department').val() !== ''){
			if(searchStr.length == 1){
				searchStr += "DEPARTMENT="+$('#department').val();	
			}else{
				searchStr += "&DEPARTMENT="+$('#department').val();	
			}
		}
		if($('#username').val() !== ''){
			if(searchStr.length == 1){
				searchStr += "CAR_OWNER_NAME="+$('#username').val();	
			}else{
				searchStr += "&CAR_OWNER_NAME="+$('#username').val();	
			}
		}
		if($('#platenumber').val() !== ''){
			if(searchStr.length == 1){
				searchStr += "CAR_NO="+$('#platenumber').val();	
			}else{
				searchStr += "&CAR_NO="+$('#platenumber').val();	
			}
		}
		if($('#status').val() !== ''){
			if(searchStr.length == 1){
				searchStr += "STATUS="+$('#status').val();	
			}else{
				searchStr += "&STATUS="+$('#status').val();	
			}
		}
		
		if($('#S_START_DATE').val() !== ''){
			if(searchStr.length == 1){
				searchStr += "START_DATE="+$('#S_START_DATE').val();	
			}else{
				searchStr += "&START_DATE="+$('#S_START_DATE').val();	
			}
		}
		if($('#S_END_DATE').val() !== ''){
			if(searchStr.length == 1){
				searchStr += "END_DATE="+$('#S_END_DATE').val();	
			}else{
				searchStr += "&END_DATE="+$('#S_END_DATE').val();	
			}
		}

		searchStr += "\"";
		table.search(searchStr).draw();
	});
	
	function editParking(){
		$('.auth-btn').click(function(){
			var rowData = table.row($(this).parents('tr')[0]).data(),
			    carNo = $(this).attr('id').split('_')[1];
			
			$('#org_name_hidden').val(rowData["ORG_NAME"])
			$('#department_hidden').val(rowData["DEPARTMENT"])
			$('#car_owner_name_hidden').val(rowData["CAR_OWNER_NAME"])
			$('#car_owner_phone_hidden').val(rowData["CAR_OWNER_PHONE"])
			$('#car_no_hidden').val(rowData["CAR_NO"])
			$('#start_date_and_end_date_hidden').val(rowData["START_DATE_AND_END_DATE"])
			var timeStr = "",
			    len = rowData["START_TIME_AND_END_TIME"].length;
			for(var i = 0 ; i<len; i++) {
				var index=i+1;
				var tmpStr = "["+rowData["START_TIME_AND_END_TIME"][i]["START_TIME_"+index] + " ~ " + rowData["START_TIME_AND_END_TIME"][i]["END_TIME_"+index] + ",";
				if (rowData["START_TIME_AND_END_TIME"][i]["TIME_"+index+"_USE_FLAG"] == "1"){
					tmpStr += " 启用]";
				}else{
					tmpStr += " 禁止]";
				}
				timeStr += tmpStr;
			}
			
			$('#start_time_and_end_time_hidden').val(timeStr);
			$('#status_hidden').val(rowData["STATUS"]);
			//出入口
        	var carChannelInfo = "";
        	if(rowData["CAR_CHANNEL_INFO"] == '0') {
        		carChannelInfo = "公车";
        	}else if (rowData["CAR_CHANNEL_INFO"] == '2|3|4|5|6|7|8|9|10') {
        		carChannelInfo = "ABCD私车";
        	}else if (rowData["CAR_CHANNEL_INFO"] == '2|5|6') {
        		carChannelInfo = "E座私车";
        	}else{
        		carChannelInfo = "环道车辆";
        	}
			$('#car_channel_info_hidden').val(carChannelInfo);
			
			windowDialog("编辑车牌信息","${ctx}/app/property/parkingInfo/popup/editParking.jsp",'600','400');
			
			/*$.ajax({
			    type: 'GET',
			    url: 'http://localhost:3000/parkings/show',//'${ctx}/app/attendance/getAccepters.act',
			    data: {carNo: carNo},
			    dataType: 'json'
			}).done(function(res){*/
				//JSON.stringify(res.accepters)
				// res = {success: 'true', data: {
			      /* CAR_CHANNEL_INFO: "北门1号岗入口1|北门1号岗入口2",
			      CAR_NO: "苏A8UI78",
			      CAR_OWNER_NAME: "aaa",
			      CAR_OWNER_PHONE: "123456789",
			      DEPARTMENT: "南京市信息中心",
			      ORG_NAME: "南京市信息中心",
			      START_DATE_AND_END_DATE: "2015-04-01~2015-05-08",
			      START_TIME_AND_END_TIME: [
			        {END_TIME_1: "00:00:00", START_TIME_1: "01:15:00", TIME_1_USE_FLAG: "1"},
			        {END_TIME_2: "01:30:00", START_TIME_2: "03:00:00", TIME_2_USE_FLAG: "1"},
			        {END_TIME_3: "04:00:00", START_TIME_3: "05:00:00", TIME_3_USE_FLAG: "1"},
			        {END_TIME_4: "06:30:00", START_TIME_4: "10:45:00", TIME_4_USE_FLAG: "1"}
			      ],
			      STATUS: "0"
			    }} */
				/*if(res.success !== 'true') {
				  alert('查看车牌信息失败，请联系管理员');
			    }else{
				  windowDialog("编辑车牌信息","${ctx}/app/property/parkingInfo/popup/editParking.jsp",'600','400');
				}
			}).fail(function(){
			    alert('查看车牌信息失败，请联系管理员');
			});*/
		});
	 }
});
</script>
</head>

<body style="background: white; width: 950px; margin: 0 auto;">
	<div class="main_border_01">
		<div class="main_border_02">车牌号分配管理</div>
	</div>
	<div class="main_conter" style="height: 700px">
		<div class="main3_left_01" style="width:100%">
			<div>
				<form id="queryForm" action="configParkings.act" method="post" autocomplete="off" style="height: 560px;">
				 	<h:panel id="query_panel" width="100%" title="查询条件">	
				 		<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
					      <tr>
						        <td class="form_label" style="width: 25px;" align="left">
						           	单位名称：
						        </td>
						        <td width="10%">
									<s:textfield name="orgname" theme="simple" size="12"/>  
						        </td>
						        <td class="form_label" style="width: 25px;" align="left">
						           	处室：
						        </td>
						        <td width="10%">
									<s:textfield name="department" theme="simple" size="12"/>  
						        </td>
						        <td class="form_label" style="width: 25px;" align="left">
						           	状态：
						        </td>
						        <td width="10%">
									<select name="status" id="status">
										<option selected="selected"></option>
										<option value="0">审核通过</option>
										<option value="1">等待审核</option>
										<option value="2">审核拒绝</option>
									</select>
						        </td>
						  </tr>
						  <tr>
						        <td class="form_label" style="width: 25px;" align="left">
						           	姓名：
						        </td>
						        <td width="10%">
									<s:textfield name="username" theme="simple" size="12"/>  
						        </td>
						        <td class="form_label" style="width: 25px;" align="left">
						           	车牌号：
						        </td>
						        <td  width="10%">
						           <s:textfield name="platenumber" theme="simple" size="12"/>  
						        </td>
						        <td class="form_label" style="width: 25px;" align="left"></td>
						        <td width="10%"></td>
						        <td width="25%" style="text-align: center;">
						        </td>
						  </tr>
						  <tr>
					  		<td class="form_label" style="width: 25px;" align="left">
							  	开始时间:				
							</td>
							<td style="width:10%">
								<input name="START_DATE", id="S_START_DATE" style="width: 120px; height:18px;" type="text" class="datepicker"  title="有效期开始日期" /> 
							</td>
							<td class="form_label" style="width: 25px;" align="left">
								结束时间:
							</td>
							<td  width="10%">
								<input name="END_DATE", id="S_END_DATE" style="width: 120px; height:18px;" type="text" class="datepicker"  title="有效期结束日期"/>
							</td>
							<td></td>
							<td></td>
							 <td width="25%" style="text-align: center;">
						        	<s:textfield  name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px;" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
						          	<script>$("#pageSize").hide();</script>
									<!-- <a href="javascript:void(0);" class="table_btn_orgframe" id="resetbutton" iconCls="icon-reload" onclick="clearSearch();">重置</a>
						        	<a href="javascript:void(0);" class="table_btn_orgframe" id="searchbutton" iconCls="icon-search"  onclick="querySubmit();">查询</a> -->
						        	<a href="javascript:void(0);" class="table_btn_orgframe" id="resetbutton" iconCls="icon-reload">重置</a>
						        	<a href="javascript:void(0);" class="table_btn_orgframe" id="searchbutton" iconCls="icon-search">查询</a>
						        </td>
						  </tr>
					  </table>
				   	</h:panel>
 					<%-- <page:pager id="list_panel" width="100%" buttons="${buttons}" title="结果列表    结果条数:${page.totalCount}">
						<d:table name="page.result" id="row"  export="false" class="table"	requestURI="list.act?exFile=1">
							<d:col property="ORGNAME" class="display_centeralign" title="单位"/>
							<d:col property="DEPARTMENT" class="display_centeralign" title="处室"/>
						    <d:col property="USERNAME" class="display_centeralign" title="姓名"/>
						    <d:col property="PLATENUMBER" class="display_centeralign" title="车牌号"/>
						    <d:col property="TELPHONE" class="display_centeralign" title="电话号码"/>
						</d:table>
					</page:pager> --%>
					<!-- changed to  -->
					<div style="display: none;" id="hidden_fields">
						<input type="hidden" id="org_name_hidden" value=""/>
						<input type="hidden" id="department_hidden" value=""/>
						<input type="hidden" id="car_owner_name_hidden" value=""/>
						<input type="hidden" id="car_owner_phone_hidden" value=""/>
						<input type="hidden" id="car_no_hidden" value=""/>
						<input type="hidden" id="start_date_and_end_date_hidden" value=""/>
						<input type="hidden" id="start_time_and_end_time_hidden" value=""/>
						<input type="hidden" id="status_hidden" value=""/>
						<input type="hidden" id="car_channel_info_hidden" value=""/>
					</div>
					<table id="parking_t" class="pt" cellspacing="0" width="100%">
					    <thead>
					        <tr>
					            <th>单位名称</th>
					            <th>处室</th>
					            <th>姓名</th>
					            <th>车牌</th>
					            <th>电话</th>
					            <th>通道</th>
					            <th>有效期</th>
					            <th>状态</th>
					            <th>操作</th>
					        </tr>
					    </thead>
					</table>
					
				</form>
			</div>
		</div>

		<div style="width: 28%;float: left;margin-right: 10px;">
			<div class="clear"></div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
<script type="text/javascript" src="${ctx}/app/property/parkingInfo/js/height.js"></script>
</body>
</html>