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
		<title>巡更历史轨迹</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<!-- 		<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>  
<script type="text/javascript" src="${ctx}/app/property/patrol/js/addPositionCard.js"  />
-->
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		
		
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div class="fkdj_index">
			<div class="bgsgl_border_left">
				<div class="bgsgl_border_right">
					巡更历史轨迹
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name ="queryForm" action="getPatrolStickRecord.act" method="post">
						<h:panel id="query_panel" width="100%" title="查询条件">
							<table align="center" id="hide_table" border="0" width="100%">
								<tr>
									<td class="fkdj_name" width="8%">员工姓名</td>
									<td width="20%"><s:textfield name="userName" id="userName" cssClass="fkdj_from_input" /></td>
								</tr>
								<tr>
									<td class="fkdj_name">当班时段</td>
									<td><s:textfield name="startTime"
											id="scheduleStartQueryTime" cssClass="fkdj_from_input"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
									<td class="fkdj_name_lkf" style="text-align: center;">至</td>
									<td><s:textfield name="endTime" id="scheduleEndQueryTime"
											cssClass="fkdj_from_input"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
									<td><a class="property_query_button"
										href="javascript:void(0);"
										onmousemove="this.style.background='FFC600'"
										onmouseout="this.style.background = '#8090b4'"
										onclick="queryPatrolPositionCard()">查询 </a></td>
								</tr>
							</table>
						</h:panel>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="true" requestURI="getPatrolStickRecord.act?type=export" cellspacing="0" cellpadding="0" class="table">
									<d:col property="DISPLAY_NAME" class="display_centeralign" title="员工姓名" maxLength="15"/>
									<!-- 
									<d:col property="SHORT_NAME" class="display_centeralign" title="部门名称" maxLength="15"/>
									 -->
									<d:col property="CHECK_DATE_TIME" class="display_centeralign" title="记录时间"/>
									<d:col property="POSITION" class="display_centeralign" title="位置名称"/>
									<d:col property="TERM_ID" class="display_centeralign" title="定位卡号"/>
									<d:setProperty name="export.excel.filename" value="巡更历史轨迹.xls" />
									<d:setProperty name="export.amount" value="list" />
									<d:setProperty name="export.xml" value="false" />
									<d:setProperty name="export.types" value="excel" />
								</d:table>
							</h:page>

						</form>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	//查询员工定位卡记录
	function queryPatrolPositionCard(){
		$("#pageNo").val("1");
		$("#queryForm").submit();
	    //querySubmit();
	}
	</script>
	
	<script type="text/javascript">
	//跳转至新增员工定位卡页面
	function toAddPatrolPositionCard(){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toAddPositionCard.act";
	}
	
	//跳转至编辑员工定位卡页面
	function toEditPatrolPositionCard(patrolPositionCardId){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toEditPositionCard.act?patrolPositionCardId=" + patrolPositionCardId;
	}
	
	//跳转至查看员工定位卡页面
	/*
	function toViewPatrolPositionCard(patrolPositionCardId){
	    window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/toViewPositionCard.act?patrolPositionCardId=" + patrolPositionCardId;
	}*/
	
	$(document).ready(function(){
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changecheckboxstyle();
		});
	
   //删除员工定位卡记录
	function deletePatrolPositionCard() {
		easyConfirm('提示', '确定删除？', function(r) {
			if (r) {
			    var checkboxs = $("input[type='checkbox'][name='_chk'][checked]");
			    var patrolPositionCardIds = "";
			    $.each(checkboxs, function(){
			            if(this.checked == true){
			            var tempPatrolPositionCardId = this.value;
			            patrolPositionCardIds =patrolPositionCardIds+ "patrolPositionCardIds="+tempPatrolPositionCardId+"&";
				        }
				    });
				        window.parent.frames["main_frame_right"].location.href = "${ctx}/app/pro/deletePositionCard.act?" + patrolPositionCardIds;
			}
		});
	}
    </script>
</body>
</html>
