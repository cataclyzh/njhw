
$(document).ready(function(){
		
	jQuery.validator.methods.checkDeviceType = function(value, element, param) {
		if(value!="--请输入设备数量--"){
			var deviceType = jQuery(param).val();
	        if(deviceType!="0"){
	        	
	        return true;
	        }else {
	        	
	        return false;
	        }
		}else{
			
		return true;
		}
		
		
        
    };  
	
	jQuery.validator.methods.checkDevice = function(value, element, param) {
		
		if(value!="--请输入设备数量--"){
			var device = jQuery(param).val();
	        if(device!="0"){
	        	
	        return true;
	        }else {
	        
	        return false;
	        }
		}else {
		
		return true;
		}
		
        
    };  
	
jQuery.validator.methods.checkNumber = function(value, element, param) {	
			var number = jQuery(param).val();
	        var numberTest=/^[0-9]*[1-9][0-9]*$/;
	        var deviceType=document.getElementById("deviceTypeIdSelect").value;
	        var device=document.getElementById("deviceIdSelect").value;
	     
			if(number!="--请输入设备数量--"){
	        	if(numberTest.test(number))
	        	{
	        	
	    	        return true;

	        	}else{
                  
	        	    return false;
	        	}
	        }else if(deviceType=="0"&&device=="0") 
	        {
              
	        return  true;
	        }else{
	           return false;
	        }
        
    };  

				$("#completeRepairForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					completeRepairReceipt:{
				    maxlength:255
				    },
				    deviceNumber:{
				    checkNumber:"#deviceNumber",
					checkDeviceType:"#deviceTypeIdSelect",
				    checkDevice:"#deviceIdSelect",
				    maxlength:12
				    
				    }
					},
					messages:{
						
					completeRepairReceipt:{
				    maxlength:"输入的回执字符过长"
					},
					 deviceNumber:{
						checkNumber:"请输入正确的设备数量",
						checkDeviceType:"请选择设备类型",
					    checkDevice:"请选择设备",
					    maxlength:"设备数量过大"
					    }

					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					 var deviceCostHiddens = document.getElementById('deviceCostTotal').getElementsByTagName("input");
				     var deviceCostTotal = document.getElementById("deviceCostTotal");
				     var deviceCostInput = document.createElement("input");
				     var deviceContent = "";
				     for (var i=0;i<deviceCostHiddens.length;i++){
				        deviceId = deviceCostHiddens[i].id;
				        deviceNumber = deviceCostHiddens[i].value;
				        deviceContent = deviceContent + deviceId + "$" + deviceNumber + ",";
				     }
				     deviceCostInput.id = "deviceContent";
				     deviceCostInput.name = "deviceContent";
				     deviceCostInput.type = "hidden";
				     deviceCostInput.value = deviceContent;
				        
				     deviceCostTotal.appendChild(deviceCostInput);
					$('#completeRepairForm').submit();
				    closeEasyWin("completeRepair");
				
				}
			}
			
			function showRequest(){
				 return $("#completeRepairForm").valid();
			}