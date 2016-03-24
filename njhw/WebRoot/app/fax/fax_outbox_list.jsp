<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<title>My JSP 'fax_outbox_list.jsp' starting page</title>

<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
<style>
body {
	background: url(a.jpg) left top repeat-x;
	/* font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
		"sans-serif"; */
}

<!--
--> /* custom css style: pageNum,cPageNum */
.pageNum {
	width: 24px;
	height: 24px;
	line-height: 24px;
	display: inline-block;
	background: #98a6be;
	margin: 5px;
}

.cPageNum {
	width: 24px;
	height: 24px;
	line-height: 24px;
	display: inline-block;
	background: #CCCCCC;
	margin: 5px;
}
/* 
#pageNav a:hover {
	text-decoration: none;
	background: #fff4d8;
} */
.green {
	color: green;
}

.red {
	color: red;
}

.orange {
	color: orange;
}

.black {
	color: black;
}

.white {
	color: white;
}

.blue {
	color: blue;
}

.yellow {
	color: yellow;
}

.grey {
	color: grey;
}

.thead_inbox_col_1 {
	width: 20%;
	float: left;
	/* background-color: #ebebeb; */
	text-align: center;
}

.thead_inbox_col_2 {
	width: 16%;
	float: left;
	/* background-color: #ebebeb; */
	text-align: center;
}

.thead_inbox_col_3 {
	width: auto;
	/* background-color: #ebebeb; */
	text-align: center;
}

.tbody_inbox_col_1 {
	width: 20%;
	float: left;
	color: #7f90b3;
	background: url(border_left.jpg) left center no-repeat;
	text-align: left;
}

.tbody_inbox_col_1 .col1 {
	padding-left: 20px;
}

.tbody_inbox_col_2 {
	width: 16%;
	float: left;
	text-align: center;
}

.tbody_inbox_col_3 {
	width: auto;
	text-align: center;
}

.tbody_inbox_col_3  a {
	width: auto;
	text-align: center;
	color: orange;
	text-decoration: none;
}

/* outbox */
.thead_outbox_col_1 {
	width: 20%;
	float: left;
	/* background-color: #ebebeb; */
	text-align: center;
}

.thead_outbox_col_2 {
	width: 15%;
	float: left;
	/* background-color: #ebebeb; */
	text-align: center;
}

.thead_outbox_col_3 {
	width: auto;
	/* background-color: #ebebeb; */
	text-align: center;
}

.tbody_outbox_col_1 {
	width: 20%;
	float: left;
	color: #7f90b3;
	background: url(border_left.jpg) left center no-repeat;
	text-align: center;
}

.tbody_outbox_col_1 .col1 {
	padding-left: 5px;
	width: 100%;
}

.tbody_outbox_col_2 {
	width: 15%;
	float: left;
	text-align: center;
}

.tbody_outbox_col_3 {
	width: auto;
	text-align: center;
}

.tbody_outbox_col_3  a {
	width: auto;
	text-align: center;
	color: orange;
	text-decoration: none;
}

.main_pagination {
	background: #e0e3ea;
	text-align: center;
	width: 99%;
	margin: 0 auto;
}

.main_pagination a {
	text-decoration: none;
	color: white;
}
.main2_border_01 p {
	padding: 0;
	margin: 0;
	height: 30px;
	line-height: 30px;
	padding-left: 12px;
	background: url(../images/border_left2.jpg) left center no-repeat;
	font-size: 14px;
	font-family: "微软雅黑";
	font-weight: bold;
	color: #808080;
}
</style>

</head>

