<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	 <script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
	<script type="text/javascript">
		function querySubmit(){	
			$("#queryForm").submit(); 
		}
		
		function clear() {
			$(":text[id!='pageSize']").val("");
			$("#recu").removeAttr("checked");
		}
		
		$(function() {
			if ("${recu}" == "on") $("#recu").attr("checked", "checked");
		})
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="overflow: auto;">
	<s:hidden name="vi_Id"/>
 	<form id="queryForm" action="queryPerInfo.act" method="post"  autocomplete="off">
	 	<h:panel id="query_panel" width="100%" title="查询条件">		
		<s:hidden name="orgId"/>
	      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
	       
	        <tr>
	          	<td class="form_label"  align="left"> 姓名： </td>
	          	<td > <s:textfield name="displayName" theme="simple" size="18" /> </td>
	           	<td class="form_label"  align="left"> 手机： </td>
	          	<td > <s:textfield name="phone"  id="phone" theme="simple" size="18"/> </td> 
	          	<td class="form_label"  align="left">  市民卡： </td>
	          	<td > <s:textfield name="cityCard"  id="cityCard" theme="simple" size="18"/> </td> 
	          	<td class="form_label"  align="left"> 身份证号： </td>
	          	<td > <s:textfield name="residentNo"  id="residentNo" theme="simple" size="18"/> </td> 
	          	<td class="form_label"  align="left"> 车牌： </td>
	          	<td > <s:textfield name="carNum"  id="carNum" theme="simple" size="18"/> </td> 
	         	<td class="form_label" align="left">
	         		<table>
	         			<tr>
	         				<td><input type="checkbox" name="recu" id="recu" value="on"/></td>
	         				<td>所有下级人员</td>
	         			</tr>
	         		</table>
	          	</td>
	        </tr> 
	       
	        <tr>
	          <td colspan="12" class="form_bottom" >
	            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
	          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" id="rstbutton" iconCls="icon-reload" onclick="clear();">清空</a>
	          </td>
	        </tr>
	      </table>     
		</h:panel>
		
		<h:page id="list_panel" width="100%" title="结果列表">		
			<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
				<d:col style="width:30px;" title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
			    <d:col property="ORG_NAME"  class="display_leftalign" title="单位"/>
			    <d:col property="DEPARTMENT_NAME"  class="display_leftalign" title="处室"/>
			    <d:col property="DISPLAY_NAME"   class="display_leftalign"   title="姓名"/>
				<d:col property="UEP_SEX"  dictTypeId="SEX"  class="display_centeralign" title="性别"/>
				<d:col property="ROOM_INFO" class="display_centeralign" title="房间号"/>
				<d:col property="TEL"  class="display_centeralign" title="电话号码"/>
				<d:col property="UEP_MOBILE"  class="display_centeralign" title="手机号码"/>		  
				<d:col property="CARD_ID"  class="display_leftalign" title="市民卡"/>	
				<d:col property="RESIDENT_NO" class="display_leftalign" title="身份证"/>
			    <d:col property="PLATENUM"  class="display_leftalign" title="车牌号"/>
			</d:table>
	   </h:page>
    </form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
