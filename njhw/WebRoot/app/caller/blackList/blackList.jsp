<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-17 15:37:15
- Description: 黑名单管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>黑名单列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0"  style="min-width: 1000px;">
	<form id="queryForm" action="queryBlackList.act" method="post"  autocomplete="off"  >
	<s:hidden name="VI_ID"/>
	<s:hidden name="VI_NAME"/>
    <h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          	<td class="form_label">访客姓名：</td>
	        <td><s:textfield name="viName" theme="simple" size="18"/></td> 
	        <td class="form_label">身份证：</td>
	        <td><s:textfield name="residentNo" theme="simple" size="18"/></td> 
	        <td class="form_label">市民卡：</td>
	        <td><s:textfield name="resBak1" theme="simple" size="18"/></td> 
	        <td class="form_label">是否注册：</td>
	        <td>
				 <select name="nvrId" id="nvrId">
					<option value="0" selected>全部</option>
					<option value="1">是</option>
					<option value="2">否</option>
				 </select>
	        </td>
	        <td class="form_label">是否加入黑名单：</td>
	        <td> 
				 <select name="isBlack" id="isBlack">
					<option value="0" selected>全部</option>
					<option value="1">是</option>
					<option value="2">否</option>
				 </select>
	        </td>
        </tr> 
        <tr>
          <td colspan="11" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:100px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
    </h:panel>
	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table" >	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<td class="form_label"><font style="color:red">*</font>：</td>
		    <d:col property="VI_NAME"  class="display_centeralign" title="访客姓名"/>
		    <d:col property="RESIDENT_NO" class="display_centeralign" title="身份证号码"/>
		    <d:col property="RES_BAK1" class="display_centeralign" title="市民卡号"/>
		    <d:col property="NVR_ID"   dictTypeId="BUS_YES_NO" class="display_centeralign" title="是否注册"/>
		    <d:col property="IS_BLACK" dictTypeId="BUS_YES_NO" class="display_centeralign" title="是否黑名单"/>
		    <d:col property="BLACK_RESON" dictTypeId="DICT_BLACK_RESONS" class="display_centeralign" title="加入黑名单原因"/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<c:if test="${row.IS_BLACK eq '1'}">
				<a href="javascript:void(0);" onclick="delBlackList('${row.VI_ID}')">[取消黑名单]</a>&nbsp;
				</c:if>
				<c:if test="${row.IS_BLACK eq '2'}">
				<span id="unlock_${row.VI_ID}">
				<a href="javascript:void(0);" onclick="addBlackList('${row.VI_ID}')">[加入黑名单]</a>&nbsp;
				</span>
				</c:if>
		    </d:col>
		</d:table>
   </h:page>
   </form>
<script type="text/javascript">
    function addBlackList(id){
		var url = "${ctx}/app/caller/addBlackList.act?viId="+id;
		openEasyWin("winId","黑名单详细信息",url,"399","400",true);
    }
    
    function delBlackList(id){
    	var url = "${ctx}/app/caller/delBlackList.act?viId="+id;
    	openEasyWin("winId","黑名单详细信息",url,"399","400",true);
    }
</script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>