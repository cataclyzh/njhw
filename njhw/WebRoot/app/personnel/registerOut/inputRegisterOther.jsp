<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/metaIframe.jsp" %>
		<title>人员登记</title>
		<OBJECT id="WebRiaReader" codeBase="WebRiaReader.cab" classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		var surplusNum = 0;
		$(document).ready(function() {
				$("#name").val("${user.displayName}".replace(/(^\s*)|(\s*$)/g, ""));

				$("#sex").val("${exp.uepSex}");
				$("#residentNo").val("${exp.residentNo}");
				$("#phone").val("${exp.uepMobile}");
				$("#ipTelNum").val("${exp.telNum}");
				// 门禁
				$("#accFloor").text('${strAcc}');
				// 闸机
				$("#accGate").text('${strGate}');

				
				$("#cityCardType").val("${exp.cardType}");
				if ("${exp.cardType}" == "1") {	// 市民卡信息
					$("#isLosted").show();
		            $("#lostSpan").show();
		            $("#tmpCardInfo").hide();
					$("#isLosted").removeAttr("disabled");	// 启用市民卡挂失功能
					if ("${card.systemLosted}" == "1") {
						$("#isLosted").attr("checked", "checked");
					}
					
					$("#cityCard").val("${card.cardId}");
					$("#cityCardold").val("${card.cardId}");
					$("#cardlostedstatus").val("${card.cardLosted}");
					$("#cardstatus").val("${card.cardstatus}");
					$("#cardpuicstatus").val("${card.puicstatus}");
				} else {
					$("#isLosted").hide();
		            $("#lostSpan").hide();
		            $("#tmpCardInfo").show();
					$("#cityCard").val("${exp.tmpCard}");	// 显示临时卡号
					$("#cityCardold").val("${exp.tmpCard}");	// 显示临时卡号
				} 
	        
	        // 身份证号码验证 
			jQuery.validator.addMethod("isIdCardNo", function(value, element) {
				return this.optional(element) || idCardNoUtil.checkIdCardNo(value);     
			}, "请正确输入您的身份证号码");
			
			jQuery.validator.addMethod("isMobile", function(value, element) { 
				var length = value.length; 
				var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
				return this.optional(element) || (length == 11 && mobile.test(value)); 
			}, "请正确填写您的手机号码");
			
			jQuery.validator.addMethod("isCityCard", function(value, element) { 
				var card = /^[0-9]*$/;
				return value.replace(/(^\s*)|(\s*$)/g, "").length == 16 && card.test(value);
			}, "请输入正确的十六卡号");
			
			jQuery.validator.addMethod("isTelePhone", function(value, element) {
				var telephone = /^([0-9]{3,4}\-){0,1}[0-9]{7,8}$/;
				if (value != "") return telephone.test(value);
				else return true;
			});
			
			$("#inputForm").validate(
			{
			    // 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					name: {
						required: true,
						maxlength: 6
					},
					phone:{
						isMobile:true,
						maxlength: 20
					},
					cityCard:{
						required: true,
					  	isCityCard: true,
					  	maxlength: 16
					},
					residentNo:{
						//required: true,
						isIdCardNo: true,
						maxlength: 20
					}
				},
				messages:{
					name: {
						required: "请输入姓名",
						maxlength: "姓名长度不能大于6个字符"
					},
					residentNo:{
						//required: "请输入身份证号",
					  	isIdCardNo:"请输入正确的身份证号",
					  	maxlength: "身份证号长度不能大于20个字符"
					},
					cityCard:{
						required: "请读取或输入市民卡号",
					    isCityCard:"请输入正确的市民卡号",
					  	maxlength: "市民卡号长度为16个字符"
					},
					phone:{
					  	isMobile: "请输入正确的手机号",
					  	maxlength: "手机号长度不能大于20个字符"
					}
				}
			});
			
			setAuthBtnStatus();
			
			refreshCarNum();
			loadPlatenum();
			
		$("#cityCard").mouseover(function(e){
			$("div.prompt").show();
	    }).mouseout(function(){
	    	$("div.prompt").hide();
	    });
		});
		
		function setAuthBtnStatus() {
			if (($("#cityCardType").val() == "1" && ($("#cardstatus").val() == "1" || $("#cardlostedstatus").val() == "1"))
			    || (null == $("#userid").val()  || '' == $("#userid").val()) 
		        	&& ('' == $("#msg").val() || null == $("#msg").val())) {
				$("#access_auth").css("background", "#808080");
				$("#access_auth").css("cursor", "default");
				return false;
			} else {
				 $("#access_auth").addClass("infrom_a");
				// $("#access_auth").css("cursor", "pointer");
				return true;
			}
		}

		// 刷新车位信息
		/*
		function refreshCarNum(){
			$("#lessthan").hide();
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/per/refreshCarNumOther.act",
	            data: {"orgId": $("#orgId").val()},
	            dataType: 'json',
	            async : false,
	            success: function(json){
               		$("#totleNum").text(json.countAll);
               		$("#allotNum").text(json.countAllot);
               		$("#surplusNum").text(json.countSurplus);
	            },
	            error: function(msg, status, e){
	            	easyAlert("提示信息", "加载市民卡信息出错！","info");
	            }
	         });
		}*/
		
		$("#totleNum").text(0);
   		$("#allotNum").text(0);
   		$("#surplusNum").text(0);
		
		function disOrEnLosted() {
				$.ajax({
		            type: "POST",
		            url: "${ctx}/app/per/loadCardNum.act",
		            data: {"userid": $("#userid").val(), "cardType": $("#cityCardType").val()},
		            dataType: 'json',
		            async : false,
		            success: function(json){
		            	if (json.isTmpOver && "${exp.cardType}" != "2") {
		            		$("#cityCardType").val("1");
		            		easyAlert("提示信息", "临时卡已经用完，不能再次分配！","info");
		            		return false;
		            	}
		            	if($("#cityCardType").val() == "1") {
		            		$("#isLosted").show();
		            		$("#lostSpan").show();
		            		$("#tmpCardInfo").hide();
							$("#isLosted").removeAttr("disabled");
							$("#cardpuicstatus").val(json.puicStatus);
							$("#cardstatus").val(json.cardStatus);
							$("#cardlostedstatus").val(json.cardLostedStatus);
							
							if (json.systemLosted == '1') {
								$("#isLosted").attr("checked", "checked");
							}
						} else {
							$("#isLosted").hide();
		            		$("#lostSpan").hide();
		            		$("#tmpCardInfo").show();
						
							$("#isLosted").attr("disabled","disabled");
							$("#cardpuicstatus").val("");
							$("#cardstatus").val("");
							$("#cardlostedstatus").val("");
						}
						$("#cityCard").val(json.rstCardNum);
						setAuthBtnStatus();
		            },
		            error: function(msg, status, e){
		            	easyAlert("提示信息", "切换卡号出错！","info");
		            }
		         });
		}
		
		
		function closeWindow(type,Id,Num)
		{	
			$("#companyWin").window("close");
			if("R" == type)
			{    
				$("#roomId").val(Id);
				$("#roomNum").val(Num);
			}
		
			if("D" == type)
			{
				$("#dRoomName").text(Num);
				if ("${user.loginUid}" != "" && 'true' != '${backList}')
				{
					easyAlert("提示信息", "登记成功！","info");
					refreshCarNum();
					loadPlatenum();
				} 
		        else if("${user.loginUid}" != ""  && 'true' == '${backList}')
		        {
		        	easyAlert("提示信息", "修改成功！","info");
		        	closeWin();
		        }
				else 
				{
					easyConfirm('提示信息', '登记成功！系统默认生成的登陆密码：123456，是否继续添加？', function(r){
						if (r) window.location.reload();
						else {
							var reloadFun = parent.ifrmOrgTree.window.reload;
							reloadFun.apply(parent.ifrmOrgTree.window);
						}
					});
				}
				$("#img").removeAttr("disabled");
		
			}
			if("1" == type)
			{
			  	$("#ipTelId").val(Id);
			  	$("#ipTelNumval").val(Num);
			}
			if("2" == type)
			{   
				$("#fax").val(Id);
				$("#sfax").val(Num);
			}
			if("3" == type)
			{    
				$("#fax_web").val(Id);
				$("#faxweb").val(Num);
			}
		
		}
		
		// 根据卡号，加载卡状态
		function validCityCard(type1){
	
			var isOk = true;
			var type = $("#cityCardType").val();
			var cityCard = $("#cityCard").val().replace(/(^\s*)|(\s*$)/g, "");
			
			if (cityCard.length == 16) {
				if (type == "1") {	// 当选中的是市民卡时，加载出市民卡的各种状态
					$.ajax({
					
			            type: "POST",
			            url: "${ctx}/app/per/loadCityCard.act",
			            data: {"cityCard": cityCard, "userId": $("#userid").val(),"orgId":$("#orgId").val()},
			            dataType: 'json',
			            async : false,
			            success: function(json){
			      
						if(json.status == 0){
		                		$("#cardstatus").val(json.cardstatus);
		                		$("#cardlostedstatus").val(json.cardlostedstatus);
		                		$("#cardpuicstatus").val(json.cardpuicstatus);
		                		if (type1 == "read") {
		                			$("#residentNo").val(json.residentNo);
		                			$("#name").val(json.name);
		                			$("#sex").val(json.sex);
		                		}
		                		if(type1 == 'save')
		                		{
		                			if($('#name').val().replace(/(^\s*)|(\s*$)/g, "") != json.name)
		                			{
		                				easyAlert("提示信息", "输入的姓名和此市民卡对应的姓名不一致！","info");
		                				isOk = false;
		                			}
		                			
		                			if($('#residentNo').val().replace(/(^\s*)|(\s*$)/g, "") != json.residentNo && '' !=$('#residentNo').val().replace(/(^\s*)|(\s*$)/g, "") )
		                			{
		                				easyAlert("提示信息", "输入的身份证号和此市民卡对应的身份号不一致！","info");
		                				isOk = false;
		                			}
		                		
		                		}
		                		if(json.binding == 1) {
		                			easyAlert("提示信息", "此卡号已绑定用户："+json.username+"，请使用其他卡号登记！","info");
		                			isOk = false;
		                		}
		                    } else {
		                    	if (type1 == "blur") {
		                    		$("#cardstatus").val("");
			                		$("#cardlostedstatus").val("");
			                		$("#cardpuicstatus").val("");
			                    	$("#isLosted").attr("disabled","disabled");
			                    	easyAlert("提示信息", "无法取得市民卡信息，此市民卡号将不会保存，请使用临时卡登记！","info");
			                    	isOk = false;
		                    	}
		                    	if (type1 == "read" || type1 == "save") {
		                    		$("#cardstatus").val("");
			                		$("#cardlostedstatus").val("");
			                		$("#cardpuicstatus").val("");
			                    	$("#isLosted").attr("disabled","disabled");
			                    	easyAlert("提示信息", "无法取得市民卡信息，此市民卡号将不会保存，请使用临时卡登记！","info");
			                    	isOk = false;
		                    	}
		                    }
		                    setAuthBtnStatus();
			            },
			            error: function(msg, status, e){
			            	easyAlert("提示信息", "加载市民卡信息出错！","info");
			            }
			         });
				}
			}
			else
			{
			  if(type1=="save")
			  {
			  		easyAlert("提示信息", "请输入正确格式的十六位卡号！","info");
			  		isOk = false;
			  }
			}
			return isOk;
		}
		
		function addCarNum() {
	
			var newid = 0;
			var sid = $("#carNumtab tr").last().attr("id");
			
			if (sid == null || sid == 0) { 
				newid = 1;
			} else {
				var ind = sid.indexOf("_");
				var id = parseInt(sid.substr(ind + 3, sid.length-1));
				newid = id + 1;
			}

			$("#carNumtab").append("<tr id='carNum_tr"+ newid +"'></tr>");
			$("#carNum_tr"+ newid).append("<td  width='20%' class='form_label'><div class='infrom_td'>车牌号</div></td>");
			$("#carNum_tr"+ newid).append("<td  width='50%' colspan='2'><input name='_car_num_"+ newid +"' id='_car_num_"+ newid +"' class='infrom_input_s1' maxlength='7' onclick='addprefix(&quot;_car_num_"+newid+"&quot;)' onblur='validCarNum("+newid+")'/><div id='alreadyAllot_"+ newid +"' style='display: none;'>车牌号已存在，请勿重复登记！</div><div id='repeat_"+ newid +"' style='display: none;'>车牌号已添加，请勿重复添加！</div><div id='length_lack_"+ newid +"' style='display: none;'>请输入正确的车牌号！</div></td>");
			$("#carNum_tr"+ newid).append("<td  width='21%'><input type='checkbox'   name='isFastenCarNum"+newid+"' id='isFastenCarNum"+newid+"' value='1' onclick='changeThisVal(&quot;isFastenCarNum"+newid+"&quot;)'/>占用内部车位<a onClick='del(this)' class='infrom_a' href='javascript:void(0)'>删除</a></td>");
			$("#carNum_tr"+ newid).append("<td></td>");
		}
		
		// 移除行
		function del(obj) {
			$(obj).parent().parent().remove();
		}
		
		
		function checkTmpCardUnique() {
			var isOK = true;
			var type = $("#cityCardType").val();
			var cityCard = $("#cityCard").val().replace(/(^\s*)|(\s*$)/g, "");
			if (cityCard.length == 16) {
				if (type == "2") {
					$.ajax({
			            type: "POST",
			            url: "${ctx}/app/per/validTmpCard.act",
			            data: {
			            "cityCard": cityCard, 
			            "userId": $("#userid").val(),
			            "orgId": $("#orgId").val()
			            },
			            dataType: 'text',
			            async : false,
			            success: function(msg){
		                    if(msg == "exist"){
		                		easyAlert("提示信息", "此卡号已被注册！","info");
		                		isOK = false;
		                    } else {
		                    	$("#exist_tmpCard").hide();
		                    }
			            },
			            error: function(msg, status, e){
			            	easyAlert("提示信息", "加载市民卡信息出错！","info");
			            }
			         });
				}
			}
			return isOK;
		}
		
		function readCard() {
			WebRiaReader.LinkReader();
			var readVal = WebRiaReader.RiaReadCardEngravedNumber();
			var cardUID = WebRiaReader.RiaReadCardUID();
			if(null != cardUID)
			{   
				$("#cardUID").val(cardUID.substr(2,cardUID.length));
			}
			if (readVal.indexOf("0000") >= 0)
			{
				$("#cityCard").val(readVal);
				validCityCard('read');
			} else {
				easyAlert("提示信息", "无法读取市民卡，请检查读卡器连接状态和市民卡插件安装情况！","info");
			}
		}
		
		function addprefix(id) {
			var val = $("#"+id).val().replace(/(^\s*)|(\s*$)/g, "");
			if (val == "") {
				if (id == "cityCard") $("#"+id).val("0000");
				else $("#"+id).val("苏A");
			}
		}

		
		function setChkVal(id) {
			if ($("#"+id).attr("checked") == "checked") $("#"+id).val("1");
			else $("#"+id).val("0");
		}
		
		function modifyCardIsLosted() {
			var cityCard = $("#cityCard").val();
			var optType = "";
			var optConfirmMsg = "";
			if ($("#isLosted").attr("checked")=="checked") {	// 选中挂失
				$("#isLosted").removeAttr("checked");
				optType = "confirmLosted";
				optConfirmMsg="确定挂失市民卡？";
			} else {	// 取消挂失
				$("#isLosted").attr("checked", "checked");
				optType = "cancelLosted";
				optConfirmMsg="确定取消市民卡挂失？";
			}
			if (cityCard.length == 16) {
				easyConfirm('提示', optConfirmMsg, function(r){
					if (r) {
						openAdminConfirmWin(modifyCardIsLostedFn, {cityCard: cityCard, optType: optType});
		          	}
				});
			} else {
				easyAlert("提示信息", "市民卡长度不对，不足16位！","info");
                setAuthBtnStatus();
			}
		}
		
		// 加载车辆
		function loadPlatenum(){
		
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/per/loadPlatenum.act",
	            data: {"userid": "${user.userid}"},
	            dataType: 'json',
	            async : false,
	            success: function(json){
                    if(json.length > 0){
                    	$("#carNumtab tr:gt(0)").remove();
                    	surplusNum = 0;
                		$.each(json, function(i) {
                		
                			var newid = i+1;
                			$("#carNumtab").append("<tr id='carNum_tr"+ newid +"'></tr>");
							$("#carNum_tr"+ newid).append("<td width='20%' class='form_label'><div class='infrom_td'>车牌号</div> </td>");
							$("#carNum_tr"+ newid).append("<td width='50%' colspan='2'><input class= 'infrom_input_s1' name='_car_num_"+ newid +"' id='_car_num_"+ newid +"' theme='simple' value='"+json[i].nupPn+"'   onclick='addprefix(&quot;_car_num_"+newid+"&quot;)' onblur='validCarNum("+newid+")'/><div id='alreadyAllot_"+ newid +"' style='display: none;'>车牌号已存在，请勿重复登记！</div><div id='repeat_"+ newid +"' style='display: none;'>车牌号已添加，请勿重复添加！</div><div id='length_lack_"+ newid +"' style='display: none;'>请输入车牌号！</div></td>");
							$("#carNum_tr"+ newid).append("<td width='21%'><input  type='checkbox' name='isFastenCarNum"+newid+"' id='isFastenCarNum"+newid+"' value='1' onclick='changeThisVal(&quot;isFastenCarNum"+newid+"&quot;)'/>占用内部车位<a onClick='del(this)' class='infrom_a' href='javascript:del(this)'>删除</a></td>");
							$("#carNum_tr"+ newid).append("<td></td>");
			            	
			            	if (json[i].nupFlag == "2") {
			            		surplusNum += 1;
			            		$("#isFastenCarNum"+ newid).attr("checked","checked");
			            		$("#isFastenCarNum"+ newid).val("2");
			            	}
						});
                    } 
	            },
	            error: function(msg, status, e){
	            	easyAlert("提示信息", "加载车牌信息出错！","info");
	            }
	         });
			
			$("#selfallotNum").text(surplusNum);
		}
		
		function modifyCardIsLostedFn(params) {
			var cityCard = params.cityCard;
			var optType = params.optType;
			$.ajax({
				type: "POST",
				url: "${ctx}/app/per/modifyCardIsLosted.act",
				data: {"cityCard": cityCard, "optType": optType},
				dataType: 'text',
				async : false,
				success: function(msg){
			        if(msg == "success") {
			            if (optType == "confirmLosted") {
			                $("#isLosted").attr("checked", "checked");
			                easyAlert("提示信息", "挂失成功！","info");
			            } else if (optType == "cancelLosted") {
			                $("#isLosted").removeAttr("checked");
			                easyAlert("提示信息", "取消挂失成功！","info")
			            };
			        } else if (msg == "cardNoExist") {
			            easyAlert("提示信息", "市民卡号不存在，无法挂失！","info");
			        } else {
			            if (optType == "confirmLosted") {
			                easyAlert("提示信息", "挂失失败！","info");
			            } else if (optType == "cancelLosted") {
			                 easyAlert("提示信息", "取消挂失失败！","info");
			            }
			        }
			        setAuthBtnStatus();
				},
				error: function(msg, status, e){
				    easyAlert("提示信息", "操作出错！","info");
				}
			});
		}

		
		function saveData() {
		    /* if(showRequest() && checkTmpCardUnique() && validCityCard('save'))
		    {
		    	openAdminConfirmWin(saveDataFun);
		    } */
			saveDataFun(); //去除刷卡权限 树那边的删除还没有去掉。调查
		}
		
		function saveDataFun(){
			//$("#img").attr("disabled","disabled"); // 禁用file
			if ($("#carNumIsValid").val() != "") return false;
			var otherSurplusNum = parseInt($("#surplusNum").text());	// 剩余的内部车辆
			var allotNum = parseInt($("#allotNum").text());				// 已分配数
			var totleNum = parseInt($("#totleNum").text());				// 总数
			
			var carNum = "";			// 拼凑字符串
			var isFastenCarNum = "";	// 拼凑字符串
			var chooseFastenNum = 0;	// 当前选中的车位数
			$(":text[id^='_car_num_']").each(function() {
				var name = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
				if (name != "") {
					carNum += (name + ",");	
					var sid = $(this).attr("id");
					var ind = sid.lastIndexOf("_");
					var id = parseInt(sid.substr(ind + 1, sid.length-1));
					isFastenCarNum += ($("#isFastenCarNum"+id).val() + ",");
					if ($("#isFastenCarNum"+id).val() == "2") chooseFastenNum+=1;
				}
			});
			$("#carNums").val(carNum);
			$("#isFastenCarNums").val(isFastenCarNum);
			var isGT = (otherSurplusNum + surplusNum) - chooseFastenNum;	//计算总的剩余车位数
			if (isGT < 0) {
				$("#lessthan").show();
				 return;
			}
			if(chooseFastenNum>0 && ''==$("#phone").val())
			{
			    $("#phonemust").show();
			    return;
			}
		
		 	var cityCardold = $("#cityCardold").val();
			var cityCard =  $("#cityCard").val();
			if (showRequest() && checkTmpCardUnique() && validCityCard('save'))
		    {  
				   	var  isNormal =  $("#cardstatus").val() ==1 || $("#cardlostedstatus").val() ==1;
	  				var options = {
				    dataType:'text',
				    success: function(msg) {
						if(msg == "success")
						{       
								$("#msg").val(msg);
								$("#cityCardold").val(cityCard);
									if ("${user.loginUid}" != "" && 'true' != '${backList}')
									{
										easyAlert("提示信息", "登记成功！","info");
										refreshCarNum();
										loadPlatenum();
									} 
							        else if("${user.loginUid}" != ""  && 'true' == '${backList}')
							        {
							        	easyAlert("提示信息", "修改成功！","info");
							        	closeWin();
							        }
									else 
									{
										easyConfirm('提示信息', '登记成功！', function(r){
												if (r) window.location.reload();
											else {
												var reloadFun = parent.ifrmOrgTree.window.reload;
												reloadFun.apply(parent.ifrmOrgTree.window);
											}
										});
									}
							
					 	} else if(msg == "fail") {
					 		easyAlert("提示信息", "登记失败！","info");
					 		$("#img").removeAttr("disabled");
						} 
					}
				}
				

				$('#inputForm').ajaxSubmit(options);
			}else{return false;}
		}
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		function changeThisVal(id) {
			if ($("#"+id).attr("checked")=="checked")  $("#"+id).val("2");
			else $("#"+id).val("1");
		}
		
		function closeWin() {
			var url = "${ctx}/app/personnel/perInfoCheckinOther.act?orgId=${orgId}";
			window.location.href = url;
		}
		
		/**
		* 导航方法
		*/
		function linkFunction(type){
			var linkUrl = "";
			var title = "";
			var id = "";
			var orgId = $("#orgId").val();
			if(null == orgId || "" == orgId)
			{
				alert("请选择组织后再新增人员!");
				return false;
			}

			if(type=="ipTel"){
				id = '10261';
				title = "IP电话号码分配";
				linkUrl = "${ctx}/app/per/telDis.act?orgId="+orgId+"&TEL_TYPE=1";
			} 
			else if("fax" == type)
			{
				title = "传真号码分配";
				linkUrl = "${ctx}/app/per/telDis.act?orgId="+orgId+"&TEL_TYPE=2";
			
			}
			else if ("webFax" == type)
			{	
				title = "网络传真号码分配";
				linkUrl = "${ctx}/app/per/telDis.act?orgId="+orgId+"&TEL_TYPE=3";
			}
			else if(type=="roomDistribute")
			{
				id = '10213';
				title = "办公室分配";
				linkUrl = "${ctx}/app/per/roomDis.act?orgId="+orgId+"&ROOM_TYPE=R"+"&userId="+$("#userid").val();
			}
			else if(type == "door")
			{    
			
		        if(!setAuthBtnStatus())
		        {
		        	return false;
		        }
		        else
		        {
		        	title = "门锁分配";
					linkUrl = "${ctx}/app/per/roomDis.act?orgId="+orgId+"&ROOM_TYPE=D"+"&userId="
					+$("#userid").val()+"&cardId="+$("#cityCardold").val().replace(/(^\s*)|(\s*$)/g, "");
		        }
		        
		       
				
			}
			windowDialog(title,linkUrl,"500","450",false);
		}
		
		
		function windowDialog(title,url,w,h,refresh){
			 	var body =  window.document.body;
				var left =  body.clientWidth/2 - w/2;
				var top =  body.clientHeight/2+h/4;
				var scrollTop = document.body.scrollTop;
				//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
				top = top + scrollTop;
				// $("#companyWin").show();
			
		        openEasyWin("telDis",title,url,w,h,false);
				

			}

		
		function tmpCardDetail() {
			if ($.trim($("#tmpCardStartDate").val()) == '') {
				easyAlert("提示信息", "临时卡权限剩余天数为30天！","info");
			} else {
				var startDate = $.trim($("#tmpCardStartDate").val());
				startDate = startDate.replace(/-/g, "/");
 				startDate = new Date(startDate);
 				var myDate=new Date();
 				var days = parseInt((myDate.getTime() -startDate.getTime()) / (1000 * 60 * 60 * 24));
 				if (days >= 30) {
 					easyAlert("提示信息", "临时卡权限已经过期","info");
 				} else {
 					var restDays = 30-days;
 					easyAlert("提示信息", "临时卡权限剩余天数为"+restDays+"天！","info");
 				}
			}
		}
		
		function checkRequired(id) {
			$("#required_"+id).hide();
			var isOk = true;
			var val = $("#"+id).val().replace(/(^\s*)|(\s*$)/g, "");
			if (val == '') {
				$("#required_"+id).show();
				isOk = false;
	        }

			return isOk;
		}

			
		function accessApply() {
			if(!setAuthBtnStatus())
		    {
		    	return false;
		    }
		    else
		    {
		        var url = "${ctx}/app/per/accessApplyInputForUser.act?userid=" + $("#userid").val();
				openEasyWin("accessInput","新增门锁闸机授权申请",url,"420","440",false, null, 'aiFrame');
		    }
		}	
	</script>
	</head>

