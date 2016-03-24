	

$(document).ready(function(){

				$("#addPatrolLineForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					patrolLineName:{
				    required:true,
				    maxlength:40
				    },
				    patrolLineDesc:{
					    maxlength:270
					    }
				    
					
					},
					messages:{
						patrolLineName:{
					    required:"请输入路线名称",
					    maxlength:"输入的路线名称过长"
					    },
					    patrolLineDesc:{
						    maxlength:"输入的路线描述字符过长"
						    }
				    
				 	
					}
				});
			});    
						document.domain = "njnet.gov.cn";
    var device;	   
			function saveData(){		
				if (showRequest()) {
					 var patrolNodes = document.getElementById("patrolNodes");
				        device.OnSaved(device.GetSelectedDeviceArray());
				        $('#addPatrolLineForm').submit();
						
				}
			}
			
			function showRequest(){
				 return $("#addPatrolLineForm").valid();
			}
			

		  
		    function initMapByFloorId(){
		        var patrolNodes = document.getElementById("patrolNodes");
		        var floorId = document.getElementById("floorId");
		        try {
	                    var DeviceDefaultOption = {
				            IsFrameMap : false,
				            OnSaved : function(Points) {
				                patrolNodes.value = Points;
				            }
			            }

			            device = window.frames["threeDimensional"].Init3D("Device",
					    DeviceDefaultOption);
	                    device.MenuContent({floorNavigate:true});
					    //device.Testtoolbar(floorId.value);
			            //device.AddPatrolPoint(floorId.value);
		        }catch (e) {
			         initMapByFloorId();
		        }
	        }
	       function clearInput(){
	           document.getElementById("patrolLineName").value="";
	           document.getElementById("patrolLineDesc").value="";
	         

	       }
	