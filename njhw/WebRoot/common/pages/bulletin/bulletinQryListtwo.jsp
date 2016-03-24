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
	
			<script src="${ctx}/common/pages/bulletin/js/showBulletin.js" type="text/javascript"></script>	

			<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
		<script type="text/javascript">
		
	function showBoard(msgid) {
  		var url = "${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId="+msgid;
		//openEasyWinNotResizable('winId','物业通知',url,'800','450',false);
		showShelters11('660','510',url);
	
		
  	}


		
		$(function () {
			changedisplaytagstyle();
		});
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

	
	<body>
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
				
				<form id="queryForm" name="carManageForm" action="msgBoardAction_query.act" method="post" autocomplete="off">
             <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
        
 	<h:page id="bulletin_panel" width="100%" chkId="msgid" title="通知列表">
		<d:table id="row" name="page.result" export="false" class="table" requestURI="msgBoardAction_query.act">
			<d:col title="序号" class="display_centeralign">${row_rowNum + ((page.pageNo-1)*page.pageSize)}</d:col>
						
				<c:if test="${row.ISREAD == 'false'}">
						<d:col title="标题" style="width:30%;"class="display_leftalign">
					    <a style="cursor:pointer;color:black;" onclick="showBoard(${row.MSGID});"> ${row.TITLE} </a>
				    	 </d:col>
				</c:if>		
				<c:if test="${row.ISREAD == 'true'}">
						<d:col title="标题" style="width:30%;"class="display_leftalign">
					    <a style="cursor:pointer;color:orange;" onclick="showBoard(${row.MSGID});"> ${row.TITLE} </a>
				    	 </d:col>
				</c:if>				
								
					<d:col title="内容" style="width:30%;" sortable="true" property="CONTENT" class="display_leftalign"  maxLength="13"/>			
			
			<c:if test="${row.ISREAD == 'true'}">
				<d:col title="状态" style="65px;" class="display_centeralign" value="已读"/>
			</c:if>
			<c:if test="${row.ISREAD == 'false'}">
				<d:col title="状态" style="65px;"   class="display_centeralign" value="未读"/>
			</c:if>
			<d:col title="发布时间" style="width:12%"  sortable="true" property="MSGTIME" class="display_centeralign" format="{0,date,yyyy年MM月dd日}"/>
			<d:col title="发布人" style="width:12%" sortable="true" property="AUTHOR" class="display_centeralign" />
			
				
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