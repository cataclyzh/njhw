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
		<title>定位卡管理</title>
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
					定位卡管理
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
						<form id="queryForm" name ="queryForm" action="queryPositionCardList.act" method="post">
							<h:panel id="query_panel" width="100%" title="查询条件">
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name">
											员工姓名
										</td>
										<td>
										    <s:textfield name="userName" id="userName" cssClass="fkdj_from_input"/>
										</td>
										<td class="fkdj_name">
											所属部门
										</td>
										<td>
											<select id="orgIdSelect" class="fkdj_select" name="orgIdSelect">
											    <option value="0">全部</option>
												<c:forEach items="${orgList}" var="org">
													<option value="${org.orgId }" <c:if test='${orgIdSelect == org.orgId }'> selected="selected" </c:if>>
														${org.name }
													</option>
												</c:forEach>
											</select>
										</td>
										<td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="queryPatrolPositionCard()">查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="false" cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:45;" class="display_centeralign"  title="<input type=\"checkbox\" id=\"checkAll\" checked=\"checked\"/>">
						                <input checked="checked" type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.PATROL_POSITION_CARD_ID}"/>
			                        </d:col>
									<d:col property="DISPLAY_NAME" class="display_centeralign" title="员工姓名" maxLength="10"/>
									<d:col property="SHORT_NAME" class="display_centeralign" title="部门名称" maxLength="15"/>
									<d:col property="TAGNICKNAME" class="display_centeralign" title="定位卡MAC"/>
									<d:col property="CARD_ID" class="display_centeralign" title="定位卡号" />
									<d:col class="display_centeralign" title="操作">
										<span class="display_centeralign" 
										        onclick="toEditPatrolPositionCard('${row.PATROL_POSITION_CARD_ID}');" 
										        style="cursor:pointer" 
										        onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'">
												[修改]</span>
									</d:col>
								</d:table>
							</h:page>
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" style="cursor: pointer;" onclick="toAddPatrolPositionCard();">增加</a>
				<a class="fkdj_botton_right" href="javascript:deletePatrolPositionCard()">删除</a>
			</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	//查询员工定位卡记录
	function queryPatrolPositionCard(){
	    querySubmit();
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
