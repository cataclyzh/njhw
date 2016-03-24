<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/meta.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>访问受限</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="main_index">
		<jsp:include page="headerForNoAuth.jsp">
			<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
		</jsp:include>
			<div class="main_index">
				<div>
					<img src="${ctx}/app/integrateservice/images/few_eorr.jpg">
				</div>
			</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>