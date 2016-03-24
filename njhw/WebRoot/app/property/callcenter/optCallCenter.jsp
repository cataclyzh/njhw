<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<c:if test="${opt == 'allot'}"><title>报修单-派单</title></c:if>
		<c:if test="${opt == 'complete'}"><title>报修单-完成</title></c:if>
		<c:if test="${opt == 'assess'}"><title>报修单-评价</title></c:if>
	</head>

	<body>
	<c:if test="${opt == 'allot'}">
		<script type="text/javascript">
			$(document).ready(function() {
				 jQuery.validator.methods.compareValue = function(value, element) {
		           if(value=="0"){
		        	   return false;
		           }
		        	return true;
		        };
				$("#inputForm").validate({
					meta :"validate",
					errorElement :"div",
					rules: {
						crfBak1: {
							required: true,
							compareValue:true
						},
						crfComment: {
							maxlength: 200
						},
						crfRdesc: {
							maxlength: 200
						}
					},
					messages: {
						crfBak1:{
							compareValue:"请选择维修人员"
						},
						crfComment: {
							maxlength: "回执内容长度不能大于200个字符"
						},
						crfRdesc: {
							maxlength: "评价内容长度不能大于200个字符"
						}
					}
				});
			});
			function saveData(){
				$("#crfBak2").val($("#crfBak1 option:selected").text());
				$('#inputForm').submit();
			}
		</script>
		
		<form id="inputForm" action="allot.act" method="post" autocomplete="off">
			<table align="center" border="0" width="100%" class="form_table">
				<input type="hidden" name="crfId" value="${csRepairFault.crfId }"/>
				<input type="hidden" name="crfBak2" id="crfBak2"/>
				<tr>
					<td class="form_label" width="220px;"> <font style="color: red"></font>报修单编号： </td>
					<td>${csRepairFault.crfId }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>申请人名称： </td>
					<td>${csRepairFault.userName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>电话： </td>
					<td>${csRepairFault.crfTel }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>申请人位置信息： </td>
					<td >${csRepairFault.staName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>设备类型： </td>
					<td>${csRepairFault.aetName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>设备名称： </td>
					<td>${csRepairFault.easName }</td>
				</tr>
				<!--<tr>
					<td class="form_label"> <font style="color: red"></font>报修单状态： </td>
					<td >
						<c:if test="${csRepairFault.crfFlag == '01'}">申请</c:if>
						<c:if test="${csRepairFault.crfFlag == '02'}">派单</c:if>
						<c:if test="${csRepairFault.crfFlag == '03'}">完成</c:if>
						<c:if test="${csRepairFault.crfFlag == '04'}">评价</c:if>
					</td>
				</tr>-->
				<tr>
					<td class="form_label"> <font style="color: red"></font>报修说明： </td>
					<td style="word-wrap: break-word;word-break:break-all;">${csRepairFault.crfDesc }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red">*</font>维修人员： </td>
					<td colspan="3">
						<select id="crfBak1" name="crfBak1">
							<option value="0">请选择...</option>
							<c:forEach items="${personList}" var="person">
								<option value="${person.userid }">${person.displayName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" id="savebut">派单</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</c:if>
	
	<c:if test="${opt == 'complete'}">
		<script type="text/javascript">
			function saveData(){
				$('#inputForm').submit();
			}
		</script>
		<form id="inputForm" action="complete.act" method="post" autocomplete="off">
			<table align="center" border="0" width="100%" class="form_table">
				<input type="hidden" name="crfId" value="${csRepairFault.crfId }"/>
				<tr>
					<td class="form_label"> <font style="color: red"></font>报修单编号： </td>
					<td>${csRepairFault.crfId }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>申请人： </td>
					<td>${csRepairFault.userName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>电话： </td>
					<td>${csRepairFault.crfTel }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>申请人位置信息： </td>
					<td >${csRepairFault.staName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>设备类型： </td>
					<td>${csRepairFault.aetName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>设备名称： </td>
					<td>${csRepairFault.easName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>报修单状态： </td>
					<td >
						<c:if test="${csRepairFault.crfFlag == '01'}">申请</c:if>
						<c:if test="${csRepairFault.crfFlag == '02'}">派单</c:if>
						<c:if test="${csRepairFault.crfFlag == '03'}">完成</c:if>
						<c:if test="${csRepairFault.crfFlag == '04'}">评价</c:if>
					</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>报修说明： </td>
					<td style="word-wrap: break-word;word-break:break-all;">${csRepairFault.crfDesc }</td>
				</tr>
				
				<tr>
					<td class="form_label"> <font style="color: red"></font>派单人： </td>
					<td>${csRepairFault.crfUname }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>派单时间： </td>
					<td>${csRepairFault.crfDt }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>维修人： </td>
					<td>${csRepairFault.crfBak2 }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>回执内容： </td>
					<td><s:textarea name="crfRdesc" id="crfRdesc" rows="4" cols="40"></s:textarea></td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" id="savebut">完成</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</c:if>

	<c:if test="${opt == 'assess'}">
		<script type="text/javascript">
			function saveData(){
				var isChecked = 0;
				$(":radio").each(function(){
					if ($(this).attr("checked") == "checked")  isChecked = 1;
				})
				if (isChecked == 0) {
					easyAlert("提示信息","请选择一项满意度，完成本次评价！","info");
					return false;
				} else {
					$('#inputForm').submit();
				}
			}
		</script>
		<form id="inputForm" action="assess.act" method="post" autocomplete="off">
			<table align="center" border="0" width="100%" class="form_table">
				<input type="hidden" name="crfId" value="${csRepairFault.crfId }"/>
				<tr>
					<td class="form_label"> <font style="color: red"></font>报修单编号： </td>
					<td>${csRepairFault.crfId }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>申请人： </td>
					<td>${csRepairFault.userName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>电话： </td>
					<td>${csRepairFault.crfTel }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>申请人位置信息： </td>
					<td >${csRepairFault.staName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>设备类型： </td>
					<td>${csRepairFault.aetName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>设备名称： </td>
					<td>${csRepairFault.easName }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>报修单状态： </td>
					<td >
						<c:if test="${csRepairFault.crfFlag == '01'}">申请</c:if>
						<c:if test="${csRepairFault.crfFlag == '02'}">派单</c:if>
						<c:if test="${csRepairFault.crfFlag == '03'}">完成</c:if>
						<c:if test="${csRepairFault.crfFlag == '04'}">评价</c:if>
					</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>报修说明： </td>
					<td style="word-wrap: break-word;word-break:break-all;">${csRepairFault.crfDesc }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>派单人： </td>
					<td>${csRepairFault.crfUname }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>派单时间： </td>
					<td>${csRepairFault.crfDt }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>维修人： </td>
					<td>${csRepairFault.crfBak2 }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>维修时间： </td>
					<td>${csRepairFault.crfPt }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>回执内容： </td>
					<td style="word-wrap: break-word;word-break:break-all;">${csRepairFault.crfRdesc }</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>满意度：</td>
					<td>
						<input type="radio" name="crfSatis" id="rad_vg" value="01"/>非常满意　　
						<input type="radio" name="crfSatis" id="rad_g" value="02"/>满意　　
						<input type="radio" name="crfSatis" id="rad_ge" value="03"/>一般　　
						<input type="radio" name="crfSatis" id="rad_b" value="04"/>不满意
					</td>
				</tr>
				<tr>
					<td class="form_label"> <font style="color: red"></font>评价内容： </td>
					<td><s:textarea name="crfComment" id="crfComment" rows="4" cols="40"></s:textarea></td>
				</tr>
			</table>
			<table align="center" border="0" width="100%" class="form_table">
				<tr class="form_bottom">
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" id="savebut">评价</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</c:if>	
	</body>
	<script type="text/javascript">
		<c:if test="${isSuc=='true'}">
			easyAlert('提示信息','处理成功！','info', function(){closeEasyWin('winId');});
		</c:if>
		<c:if test="${isSuc=='statusError'}">
			easyAlert('提示信息','此报修单状态已变更，不能继续操作!','info',function(){closeEasyWin('winId');});
		</c:if>
		<c:if test="${isSuc=='false'}">
			easyAlert('提示信息','处理失败！','info',function(){closeEasyWin('winId');});
		</c:if>
	</script>
</html>
