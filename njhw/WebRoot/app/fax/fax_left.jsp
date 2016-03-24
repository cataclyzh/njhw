<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<title>My JSP 'fax_left.jsp' starting page</title>

<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">

.btn_inbox_selected a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/fex_lnbox.jpg) left top no-repeat;
	}

.btn_send_selected a {
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/send_fax1.jpg) left top no-repeat;
	}
.btn_outbox_selected a{
	width:327px;
	height:70px;
	display:block;
	background:url(${ctx}/app/integrateservice/images/fax_been1.jpg) left top no-repeat;
	}
</style>
</head>

<body style="margin:0;padding:0;background: white;" scroll=no>
	<div class="main1_main2_left1">
		<ul>
			<li class="btn_inbox_selected"><a id="btn_inbox" href="javascript:void(0);"></a></li>
			<li class="main1_send"><a id="btn_send" href="javascript:void(0);"></a>
			</li>
			<li class="main1_been"><a id="btn_outbox" href="javascript:void(0);"></a></li>
		</ul>
	</div>
	<script type="text/javascript" src="js/queryString.js"></script>
	<script type="text/javascript" src="js/fax_left.js"></script>
</body>

</html>
