<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: ls
  - Date: 2010-11-20 18:08:00
  - Description: 发件人消息显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>已发消息</title>

<%@ include file="/common/include/meta.jsp" %>
<script src="${ctx}/app/portal/toolbar/showModel.js"
	type="text/javascript"></script>


<style >
a {
	text-decoration: none;
	/* color:#7F90B3;  */
	color:orange;
}

</style>
</head>
<body style="margin:0;padding:0;background: white;">
<div class="main_border_01">
  <div class="main_border_02">已发通知</div>
</div>
<div class="main_conter" style="padding: 15px;">
  <!-- 
	<table class="form_table" width="100%">
		<tr>
			<td align="right">
				<a href="javascript:void(0);" style="color:black; font-weight: bold;" class="easyui-linkbutton" onclick="javascript:msgBoxAction_init();">发送新消息</a>　　
				<a href="javascript:void(0);" style="color:black; font-weight: bold;" class="easyui-linkbutton" onclick="javascript:mymsg();">我的消息</a>
			</td>
		</tr>
	</table>
-->
  <form id="queryForm" action="msgBoxAction_querySenderbox.act" method="post" autocomplete="off">
    <!-- <div class="main2_border_01" style="margin-top: 10px;margin-bottom: 10px;">
      <p> 全部消息<span> </span> </p>
    </div> -->
    <h:page id="message_panel" width="100%"  chkId="msgid" title="已发消息"  >
    
      <s:hidden id="pageSize" name="page.pageSize" value="25"></s:hidden>
      <s:hidden name="msgtype" value="se"></s:hidden>
      <d:table id="row" name="page.result" export="false" class="table" requestURI="msgBoxAction_querySenderbox.act">
        <d:col title="序号" style="width: 10%;"   class="display_centeralign"> ${row_rowNum + ((page.pageNo-1)*page.pageSize)} </d:col>
        <d:col title="标题" style="width: 20%;"  sortable="true" property="title" class="display_leftalign" maxLength="13"/>
        
        <d:col title="内容" style="width: 20%;"  sortable="true" property="content" class="display_leftalign" maxLength="13">
        </d:col>
        
        <d:col title="发送时间" style="width: 15%;"  sortable="true" property="msgtime" class="display_leftalign" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
        <d:col title="详情和状态" style="width:15%;" class="display_leftalign" media="html" ><a href="javascript:void(0);" onclick="detailMsg('${row.msgid}','${row.sender}','${row.title }','${row.receiverid }',${row.senderId })">[详情]</a>&nbsp;<a href="javascript:void(0);" onclick="detailRecord('${row.msgid}','${row.notificationId}')">[状态]</a>  </d:col>
        <d:col title="操作" style="width:15%;"  class="display_leftalign" media="html"> <a href="javascript:void(0);" style="color: red;" onclick="delRecord('${row.msgid}','${row.notificationId}')">[删除]</a> </d:col>
      </d:table>
    </h:page>
  </form>
</div>
<script type="text/javascript">
	
		$(function(){	
		 	$("#message_panel tbody tr:eq(0)").hide();
			//$("#message_panel").find("thead").addClass("main_main2_list");//css({"background":"#EBEBEB","padding":"0"});
			//$("#message_panel").css("marginBottom","-30px");
			//$("td.main_peag").siblings("td").remove();
			//$(".main_peag").css("width","98%");
			//$(".panel-body").find("div").css({"padding-top":"10px","background":"white",});
			//$(".panel-body").find("div").find("table").css({"background":"#E0E3EA","padding":"0"});
			if(($(".main_conter").height() + 80 )> 570) 
			{
				$("#main_frame_left", window.parent.document).height($(".main_conter").height() + 80);
				$("#main_frame_right", window.parent.document).height($(".main_conter").height() + 80);
			}else{
				$("#main_frame_left", window.parent.document).height(570);
				$("#main_frame_right", window.parent.document).height(570);
			}
			
		});
		function detailRecord(msgId,notificationId) {
			//alert(msgId+","+notificationId);
			window.parent.showMessageDetail(msgId,notificationId);
			//var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailSenderbox.act?msgId=" + msgId;
			//openEasyWin("winId","消息内容", url, "700", "300", false);
		}
		
		function detailMsg(msgId,sender,title,receiverid,senderId) {
			window.parent.showMsg(msgId,sender,title,receiverid,senderId);
			
			//var src = "${ctx}/common/bulletinMessage/msgBoxAction_checkSendMsg.act?msgId=" + msgId;
			//showShelter(650, 450, src);
			//var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
			//openEasyWin("winId","消息内容", url, "700", "500",false);
		}
		
		$(document).ready(function(){
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});
		
		function msgBoxAction_init(){
			var url = "${ctx}/common/bulletinMessage/msgBoxAction_init.act";
			openEasyWin("winId","发送新消息", url, "700", "500",true);
		}
		
		function mymsg(){			
			parent.open_main3('66' ,'我的消息' ,'${ctx}/common/bulletinMessage/msgBoxAction_queryReceiverbox.act');
		}
		function delRecord(msgId,notificationId){
			window.parent.showConfirmBox("确定删除所选记录？",function(){
				// post请求
				$.post(
				// 请求action
				"${ctx}/common/bulletinMessage/msgBoxAction_delSentMsg.act", {
					// 参数设定
					notificationId : notificationId
				}, function(returnedData, textstatus){
					window.location.reload();
				});			
			});
		
    	}
		
	</script>
</body>
</html>
<s:actionmessage theme="custom"/>