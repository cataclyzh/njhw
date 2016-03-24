<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp"%>
	<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
	<title>查看联系人</title>
	<script type="text/javascript">
	
	function closeWin() {
		closeEasyWin('winId');
	}
</script>
</head>
	
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:HIDDEN;background:rgb(246, 245, 241)'>

	<table align="center" border="0" width="100%" class="form_table">
		<tr>
			<td class="form_label" width="120px;"> <font style="color: red">*</font>所属分组： </td>
			<td style="back"> 
				${groupName }
			</td>
		</tr>
		<tr>
			<td class="form_label"> <font style="color: red">*</font>姓名： </td>
			<td>${addEntity.nuaName}</td>
		</tr>
		<tr>
			<td class="form_label">昵称： </td>
			<td>${addEntity.nuaName1}</td>
		</tr>
		<tr>
			<td class="form_label">手机： </td>
			<td> 
				${addEntity.nuaPhone}
			</td>
		</tr>
		<tr>
			<td class="form_label">住宅电话： </td>
			<td> 
				${addEntity.nuaTel1}
			</td>
		</tr>
		<tr>
			<td class="form_label">办公电话： </td>
			<td>
				${addEntity.nuaTel2}
			</td>
		</tr>
		<tr>
			<td class="form_label">其他电话： </td>
			<td>
				${addEntity.nuaTel3}
			</td>
		</tr>
		<tr>
			<td class="form_label">邮箱： </td>
			<td>${addEntity.nuaMail}</td>
		</tr>
	</table>
	
	<table align="center" border="0" width="100%" class="form_table">
		<tr class="form_bottom">
			<td colspan="4">
			 <div class="fkdj_botton_ls" align="right;" style="text-align: center;padding-left: 340px;">
				<a style="text-align: center;" class="fkdj_botton_left" href="javascript:void(0);" onclick="parent.closeEasyWin('winId');" id="closeBut">确定</a>
			</div>
			</td>
		</tr>
	</table>
</body>
</html>