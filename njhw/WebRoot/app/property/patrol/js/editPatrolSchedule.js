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
	    jQuery.validator.methods.compareNowDate = function(value, element, param) {
			var nowDate = jQuery(param).val();
			
			return value >= nowDate;
	    };
	    
	    jQuery.validator.methods.compareNowTime = function(value, element, param) {
	    	var myDay = jQuery(param).val();
	    	var myTime = myDay+" "+value;
	    	var ss=document.getElementById("nowTime").value;
			return myTime >= ss;
	    };
	      jQuery.validator.methods.compareTime = function(value, element, param) {
			var beginTime = jQuery(param).val();
			return true;
			/*
			if(dayFlag==""){
			   return false;
			}else if(dayFlag=="1"){
			         if(beginTime!=value){
			                return true;
			          }else{
			                return false;
			          }
			}else if(dayFlag=="2"){
			          if(beginTime<value){
			                return true;
			          }else if(beginTime>value){
			          	    return false;
			          }else if(beginTime==value){
			                return false;
			          }
			}else{
			      return false;
			
			}
			*/
			
	    };
	    
	    jQuery.validator.methods.checkLine = function(value, element, param) {
	          var lineId=value;
	          if(lineId==0){
	            return false;
	          }else{
	            return true;
	          }
	    	
	    	
	    };
			$("#editPatrolScheduleForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					
				userName:{
						required: true,
						maxlength:10
					},
					patrolLineId:{
					    required:true,
					    checkLine:true
					},
					scheduleStartDate:{
					   required:true,
						compareNowDate:"#nowDate"	
					},
					scheduleEndDate:{
					   required:true,
						compareDate: "#scheduleStartDate"
	 
					},
				
					scheduleStartTime:{
					required:true,
					compareNowTime:"#scheduleStartDate"
				},
				
				scheduleEndTime:{
					required:true,
					compareTime:"#scheduleStartTime"
				},
				
				scheduleDesc:{
					   maxlength:255
					}
					
				},
				messages:{
					
					userName:{
						required: "请选择员工姓名",
						maxlength:"选择的员工过多"
					},
					patrolLineId:{
					    required:"请选择一条巡查路线",
					    checkLine:"请选择一条巡查路线"
					},
					scheduleStartDate:{
						required:"请填写巡查起始日期",
		                compareNowDate: "开始日期必须不能早于当前日期"
					},
					scheduleEndDate:{
					    required:"请填写巡查结束日期",
						compareDate: "结束日期必须晚于开始日期"
					},
					scheduleStartTime:{
						required:"请填写巡查开始时间",
						compareNowTime:"巡查时间组合的结果中排班开始时间要晚于当前时间"
					},
					scheduleEndTime:{
						required:"请填写巡查结束时间",
						compareTime:"巡查排班结束时间要晚于开始时间"
					},
					scheduleDesc:{
						maxlength:"填写的备注字符过长"
					}
				}
			});
			
			
	});
	
	function saveData(){		
		if (showRequest()) {
			

					$('#editPatrolScheduleForm').submit();

			
		}
	}
	
	function showRequest(){
		 return $("#editPatrolScheduleForm").valid();
	}   
			