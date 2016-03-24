<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
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
	$(document).ready(function(){
		$("#pagesize").hide();
		
		
	});
	</script>
    </head>
    <body>
		<form id="applyInputForm" action="saveApply.act" method="post">
			
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
					<td colspan="2" align="left">
					<img src="${ctx}/common/caapplyAudit/getImgById.act?caid=${caid}" />
					</td>
					<td class="form_label">
						日期：
					</td>
					<td colspan="2" align="left"><s:date name="stampdate" format="yyyy年MM月dd日" /></td>
				</tr>
				<tr>
                    <td colspan="7"><span style="font-size: 12px;font-weight: bold;"><f:message key="common.ca.contact_us"/></span></td>
                </tr>
				<c:if test="${applytype == 0}">
				<tr>
					<td class="form_label" rowspan="2" align="center">
						<span style="font-size: 12px;font-weight: bold;">审<br/>核</span>
					</td>
					<td class="form_label">审核状态：</td>
					<td colspan="5">
						<s:iterator value="#application.DICT_CA_AUDITSTATUS" id="type">
        	  				  <s:if test="#type.dictcode == auditstatus"><s:property value="#type.dictname" /></s:if>
        				</s:iterator>
					</td>
				</tr>
				<c:if test="${auditstatus == 2}">
				<tr>
					<td class="form_label">审核意见：</td>
					<td colspan="5">
						<pre><s:property value="auditdesc" escape="false"/></pre>
					</td>
				</tr>
				</c:if>
				</c:if>

			</table>
			<div id="pagesize"><s:textfield name="pageCauser.pageSize" id="pageSize" theme="simple" size="1" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;</div>
			<h:page id="list_panel" width="100%" pageName="pageCauser" title="用户信息" btt="">
				<d:table name="pageCauser.result" id="row" class="table" requestURI="export.act" export="false" >
				    <d:col title="序号" class="display_centeralign" >
							${row_rowNum+((pageCauser.pageNo-1)*pageCauser.pageSize)}
					</d:col>	
				    <d:col property="loginname" class="display_centeralign" title="登陆名称" sortable="false" />
				    <d:col property="applyno" class="display_centeralign" title="申请代码" sortable="false" />
				    <d:col property="canum" class="display_centeralign" title="CA数量" sortable="false"/>
				</d:table>
   			</h:page>
			
			
			<table class="form_table" border="0" style="width:100%">
				<tr>
					<td colspan="4" style="text-align: center; height: 35px">
						<a href="javascript:closeEasyWin('winId');" class="easyui-linkbutton" id="resetbutton">关闭</a>
					</td>
				</tr>
			</table>
		</form>
    </body>
</html>