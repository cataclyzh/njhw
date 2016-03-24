<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
  - Author: ls 
  - Date: 2010-12-17 11:48:03
  - Description: 密码重置页面
  - update by sjy 页面校验 2010-12-23
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>密码重置</title>
		<%@ include file="/common/include/metaIframe.jsp"%>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<link href="${ctx}/common/pages/main/css/member.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		$(document).ready(function(){
			$("#step-2").hide();
			$("#step-3").hide();
			$(".member_nav1").addClass("selected");
			var steps = $(".member_list a");
			$(steps).bind("click", function(e){
				  var selected = steps.index(this) + 1;
                  if(selected == 1){
                    $(".member_nav1").addClass("selected");
                    $(".member_nav2").removeClass("selected");
                    $(".member_nav3").removeClass("selected");
                    $("#step-1").show();
                    $("#step-2").hide();
					$("#step-3").hide();
                  }
                  if(selected == 2){
                    $(".member_nav1").removeClass("selected");
                    $(".member_nav2").addClass("selected");
                    $(".member_nav3").removeClass("selected");
                    $("#step-1").hide();
                    $("#step-2").show();
					$("#step-3").hide();
                  }
                  if(selected == 3){
                    $(".member_nav1").removeClass("selected");
                    $(".member_nav2").removeClass("selected");
                    $(".member_nav3").addClass("selected");
                    $("#step-1").hide();
                    $("#step-2").hide();
					$("#step-3").show();
                  }
            }); 
			$("#username").val("${fn:trim(personInfoList.DISPLAY_NAME)}")
	      	$("#sex").val("${personInfoList.UEP_SEX}");
			$("#residentNo").val("${personInfoList.RESIDENT_NO}");
			$("#phone").val("${fn:trim(personInfoList.UEP_MOBILE)}");
			$("#email").val("${fn:trim(personInfoList.UEP_MAIL)}");
			$("#telNo").val("${fn:trim(personInfoList.TELNUM)}");
			
			//$("#carNum").val("${personInfoList.NUP_PN}");
			//卡类型 
			$("#cityCardType").val("${personInfoList.CARD_TYPE}");
		
			 if("${personInfoList.CARD_TYPE}" !="1")
			 {   $("#cityCard").val("${personInfoList.TMP_CARD}");
				 }
			 else	
				 
			 {   //卡号赋值市民卡号 
				 $("#cityCard").val("${cardsInfoList.CARD_ID}");
			}
		           
			$('body').bind('contextmenu', function() {
		    	return false;
		    });
			$('body').bind('paste', function() {
		    	return false;
		    });

			var options = {
				onkeyup: false,
				rules: {
					oldpw:{
						required: true,
						remote: { //验证密码是否正确
　　							 type:"POST",
　　							 url: "${ctx}/common/userOrgMgr/checkPassword.act",
　　 						 data:{
　　 							oldpw:function(){return $("#oldpw").val();}
　　 						}
　　 					}
					},
					newpw1:{
						required: true,
						minlength: 6
					},
					newpw2:{
						required: true,
						equalTo: "#newpw1"
					}
				},
				messages: {
					oldpw:{
						required: " 请输入旧密码!",
						remote: " 密码错误!"
					},
					newpw1:{
						required: " 请输入新密码!",
						minlength: " 密码需要六位以上!"
					},
					newpw2:{
						required: " 请输入确认密码!",
						equalTo: " 确认密码错误!"
					}
					
				}
			}
			
			$("#editForm").validate(options);
		
			$("#personInfoForm").validate({
				onkeyup: false,
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				//errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					loginId:{
						required: true,
						remote: { //验证登陆名是否存在
　　							 type:"POST",
　　							 url:"${ctx}/common/userOrgMgr/checkLoginId.act",
　　 						 data:{
　　 							loginId:function(){return $("#loginId").val();}
　　 						}
　　 					} 
					},
					username: {
						required: true
					},
					phone: {
						digits:true,
						maxlength:11,
						minlength:11,
					},
					email:{
						email:true
					}
					
					
				},
				messages:{
				    loginId:{
						required: " 登录名不能为空!",
						remote: " 登录名重复!"
					},
					username:{
						required: " 姓名不能为空!",
					 },
					phone: {
						digits:" 请输入正确的手机号码",
						maxlength:" 请输入正确的手机号码",
						minlength:" 请输入正确的手机号码",
					},
		           email:{
		             	email:" 请输入正确的邮箱地址"
		            }
		           

		            
		           
				}
			});
		});	
		
		
		
		document.onpaste=function(){return   false;}//屏蔽粘贴
		

		function sendIphoneVerificationCode(){
			$("#phone").removeAttr("disabled");
			var phone=$("#phone").val();
			$("#phone").attr("disabled", "disabled");
			$("#ggsj").append("<iframe id='ifrmComWin' name='ifrmComWin' style='width: 100%; height: 100%;' frameborder='0' src='${ctx}/common/userOrgMgr/sendIphoneVerificationCode.act?phone="+phone+"'></iframe>");
			$("#ggsj").show();
			var left = (document.body.scrollWidth - 500) / 2;
			$("#ggsj").window({
				title : "更改手机号",
				modal : true,
				shadow : false,
			    closed : false,
				width : 500,
				height : 180,
				top : 150,
				left : left,
				showType : "slide",
				onClose : function() {
					$("#ggsj").empty();
				},
			});
		}
		
		


		
