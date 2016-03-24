﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>房间分配及授权管理</title>
		<%@ include file="/common/include/metaIframe.jsp"%>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		$(document).ready(function(){
			showAllRoom();
			//var orgId = ${page.result[0].orgId};
			//var url = "${ctx}/app/room/queryAllotRooms.act?orgId="+orgId+"&isAllot=1";
    		//$("#ifrmObjList").attr("src",url);
		});
    		    function allotRoom(orgId){
    				var url = "${ctx}/app/room/queryAllotRooms.act?orgId="+orgId+"&isAllot=1";
    				$("#ifrmObjList").attr("src",url);
    			}
    			function showAllRoom(){
    				var url = "${ctx}/app/room/queryAllRooms.act?";
    				$("#ifrmObjList").attr("src",url);
    			}
		</script>
	</head>
	<body style="background: #fff;">
		<div class="bgsgl_border_left">
			<!--此处写页面的标题 -->
			<div class="bgsgl_border_right">
				房间分配
			</div>
		</div>
		<div class="bgsgl_conter" style="min-height: 660px">
			<div title="机构列表"
				style="width: 30%; height: 660px; padding: 0px; overflow: auto; float: LEFT;">
				<form id="queryForm" method="post" autocomplete="off">
				<div style="height:590px;overflow:auto;">
					<div style="width:100%;height:30px;color:#808080;font-size:10pt;font-weight:bold;background:#f6f5f1;">
						<div style="height:10px;"></div>
						<span style="margin-left:24px;float:left;">机构列表</span>
						<a href="javascript:void(0);" style="float:right;"
									onclick="showAllRoom()">查看全部</a>
					</div>
						<d:table name="page.result" id="row" uid="row" export="false"
							class="table" requestURI="list.act?exFile=1">
							<d:col style="width:30" title="序号" class="display_centeralign">
										${row_rowNum+((page.pageNo-1)*page.pageSize)}
									</d:col>
							<d:col property="name" class="display_leftalign" title="机构名称" />
							<d:col class="display_centeralign" media="html" title="分配">
								<a href="javascript:void(0);" style="display:block;width:40px"
									onclick="allotRoom('${row.orgId}')">[分配]</a>&nbsp;	
									</d:col>
						</d:table>
				</div>
				</form>
			</div>
			<div title="部门房间分配"
				style="padding: 0px; background: #fff; overflow: hidden; float: LEFT; height: 660px; width: 70%;">
				<div id="center" title="部门房间分配"
					style="height: 100%; padding-left: 10px; background: #fff; overflow: hidden;">
					<iframe scrolling="auto" frameborder="0" name="ifrmOrgList"
						id="ifrmObjList" src="${ctx}/common/include/body.jsp"
						style="width: 100%; height: 100%; padding: 0px;"></iframe>
				</div>
			</div>
			<div class="bgsgl_clear"></div>
		</div>
	</body>

</html>