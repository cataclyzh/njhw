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
		<title>查看巡查路线</title>
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
				<script type="text/javascript">
		    document.domain = "njnet.gov.cn";
		    var device;
		    function initMapByFloorId(){
		        var patrolNodes = document.getElementById("patrolNodes");
		        var floorId = document.getElementById("floorId");
		        try {
	                    var DeviceDefaultOption = {
				            IsFrameMap : false,
				            OnSaved : function(Points) {
				                patrolNodes.value = Points;
				            }
			            }

			            device = window.frames["threeDimensional"].Init3D("Device",
					    DeviceDefaultOption);
					    device.MenuContent({floorNavigate: true});
			            device.ShowPatrolPoint(patrolNodes.value);
		        }catch (e) {
			         initMapByFloorId();
		        }
	        }
				</script>
	</head>
	<body style="background:#fff;" onload="initMapByFloorId()">
	<div class="khfw_wygl">
	    <div class="bgsgl_border_left">
		    <div class="bgsgl_border_rig_ht">查看巡查路线</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
			<form action="viewPatrolLine.act" method="post" id="viewPatrolLineForm" name="viewPatrolLineForm" target="main_frame_right">
			<s:hidden id="patrolLineId" name="patrolLineId"></s:hidden>
			<s:hidden id="patrolNodes" name="patrolNodes"></s:hidden>
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
								路线名称
							</td>
							<td width="35%">
								<s:textfield cssClass="show_inupt" name="patrolLineName" id="patrolLineName" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								路线描述
							</td>
							<td colspan="3">
								<textarea name="patrolLineDesc" style="width:98%;height:80px;overflow-y:auto"
									id="patrolLineDesc" cols="90" class="show_textarea" readonly="readonly"
									rows="15">${patrolLineDesc }</textarea>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								巡查点查看
							</td>
							<td colspan="3">
								<div style="width:98%;height:300px">
								<iframe id="threeDimensional" name="threeDimensional"
							         src="http://3d.njnet.gov.cn/1/index.htm" width="100%" height="100%"></iframe>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</form>
			</div>
			</div>
		</div>
</body>
</html>
