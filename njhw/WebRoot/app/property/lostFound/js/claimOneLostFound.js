
$(document).ready(function(){
	
	jQuery.validator.methods.compareDate = function(value, element, param) {
        var beginTime = jQuery(param).val();
        if(value!=""){
        return beginTime <= value;
        }else {
        return true;
        }
    };  
	
	
	
	jQuery.validator.addMethod("checkMobile", function(value, element) { 
		var isMobile=/^(?:13\d|15\d)\d{5}(\d{3}|\*{3})$/;   
		var isPhone=/^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;	
		return this.optional(element) || (isMobile.test(value)||isPhone.test(value)); 
	}, "请输入正确的电话号码");
	
				$("#claimform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					claim_lostFoundLostUser:{
				    required:true,
				    maxlength:20
				    },
				    claim_lostFoundPhone:{
				    required:true,
				    checkMobile:true,
				    maxlength:20
				    },
				    claim_lostFoundLostUserOrg:{
				     required:true,	
				     maxlength:20
				    },
				    claim_lostFoundOverTime:{
				    required:true,
				    compareDate: "#claim_lostFoundIntime"
				    }
					},
					messages:{
						claim_lostFoundLostUser:{
					    required:"请填写失物认领人",
					    maxlength:"失物认领人的字符过长"
					    },
					    claim_lostFoundPhone:{
					    required:"请填写失物认领人电话",	
					    checkMobile:"请填写正确的电话号码",
					    maxlength:"失物认领人电话字符过长"
					    },
					    claim_lostFoundLostUserOrg:{
					     required:"请填写失物认领人单位",	
					     maxlength:"失物认领人单位字符过长"
					    },
					    claim_lostFoundOverTime:{
					    required:"请填写失物认领时间",
					    compareDate: "认领时间必须晚于登记时间"
					    }
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					$('#claimform').submit();
					closeEasyWin("claimOneLostFoundPrepare");
					
				}
			}
			
			function showRequest(){
				 return $("#claimform").valid();
			}