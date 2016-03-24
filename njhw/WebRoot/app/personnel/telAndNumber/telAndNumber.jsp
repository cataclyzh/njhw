﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>电话传真管理 </title>
		<script type="text/javascript">
			var _ctx = '${ctx}';
		</script>
		<link type="text/css" rel="stylesheet" href="css/TelMessage.css" />
		<link type="text/css" rel="stylesheet" href="css/table.css" />
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
		<script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
		<script type="text/javascript">
			/**
			*	查询电话和号码分配
			*/
			function searchTelAndNum(){
				var url = "${ctx}/app/personnel/telAndNumber/telAndNumberList.act";
				var telNum = $.trim($("#telNum").val());
				var disStatus = $("input[name = 'disStatus']:checked").val();
				var resType = $("#resType").val();
				var orgId = $("#orgId").val();
				var permit = $("#permit").val();
				var pageNo = $("#pageNo").val();
				var displayName = $.trim($("#displayName").val());
				var pageSize = 10000000;
				var data = {
					telNum:telNum,
					disStatus:disStatus,
					orgId:orgId,
					permit:permit,
					resType:resType,
					pageNo:pageNo,
					pageSize:pageSize,
					displayName:displayName
				};
				sucFun = function (data){
					$("#tel_num_list_div").html(data);
					$("#allocatedTel").text($("#allocatedTelHidden").val());
					$("#unAllocatedTel").text($("#unAllocatedTelHidden").val());
					$("#totalTel").text($("#totalTelHidden").val());
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
			}
			
			/*
			分页查询
			*/
			function searchPageTelAndNum(){
				var url = "${ctx}/app/personnel/telAndNumber/telAndNumberList.act";
				var telNum = $("#telNumHidden").val();
				var disStatus = $("disStatusHidden").val();
				var resType = $("#resTypeHidden").val();
				var orgId = $("#orgIdHidden").val();
				var permit = $("#permitHidden").val();
				var displayName = $("#displayNameHidden").val();
				var pageNo = $("#pageNo").val();
				var data = {
					telNum:telNum,
					disStatus:disStatus,
					orgId:orgId,
					permit:permit,
					resType:resType,
					pageNo:pageNo,
					displayName:displayName
				};
				sucFun = function (data){
					$("#tel_num_list_div").html(data);
					$("#allocatedTel").text($("#allocatedTelHidden").val());
					$("#unAllocatedTel").text($("#unAllocatedTelHidden").val());
					$("#totalTel").text($("#totalTelHidden").val());
				};
				errFun = function (data){
					easyAlert('提示信息','加载数据出错!','info');
				};
				ajaxQueryHTML(url,data,sucFun,errFun);
			}
			
			$(function(){
				//初始查询电话分配列表
				searchTelAndNum();
			});
			
			function goPage(pageNo){
				if (null != pageNo){
					$("#pageNo").val(pageNo);
				}
				searchPageTelAndNum();
			}
			/**
			* 回收话机
			*/
			function revertDistrbute(telID){
				easyConfirm('提示', '当前号码正使用，是否确认回收其使用权？', function(r){
					if (r){
						if (null == telID || telID < 1) {
							return;
						}
						var url = "${ctx}/app/personnel/telAndNumber/revertDistrbute.act";
						var data = {telID:telID};
						sucFun = function (data){
							if (data.result == "1") {
								easyAlert('提示信息','话机回收成功!','info');
								searchTelAndNum();
							} else {
								easyAlert('提示信息','话机回收失败!','info');
							}
						};
						errFun = function (data){
							easyAlert('提示信息','加载数据出错！','info');
						};
						ajaxQueryJSON(url,data,sucFun,errFun);
					}
				});
			}
			/**
			* 变更分配话机
			*/
			function modifyDistrbute(userId,telID,telType){
					if (null == userId || userId < 1 || null == telID || telID < 1) {
							return;
					}
					var url = "${ctx}/app/personnel/telAndNumber/modifyDistrbute.act?telID="+telID+"&userId="+userId+"&telType="+telType;
					//showShelter('600','400',url);
					windowDialog("<span style='color:#808080;font-family:\"微软雅黑\";font-size:14px;font-weight:bold;'>电话变更</span>",url,100,'750','320');
	
			}
			/**
			* 分配话机
			*/
			function distribute(telID,telType){
				if (null == telID || telID < 1) {
					return;
				}
				var url = "${ctx}/app/personnel/telAndNumber/distribute.act?telID="+telID+"&telType="+telType;
				//showShelter('600','400',url);
				windowDialog("<span style='color:#808080;font-family:\"微软雅黑\";font-size:14px;font-weight:bold;'>电话新增</span>",url,100,'750','320'); 
			}
			
			/**
			* 关闭弹出框刷新数据
			*/
			function frameDialogClose(){
				searchTelAndNum();
			}
			
			function btnSearchTelAndNum(){
				$("#pageNo").val(1);
				//初始查询电话分配列表
				searchTelAndNum();
			}
			
			/**
			* 导出清单
			*/
			function exportDetail() {
				
			}
			
			function windowDialog(title,url,top,w,h){
				$("#companyWin").show();
				var left = (document.body.scrollWidth - w) / 2;
				$("#companyWin").window({
					title : title,
					modal : true,
					shadow : false,
					closed : false,
					width : w,
					height : h,
					top : top,
					left : left,
					onBeforeClose:function(){
						searchTelAndNum();
					}
				});
				$("#companyIframe").attr("src", url);
			}
			
		</script>
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
						电话传真管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px;height: auto;">
				    <!--此处写页面的内容 -->
				    <h:panel id="query_panel" width="100%" title="查询条件">
				    <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
						<tbody>
							<tr style="height: 30px">
								<td style="width:5%;text-align:right;">
									<span>资源类型</span>				
								</td>
								<td style="width:15%">
									<select id="resType" name="resType" style="width: 200px; height: 26px">
										<option value="">全部</option>
										<option value="1">IP电话</option>
										<option value="2">传真</option>
										<option value="3">网络传真</option>
									</select>
							  	</td>
							   	<td style="width: 5%;text-align:right;">
									<span>号码</span>				
							   	</td>
							   	<td style="width:15%">
								  <input id="pageNo" value="1" type="hidden">
								  <input id="telNum" style="width: 200px; height: 26px" type="text">
							    </td>
								<td style="width: 5%;text-align:right;">
									<span>单位处室</span>
								</td>
								<td style="width: 15%;text-align:left;">
									<select id="orgId" name="orgId" style="width: 200px; height: 26px">
										<option value="">全部</option>
										<c:forEach items="${orgList}" var ="org">
											<option value="${org.orgId}">${org.name}</option>
										</c:forEach>
									</select>
								</td>
								<td style="width: 5%;text-align:right;">
									<font face="Agency FB"><span>使用人员</span></font>				
								</td>
								<td style="width:15%">
									<input id="displayName" style="width: 200px; height: 26px" type="text">
							 	 </td>
							</tr>
							<tr style="width: 16%;text-align:right;">
								<td style="width: 5%;text-align:right;">
									<span>分配状态</span>
								</td>
								<td style="width: 20%;text-align:left;">
									<table width="100%" border="0">
							  			<tr>
											<td align="center" style="width: 70px">
												<input name="disStatus" checked="checked" value="0" type="radio">
												<span style="font-size: 13px; color: #4c83ec;">全部</span>
										  </td>
											<td width="273" align="center" style="width: 70px">
												<input name="disStatus" value="1" type="radio">
												<span style="font-size: 13px; color: #4c83ec;">已分配</span>
										  </td>
											<td width="258" align="center" style="width: 70px">
												<input name="disStatus" value="2" type="radio">
												<span style="font-size: 13px; color: #4c83ec;">未分配</span>
										  </td>
							  			</tr>
									</table>
								</td>
								<td style="width: 25%;text-align:left;" colspan="4">
									总电话数: <span id="totalTel">0</span>
									已分配数：<span id="allocatedTel">0</span>
									未分配数：<span id="unAllocatedTel">0</span>
								</td>
								<td width="25" colspan="2" align="right" valign="middle" style="padding-right: 30px;">
									<div class="table_btn1" style="float: right;"  onClick="javascript:btnSearchTelAndNum();">查询</div>
								</td>							
							</tr>
						</tbody>
					</table>
					</h:panel>
					<div class="main_right_bottom" id="tel_num_list_div" style="overflow: auto;"></div>
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
	</body>
</html>