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
		<title>物业报修--回访页面</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		
	</head>
	<body style="background:#fff;">
		<div class="khfw_wygl">
			<form action="saveEvaluateConference.act" id="evaluateForm" method="post" target="main_frame_right">
				<div class="winzard_show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
						    <td class="winzard_show_from_right" width="12%">
								会议名称
							</td>
							<td width="35%">
								<input name="repairId" id="repairId" type="text"
									class="show_inupt" value="${conference.CONFERENCE_NAME}"
									readonly="readonly"/>
							</td>
							<td class="winzard_show_from_right" width="12%">
								会议室
							</td>
							<td width="35%">
								<input name="deviceName" style="width:86%;" id="deviceName" type="text"
									class="show_inupt" value="${conference.CONFERENCE_PACKAGE_NAME}"
									readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								开始时间
							</td>
							<td width="34%">
								<input name="reportUserName" id="reportUserName" type="text"
									class="show_inupt" value="${conference.CONFERENCE_STARTTIME}"
									readonly="readonly"/>
							</td>
							<td class="winzard_show_from_right">
								结束时间
							</td>
							<td width="34%">
								<input name="reportUserTel" id="reportUserTel" type="text"
									class="show_inupt" style="width:86%;" value="${conference.CONFERENCE_ENDTIME}"
									readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								会议申请人
							</td>
							<td width="35%">
								<input name="reportUserName" id="reportUserName" type="text"
									class="show_inupt" value="${conference.CONFERENCE_USERNAME}"
									readonly="readonly"/>
							</td>
							<td class="winzard_show_from_right">
								
							</td>
							<td width="34%">
							    <input name="repairUserName" id="repairUserName" type="text"
									class="show_inupt" style="width:86%;" value="" readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
								会议明细
							</td>
							<td colspan="3">
							    <div class="text_right_move">
									<textarea name="completeRepairReceipt" id="completeRepairReceipt"
										class="show_textarea" cols="80" rows="5" style="height:80px;overflow-y:auto" readonly="readonly">${conference.CONFERENCE_DETAIL_SERVICE}</textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td class="winzard_show_from_right">
							<font color="red">*</font>	满意程度
							</td>
							<td colspan="3">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<input type="radio" name="conferenceSatisfy"
												id="conferenceSatisfy" value="非常满意" />
											非常满意
										</td>
										<td>
											<input type="radio" name="conferenceSatisfy"
												id="conferenceSatisfy" value="满意" />
											满意
										</td>
										<td>
											<input type="radio" name="conferenceSatisfy"
												id="conferenceSatisfy" value="一般" />
											一般
										</td>
										<td>
											<input type="radio" name="conferenceSatisfy"
												id="conferenceSatisfy" value="不满意" />
											不满意
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- 
						<tr>
							<td class="winzard_show_from_right" valign="top">
								客户评价
							</td>
							<td colspan="3">
								<textarea name="repairEvaluate" id="repairEvaluate"
								cols="91" rows="5" style="overflow-y:auto"></textarea>
							</td>
						</tr>
						 -->
					</table>
				</div>
				<input type="hidden" value="${conference.CONFERENCE_ID}" id="conferenceId" name="conferenceId" />
			</form>
		</div>
		
		<!-- 
		<div id="buttonbox_evaluate" class="allbox_back">
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_left" onclick="saveData()">确定</a>
				<a class="fkdj_botton_right" href="javascript:cancelEvaluateRepair()">取消</a>
			</div>
		</div>
		 -->
		<div id="buttonbox_evaluate" class="allbox_back">
			<div class="energy_fkdj_botton_ls">
				<a class="fkdj_botton_right" onclick="saveData()">确定</a>
			</div>
		</div>
		
		
		
	</body>
	<script type="text/javascript">
	/*
	    function evaluateRepair(){
	        $('#evaluateRepairForm').submit();
		    closeEasyWin("evaluateRepair");
	    }
	    
	    function cancelEvaluateRepair(){
	        $('#evaluateRepairForm').resetForm();
	    }
	    */
	    
	    $(document).ready(function(){
			$("#evaluateForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
			
				rules: {
					conferenceSatisfy:{
				    	required:true
				    }
				},
				messages:{					
					conferenceSatisfy:{
				    	required:"请选择满意程度"
					}
				},
				 /* 重写错误显示消息方法,以alert方式弹出错误消息 */  
		        showErrors: function(errorMap, errorList) {  
		            var msg = "";  
		            $.each( errorList, function(i,v){  
		            	msg += (v.message+"\r\n");  
		            });  
		            if(msg!="") alert(msg);  
		        },  
		        /* 失去焦点时不验证 */   
		        onfocusout: false 
			});
		});    
		
		function saveData(){		
			if (showRequest()) {
				$("#evaluateForm").submit();
			    closeEasyWin("evaluateConference");
			}
		}
		
		function showRequest(){
			 return $("#evaluateForm").valid();
		}
	</script>
</html>
