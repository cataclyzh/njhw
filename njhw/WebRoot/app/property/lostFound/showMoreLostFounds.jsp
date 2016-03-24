<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
			<%@ include file="/common/include/meta.jsp"%>
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>失物招领更多信息</title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>

		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/block.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/styles/property/css_body.css" rel="stylesheet"
			type="text/css" />
		<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/property/calendarCompare.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
			<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

		<script type="text/javascript">
			function showLostFoundsList(){
	 			$("#queryForm").submit();
	 		}
	 		
	 	
			
				function viewLostFound(lostFoundId) {
				var url = "${ctx}/app/pro/viewLostFoundById.act?lostFoundId="+lostFoundId;
         		showShelter('660','500',url);
  	}
		</script>
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/integrateservice/init.act"
					name="gotoParentPath" />
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
					<!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						失物招领一览
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
					<!--此处写页面的内容 -->
					<div class="fkdj_lfrycx">
						<div class="fkdj_from">
							<form id="queryForm" name="queryForm"
								action="showMoreLostFounds.act" method="post">
								<s:textfield name="page.pageSize" id="pageSize" theme="simple"
									cssStyle="display:none" onblur="return checkPageSize();"
									onkeyup="value=value.replace(/[^\d]/g,'');" />
								<h:page id="list_panel" width="100%" title="">
									<d:table name="page.result" id="row" uid="row" export="false"
										cellspacing="0" cellpadding="0" class="table">
										<c:if test="${row.LOST_FOUND_STATE == 0}">
						<d:col title="标题" style="width:30%;"  class="display_leftalign">
					      <a style="cursor:pointer;color:black;" onclick="viewLostFound(${row.LOST_FOUND_ID})">${row.LOST_FOUND_TITLE} </a>
				    	 </d:col>
				</c:if>		
					<c:if test="${row.LOST_FOUND_STATE == 1}">
						<d:col title="标题" style="width:30%;"   class="display_leftalign">
					    <a style="cursor:pointer;color:orange;" onclick="viewLostFound(${row.LOST_FOUND_ID})">${row.LOST_FOUND_TITLE} </a>
				    	 </d:col>
				</c:if>	
										

										<d:col property="LOST_FOUND_THINGNAME"
											class="display_centeralign" title="失物名称" />
										<d:col property="LOST_FOUND_LOCATION"
											class="display_centeralign" title="捡到失物地点" />
										<d:col property="LOST_FOUND_INTIME"
											class="display_centeralign" title="登记时间" />
										<d:col class="display_centeralign" title="认领状态">
											<c:if test="${row.LOST_FOUND_STATE == 0}">
												<span class="display_centeralign">未认领</span>
											</c:if>
											<c:if test="${row.LOST_FOUND_STATE == 1}">
												<span class="display_centeralign">已认领</span>
											</c:if>
										</d:col>
									</d:table>
								</h:page>
							</form>
							<div class="bgsgl_clear"></div>
						</div>
					</div>
					<jsp:include page="/app/integrateservice/footer.jsp" />
				</div>
			</div>
		</div>
	</body>
</html>