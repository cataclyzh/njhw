<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: sqs
- Date: 2013-3-29
- Description: 委办局房间分配查询
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>委办局房间分配查询</title>	
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
		// 通过选择不同的部门去调用接口显示三维信息(委办局房间分配  调三维方法)
		function queryAssignRoom(){
			alert($("#orgId").val());
			//sw js-api处理
			return;
		}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" method="post"  autocomplete="off">
	 	<h:panel id="query_panel" width="100%" title="委办局">	
	      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
	        <tr>
	          <td class="form_label" width="20%" align="left">委办局：</td>
	          <td width="30%">
	          	<select id="orgId" name="orgId" onchange="javascript:queryAssignRoom();">
	          		<option value="0">全部</option>
	          		<c:forEach items="${orgList}" var="org">
	          			<c:if test="${org.orgId == orgId }"><option value="${org.orgId }" selected="selected">${org.name }</option></c:if>
	          			<c:if test="${org.orgId != orgId }"><option value="${org.orgId }">${org.name }</option></c:if>
	          		</c:forEach>
	          	</select>
	          </td>
	        </tr>
	      </table>
	   </h:panel>
	   
	   <div>调用三维界面</div>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/app/room/input.act?orgId="+$("#orgId").val();
		openEasyWin("winId","委办局房间分配查询",url,"800","500",true);
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
