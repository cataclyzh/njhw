
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>车辆进出管理</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
			
			function lookParking(parkingInfoId){
				var url = "${ctx}/app/pro/lookParking.act?parkingInfoId="+parkingInfoId;
				openEasyWin("orgInput","查看车位信息",url,"750","500",false);
			}
			function insertParking(){
			    var url = "${ctx}/app/pro/insertParkingInfo.act";
			    openEasyWin("orgInput","增加车位信息",url,"750","500",false);
			    
			}
			
			
			function modifyParking(parkingInfoId,orgId){
			    var url = "${ctx}/app/pro/modifyParking.act?parkingInfoId="+parkingInfoId+"&orgId="+orgId;
			    openEasyWin("orgInput","修改车位信息",url,"750","300",false);
			    
			}
			
			function configParking(){
			    var url = "${ctx}/app/pro/configParking.act";
			    openEasyWin("orgInput","配置车位总数",url,"750","500",false);
			    
			}
			
			function closeWin(){
			    closeEasyWin('orgInput');
			}
			
			function deleteData(parkingInfoId){		       
				   easyConfirm('提示', '确定置零？', function(r){
					if (r){
						window.location.href="${ctx}/app/pro/deleteParking.act?parkingInfoId=" + parkingInfoId;
					}
				});			    
			}	
		</script>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	</head>
	<body style="margin: 0; padding: 0; background: white;">
		<div class="main_border_01">
			<div class="main_border_02">
				查看车位分配
			</div>
		</div>
		<div class="main_conter" style="padding: 15px;">

			<form id="queryForm" name="queryForm" action="parkingList.act" method="post" autocomplete="off">
				<div class="fkdj_lfrycx">
					<div class="fkdj_from">

						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fkdj_name">
									单位名称
								</td>
								<td>
									<select id="parkingInfoOrgId" name="parkingInfoOrgId">
										<c:forEach items="${orgList}" var="org">
											<option value="${org.orgId }"
												<c:if test='${parkingInfoOrgId == org.orgId}'>selected="selected"</c:if>>
												${org.name }
											</option>
										</c:forEach>
									</select>
								</td>

								<td class="fkdj_name">
									&nbsp;
								</td>

								<td>
									<a class="property_query_button" href="javascript:void(0);" onmousemove="this.style.background='FFC600'" onmouseout="this.style.background = '#8090b4'" onclick="querySubmit()">查询 </a>
								</td>
							</tr>
						</table>
						<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
					</div>
				</div>
				<h:page id="list_panel" width="100%" title="结果列表">
					<d:table name="page.result" id="row" uid="row" export="false" cellspacing="0" cellpadding="0" class="table">
						<d:col property="ORG_NAME" class="display_centeralign" title="单位名称" />
						<d:col property="RES_NAME" class="display_centeralign" title="车位数" />
						<d:col class="display_centeralign" title="操作">
							<span class="display_centeralign" onclick="modifyParking('${row.EOR_ID}','${row.ORG_ID}')" onmousemove="this.style.color='#ffae00';this.style.cursor='pointer';" onmouseout="this.style.color='#8090b4';this.style.cursor='pointer';">[修改]
							</span>
					    </d:col>
					</d:table>
				</h:page>
			</form>
		</div>
	</body>
</html>