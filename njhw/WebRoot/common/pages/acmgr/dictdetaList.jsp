<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-06 15:37:15
- Description: 业务字典明细页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>业务字典列表</title>	
	<%@ include file="/common/include/meta.jsp" %>	
			<script type="text/javascript">
	$(document).ready(function() {
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			//聚焦第一个输入框
			$("#dictname").focus();
			changebasiccomponentstyle();			
			changedisplaytagstyle();
			changecheckboxstyle();
		});
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="dictdetaList.act" method="post"  autocomplete="off">
	<s:hidden name="dicttype"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	字典名称：
          </td>
          <td width="30%">
           <s:textfield name="dictname" theme="simple" size="10"/>  
          </td>
          <td class="form_label" width="20%" align="left">
           	字典编码：
          </td>
          <td width="30%">
           <s:textfield name="dictcode" theme="simple" size="10"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
            <ahref="javascript:void(0);"" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
			<ahref="javascript:void(0);"" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>         	
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row"  export="false" class="table" requestURI="list.act?exFile=1">
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45"   class="display_centeralign"   title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.dictid}"/>
			</d:col>
		    <d:col property="dictname" class="display_leftalign" title="业务字典名称"/>
		    <d:col property="dictcode" class="display_leftalign" title="业务字典编码"/>
		    <d:col property="sortno"   class="display_centeralign"   title="排序"/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<ahref="javascript:void(0);"" onclick="updateRecord('${row.dictid}')">[修改]</a>&nbsp;
			</d:col>				
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/common/dictMgr/dictdetainput.act?dicttype="+$('#dicttype').val();
		openEasyWin("winId","录入业务字典信息",url,"600","400",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/dictMgr/dictdetainput.act?dictid="+id;
		openEasyWin("winId","修改业务字典信息",url,"600","400",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteDeta.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
