<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>会议套餐一览</title>
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
		
		<script type="text/javascript">
			function showConferencePackagesList(){
	 			$("#queryForm").submit();
	 		}
	 		
	 		function viewConferencePackage(conferencePackageId){
				var url = "${ctx}/app/pro/queryConferencePackageById.act?conferencePackageId="+conferencePackageId;
				openEasyWin("viewLostFound","查看套餐",url,"800","600",false);
			}
			
			function addOneConferencePackagePrepare(){
				var url = "${ctx}/app/pro/addOneConferencePackagePrepare.act";
				openEasyWin("addOneConferencePackagePrepare","增加套餐",url,"800","600",false);
			}
			
			function modifyOneConferencePackagePrepare(conferencePackageId){
				var url = "${ctx}/app/pro/modifyOneConferencePackagePrepare.act?conferencePackageId="+conferencePackageId;
				openEasyWin("modifyOneConferencePackagePrepare","套餐修改",url,"800","650",false);
			}
			
			function suspendOneConferencePackage(conferencePackageId){
						
					  $.ajax({
				             type:"POST",
				             url:"checkConferencePackage.act?conferencePackageId=" + conferencePackageId,
				             data:{},
				             dataType: 'json',
						     async : false,
						     success: function(json){
				             if (json.status == 0) {
                              easyConfirm('提示', json.message, function(r){
				               if (r){
	
					window.location.href="${ctx}/app/pro/suspendOneConferencePackage.act?conferencePackageId=" + conferencePackageId;
				}
			});
      
				             }
			                 if(json.status == 1){ 
			                 easyConfirm('提示', '确定要停用该套餐？', function(r){
				               if (r){
	
					window.location.href="${ctx}/app/pro/suspendOneConferencePackage.act?conferencePackageId=" + conferencePackageId;
				}
			});
			                 }    
				          }
				      });
					
			
			
			
			
			
	 	
	 			
	 		}
	 		
	 		function activeOneConferencePackage(conferencePackageId){
	 			easyConfirm('提示', '确定重新启用该套餐？', function(r){
				if (r){
					window.location.href="${ctx}/app/pro/activeOneConferencePackage.act?conferencePackageId=" + conferencePackageId;
				}
			});
	 			
	 		}			
			
			function closeViewConferencePackage(){
				closeEasyWin("viewConferencePackage");
			}
			
			function closeAddConferencePackage(){
				closeEasyWin("addOneConferencePackagePrepare");
			}
			
			function closeModifyConferencePackage(){
				closeEasyWin("modifyOneConferencePackagePrepare");
			}
		</script>
	</head>
	<body style="background: #fff;">
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="bgsgl_border_right">
						查看套餐
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				    <!--此处写页面的内容 -->
				<div class="">
 				<div class="fkdj_from">
 					<form id="queryForm" name="queryForm" action="showConferencePackagesList.act" method="post">
 						<h:panel id="query_panel" width="100%" title="查询条件">
 						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hide_table" id="form_table">
 							<tr>
 								<td	class="fkdj_name">
 									会议室名称
 								</td>
 								<td><s:textfield name="list_conferencePackageName" id="list_conferencePackageName" cssClass="fkdj_from_input"/></td>
 							
 							
 								<td class="fkdj_name">
 									套餐启用状态
 								</td>
 								<td>
 									<select name="list_conferencePackageState" class="fkdj_select" style="height:26px;" id="list_conferencePackageState">
 										<option id="list_allState" value="all">全部</option>
								        <option id="list_active" value="1" <c:if test="${list_conferencePackageState=='1'}">selected="selected"</c:if>>启用</option>
								        <option id="list_notActive" value="0" <c:if test="${list_conferencePackageState=='0'}">selected="selected"</c:if>>停用</option>
							      	</select>
 								</td>
                                 
                                 <td class="fkdj_name">
                                 &nbsp;
                                 </td>
                                  <td>
											<a class="property_query_button" href="javascript:void(0);"
												onmousemove="this.style.background='FFC600'"
												onmouseout="this.style.background = '#8090b4'"
												onclick="javascript:querySubmit()">查询 </a>
										</td>

						    </tr>
 						</table>
 						</h:panel>
 						<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
	 	
 						
 	<h:page id="list_panel" width="100%" title="结果列表">
 		<d:table name="page.result" id="row" uid="row" export="false"
									cellspacing="0" cellpadding="0" class="table">
		
				
						<d:col  class="display_centeralign" title="会议室名称" >
					    <a style="cursor:pointer;color:black;" onclick="javascript:viewConferencePackage('${row.CONFERENCE_PACKAGE_ID}')"> ${row.CONFERENCE_PACKAGE_NAME} </a>
				    	 </d:col>
				
				
			
			<d:col property="CONFERENCE_PACKAGE_LOCATION" class="display_centeralign"
				title="会场地址" />
			<d:col property="CONFERENCE_PACKAGE_STYLE" class="display_leftalign"
				title="会议室布局" />
			<d:col property="CONFERENCE_PACKAGE_PRICE" class="display_centeralign"
				title="价目" />											
			<d:col class="display_centeralign" title="套餐启用状态">
				<c:if test="${row.CONFERENCE_PACKAGE_STATE == 0}">
					<span class="display_centeralign">停用</span>
				</c:if>
				<c:if test="${row.CONFERENCE_PACKAGE_STATE == 1}">
					<span class="display_centeralign">启用</span>
				</c:if>									
			</d:col>
			<d:col class="display_centeralign" title="操作">
										
										<c:if test="${operationAuthor eq null}">
											<span class="display_centeralign"
													onclick="javascript:modifyOneConferencePackagePrepare('${row.CONFERENCE_PACKAGE_ID}')"
													onmousemove="this.style.color='#ffae00'"
													onmouseout="this.style.color='#8090b4'"
													style="cursor:pointer"
													
													>
													[修改]&nbsp;
											</span>
											<c:if test="${row.CONFERENCE_PACKAGE_STATE == 0}">
												<span class="display_centeralign"
													onclick="javascript:activeOneConferencePackage('${row.CONFERENCE_PACKAGE_ID}')"
													onmousemove="this.style.color='#ffae00'"
													onmouseout="this.style.color='#8090b4'"
													style="cursor:pointer"
													>&nbsp;[启用]&nbsp;
												</span>
											</c:if>
											<c:if test="${row.CONFERENCE_PACKAGE_STATE == 1}">
												<span class="display_centeralign"
													onclick="javascript:suspendOneConferencePackage('${row.CONFERENCE_PACKAGE_ID}')"
													onmousemove="this.style.color='#ffae00'"
													onmouseout="this.style.color='#8090b4'"
													style="cursor:pointer"
													>&nbsp;[停用]&nbsp; 
												</span>
											</c:if>
										</c:if>																			
									</d:col>	
		</d:table>	
	</h:page>
	
	

	</form>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
		</div>
		
	</div>
	</body>
</html>