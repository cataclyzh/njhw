﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="p" %>

<html>
<head>
<%@ include file="/common/include/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的通知</title>
<script type="text/javascript">
	var _ctx = '${ctx}';
</script>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
	rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/basic/jquery.js.gzip"
	type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
	type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<link href="${ctx}/app/personnel/telAndNumber/css/TelMessage.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
</head>
<body>
	<div class="main_index">
		<jsp:include page="/app/integrateservice/header.jsp">
			<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
		</jsp:include>
		<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div class="bgsgl_border_left">
			 <!--此处写页面的标题 -->
				<div class="bgsgl_border_right">
					我的通知
				</div>
			</div>
			<div class="bgsgl_conter" style="min-height: 300px">
			    <!--此处写页面的内容 -->
				<div class="main1_main2_right">
					<ul>
						<div class="main_main2_list">
							<input id="totalCheckBox" style="cursor: pointer;" name="" type="checkbox" value="" onclick="checkAll()"/>
							<span class="main3_list1">主题</span>
							<span class="main3_list1">内容摘要</span>
							<span class="main3_list2">发送时间</span>
							<span class="main3_list2">发件人</span>
							<span class="main3_list2"> 信息状态</span>
						</div>
						<c:if test="${not empty list }">
							<c:forEach items="${list }" var="listDate" varStatus="status">
								<li>
									<div class="main3_cen_list">
										<input id="checked_0${status.index }" name="" type="checkbox" value="${listDate.MSGID }"/>
										<a class="main3_list" onclick="showInfo('${listDate.MSGID}')" style="cursor: pointer;">${fn:substring(listDate.TITLE,0,30) }</a><span style="cursor: pointer;" class="main3_lists1" onclick="showInfo('${listDate.MSGID}')">${fn:substring(listDate.CONTENT,0,30) }</span><span style="cursor: pointer;" class="main3_list3" onclick="showInfo('${listDate.MSGID}')">${listDate.T }</span><span style="cursor: pointer;" class="main3_list3" onclick="showInfo('${listDate.MSGID}')">${empty listDate.USERNAME ? listDate.SENDER : listDate.USERNAME}</span>
										<c:if test="${listDate.STATUS == 1}">
										<span style="cursor: pointer;" class="caller_refuse" id="${listDate.MSGID }Yd" onclick="showInfo('${listDate.MSGID}')">[已读</c:if><c:if test="${listDate.STATUS == 0}">
										<span style="cursor: pointer;" class="main3_list4" id="${listDate.MSGID }w" onclick="showInfo('${listDate.MSGID}')">
										[未读</c:if>]</span>
										<span id="${listDate.MSGID }Y" class="caller_refuse" onclick="showInfo('${listDate.MSGID}')"></span></div>
								</li>
							</c:forEach>
						</c:if>
					</ul>
					<div class="main_pag"><a style="cursor: pointer;" onclick="delRecord();">删除选中信息</a></div>
					<div class="main_peag" style="height:30px;">
						<p:pager recordCount="${recordCount}" pageSize="10" pageNo="${pageNo}" recordShowNum="4" url="${ctx}/app/personnel/telAndNumber/telAndNumberList.act"/>
					</div>
					<div class="clear"></div>
				</div>
			    
				<div class="bgsgl_clear"></div>
			</div>
		</div>
		<jsp:include page="/app/integrateservice/footer.jsp" />
	</div>
</body>
</html>
<script type="text/javascript">
	function showInfo(msgId) {
		$("#"+msgId+"Y").text("[已读]");
		$("#"+msgId+"w").css('display','none');
		$("#"+msgId+"Yd").css('display','none');
		var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
		//openEasyWin('winId','查看消息',url,'700','500',true);
		showShelter("700", "500", url);
		$("#content_"+msgId).css("fontWeight", "");
	}
	
	function goPage(a){
		var url = "${ctx}/message/queryMsgList.act?page="+(a);
		window.location.href = url;
	}
	
	var resetCheckBox = function(){
		var totalCheckBox = $("input[id*='checked_0']").size();
		var checkboxSize = $("input[id*='checked_0']:checked").size();
		//alert(checkboxSize);
		if(checkboxSize == totalCheckBox){
			$("#totalCheckBox").attr("checked", true);
			//$("input[id*='checked_0']").attr("checked", true);
		}else{
			$("#totalCheckBox").attr("checked", false);
		}
	}
	
	function checkAll(){
		var totalCheckBox = $("input[id*='checked_0']").size();
		var checkboxSize = $("input[id*='checked_0']:checked").size();
		if(checkboxSize == totalCheckBox){
			$("#totalCheckBox").attr("checked", false);
			$("input[id*='checked_0']").attr("checked", false);
		}else{
			$("#totalCheckBox").attr("checked", true);
			$("input[id*='checked_0']").attr("checked", true);
		}
	}
	
	$(document).ready(function(){
		$("input[id*='checked_0']").bind("click", resetCheckBox);
	});

	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			//using('messager', function () {
				//$.messager.alert('提示信息','请勾选要删除的记录！','info');
				//
			//});
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			//using('messager', function () {
				easyConfirm('提示', '确定删除？', function(r){
					if (r){
						var result = new Array(); 
						$("div [id^='checked_0']").each(function(){
							if($(this).is(":checked")){
								result.push($(this).attr("value"));
							}
						});	
						var msgIds = result.join(",");
						url = "${ctx}/message/delMess.act?msgIds="+msgIds;
						window.location.href = url;
						//$('#queryForm').attr("action","msgBoxAction_delMess.act");
						//$('#queryForm').submit();
					}
				});
		}
	}
</script>