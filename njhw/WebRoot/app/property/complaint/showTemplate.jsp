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
		<link href="${ctx}/app/property/css/wizard_css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/property/css/css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/property/css/block.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/property/css/fex_center.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/property/css/css_body.css" rel="stylesheet"
			type="text/css" />

<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>



		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<title></title>
			<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
			<script type="text/javascript">
             function addForm(){
				var url = "${ctx}/app/pro/addComplaints.act";
				openEasyWin("orgInput","投诉申请",url,"900","600",false);
			}
			
			function viewForm(complaintsId){
			  
	            var url = "${ctx}/app/pro/viewComplaints.act?complaintsId="+complaintsId;
	            openEasyWin("orgInput","查看投诉信息",url,"750","500",false);
		
			}
			function processForm(complaintsId){
			  
	            var url = "${ctx}/app/pro/processComplaints.act?complaintsId="+complaintsId;
	            openEasyWin("orgInput","处理投诉信息",url,"750","800",false);
		
			}
				function feedbackForm(complaintsId){
			  
	            var url = "${ctx}/app/pro/feedbackComplaints.act?complaintsId="+complaintsId;
	            openEasyWin("orgInput","反馈投诉信息",url,"750","850",false);
		
			}
			
			function closeForm(){
			closeEasyWin("orgInput");
			easyConfirm('提示', '处理完成', function(r){
				if (r){
					window.location.href="javascript:void(0);";
				}
			});			    
			}
			
			
			
			function closeOther(){
						closeEasyWin("orgInput");
			
			}
        </script>



			<%@ include file="/common/include/meta.jsp"%>
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
		

