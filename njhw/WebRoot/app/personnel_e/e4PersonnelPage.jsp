<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<%@ include file="/common/include/header-meta.jsp" %>
<title>人员管理</title>
<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<div class="main_index" style="margin: 0 auto;">
	<jsp:include page="/app/integrateservice/header.jsp">
			<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
	</jsp:include>
	</div>
	
	<div class=main_index style="height:560px;">
	<div class="index_bgs" style="height:100%;padding-left: 10px; padding-right: 10px;padding-bottom: 10px;">
	<div class="bgsgl_border_left">
	  	<div class="bgsgl_border_right">E座人员机构管理</div>
	</div>
	<div class="bgsgl_conter" style="height:496px;padding-bottom: 10px;">
	<div class="bgsgl_clear"></div>
	<div split="true" title="人员机构管理" style="width:22%;background:#fafafa;padding:0px;FLOAT:LEFT;height:100%;">
	<c:if test="${loginId ne '1'}">
		<iframe scrolling="no" frameborder="0" name="ifrmOrgTree" id="ifrmObjTree" src="${ctx}/app/e4personnel/personnelTree.act"
		border="0" marginheight="0" marginwidth="0" frameborder="0" 
		style="width:100%;height:100%;padding:0px;"></iframe>	
	</c:if>
	<c:if test="${loginId eq '1'}">
		<iframe scrolling="no" frameborder="0" name="ifrmOrgTree" id="ifrmObjTree" src="${ctx}/app/per/orgTreeUserCheckinAdmin.act"
		border="0" marginheight="0" marginwidth="0" frameborder="0" 
		style="width:100%;height:100%;padding:0px;"></iframe>	
	</c:if>		
	</div>
	<div style="width:78%;background:#fafafa;padding:0px;overflow:hidden;FLOAT:LEFT;height:102%;" id = "ifrmObj2">
		<iframe frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/app/personnel/orguser/orgIndex.act?orgId=${_orgid}&opt=init" style="width:100%;height:100%;padding:0px;"></iframe>
	</div>
	</div>
	</div>
	</div>
	<jsp:include page="/app/integrateservice/footer.jsp" />
</body>
</html>
