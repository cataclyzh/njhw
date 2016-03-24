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
			
			
			$.each(($("td[id^=td_]")), function(i, n){
				$(n).mouseover(function(e) {
        			// 判断当前是否鼠标停留在对象上,可以修正ie6下出现的意外Bug;
					mouseon=true;
        			// 清空内容，因为title属性浏览器默认会有提示;
					$(this).attr('title','');
				
					var tel_id = $(this).attr('id').substring(3);
				
        			// 取得鼠标坐标来指定提示容器的位置;
        			var left = e.pageX + 30;
        			if (left + $('#tooltip_'+tel_id).width() > $("body").width()) {
        				left = e.pageX - $('#tooltip_'+tel_id).width() - 20;
        			}
					$('#tooltip_'+tel_id).css({
              			top:e.pageY + 10,
              			left:left
        			}).show();
				}).mousemove(function(e) {
					var tel_id = $(this).attr('id').substring(3);
					var left = e.pageX + 30;
        			if (left + $('#tooltip_'+tel_id).width() > $("body").width()) {
        				left = e.pageX - $('#tooltip_'+tel_id).width() - 20;
        			}
					$('#tooltip_'+tel_id).css({
              			top:e.pageY + 10,
              			left:left
        			}).show();
				}).mouseout(function() {
					var tel_id = $(this).attr('id').substring(3);
					if(mouseon==true){
						$('#tooltip_'+tel_id).hide();
						mouseon=false;
					}
				});
			});
		});
		
		function delPubCard(cardId) {
			params = {cardId: cardId};
			openAdminConfirmWin(delPubCardFn, params);
		}
		
		function retryPubCard(cardId,roomIds) {
			params = {cardId: cardId, roomIds: roomIds};
			openAdminConfirmWin(retryPubCardFn, params);
		}
		
		function delPubCardFn(params) {
			var url = '${ctx}/app/personnel/unit/deletePubCardAll.act?cardId='+params.cardId;
			var e = (screen.availWidth - 500) / 2;
			var g = (screen.availHeight - 300) / 2;
			var b = '<div id="tksc" class="easyui-window" modal="true" draggable="true" resizable="false"  collapsible="false" minimizable="false" maximizable="true" style="top:100px;300px;width:400px;height:300px;padding:0px;background:#fafafa;overflow:visible;" closed="true" ><Iframe id="ifrmComWin" name="ifrmComWin" style="width:100%;height:235px;overflow:visible;"'+
			'src="'+ url +'" frameborder="0"></iframe></div>';
			var h = window.top.$(b).appendTo(window.top.document.body);
			h.show();
			h.window( {
				title : "全楼通卡删除确认",
				showType : "slide",
				onClose : function() {
					h.window("destroy");
					$("#tkInfoForm").submit()
				},
				width : 500,
				height : 300,
				shadow : false,
				closed : false,
				top : g,
				left : e
			});
		}
		
		function retryPubCardFn(params) {
			$("#cardId").val(params.cardId);
			$("#roomIds").val(params.roomIds);
			
			var e = (screen.availWidth - 800) / 2;
			var g = (screen.availHeight - 500) / 2;
			var b = '<div id="tkcs" class="easyui-window" modal="true" draggable="true" resizable="false"  collapsible="false" minimizable="false" maximizable="true" style="top:100px;300px;width:400px;height:300px;padding:0px;background:#fafafa;overflow:visible;" closed="true" ><Iframe id="ifrmComWin" name="ifrmComWin" style="width:100%;height:435px;overflow:visible;" frameborder="0"></iframe></div>';
			var h = window.top.$(b).appendTo(window.top.document.body);
			h.show();
			h.window( {
				title : "通卡授权失败重新授权确认",
				showType : "slide",
				onClose : function() {
					h.window("destroy");
					$("#tkInfoForm").submit()
				},
				width : 800,
				height : 500,
				shadow : false,
				closed : false,
				top : g,
				left : e,
				onOpen : function() {
					$("#tkcsForm").submit();
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
<style type="text/css" charset="utf-8">
.tooltip1 {
	display:none;
	width:200px;
	position:absolute;
	z-index:9999;
	border:1px solid #333;
	/*background:#f7f5d1;*/
	background:#fff;
	padding:1px;
	color:#808080;
	font-size:13px;
	text-align:left;
	word-wrap:break-word;
}
</style>
</head>
<body style="background:#fff; height:600px;">
	<s:hidden name="vi_Id"/>
	<div class="bgsgl_right_list_border">
		<div class="bgsgl_right_list_left">通卡列表</div>
	</div>
	<form name="tkInfoForm" id="tkInfoForm" method="post" action="${ctx}/app/personnel/unit/showPubCardAllInfo.act">
	<table class="main_table" border="0" style="border-collapse: collapse;" id="main_table">
		<tr class="table_header">
			<td width="5%" style="text-align: center; border-right: solid 1px #e4e4e4;">
				<span>序号</span>
			</td>
			<td width="20%" style="border-right: solid 1px #e4e4e4;">
				<span>卡号</span>
			</td>
			<td width="45%" style="border-right: solid 1px #e4e4e4;">
				<span>授权失败权限</span>
			</td>
			<td width="15%" style="border-right: solid 1px #e4e4e4;text-align: center;">
				<span>失败重授</span>
			</td>
			<td width="15%" style="border-right: solid 1px #e4e4e4;text-align: center;">
				<span>删除</span>
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
				<td style="text-align: center; border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;font-family: '微软雅黑'; font-weight: bold;">
					${stat.index + 1}
				</td>
				<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
					${row.CARDID}
				</td>
				<c:if test="${not empty row.NODENAME}">
				<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;" id="td_${stat.index}">
					<c:if test="${fn:length(row.NODENAME)<=30}">
						${row.NODENAME}
					</c:if>
					<c:if test="${fn:length(row.NODENAME)>30}">
						${fn:substring(row.NODENAME, 0, 30)}...
					</c:if>
					<div class="tooltip1" id="tooltip_${stat.index}">
					<c:if test="${not empty row.NODENAME}">
						${row.NODENAME}
					</c:if>
					</div>
				</td>
				</c:if>
				<c:if test="${empty row.NODENAME}">
					<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
					无
					</td>
				</c:if>
				<td style="text-align: center; border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
					<c:if test="${empty row.NODENAME}">
						失败重授
					</c:if>
					<c:if test="${not empty row.NODENAME}">
						<a style="color: blue;" href="javascript:void(0);" onclick="retryPubCard('${row.CARDID}','${row.ROOMID}')">[失败重授]</a>
					</c:if>
				</td>
				<td style="text-align: center; border-bottom: solid 1px #e4e4e4;">
					<a style="color: blue;" href="javascript:void(0);" onclick="delPubCard('${row.CARDID}')">[删除]</a>&nbsp;
				</td>
			</tr>
			</c:if>
			<c:if test="${empty row}">
			<tr>
				<td style="text-align: center; border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;font-family: '微软雅黑'; font-weight: bold;">
				</td>
				<td style="border-right: solid 1px #e4e4e4; border-bottom: solid 1px #e4e4e4;">
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
	<form name="tkcsForm" id="tkcsForm" method="post" action="${ctx}/app/personnel/unit/retryPubCard.act" target="ifrmComWin">
		<input type="hidden" id="rooms" name="rooms"/>
		<input type="hidden" id="cardId" name="cardId"/>
		<input type="hidden" id="roomIds" name="roomIds"/>
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

	
