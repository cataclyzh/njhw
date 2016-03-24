<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>通卡列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/ca/common.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="css/TelMessage.css" />
	<link type="text/css" rel="stylesheet" href="css/table.css" />
	<link type="text/css" rel="stylesheet" href="css/toolbar.css"/>
	<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(document).ready(function(){
			var failIndex = $("#failIndex").val();
			if (failIndex != null && failIndex != '') {
				$(".errorColumn:eq(" + failIndex + ")").html("删除失败&nbsp;&nbsp;");
			}
		});
		
		function delPubCard(index,cardId,nodeIds,rooms) {
			params = {index: index, cardId: cardId, nodeIds: nodeIds, rooms: rooms}
			openAdminConfirmWin(delPubCardFn, params);
		}
		
		function delPubCardFn(params) {
			$("#cardId").val(params.cardId);
			$("#nodeIds").val(params.nodeIds);
			$("#rooms").val(params.rooms);
			$("#delIndex").val(params.index);
			
			var e = (screen.availWidth - 800) / 2;
			var b = '<div id="tksc" class="easyui-window" modal="true" draggable="true" resizable="false"  collapsible="false" minimizable="false" maximizable="true" style="top:100px;300px;width:400px;height:300px;padding:0px;background:#fafafa;overflow:visible;" closed="true" ><Iframe id="ifrmComWin" name="ifrmComWin" style="width:100%;height:435px;overflow:visible;" frameborder="0"></iframe></div>';
			var h = window.top.$(b).appendTo(window.top.document.body);
			h.show();
			h.window( {
				title : "通卡删除确认",
				showType : "slide",
				onClose : function() {
					h.window("destroy");
					$("#tkInfoForm").submit()
				},
				width : 800,
				height : 500,
				shadow : false,
				closed : false,
				top : 30,
				left : e,
				onOpen : function() {
					$("#tkscForm").submit();
				}
			});
		}
		
		function dispMore(rooms) {
			$("#dispMore").show();
			$("#dispMoreContent").html(rooms);
			var top = (document.body.scrollHeight - 200) / 2;
			var left = (document.body.scrollWidth - 400) / 2;
			$("#dispMore").window({
				title : "查看更多门锁权限",
				modal : true,
				shadow : false,
			    closed : false,
				width : 400,
				height : 200,
				top : top,
				left : left,
				showType : "slide"
			});
		}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" align="center" style="background:white !important;">
	<s:hidden name="vi_Id"/>
	<div style="height: 50px"></div>
	<div class="bgsgl_right_list_border" style="width:766px;margin-left:86px;">
		<div class="bgsgl_right_list_left">通卡列表</div>
	</div>
	<form name="tkInfoForm" id="tkInfoForm" method="post" action="${ctx}/app/personnel/unit/showPubCardInfo.act">
	<table class="main_table" border="0" style="border-collapse: collapse;width: 90%;" id="main_table">
		<tr class="table_header">
			<td width="10%" style="border-style: none; background: white;">
			</td>
			<td width="5%" style="text-align: center; border-right: solid 1px #e4e4e4;">
				<span>序号</span>
			</td>
			<td width="25%" style="border-right: solid 1px #e4e4e4;">
				<span>卡号</span>
			</td>
			<td width="50%" style="border-right: solid 1px #e4e4e4;">
				<span>门锁权限</span>
			</td>
			<td width="10%" style="border-right: solid 1px #e4e4e4;text-align: center;">
				<span>操作</span>
			</td>
		</tr>
		<c:if test="${empty pubCardList}">
			<tr>
				<td class="errorColumn" style="border-style: none; background: white; text-align: right; color: red;">
				</td>
				<td colspan="3">
					暂无通卡记录。
				</td>
			</tr>
		</c:if>
		<c:forEach var="row" items="${pubCardList}" varStatus="stat" >
			<c:if test="${not empty row}">
			<tr>
				<td class="errorColumn" style="border-style: none; background: white; text-align: right; color: red; border-bottom: solid 1px white;">
				</td>
				<td style="text-align: center; border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;font-family: '微软雅黑'; font-weight: bold;">
					${stat.index + 1}
				</td>
				<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
					${row.CARDID}
				</td>
				<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
					<c:if test="${fn:length(row.NODENAME)<=50}">
						${row.NODENAME}
					</c:if>
					<c:if test="${fn:length(row.NODENAME)>50}">
						${fn:substring(row.NODENAME, 0, 50)}...<a href="javascript:void(0);" class="table_btn" onclick = "dispMore('${row.NODENAME}');">查看更多</a>
					</c:if>
				</td>
				<td style="text-align: center; border-bottom: solid 1px #e4e4e4;">
					<a style="color: blue;" href="javascript:void(0);" onclick="delPubCard('${stat.index}','${row.CARDID}','${row.NODEID}','${row.NODENAME}')">[删除]</a>&nbsp;
				</td>
			</tr>
			</c:if>
			<c:if test="${empty row}">
			<tr>
				<td class="errorColumn" style="border-style: none; background: white; text-align: right; color: red; border-bottom: solid 1px white;">
				</td>
				<td style="text-align: center; border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;font-family: '微软雅黑'; font-weight: bold;">
				</td>
				<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
				</td>
				<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
				</td>
				<td style="text-align: center; border-bottom: solid 1px #e4e4e4;">
				</td>
			</tr>
			</c:if>
		</c:forEach>
	</table>
	<input type="hidden" id="failIndex" name="failIndex" value="${param.failIndex}"/>
	</form>
	<form name="tkscForm" id="tkscForm" method="post" action="${ctx}/app/personnel/unit/delPubCard.act" target="ifrmComWin">
		<input type="hidden" id="rooms" name="rooms"/>
		<input type="hidden" id="cardId" name="cardId"/>
		<input type="hidden" id="nodeIds" name="nodeIds"/>
		<input type="hidden" id="delIndex" name="delIndex"/>
	</form>
	<div id='dispMore' class='easyui-window' collapsible='false' draggable="true" resizable="false"
		minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
		closed='true'>
		<div id="dispMoreContent" style="width: 100%; word-break: break-all;">
		</div>
	</div>
</body>
</html>

	
