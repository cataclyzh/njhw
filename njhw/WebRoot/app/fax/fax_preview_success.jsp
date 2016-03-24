<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>My JSP 'send_success.jsp' starting page</title>

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
#progressbar .ui-progressbar-value {
	background-color: #ccc;
}

.ui-progressbar {
	position: relative;
}

.progress-label {
	position: absolute;
	left: 35%;
	top:8px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}
</style>
</head>

<body>
	

	<div style="margin-top: 100px; margin-left: 20px;margin-right: 20px;"
		id="progressbar">
		<div class="progress-label">正在转换附件，请稍等，请勿关闭此窗口！</div>
	</div>
	<div align="center" style="margin-top: 80px;">
		<input type="button" value="取消预览" id="cancel" style="width: 120px;font-size: medium;"/>
	</div>
	<script type="text/javascript" src="js/jquery-1.10.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript" ></script>
	
	<script type="text/javascript">
		$(function() {
			$("input[type=submit],input[type=button],input[type=reset], input[type=file],button")
			.button().click(function(event) {
				// event.preventDefault();
			});
			var progressbar = $("#progressbar"), progressLabel = $(".progress-label");

			progressbar.progressbar({
				value : false,
				
			});
			
			$("#cancel").click(function(){
				$.post("faxCancel.act", function(result, status) {
					if (result.data == "ok") {
						//window.location.replace("fax_send.jsp?sid=cancel");
						//$("#main_frame_right",window.top.document).attr("src", "fax_send.jsp?sid=cancel");
					}else{
						//window.top.showMessageBox("取消预览失败，请稍后再试。");
					}								
				});
				window.location.replace("fax_send.jsp?sid=cancel");
			});
			
			$("#main_frame_left", window.parent.document).height(
					window.parent.document.documentElement.clientHeight - 220);
			$("#main_frame_right", window.parent.document).height(
					window.parent.document.documentElement.clientHeight - 220);

			//window.parent.showPreviewWaiting("转换中，请勿关闭此窗口！");
		});

		//$("#query").submit();
		var intervalID = window.setInterval(function() {

			$.post("queryFax.act",
					function(result, status) {
						//alert(returnedData.data.list[0].id);
						//有记录返回
						//alert(result.data.total);
						/**
						 * 有记录返回,状态已经发送
						 */
						if (result.data.total != 0
								&& result.data.list[0].status == 90) {

							//根据ID查询传真,后台下载传真附件、解压、tif路径传过来
							$.post("downloadFax.act", {
								fo_id : result.data.list[0].id
							},

							function(returnedData, status) {
								/**
								 * alert(returnedData);
								 * 接收 JSONArray[1.jpg.tif, 2.jpg.tif, body.txt.tif]
								 * 参数解析传给ActiveX控件
								 */

								//window.showModalDialog("fax_outbox_preview.jsp",returnedData,"scrollbars=no;statusbars=no;resizable=no;help=no;status=no;dialogHeight=640px;dialogwidth=550px;");
								//alert($.toJSON(returnedData));
								//打开预览
								window.parent.showOutboxPreview($.toJSON(returnedData));
							});

							window.clearInterval(intervalID);
						}
					}

			);

		}, 5000);
	</script>
</body>
</html>
