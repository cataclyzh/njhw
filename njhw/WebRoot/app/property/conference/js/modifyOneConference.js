
$(document).ready(function(){
				jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime <= value;
	        };
	        
	        jQuery.validator.methods.compareNowDate = function(value, element, param) {
				return value >= "${nowDate}";
	        };
				
				$("#modifyform").validate({		// 为inputForm注册validate函数
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
					modify_conferenceStartTime:{
						required: true,
						compareNowDate:true
					},
					modify_conferenceEndTime:{
						required: true,
						compareDate: "#modify_conferenceStartTime"
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
						modify_conferenceStartTime:{
							required: "请选择开始时间",
		                compareNowDate: "开始时间不能早于当前时间"
						},
						modify_conferenceEndTime:{
							required: "请选择结束时间",
		                compareDate: "结束日期必须晚于开始日期"
						}

					}
				});
			});    
			
			
				function saveData(){		
				if (showRequest()) {
						$('#modifyform').submit();
						closeEasyWin("modifyOneConferencePrepare");
				
				}
			}
			
			function showRequest(){
				 return $("#modifyform").valid();
			}