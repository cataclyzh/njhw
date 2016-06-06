<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="d" %>
<%@ taglib uri="http://www.holytax.com/taglib" prefix="h" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8" />
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
		<meta http-equiv="Cache-Control" content="no-store"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/>
		<script type="text/javascript">var _ctx = '${ctx}';</script>
		<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
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
			if('${org.levelNum}' == '2'){
				document.getElementById("leaderLevel").options.remove(1);  
			}

				$("#name").val("${user.displayName}".replace(/(^\s*)|(\s*$)/g, ""));
				$("#loginUid").val("${user.loginUid}");
				$("#ucode").val("${user.UCode}".replace(/(^\s*)|(\s*$)/g, ""));
				$("#isSystem").removeAttr("disabled");
				$("#isTempUser").val("01");
				if ("${user.isSystem}" == "1") {
					$("#isSystem").attr("checked", "checked");
					$("#isSystem").val("1");
				}
				$("#leaderLevel").val("${stage}");
				$("#cardUID").val("${exp.cardUid}");
				$("#sex").val("${exp.uepSex}");
				$("#residentNo").val("${exp.residentNo}");
				$("#phone").val("${exp.uepMobile}");
				$("#email").val("${exp.uepMail}");
				$("#roomId").val("${exp.roomId}");
				$("#roomNum").val("${exp.roomInfo}");
				$("#ipTelId").val("${exp.telNum}");
				// 门禁
				$("#accFloor").text('${strAcc}');
				// 闸机
				$("#accGate").text('${strGate}');
				if("${exp.telNum}"!=""){
					$("#ipTelNumval").val("${tel1.telNum}");
				}
				
				if("${exp.uepFax}"!=""){
					 $("#sfax").val("${tel2.telNum}");
					 $("#fax").val("${exp.uepFax}");
				}
			
				
				if("${exp.webFax}"!=""){
					$("#faxweb").val("${tel3.telNum}");
					$("#fax_web").val("${exp.webFax}");
				}
				
				
				$("#dRoomName").text("${dRoomName}");
				$("#dRoomId").val("${dRoomId}");
				
				$("#attendance_approvers").text("${approvers}");
				if ("${user.activeFlag}" == "0") {
					$("#isLogOut").attr("checked", "checked");
				}
				
				// 考勤管理员
				if("${isOrgAttAdmin}" == "1"){
					$("#isAttendanceAdmin").attr("checked", "checked");
					$("#isAttendanceAdmin").val("1");
				}
				
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
				
				if ("${exp.uepType}"=="04") {
					$("#isTempUser").attr("checked", "checked");
					$("#tempUserInfoRow").show();
					$("#leaderLevelrow").hide();
					$("#attendance_approvers_id").hide();
					$("#dateFrom").val("${exp.uepBak3}");
					$("#dateTo").val("${exp.uepBak4}");
					$("#isTempUser").val("04");
				} else {
					$("#leaderLevelrow").show();
					$("#attendance_approvers_id").show();
					$("#tempUserInfoRow").hide();
				}
				
				refreshCarNum();
				loadPlatenum();
			/*} else {
				$("input :text").val("");
				$("input :hidden").val("");
				$("#isLogOut").attr("disabled",true);
				refreshCarNum();
				$("#selfallotNum").text(0);
				$("#tempUserInfoRow").hide();
				$("#leaderLevelrow").show();
				$("#attendance_approvers_id").show();
			}*/
	        
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
				return value.replace(/(^\s*)|(\s*$)/g, "").length == 16 && card.test(value) || value.replace(/(^\s*)|(\s*$)/g, "").length == 0;
			}, "请输入正确的十六卡号");
			
			jQuery.validator.addMethod("isStandard", function(value, element) {
				var isSt = /^\w+$/;
				return isSt.test(value);
			}, "登录名只能由数字、英文字母和下划线组成");
			
			jQuery.validator.addMethod("isUcode", function(value, element) {
				if (value.replace(/(^\s*)|(\s*$)/g, "") == "") return true;
				else {
					var isSt = /^\w+$/;
					return isSt.test(value);
				}
			}, "用户编码只能由数字、英文字母和下划线组成");
			
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
					loginUid: {
						required: true,
						maxlength: 20,
						minlength: 6,
						isStandard:true
					},
					ucode: {
						maxlength: 10,
						//minlength: 4,
						isUcode:true
					},
					name: {
						required: true,
						maxlength: 6
					},
					phone:{
						isMobile:true,
						maxlength: 20
					},
					residentNo:{
						//required: true,
						isIdCardNo: true,
						maxlength: 20
					},
					email:{
	                    email:true
	               	},
	               	sfax:{
	               		isTelePhone:true
	               	}
				},
				messages:{
					loginUid: {
						required: "请输入登录名",
						maxlength: "登录名长度不能大于20个字符",
						minlength: "登录名长度不能少于6个字符",
						isStandard:"登录名只能由数字、英文字母和下划线组成"
					},
					ucode: {
						maxlength: "用户编码长度不能大于10个字符",
						//minlength: "用户编码长度不能少于4个字符",
						isUcode:"用户编码只能由数字、英文字母和下划线组成"
					},
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
					    isCityCard:"请输入正确的市民卡号",
					  	maxlength: "市民卡号长度为16个字符"
					},
					phone:{
					  	isMobile: "请输入正确的手机号",
					  	maxlength: "手机号长度不能大于20个字符"
					},
	               	email:{
	                    isEmail: "请输入正确的邮箱"
	               	},
	               	sfax:{
	               		isTelePhone:"请输入正确的传真号，例如025-88888888或99999999"
	               	}
				}
			});
			
			setAuthBtnStatus();
			
		$("#cityCard").mouseover(function(e){
			$("div.prompt").show();
	    }).mouseout(function(){
	    	$("div.prompt").hide();
	    });
		});
		
		function setAuthBtnStatus() {
			if (($("#cityCardType").val() == "1" && ($("#cardstatus").val() == "1" || $("#cardlostedstatus").val() == "1"))
			    || $("#isSystem").is(':checked') || $("#isLogOut").is(':checked') || (null == $("#userid").val()  || '' == $("#userid").val()) 
		        	&& ('' == $("#msg").val() || null == $("#msg").val())) {
				$("#door_auth").css("background", "#808080");
				$("#door_auth").css("cursor", "default");
				$("#access_auth").css("background", "#808080");
				$("#access_auth").css("cursor", "default");
				$("#approvers").css("background", "#808080");
				$("#approvers").css("cursor", "default");
				return false;
			} else {
				$("#door_auth").addClass("infrom_a");
				// $("#door_auth").css("cursor", "pointer");
				 $("#access_auth").addClass("infrom_a");
				 $("#approvers").addClass("infrom_a");
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
	            url: "${ctx}/app/per/refreshCarNum.act",
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
		
			/**
			* @type lock:门锁分配;room:房间分配
			* @roomId 房间id
			*/
			function closeWindowroom(type,cardNo){
				$("#companyWin").window("close");
			
				var url = "${ctx}/app/per/disResultPage.act?type="+type+"&cardNo="+cardNo;
				var title = "";
				if (type=="lock"){
					title = "门锁授权情况";
				}
				if (type != null && type != "") {
					windowDialog(title,url,400,300,true);
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
							$("#carNum_tr"+ newid).append("<td width='50%' colspan='2'><input class= 'infrom_input_s1' name='_car_num_"+ newid +"' id='_car_num_"+ newid +"' theme='simple' value='"+json[i].nupPn+"'   onmousedown='addprefix(&quot;_car_num_"+newid+"&quot;)' onblur='validCarNum("+newid+")'/><div id='alreadyAllot_"+ newid +"' style='display: none;'>车牌号已存在，请勿重复登记！</div><div id='repeat_"+ newid +"' style='display: none;'>车牌号已添加，请勿重复添加！</div><div id='length_lack_"+ newid +"' style='display: none;'>请输入车牌号！</div></td>");
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
			  if(type1=="save" && cityCard.length !=0)
			  {     
					easyAlert("提示信息", "请输入正确格式的十六位卡号！","info");
			  		isOk = false;
			  }
			}
			return isOk;
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
		
		// 检测登录名是否已存在
		function checkUniqueId(id) {
			$("#exist_"+id).hide();
			var isOk = true;
			var val = $("#"+id).val().replace(/(^\s*)|(\s*$)/g, "");
			if (val.length >= 6 && val.length <= 20) {
				$.ajax({
		            type: "POST",
		            url: "${ctx}/app/per/checkUniqueId.act",
		            data: {"val": val, "id":id, "userId": $("#userid").val()},
		            dataType: 'text',
		            async : false,
		            success: function(msg){
	                    if(msg == "exist") {
	                    	$("#exist_"+id).show();
	                    	isOk = false;
	                    } else {
	                    	$("#exist_"+id).hide();
	                    }
		            },
		            error: function(msg, status, e){
		            	if (id == "loginUid") easyAlert("提示信息", "验证登录名出错！","info");
		            	else if (id == "ucode") easyAlert("提示信息", "验证用户编码出错！","info");
		            }
		         });
	         }
	        else
	        {
	        	$("#exist_"+id).hide();
	        }
			return isOk;
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
		
		function checkPic(obj) {
			var reslut = true;
			var picType = obj.substring(obj.length - 3, obj.length);
			if (picType == ("jpg")) reslut = false;
			if (picType == ("gif")) reslut = false;
			if (picType == ("bmp")) reslut = false;
			return reslut;
		}
		
		function isJpg() {
			var rst = true;
			var img = $("#img").val();
			if (img != null && img != "") {
				if (checkPic(img)) {
					easyAlert("提示信息", "只能上传JPG,GIF,BMP格式的文件！","info");
					rst = false;
				}
			} else {
				easyAlert("提示信息", "请选择文件后上传！","info");
				rst = false;
			}
			return rst;
		}

		function ajaxFileUpload1() {
			if (isJpg()) {
				$.ajaxFileUpload({
					url : "${ctx}/app/fileupload/saveImg.act",
					secureuri : false,
					fileElementId : 'img',
					dataType : 'json',
					success : function(json) {
						$("#img5").attr("src", json.imgSrc);
						$("#userPhoto").val(json.picSrc);
					},
					error : function(data, status, e) {
						alert(e);
					}
				});
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
		
		function modifySMA() {
			if ($("#userid").val() != ""){
				var optType = "";
				var optConfirmMsg = "";
				if ($("#isSystem").attr("checked")=="checked") {	// 设置为管理员
					optType = "confirm";
					optConfirmMsg="确定设置为管理员？";
				} else {	// 取消管理员身份
					optType = "cancel";
					optConfirmMsg="确定取消设置？";
				}
				easyConfirm('提示', optConfirmMsg, function(r){
					if (r) {
						$.ajax({
				            type: "POST",
				            url: "${ctx}/app/per/modifySMA.act",
				            data: {"userid": $("#userid").val(), "optType": optType},
				            dataType: 'text',
				            async : false,
				            success: function(msg){
			                    if(msg == "success") {
			                    	if (optType == "confirm") easyAlert("提示信息", "设置成功！","info");
			                    	else if (optType == "cancel") easyAlert("提示信息", "取消成功！","info");
			                    } else {
			                    	if (optType == "confirm") {
			                    		easyAlert("提示信息", "设置失败！","info");
			                    		$("#isSystem").removeAttr("checked");
			                    	} else if (optType == "cancel") {
			                    		easyAlert("提示信息", "取消失败！","info");
			                    		$("#isSystem").attr("checked","checked");
			                    	}
			                    }
				            },
				            error: function(msg, status, e){
				            	easyAlert("提示信息", "操作出错！","info");
				            }
				         });
				     } else {
						if (optType == "confirm") {
                    		$("#isSystem").removeAttr("checked");
                    	} else if (optType == "cancel") {
                    		$("#isSystem").attr("checked","checked");
                    	}
					}
				});
			}
		}
		
		// 停用用户
		function userLogout() {
			var optType = "";
			var optConfirmMsg = "";
			if ($("#isLogOut").attr("checked")=="checked") {	// 停用
				$("#isLogOut").removeAttr("checked");
				optType = "logout";
				optConfirmMsg="确定停用此用户？";
			} else {	// 启用
				$("#isLogOut").attr("checked", "checked");
				optType = "login";
				if ($("#dateTo").val() != '') {
					var myDate = new Date();
					var endData = $("#dateTo").val().split('-');
					if ($("#isTempUser").attr("checked")=="checked" && (myDate.getFullYear() > endData[0] || 
						myDate.getMonth()+1 > parseInt(endData[1]) ||
						myDate.getDate() > endData[2])) {
						easyAlert("提示信息", "该临时人员已经到期，无法启用！","info");
						return false;
					}
				} else {
					if ($("#isTempUser").attr("checked")=="checked") {
						easyAlert("提示信息", "启用临时人员，请先输入结束日期！","info");
						return false;
					}
				}
				optConfirmMsg="确定启用此用户？";
			}
			
			if ($("#userid").val() != ""){
				easyConfirm('提示', optConfirmMsg, function(r){
					if (r) {
						var days = 0;
						if ($.trim($("#tmpCardStartDate").val()) != '') {
							var startDate = $.trim($("#tmpCardStartDate").val());
							startDate = startDate.replace(/-/g, "/");
 							startDate = new Date(startDate);
 							var myDate=new Date();
 							days = parseInt((myDate.getTime() -startDate.getTime()) / (1000 * 60 * 60 * 24));
						}
						if (days >= 30 && optType == "login") {
 							easyConfirm('提示', "临时卡已过期，是否重置开始日期并且继续启用此用户？", function(r1){
 								if (r1) {
 									openAdminConfirmWin(userLogoutFn, {optType: optType, tmpLogin: true});
 								}
 							});
 						} else {
 							openAdminConfirmWin(userLogoutFn, {optType: optType});
 						}
					} else {
                    	setAuthBtnStatus();
					}
				});
			} else {
				easyAlert("提示信息", "用户不存在！","info");
               	setAuthBtnStatus();
			}
		}
		
		function userLogoutFn(params) {
			var optType = params.optType;
			$.ajax({
				            type: "POST",
				            url: "${ctx}/app/per/updateOut.act",
				            data: {"userid": $("#userid").val(), "optType": optType},
				            dataType: 'text',
				            async : false,
				            success: function(msg){
			                    if(msg == "success") {
			                    	if (optType == "logout") {
			                    		$("#isLogOut").attr("checked", "checked");
			                    		easyAlert("提示信息", "停用成功！","info");
			                    		$("#ipTelNum").val("");
			                    		$("#ipTelId").val("");
			                    	} else if (optType == "login") {
			                    		$("#isLogOut").removeAttr("checked");
			                    		easyAlert("提示信息", "启用成功！","info");
			                    		if (params.tmpLogin) $("#tmpCardStartDate").val("");
			                    	}
			                    } else {
			                    	if (optType == "logout") {
			                    		easyAlert("提示信息", "停用失败！","info");
			                    	} else if (optType == "login") {
			                    		easyAlert("提示信息", "启用失败！","info");
			                    	}
			                    }
			                    setAuthBtnStatus();
				            },
				            error: function(msg, status, e){
				            	easyAlert("提示信息", "操作出错！","info");
				            }
			});
		}
		
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
		
		function saveData() {
		    if(showRequest() && (($("#isTempUser").attr("checked")=="checked" && checkRequired('dateFrom') && checkRequired('dateTo')) || ($("#isTempUser").attr("checked")!="checked")) && checkUniqueId('ucode') && checkUniqueId('loginUid') && checkTmpCardUnique() && validCityCard('save'))
		    {
		   	//	openAdminConfirmWin(saveDataFun);
				saveDataFun();
		   	}
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
			if (chooseFastenNum > 0) {
				var isGT = (otherSurplusNum + surplusNum) - chooseFastenNum;	//计算总的剩余车位数
				if (isGT < 0) {
					$("#lessthan").show();
				 	return;
				}
			}
			
			if(chooseFastenNum>0 && ''==$("#phone").val())
			{
			    $("#phonemust").show();
			    return;
			}
		 	var cityCardold = $("#cityCardold").val();
			var cityCard =  $("#cityCard").val();
			var dRoomName = $("#dRoomName").text();
			
			//alert(cityCardold);
			
			if (showRequest() && (($("#isTempUser").attr("checked")=="checked" && checkRequired('dateFrom') && checkRequired('dateTo')) || ($("#isTempUser").attr("checked")!="checked")) && checkUniqueId('ucode') && checkUniqueId('loginUid') && checkTmpCardUnique() && validCityCard('save'))
		    {  
				   	var  isNormal =  $("#cardstatus").val() ==1 || $("#cardlostedstatus").val() ==1;
	  				var options = {
				    dataType:'text',
				    success: function(msg) {
						if(msg == "success")
						{       
								$("#msg").val(msg);
								
								$("#cityCardold").val(cityCard);
								
								if(('' != cityCardold && '' !=cityCard) && cityCardold != cityCard  && '' != dRoomName)
								{
									$.ajax({
									          type: "POST",
									          url: "${ctx}/app/per/doorDis.act",
									          data: {
									          	  "userId": $("#userid").val(), 
									          	  "cityCardold":cityCardold,
									          	  "cardId":cityCard,
									          	  "cardChange":"Y",
									          	  "isNormal":isNormal
									          },
									          dataType: 'text',
									          async : false,
									          success: function(msg)
									          {
													var url = "${ctx}/app/per/disResultPage.act?type=D"+"&cardNo="+cityCard;
													windowDialog("门锁授权状况",url,500,300,true);
									    	  }
									});
								}
								else
								{  
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
									
							
					 	} else if(msg == "fail") {
					 		easyAlert("提示信息", "登记失败！","info");
					 		$("#img").removeAttr("disabled");
						} 
					}
				}
				
				
				
				if(('' != cityCardold && '' !=cityCard)
					&& cityCardold != cityCard  &&  '' != $("#dRoomName").text() && !isNormal)
				{
					//alert(cityCardold);
						easyConfirm('提示', '卡号变动，系统要对门锁重新授权，请确认是否继续进行？', function(r)
						{
							if (r)
								$('#inputForm').ajaxSubmit(options);
						});
				}
				
				else if(('' != cityCardold && '' !=cityCard)
					&& cityCardold != cityCard  &&  '' != $("#dRoomName").text() && isNormal)
				{
						easyConfirm('提示', '新卡号状态不正常，系统要删除老卡的门锁权限，请确认是否继续进行？', function(r)
						{
							if (r)
								$('#inputForm').ajaxSubmit(options);
						});
				}
				else
				{
					//alert(cityCardold);
					//alert(cityCard);
					//alert($("#dRoomName").text());
					
					
					$('#inputForm').ajaxSubmit(options);
				}
			}else{return false;}
		}
		
		function showRequest(){
			 return $("#inputForm").valid();
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
			$("#carNum_tr"+ newid).append("<td  width='50%' colspan='2'><input name='_car_num_"+ newid +"' id='_car_num_"+ newid +"' class='infrom_input_s1' maxlength='7' onmousedown='addprefix(&quot;_car_num_"+newid+"&quot;)' onblur='validCarNum("+newid+")'/><div id='alreadyAllot_"+ newid +"' style='display: none;'>车牌号已存在，请勿重复登记！</div><div id='repeat_"+ newid +"' style='display: none;'>车牌号已添加，请勿重复添加！</div><div id='length_lack_"+ newid +"' style='display: none;'>请输入正确的车牌号！</div></td>");
			$("#carNum_tr"+ newid).append("<td  width='21%'><input type='checkbox'   name='isFastenCarNum"+newid+"' id='isFastenCarNum"+newid+"' value='1' onclick='changeThisVal(&quot;isFastenCarNum"+newid+"&quot;)'/>占用内部车位<a onClick='del(this)' class='infrom_a' href='javascript:void(0)'>删除</a></td>");
			$("#carNum_tr"+ newid).append("<td></td>");
		}
		
		// 移除行
		function del(obj) {
			$(obj).parent().parent().remove();
		}
		
		function isRepeat(idx){
			var status = false;
			var compID = "_car_num_"+idx;
			var compName = $("#"+compID).val().replace(/(^\s*)|(\s*$)/g, "");
			$(":text[id^='_car_num_']").each(function() {
				if ($(this).attr("id") != compID) {
					var name = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
					if (name != "" && compName == name) status = true;
				}
			});
			return status;
		}
		
		function validCarNum(idx) {
			if (isRepeat(idx)) {	// 存在重复的车牌
				$("#repeat_"+idx).show();
				$("#carNumIsValid").val("1");
				return;
			} else {
				$("#repeat_"+idx).hide();
				$("#carNumIsValid").val("");
			}
			var carNum = $("#_car_num_"+idx).val().replace(/[ ]/g,"");
			if (carNum.length >0) {
				$("#length_lack_"+idx).hide();
				$.ajax({
		            type: "POST",
		            url: "${ctx}/app/per/validCarNum.act",
		            data: {"carNum": carNum, "userId": $("#userid").val()},
		            dataType: 'text',
		            async : false,
		            success: function(msg){
	                    if(msg == "exist") {
	                    	$("#alreadyAllot_"+idx).show();
	                    	$("#carNumIsValid").val("1");
	                    } else {
	                    	$("#alreadyAllot_"+idx).hide();
	                    	$("#carNumIsValid").val("");
	                    }
		            },
		            error: function(msg, status, e){
		            	easyAlert("提示信息", "验证车牌号出错！","info");
		            }
		         });
			} else if (carNum == "") {
				$("#length_lack_"+idx).show();
				$("#carNumIsValid").val("1");
			}
		}
		
		function changeThisVal(id) {
			if ($("#"+id).attr("checked")=="checked")  $("#"+id).val("2");
			else $("#"+id).val("1");
		}
		
		function closeWin() {
			var url = "${ctx}/app/personnel/perInfoCheckin.act?orgId=${orgId}";
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
			
		/*管理员重置用户密码*/
		function pwdReset(userid){
			easyConfirm('提示', "密码将被重置为：123456，是否确定？", function(r){
				if (r) {
					var url = "${ctx}/app/personnel/unit/manage/resetPwd.act";
					var data = {userid:userid};
					var sucFun =  function(data){
						if (data.status == '1'){
							easyAlert("提示信息", "重置密码成功!","info");
						}else {
							easyAlert("提示信息", "重置密码失败!","info");
						}
					};
					var errFun = function (){
						easyAlert("提示信息", "重置密码失败,请检查服务器是否正常!","info");
					};
					ajaxQueryJSON(url, data, sucFun, errFun);
				}
			});
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
			
		/*临时人员信息展示*/
		function showOrHideTempInfoRow(){
			if ($("#isTempUser").attr("checked")=="checked") {
				$("#isTempUser").val("04");
				$("#tempUserInfoRow").show();
				$("#leaderLevelrow").hide();
				$("#attendance_approvers_id").hide();
			} else {
				$("#tempUserInfoRow").hide();
				$("#isTempUser").val("01");
				$("#leaderLevelrow").show();
				$("#attendance_approvers_id").show();
			}
		}
		
		/*单位考勤管理员*/
		function setAttendanceAdmin(){
			if ($("#isAttendanceAdmin").attr("checked")=="checked") {
				$.ajax({
					type: "POST",
					url: "${ctx}/app/per/orgIsContainAttendanceAdmin.act",
					data: {"userId": $("#userid").val()},
			        dataType: 'text',
					async : false,
					success: function(msg){
						if('' == msg){
							$("#isAttendanceAdmin").val("1");
						}else{
							 $("#isAttendanceAdmin").val("0");
							 $("#isAttendanceAdmin").removeAttr("checked");
							 easyAlert("提示信息", '本单位已经有考勤管理员：'+msg,"info");
						}
					},
					error: function(msg, status, e){
					    easyAlert("提示信息", "操作出错！","info");
					}
				});
			} else {
				$("#isAttendanceAdmin").val("0");
			}
		}
		
		/**
		* 审批人员选择
		*/
		function selectApprovers(roomId){
			
			if(!setAuthBtnStatus())
		    {
		    	return false;
		    }
		    else
		    {
		    	var url = "${ctx}/app/per/attendanceApprovers.act?roomId="+$("#userid").val()+"&dtype="+$("#orgId").val();
				openEasyWin("approversInput","选择考勤审批人员",url,"520","420",false);
		    }
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
		
		function showApprovers(appNames){
			$("#attendance_approvers").val(appNames);
		}
		
	</script>
	</head>

<body>
<div class="inform_index">
   
	<input type="hidden"  id="msg"/>
	<form id="inputForm" action="saveRegister.act" method="post" autocomplete="off">
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
	  
		<input type="hidden" name="dRoomId" id="dRoomId"/>
		
		<div class="bgsgl_conter" style="border-top:#7f90b3 1px solid">
	  	    <div class="bgsgl_right_list_border">
			  <div class="bgsgl_right_list_left">登录基本信息2</div>
			</div>
		<div class="infrom_ryjbxx">
		
 	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  	<td width="5%"></td>
    <td width="15%"><div class="infrom_td"> <font style="color: red">*</font>登录名</div></td>
    <td width="21%" style="color:#808080;font-size:13px;">
    	<input type="text" class="infrom_input_s" name="loginUid" id="loginUid"   maxlength="20" onchange="checkUniqueId('loginUid');" />
		<input type="checkbox" name="isTempUser" id="isTempUser" onclick="showOrHideTempInfoRow()"/>
		临时人员
		<div id="exist_loginUid" style="display: none;" generated="true" class="error">此登录名已存在！</div>
    </td>
    <td width="4%"></td>
    <td width="5%"></td>
    <td width="5%"></td>
    <td width="15%"><div class="infrom_td">处室</div></td>
    <td width="21%"><input type="text" class="infrom_input" value="${org.name}" readonly="readonly" disabled/></td>
    <td width="4%"></td>
    <td width="5%"></td>
  </tr>
  <tr id="leaderLevelrow">
  <td width="5%"></td>
    <td width="15%"><div class="infrom_td">人员类别</div></td>
    <td width="21%" style="color:#808080;font-size:13px;">
    	<s:select list="#application.DICT_LEADER_LEVEL" cssStyle="width:100%;list-style:none;" listKey="dictcode" listValue="dictname" name="stage" id="leaderLevel"/>
    <td width="4%"></td>
    <td width="5%"></td>
    <td width="5%"></td>
    <td width="15%"><div class="infrom_td"><input type="checkbox" name="isAttendanceAdmin" id="isAttendanceAdmin" onclick="setAttendanceAdmin()"/></div></td>
    <td width="21%" style="color:#808080;font-size:13px;">单位考勤管理员</td>
    <td width="4%"></td>
    <td width="5%"></td>
  </tr>
  <tr id="tempUserInfoRow">
  	<td></td>
  	<td><div class="infrom_td"><font style="color: red">*</font>开始日期</div></td>
    <td><input type="text"  class="infrom_input_s" onchange="checkRequired('dateFrom');" onclick="WdatePicker({isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'dateTo\',{M:-0,d:-1})||\'2020-10-01\'}'})" name="dateFrom" id="dateFrom"/>
    	<img onclick="WdatePicker({isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'dateTo\',{M:-0,d:-1})||\'2020-10-01\'}',el:'dateFrom'})"
			src="${ctx}/scripts/widgets/My97DatePicker/skin/datePicker.gif"
			width="16" height="22" align="absmiddle" />
		<div id="required_dateFrom" style="display: none;" generated="true" class="error">请输入开始日期</div>
    </td>
    <td></td>
	<td></td>
    <td></td>
    <td><div class="infrom_td"><font style="color: red">*</font>结束日期</div></td>
    <td>
		<input type="text"  class="infrom_input_s" onchange="checkRequired('dateTo');" onclick="WdatePicker({isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'dateFrom\',{M:-0,d:1})}',maxDate:'2020-10-01'})" name="dateTo" id="dateTo"/>
    	<img onclick="WdatePicker({isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'dateFrom\',{M:-0,d:1})}',maxDate:'2020-10-01',el:'dateTo'})"
			src="${ctx}/scripts/widgets/My97DatePicker/skin/datePicker.gif"
			width="16" height="22" align="absmiddle" />
		<div id="required_dateTo" style="display: none;" generated="true" class="error">请输入结束日期</div>
    </td>
    <td></td>
	<td></td>
  </tr>
</table>
<div class="infrom_borders">
</div>
		
		</div>
	  	    <div class="bgsgl_right_list_border">
			  <div class="bgsgl_right_list_left">人员基本信息</div>
			</div>
		<div class="infrom_ryjbxx" style="padding-top: 10px;">
		
 	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  	<td width="5%"></td>
  	<td width="15%"><div class="infrom_td">卡类型</div></td>
    <td width="21%"><table width="100%" border="0">
		<tr>
    <td width="63%">	
		<s:select list="#application.DICT_CARD_TYPE" cssStyle="width:100%;list-style:none;" listKey="dictcode" listValue="dictname" name="cityCardType" id="cityCardType" onchange="disOrEnLosted()"/>
	</td>
    <td style="font-size:14px;color:#808080;" width="7%">
		<input type="checkbox" name="isLosted" id="isLosted" disabled="disabled" onclick="modifyCardIsLosted()"/>
	</td>
    <td style="font-size:14px;color:#808080;" width="30%">
    	<span id="lostSpan">内部挂失</span>
    	<a class="infrom_a" href="javascript:void(0);" id="tmpCardInfo" style="display: none;width: 100%;" onclick="javascript:tmpCardDetail();">详细</a>
    </td>
  	</tr>
	</table>
	</td>
	<td width="4%"></td>
	<td width="5%"></td>
    <td width="10%"></td>
    <td width="10%"style="border-left: dashed 1px #808080;border-top: dashed 1px #808080;"><div class="infrom_td"> <font style="color: red">*</font>姓名</div></td>
  
		<c:if test="${user!=null && user.userid!=''}">
		  <td width="21%"style="border-top: dashed 1px #808080;"><input type="text" class="infrom_input_s" name="name" id="name" maxlength="6" />
	      <a class="infrom_a" href="javascript:void(0);" onclick="javascript:pwdReset('${user.userid}');">密码重置</a>
	      </td>
		</c:if>
		<c:if test="${user == null || user.userid ==''}">
		  <td width="21%"style="border-top: dashed 1px #808080;"><input type="text" class="infrom_input" name="name" id="name" maxlength="6" />
	    
	      </td>
		</c:if>
	
	<td width="4%"style="border-right: dashed 1px #808080;border-top: dashed 1px #808080;"></td>
	<td width="5%"></td>
  </tr>
  <tr>
  	<td></td>
  	<td><div class="infrom_td">卡号</div></td>
    <td><input type="text"  class="infrom_input_s"  onchange="validCityCard('blur'); checkTmpCardUnique();"   onmousedown="addprefix('cityCard')" maxlength="16" name="cityCard" id="cityCard"/>
		<!--
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
		-->
    	<a class="infrom_a" href="javascript:void(0);" onclick="readCard()">点击读卡</a>
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
    <td><div class="infrom_td">电子邮箱</div></td>
    <td><input type="text" class="infrom_input" name="email" id="email" maxlength="35"/></td>
    <td></td>
    <td></td>
  </tr>
</table>
<div class="infrom_borders">
<table width="100%">
  <tr>
    <td width="70%"><div class="infrom_td" style="text-align:left;">&nbsp;&nbsp;&nbsp;如果卡类型是市民卡，请先正确输入市民卡号或者刷卡。</div></td>
    <td width="1%"><input  type="checkbox" value="" id="isLogOut" 	<c:if test="${user.userid != '' && user.UType=='1'}">disabled ="false"</c:if>  name="isLogOut" onclick="userLogout();"/></td>
    <td width="10%"><div class="infrom_td" style="text-align:left;">停用此账户</div></td>
    <td width="9%"></td>
    <c:if test="${_loginname == 'admin'}">
		<td>
			<input  width="1%" type="checkbox" id="isSystem" disabled="disabled" checked name="isSystem" value="0" onclick="setChkVal('isSystem'); modifySMA()"/>
		</td>
		<td width="10%"><div class="infrom_td">设置为管理员</div> </td>
	</c:if>
	
	
    <!--<td width="8%"><a href="javascript:void(0);" class="infrom_checkbox">确&nbsp;&nbsp;定</a></td>
  --></tr>
</table>
</div>
		
		</div>
<!--电话信息-->
  	    <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">办公室&nbsp;&nbsp;电话信息</div>
		</div>
		<div class="infrom_ryjbxx">
			
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="20%"><div class="infrom_td">办公室</div></td>
    <td width="21%"><input type="text" class="infrom_input_s"  readonly name="roomNum"  id="roomNum"/>
      <a class="infrom_a" style="cursor:hand" onclick="linkFunction('roomDistribute')">分配</a></td>
    <td width="9%"></td>
    <td width="20%"><div class="infrom_td">电话号码</div></td>
	<td width="21%"><input type="text" class="infrom_input_s" readonly name="ipTelNum" id="ipTelNumval" />
      <a class="infrom_a" href="javascript:void(0);" onclick="javascript:linkFunction('ipTel')">分配</a></td>
    <td width="9%"></td>
  </tr>
  <tr>
    <td><div class="infrom_td">传真号</div></td>
    <td><input type="text" class="infrom_input_s"  id ="sfax" readonly/>
      <a class="infrom_a"  href="javascript:void(0);" onclick="javascript:linkFunction('fax')">分配</a></td>
    <td></td>
    <td><div class="infrom_td">网络传真号</div></td>
    <td><input type="text" class="infrom_input_s"   id = "faxweb" readonly />
      <a class="infrom_a" href="javascript:void(0);" onclick="javascript:linkFunction('webFax')">分配</a></td>
    <td></td>
  </tr>
</table>

<div class="infrom_borders">
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

<!--办公系统连携信息-->
	<div class="bgsgl_right_list_border">
	  <div class="bgsgl_right_list_left">办公系统连携信息</div>
	</div>
	<div class="infrom_ryjbxx">
		
	<table width="100%" border="0">
	  <tr>
	  	<td width="20%"><div class="infrom_td">OA系统登录名</div></td>
		<td width="21%"><input type="text" class="infrom_input" name="ucode" id="ucode" maxlength="10" onchange="checkUniqueId('ucode')" /></td>
		<td><div id="exist_ucode" class="error" style="display: none;">OA系统登录名已存在！</div></td>
	  </tr>
	</table>

	<div class="infrom_borders">
		<div class="infrom_td" style="text-align:left;">&nbsp;&nbsp;&nbsp;本系统与OA系统待办事项同步时使用此登录名。</div>
	</div>
	</div>
	
	<div style="margin: 10px;">
		<a onclick="saveData();" href="#"  id="savebut" class="buttonFinish">保&nbsp;&nbsp;存</a>
	</div>

<!--考勤审批人员设定-->
<div id="attendance_approvers_id">
	<div class="bgsgl_right_list_border">
	  <div class="bgsgl_right_list_left">考勤审批人员设定</div>
	</div>
	<div class="infrom_ryjbxx">
		
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="10%" style="padding-left: 10px;"><div class="infrom_mesu">审批人员</div></td>
			<td width="60%"><span style="white-space:normal,word-wrap:break-word;" id="attendance_approvers"></span></td>
			<td width="21%"><a class="infrom_a" id="approvers"  href="javascript:void(0);" onclick="javascript:selectApprovers('6617');">选择</a></td>
			<td>
			</td>
		</tr> 
	</table>
	</div>
</div>	

<!--权限信息-->
<div class="bgsgl_right_list_border">
		<div class="bgsgl_right_list_left">权限信息</div>
</div>
<div class="infrom_ryjbxx">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="10%" style="padding-left: 10px;"><div class="infrom_mesu">门锁</div></td>
			<td width="60%"><span style="white-space:normal,word-wrap:break-word;" id="dRoomName"></span></td>
			<td width="21%"><a class="infrom_a" id="door_auth"  href="javascript:void(0);" onclick="javascript:linkFunction('door');">授权</a></td>
			<td>
			</td>
		</tr> 
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
<div id='companyWinApp' class='easyui-window' collapsible='false'
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<iframe id='companyIframeApp' name='companyIframe'
			style='width: 100%; height: 100%;' frameborder='0'></iframe>
	</div>
</body>
</html>