<body>
<div class="inform_index">
   
	<input type="hidden"  id="msg"/>
	<form id="inputForm" action="saveRegisterOther.act" method="post" autocomplete="off">
		<input type="hidden" name="orgId" id="orgId" value="${org.orgId}"/>
		<input type="hidden" name="userPhoto" id="userPhoto" value="${exp.uepPhoto}"/>
		<input type="hidden" name="userid" id="userid" value="${user.userid}"/>
		<input type="hidden" name="roomId" id="roomId" value="${exp.roomId}"/>
		<input type="hidden" name="cityCardold" id="cityCardold"/>
		<input type="hidden" name="carNumIsValid" id="carNumIsValid"/>
	    <input type="hidden" name="ipTelId" id="ipTelId"/>
	    <input type="hidden" name="cardUID" id="cardUID"/>
	    <input type="hidden" name="fax_web" id= "fax_web"/>
	    <input type="hidden" name="fax" id= "fax"/>
		<input type="hidden" name="tmpCardStartDate" id="tmpCardStartDate" value="${exp.uepBak2}"/>
	  	<div style="padding:10px;">
		<input type="hidden" name="dRoomId" id="dRoomId"/>
	  	    <div class="bgsgl_right_list_border">
			  <div class="bgsgl_right_list_left">人员基本信息</div>
			</div>
		<div class="infrom_ryjbxx" style="padding-top: 10px;">
		
 	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  	<td width="5%"></td>
  	<td width="12%"><div class="infrom_td">卡类型</div></td>
    <td width="27%"><table width="100%" border="0">
		<tr>
    <td width="63%">	
		<s:select list="#application.DICT_CARD_TYPE" cssStyle="width:100%;list-style:none;" listKey="dictcode" listValue="dictname" name="cityCardType" id="cityCardType" onchange="disOrEnLosted()"/>
	</td>
    <td style="font-size:14px;color:#808080;" width="7%">
		<input type="checkbox" name="isLosted" id="isLosted" disabled="disabled" onclick="modifyCardIsLosted()"/>
	</td>
    <td style="font-size:12px;color:#808080;" width="30%">
    	<span id="lostSpan">内部挂失</span>
    	<a class="infrom_a" href="javascript:void(0);" id="tmpCardInfo" style="display: none;width: 100%;" onclick="javascript:tmpCardDetail();">详细</a>
    </td>
  	</tr>
	</table>
	</td>
	<td width="3%"></td>
	<td width="3%"></td>
    <td width="7%"></td>
    <td width="10%"style="border-left: dashed 1px #808080;border-top: dashed 1px #808080;"><div class="infrom_td"> <font style="color: red">*</font>姓名</div></td>
  
		  <td width="27%"style="border-top: dashed 1px #808080;"><input type="text" class="infrom_input" name="name" id="name" maxlength="6" />
	      </td>
	
	<td width="3%"style="border-right: dashed 1px #808080;border-top: dashed 1px #808080;"></td>
	<td width="3%"></td>
  </tr>
  <tr>
  	<td></td>
  	<td><div class="infrom_td"><font style="color: red">*</font>卡号</div></td>
    <td><input type="text"  class="infrom_input_s"  onchange="validCityCard('blur'); checkTmpCardUnique();"   onclick="addprefix('cityCard')" maxlength="16" name="cityCard" id="cityCard"/>
    	<div id="card_prompt" class="prompt" style="display:none; padding: 0 10px;">
    		<div class="bgsgl_right_list_border">
			  <div class="bgsgl_right_list_left" style="width:100%;font-size:13px;">市民卡号帮助</div>
			</div>
    		<div style="width:210px;margin:0 auto;">
    			<div class="yellow_panel"></div>
    			<font style="color:#808080;font-size:11px;float:left;padding-top:2px;">黄色部分为市民卡号</font>
    			<font style="color:#808080;font-size:11px;float:left;padding-top:2px;">卡号开头默认添加0000</font>
    			<div style="clear:both;height:10px;"></div>
    			<div class="card_help" style="clear:both;"></div>
    		</div>
    	</div>
    	<a class="infrom_a" href="javascript:void(0);" onclick="readCard()">读卡</a>
    </td>
    <td></td>
	<td style="border-right: solid 3px #808080;"></td>
    <td></td>
    <td style="border-left: dashed 1px #808080;"><div class="infrom_td">性别</div></td>
    <td>
		<s:select list="#application.DICT_SEX" id ="sex" name="sex" listKey="dictcode" listValue="dictname" cssStyle="width:100%;list-style:none;"/>
    </td>
    <td style="border-right: dashed 1px #808080;"></td>
	<td></td>
  </tr>
  <tr>
    <td></td>
  	<td><div class="infrom_td">卡状态</div></td>
    <td>
		<s:select list="#application.DICT_CARD_STATUS" headerKey="" headerValue="无" cssStyle="width:100%;list-style:none;" listKey="dictcode" listValue="dictname" name="cardstatus" id="cardstatus" disabled="true"/>
	</td>
	<td></td>
	<td style="border-right: solid 3px #808080;"></td>
    <td></td>
    <td style="border-left: dashed 1px #808080;border-bottom: dashed 1px #8090B2;"><div class="infrom_td">身份证号</div></td>
    <td style="border-bottom: dashed 1px #808080;"><input type="text" class="infrom_input" name="residentNo" id="residentNo"  maxlength="20" /></td>
	<td style="border-right: dashed 1px #808080;border-bottom: dashed 1px #8090B2;"></td>
	<td></td>
  </tr>
  <tr>
    <td></td>
  	<td><div class="infrom_td">挂失状态</div></td>
    <td><s:select list="#application.DICT_CARD_LOSTED_STATUS"  headerKey="" headerValue="无" cssStyle="width:100%;list-style:none;"  listKey="dictcode" listValue="dictname" name="cardlostedstatus" id="cardlostedstatus"  disabled="true"/></td>
	<td></td>
	<td style="border-right: solid 3px #808080;"></td>
    <td></td>
    <td><div class="infrom_td">手机号</div></td>
    <td><input type="text" class="infrom_input" name="phone" id="phone" maxlength="20" />
     <span id="phonemust" style="display:none;color:red">占用了车位，请输入手机号码！</span>
    </td>
    <td></td>
    <td></td>
  </tr>
  <tr>
  	<td></td>
    <td><div class="infrom_td">开卡状态</div></td>
    <td>
		<s:select list="#application.DICT_CARD_PUIC_STATUS"  headerKey="" headerValue="无" cssStyle="width:100%;list-style:none;"   listKey="dictcode" listValue="dictname" name="cardpuicstatus" id="cardpuicstatus" disabled="true" />
	</td>
	<td></td>
	<td></td>
    <td></td>
    <td><div class="infrom_td">电话号码</div></td>
    <td><input type="text" class="infrom_input" name="ipTelNum" id="ipTelNum" maxlength="35"/></td>
    <td></td>
    <td></td>
  </tr>
