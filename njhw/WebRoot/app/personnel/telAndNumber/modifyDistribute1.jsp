<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>电话变更申请单</title>
		<link href="css/dhsq.css" type="text/css" rel="stylesheet" />
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript">
        	/**
        	*保存电话分配
        	*/
	        function saveTelDisChange(){
	        	var userId = $("#sel_user_id").val();
	        	var telId = '${newTel.telId}';
	        	var permits = "";
	        	var functions = "";
	        	var oldUserId = "${oldUser.userid}";
	        	
	        	$("input[name='permit']:checked").each(function(){
					permits += $(this).val()+",";
				});
				$("input[name='function']:checked").each(function(){
					functions += $(this).val()+",";
				});
	        	var url = "${ctx}/app/personnel/telAndNumber/saveTelDisChange.act";
	        	var data = {userId:userId,telId:telId,permits:permits,functions:functions,oldUserId:oldUserId};
				sucFun = function (data){
					if (data.result=='1'){
						easyAlert('提示信息','话机变更成功！请打印申请单,到电话班办理变更申请!','info');
					} else {
						easyAlert('提示信息','话机变更失败！','info');
					}
				};
				errFun = function (data){
					easyAlert('提示信息','加载页面出错!','info');
				};
				ajaxQueryJSON(url,data,sucFun,errFun);
	        }
	        
	        /**
	        * 查询用户信息
	        */
	        function chooseUser(){
	        	var url = "${ctx}/app/personnel/telAndNumber/orgTreeUser.act";
	        	windowDialog(url,"10",500,300);
	        }
        	function windowDialog(url,top,w,h){
				$("#companyWin").show();
				var left = (document.body.scrollWidth - w) / 2;
				$("#companyWin").window({
					title : '更改电话申请',
					modal : true,
					shadow : false,
					closed : false,
					width : w,
					height : h,
					top : top,
					left : left,
					onBeforeClose:function(){
						
					}
				});
				$("#companyIframe").attr("src", url);
			}
			//刷新用户
			function refreshUser(userId,userName){
				userId = userId.substring(0,userId.indexOf("-"));
				$("#sel_user_name").val($.trim(userName));
				$("#sel_user_id").val(userId);
				var url = "${ctx}/app/personnel/telAndNumber/refreshUser.act";
				var data = {userId:userId};
				sucFun = function (data){
					//data.roomId
					$("#div_room_id").html(data.roomInfo);
					$("#companyWin").window("close");
					
				};
				errFun = function (data){
					easyAlert('提示信息','加载页面出错!','info');
				};
				ajaxQueryJSON(url,data,sucFun,errFun);
			}
        </script>
	</head>
	<body>
		<div class="big_box">
			<div style="width: 800px; height: 65px;">
				<img src="images/name02.gif" alt=""
					style="margin: 10px 0 10px 0; float: left; display: inline;">
			</div>
			<div class="big01_box">
				<table
					style="width: 770px; margin-top: 0px !important; margin: 10px auto 40px auto; padding-top: 10px;"
					cellpadding="0" cellspacing="0">
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 80px; font-size: 14px; color: #333;"
							align="left">
							申请变更：
						</td>
						<td colspan="3">
							<div class="quanxian">
								<table cellpadding="0" cellspacing="0"
									style="float: left; margin: 5px 0 0 5px; display: inline; font-size: 14px;">
									<tr>
										<td style="height: 14px; width: 18px;" align="left">
											<input name="Checkbox1" type="checkbox" />
										</td>
										<td style="height: 14px; width: 100px;" align="left">
											号码
										</td>
										<td style="height: 14px; width: 18px;" align="left">
											<input name="Checkbox1" type="checkbox" />
										</td>
										<td style="height: 14px; width: 100px;" align="left">
											权限
										</td>
										<td style="height: 14px; width: 18px;" align="left">
											<input name="Checkbox1" type="checkbox" />
										</td>
										<td style="height: 14px; width: 100px;" align="left">
											功能
										</td>
										<td style="height: 14px; width: 18px;" align="left">
											<input name="Checkbox1" type="checkbox" />
										</td>
										<td style="height: 14px; width: 100px;" align="left">
											更换话机
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							申请单位
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${_orgname}
							</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							申请日期
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${unitUserExp.uepBak1}
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							申请人
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${_username}
							</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							联系方式
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${unitUserExp.telNum}
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							原用户姓名
						</td>
						<td style="width: 310px;">
						<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${oldUser.displayName}
								</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							原房间号
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;" id="div_room_id">
								${oldUserExp.roomInfo}
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							用户姓名
						</td>
						<td style="width: 310px;">
							<input id ="sel_user_name"
								style="padding-left: 5px; width: 200px; height: 25px; font-style: 12px;"
								value="${oldUser.displayName}" onclick="chooseUser();" />
							<input id="sel_user_id" type = "hidden" value="${oldUser.userid}"/>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							房间号
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;" id="div_room_id">
								${oldUserExp.roomInfo}
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							原号码
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${oldUserExp.telNum}
							</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							新号码
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${newTel.telNum}
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							原权限
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								<c:if test="${oldTel.telIdd=='1'}">国际&nbsp;&nbsp;</c:if>
								<c:if test="${oldTel.telddd=='2'}">国内&nbsp;&nbsp;</c:if>
								<c:if test="${oldTel.telLocal=='3'}">市话&nbsp;&nbsp;</c:if>
								<c:if test="${oldTel.telCornet=='4'}">内网</c:if>
							</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							新权限
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								<input type="checkbox" name="permit" value="1" />
								国际
								<input type="checkbox" name="permit" value="2" />
								国内
								<input type="checkbox" name="permit" value="3" />
								市话
								<input type="checkbox" name="permit" value="4" />
								内网
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							原功能
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								<c:if test="${data.telForword=='2'}">呼叫转移</c:if>
								<c:if test="${data.telCw=='2'}">呼叫等待</c:if>
							</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							新功能
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								<input type="checkbox" name="function" value="1" />
								呼叫转移
								<input type="checkbox" name="function" value="2" />
								呼叫等待
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 80px; font-size: 14px; color: #333;"
							align="left">
							原电话MAC
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								<div
									style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
									${oldTel.telMac}
								</div>
						</td>
						<td style="width: 80px; font-size: 14px; color: #333;"
							align="left">
							新话机MAC
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${newTel.telMac}
							</div>
						</td>
						<td></td>
					</tr>
				</table>
			</div>
			<div class="input_box">
				<input name="Button1" type="button" value=""
					onclick="javascript:saveTelDisChange();" class="baocun" />
				<input name="Button1" type="button" value="" class="daochu" />
			</div>

			<div class="clear"></div>
		</div>
		<div id='companyWin' class='easyui-window' collapsible='false'
			minimizable='false' maximizable='true'
			style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
			closed='true'>
			<iframe id='companyIframe' name='companyIframe'
				src=""
				style='width: 100%; height: 100%;' frameborder='0'></iframe>
		</div>
	</body>
</html>