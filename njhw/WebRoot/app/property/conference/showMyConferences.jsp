<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		<script src="${ctx}/app/property/conference/js/Conference.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			function selectConferencesList(){
	 			$("#queryForm").submit();
	 		}
	 		
	 		function cancelConference(conferenceId){
	 			easyConfirm('提示', '确定取消该会议吗？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/cancelConferenceStateById.act?conferenceId=" + conferenceId;
				}
			});
	 			
	 		}
	 		
	 		function viewConference(conferenceId){
				var url = "${ctx}/app/pro/queryConferenceById.act?conferenceId="+conferenceId;
				openEasyWin("viewConference","会议详细",url,"900","600",false);
			}
			
			function addOnePrepare(){
				var url = "${ctx}/app/pro/addOnePrepare.act";
				openEasyWin("addOneConferencePrepare","会议申请",url,"900","600",false);
			}
			
			function modifyOnePrepare(conferenceId){
				var url = "${ctx}/app/pro/modifyOnePrepare.act?conferenceId="+conferenceId;
				openEasyWin("modifyOneConferencePrepare","会议修改",url,"900","600",false);
			}
			
			function valueOnePrepare(conferenceId){
				var url = "${ctx}/app/pro/valueOnePrepare.act?conferenceId="+conferenceId;
				openEasyWin("valueOneConferencePrepare","满意度评价",url,"900","600",false);
			}
			
			function closeViewConference(){
				closeEasyWin("viewConference");
			}
			
			function closeAddConference(){
				closeEasyWin("addOneConferencePrepare");
			}
			
			function closeModifyConference(){
				closeEasyWin("modifyOneConferencePrepare");
			}
			
			function closeValueConference(){
				closeEasyWin("valueOneConferencePrepare");
			}
		</script>
		
		<style>
.fkdj_ch_tabl_es{
	border-top:solid 1px #7f90b3;
	padding:4px 0;
	width:100%;
	margin:0 auto;
	margin-top:10px;
	padding-top:10px;
	text-align:right;
}
.fkdj_ch_tabl_es a{
	padding:4px 10px;
	background:#7f90b3;
	font-size:12px;
	text-decoration:none;
	font-weight:bold;
	color:#fff;
}
.fkdj_ch_tabl_es a:hover{
	padding:4px 10px;
	background:#ffc600;
	font-size:12px;
	text-decoration:none;
	font-weight:bold;
	color:#fff;
}
		</style>
	</head>
<body style="height: auto; background: #FFF;">
<div class="fkdj_index">
<div class="bgsgl_border_left">
		<div class="bgsgl_border_right">查看会议</div>
</div>
<div class="bgsgl_conter">
				<div class="fkdj_lfrycx">
 				<div class="fkdj_from">
