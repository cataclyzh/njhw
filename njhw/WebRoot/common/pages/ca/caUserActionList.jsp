<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: csq
- Date: 2011-12-15
- Description: CA用户操作功能
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA用户操作功能</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
    <script type="text/javascript">
		$(document).ready(function() {
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);

			changebasiccomponentstyle();			
			changedisplaytagstyle();
			changecheckboxstyle();

			initQueryToggle("listCnt", "hide_table", "");
		});
	</script>	
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="query.act" method="post"  autocomplete="off">
		<h:panel id="query_panel" width="100%" title="查询条件">			
			<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label" width="20%" align="left">
					用户登录名：
					</td>
					<td width="30%">
			         <s:textfield name="userActionModel.loginname" theme="simple" maxlength="20" cssClass="input180box"/>
					</td>
					<td class="form_label" width="20%" align="left">
						是否应用CA：
					</td>
					<td width="30%">
						<s:select list="#application.DICT_CA_ISVALIDCA" name="userActionModel.isuseca" 
         						emptyOption="false" headerKey="" headerValue="全部" 
          						listKey="dictcode" listValue="dictname" cssClass="input180box"/>
					</td>	
				</tr>
				<tr>
					<td class="form_label" width="20%" align="left">
					操作编码：
					</td>
					<td width="30%">
			            <s:textfield name="userActionModel.actioncode" theme="simple" maxlength="40" cssClass="input180box"/>
					</td>
					<td class="form_label" width="20%" align="left">
					操作名称：
					</td>
					<td width="30%">
					    <s:textfield name="userActionModel.actionname" theme="simple" maxlength="40" cssClass="input180box"/>
					</td>	
				</tr>
				<tr class="form_bottom">
					<td colspan="4" class="form_bottom">
						<s:textfield name="page.pageSize" theme="simple" id="pageSize" size="1" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" maxlength="3"/>条/页&nbsp;&nbsp;
			          	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:querySubmit()">查询</a>
			          	&nbsp;&nbsp;&nbsp;&nbsp;
			          	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetForm();">重置</a>
			          	&nbsp;&nbsp;&nbsp;&nbsp;
                       <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
					</td>
				</tr>
			</table>
		</h:panel>

		<div id="listCnt" style="OVERFLOW-y:auto;">
		<h:page id="list_panel" width="100%" buttons="add,del" title="CA用户操作列表">		
			<d:table name="page.result" id="row" export="false" class="table">
				<d:col style="width:5%" title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
				<d:col style="width:5%" class="display_centeralign" title="选择<input type='checkbox' id='checkAll'/>" >
				    <input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.CAUSERACTIONID}"/>
			    </d:col>
			    <d:col style="width:10%" property="LOGINNAME" class="display_centeralign" title="用户登录名"/>
			    <d:col style="width:10%" property="ACTIONCODE" class="display_centeralign" title="操作编码"/>
			    <d:col style="width:20%" property="ACTIONNAME" class="display_leftalign" title="操作名称"/>
			    <d:col style="width:30%" property="ACTIONDESC" class="display_leftalign" title="操作说明"/>
			    <d:col style="width:10%" property="ISUSECA" class="display_centeralign" dictTypeId="CA_ISVALIDCA" title="是否应用CA"/>
			    <d:col style="width:10%" class="display_centeralign" media="html" title="操作">				
				    <a href="javascript:void(0);" onclick="updateRecord('${row.CAUSERACTIONID}')">[修改]</a>&nbsp;
			    </d:col>			    
			</d:table>
	    </h:page>
	    </div>
	</form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>

<script type="text/javascript">

function addRecord(){
	var url = "${ctx}/common/caUserActionAction/edit.act";
	openEasyWin("winId","增加CA用户操作信息",url,"750","550",true);
}

function delRecord(){
	if($("input[@type=checkbox]:checked").size()==0){
		easyAlert('提示信息','请勾选要删除的记录！','info');
	}
	else{
		easyConfirm('提示', '确定删除？', function(r){
			if (r){
				$('#queryForm').attr("action","deleteCaUserAction.act");
				$('#queryForm').submit();
			}
		});		
	}
}

function updateRecord(id){
	var url = "${ctx}/common/caUserActionAction/input.act?causeractionid="+id;
	openEasyWin("winId","CA用户操作-修改",url,"600","300",true);
}

function refreshCache(){
	$('#queryForm').attr("action","refreshUserActionListCache.act");
	$('#queryForm').submit();
}
</script>