</table>
<div class="infrom_borders">
<table width="100%">
  <tr>
    <td width="70%"><div class="infrom_td" style="text-align:left;">&nbsp;&nbsp;&nbsp;如果卡类型是市民卡，请先正确输入市民卡号或者刷卡。</div></td>
    <td width="1%"></td>
    <td width="10%"></td>
    <td width="9%"></td>
</tr>
</table>
</div>
		
		</div>
		
<!--车位分配-->
    <div class="bgsgl_right_list_border">
	  <div class="bgsgl_right_list_left">车位分配信息</div>
	</div>
<div class="infrom_ryjbxx">
		<input type="hidden" name="carNums" id="carNums"/>
		<input type="hidden" name="isFastenCarNums" id="isFastenCarNums"/>
		<table id="carNumtab" width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr id="carNum_tr0">
	   			<td colspan="3" width="70%">
		    		<div class="infrom_td" style="text-align:left;">&nbsp;&nbsp;&nbsp;本单位共有内部车位<span  id="totleNum"></span>个， 已经分配<span id="allotNum">50</span>个， 目前剩余<span id="surplusNum"></span>个。
						<span id="lessthan" style="display:none;color:red">剩余车位数不足！</span>
					</div>
				</td>
	   			<td class="infrom_as_td" width="21%">
	    			<a class="infrom_a" href='javascript:void(0)' onclick="javascript:addCarNum();">分配</a>
	    		</td>
	    		<td></td>
 			 </tr>

		</table>

	<div class="infrom_borders">
	</div>
