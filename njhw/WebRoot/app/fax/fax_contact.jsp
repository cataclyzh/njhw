<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	//String orgId = request.getParameter("orgId");
	//String type = request.getParameter("type");				//	"tel"|"fax"
	//String selfMac = request.getParameter("smac");	//	当前用户的ip电话MAC地址
	//String selfTel = request.getParameter("stel");	//	当前用户的ip电话号码
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合办公首页</title>
<%@ include file="/common/include/meta.jsp"%>
<link href="${ctx}/app/integrateservice/css/newtree.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
<style type="text/css">
body {
	background: url(a.jpg) left top repeat-x;
}

.hand {
	cursor: pointer;
}

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
.darkblue {
	color: #8090B4;
}
.yellow {
	color: yellow;
}

.grey {
	color: grey;
}

.main_main2_list .item {
	width: 33%;
	float: left;
	text-align: center;
	margin: 0;
}

.main3_cen_list .item {
	width: 33%;
	float: left;
	text-align: center;
}
</style>
</head>
<body style="margin: 0;padding: 0;">
	<div class="main_left_main1" id="treeComm">
		<div class="main_border_01">
			<div class="main_border_02">通讯录</div>
		</div>
		<div class="main_conter" id="treeComm_div" style="height:430px;">
			<div class="main_right3" >
				<div class="clear"></div>
				<div class="main_right3_input" >
					<input type="text" id="searchTJ" onfocus="inputFun(this, 0)"
						onblur="inputFun(this, 1)" value="请输入姓名或传真号" class="input_txt" />
					<input name="" class="input_button" type="button" id="search" />
				</div>
				<div class="reportTips_tips1">
					<div style="cursor: pointer;" class="tips2" id="tab-recent">最近</div>
					<div style="cursor: pointer;" class="tips1" id="tab-all">全部</div>
					<div class="main_img2" style="width: 50px;text-align: right; margin-right: 5px;">
						<a style="cursor:pointer; margin-right: 10px;">
							<img src="${ctx}/app/integrateservice/images/more_SZ.png" width="15" 
							height="15" id="editTree" title="更多" />
						</a>
						
						<a  href="javascript:void(0);" title="刷新" id="refresh_List"> <img
							src="${ctx}/app/integrateservice/images/shipin_2.jpg" width="15"
							height="15" />
						</a>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="address_list" id="address_list"
				style="padding: 0; margin:0;height: 335px;">
				<div class="main1_main2_right" style="margin: 0;">
					<div class="main_main2_list" style="margin: 0;">
						<span class="item">姓名</span> <span class="item">传真号</span> <span
							class="item">操作</span>
					</div>
					<ul id="contact-recent">

					</ul>
					<ul id="contact-all" style="display: none;">
						<!-- <li>
							<div class="main3_cen_list" style="text-align: center;">
								<span class="item">中心管理员</span> <span class="item">88888888</span>
								<span class="item">add</span>
							</div>
						</li> -->
					</ul>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<script type="text/javascript" src="js/fax_contact.js"></script>
	<script type="text/javascript" src="js/queryString.js"></script>
	<script type="text/javascript" src="js/replaceAll.js"></script>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>
</body>
</html>