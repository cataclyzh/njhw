<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-17 15:37:15
- Description: 访客统计
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>访客统计</title>	
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="visitorsReport.act" method="post"  autocomplete="off">
	<s:hidden name="viId"/>
	<s:hidden name="vsId"/>
    	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label">卡号：</td>
	        <td><s:textfield name="cardId" theme="simple" size="18"/></td> 
	        <td class="form_label">姓名：</td>
	        <td><s:textfield name="userName" theme="simple" size="18"/></td> 
	        <td class="form_label">访客状态：</td>
	        <td>
	         <s:select list="#{'all':'全部','01':'申请中..','02':'确认','03':'拒绝','04':'到访','05':'正常结束','06':'异常结束'}" listKey="key" listValue="value" name="vsFlag">
             </s:select>
	        </td> 
        </tr>     
        <tr>
          <td colspan="6" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:100px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<td class="form_label"><font style="color:red">*</font>：</td>
	        <td><s:textfield name="userName" theme="simple" size="18" maxlength="20"/></td>  
		    <d:col property="cardId" class="display_centeralign"   title="市民卡号"/>
		    <d:col property="cardType" dictTypeId="CARD_TYPE" class="display_leftalign" title="卡类型"/>
		    <d:col property="viName" class="display_centeralign" title="访客姓名"/>
		    <d:col property="userName" class="display_centeralign" title="受访者姓名"/>
		    <d:col property="vsType" dictTypeId="VS_TYPE" class="display_centeralign" title="预约类型"/>
		    <d:col property="vsSt" class="display_centeralign"  format="{0,date,yyyy年MM月dd日 }"  title="来访时间"/>
			<d:col class="display_centeralign"  media="html" title="报表">				
				<a href="javascript:void(0);" onclick="lookRecord('${row.vsId}')">[报表]</a>&nbsp;	
			</d:col>				
		    <d:col class="display_centeralign"  media="html" title="访问轨迹">				
				<a href="javascript:void(0);" onclick="showVideo.act('${row.vsId}')">[查看]</a>&nbsp;	
			</d:col>		
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function lookRecord(id){
		var url = "${ctx}/app/caller/lookVisitors.act?vsId="+id;
		openEasyWin("winId","访客详细信息",url,"700","500",false);
	}
	function check(id){
		var url = "${ctx}/app/caller/showVideo.act?userId="+id;
		openEasyWin("winId","访客访问线路",url,"900","600",false);
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>