<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>机构列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	 <script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
	<script ty pe="text/javascript">
	function modOrg(id){
		//alert(id);
		window.location.href="${ctx}/app/personnel/unit/manage/modOrgByid.act?orgId="+id;
}
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0">
	
	

	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
				
		    <d:col property="NAME"  class="display_centeralign"   title="姓名"/>
		   
		    <d:col class="display_centeralign"  media="html" title="操作">	
				<a href="javascript:void(0);" onclick="modOrg('${row.ORG_ID}')">[修改]</a>&nbsp;
			</d:col>	
		  			  
		</d:table>
   </h:page>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
