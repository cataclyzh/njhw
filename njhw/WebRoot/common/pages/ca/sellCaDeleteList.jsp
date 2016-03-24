<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cosmosource.base.util.*" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: yc
- Date: 2011-11-22 15:18:15
- Description: CA信息删除（宏三/供应商）-CA信息删除查询页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA信息删除(宏三/供应商)列表</title>	
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
		//if($("#subModel_buyNo").val()==''&&$("#subModel_buyTaxNo").val()==''){
		//	easyAlert('提示信息','请先输入购方代码或购方税号','info');
		//	return false;
		//}
		$("#pageNo").val("1");
		$('#queryForm').attr("action","query.act");
		$("#loadingdiv").show();
		disableButton();
		$("#queryForm").submit();
		//document.queryForm.submit();
	}
	
	//点取消选中checkboxd的时候执行一次该方法。
	function checkedEnter(){
		checkboxs = $("input:checked[id='_chk']");
		var length = checkboxs.length;
		for (var i = 0; i < length; i++){
			if(checkboxs[i].checked == true){
				changeColor();
			}
		}
	}
	
	//改变table行颜色
	function changeColor(){
		if($("input:checked[name='fsno']").parent().parent().attr("class") == "oddselected"){
			$("input:checked[name='fsno']").parent().parent().attr("class","odd");
		}
		if($("input:checked[name='fsno']").parent().parent().attr("class")=="evenselected"){
			$("input:checked[name='fsno']").parent().parent().attr("class","even");
		}
		if($("input:checked[name='fsno']").parent().parent().attr("class")=="oddhighlight"){
			$("input:checked[name='fsno']").parent().parent().attr("class","oddselected");
		}
		if($("input:checked[name='fsno']").parent().parent().attr("class")=="evenhighlight"){
			$("input:checked[name='fsno']").parent().parent().attr("class","evenselected");
		}
		if($("input:checked[name='fsno']").parent().parent().attr("class")=="odd"){
			$("input:checked[name='fsno']").parent().parent().attr("class","evenselected");
		}
		if($("input:checked[name='fsno']").parent().parent().attr("class")=="even"){
			$("input:checked[name='fsno']").parent().parent().attr("class","evenselected");
		}
	}
	
	function resetForm(){
		document.forms[0].reset();
		//$("#subModel_sellTaxNo").attr("value","");
		//$("#subModel_sellTaxNo").change();
	}
	
	//取rowIndex行第columnIndex列的text
	function getColumnText(rowIndex, columnIndex){
        return $("#fsList tr").eq(rowIndex).children("td").eq(columnIndex).text();
    }
    
    function selectAlls(){
    	checkboxs = $("input:[id='_chk']");
    	//checkflag = $("input:[id='checkAll']");
		var length = checkboxs.length;
		for (var i = 0; i < length; i++){
			if(checkboxs[i].checked == false){
				changeColor();
			}
		}
    }
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="query.act" method="post" >
		<h:panel id="query_panel" width="100%" title="查询条件">	
			<!-- 销方标志flog=1 -->
			<input type="hidden" name="flog" value="1"/>	
			<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label" width="20%" align="left">
						申请日期：  
						<!-- 
						<s:select name="vatModel.dateType"  theme="simple" list="#{0:'入库日期',1:'开票日期',2:'认证日期',3:'扫描日期'}" onchange="changeDate(this);" listKey="key" listValue="value"/>：
						 -->
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
						是否已制证：
					</td>
					<td width="30%">
						<s:select list="#application.DICT_CA_ISGENCA" name="isgenca" 
         						emptyOption="false" headerKey=""  headerValue="全部" 
          						listKey="dictcode" listValue="dictname" cssClass="input180box"/>
					</td>	
					<td class="form_label" width="20%" align="left">
					</td>
					<td width="30%">
					</td>
				</tr>
				<tr>
				
					<td colspan="4" class="form_bottom">
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" size="1" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetForm();">重置</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
		<h:page id="list_panel" width="${listSize}" buttons="del" title="CA信息列表">
			<d:table name="page.result" id="row" uid="row"  export="false" class="table" requestURI="query.act">
				<d:col title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>				
				<d:col style="width:45"   class="display_centeralign" title="选择<input type='checkbox' id='checkAll' onclick='selectAlls()' />" >
					<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.CAID}"/>
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
					<a href="javascript:void(0);" onclick="caDetail('${row.CAID}')">[明细]</a>&nbsp;
				</d:col>	
			</d:table>
	    </h:page> 
	    </div>
	</form>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
<script type="text/javascript">
	function caDetail(id){
		var url = "${ctx}/common/caapplyAudit/detail.act?caid="+id;
		openEasyWin("winId","CA资料明细信息",url,"780","500");
	}
	
	function delRecord(){
		var fsnoStr = "";
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '删除选中的CA信息吗？', function(r){
				if (r){
					$('#queryForm').attr("action","delete.act");
					$("#loadingdiv").show();
					disableButton();
					$("#queryForm").submit();
					//document.queryForm.submit();
				}
			});	
		}
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
</script>
<s:actionmessage theme="custom" cssClass="success"/>		