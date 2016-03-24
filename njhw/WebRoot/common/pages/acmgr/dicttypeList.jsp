<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-06 15:37:15
- Description: 业务字典类型页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>业务字典类型列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
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
		});
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="dicttypeList.act" method="post"  autocomplete="off">
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	类型名称：
          </td>
          <td width="30%">
           <s:textfield name="dicttypename" theme="simple" size="10"/>  
          </td>
          <td class="form_label" width="20%" align="left">
           	类型编码：
          </td>
          <td width="30%">
           <s:textfield name="dicttypecode" theme="simple" size="10"/>  
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
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row"  export="false" class="table"
		requestURI="list.act?exFile=1">
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45"   class="display_centeralign"   title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.dicttypeid}"/>
			</d:col>
			<d:col property="dicttypename" class="display_leftalign" title="字典类型名称"/>	
			<d:col property="dicttypecode" class="display_leftalign" title="字典类型编码"/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<a href="javascript:void(0);" onclick="updateRecord('${row.dicttypeid}')">[修改]</a>&nbsp;
				<a href="javascript:void(0);" onclick="dictDeta('${row.dicttypeid}')">[明细]</a>
			</d:col>				
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/common/dictMgr/dicttypeinput.act";
		openEasyWin("winId","录入字典类型信息",url,"600","400",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/dictMgr/dicttypeinput.act?dicttypeid="+id;
		openEasyWin("winId","修改字典类型信息",url,"600","400",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			
		
		
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteType.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
	function refreshCache(){
		$('#queryForm').attr("action","refreshCache.act");
		$('#queryForm').submit();
    }
	function dictDeta(id){
		var url = "${ctx}/common/dictMgr/dictdetaList.act?dicttype="+id;
		parent.frames.ifrmDictdeta.location.href = url;
		//openEasyWin("winId","修改业务字典类型信息",url,"600","400");
	}

	function checkDetas(){ 
		//发送ajax请求
		$.getJSON("${ctx}/common/dictMgr/getDetas.act",{orgname:"test",orgcode:"20"},
			function(json){ 
				//循环取json中的数据,并呈现在列表中 
				$.each(json,function(i){ 
					$("#list").append("<li>机构名称:"+json[i].orgname+" 机构ID:"+json[i].orgid+"</li>") 
				}) 
			}
		)
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
