<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%
	String notificationId = request.getParameter("notificationId");
	String msgId = request.getParameter("msgId");
	String status = request.getParameter("status");
%>
<%--
  - Author: ls
  - Date: 2010-11-20 18:10:13
  - Description: 发件人消息内容显示页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>消息内容</title>
<%@ include file="/common/include/meta.jsp"%>
<link rel="stylesheet"
	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
<style>
.green {
	color: green;
}

.red {
	color: red;
}

.orange {
	color: orange;
}

.black {
	color: black;
}

.white {
	color: white;
}

.blue {
	color: blue;
}

.yellow {
	color: yellow;
}

.grey {
	color: grey;
}

.custom-combobox {
	position: relative;
	display: inline-block;
}

.custom-combobox-toggle {
	position: absolute;
	top: 0;
	bottom: 0;
	margin-left: -1px;
	padding: 0;
	/* support: IE7 */
	*height: 1.7em;
	*top: 0.1em;
}

.custom-combobox-input {
	margin: 0;
	padding: 0.3em;
}
</style>
<style>
/* outbox */
.thead_col_1 {
	width: 30%;
	float: left;
	background-color: #ebebeb;
	text-align: center;
}

.thead_col_2 {
	width: 30%;
	float: left;
	background-color: #ebebeb;
	text-align: center;
}

.thead_col_3 {
	width: auto;
	background-color: #ebebeb;
	text-align: center;
}

.tbody_col_1 {
	width: 30%;
	float: left;
	color: #7f90b3;
	background: url(${ctx}/app/integrateservice/images/border_left.jpg) left
		center no-repeat;
	text-align: left;
}

.tbody_col_1 .col1 {
	padding-left: 20px;
	width: 100%;
}

.tbody_col_2 {
	width: 30%;
	float: left;
	text-align: center;
}

.tbody_col_3 {
	width: auto;
	text-align: center;
}

.tbody_col_3 a {
	width: auto;
	text-align: center;
	color: orange;
	text-decoration: none;
}
</style>
</head>
<body style="background: white; margin: 0;padding: 0;">
	<div class="main1_main2_right">
		<form id="queryForm"
			action="msgBoxAction_queryOutboxStatus.act?notificationId=${notificationId}"
			method="post" autocomplete="off">
			<div class="main2_border_01" style="margin-bottom: 10px;">
				<p style="padding-bottom: 5px; ">
					阅读状态 <span> <select id="combobox" style="width: 100px;">
							<option value="-1" selected="selected">全部</option>
							<option value="1">已读</option>
							<option value="0">未读</option>
					</select>
					</span> <span id="count" style="float: right;margin-right: 5px;"> </span>

				</p>
			</div>
			<h:page id="message_panel" width="100%" chkId="msgid" title="已发消息">
				<s:hidden id="pageSize" name="page.pageSize" value="25"></s:hidden>
				<s:hidden name="msgtype" value="se"></s:hidden>
				<d:table id="row" name="page.result" export="false" class="table"
					style="border:none;"
					requestURI="msgBoxAction_queryOutboxStatus.act?notificationId=${notificationId}">
					<d:col title="接收人" style="width: 30%;" sortable="true"
						property="receiver" class="display_leftalign" />
					<d:col title="处室" style="width: 30%;" sortable="true"
						property="busnId" class="display_leftalign" />
					<d:col title="阅读状态" style="width: auto;" sortable="true"
						property="status" class="display_leftalign" />
				</d:table>
			</h:page>
		</form>
	</div>
	<%-- <table align="center" border="0" width="100%" class="form_table">
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;标题：</td>
			<td>&nbsp;<s:text name="title" /></td>
		</tr>
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;接收人：</td>
			<td>&nbsp;<s:text name="receiver" /></td>
		</tr>
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;发送时间：</td>
			<td>&nbsp;<s:date name="msgtime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;内容：</td>
			<td><s:text name="content" /></td>
		</tr>
	</table> --%>
	<script type="text/javascript"
		src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/common/pages/message/js/queryString.js"></script>
	<script type="text/javascript">
		$("#message_panel tbody tr:eq(0)").hide();
		//$("#message_panel").css("marginBottom", "-30px");
		$("#main_peag").parent().remove();
		$(function($) {
			
			//$("#message_panel").find("thead").addClass("main_main2_list");
			var status = getQueryString("status");
			//全部
			if (status == -1) {
				$("#count").text("共 "+$("#row").find(".orange").length+"/"+$("#row").find("span").length+" 条");
			}
			//其他：已读和未读
			else
			{
				$("#count").text("共 "+$("#row").find("span").length+" 条");
			}
			
			//$("#count").text($("#row").find(".orange").length);
			$("select").val(getQueryString("status"));
			$("select")
					.change(
							function() {

								$("#iframe-message-detail",
										window.parent.document)
										.attr(
												"src",
												"msgBoxAction_queryOutboxStatus.act?status="
														+ $(this).val()
														+ "&msgId="
														+ getQueryString("msgId")
														+ "&notificationId="
														+ getQueryString("notificationId"));
							});
		});
	</script>
</body>
</html>