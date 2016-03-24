<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<title>读取卡权限</title>
		<%@ include file="/common/include/meta.jsp"%>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<OBJECT id="WebRiaReader" codeBase=""
			classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
		<script>
			$(document).ready(function(){
				initPhotoActiveX();
			});
			
			/**
			* init photo activeX
			*/
			function initPhotoActiveX(){
				WebRiaReader.LinkReader();
			}
			
			/**
			* window close 关闭市民卡
			*/
			function close()
			{
				//关闭市民卡扫描插件
				WebRiaReader.RiaCloseReader();
			}
			
			/**
			* 读取卡权限
			*/
			function readCard(){
				var str;
				str = WebRiaReader.RiaReadCardEngravedNumber;
				id = 'dqkqx';
				title = "读取卡权限";
				window.location.href =  "${ctx}/app/personnel/unit/readCardPrivileges.act?cardId="+str;
			}
		</script>

	</head>

	<body>
		<div>
			<!-- 
			<div style="margin:0 0 0 0px;text-align:right;float:right;"><input type="button" value="读&nbsp;&nbsp;&nbsp;&nbsp;卡" style="height: 30px;margin-right:11px;margin-bottom:10px;" onclick="readCard();" /></div>
			 -->
			<table align="center" border="0" width="750px" class="form_table" style="margin-top:10px;">
				<tr>
				<td colspan = "4"><span style="font-weight:bold">基本信息</span><td>
				</tr>
				<tr>
					<td style="width:80px;text-align:right;">
						卡号：
					</td>
					<td style="width:290px;">
						<c:if test="${cardId!=null && cardId !='' && fn:trim(cardId) != 'FoundCard error!'}">
							${cardId}
						</c:if>
						<c:if test="${cardId==null || cardId =='' || fn:trim(cardId) == 'FoundCard error!'}">
							读卡异常!
						</c:if>
					</td>
					<td style="width:80px;text-align:right;">
					卡类型：
					</td>
					<td align="left" style="width:290px;">
						<c:if test="${curUserExp.cardType eq '1'}">市民卡</c:if>
						<c:if test="${curUserExp.cardType eq '2'}">临时卡</c:if>
					</td>
					<td rowspan="3"><div id="readBtn" style="background: url('${ctx}/app/personnel/images/readCard.png'); width: 187px; height: 93px; cursor: pointer;" onclick="readCard()" title="读卡" ></div></td>
				</tr>
				<c:if test="${curUserExp.cardType=='1'}">
					<tr>
						<td style="width:80px;text-align:right;">
							卡状态：
						</td>
						<td>
						<c:if test="${curUserCard.cardstatus=='1'}">不正常</c:if>
						<c:if test="${curUserCard.cardstatus=='0'}">正常</c:if>
						</td>
						<td style="text-align:right;">
							挂失状态：
						</td>
						<td>
						<c:if test="${curUserCard.cardLosted=='1'}">已挂失</c:if>
						<c:if test="${curUserCard.cardLosted=='0'}">未挂失</c:if>
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td>
						</td>
						<td style="text-align:right;">
							通卡状态：
						</td>
						<td>
						<c:if test="${curUserCard.puicstatus=='1'}">已开卡</c:if>
						<c:if test="${curUserCard.puicstatus=='0'}">未开卡</c:if>
						</td>
					</tr>
				</c:if>
				<tr>
					<td style="width:80px;text-align:right;">
						单位：
					</td>
					<td>
						${curUserUnit.ORG_NAME}		
					</td>
					<td style="width:80px;text-align:right;">
						
						部&nbsp;&nbsp;&nbsp;&nbsp;门：
					</td>
					<td>	
						${curUserOrg.shortName}			
					</td>
					<c:if test="${curUserExp.cardType=='1'}">
					<td></td>
					</c:if>
				</tr>
				<tr>
					<td style="width:80px;text-align:right;">
						账号：
					</td>
					<td>
						<c:if test="${curUser!=null}">
							${curUser.loginUid}
						</c:if>
						<c:if test="${cardId != null && cardId !='' && curUser == null}">
							没有卡账号!
						</c:if>
					</td>
					<td style="width:80px;text-align:right;">
						姓&nbsp;&nbsp;&nbsp;&nbsp;名：
					</td>
					<td>
						${curUser.displayName}
					</td>
					<c:if test="${curUserExp.cardType=='1'}">
					<td></td>
					</c:if>
				</tr>
				<tr>
					<td colspan = "4"><span style="font-weight:bold">设备权限</span><td>
				</tr>
				<tr>
					<td style="width:80px;text-align:right;">
						门锁：
					</td>
					<td colspan="4">
					${msPermits}
					</td>
				</tr>
				<tr>
					<td style="width:80px;text-align:right;">
						门禁：
					</td>
					<td colspan="4">
					${mjPermits}
					</td>
				</tr>
				<tr>
					<td style="width:80px;text-align:right;">
						闸机：
					</td>
					<td colspan="4">
					${zjPermits}
					</td>
				</tr>
				<tr>
					<td style="width:80px;text-align:right;">
						车位：
					</td>
					<td colspan="4">
					${plates}
					</td>
				</tr>
			</table>
			</div>
	</body>
</html>
