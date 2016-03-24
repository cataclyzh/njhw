<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>发布失物招领</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />

	
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
    <script src="${ctx}/app/property/lostFound/js/addOneLostFound.js" type="text/javascript"></script>
	
	<script type="text/javascript">
			
			function addOneLostFound(){
				$("#addform").submit();
			}
			
			function addLostFoundCancel(){
				$("#addform").resetForm();
			}
		</script>
</head>

<body style="background:#fff;">
<div class="khfw_wygl">
	<div class="bgsgl_border_left">
				<div class="bgsgl_border_rig_ht">
					发布失物招领
				</div>
			</div>
		
				<div class="bgsgl_conter" style="">
		<div class="fkdj_lfrycx">
	<form id="addform" name="addform" action="addOneLostFound.act" method="post">
		<div class="winzard_show_from">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="winzard_show_from_right" width="12%"><font color="red">*</font>标题</td>
					<td width="20%"><input id="add_lostFoundTitle" name="add_lostFoundTitle" type="text" class="fkdj_from_input" value="" /></td>
					<td class="winzard_show_from_right" width="19%"><font color="red">*</font>失物名称</td>
					<td width="35%"><input id="add_lostFoundThingName" name="add_lostFoundThingName" type="text" class="fkdj_from_input" value="" /></td>
				</tr>
				<tr>
					
					<td class="winzard_show_from_right" width="19%"><font color="red">*</font>失物上交者</td>
					<td><input id="add_lostFoundPickUser" name="add_lostFoundPickUser" type="text" class="fkdj_from_input" value="" /></td>
					<td class="winzard_show_from_right" width="12%"><font color="red">*</font>上交者单位</td>
					<td><input id="add_lostFoundPickUserOrg" name="add_lostFoundPickUserOrg" type="text" class="fkdj_from_input" value="" /></td>
				</tr>
				<tr>
					<td class="winzard_show_from_right" width="12%"><font color="red">*</font>捡到地点</td>
					<td><input id="add_lostFoundLocation" name="add_lostFoundLocation" type="text" class="fkdj_from_input" value="" /></td>
					<td class="winzard_show_from_right" width="12%"><font color="red">*</font>失物登记者</td>
					<td><input id="add_lostFoundKeeper" name="add_lostFoundKeeper" type="text" class="fkdj_from_input" value="" /></td>
					
				</tr>
				<tr>
					<td class="winzard_show_from_right" class="fkdj_name"><font color="red">*</font>登记时间</td>
					<td><table border="0" cellspacing="0" cellpadding="0">
				 			<tr>
								<td><input type="text" class="fkdj_from_input" name="add_lostFoundIntime" id="add_lostFoundIntime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'${nowDate}'})" value="${add_lostFoundIntime}" /></td>								
							</tr>
						</table>
					</td>
					<td class="fkdj_name"><br/></td>
    				<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="winzard_show_from_right" valign="top">详细描述</td>
					<td colspan="3"><textarea id="add_lostFoundDetail" name="add_lostFoundDetail" class="input_textarea" cols="80" rows="10"></textarea></td>
				</tr>
			</table>	
			
			<div class="clear"></div>
			
			<!-- 
			<div class="energy_fkdj_botton_ls">
			<a class="fkdj_botton_left" onclick="saveData()">确定</a>
			<a class="fkdj_botton_right" onclick="javascript:addLostFoundCancel()">取消</a>
		</div>
			
			 -->
			
			<div class="energy_fkdj_botton_ls">
			<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			<a class="fkdj_botton_left" style="cursor:pointer" onclick="javascript:clearInput()">重置</a>
		</div>
			
		
							
		</div>
		
		
	</form>
	</div>		
		</div>			
</div>
		

	</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>