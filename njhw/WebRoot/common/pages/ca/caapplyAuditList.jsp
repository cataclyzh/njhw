<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: fengfj
- Date: 2011-11-24
- Description: CA资料审核查询
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA资料审核查询</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript" src="${ctx}/hisap/scripts/getTaxnoOrCode.js"></script>
	<script type="text/javascript" src="${ctx}/hisap/scripts/dateUtil.js"></script>
	<script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		//鼠标途经组件时例button,input时改变其样式
		changebasiccomponentstyle();
		//鼠标途经displaytag table时改变其样式
		changedisplaytagstyle();
		initQueryToggle("listCnt", "hide_table", "");
	});
	
	function doSubmit(){
		$("#pageNo").val("1");
		$('#queryForm').attr("action","query.act");
		$("#loadingdiv").show();
		disableButton();
		$("#queryForm").submit();
	}
	
	function bqDetail(id){
		var url = "${ctx}/common/caapplyAudit/detail.act?caid="+id;
		openEasyWin("winId","CA资料审核明细信息",url,"780","500");
	}

	function downloadImg(id){
		var url = "${ctx}/common/caapplyAudit/downloadImgById.act";
		var params = "?caid="+id;
		url += params;
		$("#queryForm").attr("action", url);
		$("#queryForm").submit();
	}
	
	function bqEditDetail(id){
		var url = "${ctx}/common/caapplyAudit/edit.act?caid="+id;
		openEasyWin("winId","CA资料审核明细信息",url,"780","500",true);
	}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="query.act" method="post"  autocomplete="off">
		<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">	
          <div class="float:left">
          	机构名称：
          </div>
          </td>
          <td width="30%">
            <s:textfield id="orgname" name="caapplyQueryModel.orgname" theme="simple" maxlength="20" cssClass="input180box"/>
          </td>
          <td class="form_label" width="20%" align="left">	
          <div class="float:left">
          	机构证件号码：
          </div>
          </td>
          <td width="30%">
            <s:textfield id="orgidnum" name="caapplyQueryModel.orgidnum" theme="simple" maxlength="20" cssClass="input180box"/>
          </td>
        </tr>    
        <tr>
          <td class="form_label" width="20%" align="left">	
          <div class="float:left">
          	申请人：
          </div>
          </td>
          <td width="30%">
            <s:textfield id="applyuser" name="caapplyQueryModel.applyuser" theme="simple" maxlength="20" cssClass="input180box"/>
          </td>
          <td class="form_label" width="20%" align="left">	
          <div class="float:left">
          	申请代码：
          </div>
          </td>
          <td width="30%">
            <s:textfield id="applyno" name="caapplyQueryModel.applyno" theme="simple" maxlength="20" cssClass="input180box"/>
          </td>
        </tr>    
        <tr>
        <td class="form_label" width="20%" align="left">
       	  	申请日期区间：     	  	
          </td>
          <td width="30%">
          	<s:date id="startdate" name="caapplyQueryModel.startTime" format="yyyy-MM-dd"/>
			<div id="startdate_div" style="display:inline">
	       		<s:textfield name="caapplyQueryModel.startTime" theme="simple"  size="10" readonly="true" />
				<img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="matchDateStart_img"/>
			</div>
			至
			<s:date id="enddate" name="caapplyQueryModel.endTime" format="yyyy-MM-dd"/>
			<div id="enddate_div" style="display:inline">
	       		<s:textfield name="caapplyQueryModel.endTime" theme="simple" size="10" readonly="true" />
	       		<img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="matchDateEnd_img"/>
       		</div>
          </td>
          <td colspan="2"></td>
        </tr>
        
        <tr>
          <td colspan="4" class="form_bottom">
          	<s:textfield name="page.pageSize" id="pageSize" theme="simple" size="1" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="doSubmit();">查询</a>&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a> 
          	<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
          </td>
        </tr>
      </table>      
   </h:panel>
		<div id="listCnt"  style="OVERFLOW-y:auto;">
		<h:page id="list_panel" width="100%" title="查询结果" btt="">
		<d:table name="page.result" id="row" class="table" requestURI="export.act" export="false" >
		    <d:col title="序号" class="display_centeralign" >
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>	
		    <d:col property="ORGNAME" class="display_centeralign" title="机构名称" sortable="false" />
		    <d:col property="ORGIDNUM" class="display_centeralign" title="机构证件号码" sortable="false" />
		    <d:col property="ORGIDTYPE" class="display_centeralign" title="机构证件类型" sortable="false" dictTypeId="CA_ORGIDTYPE"/>
		    <d:col property="APPLYNO" class="display_centeralign" title="申请代码" sortable="false"/>
		    <d:col property="APPLYNUM" class="display_centeralign" title="申请数量" sortable="false" />
		    <d:col property="CATERM" class="display_centeralign" title="证书期限" sortable="false" dictTypeId="CA_CATERM"/>
		    <d:col property="CATYPE" class="display_centeralign" title="证书种类" sortable="false" dictTypeId="CA_CATYPE"/>
		    <d:col property="APPLYDATE" class="display_centeralign" title="申请日期" sortable="false" format="{0,date,yyyy-MM-dd }"/>
		    <d:col class="display_centeralign"  media="html" title="操作">
		    	<a href="javascript:void(0);" onclick="downloadImg('${row.CAID}')">[下载图片]</a>&nbsp;			
				<a href="javascript:void(0);" onclick="bqDetail('${row.CAID}')">[明细]</a>
				<a href="javascript:void(0);" onclick="bqEditDetail('${row.CAID}')">[审核]</a>
			</d:col>	
		</d:table>
   </h:page>
	    </div>
	</form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
<script type="text/javascript">
	Calendar.setup({
		inputField  : "caapplyQueryModel_startTime",
		ifFormat	: "%Y-%m-%d",
		button	  : "startdate_div"	
	});
	
	Calendar.setup({
		inputField  : "caapplyQueryModel_endTime",
		ifFormat	: "%Y-%m-%d",
		button	  : "enddate_div"	
	});

</script>
