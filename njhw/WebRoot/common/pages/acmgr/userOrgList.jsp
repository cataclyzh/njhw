<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-27 15:37:15
- Description: 机构维护页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>机构列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
			<script type="text/javascript">
		$(document).ready(function(){
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #f1f8fd;">
<div id="list"></div>
	<form id="queryForm" action="orgList.act" method="post"  autocomplete="off">
	<s:hidden name="entityOrg.parentid"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	机构名称：
          </td>
          <td width="30%">
           <s:textfield name="entityOrg.orgname" theme="simple" size=""/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	机构编码：       	  	
          </td>
          <td width="30%">
            <s:textfield name="entityOrg.orgcode" theme="simple" size="18"/>  
          </td>
        </tr> 
        <tr>
          <td class="form_label" width="20%" align="left">
           	纳税人名称：
          </td>
          <td width="30%">
           <s:textfield name="entityOrg.taxname" theme="simple" size="18" />  
          </td>
          <td class="form_label" width="20%" align="left">
           	纳税人识别号：
          </td>
          <td width="30%" >
           <s:textfield name="entityOrg.taxno" theme="simple" size="18" />  
          </td>
        </tr>  
        <tr>
          <td colspan="4" class="form_bottom">
          	<s:textfield name="pageOrg.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<ahref="javascript:void(0);"" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>&nbsp;&nbsp;
          	 <ahref="javascript:void(0);"" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a> &nbsp;&nbsp;
          </td>
        </tr>
      </table>      
   </h:panel>

	<h:page pageName="pageOrg" id="list_panel" width="100%"  title="结果列表">
		<d:table name="pageOrg.result" id="row"  export="false" class="table">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
		    <d:col property="orgcode"   class="display_centeralign"   title="机构编码"/>
		    <d:col property="orgname" class="display_leftalign" title="机构名称"/>
		    <d:col property="taxno" class="display_centeralign" title="纳税人识别号"/>
			<d:col class="display_centeralign"  media="html" title="操作">
				<ahref="javascript:void(0);"" onclick="userList('${row.orgid}')">[用户]</a>&nbsp;
			</d:col>	
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function userList(id){
		var url = "${ctx}/common/userMgr/list.act?nodeId="+id;
		parent.frames.ifrmUserList.location.href = url;
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
