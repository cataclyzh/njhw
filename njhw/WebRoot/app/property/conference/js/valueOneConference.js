
$(document).ready(function(){
		
				
				$("#valueform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					value_conferenceSatisfy:{
					required: true
					},
				value_conferenceClientValue:{
				     maxlength:255
				}
					
					},
					messages:{
						value_conferenceSatisfy:{
						required: "请选择满意程度"
						},
						value_conferenceClientValue:{
						maxlength:"服务评价字符过长"
						}
					}
				});
			});    
			
			
				function saveData(){		
				if (showRequest()) {
						$('#valueform').submit();
						closeEasyWin("valueOneConferencePrepare");
				
				}
				
			}
			
			function showRequest(){
				 return $("#valueform").valid();
			}
			
			function cancelAddDevice() {
				$('#valueform').resetForm();
			}