<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
  <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
    
    <script type="text/javascript">
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
	
	function delSms(){
		//alert($('#smsid').val());
		
		//var str = $('#smsid').val();
    	//var url = "${ctx}/app/messagingplatform/deleteOutBoxMessages.act";
		//	var sucFun = function(data) {
		//		$("#listMessage").empty().html(data);
		//	};
		//	var errFun = function() {
		//		alert("加载数据出错!");
		//	};
		//	var data = "idNum=" + textstr;
		//	ajaxQueryHTML(url, data, sucFun, errFun);
		
	}
	
</script>
<div id="message_min"  style="margin: 0 0px 0px 22px ;  position:absolute; top:72px; background: url('${ctx}/app/integrateservice/messagingplatform/images/checkduanxi.png'); width: 259px;height: 442px;">
<div style=" float:right; margin-left:50px; margin-right:4px; margin-top: 7px; width: 22px; height: 25px;"><img src="${ctx}/app/integrateservice/messagingplatform/images/del-delfa.png" onclick="javaScript:delSms();"/></div>
<div style="float:right; margin-left:10px; margin-top: 7px; width: 80px; height: 31px;"><img src="${ctx}/app/integrateservice/messagingplatform/images/yifa1.png"/></div>
<div style=" flot:left;margin-top: 7px; width: 56px; height: 27px;"  id="imgSrc"><img src="${ctx}/app/integrateservice/messagingplatform/images/return-def.png" onclick="reutenPage(this)" onmouseover="returnon(this)"  onmouseout="returnout(this)"/></div>
<div style="margin: 40px 0 0 5px;">
<input id="smsid" name="smsid" type="hidden" value="${tCommonSmsBox.smsid}" />
<span style="display: block; margin:0 0 0 80px; ">至 ：&nbsp;&nbsp;&nbsp;${tCommonSmsBox.receiver}  </span>
</div>
<div style="margin:20px 0 0 30px; line-height: 20px;"><span>${tCommonSmsBox.content}</span></div>
<div style="position: absolute; top:310px; width: 220px; left:20px;"><span>
信息已发出<br />
发送于： &nbsp;&nbsp;&nbsp;&nbsp; ${tCommonSmsBox.receiver }<br/>
</span></div>
</div>