<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>
<%--
  - Author: ls
  - Date: 2010-11-20 18:08:00
  - Description: 发件人消息显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>

<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />

<title>已发通知</title>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript"></script>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>

<style type="css/text">
.in_sss_left{
	margin:0;
	padding:0;
	border:0;
}
</style>	
</head>
<body >
<div class="main_index">
<c:if test="${param.type eq 'A'}">
<jsp:include page="/app/integrateservice/headerWy.jsp">
	<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
</jsp:include>
</c:if>
<c:if test="${param.type eq 'B'}">
<jsp:include page="/app/integrateservice/headerWy.jsp">
	<jsp:param value="/app/pro/propertyIndex7.act" name="gotoParentPath"/>
</jsp:include>
</c:if>

<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
<div class="main_border_01">
	<div class="main_border_02">查看通知</div>
</div>
<div class="main_conter" style="padding: 15px;height: 555px;">
<form id="queryForm" name="queryForm" action="msgBoardAction_save1.act" method="post" autocomplete="off">
	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
	<page:pager id="list_panel" width="100%" buttons="${buttons}" title="物业通知">
		<d:table id="row" name="page.result" export="false" class="table" requestURI="msgBoardAction_queryAndDelete.act">
			<d:col title="标题" style="width:20%;" maxLength="20" class="display_leftalign" >
			  <a style="cursor:pointer;" onclick="viewForm(${row.MSGID});">${row.TITLE}</a>
			</d:col>
			<d:col title="内容" style="width:20%;" property="CONTENT" class="display_leftalign" maxLength="13"/>		
			<d:col title="发布人" style="width:15%" property="AUTHOR" class="display_centeralign"/>
			<d:col title="发布时间" style="width:15%" property="MSGTIME" class="display_centeralign" format="{0,date,yyyy年MM月dd日}"/>				
			<d:col class="display_centeralign" media="html" title="操作">
			    <a  style="cursor:pointer" onclick="viewForm('${row.MSGID}')" onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'">[详细]</a>&nbsp;
				<!-- a  style="cursor:pointer;display: none;" onclick="deleteData('${row.MSGID}')" onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'">[删除]</a>&nbsp; -->
			</d:col>
		</d:table>
	</page:pager>
</form>
</div>
</div>
<jsp:include page="/app/integrateservice/footer.jsp" />
</div>
<script type="text/javascript">
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
	function viewForm(msgId){
         showShelter('800','500','${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId='+msgId);
	}
	function modifyData(msgId){
		showShelter('800','500','${ctx}/common/bulletinMessage/msgModify.act?msgId='+msgId);
	}
	function closeInForm(){
       closeEasyWin("orgInput");
	}
	function deleteData(msgId){		       
	    easyConfirm('提示', '确定删除？', function(r){
			if (r){
				window.location.href="${ctx}/common/bulletinMessage/msgBoardAction_deleteData.act?msgId=" + msgId;
			}
		});			    
   	}
	
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					var ids = new Array();
					var pageNo = $("#pageNo").val();
					$.each(($("input[name=_chk]:checked")), function(i, n){
						ids[i] = $(n).val();
					});
					var idStr = ids.join(',');
					$.ajax({
						type: "POST",
						url: "${ctx}/common/bulletinMessage/msgBoardAction_deleteData.act",
						data: {"msgId": idStr},
						dataType: 'text',
						async : true,
						success: function(json){
							easyAlert("提示信息", json,"info");
							jumpPage(pageNo);
						}
					});
					//alert($("input[@type=checkbox]:checked").val());
					//$('#queryForm').attr("action","mergeHomeById.act");
					//$('#queryForm').attr("action","delJzsbCheck.act");  
					//$('#queryForm').submit();
				}
			});		
		}
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>