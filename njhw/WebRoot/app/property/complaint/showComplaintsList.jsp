<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>投诉信息一览</title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		<%@ include file="/common/include/meta.jsp"%>
		
		<link href="${ctx}/app/property/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/property/css/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/property/css/block.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
			
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/property/block.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/property/calendarCompare.js" type="text/javascript" charset="UTF-8"></script>
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
	 		
	 		function viewLostFound(lostFoundId){
				var url = "${ctx}/app/pro/queryLostFoundById.act?lostFoundId="+lostFoundId;
				openEasyWin("orgInput1","查看失物详细信息",url,"900","600",false);
			}
			
			function addOneLostFoundPrePare(){
				var url = "${ctx}/app/pro/addOneLostFoundPrepare.act";
				openEasyWin("orgInput2","失物登记",url,"900","600",false);
			}
			
			function modifyOneLostFoundPrepare(lostFoundId){
				var url = "${ctx}/app/pro/modifyOneLostFoundPrepare.act?lostFoundId="+lostFoundId;
				openEasyWin("orgInput3","失物信息修改",url,"900","600",false);
			}
			
			function claimOneLostFoundPrepare(lostFoundId){
				var url = "${ctx}/app/pro/claimOneLostFoundPrepare.act?lostFoundId="+lostFoundId;
				openEasyWin("orgInput4","失物认领",url,"900","600",false);
			}
		</script>
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp" />
		
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						投诉信息一览
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				    <!--此处写页面的内容 -->
				<div class="fkdj_lfrycx">
 				<div class="fkdj_from">
 				<div class="bgsgl_right_list_border">
		  			<div class="bgsgl_right_list_left">投诉信息查询</div>
				</div>
 					<form id="queryForm" name="queryForm" action="showLostFoundsList.act" method="post" autocomplete="off">
 						<table width="100%" border="0" cellspacing="0" cellpadding="0">
 							<tr>
 								<td	class="fkdj_name">
 									标题
 								</td>
 								<td><input id="list_lostFoundTitle" name="list_lostFoundTitle" class="fkdj_from_input" type="text"></input></td>
 								<td class="fkdj_name_two">
 									投诉人
 								</td>
 								<td><input id="list_lostFoundThingName" name="list_lostFoundThingName" class="fkdj_from_input" type="text"></input></td>
 								<td class="fkdj_name_two">
 									投诉状态
 								</td>
 								<td>
 									<select name="list_lostFoundState" class="fkdj_select" id="list_lostFoundState" name="list_lostFoundState">
								        <option id="list_hasClaimed" value="1">未处理</option>
								        <option id="list_noClaimed" value="0">已处理</option>
								       <option id="list_noClaimed" value="0">已结束</option>
								        
							      	</select>
 								</td>						
 							</tr>
 							<tr>
						    	<td class="fkdj_name">投诉时间</td>
						    	<td><table border="0" cellspacing="0" cellpadding="0">
							      <tr>
							        <td><input type="text" class="fkdj_from_input" name="list_lostFoundIntime" id="list_lostFoundIntime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${list_lostFoundIntime}" /></td>
							        <td class="fkdj_name_lkf">至</td>
							        <td><input type="text" class="fkdj_from_input" name="list_lostFoundOverTime" id="list_lostFoundOverTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${list_lostFoundOverTime}"/></td>
							      </tr>
						    		</table>
						    	</td>
						    	
							    <td class="fkdj_name"><br></td>
    							<td>&nbsp;</td>
    							<td class="fkdj_name"><br></td>
    							<td>&nbsp;</td>
						    </tr>
 						</table>
 						<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
	 	<div class="fkdj_ch_tabl_es">
			<a class="display_centeralign"
				onClick="javascript:querySubmit()"
				onmousemove="this.style.color='#ffae00'"
				onmouseout="this.style.color='#8090b4'">查&nbsp;&nbsp;&nbsp;&nbsp;询 </a>
	
		</div>
 						
 	<h:page id="list_panel" width="100%" title="结果列表">
 		<d:table name="page.result" id="row" uid="row" export="true"
									cellspacing="0" cellpadding="0" class="table">
			<d:col style="width:45" class="display_centeralign" title="全选">
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.LOST_FOUND_ID}" />
			</d:col>
			<d:col property="LOST_FOUND_ID" class="display_centeralign"
				title="失物编号" />
			<d:col property="LOST_FOUND_TITLE" class="display_centeralign"
				title="失物标题" />
			<d:col property="LOST_FOUND_THINGNAME" class="display_centeralign"
				title="失物名称" />
			<d:col property="LOST_FOUND_LOCATION" class="display_centeralign"
				title="捡到失物地点" />
			<d:col property="LOST_FOUND_PICKUSER" class="display_centeralign"
				title="失物上交者" />
			<d:col property="LOST_FOUND_KEEPER" class="display_centeralign"
				title="失物保管人" />
			<d:col property="LOST_FOUND_INTIME" class="display_centeralign"
				title="登记时间" />													
			<d:col class="display_centeralign" title="认领状态">
				<c:if test="${row.LOST_FOUND_STATE == 0}">
					<span class="display_centeralign">未认领</span>
				</c:if>
				<c:if test="${row.LOST_FOUND_STATE == 1}">
					<span class="display_centeralign">已认领</span>
				</c:if>									
			</d:col>
			<d:col class="display_centeralign" title="操作">
										<span class="display_centeralign"
												onclick="javascript:viewLostFound('${row.LOST_FOUND_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'">
												[查看]&nbsp;
										</span>
										<c:if test="${row.LOST_FOUND_STATE == 0}">
											<span class="display_centeralign"
												onclick="javascript:modifyOneLostFoundPrepare('${row.LOST_FOUND_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'">&nbsp;[修改]&nbsp;
											</span>
										</c:if>
										<c:if test="${row.LOST_FOUND_STATE == 0}">
											<span class="display_centeralign"
												onClick="javascript:claimOneLostFoundPrepare('${row.LOST_FOUND_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'">&nbsp;[认领]&nbsp; 
											</span>
										</c:if>																			
									</d:col>						
		</d:table>	
	</h:page>
	</form>
			<div class="fkdj_botton_ls">
				<a class="fkdj_botton_left" href="javascript:addOneLostFoundPrePare()">增加</a>
				<a class="fkdj_botton_left" href="#"
					onclick="suspendConference('${row.LOST_FOUND_ID }')">中止</a>
			</div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
		
	</div>
</div>
	</body>

</html>