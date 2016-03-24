
$(document).ready(function(){
		
	jQuery.validator.methods.checkDeviceType = function(value, element, param) {
        var deviceType = jQuery(param).val();
        if(deviceType!="0"){
        return true;
        }else {
        return false;
        }
    };  
	
	jQuery.validator.methods.checkDevice = function(value, element, param) {
        var device = jQuery(param).val();
        if(device!="0"){
        return true;
        }else {
        return false;
        }
    };  
	
	
	
				$("#addRepairForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					repairTheme:{
				    required:true,
				    maxlength:30
				    },
				    outName:{
				    required:true	
				    },
				    deviceTypeId:{
				    required:true,
				    checkDeviceType:"#deviceTypeId"
				    },	
				    deviceId:{
				    required:true,
				    checkDevice:"#deviceId"
				    },
				    repairDetail:{
				    maxlength:255
				    }
					
					},
					messages:{
						repairTheme:{
					required:"请填写报修主题",
					maxlength:"报修主题字符过长"
				    },
				    outName:{
				    required:"请填写报修人"	
				    },
				    deviceTypeId:{
				    required:"请选择设备类型",
				    checkDeviceType:"请选择设备类型"
				    },	
				    deviceId:{
				    required:"请选择设备名称",	
					checkDevice:"请选择设备名称"
				    },
				    repairDetail:{
				    maxlength:"填写的故障描述字符过长"
				    }
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					$('#addRepairForm').submit();
					
					window.parent.frames["main_frame_left"].$('#btn_viewRepair').parent().attr("class","main1_viewRepair_select");
 					window.parent.frames["main_frame_left"].$('#btn_addRepair').parent().attr("class","main1_addRepair");
				
				}
			}
			
			function showRequest(){
				 return $("#addRepairForm").valid();
			}
			
			function clearInput(){
			
				$('#addRepairForm').resetForm();

			
			}