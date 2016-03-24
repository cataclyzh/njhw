<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-03-27 15:37:15
- Description: 门禁监控
--%>
	<%--当前用户资源类型为根节点时可添加删除操作 并且不是   --%>

		<c:set var="buttons" value="add,del"/>
	
	<%--当前用户资源类型为总部时只能添加
	<c:if test="${_orgtype!='0' and treelevel!=2}">
		<c:set var="buttons" value="add"/>
	</c:if>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>门禁监控列表</title>	
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
			
			refreshMJ();
		});
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #f1f8fd;" onload="setInterval(function(){ refreshMJ() }, 30000);">
<div id="list"></div>
	<form id="queryForm" action="mjList.act" method="post"  autocomplete="off">
	<s:hidden name="nodeId"/>

 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
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
	<h:page id="list_panel" width="100%"  title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="list.act?exFile=1">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
		    <d:col property="NAME"   class="display_centeralign"   title="门禁编码"/>
		    <d:col property="TITLE" class="display_leftalign" title="门禁名称"/>
		    <d:col property="KEYWORD" class="display_centeralign" title="门禁KEY"/>
		    <d:col class="display_centeralign" title="状态"/>
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.NODE_ID}')">[调用接口]</a>&nbsp;
			</d:col>	
	
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function updateRecord(id){
		alert("调用接口 未做..");
<%--		var url = "${ctx}/app/per/input.act?nodeId="+id;--%>
<%--		openEasyWin("orgInput","修改资源信息",url,"800","450",true);--%>
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
    
    function refreshMJ() {
		$.ajax({
			type: "POST",
			url: "${ctx}/app/buildingmon/refreshMJStatus.act",
			data: "mjids=${mjids}",
			dataType: 'json',
			async : false,
			success: function(json){
				  if(json.status == 0){
						$.each(json.statusList,function(i){
							$("#row tr:eq("+(i+1)+") td:eq(4)").text(json.statusList[i]);
						})
				  }
			 }
		 });
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
