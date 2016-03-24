<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-02 9:21:22
- Description: 未分配角色的用户信息页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户列表</title>	
	<%@ include file="/common/include/meta.jsp" %>	
			<script type="text/javascript">
		$(document).ready(function() {
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			//聚焦第一个输入框
			$("#username").focus();
			changebasiccomponentstyle();			
			changedisplaytagstyle();
			changecheckboxstyle();
		});
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="userListNoRight.act" method="post"  autocomplete="off">
 <s:hidden name="roleid"/>
 <s:hidden name="orgid"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" border="0"  id="hide_table"  width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	用户名称：
          </td>
          <td width="30%">
           <s:textfield name="username" theme="simple" size="20"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	用户编码：       	  	
          </td>
          <td width="30%">
            <s:textfield name="usercode" theme="simple" size="20"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="pageUsersNoRight.pageSize" id="pageSize" theme="simple" size="3"/>&nbsp;&nbsp;
          	<ahref="javascript:void(0);"" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
			<ahref="javascript:void(0);"" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add"  pageName="pageUsersNoRight" title="结果列表">		
		<d:table name="pageUsersNoRight.result" id="row"  export="false" class="table">
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((pageUsersNoRight.pageNo-1)*pageUsersNoRight.pageSize)}
			</d:col>
			<d:col  style="width:45"   class="display_centeralign"   title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.USERID}"/>
			</d:col>	
		    <d:col property="USERCODE"   class="display_centeralign"   title="用户编码"/>
		    <d:col property="USERNAME" class="display_leftalign" title="用户名称"/>
		    <d:col property="ORGNAME" class="display_leftalign" title="所属机构"/>
		    <d:col property="LOGINNAME" class="display_centeralign" title="登录名"/>	
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要增加的记录！','info');
		}else{
			easyConfirm('提示', '确定增加？', function(r){
				if (r){
					$('#queryForm').attr("action","saveUsersToRole.act");
					$('#queryForm').submit();
				}
			});
		}
    }
    
</script>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','给角色添加用户成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','给角色添加用户失败！','error');
</c:if>
</script>
