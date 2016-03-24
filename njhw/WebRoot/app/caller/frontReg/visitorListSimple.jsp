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
	<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>查看报修</title>
		<%@ include file="/common/include/meta.jsp"%>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
				<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
		
		
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	
<script type="text/javascript">
            var str = "${ctx}";
	    	var ctx = "${ctx}";

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
						
								vsEt:{
								compareDate: "#vsSt"
							}
								},
								messages:{
									vsEt:{
										compareDate: "结束时间必须晚于开始时间"
									}
								}
							});
						});    
	

				
		/**
		* 页面查询按钮事件
		*/
		function queryBtnClick(){
			$("#DRIVER_TYPE").val("btnQuery");
		    if($("#queryForm").valid()){
		
		querySubmit();
		    }
		
		}

	
	    </script>		
		
		
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div class="fkdj_index">
			<div class="oper_border_right">
				<div class="oper_border_left">
					来访人员查询
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="">
					<div class="fkdj_from">
                      <form id="queryForm" name="queryForm" action="visitorListSimple.act" method="post" >							
                          <h:panel id="query_panel" width="100%" title="查询条件">
							
								<table align="center" id="hide_table" border="0" width="100%">
									<tr>
										<td class="fkdj_name">
											访客姓名
										</td>
										<td>
										<input type="hidden" name="DRIVER_TYPE" id="DRIVER_TYPE"
											value="">
										<input type="hidden" name="cur_broom_card_no" id="DRIVER_TYPE"
											value="">
										<input type="hidden" name="pageNo" id="pageNo" value="1">
										<s:textfield  cssClass="fkdj_from_input" name="viName" id="viName" />
									</td>
										<td class="fkdj_name">
											访客状态
										</td>
										<td>
								<s:select list="#application.DICT_VISIT_STATUS" cssClass="fkdj_from_input"  headerKey="0" headerValue="全部" listKey="dictcode" listValue="dictname" name="vsFlag" id="vsFlag"/>

										</td>
									</tr>
							
										
										
								

									<tr>
										<td class="fkdj_name">
											访问时间
										</td>
										<td>
                                   <s:textfield cssStyle="width:164px;height:24px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="vsSt" name="vsSt" cssClass="Wdate" />
									</td>
									<td class="fkdj_name">
										至
									</td>
									<td>
									<s:textfield cssStyle="width:164px;height:24px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="vsEt" name="vsEt" cssClass="Wdate" />
									</td>
									
									
										
										<td>
											<a class="botton_oper_a" 
												onclick="queryBtnClick()"
												style="cursor:pointer"
												>查询 </a>
										</td>
									</tr>
								</table>
							</h:panel>
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
							<h:page id="list_panel" width="100%" title="结果列表">
		<d:table name="page.result" id="row" uid="row" export="false" class="table">	
		
		    <d:col class="display_leftalign" title="姓名" property="VI_NAME" maxLength="4">
			</d:col>
		    <d:col property="CARD_TYPE" dictTypeId="CARD_TYPE" class="display_centeralign" title="卡类型" maxLength="10"/>
		    <d:col property="CARD_ID"  class="display_leftalign"  title="卡号" maxLength="10"/>
		    <d:col property="VI_MOBILE"   class="display_centeralign"  title="手机" maxLength="11"/>
		    <d:col property="VS_FLAG" dictTypeId="USER_VISIT_FLAG" class="display_centeralign" title="访问状态" maxLength="10"/>
            <d:col property="VS_ST"   class="display_centeralign"  title="开始时间" maxLength="10"/>
            <d:col property="VS_ET"   class="display_centeralign"  title="结束时间" maxLength="10"/>
		</d:table>
		</h:page>
						</form>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
