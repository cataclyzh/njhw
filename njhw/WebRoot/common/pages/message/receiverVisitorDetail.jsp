<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: ls
  - Date: 2010-11-20 17:46:46
  - Description: 收件人消息内容显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>查看访客信息</title>	
	<%@ include file="/common/include/meta.jsp" %>
</head>

<body style="background: #FFF;">
<div class="main_left_main1">
	<div class="main_conter">
		<div class="main1_main2_right">
			<div class="main2_border_list">
				<p>我的访客</p>
			</div>
			<form action="newAffirm.act" method="post" autocomplete="off" id="inputForm">
				<input name="vsId" id="vsId" value="${vmVisitInfo.vsId}" type="hidden"/>
				<div class="show_from">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="10%">访客姓名：</td>
							<td width="40%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${vmVisitInfo.viName}" /></td>
							<td widht="10%">访问状态：</td>
							<td width="40%"><input name="" type="text" readonly="readonly" class="show_inupt" value="${vmFlagStr}" /></td>
						</tr>
						<tr>
							<td width="10%">到访时间：</td>
							<td width="40%"><input name="vsSt" id="vsSt" readonly="readonly" type="text" class="show_inupt" value="${vsSt}" /></td>
							<td widht="10%">结束时间：</td>
							<td width="40%"><input name="vsEt" id="vsEt" type="text" readonly="readonly" class="show_inupt" value="${vsEt}" /></td>
						</tr>
						<tr>
							<td width="10%">预约性质：</td>
							<td width="40%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${vmTypeFlag}" /></td>
						</tr>
						<tr>
							<td valign="top">访问事由：</td>
							<td colspan="3"><textarea name="vsCommets" style="overflow: auto;" readonly="readonly" class="show_textarea" >${vmVisitInfo.vsInfo  }</textarea></td>
						</tr>
					</table>
				</div>
				<div class="show_pop_bottom">
					<a style="float: right;cursor: pointer;" onclick="parent.closeIframe()">关闭窗口</a>
					<c:if test="${vmFlagStr eq '已确认' }">
						<a style="float: left;cursor: pointer;" id="savebut" onclick="saveData('can');">取消预约</a>
					</c:if>
					 
					<c:if test="${vmFlagStr eq '已申请' }">
						<a style="float: left;cursor: pointer;" id="savebut" onclick="saveData('aff');">确认申请</a>
						<a style="float: left;cursor: pointer;" id="savebut" onclick="saveData('rep');">拒绝申请</a>
					</c:if>
				</div>
			</form>
		</div>
		<div class="clear"></div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
function showRequest(){
	return $("#inputForm").valid();
}

function saveData(type){
	
	if (showRequest()) {
		$("#savebut").attr('disabled',true); //禁用事件
		if (type == "rep") {
			$('#inputForm').attr("action","newRepulse.act");
		}
		if (type == "can") {
			$('#inputForm').attr("action","newCannel.act");
		}
		var options = {
		    dataType:'text',
		    success: function(responseText) { 
		    	var froms = parent.document.getElementById("CallForm");
				if(responseText=='success') { 
					easyAlert("提示信息", "您已确认对方来访！","info", function(){
						if(froms != null)
							froms.submit();
						else{
							window.parent.loadVisitor();
							parent.closeIframe();
						}
					});
			 	}else if(responseText=='successCannel') { 
					easyAlert("提示信息", "您已取消对方来访！","info", function(){
						if(froms != null)
							froms.submit();
						else{
							window.parent.loadVisitor();
							parent.closeIframe();
						}
					});
			 	}else if(responseText=='successRefuse') { 
					easyAlert("提示信息", "您已拒绝对方来访！","info", function(){
						if(froms != null)
							froms.submit();
						else{
							window.parent.loadVisitor();
							parent.closeIframe();
						}
					});
			 	}else if(responseText=='ser1') { 
					easyAlert("提示信息", "您已确认对方来访,但发送信息失败！","info", function(){
						if(froms != null)
							froms.submit();
						else{
							window.parent.loadVisitor();
							parent.closeIframe();
						}
					});
			 	}else if(responseText=='ser4') { 
					easyAlert("提示信息", "您已取消对方来访,但发送信息失败！","info", function(){
						if(froms != null)
							froms.submit();
						else{
							window.parent.loadVisitor();
							parent.closeIframe();
						}
					});
			 	}else if(responseText=='ser2') { 
					easyAlert("提示信息", "您已拒绝对方来访,但发送信息失败！","info", function(){
						if(froms != null)
							froms.submit();
						else{
							window.parent.loadVisitor();
							parent.closeIframe();
						}
					});
			 	}else if(responseText =='statusError') {
			 		easyAlert("提示信息", "此访问申请状态已变更，不能继续操作！","info", function(){
						//parent.closeEasyWin('winId');
			 			parent.closeIframe();
					});
				} else if(responseText =='error') {
			 		easyAlert("提示信息", "加载访客信息出错！","info",function(){
			 			//parent.closeEasyWin('winId');
			 			parent.closeIframe();
			 		});
				}
				$("#savebut").attr('disabled',false);//开放事件
			}
		};
		$('#inputForm').ajaxSubmit(options);
	}
}
</script>
