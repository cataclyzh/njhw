<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资源信息录入</title>
		<%@ include file="/common/include/meta.jsp"%>
		<style type="text/css">
		    body{background:#f6f5f1;}
		</style>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#title").focus();
			
			$("#inputForm").validate({
				errorElement :"div",
				rules: {
					title: {
						required: true,
						lengthValidate:true
					}
				},
				messages: {
					title: {
						required: "房间名称不能为空!",
						lengthValidate:"房间名称过长!"
					}
				}
			});	
									
		});
        //长度
	    jQuery.validator.methods.lengthValidate = function(value, element) {
	      	if(value.length > 216){
	            return false;
	        }else{
	            return true;
	        }
		};
		
		function saveData(){
			openAdminConfirmWin(saveDataFn);
		}

		function saveDataFn(){
			$('#inputForm').submit();
		}
	</script>
	</head>
	<body topmargin="0" leftmargin="0" background="#f6f5f1">
		<form id="inputForm" action="${ctx}/app/personnel/unit/roomEditSave.act" method="post"
			autocomplete="off">
			<input type="hidden" id="nodeId" name="nodeId" value="${tank.nodeId}" />
			<input type="hidden" name="pageNo" value="${pageNo}" />
			<table align="center" border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label">
						&nbsp;房间编号：
					</td>
					<td>
						${tank.name}
					</td>
				</tr>
				<tr>
					<td class="form_label">
						<font style="color: red">*</font>房间名称：
					</td>
					<td>
						<input id="title" name="title" size="30" value="${tank.title}" maxlength="20" />
					</td>
				</tr>
			</table>

			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="6">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save"
							onclick="saveData();">保存</a>
						<!-- 
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton"
							iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
						 -->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	parent.closeRoomEditWindow($("#nodeId").val(),$("#title").val());
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
</script>

