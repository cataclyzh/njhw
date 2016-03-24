﻿﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="p" %>

<html>
<head>
<%@ include file="/common/include/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待办事项</title>
<script type="text/javascript">
	var _ctx = '${ctx}';
</script>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
	rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/basic/jquery.js.gzip"
	type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
	type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<link href="${ctx}/app/personnel/telAndNumber/css/TelMessage.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="main_index">
		<jsp:include page="/app/integrateservice/header.jsp">
			<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
		</jsp:include>
		<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div class="bgsgl_border_left">
			 <!--此处写页面的标题 -->
				<div class="bgsgl_border_right">
					待办事项
				</div>
			</div>
			<div class="bgsgl_conter" style="min-height: 100px">
			    <!--此处写页面的内容 -->
				<div class="main1_main2_right">
					<ul>
						<div class="caller_index"><span class="caller_title">创建人</span><span class="caller_fistMy" >待办内容</span><span class="caller_left">创建时间</span><span class="caller_left"> 截止时间</span></div>
		    			<div> <!--  style="overflow: auto;height: 200px;" -->
		    			<c:if test="${not empty listMatter }">
							<c:forEach items="${listMatter }" var="listDate" varStatus="status">
								<li>
									<div class="caller_center"><a style="cursor: pointer;" class="caller_conter_right" onclick="showMatter('${listDate.MESSAGEITEMGUID}')">${listDate.FROMDISPNAME }</a><span style="cursor: pointer;" class="caller_fistMy" onclick="showMatter('${listDate.MESSAGEITEMGUID}')">${fn:substring(listDate.TITLE,0,40) }</span><span class="caller_right">${listDate.GENERATEDATE }</span><span class="caller_right">${listDate.LASTFILEDATE }</span></div>
								</li>
							</c:forEach>
						</c:if>
						</div>
					</ul>
					<div class=""><a href="javascript:void(0);"></a></div>
					<div class="main_peag" id="main_peag" style="border: 0px solid red;">
						<div style="border: 0px solid red;float: left;width: 50px;height: 32px;line-height: 32px;">
							<!--span>${empty param.page ? 1 : param.page }</span>/<span>${pagesCount }</span-->
						</div>
					</div>
					<p:pager recordCount="${recordCount}" pageSize="20" pageNo="${pageNo}" recordShowNum="4" url="${ctx}/message/queryMyMatter.act"/>
					<div class="clear"></div>
				</div>
				<div class="bgsgl_clear"></div>
			</div>
		</div>
		
		<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
</body>
</html>
<script type="text/javascript">
	function showMatter(MessageItemGuid){
		var url = "${ctx}/message/queryMyMatterInfo.act?MessageItemGuid="+MessageItemGuid;
		//openEasyWin("winId","待办事项",url,"700","500",true);
		showShelter("700", "430", url);
	}
	function goPage(a){
		var url = "${ctx}/message/queryMyMatter.act?page="+(a);
		window.location.href = url;
	}
</script>