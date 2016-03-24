<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<title>预览</title>


<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
body {
	font-size: 80%;
	font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
		"sans-serif";
}
</style>
</head>

<body style="margin:0;padding:0;" scroll=no>
	<div style="margin:0;padding:0; align=" center" id="tifContainer"></div>
	<!--
	<object width="0" height="0" id="obj1"
		classid="clsid:106E49CF-797A-11D2-81A2-00E02C015623"
		codebase="alternatiff-selfregister-signed.cab"> </object>
		-->
	<script type="text/javascript" src="js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="js/queryString.js"></script>
	<SCRIPT type="text/javascript">
		$(function($) {
			var url_fi = "http://10.254.101.100/ipcom/index.php/Api/fi_download?";
			var url_fo = "http://10.254.101.100/ipcom/index.php/Api/fo_download?";
			var url;
			if (getQueryString("type") != null) {
				if (getQueryString("type") == "fi")
					url = url_fi;
				if (getQueryString("type") == "fo")
					url = url_fo;
			}
			if (getQueryString("id") != null) {
				url += "id=" + getQueryString("id");
			}
			if (getQueryString("session_id") != null) {
				url += "&session_id=" + getQueryString("session_id");
			}
			$("#tifContainer").height(document.documentElement.clientHeight);
			$("#tifContainer").html(
					'<embed width="100%" height="100%"  src="' + url
							+ '" type="image/tiff">');

		});
	</SCRIPT>

</body>

</html>
