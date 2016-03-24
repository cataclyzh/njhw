<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-18 10:12:11
- Description: 报修统计
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>报修统计</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
	<script >
		$(function() {
			$("#month").val(${params.month});
		});
		
		function querySubmit() {
			$("#queryForm").attr("action","statistics.act");
			$("#queryForm").submit();
		}
		
		function valid() {
			if ($("#orgId").val() == 0 && $("#aetId").val() == 0) {
				easyAlert("提示信息", "请选择单位或设备类型后统计！", "info");
				return false;
			} else {
				return true;
			}
		}
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="queryForm" action="statistics.act"  method="post"  autocomplete="off" >
	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
		<tr>
	        <td class="form_label" width="20%" align="left">委办局：</td>
	        <td >
		        <select id="orgId" name="orgId">
		        	<option value="0">全部</option>
			        <c:forEach items="${orgList}" var="org">
						<c:if test="${org.orgId == orgId }"><option value="${org.orgId }" selected="selected">${org.name }</option></c:if>
						<c:if test="${org.orgId != orgId }"><option value="${org.orgId }">${org.name }</option></c:if>
					</c:forEach>
				</select>
			</td>
			<td class="form_label">设备类型：</td>
			<td>
				<select id="aetId" name="aetId">
					<option value="0">全部</option>
					<c:forEach items="${typeList}" var="type">
						<c:if test="${params.aetId == type.aetId}"><option value="${type.aetId }" selected="selected">${type.aetName }</option></c:if>
						<c:if test="${params.aetId != type.aetId}"><option value="${type.aetId }">${type.aetName }</option></c:if>
					</c:forEach>
				</select>
			</td>
			<td class="form_label">报修年份：</td>
			<td><select name="year" id="year">
				<c:forEach items="${yearList}" var="year">
					<c:if test="${params.year == year.YEAR}"><option value="${year.YEAR }" selected="selected">${year.YEAR }年</option></c:if>
					<c:if test="${params.year != year.YEAR}"><option value="${year.YEAR }">${year.YEAR }年</option></c:if>
				</c:forEach>
			</select></td>
			<td class="form_label">报修月份：</td>
			<td><select name="month" id="month">
				<option value="1">一月</option>
				<option value="2">二月</option>
				<option value="3">三月</option>
				<option value="4">四月</option>
				<option value="5">五月</option>
				<option value="6">六月</option>
				<option value="7">七月</option>
				<option value="8">八月</option>
				<option value="9">九月</option>
				<option value="10">十月</option>
				<option value="11">十一月</option>
				<option value="12">十二月</option>
			</select></td>
		</tr>
		<tr>
          <td colspan="8" class="form_bottom">
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
	  </table>
	</h:panel>

	<h:page id="list_panel" width="100%" title="统计结果">
		<table>
			<tr>
				<td>设备名称</td>
				<td>维修次数</td>
			</tr>
			<c:forEach items="${rstList}" var="rst">
				<tr>
					<td>${rst.EAS_NAME }</td>
					<td>${rst.NUM }</td>
				</tr>
			</c:forEach>
		</table>
	</h:page>
   </form>
</body>
</html>