<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-02 13:24:25
- Description: 角色列表分配权限页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>角色列表分配权限</title>	
	<%@ include file="/common/include/meta.jsp" %>
		<script type="text/javascript">
		$(document).ready(function(){
			changebasiccomponentstyle();
			changedisplaytagstyle();
		});
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="roleList.act" method="post"  autocomplete="off">
	 <s:hidden name="orgid"/>
	  <s:hidden name="company"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	角色名称：
          </td>
          <td width="30%">
           <s:textfield name="rolename" theme="simple" size="10"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	角色编码：       	  	
          </td>
          <td width="30%">
            <s:textfield name="rolecode" theme="simple" size="10"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row"   export="false" class="table"   
		requestURI="list.act?exFile=1">
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
		    <d:col property="rolecode"   class="display_leftalign"   title="角色编码"/>
		    <d:col property="rolename" class="display_leftalign" title="角色名称"/>
<%--
		    <d:col property="roletype" class="display_centeralign" title="角色类型"/>
		    <d:col property="roledesc" class="display_centeralign" title="角色描述"/>--%>
			<d:col class="display_centeralign"  media="html" title="操作">
					
				<a href="javascript:void(0);" onclick="rights('${row.roleid}')">[权限]</a>&nbsp;
			<%--<c:if test="${_orgtype=='0'}">	
			</c:if>		--%>
				<a href="javascript:void(0);" onclick="users('${row.roleid}')">[用户]</a>&nbsp;
			</d:col>				
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function rights(id){
		var url = "${ctx}/common/rightMgr/menuTree.act?roleid="+id+"&orgid="+$('#orgid').val()+"&company="+$('#company').val();
		parent.frames.ifrmMenuTree.location.href = url;
	}
	function users(id){
		var url = "${ctx}/common/rightMgr/userList.act?roleid="+id+"&orgid="+$('#orgid').val()+"&company="+$('#company').val();
		parent.frames.ifrmMenuTree.location.href = url;
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
