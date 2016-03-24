
$(document).ready(function(){			
			$("#modifyform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
				modify_conferencePackageName:{
					required: true,
					maxlength:20,
					remote: {
　　						type:"POST",
　　						url:"checkConferencePackageName.act",
						async:false,
　　 					data:{
　　 						conferencePackageName:function(){return $("#modify_conferencePackageName").val();},
　　 						oldConferencePackageName:function(){return $("#old_modify_conferencePackageName").val();}
　　 					}
					}
				},
				modify_conferencePackageRoom:{
					required: true,
					maxlength:10,
					remote: {
　　						type:"POST",
　　						url:"checkConferencePackageRoom.act",
						async:false,
　　 					data:{
　　 						conferencePackageRoom:function(){return $("#modify_conferencePackageRoom").val();},
　　 						oldConferencePackageRoom:function(){return $("#old_modify_conferencePackageRoom").val();}
　　 					}
					}
				},
				modify_conferencePackageLocation:{
					required: true,
					maxlength:50
					},
				modify_conferencePackageSeat:{
					required: true,
					maxlength:50
					},
				modify_conferencePackagePrice:{
					required: true,
					maxlength:10
					},
				modify_conferencePackageFacility:{
					maxlength:50
					},
				modify_conferencePackageStyle:{
					maxlength:50
					},
				modify_conferencePackageService:{
					maxlength:255
					}
					
					},
					messages:{
						modify_conferencePackageName:{
							required: "请输入套餐名称",
						  	maxlength: "输入的套餐名称过长",
						  	remote:"输入的套餐名称已存在"
						},
						modify_conferencePackageRoom:{
							required: "请填写会场编号",
							maxlength:"输入的会场编号内容过长",
							remote:"输入的会场编号已存在"
						},
						
						modify_conferencePackageLocation:{
							required: "请填写会场地址",
							maxlength:"输入的会场地址过长"
						},
						
						modify_conferencePackageSeat:{
							required: "请填写会场座位",
							maxlength:"输入的会场座位内容过长"
						},
						
						modify_conferencePackagePrice:{
							required: "请填写价目",
							maxlength:"输入的价目内容过长"
						},
						
						modify_conferencePackageFacility:{
							maxlength:"输入的会场设施内容过长"
						},
						
						modify_conferencePackageStyle:{
							maxlength:"输入的布局样式内容过长"
						},
						
						modify_conferencePackageService:{
							maxlength:"输入的服务项目内容过长"
						}	
						
					}
				});
			});    
			
			
				function saveData(){		
				if ($("#modifyform").valid()&&$("#modify_conferencePackageName").valid()&&$("#modify_conferencePackageRoom").valid()) {
					$('#modifyform').submit();
                    closeEasyWin("modifyOneConferencePackagePrepare");
				}else{
					return;
				}
			}
			
