
$(document).ready(function(){
		
	jQuery.validator.addMethod("checkNumber", function(value, element) { 
		var length = value.length; 
		var checkStorage = /^[0-9]*[1-9][0-9]*$/; 
		return this.optional(element) || (length <= 12 && checkStorage.test(value)); 
	}, "请正确填写出库数量");

jQuery.validator.addMethod("checkLeftNumber", function(value, element) { 
	    var maxValue =  $("#storageInventory").attr("value");
		return this.optional(element) || (eval(value)<=eval(maxValue)); 
	}, "超出库存总量，请重新输入");
	
	
	
				$("#outStorageForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
				
				    inoutStorageOutNumber:{
				    required:true,
				    checkNumber:true,
				    checkLeftNumber:true,
				    maxlength:12
				    	
				    },
				    inoutStorageInDetail:{
				    maxlength:255	
				    },
				    outName:{
					    required:true
					    }	
				
					
					},
					messages:{
				
				    inoutStorageOutNumber:{
				    	required:"请输入出库数量",
				    	checkNumber:"请正确填写出库数量",
				    	checkLeftNumber:"超出库存总量，请重新输入",
						maxlength:"输入的出库数量过大"
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
					$('#outStorageForm').submit();
					closeEasyWin("outStorage");
         
				}
			}
			
			function showRequest(){
				 return $("#outStorageForm").valid();
			}