<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: fengfj
- Date: 2011-11-24 
- Description: CA详细信息
--%>
<html>
	<head>
		<title>CA详细信息</title>
		<%@ include file="/common/include/meta.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
		$("#descRow").hide();
		
		$("#auditdesc").bind('keyup blur', function(){
            var filledDesc = $("#auditdesc").val();
            var filledLength = filledDesc.length;
            var maxLength = 60;
            if(filledLength >= maxLength){
                $("#remainCount").text(0);
                var desc = filledDesc.substr(0, maxLength);  
                $("#auditdesc").val(desc);
            }
            else{
                $("#remainCount").text(maxLength - filledLength);
            }
        });
	});
	
function changeDesc(obj){
     var val = $(obj).val();
     if(val == '2'){
         $("#descRow").show();
     }else if(val == '1'){
         $("#descRow").hide();
     }
}

function doSubmit(){
	
     var auditStatus = $("input:checked[name='caapplyQueryModel.auditstatus']").val();
     if(auditStatus == '2'){
         var auditDesc = $("#auditdesc").val();
         if(auditDesc.length == 0){
             easyAlert("提示信息", "请输入审核意见！","info");
             return ;
         }
         else if(auditDesc.length > 100){
             easyAlert("提示信息", "审核意见最多允许输入60个字符！","info");
             return ;
         }
     }
   
     var confirmMsg = "确认提交审核？";
     easyConfirm('提示', confirmMsg, function(r){
         if(r){
            $("#applyInputForm").submit();
         }
     }); 
}
	
