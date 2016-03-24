<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: ls
  - Date: 2010-11-20 17:23:19
  - Description: 收件人消息显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的消息</title>
<%@ include file="/common/include/meta.jsp" %>
<style >
a {
	text-decoration: none;
}
.main_pag a {
    background: none repeat scroll 0 0 #FFC600;
    color: #FFFFFF;
    font-size: 18px;
    font-weight: bold;
    padding: 4px 30px;
    text-decoration: none;
}
</style>
<script type="text/javascript">
		function detailRecord(msgId,sender,title,receiverid,senderId) {
			//alert(senderId);
			window.parent.showMsg(msgId,sender,title,receiverid,senderId);
			
			//var src = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + msgId;
			//showShelter(600, 500, src);
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
		
		function msgBoxAction_querySenderbox(){			
			var url = "${ctx}/common/bulletinMessage/msgBoxAction_querySenderbox.act";
			parent.open_main3('67' ,'已发消息' ,url);
			//openEasyWin("winId","发件箱", url, "700", "600",true);
		}
		
		function delRecord(){
			
			if($("input[@type=checkbox]:checked").size()==0){
				window.parent.showMessageBox('请勾选要删除的记录！');	
			}else{
				
				
				window.parent.showConfirmBox("确定删除？",function(){
					
					$('#queryForm').attr("action","msgBoxAction_delMess.act");
					$('#queryForm').submit();
					
				});
			}	
			
			
			
			/*
			if($("input[@type=checkbox]:checked").size()==0){
				easyAlert('提示信息','请勾选要删除的记录！','info');
			}else{
				easyConfirm('提示', '确定删除？', function(r){
					if (r){
						$('#queryForm').attr("action","msgBoxAction_delMess.act");
						$('#queryForm').submit();
					}
				});		
			}
			*/
    	}
	</script>
<script type="text/javascript">
	
		$(function(){
			
			//隐藏我的消息
			$("#message_panel tbody tr:eq(0)").hide();
			$("#message_panel").css("marginBottom","-30px");
			
			$("td.main_peag").siblings("td").remove();
		 	 
			
		});
	
		
		function adjustHeight(){
			
			//console.log("window.parent.document.documentElement.clientHeight" + window.parent.document.documentElement.clientHeight);
			//console.log("document.body.clientHeight" + document.body.clientHeight);
			//console.log($("#main_frame_left").height());
			//console.log($("#main_frame_right").height);
			
			// 控制父页面高度
			/*
			$("#main_frame_left", window.parent.document).height(
					$(".main_conter").height() + 50);
			$("#main_frame_right", window.parent.document).height(
					$(".main_conter").height() + 50);
			*/
			if (($("body", window.parent.document).height() - 220) > $ (".main_conter").height()) {
				$("#main_frame_left", window.parent.document).height($("body", window.parent.document).height() - 220);
				$("#main_frame_right", window.parent.document).height($("body", window.parent.document).height() - 220);
			} else {
				$("#main_frame_left", window.parent.document).height($(".main_conter").height() + 50);
				$("#main_frame_right", window.parent.document).height($(".main_conter").height() + 50);
			}
			
		}
		
	</script>
	
			<script type="text/javascript"
			src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript"
			src="${ctx}/app/fax/js/date.js"></script>
	
	
	
	
</head>
<body topmargin="0" leftmargin="0" rightmargin="0"  style="margin:0;padding:0;overflow-x:hidden;background: white;"
	onload="adjustHeight();">
<!--  
	<table class="form_table" width="100%">
		<tr>
			<td align="right">
				<a href="javascript:void(0);" style="color:black; font-weight: bold;" class="easyui-linkbutton" onclick="javascript:msgBoxAction_init();">发送新消息</a>　　
				<a href="javascript:void(0);" style="color:black; font-weight: bold;" class="easyui-linkbutton" onclick="javascript:msgBoxAction_querySenderbox();">已发消息</a>　　
			</td>
		</tr>
	</table>
	-->
<div class="main_border_01">
  <div class="main_border_02">收件箱</div>
</div>
<div class="main_conter" style="overflow: hidden;padding:0;">

  <form id="queryForm" action="msgBoxAction_queryReceiverbox.act" method="post" autocomplete="off" >
   <!--  <div class="main2_border_01" style="margin-top: 10px;margin-bottom: 10px;">
      <p> 全部消息<span> </span> </p>
    </div> -->
    <h:page id="message_panel" width="100%" chkId="msgid" title="我的消息">
      <s:hidden name="msgtype" value="re"></s:hidden>
      <s:hidden id="pageSize" name="page.pageSize" value="25"></s:hidden>
      <d:table id="row" name="page.result" export="false" class="table" requestURI="msgBoxAction_queryReceiverbox.act">
        <d:col title="序号" class="display_centeralign">${row_rowNum + ((page.pageNo-1)*page.pageSize)}</d:col>
        <d:col   style="width:65px;"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>
        " >
        <input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.msgid}"/>
        </d:col>
        <d:col title="状态" style="width: 10%;" sortable="true" property="status" class="display_centeralign"/>
        <d:col title="标题" style="width: 30%;" sortable="true" property="title" class="display_leftalign" maxLength="10"/>
        <d:col title="发送时间"  style="width: 13%;" sortable="true" property="msgtime" class="display_leftalign" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
        <d:col title="发送人" style="width: 13%;" sortable="true" property="sender" class="display_leftalign"/>
        <d:col title="内容" class="display_leftalign" media="html" > <a href="javascript:void(0);" onclick="detailRecord('${row.msgid}','${row.sender}','${row.title }','${row.receiverid }',${row.senderId })">[显示]</a> </d:col>
      </d:table>
      
      <div class="main_pag">
      	<a style="cursor: pointer;" onclick="delRecord();">删除选中信息</a>
      </div>
      
      
    </h:page>
  </form>
</div>
</body>
</html>
<%-- 
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","删除成功!","info",
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","删除失败!","error");
</c:if>
</script>
--%>
<s:actionmessage theme="custom"/>