
$(document).ready(function(){
		
				$("#addDeviceTypeForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					deviceTypeNo:{
					required:true,
					maxlength:20
				    },
					deviceTypeName:{
					required:true,
					maxlength:20
				    },
				    deviceTypeDescribe:{
				    maxlength:255
				    }
					
					},
					messages:{
					deviceTypeNo:{
					required:"请输入设备类型编号",
					maxlength:"设备类型编号字符过长"	
					},
					deviceTypeName:{
					required:"请输入设备类型名称",
					maxlength:"设备类型名称字符过长"	
				    },
				    deviceTypeDescribe:{
				    maxlength:"设备类型描述过长"
				    }
				 	
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					var deviceTypeNo=$("#deviceTypeNo").val();
					var deviceTypeName=$("#deviceTypeName").val();
					  $.ajax({
				             type:"POST",
				             url:"checkDeviceType.act",
				             data:{deviceTypeNo:deviceTypeNo,deviceTypeName:deviceTypeName},
				             dataType: 'json',
						     async : false,
						     success: function(json){
				             if (json.status == 0) {
				            
						            easyAlert("提示", json.message, "confirm");
				             }
			                 if(json.status == 1){
			 					$('#addDeviceTypeForm').submit();
			 					 window.parent.frames["main_frame_left"].$('#btn_viewDeviceType').parent().attr("class","main1_viewDeviceType_select");
				 					window.parent.frames["main_frame_left"].$('#btn_addDeviceType').parent().attr("class","main1_addDeviceType");
			                 }    
				          }
				      });
					
					
					
					
					
					
					
				}
			}
			
			function showRequest(){
				 return $("#addDeviceTypeForm").valid();
			}
			
			function clearInput(){
				document.getElementById("deviceTypeNo").value="";
                document.getElementById("deviceTypeName").value="";
                document.getElementById("deviceTypeDescribe").value="";
			}
			