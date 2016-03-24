<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
  <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
    <script type="text/javascript">	
	    function repeatNum(id){
	   
	       var url = "${ctx}/app/messagingplatform/checkOutbox.act";
				var sucFun = function(data) {
					$("#message_min").replaceWith(data);
				};
				var errFun = function() {
					alert("加载数据出错!");
				};
				var data = "smid=" + id;
				ajaxQueryHTML(url, data, sucFun, errFun);
	    	}
    	
    	function queryPhoneMessage(){
			var messageName = $("#messageName").val();
			var pageNo = $("#pageNo").val();
		    //	alert(messageName);
			//	if(messageName == null || messageName == ""){
			//		alert("请输入内容！");
			//		return false;
			//	}
			var url = "${ctx}/app/messagingplatform/findMessageButton1.act";
			var sucFun = function(data) {
				$("#listMessage").empty().html(data);
			};
			var errFun = function() {
				alert("加载数据出错!");
			};
			var data = "messageName=" + messageName+"&pageNo="+pageNo;
			ajaxQueryHTML(url, data, sucFun, errFun);
    	}	
    
    function returnon(obj){
		obj.src="${ctx}/app/integrateservice/messagingplatform/images/returnshow.png";
	}
	function returnout(obj){
		obj.src="${ctx}/app/integrateservice/messagingplatform/images/return-def.png";
	}
	  function delMegson(obj){
		obj.src="${ctx}/app/integrateservice/messagingplatform/images/del_showss.png";
	}
	function delMegsout(obj){
		obj.src="${ctx}/app/integrateservice/messagingplatform/images/del-delfa.png";
	}
 
    function reutenPage(){
		  var url = "${ctx}/app/messagingplatform/reutenPage.act";
			var sucFun = function(data) {
				$("#message_min").replaceWith(data);
			};
			var errFun = function() {
				alert("加载数据出错!");
			};
			ajaxQueryHTML(url, null, sucFun, errFun);

	}
	
    function delMegs(){
    //  alert($("input:checked[id='checkbox']"));
    
     var str="";
    var textstr="";
    $("#tableId input[name='checkbox']").each(function(){
    var isCheck = $(this).attr("checked");
	if (isCheck) {
     str+=$(this).val()+",";
      }
    })
  textstr = str.substr(0, str.length - 1);
  
    
    var url = "${ctx}/app/messagingplatform/deleteOutBoxMessages.act";
			var sucFun = function(data) {
				$("#listMessage").empty().html(data);
			};
			var errFun = function() {
				alert("加载数据出错!");
			};
			var data = "idNum=" + textstr;
			ajaxQueryHTML(url, data, sucFun, errFun);
    
    }
    function allchek(){
  ///  $("#listMessage input[type=checkbox]").attr("checked",'true');
    
    $("#listMessage :checkbox").each(function() {
				$(this).attr("checked", !$(this).attr("checked"));
			});
    }
    	
    function goPage(pageNo){
		$("#pageNo").val(pageNo);
    	queryPhoneMessage();
    }
    </script>
   
<div id="message_min"  style="margin: 62px 0px 0px 22px ; background: url('${ctx}/app/integrateservice/messagingplatform/images/default.png'); width: 261px;height: 444px;">
<div style="float: left;margin-top: 7px;"  id="imgSrc"><img src="${ctx}/app/integrateservice/messagingplatform/images/return-def.png" onclick="reutenPage(this)" onmouseover="returnon(this)"  onmouseout="returnout(this)"/></div>
<div style="float:left; margin-left:40px; margin-top: 7px"><img src="${ctx}/app/integrateservice/messagingplatform/images/yifa1.png"/></div>
<div style=" float:left; margin-left: 38px;margin-top: 7px"><img src="${ctx}/app/integrateservice/messagingplatform/images/del-delfa.png" onclick="delMegs()" onmouseover="delMegson(this)"  onmouseout="delMegsout(this)"/></div>
 
<div style="width: 260px; height:44px;  position: absolute; top:125px; left:650px; margin-left: -100px;  ">
<input type="hidden" name="pageNo" id="pageNo" value="1">
 <input type="text" name="messageName" id="messageName" style="background:url('${ctx}/app/integrateservice/messagingplatform/images/sosok.png') top left; background-color: none; border:0;  width:181px; height:27px; flot:left;" />
 <img onclick="javaScript:queryPhoneMessage();" src="${ctx}/app/integrateservice/messagingplatform/images/select-bu.png" style="position: absolute;"/> 
 </div>
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
 </div>
 
 