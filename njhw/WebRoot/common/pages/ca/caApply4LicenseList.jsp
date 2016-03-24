<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Date: 2011-11-24
- Description: CA资料查询 FOR LICENSE
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>CA资料查询FOR LICENSE</title>
		<%@ include file="/common/include/meta.jsp"%>
		<script type="text/javascript" src="${ctx}/scripts/custom/display.js"></script>
		<script type="text/javascript">
	$(document).ready(function(){
		//鼠标途经组件时例button,input时改变其样式
		changebasiccomponentstyle();
		//鼠标途经displaytag table时改变其样式
		changedisplaytagstyle();
		initQueryToggle("listCnt", "hide_table", "");
	});
	function doSubmit(){
		//checkDate();
		$("#loadingdiv").show();
		$("#pageNo").val("1");
		$("#queryForm").submit();
		
	}
	function bqDetail(id){
		var url = "${ctx}/common/caapplyAudit/detail.act?caid="+id;
		openEasyWin("winId","CA资料明细信息",url,"780","500");
	}
	function bqLicense(id){
		var url = "${ctx}/common/caLicense/license.act?caid="+id;
		openEasyWin("CALicense","CA发证",url,"780","500",true);
	}
	function checkDate(){
        var startDate = $("#bqModel_startdate").val();
        var endDate = $("#bqModel_enddate").val();
        if(!validDate(startDate, endDate)){
            easyAlert('提示信息','录入日期的开始时间不能大于结束时间!','info');
            return;
        }
	}

	//比较两个日期的大小
	//from <= to 返回true
	//from > to 返回false
	function validDate(from, to){
	    if(from != "" && to != ""){
			var fromDate = new Date(from.replace(/-/g, "/")); 
			var toDate = new Date(to.replace(/-/g, "/"));
	  		if((Date.parse(fromDate)-Date.parse(toDate))>0){
	  			return false;
	  		}
	  		else {
	  			return true;
	  		}
	    }
	    
	    return true;
	}
	</script>
	</head>
	<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
		<form id="queryForm" action="query.act" method="post"
			autocomplete="off">
			<h:panel id="query_panel" width="100%" title="查询条件">
				<table align="center" id="hide_table" border="0" width="100%"
					class="form_table">
					<tr>
						<td class="form_label" width="20%" align="left">
							<div class="float:left">
								机构名称：
							</div>
						</td>
						<td width="30%">
							<s:textfield id="orgname" name="caLicenseModel.orgname"
								theme="simple" maxlength="20" cssClass="input180box" />
						</td>
						<td class="form_label" width="20%" align="left">
							<div class="float:left">
								机构证件号码：
							</div>
						</td>
						<td width="30%">
							<s:textfield id="orgidnum" name="caLicenseModel.orgidnum"
								theme="simple" maxlength="20" cssClass="input180box" />
						</td>
					</tr>
					<tr>
						<td class="form_label" width="20%" align="left">
							<div class="float:left">
								申请人：
							</div>
						</td>
						<td width="30%">
							<s:textfield id="applyuser" name="caLicenseModel.applyuser"
								theme="simple" maxlength="20" cssClass="input180box" />
						</td>
						<td class="form_label" width="20%" align="left">
							<div class="float:left">
								申请代码：
							</div>
						</td>
						<td width="30%">
							<s:textfield id="applyno" name="caLicenseModel.applyno"
								theme="simple" maxlength="20" cssClass="input180box" />
						</td>
					</tr>
					<tr>
						<td class="form_label" width="20%" align="left">
							申请日期区间：
						</td>
						<td width="30%">
							<s:date id="startdate" name="caLicenseModel.startTime"
								format="yyyy-MM-dd" />
							<div id="startdate_div" style="display: inline">
								<s:textfield name="caLicenseModel.startTime" theme="simple"
									size="10" readonly="true" />
								<img alt="" src="${ctx}/scripts/widgets/calendar/img.gif"
									id="matchDateStart_img" />
							</div>
							至
							<s:date id="enddate" name="caLicenseModel.endTime"
								format="yyyy-MM-dd" />
							<div id="enddate_div" style="display: inline">
								<s:textfield name="caLicenseModel.endTime" theme="simple"
									size="10" readonly="true" />
								<img alt="" src="${ctx}/scripts/widgets/calendar/img.gif"
									id="matchDateEnd_img" />
							</div>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td colspan="4" class="form_bottom">
							<s:textfield name="page.pageSize" id="pageSize" theme="simple"
								size="1" onblur="return checkPageSize();"
								onkeyup="value=value.replace(/[^\d]/g,'');" />
							条/页&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton"
								iconCls="icon-search" onclick="doSubmit();">查询</a>&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton"
								iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a>
							<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
						</td>
					</tr>
				</table>
			</h:panel>
			<c:if test="${fn:length(page.result)>0}">
				<c:set var="listSize" value="1100px" />
			</c:if>
			<c:if test="${fn:length(page.result)==0}">
				<c:set var="listSize" value="100%" />
			</c:if>
			<div id="listCnt" style="OVERFLOW-y: auto;">
				<h:page id="list_panel" width="${listSize}" title="查询结果" btt="">
					<d:table name="page.result" id="row" class="table"	requestURI="export.act" export="false">
						<d:col title="序号" class="display_centeralign">
								${row_rowNum+((page.pageNo-1)*page.pageSize)}
						</d:col>

						<d:col property="APPLYUSER" class="display_centeralign"
							title="申请人" sortable="false" />
						<d:col property="APPLYDATE" class="display_centeralign"
							title="申请日期" sortable="false" format="{0,date,yyyy-MM-dd }" />
						<d:col class="display_centeralign" title="申请代码" sortable="false">
							<a href="javascript:void(0);" onclick="bqDetail('${row.CAID}')">${row.APPLYNO}</a>
						</d:col>
						<d:col property="APPLYNUM" class="display_centeralign"
							title="申请数量" sortable="false" />
						<d:col property="ORGNAME" class="display_centeralign" title="机构名称"
							sortable="false" />
						<d:col property="HANDLER" class="display_centeralign"
							title="经办人姓名" sortable="false" />
						<d:col property="AUDITUSER" class="display_centeralign"
							title="审核人" sortable="false" />
						<d:col property="AUDITDATE" class="display_centeralign"
							title="审核日期" sortable="false" format="{0,date,yyyy-MM-dd }" />
						<d:col property="AUDITSTATUS" class="display_centeralign" title="审核状态"
							dictTypeId="CA_AUDITSTATUS"  sortable="false" />
						<d:col class="display_centeralign" media="html" title="操作">
							<a href="javascript:void(0);" onclick="bqLicense('${row.CAID}')">[发证]</a>
						</d:col>
					</d:table>
				</h:page>
			</div>
		</form>
	</body>
</html>
<s:actionmessage theme="custom" cssClass="success" />
<script type="text/javascript">
	Calendar.setup({
		inputField  : "caLicenseModel_startTime",
		ifFormat	: "%Y-%m-%d",
		button	  : "startdate_div"	
	});
	
	Calendar.setup({
		inputField  : "caLicenseModel_endTime",
		ifFormat	: "%Y-%m-%d",
		button	  : "enddate_div"	
	});

</script>
