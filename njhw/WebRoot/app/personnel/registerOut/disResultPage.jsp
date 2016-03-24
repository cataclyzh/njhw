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
		<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript">
		var type = '${type}';
		var iId = '';
		var canEnd = false;
		$(document).ready(function() {
			refreshResult();
			if (true){//门锁定时刷洗状态
				iId = setInterval(function(){ refreshResult(); }, 500);
				refreshResult();
				setTimeout(function(){
					canEnd = true;
				}, 3000);
			}					
		});
     	function refreshResult(){
     		var info = "";
			if (true){
				var url = "${ctx}/app/per/showDisDoor.act?cardNo="+'${cardNo}'+"&isAuth=YES";
				var tooltipContent = "";
				var sucFun = function(data) {
					if (null != data) {
						var permitPersons = 0;
						var add = ""
						if('' != data.roomSu)
						{
							add += "<tr><td><span style='white-space:normal,word-wrap:break-word;' id ='success'>" + data.roomSu.substr(0,data.roomSu.length-1) + "</span></td><td>授权成功!</td><td></td></tr>";
						}
						if('' != data.roomFa)
						{
						 	add +="<tr><td id ='fail'>" + data.roomFa.substr(0,data.roomFa.length-1) + "</td><td>授权失败!</td><td></td></tr>";
						}
								
						tooltipContent +=  add;
						
						if (canEnd) {
							clearInterval(iId);
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
			}
			
		} 
		
		function closewindow()
		{  
		    clearInterval(iId);
		    if(null != $('#success') && null !=$('#success')[0] )
		    {
		    	$(window.top.ifrmOrgList.document.getElementById("dRoomName")).text($('#success')[0].innerHTML);
		    }
		    else
		    {
		    	$(window.top.ifrmOrgList.document.getElementById("dRoomName")).text('');
		    }
			parent.$("#telDis").window("close");
		}
     	
	</script>
	</head>
	<body>
		<div id="page_div" class="room_list_container" style="height:210px;">
				<table align="center" border="0" style="width:100%;height:30px;">
					<tr>
						<td style="width:80px;text-align:right;">
							<span style="font-weight:bold">卡号：</span>
						</td>
						<td>
							${cardNo}
						</td>
						<td>
						</td>
					</tr>
				</table>
				<table align="center" border="0" style="width:100%;height:145px;" id="info_span">
				</table>
				<table align="center" border="0" style="width:100%;height:30px;">
					<tr>
						<td colspan="3" style="text-align:center;">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closewindow()">关闭</a>
						</td>
					</tr>
				</table>
		</div>
	</body>
</html>

