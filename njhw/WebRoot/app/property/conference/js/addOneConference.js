
$(document).ready(function(){
				jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime <= value;
	        };
	        
	        jQuery.validator.methods.compareNowDate = function(value, element, param) {
				return value >= "${nowDate}";
	        };
				
				$("#addform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					add_conferenceName:{
					required: true,
					maxlength:20
					},
					outName:{
					required: true,
					maxlength:20
					},
					_chk:{
					required: true
					},
					add_conferenceStartTime:{
						compareNowDate:true
					},
					add_conferenceEndTime:{
						compareDate: "#add_conferenceStartTime"
					},
					
					add_conferenceDetailService:{
						maxlength:255
						}
					},
					messages:{
						add_conferenceName:{
						required: "请输入会议名称",
					  	maxlength: "输入的会议名称过长"
						},
						outName:{
						required: "请选择申请人",
						maxlength: "申请人名称过长"
						},
						_chk:{
						required: "请选择一个套餐"
						},
						add_conferenceStartTime:{
		                compareNowDate: "开始时间不能早于当前时间"
						},
						add_conferenceEndTime:{
							compareDate: "开始时间必须早于结束时间"
						},
						add_conferenceDetailService:{
							maxlength:"备注内容过长"
						}
					}
				});
			});    
			
			
				function saveData(){		
                                    if($("#_chk").length==0){
				    	alert("没有套餐记录，无法定会议室！");
				    	return;
				    }
				if (showRequest()) {
					var add_conferenceStartTime=$("#add_conferenceStartTime").val();
					var add_conferenceEndTime=$("#add_conferenceEndTime").val();
					var conferencePackageId=$("#conferencePackageId").val();
					  $.ajax({
				             type:"POST",
				             url:"checkOneConference.act",
				             data:{beginTime:add_conferenceStartTime,endTime:add_conferenceEndTime,packageId:conferencePackageId},
				             dataType: 'json',
						     async : false,
						     success: function(json){
				             if (json.status == 0) {
				            
						            easyAlert("提示", json.message, "confirm");
				             }
			                 if(json.status == 1){
			                	 $('#addform').submit();
			                	 window.parent.frames["main_frame_left"].$('#btn_viewconference').parent().attr("class","main1_viewconference_select");
				 					window.parent.frames["main_frame_left"].$('#btn_addconference').parent().attr("class","main1_addconference");
			                 
			                 }    
				          }
				      });
					
					
					
					
										
				}
			}
				function saveData1(){
				    if($("#_chk").length==0){
				    	alert("没有套餐记录，无法定会议室！");
				    	return;
				    }
				if (showRequest()) {
					var add_conferenceStartTime=$("#add_conferenceStartTime").val();
					var add_conferenceEndTime=$("#add_conferenceEndTime").val();
					var conferencePackageId=$("#conferencePackageId").val();
					 $.ajax({
			             type:"POST",
			             url:"checkOneConference.act",
			             data:{beginTime:add_conferenceStartTime,endTime:add_conferenceEndTime,packageId:conferencePackageId},
			             dataType: 'json',
					     async : false,
					     success: function(json){
			             if (json.status == 0) {
			            
					            easyAlert("提示", json.message, "confirm");
			             }
		                 if(json.status == 1){
		                	 $('#addform').submit();   
		                	 
		                	 window.parent.frames["main_frame_left"].$('#btn_viewconference').parent().attr("class","main1_viewconference_select");
		 					window.parent.frames["main_frame_left"].$('#btn_addconference').parent().attr("class","main1_addconference");
		                 
		                 }    
			          }
			      });
				}
			}
			
			function showRequest(){
				 return $("#addform").valid();
			}
			
			function clearInput(){
				$('#addform').resetForm();

             
			}
			
			