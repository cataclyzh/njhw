<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-24
- Description: 房间分配
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>房间分配</title>	
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
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">委办局：</td>
          <td width="30%">
          	<select id="orgId" name="orgId">
          		<option value="0">全部</option>
          		<c:forEach items="${orgList}" var="org">
          			<c:if test="${org.orgId == orgId }"><option value="${org.orgId }" selected="selected">${org.name }</option></c:if>
          			<c:if test="${org.orgId != orgId }"><option value="${org.orgId }">${org.name }</option></c:if>
          		</c:forEach>
          	</select>
          </td>
        </tr>
        <tr>
          <td colspan="2" class="form_bottom">
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
		    <d:col property="ORG_NAME" class="display_leftalign" title="委办局"/>
		    <d:col property="BULID" class="display_centeralign" title="楼座"/>
		    <d:col property="FLOOR" class="display_centeralign" title="楼层"/>
		    <d:col property="ROOM" class="display_centeralign" title="门牌号"/>
		    <d:col property="RES_NAME" class="display_centeralign" title="房间名"/>
		    <d:col property="INSERT_DATE" class="display_centeralign" format="{0,date,yyyy-MM-dd }" title="登记日期"/>
		    <d:col property="POR_FLAG" dictTypeId="POR_FLAG" class="display_centeralign" title="状态"/>
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/app/room/input.act?orgId="+$("#orgId").val();
		openEasyWin("winId","房间分配",url,"800","500",true);
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
