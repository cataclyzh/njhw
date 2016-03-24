<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资源信息录入</title>
		<%@ include file="/common/include/meta.jsp"%>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript">
		var type = '${type}';
		var canEnd = false;
		$(document).ready(function() {
			refreshResult();
			if (type == "lock"){//门锁定时刷洗状态
				top.iId = setInterval(function(){ refreshResult(); }, 500);
				setTimeout(function(){
					canEnd = true;
				}, 3000);
			}					
		});
     	function refreshResult(){
     		var info = "";
			if (type=="lock"){
				var url = "${ctx}/app/personnel/unit/showDistributeLock.act?roomId=${roomId}&isAuth=YES";
				var sucFun = function(data) {
					var tooltipContent = "";
					if (null != data && data.length > 0) {
						var permitPersons = 0;
						for (var i = 0 ; i < data.length ; i++){
							var uList = data[i].userList;
							if (null != uList){
								permitPersons += uList.length;
							}
							if (uList!=null && uList.length > 0) {
								var isEnd = true;
								for (var j = 0 ; j < uList.length ; j++){
									var id = uList[j].ID;
									var add = "";
									if (id == "0"){
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>正在授权中...</td><td></td></tr>";
										isEnd = false;
									} else if (id == "1") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>添加授权失败！</td><td>未知异常,联系系统管理员！</td></tr>";
										isEnd = false;
									} else if (id == "2") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>添加授权失败！</td><td>通信机或者门锁通信异常！</td></tr>";
									} else if (id == "3") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>删除授权失败！</td><td>通信机或者门锁通信异常！</td></tr>";
									} else if (id == "4") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>删除授权失败！</td><td>未知异常,联系系统管理员！</td></tr>";
									} else if (id == "5") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>添加授权失败！</td><td>市民卡状态异常！</td></tr>";
									} else if (id == "6") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>添加授权失败！</td><td>市民卡超出有效期！</td></tr>";
									} else if (id == "7") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>添加授权失败！</td><td>市民卡为黑名单！</td></tr>";
									} else if (id == "8") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>添加授权失败！</td><td>市民卡未开卡！</td></tr>";
									} else if (id == "9") {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>添加授权失败！</td><td> 此用户没有绑定市民卡！</td></tr>";
									} else {
										add = "<tr><td>" + $.trim(uList[j].DISPLAY_NAME) + "</td><td>已授权</td><td></td></tr>";
									} 
									tooltipContent +=  add;
								}
								if (isEnd && canEnd) {
									clearInterval(top.iId);
								}
							}
											
						}
								
					} 
				info = tooltipContent;
				$("#info_span").empty().html(info);
			};
			var errFun = function (){
				info = "刷新门锁授权信息失败!";
				$("#info_span").empty().html(info);
			};
			ajaxQueryJSON(url, null, sucFun, errFun);
			} else {
					var url = "${ctx}/app/personnel/unit/showDistributeRoom.act?roomId=${roomId}";
					var sucFun = function(data) {
						var tooltipContent = "";
						if (null != data && data.length > 0){
							for (var i = 0 ; i < data.length ; i++){
								var tel = $.trim(data[i].TEL_NUM);
								if ("" == tel) {
									tel = "未分配IP电话";
								}
								tooltipContent +=  $.trim(data[i].DISPLAY_NAME) +"["+tel+"]" + "<br/>";
							}
						}
						info = tooltipContent;
						$("#info_span").empty().html(info);
					};
					var errFun = function (){
						info = "刷新人员房间分配信息失败!";
						$("#info_span").empty().html(info);
					};
					ajaxQueryJSON(url, null, sucFun, errFun);
			}
     	}
	</script>
	</head>
	<body style="background-color: white;background-image: none;">
			<table align="center" border="0" style="width:100%;height:30px;">
				<tr>
					<td style="width:80px;text-align:right;">
						<span style="font-weight:bold">房间编号：</span>
					</td>
					<td>
						${room.name}
					</td>
					<td>
					</td>
				</tr>
			</table>
			<table align="center" border="0" style="width:100%;" id="info_span">
			</table>
			<%---
			<table align="center" border="0" style="width:100%;height:30px;">
				<tr>
					<td colspan="3" style="text-align:center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="parent.closeWindowNew(null,${roomId});">关闭</a>
					</td>
				</tr>
			</table>
			 --%>
	</body>
</html>

