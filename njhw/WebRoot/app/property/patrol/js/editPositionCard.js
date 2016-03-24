	

$(document).ready(function(){

				$("#editPositionCardForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					userName:{
				    required:true,
				    maxlength:10
				    },
				    patrolPositionCardNo:{
				    required:true,
					maxlength:45
					}
				    
					
					},
					messages:{
						userName:{
					    required:"请输入员工姓名",
					    maxlength:"输入的员工姓名过长"
					    },
					    patrolPositionCardNo:{
					    required:"请输入定位卡号",
						maxlength:"输入的定位卡号字符过长"
						}
				    
				 	
					}
				});
			});    

			function saveData(){		
				if (showRequest()) {
					
				        $('#editPositionCardForm').submit();
						
				}
			}
			
			function showRequest(){
				 return $("#editPositionCardForm").valid();
			}
			

		  
		   