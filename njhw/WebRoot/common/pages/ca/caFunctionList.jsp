<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA操作</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(document).ready(function() {
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			//聚焦第一个输入框
			$("#dicttypename").focus();
			changebasiccomponentstyle();			
			changedisplaytagstyle();
			changecheckboxstyle();
			initQueryToggle("listCnt", "hide_table", "");
		});
	
	function querySubmit(){
		$("#queryForm").submit();
	}
	
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="query.act" method="post"  autocomplete="off">
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	操作名称：
          </td>
          <td width="30%">
           <s:textfield name="cafunctionQueryModel.actionname" theme="simple" cssClass="input180box"/>  
          </td>
          <td class="form_label" width="20%" align="left">
           	操作代码：
          </td>
          <td width="30%">
           <s:textfield name="cafunctionQueryModel.actioncode" theme="simple" cssClass="input180box"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
   <div id="listCnt" style="OVERFLOW-y:auto;">
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row"  export="false" class="table"
		requestURI="">
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45" class="display_centeralign" title="选择<input type='checkbox' id='checkAll'/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.ACTIONID}"/>
			</d:col>
			<d:col property="ACTIONNAME" class="display_centeralign" title="操作名称"/>	
			<d:col property="ACTIONCODE" class="display_centeralign" title="操作代码"/>
			
			<d:col class="display_centeralign" title="操作描述">
                <span title="${row.ACTIONDESC}">${fn:substring(row.ACTIONDESC, 0, 20)}<c:if test="${fn:length(row.ACTIONDESC) > 20}">...</c:if></span>
            </d:col>
			
			<d:col class="display_centeralign"  media="html" title="操作">				
				<a href="javascript:void(0);" onclick="updateRecord('${row.ACTIONID}')">[修改]</a>&nbsp;
				<a href="javascript:void(0);" onclick="dictDeta('${row.ACTIONID}')">[明细]</a>
			</d:col>				
		</d:table>
   </h:page>
   </div>
	</form>
<script type="text/javascript">

	function addRecord(){
		var url = "${ctx}/common/cafunction/input.act";
		openEasyWin("winId","CA操作-录入",url,"600","400",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/cafunction/input.act?actionid="+id;
		openEasyWin("winId","CA操作-修改",url,"600","400",true);
	}
	function dictDeta(id){
		var url = "${ctx}/common/cafunction/detail.act?actionid="+id;
		openEasyWin("winId","CA操作明细",url,"600","400",true);
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
