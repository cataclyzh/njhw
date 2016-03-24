<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: sqs
- Date: 2013-3-19 10:12:11
- Description: 机构列表页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>机构列表页面</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0">
<form  id="queryForm" action="getPageList.act"   method="post"  autocomplete="off" >
	<input type="hidden" value="${orgId}"  name="orgId"/>
	<h:page id="list_pane" width="100%"  buttons="save"  title="结果列表">
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col   style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
			    <c:if test="${row.YN eq 'y'}">
					<input type="checkbox" checked="checked" name="_chk" id="_chk" class="checkItem" value="${row.NODE_ID}"/>
			    </c:if>
			    <c:if test="${row.YN eq 'n'}">
					<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.NODE_ID}"/>
			    </c:if>
			  	 
			</d:col>
		    <d:col property="BUILD_NAME" class="display_leftalign" 	title="楼座名称"/>
		    <d:col property="FLOOR_NAME" class="display_leftalign" 	title="楼层名称"/>
  		<%--<d:col property="ROOM_NAME"  class="display_leftalign" 	title="房间名称"/> --%>
		    <d:col property="NAME"       class="display_leftalign" 	title="门禁名称"/>
		</d:table>
   	</h:page>
   </form>
   
<script type="text/javascript">
	
    function save(nodeId){
	    var nodeId = new Array();
		    $("input[id^='_chk']:checked").each(function(i) {
					nodeId.push($(this).val());
			});
			$('#queryForm').attr("action","doorCardSave.act?nodeId="+nodeId);
			$('#queryForm').submit();
		}
		
     //修改
     function edit(nodeId){
     	var nodeId = new Array();	
     	 $("input[id^='_chk']:checked").each(function(i) {
				nodeId.push($(this).val());
			});
     	$('#queryForm').attr("action","doorCardSave.act?nodeId="+nodeId+"&orgId="+$("#orgId").val());
		$('#queryForm').submit();
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
		
		<c:if test="${isSuc=='true'}">
			easyAlert('提示信息','保存成功！','info');
		//	easyAlert('提示信息','保存成功！','info', function(){closeEasyWin('winId');});
		</c:if>
		<c:if test="${isSuc=='false'}">
			easyAlert('提示信息','保存失败！','error');
		</c:if>
		
</script>
</body>
</html>