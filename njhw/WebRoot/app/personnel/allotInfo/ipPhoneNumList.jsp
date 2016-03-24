<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-23
- Description: Ip电话号码分配页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Ip电话号码分配</title>	
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
	<input type="hidden" name="orgId" id="orgId" value="${map.orgId}"/>
	<input type="hidden" name="orgName" id="orgName" value="${map.orgName}"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
		　<td class="form_label" width="20%" align="left">单位名称：</td>
          <td width="30%">${map.orgName }</td>
          <td class="form_label" width="20%" align="left">Ip电话号码：</td>
          <td width="30%"><s:textfield name="resName" theme="simple" size="18"/></td>
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
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">${row_rowNum+((page.pageNo-1)*page.pageSize)}</d:col>
			<d:col style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.EOR_ID}"/>
			</d:col>
		    <d:col property="RES_NAME" class="display_centeralign" title="Ip电话号码"/>
		    <d:col property="INSERT_DATE" class="display_centeralign" format="{0,date,yyyy-MM-dd }" title="登记日期"/>
		    <d:col property="POR_FLAG"	dictTypeId="POR_FLAG" 	class="display_centeralign" title="状态"/>
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.EOR_ID}')">[修改]</a>&nbsp;	
			</d:col>
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/app/allotIPNum/input.act?orgId="+$("#orgId").val();
		openEasyWin("winId","批量分配Ip电话号码",url,"600","350",true);
	}
	function updateRecord(id){
		var url = "${ctx}/app/allotIPNum/input.act?resId="+id+"&orgId="+$("#orgId").val();
		openEasyWin("winId","修改Ip电话号码分配",url,"600","350",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		} else {
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
