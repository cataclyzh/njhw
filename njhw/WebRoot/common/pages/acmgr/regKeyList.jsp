<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2011-01-26 15:37:15
- Description: 注册码生成页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>注册码生成</title>	
	<%@ include file="/common/include/meta.jsp" %>
	
			
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
		});
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #f1f8fd;">
	<form id="queryForm" action="list.act" method="post"  autocomplete="off">
	<s:hidden name="parentid"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	纳税人识别号：
          </td>
          <td width="30%">
           <s:textfield name="taxno" theme="simple" size="25"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	纳税人名称：       	  	
          </td>
          <td width="30%">
            <s:textfield name="taxname" theme="simple" size="25"/>  
          </td>
        </tr>
        <tr>
          <td class="form_label" width="20%" align="left">
           	截止月份：
          </td>
          <td width="30%">
        <s:date id="format_uptomon" name="uptomon" format="yyyyMM"/>
        <s:textfield name="uptomon" theme="simple" readonly="true" size="18"/>
        <img alt="" src="${ctx}/scripts/widgets/calendar/img.gif" id="uptomon_img"/>
          </td>
          <td class="form_label" width="20%" align="left">
       	  	系统：       	  	
          </td>
          <td width="30%">
          <s:select list="#application.DICT_REGKEY_SYS" name="sys"  headerKey=""  headerValue="---请选择---" 
           emptyOption="false" listKey="dictcode" listValue="dictname"/>  
          </td>
        </tr>  
        <tr>
          <td colspan="4" class="form_bottom">
          	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<ahref="javascript:void(0);"" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>&nbsp;&nbsp;
          	 <ahref="javascript:void(0);"" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a> &nbsp;&nbsp;
          </td>
        </tr>
      </table>      
   </h:panel>

	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="list.act?exFile=1">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.regid}"/>
			</d:col>	
		    <d:col property="taxno"   class="display_centeralign"   title="纳税人识别号"/>
		    <d:col property="taxname" class="display_leftalign" title="纳税人名称"/>
		    <d:col property="sys" class="display_centeralign" dictTypeId="REGKEY_SYS" title="系统"/>
		    <d:col property="uptomon" class="display_centeralign" title="截止月份"/>
			<d:col property="registkey" class="display_centeralign" title="注册码"/>
			<d:col property="operdate" class="display_centeralign" title="注册日期"/>
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var	url = "${ctx}/common/regKey/input.act";
		openEasyWin("winId","录入注册码信息",url,"780","450",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","delete.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
	Calendar.setup({
		inputField  : "uptomon",	 
		ifFormat	: "%Y%m",	 
		button	  : "uptomon_img"	
	});
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
