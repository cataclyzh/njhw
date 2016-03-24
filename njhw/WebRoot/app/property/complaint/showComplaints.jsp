<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>投诉信息一览</title>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
			<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>

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
             function addForm(){
				var url = "${ctx}/app/pro/addComplaints.act";
				openEasyWin("orgInput","投诉申请",url,"750","700",false);
			}
			
			function viewForm(complaintsId){
			  
	            var url = "${ctx}/app/pro/viewComplaints.act?complaintsId="+complaintsId;
	            openEasyWin("orgInput","查看投诉信息",url,"750","700",false);
		
			}
			function processForm(complaintsId){
			  
	            var url = "${ctx}/app/pro/processComplaints.act?complaintsId="+complaintsId;
	            openEasyWin("orgInput","处理投诉信息",url,"750","700",false);
		
			}
				function feedbackForm(complaintsId){
			  
	            var url = "${ctx}/app/pro/feedbackComplaints.act?complaintsId="+complaintsId;
	            openEasyWin("orgInput","反馈投诉信息",url,"750","700",false);
		
			}
			
			function closeForm(){
			closeEasyWin("orgInput");
			easyConfirm('提示', '处理完成', function(r){
				if (r){
					window.location.href="#";
				}
			});			    
			}
			
			
			
			function closeOther(){
						closeEasyWin("orgInput");
			
			}
        </script>
			<script type="text/javascript">
			$(document).ready(function(){
				jQuery.validator.methods.compareDate = function(value, element, param) {
			        var beginTime = jQuery(param).val();
			        if(value!=""){
			        return beginTime <= value;
			        }else {
			        return true;
			        }
			    };  
							$("#queryForm").validate({		// 为inputForm注册validate函数
								meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
								errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
								onsubmit: true,
								rules: {
						
								complaintsOver_Time:{
								compareDate: "#complaintsIn_Time"
							}
								},
								messages:{
									complaintsOver_Time:{
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
<body style="margin:0;padding:0;background: white;">
<div class="fkdj_index">
<div class="main_border_01">
  <div class="main_border_02">查看投诉</div>
</div>
<div class="main_conter" style="padding: 15px;">
	<div class="fkdj_from">
	<form id="queryForm" name="queryForm" action="showComplaints.act" method="post">
	
	                                <h:panel id="query_panel" width="100%" title="查询条件">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hide_table" class="form_table">
										<tr>
											<td class="fkdj_name">
												标题
											</td>
											<td>
											    <s:textfield name="complaints_Title" id="complaints_Title" cssClass="fkdj_from_input"/>
											</td>
											<td class="fkdj_name">
												投诉人
											</td>
											<td>
											    <s:textfield name="complaints_User" id="complaints_User" cssClass="fkdj_from_input"/>
											</td>
										</tr>
										<tr>
											<td class="fkdj_name">
												投诉时间
											</td>
											<td>
												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td style="padding-bottom: 10px">
														　　<s:textfield name="complaintsIn_Time" id="complaintsIn_Time" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" >  
  
  
　　                                                                                                     </s:textfield>  
														</td>
														<td class="fkdj_name_lkf">
															&nbsp;至&nbsp;
														</td>
														<td style="padding-bottom: 10px">
																		　　<s:textfield name="complaintsOver_Time" id="complaintsOver_Time" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">  
  
　　                                                                                                     </s:textfield>  
														</td>
													</tr>
												</table>
												
											</td>
											
											<td class="fkdj_name">
												投诉状态
											</td>
											<td>
												<select id="complaintsState" name="complaintsState"  class="fkdj_from_input">
													<option value="all">全部</option>
													<option value="0"<c:if test="${complaintsState=='0'}">selected="selected"</c:if>>
														未处理
													</option>
													<option value="1"<c:if test="${complaintsState=='1'}">selected="selected"</c:if>>
														已处理
													</option>
													<option value="2"<c:if test="${complaintsState=='2'}">selected="selected"</c:if>>
														已结束
													</option>
												</select>
											</td>
											<td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="saveData()">查询 </a>
										</td>
										</tr>
										<tr>
								

								</tr>
									</table>
	                        </h:panel>
							<h:page id="list_panel" width="100%" title="结果列表">
							
							<s:textfield name="page.pageSize" id="pageSize" theme="simple"
										cssStyle="display:none" onblur="return checkPageSize();"
										onkeyup="value=value.replace(/[^\d]/g,'');" />
								<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
			
									
										
										<d:col  class="display_centeralign"
										title="投诉标题" >
					    <a style="cursor:pointer;color:black;" onclick="viewForm('${row.COMPLAINTS_ID}')"> ${row.COMPLAINTS_TITLE} </a>
				    	 </d:col>
										
										
									
											<d:col property="COMPLAINTS_USER" class="display_centeralign"
										title="投诉人" />
									<d:col property="COMPLAINTS_INTIME" class="display_centeralign"
										title="投诉时间" />
									<d:col property="COMPLAINTS_PROCESS_USER"
										class="display_centeralign" title="处理人" />
									<d:col property="COMPLAINTS_OVERTIME"
										class="display_centeralign" title="处理时间" />
									<d:col property="COMPLAINTS_PROCESS_RESULT"
										class="display_centeralign" title="投诉结果" maxLength="13"/>
									<d:col class="display_centeralign" title="投诉状态">
										<c:if test="${row.COMPLAINTS_STATE == 0}">
											<span class="display_centeralign">未处理</span>
										</c:if>
										<c:if test="${row.COMPLAINTS_STATE == 1}">
											<span class="display_centeralign">已处理</span>
										</c:if>
										<c:if test="${row.COMPLAINTS_STATE == 2}">
											<span class="display_centeralign">已结束</span>
										</c:if>
									</d:col>
								
										<d:col class="display_centeralign" title="操作">
										<c:if test="${row.COMPLAINTS_STATE == 0}">
											<span class="display_centeralign"
												onclick="javascript:processForm('${row.COMPLAINTS_ID}')"
												onmousemove="this.style.color = '#ffae00';this.style.cursor='pointer';" onmouseout="this.style.color = '';this.style.cursor='pointer';">&nbsp;[处理]&nbsp;
											</span>
											<span class="display_centeralign"
												onClick=""
												onmousemove="this.style.color = '#8090b4';"onmouseout="this.style.color = '';" style="cursor:default;">&nbsp;[反馈]&nbsp;
											</span>
										</c:if>
										<c:if test="${row.COMPLAINTS_STATE == 1}">
										<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color = '#8090b4';" onmouseout="this.style.color = '';" style="cursor:default;">&nbsp;[处理]&nbsp;
											</span>
											<span class="display_centeralign"
												onClick="javascript:feedbackForm('${row.COMPLAINTS_ID}')"
												onmousemove="this.style.color = '#ffae00';this.style.cursor='pointer';"onmouseout="this.style.color = '';this.style.cursor='pointer';">&nbsp;[反馈]&nbsp;
											</span>
										</c:if>
										<c:if test="${row.COMPLAINTS_STATE == 2}">
											<span class="display_centeralign"
												onclick=""
												onmousemove="this.style.color = '#8090b4';" onmouseout="this.style.color = '';" style="cursor:default;">&nbsp;[处理]&nbsp;
											</span>
											<span class="display_centeralign"
												onClick=""
												onmousemove="this.style.color = '#8090b4';"onmouseout="this.style.color = '';" style="cursor:default;">&nbsp;[反馈]&nbsp;
											</span>
										</c:if>										
									</d:col>
								</d:table>
							</h:page>
						</form>
						</div>
					</div>
					</div>
<script type="text/javascript">
	
		$(function(){	
			$("#bulletin_panel tbody tr:eq(0)").hide();
			$("#bulletin_panel").find("thead").addClass("main_main2_list");//css({"background":"#EBEBEB","padding":"0"});
			$("#bulletin_panel").css("marginBottom","-30px");
			$("td.main_peag").siblings("td").remove();
			$(".main_peag").css("width","98%");
			$(".panel-body").find("div").find("table").css({"background":"#E0E3EA","padding":"0"});
			
			if (($("body", window.parent.document).height() - 180) > $ (".main_conter").height()) {
				$("#main_frame_left", window.parent.document).height($("body", window.parent.document).height() - 180);
				$("#main_frame_right", window.parent.document).height($("body", window.parent.document).height() - 180);
			} else {
				$("#main_frame_left", window.parent.document).height($(".main_conter").height() + 80);
				$("#main_frame_right", window.parent.document).height($(".main_conter").height() + 80);
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
		

	</script>
	


	</body>
<s:actionmessage theme="custom" cssClass="success"/>

</html>




















