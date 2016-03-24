<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>访客预约</title>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript" />
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
		<script type="text/javascript">
		
			$(document).ready(function(){
	            $("#uepSex").val("${map.UEP_SEX}");//性别
				$("#plateNumid").val("${map.PLATE_NUM}");//车牌				
				$("#roomId").val("${map.ROOM_ID}");  //房间id
				$("#telId").val("${map.TEL_ID}");// mac地址
				$("#TelNumid").val("${map.TEL_NUM}");//电话号码
				
				$("#feeType").val("${map.FEE_TYPE}");
				var id = $("#userid").val();
				var message = $("#message").val();
				if (message == "true") {
					
					alert("保存成功!");

				} else if (message == "false") {
					
					alert( "保存失败!");
				}



		        //空格
		      	 jQuery.validator.methods.nameValue = function(value, element) {
						
		      		 if(value.indexOf(" ")==-1){
		                return true;
		                }else{
		                     return false;
		              }
			       };
		        //长度
			        jQuery.validator.methods.lengthValue = function(value, element) {
						
			      		 if(value.length>30){
			                 return false;
			                 }else{
			                     return true;
			               }
				        };
				     

				//市民卡校验
					jQuery.validator.methods.validateNvrCard = function(value,
							element) {
                      
						if (value == "") {
							return true;
						} else {
							return ajaxNvrCard(value);
							// return true;
						}
					};
					// 身份证号码验证 
					jQuery.validator.addMethod("isIdCardNo", function(value, element) {
						 
						return this.optional(element) || idCardNoUtil.checkIdCardNo(value);
					}, "请正确输入您的身份证号码");
					// 手机号码验证 
					jQuery.validator
							.addMethod(
									"isMobile",
									function(value, element) {
										var length = value.length;
										var uepMobile = /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
										return this.optional(element)
												|| (length == 11 && uepMobile
														.test(value));
									}, "请正确填写您的手机号码");

					//聚焦第一个输入框
					$("#displayName").focus();
					//为inputForm注册validate函数
					$("#inputForm").validate( {
						meta : "validate",// 采用meta String方式进行验证（验证内容与写入class中）
						errorElement : "div",// 使用"div"标签标记错误， 默认:"label"
						rules : {

						displayName :{
						      required : true
							},
							loginUid:{
								 required : true
								},	
							uepMail : {
							
							email : true
						},
						uepBak1 : {
							required : true
						//	validateNvrCard : true
						
						},
						uepMobile : {

							isMobile : true

						},
						residentNo : {
							isIdCardNo : true
						},
						uepSkill:{
							
							lengthValue :true
							},
						uepHobby:{
								
								lengthValue :true
								}
						},
						messages : {
							displayName :{
						      required : "姓名不能为空"
							},
							loginUid:{
								 required : "登录id不能为空"
								},						
							uepSkill:{
							lengthValue : "请输入20字以内"
							},
						uepHobby:{
								lengthValue : "请输入20字以内"
								},
							
							residentNo : {
								isIdCardNo : "请输入正确的身份证号"
							},
							uepMail : {
								required : " 请输入电子邮箱!",
								email : " 请输入合法电子邮箱!"
							},
							uepBak1 : {
								//validateNvrCard : "市民卡无效",
							
							}
							
						}
					});

				});
				
				
	function ajaxNvrCard(nvrCard) {
		var res = true;
		if (nvrCard != "") {
			var url = "${ctx}/app/personnel/unit/manage/checkCityCar.act";
			var data = {
				nvrCard : nvrCard
			};
			sucFun = function(data) {
				if (data.state != "error") {
					res = true;

				} else {
					res = false;

				}
			};
			errFun = function(data) {

			};
			ajaxQueryJSON(url, data, sucFun, errFun);
		}
		return res;
	}
	//市民卡挂失
	function lossCityCardId() {
		var cityCardId = $("#uepBak1").val();

		var roomid = $("#roomId").val();
		$.ajax( {
			type : "POST",
			url : "${ctx}/app/personnel/unit/manage/lossCityCardId.act",
			data : {
				cityCardId : cityCardId,
				roomid : roomid
			},
			dataType : 'json',
			async : false,
			success : function(json) {
				if (json.status == 0) {
					alert( "挂失成功!");
				} else {
					alert("挂失失败!");
				}
			}
		});
	}
	function checkPic(obj) {

		var reslut = true;
		var picType = obj.substring(obj.length - 3, obj.length);
		if (picType == ("jpg")) {
			reslut = false;
		}
		if (picType == ("gif")) {
			reslut = false;
		}
		if (picType == ("bmp")) {
			reslut = false;
		}
		return reslut;
	}

	function isJpg() {

		var img = $("#img").val();

		if (img!=null&&img!="") {

			if (checkPic(img)) {
				alert("文件只能上传JPG,GIF,BMP格式");

				return false;
			}

		}
	}

	function ajaxFileUpload1() {
		
		isJpg();

		var uepId = $("#uepId").val();
		var userid = $("#userid").val();
	    var	loginUid = $("#loginUid").val();
	    var	displayName = $("#displayName").val();
	    var orgId = $("#orgId").val();
		//alert(uepId+"/"+userid);
		$.ajaxFileUpload({
			url : '${ctx}/app/fileupload/saveImg.act?uepId=' + uepId + '&userid=' + userid + '&displayName=' +displayName + '&loginUid='+loginUid +'&orgId='+ orgId ,
			secureuri : false,
			fileElementId : 'img',
			dataType : 'json',//返回值类型 一般设置为json  
			success : function(json) {
				
			$("#img5").attr("src", json.imgSrc);
			$("#uepId").val(json.uepId);
			$("#userid").val(json.userid);
		//	alert($("#uepId").val()+"//"+$("#userid").val())
			if(json.error=="error"){
                alert("请先填写用户名和登录id");
				}

		},
		error : function(data, status, e) {
			alert(e);
		}

		});

	}

	function buttonSubmit(){
	
		var plateNum = $("#plateNumid").find("option:selected").text();	//车牌
		plateNum=$.trim(plateNum);
		var roomId = $("#roomId").find("option:selected").text();	    //房间号
		roomId=$.trim(roomId);
		var telId = $("#telId").find("option:selected").text();        //mac
		telId=$.trim(telId);
    	var telNum = $("#TelNumid").find("option:selected").text();   //电话号码
    	telNum=$.trim(telNum);
		$("#roomInfo").val(roomId);
		$("#plateNum").val(plateNum);
		$("#telNum").val(telNum);
		$("#telMac").val(telId);
       $("#inputForm").submit(); 

        
		}

	function resetPwd(){
     
        var userid= $("#userid").val();
        if(userid==""||userid==null){
            alert("请先创建用户！")
            }else{

    	$.ajax({
			type : "POST",
			url : "${ctx}/app/personnel/unit/manage/resetPwd.act",
			data : {
				
				userid : userid
			},
			dataType : 'json',
			async : false,
			success : function(json) {
				if (json.status == 0) {
					alert( "密码重置成功!");
				} else {
					alert("密码重置失败!");
				}
			}
		});
		}
	}

