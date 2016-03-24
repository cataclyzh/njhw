<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-18 10:12:11
- Description: 来访列表页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>来访列表页面</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0"  style="min-width: 1000px;">
<form id="queryForm" action="list.act"  method="post"  autocomplete="off" >
	<h:panel id="query_panel" width="100%" title="查询条件">
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
		<tr>
			<td class="form_label" > <font style="color: red"></font>预约时间段： </td>
			<td>
				<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="beginTime" name="beginTime" cssClass="Wdate" value="${beginTime}"/> - 
				<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="endTime" name="endTime" cssClass="Wdate" value="${endTime}"/>
			</td>
			<td class="form_label">访问状态：</td>
			<td><s:select list="#application.DICT_USER_VISIT_FLAG" headerKey="" headerValue="全部" listKey="dictcode" listValue="dictname" name="flag"/></td>
		</tr>
		<tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
	  </table>
	</h:panel>

	<h:page id="list_panel" width="100%" title="结果列表">
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
		    <d:col property="VI_NAME" class="display_leftalign"   title="访客名称"/>
		    <d:col property="USER_NAME" class="display_leftalign" title="受访者名称"/>
		    <d:col property="ORG_NAME"  class="display_leftalign"  title="部门"/>
		    <d:col property="VSST"   class="display_centeralign"  title="开始日期"/>
		    <d:col property="VSET"   class="display_centeralign"  title="结束日期"/>
		    <d:col property="VS_FLAG" dictTypeId="USER_VISIT_FLAG" class="display_centeralign" title="来访状态"/>
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="show('${row.VS_ID}')">[查看]</a>&nbsp;
				<c:if test="${row.VS_FLAG == '02'}">
					<a href="javascript:void(0);" onclick="showCancel('${row.VS_ID}')">[取消]</a>&nbsp;
				</c:if>
			</d:col>
		</d:table>
   </h:page>
   </form>
   
<script type="text/javascript">
    // 显示访问申请
	function show(vsid){
		var url = "${ctx}/app/myvisit/showOpt.act?vsid="+vsid;
		openEasyWin("winId","访问申请",url,"700","500",true);
	}
	
	// 取消访问
	function showCancel(vsid){
		var url = "${ctx}/app/myvisit/showOptCancel.act?vsid="+vsid;
		openEasyWin("wincancel","取消访问",url,"700","500",true);
	}
</script>
</body>
</html>