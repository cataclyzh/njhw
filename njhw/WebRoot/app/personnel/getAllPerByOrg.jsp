<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	 <script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
	<script ty pe="text/javascript">
	
			function check(id){
				    var orgId = $("#orgId").val();
				  //  alert(orgId);
					window.location.href="${ctx}/app/personnel/unit/manage/infoPersManage.act?userId="+id;
					
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
				
		    <d:col property="DISPLAY_NAME"   class="display_centeralign"   title="姓名"/>
		    <d:col property="RESIDENT_NO" class="display_leftalign" title="身份证"/>
		    
		    <d:col property="NAME"  class="display_leftalign" title="部门"/>
		    <d:col property="UEP_SEX" dictTypeId="SEX" class="display_centeralign" title="性别"/>
		    <d:col property="UEP_MOBILE"  class="display_centeralign" title="手机号码"/>
		  
		    <d:col property="ROOM_INFO" class="display_centeralign" title="房间号"/>
		    <d:col property="TEL_NUM"  class="display_centeralign" title="电话号码"/>
		    
		<!--     <d:col class="display_centeralign"  media="html" title="操作">	
				<a href="javascript:void(0);" onclick="check('${row.USERID}')">[查看]</a>&nbsp;
				<input type="hidden" name="orgId" id="orgId" value="${row.ORG_ID}" />
		  	</d:col> -->		  
		</d:table>
   </h:page>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
