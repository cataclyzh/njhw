
$(document).ready(function(){
	
	jQuery.validator.addMethod("checkNumber", function(value, element) { 
		var length = value.length; 
		var checkStorage = /^[0-9]*[1-9][0-9]*$/; 
		return this.optional(element) || (length <= 12 && checkStorage.test(value)); 
	}, "请正确填写入库数量");
	
	jQuery.validator.methods.checkTotal = function(value, element, param) {
        var nowTotal = jQuery(param).val();
        var Total= eval(nowTotal)+eval(value);
        if(Total<=999999999999){
            return true;
        }else{
            return false;
        }
    };  
	
	
	$("#inStorageForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					
				    inoutStorageInNumber:{
				    required:true,
				    checkNumber:true,
				    maxlength:12,
				    checkTotal:"#storageInventory"
				    	
				    },
				    inoutStorageInDetail:{
				    maxlength:255	
				    },
				    outName:{
				    required:true
				    }
				
					
					},
					messages:{
				
				    inoutStorageInNumber:{
				    	required:"请输入入库数量",
				    	checkNumber:"请正确填写入库数量",
						maxlength:"输入的入库数量过大",
						checkTotal:"超过最大库存量"
				    },
				    inoutStorageInDetail:{
						maxlength:"输入的备注字符过长"

				    },
				    outName:{
				    	required:"请填写入库人"
				    }
				 	
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					$('#inStorageForm').submit();
					closeEasyWin("inStorage");
       
				}
			}
			
			function showRequest(){
				 return $("#inStorageForm").valid();
			}