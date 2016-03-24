
$(document).ready(function(){	
				$("#distirbuteRepairForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					outName:{
				    required:true
				    }
					
					},
					messages:{
						outName:{
					required:"请填写维修人员"
				    }
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					$('#distirbuteRepairForm').submit();
					closeEasyWin("distributionRepair");
					
				}
			}
			
			function showRequest(){
				 return $("#distirbuteRepairForm").valid();
			}