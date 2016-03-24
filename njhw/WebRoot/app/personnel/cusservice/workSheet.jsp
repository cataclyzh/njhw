<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
<table id="table_work_sheet" border="0" cellspacing="0" class="main_main1_table1">
	<c:forEach items="${sheetList}" var ="data">
		<tr>
			<td style="padding: 0 5px">
				<img src="images/point.png" alt="">
			</td>
			<td>
				<span>${data.USER_NAME}</span>
			</td>
			<td>
				<span>${data.AET_NAME}</span>
			</td>
			<td>
				<span>${data.CRF_RT}</span>
			</td>
			<td>
				<span title="${data.CRF_DESC}">${fn:substring(data.CRF_DESC,0,8)}</span>
			</td>
			<td>
				<a href="javascript:void(0)" onclick="showShelter('700','350','${ctx}/app/pro/optShow.act?crfid=${data.CRF_ID}&opt=allot');" hidefocus>详情>></a>
			</td>
		</tr>
	</c:forEach>
</table>