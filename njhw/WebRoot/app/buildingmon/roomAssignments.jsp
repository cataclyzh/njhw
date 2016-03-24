<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-17 15:37:15
- Description: 页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>楼宇监控</title>	
	<%@ include file="/common/include/meta.jsp" %>
		<script type="text/javascript">
		$(document).ready(function(){
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="roomAssignments.act" method="post"  autocomplete="off">
	<s:hidden name="nodeId"/>
    <h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label">楼座：</td>
	       <td><input name="name" id="name" value="${map.name}" type="text" theme="simple"/></td> 
	        <td class="form_label">楼层：</td>
	        <td><input name="title" id="title" value="${map.title}" type="text" theme="simple"/></td> 
	        <td class="form_label">房间号：</td>
	        <td><input name="keyword" id="keyword" value="${map.keyword}" type="text" theme="simple"/></td>
        </tr>     
        <tr>
          <td colspan="6" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:100px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<td class="form_label"><font style="color:red">*</font>：</td>
	        <td><s:textfield name="userName" theme="simple" size="18" maxlength="20"/></td>  
	        
		    <d:col property="name" class="display_centeralign"   title="名称"/>
		    <d:col property="title" class="display_centeralign" title="标题"/>
		    <d:col property="createDate" class="display_centeralign"  format="{0,date,yyyy年MM月dd日 }"  title="创建时间"/>
		</d:table>
   </h:page>
	</form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>