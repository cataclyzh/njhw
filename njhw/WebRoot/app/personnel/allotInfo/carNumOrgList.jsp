<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 
- Description: 部门车位分配机构列表
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>部门车位分配机构列表</title>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body style="height:100%;width:100%;" class="easyui-layout">
	<div region="west" split="true" title="机构列表" style="width:519px;height:auto;background:#fafafa;padding:0px;overflow:auto;">
		<form id="queryForm"  method="post"  autocomplete="off">
			<h:page id="list_pane" width="100%" title="机构列表">
			<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
				<d:col style="width:30" title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
			    <d:col property="name"  class="display_leftalign" 	title="机构名称"/>
			    <d:col class="display_centeralign"  media="html" title="分配">
					<a href="javascript:void(0);" onclick="allotCarNum('${row.orgId}')">[分配]</a>&nbsp;	
				</d:col>
			</d:table>
			</h:page>
		</form>
	</div>
	<div region="center" title="部门车位分配" style="padding:0px;background:#fafafa;overflow:hidden;">
		<div id="center" title="部门车位分配" style="width:100%;height:100%;padding:0px;background:#fafafa;overflow:hidden;">
			<iframe scrolling="auto" frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/common/include/body.jsp" style="width:100%;height:100%;padding:0px;"></iframe>
		</div>
	</div>

	<script type="text/javascript">
	    function allotCarNum(orgId){
	    	var url = "${ctx}/app/personnel/list.act?orgId="+orgId;
	    	$("#ifrmObjList").attr("src",url);
	    }
	</script>
</body>
</html>