<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
  <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
    <script type="text/javascript">

       function allchek(){
    	   
    	    $("#listMessage :checkbox").each(function() {
    					$(this).attr("checked", !$(this).attr("checked"));
    				});
       }
       
</script>
    
  <div  id="listMessage">
  <div style="width: 260px; padding-top: 85px;"><input type="checkbox" id="all" name="all" onclick="allchek()"/> 全选</div>
 <table  id = "tableId"  style="float:left; margin-top: 10px; width: 260px;height: 290px; border:1px solid #8895c4; background-image: url('${ctx}/app/integrateservice/messagingplatform/images/table.png'); ">
   
 	<c:forEach var="row" items="${page.result}" varStatus="stat" begin="0" end="6">
 	
 	<tr><td><input type="checkbox"  name="checkbox"  value="${row.SMSID}"/><a href="javaScript:repeatNum('${row.SMSID}')"> 
 	&nbsp;&nbsp;&nbsp; ${row.CONTENT} </a></td></tr>
 	</c:forEach>
 	
 
 </table>
 	<jsp:include page="paging.jsp" flush="true">
		<jsp:param name="type" value="0" />
		<jsp:param name="position" value="down" />
	</jsp:include>
</div>