<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>失物认领</title>	
	<%@ include file="/common/include/meta.jsp" %>
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
	<script src="${ctx}/app/property/lostFound/js/claimOneLostFound.js" type="text/javascript"></script>
		
	<script type="text/javascript">
			
			function claimOneLostFound(){
				$("#claimform").submit();
				closeEasyWin("claimOneLostFoundPrepare");
			}
			
			function claimLostFoundCancel(){
				closeEasyWin("claimOneLostFoundPrepare");
			}
		</script>
</head>

<body style="background: #fff;">
<div class="main1_main2_right_khfw">
<div class="khfw_wygl" >
	<form id="claimform" name="claimform" action="claimOneLostFound.act" method="post" target="main_frame_right">
		<div class="show_from">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr style="display: none;">
	    <td width="12%">失物编号</td>
	    	<td width="35%"><input name="claim_lostFoundId" id="claim_lostFoundId"  class="show_inupt" type="text" value="${view_lostfound.lostFoundId}" readonly="readonly"/>
	    </td>   
	    
	  </tr>
  
    <tr>
    	<td width="12%">标题</td>
    		<td width="35%"><input name="claim_lostFoundTitle" id="claim_lostFoundTitle"  class="show_inupt" type="text" value="${view_lostfound.lostFoundTitle}" readonly="readonly" disabled="disabled"/>
   		</td>
   		
	    <td width="19%">失物名称</td>
	    	<td width="35%"><input style="width:86%" name="claim_lostFoundThingName" id="claim_lostFoundThingName"  class="show_inupt" type="text" value="${view_lostfound.lostFoundThingName}" readonly="readonly" disabled="disabled"/>
	    </td>	     
   </tr>
   <tr>
   		<td>失物上交者</td>
	    	<td><input name="claim_lostFoundPickUser" id="claim_lostFoundPickUser"  class="show_inupt" type="text" value="${view_lostfound.lostFoundPickUser}" readonly="readonly" disabled="disabled"/>
	    </td>
   		<td>上交者单位</td>
   			 <td><input style="width:86%" name="claim_lostFoundPickUserOrg" id="claim_lostFoundPickUserOrg"  class="show_inupt" type="text" value="${view_lostfound.lostFoundPickUserOrg}" readonly="readonly" disabled="disabled"/>
  		 </td>  		 
   </tr>
  
    <tr>
    	 <td>捡到地点</td>
	    	<td><input name="claim_lostFoundLocation" id="claim_lostFoundLocation"  class="show_inupt" type="text" value="${view_lostfound.lostFoundLocation}" readonly="readonly" disabled="disabled"/>
	    </td>
	    
	     <td>失物登记者</td>
   			 <td><input style="width:86%" name="claim_lostFoundKeeper" id="claim_lostFoundKeeper"  class="show_inupt" type="text" value="${view_lostfound.lostFoundKeeper}" readonly="readonly" disabled="disabled"/>
  		 </td>   
    </tr>
    
    <tr>
   		<td class="fkdj_name">登记时间</td>
		<td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><input type="text" class="show_inupt" name="claim_lostFoundIntime" id="claim_lostFoundIntime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
							value="<f:formatDate value="${view_lostfound.lostFoundIntime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" disabled="disabled"/></td>   
    		</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td valign="top">详细描述</td>
		<td colspan="3"><textarea id="claim_lostFoundDetail" name="claim_lostFoundDetail" style="height:80px;" class="show_textarea" cols="80" rows="15" readonly="readonly" disabled="disabled">${view_lostfound.lostFoundDetail }</textarea></td>
	</tr>
</table>					
</div>
		
		<div class="show_from">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
				<tr>
					<td width="13%"><font color="red">*</font>失物认领人</td>
					<td  style="text-align: left;padding-left:10px;"><input name="claim_lostFoundLostUser" id="claim_lostFoundLostUser" class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundLostUser}"/>
					</td>

					<td width="19%"><font color="red">*</font>联系方式</td>
					<td  style="text-align: left;padding-left:10px;"><input name="claim_lostFoundPhone" id="claim_lostFoundPhone" class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundPhone}"/>
					</td>
				</tr>
				
				<tr>
					<td width="13%"><font color="red">*</font>认领人单位</td>
					<td  style="text-align: left;padding-left:10px;"><input name="claim_lostFoundLostUserOrg" id="claim_lostFoundLostUserOrg" class="fkdj_from_input" type="text" value="${view_lostfound.lostFoundLostUserOrg}"/>
					</td>

					<td width="19%"><font color="red">*</font>认领时间</td>
					<td  style="text-align: left;padding-left:10px;">
					    <input type="text" class="fkdj_from_input" name="claim_lostFoundOverTime" id="claim_lostFoundOverTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'${nowDate}'})" value="${view_lostfound.lostFoundOverTime}"/>
					</td>
				</tr>
								
			</table>							
		</div>
	</form>					
</div>
</div>


<!-- 
<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" onclick="saveData()">认领</a>
				<a class="fkdj_botton_right" onclick="javascript:claimLostFoundCancel()">取消</a>
			</div>
 -->
<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_right" onclick="saveData()">认领</a>
			</div>
		

</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>