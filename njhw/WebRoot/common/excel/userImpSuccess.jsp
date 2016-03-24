<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户信息导入</title>
	<%@ include file="/common/include/meta.jsp" %>	
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/custom/dateUtil.js" type="text/javascript"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="userImportexl.act" method="post"  autocomplete="off"  enctype="multipart/form-data">
		<h:panel id="query_panel" width="100%" title="查询条件">		
			<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label">
						导入成功<a href="${ctx }/common/excel/excelInit.act">继续导入</a>
					</td>
				</tr>
					
			</table>
		</h:panel>
	</form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>