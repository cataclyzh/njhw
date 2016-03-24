﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>失物招领</title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		<%@ include file="/common/include/meta.jsp"%>
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
	<style type="text/css">
.caller_center span{
	cursor: pointer;
}
.caller_index_num{
	width:5%;
	padding-left:1%;
	display:inline-block;
}
.caller_index_title{
	width:30%;
	padding-left:1%;
	display:inline-block;
}
.caller_index_time{
	width:15%;
	padding-left:1%;
	display:inline-block;
}
.caller_index_status{
	width:15%;
	padding-left:20px;
	display:inline-block;
}
.caller_index_people{
	width:15%;
	padding-left:2%;
	display:inline-block;
}
.caller_centers{
	padding-left:10px;
	height:30px;
	line-height:30px;
	background:url(../images/border_00_7.jpg) left bottom repeat-x;
}
.caller_centers a{
	cursor: pointer;
	width:30%;
	display:inline-block;
	color:#999999;
	padding-left:0px;
}
</style>
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
						失物招领
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
					<div class="main1_main2_right">
						<ul>
							<div class="caller_index">
								<span class="caller_index_num">序号</span>
								<span class="caller_index_title">标题</span>
								<span class="caller_index_time">发布时间</span>
								<span class="caller_index_status">状态</span>
							</div>
			    			<c:if test="${not empty list}">
								<c:forEach items="${list}" var="listDate" varStatus="status">
									<li>
										<div class="caller_centers">
											<input type="hidden" id="mid" value="${listDate.MID } "/>
											<span class="caller_index_num">${status.index + 1}</span>
											<a style="cursor: pointer;" onclick="showInfo(${listDate.ID})" class="caller_index_title">${listDate.TITLE}</a>
											<span class="caller_index_time">${listDate.CREATETIME}</span>
											<span class="caller_index_status">${listDate.CART_FLAG eq '1' ? '认领' : '未认领' }</span>
										</div>
									</li>
								</c:forEach>
							</c:if>
						</ul>
						<div class="clear"></div>
					</div>
				    <!--此处写页面的内容 -->
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
	</body>
</html>
<script type="text/javascript">
	//分页附属方法
	function goPage(a){
		var url = "${ctx}/message/queryMsgList.act?page="+(a);
		window.location.href = url;
	}

	function showInfo(vsid){
		 var url="${ctx}/app/cms/channelcms/searchCmsDetail.act?id="+vsid;
		 //openEasyWin('DetailId','失物招领',url,'700','450',true);
		 showShelter("700", "450", url);
	}
	
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					var result = new Array(); 
					$("div [id^='checked_0']").each(function(){
						if($(this).is(":checked")){
							result.push($(this).attr("value"));
						}
					});	
					var msgId = result.join(",");
					url = "delMess.act?msgIds="+msgId;
					window.location.href = url;
					//$('#queryForm').attr("action","msgBoxAction_delMess.act");
					//$('#queryForm').submit();
				}
			});		
		}
	}
	var flag = false;
	// CHECKED 全选效果
	function checkAll(){
		//var flag = $("div [id^='checked_0']").attr("checked");
		
		if(flag){
			//取消选中
			/* $("div [id^='checked_0']").each(function(){
				$(this).attr('checked',!$(this).attr('checked'));
			}); */
			$("div [id^='checked_0']").attr("checked",false);
			flag = false;
		}else{
			//选中全部
			$("div [id^='checked_0']").attr("checked",true);
			flag = true;
		}
	}
</script>