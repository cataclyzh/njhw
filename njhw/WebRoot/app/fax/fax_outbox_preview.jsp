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
<link rel="stylesheet"
	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
<style>
body {
	font-size: 80%;
	font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
		"sans-serif";
}
</style>
</head>

<body style="margin:0;padding:0;" scroll=no>
	<div style="margin:0;padding:0;text-align: center;" id="tifContainer"></div>
	<div style="width: 100%;text-align: center;">
		<input type="button" value="上一个附件" id="previous" /> <input
			type="button" value="下一个附件" id="next" />

	</div>
	<!--
	<object width="0" height="0" id="obj1"
		classid="clsid:106E49CF-797A-11D2-81A2-00E02C015623"
		codebase="alternatiff-selfregister-signed.cab"> </object>
	-->
	<script type="text/javascript" src="js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
	<script type="text/javascript" src="js/queryString.js"></script>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript" ></script>
	<SCRIPT type="text/javascript">
		$(function($) {
			var returnedData = null;
			//返回数组
			var current = 0;
			//
			$(
					"input[type=submit], input[type=button],input[type=reset],a, button")
					.button();

			//窗体高度
			$("#tifContainer").height(
					document.documentElement.clientHeight - 30);
			//init
			$("#tifContainer").html(
					"<embed width=100% height=97% " + " src= '' "
							+ " type='image/tiff'>"

			);

			if (getQueryString("returnedData") != null) {
				returnedData = $.evalJSON(getQueryString("returnedData"));

				$("#tifContainer")
						.html(
								"<embed width=100% height=97% " + " src= '"
										+ "../../" + returnedData[0] + "'"
										+ " type='image/tiff'>");
				$("#previous").click(
						function() {

							if (current == 0) {
								current = returnedData.length - 1;
							} else {
								current--;
							}

							$("#tifContainer").html(
									"<embed width=100% height=97% " + " src= '"
											+ "../../" + returnedData[current]
											+ "'" + " type='image/tiff'>"

							);

						});

				$("#next").click(
						function() {

							if (current == returnedData.length - 1) {
								current = 0;
							} else {
								current++;
							}

							$("#tifContainer").html(
									"<embed width=100% height=97% " + " src= '"
											+ "../../" + returnedData[current]
											+ "'" + " type='image/tiff'>"

							);

						});

			}

		});
	</SCRIPT>

</body>

</html>