//更新登录用户的名字
function updateLoginUserName(){

	 if(window.confirm('你确定要修改登录名？ ')){
	var  loginId = $("#loginId").val();
	if(loginId==""){
       $("#loginNameNotEmpty").text("登录名不能为空!");
		}else{
			
	 $("#loginNameNotEmpty").text(" ");
	var data = {loginId:loginId};
	var url="${ctx}/common/userOrgMgr/upDateLoginUserName.act";	
	sucFun = function (data){
		if(data.state =='0'){
		  alert("登录名重复不能保存！！");
		}
	};
	errFun = function (data){
	};
	ajaxQueryJSON(url,data,sucFun,errFun);
		}
	 }else{
        
          return false;
      }



	
}

function showResponse(responseText,statusText) {
    $("#phone").attr("disabled", "disabled");
    if (responseText != undefined) {
    	if(responseText['msg']='success') {
			easyAlert("提示信息", "修改用户信息成功!","info");
	 	} else if(responseText['msg'] =='error') {
		 	easyAlert("提示信息", "修改用户信息失败!","info");
		}
    }
}

//异步提交保存信息
function ajaxSubmitForm(){
	$("#personInfoForm").valid();
	if ($("#personInfoForm").valid()) {
		$("#phone").removeAttr("disabled");
	    $("#personInfoForm").ajaxSubmit({
	      type:"post",
	      dataType:'json',
	      url:"${ctx}/common/userOrgMgr/savePersonInfo.act",
	      success:showResponse
	    });
	}
}

//保存信息End 
 
function showResponse1(responseText,statusText) {
    if (responseText != undefined) {
		if(responseText['msg']='success') {
			easyAlert("提示信息", "修改密码成功!","info");
	 	} else if(responseText['msg'] =='error') {
		 	easyAlert("提示信息", "修改密码失败!","info");
		}
	}
}

//异步提交密码的更新
function submitPasssWordForm(){
	$("#editForm").valid();
	if ($("#editForm").valid()) {
	    $("#editForm").ajaxSubmit({
	      type:"post",
	      dataType:'json',
	      url:"${ctx}/common/userOrgMgr/updatePwd.act",
	      success:showResponse1
	    });
	}
}

//密码的修改End 


