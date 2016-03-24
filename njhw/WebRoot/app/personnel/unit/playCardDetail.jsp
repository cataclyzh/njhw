<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>制作通卡</title>	
	<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
	<%@ include file="/common/include/meta.jsp"%>
	<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<OBJECT id="WebRiaReader" codeBase=""
			classid="clsid:13C57329-846A-4C82-AAB1-1AA0EC3CA0BB"></OBJECT>
	<script type="text/javascript">
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
				
				if (str==undefined || "" == str){
					alert("请检查市民卡驱动是否正确安装。");
					return;
				}
				if($.trim(str) == "FoundCard error!"){
					alert("请放置市民卡。");
					return;
				}else{
					$("#person_card").val(str);			
				}
			
				$("#cardId").val(str);			
				window.location.href = "${ctx}/app/personnel/unit/playCard.act?cardId="+str;
			}
			
			function checkCardId() {
				var cityCard = $("#cardIdInput").val().replace(/(^\s*)|(\s*$)/g, "");
				if (cityCard != "") {
					if (cityCard.length == 16) {
						window.location.href = "${ctx}/app/personnel/unit/playCard.act?type=2&cardId="+cityCard;
					} else {
						easyAlert("提示信息", " 市民卡号长度不足16位！","info");
					}
				} else {
					easyAlert("提示信息", " 无市民卡号，无法创建通卡用户！","info");
				}
			}
			
			function saveData(){
				$("#page_search_form").submit();  			
			}
		
		// 选中全部
		function chk_all() {
			$("#page_div input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("disabled")!="disabled")	{		
					if ($("#check_all").attr("checked") == "checked"){
						$(item).attr("checked", "true");
					} else {
						$(item).removeAttr("checked");
					}
				}
			});
		}
		/**
		* 实现翻页的方法
		*/
		function goPage(pageNo){
			$("#page_search_form input[id='pageNo']").val(pageNo);
			var orgId = $("#page_search_form input[id='orgId']").val();
			var url = $("#page_search_form").attr("action");
			var sucFun = function (data){
				$("#page_div").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data =  "pageNo="+pageNo+"&orgId="+orgId;
			ajaxQueryHTML(url,data,sucFun,errFun);
		}
		
		function closeWin() {
			closeEasyWin('zztk');
		}
		
<c:if test="${isSuc=='1'}">
	easyConfirm('提示信息', '无法获取用户信息，系统将自动创建通卡用户，是否继续？', function(r){
		if (r) autoCreateUser();
	});
</c:if>

	function autoCreateUser() {
		var cityCard = $("#cardId").val().replace(/(^\s*)|(\s*$)/g, "");
		if (cityCard != "") {
			if (cityCard.length == 16) {
				$.ajax({
		            type: "POST",
		            url: "${ctx}/app/personnel/unit/autoCreateUser.act",
		            data: {"cityCard": cityCard},
		            dataType: 'text',
		            async : false,
		            success: function(msg){
	                    if(msg == 'success') {
	                    	window.location.href = "${ctx}/app/personnel/unit/playCard.act?cardId="+cityCard+"&type="+$('#type').val();
	                    } else {
	                    	easyAlert("提示信息", "创建通卡用户失败！","info");
	                    }
		            },
		            error: function(msg, status, e){
		            	easyAlert("提示信息", "创建通卡用户出错！","info");
		            }
		         });
			} else {
				easyAlert("提示信息", " 市民卡号长度不足16位！","info");
			}
		} else {
			easyAlert("提示信息", " 无市民卡号，无法创建通卡用户！","info");
		}
	}
	
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0">
<table align="center" border="0" width="750px" class="form_table" style="margin-top:1px;">
				<tr>
				<td colspan = "4"><span style="font-weight:bold">基本信息</span><td>
				</tr>
				<tr>
					<td style="width:80px;text-align:right;">
						卡号：
					</td>
					<td style="width:290px;">
						<c:choose>
						<c:when  test="${param.type == '2'}">
							<input type="text" name="cardIdInput" size="18" value="${cardId}" id="cardIdInput"/>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="checkCardId();">查询</a>
						</c:when>
						<c:otherwise>${cardId}</c:otherwise>
						</c:choose>
					</td>
					<td style="width:80px;text-align:right;">
					卡类型：
					</td>
					<td align="left" style="width:290px;">
						<c:if test="${curUserExp.cardType=='1'}">市民卡</c:if>
						<c:if test="${curUserExp.cardType=='2'}">临时卡</c:if>
					</td>
					<td rowspan="3">
						<c:choose>
						<c:when  test="${param.type == '2'}">
						</c:when>
						<c:otherwise><div id="readBtn" style="background: url('${ctx}/app/personnel/images/readCard.png'); width: 187px; height: 93px; cursor: pointer;" onclick="readCard()" title="读卡" ></div></c:otherwise>
						</c:choose>
					</td>
				</tr>				
				<tr>
					<td style="width:80px;text-align:right;">
						姓&nbsp;&nbsp;&nbsp;&nbsp;名：
					</td>
					<td>
						${curUser.displayName}
					</td>
					<td style="width:80px;text-align:right;">
						单位：
					</td>
					<td>
						${curUserUnit.ORG_NAME}		
					</td>
					
				</tr>
				
				
			</table>
			<table align="center" border="0" width="750px" class="form_table" style="margin-top:10px;border:1px;">
			<tr>
			<td colspan = "2"><span style="font-weight:bold">门锁分配<input type ="checkbox" id="check_all" onclick="javascript:chk_all();"/></span><td>
			</tr>
			<tr>
				<td colspan = "2">
					<div id="page_div">
					<form id="page_search_form" name="searchForm" action="${ctx}/app/personnel/unit/playCardSave.act" method="post">
					<table width="100%" border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;"align="center">
						<tr>    
						<c:forEach var="roomLock" items="${page.result}" varStatus="stat">
							<td style="border: solid 1px #a0c6e5; height: 20px;">
							<c:if test="${roomLock.IS_CHECKED ne '1'}">
							<input type="checkbox" value="${roomLock.NODE_ID}" name="_chk" id="_chkid" />${roomLock.NAME}
							</c:if>
							<c:if test="${roomLock.IS_CHECKED eq '1'}">
							<input type="checkbox" value="${roomLock.NODE_ID}" name="_chk" id="_chkid" checked/>${roomLock.NAME}
							</c:if>
							<!-- 不够四列的行用空列补齐 -->  
							<c:if test="${stat.last && stat.count%4 != 0}">  
							<c:set value="${4-(stat.count%4)}" var="restTd"/>                                    
							<c:forEach var="x" begin="1" end="${restTd}" step="1">  
							<td width="22%"></td>  
							</c:forEach>  
							</c:if>  
							<!-- 4列换一行 -->  
							<c:if test="${stat.count%4==0}">  
							</tr><tr>  
							</c:if>
							</td>
		                 </c:forEach>  
		                </tr>     
					</table>
		        	<jsp:include page="/common/include/paging/gopage.jsp" flush="true">
						<jsp:param name="type" value="0" />
						<jsp:param name="position" value="down" />
					</jsp:include>
					
						<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
						<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }" />
						<input type="hidden" id="userId" name="userId" value="${userId}" />
						<input type="hidden" id="cardId" name="cardId" value="${cardId}" />
						<input type="hidden" id="type" name="type" value="${param.type}" />
					</form>
				</div>
				</td>
			</tr>
			<tr>
			<td colspan = "2"><span style="font-weight:bold">其它权限</span><td>
			</tr>
			<tr>
			<td ><input type="checkbox" checked disabled/>本单位全部门禁</td>
			<td ><input type="checkbox" checked disabled/>全部闸机</td>
			</tr>
			<tr>
			<td colspan="2" align="center">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>　　
			<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" onclick="closeWin()">关闭</a>
			</tr>
			</table>
</body>
</html>