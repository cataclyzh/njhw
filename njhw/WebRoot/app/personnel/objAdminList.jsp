<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp"%>
<title>组织机构列表</title>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/personnel/css/adminUser.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<link href="${ctx}/app/personnel/telAndNumber/css/TelMessage.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
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
	function getObjAdminUserTreeList(){
		//var url = "${ctx}/app/per/orgUserTree.act?roomId="+roomId+"&dtype=room";
		var url = "${ctx}/app/per/orgAdminUserTree.act?nodeId="+$("#nodeId").val();
		showShelter('500','400',url);
	}

	function windowDialog(title, url, w, h, roomId) {
		var body = window.document.body;
		var left = body.clientWidth / 2 - w / 2;
		var top = body.clientHeight / 2 - h / 2;
		var scrollTop = document.body.scrollTop;
		//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
		top = top + scrollTop;
		$("#companyWin").show();
		$("#companyWin").window( {
			title : title,
			modal : true,
			shadow : false,
			closed : false,
			width : w,
			height : h,
			top : top,
			left : left,
			onBeforeClose : function() {
			}
		});
		$("#companyIframe").attr("src", url);
	}
	
	function closeWindow(type,roomId){
		$("#companyWin").window("close");
	}

	function windowDialogNew(title, url, w, h, roomId) {
		var body = window.document.body;
		var left = body.clientWidth / 2 - w / 2;
		var top = body.clientHeight / 2 - h / 2;
		var scrollTop = document.body.scrollTop;
		//alert("left:"+left+"||top:"+top+"||scroll:"+scrollTop);
		top = top + scrollTop;
		$("#companyWin").show();
		$("#companyWin").window( {
			title : title,
			modal : true,
			shadow : false,
			closed : false,
			width : w,
			height : h,
			top : top,
			left : left,
			onBeforeClose : function() {
			}
		});
		$("#companyIframe").attr("src", url);
	}
</script>
<style>
	.table_btn_orgframe
	{
	    width: 68px;
	    height: 22px;
	    background:#8090b2;
		color:#fff;
		line-height:22px;
		text-align:center;
		font-family:"微软雅黑";
		margin-right:10px;
		float:right;
		cursor:pointer;
		text-decoration: none;
	}
</style>	
</head>

<body style="background:#fff;">
<div id="list"></div>
<div class="from_list_table_f">
<form id="queryForm" action="objAdminList.act" method="post"  autocomplete="off" style="height: 420px;">
<input type="hidden" id="nodeId" value="${nodeId }" />
	<table class="table">
		<tr>
			<td class="from_list_td1 from_background"><input type="checkbox" onclick="checkAll()" value=""/></td>
			<td class="from_list_td2 from_background">姓名</td>
			<td class="from_list_td2 from_background">类型</td>
		</tr>
	</table> 	
    <table class="table">
	   <c:if test="${not empty list}">
			<c:forEach items="${list }" var="listDate" varStatus="status">
			<tr class="from_tr_list">
				<td class="from_list_td1"><input type="checkbox" name="_chk" id="checked_0${status.index }" value="${listDate.USERID }" /></td>
				<td class="from_list_td2">${listDate.DISPLAY_NAME }</td>
				<c:choose>
					<c:when test="${listDate.TYPE == 'user'}">
					<td class="from_list_td2">用户</td>
					</c:when>
					<c:when test="${listDate.TYPE == 'org'}">
					<td class="from_list_td2">机构</td>
					</c:when>
				</c:choose>
			</tr>
			</c:forEach>
	   </c:if>
   </table>
   <div style="width: 100%;margin:10px 0;position: relative;">
   		<a href="javascript:void(0);" class="from_tr_list_trst_a" onclick="getObjAdminUserTreeList();">添加</a>
   		<a href="javascript:void(0);" class="from_tr_list_trst_a" onclick="delRecord()">删除</a>
   </div>
   <div id='companyWin' class='easyui-window' collapsible='false'
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<iframe id='companyIframe' name='companyIframe'
			style='width: 100%; height: 100%;' frameborder='0'></iframe>
	</div>
</form>
</div>
<script type="text/javascript">
var flag = false;
// CHECKED 全选效果
function checkAll(){
	if(flag){
		$("td [id^='checked_0']").attr("checked",false);
		flag = false;
	}else{
		//选中全部
		$("td [id^='checked_0']").attr("checked",true);
		flag = true;
	}
}
	
	function addRecord(){
		var url = "${ctx}/app/per/objInput.act?PId=" +$('#PId').val() +"&res="+$('#res').val() ;
		showShelter("800","450",url);
	}
	function delRecord(){
		
		//var items = $("input[name='_chk']:checked");
		//var chkArray = new Array();
				//for(var i=0;i<items.size();i++){
			//		chkArray.push(items.eq(i).val());
			//	}
		
		if($("input[@type=checkbox]:checked").size()==0){
			//using('messager', function () {
			//	$.messager.alert('提示信息','请勾选要删除的记录！','info');
			//});
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			//using('messager', function () {
				easyConfirm('提示', '确定删除？', function(r){
					if (r){
						var result = new Array(); 
						$("td [id^='checked_0']").each(function(){
							if($(this).is(":checked")){
								result.push($(this).attr("value"));
							}
						});	
						for(var i=0;i<result.length;i++)
						{
							if(result[i]=='1')
							{
								alert("禁止删除系统管理员!");
								return;
							}
						}
						var msgIds = result.join(",");
						//alert(msgIds);
						url = "${ctx}/app/per/delObjAdminUser.act?ids="+msgIds+"&nodeId="+$("#nodeId").val();
						window.location.href = url;
						//$('#queryForm').attr("action","msgBoxAction_delMess.act");
						//$('#queryForm').submit();
					}
				});
			}
		}
		
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>