</script>

	</head>

	<body style="background: #fff">
		<div class="main1_main2_right_khfw">
			<div class="khfw_wygl" style="padding: 0 10px 10px 10px;">
				<div class="bgsgl_right_list_border">
					<div class="bgsgl_right_list_left">
						个人设置
					</div>
				</div>
				<div class="member_left">
					<div class="member_list">
						<a class="member_nav1" href="javascript:void(0);"></a>
						<a class="member_nav2" href="javascript:void(0);"></a>
						<a class="member_nav3" href="javascript:void(0);"></a>
					</div>
				</div>
				<div class="member_right" id="step-2">
					<form id="editForm" action="updatePwd.act" method="post">
						<div class="show_from_member">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="member_form_txt">
										旧密码：
									</td>
									<td>
										<s:password id="oldpw" name="oldpw" maxlength="20"
											cssClass="member_form_input" />
									</td>
								</tr>
								<tr>
									<td class="member_form_txt">
										新密码：
									</td>
									<td>
										<s:password name="newpw1" maxlength="20"
											cssClass="member_form_input" />
									</td>
								</tr>
								<tr>
									<td class="member_form_txt">
										再输入新密码：
									</td>
									<td>
										<s:password name="newpw2" maxlength="20"
											cssClass="member_form_input" />
									</td>
								</tr>
							</table>
						</div>
						<div style="height: 176px;">
						</div>
						<div class="show_pop_bottom">
							<a href="javascript:void(0);" onclick="javascript:submitPasssWordForm();">确&nbsp;&nbsp;定</a>
							<div class="clear"></div>
						</div>
					</form>
				</div>
				<div class="member_right" id="step-1">
					<form id="personInfoForm" action="savePersonInfo.act" method="post">
						<div class="show_from_member">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="member_form_txt">
										登录名：
									</td>
									<td>
										<input type="text" name="loginId" id="loginId" maxlength="15"
											value="${fn:trim(personInfoList.LOGIN_UID)}"
											onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"
											class="member_form_input" />
									</td>
								</tr>
								<tr>
									<td class="member_form_txt">
										姓名：
									</td>
									<td>
										<input name="username" id="username" class="member_form_input"
											type="text" />
									</td>
								</tr>
								<tr>
									<td class="member_form_txt">
										身份证号：
									</td>
									<td>
										<input type="text" name="residentNo" id="residentNo"
											class="member_form_inp_uts" disabled />
									</td>
								</tr>
								<tr>
									<td valign="top" class="member_form_txt">
										性别：
									</td>
									<td>
										<s:select list="#application.DICT_SEX" listKey="dictcode"
											listValue="dictname" name="sex" id="sex"
											cssClass="member_form_input" />
									</td>
								</tr>
								<tr>
									<td class="member_form_txt">
										手机号：
									</td>
									<td>
										<input type="text" name="phone" id="phone"
											class="member_form_input_short"
											style="display: inline-block;" disabled />
										<a href="javascript:void(0);" style="display: inline-block; margin-left: 10px;"
											class="form_update_btn"
											onclick="javascript:sendIphoneVerificationCode();">更&nbsp;改</a>
									</td>
								</tr>
								<tr>
									<td class="member_form_txt">
										办公室电话：
									</td>
									<td>
										<input type="text" name="telNo" id="telNo"
											class="member_form_inp_uts" maxlength="20" disabled />
									</td>
								</tr>
								<tr>
									<td class="member_form_txt">
										电子邮箱：
									</td>
									<td>
										<input type="text" name="email" id="email" maxlength="30"
											class="member_form_input" />
									</td>
								</tr>
							</table>
						</div>
						<div class="show_pop_bottom">
							<a href="javascript:void(0);" onclick="javascript:ajaxSubmitForm();">确&nbsp;&nbsp;定</a>
							<div class="clear"></div>
						</div>
					</form>
				</div>
				<div class="member_right" id="step-3">
					<div class="show_from_member">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="member_form_txt">
									卡类型：
								</td>
								<td>
									<s:select list="#application.DICT_CARD_TYPE" listKey="dictcode"
										listValue="dictname" name="cityCardType" id="cityCardType"
										disabled="true" cssClass="member_form_inp_uts" />
								</td>
							</tr>
							<tr>
								<td class="member_form_txt">
									卡号：
								</td>
								<td>
									<input type="text" name="cityCard" id="cityCard" maxlength="40"
										disabled class="member_form_inp_uts" />
								</td>
							</tr>
							<tr>
								<td valign="top" class="member_form_txt">
									门锁：
								</td>
								<td>
									<textarea name="" class="member_form_inp_textarea" cols=""
										rows="" disabled>${allFacilityList[0].DEV3 }</textarea>
								</td>
							</tr>
							<tr>
								<td class="member_form_txt">
									闸机：
								</td>
								<td>
									<input name="" type="text" class="member_form_inp_uts"
										value="${allFacilityList[0].DEV1 }" disabled />
								</td>
							</tr>
							<tr>
								<td class="member_form_txt">
									车牌：
								</td>
								<td>
									<textarea name="" class="member_form_inp_textarea" cols=""
										rows="" disabled>${allFacilityList[0].DEV4 }</textarea>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div id='ggsj' class='easyui-window' draggable="true" resizable="false" collapsible='false'
			minimizable='false' maximizable='true'
			style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
			closed='true'>
		</div>
	</body>
</html>
