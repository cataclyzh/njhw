<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/metaIframe.jsp"%>
		<title>通卡管理</title>
		<link type="text/css" rel="stylesheet" href="css/tabMian.css" />
		<script type="text/javascript" src="js/tabMain.js"></script>
		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script type="text/javascript">
			/*加载房间分配页面*/
			function loadPubCardInfo(){
				var url = "${ctx}/app/personnel/unit/showPubCardAllInfo.act";
				$("#unit_nav_content").attr("src",url);
			}
			
			$(document).ready(function(){
			//$("#playCard").attr("backgroundPosition","-250px -400px");
				loadPubCardInfo();
			});
		</script>
	</head>
	<body style="background:#fff;">
	<div class="index_bgs" style="height:100%;">
       	<div class="bgsgl_border_left">
	  		<div class="bgsgl_border_right">通卡管理</div>
		</div>
		<div class="bgsgl_conter" style="min-height: 620px;">
        <div class="main_left">
        	<div style="margin-top:50px; width:100%; height: 63px;">
        		<a class="nav_btn" href="${ctx}/app/personnel/unit/playCardInit.act" style="margin-left:50px;"></a>
        	</div>
        </div>
        <div class="main_right">
            <iframe id= "unit_nav_content" frameborder="0" src="" width="100%" height="100%"></iframe>
        </div>
    </div>
    </div>
	</body>
</html>