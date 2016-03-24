<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>发送短信</title>
		<script type="text/javascript">
		$(document).ready(function() {
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
		
			$("#inputForm").validate({
				meta :"validate",
				errorElement :"div",
				rules: {
					foreignReceiverInfo: {
						isMobile: true,
						isRepeat: true
					},
					content:{
						maxlength: 1000
					}
				},
				messages: {
					foreignReceiverInfo:{
						isMobile: "手机号格式错误，例:(0)13866669999，多个手机号以逗号分隔!",
						isRepeat:"请勿重复输入手机号"
					},
					content:{
						maxlength: "备注长度不能大于200个字符"
					}
				}
			});
			
			
			if ("${info}" != "") {
				var type = "${type}";
				var chooseText = "<div style='float: left; margin-right: 10px;'>${name}<img src='${ctx}/app/integrateservice/images/close.png' title='删除联系人' onclick='del(this, &quot;${type}TR&quot;, &quot;${type}AlreadyChoose&quot;, &quot;${info}&quot;, &quot;${type}ReceiverInfo&quot;)'/></div>";
				if (chooseText != "")  $('#${type}TR').show();
				else $('#${type}TR').hide();
				
				$('#${type}AlreadyChoose').children().remove();
				$('#${type}AlreadyChoose').append(chooseText);
				$('#${type}ReceiverInfo').val("${info}");
			}
		});
		
		function selectAddressRecieveid() {
			var ids = "";
			var info = $("#addressReceiverInfo").val();
			if (info != "") {
				var infoArray = info.split(",");
				for ( var idx in infoArray ) {
					var info_Array = infoArray[idx].split("_");	// 得到每个人员的信息数组
					ids += info_Array[0]+",";
				}
			}
			var url = "${ctx}/app/sendsms/showAddressTree.act?addressUserIds="+ids;
			$("#companyWin").show();
			$("#companyWin").window({
				title : '个人通讯录',
				modal : true,
				shadow : false,
				closed : false,
				width : 400,
				height : 350,
				top : 10,
				left : 30,
			});
			$("#companyIframe").attr("src", url);
		}
		
		function selectUnitRecieveid() {
			var ids = "";
			var info = $("#unitReceiverInfo").val();
			if (info != "") {
				var infoArray = info.split(",");
				for ( var idx in infoArray ) {
					var info_Array = infoArray[idx].split("_");	// 得到每个人员的信息数组
					ids += info_Array[0]+",";
				}
			}
			var url = "${ctx}/app/sendsms/showUnitTree.act?unitUserids="+ids;
			$("#companyWin").show();
			$("#companyWin").window({
				title : '单位通讯录',
				modal : true,
				shadow : false,
				closed : false,
				width : 400,
				height : 350,
				top : 10,
				left : 30,
			});
			$("#companyIframe").attr("src", url);
		}
		
		function saveData(){
			var foreign = $("#foreignReceiverInfo").val();
			var address = $("#addressAlreadyChoose").text();
			var unit = $("#unitAlreadyChoose").text();
			if (foreign == "" && address == "" && unit == "") {
				easyAlert("提示信息", "请选择联系人或输入手机号","info");
				return false;
			}
			
			if (showRequest()) {
				var options = {
			    dataType:'json',
			    success: function(json) { 
					if(json.status == 'success') {
						if (json.rst == '') {
							easyAlert("提示信息", "发送成功！","info", function(){
								closeEasyWin('winId');
							});
						} else {
							easyAlert("提示信息", json.rst+" 发送失败","info");
						}
				 	} else {
				 		easyAlert("提示信息", json.rst+" 发送失败","info");
					} 
				},
				error: function(json) { 
				 	easyAlert("提示信息", "发送出错！","info");
				}
			};
			$('#inputForm').ajaxSubmit(options);
			}
		}
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		function setChkVal() {
			if ($("#isSave").attr("checked") == "checked") {
				$("#isSave").val("y");
			} else {
				$("#isSave").val("n");
			}
		}
		
		function del(obj, trId, tdId, delObj, infoId) {
			$("#"+infoId).val($("#"+infoId).val().replace(delObj, ""));	// 去掉指定的值
			$(obj).parent().remove();	// 移除div
			if ($("#"+tdId).text() == "")  $("#"+trId).hide();	// 移除全部DIV后隐藏tr
		}
		
		function closeWin() {
			closeEasyWin('winId');
		}
	</script>
	</head>

	<body>
		<div>
		<form id="inputForm" action="saveSendSms.act" method="post" autocomplete="off">
			<table align="center" border="0" width="100%" class="form_table">
<!--				<input name="unitreceiverids" id="unitreceiverids" type="hidden"/>	 单位接收人ID -->
<!--				<input name="unitreceivernames" id="unitreceivernames" type="hidden"/>	 单位接收人 -->
<!--				<input name="unitreceivermobiles" id="unitreceivermobiles" type="hidden"/>	 单位接收人手机-->
				<input name="unitReceiverInfo" id="unitReceiverInfo" type="hidden"/>	<!-- 单位接收人信息-->
				
<!--				<input name="addressreceiverids" id="addressreceiverids" type="hidden"/>	 通讯录接收人ID -->
<!--				<input name="addressreceivernames" id="addressreceivernames" type="hidden"/>  通讯录接收人 -->
<!--				<input name="addressreceivermobiles" id="addressreceivermobiles" type="hidden"/>	 通讯录接收人手机 -->
				<input name="addressReceiverInfo" id="addressReceiverInfo" type="hidden"/>		<!-- 个人通讯录接收人信息-->
				<tr>
					<td class="form_label" width="100px;">其他手机号： </td>
					<td>
						<input type="text" name="foreignReceiverInfo" id="foreignReceiverInfo" size="50"/>　　
<!--						<a href="javascript:void(0)" style="text-decoration: none;" onclick="selectUnitRecieveid()">单位通讯录</a>　　-->
<!--						<a href="javascript:void(0)" style="text-decoration: none;" onclick="selectAddressRecieveid()">个人通讯录</a> -->
					</td>
				</tr>
				<tr id="unitTR" style="display: none;">
					<td class="form_label">收件人(单位)：</td>
					<td id="unitAlreadyChoose" style="word-wrap: break-word;"> </td>
				</tr>
				<tr id="addressTR" style="display: none;">
					<td class="form_label">收件人(个人)：</td>
					<td id="addressAlreadyChoose" style="word-wrap: break-word;"> </td>
				</tr>
				<tr>
					<td class="form_label">内容： </td>
					<td> <s:textarea name="content" id="content" cols="60" rows="10"></s:textarea></td>
				</tr>
				<tr>
					<td></td>
					<td><table><tr><td><input type="checkbox" id="isSave" name="isSave" onclick="setChkVal()" value="n"/></td><td>保存到发件箱</td></tr></table></td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="saveData();" id="savebut">发送</a>　　
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" onclick="closeWin()">关闭</a>
					</td>
				</tr>
			</table>
		</form>
		</div>
<div id='companyWin' class='easyui-window' collapsible='false' minimizable='false' maximizable='true'
	style='padding: 0px; background: #fafafa;  display: none; width:599px;height: 490px;overflow: hidden;' closed='true'>
	<iframe id='companyIframe' name='companyIframe'  style='width: 100%; height: 100%;' frameborder='0'></iframe>
</div>
	</body>
</html>