<form id="queryForm" name="queryForm" action="showMyConferencesList.act" method="post">
                         <h:panel id="query_panel" width="100%" title="查询条件">
 						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hide_table">
 							<tr>
 								<td	class="fkdj_name">
 									会议名称
 								</td>
 								<td ><s:textfield id="list_conferenceName" cssClass="fkdj_from_input" name="list_conferenceName"/></td>
 								<td class="fkdj_name">
 									申请人
 								</td>
 								<td ><s:textfield id="list_conferenceUserName" cssClass="fkdj_from_input" name="list_conferenceUserName"/></td>
 								<td class="fkdj_name">状态</td>
							    <td >
							    	<select name="list_conferenceState" class="fkdj_select" id="list_conferenceState">
							    		<option id="list_allState" value="all">全部</option>
								        <option id="list_unstart" value="0" <c:if test="${optionConferenceState=='0'}">selected="selected"</c:if>>申请中</option>
								        <option id="list_complete" value="1" <c:if test="${optionConferenceState=='1'}">selected="selected"</c:if>>已完成</option>
								        <option id="list_value" value="2" <c:if test="${optionConferenceState=='2'}">selected="selected"</c:if> >已评价</option>
								        <option id="list_cancel" value="3" <c:if test="${optionConferenceState=='3'}">selected="selected"</c:if>>已取消</option>
								        <option id="list_confirm" value="4" <c:if test="${optionConferenceState=='4'}">selected="selected"</c:if>>已确认</option>
							      	</select>
							    </td>
							    <td></td>			
 							</tr>
 							<tr>
						    	<td class="fkdj_name" height="40px">会议时间</td>
						    	<td><table border="0" cellspacing="0" cellpadding="0">
							      <tr>
							        <td><s:textfield  cssClass="fkdj_from_input" name="conferenceStartTime" id="conferenceStartTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
							        <td class="fkdj_name_lkf">&nbsp;至&nbsp;</td>
							        <td><s:textfield   cssClass="fkdj_from_input" name="conferenceEndTime" id="conferenceEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
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
		<h:page id="list_panel" width="100%" title="">
 		<d:table name="page.result" id="row" uid="row" export="false" cellspacing="0" cellpadding="0" class="table">
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
										
										<c:if test="${row.CONFERENCE_STATE == 0}">
											<span class="display_centeralign"
												onclick="javascript:modifyOnePrepare('${row.CONFERENCE_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:pointer"
												>&nbsp;[修改]&nbsp;
											</span>
										
											<span class="display_centeralign"
												onclick="javascript:cancelConference('${row.CONFERENCE_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:pointer"
												>&nbsp;[取消]&nbsp;
											</span>
											
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[评价]&nbsp; 
											</span>
											
										</c:if>
										<c:if test="${row.CONFERENCE_STATE == 1}">
										<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[修改]&nbsp;
											</span>
										
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[取消]&nbsp;
											</span>
											<span class="display_centeralign"
												onclick="javascript:valueOnePrepare('${row.CONFERENCE_ID}')"
												onmousemove="this.style.color='#ffae00'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:pointer"
												>&nbsp;[评价]&nbsp; 
											</span>
										</c:if>
											<c:if test="${row.CONFERENCE_STATE == 2}">
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[修改]&nbsp;
											</span>
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[取消]&nbsp; 
											</span>
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[评价]&nbsp; 
											</span>
										</c:if>
										<c:if test="${row.CONFERENCE_STATE == 3}">
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[修改]&nbsp;
											</span>
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[取消]&nbsp; 
											</span>
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[评价]&nbsp; 
											</span>
										</c:if>
										<c:if test="${row.CONFERENCE_STATE == 4}">
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[修改]&nbsp;
											</span>
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default;"
												>&nbsp;[取消]&nbsp; 
											</span>
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color='#8090b4'"
												onmouseout="this.style.color='#8090b4'"
												style="cursor:default"
												>&nbsp;[评价]&nbsp; 
											</span>
										</c:if>
									</d:col>						
		</d:table>		
	</h:page>
	</form>
	</div>
	</div>
	</div>	
</div>

<script type="text/javascript">
	
		$(function(){	
			$("#bulletin_panel tbody tr:eq(0)").hide();
			$("#bulletin_panel").find("thead").addClass("main_main2_list");//css({"background":"#EBEBEB","padding":"0"});
			$("#bulletin_panel").css("marginBottom","-30px");
			$("td.main_peag").siblings("td").remove();
			$(".main_peag").css("width","100%");
			$(".panel-body").find("div").find("table").css({"background":"#E0E3EA","padding":"0"});
			
			if (($("body", window.parent.document).height() - 180) > $ (".fkdj_index").height()) {
				$("#main_frame_left", window.parent.document).height($("body", window.parent.document).height() - 180);
				$("#main_frame_right", window.parent.document).height($("body", window.parent.document).height() - 180);
			} else {
				$("#main_frame_left", window.parent.document).height($(".fkdj_index").height() + 80);
				$("#main_frame_right", window.parent.document).height($(".fkdj_index").height() + 80);
			}
		});
	
		
		$(document).ready(function(){
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});
		

			function selectConferencesList(){
	 			querySubmit();
	 		}
	 		
	 		function cancelConference(conferenceId){
	 			easyConfirm('提示', '确定取消该会议吗？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/cancelConferenceStateById.act?conferenceId=" + conferenceId;
				}
			});
	 			
	 		}
		 		function viewConference(conferenceId){
				var url = "${ctx}/app/pro/queryConferenceById.act?conferenceId="+conferenceId;
				openEasyWin("viewConference","会议详细",url,"900","600",false);
			}
			
			function addOnePrepare(){
				var url = "${ctx}/app/pro/addOnePrepare.act";
				openEasyWin("addOneConferencePrepare","会议申请",url,"900","600",false);
			}
			
			function modifyOnePrepare(conferenceId){
				var url = "${ctx}/app/pro/modifyOnePrepare.act?conferenceId="+conferenceId;
				openEasyWin("modifyOneConferencePrepare","会议修改",url,"900","600",false);
			}
			
			function valueOnePrepare(conferenceId){
				var url = "${ctx}/app/pro/valueOnePrepare.act?conferenceId="+conferenceId;
				openEasyWin("valueOneConferencePrepare","满意度评价",url,"900","600",false);
			}
			
			function closeViewConference(){
				closeEasyWin("viewConference");
			}
			
			function closeAddConference(){
				closeEasyWin("addOneConferencePrepare");
			}
			
			function closeModifyConference(){
				closeEasyWin("modifyOneConferencePrepare");
			}
			
			function closeValueConference(){
				closeEasyWin("valueOneConferencePrepare");
			}
	</script>
	</body>
</html>