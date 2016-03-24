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
        	$(document).ready(function() {
				//注册validate函数
			
			});
	        /**
        	*保存电话分配
        	*/
	        function saveUnitApp(){
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
							申请号码
						</td>
						<td style="width: 310px;">
							<div
								style="padding-left: 5px; width: 300px; height: 40px; font-style: 12px;">
								<input type="text" name="telNum" id="telNum">
							</div>
						</td>
						<td style="width: 58px; font-size: 14px; color: #333;"
							align="left">
							电话MAC
						</td>
						<td>
							<div
								style="padding-left: 5px; width: 300px; height: 40px; font-style: 12px;">
								<input type="text" name="telMac" id="telMac">
							</div>
						</td>
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
			</div>
			<div class="input_box">
				<input name="Button1" type="button" onclick="saveUnitApp();" value="" class="baocun" />
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