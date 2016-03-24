<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2011-03-04 19:27:22
- Description:文件下载列表管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>文件下载列表</title>	
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
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="queryDown.act" method="post"  autocomplete="off">
	<s:hidden name="parentid"/>
	<s:hidden name="orgid"/>
	
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="15%" align="left">
           	文件类型：
          </td>
          <td width="30%">
          <s:select list="#application.DICT_SOFTDOWN_FILETYPE" name="filetype" 
          emptyOption="false" headerKey=""  headerValue="全部" 
           listKey="dictcode" listValue="dictname"/>
          </td>
          <td class="form_label" width="15%" align="left">
           	版本号：
          </td>
          <td width="30%">
           <s:textfield name="softver" theme="simple" size="30"/>  
          </td>
        </tr> 
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row"  export="false" class="table" requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
		    <%--<d:col property="softtype"  dictTypeId="SOFTDOWN_TYPE"  class="display_leftalign" title="软件类型"/> --%>
		    <d:col property="softname" class="display_leftalign" title="软件名称"/>
		    <d:col property="releasedate" class="display_centeralign" format="{0,date,yyyy-MM-dd }" title="发布日期"/>
		    <d:col property="softver" class="display_leftalign" title="版本号"/>
		    <d:col property="filetype" dictTypeId="SOFTDOWN_FILETYPE" class="display_leftalign" title="文件类型"/>
		    <d:col property="description" class="display_leftalign" title="软件说明"/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<c:if test="${row.outurl == null}">
					<a href="javascript:void(0);" onclick="downloadFile('${row.downid}')">[下载]</a>&nbsp;
				</c:if>
				<c:if test="${row.outurl != null}">
					<a href="javascript:void(0);" onclick="downloadOutFile('${row.outurl}','${row.downid}')">[下载]</a>&nbsp;
				</c:if>
			</d:col>				
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function downloadFile(id){
		var url = "${ctx}/common/filesMgr/download.act?downid="+id;
		location.href=url;
	}
	function downloadOutFile(outurl,id){
		var url = outurl+"/common/filesMgr/download.act?downid="+id;
		location.href=url;
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
