<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 组织机构管理框架页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<title>人员机构管理</title>
<%@ include file="/common/include/header-meta.jsp" %>
<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="main_index">
		<jsp:include page="/app/integrateservice/header.jsp"></jsp:include>
	<%-- <c:if test="${param.type eq 'A'}">
		<jsp:include page="/app/integrateservice/headerWy.jsp">
		    <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
		</jsp:include>
	</c:if>
	<c:if test="${param.type ne 'A'}">
		<jsp:include page="/app/integrateservice/header.jsp">
		    <jsp:param value="/app/pro/propertyIndex.act" name="gotoParentPath"/>
		</jsp:include>
	</c:if> --%>
	<div class="index_bgs" style="height:100%;">
	<div class="bgsgl_border_left">
	  	<div class="bgsgl_border_right">人员机构管理</div>
	</div>
	<div class="bgsgl_conter" style="height:600px;padding-bottom: 10px;">
	<div class="bgsgl_clear"></div>
	<div split="true" title="人员机构管理" style="width:25%;background:#fafafa;padding:0px;FLOAT:LEFT;height:100%;">
		<iframe scrolling="no" frameborder="0" name="ifrmOrgTree" id="ifrmObjTree" src="${ctx}/app/per/orgTreeUserCheckinOther.act"
		border="0" marginheight="0" marginwidth="0" frameborder="0" 
		style="width:100%;height:100%;padding:0px;"></iframe>
	</div>
	<div style="width:75%;background:#fafafa;padding:0px;overflow:hidden;FLOAT:LEFT;height:100%;" id = "ifrmObj2">
		<iframe frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/app/personnel/perInfoCheckinOther.act?opt=init" style="width:100%;height:100%;padding:0px;"></iframe>
	</div>
	</div>
	</div>
	  <div>
    <iframe src="${ctx}/app/property/complaint/footer.jsp" width="100%" height="103" scrolling="no" frameborder="0"></iframe>
  </div>
</div>
</body>
</html>
