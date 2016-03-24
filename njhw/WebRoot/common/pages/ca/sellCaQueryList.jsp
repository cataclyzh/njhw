<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: yc
- Date: 2011-11-22
- Description: CA查询页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA查询</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	$(document).ready(function(){
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();	
		initQueryToggle("listCnt", "hide_table", "");
	});
	
	//表单提交
	function querySubmit(){
		$("#pageNo").val("1");
		$('#queryForm').attr("action","query.act");
		$("#loadingdiv").show();
		disableButton();
		$("#queryForm").submit();
	}
	
	function resetForm(){
		$('#queryForm').resetForm();
	}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="query.act" method="post"  autocomplete="off">
		<h:panel id="query_panel" width="100%" title="查询条件">		
			<!-- 销方标志flog=1 -->
			<input type="hidden" name="flog" value="1"/>	
			<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label" width="20%" align="left">
						申请日期：  
					</td>
					<td width="30%" nowrap colspan="3">
            			<div id="recordTimeDiv" >
							<s:date id="format_invRecordTimeStart" name="caModel.applyDateStart" format="yyyy-MM-dd"/>
							<div id="invRecordTimeStart_div" style="display:inline">
       						<s:textfield name="caModel.applyDateStart" theme="simple"  size="10" readonly="true"/>
							<img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="invRecordTimeStart_img"/>
							</div>
							至
							<s:date id="format_invRecordTimeEnd" name="caModel.applyDateEnd" format="yyyy-MM-dd"/>
							<div id="invRecordTimeEnd_div" style="display:inline">
       						<s:textfield name="caModel.applyDateEnd" theme="simple" size="10" readonly="true"/>
       						<img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="invRecordTimeEnd_img"/>
       						</div>
       					</div> 	
					</td>	
				</tr>
				<tr>
					<td class="form_label" width="20%" align="left">
						审核状态：
					</td>
					<td width="30%">
						<s:select list="#application.DICT_CA_AUDITSTATUS" name="auditstatus" 
         						emptyOption="false" headerKey=""  headerValue="全部" 
          						listKey="dictcode" listValue="dictname" cssClass="input180box"/>
					</td>
					<td class="form_label" width="20%" align="left">
						是否已制证：
					</td>
					<td width="30%">
						<s:select list="#application.DICT_CA_ISGENCA" name="isgenca" 
         						emptyOption="false" headerKey=""  headerValue="全部" 
          						listKey="dictcode" listValue="dictname" cssClass="input180box"/>
					</td>	
				</tr>
				<tr class="form_bottom">
					<td colspan="4" class="form_bottom">
						<s:textfield name="page.pageSize" theme="simple" id="pageSize" size="1" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" maxlength="3"/>条/页&nbsp;&nbsp;
			          	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="javascript:querySubmit()">查询</a>
			          	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetForm();">重置</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
					</td>
				</tr>
			</table>
		</h:panel>

		<c:if test="${fn:length(page.result)>0}">
			<c:set var="listSize" value="1400"/>
		</c:if>
		<c:if test="${fn:length(page.result)==0}">
			<c:set var="listSize" value="100%"/>
		</c:if>
		<div id="listCnt"  style="OVERFLOW-y:auto;">
		<h:page id="list_panel" width="${listSize}" btt="vatSum" title="CA信息列表">		
			<d:table name="page.result" id="row" export="false" class="table" requestURI="export.act">
				<d:col title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
				<d:col property="APPLYNUM" class="display_centeralign" title="申请数量"/>
			    <d:col property="CATERM" class="display_centeralign" dictTypeId="CA_CATERM" title="证书期限"/>
			    <d:col property="ORGNAME" class="display_leftalign" title="机构名称"/>
			    <d:col property="HANDLER" class="display_leftalign" title="经办人姓名"/>
			    <d:col property="HANDLERIDTYPE" class="display_centeralign" dictTypeId="CA_HANDLERIDTYPE" title="经办人证件类型"/>
			    <d:col property="HANDLERIDNUM" class="display_leftalign" title="经办人证件号码"/>
			    <d:col property="STAMPDATE" class="display_centeralign"   format="{0,date,yyyy-MM-dd }"  title="签章日期"/>
			    <d:col property="AUDITUSER" class="display_leftalign" title="审核人"/>
			    <d:col property="AUDITSTATUS" class="display_centeralign" dictTypeId="CA_AUDITSTATUS" title="审核状态"/>
			    <d:col property="ISGENCA" class="display_centeralign" dictTypeId="CA_ISGENCA" title="是否已制证"/>
			    
				<d:col class="display_centeralign"  media="html" title="操作">
					<a href="javascript:void(0);" onclick="downloadImg('${row.CAID}')">[下载图片]</a>&nbsp;
					<a href="javascript:void(0);" onclick="caDetail('${row.CAID}')">[明细]</a>&nbsp;					
					<c:if test="${row.ISSUBMIT eq '1'}">				
					    <a href="javascript:void(0);" onclick="caExport('${row.CAID}')">[打印]</a>&nbsp;
					</c:if>
				</d:col>	
			</d:table>
	    </h:page>
	    </div>
	</form> 
</body>
</html>
<script type="text/javascript">
	function caDetail(id){
		var url = "${ctx}/common/caapplyAudit/detail.act?caid="+id;
		openEasyWin("winId","CA资料明细信息",url,"780","500");
	}

	function caExport(id){
		var url = "${ctx}/common/ca/apply/exportApplyPDF.act";
		var params = "?caid="+id;
		url += params;
		$("#queryForm").attr("action", url);
		$("#queryForm").submit();
	}
	
	function downloadImg(id){
		var url = "${ctx}/common/caapplyAudit/downloadImgById.act";
		var params = "?caid="+id;
		url += params;
		$("#queryForm").attr("action", url);
		$("#queryForm").submit();
	}
	
	Calendar.setup({
		inputField  : "caModel_applyDateStart",
		ifFormat	: "%Y-%m-%d",
		button	  : "invRecordTimeStart_div"	
	});
	Calendar.setup({
		inputField  : "caModel_applyDateEnd",	  
		ifFormat	: "%Y-%m-%d",	  
		button	  : "invRecordTimeEnd_div"	
	});		

	//设置查询结果div -----end
</script>
<s:actionmessage theme="custom" cssClass="success"/>