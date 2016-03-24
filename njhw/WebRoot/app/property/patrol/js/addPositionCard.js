$(document).ready(function(){
	
	//$("#receiver").focus();
	//selectRecieveid();
 	// 控制父页面高度
	//$("#main_frame_left", window.parent.document).height($(".main_conter").height() + 100);
	//$("#main_frame_right", window.parent.document).height($(".main_conter").height() + 100);

	$("#addPatrolPositionCardForm").validate({		// 为inputForm注册validate函数
		meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
		errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
		rules: {
			receiver:{
		    	required:true,
			},
		    visId:{
		    	required:true
			}
		},
		messages:{
			receiver:{
		    	required:"请选择员工",
		    },
		    visId:{
		    	required:"请选择定位卡号",
			}
	    
	 	
		}
	});
});    

function saveData(){		
	if (showRequest()) {
		
	        $('#addPatrolPositionCardForm').submit();
			
	}
}

function showRequest(){
	 return $("#addPatrolPositionCardForm").valid();
}

function clearInput(){
	  document.getElementById("receiver").value="";
      document.getElementById("patrolPositionCardNo").value="";
      
}
		  
		   