<body style="margin:0;padding:0;overflow-x:hidden">
	<div class="main_left_main1">
		<div class="main_border_01">
			<div class="main_border_02">发件箱</div>
		</div>
		<div class="main_conter">
			<div class="main1_fax_top"	style="margin-top: 5px;width: 97%;text-align: right; position: absolute;">
				<form action="" method="get">
					<input type="text" id="from" onfocus="this.blur()" 
						onClick="WdatePicker({isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'to\',{M:-0,d:0})||\'2020-10-01\'}'})" />
					<img 
						src="${ctx}/scripts/widgets/My97DatePicker/skin/datePicker.gif" width="16" height="22"
						align="absmiddle" />&nbsp;至&nbsp; <input type="text" id="to" onfocus="this.blur()" 
						onClick="WdatePicker({isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'from\',{M:-0,d:0})}',maxDate:'2020-10-01'})" />
					<img 
						src="${ctx}/scripts/widgets/My97DatePicker/skin/datePicker.gif" width="16" height="22"
						align="absmiddle" />&nbsp;<input name=""
						type="button" id="search" value="搜索" style="padding-top: 2px;padding-bottom: 2px;"/>
				</form>
			</div>
			<div class="main1_main2_right" id="fax_list_today_container">
				<div class="main2_border_02">
					<div class="main1_p">
						<label style="background-color: white;">今日传真</label>
						<span  style="display: none;">
							[共<span id="fax_list_today_count">0</span>封]
						</span>
					</div>
					<div class="clear2"></div>
				</div>
				<div class="main_main2_list">
					<div class="thead_outbox_col_1">主题</div>
					<div class="thead_outbox_col_2">主叫号码</div>
					<div class="thead_outbox_col_2">被叫号码</div>
					<div class="thead_outbox_col_2">提交时间</div>
					<div class="thead_outbox_col_2">发送状态</div>
					<div class="thead_outbox_col_3">&nbsp;&nbsp;操作</div>

				</div>
				<ul id="fax_list_today">


				</ul>
			</div>
			<div class="main1_main2_right" id="fax_list_yesterday_container">
				<div class="main2_border_02">
					<div class="main1_p">
						<label style="background-color: white;">昨日传真</label>						
						<span  style="display: none;">
							[共<span id="fax_list_yesterday_count">0</span>封]
						</span>
					</div>
					<div class="clear2"></div>
				</div>
				<div class="main_main2_list">
					<div class="thead_outbox_col_1">主题</div>
					<div class="thead_outbox_col_2">主叫号码</div>
					<div class="thead_outbox_col_2">被叫号码</div>
					<div class="thead_outbox_col_2">提交时间</div>
					<div class="thead_outbox_col_2">发送状态</div>
					<div class="thead_outbox_col_3">&nbsp;&nbsp;操作</div>
				</div>
				<ul id="fax_list_yesterday">


				</ul>
			</div>
			<div class="main1_main2_right" id="fax_list_older_container">
				<div class="main2_border_02">
					<div class="main1_p">
						<label style="background-color: white;">更早传真</label>
						<span  style="display: none;">
							[共<span id="fax_list_older_count">0</span>封]
						</span>
					</div>
					<div class="clear2"></div>
				</div>
				<div class="main_main2_list">
					<div class="thead_outbox_col_1">主题</div>
					<div class="thead_outbox_col_2">主叫号码</div>
					<div class="thead_outbox_col_2">被叫号码</div>
					<div class="thead_outbox_col_2">提交时间</div>
					<div class="thead_outbox_col_2">发送状态</div>
					<div class="thead_outbox_col_3">&nbsp;&nbsp;操作</div>
				</div>

				<ul id="fax_list_older">


				</ul>
			</div>
			<div class="main_pagination" id="pagination">
				<div id="pageNav"></div>
				<!-- <a href="javascript:void(0);">&laquo; </a><a href="javascript:void(0);">&lsaquo; </a><a href="javascript:void(0);">1</a><a
					href="javascript:void(0);">2 </a><a href="javascript:void(0);">3 </a><a href="javascript:void(0);">4</a><a href="javascript:void(0);">...
				</a><a href="javascript:void(0);">&rsaquo; </a><a href="javascript:void(0);">&raquo; </a> -->
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<script type="text/javascript"
		src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/fax_outbox_list.js"></script>
	<script type="text/javascript" src="js/date.js"></script>
	<script type="text/javascript" src="js/pagenav1.1.min.js"></script>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
</body>


</html>
