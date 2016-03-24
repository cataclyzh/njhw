<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<title>My JSP 'fax_snd.jsp' starting page</title>
<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<script type="text/javascript" src="js/queryString.js"></script>

<!--  -->


<style>
body {
	background: url(a.jpg) left top repeat-x;
}
.label {
	padding-top: 2px;
	padding-bottom: 2px;
}

h2 {
	font-size: large;
	font-weight: normal;
}

textarea {
	width: 100%;
	font-size: medium;
}

input[type="text"] {
	height: 22px;
	width: 100%;
	font-size: medium;
	font-weight: normal;
}
#preview,#send {
	cursor:pointer;
}
.main3_left_01 table tr td p a {
	text-decoration: none;
}
</style>
</head>

<body style="height: auto;">
	<div class="main_border_01">
		<div class="main_border_02">发传真</div>
	</div>
	<div class="main_conter" >
		<div style="width: 62%;float: left;margin-top: 10px;">
			<form action="sendFax.act" method="post" class="main3_from"
				id="fax_form" enctype="multipart/form-data" name="fax_form">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="main_from_left">收件人</td>
						<td><input name="fo_to" id="fo_to" class="main_input"
							type="text" size="70""/>
						</td>
					</tr>
					<tr>
						<td class="main_from_left">主题</td>
						<td><input name="fo_subject" id="fo_subject" class="main_input" type="text"
							size="70""/></td>
					</tr>
					<tr>
						<td valign="top" class="main_from_left">正文</td>
						<td style="padding-top:3px;"><textarea name="fo_body"
								id="fo_body" class="main_input" cols="70" rows="18" ></textarea>
						</td>
					</tr>
					<tr>
						<td class="main_from_left">附件列表</td>
						<td id="fax_td">
							<p style="padding-top:6px;padding-bottom: 6px">
								<input type="file" name="fax_attach" />&nbsp;&nbsp;
								<a href="javascript:void(0)" id="addNewAttach">继续添加</a>&nbsp;&nbsp;
								<a href="javascript:void(0)" id="clearAttach" style="display:none;">清除附件</a>
								
							</p>
						</td>
					</tr>


					<tr>
						<td class="main_from_left">&nbsp;</td>
						<td class="main_from_right1"><input name="urgent"
							type="checkbox" value="1" style="vertical-align: text-bottom;" />&nbsp;加急传真</td>
					</tr>
					<tr>
						<td class="main_from_left">&nbsp;</td>
						<td><input name="preview" id="preview" class="input_button2" type="button" />
							<input name="send" id="send" class="input_button3" type="button" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div
			style="width:35%;height:480px; float: left;margin-left: 20px;">
			<div class="clear"></div>
			<iframe src="${ctx}/app/integrateservice/contact.act" width="100%" height="100%" scrolling="no"
				frameborder="0"></iframe>
			<%-- <jsp:include
				page="fax_public_book.jsp?orgId=70&type=tel&smac=${smac}&stel=${stel}">
				<jsp:param value="" name="" />
			</jsp:include> --%>

			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>

	<script type="text/javascript">
		function frameDialogClose() {

		}
		function fun() {
			//showShelter('680', '570', '${ctx}/app/guide/index.html');
			window.parent.openGuide();
		}
		function importNDRC() {

			window.parent.importNDRCDoList();
		}
		//附件类型判断标志
		var flag = false;

		function checkFileName() {
			//alert($("input[name=fax_attach]").val());
			$("input[name=fax_attach]")
					.change(
							function() {

								var fileName = $(this).val();
								//['.txt', '.pdf', '.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx',
								// '.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tif', '.tiff' ]
								var ext = ".txt|.pdf|.doc|.docx|.xls|.xlsx|.ppt|.pptx|.jpg|.jpeg|.png|.gif|.bmp|.tif|.tiff|";

								if (fileName.indexOf(".") != -1) {
									var position = fileName.lastIndexOf(".");
									var subExt = fileName.substring(position,
											fileName.length)
											+ "|";

									if (ext.indexOf(subExt.toLowerCase()) == -1) {

										//alert("不支持此类文件格式！");
										window.parent.showMessageBox("不支持此类文件格式！");
										
										//this.outerHTML += "";
										//$(this).parent().remove();
										flag = false ;
										return false;
										
									} 

								}

							});
		}
		
		$(function() {

			checkFileName();

			/* 页面控制 */
			parent.document.getElementById("hid_sendStatus").value = "";
			// 控制父页面高度
			//$("#main_frame_left", window.parent.document).height(window.parent.document.documentElement.clientHeight - 220);
			//$("#main_frame_right", window.parent.document).height(window.parent.document.documentElement.clientHeight - 220);
			$("#main_frame_left", window.parent.document).height(
					document.body.clientHeight + 60);
			$("#main_frame_right", window.parent.document).height(
					document.body.clientHeight + 60);

			//预览
			$("input[name=preview]")
					.click(
							function() {
								/*
									1,提交
									2,等待转换	
									3,下载附件，解压保存本地
									4,传附件地址
									5,取消=删除此传真
								 */

								if (document.getElementById("fo_to").value == "") {
									//alert("收件人不能为空！");
									window.parent.showMessageBox("收件人不能为空！");

									return false;
								}
								
								

								if (!flag) {
									
									//如果只有一个file
									var len = $("input[name=fax_attach]").length;
									if (len == 1) {
										
										//附件为空可发送
										var value = $("input[name=fax_attach]").val();
										if (value == ""){
											
											$(".main3_from").attr("action",
											"faxPreview.act");
											window.parent.showConfirmBox("<p>预览需要较长时间，请耐心等待。</p><p>是否开始预览？</p>",function(){$(".main3_from").submit();});
											
											
											
										}else {
											
											//有附件的话要验证
											
											var fileName = $("input[name=fax_attach]").val();
											//['.txt', '.pdf', '.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx',
											// '.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tif', '.tiff' ]
											var ext = ".txt|.pdf|.doc|.docx|.xls|.xlsx|.ppt|.pptx|.jpg|.jpeg|.png|.gif|.bmp|.tif|.tiff|";
			
											if (fileName.indexOf(".") != -1) {
												var position = fileName.lastIndexOf(".");
												var subExt = fileName.substring(position,
														fileName.length)
														+ "|";
			
												if (ext.indexOf(subExt.toLowerCase()) == -1) {
			
													//alert("不支持此类文件格式！");
													window.parent.showMessageBox("上传附件类型不匹配！");
													
													//this.outerHTML += "";
													//$(this).parent().remove();
													flag = false ;
													return false;
													
												} 
			
											}
											
											
											$(".main3_from").attr("action",
											"faxPreview.act");
											window.parent.showConfirmBox("<p>预览需要较长时间，请耐心等待。</p><p>是否开始预览？</p>",function(){$(".main3_from").submit();});
											
										}

									}
									//多个附件
									else{
										
										//获取 file 个数
										var files = $("input[type='file']");
										var arr = [];
										files.each(function() {
											//alert($(this).val());
											arr.push($(this).val());
										});
										
										//['.txt', '.pdf', '.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx',
										// '.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tif', '.tiff' ]
										var ext = ".txt|.pdf|.doc|.docx|.xls|.xlsx|.ppt|.pptx|.jpg|.jpeg|.png|.gif|.bmp|.tif|.tiff|";
										
										for (var i=0;i<arr.length;i++){
											
											var fileName =  arr[i];
											
											
											if (fileName.indexOf(".") != -1) {
												var position = fileName.lastIndexOf(".");
												var subExt = fileName.substring(position,
														fileName.length)
														+ "|";
					
												if (ext.indexOf(subExt.toLowerCase()) == -1) {
					
													//alert("不支持此类文件格式！");
													window.parent.showMessageBox("上传附件类型不匹配！");
													
													return false;
													break;
													
												} 
					
											}
										}
										
										$(".main3_from").attr("action",
										"faxPreview.act");
										window.parent.showConfirmBox("<p>预览需要较长时间，请耐心等待。</p><p>是否开始预览？</p>",function(){$(".main3_from").submit();});
										
									}

								}

							});

			//表单提交
			$("input[name=send]").click(function() {
				/*
				var files = $("input[type='file']");
				files.each(function() {
					alert($(this).val());
				});
				 */
				if (document.getElementById("fo_to").value == "") {
					//alert("收件人不能为空！");
					window.parent.showMessageBox("收件人不能为空！");
					return false;
				}
				if (!flag) {
					
					//如果只有一个file
					var len = $("input[name=fax_attach]").length;
					if (len == 1) {
						
						//附件为空可发送
						var value = $("input[name=fax_attach]").val();
						if (value == ""){
							$(".main3_from").submit();
							
						}else {
							
							//有一个附件
							var fileName = $("input[name=fax_attach]").val();
							//['.txt', '.pdf', '.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx',
							// '.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tif', '.tiff' ]
							var ext = ".txt|.pdf|.doc|.docx|.xls|.xlsx|.ppt|.pptx|.jpg|.jpeg|.png|.gif|.bmp|.tif|.tiff|";

							if (fileName.indexOf(".") != -1) {
								var position = fileName.lastIndexOf(".");
								var subExt = fileName.substring(position,
										fileName.length)
										+ "|";

								if (ext.indexOf(subExt.toLowerCase()) == -1) {

									//alert("不支持此类文件格式！");
									window.parent.showMessageBox("上传附件类型不匹配！");
									
									//this.outerHTML += "";
									//$(this).parent().remove();
									flag = false ;
									return false;
									
								} 

							}
							
							//格式正确提交
							$(".main3_from").submit();
							
							
						}

					}
					//多个附件
					else{
						
						//获取 file 个数
						var files = $("input[type='file']");
						var arr = [];
						files.each(function() {
							//alert($(this).val());
							arr.push($(this).val());
						});
						
						//['.txt', '.pdf', '.doc', '.docx', '.xls', '.xlsx', '.ppt', '.pptx',
						// '.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tif', '.tiff' ]
						var ext = ".txt|.pdf|.doc|.docx|.xls|.xlsx|.ppt|.pptx|.jpg|.jpeg|.png|.gif|.bmp|.tif|.tiff|";
						
						for (var i=0;i<arr.length;i++){
							
							var fileName =  arr[i];
							
							if (fileName.indexOf(".") != -1) {
								var position = fileName.lastIndexOf(".");
								var subExt = fileName.substring(position,
										fileName.length)
										+ "|";
	
								if (ext.indexOf(subExt.toLowerCase()) == -1) {
	
									//alert("不支持此类文件格式！");
									window.parent.showMessageBox("上传附件类型不匹配！");
									
									return false;
									break;
									
								} 
	
							}
						}
						
						$(".main3_from").submit();
						
					}

				}
				

			});

			//继续添加附件
			$("#addNewAttach").click(function() {
				
				
				
				$("#fax_td").append(
						'<p style="padding-top:6px;padding-bottom: 6px"><input type="file" name="fax_attach" />'
								+ '&nbsp;&nbsp;<a href="javascript:void(0)" class="delete">删除</a></p>');
	
				$(".delete").click(function() {
							//alert($(this).parent().parent().html());
							//删除当前行
							$(this).parent().remove();
							// 控制父页面高度
							$("#main_frame_left",window.parent.document).height(document.body.clientHeight + 20);
							$("#main_frame_right",window.parent.document).height(document.body.clientHeight + 20);
						});

			checkFileName();
			// 控制父页面高度
			$("#main_frame_left", window.parent.document).height(document.body.clientHeight + 20);
			$("#main_frame_right", window.parent.document).height(document.body.clientHeight + 20);
			});		
			
			
			
			//如果session中有fo_body 值放到textarea里
			
			//alert(getQueryString("sid"));
			if (getQueryString("sid") == "cancel"){
			
				//textarea 回显
				var fo_body = "${fo_body}";
				$("#fo_body").html(fo_body);
				//主题回显
				var fo_subject = "${fo_subject}";
				$("#fo_subject").val(fo_subject);
				//收件人回显
				var fo_to = "${fo_to}";
				$("#fo_to").val(fo_to);
			
				
			}
			
			$("input[type=file]:eq(0)").change(function(){
				
				$("#clearAttach").css("display","inline");
				
			});
			
			$("#clearAttach").click(function(){
				
				var obj = $("input[type=file]:eq(0)");
				//alert(obj.get(0).outerHTML);
				obj.get(0).outerHTML += "";
				
				//$(this).hide();
				
				//checkFileName();
				
				/* $("input[type=file]:eq(0)").change(function(){
					
					$("#clearAttach").css("display","inline");
					
				}); */
				
				/* $(this).parent().find("input[type=file]").change(function(){
					
					alert("Fuck");
					
				}); */
				
			});
			

		});
	</script>
</body>
</html>
