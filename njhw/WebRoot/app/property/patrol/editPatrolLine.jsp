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
		<title>编辑巡查路线</title>
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
		<script src="${ctx}/app/property/patrol/js/editPatrolLine.js" type="text/javascript"></script>

		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
	
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

		
	</head>
	<body style="background:#fff;" onload="initMapByNodes()">
	<div class="khfw_wygl">
	    <div class="bgsgl_border_left">
		    <div class="bgsgl_border_rig_ht">编辑巡查路线</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
			<form action="editPatrolLine.act" method="post" id="editPatrolLineForm" name="editPatrolLineForm" target="main_frame_right">
			<s:hidden id="patrolLineId" name="patrolLineId"></s:hidden>
			<s:hidden id="patrolNodes" name="patrolNodes"></s:hidden>
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
							  <font color="red">*</font>
								路线名称
							</td>
							<td width="35%">
								<s:textfield name="patrolLineName" id="patrolLineName" cssClass="fkdj_from_input"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								路线描述
							</td>
							<td colspan="3">
								<textarea name="patrolLineDesc" style="width:98%;height:80px;overflow-y:auto"
									id="patrolLineDesc" cols="90"
									rows="15">${patrolLineDesc }</textarea>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								路线制定<br/>
								        <!-- 
										<label>
											楼层选择
										</label>
										<br />
										<select id="floorId" name="floorId" onchange="initMapByFloorId()">
											<option value="-2">
												负二楼
											</option>
											<option value="-1">
												负一楼
											</option>
											<option value="56">
												一楼
											</option>
											<option value="55">
												二楼
											</option>
											<option value="27">
												三楼
											</option>
											<option value="31">
												四楼
											</option>
											<option value="32">
												五楼
											</option>
											<option value="35">
												六楼
											</option>
											<option value="37">
												七楼
											</option>
											<option value="38">
												八楼
											</option>
											<option value="39">
												九楼
											</option>
											<option value="40">
												十楼
											</option>
											<option value="41">
												十一楼
											</option>
											<option value="42">
												十二楼
											</option>
											<option value="43">
												十三楼
											</option>
											<option value="44">
												十四楼
											</option>
											<option value="45">
												十五楼
											</option>
											<option value="46">
												十六楼
											</option>
											<option value="47">
												十七楼
											</option>
											<option value="48">
												十八楼
											</option>
											<option value="49">
												十九楼
											</option>
										</select>
										 -->
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
				<div class="energy_fkdj_botton_ls" style="margin-right:10px;">
				    <a class="fkdj_botton_right" href="javascript:void(0);" onclick="saveData()">提交</a>　
				</div>				
			</form>
			</div>
			</div>
		</div>
</body>
</html>
