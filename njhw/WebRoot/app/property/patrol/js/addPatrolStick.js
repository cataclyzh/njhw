	

$(document).ready(function(){

				$("#addPatrolStickForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					receiver:{
						required:true
				    },
				    sId:{
				    	required:true
					}
				    
					
					},
					messages:{
						receiver:{
							required:"请选择员工"
					    },
					    sId:{
					    	required:"请选择巡更棒号",
						}
				    
				 	
					}
				});
			});    

			function saveData(){		
				if (showRequest()) {
					
				        $('#addPatrolStickForm').submit();
						
				}
			}
			
			function showRequest(){
				 return $("#addPatrolStickForm").valid();
			}
			
            function clearInput(){
            	  document.getElementById("receiver").value="";
                  document.getElementById("sId").selectIndex = 0;
                  
            }
		  
		   