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
	<form id="queryForm" action="list.act" method="post"  autocomplete="off">
	<s:hidden name="parentid"/>
	<s:hidden name="orgid"/>
	
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="15%" align="left">
           	软件类型：
          </td>
          <td width="30%">
          <s:select list="#application.DICT_SOFTDOWN_TYPE" name="softtype" 
          emptyOption="false" headerKey=""  headerValue="全部" 
           listKey="dictcode" listValue="dictname"/>
          </td>
          <td class="form_label" width="15%" align="left">
           	所属公司：
          </td>
          <td width="30%">
          <s:select list="#application.DICT_COMPANY" name="company" 
          emptyOption="false" headerKey=""  headerValue="全部" 
           listKey="dictcode" listValue="dictname"/>
          </td>

        </tr> 
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
           	机构类型：
          </td>
          <td width="30%">
          <s:select list="#{'5':'购方','8':'销方','6':'购销双方'}" name="orgtype" 
          emptyOption="false" headerKey=""  headerValue="全部" />
          </td>
        </tr> 
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row"  export="false" class="table" requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col style="width:45" class="display_centeralign" title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.downid}"/>
			</d:col>
		    <d:col property="softtype"  dictTypeId="SOFTDOWN_TYPE"  class="display_centeralign" title="软件类型"/>
		    <d:col property="softname" class="display_leftalign" title="软件名称"/>
		    <d:col property="releasedate" class="display_centeralign"    format="{0,date,yyyy-MM-dd }"  title="发布日期"/>
		    <d:col property="softver" class="display_centeralign" title="版本号"/>
		    <d:col property="filetype" dictTypeId="SOFTDOWN_FILETYPE" class="display_centeralign" title="文件类型"/>
		    <d:col property="company" dictTypeId="COMPANY" class="display_centeralign" title="所属公司"/>
		    <d:col property="orgtype" dictTypeId="ORG_TYPE" class="display_centeralign" title="机构类型"/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<a href="javascript:void(0);" onclick="updateRecord('${row.downid}')">[修改]</a>&nbsp;
			</d:col>				
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/common/filesMgr/input.act";
		openEasyWin("winId","录入软件下载信息",url,"780","400",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/filesMgr/input.act?downid="+id;
		openEasyWin("winId","修改软件下载信息",url,"780","400",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","delete.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
