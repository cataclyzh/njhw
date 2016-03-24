<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>报修单-查看</title>
	</head>

	<body>
		<table align="center" border="0" width="100%" class="form_table">
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
					<c:if test="${csRepairFault.crfFlag == '02'}">分派</c:if>
					<c:if test="${csRepairFault.crfFlag == '03'}">完成</c:if>
					<c:if test="${csRepairFault.crfFlag == '04'}">评价</c:if>
				</td>
			</tr>
			<tr>
				<td class="form_label"> <font style="color: red"></font>报修说明： </td>
				<td>${csRepairFault.crfDesc }</td>
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
				<td>${csRepairFault.crfRdesc }</td>
			</tr>
			<tr>
				<td class="form_label"> <font style="color: red"></font>满意度: </td>
				<td >
					<c:if test="${csRepairFault.crfSatis == '01'}">非常满意</c:if>
					<c:if test="${csRepairFault.crfSatis == '02'}">满意</c:if>
					<c:if test="${csRepairFault.crfSatis == '03'}">一般</c:if>
					<c:if test="${csRepairFault.crfSatis == '04'}">不满意</c:if>
				</td>
			</tr>
			<tr>
				<td class="form_label"> <font style="color: red"></font>评价内容： </td>
				<td>${csRepairFault.crfComment }</td>
			</tr>
			<tr>
				<td class="form_label"> <font style="color: red"></font>评价时间： </td>
				<td>${csRepairFault.crfCt }</td>
			</tr>
		</table>
	</body>
</html>
