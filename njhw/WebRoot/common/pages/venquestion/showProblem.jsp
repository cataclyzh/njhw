<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: XieRX
- Date: 2012-7-13
- Description: 查看问题帖内容页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>创建问题贴</title>
<%@ include file="/common/include/meta.jsp"%>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0">
	<table border="0" id="receive_panel" width="100%"  cellspacing="0" cellpadding="0" class="panel-table">
		<tr>
			<td height="20">
				<table  border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="4px"><div class="panel-title-left"></div></td><td width="20px"class="panel-title"></td>
						<td class="panel-title">问题标题：${entity.questTitle}&nbsp; </td>
					</tr>
					<s:if test="entityFile!=null">
					<tr>
						<td width="4px"><div class="panel-title-right"></div></td><td width="20px"class="panel-title"></td>
						<td class="panel-title">附件下载：<a href="${ctx}/common/ques/download.act?fileId=${entityFile.fileId}">${entityFile.fileName}</a>&nbsp; </td>
					</tr>
					</s:if>
				</table>
			</td>
		</tr>
		<tr>
			<td  class="panel-body"  valign="top" >
				<table align="center" border="0" width="100%" class="form_table">
					<tr>
						<td class="form_label" width="79" >&nbsp;&nbsp;提出人：</td>
						<td  width="454">${entity.questUser}</td>
						<td width="138" nowrap="nowrap" class="form_label">&nbsp;&nbsp;提出时间：</td>
						<td width="558"><f:formatDate value="${entity.questTime}" pattern="yyyy年MM月dd日 HH时mm分ss秒"/></td>
						<td width="138" nowrap="nowrap" class="form_label">&nbsp;公司：</td>
						<td width="558">${entity.questCompany}</td>
					</tr>
					<tr>
						<td class="form_label" width="79" nowrap>&nbsp;问题内容：</td>
						<td colspan="5">${entityContent.ansContent}</td>
				   </tr>
				</table>
				<table border="0" id="receive_panel" width="100%"  cellspacing="0" cellpadding="0" class="panel-table">
					<c:forEach items="${entityList}" var="el" varStatus="rowid" >
						<tr><td height="20">
							<table  border="0" width="100%" cellspacing="0" cellpadding="0"><tr><td width="4px"><div class="panel-title-left"></div></td><td width="20px"class="panel-title"></td>
								<td class="panel-title">回复${rowid.count}</td>
							</tr></table>
						</td></tr>
						<tr><td  class="panel-body"  valign="top" >
							<table align="center" border="0" width="100%" class="form_table">
								 	<tr>
										<td class="form_label" width="79" >回复人：</td>
										<td  width="454">${el.ansUser}</td>
										<td width="138" nowrap="nowrap" class="form_label">&nbsp;&nbsp;回复时间：</td>
										<td width="558"><f:formatDate value="${el.ansTime}" pattern="yyyy年MM月dd日 HH时mm分ss秒"/></td>
										<td width="138" nowrap="nowrap" class="form_label">&nbsp;公司：</td>
										<td width="558">${el.ansCompany}</td>
									</tr>
									<tr>
										<td class="form_label" width="79" nowrap>回复内容：</td>
										<td colspan="5">${el.ansContent}</td>
								   </tr>
							</table>
					 	</td></tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
 </body>
</html>
