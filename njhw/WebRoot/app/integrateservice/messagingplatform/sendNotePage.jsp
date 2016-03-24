<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
  <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

    <script type="text/javascript">
	function returnon(obj){
		obj.src="${ctx}/app/integrateservice/messagingplatform/images/returnshow.png";
	}
	function returnout(obj){
		obj.src="${ctx}/app/integrateservice/messagingplatform/images/return-def.png";
	}
	function reutenPage(){
		  var url = "${ctx}/app/messagingplatform/reutenPage.act";
			var sucFun = function(data) {
				$("#message_min").replaceWith(data);
			};
			var errFun = function() {
				alert("加载数据出错!");
			};
			ajaxQueryHTML(url, null, sucFun, errFun);

	}
	function showResponse(responseText,statusText) {
		   if(responseText.status=="true"){
			 	alert("发送成功并保存到发件箱！");
			 }else {
			    alert("发送成功未保存到发件箱！");
			 }
	    }
function showRequest(){
	
	 	return $('#sendMessageForm').valid();
	}
	function sendPhoneMessage(){
				
		var options = {
				type:'post',
				dataType:'json',
				beforeSubmit:showRequest,
				success: showResponse
			}; 
		var flag = $("#check").attr('checked');
		if (flag == "checked") {
			$("#isNoSave").val("1");
			
		} else {
			$("#isNoSave").val("2");
		}

		$('#sendMessageForm').ajaxSubmit(options);
	}
	
	function orgOrPhoneTree(){
		var url = "${ctx}/app/messagingplatform/orgUserToPhoneMess.act";
			var params = "";
			url += params;
			var width = 599;
			var left = (document.body.scrollWidth - width) / 2;
			$("#orgTreeWin").show("请选择收件人");
			$("#orgTreeWin").window({
				title : '请选择收件人',
				modal : true,
				shadow : false,
				closed : false,
				width : 599,
				height : 490,
				top : 10,
				left : left
				});
			$("#companyIframe").attr("src", url);
	}

	 $("#message_min").ready(function() {
		$("#messReceiverName").val("${name}");
		$("#megName").val("${mobile}");
		 
		
			$("#messReceiverName").focus();
			//为inputForm注册validate函数
		$("#sendMessageForm").validate({
			  meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
				messReceiverName: {
						required: true
					},
					
					messageContent:{
						required: true
					}
					
			},
			messages:{ 
				messReceiverName:{
				required:"请选择人员！"
				 },
				 messageContent:{
					 required:"请填写内容！"
				}
			}
			});
		 });
		 
</script>
<div id="message_min"  style="margin: 62px 0px 0px 22px ; background: url('${ctx}/app/integrateservice/messagingplatform/images/default.png'); width: 261px;height: 444px;">
<div style="float: left;margin-top: 7px; cursor:pointer;"  id="imgSrc"><img src="${ctx}/app/integrateservice/messagingplatform/images/return-def.png" onclick="reutenPage(this)" onmouseover="returnon(this)"  onmouseout="returnout(this)"/></div>
<div style="margin-left:88px; margin-top: 7px"><img src="${ctx}/app/integrateservice/messagingplatform/images/title.png"/></div>

<form id="sendMessageForm" action="sendPhoneMessage.act" method="post" autocomplete="off">
<input type="hidden" name="messReceiverId" id="messReceiverId" value="${messReceiverId }" />
<input type="hidden" name="isNoSave" id="isNoSave" value="" />
<input type="hidden" name="megName" id="megName" value="" />

<table style="margin-top:18px; margin-left:10px; width:250px; height:350px;">
<tr style="height: 40px;"valign="middle">
 <td style="font-size:14px; height:40px;" ><span style="height: 30px; line-height:30px; display: block; float: left; padding-right: 10px;" >至</span>
 <input type="text" name="messReceiverName" id="messReceiverName" style="float:left; line-height:26px; width:154px; height: 26px; border: 1px solid #6fa3cc" />
 
 <img src="${ctx}/app/integrateservice/messagingplatform/images/per.png" style="cursor:pointer; margin-left:5px; float: left;" onclick="javaScript:orgOrPhoneTree();" />

 </td>
</tr>
<tr>
<td style="font-size:14px;" style="height: 20px;" >内容</td>
</tr>
<tr>

<td style="height:180px;">
<textarea style="width:234px; height:180px;font-size:14px; border: 1px solid #6fa3cc;" id="messageContent" name="messageContent"></textarea>

</td>
</tr>
<tr style="text-align: right;" style="height:20px;">
<td style="font-size:14px;" ><input  type="checkbox" name="checkbox" id="check" checked="checked" onclick="showchecked()"/>&nbsp;&nbsp;存入发件箱</td>
</tr>
<tr style="text-align: center;">
<td><img onclick="javaScript:sendPhoneMessage();" src = "${ctx}/app/integrateservice/messagingplatform/images/sendnode.png" style="cursor:pointer;"/></td>
</tr>
</table>
</form>

</div>
<div id='orgTreeWin' class='easyui-window' collapsible='false'
	minimizable='false' maximizable='true'
	style='padding: 0px; background: #fafafa;  display: none; width:599px;height: 490px;overflow: hidden;'
	closed='true'>
	<iframe id='companyIframe' name='companyIframe' 
		style='width: 100%; height: 100%;' frameborder='0'></iframe>
</div>