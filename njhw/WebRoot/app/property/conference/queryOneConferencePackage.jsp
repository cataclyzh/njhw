<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>会务详情信息</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/block.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
			<script src="${ctx}/app/property/conference/js/Conference.js" type="text/javascript"></script>
		
	<script type="text/javascript">
		function viewConferenceCancel(){
			parent.closeViewConference();
		}
		
	</script>
</head>

<body style="background:#F6F5F1;">
<!-- -------------------------查看Block------------------------- -->


		
	<form id="queryForm" name="queryForm" action="" method="post">
	<input type="hidden" id="view_conferenceId" value="${view_conference.conferenceId}" />
	

	<h:page id="list_panel" width="100%" title="">
 		<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
		
			<d:col property="CONFERENCE_STARTTIME" class="display_centeralign"
				title="开始时间" />
				<d:col property="CONFERENCE_ENDTIME" class="display_centeralign"
				title="结束时间" />	
			<d:col property="CONFERENCE_USERNAME" class="display_centeralign"
				title="会议申请人" maxLength="15"/>
			
			<d:col property="CONFERENCE_USERPHONE" class="display_centeralign"
				title="联系方式" maxLength="15"/>
			
										
			<d:col class="display_centeralign" title="会议状态">
				<c:if test="${row.CONFERENCE_STATE == 0}">
					<span class="display_centeralign">申请中</span>
				</c:if>
				<c:if test="${row.CONFERENCE_STATE == 1}">
					<span class="display_centeralign">已完成</span>
				</c:if>	
				<c:if test="${row.CONFERENCE_STATE == 2}">
					<span class="display_centeralign">已评价</span>
				</c:if>
				<c:if test="${row.CONFERENCE_STATE == 3}">
					<span class="display_centeralign">已取消</span>
				</c:if>
				<c:if test="${row.CONFERENCE_STATE == 4}">
					<span class="display_centeralign">已确认</span>
				</c:if>								
			</d:col>
							
		</d:table>		
	</h:page>

</form>



	
  
</body>
</html>