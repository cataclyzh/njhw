<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/common/include/meta.jsp"%>
		<title>社区导航</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/app/portal/citynav/css/citynavigation.css" />
		<script language="JavaScript" src="${ctx}/app/portal/citynav/js/citynavigation.js" type="text/JavaScript"></script>
		<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
		<script type="text/javascript">
			var menuKeywordStr = "${menuKeywordStr}";
			var urlStr = '${urlStr}';
			var basePath = '<%=basePath%>';
			var loginurl = MXW_SERVER_URL+'?command=login&uid=${loginname}&pwd_md5=${loginpwd}&t=' + Math.random();
		</script>
	</head>
	<body>
		<jsp:include page="/app/portal/toolbar/toolbar.jsp"></jsp:include>
		<iframe  id="loginframe" style="position:absolute;display:none;"></iframe>
		<div class="header">
			<div class="logo"></div>
		</div>
		<div id="main" class="main">
			<div class="left1">
				<div class="left1_top"></div>
				<a id="citynav_1" class="left1_main" title="电子商务" href="javascript:void(0)" hidefocus></a>
				<div class="left1_bottom"></div>
			</div>
			<div class="left2">
				<div class="left2_1"></div>
				<a id="citynav_2" class="left2_2" title="运营管理中心" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_3"  class="left2_3" title="楼宇监控" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_4" class="left2_4" title="能耗管理" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_5" class="left2_5" title="物业管理" href="javascript:void(0)" hidefocus></a>
				<div class="left2_6"></div>
			</div>
			<div class="left3">
				<a id="citynav_6" class="left3_1" title="人员管理" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_7" class="left3_2" title="访客管理" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_8" class="left3_3" title="IT运维管理" href="javascript:void(0)" hidefocus></a>
				<div class="left3_4"></div>
			</div>
			<div class="left4">
				<div class="left4_top"></div>
				<div class="left4_main">
				<div class="left4_main_left">
				<a id="citynav_9" class="left4_main_left1" title="办公自动化" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_10" class="left4_main_left2" title="IP电话应用" href="javascript:void(0)" hidefocus></a>
				<div class="left4_main_left3"></div>
				</div>
				<div class="left4_main_right">
				<a id="citynav_11" class="left4_main_right1" title="移动办公" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_12" class="left4_main_right2" title="即时通讯" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_13" class="left4_main_right3" title="综合办公门户" href="javascript:void(0)" hidefocus></a>
				<div class="left4_main_right4"></div>
				</div>
				</div>
			</div>
			<div class="left5"></div>
			<div class="left6">
				<div class="left6_1"></div>
				<a id="citynav_14" class="left6_2" title="可持续性服务" href="javascript:void(0)" hidefocus></a>
				<div class="left6_3"></div>
			</div>
			<div class="left7">
				<div class="left7_top"></div>
				<a id="citynav_15" class="left7_1" title="餐饮服务" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_16" class="left7_2" title="健康管理" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_17" class="left7_3" title="文印服务" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_18" class="left7_4" title="电子秘书" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_19" class="left7_5" title="一键保修" href="javascript:void(0)" hidefocus></a>
				<a id="citynav_20" class="left7_6" title="其他服务" href="javascript:void(0)" hidefocus></a>
			</div>
			<div class="car1"></div>
			<div class="car2"></div>
		</div>
	</body>
</html>