</div>

	
	<div style="margin: 10px;">
		<a onclick="saveData();" href="javascript:void(0);"  id="savebut" class="buttonFinish">保&nbsp;&nbsp;存</a>
	</div>


<!--权限信息-->
<div class="bgsgl_right_list_border">
		<div class="bgsgl_right_list_left">权限信息</div>
</div>
<div class="infrom_ryjbxx">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td width="10%" style="padding-left: 10px;"><div class="infrom_mesu">门禁</div></td>
		<td width="60%"><span style="white-space:normal,word-wrap:break-word;" id="accFloor"></span></td>
		<td width="21%"><a class="infrom_a" id="access_auth"   href="javascript:void(0);" onclick="javascript:accessApply()">申请</a></td>
		<td>
		</td>
		</tr>
		<tr>
		<td width="10%" style="padding-left: 10px;"><div class="infrom_mesu">闸机</div></td>
		<td width="60%"><span style="white-space:normal,word-wrap:break-word;" id="accGate"></span></td>
		<td width="21%"></td>
		<td>
		</td>
		</tr> 
	</table>

<div class="infrom_borders">
</div>
</div>
	<c:if test="${backList == 'true'}">
		<a class="buttonFinish"  href="javascript:void(0);"  id="resetbutton" onclick="closeWin()">关&nbsp;&nbsp;闭</a>
	</c:if>
	</div>
	    <div class="botton_from">
	</div>
	</div>
</form>
</div>

</body>
</html>