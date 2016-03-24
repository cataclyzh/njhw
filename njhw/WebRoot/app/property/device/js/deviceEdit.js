
$(document).ready(function(){
	jQuery.validator.methods.checkDevice = function(value, element, param) {
        var device = jQuery(param).val();
        if(device!="0"){
        return true;
        }else {
        return false;
        }
    };  
	
    jQuery.validator.methods.compareDate = function(value, element, param) {
        var beginTime = jQuery(param).val();
        return beginTime <= value;
    };
    
    jQuery.validator.methods.compareNowDate = function(value, element, param) {
    	var now = jQuery(param).val();
        return now >= value;
    };
	
	
	jQuery.validator.addMethod("checkNumber", function(value, element) { 
		var length = value.length; 
		var checkWarrantyTime = /^[0-9]*[1-9][0-9]*$/; 
		return this.optional(element) || (length <= 12 && checkWarrantyTime.test(value)); 
	}, "请正确填写保修时间");
	
				$("#editDeviceForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					deviceProduceTime:{
					compareNowDate:"#nowDate"
				    },
				    deviceBuyTime:{
				    compareNowDate:"#nowDate",
				    compareDate:"#deviceProduceTime"
				    },
					deviceTypeId:{
					required:true,
					maxlength:12,
				    checkDevice:"#deviceTypeId"

				    },
				    deviceName:{
					required:true,
					maxlength:20
				    },
				    deviceNo:{
				    required:true,
					maxlength:20
				    },
				    deviceCompany:{
				    maxlength:45 	
				    },
				    deviceSequence:{
					 maxlength:45 	
				    },
				   
				    deviceWarrantyTime:{
				    	checkNumber:true
				    },
				    deviceDescribe:{
				    maxlength:255	
				    }	
				    
				    
					
					},
					messages:{
						deviceProduceTime:{
						compareNowDate:"生产日期必须早于当前日期"
					    },
					    deviceBuyTime:{
					    compareNowDate:"购买日期必须早于当前日期",
					    compareDate:"购买日期必须晚于生产日期"
					    },
						deviceTypeId:{
						required:"请输入设备类型编号",
						maxlength:"设备类型编号字符过长",
					    checkDevice:"请选择设备类型"
				    },
				    deviceName:{
				    	required:"请输入设备名称",
						maxlength:"设备名称字符过长"
				    },
				    deviceNo:{
				    	required:"请输入设备编号",
						maxlength:"设备编号字符过长"
				    },
				    deviceCompany:{
						maxlength:"厂家名称过长"

				    },
				    deviceSequence:{
						maxlength:"序列号字符过长"

				    },
				   
				    deviceWarrantyTime:{
				    	checkNumber:"请正确填写保修时间"
				    },
				    deviceDescribe:{
						maxlength:"设备描述字符过长"

				    }	
				 	
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					var deviceNo=$("#deviceNo").val();
					var deviceName=$("#deviceName").val();
					var deviceId=$("#deviceId").val();
					
					  $.ajax({
				             type:"POST",
				             url:"checkDeviceModify.act",
				             data:{deviceNo:deviceNo,deviceName:deviceName,deviceId:deviceId},
				             dataType: 'json',
						     async : false,
						     success: function(json){
				             if (json.status == 0) {
				            
						            easyAlert("提示", json.message, "confirm");
				             }
			                 if(json.status == 1){
			             		
			 					$('#editDeviceForm').submit();
			 					closeEasyWin("editDevice");
			                 
			                 }    
				          }
				      });
					
					
					
					
					
					
			
					
				}
			}
			
			function showRequest(){
				 return $("#editDeviceForm").valid();
			}