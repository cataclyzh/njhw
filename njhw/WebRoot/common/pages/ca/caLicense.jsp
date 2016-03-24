<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Date: 2011-11-24
- Description: CA LICENSE
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA LICENSE</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript" src="${ctx}/hisap/scripts/signTemplet.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
	
		});
		function doSubmit(){
			var flag = false;
			$("input[id^='caUserLicenseModels'][id$='cano']").each(function(i){
				if($(this).val() == "" || $(this).val() == undefined){
					flag = true;
					return false;
				}
			});
			if(flag){
				alert("有未发证的用户，请确认!");
				return;
			}
			//var cano = $("input[id^='caUserLicenseModels'][id$='cano']").get();
			//for(var i = 0; i<cano.length;i++){
			//	alert(cano[i].value);
			//}
			document.forms[0].submit();
		}
		function doClose(){
			var flag = false;
			$("input[id^='caUserLicenseModels'][id$='cano']").each(function(i){
				if($(this).val() != "" && $(this).val() != undefined){
					flag = true;
					return false;
				}
			});
			if(flag){
				if(confirm("有已经发证的用户，确认关闭！如关闭，发证信息将不保存！")){
					closeEasyWin("CALicense");
				}
			} else{
				closeEasyWin("CALicense");
			}
		}
		function setKey(someCanoId){
			if (webstamp.CFCA_SIGNANDDISPLAYSTAMP("", "","", "", 1, "") == false) {
				alert("签章失败");
				return;
			}
			//获得签名者公钥证书 1:证书序列号; 2:证书CN 3:公钥证书base64编码
			var signcert = webstamp.CFCA_GETSIGNCERT(1);
			var flag = false;
			$("input[id^='caUserLicenseModels'][id$='cano']").each(function(i){
				if($(this).val() == signcert){
					flag=true;
					return false;
				}
			});
			if(flag){
				alert(signcert+"此证书已经存在，请重新发证!");
			} else{
				$("#"+someCanoId).val(signcert);
			}
		}
		function delKey(someCanoId){
			if (webstamp.CFCA_GETSTAMPSTATUS() != -1) {
				if (webstamp.CFCA_DELETESTAMP() == false) {
					alert("删除印章失败");
					return;
				}
			}
			$("#"+someCanoId).val("");
		}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<object id="webstamp" style="position:absolute;left:15px;top:100px;display: none;" classid="CLSID:CFE9696A-781F-4502-B8FB-92E4A1A5589F"></object>
	<form id="caLicenseForm" action="update.act" method="post">
		<s:hidden name="caid"></s:hidden>
		<h:panel width="100%" title="申请单信息" id="list_panel">
			<div style="width: 70%;float: left;">
				<table width="100%" align="center" class="form_table" border="0">
					<tr>
						<td class="form_label">
							申请人：
						</td>
						<td>
							<s:property value="applyuser"/>
						</td>
						<td class="form_label">
							申请日期：
						</td>
						<td>
							<s:date name="applydate" format="yyyy-MM-dd" var="applydateFormat"/>
							<s:property value="applydateFormat"/>
						</td>
					</tr>
					
					<tr>
						<td class="form_label">
							审核日期：
						</td>
						<td>
							<s:date name="auditdate" format="yyyy-MM-dd" var="auditdateFormat"/>
							<s:property value="auditdateFormat"/>
						</td>
						<td class="form_label">
							申请数量：
						</td>
						<td>
							<s:property value="applynum"/>
						</td>
					</tr>
					<tr>
						<td class="form_label">
							审核人：
						</td>
						<td>
							<s:property value="audituser"/>
						</td>
						<td class="form_label">
							审核状态：
						</td>
						<td>
							<s:iterator value="#application.DICT_CA_AUDITSTATUS" var="dict">
								<s:if test="#dict.dictcode == auditstatus">
									<s:property value="#dict.dictname" />
								</s:if>
							</s:iterator>
						</td>
					</tr>
					<tr>
						<td class="form_label">
							审核意见：
						</td>
						<td colspan="3">
							<s:property value="auditdesc"/>
						</td>
					</tr>
					<tr>
						<td class="form_label">
							发证人：
						</td>
						<td>
							<s:textfield name="gencauser" theme="simple" cssClass="input180px" readonly="true"></s:textfield>
						</td>
						<td class="form_label">
							发证日期：
						</td>
						<td>
							<s:date name="gencadate" format="yyyy-MM-dd" var="gencadateFormat"/>
							<s:textfield name="gencadate" theme="simple" cssClass="input180px" readonly="true" value="%{gencadateFormat}"></s:textfield>
						</td>
					</tr>
					<tr>
						<td class="form_label">
							发证备注：
						</td>
						<td colspan="3">
							<s:textfield name="gencadesc" theme="simple" cssStyle="width:90%" maxlength="100"></s:textfield>
						</td>
					</tr>
				</table>
			</div>
			<div style="width: 30%;float: right;">
				<img src="${ctx}/common/caLicense/getImg.act?caid=${caid}"/>
			</div>
		</h:panel>
		<h:panel width="100%" title="申请单下需发证用户信息" id="list_panel">
			<table width="100%" align="center" class="form_table" border="0">
				<tr>
					<th>用户组</th>
					<th>key信息</th>
					<th>操作</th>
				</tr>
				<s:iterator value="caUserLicenseModels" var="caUserLicenseModel" status="_status">
					<tr>
						<td style="width: 40%">
							<s:property value="%{#caUserLicenseModel.loginname}"/>
							<s:hidden name="caUserLicenseModels[%{#_status.index}].loginname"></s:hidden>
						</td>
						<td style="text-align: center;width: 40%">
							<s:textfield name="caUserLicenseModels[%{#_status.index}].cano" theme="simple" cssStyle="width:100%" readonly="true"></s:textfield>
						</td>
						<td style="text-align: center;width: 20%">
							<a id="licenseDispatch" class="easyui-linkbutton" href="javascript:setKey('caUserLicenseModels_${_status.index }__cano');">发证</a> &nbsp;&nbsp;
							<a id="licenseCancel" class="easyui-linkbutton" href="javascript:delKey('caUserLicenseModels_${_status.index }__cano');" >取消</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</h:panel>
		<div style="width: 100%;text-align: center;">
			<a id="licenseSubmit" href="javascript:void(0);" class="easyui-linkbutton" onclick="doSubmit();">确认</a> &nbsp;&nbsp;
			<a id="licenseClose" href="javascript:void(0);" class="easyui-linkbutton" onclick="doClose();">关闭</a> &nbsp;&nbsp;
			<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
		</div>
	</form>
</body>
</html>
<script type="text/javascript">
	<c:if test="${isSuc=='true'}">
		easyAlert('提示信息','CA发证成功！','info',function(){closeEasyWin('CALicense');});
	</c:if>
	<c:if test="${isSuc=='false'}">
		easyAlert('提示信息','CA发证保存失败！','error',function(){closeEasyWin('CALicense');});
	</c:if>
</script>
