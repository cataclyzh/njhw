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
	<title>机构列表页面</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body style="height:100%;width:100%;" class="easyui-layout">
	
	<div region="west" split="true" title="机构名称" style="width:519px;height:auto;background:#fafafa;padding:0px;overflow:auto;">
	  	<form id="queryForm"  method="post"  autocomplete="off">
		<h:page id="list_pane" width="100%"  title="机构名称列表" >
			<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
				<d:col style="width:30" title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
			    <d:col property="NAME"  class="display_leftalign" 	title="机构名称"/>
			    <d:col class="display_centeralign"  media="html" title="分配">	
					<a href="javascript:void(0);" onclick="assignPhone('${row.ORG_ID}')">[分配]</a>&nbsp;	
				</d:col>
			</d:table>
	   	</h:page>
		</form>
	</div>

	<div region="center" title="门禁分配" style="padding:0px;background:#fafafa;overflow:hidden;">
		<div id="center" title="门禁分配" style="width:100%;height:100%;padding:0px;background:#fafafa;overflow:hidden;">
			<iframe scrolling="auto" frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/common/include/body.jsp" style="width:100%;height:100%;padding:0px;"></iframe>
		</div>
	</div>

<script type="text/javascript">
    
    function assignPhone(orgId){
    	var url = "${ctx}/app/per/assignDoorCard.act?orgId="+orgId;
    	$("#ifrmObjList").attr("src",url);
    }
    
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
		
	//	<c:if test="${isSuc=='true'}">
	//		easyAlert('提示信息','分配成功！','info',
	//   		  function(){closeEasyWin('winId');}
	//		  );
	//	</c:if>
	//	<c:if test="${isSuc=='false'}">
	//		easyAlert('提示信息','分配失败！','error');
	//	</c:if>
		
</script>
</body>
</html>