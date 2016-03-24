
$(document).ready(function(){
		
				$("#addform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					add_lostFoundTitle:{
				    required:true,
				    maxlength:20
				    },
				    add_lostFoundThingName:{
				    required:true,	
				    maxlength:10
				    },
				    add_lostFoundPickUser:{
				     required:true,	
				     maxlength:20
				    },
				    add_lostFoundPickUserOrg:{
				    required:true,
				    maxlength:20
				    },
				    add_lostFoundLocation:{
				    required:true,
				    maxlength:20
				    },
				    add_lostFoundKeeper:{
				    required:true,
				    maxlength:20
				    },
				    add_lostFoundIntime:{
				    required:true,
				    },
				    add_lostFoundDetail:{
				    maxlength:255	
				    }	
				    
					
					},
					messages:{
						add_lostFoundTitle:{
					    required:"请填写标题",
					    maxlength:"输入的标题字符过长"
					    },
					    add_lostFoundThingName:{
					    required:"请填写失物名称",	
					    maxlength:"输入的失物名称过长"
					    },
					    add_lostFoundPickUser:{
					     required:"请填写上交者名称",	
					     maxlength:"输入的上交者名称过长"
					    },
					    add_lostFoundPickUserOrg:{
					    required:"请填写上交者单位",
					    maxlength:"输入的上交者单位名称过长"
					    },
					    add_lostFoundLocation:{
					    required:"请填写捡到地点",
					    maxlength:"输入的地点名称过长"
					    },
					    add_lostFoundKeeper:{
					    required:"请填写失物的保管者",
					    maxlength:"输入的失物保管者名称过长"
					    },
					    add_lostFoundIntime:{
					    required:"请填写登记时间",
					    },
					    add_lostFoundDetail:{
					    maxlength:"填写的详细信息过长"	
					    }	
				    
				 	
					}
				});
			});    
			
			function saveData(){		
				if (showRequest()) {
					$('#addform').submit();
					window.parent.frames["main_frame_left"].$('#btn_viewLostFound').parent().attr("class","main1_viewLostFound_select");
 					window.parent.frames["main_frame_left"].$('#btn_addLostFound').parent().attr("class","main1_addLostFound");
						
				}
			}
			
			function showRequest(){
				 return $("#addform").valid();
			}
			function clearInput(){
				$("#addform").resetForm();
			}