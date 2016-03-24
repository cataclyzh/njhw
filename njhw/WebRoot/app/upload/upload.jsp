<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: hp
- Date: 2013-05-24
- Description: 发改委代办事项
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发改委代办事项</title>
<%@ include file="/common/include/meta.jsp"%>
<link rel="stylesheet"
	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
<style>
body {
	font-size: medium;
	/* font-family: "Trebuchet MS", "Arial", "Helvetica", "Verdana",
		"sans-serif"; */
}

p {
	padding: 10px;
}

.toggler {
	width: 100%;
	height: 200px;
}

#button {
	padding: .5em 1em;
	text-decoration: none;
}

#uploader {
	height: 200px;
	padding: 0.4em;
	width: 500px;
	margin-top: 10%;
	margin-left: auto;
	margin-right: auto;
	position: relative;
	margin-top: 5%;
	margin-left: auto;
	margin-left: auto;
}

#uploader h3 {
	margin: 0;
	padding: 0.4em;
	text-align: center;
}

#progressbar .ui-progressbar-value {
	background-color: #ccc;
}

.ui-progressbar {
	position: relative;
}

.progress-label {
	position: absolute;
	left: 39%;
	top: 5px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}

.modal {
	overflow: hidden;
	display: none;
	text-align: center;
	padding: 0;
}
</style>
</head>
<body>
	<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
	<div class="main_index">
		<div>
			<Iframe src="${ctx}/app/upload/header.jsp" width="100%" height="99"
				scrolling="no" frameborder="0"></iframe>
		</div>
		<div style="padding: 10px;">
			<div class="main_border_01">
				<div class="main_border_02">发改委代办事项导入</div>
			</div>
			<div class="main_conter" style="height: 450px;">
				<div class="toggler">
					<div id="uploader" class="ui-widget-content ui-corner-all">
						<!-- <h3 class="ui-widget-header ui-corner-all">发改委代办事项导入</h3> -->

						<form id="inputForm" action="upload.act" method="post"
							autocomplete="off" enctype="multipart/form-data">
							<div
								style="width: 480px;background: #8090B2;color: white;font-weight: bold;text-align: center;border: solid;border-color: #7F90B3;border-width: 1px;padding: 10px;font-size: medium;">导入代办文件</div>

							<table align="center" border="0"
								style="width: 500px;height: 100%; margin-top: 5%;">
								<tr>
									<td style="width: 25%;text-align: center;">上传文件：</td>
									<td style="text-align: center;"><input name="file" type="file" style="width: 90%;height:30px;line-height:30px;"
										id="file" /></td>
								</tr>
								<tr style="height: 40px;">
									<td colspan="2"><div id="progressbar"
											style="display: none;">
											<div class="progress-label">正在上传中,请等待......</div>
										</div></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: center;">
										<table style="width: 100%;">
											<tr>
												<td style="width: 50%;text-align: right;padding-right:50px;"><input
													type="button" onclick="saveData();" value="上传"
													style="width: 100px;" /></td>
												<td style="width: 50%;text-align: left;padding-left: 50px;"><input
													type="reset" id="resetbutton" value="重置"
													style="width: 100px;" /></td>
											</tr>
										</table>
									</td>

								</tr>
							</table>


						</form>

					</div>
				</div>
			</div>
		</div>
		<DIV class="clear"></DIV>
		<div>
			<Iframe src="${ctx}/app/upload/footer.jsp" width="100%" height="103"
				scrolling="no" frameborder="0"></iframe>
		</div>
	</div>


	<div id="dialog-message" class="modal" title="消息">
		<p>content</p>
	</div>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"
		type="text/javascript"></script>

	<script type="text/javascript">
		
		$(function() {
			$(
					"input[type=submit],input[type=button],input[type=reset], button")
					.button().click(function(event) {
						//event.preventDefault();
					});
			var progressbar = $("#progressbar"), progressLabel = $(".progress-label");

			progressbar.progressbar({
				value : false,

			});

		});

		/**
		 * @description 消息对话框
		 * 
		 * @param content
		 *            内容
		 * @param func
		 *            关闭对话框时的回调函数,可为空
		 */
		function showMessageBox(content, func) {
			$("#dialog-message").find("p").css("font-family","Verdana,Arial,sans-serif").html(content);
		
			$("#dialog-message").clone().dialog({
				modal : true,
				draggable : true,
				resizable : false,
				closeText : "关闭",
				buttons : {
		
					"确定" : function() {
						$(this).dialog("close");
					}
				},
				open : function() {			
					$(".ui-button").blur();
				},
				close : function() {
					if (func != null)
						func();
				}
			});
		}
		function saveData() {
			var file = $("#file").val();
			file = file.substring(file.length - 3, file.length);
			if (file == "xml") {
				if (file != "") {
					$("#progressbar").show();
					//$("#loadingdiv").show();
					//$('a.easyui-linkbutton').linkbutton('disable');
					$("#inputForm").submit();
				} else {
					showMessageBox("上传文件不能为空！");
					//alert("提示信息', '上传文件不能为空！");
					//easyAlert('提示信息', '上传文件不能为空！', 'info');
				}
			} else {
				showMessageBox("请上传xml文档！");
				//alert("请上传xml文档！");
				//easyAlert('提示信息', '请上传xml文档！', 'info');
			}
		}

		<c:if test="${isSuc=='true'}">
		showMessageBox("上传成功！");
		//alert("上传成功！");
		//easyAlert('提示信息', '上传成功！', 'info');
		</c:if>
		<c:if test="${isSuc=='false'}">
		showMessageBox("上传失败！");
		//alert("上传失败！");
		//easyAlert('提示信息', '上传失败！', 'error');
		</c:if>
	</script>
</body>
</html>
