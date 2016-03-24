<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源管理框架页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>资源管理框架</title>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/app/integrateservice/js/util.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/newtree.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/IntegratedOffice.js" type="text/javascript"></script>
<script src="${ctx}/app/portal/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/scripts/ca/highcharts.js" type="text/javascript"></script>
<script src="${ctx}/scripts/ca/exporting.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/index.js" type="text/javascript"></script>
<script src="${ctx}/app/integrateservice/js/energy.js" type="text/javascript"></script>
<link href="${ctx}/app/integrateservice/css/newtree.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/jienen.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
</head>
<body style="background:#fff;">
<div class="index_bgs">
	<div class="bgsgl_border_left">
	  	<div class="bgsgl_border_right">IP电话帮助</div>
	</div>
	<div class="bgsgl_conter">
		<!-- div region="west" split="true" title="组织机构" style="width:20%;background:#fafafa;padding:0px;overflow:hidden;float: left;height:560px">
			<iframe scrolling="auto" frameborder="0" name="ifrmOrgTree" id="ifrmObjTree" src="${ctx}/app/per/rzdwTree.act"
		border="0" marginheight="0" marginwidth="0" frameborder="0" 
		style="width:100%;height:100%;padding:0px;"></iframe>		
		</div> -->
		<div region="center" title="列表" style="background:#fafafa;float: right;width: 100%;height: 100%">
			<iframe scrolling="auto" frameborder="0" name="ifrmOrgList" id="ifrmObjList" src="${ctx}/app/cms/channelcms/ipHelp.act" style="width:100%;height:628px;padding:0px;"></iframe>
		</div>
		<div class="clear"></div>
	</div>
</div>

</body>
</html>
