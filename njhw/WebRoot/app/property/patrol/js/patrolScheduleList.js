	$(document).ready(function() {
        var dayFlag="";
		jQuery.validator.methods.compareDate = function(value, element, param) {
	        var beginDay = jQuery(param).val();
	        if(beginDay<value){
	            dayFlag="1";
	            return true;
	        }else if(beginDay==value){
	            dayFlag="2";
	            return true;
	        }else{
	            dayFlag="";
	            return false;
	        }
	    };
	  
	      jQuery.validator.methods.compareTime = function(value, element, param) {
			var beginTime = jQuery(param).val();
			if(dayFlag=="1"){
			         return true;
			}else if(dayFlag=="2"){
			          if(beginTime<value){
			                return true;
			          }else if(beginTime>value){
			          	    return false;
			          }else if(beginTime==value){
			                return true;
			          }
			}
			
	    };
	    
	
		
			$("#queryForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					
				
				scheduleStartQueryDate:{
					  
					},
					scheduleEndQueryDate:{
						compareDate: "#scheduleStartQueryDate"
	 
					},
				
					scheduleStartQueryTime:{
				},
				
				scheduleEndQueryTime:{
					compareTime:"#scheduleStartQueryTime"
				}
					
				},
				messages:{
					
					
					scheduleStartQueryDate:{
		                compareNowDate: "开始日期必须不能早于当前日期"
					},
					scheduleEndQueryDate:{
						compareDate: "结束日期必须晚于开始日期"
					},
					scheduleStartQueryTime:{
						compareNowTime:"排班时间组合的结果中排班开始时间要晚于当前时间"
					},
					scheduleEndQueryTime:{
						compareTime:"排班排班结束时间要晚于开始时间"
					}
				}
			});
			
	});
	
	function saveData(){		
		if (showRequest()) {
			

					$('#queryForm').submit();

			
		}
	}
	
	function showRequest(){
		 return $("#queryForm").valid();
	}   
			