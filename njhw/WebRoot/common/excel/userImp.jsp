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
<script type="text/javascript">
	function dosubmit(){
		var s = $("#file").val();
		var subs = s.substring(s.length-4,s.length);
		if(subs == '.xls'){
			$("#loadingdiv").show();
			$("#queryForm").submit();
		}else{
			easyAlert('提示信息','请您选择excel文件即后缀为.xls的文件');
		}
	}

	               
</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="userImportexl.act" method="post"  autocomplete="off"  enctype="multipart/form-data">
		<h:panel id="query_panel" width="100%" title="查询条件">		
			<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label" width="30%">
						选择上传文件，限2003版Excel文件(文件名中不要包含.)：
					</td>
					<td>
					<s:file id="file" name="file" theme="simple"  maxlength="8" cssClass="input180box"></s:file>
					</td>
					<td colspan="2">上传的文件格式请参照此范例模板，点此下载上传的文件格式请参照此范例模板，<a href="${ctx }/dataconfig/impTemplate.xls">点此下载</a></td>
				</tr>
				<tr class="form_bottom">
					<td colspan="4" class="form_bottom">
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton"
							onclick="dosubmit();">上传</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton"
							onclick="resetCheckboxForm('#queryForm');">取消</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						导入文件的字段顺序为：用户名  登录名  组织机构id  组织机构名称   市民卡号   性别   电话   邮箱
					</td>
				</tr>
			</table>
		</h:panel>
	</form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>