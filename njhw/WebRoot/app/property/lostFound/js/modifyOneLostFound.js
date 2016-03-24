
$(document).ready(function(){
		
				$("#modifyform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					modify_lostFoundTitle:{
				    required:true,
				    maxlength:20
				    },
				    modify_lostFoundThingName:{
				    required:true,	
				    maxlength:10
				    },
				    modify_lostFoundPickUser:{
				     required:true,	
				     maxlength:20
				    },
				    modify_lostFoundPickUserOrg:{
				    required:true,
				    maxlength:20
				    },
				    modify_lostFoundLocation:{
				    required:true,
				    maxlength:20
				    },
				    modify_lostFoundKeeper:{
				    required:true,
				    maxlength:20
				    },
				    modify_lostFoundIntime:{
				    required:true,
				    },
				    modify_lostFoundDetail:{
				    maxlength:255	
				    }	
				    
					
					},
					messages:{
						modify_lostFoundTitle:{
					    required:"请填写标题",
					    maxlength:"输入的标题字符过长"
					    },
					    modify_lostFoundThingName:{
					    required:"请填写失物名称",	
					    maxlength:"输入的失物名称过长"
					    },
					    modify_lostFoundPickUser:{
					     required:"请填写上交者名称",	
					     maxlength:"输入的上交者名称过长"
					    },
					    modify_lostFoundPickUserOrg:{
					    required:"请填写上交者单位",
					    maxlength:"输入的上交者单位名称过长"
					    },
					    modify_lostFoundLocation:{
					    required:"请填写捡到地点",
					    maxlength:"输入的地点名称过长"
					    },
					    modify_lostFoundKeeper:{
					    required:"请填写失物的保管者",
					    maxlength:"输入的失物保管者名称过长"
					    },
					    modify_lostFoundIntime:{
					    required:"请填写登记时间",
					    },
					    modify_lostFoundDetail:{
					    maxlength:"填写的详细信息过长"	
					    }	
				    
				 	
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					$('#modifyform').submit();
					closeEasyWin("modifyOneLostFoundPrepare");
        
				}
			}
			
			function showRequest(){
				 return $("#modifyform").valid();
			}