<%--
  - Author: ycw
  - Date: 2012-7-10 
  - Description: 已回复问题贴详细页面
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>回复问题贴显示页面</title>	
	<%@ include file="/common/include/meta.jsp" %>
	
<script type="text/javascript">
$(function() {
	$("#approveResult").focus();
	changebasiccomponentstyle();
});
var options = {
		onkeyup: false,
		rules: {
			ansContent : {
				required: true,
				maxlength: 1500,
				minlength: 1
			}
		},
		messages: {
			ansContent: {
				required: " 请输入要回帖的内容",
				minlength: " 回帖的内容最少为一个字符"
			}
		}
	}
</script>
	<style type="text/css">
		.textfield { 
			width:240px;
		}
		.textarea { 
			width:360px;
			height:120px;
		}
	</style>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0">
<h:page id="receive_panel" width="100%" title="问题贴信息">
	<table align="center" border="0" width="100%" class="form_table">
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;问题贴标题：</td>
			<td colspan="24">${vendorQuestion.questTitle }</td>
		</tr>
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;发帖人：</td>
			<td>${vendorQuestion.questUser }</td>
			<td class="form_label" width="80px" nowrap>&nbsp;&nbsp;发帖时间：</td>
			<td>&nbsp;<s:date name="vendorQuestion.questTime" format="yyyy年MM月dd日 HH时mm分ss秒"/></td>
				<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;发帖公司：</td>
			<td>${vendorQuestion.questCompany }</td>
		</tr>
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;发帖内容：</td>
			<td colspan="24">${ansContent}</td>
		</tr>
	</table>	
		<c:forEach items="${ansList }" var="p" varStatus="rowid">
				<table align="center" border="0" width="100%" class="form_table">
						<tr>
								<td class="panel-title" colspan="20">&nbsp;&nbsp;回复帖信息${rowid.count}</td>
						</tr>
						<tr>
							<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;回复人：</td>
							<td>${p.ansUser }</td>
							<td class="form_label" width="80px" nowrap>&nbsp;&nbsp;回复时间：</td>
							<td><f:formatDate value="${p.ansTime}" pattern="yyyy年MM月dd日 HH时mm分ss秒"/></td>
							<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;回复公司：</td>
							<td>${p.ansCompany}</td>
						</tr>
						<tr>
							<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;回复内容：</td>
							<td colspan="24">${p.ansContent }</td>
						</tr>
				</table>
		</c:forEach>
			
		<tr class="form_bottom">
			<td colspan="9">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:closeQuest();" >关帖</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#sendDiv').show();" >回复</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" id="closeBtn" class="easyui-linkbutton" iconCls="icon-save" onclick="closeWin();">关闭</a>
			</td>
		</tr>
</h:page>

<div style="display: none" id="sendDiv">
<h:page id="send_panel" width="100%" title="问题贴追加">
	<form id="message_form" action="answer.act" method="post"  autocomplete="off" >
		<input type="hidden" name="flag" id="flag" value="${flag}"/>
	<input type="hidden" name="questId" value="${vendorQuestion.questId }"/>
		<table align="center" border="0" width="100%" class="form_table">
			<tr>
				<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;接收人：</td>
				<td>${receiver }</td>
			</tr>
			<tr>
				<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;问题贴标题：</td>
				<td colspan="24">${vendorQuestion.questTitle }</td>
				</td>
			</tr>
			<tr>
				<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;问题贴受理内容：</td>
				<td><s:textarea name="ansContent" theme="simple" cssStyle="width:500px;height:120px;"  cssClass="textarea"/><span style="color: red;">*</span></td>
			</tr>
			<tr>
				<td colspan="2" class="form_bottom">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript: formSubmit();">发送</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#message_form').resetForm();">重置</a>
				</td>
			</tr>
		</table>
	</form>
</h:page>
</div>
</body>
</html>
<script type="text/javascript">

//关闭
function closeWin(){
	closeEasyWin('answer_question_detail');
}

//关帖
function closeQuest(){
	easyConfirm('提示', '确定关帖吗？', function(r){
		if (r){
			var f=document.getElementById("message_form");
			f.action="${ctx}/common/answeredQuestion/closeQuestion.act";
			f.submit();
		}
	});		
}

//回复操作
function formSubmit() {
	var url="${ctx}/common/answeredQuestion/answer.act";
	$("#message_form").validate(options);
	$("#message_form").attr("action", url);
	$("#message_form").submit();
}

init();
function init(){
  var flag=document.getElementById("flag").value;
  if(flag){
	  easyAlert('提示信息','关帖操作成功！','info',
			 function(){closeEasyWin('answer_question_detail');});
  }
}
</script>