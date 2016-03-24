
$(document).ready(function(){			
			$("#addform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
				    add_conferencePackageName:{
					maxlength:20
					},
					add_conferencePackageRoom:{
					maxlength:20
					},
					add_conferencePackageLocation:{
					maxlength:50
					},
					add_conferencePackageSeat:{
					maxlength:50
					},
					add_conferencePackagePrice:{
					maxlength:20
					},
					add_conferencePackageFacility:{
					maxlength:50
					},
					add_conferencePackageStyle:{
					maxlength:50
					},
					add_conferencePackageService:{
					maxlength:255
					}
					
					},
					messages:{
					    add_conferencePackageName:{
						maxlength:"套餐名称字符过长"
						},
						add_conferencePackageRoom:{
						maxlength:"会场编号字符过长"
						},
						add_conferencePackageLocation:{
						maxlength:"会场地址字符过长"
						},
						add_conferencePackageSeat:{
						maxlength:"会场座位字符过长"
						},
						add_conferencePackagePrice:{
						maxlength:"价目字符过长"
						},
						add_conferencePackageFacility:{
						maxlength:"其他设施字符过长"
						},
						add_conferencePackageStyle:{
					    maxlength:"布局样式字符过长"
						},
						add_conferencePackageService:{
					    maxlength:"服务项目字符过长"
						}
					}
				});
			});    
			
			
				function saveData(){		
                                  
				if (showRequest()) {
					var add_conferencePackageName=$("#add_conferencePackageName").val();
					var add_conferencePackageRoom=$("#add_conferencePackageRoom").val();
					  $.ajax({
				             type:"POST",
				             url:"checkAddConferencePackage.act",
				             data:{add_conferencePackageName:add_conferencePackageName,add_conferencePackageRoom:add_conferencePackageRoom},
				             dataType: 'json',
						     async : false,
						     success: function(json){
				             if (json.status == 0) {
				            
						            easyAlert("提示", json.message, "confirm");
				             }
			                 if(json.status == 1){
			                	 $('#addform').submit();   
			                 
			                 }    
				          }
				      });

									
				}
			}
			
			function showRequest(){
				 return $("#addform").valid();
			}