<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: hp
- Date: 2013-05-24
- Description: 发改委代办事项
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>发改委代办事项</title>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body style="height:100%;width:100%;" class="easyui-layout">
	<form id="inputForm" action="upload.act" method="post" autocomplete="off"  enctype="multipart/form-data">
				<table align="center" border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label">
						上传文件：
					</td>
					<td>
						<input name="file" type="file"  id="file" theme="simple" size="40" maxlength="50" />
					</td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">上传</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="loadingdiv" class="icon-loding" style="display: none">正在上传中,请等待...............</span>
					</td>
				</tr>
			</table>
	</form>
</body>
<script type="text/javascript">

	function saveData(){
		var file = $("#file").val();
		file = file.substring(file.length-3,file.length);
		if(file == "xml"){
			if(file != ""){
				$("#loadingdiv").show();
				$('a.easyui-linkbutton').linkbutton('disable');
				$("#inputForm").submit();
			}else{
				easyAlert('提示信息','上传文件不能为空！','info');
			}
		}else{
			easyAlert('提示信息','请上传xml文档！','info');
		}
	}

	<c:if test="${isSuc=='true'}">
		easyAlert('提示信息','上传成功！','info');
	</c:if>
	<c:if test="${isSuc=='false'}">
		easyAlert('提示信息','上传失败！','error');
	</c:if>
</script>
</html>
