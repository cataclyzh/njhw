<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-18 10:12:11
- Description: 报修单列表页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>报修单列表页面</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0"  style="min-width: 1000px;">
<form  id="queryForm" action="list.act"  method="post"  autocomplete="off" >
	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
		<tr>
			<td class="form_label">申请人名称：</td>
			<td><s:textfield name="userName"></s:textfield></td>
			<td class="form_label" > <font style="color: red"></font>申请日期： </td>
			<td>
				<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="beginTime" name="beginTime" cssClass="Wdate" value="${beginTime}"/> - 
				<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="endTime" name="endTime" cssClass="Wdate" value="${endTime}"/>
			</td>
			<td class="form_label">状态：</td>
			<td><s:select list="#application.DICT_SHEET_STATUS" headerKey="" headerValue="全部" listKey="dictcode" listValue="dictname" name="crfFlag" id="crfFlag"/></td>
		</tr>
		<tr>
          <td colspan="6" class="form_bottom">
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
		    <d:col property="USER_NAME" class="display_leftalign" 	title="申请人"/>
		    <d:col property="CRF_TEL" 	class="display_leftalign" 	title="联系电话"/>
		    <d:col property="ORG_NAME" 	class="display_leftalign" 	title="部门名称"/>
		    <d:col property="EAS_NAME" 	class="display_leftalign" 	title="报修设备"/>
		    <d:col property="CRF_BAK2" 	class="display_leftalign" 	title="维修人"/>
		    <d:col property="CRF_RT"   	class="display_centeralign" format="{0,date,yyyy-MM-dd }" title="报修日期"/>
		    <d:col property="CRF_FLAG"	dictTypeId="SHEET_STATUS" 	class="display_centeralign" title="状态"/>
			<d:col class="display_centeralign"  media="html" title="操作">
				<c:if test="${row.CRF_FLAG eq '01'}">
					<a href="javascript:void(0);" onclick="allot('${row.CRF_ID}')">[派单]</a>&nbsp;
					<!-- 只能删除自己提交的报修单 -->
					<c:if test="${user_id == row.USER_ID}"><a href="javascript:void(0);" onclick="deleteSheet('${row.CRF_ID}')">[删除]</a>&nbsp;</c:if>
				</c:if>
				<c:if test="${row.CRF_FLAG eq '02'}"><a href="javascript:void(0);" onclick="complete('${row.CRF_ID}')">[完成]</a>&nbsp;</c:if>
				<!-- 只能对自己提交的报修单进行评价-->
				<c:if test="${row.CRF_FLAG eq '03' && user_id == row.USER_ID}"><a href="javascript:void(0);" onclick="assess('${row.CRF_ID}')">[评价]</a>&nbsp;</c:if>
				<a href="javascript:void(0);" onclick="showSheet('${row.CRF_ID}')">[查看]</a>&nbsp;
			</d:col>
		</d:table>
   	</h:page>
   </form>
   
<script type="text/javascript">
	$(function() {
		$("#crfFlag").val("${crfFlag}");
	});
    // 分派
   function allot(crfid){
		var url = "${ctx}/app/pro/optShow.act?crfid="+crfid+"&opt=allot";
		openEasyWin("winId","报修单-派单",url,"700","400",true);
	}
	
	// 完成
   function complete(crfid){
		var url = "${ctx}/app/pro/optShow.act?crfid="+crfid+"&opt=complete";
		openEasyWin("winId","报修单-完成",url,"700","500",true);
	}
	
	// 评价
   function assess(crfid){
		var url = "${ctx}/app/pro/optShow.act?crfid="+crfid+"&opt=assess";
		openEasyWin("winId","报修单-评价",url,"700","600",true);
	}
	
	// 查看
   function showSheet(crfid){
		var url = "${ctx}/app/pro/showSheet.act?crfid="+crfid;
		openEasyWin("winId","报修单-查看",url,"700","500",true);
	}
	
	// 删除
	function deleteSheet(crfid){
		 easyConfirm("提示信息", "是否删除编号为："+crfid+"的报修单！", function(r){
		  if (r) {
		  	$.ajax({
				type: "POST",
				url: "${ctx}/app/pro/delete.act",
				data: "crfid="+crfid,
				dataType: 'text',
				async : false,
				success: function(msg){
					  if(msg == "true"){
						easyAlert("提示信息", "删除完成！", "info", function(){querySubmit();} );
					  } else if(msg == "false"){
						easyAlert("提示信息", "此报修单当前不是“申请”状态，无法删除！", function(){querySubmit();} );
					  }
				 },
				 error: function(msg, status, e){
					 easyAlert("提示信息", "删除出错！","info");
				 }
			 });
		  }
		});
	}
</script>
</body>
</html>