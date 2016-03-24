<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%--
- Author: yc
- Date: 2011-12-22 
- Description: CAUSER用户关联修改
--%>
<html>
	<head>
		<title>CAUSER用户关联修改</title>
		<%@ include file="/common/include/meta.jsp"%>
	<script type="text/javascript" src="${ctx}/hisap/scripts/signTemplet.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#pagesize").hide();
		
	});
	
	function doSubmit(){
		var flag = false;
		$("input[id='cano']").each(function(i){
				if($(this).val() == "" || $(this).val() == undefined){
					flag = true;
					return false;
				}
		});
		if(flag){
				alert("有未发证的用户，请确认!");
				return;
		}
    	$("#applyInputForm").submit();
	}
	
	function setKey(someCanoId){
			if (webstamp.CFCA_SIGNANDDISPLAYSTAMP("", "","", "", 1, "") == false) {
				alert("签章失败");
				return;
			}
			//获得签名者公钥证书 1:证书序列号; 2:证书CN 3:公钥证书base64编码
			var signcert = webstamp.CFCA_GETSIGNCERT(1);
			var flag = false;
			$("input[id='cano']").each(function(i){
				if($(this).val() == signcert){
					flag=true;
					return false;
				}
			});
			if(flag){
				alert(signcert+"此证书已经存在，请重新发证!");
			} else{
				$("#cano").val(signcert);
			}
	}
	
	function delKey(someCanoId){
			if (webstamp.CFCA_GETSTAMPSTATUS() != -1) {
				if (webstamp.CFCA_DELETESTAMP() == false) {
					alert("删除印章失败");
					return;
				}
			}
			$("#cano").val("");
	}
	</script>
    </head>
    <body>
    <object id="webstamp" style="position:absolute;left:15px;top:100px;display: none;" classid="CLSID:CFE9696A-781F-4502-B8FB-92E4A1A5589F"></object>
		<form id="applyInputForm" action="update.act" method="post">
			<s:hidden name="causerid"></s:hidden>
			<table width="100%" align="center" class="form_table" border="0">
				<tr>	
					<td class="form_label" width="20%">
						用户登录名：
					</td>
					<td  align="left" width="30%">
						<s:property value="loginname"/>
					</td>
					<td class="form_label" width="20%">
						CA序列号：
					</td>
					<td  align="left" width="30%">
						<s:textfield name="cano" theme="simple" cssStyle="width:30%" readonly="true"></s:textfield>
						<a id="licenseDispatch" class="easyui-linkbutton" href="javascript:setKey('${cano}');">发证</a> &nbsp;&nbsp;
						<a id="licenseCancel" class="easyui-linkbutton" href="javascript:delKey('${cano}');" >取消</a>
					</td>
				</tr>
				<tr>	
					<td class="form_label" width="20%">
						是否有效CA：
					</td>
					<td  align="left" width="30%">
						<s:select list="#application.DICT_CA_ISVALIDCA" name="isvalidca" 
         						emptyOption="false" 
          						listKey="dictcode" listValue="dictname" cssClass="input180box"/>
					</td>
					<td class="form_label" width="20%">
						申请代码：
					</td>
					<td  align="left" width="30%">
						<s:property value="applyno"/>
					</td>
				</tr>
			</table>
			<table class="form_table" border="0" style="width:100%">
                <tr>
                    <td colspan="4" style="text-align: center; height: 35px">
                        <a id="submit" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="doSubmit();">修改</a> &nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#applyInputForm').resetForm();">重置</a>
                        <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
                    </td>
                </tr>
            </table>
		</form>
    </body>
</html>
<s:actionmessage theme="custom"/>
<script>
	<c:if test="${isSuc=='true'}">
		easyAlert('提示信息','修改成功!','info',
		   function(){closeEasyWin('winId');}
		);
	</c:if>
	<c:if test="${isSuc=='false'}">
		easyAlert('提示信息','修改失败!','error');
	</c:if>
</script>