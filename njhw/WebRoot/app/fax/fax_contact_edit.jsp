<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>新增通讯录</title><script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" type="text/css" rel="stylesheet"  />
		<link href="${ctx}/app/integrateservice/css/tongxunlu.css" type="text/css" rel="stylesheet"  />
		<script src="${ctx}/app/integrateservice/paddressbook/js/queryString.js" type="text/javascript" ></script>
		<script type="text/javascript">
		$(document).ready(function() {
			
			if ("${addEntity.nuaId}" != "") {
				$("#nuaId").val("${addEntity.nuaId}");
				$("#nuaGroup").val("${addEntity.nuaGroup}");
				$("#nuaName").val("${addEntity.nuaName}");
				$("#nuaName1").val("${addEntity.nuaName1}");
				$("#nuaPhone").val("${addEntity.nuaPhone}");
				$("#nuaTel1").val("${addEntity.nuaTel1}");
				$("#nuaTel2").val("${addEntity.nuaTel2}");
				$("#nuaTel3").val("${addEntity.nuaTel3}");
				$("#nuaMail").val("${addEntity.nuaMail}");
			} else {
				$("input :text").val("");
				$("input :hidden").val("");
				$("#nuaGroup").val("${gid}");
			}
			/* ----------start---------- */
			//添加传真号码			
			if($.trim(getQueryString("fax_no"))!="")
			{
				if($.trim($("#nuaTel3").val())=="")
					$("#nuaTel3").val($.trim(getQueryString("fax_no")));
				else  
					$("#nuaTel3").val($.trim($("#nuaTel3").val())+","+$.trim(getQueryString("fax_no")));
			}				
			//下拉列表隐藏
			if($.trim(getQueryString("fax_no"))=="")
				$("#locationContainer").hide();
			else {
				
				$("#locationContainer").show();
			}
			//载入通讯录下拉列表
			queryPersonalContactList();
			//下拉列表触发事件
			$("#storeLocation").change(function() {				
				if($(this).val()!=0)
					window.location.href="contactEdit.act?nuaId="+$(this).val()+"&titleNum=edit&fax_no="+$.trim(getQueryString("fax_no"));
				else
					window.location.href="contactEdit.act?gid=0&titleNum=add&fax_no=" + $.trim(getQueryString("fax_no"));
				
			});
			/* ----------end---------- */
			jQuery.validator.addMethod("isFax", function(value, element) {
				var telephone = /^([0-9]{3,4}\-){0,1}[0-9]{7,8}(\-[0-9]{0,9}){0,1}$/;
				var isOk = true;
				var convVal = "";
				
				if (value.replace(/(^\s*)|(\s*$)/g, "") != "") {
					if (value.indexOf(',') > 0 || value.indexOf('，') > 0) {
						value = value.replace('，',',');
						var telList = value.split(',');
						for ( var i = 0; i < telList.length; i++) {
							var tel = telList[i].replace(/(^\s*)|(\s*$)/g, "");
							if (tel != "") {
								if (i == telList.length - 1) convVal += tel;
								else  convVal += tel+",";
								if (telephone.test(tel) == false) isOk = false;
							}
						}
						$(element).val(convVal);
					} else {
						return telephone.test(value);
					}
					return isOk;
				} else return true;
			});
			
			jQuery.validator.addMethod("isTelePhone", function(value, element) {
				var telephone = /^([0-9]{3,4}\-){0,1}[0-9]{7,8}$/;
				var isOk = true;
				var convVal = "";
				
				if (value.replace(/(^\s*)|(\s*$)/g, "") != "") {
					if (value.indexOf(',') > 0 || value.indexOf('，') > 0) {
						value = value.replace('，',',');
						var telList = value.split(',');
						for ( var i = 0; i < telList.length; i++) {
							var tel = telList[i].replace(/(^\s*)|(\s*$)/g, "");
							if (tel != "") {
								if (i == telList.length - 1) convVal += tel;
								else  convVal += tel+",";
								if (telephone.test(tel) == false) isOk = false;
							}
						}
						$(element).val(convVal);
					} else {
						return telephone.test(value);
					}
					return isOk;
				} else return true;
			});
			
			jQuery.validator.addMethod("isRepeat", function(value, element) {
				var isOk = true;
				if (value.replace(/(^\s*)|(\s*$)/g, "") != "") {
					if (value.indexOf(',') > 0 || value.indexOf('，') > 0) {
						value = value.replace('，',',');
						var vallist = value.split(',');
						for ( var i = 0; i < vallist.length; i++) {
							var val = vallist[i].replace(/(^\s*)|(\s*$)/g, "");
							if (val != "") {
								for (var j = 0; j < vallist.length; j++) {
									if (i != j && val == vallist[j]) return false;
								}
							}
						}
					}
				}
				return isOk;
			});
			
			jQuery.validator.addMethod("isMobile", function(value, element) {
				var mobileZZ = /^0{0,1}(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
				var isOk = true;
				var convVal = "";
				
				if (value.replace(/(^\s*)|(\s*$)/g, "") != "") {
					if (value.indexOf(',') > 0 || value.indexOf('，') > 0) {
						value = value.replace('，',',');
						var mobileList = value.split(',');
						for ( var i = 0; i < mobileList.length; i++) {
							var mobile = mobileList[i].replace(/(^\s*)|(\s*$)/g, "");
							if (mobile != "") {
								if (i == mobileList.length - 1) convVal += mobile;
								else  convVal += mobile+",";
								if (mobileZZ.test(mobile) == false) isOk = false;
							}
							
						}
						$(element).val(convVal);
					} else {
						return mobileZZ.test(value);
					}
					return isOk;
				} else return true;
			});
			
			$("#inputForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					nuaName: {
						required: true,
						maxlength: 20
					},
					nuaName1: {
						maxlength: 20
					},
					nuaPhone:{
						isMobile:true,
						isRepeat:true
					},
					nuaMail:{
	                    email:true,
	                    maxlength:50
	               	},
	               	nuaTel1:{
	               		isTelePhone: true,
						isRepeat:true
	               	},
	               	nuaTel2:{
	               		isTelePhone: true,
						isRepeat:true
	               	},
	               	nuaTel3:{
	               		isFax: true,
						isRepeat:true
	               	}
				},
				messages:{
					nuaName: {
						required: "请输入联系人姓名",
						maxlength: "姓名长度不能大于20个字符"
					},
					nuaName1: {
						maxlength: "昵称长度不能大于20个字符"
					},
					nuaPhone:{
					  isMobile:"请输入正确的手机号",
					  isRepeat:"请勿重复输入手机号"
					},
	               	nuaMail:{
	                  isEmail: "请输入正确的邮箱",
	                  maxlength: "邮箱长度不能大于50个字符"
	               	},
	               	nuaTel1:{
	               		isTelePhone: "请输入正确的住宅电话",
	               		isRepeat:"请勿重复输入住宅电话"
	               	},
	               	nuaTel2:{
	               		isTelePhone: "请输入正确的办公电话",
	               		isRepeat:"请勿重复输入办公电话"
	               	},
	               	nuaTel3:{
	               		isFax: "请输入正确的传真电话",
	               		isRepeat:"请勿重复输入其他电话"
	               	}
				}
			});
		});
		function queryPersonalContactList() {
			//alert();
			// post请求
			$.post(
			// 请求action
			"getPersonalContactList.act", {
				// 参数设定
				//searchVal : searchVal
			}, function(jList, textstatus) {				
				if (jList != null) {
					for ( var i = 0; i < jList.length; i++) {
						$("#storeLocation").append("<option value='"+$.trim(jList[i].nuaid)+"'>"+jList[i].name+"</option>"); 
					}
					if($.trim(getQueryString("nuaId"))!="")
						$("#storeLocation").val($.trim(getQueryString("nuaId")));
				}
				
			}, "json");
		}
		function saveData(){
			if (showRequest()) {
				var options = {
				    dataType:'text',
				    success: function(responseText) { 
						if(responseText=='success') {
							if ($("#nuaId").val() != "") {
								if ("${isPopup}" == "1") {
									window.top.showMessageBox("保存成功！",function(){
										window.top.closeContactEdit();
									});
									//easyAlert('提示信息', '保存成功！', "info");
									//closeEasyWin('winId');
								} else {
									window.top.showMessageBox("保存成功！",function(){
										window.top.closeContactEdit();
									});
									//easyAlert('提示信息', '保存成功！', "info");
									//window.parent.loadMenu();
								}
							} else {
								window.top.showMessageBox("保存成功！",function(){
									window.top.closeContactEdit();
								});
								/* window.top.showConfirmBox("保存成功，是否继续添加？",function(){
									window.location.href = "contactEdit.act?gid="+$("#nuaGroup").val();									
								}) */
								//easyConfirm('提示信息', '保存成功，是否继续添加？', function(r){
								//	if (r) window.location.href = "contactEdit.act?gid="+$("#nuaGroup").val();
								//	else closeEasyWin('winId');
								//});
							}
					 	} else if(responseText =='error') {
					 		window.top.showMessageBox("保存出错！");
					 		//easyAlert("提示信息", "保存出错！","info");
						} 
					}
				};
				$('#inputForm').ajaxSubmit(options);
			}
		}
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		function closeWin() {
			closeEasyWin('winId');
		}
	</script>
	</head>
	
	<body style="background: #FFF;">
	<div class="tongxunlu_main" style="width:97%">
	<div class="bgsgl_right_list_border" style="display: none;">
		<div class="bgsgl_right_list_left">${title }联系人</div>
	</div>
		<form id="inputForm" action="contactSave.act" method="post" autocomplete="off">
			<!--<input type="hidden" name="nuaGroup" id="nuaGroup" value="${gid }"/>-->
			<input type="hidden" name="nuaId" id="nuaId"/>
			<table align="center" border="0" width="100%" class="tongxunlu_form_table">
				<tr>
					<td class="tongxunlu_form_label" > <label id="group_label"><font style="color: red">*</font>分组：</label> </td>
					<td> 
						<div style="float: left;" id="group_select">
							<label class="tongxunlu_form_label">&nbsp;</label>
							<select  name="nuaGroup" id="nuaGroup" style="width: 120px;">
								<option value="0">无</option>
								<c:forEach var="group" items="${groupList}">
									<option value="${group.NAG_ID }">${group.NAG_NAME }</option>
		               			</c:forEach>
							</select>
						</div>				
					
						<div style="float: right;margin-right: 225px;" id="locationContainer">
							<label class="tongxunlu_form_label">存储联系人：</label>
							<select id="storeLocation" style="width: 120px;">
								<option value="0">--新建联系人--</option>
								
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td class="tongxunlu_form_label"> <font style="color: red">*</font>姓名： </td>
					<td>&nbsp; <s:textfield name="nuaName" cssClass="tongxunlu_input" id="nuaName" theme="simple" size="50" maxlength="20" /> </td>
				</tr>
				<tr>
					<td class="tongxunlu_form_label">昵称： </td>
					<td>&nbsp; <s:textfield name="nuaName1" cssClass="tongxunlu_input" id="nuaName1" theme="simple" size="50" maxlength="20" /> </td>
				</tr>
				<tr>
					<td class="tongxunlu_form_label">手机： </td>
					<td> 
						&nbsp; <s:textfield name="nuaPhone" cssClass="tongxunlu_input" id="nuaPhone" theme="simple" size="50" maxlength="100" />
						例:(0)13866669999
					</td>
				</tr>
				<tr>
					<td class="tongxunlu_form_label">住宅电话： </td>
					<td> 
						&nbsp; <s:textfield name="nuaTel1" cssClass="tongxunlu_input" id="nuaTel1" theme="simple" size="50" maxlength="100" />
						例:025(0)-3247240(0)或3247240(0)
					</td>
				</tr>
				<tr>
					<td class="tongxunlu_form_label">办公电话： </td>
					<td>
						&nbsp; <s:textfield name="nuaTel2" cssClass="tongxunlu_input" id="nuaTel2" theme="simple" size="50" maxlength="100" />
						例:025(0)-3247240(0)或3247240(0)
					</td>
				</tr>
				<tr>
					<td class="tongxunlu_form_label">传真： </td>
					<td>
						&nbsp; <s:textfield name="nuaTel3" cssClass="tongxunlu_input" id="nuaTel3" theme="simple" size="50" maxlength="100" />
						例:025(0)-3247240(0)或3247240(0)
					</td>
				</tr>
				<tr>
					<td class="tongxunlu_form_label">邮箱： </td>
					<td>&nbsp; <s:textfield name="nuaMail" cssClass="tongxunlu_input" id="nuaMail" theme="simple" size="50" maxlength="30" /> </td>
				</tr>
				<tr>
					<td class="form_label">注：</td>
					<td>多个手机或电话请以逗号分隔</td>
				</tr>
			</table>
			
<div class="energy_fkdj_botton_ls">
						<a style="cursor: pointer;" class="fkdj_botton_left" onclick="saveData();" id="savebut">保存</a>　　
						<a style="cursor: pointer;" class="fkdj_botton_right" onclick="window.top.closeContactEdit();">关闭</a> 
</div>
		</form>
	</div>
	</body>
</html>