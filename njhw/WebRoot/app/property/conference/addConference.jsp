<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>会议申请</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/app/property/conference/js/addOneConference.js" type="text/javascript"></script>
	<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
				rel="stylesheet" type="text/css" />
			<script src="${ctx}/scripts/basic/jquery.js.gzip"
				type="text/javascript"></script>
			<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
				type="text/javascript"></script>
			<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
			<script type="text/javascript"
				src="${ctx}/app/portal/toolbar/showModel.js"></script>
			<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>	
	
	<script type="text/javascript">
	function viewConference(conferencePackageId){
				var url = "${ctx}/app/pro/showOnePackage.act?conferencePackageId="+conferencePackageId;
				openEasyWin("viewConference","会议室预定详情",url,"900","400",false);
			}
	
	//机构树
	function selectRespondents() {
window.parent.selectRespondents();

	}
	       
			
			function addOneConference(){
				$("#addform").submit();
				parent.closeAddConference();
			}
			
			function addConferenceCancel(){
				parent.closeAddConference();
			}
			
			function selectPackage(){
			var fdiId = $("input:radio[name='_chk']:checked").val();
			$("#conferencePackageId").val(fdiId);
			
			}
			function cancelAddDevice() {
		$('#addform').resetForm();
	}
		</script>
</head>

<body style="height: auto;background:#FFF;">
	<div class="main_border_01">
		<div class="main_border_02">申请会议1</div>
	</div>
	<div class="main_conter" >
	
		<div class="bgsgl_conter" style="border:0px">
		<div class="fkdj_lfrycx">
	<form id="addform" name="addform" action="addConference.act" method="post">
	<input type="hidden" id="conferencePackageId" name="conferencePackageId" value="0"/>
		<div class="show_from" style="padding-top:10px;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><font color="red">*</font>会议名称</td>
					<td width="336px"><input class="fkdj_from_input" name="add_conferenceName" type="text" style="width:320px" value="" /></td>
				
					<td><font color="red">*</font>会议申请人</td>
					<td width="336px">
					 	<input type="hidden" name="userid" id="userid" value="${vm.USER_ID}" />
					 	<input type="text" class="fkdj_from_input" style="width:320px" name="proposerName" id="proposerName" />
					</td>
					<td></td>
				</tr>
				<tr>
					<td>申请人单位</td>
					<td width="336px">
                        <input type="text" class="fkdj_from_input" style="width:320px" id="orgName" name="orgName" value="${vm.ORG_NAME}" />
                        <input  type="hidden" name="orgId" id="orgId" />
                    </td>
					<td>联系方式</td>
					<td width="336px"><input class="fkdj_from_input" type="text" style="width:320px" name="phonespan" id="phonespan" value="${userExp.telNum}"/></td>
				</tr>
				<tr>
					<td><font color="red">*</font>会议时间</td>
					<td width="336px"><table width="100%" border="0" cellspacing="0" cellpadding="0" >
				 			<tr>
								<td><input type="text" class="fkdj_from_input" style="width:144px" name="add_conferenceStartTime" id="add_conferenceStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" value="${add_conferenceStartTime}" /></td>
								<td class="fkdj_name_lkf">&nbsp;至</td>
								<td><input type="text" class="fkdj_from_input" style="width:144px" name="add_conferenceEndTime" id="add_conferenceEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" value="${add_conferenceEndTime}"/></td>
							</tr>
						</table>
					</td>				
				</tr>
				<tr >
				<td class="fkdj_name" style="text-align:left;"><font color="red">*</font><strong>会议室选择</strong></td>
 		               
				</tr>
				</table>
			</div>
			<div class="ovdbok_height">
				<table>
				<tr>
				<d:table name="pagepackage.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
			           <d:col style="width:5%" class="display_centeralign" title="选择">
				       <input type="radio" name="_chk" id="_chk" class="checkItem" value="${row.CONFERENCE_PACKAGE_ID}" onchange="selectPackage()"/>
			           </d:col>
			           	<d:col  class="display_centeralign" title="会议室名称" >
					    <a style="cursor:pointer;color:black;" onclick="javascript:viewConference('${row.CONFERENCE_PACKAGE_ID}')"> ${row.CONFERENCE_PACKAGE_NAME} </a>
				    	 </d:col>
			           <d:col style="width:10%" property="CONFERENCE_PACKAGE_LOCATION" class="display_centeralign"
				             title="会场地址" maxLength="10"/>
				       <d:col style="width:10%" property="CONFERENCE_PACKAGE_SEAT" class="display_centeralign"
				             title="座位" maxLength="10"/>
			           <d:col style="width:15%" property="CONFERENCE_PACKAGE_STYLE" class="display_leftalign"
				             title="会议室布局" maxLength="10"/>
				       <d:col style="width:15%" property="CONFERENCE_PACKAGE_FACILITY" class="display_centeralign"
				             title="其他设施" maxLength="10" />
			           <d:col style="width:10%" property="CONFERENCE_PACKAGE_PRICE" class="display_centeralign"
				             title="价目" maxLength="10"/>	
				       <d:col style="width:15%" property="CONFERENCE_PACKAGE_SERVICE" class="display_centeralign"
				             title="服务项目" maxLength="15"/>															
		               </d:table>
		               
				</tr>
			    </table>					
</div>
				
				<div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">备注</div>
		</div>
				<table>
			
				<tr>	
				<td><textarea name="add_conferenceDetailService" id="add_conferenceDetailService" style="width:920px;height:200px;"></textarea></td>
				</tr>
				
				
				   
				
				</table>
				
				<!-- 
				<div class="energy_fkdj_botton_ls">
					<a class="fkdj_botton_left"  onclick="javascript:saveData1()">提交</a>　
			<a class="fkdj_botton_right" href="javascript:cancelAddDevice()">取消</a>
</div>
				
				 -->
				 
				 
				 <div class="energy_fkdj_botton_ls">
					<a class="fkdj_botton_right"  onclick="javascript:saveData1()">提交</a>　
					<a class="fkdj_botton_left" style="cursor:pointer"  onclick="javascript:clearInput()">重置</a>　
					
</div>
				
				
				
				
							
<div class="clear"></div>
				   <div class="clear"></div>
		
			
	</form>	

</div>
</div>	
			

</div>

				<div >
				
					<div class="clear"></div>
				</div>
	<div id='companyWin' class='easyui-window' collapsible='false'
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<iframe id='companyIframe' name='companyIframe'
			style='width: 100%; height: 100%;' frameborder='0'></iframe>
	</div>
</body>
</html>
