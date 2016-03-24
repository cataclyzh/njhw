<%--
  - Author: ycw
  - Date: 2012-7-10 
  - Description: 审批问题贴详细页面
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>审批问题贴详细页面</title>	
<%@ include file="/common/include/meta.jsp" %>
<style type="text/css">
	.textfield { 
		width:240px;
	}
	.textarea { 
		width:360px;
		height:120px;
	}
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0">
<h:page id="receive_panel" width="100%" title="问题贴信息">
	<table align="center" border="0" width="100%" class="form_table">
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;问题贴标题：</td>
			<td colspan="24">${vendorQuestion.questTitle }</td>
		</tr>
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;发帖人：</td>
			<td>${vendorQuestion.questUser }</td>
			<td class="form_label" width="80px" nowrap>&nbsp;&nbsp;发帖时间：</td>
			<td>&nbsp;<s:date name="vendorQuestion.questTime" format="yyyy年MM月dd日 HH时mm分ss秒"/></td>
				<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;发帖公司：</td>
			<td>${vendorQuestion.questCompany }</td>
		</tr>
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;发帖内容：</td>
			<td colspan="24">${ansContent}</td>
		</tr>
		</table>
	</h:page>
	
	 <form id="downloadForm"  action="" method="post">
		 <div id="listCnt" style="OVERFLOW-y:auto;overflow-x:hidden;"> 
	 		<h:page id="grnList_panel" pageName="filePage" width="100%" title="文件列表">
				<table align="center" border="0" width="100%" class="form_table">
						<tr>
							<td class="form_label" width="80px" nowrap>&nbsp;&nbsp;文件名</td>
							<td class="form_label" width="80px" nowrap>&nbsp;&nbsp;文件大小（KB）</td>
							<td class="form_label" width="80px" nowrap>&nbsp;&nbsp;文件类型</td>
							<td class="form_label" width="80px" nowrap>&nbsp;&nbsp;操作</td>
						</tr>
						<c:forEach items="${fileList }" var="p">
							<tr>
								<td class="form_label" width="120px" nowrap>${fn:substringBefore(p.fileName, '.')}</td>
								<td class="form_label" width="120px" nowrap><f:formatNumber value="${p.fileSize/1024}" pattern=""></f:formatNumber> </td>
								<td class="form_label" width="120px" nowrap> ${p.fileExtension}</td>
								<td class="form_label" width="120px" nowrap><a href="javaScript:downloadFile(${p.fileId })">下载</a> </td>
							</tr>
						</c:forEach>
				</table>
			</h:page>
	    </div>
     </form>
</body>
</html>
<script type="text/javascript">

//下载
 function downloadFile(fileId){
	 window.location.href = "${ctx}/common/question/download.act?fileId="+fileId;
 }
 </script>