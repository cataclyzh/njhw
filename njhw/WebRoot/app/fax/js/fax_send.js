(function() {
	'use strict';
	
	$("input[type=submit],input[type=button],input[type=reset],button").button().click(function(event) {
		// event.preventDefault();
	});
	$(function() {
		// post请求,获取当前登录用户网络传真号码
		$.post(
		// 请求action
		"getWebFaxNum.act", 
		function(returnedData, textstatus) {
			if (returnedData){
				/**
				 * 查询到网络传真号码
				 * {TEL_NUM=68787485}
				 */
				var current = $.trim($("#fo_sender").val());
				$("#fo_sender").val(current + "(" + returnedData[0].TEL_NUM + ")");
			}
		}, "json");
		
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
		
		$("#reset").click(function(){
			window.location.replace("fax_send.jsp");
		});
		//$("#send").focus();
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
								
								var faxReciever = $("#fax-hidden").val();
								var regex = /[0][2][5](\d+)/g;
								$("#fax-hidden").val(faxReciever.replace(regex,"$1"));
								
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
				
				//fax-hidden 有025开头的收件人信息（去掉025）
				
				var faxReciever = $("#fax-hidden").val();
				var reg = /[\-+]/g;
				var regex = /[0][2][5](\d+)/g;
				$("#fax-hidden").val(faxReciever.replace(reg,"").replace(regex,"$1"));
				
				//如果只有一个file
				var len = $("input[name=fax_attach]").length;
				if (len == 1) {
					
					//附件为空可发送
					var value = $("input[name=fax_attach]").val();
					if (value == ""){
						$(".main3_from").submit();
						$("#main_frame_left", window.parent.document).contents().find("#btn_outbox").parent().attr("class","btn_outbox_selected");
						$("#main_frame_left", window.parent.document).contents().find("#btn_inbox").parent().attr("class","main1_lnbox");
						$("#main_frame_left", window.parent.document).contents().find("#btn_send").parent().attr("class","main1_send");
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
						$("#main_frame_left", window.parent.document).contents().find("#btn_outbox").parent().attr("class","btn_outbox_selected");
						$("#main_frame_left", window.parent.document).contents().find("#btn_inbox").parent().attr("class","main1_lnbox");
						$("#main_frame_left", window.parent.document).contents().find("#btn_send").parent().attr("class","main1_send");
						
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
					$("#main_frame_left", window.parent.document).contents().find("#btn_outbox").parent().attr("class","btn_outbox_selected");
					$("#main_frame_left", window.parent.document).contents().find("#btn_inbox").parent().attr("class","main1_lnbox");
					$("#main_frame_left", window.parent.document).contents().find("#btn_send").parent().attr("class","main1_send");
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
		
		
		$("#delete-receiver").click(function(){
			
			
			//var topDom = window.top;
			//var doc = $(topDom);
			
			window.parent.delReceiverBox();
			
		//	$("#fo_to").val("");
		//	$("input[name=fo_to]").val("");
			
		});
		

	});
	
	

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
	
	$("#add-receiver").click(function(){
		
		window.parent.showAddReceiverBox();
		
	});
	
	
	
	
	
}()); // $(function()) 结束