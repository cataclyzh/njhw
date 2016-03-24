<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cosmosource.base.util.*" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: yc
- Date: 2011-12-22 15:18:15
- Description: CA用户关联（宏三/供应商）-CA用户关联查询页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CA用户关联(宏三/供应商)列表</title>	
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
		document.forms[0].reset();
	}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="query.act" method="post" >
		<h:panel id="query_panel" width="100%" title="查询条件">
			<!-- 购方标志flog=0 -->
			<input type="hidden" name="flog" value="0"/>	
			<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label" width="20%" align="left">
					用户登录名：
					</td>
					<td width="30%">
			         <s:textfield name="loginname" theme="simple" maxlength="20" cssClass="input180box" readonly="false"/>
					</td>
					<td class="form_label" width="20%" align="left">
					CA序列号：  
					</td>
					<td width="30%" nowrap>
					 <s:textfield name="cano" theme="simple" maxlength="80" cssClass="input180box" readonly="false" />
					</td>					
				</tr>
				<tr>
					<td class="form_label" width="20%" align="left">
						是否有效CA：
					</td>
					<td width="30%">
						<s:select list="#application.DICT_CA_ISVALIDCA" name="isvalidca" 
         						emptyOption="false" headerKey=""  headerValue="全部" 
          						listKey="dictcode" listValue="dictname" cssClass="input180box"/>
					</td>	
					<td class="form_label" width="20%" align="left">
						申请代码：
					</td>
					<td width="30%">
					 <s:textfield name="applyno" theme="simple" maxlength="20" cssClass="input180box" />
					</td>
				</tr>
				<tr>
				
					<td colspan="4" class="form_bottom">
							<s:textfield name="page.pageSize" id="pageSize" theme="simple" size="1" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetForm();">重置</a>
			          	    &nbsp;&nbsp;&nbsp;&nbsp;                            
                            <span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
					</td>
				</tr>
			</table>
		</h:panel>
   		<div id="listCnt"  style="OVERFLOW-y:auto;">
		<h:page id="list_panel" width="100%" title="CAUSER信息列表">
			<d:table name="page.result" id="row" uid="row"  export="false" class="table" requestURI="query.act">
				<d:col title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>				
			    <d:col property="LOGINNAME" class="display_leftalign" title="用户登录名"/>
			    <d:col property="CANO" class="display_centeralign"  title="CA序列号"/>
			    <d:col property="ISVALIDCA" class="display_centeralign" dictTypeId="CA_ISVALIDCA" title="是否有效CA"/>
			    <d:col property="APPLYNO" class="display_centeralign" title="申请代码"/>
			    
				<d:col class="display_centeralign"  media="html" title="操作">
					<a href="javascript:void(0);" onclick="causerUpdate('${row.CAUSERID}')">[修改]</a>&nbsp;
					<a href="javascript:void(0);" onclick="causerDelete('${row.CAUSERID}')">[删除]</a>
				</d:col>	
			</d:table>
	    </h:page> 
	    </div>
	</form>
</body>
</html>
<script type="text/javascript">
	function causerUpdate(id){
		var url = "${ctx}/common/causer/detail.act?causerid="+id;
		openEasyWin("winId","CA用户关联",url,"780","500",true);
	}
	
	function causerDelete(id){
		easyConfirm('提示', '确定删除吗？', function(r){
				if (r){
					$('#queryForm').attr("action","delete.act?causerid="+id);
					$("#loadingdiv").show();
					disableButton();
					$("#queryForm").submit();
				}
		});	
    }

	function refreshCache(){
		$('#queryForm').attr("action","refreshCaUserCache.act");
		$('#queryForm').submit();
	}
</script>
<s:actionmessage theme="custom" cssClass="success"/>		