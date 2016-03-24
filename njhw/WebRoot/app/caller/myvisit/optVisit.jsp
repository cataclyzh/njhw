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
			//日期比较
			 jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime < value;
	        };
			
			$("#vsCommets").focus();
			$("#inputForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					vsSt:{
						required: true
					},
					vsEt:{
						required: true,
						compareDate: "#vsSt"
					},
					vsCommets:{
						maxlength:200
					}
				},
				messages:{
					vsEt:{
	                    required: "开始时间不能为空",
	               	},
	                vsEt:{
	                    required: "结束时间不能为空",
	                    compareDate: "结束日期必须大于开始日期"
	               	},
	               	vsCommets:{
						maxlength: "备注长度不能大于200个字符"
					}
				}
			});
		});
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		function saveData(type){
			if (showRequest()) {
				if (type == "rep") {
					$('#inputForm').attr("action","repulse.act");
				}
						
				var options = {
				    dataType:'text',
				    success: function(responseText) { 
						if(responseText='success') { 
							easyAlert("提示信息", "处理成功！","info", function(){
								closeEasyWin('winId');
							});
					 	} else if(responseText =='statusError') {
					 		easyAlert("提示信息", "此访问申请状态已变更，不能继续操作！","info", function(){
								closeEasyWin('winId');
							});
						} else if(responseText =='error') {
					 		easyAlert("提示信息", "加载访客信息出错！","info");
						}
					}
				};
				$('#inputForm').ajaxSubmit(options);
			}
		}
	</script>
	</head>

	<body>
		<form id="inputForm" action="affirm.act" method="post" autocomplete="off">
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
				<c:if test="${vmVisitInfo.vsFlag == '01'}">
					<tr>
						<td class="form_label" > <font style="color: red"></font>被访者确认预约时段： </td>
						<td>
							<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="vsSt" name="vsSt" cssClass="Wdate" value="${vsSt}"/> - 
							<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="vsEt" name="vsEt" cssClass="Wdate" value="${vsEt}"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="form_label"> <font style="color: red"></font>备注： </td>
					<td> 
						<c:if test="${vmVisitInfo.vsFlag == '01'}">
							<s:textarea name="vsCommets" id="vsCommets" cols="50" rows="4"></s:textarea>
						</c:if>
						<c:if test="${vmVisitInfo.vsFlag != '01'}">
							${vmVisitInfo.vsCommets}
						</c:if>
					</td>
				</tr>
			</table>
			<c:if test="${vmVisitInfo.vsFlag == '01'}">
				<table align="center" border="0" width="100%" class="form_table">
					<tr class="form_bottom">
						<td colspan="6">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData('aff');" id="affirmbut">确认</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData('rep');" id="repulsebut">拒绝</a>
						</td>
					</tr>
				</table>
			</c:if>
		</form>
	</body>
</html>