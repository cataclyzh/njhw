<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>
<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
	
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="${ctx}/app/caller/frontReg/cardSender.js"
	type="text/javascript"></script>
<script type="text/javascript">
</script>
<div id="left_below"  style="overflow:hidden">
<div class="fkdj_lfrycx;overflow-x:visible;overflow-y:visible" >

		<div style="height:438px;">
		<h:page id="list_panel" width="100%;"  title="">
		<d:table style="height:98%;width:96%" name="page.result" id="row" uid="row" export="true" requestURI="visitorList.act?type=export" class="table">	
		    <d:col class="display_leftalign" title="姓名" media="html">
			    <a style="cursor:pointer;color:black;" onclick="javascript:readInfoval('${row.VS_ID}');">${row.VI_NAME}</a>
			</d:col>
			<d:col class="display_leftalign" title="姓名" media="excel">
			    ${row.VI_NAME}
			</d:col>
		    <d:col class="display_centeralign" title="预约性质">
		        <c:if test="${row.VS_TYPE == 1}">
		                                   主动预约
				</c:if>
				<c:if test="${row.VS_TYPE == 2}">
		                                   外网预约
				</c:if>
				<c:if test="${row.VS_TYPE == 3}">
		                                   前台预约
				</c:if>
		    </d:col>
		    <d:col property="VI_MOBILE"   class="display_centeralign"  title="手机" maxLength="11" />
		    <d:col property="VS_FLAG" dictTypeId="USER_VISIT_FLAG" class="display_centeralign" title="访问状态" maxLength="10"/>
            <d:col property="VS_ST"   class="display_centeralign"  title="开始时间" maxLength="19"/>
            <d:col property="VS_ET"   class="display_centeralign"  title="结束时间" maxLength="19"/>
			<d:col property="USER_NAME"   class="display_centeralign"  title="被访问人" media="excel"/>
            <d:col property="ORG_NAME"   class="display_centeralign"  title="被访问人单位" media="excel"/>
            <d:col class="display_centeralign"  title="操作" media="html">
			    <span class="display_centeralign" style="cursor:default;color='#778899';" <c:if test="${row.VS_TYPE == 3 && (row.VS_FLAG == '01' || row.VS_FLAG == '02' || row.VS_FLAG == '03')}"> onclick="cancelVistor('${row.VS_ID}');" onmousemove="this.style.color='#ffae00';this.style.cursor='pointer';"</c:if>>[取消预约]
				</span>
            </d:col>
            <d:setProperty name="export.excel.filename" value="访客一览.xls"/>
			<d:setProperty name="export.amount" value="list"/>
			<d:setProperty name="export.xml" value="false"/>
			<d:setProperty name="export.types" value="excel"/>
		</d:table>
		</h:page>

</div>
</div>
</div>