<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>会议申请</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
	
	
	
	
$(document).ready(function(){
                		    easyAlert("提示", "会议时间冲突!", "warning");
                

				jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime <= value;
	        };
	        
	        jQuery.validator.methods.compareNowDate = function(value, element, param) {
				return value >= "${nowDate}";
	        };
				
				$("#addform").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					add_conferenceName:{
					required: true,
					maxlength:20
					},
					outName:{
					required: true,
					maxlength:90
					},
					_chk:{
					required: true
					},
					add_conferenceStartTime:{
						required:true,
						compareNowDate:true
					},
					add_conferenceEndTime:{
						required:true,
						compareDate: "#add_conferenceStartTime"
					},
					add_conferenceDetailService:{
						maxlength:255
					}
					
					},
					messages:{
						add_conferenceName:{
						required: "请输入会议名称",
					  	maxlength: "输入的会议名称过长"
						},
						outName:{
						required: "请选择申请人",
						maxlength: "申请人名称过长"
						},
						_chk:{
						required: "请选择一个套餐"
						},
						add_conferenceStartTime:{
					    required:"请填写开始时间",
		                compareNowDate: "开始时间必须不能早于当前时间"
						},
						add_conferenceEndTime:{
							required:"请填写结束时间",
							compareDate: "开始时间必须早于结束时间"
						},
						add_conferenceDetailService:{
							maxlength: "申请备注字符过长"
						}
					}
				});
			});    
			
			
				function saveData(){		
                                    if($("#_chk").length==0){
				    	alert("没有套餐记录，无法定会议室！");
				    	return;
				    }
				if (showRequest()) {
					openAdminConfirmWin(function(){
						$('#addform').submit();
					});					
				}
			}
			
			function showRequest(){
				 return $("#addform").valid();
			}	
</script>
	<script type="text/javascript">
	
	
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
		</script>
</head>

<body style="height: auto;background:#FFF;">
	<div class="main_border_01">
		<div class="main_border_02">申请会议</div>
	</div>
	<div class="main_conter" >
	 <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left"></div>
		</div>
	<form id="addform" name="addform" action="addOneConference.act" method="post">
	<input type="hidden" id="conferencePackageId" name="conferencePackageId" value="0"/>
		<div class="show_from" style="padding-top:10px;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><font color="red">*</font>会议名称</td>
					<td width="336px"><input name="add_conferenceName" type="text" style="width:320px" value="" /></td>
				
					<td><font color="red">*</font>会议申请人</td>
					<td width="336px"><input type="text" style="width:320px" name="outName" id="outName" readonly="readonly" value="${vm.USER_NAME}" />
					 <input type="hidden" name="userid" id="userid" value="${vm.USER_ID}" />
					</td>
					<td><img src="${ctx}/app/integrateservice/images/fkdj_pho.jpg" width="22" height="18" onclick="selectRespondents()" /></td>
				</tr>
				<tr>
					<td>申请人单位</td>
					<td width="336px">
                        <input type="text" style="width:320px" id="orgName" name="orgName" readonly="readonly" value="${vm.ORG_NAME}" />
                        <input  type="hidden" name="orgId" id="orgId" />
                    </td>
					<td >联系方式</td>
					<td width="336px"><input type="text" style="width:320px" name="phonespan" id="phonespan" readonly="readonly" value="${userExp.telNum}"/></td>
				</tr>
				<tr>
					<td><font color="red">*</font>会议时间</td>
					<td width="336px"><table width="100%" border="0" cellspacing="0" cellpadding="0" >
				 			<tr>
								<td><input type="text" class="fkdj_from_input" name="add_conferenceStartTime" id="add_conferenceStartTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" value="${add_conferenceStartTime}" /></td>
								<td class="fkdj_name_lkf">&nbsp;至</td>
								<td><input type="text" class="fkdj_from_input" name="add_conferenceEndTime" id="add_conferenceEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${nowDate}'})" value="${add_conferenceEndTime}"/></td>
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
				<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
			           <d:col style="width:5%" class="display_centeralign" title="选择">
				       <input type="radio" name="_chk" id="_chk" class="checkItem" value="${row.CONFERENCE_PACKAGE_ID}" onchange="selectPackage()"/>
			           </d:col>

						<d:col  class="display_centeralign" title="会议室名称" >
					    <a style="cursor:pointer;" onclick="javascript:viewConference('${row.CONFERENCE_PACKAGE_ID}')"> ${row.CONFERENCE_PACKAGE_NAME} </a>
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
				<td><textarea name="add_conferenceDetailService" id="add_conferenceDetailService" style="width:950px;height:200px;"></textarea></td>
				</tr>
				
				
				   
				
				</table>
				
							<div class="show_pop_bottom">
					<a style="float: left;cursor: pointer;"  onclick="javascript:saveData()">提交</a>　
					　

</div>
<div class="clear"></div>

			
	</form>			

</div>

</body>
</html>