function isSystem(){
	 var userid= $("#userid").val();
     if(userid==""||userid==null){
         alert("请先创建用户！")
         }else{

 	$.ajax({
			type : "POST",
			url : "${ctx}/app/personnel/unit/manage/isSystem.act",
			data : {
				
				userid : userid
			},
			dataType : 'json',
			async : false,
			success : function(json) {
				if (json.status == 0) {
					alert( "设置成功!");
				} else {
					alert("设置失败!");
				}
			}
		});
		}

	
}

	
</script>
	</head>

	<body style="background: #fff">

		<form id="inputForm" action="savePersonnelExinfo.act" method="post"
			autocomplete="off">

			<table align="center" border="0" width="100%" class="form_table">
				<input type="hidden" name="uepId" id="uepId"
					value="${map.UEP_ID}"/>
				<input type="hidden" name="orgId" id="orgId" value="${departmentid}" />	
				<input type="hidden" name="userid" id="userid" value="${curUserId}" />
				<input type ="hidden" name="roomInfo" id="roomInfo" />
				<input type = "hidden" name="plateNum" id="plateNum"/>
				<input type = "hidden"  name="telNum" id = "telNum"/>
				<input type = "hidden"  name="telMac" id="telMac"/>
				<tr>
				<td class="form_label">
						姓名：
					</td>
					<td>
                 <input type="text" style="width:120px" name="displayName" id="displayName" value="${map.DISPLAY_NAME }"/>
						
					</td>
					<td class="form_label">
					    登录id：
					</td>
					<td>
					<input type="text" style="width:120px" name="loginUid" id="loginUid" value="${map.LOGIN_UID }"/>	
					</td>
				   <td> 
					   <input type="button" name="pwds" id="pwds" value="密码重置" onclick="resetPwd()"/>
					  
					</td>
					 <td> 
					   <input type="button" name="system" id="system" value="设置为管理员" onclick="isSystem()"/>
					  
					</td>
				</tr>
				
				<tr>

					<td class="form_label">
						性别：
					</td>
					<td>

						<s:select list="#application.DICT_SEX" listKey="dictcode"
						 listValue="dictname" id="uepSex" name="uepSex" />
					</td>
					<td class="form_label">
						身份证号：
					</td>
					<td>
						<input type="text" name="residentNo"
						 style="width:120px" value="${map.RESIDENT_NO }" theme="simple" size="18"
							maxlength="20" />
					</td>
					<td class="form_label">
						照片：
					</td>
					<td>
						<input type="file" name="file" id="img"/>
						<input type="button" id="sb" name="sb" value="上传"
							onclick="ajaxFileUpload1();" />
					</td>

				</tr>

				<tr>
				<td class="form_label">
						市民卡号：
					</td>
					<td>
						<input type="text" name="uepBak1" id="uepBak1"
						style="width:120px"	value="${map.UEP_BAK1}" cols="15" />
					</td>
				
				<td class="form_label">
						手机号：
					</td>
					<td>
						<input type="text" name="uepMobile"
						style="width:120px"	value="${map.UEP_MOBILE }" theme="simple" size="18"
							maxlength="20" />
					</td>
				
					
				
                 <td rowspan="6" colspan="2"  valign="top">
						<div id="result">
							<img id="img5" src="${imgSrc}" style="width:130px;height:110px;" />
						</div>
					</td>
				</tr>
				<tr>

					<td class="form_label">
						房间号：
					</td>
					<td>

						<select name="roomId" id="roomId">
							<c:forEach items="${EmOrgResList1}" var="vm1" varStatus="num">
								<option value="${vm1.eorId}">
									${vm1.resName}
								</option>
							</c:forEach>
						</select>
					</td>
               <td class="form_label">
						电话：
					</td>
					<td>
						<select name="telId" id="telId">
							<c:forEach items="${EmOrgResList3}" var="vm3" varStatus="num">
								<option value="${vm3.eorId}">
									${vm3.resName}
								</option>
							</c:forEach>
						</select>
					</td>
					
					
				</tr>
				<tr>
				  <td class="form_label">
						电话号码：
					</td>
					<td>
						<select name="TelNumid" id="TelNumid">
							<c:forEach items="${EmOrgResList5}" var="vm5" varStatus="num">
								<option value="${vm5.resName}">
									${vm5.resName}
								</option>
							</c:forEach>
						</select>
					</td>
					<td class="form_label">
						车牌号：
					</td>
					<td>

						<select name="plateNumid" id="plateNumid">
							<c:forEach items="${EmOrgResList4}" var="vm4" varStatus="num">
								<option value="${vm4.resName}">
									${vm4.resName}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
				
				
				
				
                   <td class="form_label">
						邮箱：
					</td>
					<td>
						<input type="text" name="uepMail" value="${map.UEP_MAIL}"
						style="width:120px"	theme="simple" size="18" maxlength="20" />
					</td>
					
					<td class="form_label">
						电子传真：
					</td>
					<td>
						<input type="text" name="uepFax" value="${map.UEP_FAX }"
						style="width:120px"	cols="15" />
					</td>
					
					</tr>
					<tr>
					
                      <td class="form_label">
						临时卡号：
					</td>
					<td>
						<input type="text" name="tmpCard" value="${map.TMP_CARD}"
						style="width:120px"	cols="15" />
					</td>
					 <td class="form_label">
						停车场收费：
					</td>
					<td>
							<s:select list="#application.DICT_FEE_TYPE" listKey="dictcode"
							listValue="dictname" id="feeType" name="feeType" />
					</td>
					
					
				</tr>
          
				<tr>
				    <td class="form_label">
						专业技能：
					</td>
					<td>
						<textarea name="uepSkill" id="uepSkill" rows="3" cols="30">${map.UEP_SKILL}</textarea>

					</td>
					<td class="form_label">
						个人爱好：
					</td>
					<td>
						<textarea name="uepHobby" id="uepHobby" rows="3" cols="30">${map.UEP_HOBBY}</textarea>

					</td>
				
				</tr>
			
			</table>
		

			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="6">
						<input type="button" name="loss" value="市民卡挂失"
							onclick="lossCityCardId()" />
						<input name="submitbutton" type="button" value="保存"  onclick="buttonSubmit()"/>

					</td>
				</tr>
			</table>
		</form>
		<input type="hidden" value="${requestScope.isSuc}" id="message" />

	</body>
</html>

