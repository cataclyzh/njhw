<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>电话申请单</title>
		<link href="css/dhsq.css" type="text/css" rel="stylesheet" />
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript">
        	
	        /**
        	*保存电话分配
        	*/
	        function saveTelDis(){
	        	var userId = $("#sel_user_id").val();
	        	var telId = '${newTel.telId}';
	        	var permits = "";
	        	var functions = "";
	        	
	        	$("input[name='permit']:checked").each(function(){
					permits += $(this).val()+",";
				});
				$("input[name='function']:checked").each(function(){
					functions += $(this).val()+",";
				});
				alert(permits+"||"+functions);
	        	var url = "${ctx}/app/personnel/telAndNumber/saveTelDis.act";
	        	var data = {userId:userId,telId:telId,permits:permits,functions:functions};
				sucFun = function (data){
					if (data.result=='1'){
						easyAlert('提示信息','电话分配成功！请打印申请单,到电话班办理变更申请!','info');
					} else {
						easyAlert('提示信息','电话分配失败！','info');
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
					title : '新增电话申请',
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
				<img src="images/name01.gif" alt=""
					style="margin: 10px 0 10px 0; float: left; display: inline;">
			</div>
			<div class="big01_box">
				<table
					style="width: 770px; margin-top: 0px !important; margin: 10px auto 40px auto; padding-top: 10px;"
					cellpadding="0" cellspacing="0">
					
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
							用户姓名
						</td>
						<td style="width: 310px;">
							<div 
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								<input id = "sel_user_name"
									style="padding-left: 5px; width: 200px; height: 25px; font-style: 12px;"
									onclick="chooseUser();" />
							</div>
							<input type="hidden" id="sel_user_id">
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							房间号
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;" id="div_room_id"></div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							话机来源
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								已有话机
							</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							电话MAC
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								${newTel.telMac}
							</div>
						</td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 74px; font-size: 14px; color: #333;"
							align="left">
							申请号码
						</td>
						<td style="width: 310px;">${newTel.telNum}</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left"></td>
						<td></td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 64px; font-size: 14px; color: #333;"
							align="left">
							申请权限
						</td>
						<td style="width: 310px;">
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
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 80px; font-size: 14px; color: #333;"
							align="left">
							申请功能
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 30px; font-style: 12px;">
								<input type="checkbox" name="function" value="1" />
								呼叫转移
								<input type="checkbox" name="function" value="2" />
								呼叫等待
							</div>
						</td>
						<td style="width: 80px; font-size: 14px; color: #333;"
							align="left"></td>
						<td></td>
						<td></td>
					</tr>
					<tr valign="middle" style="height: 30px; line-height: 30px;">
						<td style="width: 80px; font-size: 14px; color: #333;"
							align="left"></td>
						<td style="width: 310px;"></td>
						<td style="width: 80px; font-size: 14px; color: #333;"
							align="left"></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<!-- 
        <table style="width:770px; margin:0 auto;" cellpadding="0" cellspacing="0" >
            <tr valign="middle">
                <td colspan="3" align="center" style="font-weight: bold">变更记录</td>
            </tr>
            <tr valign="middle" style="height:30px; line-height:30px;">
                <td style="width:80px; font-size:14px; color:#333;" align="left">开通号码</td>
                <td style="width:310px;">
                    11
                </td>
                <td style="width:68px; font-size:14px; color:#333;" align="left">电话MAC</td>
                <td>
                    11
                </td>
            </tr>
            <tr valign="middle" style="height:30px; line-height:30px;">
                <td style="width:80px; font-size:14px; color:#333;" align="left">开通权限</td>
                <td style="width:310px;">
                    11
                </td>
                <td style="width:68px; font-size:14px; color:#333;" align="left">操作人</td>
                <td>
                    11
                </td>
            </tr>

            <tr valign="middle" style="height:30px; line-height:30px;">
                <td style="width:80px; font-size:14px; color:#333;" align="left">开通功能</td>
                <td style="width:300px;">
                    11
                </td>
                <td style="width:78px; font-size:14px; color:#333;" align="left">开通日期</td>
                <td>&nbsp;</td>
            </tr>
        </table>
         -->
			</div>
			<div class="input_box">
				<input name="Button1" type="button" onclick="saveTelDis();" value="" class="baocun" />
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