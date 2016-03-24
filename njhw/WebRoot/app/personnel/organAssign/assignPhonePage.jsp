<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: sqs
- Date: 2013-3-19 10:12:11
- Description: IP电话列表页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>IP电话列表页面</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0">
<form id="queryForm" action="assignPhone.act" method="post"  autocomplete="off">
    <s:hidden name="telId"/>
    <input type="hidden" value="${orgId}"  id="orgId"/>
	<h:page id="list_panel" width="100%"  buttons="save"  title="结果列表">
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:15" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col   style="width:16"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.TEL_ID}"/>
			</d:col>
		    <d:col style="width:69" property="TEL_NUM" class="display_leftalign" title="IP电话"/>
		</d:table>
   	</h:page>
</form>
<script>
	function save(telId){
	    var telId = document.getElementById("_chk").value;
	    var tt = new Array();
	    
	    if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要分配的记录！','info');
	    }else{
		    $("input[id^='_chk']:checked").each(function(i) {
					tt.push($(this).val());
			});
			$('#queryForm').attr("action","phoneSave.act?telId="+tt+"&orgId="+$("#orgId").val());
						$('#queryForm').submit();
        }
     }
     
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
</body>
</html>