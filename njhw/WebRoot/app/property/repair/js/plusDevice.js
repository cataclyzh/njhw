
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
				    deviceNumber:{
				    checkNumber:"#deviceNumber",
					checkDeviceType:"#deviceTypeIdSelect",
				    checkDevice:"#deviceIdSelect"
				    }
					},
					messages:{
						
					 deviceNumber:{
						checkNumber:"请输入正确的设备数量",
						checkDeviceType:"请选择设备类型",
					    checkDevice:"请选择设备"
					    }

					}
				});
			});    
			




			function plus(){		
				if (showRequest()) {	
					var lastNumber=document.getElementById("deviceNumber").value;
					var lastDevice=document.getElementById("deviceTypeIdSelect").value;
					var lastDeviceType=document.getElementById("deviceIdSelect").value;
					if((lastNumber=="--请输入设备数量--")||(lastDevice==0)||(lastDeviceType=0))
					{
					    alert("请填写正确的耗材信息");
					}else{
						plusDevice();
					}
					
					
					
			
				}
			}
			
			function showRequest(){
				 return $("#completeRepairForm").valid();
			}
			
			function plusDevice(){
			    var deviceCostId=document.getElementById("deviceIdSelect").value;
		        var deviceCostName="" + $("#deviceIdSelect").find("option:selected").text();
			    var deviceCostNumber ="" + document.getElementById("deviceNumber").value;
			    var deviceCostTotalTable = document.getElementById("deviceCostTotalTable");
			    
			    var deviceCostDesc = deviceCostName + "*" +deviceCostNumber;
			    
			    var deviceCostTr = document.createElement('tr');
			    var deviceCostNameTd = document.createElement('td');
			    var deviceCostNumberTd = document.createElement('td');
			    var deviceCostOperationTd = document.createElement('td');
			    var deviceCostLink = document.createElement('a');
			    var deviceCostHidden = document.createElement('input');
			    deviceCostNameTd.appendChild(document.createTextNode(deviceCostName));
			    deviceCostNumberTd.appendChild(document.createTextNode(deviceCostNumber));
			    deviceCostLink.innerHTML='[移除]';
			    deviceCostLink.onmousemove="this.style.color='#ffae00'";
				deviceCostLink.onmouseout="this.style.color='#8090b4'";
			    deviceCostOperationTd.appendChild(deviceCostLink);
			    
			    
			    
			    deviceCostHidden.type="hidden";
			    deviceCostHidden.id=deviceCostId;
			    deviceCostHidden.value=deviceCostNumber;
			    
			    deviceCostLink.onclick=function(){
			    	deviceCostTotalTable.removeChild(deviceCostTr);
			    }
			    
			    deviceCostTr.id=deviceCostId;
			    deviceCostTr.appendChild(deviceCostNameTd);
			    deviceCostTr.appendChild(deviceCostNumberTd);
			    deviceCostTr.appendChild(deviceCostOperationTd);
			    deviceCostTr.appendChild(deviceCostHidden);
			    deviceCostTotalTable.appendChild(deviceCostTr);
			}