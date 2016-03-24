<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<title>预约申请</title>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#inputForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
						cancelInfo:{
							maxlength:200
						}
					},
					messages:{
		               	cancelInfo:{
							maxlength: "取消原由长度不能大于200个字符"
						}
					}
				});
			});
		
			function saveData(){
				var options = {
				    dataType:'text',
				    success: function(responseText) { 
						if(responseText=='success') { 
							easyAlert("提示信息", "处理成功！","info", function(){
								closeEasyWin('wincancel');
							});
					 	} else if(responseText =='statusError') {
					 		easyAlert("提示信息", "此访问申请状态不是“已确定”，不能继续操作！","info", function(){
								closeEasyWin('wincancel');
							});
						} else if(responseText =='error') {
					 		easyAlert("提示信息", "加载访客信息出错！","info");
						}
					}
				};
				$('#inputForm').ajaxSubmit(options);
			}
			
			function closeWin() {
				closeEasyWin('wincancel');
			}
		</script>
	</head>

	<body>
		<form id="inputForm" action="cancel.act" method="post" autocomplete="off">
			<table align="center" border="0" width="100%" class="form_table">
				<input name="vsId" id="vsId" value="${vmVisitInfo.vsId}" type="hidden"/>
				<tr>
					<td class="form_label"> <font style="color: red"></font>来访记录编号： </td>
					<td>${vmVisitInfo.vsId}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>访客名称： </td>
					<td>${vmVisitInfo.viName}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>访问事由： </td>
					<td>${vmVisitInfo.vsInfo}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>访问人数： </td>
					<td>${vmVisitInfo.vsNum}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>预约类型： </td>
					<td>${vmVisitInfo.vsType}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>卡类型： </td>
					<td>${vmVisitInfo.cardType}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>访客预约时段： </td>
					<td>${vsSt} ~ ${vsEt}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>备注： </td>
					<td>${vmVisitInfo.vsCommets}</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>取消原由： </td>
					<td><s:textarea name="cancelInfo" id="cancelInfo" cols="50" rows="4"></s:textarea></td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="6">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" id="affirmbut">取消访问</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeWin()">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>