<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: sqs
- Date: 2013-3-23
- Description: 车牌号分配页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>车位分配</title>	
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
		
		function saveSubmit(){
			var carPortsNum = $('#carPortsNum').val();
			if(carPortsNum==null || carPortsNum==""){
				alert("请输入车位数量！");
				return ;
			}
			var frm = $('#saveForm');
 			frm.submit();
		}
		
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="saveForm" action="saveCarPorts.act" method="post"  autocomplete="off">
	<input type="hidden" name="orgId" id="orgId" value="${map.orgId}"/>
	<input type="hidden" name="orgName" id="orgName" value="${map.orgName}"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">单位名称：</td>
          <td width="30%">${map.orgName }</td>
          <td class="form_label" width="20%" align="left">车位总数：</td>
          <td width="30%">
          	${carPortsNum}
          </td>
        </tr>
      </table>      
   </h:panel>
   </form>
	<h:page id="list_panel" width="100%"  title="分配结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">${row_rowNum+((page.pageNo-1)*page.pageSize)}</d:col>
		    <d:col property="DISPLAY_NAME" class="display_centeralign" title="车位用户"/>
		    <d:col property="NUP_PN" class="display_centeralign" title="车牌号"/>
		    <d:col property="NUP_FLAG" dictTypeId="NUP_FLAG" class="display_centeralign" title="是否收费"/>
		    <d:col property="INSERT_DATE" class="display_centeralign" format="{0,date,yyyy-MM-dd }" title="登记日期"/>
		</d:table>
   </h:page>
	
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>