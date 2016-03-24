<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>新增通讯分组</title>
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" type="text/css" rel="stylesheet"  />
		<link href="${ctx}/app/integrateservice/css/tongxunlu.css" type="text/css" rel="stylesheet"  />
		
		<script type="text/javascript">
		$(document).ready(function() {
			if ("${groupEntity.nagId}" != "") {
				$("#nagId").val("${groupEntity.nagId}");
				$("#nagName").val("${groupEntity.nagName}");
			} else {
				$("input :text").val("");
				$("input :hidden").val("");
			}
			
			$("#inputForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					nagName: {
						required: true,
						maxlength: 20
					}
				},
				messages: {
					nagName: {
					    required: "请输入分组名称",
					    maxlength: "名称长度不能大于20个字符"
					}
				}
			});
		});
		
		function saveData(){
			$('#inputForm').submit();
		}
	</script>
	</head>
	
	<body style="background: #FFF;">
	
		<form id="inputForm" action="groupSave.act" method="post" autocomplete="off">
			<input type="hidden" name="nagId" id="nagId"/>
			<table align="center" border="0" width="100%" class="tongxunlu_form_table">
				<tr>
					<td class="tongxunlu_form_label"> <font style="color: red">*</font>分组名称： </td>
					<td> <s:textfield name="nagName" cssClass="tongxunlu_input" id="nagName" theme="simple" size="50" maxlength="50" /> </td>
				</tr>
			</table>
<div class="energy_fkdj_botton_ls">
						<a href="#" class="fkdj_botton_left" iconCls="icon-save" onclick="saveData();" id="savebut">保存</a>
						<a href="#" class="fkdj_botton_right" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
</div>
		</form>
	</div>
	</body>
	<script type="text/javascript">
		<c:if test="${isSuc=='true'}">
			window.top.showMessageBox("保存成功！");
			//easyAlert('提示信息','保存成功！','info', function(){parent.closeIframe1('win');});
		</c:if>
		<c:if test="${isSuc=='false'}">
			window.top.showMessageBox("保存失败！");
			//easyAlert('提示信息','保存失败！','info',function(){closeEasyWin('winId');});
		</c:if>
	</script>
</html>