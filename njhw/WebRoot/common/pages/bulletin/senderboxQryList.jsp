

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
  - Author: ls
  - Date: 2010-11-18 17:20:46
  - Description: 公告显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>物业通知</title>	
	<%@ include file="/common/include/meta.jsp" %>
		<script type="text/javascript">
			function showBoard(msgid) {
  		var url = "${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId="+msgid;
		//openEasyWinNotResizable('winId','物业通知',url,'800','450',false);
		showShelter('660','510',url);
  		$("#board_content_"+msgid).css("fontWeight", "");
  	}
		function detailRecord(msgId) {
			var url = "${ctx}/common/bulletinMessage/msgBoardAction_detailtwo.act?msgId=" + msgId;
			openEasyWinNotResizable("winId","物业通知", url, '800','450', false);
		}
		
		function publishInform() {
			var url = "${ctx}/common/bulletinMessage/msgBoardAction_init.act";	
			openEasyWin("winId","发布物业通知", url, '800','450', false);
			
		}
		
		function closeInForm(){
	       closeEasyWin("winId");
	
		}
		
		function deleteData(msgId){		       
		    easyConfirm('提示', '确定删除？', function(r){
				if (r){
					window.location.href="${ctx}/common/bulletinMessage/msgBoardAction_deleteData.act?msgId=" + msgId;
				}
			});			    
			
    	}
	</script>
	
	<style type="text/css">
		.wenzi1{
			font-family: "黑体";
			font-size: 13px;
			color: #000000;
			line-height:15px;
		}
		a.wenzi1:link {
			font-family: "宋体";
			font-size: 13px;
			color: #000000;
			text-decoration: none;
		}
		a.wenzi1:visited {
			font-family: "宋体";
			font-size: 13px;
			color: #000000;
			text-decoration: none;
		}
		a.wenzi1:hover {
			font-family: "宋体";
			font-size: 13px;
			color: #003399;
			text-decoration: none;
		}
	</style>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0"  style="min-width: 1000px;">

<div class="main_index">
<jsp:include page="/app/integrateservice/header.jsp">
<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
					<div class="bgsgl_border_right">
				 物业通知
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				<form id="queryForm" action="msgBoardAction_save.act" method="post" autocomplete="off">
             <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
				<div class="show_pop_bottom">
				
				<a  style="folat:left;cursor:pointer;"  onclick="javascript:publishInform();">发布物业通知</a>　　
</div>
		<h:page id="bulletin_panel" width="100%" chkId="msgid" title="物业通知">
			<d:table id="row" name="page.result" export="false" class="table" requestURI="msgBoardAction_queryAndDelete.act">
				<d:col title="序号" class="display_centeralign">${row_rowNum + ((page.pageNo-1)*page.pageSize)}</d:col>
				<d:col title="标题" style="width:30%;"  property="TITLE" class="display_leftalign" maxLength="13"/>	
				<d:col title="内容" style="width:30%;"  property="CONTENT" class="display_leftalign" maxLength="13"/>		
				<d:col title="发布人" style="width:12%" property="AUTHOR" class="display_centeralign"/>
				<d:col title="发布时间" style="width:12%" property="MSGTIME" class="display_centeralign" format="{0,date,yyyy年MM月dd日}"/>				

				<d:col class="display_centeralign" media="html" title="操作">
				    <a href="javascript:void(0);" onclick="showBoard(${row.MSGID});">[显示]</a>
					<a  href="javascript:void(0);" onclick="deleteData('${row.MSGID}')">[删除]</a>&nbsp;
				</d:col>
			</d:table>
		</h:page>
	</form>

        					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>

	</body>
<s:actionmessage theme="custom" cssClass="success"/>
</html>
