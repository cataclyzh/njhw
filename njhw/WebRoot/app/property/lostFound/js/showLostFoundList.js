
$(document).ready(function(){
	jQuery.validator.methods.compareDate = function(value, element, param) {
        var beginTime = jQuery(param).val();
        if(value!=""){
        return beginTime <= value;
        }else {
        return true;
        }
    };  
				$("#queryForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					onsubmit: true,
					rules: {
			
					list_lostFoundOvertime:{
					compareDate: "#list_lostFoundIntime"
				}
					},
					messages:{
						list_lostFoundOvertime:{
							compareDate: "结束时间必须晚于开始时间"
						}
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					querySubmit();
				}
			}
			
			function showRequest(){
				 return $("#queryForm").valid();
			}