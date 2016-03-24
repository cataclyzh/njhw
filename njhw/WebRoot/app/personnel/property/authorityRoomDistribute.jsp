<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: cjw
- Date: 2013-05-25 22:37:15
- Description: 单位房间分配情况
--%>
		<c:set var="buttons" value="add,del"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>单位房间分配情况</title>	
	<%@ include file="/common/include/meta.jsp" %>
<%--	<script>--%>
<%--		function resetForm(){--%>
<%--			$("#displayName").val('');--%>
<%--		}--%>
<%--	</script>--%>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #f1f8fd;">
<form id="queryForm" action="authorityRoomDistribute.act" method="post"  autocomplete="off">
<%--	<s:hidden name="displayName"/>--%>
<%--	--%>
<%-- 	<h:panel id="query_panel" width="100%" title="查询条件">	--%>
<%--      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">--%>
<%--        <tr>--%>
<%--          <td class="form_label" width="50%" align="right">--%>
<%--          	用户名称：--%>
<%--          </td>--%>
<%--          <td width="50%" align="left">--%>
<%--           <s:textfield name="displayName" id="displayName" theme="simple" size="30"/>  --%>
<%--          </td>--%>
<%--        </tr>     --%>
<%--        --%>
<%--        <tr>--%>
<%--          <td colspan="2" class="form_bottom">--%>
<%--          	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;--%>
<%--          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search"  onclick="querySubmit();">查询</a>--%>
<%--          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetForm();">重置</a>--%>
<%--			--%>
<%--          </td>--%>
<%--        </tr>--%>
<%--      </table>      --%>
<%--   </h:panel>--%>
<%--	buttons="${buttons}"--%>
	<h:page id="list_panel" width="100%" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="usersLimitList.act?exFile=1">
			<d:col title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
		    <d:col property="NAME"   class="display_leftalign" title="单位名称"/>
		    <d:col property="RCOUNT"   class="display_leftalign" title="房间数目"/>
		    <d:col property="ROOMS" class="display_centeralign"  title="房间号码" style="text-align: left;"/>
		</d:table>
   </h:page>
	</form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
