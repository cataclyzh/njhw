﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>会务服务一览</title>
		<%@ include file="/common/include/meta.jsp"%>
		
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
			<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
	

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
			function selectConferencesList(){
	 			$("#queryForm").submit();
	 		}
	 		
	 			function cancelConference(conferenceId){
	 			easyConfirm('提示', '确定取消该会议吗？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/cancelConferenceListStateById.act?conferenceId=" + conferenceId;
				}
			});
	 			
	 		}
	 		
	 		function completeConference(conferenceId){
	 			easyConfirm('提示', '确定将该会议设为完成状态？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/updateConferenceStateById.act?conferenceId=" + conferenceId;
				}
			});
	 			
	 		}
	 		function confirmConference(conferenceId){
	 			easyConfirm('提示', '将该会议设为已确认状态？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/confirmConferenceStateById.act?conferenceId=" + conferenceId;
				}
			});
	 			
	 		}
	 		
	 		function viewConference(conferenceId){
				var url = "${ctx}/app/pro/queryConferenceById.act?conferenceId="+conferenceId;
				openEasyWin("viewConference","查看会务详细信息",url,"900","600",false);
			}
			
			function addOnePrepare(){
				var url = "${ctx}/app/pro/addOnePrepare.act";
				openEasyWin("addOneConferencePrepare","会议申请",url,"900","600",false);
			}
			
			function modifyOnePrepare(conferenceId){
				var url = "${ctx}/app/pro/modifyPrepare.act?conferenceId="+conferenceId;
				openEasyWin("modifyOneConferencePrepare","会议修改",url,"900","600",false);
			}
			
			function valueOnePrepare(conferenceId){
				var url = "${ctx}/app/pro/valueOnePrepare.act?conferenceId="+conferenceId;
				openEasyWin("valueOneConferencePrepare","会议服务满意度评价",url,"900","600",false);
			}
			
			function packageManage(){
			    window.location.href="${ctx}/app/pro/showConferencePackagesList.act";
			}
			
			$(document).ready(function(){
				jQuery.validator.methods.compareDate = function(value, element, param) {
			        var beginTime = jQuery(param).val();
			        if(value!=""){
			        return beginTime < value;
			        }else {
			        return true;
			        }
			    };  
							$("#queryForm").validate({		// 为inputForm注册validate函数
								meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
								errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
								onsubmit: true,
								rules: {
						
							list_conferenceEndTime:{
								compareDate: "#list_conferenceStartTime"
							}
								},
								messages:{
									list_conferenceEndTime:{
										compareDate: "结束时间必须晚于开始时间"
									}
								}
							});
			});    
			
			function saveData(){		
				if (showRequest()) {
                  querySubmit();
				}
			}
			
			function showRequest(){
				 return $("#queryForm").valid();
			}
		</script>
	</head>
	<body style="">
			<div class="main_index">
				<c:if test="${param.type eq 'A'}">
<jsp:include page="/app/integrateservice/headerWy.jsp">
	<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
</jsp:include>
</c:if>
<c:if test="${param.type eq 'B'}">
<jsp:include page="/app/integrateservice/headerWy.jsp">
	<jsp:param value="/app/pro/propertyIndex7.act" name="gotoParentPath"/>
