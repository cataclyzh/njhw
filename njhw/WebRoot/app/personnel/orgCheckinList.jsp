<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-27 15:37:15
- Description: 组织机构维护页面
--%>
	<%--当前用户组织机构类型为根节点时可添加删除操作 并且不是   --%>

		<c:set var="buttons" value="add,del"/>
	
	<%--当前用户组织机构类型为总部时只能添加
	<c:if test="${_orgtype!='0' and treelevel!=2}">
		<c:set var="buttons" value="add"/>
	</c:if>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<title>组织机构列表</title>	
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
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #f1f8fd;">
<div id="list"></div>
	<form id="queryForm" action="orgList.act" method="post"  autocomplete="off">
	<s:hidden name="PId"/>
	<s:hidden name="orgId"/>
	<s:hidden name="levelNum"/>
	
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	名称：
          </td>
          <td width="30%">
           <s:textfield name="name" theme="simple" size="30"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	简称：       	  	
          </td>
          <td width="30%">
            <s:textfield name="shortName" theme="simple" size="30"/>  
          </td>
        </tr>     
        
        <tr>
          <td colspan="4" class="form_bottom">
          	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search"  onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
			
          </td>
        </tr>
      </table>      
   </h:panel>
	<div id="pp" style="background:#efefef;border:1px solid #ccc;"></div>
	<h:page id="list_panel" width="100%" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="orgList.act?exFile=1">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>			
		    <d:col property="name"   class="display_leftalign"   title="名称"/>
		    <d:col property="shortName" class="display_leftalign" title="简称"/>
		    	<d:col property="orgCode" class="display_leftalign" title="代码"/>
					<d:col property="memo" class="display_centeralign" title="备注"/>
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.orgId}')">[修改]</a>&nbsp;
			</d:col>	
	
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/app/per/orgInput.act?PId=" +$('#orgId').val();
		openEasyWin("orgInput","录入组织机构信息",url,"800","450",true);
	}
	function updateRecord(id){
		var url = "${ctx}/app/per/orgInput.act?orgId="+id;
		openEasyWin("orgInput","修改组织机构信息",url,"800","450",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","orgDelete.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
		
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