$(document).ready(function(){
				jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime <= value;
	        };
	        
	        jQuery.validator.methods.compareNowDate = function(value, element, param) {
				return value >= "${nowDate}";
	        };
				
				$("#queryComplaintsForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
					complaintsInTime:{
						compareNowDate:true
					},
					complaintsOverTime:{
						compareDate: "#complaintsInTime"
					}
						
					},
					messages:{
						complaintsInTime:{
	                    compareNowDate: "开始时间不能早于当前时间"
				 	},
	                complaintsOverTime:{
	                    compareDate: "结束日期必须晚于开始日期"
	               	}
					},
				});
			});    
			
			
				function saveData(){		
				if (showRequest()) {
			
					$('#queryComplaintsForm').submit();
					
				}
			}
			
			function showRequest(){
				 return $("#queryComplaintsForm").valid();
			}
		</script>
			
			
			
			
			
		</head>


		<body>
			<div class="main_index">
				<jsp:include page="/app/integrateservice/header.jsp" />
				<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
					<div class="bgsgl_border_left">
						<div class="bgsgl_border_right">
							投诉信息一览
						</div>
					</div>
					<div class="bgsgl_conter" style="min-height: 300px">
						<form id="queryComplaintsForm" name="queryComplaintsForm"
							action="showComplaintsList.act" method="post"
							autocomplete="off">
							<div class="fkdj_lfrycx">
								<div class="fkdj_from">

									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td class="fkdj_name">
												标题
											</td>
											<td>
												<input id=complaintsTitle name="complaintsTitle"
													class="fkdj_from_input" type="text"></input>
											</td>
											<td class="fkdj_name">
												投诉人
											</td>
											<td>
												<input id="complaintsUser"
													name="complaintsUser" class="fkdj_from_input"
													type="text"></input>
											</td>
											<td class="fkdj_name">
												投诉状态
											</td>
											<td>
												<select  class="fkdj_select"
													id="complaintsState" name="complaintsState">
													<option></option>
													<option>
														未处理
													</option>
													<option>
														已处理
													</option>
													<option>
														已结束
													</option>

												</select>
											</td>
										</tr>
										<tr>
											<td class="fkdj_name">
												投诉时间
											</td>
											<td>
												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td>
															<input type="text" class="fkdj_from_input"
																name="complaintsInTime" id="complaintsInTime"
																onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
																 />
														</td>
														<td class="fkdj_name_lkf">
															至
														</td>
														<td>
															<input type="text" class="fkdj_from_input"
																name="complaintsOverTime" id="complaintsOverTime"
																onclick="WdatePicker( {dateFmt : 'yyyy-MM-dd HH:mm:ss'});" />
														</td>
													</tr>
												</table>
											</td>

											<td class="fkdj_name">
												<br>
											</td>
											<td>
												&nbsp;
											</td>
											<td class="fkdj_name">
												<br>
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
									</table>


									<s:textfield name="page.pageSize" id="pageSize" theme="simple"
										cssStyle="display:none" onblur="return checkPageSize();"
										onkeyup="value=value.replace(/[^\d]/g,'');" />
									<div class="fkdj_ch_tabl_es">
										<a class="display_centeralign"
											onClick="saveData()" onmousemove="this.style.color = '#ffae00';this.style.cursor='pointer';" onmouseout="this.style.color = '#8090b4';this.style.cursor='pointer';" >查&nbsp;&nbsp;&nbsp;&nbsp;询
										</a>

									</div>

								</div>
							</div>
							<h:page id="list_panel" width="100%" title="结果列表">
								<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
									<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
									<d:col property="COMPLAINTS_TITLE" class="display_centeralign"
										title="投诉标题" />
										<d:col property="COMPLAINTS_DETAIL" class="display_centeralign"
										title="投诉内容" maxLength="13"/>
											<d:col property="COMPLAINTS_USER" class="display_centeralign"
										title="投诉人" />
									<d:col property="COMPLAINTS_INTIME" class="display_centeralign"
										title="投诉时间" />
									<d:col property="COMPLAINTS_PROCESS_USER"
										class="display_centeralign" title="处理人" />
									<d:col property="COMPLAINTS_OVERTIME"
										class="display_centeralign" title="处理时间" />
									<d:col property="COMPLAINTS_PROCESS_RESULT"
										class="display_centeralign" title="投诉结果" />
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
									<d:col class="display_centeralign" title="详情">
										<span class="display_centeralign"
											onclick="javascript:viewForm('${row.COMPLAINTS_ID}')"
											onmousemove="this.style.color = '#ffae00';this.style.cursor='pointer';"onmouseout="this.style.color = '#8090b4';this.style.cursor='pointer';"> [查看]&nbsp; </span>
										</d:col>
										<d:col class="display_centeralign" title="操作">
										<c:if test="${row.COMPLAINTS_STATE == 0}">
											<span class="display_centeralign"
												onclick="javascript:processForm('${row.COMPLAINTS_ID}')"
												onmousemove="this.style.color = '#ffae00';this.style.cursor='pointer';" onmouseout="this.style.color = '#8090b4';this.style.cursor='pointer';">&nbsp;[处理]&nbsp;
											</span>
										</c:if>
										<c:if test="${row.COMPLAINTS_STATE == 1}">
											<span class="display_centeralign"
												onClick="javascript:feedbackForm('${row.COMPLAINTS_ID}')"
												onmousemove="this.style.color = '#ffae00';this.style.cursor='pointer';"onmouseout="this.style.color = '#8090b4';this.style.cursor='pointer';">&nbsp;[反馈]&nbsp;
											</span>
										</c:if>
										<c:if test="${row.COMPLAINTS_STATE == 2}">
											<span class="display_centeralign"
												
												style="color:#ffae00" onmouseout="this.style.color = '#ffae00';">&nbsp;已完成&nbsp;
											</span>
										</c:if>										
									</d:col>
								</d:table>
							</h:page>
						</form>
						<div class="fkdj_botton_ls">
							<a class="fkdj_botton_left" href="javascript:addForm()">增加</a>
							
						</div>

						<div class="bgsgl_clear"></div>
					</div>
				</div>
				<jsp:include page="/app/integrateservice/footer.jsp" />
			</div>

		</body>
		<s:actionmessage theme="custom" cssClass="success" />
</html>