</script>
    </head>
    <body>
		<form id="applyInputForm" action="update.act" method="post">
			<s:hidden name="caid"/>
			<table width="100%" align="center" class="form_table" border="0">
				<tr>
					<td height="50" colspan="7" align="center" >
						<span style="font-size: 12px;font-weight: bold;">证书申请信息</span>
					</td>
				</tr>
				<tr>
					<td width="5%" class="form_label" rowspan="3" align="center">
					   <span style="font-size: 12px;font-weight: bold;">证<br/>书<br/>申<br/>请<br/>信<br/>息</span>
					</td>
					<td class="form_label" nowrap>
					         申请日期：
					</td>
					<td align="left">
						<s:date name="applydate" format="yyyy年MM月dd日" />
					</td>
					<td class="form_label" nowrap>证书数量：</td>				
					<td align="left">
					    <s:property value="applynum" />
					</td>
					<td class="form_label" nowrap>证书期限：</td>
					<td align="left">
					    <s:iterator value="#application.DICT_CA_CATERM" id="type">
        	  				  <s:if test="#type.dictcode == caterm"><s:property value="#type.dictname" /></s:if>
        				</s:iterator>
					</td>
				</tr>
				<tr>
					<td class="form_label" width="15%" nowrap align="left">证书种类：</td>
					<td colspan="5" align="left">
						<s:iterator value="#application.DICT_CA_CATYPE" id="type">
        	  				  <s:if test="#type.dictcode == catype"><s:property value="#type.dictname" /></s:if>
        				</s:iterator>
					</td>
				</tr>
				<tr>	
					<td class="form_label" id="sellnotext">
						业务类型：
					</td>
					<td colspan="2" align="left" nowrap>
						<s:iterator value="#application.DICT_CA_APPLYTYPE" id="type">
        	  				  <s:if test="#type.dictcode == applytype"><s:property value="#type.dictname" /></s:if>
        				</s:iterator>
					</td>	
					<td class="form_label" id="sellnotext">
						证书DN：
					</td>
					<td colspan="2" align="left" nowrap>
						<s:property value="cadn" />
					</td>
				</tr>
				<tr>
					<td class="form_label" rowspan="3" align="center">
						<span style="font-size: 12px;font-weight: bold;">申<br/>请<br/>机<br/>构<br/>信<br/>息</span>
					</td>
					<td class="form_label">
						机构名称：
					</td>
					<td colspan="3" align="left">
						<s:property value="orgname" />
					</td>
					<td class="form_label">
						英文/拼音简称：
					</td>
					<td align="left">
						<s:property value="orgnameen" />
					</td>
				</tr>
				<tr>
					<td class="form_label">
						机构证件类型：
					</td>
					<td colspan="5" align="left">
						<s:iterator value="#application.DICT_CA_ORGIDTYPE" id="type">
        	  				  <s:if test="#type.dictcode == orgidtype">
									<s:if test="orgidtype != 3"><s:property value="#type.dictname" /></s:if>
        	  				  		<s:if test="orgidtype == 3"><s:property value="orgidname" /></s:if>
        	  				  </s:if>
        				</s:iterator>
					</td>
				</tr>
				<tr>	
					<td class="form_label">
						机构证件号码：
					</td>
					<td colspan="5" align="left">
						<s:property value="orgidnum" />
					</td>
				</tr>
				<tr>
                    <td class="form_label" rowspan="6" align="center">
                        <span style="font-size: 12px;font-weight: bold;">机<br/>构<br/>经<br/>办<br/>人<br/>信<br/>息</span>
                    </td>
					<td class="form_label">
						经办人姓名：
					</td>
					<td colspan="5" align="left">
						<s:property value="handler" />
					</td>
				</tr>
				<tr>	
					<td class="form_label">
						经办人证件类型：
					</td>
					<td colspan="5" align="left">
						<s:iterator value="#application.DICT_CA_HANDLERIDTYPE" id="type">
        	  				  <s:if test="#type.dictcode == handleridtype"><s:property value="#type.dictname" /></s:if>
        				</s:iterator>
					</td>
				</tr>
				<tr>	
					<td class="form_label">
						经办人证件号码：
					</td>
					<td colspan="5" align="left">
						<s:property value="handleridnum" />
					</td>
				</tr>
				<tr>
					<td class="form_label">
						电话：
					</td>
					<td colspan="2" align="left">
						<s:property value="handlertel" />
					</td>
					<td class="form_label">
						传真：
					</td>
					<td colspan="2" align="left">
						<s:property value="handlerfax" />
					</td>
				</tr>
				<tr>
					<td class="form_label">
						电子邮件：
					</td>
					<td colspan="5" align="left">
						<s:property value="handlermail" />
					</td>
				</tr>
				<tr>
					<td class="form_label">
						USB Key寄送地址：
					</td>
					<td colspan="2" align="left">
						<s:property value="handleraddr" />
					</td>
					<td class="form_label">
						邮政编码：
					</td>
					<td colspan="2" align="left">
						<s:property value="handlerpostcode" />
					</td>
				</tr>
				<tr>
                    <td class="form_label" rowspan="2" align="center">
                        <span style="font-size: 12px;font-weight: bold;">申<br/>请<br/>机<br/>构<br/>声<br/>明</span>
                    </td>
					<td class="form_label" colspan="6">
						本机构承诺以上所填写的申请资料真实、有效。本机构已认真阅读并同意遵守中金金融认证中心有限公司（CFCA）网站（<a href="http://www.cfca.com.cn" target="top">http://www.cfca.com.cn</a>)发布的《数字证书服务协议》、《电子认证业务规则（CPS））》中规定的相关义务。
					</td>
				</tr>
				<tr>
					<td class="form_label">
						申请机构盖章：
					</td>
					<td colspan="2" align="left"><img src="${ctx}/common/caapplyAudit/getImgById.act?caid=${caid}" /></td>
					<td class="form_label">
						日期：
					</td>
					<td colspan="2" align="left">
						<s:date name="stampdate" format="yyyy年MM月dd日" />
					</td>
				</tr>
				<tr>
                    <td colspan="7"><span style="font-size: 12px;font-weight: bold;"><f:message key="common.ca.contact_us"/></span></td>
                </tr>
				<tr>
					<td class="form_label" rowspan="2" align="center">
						<span style="font-size: 12px;font-weight: bold;">审<br/>核</span>
					</td>
					<td class="form_label">审核状态：</td>
					<td colspan="5">
						<s:radio list="#{'1':'通过','2':'不通过'}" value="1" onclick="changeDesc(this)" name="caapplyQueryModel.auditstatus"></s:radio>
					</td>
				</tr>
				<tr id="descRow" style="display:none;">
					<td class="form_label">审核意见：</td>
					<td colspan="5">
						<s:textarea id="auditdesc" name="auditdesc" theme="simple" cols="80" rows="5"></s:textarea>
						<span style="width: 50px">还可以输入</span>
                    	<span id="remainCount" style="color: red">60</span>
                    	<span>个字符</span>
					</td>
				</tr>
			</table>
			<table class="form_table" border="0" style="width:100%">
				<tr>
					<td colspan="4" style="text-align: center; height: 35px">
						<a href="javascript:void(0);" class="easyui-linkbutton" id="submit" onclick="doSubmit();">提交</a>
					</td>
				</tr>
			</table>
		</form>
    </body>
</html>
<script type="text/javascript">
	<c:if test="${isSuc=='success'}">
		easyAlert('提示信息','审核成功!','info',
		   function(){closeEasyWin('winId');}
		);
	</c:if>
	
	<c:if test="${isSuc=='edit'}">
		easyAlert('提示信息','审核的CA资料已被审核，请重新选择!','info',
		   function(){closeEasyWin('winId');}
		);
	</c:if>
	
	<c:if test="${isSuc=='del'}">
		easyAlert('提示信息','审核的CA资料已被删除，请重新选择!','info',
		   function(){closeEasyWin('winId');}
		);
	</c:if>

</script>