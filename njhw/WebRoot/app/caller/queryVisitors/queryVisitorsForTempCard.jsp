<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-17 15:37:15
- Description: 页面
--%>
<html>
<head>
	<title>访客列表</title>
	<%@ include file="/common/include/meta.jsp" %>
	<jsp:include page="/app/portal/toolbar/toolbar_new.jsp"></jsp:include>
			  <link type="text/css" rel="stylesheet"
			href="${ctx}/app/caller/frontReg/wizard_css.css" />
			<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
			<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
			
</head>
<body style="background: #fff;">
	<form id="queryForm" action="queryVisitorsForTempCard.act?cardType=2" method="post">
	<s:hidden name="viId"/>
	<s:hidden name="vsId"/>	
	<div class="fkdj_lfrycx" >
	<div class="fkdj_sxry_list">
	<div class="fkdj_sxry">
      							<table width="96%" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;" id="main_table">
								<tr>
									<td class="fkdj_name">
										访客姓名
									</td>
									<td>
										<s:textfield type="text" cssClass="fkdj_from_input" name="viName" id="viName" />
									</td>
									<td class="fkdj_name">
										访客状态
									</td>
									<td>
										<s:select list="#application.DICT_VISIT_STATUS" cssClass="fkdj_from_input" headerKey="0" headerValue="全部" listKey="dictcode" listValue="dictname" name="vsFlag" id="vsFlag"/>
									</td>
								</tr>
								<tr>
									<td class="fkdj_name">
										受访人姓名
									</td>
									<td>
										<s:textfield type="text" cssClass="fkdj_from_input" name="userName" id="userName" />
									</td>
									<td class="fkdj_name">
										临时卡
									</td>
									<td>
										<s:select list="#application.DICT_VS_RETURN" cssClass="fkdj_from_input" headerKey="0" headerValue="全部" listKey="dictcode" listValue="dictname" name="vsReturn" id="vsReturn"/>
									</td>
								</tr>
								<tr>
									<td class="fkdj_name">
										访问时间
									</td>
									<td>
                                   <s:textfield type="text" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="beginTime" name="beginTime" />
									</td>
									<td class="fkdj_name">
										至
									</td>
									<td>
									<s:textfield type="text"  cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="endTime" name="endTime" />
									</td>
								</tr>
							</table> 
							<div class="fkdj_ch_table" style="margin-top:10px;">
							    <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
								<a href="javascript:void(0);" onclick="querySubmit();">搜&nbsp;&nbsp;&nbsp;索</a>
							</div>   
	<h:page id="list_panel" width="100%" title="结果列表">	
	<div style="overflow-y:auto; height:362px;">	
		<d:table name="page.result" id="row" uid="row" export="false" class="table">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<td class="form_label"><font style="color:red">*</font>：</td>
	        <td><s:textfield name="USER_NAME" theme="simple" size="18" maxlength="10"/></td>  
		    <d:col property="CARD_ID" class="display_centeralign"   title="卡号" maxLength="10"/>
		    <d:col property="VI_NAME" class="display_centeralign" title="访客姓名" maxLength="10"/>
		    <d:col property="USER_NAME" class="display_centeralign" title="受访者姓名" maxLength="10"/>
		    <d:col property="VS_RETURN" dictTypeId="VS_RETURN" class="display_centeralign" title="是否归还临时卡" maxLength="10"/>
		    <d:col property="VS_FLAG" dictTypeId="VISIT_STATUS" class="display_centeralign" title="访客状态" maxLength="10"/>
		    <d:col property="VS_ST" class="display_centeralign"  format="{0,date,yyyy-MM-dd hh:mm:ss}"  title="访问开始时间" maxLength="10"/>
			<d:col property="VS_ET" class="display_centeralign"  format="{0,date,yyyy-MM-dd hh:mm:ss}"  title="访问结束时间" maxLength="10"/>
		</d:table>
		</div>
   </h:page>
   	</div>
</div>
</div>
	</form>
<script type="text/javascript">
    function lookTransaction(vsId){
		var url = "${ctx}/app/caller/visitorsTransaction.act?vsId="+vsId;
		openEasyWin("winId","访问事务信息",url,"700","500",false);
    }
	function lookRecord(vsId){
		var url = "${ctx}/app/caller/lookVisitors.act?vsId="+vsId;
		openEasyWin("winId","访客信息",url,"700","500",true);
	}
	function showVideo(vsId){
		var url = "${ctx}/app/caller/showVideo.act?vsId="+vsId;
		openEasyWin("winId","访客访问线路",url,"900","600",true);
	}
	
	$(document).ready(function(){
			//日期比较
			 jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            if(beginTime=="" || value==""){
	            	return true;
	            }else{
	            	return beginTime < value;
	            }
	        };
			
			$("#queryForm").validate({
				meta :"validate",
				errorElement :"div",
				rules: {
					endTime:{
						compareDate: "#beginTime"
					}
				},
				messages:{ 
	                endTime:{
	                    compareDate: "结束日期必须大于开始日期!"
	               	}
				}
			});
		});
</script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>