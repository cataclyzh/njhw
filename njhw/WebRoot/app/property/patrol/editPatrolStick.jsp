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
		<title>编辑员工巡更棒</title>
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
		
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		
	
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

					<script src="${ctx}/app/property/patrol/js/editPatrolStick.js" type="text/javascript"></script>
			

		
	</head>
	<body style="background:#fff;">
	<div class="khfw_wygl">
	    <div class="bgsgl_border_left">
		    <div class="bgsgl_border_rig_ht">编辑员工巡更棒</div>
		</div>
		<div class="bgsgl_conter">
		<div class="fkdj_lfrycx">
			<form action="editPatrolStick.act" method="post" id="editPatrolStickForm" name="editPatrolStickForm" target="main_frame_right">
			<s:hidden id="patrolStickUserId" name="patrolStickUserId"></s:hidden>
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
							  <font color="red">*</font>
								员工姓名
							</td>
							<td width="35%">
								<s:textfield name="userName" id="userName" cssClass="fkdj_from_input" readonly="true"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right" valign="top">
								<font color="red">*</font>
								巡更棒号
							</td>
							<td colspan="3">
								 <select name="sId">
							    	<option value="">请选择</option>
								    <c:forEach items="${grPatrolStickList}" var="grPatrolStick">
										<c:choose>
								    		<c:when test="${grPatrolStick.sId eq patrolStickId}">
												<option value="${grPatrolStick.sId }" selected="selected">${grPatrolStick.sName }</option>							    	
								    		</c:when>
								    		<c:otherwise>
												<option value="${grPatrolStick.sId }">${grPatrolStick.sName }</option>								    		
								    		</c:otherwise>
								    	</c:choose>							    	
								    </c:forEach>
							    </select>
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
