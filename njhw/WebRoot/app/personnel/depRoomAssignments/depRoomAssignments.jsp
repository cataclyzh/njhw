<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: sqs
- Date: 2013-3-19 10:12:11
- Description: 机构列表页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>物业房间分配</title>
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script>
		
	    $(document).ready(function() {
			rightAssignOrg();
		});
		
	 	function rightAssignOrg(){
		    var orgId = $('#org').val();
			var url = "${ctx}/app/per/propertyRoomAssign.act?orgId=" + orgId;
			$("#ifrmRoomList").attr("src",url);
		}

		//保存从树选中的房间
		function saveAssignList(){
			alert('sdsd');
			var a = $(window.frames["ifrmRoomList"].document).contents().find("chk_");
			alert(a);
			//parent.window.frames["ifrmRoomList"].saveNode();
		}

		//取消已分配的房间
		function delAssignList(){
		}
	</script>
</head>
<body style="height:100%;width:100%;" class="easyui-layout">
	<div region="west" split="true" title=" 新城大厦" style="width:150px;height:auto;background:#fafafa;padding:0px;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" name="ifrmOrgTree" id="ifrmObjTree" src="${ctx}/app/per/objTreeSelect.act"
	border="0" marginheight="0" marginwidth="0" frameborder="0" 
	style="width:100%;height:100%;padding:0px;"></iframe>		
	</div>
	<div region="center" title="已分配房间" style="width:100%;height:100%;padding:0px;background:#f1f8fd;" >
		<table>
			<tr>
			<td>单位：</td>
			<td>
				<select id="org" name="org" onchange="javaScript:rightAssignOrg();">
					<c:forEach items="${orgList}" var="org">
					<option value="${org.orgId}">${org.name}</option>
					</c:forEach>
				</select>
			</td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAssignList();">保存</a></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="delAssignList();">取消分配</a></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" >三维</a></td>
			<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" >导出清单</a></td>
			</tr>
			<tr>
				<td width="799px;" height="369px;" colspan="5"><iframe scrolling="auto" frameborder="0" name="ifrmRoomList" id="ifrmRoomList" src="${ctx}/common/include/body.jsp" style="width:100%;height:100%;padding:0px;"></iframe></td>
			</tr>
		</table>
	</div>
</body>
</html>