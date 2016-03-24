<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: hp
- Date: 2013-06-01
- Description:日志查询
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>日志查询</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/custom/dateUtil.js" type="text/javascript"></script>
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

		
		function logSubmit(){
			$("#loadingdiv").show();
			$('a.easyui-linkbutton').linkbutton('disable');
			$("#queryForm").submit();
		}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<form id="queryForm" action="query.act" method="post"  autocomplete="off">
		<h:panel id="query_panel" width="100%" title="查询条件">		
			<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
				<tr>
					<td class="form_label" width="20%" align="left">
						设备名称：
					</td>
					<td width="30%">
			         	<s:textfield name="resName" theme="simple" maxlength="20" cssClass="input180box"/>
					</td>
					<td class="form_label" width="20%" align="left">
						操作人：
					</td>
					<td width="800">
						<s:textfield name="insertName" theme="simple" cssClass="input180box" maxlength="20"/>
					</td>											
				</tr>
<%--				<tr>--%>
<%--					<td  class="form_label">操作日期：</td>--%>
<%--					<td>--%>
<%--						<s:date id="format_invdate" name="insertDate" format="yyyy-MM-dd"/>--%>
<%--						<s:textfield name="operationDateStr" readonly="true"  value="%{format_invdate}" cssClass="input180box"/>--%>
<%--				    </td>--%>
<%--				</tr>--%>
<%----%>
				<tr class="form_bottom">
					<td colspan="4" class="form_bottom">
							<s:textfield name="page.pageSize" theme="simple" id="pageSize" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" maxlength="3"/>条/页&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" 
							onclick="logSubmit();">查询</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" 
							onclick="resetCheckboxForm('#queryForm');">重置</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
					</td>
				</tr>
			</table>
		</h:panel>
		<h:page id="list_panel" width="100%" buttons="del"  title="查询结果">		
			<d:table name="page.result" id="row"  export="false" class="table" requestURI="query.hlt">
				<d:col style="width:30" title="序号" class="display_centeralign">
					${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
				<d:col style="width:45"   class="display_centeralign" title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
					<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.BMID}"/>
				</d:col>	
			    <d:col property="RESNAME" class="display_centeralign" title="设备名称"/>
			    <d:col property="BMDETAIL" class="display_leftalign" title="操作内容"/>
<%--			    <d:col property="BMEXP2" class="display_leftalign" title="调用方向"/>--%>
			    <d:col property="INSERTNAME" class="display_leftalign" title="操作人"/>
			    <d:col property="INSERTDATE" class="display_centeralign" format="{0,date,yyyy-MM-dd HH:mm:ss }"  title="操作时间"/>
			</d:table>
	    </h:page>
	</form>
</body>
</html>
<script type="text/javascript">
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

	<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','删除成功！','info',function(){
		$('#queryForm').attr("action","query.act");
		$('#queryForm').submit();
		});
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','删除失败！','error',function(){
		$('#queryForm').attr("action","query.act");
		$('#queryForm').submit();
	});
</c:if>
</script>
<s:actionmessage theme="custom" cssClass="success"/>