</jsp:include>
</c:if>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div class="fkdj_index">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
					   查看会议
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				<div class="">
 				<div class="fkdj_from">
 					<form id="queryForm" name="queryForm" action="showConferencesList1.act" method="post">
 					<h:panel id="query_panel" width="100%" title="查询条件">
 						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hide_table" id="form_table">
 							<tr>
 								<td	class="fkdj_name">
 									会议名称
 								</td>
 								<td><s:textfield name="list_conferenceName" id="list_conferenceName" cssClass="fkdj_from_input"/></td>
 								<td class="fkdj_name">
 									申请人
 								</td>
 								<td><s:textfield name="list_conferenceUserName" id="list_conferenceUserName" cssClass="fkdj_from_input"/></td>				
 							
 						 	<td class="fkdj_name">状态</td>
							    <td>
							    	<select name="list_conferenceState" class="fkdj_select" id="list_conferenceState">							    						
							      		<option id="list_allState" value="all" <c:if test="${list_conferenceState=='all'}">selected="selected"</c:if>>全部</option>
								        <option id="list_unstart" value="0" <c:if test="${list_conferenceState=='0'}">selected="selected"</c:if>>申请中</option>
								        <option id="list_complete" value="1" <c:if test="${list_conferenceState=='1'}">selected="selected"</c:if>>已完成</option>
								        <option id="list_value" value="2" <c:if test="${list_conferenceState=='2'}">selected="selected"</c:if>>已评价</option>
								        <option id="list_cancel" value="3" <c:if test="${list_conferenceState=='3'}">selected="selected"</c:if>>已取消</option>
								        <option id="list_confirm" value="4" <c:if test="${list_conferenceState=='4'}">selected="selected"</c:if>>已确认</option>
							      	</select>
							    </td>
 							
 							</tr>
 							<tr>
						    	<td class="fkdj_name">会议时间</td>
						    	<td><table border="0" cellspacing="0" cellpadding="0">
							      <tr>
							        <td>
							        <s:textfield name="list_conferenceStartTime" id="list_conferenceStartTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>
							        <td class="fkdj_name_lkf">至</td>
							        <td>
							        <s:textfield name="list_conferenceEndTime" id="list_conferenceEndTime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/></td>							
							      </tr>
						    		</table>
						    	</td>
						   
							    	<td class="fkdj_name">会议室名称</td>
    							<td>
    								
    								<select name="list_conferencePackageId" class="fkdj_select" id="list_conferencePackageId" >
    								
							    		<option id="list_allPackage" value="all">全部</option>
							    		<c:forEach items="${view_packages}" var="conferencePackage">
							    			<option id="list_package" value="${conferencePackage.conferencePackageId }" <c:if test="${conferencePackage.conferencePackageId==optionConferenceId}">selected="selected"</c:if>>${conferencePackage.conferencePackageName }</option>
							    		</c:forEach>
							      	</select>
    							
    							</td>
							   <td class="fkdj_name">
							   &nbsp;
							   </td> 
    							
 		                    <td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="saveData()">查询 </a>
										</td>
    							
						    </tr>
 						
 				
	 	
		
		</table>
		</h:panel>
	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
			
 	<h:page id="list_panel" width="100%" title="结果列表">
 		<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
		
				<d:col  class="display_centeralign" title="会议名称" >
					    <a style="cursor:pointer;color:black;" onclick="javascript:viewConference('${row.CONFERENCE_ID}')"> ${row.CONFERENCE_NAME} </a>
				    	 </d:col>
				
				
			<d:col property="CONFERENCE_USERNAME" class="display_centeralign"
				title="会议申请人" maxLength="15"/>
			<d:col property="CONFERENCE_STARTTIME" class="display_centeralign"
				title="开始时间" maxLength="15"/>
			<d:col property="CONFERENCE_ENDTIME" class="display_centeralign"
				title="结束时间" maxLength="15"/>
			<d:col property="CONFERENCE_USERPHONE" class="display_centeralign"
				title="联系方式" maxLength="15"/>
			<d:col property="CONFERENCE_PACKAGE_NAME" class="display_centeralign"
				title="会议室名称" maxLength="15"/>
			<d:col class="display_centeralign" title="会议状态">
				<c:if test="${row.CONFERENCE_STATE == 0}">
					<span class="display_centeralign">申请中</span>
				</c:if>
				<c:if test="${row.CONFERENCE_STATE == 1}">
					<span class="display_centeralign">已完成</span>
				</c:if>	
				<c:if test="${row.CONFERENCE_STATE == 2}">
					<span class="display_centeralign">已评价</span>
				</c:if>
				<c:if test="${row.CONFERENCE_STATE == 3}">
					<span class="display_centeralign">已取消</span>
				</c:if>
				<c:if test="${row.CONFERENCE_STATE == 4}">
					<span class="display_centeralign">已确认</span>
				</c:if>							
			</d:col>
			<d:col class="display_centeralign" title="操作">
										
											<span class="display_centeralign"
												onclick="javascript:viewConference('${row.CONFERENCE_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:pointer"
												>&nbsp;[详细]&nbsp;
											</span>
										
									</d:col>						
		</d:table>		
	</h:page>
	</form>	
	 <!-- 
			<div class="fkdj_botton_ls">
				<a class="fkdj_botton_left" href="javascript:addOnePrepare()">申请</a>
				<a class="fkdj_botton_left" href="javascript:void(0);"
					onclick="packageManage()">套餐管理</a>
			</div>
	 -->
				</div>
			</div>
		</div>
		
	</div>
	
		</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
	</body>
</html>