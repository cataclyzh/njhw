﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager_min.tld" prefix="w" %>

<html>
	<head>
		<!-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->
		<meta http-equiv="refresh" content="300"> 
		<title>访客异常列表</title>
		<script type="text/javascript">
	var _ctx = '${ctx}';
</script>
		<%@ include file="/common/include/meta.jsp"%>
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
		<link href="${ctx}/app/buildingmon/pageIndex/css/3d_css.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript">
		 document.domain = "njnet.gov.cn";
		</script>
		<script type="text/javascript" language="javascript">
			//<!CDATA[
			function goPage(a){
				var url = "${ctx}/app/3d/visitorErrorList.act?pageNo="+(a);
				window.location.href = url;
			}
			//如果要做成点击后再转到请将__tag_34$15_中的onmouseover 改成 onclick;
		</script>
<style type="text/css" charset="utf-8">
.main_index{
	width:100%;
	margin:0 auto;
	background:#fff;
	}
.bgsgl_conter{
	border:solid 0px #7F90B3; 
	border-top:none;
	padding:0px;
}
.errorVisitor_table{
	margin-top:2px;
}

.errorVisitor_table tr{
	height:30px;
	line-height:30px;
}

.errorVisitor_title{
	font-family:"微软雅黑";
	color:#7f90b3;
	font-size:14px;
	font-weight:bold;
	padding-left:10px;
	background:#ecebe4;
}
.errorVisitor_td{
	color:#808080;
	font-size:12px;
	padding-left:10px;
	background:#f6f5f1;	
	border-bottom:dotted 1px #808080;
}
</style>
	</head>
	<body>
		<div class="main_index">
			<div class="bgsgl_conter" style="min-height: 550px">
				<table width="100%" border="0" cellspacing="0" class="errorVisitor_table" cellpadding="0">
				    <tr>
						<td class="errorVisitor_title" style="width:100px">
							访客姓名
						</td>
						<td class="errorVisitor_title">
							访问单位
						</td>
						<td class="errorVisitor_title" style="width:135px">
							入闸时间
						</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" class="errorVisitor_table" cellpadding="0">
				<c:forEach var="row" items="${page.result}" >
					<tr class="errorVisitor_tr">
						<td class="errorVisitor_td" style="width:100px">
						    <a style="cursor:pointer" onclick="parent.window.pushErrorPoint('${row.ID}', '${row.TIME}')">
							    ${row.NAME}
							</a>
						</td>
						<td class="errorVisitor_td" title=${row.ORGNAME}>
							${row.SHORT}
						</td>
						<td class="errorVisitor_td" style="width:135px">
							${row.TIME}
						</td>
					</tr>
				</c:forEach>
				</table>
				<div>
                    <w:pager pageSize="${page.pageSize}" pageNo="${page.pageNo}" recordShowNum="1" url="${ctx}/app/3d/visitorErrorList.act" recordCount="${page.totalCount}"/>
                </div>
			</div>
		</div>
	</body>

</html>