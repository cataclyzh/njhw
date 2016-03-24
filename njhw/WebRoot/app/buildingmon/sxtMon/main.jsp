<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title id="PageTitle"></title>
		<link id="StyleSheetLink" type="text/css" rel="stylesheet"
			href="${ctx}/app/portal/css/buildingmon.css" />
		<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
		<script type="text/javascript">
		/**
		*@description 得到监控类型
		*@author herb
		*/
		function getMonType(){
			return MON_TYPE_SXT;
		}
	</script>
	</head>
	<frameset id="navWorkFrameSet" cols="272,*" framespacing="2"
		frameborder="1" border="2" bordercolor="#f6f6fb">
		<div region="west" split="true" title="资源树" style="width:150px;height:auto;background:#fafafa;padding:0px;overflow:hidden;">
		<frame name="ifrmOrgTree" id="ifrmObjTree" class="panelFrame"
			id="navigationFrame" src="${ctx}/app/buildingmon/objTree.act?floor=F&actionName=sxtMonList"
			frameborder="0" scrolling="no" marginheight="0" marginwidth="0"
			noresize />
		</div>
		<div region="center" title="资源列表" style="padding:0px;background:#fafafa;overflow:hidden;">
		<frame name="ifrmOrgList" id="ifrmObjList" class="panelFrame" frameborder="0"
			src="${ctx}/common/include/body.jsp" marginwidth="0" marginheight="0"
			name="ifrmOrgList" id="ifrmObjList" scrolling=no />
		</div>
</html>