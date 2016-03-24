
$(document).ready(function(){
		
				$("#evaluateRepairForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				
					rules: {
					repairSatisfy:{
				    required:true
				    },
				    repairEvaluate:{
				    maxlength:255
				    }
					},
					messages:{
						
					repairSatisfy:{
				    required:"请选择满意程度"
					},
					repairEvaluate:{
					maxlength:"输入的评价字符过长"
					}
					},
					 /* 重写错误显示消息方法,以alert方式弹出错误消息 */  
			        showErrors: function(errorMap, errorList) {  
			            var msg = "";  
			            $.each( errorList, function(i,v){  
			              msg += (v.message+"\r\n");  
			            });  
			            if(msg!="") alert(msg);  
			        },  
			        /* 失去焦点时不验证 */   
			        onfocusout: false 
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					$('#evaluateRepairForm').submit();
				    closeEasyWin("evaluateRepair");
          
				}
			}
			
			function showRequest(){
				 return $("#evaluateRepairForm").valid();
			}