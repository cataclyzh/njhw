<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: HJ
- Date: 2013-08-16
- Description: 退回理由输入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/metaIframe.jsp" %>
    <title>填写退回理由</title>
   
    <script type="text/javascript" src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
    <link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">	

    $(document).ready(function() {
		//聚焦第一个输入框
		$("#refuseReason").focus();
	});
    
		function refuseConfirm(){
			var refuseReason = $('#refuseReason').val();
			if (${type} == '1') {
				var url = "${ctx}/app/personnel/property/json/refuseAccessApply.act";
				var sucFun = function (json){
					easyAlert("提示信息",json.msg,"info", function() {
						window.top.$('#queryForm').submit();
						window.top.$("#refuseInput").window("close");
					});
				};
				var errFun = function (){
					alert("退回申请失败!");
				};
				var data = {id:'${id}',lockVer:'${lockVer}',refuseReason:refuseReason};
				$.ajax({
					url : url,
					data: data,
					dataType : 'json',//返回值类型 一般设置为json
					success : sucFun,
					error : errFun,
					async : false
				});
			} else if (${type} == '2') {
				window.top.$('#refuseReason').val(refuseReason);
				window.top.$('#queryForm').attr("action","refuseAccessApplyBatch.act");
				window.top.$('#queryForm').submit();
				window.top.$("#refuseInput").window("close");
			} else if (${type} == '3') {
				var url = "${ctx}/app/personnel/property/json/refuseAccessApply.act";
				var sucFun = function (json){
					easyAlert("提示信息",json.msg,"info", function() {
						window.top.$('#queryForm').submit();
						window.top.$("#accessInput").window("close");
						window.top.$("#refuseInput").window("close");
					});
				};
				var errFun = function (){
					alert("退回申请失败!");
				};
				var data = {id:'${id}',lockVer:'${lockVer}',refuseReason:refuseReason};
				$.ajax({
					url : url,
					data: data,
					dataType : 'json',//返回值类型 一般设置为json
					success : sucFun,
					error : errFun,
					async : false
				});
			}
		}
	 	
	 	function closeWin() {			
			window.top.$("#refuseInput").window("close");
		}

	</script>
  </head>
  
  <body style="background:#fff;">
     <div class="infrom_ryjbxx">
        <s:hidden name="nuacId"/>
        <s:hidden name="userid" id="userid"/>
        <s:hidden name="lockVer" id="lockVer"/>
        <s:hidden name="accessIds" id="accessIds"/>
        <s:hidden name="guardIds" id="guardIds"/>
        <s:hidden name="opt" id="opt"/>
				<table width="100%">
					<tr>
						<td>
							<div class="infrom_td">退回理由：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="refuseReason" id="refuseReason" style="width:246px;resize:none;color: #818284;"></textarea>
						</td>
					</tr>
				</table>
     </div>
     <div class="botton_from" style="margin-top:20px;">
     	<a href="javascript:void(0)" onclick="refuseConfirm();">确&nbsp;&nbsp;定</a>
     </div>
  </body>
</html>