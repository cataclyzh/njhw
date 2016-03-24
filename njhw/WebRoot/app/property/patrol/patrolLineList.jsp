<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>巡查路线管理</title>
		<%@ include file="/common/include/meta.jsp"%>
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
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div class="fkdj_index">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_right">
					巡查路线管理
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name ="queryForm" action="queryPatrolLineList.act" method="post">
							<h:panel id="query_panel" width="100%" title="查询条件">
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name" style="width:80px;">
											路线名称
										</td>
										<td style="width:200px;">
										    <s:textfield name="patrolLineName" id="patrolLineName" cssClass="fkdj_from_input"/>
										</td>
										<td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="queryPatrolLine()">查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="false" cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:45;" class="display_centeralign"  title="<input type=\"checkbox\" id=\"checkAll\" checked=\"checked\"/>">
						                <input checked="checked" type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.PATROL_LINE_ID}"/>
			                        </d:col>
									<d:col property="PATROL_LINE_ID" class="display_centeralign"
										title="路线编号" maxLength="15"/>
									<d:col class="display_centeralign" title="路线名称" maxLength="15">
										<a style="cursor:pointer;color:black;" onclick="toViewPatrolLine('${row.PATROL_LINE_ID}');">${row.PATROL_LINE_NAME}</a>
									</d:col>
									<d:col class="display_centeralign" title="操作">
										<span class="display_centeralign" 
										        onclick="toEditPatrolLine('${row.PATROL_LINE_ID}');" 
										        style="cursor:pointer" 
										        onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'">
												[修改]</span>
									</d:col>
								</d:table>
							</h:page>
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" style="cursor: pointer;" onclick="toAddPatrolLine();">增加</a>
				<a class="fkdj_botton_right" href="javascript:deletePatrolLine()">删除</a>
			</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	//查询线路
	function queryPatrolLine(){
	    querySubmit();
	}
	</script>
	
	<script type="text/javascript">
	//跳转至新增线路页面
	function toAddPatrolLine(){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toAddPatrolLine.act";
	}
	
	//跳转至编辑线路页面
	function toEditPatrolLine(patrolLineId){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toEditPatrolLine.act?patrolLineId=" + patrolLineId;
	}
	
	//跳转至查看线路页面
	function toViewPatrolLine(patrolLineId){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toViewPatrolLine.act?patrolLineId=" + patrolLineId;
	}
	
	$(document).ready(function(){
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changecheckboxstyle();
		});
	
   //删除线路
	function deletePatrolLine() {
		easyConfirm('提示', '确定删除？', function(r) {
			if (r) {
			    var checkboxs = $("input[type='checkbox'][name='_chk'][checked]");
			    $.each(checkboxs, function(){
			            if(this.checked == true){
			            var patrolLineId = this.value;
				        window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/deletePatrolLine.act?patrolLineId=" + patrolLineId;
				        }
				    });
			}
		});
	}
    </script>
</body>
</html>
