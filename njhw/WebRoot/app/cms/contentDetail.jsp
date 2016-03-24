<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>CMS内容详情</title>
		<%@ include file="/common/include/meta.jsp"%>
		<script type="text/javascript" src="${ctx }/ckeditor/ckeditor.js"></script>
		<script type="text/javascript" src="${ctx }/ckfinder/ckfinder.js"></script>
		<script type="text/javascript"></script>
	</head>
	<body>
		<form id="cmsContentDetailaForm"   autocomplete="off">
			<input type="hidden" name="id" id="id" value="${articleDetail.id}" />
			<input type="hidden" name="inputtype" value="${channel_id}" />
			<table align="center" border="0" width="100%" class="form_table">

				<!-- <td class="form_label">
						<font style="color: red"></font>标题：
					</td>
					
					<td>
						<input name="title"   id="title" theme="simple" size="18" maxlength="20" value="${articleDetail.title}"/>
					</td>
					-->
				<tr>
					<td theme="simple" size="18" maxlength="20" align="center">
						${articleDetail.title}
					</td>

				</tr>
				<tr>
					<!--  
					<td class="form_label">
						<font style="color: red"></font>内容：
					</td>-->
					<td theme="simple" size="18" maxlength="20">
						${articleDetail.content}
					</td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
			</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	
</script>